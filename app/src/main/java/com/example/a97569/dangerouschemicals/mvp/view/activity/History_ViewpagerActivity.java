package com.example.a97569.dangerouschemicals.mvp.view.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a97569.dangerouschemicals.R;
import com.example.a97569.dangerouschemicals.app.VehicleApplication;
import com.example.a97569.dangerouschemicals.di.component.DaggerHistory_ViewPagerComponent;
import com.example.a97569.dangerouschemicals.di.module.History_ViewpagerModule;
import com.example.a97569.dangerouschemicals.mvp.adapter.WarningListAdapter;
import com.example.a97569.dangerouschemicals.mvp.chart.LineChartChart;
import com.example.a97569.dangerouschemicals.mvp.contract.History_ViewPagerContract;
import com.example.a97569.dangerouschemicals.mvp.datapicker.CustomDatePicker;
import com.example.a97569.dangerouschemicals.mvp.presenter.History_ViewpagerPresenter;
import com.github.mikephil.charting.charts.LineChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by 97569 on 2018/12/1.
 */

public class History_ViewpagerActivity extends BaseActivity implements History_ViewPagerContract.view,View.OnClickListener {
    @Inject
    History_ViewpagerPresenter presenter;
    @BindView(R.id.control)
    RelativeLayout control;
    @BindView(R.id.dialog)
    LinearLayout dialog;
    boolean flag = true;
    @BindView(R.id.content)
    RelativeLayout content;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.arrow)
    TextView arrow;
    @BindView(R.id.offer)
    Button offer;
    @BindView(R.id.visible)
    Button visible;
    @BindView(R.id.all)
    Button all;
    @BindView(R.id.back)
    TextView back;
    private boolean flag1 = false;
    private boolean allCheckControl = false;

    WarningListAdapter listAdapter;

    CustomDatePicker starttimepicker,endtimepicker;

    @Override
    protected void preLoad() {
        Intent intent = getIntent();
        boolean setView = intent.getBooleanExtra("flag",false);
        if(setView==false){
            dialog.setVisibility(View.GONE);
        }
    }
    @Override
    public void setWarningList(List<Map<String, String>> data1,List<Map<String, String>> data0) {
        listAdapter = new WarningListAdapter(this,data1);
        list.setAdapter(listAdapter);
        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenter.offerWarning(data1);
            }
        });
        visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapter.checkBoxVisible(flag1);
                list.setAdapter(listAdapter);
                if(flag1){
                    flag1 = false;
                    visible.setText("取消显示");
                }else {
                    flag1 = true;
                    visible.setText("显示");
                }


            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.setAdapter(listAdapter);
                if(allCheckControl == false){
                    allCheckControl = true;
                    all.setText("非全选");
                }else {
                    allCheckControl = false;
                    all.setText("全选");
                }
                listAdapter.changeAllCheck(allCheckControl);
            }
        });
    }

    @Override
    protected void loadContent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        presenter.ReferFault();
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    content.setVisibility(View.GONE);
                    ObjectAnimator rotate_btn = ObjectAnimator.ofFloat(arrow, "rotation", 45, 0).setDuration(200);
                    rotate_btn.setStartDelay(100);
                    rotate_btn.start();
                    flag = false;
                }else {
                    ObjectAnimator rotate_btn = ObjectAnimator.ofFloat(arrow, "rotation", 0, 45).setDuration(200);
                    rotate_btn.setStartDelay(100);
                    rotate_btn.start();
                    content.setVisibility(View.VISIBLE);
                    flag = true;
                }
            }
        });

    }

    @Override
    protected void initload() {

    }

    @Override
    protected void initSDK() {

    }

    //绑定页面
    @Override
    protected int setLayoutId() {
        return R.layout.history_viewpager;
    }

    @Override
    protected void injectComponent(VehicleApplication application) {
        DaggerHistory_ViewPagerComponent.builder()
                .history_ViewpagerModule(new History_ViewpagerModule(this))
                .netComponent(application.getNetComponent())
                .build()
                .injectHistoryactivity(this);
    }

    @BindView(R.id.S_H_li)
    LineChart Line_liquidlevel;
    @BindView(R.id.S_H_pre)
    LineChart Line_pressure;
    @BindView(R.id.S_H_tem)
    LineChart Line_tempreature;
    @BindView(R.id.select_mamage)
    TextView select_manage;
    LineChartChart lineChartChart=new LineChartChart();
    //获取历史数据并展示在图表中
    @Override
    public void showhistorymanage(List<String> liquideveltime,List<String> tempreaturetime,List<String> pressuretime,
                                  List<String> liquidevel,List<String> tempreature, List<String> pressure) {
        if (liquidevel.size()>1||tempreature.size()>1||pressure.size()>1){
            initVis();
            lineChartChart.LineChartDataSet(Line_liquidlevel,liquidevel,"液位",liquideveltime, Color.WHITE);
            lineChartChart.LineChartDataSet(Line_pressure,pressure,"压力",pressuretime,Color.WHITE);
            lineChartChart.LineChartDataSet(Line_tempreature,tempreature,"温度",tempreaturetime,Color.WHITE);
        }else {
            initClear();
        }
    }

    @Override
    public void checkdata(List<String> liquideveltime,List<String> tempreaturetime,List<String> pressuretime,
                          List<String> liquidevel,List<String> tempreature, List<String> pressure) {
        initClear();
        if (liquidevel.size()>1||tempreature.size()>1||pressure.size()>1){
            initVis();
            lineChartChart.LineChartDataSet(Line_liquidlevel,liquidevel,"液位",liquideveltime, Color.WHITE);
            lineChartChart.LineChartDataSet(Line_pressure,pressure,"压力",pressuretime,Color.WHITE);
            lineChartChart.LineChartDataSet(Line_tempreature,tempreature,"温度",tempreaturetime,Color.WHITE);

        }else{
            test1.setVisibility(View.VISIBLE);
            test2.setVisibility(View.VISIBLE);
            test3.setVisibility(View.VISIBLE);
        }
    }

    private void initClear() {
        lineChartChart.initclear(Line_liquidlevel);
        lineChartChart.initclear(Line_pressure);
        lineChartChart.initclear(Line_tempreature);
    }

    @Override
    public void viewtest() {
        /*List<String> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            int a= (int) (Math.random()*10);
            list.add(String.valueOf(a));
        }
        lineChartChart.LineChartDataSet(Line_liquidlevel,list,"液位",list, Color.rgb(255,255,255));
        lineChartChart.LineChartDataSet(Line_pressure,list,"压力",list,Color.rgb(255,255,255));
        lineChartChart.LineChartDataSet(Line_tempreature,list,"温度",list,Color.rgb(255,255,255));*/
        /*select_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list1=new ArrayList<>();
                for(int i=0;i<15;i++){
                    int a= (int) (Math.random()*10);
                    list1.add(String.valueOf(a));
                }
                lineChartChart.initclear(Line_liquidlevel);
                lineChartChart.initclear(Line_pressure);
                lineChartChart.initclear(Line_tempreature);
                lineChartChart.LineChartDataSet(Line_liquidlevel,list1,"液位",list1, Color.rgb(255,255,255));
                lineChartChart.LineChartDataSet(Line_pressure,list1,"压力",list1,Color.rgb(255,255,255));
                lineChartChart.LineChartDataSet(Line_tempreature,list1,"温度",list1,Color.rgb(255,255,255));
            }
        });*/
    }

    @Override
    public void finishOffer(String info) {
        if(info!=null||info.equals("")==false){
            Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
            dialog.setVisibility(View.GONE);
        }
    }




    @Override
    protected void onStart() {
        super.onStart();
        presenter.showhistory("津A12345");
        presenter.getWarinig("浙N2312w");
        presenter.test();
        initOnclick();
        initDatePicker();

    }

    //提交数据
    @Override
    public void referFault() {


    }


    //时间选择点击事件
    @BindView(R.id.text_starttime)
    TextView text_starttime;
    @BindView(R.id.text_endtime)
    TextView text_endtime;

    @BindView(R.id.test1)
    TextView test1;
    @BindView(R.id.test2)
    TextView test2;
    @BindView(R.id.test3)
    TextView test3;
    private void initOnclick() {
        text_starttime.setOnClickListener(this);
        text_endtime.setOnClickListener(this);
        select_manage.setOnClickListener(this);
        initVis();
    }

    private void initVis() {
        test1.setVisibility(View.GONE);
        test2.setVisibility(View.GONE);
        test3.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text_starttime:
                starttimepicker.show(text_starttime.getText().toString());
                break;
            case R.id.text_endtime:
                endtimepicker.show(text_endtime.getText().toString());
                break;
            case R.id.select_mamage:
                presenter.checkdata(
                        text_starttime.getText().toString(),text_endtime.getText().toString(),"津A12345");
                break;
        }
    }

    //时间获取的具体操作
    String sDate,eDate;
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        text_starttime.setText(now/*.split(" ")[0]*/);
        text_endtime.setText(now/*.split(" ")[0]*/);

        starttimepicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                text_starttime.setText(time/*.split(" ")[0]*/);
            }
        }, "2010-01-01 00:00", now);
        starttimepicker.showSpecificTime(true);
        starttimepicker.setIsLoop(true);
        sDate=text_starttime.getText().toString();

        endtimepicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                text_endtime.setText(time/*.split(" ")[0]*/);
                eDate=text_endtime.getText().toString();
            }
        }, "2010-01-01 00:00", now);
        endtimepicker.showSpecificTime(true);
        endtimepicker.setIsLoop(true);
        eDate=text_endtime.getText().toString();
    }
}
