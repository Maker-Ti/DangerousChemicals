package com.example.a97569.dangerouschemicals.mvp.view.activity;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a97569.dangerouschemicals.R;
import com.example.a97569.dangerouschemicals.app.VehicleApplication;
import com.example.a97569.dangerouschemicals.di.component.DaggerDataStatisticsCompoent;
import com.example.a97569.dangerouschemicals.di.module.DataStatisticsModule;
import com.example.a97569.dangerouschemicals.mvp.chart.BarChartChart;
import com.example.a97569.dangerouschemicals.mvp.chart.PieChartChart;
import com.example.a97569.dangerouschemicals.mvp.contract.DataStatisticsContrat;
import com.example.a97569.dangerouschemicals.mvp.presenter.DataStatisticsPresenter;
import com.example.a97569.dangerouschemicals.mvp.presenter.NetPresenter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by 97569 on 2018/12/19.
 */

public class DataStatisticsActivity extends BaseActivity implements DataStatisticsContrat.view{
    @BindView(R.id.back)
    TextView back;
    @Inject
    DataStatisticsPresenter presenter;

    @Override
    protected void preLoad() {

    }

    @Override
    protected void loadContent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initload() {

    }

    @Override
    protected void initSDK() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.datastatistics;
    }

    @Override
    protected void injectComponent(VehicleApplication application) {
        DaggerDataStatisticsCompoent.builder()
                .dataStatisticsModule(new DataStatisticsModule(this))
                .netComponent(application.getNetComponent())
                .build()
                .datastatistics(this);
    }

    @BindView(R.id.barchart)
    BarChart barChart;
    BarChartChart barChartChart=new BarChartChart();
    @Override
    public void showdatamanage(List<String> data, List<String> down) {
        barChartChart.BarChartDataSet(barChart,data,down,"统计（/次数）", Color.rgb(158,217,0));
    }

    @BindView(R.id.subpiechart)
    PieChart subpieChart;
    PieChartChart pieChartChart=new PieChartChart();
    @Override
    public void showdatasubmitted(List<String> number, List<String> strings) {
        pieChartChart.PieChartSet(subpieChart,number,"比例",strings);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.showmanage("苏J45908");
        presenter.showsubmitted("苏J45908");
    }
}
