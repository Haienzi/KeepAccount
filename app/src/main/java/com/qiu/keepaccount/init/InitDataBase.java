package com.qiu.keepaccount.init;

import android.content.Context;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.AccountType;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 16:44
 * @Description 第一次安装时初始化数据
 */
public class InitDataBase {

    public static void initAccountType(Context context){
        String[] costType = context.getResources().getStringArray(R.array.cost_type);
        for(int i=1;i<11;i++){
            AccountType accountType = new AccountType();
            accountType.setId(i);
            accountType.setName(costType[i]);
            accountType.setType(1);
            accountType.saveOrUpdate("name=?",costType[i]);
        }

        String[] incomeType = context.getResources().getStringArray(R.array.income_type);
        for(int i = 11;i<20;i++){
            AccountType accountType = new AccountType();
            accountType.setId(i);
            accountType.setName(incomeType[i]);
            accountType.setType(1);
            accountType.saveOrUpdate("name=?",incomeType[i]);
        }
    }


}
