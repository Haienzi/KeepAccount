package com.qiu.keepaccount.mvp.account;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.adapter.AccountTypeAdapter;
import com.qiu.keepaccount.base.BaseFragment;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.AccountType;
import com.qiu.keepaccount.entity.User;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;
import com.qiu.keepaccount.mvp.books.BookActivity;
import com.qiu.keepaccount.util.AnimUtils;
import com.qiu.keepaccount.util.ArithUtils;
import com.qiu.keepaccount.util.DateUtils;
import com.qiu.keepaccount.util.RegexUtils;
import com.qiu.keepaccount.util.ToastUtils;
import com.sbgapps.simplenumberpicker.decimal.DecimalPickerDialog;
import com.sbgapps.simplenumberpicker.decimal.DecimalPickerHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class AccountFragment extends BaseFragment implements AccountInfoContract.IAccountInfoView, DecimalPickerHandler {
    @BindView(R.id.book_type)
    TextView mBookType;
    @BindView(R.id.book_name)
    TextView mBookName;
    @BindView(R.id.txt_type)
    TextView mTxtType;//支出或者收入
    @BindView(R.id.txt_money)
    TextView mTxtMoney;//金额
    @BindView(R.id.account_type)
    TextView mAccountTypeTxt;//详细分类名称
    @BindView(R.id.account_type_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_date)
    TextView mDate;
    @BindView(R.id.edt_note)
    EditText mEditNote;//备注

    private List<AccountType> mCostTypes = new ArrayList<>(); //记账类型数组
    private List<AccountType> mIncomeTypes = new ArrayList<>(); //记账类型数组
    private AccountTypeAdapter mAccountTypeAdapter;
    private Account mAccount;
    private AccountType mAccountType;

    private boolean mIsEcho = false; // 是否是回显数据
    private boolean mIsEdit = false; // 是否是编辑账目

    private AccountInfoContract.IAccountInfoPresenter mPresenter;

    public static final int REQUEST_CODE_BOOK = 1;
    public static final String EXTRA_BOOK = "com.qiu.keep.account.book.name";

    public AccountFragment() {
        // Required empty public constructor
    }


    public static AccountFragment newInstance(Bundle bundle) {
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    /**
     * 获取 Layout 布局
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account,null);
    }

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {
        initAdapter();
        initData();
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mAccount = (Account) bundle.getSerializable(AccountInfoActivity.EXTRA_ACCOUNT);
            if(mAccount != null){
                mIsEdit = true;
                echoData();
            }
        }
    }

    /**
     * 初始化 Adapter
     */
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        mAccountTypeAdapter = new AccountTypeAdapter(getContext());
        mRecyclerView.setAdapter(mAccountTypeAdapter);
        mAccountTypeAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AccountType accountType = mAccountTypeAdapter.getData(position);
                Log.d(TAG, "onItemClick:name "+accountType.getName());
                mAccountTypeTxt.setText(accountType.getName());
            }
        });
    }

    private void initCostList(){
        mCostTypes.clear();
        String[] costType = getResources().getStringArray(R.array.cost_type);
        TypedArray costIcon = getResources().obtainTypedArray(R.array.cost_icon);
        for(int i=0;i<costType.length;i++){
            AccountType type = new AccountType();
            type.setName(costType[i]);
            type.setTypeIcon(costIcon.getResourceId(i,0));
            type.setType(1);
            mCostTypes.add(type);
        }
        costIcon.recycle();

    }
    private void initIncomeList(){
        mIncomeTypes.clear();
        String[] incomeType = getResources().getStringArray(R.array.income_type);
        TypedArray incomeIcon = getResources().obtainTypedArray(R.array.income_icon);
        for(int i=0;i<incomeType.length;i++){
            AccountType type = new AccountType();
            type.setName(incomeType[i]);
            type.setTypeIcon(incomeIcon.getResourceId(i,0));
            type.setType(2);
            mIncomeTypes.add(type);
        }
        incomeIcon.recycle();

    }
    /**
     * 初始化数据
     */
    private void initData() {
        // 初始化账目对象
        mAccount = new Account();
        mAccount.setAccountType(1);//默认支出分类
        // 初始化分类数据
        initCostList();
        initIncomeList();
        // 设置默认日期
        Calendar calendar = Calendar.getInstance();
        setSelectData(calendar);
        // 默认设置支出分类
        setTypeData();
    }



    /**
     * 回显数据
     */
    private void echoData() {
        if(mAccount == null) return;

        // 回显支出、收入分类
        mIsEcho = true;
        AccountInfoActivity act = ((AccountInfoActivity)getActivity());
        act.mType = mAccount.getType();
        act.setCostType();
        AccountType accountType = mAccount.getAccountType();
        // 回显小分类
        String cType = accountType.getName();
        if(mAccount.getType() == 1){
            mTxtType.setText(getString(R.string.cost_txt));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_cost);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            mTxtType.setCompoundDrawables(drawable,null,null,null);

        }else {
            mTxtType.setText(getString(R.string.income_txt));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_income);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            mTxtType.setCompoundDrawables(drawable,null,null,null);
        }
        mAccountTypeTxt.setText(cType);
        // 回显分类图标
        int typeIcon = accountType.getTypeIcon();

        // 回显金额
        mTxtMoney.setText(mAccount.getAmount().toString());
        // 回显日期
        mDate.setText(DateUtils.dateToString(mAccount.getCreateTime()));
        // 回显备注
        mEditNote.setText(mAccount.getRemark());
        mIsEcho = false;
    }

    /**
     * 设置分类数据
     */
    private void setTypeData(){
        Log.d(TAG, "setTypeData");
        if(mAccount == null) return;

        if(mAccount.getType() == 1){
            Log.d(TAG, "getType: 1");
            mAccountTypeAdapter.setTypeList(mCostTypes);
            mAccountTypeAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mAccountTypeAdapter);
        }else{
            Log.d(TAG, "getType: 2");
            mAccountTypeAdapter.setTypeList(mIncomeTypes);
            mAccountTypeAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mAccountTypeAdapter);
        }
        // 判断不回显时才去设置默认分类选中
        if(!mIsEcho)
            setAccountTypeTxt(0);
    }

    /**
     * 设置消费或收入分类
     * @param position 选中的位置
     */
    private void setAccountTypeTxt(int position) {
        AccountType type = mAccountTypeAdapter.getData(position);

        mAccountTypeTxt.setText(type.getName());
        mAccount.setTypeId(type.getId());
    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    @Override
    public void lazyLoad() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 保存
     */
    @Override
    public void save() {
        // 获取输入金额
        String money = mTxtMoney.getText().toString();
        // 获取输入备注
        String note = mEditNote.getText().toString();
        if(!RegexUtils.checkMoney(money)){
            AnimUtils.startVibrateAnim(mTxtMoney, -1);
            ToastUtils.show(mContext,getString(R.string.toast_save_failure));
        }else{
            mAccount.setAmount(Double.valueOf(money));
            mAccount.setRemark(note);
            if(!mIsEdit){
                mPresenter.updateAccount(new User(), mAccount);
            }else{
                mPresenter.saveAccount(new User(), mAccount);
            }
            ToastUtils.show(mContext,getString(R.string.toast_save_success));
            finish();
        }
    }

    /**
     * 查找支出类型
     */
    @Override
    public void selectCost() {
        mTxtMoney.setTextColor(getContext().getResources().getColor(R.color.textPink));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_cost);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mTxtType.setCompoundDrawables(drawable,null,null,null);
        mTxtType.setText(getString(R.string.cost_txt));
        mAccount.setType(1);
        setTypeData();
    }

    /**
     * 查找收入类型
     */
    @Override
    public void selectIncome() {
        mTxtMoney.setTextColor(getContext().getResources().getColor(R.color.textLightBlue));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_income);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mTxtType.setCompoundDrawables(drawable,null,null,null);
        mTxtType.setText(getString(R.string.income_txt));
        mAccount.setType(2);
        setTypeData();
    }

    /**
     * 显示选择日期
     */
    @Override
    public void showSelectDateDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                setSelectData(calendar);
            }
            // 设置初始日期
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 显示编辑金额对话框
     */
    @Override
    public void showInputMoneyDialog() {
        new DecimalPickerDialog.Builder()
                .setReference(1)
                .setNatural(false)
                .setRelative(false)
                .setTheme(R.style.DecimalPickerTheme)
                .create()
                .show(getChildFragmentManager(), "TAG_DEC_DIALOG");
    }


    @Override
    public void onDecimalNumberPicked(int i, float v) {
        Double money = Double.valueOf(Float.toString(v));
        String moneyStr = ArithUtils.formatMoney(money);
        if(RegexUtils.checkMoney(moneyStr)){
            mTxtMoney.setText(moneyStr);
        }else{
            AnimUtils.startVibrateAnim(mTxtMoney, -1);
        }
    }

    /**
     * 设置选择的日期
     */
    private void setSelectData(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(DateUtils.FORMAT, Locale.CHINA);
        String time = format.format(date);
        mDate.setText(time);
        mAccount.setCreateTime(date);
    }

    @OnClick({R.id.txt_money, R.id.txt_date,R.id.book_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_money:
                showInputMoneyDialog();
                break;
            case R.id.txt_date:
                showSelectDateDialog();
                break;
            case R.id.book_name:
                Intent intent = BookActivity.newIntent(getActivity());
                startActivityForResult(intent,REQUEST_CODE_BOOK);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_BOOK:
                    // 获取记账本数据并设置
                    mBookName.setText(data.getStringExtra(EXTRA_BOOK));
                    break;
            }
        }
    }

    /**
     * 在view层获取相应的Presenter实例进行交互
     *
     * @param presenter
     */
    @Override
    public void setPresenter(AccountInfoContract.IAccountInfoPresenter presenter) {
        mPresenter = presenter;
    }
}
