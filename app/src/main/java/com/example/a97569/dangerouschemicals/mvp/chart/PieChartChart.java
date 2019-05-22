package com.example.a97569.dangerouschemicals.mvp.chart;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 戴科 on 2018/3/7.
 */

public class PieChartChart {
    public void PieChartSet(PieChart pieChart, List<String> number, String text,  List<String> name){
        PieData mpieData=initPieCharDatatSet(number,name,text);
        initPieChart(pieChart,mpieData);
    }

    private void initPieChart(PieChart pieChart, PieData mpieData) {
        pieChart.setHoleRadius(40f);
        Description description=new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(90);
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("比例");
        pieChart.setCenterTextColor(Color.GRAY);
        pieChart.setData(mpieData);
        mpieData.setValueFormatter(new PercentFormatter());

        Legend legend=pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setTextSize(10);
        legend.setTextColor(Color.GRAY);

        pieChart.animateXY(2000,2000);
    }

    private PieData initPieCharDatatSet(List<String> number, List<String> name, String text) {

        ArrayList<PieEntry> yValse=new ArrayList<>();
        for (int i=0;i<number.size();i++){
            float F=Float.parseFloat(number.get(i));
            int f=Integer.valueOf((int) F);
            yValse.add(new PieEntry(F,(name.get(i))+":  "+f+"次"));
        }

        PieDataSet pieDataSet=new PieDataSet(yValse,text);
        pieDataSet.setSliceSpace(0f);
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.rgb(255,140,49));
        colors.add(Color.rgb(33,166,117));
        colors.add(Color.rgb(68,206,246));
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(20f);
        PieData pieData=new PieData(pieDataSet);
        return pieData;
    }
}
