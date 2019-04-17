package com.qiu.keepaccount.model.type;

import com.qiu.keepaccount.entity.AccountType;

/**
 *
 */
public interface IAccountTypeModel {
    /**
     * 根据指定的类型Id检索账目类型
     * @param id
     * @return
     */
    public AccountType getAccountType(int id);

    /**
     * 添加账目类型  用户只有登录后才有权限操作
     * @param accountType
     */
    public void addAccountType(AccountType accountType);
}
