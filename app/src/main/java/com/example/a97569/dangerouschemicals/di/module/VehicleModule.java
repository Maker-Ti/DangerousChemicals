package com.example.a97569.dangerouschemicals.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *******************************************************************************
 * @FileName:  VehicleModule
 * @Package com.yidaoyun.newenergyvehicleandroid.module
 * @Description:  项目全局实例Context生产类
 * @author: lpz
 * @date:   2018/10/8  14:14
 * @version V1.0
 *******************************************************************************
 */
@Module
public class VehicleModule {

    private Context context;

    public VehicleModule(Context context){
        this.context=context;
    }

    @Singleton
    @Provides
    Context provideContext(){
        return context;
    }
}
