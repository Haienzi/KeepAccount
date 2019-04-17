package com.qiu.keepaccount.mvp.chart;

import com.qiu.keepaccount.base.BasePresenter;
import com.qiu.keepaccount.base.BaseView;
import com.qiu.keepaccount.entity.LinearPointData;
import com.qiu.keepaccount.entity.PiePointData;

import java.util.List;

public interface ChartContract {
    interface ChartView extends BaseView<IChartPresenter> {
        /**
         * 线性图
         * @param accountList
         */
      void setLinerData(List<LinearPointData> accountList);
        /**
         * 线性图
         * @param accountList
         */
        void setCostLinerData(List<LinearPointData> accountList);
        /**
         * 线性图
         * @param accountList
         */
        void setIncomeLinerData(List<LinearPointData> accountList);

        /**
         * 支出分布饼图
         * @param accountList
         */
        void setCostPieChart(List<PiePointData> accountList);

        /**
         * 收入分布饼图
         * @param accountList
         */
        void setIncomePieChart(List<PiePointData> accountList);


    }


    /**
     * @author mqh
     */
    interface IChartPresenter extends BasePresenter {

        void selectLinearData(String startDate,String endDate);

        void selectPieData(String startDate, String endDate);

    }
}
