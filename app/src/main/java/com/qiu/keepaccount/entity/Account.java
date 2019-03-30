package com.qiu.keepaccount.entity;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 14:09
 * @Description
 */
public class Account extends LitePalSupport {

    private int id;

    private String remark; //备注信息

    private Date createTime; // 创建时间/修改时间

    private Double amount;//金额数

    private User user; //创建人

    private AccountType accountType;//存储 支出/收入 支出类型/收入类型 信息

    private Book book;


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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
