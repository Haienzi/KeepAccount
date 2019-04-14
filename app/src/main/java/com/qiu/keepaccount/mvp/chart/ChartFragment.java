package com.qiu.keepaccount.mvp.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.base.BaseFragment;
import com.qiu.keepaccount.ui.dialog.DateDoubleDialog;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends BaseFragment {
    private DateDoubleDialog mDateDoubleDialog;

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;


    private OnFragmentInteractionListener mListener;

    @SuppressLint("ValidFragment")
    private ChartFragment() {
        // Required empty public constructor
    }


    public static ChartFragment newInstance() {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();

        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    /**
     * 初始化日期时间选择框
     */
    public void initDateTimeDialog( long dateTime) {
        DateDoubleDialog.MyOnDateSetListener myOnDateSetListener = new DateDoubleDialog.MyOnDateSetListener() {

            /**
             * 为传入的view设置开始日期和结束日期
             *
             * @param startDate
             * @param endDate
             */
            @Override
            public void onDateSet(Date startDate, Date endDate) {

            }
        };
        //如果设置的日期时间为空就获取当前的日期时间传入对话框
        if (dateTime == 0) {
            mDateDoubleDialog = new DateDoubleDialog(getActivity(), myOnDateSetListener);
        } else {
            mDateDoubleDialog = new DateDoubleDialog(getActivity(), new Date(dateTime), myOnDateSetListener);
        }

        mDateDoubleDialog.hideOrShow();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            //需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.fragment_chart, container, false);
            initView();
            isPrepared = true;
            //实现懒加载
            lazyLoad();
        }
        //缓存的mView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;

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
        return inflater.inflate(R.layout.fragment_chart,null);
    }

    public void initView(){

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;

    }

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
