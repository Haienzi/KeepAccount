package com.qiu.keepaccount.model.budget;

import com.qiu.keepaccount.entity.Budget;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/2 14:22
 * @Description 预算接口
 */
public interface IBudgetModel {

    /**
     * 添加预算
     * @param budget 预算
     */
    void addBudget(Budget budget);

    /**
     * 修改预算
     * @param budget 预算
     */
    void updateBudget(Budget budget);

    /**
     * 查询指定日期的预算信息
     * @param createDate 指定的日期
     * @return 预算信息
     */
    List<Budget> queryBudget(String createDate);

}
