package com.qiu.keepaccount;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

/**
 * @author mqh 2019/3/27
 */
public class KeepAccount extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);

        SQLiteDatabase database = Connector.getDatabase();

    }


}
