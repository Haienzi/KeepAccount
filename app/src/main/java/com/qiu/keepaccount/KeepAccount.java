package com.qiu.keepaccount;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;
import org.litepal.tablemanager.callback.DatabaseListener;

/**
 * @author mqh 2019/3/27
 */
public class KeepAccount extends Application {
    private static KeepAccount mInstance;
    private static Context mContext;

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

            }

            @Override
            public void onUpgrade(int oldVersion, int newVersion) {
                //在数据升级的时候，对之前的数据执行一些操作

            }
        });

    }

    /**
     * 获取上下文
     * @return
     */
    public static Context getContext(){
        return mContext;
    }

}
