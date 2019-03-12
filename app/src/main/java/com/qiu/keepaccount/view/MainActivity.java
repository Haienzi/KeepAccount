package com.qiu.keepaccount.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.qiu.keepaccount.R;
import com.qiu.keepaccount.adapter.BottomViewAdapter;
import com.qiu.keepaccount.fragment.EditAccountFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;

    private MenuItem menuItem;
    private List<Fragment> mFragmentList = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_edit_account:
                    item.setIcon(R.drawable.ic_edit_account_red);
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_chart:
                    item.setIcon(R.drawable.ic_chart_red);
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_me:
                    item.setIcon(R.drawable.ic_me_red);
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        
        setFragmentView();
    }

    private void setFragmentView(){
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(menuItem != null){
                    menuItem.setChecked(false);
                }else{
                    mNavigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = mNavigation.getMenu().getItem(i);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //mFragmentList.add();
        BottomViewAdapter bottomViewAdapter = new BottomViewAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(bottomViewAdapter);
        //设置缓存view的个数
        mViewPager.setOffscreenPageLimit(3);
    }
}
