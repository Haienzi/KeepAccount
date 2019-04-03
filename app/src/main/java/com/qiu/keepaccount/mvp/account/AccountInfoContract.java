package com.qiu.keepaccount.mvp.account;

import com.qiu.keepaccount.base.BasePresenter;
import com.qiu.keepaccount.base.BaseView;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.User;

/**
 * @author mqh
 * 账目统一接口契约类
 */
public interface AccountInfoContract {

    interface IAccountInfoView extends BaseView<BasePresenter>{
        /**
         * 保存
         */
        void save();

        /**
         *查找支出类型
         */
        void selectCost();

        /**
         *查找收入类型
         */
         void selectIncome();

        /**
         * 显示选择日期
         */
        void showSelectDateDialog();

        /**
         * 显示编辑金额对话框
         */
        void showInputMoneyDialog();


    }


    /**
     * @author mqh
     */
    interface IAccountInfoPresenter extends BasePresenter{

        /**
         * 保存账目
         * @param user 用户
         * @param account 账目
         */
        void saveAccount(User user, Account account);

        /**
         * 更改账目
         * @param user 用户
         * @param account 账目
         */
        void updateAccount(User user,Account account);


    }
}
