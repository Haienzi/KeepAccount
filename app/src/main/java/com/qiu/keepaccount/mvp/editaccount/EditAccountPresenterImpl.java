package com.qiu.keepaccount.mvp.editaccount;

import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Budget;
import com.qiu.keepaccount.model.account.AccountModel;
import com.qiu.keepaccount.model.account.IAccountModel;
import com.qiu.keepaccount.model.budget.BudgetModel;
import com.qiu.keepaccount.model.budget.IBudgetModel;

import java.util.List;

/**
 *
 */
public class EditAccountPresenterImpl implements EditAccountContract.IEditAccountPresenter {

    private EditAccountContract.IEditAccountView mView;

    private IAccountModel mAccountModel;

    private IBudgetModel mBudgetModel;

    public EditAccountPresenterImpl(EditAccountContract.IEditAccountView view){
        this.mView = view;
        mAccountModel = new AccountModel();
        mBudgetModel = new BudgetModel();
        view.setPresenter(this);
    }

    /**
     * 保存预算
     */
    @Override
    public void saveBudget(Budget budget) {
        mBudgetModel.addBudget(budget);

    }

    /**
     * 检索预算信息
     *
     * @param date
     */
    @Override
    public void queryBudget( String date) {
        List<Budget> budgets = mBudgetModel.queryBudget(date);
        if(budgets.size() != 0){
            mView.setBudget(budgets.get(0));
        }
    }


    /**
     * 检索记账记录
     *
     * @param queryDate
     */
    @Override
    public void queryAccount(String queryDate) {
        List<Account> accounts = mAccountModel.queryAccounts(queryDate,queryDate,-1);
        mView.queryAccount(accounts);
    }

    /**
     * 删除记账记录
     *
     * @param account
     */
    @Override
    public void deleteAccount(Account account) {
        mAccountModel.deleteAccount(account);
        mView.deleteAccount();
    }
}
