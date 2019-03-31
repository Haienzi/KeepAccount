package com.qiu.keepaccount.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qiu.keepaccount.R;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/24 16:07
 * @Description 账本页面
 */
public class BookActivity extends AppCompatActivity {


    /**
     * 启动BookActivity时
     * @param packageContext
     * @return
     */
    public static Intent newIntent(Context packageContext)
    {
        Intent intent = new Intent(packageContext,BookActivity.class);
        //intent.putExtra(EXTRA_CRIME_ID,id);//传递附加信息
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
    }
}
