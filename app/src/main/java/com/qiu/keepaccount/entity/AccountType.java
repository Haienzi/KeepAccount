package com.qiu.keepaccount.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 14:09
 * @Description 记录分类
 */
public class AccountType extends RealmObject {

    @PrimaryKey
    private Integer id;

    private String name; // 分类名称

    private Integer Type; // 1 支出  2 收入


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }
}
