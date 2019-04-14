package com.qiu.keepaccount.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.qiu.keepaccount.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mqh on 2017/12/1.
 * 日期时间组合选择对话框
 */

public class TimeDoubleDialog extends AlertDialog implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, TimePicker.OnTimeChangedListener,
        View.OnClickListener{
    private Button mOkButton,mCancelButton;
    private RadioGroup mRadioGroup;
    private RadioButton mStartButton, mEndButton;
    private TimePicker mStartPicker;
    private TimePicker mEndPicker;
    private ViewPager mViewPager;
    private View mStartView;
    private View mEndView;
    private Date mDate;
    private Calendar mCalendar;
    //自定义监听器
    private MyOnDateSetListener mMyOnDateSetListener;
    //格式化工具
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mSimpleTimeFormat = new SimpleDateFormat("HH:mm");
    private static final int TAG_START = 101; //开始日期选择框的标志
    private static final int TAG_END= 102;//结束日期选择框的标志


    /**
     * @param context
     * @param myOnDateSetListener 监听器
     */
    public TimeDoubleDialog(Context context, MyOnDateSetListener myOnDateSetListener) {
        this(context, null, myOnDateSetListener);
    }

    /**
     * @param context
     * @param date                默认显示的时间
     * @param myOnDateSetListener 监听器
     */
    public TimeDoubleDialog(Context context, Date date, MyOnDateSetListener myOnDateSetListener) {
        super(context);
        this.mDate = date;
        this.mMyOnDateSetListener = myOnDateSetListener;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_double, null);
        setView(view);
        mEndPicker = new TimePicker(getContext());
        mEndPicker.setIs24HourView(true);
        mViewPager =  view.findViewById(R.id.content_view_pager);
        mOkButton = view.findViewById(R.id.ok_button);
        mCancelButton =  view.findViewById(R.id.cancel_button);
        mRadioGroup =  view.findViewById(R.id.title_group);
        mStartButton =  view.findViewById(R.id.date_button);
        mEndButton =  view.findViewById(R.id.time_button);

        mStartView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_time_picker, null);
        mStartPicker =  mStartView.findViewById(R.id.dialog_time_picker);

        mEndView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_picker, null);
        mEndPicker =  mEndView.findViewById(R.id.dialog_time_picker);
        mStartPicker.setTag(TAG_START);
        mEndPicker.setTag(TAG_END);

        // 初始化 状态
        if (mDate == null) {
            mCalendar = Calendar.getInstance();
            mDate = mCalendar.getTime();
        } else {
            mCalendar = Calendar.getInstance();
            mCalendar.setTime(mDate);
        }


        mStartButton.setText(mSimpleDateFormat.format(mDate));
        mEndButton.setText(mSimpleTimeFormat.format(mDate));
        mStartPicker.setIs24HourView(true);
        mEndPicker.setIs24HourView(true);

        // 设置 显示 宽高
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        mEndPicker.measure(width, height);
        mStartPicker.measure(width, height);
        int viewPagerHeight;
        if (mStartPicker.getMeasuredHeight() > mEndPicker.getMeasuredHeight()) {
            viewPagerHeight = mStartPicker.getMeasuredHeight();
        } else {
            viewPagerHeight = mEndPicker.getMeasuredHeight();
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
        mEndPicker.setOnTimeChangedListener(this);
        mCancelButton.setOnClickListener(this);
        mOkButton.setOnClickListener(this);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        mEndPicker.setCurrentHour(hour);
        mEndPicker.setCurrentMinute(minute);
        mStartPicker.setCurrentHour(hour);
        mStartPicker.setCurrentMinute(minute);


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
     * 小时 选择器 改变 监听
     *
     * @param timePicker
     * @param i
     * @param i1
     */
    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
        mCalendar.set(Calendar.YEAR, mCalendar.get(Calendar.YEAR));
        mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH));
        mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.get(Calendar.DAY_OF_MONTH));
        mCalendar.set(Calendar.HOUR_OF_DAY, i);
        mCalendar.set(Calendar.MINUTE, i1);
        switch ((int)timePicker.getTag()){
            case TAG_START:
                mStartButton.setText(mSimpleTimeFormat.format(mCalendar.getTime()));
                break;
            case TAG_END:
                mEndButton.setText(mSimpleTimeFormat.format(mCalendar.getTime()));
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
//                    SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
//                    Log.i("testss", "----------" + mFormatter.format(calendar.getTime()));
                    mMyOnDateSetListener.onDateSet(mCalendar.getTime());
                }
                break;

        }

    }
    public interface MyOnDateSetListener {
        /**
         * 为传入的view设置日期
         * @param date
         *
         */
        void onDateSet(Date date);
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
                view = mStartView;
                mStartButton.setBackgroundResource(R.drawable.dialog_title_selector);
                mEndButton.setBackgroundResource(0);
            }else
            {
                view = mEndView;
                mEndButton.setBackgroundResource(R.drawable.dialog_title_selector);
                mStartButton.setBackgroundResource(0);
            }
            container.addView(view);
            return view;
        }
    }
}
