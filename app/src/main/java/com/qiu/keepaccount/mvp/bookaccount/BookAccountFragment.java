package com.qiu.keepaccount.mvp.bookaccount;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.adapter.AccountRecyclerAdapter;
import com.qiu.keepaccount.base.BaseFragment;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.AccountType;
import com.qiu.keepaccount.entity.Book;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;
import com.qiu.keepaccount.mvp.account.AccountInfoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Use the {@link BookAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookAccountFragment extends BaseFragment implements BookAccountContract.BookAccountView {

    @BindView(R.id.account_amount_info)
    TextView mAmountInfo;
    @BindView(R.id.book_account_recycler_view)
    RecyclerView mRecyclerView;

    private List<Account> mAccountList = new ArrayList<>();
    private AccountRecyclerAdapter mAccountRecyclerAdapter;
    private BookAccountContract.IBookAccountPresenter mPresenter;
    private Book mBook;

    public BookAccountFragment(){

    }
    public static BookAccountFragment newInstance(Bundle args) {
        BookAccountFragment fragment = new BookAccountFragment();
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_book_account,null);
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    @Override
    public void lazyLoad() {

    }

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {
        initAdapter();
        if(getArguments()!=null){
            Bundle bundle = getArguments();
            mBook = (Book)bundle.getSerializable(BookAccountActivity.EXTRA_BOOK);
        }


    }

    /**
     * 初始化Adapter
     */
    public void initAdapter(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.
                VERTICAL,false));
        mAccountRecyclerAdapter = new AccountRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(mAccountRecyclerAdapter);
        mAccountRecyclerAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Account account = mAccountRecyclerAdapter.getData(position);
                jumpToEditAccount(account);
            }
        });
    }

    /**
     * 显示支出记录
     */
    @Override
    public void selectCost() {
        mPresenter.selectAccountByBook(mBook,1);
        mPresenter.selectTotalCost(mBook);
    }

    /**
     * 显示收入记录
     */
    @Override
    public void selectIncome() {
        mPresenter.selectAccountByBook(mBook,2);
        mPresenter.selectTotalIncome(mBook);
    }

    /**
     * 显示所有记录
     */
    @Override
    public void selectAll() {
        mPresenter.selectAccountByBook(mBook,-1);
        mPresenter.selectTotalMoney(mBook);
    }

    /**
     * 设置支出记录
     *
     * @param accountList
     */
    @Override
    public void setListData(List<Account> accountList) {
        mAccountList.clear();
        mAccountList=accountList;
        mAccountRecyclerAdapter.setData(mAccountList);
        mAccountRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAccountRecyclerAdapter);
    }

    /**
     * 设置总收入
     *
     * @param income
     */
    @Override
    public void setTotalIncome(double income) {
        mAmountInfo.setText(String.format(getString(R.string.account_income_info_string),income));
    }

    /**
     * 设置总支出
     *
     * @param cost
     */
    @Override
    public void setTotalCost(double cost) {
        mAmountInfo.setText(String.format(getString(R.string.account_cost_info_string),cost));
    }

    /**
     * 设置总金额
     *
     * @param totalMoney
     * @param income
     * @param cost
     */
    @Override
    public void setTotalMoney(double totalMoney, double income, double cost) {
        mAmountInfo.setText(String.format(getString(R.string.account_amount_info_string),totalMoney,income,cost));
    }

    /**
     * 弹出删除对话框
     *
     * @param account
     */
    @Override
    public void showDeleteDialog(final Account account) {
        new AlertDialog.Builder(mContext)
                .setTitle(getContext().getString(R.string.dialog_title))
                .setMessage(getContext().getString(R.string.dialog_content_delete))
                .setNegativeButton(getContext().getString(R.string.dialog_cancel), null)
                .setPositiveButton(getContext().getString(R.string.dialog_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteAccountById(account);
                    }
                }).create().show();
    }

    /**
     * 跳转到编辑记录界面
     *
     * @param account
     */
    @Override
    public void jumpToEditAccount(Account account) {
        AccountType accountType = account.getAccountType();
        Intent intent = AccountInfoActivity.newIntent(getContext(),accountType.getType(),account);
        startActivity(intent);
    }


    /**
     * 在view层获取相应的Presenter实例进行交互
     *
     * @param presenter
     */
    @Override
    public void setPresenter(BookAccountContract.IBookAccountPresenter presenter) {
        mPresenter = presenter;
    }
}
