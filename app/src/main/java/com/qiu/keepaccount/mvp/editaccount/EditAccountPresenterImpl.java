package com.qiu.keepaccount.mvp.editaccount;

import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Budget;
import com.qiu.keepaccount.entity.User;
import com.qiu.keepaccount.model.account.AccountModel;
import com.qiu.keepaccount.model.account.IAccountModel;
import com.qiu.keepaccount.model.budget.BudgetModel;
import com.qiu.keepaccount.model.budget.IBudgetModel;

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

    }

    /**
     * 保存预算
     */
    @Override
    public void saveBudget(Budget budget) {
        mBudgetModel.addBudget(budget);
    }

    /**
     * 检索记账记录
     *
     * @param user
     * @param queryDate
     */
    @Override
    public void queryAccount(User user, String queryDate) {
        mAccountModel.queryAccounts(user,queryDate,null,-1);
    }

    /**
     * 删除记账记录
     *
     * @param account
     */
    @Override
    public void deleteAccount(Account account) {
        mAccountModel.deleteAccount(account);
    }
}
