package com.qiu.keepaccount.model.type;

import com.qiu.keepaccount.entity.AccountType;
import com.qiu.keepaccount.entity.User;

import org.litepal.LitePal;

/**
 * 账目类型数据接口实现类
 */
public class AccountTypeModel implements IAccountTypeModel {
    /**
     * 根据指定的类型Id检索账目类型
     *
     * @param id
     * @return
     */
    @Override
    public AccountType getAccountType(int id) {
        AccountType accountType = LitePal.where("id = ?",String.valueOf(id))
                .findFirst(AccountType.class);
        return accountType;
    }

    /**
     * 添加账目类型  用户只有登录后才有权限操作
     *
     * @param accountType
     */
    @Override
    public void addAccountType(AccountType accountType) {
        accountType.saveAsync();
    }
}
