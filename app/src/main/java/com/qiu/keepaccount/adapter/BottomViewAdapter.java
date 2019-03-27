package com.qiu.keepaccount.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/11 14:07
 * @Description viewpager fragment适配器
 */
public class BottomViewAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public BottomViewAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
