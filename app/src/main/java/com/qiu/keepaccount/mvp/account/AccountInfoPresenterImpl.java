package com.qiu.keepaccount.mvp.account;

import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.User;
import com.qiu.keepaccount.model.account.IAccountModel;

public class AccountInfoPresenterImpl implements AccountInfoContract.IAccountInfoPresenter{

   private final AccountInfoContract.IAccountInfoView mView;
   private IAccountModel mAccountModel;
   public AccountInfoPresenterImpl(AccountInfoContract.IAccountInfoView view,IAccountModel model){
    mView = view;
    mAccountModel = model;
    mView.setPresenter(this);

   }
    /**
     * 保存账目
     *
     * @param user    用户
     * @param account 账目
     */
    @Override
    public void saveAccount(User user, Account account) {
      mAccountModel.saveAccount(user,account);
    }

    /**
     * 更改账目
     *
     * @param user    用户
     * @param account 账目
     */
    @Override
    public void updateAccount(User user, Account account) {
     mAccountModel.updateAccount(user,account);
    }

    /**
     * 数据加载初始化等
     */
    @Override
    public void start() {

    }
}
