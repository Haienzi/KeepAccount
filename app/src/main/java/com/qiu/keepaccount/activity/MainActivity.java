package com.qiu.keepaccount.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.qiu.keepaccount.fragment.ChartFragment;
import com.qiu.keepaccount.fragment.EditAccountFragment;
import com.qiu.keepaccount.fragment.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initSystemBar(false);
        ButterKnife.bind(this);
        setDefaultFragment();
        setListener();
    }

    void initSystemBar(Boolean isLight) {
        if (Build.VERSION.SDK_INT >= 21) {

            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            if (isLight) {
                window.setStatusBarColor(getResources().getColor(R.color.white));
            } else {
                window.setStatusBarColor(getResources().getColor(R.color.app_status));
            }

            //状态栏颜色接近于白色，文字图标变成黑色
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (isLight) {
                //light --> a|=b的意思就是把a和b按位或然后赋值给a,   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                //dark  --> &是位运算里面，与运算,  a&=b相当于 a = a&b,  ~非运算符
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
        }

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
