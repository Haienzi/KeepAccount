package com.qiu.keepaccount.mvp.editaccount;

import com.qiu.keepaccount.base.BaseView;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Budget;
import com.qiu.keepaccount.entity.User;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/31 14:07
 * @Description 记账页面契约类用于约定统一的接口
 */
public interface EditAccountContract {
    interface IEditAccountView extends BaseView<IEditAccountPresenter> {
        /**
         * 检索账目成功
         * @param list
         */
        void queryAccount(List<Account> list);

        /**
         * 删除账目
         */
        void deleteAccount();

        /**
         * 编辑预算信息
         * @param budget
         */
        void setBudget(Budget budget);
        /**
         * 选择日期
         */
        void showDateDialog();

        /**
         * 添加预算
         */
        void showEditBudgetDialog();

        /**
         * 跳转到账本界面
         */
        void jumpToBookActivity();

        /**
         * 添加记录
         */
        void jumpToAddAccount();

    }

    interface IEditAccountPresenter{
        /**
         * 保存预算
         */
        void saveBudget(int userId,Budget budget);

        /**
         * 检索预算信息
         * @param userId
         * @param date
         */
        void queryBudget(int userId,String date);
        /**
         * 检索记账记录
         * @param user
         * @param queryDate
         */
        void queryAccount(User user, String queryDate);

        /**
         * 删除记账记录
         * @param account
         */
        void deleteAccount(Account account);
    }
}
