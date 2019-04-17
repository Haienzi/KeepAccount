package com.qiu.keepaccount.mvp.settings;

import com.qiu.keepaccount.base.BaseView;
import com.qiu.keepaccount.entity.Budget;

public interface SettingContract {
    interface ISettingView extends BaseView<SettingContract.ISettingPresenter> {

    }

    interface ISettingPresenter{
        /**
         * 保存预算
         */
        void saveBudget(Budget budget);

        /**
         * 检索预算信息
         * @param date
         */
        void queryBudget(String date);

    }
}
