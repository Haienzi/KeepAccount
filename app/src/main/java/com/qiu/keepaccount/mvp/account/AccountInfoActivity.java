package com.qiu.keepaccount.mvp.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import com.qiu.keepaccount.R;
import com.qiu.keepaccount.base.BaseActivity;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.util.ActivityUtils;

public class AccountInfoActivity extends BaseActivity {

    @BindView(R.id.txt_title_cost)
    TextView mTxtTitleCost;
    @BindView(R.id.txt_title_income)
    TextView mTxtTitleIncome;

    private static final String EXTRA_ACCOUNT = "com.qiu.keepaccount.accountinfo.account";
    private static final String EXTRA_IS_EDIT = "com.qiu.keepaccount.accountinfo.account";
    private static final String EXTRA_TYPE = "com.qiu.keepaccount.accountinfo.account";
    private AccountFragment mAccountFragment;

    /**
     * 支出 1 收入 2
     */
    private int mType = 1;
    /**
     * 是否是编辑该帐薄
     */
    private boolean mIsEdit = false;

    /**
     * 启动AccountInfoActivity时
     * @param packageContext
     * @param isEdit
     * @param type
     * @param account
     * @return
     */
    public static Intent newIntent(Context packageContext,boolean isEdit,int type,Account account)
    {
        Intent intent = new Intent(packageContext, AccountInfoActivity.class);
        intent.putExtra(EXTRA_IS_EDIT,isEdit);
        intent.putExtra(EXTRA_ACCOUNT,account);
        intent.putExtra(EXTRA_TYPE,type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void initFragment(Account account,int typeId){
        if(mAccountFragment == null){
            mAccountFragment = AccountFragment.newInstance(account,typeId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mAccountFragment, R.layout.fragment_account);
        }
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

    }
}
