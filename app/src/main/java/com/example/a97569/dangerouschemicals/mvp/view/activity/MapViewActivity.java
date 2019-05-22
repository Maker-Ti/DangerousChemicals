package com.example.a97569.dangerouschemicals.mvp.view.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.a97569.dangerouschemicals.R;
import com.example.a97569.dangerouschemicals.app.VehicleApplication;
import com.example.a97569.dangerouschemicals.di.component.DaggerMapViewComponent;
import com.example.a97569.dangerouschemicals.di.module.MapViewModule;
import com.example.a97569.dangerouschemicals.mvp.contract.MapViewContract;
import com.example.a97569.dangerouschemicals.mvp.presenter.MapViewPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by 97569 on 2018/11/17.
 */

public class MapViewActivity extends BaseActivity implements MapViewContract.view,View.OnClickListener {
    @BindView(R.id.ZDSwitch)
    Switch aSwitch;
    OverlayOptions overlayOptions;

    boolean intent_control = false;

    @BindView(R.id.control)
    TextView control_button;
    @BindView(R.id.content)
    RelativeLayout re_content;
    @BindView(R.id.intent_warning)
    RelativeLayout intent_warning;

    @BindView(R.id.above)
    TextView above;
    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.bottom)
    RelativeLayout bottom;
    @BindView(R.id.color)
    TextView color;
    @BindView(R.id.vawe)
    TextView vawe;
    @BindView(R.id.time)
    TextView num;
    @BindView(R.id.mainContent)
    TextView mainContent;
    @BindView(R.id.user)
    TextView user;
    @BindView(R.id.warningDialog)
    RelativeLayout warningDialog;
    boolean warningControl = false;
    SharedPreferences sharedPreferences;
    private ObjectAnimator scaleX ;
    private ObjectAnimator scaleY;
    boolean control_flag = true;
    boolean dialog_control = false;
    AlertDialog alertDialog;
    TextView name,plate,vehVIN;
    Button outLogin;
    Timer timer;
    TimerTask timerTask;
    int index = 0;
    int anim_time = 3000;



    List<LatLng> showmapicon=new ArrayList<>();

    @Inject
    MapViewPresenter mapViewPresenter;

    String PATH = "custom_config_dark.json";
    @Override
    public void setWarningStatus(List<Map<String, String>> dataOffer, List<Map<String, String>> dataUnOffer) {
        if(dataUnOffer.size()>0){
            if(dialog_control==false){
                warningDialog.setVisibility(View.VISIBLE);
                dialog_control = true;
            };
            String info = "";
            for(int i = 0;i<dataUnOffer.size();i++){
                if(i==0){
                    info = info+dataUnOffer.get(i).get("info");
                }else {
                    info = info+"、"+dataUnOffer.get(i).get("info");
                }

            }
            mainContent.setText(info);
            btn_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mapViewPresenter.setWarningStatus(dataUnOffer);
                    if(dialog_control==true){
                        warningDialog.setVisibility(View.GONE);
                        dialog_control = false;
                    }
                }
            });

        }

        if(dataOffer.size()>0){
            color.setBackgroundResource(R.drawable.red_cicle);
            above.setBackgroundResource(R.drawable.red_cicle);
            num.setText(""+dataOffer.size());
            type.setText("查看预警");
            warningControl = true;
            redAnim();
        }else {
            num.setText("0");
            buleAnim();
            color.setBackgroundResource(R.drawable.coner_round_bule_full);
            above.setBackgroundResource(R.drawable.coner_round_bule_full);
            type.setText("查看历史");
            warningControl = false;
        }
    }

    @Override
    public void successToast(String info) {
        Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();
    }


    Handler getLoadTimer = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mapViewPresenter.getWarning(UserInfo.plateNumber);
            index++;
            Log.e("viewActivty",""+index);
        }
    };

    private void redAnim() {
        scaleX = ObjectAnimator.ofFloat(bottom,"scaleX",1,1.1f,1).setDuration(1500);
        scaleY = ObjectAnimator.ofFloat(bottom,"scaleY",1,1.1f,1).setDuration(1500);
        scaleX.start();
        scaleY.start();

    }

    private void buleAnim(){
        scaleX = ObjectAnimator.ofFloat(bottom,"scaleX",1,1.05f,1).setDuration(2500);
        scaleY = ObjectAnimator.ofFloat(bottom,"scaleY",1,1.05f,1).setDuration(2500);
        scaleX.start();
        scaleY.start();
    }

    public void startTimer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getLoadTimer.sendEmptyMessage(0);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,0,anim_time);
    }
    public void endTimer(){
        timerTask = null;
        if(timer!=null){
            timer.cancel();
            timer = null;
        }

    }

    @Override
    protected void preLoad() {
        alertDialog = new AlertDialog.Builder(this).create();
        View dialog = LayoutInflater.from(this).inflate(R.layout.user_dialog,null);
        alertDialog.setView(dialog);
        name = dialog.findViewById(R.id.name);
        name.setText("用户名："+UserInfo.username);
        plate = dialog.findViewById(R.id.plate);
        plate.setText("车牌号："+UserInfo.plateNumber);
        vehVIN = dialog.findViewById(R.id.vehVIN);
        vehVIN.setText("车架号："+UserInfo.vehVIN);
        outLogin = dialog.findViewById(R.id.outLogin);
        outLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(MapViewActivity.this,LoginActivity.class));
                finish();
            }
        });

        sharedPreferences = getSharedPreferences("userinfo",MODE_PRIVATE);
        if (intent_control){
            intent_control = false;

            ObjectAnimator transX = ObjectAnimator.ofFloat(intent_warning,"scaleX",3,1).setDuration(800);
            ObjectAnimator transY = ObjectAnimator.ofFloat(intent_warning,"scaleY",3,1).setDuration(800);
            transX.start();
            transY.start();
            transY.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    loadWarningIntent();
                    type.setText("查看预警");
                    above.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }else {
            mapViewPresenter.LoadTimer();
        }
    }

    @Override
    protected void loadContent() {

        mapViewPresenter.LoadMainContet();
    }

    @Override
    protected void initload() {
        setMapCustomFile();
    }

    @Override
    protected void initSDK() {
        SDKInitializer.initialize(getApplicationContext());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.mapview;
    }

    @Override
    protected void injectComponent(VehicleApplication application) {
        DaggerMapViewComponent.builder()
                .mapViewModule(new MapViewModule(this))
                .netComponent(application.getNetComponent())
                .build()
                .inject(this);

    }

    //百度地图初始化
    @BindView(R.id.mapview)
    MapView mapView;
    BaiduMap baiduMap;
    private void initMapView() {
        baiduMap=mapView.getMap();
        LatLng cenpt = new LatLng(19.7316748777,110.0455223398); //设定中心点坐标
        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(cenpt)
                .zoom(5f)
                .build();  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态
        baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                MapView.setMapCustomEnable(true);
            }
        });
        mapView.showZoomControls(false);
    }


    //找到个性化地图地址
    public void setMapCustomFile() {
        FileOutputStream out = null;
        InputStream inputStream = null;
        String moduleName = null;
        try {
            inputStream =getAssets()
                    .open(PATH);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            moduleName = getFilesDir().getAbsolutePath();
            File f = new File(moduleName + "/" + PATH);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            out = new FileOutputStream(f);
            out.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MapView.setCustomMapStylePath(moduleName + "/" + PATH);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initMapView();
        initOnClick();
        mapViewPresenter.showMapViewContract();
        mapViewPresenter.loadCarManage(UserInfo.plateNumber);

    }

    private void initOnClick() {
        Statistics.setOnClickListener(this);
    }

    //两个按钮的点击缩起
    @Override
    public void loadInfoView(List<LatLng> latLngs) {


        //设置站点是否显示
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               try{
                   if (b){
                       List<OverlayOptions> options=new ArrayList<>();
                       BitmapDescriptor bitmapDescriptor= BitmapDescriptorFactory.fromResource(R.mipmap.locicon_blue3);
                       for (int i=0;i<latLngs.size();i++){
                           overlayOptions=new MarkerOptions()
                                   .position(latLngs.get(i)).icon(bitmapDescriptor);
                           options.add(overlayOptions);
                       }
                       baiduMap.addOverlays(options);
                   }else {
                       baiduMap.clear();
                       showmapicon(showmapicon);
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });
    }

    @BindView(R.id.plateNumber)
    TextView plateNumber;
    @BindView(R.id.motorCadeName)
    TextView motorCadeName;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.vehSpeed)
    TextView vehSpeed;
    @BindView(R.id.pressures)
    TextView pressures;
    @BindView(R.id.liquid)
    TextView liquid;
    @BindView(R.id.tem)
    TextView tem;
    @BindView(R.id.number_liquidLevel)
    TextView number_liquidLevel;
    @BindView(R.id.number_pressure)
    TextView number_pressure;
    @BindView(R.id.number_temperature)
    TextView number_temperature;
    @BindView(R.id.Statistics)
    TextView Statistics;
    //在文本上显示获取的数据
    @Override
    public void CarManage(Map<String, String> map,List<LatLng> latLngs) {
        showmapicon=latLngs;
        plateNumber.setText(map.get("plateNumber"));
        motorCadeName.setText(map.get("motorCadeName"));
        status.setText(map.get("status"));
        vehSpeed.setText(map.get("vehSpeed")+"KM/H");
        pressures.setText(map.get("pressures"));
        liquid.setText(map.get("liquid"));
        tem.setText(map.get("tem"));
        number_liquidLevel.setText(map.get("liquidLevel"));
        number_pressure.setText(map.get("pressure"));
        number_temperature.setText(map.get("temperature"));
        showmapicon(latLngs);
    }

    @Override
    public void startLoadTimer() {
        Log.e("viewActivty","loadThere");
        startTimer();
    }


    private void loadWarningIntent(){
        intent_warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_warning.setOnClickListener(null);
                intent_control = true;
                above.setVisibility(View.VISIBLE);
                ObjectAnimator transX = ObjectAnimator.ofFloat(intent_warning,"scaleX",1,3).setDuration(600);
                ObjectAnimator transY = ObjectAnimator.ofFloat(intent_warning,"scaleY",1,3).setDuration(600);
                transX.start();
                transY.start();
                transY.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Intent intent = new Intent(MapViewActivity.this,History_ViewpagerActivity.class);
                            intent.putExtra("flag",warningControl);


                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
    }

    @Override
    public void loadMainContent() {
        loadWarningIntent();
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
        control_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (control_flag) {
                    control_flag = false;
                    ObjectAnimator rotate_btn = ObjectAnimator.ofFloat(control_button, "rotation", 45, 0).setDuration(200);
                    ObjectAnimator trans_content = ObjectAnimator.ofFloat(re_content, "scaleY", 1f, 0).setDuration(200);
                    rotate_btn.setStartDelay(100);
                    rotate_btn.start();
                    // trans_content.start();
                    re_content.setVisibility(View.GONE);
                } else {
                    control_flag = true;
                    ObjectAnimator rotate_btn = ObjectAnimator.ofFloat(control_button, "rotation", 0, 45).setDuration(200);
                    rotate_btn.setStartDelay(100);
                    ObjectAnimator trans_content = ObjectAnimator.ofFloat(re_content, "scaleY", 0, 1).setDuration(200);
                    rotate_btn.start();
                    //  trans_content.start();
                    re_content.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    private void showmapicon(List<LatLng> latLngs) {
        List<OverlayOptions> options1=new ArrayList<>();
        BitmapDescriptor bitmapDescriptor= BitmapDescriptorFactory.fromResource(R.mipmap.locicon_red3);
        overlayOptions=new MarkerOptions()
                .position(latLngs.get(0)).icon(bitmapDescriptor);
        options1.add(overlayOptions);
        baiduMap.addOverlays(options1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Statistics:
                startActivity(new Intent(MapViewActivity.this,DataStatisticsActivity.class));
                break;
        }
    }
}
