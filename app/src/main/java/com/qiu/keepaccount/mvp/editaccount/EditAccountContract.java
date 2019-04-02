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
        void querySuccess(List<Account> list);
        void queryFail(Error e);
        void deleteSuccess();
        void deleteFail(Error e);

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
         * 跳转到记账详情界面
         * @param account 记录
         */
        void jumpToAccountInfo(Account account);

    }

    interface IEditAccountPresenter{
        /**
         * 保存预算
         */
        void saveBudget(Budget budget);

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
