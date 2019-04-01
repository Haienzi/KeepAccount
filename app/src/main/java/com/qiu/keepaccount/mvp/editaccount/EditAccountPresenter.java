package com.qiu.keepaccount.mvp.editaccount;

import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Budget;
import com.qiu.keepaccount.entity.User;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;

import java.util.List;

/**
 *
 */
public class EditAccountPresenter implements EditAccountContract.Presenter {
    /**
     * 保存预算
     */
    @Override
    public void saveBudget(Budget budget) {
        budget.save();
    }

    /**
     * 检索记账记录
     *
     * @param user
     * @param queryDate
     */
    @Override
    public List<Account> queryAccount(User user, String queryDate) {
        List<Account> accountList = null;
        try {
            accountList = LitePal.where("createTime = ?",queryDate)
                     .order("createTime desc")
                     .find(Account.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;

    }

    /**
     * 删除记账记录
     *
     * @param account
     */
    @Override
    public void deleteAccount(Account account) {
        LitePal.delete(Account.class,account.getId());
    }
}
