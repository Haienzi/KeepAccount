package com.qiu.keepaccount.mvp.chart;

import com.qiu.keepaccount.entity.User;
import com.qiu.keepaccount.model.account.IAccountModel;

public class ChartPresenterImpl implements ChartContract.IChartPresenter {

    private ChartContract.ChartView mView;
    private IAccountModel mModel;

    public ChartPresenterImpl(ChartContract.ChartView chartView,IAccountModel accountModel){
        mView = chartView;
        mModel = accountModel;
        mView.setPresenter(this);
    }
    @Override
    public void selectAccounts(User user, String startDate, String endDate, int type) {

    }

    @Override
    public void selectChartData(User user, String startDate, String endDate) {

    }

    /**
     * 数据加载初始化等
     */
    @Override
    public void start() {

    }
}
