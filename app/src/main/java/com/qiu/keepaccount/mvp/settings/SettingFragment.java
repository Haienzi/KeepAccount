package com.qiu.keepaccount.mvp.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.base.BaseFragment;
import com.qiu.keepaccount.entity.Budget;
import com.qiu.keepaccount.model.budget.BudgetModel;
import com.qiu.keepaccount.ui.activity.BackUpActivity;
import com.qiu.keepaccount.ui.fragment.TimePickerFragment;
import com.qiu.keepaccount.util.DateUtils;
import com.qiu.keepaccount.util.ToastUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class SettingFragment extends BaseFragment implements SettingContract.ISettingView {
    @BindView(R.id.budget_notify_switch)
    Switch mBudgetSwitch;
    @BindView(R.id.account_notify_switch)
    Switch mAccountSwitch;
    @BindView(R.id.set_budget_layout)
    RelativeLayout mBudgetLayout;
    @BindView(R.id.account_time_layout)
    RelativeLayout mAccountLayout;
    @BindView(R.id.budget_min_set)
    AppCompatEditText mBudgetEdit;
    @BindView(R.id.account_time_img)
    ImageView mTimeImg;
    @BindView(R.id.set_reminder_time)
    TextView mTimeText;
    @BindView(R.id.data_back_restore)
    ImageView mBackRestore;

    private Date mReminderTime;//设置的提醒时键
    private double mBudget;
    private static final String DIALOG_TIME = "DialogTime";//TimePickerFragment的tag
    private static final int REQUEST_TIME = 1;//TimePickerFragment的请求代码


    private SettingContract.ISettingPresenter mPresenter;

    @OnClick({R.id.data_back_restore,R.id.account_time_img})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.data_back_restore:
                jumpToBackActivity();
                break;
            case R.id.account_time_img:
                showTimeDialog();
                break;
        }
    }

    /**
     * 跳转到数据备份的页面
     */
    private void jumpToBackActivity(){
        Intent intent = BackUpActivity.newIntent(getContext());
        startActivity(intent);
    }
    /**
     * 打开选择时间的对话框
     */
    private void showTimeDialog(){
        FragmentManager manager = getFragmentManager();
        TimePickerFragment dialog = TimePickerFragment.newInstance(mReminderTime);
        dialog.setTargetFragment(SettingFragment.this,REQUEST_TIME);
        dialog.show(manager,DIALOG_TIME);
    }

    @SuppressLint("ValidFragment")
    private SettingFragment() {
        // Required empty public constructor
    }


    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return inflater.inflate(R.layout.fragment_setting,null);
    }

    public void setListeners(){
        mBudgetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mBudgetLayout.setVisibility(View.VISIBLE);
                    mPresenter.queryBudget(DateUtils.dateYMDToString(new Date()));
                    mBudgetEdit.setHint(String.valueOf(mBudget/2));
                    mBudgetEdit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            Log.d(TAG, "afterTextChanged: budget" + s.toString());
                            if(Double.valueOf(s.toString()) > mBudget){
                                ToastUtils.show(getContext(),"超出预算值，请重新设置");
                            }
                            s.clear();
                        }
                    });
                }else {
                    mBudgetLayout.setVisibility(View.GONE);
                }
            }
        });
        mAccountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mAccountLayout.setVisibility(View.VISIBLE);
                }else {
                    mAccountLayout.setVisibility(View.GONE);
                }
            }
        });
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
     * 延迟加载
     * 子类必须重写此方法
     */
    @Override
    public void lazyLoad() {


    }

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {
        new SettingPrensterImpl(this,new BudgetModel());
        mReminderTime = new Date();
        mTimeText.setText(String.format(getString(R.string.reminder_time),
                DateUtils.dateToString(mReminderTime,DateUtils.TIME_FORMAT)));

        setListeners();
    }



    /**
     * 在view层获取相应的Presenter实例进行交互
     *
     * @param presenter
     */
    @Override
    public void setPresenter(SettingContract.ISettingPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_TIME)
        {
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mReminderTime = time;
            mTimeText.setText(String.format(getString(R.string.reminder_time),
                    DateUtils.dateToString(time,DateUtils.TIME_FORMAT)));
        }

    }

    @Override
    public void setBudget(Budget budget) {
        mBudget = budget.getBudget();
    }
}
