package com.qiu.keepaccount.mvp.settings;

import com.qiu.keepaccount.entity.Budget;
import com.qiu.keepaccount.model.budget.IBudgetModel;

import java.util.List;

public class SettingPrensterImpl implements SettingContract.ISettingPresenter {

    private SettingContract.ISettingView mView;
    private IBudgetModel mModel;

    public SettingPrensterImpl(SettingContract.ISettingView view,IBudgetModel budgetModel){
        mView =view;
        mModel = budgetModel;
    }
    /**
     * 保存预算
     *
     * @param budget
     */
    @Override
    public void saveBudget(Budget budget) {
        mModel.updateBudget(budget);
    }

    /**
     * 检索预算信息
     *
     * @param date
     */
    @Override
    public void queryBudget(String date) {
        List<Budget> budget = mModel.queryBudget(date);

    }
}
