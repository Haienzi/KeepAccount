package com.qiu.keepaccount;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;

import com.qiu.keepaccount.entity.AccountType;
import com.qiu.keepaccount.entity.Book;

import org.litepal.LitePal;
import org.litepal.tablemanager.callback.DatabaseListener;

import java.util.Date;

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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        initAccountType();
                    }
                }).start();
            }

            @Override
            public void onUpgrade(int oldVersion, int newVersion) {
                //在数据升级的时候，对之前的数据执行一些操作

            }
        });
        SQLiteDatabase db = LitePal.getDatabase();

    }
    public static void initAccountType(){
        LitePal.deleteAll(AccountType.class);
        LitePal.deleteAll(Book.class);
        String[] costType = mContext.getResources().getStringArray(R.array.cost_type);
        TypedArray costIcon = mContext.getResources().obtainTypedArray(R.array.cost_icon);
        for(int i=1;i<=costType.length;i++){
            AccountType accountType = new AccountType();
            accountType.setId(i);
            accountType.setName(costType[i-1]);
            accountType.setType(1);
            accountType.setTypeIcon(costIcon.getResourceId(i-1,R.mipmap.ic_def_icon));
            accountType.saveOrUpdateAsync("name=?",costType[i-1]);
        }
        costIcon.recycle();

        String[] incomeType = mContext.getResources().getStringArray(R.array.income_type);
        TypedArray incomeIcon = mContext.getResources().obtainTypedArray(R.array.income_icon);
        for(int i = 1;i<incomeType.length;i++) {
            AccountType accountType = new AccountType();
            accountType.setId(i+11);
            accountType.setName(incomeType[i-1]);
            accountType.setType(1);
            accountType.setTypeIcon(incomeIcon.getResourceId(i-1, R.mipmap.ic_def_icon));
            accountType.saveOrUpdateAsync("name=?", incomeType[i-1]);
        }
        incomeIcon.recycle();

        Book book = new Book();
        book.setSceneName("日常账本");
        book.setTotalCost(0);
        book.setTotalIncome(0);
        book.setBookName("日常开支");
        book.setCreateTime(new Date());
        book.saveAsync();
    }

    /**
     * 获取上下文
     * @return
     */
    public static Context getContext(){
        return mContext;
    }

}
