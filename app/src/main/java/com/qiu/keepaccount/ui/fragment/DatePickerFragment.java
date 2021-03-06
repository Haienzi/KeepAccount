package com.qiu.keepaccount.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.qiu.keepaccount.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 *
 */
public class DatePickerFragment extends DialogFragment {
    private static final String ARG_DATE = "date";
    //传递的日期数据
    public static final String EXTRA_DATE ="com.qiu.keepaccount.ui.fragment.date";
    private DatePicker mDatePicker;

    /**
     *  将crime的记录日期传递给DatePickerFragment
     * @param date
     * @return
     */
    public static DatePickerFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE,date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * 创建一个带标题栏和OK按钮的AlertDialog
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //DatePicker对象的初始化需整数形式的月日年 Date是个时间戳 无法提供整数形式的年月日
        Date date = (Date)getArguments().getSerializable(ARG_DATE);
        //因此 创建一个calender对象 用date配置它 再获取相关信息
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //获取DatePicker
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date_picker,null);

        mDatePicker = (DatePicker)v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year,month,day,null);

        //注意使用的时v7.app中的
        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(v)                           //添加DatePicker给AlertDialog
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    //点击OK按钮将DatePicker中获取的日期传递给CrimeFragment
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        //阳历
                        @SuppressLint({"NewApi", "LocalSuppress"})
                        Date date = new GregorianCalendar(year,month,day).getTime();
                        sendResult(date);

                    }
                })
                .create();
    }

    /**
     * 设置返回结果
     * @param date 选中的日期
     */
    private void sendResult(Date date)
    {
        if(getTargetFragment() == null)
        {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE,date);
        //回调目标fragment 传递结果
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}
