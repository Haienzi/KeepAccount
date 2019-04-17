package com.qiu.keepaccount.mvp.chart;

import com.qiu.keepaccount.model.account.IAccountModel;

public class ChartPresenterImpl implements ChartContract.IChartPresenter {

    private ChartContract.ChartView mView;
    private IAccountModel mModel;

    public ChartPresenterImpl(ChartContract.ChartView chartView,IAccountModel accountModel){
        mView = chartView;
        mModel = accountModel;
        mView.setPresenter(this);
    }


    /**
     * 数据加载初始化等
     */
    @Override
    public void start() {

    }

    @Override
    public void selectLinearData(String startDate, String endDate) {

    }

    @Override
    public void selectPieData(String startDate, String endDate) {

    }
}
