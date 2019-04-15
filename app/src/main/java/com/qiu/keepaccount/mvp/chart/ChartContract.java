package com.qiu.keepaccount.mvp.chart;

import com.qiu.keepaccount.base.BasePresenter;
import com.qiu.keepaccount.base.BaseView;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.User;

import java.util.List;

public interface ChartContract {
    interface ChartView extends BaseView<IChartPresenter> {
        /**
         * 切换为线性
         * @param accountList
         */
      void setLinerData(List<Account> accountList);

        /**
         * 切换为饼图
         * @param accountList
         */
      void setCircleData(List<Account> accountList);

      /**
       * 导出为excel文件
       */
      void exportToExcel(List<Account> accountList);

        /**
         * 弹出选择时间对话框
         */
      void showDateDoubleDialog();

        /**
         * 填充List中的数据
         * @param accountList
         */
      void setListData(List<Account> accountList);
    }


    /**
     * @author mqh
     */
    interface IChartPresenter extends BasePresenter {

        void selectAccounts(User user,String startDate,String endDate,int type);

        void selectChartData(User user, String startDate, String endDate);

    }
}
