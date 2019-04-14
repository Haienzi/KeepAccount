package com.qiu.keepaccount.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.qiu.keepaccount.R;

import java.util.Objects;

public class BudgetPickerFragment extends DialogFragment {
    private static final String ARG_BUDGET = "budget";
    //传递的日期数据
    public static final String EXTRA_BUDGET ="com.qiu.keepaccount.ui.fragment.budget";
    private EditText mEditText;

    /**
     *  将预算传递给BudgetPickerFragment
     * @param budget 预算
     * @return
     */
    public static BudgetPickerFragment newInstance(int budget)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BUDGET,budget);
        BudgetPickerFragment fragment = new BudgetPickerFragment();
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
        int budgetDefault = 0;
        if (getArguments() != null) {
            budgetDefault = (int)getArguments().getSerializable(ARG_BUDGET);
        }
        //获取EditText
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_budget_picker,null);

        //设置默认值
        mEditText = v.findViewById(R.id.edit_budget);
        mEditText.setHint(String.valueOf(budgetDefault));

        //注意使用的时v7.app中的
        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(v)                           //添加DatePicker给AlertDialog
                .setTitle(R.string.budget_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    //点击OK按钮将DatePicker中获取的日期传递给CrimeFragment
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int budgetMoney = 1500;
                        if(!TextUtils.isEmpty(mEditText.getText().toString())){
                             budgetMoney  = Integer.valueOf(mEditText.getText().toString());
                        }
                        //阳历
                        sendResult(budgetMoney);
                    }
                })
                .create();
    }

    /**
     * 设置返回的结果
     * @param budget 预算
     */
    private void sendResult(int budget)
    {
        if(getTargetFragment() == null)
        {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_BUDGET,budget);
        //回调目标fragment 传递结果
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}
