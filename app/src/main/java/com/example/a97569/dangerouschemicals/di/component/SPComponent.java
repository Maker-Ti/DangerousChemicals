package com.example.a97569.dangerouschemicals.di.component;

import android.content.SharedPreferences;

import com.example.a97569.dangerouschemicals.di.module.SPModule;
import com.example.a97569.dangerouschemicals.di.module.VehicleModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 *******************************************************************************
 * @FileName:  SPComponent
 * @Package com.yidaoyun.newenergyvehicleandroid.di.component
 * @Description:  SharedPreference实例注入类
 * @author: lpz
 * @date:   2018/10/15  17:27
 * @version V1.0
 *******************************************************************************
 */
@Singleton
@Component(modules = {VehicleModule.class,SPModule.class})
public interface SPComponent {
    SharedPreferences getSharedPreference();
}
