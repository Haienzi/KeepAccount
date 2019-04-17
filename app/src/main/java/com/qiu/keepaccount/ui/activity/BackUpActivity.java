package com.qiu.keepaccount.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qiu.keepaccount.R;

public class BackUpActivity extends AppCompatActivity {

    /**
     * 启动AccountInfoActivity时
     * @param packageContext
     * @return
     */
    public static Intent newIntent(Context packageContext)
    {
        Intent intent = new Intent(packageContext, BackUpActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_up);
    }
}
