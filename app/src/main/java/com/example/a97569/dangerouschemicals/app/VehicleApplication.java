package com.example.a97569.dangerouschemicals.app;

import android.app.Application;

import com.example.a97569.dangerouschemicals.di.component.DBComponent;
import com.example.a97569.dangerouschemicals.di.component.DaggerDBComponent;
import com.example.a97569.dangerouschemicals.di.component.DaggerNetComponent;
import com.example.a97569.dangerouschemicals.di.component.DaggerSPComponent;
import com.example.a97569.dangerouschemicals.di.component.NetComponent;
import com.example.a97569.dangerouschemicals.di.component.SPComponent;
import com.example.a97569.dangerouschemicals.di.module.DBModule;
import com.example.a97569.dangerouschemicals.di.module.NetModule;
import com.example.a97569.dangerouschemicals.di.module.SPModule;
import com.example.a97569.dangerouschemicals.di.module.VehicleModule;

/**
 *******************************************************************************
 * @FileName:  VehicleApplication
 * @Package com.yidaoyun.newenergyvehicleandroid.base
 * @Description: 自定义Application类。由于全局的唯一性，故此处定义全局变量Component
 * @author: lpz
 * @date:   2018/10/8  14:01
 * @version V1.0
 *******************************************************************************
 */

public class VehicleApplication extends Application {
    private NetComponent netComponent;
    private SPComponent spComponent;
    private DBComponent dbComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        netComponent= DaggerNetComponent.builder()
                .vehicleModule(new VehicleModule(this))
                .netModule(new NetModule("http://47.101.141.93:8080/"))
                .build();
        spComponent= DaggerSPComponent.builder()
                .vehicleModule(new VehicleModule(this))
                .sPModule(new SPModule("vehicle"))
                .build();
        dbComponent= DaggerDBComponent.builder()
                .vehicleModule(new VehicleModule(this))
                .dBModule(new DBModule("vehicle.db"))
                .build();
    }

    public SPComponent getSpComponent() {
        return spComponent;
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public DBComponent getDbComponent() {
        return dbComponent;
    }
}