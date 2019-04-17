package com.qiu.keepaccount.model.account;


import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.LinearPointData;
import com.qiu.keepaccount.entity.PiePointData;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/2 10:46
 * @Description 账目信息接口
 */
public interface IAccountModel {

    /**
     * 用户已登录时 保存账目信息
     */
    void saveAccount(Account account);


    /**
     * 更新账目信息
     */
    void updateAccount(Account account);


    /**
     * 删除一条账目数据
     */
    void deleteAccount(Account account);

    /**
     * 根据账目id获取账目信息
     * @param id
     * @return
     */
    Account getAccountById(int id);

    /**
     * 查找指定用户指定日期内所有的账目信息
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    List<Account> queryAccounts(String startDate, String endDate, int type);

    /**
     * 查找指定用户指定日期所有的账目信息
     * @param startDate 开始时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    List<Account> queryAccounts(String startDate, int type);

    /**
     * 查找指定日期内所有账本中账目总支出，收入
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    double queryTotalCostOrIncome(String startDate, String endDate,int type);

    /**
     * 返回图表需要的数据
     * @param startDate
     * @param endDate
     * @return
     */
    public List<LinearPointData> queryChartData(String startDate, String endDate,int type);

    /**
     * 返回饼图需要的数据
     * @param startDate
     * @param endDate
     * @return
     */
    public List<PiePointData> queryPieData(String startDate, String endDate, int type);



}
