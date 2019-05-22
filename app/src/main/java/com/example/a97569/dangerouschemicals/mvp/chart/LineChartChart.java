package com.example.a97569.dangerouschemicals.mvp.chart;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 97569 on 2018/4/23.
 */

public class LineChartChart {
    public void LineChartDataSet(LineChart lineChart, List<String> data, String text, List<String>type,int color){
        LineData lineData=getlineDate(data,text,color);
        getlinetChart(lineData,lineChart,type);
    }

    public void initclear(LineChart lineChart){
        lineChart.setData(new LineData());
        lineChart.clear();
        lineChart.invalidate();
    }

    private void getlinetChart(LineData lineData, LineChart lineChart, final List<String> type) {
        lineChart.setDrawGridBackground(false);
        Description description=new Description();
        description.setText("");
        lineChart.setDescription(description);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDrawBorders(false);
        lineChart.setTouchEnabled(true);
        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.animateY(2000);

        Legend legend=lineChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(8f);
        legend.setTextColor(Color.WHITE);

        lineChart.setExtraRightOffset(50);

        IAxisValueFormatter iAxisValueFormatter=new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                // return type.get((int) v);
                String val=null;
                try {
                    val = type.get((int) v);
                } catch (IndexOutOfBoundsException e) {
                    axisBase.setGranularityEnabled(false);
                }
                return val;
            }
        };

        XAxis xAxis=lineChart.getXAxis();
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(iAxisValueFormatter);


        YAxis yAxisleft=lineChart.getAxisLeft();
        yAxisleft.setXOffset(20f);

        YAxis yAxis=lineChart.getAxisRight();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);

        YAxis yAxis1=lineChart.getAxisLeft();
        yAxis1.setTextColor(Color.WHITE);
    }

    private LineData getlineDate(List<String> data, String text,int color) {
        List<Integer> integers=new ArrayList<>();
        List<Entry> list1=new ArrayList<>();
        try {
            for (int i=0;i<data.size();i++){
                float F=Float.parseFloat(data.get(i));
                if (F>=5){
                    integers.add(Color.rgb(223,63,79));
                }else {
                    integers.add(Color.rgb(111,222,90));
                }
                list1.add(new Entry(i,F));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        LineDataSet lineDataSet = new LineDataSet(list1, text);
        lineDataSet.setValueTextColors(integers);
        lineDataSet.setColor(color);
        lineDataSet.setLineWidth(3);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.rgb(186,202,198));
        LineData lineData = new LineData(lineDataSet);
        return lineData;
    }
}

