package com.qiu.keepaccount.mvp.books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.base.BaseActivity;
import com.qiu.keepaccount.model.book.BookModel;
import com.qiu.keepaccount.util.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBookActivity extends BaseActivity {
    @BindView(R.id.back_img)
    ImageView mBackImg;//返回
    @BindView(R.id.back_save)
    ImageView mSaveImg;//返回
    private AddBookFragment mFragment;

    /**
     * 启动AddBookActivity时
     * @param packageContext
     * @return
     */
    public static Intent newIntent(Context packageContext)
    {
        Intent intent = new Intent(packageContext,AddBookActivity.class);
        //intent.putExtra(EXTRA_CRIME_ID,id);//传递附加信息
        return intent;
    }

    /**
     * 获取布局
     */
    @Override
    public int getLayoutRes() {
        return R.layout.activity_add_book;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        // set fragment
        mFragment = (AddBookFragment) getSupportFragmentManager().findFragmentById(R.id.add_book_frame);
        if (mFragment == null) {
            // Create the fragment
            mFragment = AddBookFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mFragment, R.id.add_book_frame);
        }

        // create the presenter
        new BookPresenterImpl(mFragment,new BookModel());
    }

    @OnClick({R.id.back_img,R.id.back_save})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.back_save:
                mFragment.save();
                break;
        }
    }

}
