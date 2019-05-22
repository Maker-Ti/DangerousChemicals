package com.example.a97569.dangerouschemicals.mvp.model.api;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 *******************************************************************************
 * @FileName:  LoggingInterceptor
 * @Package com.yidaoyun.newenergyvehicleandroid.http
 * @Description:  网络请求日志打印类
 * @author: lpz
 * @date:   2018/10/8  14:08
 * @version V1.0
 *******************************************************************************
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originRequest=chain.request();
        long t1=System.nanoTime();
        Log.d("daike：",String.format(Locale.CHINESE,"sending request %s on %s%n%s",originRequest.url(),chain.connection(),originRequest.headers()));
        Response response=chain.proceed(originRequest);
        long t2=System.nanoTime();
        Log.d("daike：",String.format(Locale.CHINESE,"receive for %s in %.1fms%n%s",response.request().url(),(t2-t1)/1e6d,response.headers()));
        return response;
    }
}
