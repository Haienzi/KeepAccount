package com.qiu.keepaccount.model.budget;

import com.qiu.keepaccount.entity.Budget;
import org.litepal.LitePal;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/2 14:22
 * @Description 预算数据操作实现
 */
public class BudgetModel implements IBudgetModel {


    /**
     * 添加预算
     *
     * @param userId 用户id
     * @param budget 预算
     */
    @Override
    public void addBudget(int userId, Budget budget) {
        if(userId != -1){
            budget.saveAsync();
        }else{
            budget.setUserId(userId);
            budget.saveAsync();
        }
    }

    /**
     * 修改预算
     *
     * @param userId 用户id
     * @param budget 预算
     */
    @Override
    public void updateBudget(int userId, Budget budget) {
        if(userId != -1){
            budget.updateAsync(budget.getId());
        }else{
            budget.setUserId(userId);
            budget.updateAsync(budget.getId());
        }
    }

    /**
     * 查询指定日期的预算信息
     *
     * @param userId     用户id
     * @param createDate 指定的日期
     * @return 预算信息
     */
    @Override
    public List<Budget> queryBudget(int userId, String createDate) {
        List<Budget> budgets = null;
        budgets = LitePal.where("userId = ? and createDate = ? ",String.valueOf(userId),createDate)
                         .find(Budget.class);
        return budgets;
    }
}
