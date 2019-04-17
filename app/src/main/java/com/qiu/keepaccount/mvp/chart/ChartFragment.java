package com.qiu.keepaccount.mvp.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.base.BaseFragment;
import com.qiu.keepaccount.entity.LinearPointData;
import com.qiu.keepaccount.entity.PiePointData;
import com.qiu.keepaccount.model.account.AccountModel;
import com.qiu.keepaccount.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import lecho.lib.hellocharts.formatter.LineChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * to handle interaction events.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends BaseFragment implements ChartContract.ChartView {

    @BindView(R.id.chart_frame)
    LineChartView mLineChartView;
    @BindView(R.id.pie_cost_frame)
    PieChartView mCostPieView;
    @BindView(R.id.pie_income_frame)
    PieChartView mIncomePieView;
    @BindView(R.id.chart_date)
    TextView mDateText;
    @BindView(R.id.chart_back_img)
    ImageView mBackImg;
    @BindView(R.id.date_last)
    ImageView mDateLast;
    @BindView(R.id.date_next)
    ImageView mDateNext;


    private List mChartLines = new ArrayList();//折线线条集合
    LineChartData lineChartData = new LineChartData();//折线图数据集
    private PieChartData mPieChartData = new PieChartData();//饼图数据集
    private List<PiePointData> mCostValues = new ArrayList<>();//支出数值集
    private List<PiePointData> mIncomeValues = new ArrayList<>();//收入数值集
    private List<SliceValue> mCostSliceValues = new ArrayList<>();
    private List<SliceValue> mIncomeSliceValues = new ArrayList<>();
    private List<String> mCostType = new ArrayList<>();
    private List<String> mIncomeTypes = new ArrayList<>();
    private int[] mColors;
    private List mPointValues = new ArrayList();
    // 定义格式，小数点等等信息
    LineChartValueFormatter chartValueFormatter = new SimpleLineChartValueFormatter(2);
    private ChartContract.IChartPresenter mPresenter;
    private String mDate;//选择的开始时间
    private String[] mTitles;
    private Handler mHandler;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;



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

    /**
     * 得出下个title
     * @param sign 1 上个月 2 下个月
     * @return
     */
    private void  getDateTitle(int sign){
        String[] date = mDate.split("-");
        int year = Integer.valueOf(date[0]);
        int month = Integer.valueOf(date[1]);
        if(sign == 1 &&  month == 1){ //上一个月
            mDate = String.valueOf((year-1) +"-"+(month-1 ));
        }else if(sign == 2 && month == 12){
            //下一个月
            mDate = String.valueOf((year+1) +"-"+(month+1) );
        }else {
            mDate = String.valueOf(year +"-"+month );
        }
    }
    private void initColor(){
        TypedArray colors = getResources().obtainTypedArray(R.array.cost_color);
        for(int i=0;i<colors.length();i++){
            mColors[i] = colors.getResourceId(i,Color.GRAY);
        }
        colors.recycle();
    }

    /**
     * 初始化线性表
     */
    public void initLinerData(List<LinearPointData> linearPointData,int color){
        List<AxisValue> axisValues = new ArrayList<>();
        List<PointValue> pointValues = new ArrayList<PointValue>();
        for(LinearPointData pointData:linearPointData){
            String[] time = pointData.getTime().split("-");
            int day = Integer.valueOf(time[2]);
            PointValue pointValue = new PointValue(day,(float) pointData.getMoney());
            pointValues.add(pointValue);
        }
        Line chartLine = new Line();
        chartLine.setValues(pointValues);
        chartLine.setColor(color);//设置线的颜色
        chartLine.setPointColor(color);//设置点的颜色
        chartLine.setShape(ValueShape.CIRCLE);
        chartLine.setPointRadius(2);//设置点的大小
        chartLine.setCubic(true);//每个点的标注信息
        chartLine.setFilled(false);//阴影面积
        chartLine.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        chartLine.setHasPoints(true);//是否用点显示。如果为false 则没有点
        chartLine.setHasLabels(true);//点的标注是否显示
        chartLine.setStrokeWidth(2);//线的粗细
        mChartLines.add(chartLine);//显示几条线，就可以分别设置每一条线，然后add进来
    }

    /**
     * 绘制线性图表
     */
    private void drawLinearView(){
        int maxDays = DateUtils.getDays(mDate);
        String[] time = mDate.split("-");
        int month = Integer.valueOf(time[1]);
        List<AxisValue> axisValues = new ArrayList<>();
        for(int i=0;i<maxDays;i++){
            AxisValue axisValue = new AxisValue(i);
            axisValue.setLabel(month+"-"+i);
            axisValues.add(axisValue);
        }
        Axis axisX = new Axis();
        axisX.setValues(axisValues).setHasLines(true);
        axisX.setTextColor(Color.BLACK).setLineColor(Color.GRAY);
        Axis axisY = new Axis();
        axisY.setHasLines(true);
        axisY.setTextColor(Color.BLACK);
        axisY.setLineColor(Color.GRAY);
        lineChartData.setLines(mChartLines);
        lineChartData.setAxisXBottom(axisX);
        lineChartData.setAxisYLeft(axisY);
        lineChartData.setValueLabelBackgroundColor(Color.TRANSPARENT);
        lineChartData.setValueLabelBackgroundEnabled(false);

        mLineChartView.setLineChartData(lineChartData);
        mLineChartView.setInteractive(true);
        mLineChartView.setZoomEnabled(true);
        mLineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

    }

    /**
     * 初始化饼图数据
     */
    private void initPieData(List<PiePointData> piePointDatas,List<SliceValue> sliceValues,int typeId,
                             List<String> typeString){
        typeString.clear();
        String[] types = getResources().getStringArray(typeId);
        for(int i=0;i<piePointDatas.size();i++){
            SliceValue sliceValue = new SliceValue((float)piePointDatas.get(i).getAmount(),
                    mColors[i]);
            sliceValues.add(sliceValue);
            typeString.add(types[i]);
        }
    }

    /**
     * 绘制饼图
     * @param pieChartView
     * @param sliceValues
     */
    private void drawPieChart(PieChartView pieChartView, List<SliceValue> sliceValues, final List<String> types, final int type){
        final PieChartData pieChartData = new PieChartData();
        pieChartData.setHasLabels(true);//显示标签
        pieChartData.setHasLabels(true);//显示表情
        pieChartData.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        pieChartData.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChartData.setHasCenterCircle(true);//是否是环形显示
        pieChartData.setValues(sliceValues);//填充数据
        pieChartData.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
        pieChartData.setCenterCircleScale(0.5f);//设置环形的大小级别
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setValueSelectionEnabled(true);//选择饼图某一块变大
        pieChartView.setAlpha(0.9f);//设置透明度
        pieChartView.setCircleFillRatio(1f);//设置饼图大小
        pieChartView.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, SliceValue sliceValue) {
                pieChartData.setCenterText1(types.get(i));
                pieChartData.setCenterText1Color(mColors[i]);
                pieChartData.setCenterText1FontSize(10);
                pieChartData.setCenterText2(sliceValue.getValue() + "（" + calPercent(i,type) + ")");
                pieChartData.setCenterText2Color(mColors[i]);
                pieChartData.setCenterText2FontSize(12);
                Toast.makeText(getContext(), types.get(i) + ":" + sliceValue.getValue(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });

    }
    private String calPercent(int j,int type) {
        String result = "";
        double sum = 0;
        List<PiePointData> piePointData;
        if(type==1){
            piePointData = mCostValues;
        }else {
            piePointData = mIncomeValues;
        }
        for (int i = 0; i < piePointData.size(); i++) {
            sum += piePointData.get(i).getAmount();
        }
        result = String.format("%.2f", (piePointData.get(j).getAmount()) * 100 / sum) + "%";
        return result;
    }

    private void setListeners(){
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
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;

    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {
        new ChartPresenterImpl(this,new AccountModel());
        initColor();
        //初始化日期
        mTitles = new String[12];
        Calendar c = Calendar.getInstance(Locale.CHINA);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        mDate = String.valueOf(year+"-"+month);

        mDateText.setText(mDate);


        mHandler = new Handler() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        initDataAndView();
                        break;

                }
                super.handleMessage(msg);
            }
        };
    }

    /**
     * 初始化视图和数据
     */
    private void  initDataAndView(){
        int days = DateUtils.getDays(mDate);
        String startDate = String.valueOf(mDate+"-"+"01");
        String endDate = String.valueOf(mDate+"-"+days);
        mPresenter.selectLinearData(startDate,endDate);
        mPresenter.selectPieData(startDate,endDate);
        drawLinearView();
        drawPieChart(mCostPieView,mCostSliceValues,mCostType,1);
        drawPieChart(mIncomePieView,mCostSliceValues,mCostType,2);
    }
    @OnClick({R.id.date_last,R.id.date_next,R.id.back_img})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.date_last:
                getDateTitle(1);
                mDateText.setText(mDate);
                break;
            case R.id.date_next:
                getDateTitle(2);
                mDateText.setText(mDate);
                break;
            case R.id.chart_back_img:
                finish();
                break;
        }
        Message message = new Message();
        message.what = 1;
        mHandler.sendMessage(message);
    }

    /**
     * 在view层获取相应的Presenter实例进行交互
     *
     * @param presenter
     */
    @Override
    public void setPresenter(ChartContract.IChartPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 线性图
     *
     * @param accountList
     */
    @Override
    public void setLinerData(List<LinearPointData> accountList) {
        initLinerData(accountList,R.color.total_color);
    }

    /**
     * 线性图
     *
     * @param accountList
     */
    @Override
    public void setCostLinerData(List<LinearPointData> accountList) {
        initLinerData(accountList,R.color.cost_color);
    }

    /**
     * 线性图
     *
     * @param accountList
     */
    @Override
    public void setIncomeLinerData(List<LinearPointData> accountList) {
        initLinerData(accountList,R.color.income_color);
    }

    /**
     * 支出分布饼图
     *
     * @param accountList
     */
    @Override
    public void setCostPieChart(List<PiePointData> accountList) {
        initPieData(accountList,mCostSliceValues,R.array.cost_type,mCostType);
    }

    /**
     * 收入分布饼图
     *
     * @param accountList
     */
    @Override
    public void setIncomePieChart(List<PiePointData> accountList) {
        initPieData(accountList,mIncomeSliceValues,R.array.income_type,mIncomeTypes);
    }
}
