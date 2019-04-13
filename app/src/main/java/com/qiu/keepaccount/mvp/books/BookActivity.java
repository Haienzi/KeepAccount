package com.qiu.keepaccount.mvp.books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.adapter.BookRecyclerAdapter;
import com.qiu.keepaccount.base.BaseActivity;
import com.qiu.keepaccount.entity.Book;
import com.qiu.keepaccount.entity.User;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;
import com.qiu.keepaccount.model.book.BookModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/24 16:07
 * @Description 账本页面
 */
public class BookActivity extends BaseActivity implements BookContract.BookView{

    @BindView(R.id.back_img)
    ImageView mBackImg;//返回
    @BindView(R.id.add_img)
    ImageView mAddImg;//添加账本
    @BindView(R.id.book_recycler_view)
    RecyclerView mRecyclerView;

    public static final String EXTRA_BOOK_INFO = "com.qiu.keepaccount.bookactivity.book";

    private BookRecyclerAdapter mBookRecyclerAdapter;
    private BookContract.BookPresenter mBookPresenter;
    private List<Book> mBooks = new ArrayList<>();
    /**
     * 启动BookActivity时
     * @param packageContext
     * @return
     */
    public static Intent newIntent(Context packageContext)
    {
        Intent intent = new Intent(packageContext,BookActivity.class);
        //intent.putExtra(EXTRA_CRIME_ID,id);//传递附加信息
        return intent;
    }


    /**
     * 获取布局
     */
    @Override
    public int getLayoutRes() {
        return R.layout.activity_book;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        mBookPresenter = new BookPresenterImpl(this,new BookModel());
        initAdapter();
    }

    void initAdapter(){
        User user = new User();
        user.setId(-1);
        mBookPresenter.queryBooks(user);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.
                VERTICAL,false));
        mBookRecyclerAdapter = new BookRecyclerAdapter(getBaseContext(),mBooks);
        mRecyclerView.setAdapter(mBookRecyclerAdapter);
        mBookRecyclerAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //进入账本详细记录页面
                Intent intent = new Intent(getBaseContext(),AddBookActivity.class);
                intent.putExtra(EXTRA_BOOK_INFO,mBooks.get(position));
                startActivity(intent);
            }
        });

    }

    @OnClick({R.id.back_img,R.id.add_img})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.add_img:
                Log.d(TAG, "onClick: add");
                Intent intent = AddBookActivity.newIntent(this);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void queryBooks(List<Book> books) {
        mBookRecyclerAdapter.setData(books);
        mBookRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mBookRecyclerAdapter);
    }

    @Override
    public void deleteBook() {

    }

    /**
     * 在view层获取相应的Presenter实例进行交互
     *
     * @param presenter
     */
    @Override
    public void setPresenter(BookContract.BookPresenter presenter) {
        mBookPresenter = presenter;
    }
}
