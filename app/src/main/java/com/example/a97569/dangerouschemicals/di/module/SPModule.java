package com.example.a97569.dangerouschemicals.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *******************************************************************************
 * @FileName:  SPModule
 * @Package com.yidaoyun.newenergyvehicleandroid.di.module
 * @Description:  SharedPreference缓存实例提供类
 * @author: lpz
 * @date:   2018/10/15  17:22
 * @version V1.0
 *******************************************************************************
 */
@Module
public class SPModule {

    private String name;

    public SPModule(String name){
        this.name=name;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreference(Context context){
        return context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }
}
