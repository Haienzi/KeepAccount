package com.qiu.keepaccount.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 13:58
 * @Description
 */
public class User extends RealmObject {
    @PrimaryKey
    private Integer id;
    private String nickName;
    private String phoneNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
