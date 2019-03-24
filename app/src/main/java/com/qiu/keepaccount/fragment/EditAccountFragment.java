package com.qiu.keepaccount.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.adapter.AccountRecyclerAdapter;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditAccountFragment extends BaseFragment {
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
            mAccountList = new ArrayList<>(2);
            mAccountList.add(new Account());
            mAccountList.add(new Account());
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
