package com.example.a97569.dangerouschemicals.mvp.model.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *******************************************************************************
 * @FileName:  OfflineInterceptor
 * @Package com.yidaoyun.newenergyvehicleandroid.http
 * @Description:  没有网络时网络缓存拦截器设置类,即3min内没有网络时从缓存中读取数据
 * @author: lpz
 * @date:   2018/10/8  14:09
 * @version V1.0
 *******************************************************************************
 */

public class OfflineInterceptor implements Interceptor {

    private Context context;
    public OfflineInterceptor(Context context){
        this.context=context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        boolean isConnected=mNetworkInfo!=null&&mNetworkInfo.isConnectedOrConnecting();
        if (!isConnected) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached,max-stale=180")
                    .build();
        }
        return chain.proceed(request);
    }
}
