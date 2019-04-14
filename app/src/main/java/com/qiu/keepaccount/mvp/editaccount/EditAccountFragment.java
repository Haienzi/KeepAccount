package com.qiu.keepaccount.mvp.editaccount;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.adapter.AccountRecyclerAdapter;
import com.qiu.keepaccount.base.BaseFragment;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Budget;
import com.qiu.keepaccount.entity.User;
import com.qiu.keepaccount.ui.fragment.BudgetPickerFragment;
import com.qiu.keepaccount.ui.fragment.DatePickerFragment;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;
import com.qiu.keepaccount.mvp.account.AccountInfoActivity;
import com.qiu.keepaccount.mvp.books.BookActivity;
import com.qiu.keepaccount.util.DateUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 */
public class EditAccountFragment extends BaseFragment implements EditAccountContract.IEditAccountView {
    @BindView(R.id.ea_recycler_view)
    RecyclerView mRecyclerView;//账单列表
    @BindView(R.id.choose_book)
    ImageView mChooseBookImg;//选择账本
    @BindView(R.id.ea_date_txt)
    TextView mDateText;//显示的日期
    @BindView(R.id.choose_date)
    ImageView mDateImg;//选择日期
    @BindView(R.id.ea_edit_surplus)
    LinearLayout mEditSurplusLayout;//添加预算
    @BindView(R.id.ea_surplus_text)
    TextView mSurplusText; // 预算详情
    @BindView(R.id.ea_today_pay)
    TextView mTodayPay;//今日支出金额
    @BindView(R.id.ea_today_income)
    TextView mTodayIncome;//今日收入
    @BindView(R.id.ea_monthly_pay)
    TextView mMonthlyPay;//本月支出
    @BindView(R.id.ea_monthly_income)
    TextView mMonthlyIncome;//本月收入
    @BindView(R.id.ea_empty_tip)
    LinearLayout mTipLayout;//空内容提示布局
    @BindView(R.id.ea_tip_image)
    ImageView mTipImg; //提示图片
    @BindView(R.id.ea_add_tip)
    TextView mTipText;//提示文本
    @BindView(R.id.fab_add)
    FloatingActionButton mFloatingActionButton;

    private static final String DIALOG_DATE = "DialogDate";//DatePickerFragment的tag
    private static final String DIALOG_BUDGET = "DialogBudget";//BudgetPickerFragment的tag
    private static final int REQUEST_DATE = 0;//DatePickerFragment的请求代码
    private static final int REQUEST_BUDGET = 1;//BudgetPickerFragment的请求代码
    private EditAccountContract.IEditAccountPresenter mPresenter;
    private AccountRecyclerAdapter mAccountRecyclerAdapter;
    private List<Account> mAccountList;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;



    private OnFragmentInteractionListener mListener;

    @SuppressLint("ValidFragment")
    private EditAccountFragment() {
        // Required empty public constructor
    }


    public static EditAccountFragment newInstance() {
        EditAccountFragment fragment = new EditAccountFragment();
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
        return inflater.inflate(R.layout.fragment_edit_account,null);
    }

    /**
     * 添加笔记
     */
    @Override
    public void jumpToAddAccount() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToAccountInfo(-1,null);
            }
        });
    }


    /**
     * 设置recyclerView的数据
     */
    public void setRecyclerData(){
        mAccountRecyclerAdapter = new AccountRecyclerAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.
                VERTICAL,false));
        mRecyclerView.setAdapter(mAccountRecyclerAdapter);
        mAccountRecyclerAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View view,int i) {
                jumpToAccountInfo(mAccountList.get(i).getType(),mAccountList.get(i));
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;

    }

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateFragment: is called");
        updateDate(new Date());//初始化日期
        new EditAccountPresenterImpl(this);
        initData();
        setRecyclerData();
    }

    public void initData(){
        showDateDialog();
        showEditBudgetDialog();
        jumpToBookActivity();
        jumpToAddAccount();
    }
    /**
     * 检索账目信息
     * @param list
     */
    @Override
    public void queryAccount(List<Account> list) {
        mAccountList = list;
        if(list.size() == 0)
        {
            mRecyclerView.setVisibility(View.GONE);
            mTipLayout.setVisibility(View.VISIBLE);
            mTipImg.setVisibility(View.VISIBLE);
            mTipText.setVisibility(View.VISIBLE);
        }else {
            mTipLayout.setVisibility(View.GONE);
            mTipImg.setVisibility(View.GONE);
            mTipText.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mAccountRecyclerAdapter.setData(list);
        mAccountRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAccountRecyclerAdapter);
    }

    /**
     * 删除账目信息后更新界面数据
     */
    @Override
    public void deleteAccount() {
        User user = new User();
        user.setId(-1);
        String queryDate = mDateText.getText().toString();
        mPresenter.queryAccount(user,queryDate);
        mPresenter.queryBudget(user.getId(),queryDate);
    }

    /**
     * 编辑预算信息
     *
     * @param budget
     */
    @Override
    public void setBudget(Budget budget) {
        mSurplusText.setText(budget.getSurplus().toString());
    }

    /**
     * 选择日期
     */
    @Override
    public void showDateDialog() {
        mDateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                //为DatePickerFragment设置目标Fragment
                dialog.setTargetFragment(EditAccountFragment.this,0);
                dialog.show(manager,DIALOG_DATE);//将DialogFragment添加给FragmentManager并显示到屏幕上
            }
        });
    }

    /**
     * 添加预算
     */
    @Override
    public void showEditBudgetDialog() {
        mEditSurplusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                BudgetPickerFragment dialog = BudgetPickerFragment.newInstance(1500);
                //为DatePickerFragment设置目标Fragment
                dialog.setTargetFragment(EditAccountFragment.this,1);
                dialog.show(manager,DIALOG_BUDGET);//将DialogFragment添加给FragmentManager并显示到屏幕上
            }
        });

    }

    /**
     * 跳转到账本界面
     */
    @Override
    public void jumpToBookActivity() {
        mChooseBookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = BookActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
    }

    /**
     * 跳转到记账详情界面
     *
     * @param account 记录
     * @param type 账目类型
     */
    public void jumpToAccountInfo(int type,Account account) {
        Intent intent = AccountInfoActivity.newIntent(getActivity(),type,account);
        startActivity(intent);
    }


    //设置日期
    private void updateDate(Date date) {
        mDateText.setText(DateUtils.dateYMDToString(date));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
        {
            return;
        }
        if(requestCode == REQUEST_DATE)
        {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            //获取选择的日期
            //为日期按钮设置日期
            updateDate(date);
            User user = new User();
            user.setId(-1);
            mPresenter.queryAccount(user, DateUtils.dateToString(date));
        }
        if(requestCode == REQUEST_BUDGET)
        {
            int budget = (int)data.getSerializableExtra(BudgetPickerFragment.EXTRA_BUDGET);
            mSurplusText.setText(String.format(getString(R.string.budget_info),(double)budget,(double)budget));
            Budget budgetObject = new Budget();
            budgetObject.setBudget((double)budget);
            budgetObject.setCreateTime(mDateText.getText().toString());
            mPresenter.saveBudget(-1,budgetObject);
        }
    }

    @Override
    public void setPresenter(EditAccountContract.IEditAccountPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
