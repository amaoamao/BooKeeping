package com.amaoamao.hsq.bookeeping;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amaoamao.hsq.bookeeping.Entity.Debt;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TypeChartFragment extends Fragment {


    private View v;
    private PieChart mPieChart;
    private List<Debt> debtList;

    public TypeChartFragment() {
    }


    public static TypeChartFragment newInstance() {
        return new TypeChartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_type_chart, container, false);
        mPieChart = (PieChart) v.findViewById(R.id.pie_chart);

        // 显示百分比
        mPieChart.setUsePercentValues(true);
        // 描述信息
//        Description desc = new Description();
//        desc.setText("测试饼图");
//        mPieChart.setDescription(desc);
        // 设置偏移量
        mPieChart.setExtraOffsets(5, 10, 5, 5);
        // 设置滑动减速摩擦系数
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        debtList = Stream.of(Debt.order("id desc").find(Debt.class))
                .filter(debt -> MainFragment.formateYYMM.format(debt.getTimeCreated()).equals(MainFragment.formateYYMM.format(MainFragment.calendar.getTime()))).collect(Collectors.toList());


        mPieChart.setCenterText("总支出：" + com.amaoamao.hsq.bookeeping.Utils.Utils.outThisMonth(debtList, MainFragment.calendar) + "元");
        /*
            设置饼图中心是否是空心的
            true 中间是空心的，环形图
            false 中间是实心的 饼图
         */
        mPieChart.setDrawHoleEnabled(true);
        /*
            设置中间空心圆孔的颜色是否透明
            true 透明的
            false 非透明的
         */
        mPieChart.setHoleColor(Color.TRANSPARENT);
        // 设置环形图和中间空心圆之间的圆环的颜色
        mPieChart.setTransparentCircleColor(Color.WHITE);
        // 设置环形图和中间空心圆之间的圆环的透明度
        mPieChart.setTransparentCircleAlpha(110);

        // 设置圆孔半径
        mPieChart.setHoleRadius(58f);
        // 设置空心圆的半径
        mPieChart.setTransparentCircleRadius(61f);
        // 设置是否显示中间的文字
        mPieChart.setDrawCenterText(true);


        // 设置旋转角度   ？？
        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(false);

        // add a selection listener
        // mPieChart.setOnChartValueSelectedListener(this);
        Map<String, Double> collect = Stream.of(debtList).groupBy(Debt::getType).collect(
                Collectors.toMap(
                        i -> String.valueOf(com.amaoamao.hsq.bookeeping.Utils.Utils.getTypeName(i.getKey()))
                        , integerListEntry -> com.amaoamao.hsq.bookeeping.Utils.Utils.outThisMonth(integerListEntry.getValue(), Calendar.getInstance())));
        setData(collect);

        // 设置动画
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        // 设置显示的比例
        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        return v;
    }

    public void setData(Map<String, Double> data) {
        List<String> xVals = new ArrayList<>();
        List<PieEntry> yVals1 = new ArrayList<>();

        int i = 0;
        for (Object o : data.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            float value = Float.valueOf(entry.getValue().toString());
            xVals.add(key);
            yVals1.add(new PieEntry(value, i++));
        }

        PieDataSet dataSet = new PieDataSet(yVals1, null);
        // 设置饼图区块之间的距离
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // 添加颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        // dataSet.setSelectionShift(0f);

        PieData data1 = new PieData(dataSet);
        data1.setValueFormatter(new PercentFormatter());
        data1.setValueTextSize(10f);
        data1.setValueTextColor(Color.BLACK);
        mPieChart.setData(data1);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
