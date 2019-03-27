package com.qiu.keepaccount;

import android.annotation.SuppressLint;
import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author mqh 2019/3/27
 */
public class KeepAccount extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("KeepAccount.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()  //声名版本冲突时自动删除原数据库
                .build();

    }


}
