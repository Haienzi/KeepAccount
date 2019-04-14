package com.qiu.keepaccount.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mqh on 2017/12/1.
 * 日期时间组合选择对话框
 */

public class DateDoubleDialog extends AlertDialog implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, DatePicker.OnDateChangedListener,
        View.OnClickListener{
    private Button mOkButton,mCancelButton;
    private RadioGroup mRadioGroup;
    private RadioButton mStartButton, mEndButton;
    private DatePicker mStartDatePicker;
    private DatePicker mEndDatePicker;
    private ViewPager mViewPager;
    private View mStartPickerView;
    private View mEndPickerView;
    private Date mDate;
    private Calendar mCalendar;
    //自定义监听器
    private MyOnDateSetListener mMyOnDateSetListener;
    //格式化工具
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final int TAG_START = 101; //开始日期选择框的标志
    private static final int TAG_END= 102;//结束日期选择框的标志

    /**
     * @param context
     * @param myOnDateSetListener 监听器
     */
    public DateDoubleDialog(Context context, MyOnDateSetListener myOnDateSetListener) {
        this(context, null, myOnDateSetListener);
    }

    /**
     * @param context
     * @param date                默认显示的时间
     * @param myOnDateSetListener 监听器
     */
    public DateDoubleDialog(Context context, Date date, MyOnDateSetListener myOnDateSetListener) {
        super(context);
        this.mDate = date;
        this.mMyOnDateSetListener = myOnDateSetListener;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_double, null);
        setView(view);
        mViewPager =  view.findViewById(R.id.content_view_pager);
        mOkButton =  view.findViewById(R.id.ok_button);
        mCancelButton = view.findViewById(R.id.cancel_button);
        mRadioGroup =  view.findViewById(R.id.title_group);
        mStartButton =  view.findViewById(R.id.date_button);
        mEndButton =  view.findViewById(R.id.time_button);

        mStartPickerView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_picker, null);
        mStartDatePicker = (DatePicker) mStartPickerView.findViewById(R.id.dialog_date_picker);

        mEndPickerView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_picker, null);
        mEndDatePicker = (DatePicker) mEndPickerView.findViewById(R.id.dialog_date_picker);
        mStartDatePicker.setTag(TAG_START);
        mEndDatePicker.setTag(TAG_END);
        // 初始化 状态
        if (mDate == null) {
            mCalendar = Calendar.getInstance();
            mDate = mCalendar.getTime();
        } else {
            mCalendar = Calendar.getInstance();
            mCalendar.setTime(mDate);
        }


        mStartButton.setText(mSimpleDateFormat.format(mDate));
        mEndButton.setText(mSimpleDateFormat.format(mDate));

        // 设置 显示 宽高
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        mEndDatePicker.measure(width, height);
        mStartDatePicker.measure(width, height);
        int viewPagerHeight;
        if (mStartDatePicker.getMeasuredHeight() > mEndDatePicker.getMeasuredHeight()) {
            viewPagerHeight = mStartDatePicker.getMeasuredHeight();
        } else {
            viewPagerHeight = mEndDatePicker.getMeasuredHeight();
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, viewPagerHeight);
        params.addRule(RelativeLayout.BELOW, mRadioGroup.getId());
        mViewPager.setLayoutParams(params);

        // 设置 viewPager 显示 内容
        ViewPagerAdapter testPage = new ViewPagerAdapter();
        mViewPager.setAdapter(testPage);

        // 设置监听器
        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager.addOnPageChangeListener(this);

        mCancelButton.setOnClickListener(this);
        mOkButton.setOnClickListener(this);

        //初始化 显示 时间
        mStartDatePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH), this);
        mEndDatePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH), this);


    }

    /**
     * 隐藏 或 显示 弹框
     */
    public void hideOrShow() {
        if (this == null) {
            return;
        }
        if (!this.isShowing()) {
            this.show();
        } else {
            this.dismiss();
        }
    }

    public void setMyOnDateSetListener(MyOnDateSetListener myOnDateSetListener) {
        this.mMyOnDateSetListener = myOnDateSetListener;
    }


    /**
     * 标题 切换 监听
     *
     * @param radioGroup
     * @param i
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == mStartButton.getId()) {
            mViewPager.setCurrentItem(0);
        } else {
            mViewPager.setCurrentItem(1);
        }
    }

    /**
     * ViewPager  滚动 监听
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            mRadioGroup.check(mStartButton.getId());
        } else {
            mRadioGroup.check(mEndButton.getId());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 日期 选择 器 改变 监听
     *
     * @param datePicker
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        mCalendar.set(Calendar.YEAR, datePicker.getYear());
        mCalendar.set(Calendar.MONTH, datePicker.getMonth());
        mCalendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        mCalendar.set(Calendar.HOUR_OF_DAY, mCalendar.get(Calendar.HOUR));
        mCalendar.set(Calendar.MINUTE, mCalendar.get(Calendar.MINUTE));
        switch ((int)datePicker.getTag()){
            case TAG_START:
                mStartButton.setText(mSimpleDateFormat.format(mCalendar.getTime()));
                break;
            case TAG_END:
                mEndButton.setText(mSimpleDateFormat.format(mCalendar.getTime()));
                break;
        }

    }

    /**
     * 确认 取消 按钮 点击 监听
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                this.hideOrShow();
                break;
            case R.id.ok_button:
                this.hideOrShow();
                if (mMyOnDateSetListener != null) {
                    Date startDate = DateUtils.stringToYMDDate(mStartButton.getText().toString());
                    Date endDate = DateUtils.stringToYMDDate(mEndButton.getText().toString());
                    mMyOnDateSetListener.onDateSet(startDate,endDate);
                }
                break;

        }

    }
    public interface MyOnDateSetListener {
        /**
         * 为传入的view设置开始日期和结束日期
         * @param startDate
         * @param endDate
         */
        void onDateSet(Date startDate,Date endDate);
    }
    /**
     * ViewPager适配器
     */
    public class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            if(position == 0)
            {
                view = mStartPickerView;
                mStartButton.setBackgroundResource(R.drawable.dialog_title_selector);
                mEndButton.setBackgroundResource(0);
            }else
            {
                view = mEndPickerView;
                mEndButton.setBackgroundResource(R.drawable.dialog_title_selector);
                mStartButton.setBackgroundResource(0);
            }
            container.addView(view);
            return view;
        }
    }
}
