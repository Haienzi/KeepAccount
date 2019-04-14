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
import android.widget.TimePicker;

import com.qiu.keepaccount.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mqh on 2017/12/1.
 * 日期时间组合选择对话框
 */

public class DateTimeDialog extends AlertDialog implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, TimePicker.OnTimeChangedListener, DatePicker.OnDateChangedListener,
        View.OnClickListener{
    private Button mOkButton,mCancelButton;
    private RadioGroup mRadioGroup;
    private RadioButton mDateButton,mTimeButton;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private ViewPager mViewPager;
    private View mDatePickerView;
    private View mTimePickerView;
    private Date mDate;
    private Calendar mCalendar;
    //自定义监听器
    private MyOnDateSetListener mMyOnDateSetListener;
    //格式化工具
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mSimpleTimeFormat = new SimpleDateFormat("HH:mm");

    /**
     * @param context
     * @param myOnDateSetListener 监听器
     */
    public DateTimeDialog(Context context, MyOnDateSetListener myOnDateSetListener) {
        this(context, null, myOnDateSetListener);
    }

    /**
     * @param context
     * @param date                默认显示的时间
     * @param myOnDateSetListener 监听器
     */
    public DateTimeDialog(Context context, Date date, MyOnDateSetListener myOnDateSetListener) {
        super(context);
        this.mDate = date;
        this.mMyOnDateSetListener = myOnDateSetListener;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_double, null);
        setView(view);
        mTimePicker= new TimePicker(getContext());
        mTimePicker.setIs24HourView(true);
        mViewPager = (ViewPager) view.findViewById(R.id.content_view_pager);
        mOkButton = (Button) view.findViewById(R.id.ok_button);
        mCancelButton = (Button) view.findViewById(R.id.cancel_button);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.title_group);
        mDateButton = (RadioButton) view.findViewById(R.id.date_button);
        mTimeButton = (RadioButton) view.findViewById(R.id.time_button);

        mDatePickerView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_picker, null);
        mDatePicker = (DatePicker) mDatePickerView.findViewById(R.id.dialog_date_picker);

        mTimePickerView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_time_picker, null);
        mTimePicker = (TimePicker) mTimePickerView.findViewById(R.id.dialog_time_picker);


        // 初始化 状态
        if (mDate == null) {
            mCalendar = Calendar.getInstance();
            mDate = mCalendar.getTime();
        } else {
            mCalendar = Calendar.getInstance();
            mCalendar.setTime(mDate);
        }


        mDateButton.setText(mSimpleDateFormat.format(mDate));
        mTimeButton.setText(mSimpleTimeFormat.format(mDate));
        mTimePicker.setIs24HourView(true);

        // 设置 显示 宽高
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        mTimePicker.measure(width, height);
        mDatePicker.measure(width, height);
        int viewPagerHeight;
        if (mDatePicker.getMeasuredHeight() > mTimePicker.getMeasuredHeight()) {
            viewPagerHeight = mDatePicker.getMeasuredHeight();
        } else {
            viewPagerHeight = mTimePicker.getMeasuredHeight();
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
        mTimePicker.setOnTimeChangedListener(this);
        mCancelButton.setOnClickListener(this);
        mOkButton.setOnClickListener(this);
        //初始化 显示 时间
        mDatePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), this);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);


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
        if (i == mDateButton.getId()) {
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
            mRadioGroup.check(mDateButton.getId());
        } else {
            mRadioGroup.check(mTimeButton.getId());
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
        mTimeButton.setText(mSimpleTimeFormat.format(mCalendar.getTime()));
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
        mDateButton.setText(mSimpleDateFormat.format(mCalendar.getTime()));
//        Log.i("testss", simpleDateFormat.format(calendar.getTime()));
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
                view = mDatePickerView;
                mDateButton.setBackgroundResource(R.drawable.dialog_title_selector);
                mTimeButton.setBackgroundResource(0);
            }else
            {
                view = mTimePickerView;
                mTimeButton.setBackgroundResource(R.drawable.dialog_title_selector);
                mDateButton.setBackgroundResource(0);
            }
            container.addView(view);
            return view;
        }
    }
}
