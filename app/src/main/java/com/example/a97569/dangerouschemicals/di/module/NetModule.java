package com.example.a97569.dangerouschemicals.di.module;

import android.content.Context;
import android.os.Environment;

import com.example.a97569.dangerouschemicals.mvp.model.api.JsonConverterFactory;
import com.example.a97569.dangerouschemicals.mvp.model.api.LoggingInterceptor;
import com.example.a97569.dangerouschemicals.mvp.model.api.OnlineInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 *******************************************************************************
 * @FileName:  NetModule
 * @Package com.yidaoyun.newenergyvehicleandroid.di.module
 * @Description:  网络类实例对象提供类
 * @author: lpz
 * @date:   2018/10/12  11:21
 * @version V1.0
 *******************************************************************************
 */
@Module
public class NetModule {

    private String baseUrl;

    public NetModule(String baseUrl){
        this.baseUrl=baseUrl;
    }

    @Singleton
    @Provides
    Cache provideCache(Context context){
        File tmpFile= context.getExternalCacheDir();
        if (tmpFile==null) tmpFile=new File(Environment.getExternalStorageDirectory(),"vehicle");
        return new Cache(tmpFile,100*1024*1024);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(Cache cache){
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .cache(cache)
                //.addInterceptor(new OfflineInterceptor(application))
                .addNetworkInterceptor(new OnlineInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .build();
    }

    //正式版地址
    @Singleton
    @Provides
    Retrofit providesRetrofitRelease(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new JsonConverterFactory())
                .client(client)
                .build();
    }

    /*测试版地址
    @Singleton
    @Provides
    Retrofit providesRetrofitTmp(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }*/
}
