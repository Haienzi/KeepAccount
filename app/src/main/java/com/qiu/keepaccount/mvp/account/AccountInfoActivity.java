package com.qiu.keepaccount.mvp.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.base.BaseActivity;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.model.account.AccountModel;
import com.qiu.keepaccount.util.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountInfoActivity extends BaseActivity {

    @BindView(R.id.txt_title_cost)
    TextView mTxtTitleCost;
    @BindView(R.id.txt_title_income)
    TextView mTxtTitleIncome;

    public static final String EXTRA_ACCOUNT = "com.qiu.keepaccount.accountinfo.account";
    public static final String EXTRA_TYPE = "com.qiu.keepaccount.accountinfo.account.type";
    private AccountFragment mAccountFragment;

    /**
     * 支出 1 收入 2
     */
    public int mType = 1;
    /**
     * 是否是编辑该帐薄
     */
    private boolean mIsEdit = false;

    /**
     * 启动AccountInfoActivity时
     * @param packageContext
     * @param type
     * @param account
     * @return
     */
    public static Intent newIntent(Context packageContext,int type,Account account)
    {
        Intent intent = new Intent(packageContext, AccountInfoActivity.class);
        intent.putExtra(EXTRA_ACCOUNT,account);
        intent.putExtra(EXTRA_TYPE,type);
        return intent;
    }

    /**
     * 获取布局
     */
    @Override
    public int getLayoutRes() {
        return R.layout.activity_account_info;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        Account account = (Account) getIntent().getSerializableExtra(EXTRA_ACCOUNT);
        int type = getIntent().getIntExtra(EXTRA_TYPE,1);
        Bundle bundle = null;
        if(account != null){
            mIsEdit = true;
            bundle = new Bundle();
            bundle.putSerializable(EXTRA_ACCOUNT,account);
            bundle.putInt(EXTRA_TYPE,type);
        }

        Toolbar toolbar = initToolbar(null);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        //设置fragment
        mAccountFragment = (AccountFragment)getSupportFragmentManager().findFragmentById(
                R.id.account_frame);
        if(mAccountFragment == null){
            mAccountFragment = AccountFragment.newInstance(bundle);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mAccountFragment,
                    R.id.account_frame);
        }

        new AccountInfoPresenterImpl(mAccountFragment,new AccountModel());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_title_finish:
                mAccountFragment.save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.txt_title_cost, R.id.txt_title_income})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_title_cost:
                if(mType != 1){
                    mType = 1;
                    mTxtTitleCost.setBackgroundResource(R.drawable.bg_tb_select);
                    mTxtTitleIncome.setBackgroundResource(0);
                    mAccountFragment.selectCost();
                }
                break;
            case R.id.txt_title_income:
                if(mType != 2){
                    mType = 2;
                    mTxtTitleIncome.setBackgroundResource(R.drawable.bg_tb_select);
                    mTxtTitleCost.setBackgroundResource(0);
                    mAccountFragment.selectIncome();
                }
                break;
        }
    }

    public void setCostType(){
        // 判断收入还是支出
        switch (mType){
            case 1:
                mTxtTitleCost.setBackgroundResource(R.drawable.bg_tb_select);
                mTxtTitleIncome.setBackgroundResource(0);
                mAccountFragment.selectCost();
                break;
            case 2:
                mTxtTitleIncome.setBackgroundResource(R.drawable.bg_tb_select);
                mTxtTitleCost.setBackgroundResource(0);
                mAccountFragment.selectIncome();
                break;
        }
    }


}
