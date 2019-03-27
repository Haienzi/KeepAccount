package com.qiu.keepaccount.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.util.Date;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 14:10
 * @Description
 */
public class Budget extends RealmObject {

    @PrimaryKey
    private Integer id;

    private Double budget; //预算

    private Double surplus; //剩余

    private Date createTime;//创建日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getSurplus() {
        return surplus;
    }

    public void setSurplus(Double surplus) {
        this.surplus = surplus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
