package com.example.a97569.dangerouschemicals.di.component;

import com.example.a97569.dangerouschemicals.di.module.NetModule;
import com.example.a97569.dangerouschemicals.di.module.VehicleModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 *******************************************************************************
 * @FileName:  NetComponent
 * @Package com.yidaoyun.newenergyvehicleandroid.di.component
 * @Description:  网络实例输入类
 * @author: lpz
 * @date:   2018/10/12  11:28
 * @version V1.0
 *******************************************************************************
 */
@Singleton
@Component(modules = {VehicleModule.class,NetModule.class})
public interface NetComponent {
    Retrofit getRetrofit();
}