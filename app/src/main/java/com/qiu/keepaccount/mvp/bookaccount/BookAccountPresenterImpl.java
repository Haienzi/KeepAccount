package com.qiu.keepaccount.mvp.bookaccount;

import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Book;
import com.qiu.keepaccount.model.account.AccountModel;
import com.qiu.keepaccount.model.account.IAccountModel;
import com.qiu.keepaccount.model.book.IBookModel;

import java.util.List;

public class BookAccountPresenterImpl implements BookAccountContract.IBookAccountPresenter {
    private BookAccountContract.BookAccountView mView;
    private IBookModel mModel;
    private IAccountModel mAccountModel;

    public BookAccountPresenterImpl(BookAccountContract.BookAccountView view, IBookModel model){
        mView = view;
        mModel = model;
        mAccountModel = new AccountModel();
        mView.setPresenter(this);
    }
    /**
     * 查找账本内的记录
     *
     * @param book
     */
    @Override
    public void selectAccountByBook(Book book,int type) {
        List<Account> accounts = mModel.queryBookAccounts(book,type);
        mView.setListData(accounts);
    }

    /**
     * 检索账本总金额
     *
     * @param book
     */
    @Override
    public void selectTotalMoney(Book book) {
        double total = mModel.queryBookCostOrIncome(book,-1);
        double cost = mModel.queryBookCostOrIncome(book,1);
        double income = mModel.queryBookCostOrIncome(book,2);
        mView.setTotalMoney(total,income,cost);
    }

    /**
     * 检索账本总收入
     *
     * @param book
     */
    @Override
    public void selectTotalIncome(Book book) {
        double income = mModel.queryBookCostOrIncome(book,-2);
        mView.setTotalIncome(income);
    }

    /**
     * 检索账本总花费
     *
     * @param book
     */
    @Override
    public void selectTotalCost(Book book) {
        double cost = mModel.queryBookCostOrIncome(book,1);
        mView.setTotalCost(cost);
    }



    /**
     * 删除账目
     *
     * @param account
     */
    @Override
    public void deleteAccountById(Account account) {
        mAccountModel.deleteAccount(account);
    }

    /**
     * 数据加载初始化等
     */
    @Override
    public void start() {

    }
}
