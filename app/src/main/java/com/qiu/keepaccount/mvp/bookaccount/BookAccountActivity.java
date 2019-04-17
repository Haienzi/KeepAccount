package com.qiu.keepaccount.mvp.bookaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.base.BaseActivity;
import com.qiu.keepaccount.entity.Book;
import com.qiu.keepaccount.model.book.BookModel;
import com.qiu.keepaccount.util.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class BookAccountActivity extends BaseActivity {
    @BindView(R.id.ba_title_cost)
    TextView mTxtTitleCost;
    @BindView(R.id.ba_title_income)
    TextView mTxtTitleIncome;
    @BindView(R.id.ba_title_all)
    TextView mTxtTitleAll;

    public static final String EXTRA_BOOK = "com.qiu.keepaccount.bookaccount.book";
    private BookAccountFragment mBookAccountFragment;
    public int mType = 1;// -1 全部 1 支出 2 收入 默认支出


    /**
     * 启动BookAccountActivity时
     * @param packageContext
     * @param book
     * @return
     */
    public static Intent newIntent(Context packageContext, Book book)
    {
        Intent intent = new Intent(packageContext, BookAccountActivity.class);
        intent.putExtra(EXTRA_BOOK,book);
        return intent;
    }

    /**
     * 获取布局
     */
    @Override
    public int getLayoutRes() {
        return R.layout.activity_book_account;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        Book book = (Book)getIntent().getSerializableExtra(EXTRA_BOOK);
        Bundle bundle = null;
        if(book != null){
            bundle = new Bundle();
            bundle.putSerializable(EXTRA_BOOK,book);
        }

        //设置fragment
        mBookAccountFragment = (BookAccountFragment)getSupportFragmentManager().findFragmentById(
                R.id.book_account_frame);
        if(mBookAccountFragment == null){
            mBookAccountFragment = BookAccountFragment.newInstance(bundle);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mBookAccountFragment,
                    R.id.book_account_frame);
        }
        new BookAccountPresenterImpl(mBookAccountFragment,new BookModel());
    }

    @OnClick({R.id.ba_title_all,R.id.ba_title_cost,R.id.ba_title_income})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ba_title_all:
                if(mType != -1){
                    mType = -1;
                    mTxtTitleAll.setBackgroundResource(R.drawable.bg_tb_select);
                    mTxtTitleCost.setBackgroundResource(0);
                    mTxtTitleIncome.setBackgroundResource(0);
                    mBookAccountFragment.selectAll();
                    break;
                }
                break;
            case R.id.ba_title_cost:
                if(mType != 1){
                    mType = 1;
                    mTxtTitleCost.setBackgroundResource(R.drawable.bg_tb_select);
                    mTxtTitleAll.setBackgroundResource(0);
                    mTxtTitleIncome.setBackgroundResource(0);
                    mBookAccountFragment.selectCost();
                }

                break;
            case R.id.ba_title_income:
                if(mType != 2){
                    mType = 2;
                    updateDataByType();
                    mTxtTitleIncome.setBackgroundResource(R.drawable.bg_tb_select);
                    mTxtTitleCost.setBackgroundResource(0);
                    mTxtTitleAll.setBackgroundResource(0);
                    mBookAccountFragment.selectIncome();
                }
                break;
        }
    }

    /**
     * 根据类型设置数据
     */
    public void updateDataByType(){
        switch (mType){
            case -1:
                mTxtTitleAll.setBackgroundResource(R.drawable.bg_tb_select);
                mTxtTitleCost.setBackgroundResource(0);
                mTxtTitleIncome.setBackgroundResource(0);
                mBookAccountFragment.selectAll();
                break;
            case 1:
                mTxtTitleCost.setBackgroundResource(R.drawable.bg_tb_select);
                mTxtTitleAll.setBackgroundResource(0);
                mTxtTitleIncome.setBackgroundResource(0);
                mBookAccountFragment.selectCost();
                break;
            case 2:
                mTxtTitleIncome.setBackgroundResource(R.drawable.bg_tb_select);
                mTxtTitleCost.setBackgroundResource(0);
                mTxtTitleAll.setBackgroundResource(0);
                mBookAccountFragment.selectIncome();
                break;
        }
    }
}
