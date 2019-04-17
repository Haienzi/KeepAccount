package com.qiu.keepaccount.mvp.chart;

import com.qiu.keepaccount.entity.LinearPointData;
import com.qiu.keepaccount.entity.PiePointData;
import com.qiu.keepaccount.model.account.IAccountModel;

import java.util.List;

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
        List<LinearPointData> allList = mModel.queryChartData(startDate,endDate,-1);
        mView.setLinerData(allList);
        List<LinearPointData> costList = mModel.queryChartData(startDate,endDate,1);
        mView.setLinerData(costList);
        List<LinearPointData> incomeList = mModel.queryChartData(startDate,endDate,2);
        mView.setLinerData(incomeList);

    }

    @Override
    public void selectPieData(String startDate, String endDate) {
        List<PiePointData> costList = mModel.queryPieData(startDate,endDate,1);
        mView.setCostPieChart(costList);
        List<PiePointData> incomeList = mModel.queryPieData(startDate,endDate,2);
        mView.setCostPieChart(incomeList);
    }
}
