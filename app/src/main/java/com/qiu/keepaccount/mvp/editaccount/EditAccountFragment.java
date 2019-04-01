package com.qiu.keepaccount.mvp.editaccount;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.mvp.books.BookActivity;
import com.qiu.keepaccount.adapter.AccountRecyclerAdapter;
import com.qiu.keepaccount.base.BaseFragment;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Budget;
import com.qiu.keepaccount.fragment.BudgetPickerFragment;
import com.qiu.keepaccount.fragment.DatePickerFragment;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;
import com.qiu.keepaccount.mvp.account.AccountInfoActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class EditAccountFragment extends BaseFragment implements EditAccountContract.View{
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
    public static String DATE_FORMAT = "yyyy-MM-dd";
    private EditAccountContract.Presenter mPresenter;
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
        Bundle args = new Bundle();

        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            //需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.fragment_edit_account, container, false);
            ButterKnife.bind(this,mView);
            mAccountList = new ArrayList<>();
            for(int i=0;i<10;i++){
                mAccountList.add(new Account());
            }
            updateDate(new Date());//初始化日期
            showDateDialog();//日期对话框
            showEditBudgetDialog();//预算对话框
            jumpToBookActivity();//跳转到账本界面
            addAccount();//添加记账记录
            showAccountOrEmpty();
            setRecyclerData();
            isPrepared = true;
            //实现懒加载
            lazyLoad();
        }
        //缓存的mView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        return mView;

    }

    /**
     * 添加记录
     */
    public void addAccount(){
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToAccountInfo(null);
            }
        });
    }

    /**
     * 没有记账数据的时候显示空内容的提示
     */
    public void showAccountOrEmpty(){
        if(mAccountList.size() == 0)
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
    }
    /**
     * 设置recyclerView的数据
     */
    public void setRecyclerData(){
        mAccountRecyclerAdapter = new AccountRecyclerAdapter(getActivity(),mAccountList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.
                VERTICAL,false));
        mRecyclerView.setAdapter(mAccountRecyclerAdapter);
        mAccountRecyclerAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                jumpToAccountInfo(new Account());
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
    public void querySuccess(List<Account> list) {

    }

    @Override
    public void queryFail(Error e) {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void deleteFail(Error e) {

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
     */
    @Override
    public void jumpToAccountInfo(Account account) {
        Intent intent = AccountInfoActivity.newIntent(getActivity());
        startActivity(intent);
    }

    @Override
    public void setPresenter(EditAccountContract.Presenter presenter) {

    }

    //设置日期
    private void updateDate(Date date) {
        java.text.DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        mDateText.setText(dateFormat.format(date));
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
        }
        if(requestCode == REQUEST_BUDGET)
        {
            int budget = (int)data.getSerializableExtra(BudgetPickerFragment.EXTRA_BUDGET);
            mSurplusText.setText(String.format(getString(R.string.budget_info),budget,budget));
            Budget budgetObject = new Budget();
            budgetObject.setBudget((double)budget);
            java.text.DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            budgetObject.setCreateTime(dateFormat.format(mDateText.getText().toString()));
            mPresenter.saveBudget(budgetObject);
        }
    }
    /**
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
