package com.example.a97569.dangerouschemicals.mvp.chart;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 戴科 on 2018/3/7.
 */

public class BarChartChart {
    public void BarChartDataSet(BarChart barChart, List<String> data, List<String> data2, String text, int r){
        BarData barData=initBarChartData(data,text,r);
        BarChartSet(barChart,barData,data2);
    }

    private void BarChartSet(BarChart barChart, BarData barData,List<String> data2) {
        barChart.setDrawGridBackground(false);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawBorders(false);
        barChart.setTouchEnabled(true);
        barChart.setData(barData);
        barChart.animateY(2000);
        barChart.setVisibleXRangeMaximum(5);


        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(8f);
        legend.setTextColor(Color.WHITE);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(5);

        IAxisValueFormatter formatter=new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                int index = (int) v;
                if (index < 0 || index >= data2.size()) {
                    return "";
                }
                return data2.get(index);
            }
        };
        xAxis.setValueFormatter(formatter);

        YAxis yAxis = barChart.getAxisRight();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);

        YAxis yAxis1=barChart.getAxisLeft();
        yAxis1.setTextColor(Color.WHITE);

    }

    private BarData initBarChartData(List<String>data, String text, int color) {

        List<BarEntry> list1 = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            float F = Float.parseFloat(data.get(i));
            list1.add(new BarEntry(i,F));
        }

        BarDataSet barDataSet = new BarDataSet(list1, text);
        barDataSet.setColor(color);
        barDataSet.setValueTextSize(10f);

        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);

        BarData barData = new BarData(barDataSets);
        barData.setBarWidth(0.2f);
       /* barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return data.get((int) v)+"次";
            }
        });*/
        barData.setValueTextColor(Color.WHITE);
        barData.setValueTextSize(12);
        return barData;
    }
}
