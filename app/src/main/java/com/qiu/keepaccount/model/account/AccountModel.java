package com.qiu.keepaccount.model.account;

import android.database.Cursor;

import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.LinearPointData;
import com.qiu.keepaccount.entity.PiePointData;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/2 10:46
 * @Description 账目信息实现
 */
public class AccountModel implements IAccountModel {

    /**
     * 用户已登录时 保存账目信息
     * @param account
     */
    @Override
    public void saveAccount(Account account) {
        account.save();
    }


    /**
     * 用户已登录时 更新账目信息
     *
     * @param account
     */
    @Override
    public void updateAccount( Account account) {
        account.update(account.getId());
    }

    /**
     * 删除一条账目数据
     *
     * @param account
     */
    @Override
    public void deleteAccount(Account account) {
        LitePal.delete(Account.class,account.getId());
    }

    /**
     * 根据账目id获取账目信息
     *
     * @param id
     * @return
     */
    @Override
    public Account getAccountById(int id) {
        Account account = LitePal.find(Account.class,id);
        return account;
    }

    /**
     * 查找指定用户指定日期内所有的账目信息
     *
     * @param startDate 开始时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    @Override
    public List<Account> queryAccounts(String startDate, int type) {
        List<Account> accountList = null;

        if(type == -1){
            accountList= LitePal.where("createTime = ?",
                     startDate)
                    .order("createTime desc")
                    .find(Account.class);
        }else {
            accountList= LitePal.where("createTime = ? and accountType = ?",
                    startDate,String.valueOf(type))
                    .order("createTime desc")
                    .find(Account.class);
        }

        return accountList;
    }

    /**
     * 查找指定用户指定日期内所有的账目信息
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    @Override
    public List<Account> queryAccounts(String startDate, String endDate, int type) {
        List<Account> accountList = null;

        if(type == -1){
            accountList= LitePal.where("createTime >= ? and createTime <= ?",
                    startDate,endDate)
                    .order("createTime desc")
                    .find(Account.class);
        }else{
            accountList= LitePal.where("createTime >= ? and createTime <= ? and type = ?",
                    startDate,endDate,String.valueOf(type))
                    .order("createTime desc")
                    .find(Account.class);
        }

        return accountList;
    }

    /**
     * 查找指定日期内所有账本中账目总支出，收入
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      类型 1、支出 2、收入 -1 支出加收入
     */
    @Override
    public double queryTotalCostOrIncome(String startDate, String endDate, int type) {
        double amount = 0.00;

        if(type == -1){
            double income = LitePal.where(" type = ? and createTime >= ? and createTime <= ?",
                     String.valueOf(2),startDate,endDate)
                    .sum(Account.class," amount",Double.class);
            double cost =  LitePal.where("type = ? and createTime >= ? and createTime <= ?",
                    String.valueOf(1),startDate,endDate)
                    .sum(Account.class,"amount",Double.class);
            amount = income + cost;
        }else if(type == 2){
            amount =  LitePal.where("type = ? and createTime >= ? and createTime <= ?",
                    String.valueOf(2),startDate,endDate)
                    .sum(Account.class,"amount",Double.class);
        }else{
            amount =  LitePal.where("type = ? and createTime >= ? and createTime <= ?",
                    String.valueOf(1),startDate,endDate)
                    .sum(Account.class,"amount",Double.class);
        }
        return amount;
    }

    /**
     * 返回图表需要的数据
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<LinearPointData> queryChartData(String startDate, String endDate,int type) {
        Cursor cursor = null;
        if(type == -1 ){
            cursor = LitePal.findBySQL("select createDate,sum(Amount) from Account where " +
                    " createDate >= ? and createDate <= ? group by createDate",startDate,endDate);
        }else{
            cursor = LitePal.findBySQL("select createDate,sum(Amount) from Account where " +
                    " createDate >= ? and createDate <= ? and type = ? group by createDate",
                    startDate,endDate,String.valueOf(type));
        }
        cursor.moveToFirst();
        List<LinearPointData> pointDataList = new ArrayList<>();
        for(int i=0;i<cursor.getCount();i++){
            LinearPointData pointData = new LinearPointData(cursor);
            pointDataList.add(pointData);
            cursor.moveToNext();
        }
        return pointDataList;
    }

    /**
     * 返回饼图需要的数据
     *
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */
    @Override
    public List<PiePointData> queryPieData(String startDate, String endDate, int type) {
        Cursor cursor = LitePal.findBySQL("select typeId,sum(Amount) from Account where " +
                        " createDate >= ? and createDate <= ? and type = ? group by typeId",
                startDate,endDate,String.valueOf(type));

        cursor.moveToFirst();
        List<PiePointData> pointDataList = new ArrayList<>();
        for(int i=0;i<cursor.getCount();i++){
            PiePointData pointData = new PiePointData(cursor);
            pointDataList.add(pointData);
            cursor.moveToNext();
        }
        return pointDataList;
    }


}
