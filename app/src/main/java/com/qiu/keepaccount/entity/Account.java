package com.qiu.keepaccount.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 14:09
 * @Description
 */
public class Account extends LitePalSupport implements Serializable {

    private int id;

    private String remark; //备注信息

    private Date createTime; // 创建时间/修改时间

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    private Date createDate; // 创建时间年月日格式

    private double amount;//金额数

    private int userId; //创建人

    private int bookId;//所属账本id

    public void setType(int type) {
        this.type = type;
    }

    private int type;//1 支出 2 收入

    private int typeId;//账目类型所属Id


    public AccountType getAccountType() {
        return mAccountType;
    }

    public void setAccountType(AccountType accountType) {
        mAccountType = accountType;
    }

    private AccountType mAccountType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getType() {
        return type;
    }


    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
