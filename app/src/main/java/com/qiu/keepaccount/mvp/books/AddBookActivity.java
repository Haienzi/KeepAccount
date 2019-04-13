package com.qiu.keepaccount.mvp.books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.base.BaseActivity;

public class AddBookActivity extends BaseActivity {

    /**
     * 启动AddBookActivity时
     * @param packageContext
     * @return
     */
    public static Intent newIntent(Context packageContext)
    {
        Intent intent = new Intent(packageContext,AddBookActivity.class);
        //intent.putExtra(EXTRA_CRIME_ID,id);//传递附加信息
        return intent;
    }

    /**
     * 获取布局
     */
    @Override
    public int getLayoutRes() {
        return R.layout.activity_add_book;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {

    }
}
