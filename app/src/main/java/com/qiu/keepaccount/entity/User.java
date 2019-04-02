package com.qiu.keepaccount.entity;

import org.litepal.crud.LitePalSupport;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 13:58
 * @Description
 */
public class User extends LitePalSupport {

    private int id;
    private String nickName; //用户昵称
    private String phoneNumber;//用户手机号码
    private String email;//邮箱
    private byte[] userImg; //用户头像

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

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getUserImg() {
        return userImg;
    }

    public void setUserImg(byte[] userImg) {
        this.userImg = userImg;
    }
}
