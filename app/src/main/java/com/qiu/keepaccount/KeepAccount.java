package com.qiu.keepaccount;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;

import com.qiu.keepaccount.entity.AccountType;

import org.litepal.LitePal;
import org.litepal.tablemanager.callback.DatabaseListener;

/**
 * @author mqh 2019/3/27
 */
public class KeepAccount extends Application {
    private static KeepAccount mInstance;
    private static Context mContext;
    public static final String BACKUP_PATH = "backup_path";
    public static final String RESTORE_PATH = "restore_path";
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        mContext = getApplicationContext();
        mInstance = this;
        //监听数据库的创建和升级
        LitePal.registerDatabaseListener(new DatabaseListener() {
            @Override
            public void onCreate() {
                //在数据库初始化的同时创建一些数据到表中
                initAccountType();
            }

            @Override
            public void onUpgrade(int oldVersion, int newVersion) {
                //在数据升级的时候，对之前的数据执行一些操作

            }
        });

    }
    public static void initAccountType(){
        String[] costType = mContext.getResources().getStringArray(R.array.cost_type);
        TypedArray costIcon = mContext.getResources().obtainTypedArray(R.array.cost_icon);
        for(int i=1;i<11;i++){
            AccountType accountType = new AccountType();
            accountType.setId(i);
            accountType.setName(costType[i]);
            accountType.setType(1);
            accountType.setTypeIcon(costIcon.getResourceId(i,R.mipmap.ic_def_icon));
            accountType.saveOrUpdate("name=?",costType[i]);
        }
        costIcon.recycle();

        String[] incomeType = mContext.getResources().getStringArray(R.array.income_type);
        TypedArray incomeIcon = mContext.getResources().obtainTypedArray(R.array.income_icon);
        for(int i = 11;i<20;i++) {
            AccountType accountType = new AccountType();
            accountType.setId(i);
            accountType.setName(incomeType[i]);
            accountType.setType(1);
            accountType.setTypeIcon(incomeIcon.getResourceId(i, R.mipmap.ic_def_icon));
            accountType.saveOrUpdate("name=?", incomeType[i]);
        }
        incomeIcon.recycle();
    }

    /**
     * 获取上下文
     * @return
     */
    public static Context getContext(){
        return mContext;
    }

}
