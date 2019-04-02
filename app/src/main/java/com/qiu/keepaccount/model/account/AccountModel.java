package com.qiu.keepaccount.model.account;

import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.User;
import org.litepal.LitePal;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/2 10:46
 * @Description 账目信息实现
 */
public class AccountModel implements IAccountModel {

    /**
     * 用户已登录时 保存账目信息
     *
     * @param user
     * @param account
     */
    @Override
    public void saveAccount(User user, Account account) {
        account.setUserId(user.getId());
        account.save();
    }

    /**
     * 用户未登录时 保存账目信息
     *
     * @param account
     */
    @Override
    public void saveAccount(Account account) {
        account.save();
    }

    /**
     * 用户已登录时 更新账目信息
     *
     * @param user
     * @param account
     */
    @Override
    public void updateAccount(User user, Account account) {
        account.setUserId(user.getId());
        account.update(account.getId());
    }

    /**
     * 用户未登录时 更新账目信息
     *
     * @param account
     */
    @Override
    public void updateAccount(Account account) {
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
     * 查找指定用户指定日期内所有的账目信息
     *
     * @param user      用户
     * @param startDate 开始时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    @Override
    public List<Account> queryAccounts(User user, String startDate, int type) {
        List<Account> accountList = null;
        if(user != null){ //用户未登录
           accountList= LitePal.where("createTime = ?",startDate)
                               .order("createTime desc")
                               .find(Account.class);

        }else{           //用户已登录
            accountList= LitePal.where("userId = ? and createTime = ?",startDate)
                    .order("createTime desc")
                    .find(Account.class);
        }

        return null;
    }

    /**
     * 查找指定用户指定日期内所有的账目信息
     *
     * @param user      用户
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    @Override
    public List<Account> queryAccounts(User user, String startDate, String endDate, int type) {
        List<Account> accountList = null;
        if(user != null){ //用户未登录
            accountList= LitePal.where("createTime <= ? and createTime >= ?",
                     startDate,endDate)
                    .order("createTime desc")
                    .find(Account.class);

        }else{           //用户已登录
            accountList= LitePal.where("userId = ? and  createTime <= ? and createTime >= ?",
                     startDate,endDate)
                    .order("createTime desc")
                    .find(Account.class);
        }

        return null;
    }

    /**
     * 查找指定日期内所有账本中账目总支出，收入
     *
     * @param user      用户
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    @Override
    public double queryTotalCostOrIncome(User user, String startDate, String endDate, int type) {
        return 0;
    }


}
