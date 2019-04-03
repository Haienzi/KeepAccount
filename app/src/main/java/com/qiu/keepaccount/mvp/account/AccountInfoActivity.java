package com.qiu.keepaccount.mvp.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import com.qiu.keepaccount.R;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.util.ActivityUtils;

public class AccountInfoActivity extends AppCompatActivity {

    private static final String EXTRA_ACCOUNT = "com.qiu.keepaccount.accountinfo.account";
    private AccountFragment mAccountFragment;

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
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

    }

    public void initFragment(Account account,int typeId){
        if(mAccountFragment == null){
            mAccountFragment = AccountFragment.newInstance(account,typeId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mAccountFragment, R.layout.fragment_account);
        }
    }
}
