package com.example.a97569.dangerouschemicals.mvp.model.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 *******************************************************************************
 * @FileName:  OnlineInterceptor
 * @Package com.yidaoyun.newenergyvehicleandroid.http
 * @Description:  在线网络请求缓存拦截器设置
 * @author: lpz
 * @date:   2018/10/8  14:09
 * @version V1.0
 *******************************************************************************
 */

public class OnlineInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String requestCache=chain.request().header("Cache-Control");
        Response originResponse=chain.proceed(chain.request());
        String cacheControl=originResponse.header("Cache-Control");
        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            return originResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", requestCache)
                    .build();
        } else {
            return originResponse;
        }
    }
}
