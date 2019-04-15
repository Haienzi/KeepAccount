package com.qiu.keepaccount.mvp.bookaccount;

import com.qiu.keepaccount.base.BasePresenter;
import com.qiu.keepaccount.base.BaseView;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Book;

import java.util.List;

public interface BookAccountContract {
    interface BookAccountView extends BaseView<BookAccountContract.IBookAccountPresenter> {
     /**
      * 显示支出记录
      */
      void selectCost();

        /**
         * 显示收入记录
         */
        void selectIncome();

        /**
         * 显示所有记录
         */
        void selectAll();

        /**
         * 设置支出记录
         */
        void setListData(List<Account> accountList);
        

        /**
         * 设置总收入
         */
      void setTotalIncome(double income);

        /**
         * 设置总支出
         */
      void setTotalCost(double cost);

        /**
         * 设置总金额
         */
      void setTotalMoney(double totalMoney,double income,double cost);
        /**
         * 弹出删除对话框
         */
      void showDeleteDialog(Account account);

        /**
         * 跳转到编辑记录界面
         */
      void jumpToEditAccount(Account account);


    }


    /**
     * @author mqh
     */
    interface IBookAccountPresenter extends BasePresenter {

        /**
         * 查找账本内的记录
         * @param book
         */
       void selectAccountByBook(Book book,int type);

        /**
         * 检索账本总金额
         * @param book
         */
       void selectTotalMoney(Book book);

        /**
         * 检索账本总收入
         * @param book
         */
       void selectTotalIncome(Book book);

        /**
         * 检索账本总花费
         * @param book
         */
       void selectTotalCost(Book book);


        /**
         * 删除账目
         * @param account
         */
       void deleteAccountById(Account account);


    }
}
