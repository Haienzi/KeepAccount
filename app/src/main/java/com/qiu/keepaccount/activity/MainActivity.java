package com.qiu.keepaccount.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.base.BaseActivity;
import com.qiu.keepaccount.fragment.ChartFragment;
import com.qiu.keepaccount.fragment.SettingFragment;
import com.qiu.keepaccount.mvp.editaccount.EditAccountFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    @BindView(R.id.main_frame)
    FrameLayout mFrameLayout;

    private EditAccountFragment mEditAccountFragment;
    private ChartFragment mChartFragment;
    private SettingFragment mSettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDefaultFragment();
        setListener();
    }


    /**
     * 获取布局
     */
    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {

    }

    //设置初始界面
    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        mEditAccountFragment = EditAccountFragment.newInstance();
        transaction.replace(R.id.main_frame, mEditAccountFragment);
        transaction.commit();
    }

    private void setListener(){
        //取消点击后 图标原始颜色不显示的问题
        //mNavigation.setItemIconTintList(null);

        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.navigation_edit_account:
                        if (mEditAccountFragment == null) {
                            mEditAccountFragment = EditAccountFragment.newInstance();
                        }
                        transaction.replace(R.id.main_frame, mEditAccountFragment);
                        break;
                    case R.id.navigation_chart:
                        if (mChartFragment == null) {
                            mChartFragment = ChartFragment.newInstance();
                        }
                        transaction.replace(R.id.main_frame, mChartFragment);
                        break;
                    case R.id.navigation_me:
                        if (mSettingFragment == null) {
                            mSettingFragment = SettingFragment.newInstance();
                        }
                        transaction.replace(R.id.main_frame, mSettingFragment);
                        break;
                }
                transaction.commit();
                return true;
            }
        });


    }

}
