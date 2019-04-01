package com.qiu.keepaccount.mvp.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qiu.keepaccount.R;

public class AccountInfoActivity extends AppCompatActivity {

    private static final String EXTRA_ACCOUNT = "com.qiu.keepaccount.accountinfo.account";

    /**
     * 启动AccountInfoActivity时
     * @param packageContext
     * @return
     */
    public static Intent newIntent(Context packageContext)
    {
        Intent intent = new Intent(packageContext, AccountInfoActivity.class);
        //intent.putExtra(EXTRA_CRIME_ID,id);//传递附加信息
        return intent;
    }

    /**
     * 启动AccountInfoActivity时
     * @param packageContext context
     * @id account id
     * @return intent
     */
    public static Intent newIntent(Context packageContext, int id)
    {
        Intent intent = new Intent(packageContext, AccountInfoActivity.class);
        intent.putExtra(EXTRA_ACCOUNT,id);//传递附加信息
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
    }
}
