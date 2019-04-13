package com.qiu.keepaccount.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.util.ActivityManager;

import butterknife.ButterKnife;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/2 10:09
 * @Description
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected static String TAG;
    protected Activity mContext;
    private Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // 设置 Activity 屏幕方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 设置不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // 隐藏 ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置 TAG
        TAG = this.getClass().getSimpleName();

        super.onCreate(savedInstanceState);

        this.mContext = this;

        // 设置布局
        setContentView(getView());

        // 绑定依赖注入框架
        ButterKnife.bind(this);
        //设置状态栏颜色和主题一致
        initSystemBar(false);
        onCreateActivity(savedInstanceState);
        // 将当前 Activity 推入栈中
        ActivityManager.getInstance().pushActivity(this);
    }

    private View getView(){
        Log.d(TAG, "getView: getView" + getLayoutRes());
        return getLayoutInflater().inflate(getLayoutRes(), null, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 设置状态栏颜色
     * @param isLight
     */
    public void initSystemBar(Boolean isLight) {
        if (Build.VERSION.SDK_INT >= 21) {

            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            if (isLight) {
                window.setStatusBarColor(getResources().getColor(R.color.white));
            } else {
                window.setStatusBarColor(getResources().getColor(R.color.app_status));
            }

            //状态栏颜色接近于白色，文字图标变成黑色
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (isLight) {
                //light --> a|=b的意思就是把a和b按位或然后赋值给a,   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                //dark  --> &是位运算里面，与运算,  a&=b相当于 a = a&b,  ~非运算符
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
        }

    }
    /**
     * 初始化标题栏
     */
    public Toolbar initToolbar(String title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        // 设置标题
        if(!TextUtils.isEmpty(title)){
            mToolbar.setTitle(title);
        }

        // 设置左侧图标
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
        return mToolbar;
    }

    /**
     * 设置标题栏标题
     */
    public void setTitle(String title){
        if(mToolbar != null){
            mToolbar.setTitle(title);
        }
    }

    /**
     * 设置标题栏标题颜色
     */
    public void setTitleTextColor(int id) {
        if (mToolbar != null) {
            mToolbar.setTitleTextColor(id);
        }
    }

    /**
     * 返回
     */
    protected void onBack() {
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            // 点击空白位置 隐藏软键盘
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popActivity(this);
    }

    /**
     * 获取布局
     */
    public abstract int getLayoutRes();

    public abstract void onCreateActivity(@Nullable Bundle savedInstanceState);

}
