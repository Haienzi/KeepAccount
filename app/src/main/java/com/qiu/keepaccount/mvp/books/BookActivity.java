package com.qiu.keepaccount.mvp.books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.base.BaseActivity;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/24 16:07
 * @Description 账本页面
 */
public class BookActivity extends BaseActivity {


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


    /**
     * 获取布局
     */
    @Override
    public int getLayoutRes() {
        return R.layout.activity_book;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {

    }
}
