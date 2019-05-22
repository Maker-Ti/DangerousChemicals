package com.example.a97569.dangerouschemicals.mvp.model.api;


import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 *******************************************************************************
 * @FileName:  ApiLogin
 * @Package com.yidaoyun.newenergyvehicleandroid.http
 * @Description:  用户模块网络请求类
 * @author: lpz
 * @date:   2018/10/8  14:04
 * @version V1.0
 *******************************************************************************
 */

public interface ApiDemo {

    @Headers("Cache-Control: public, max-age=60")
    @GET("api/userLogin")//登录
    Observable<JSONObject> login(@QueryMap Map<String, String> map);

    @GET("api/getWaitRescueList/142")//获取事件列表
    Observable<JSONObject> rescueList();

    @POST("bizServer/service/payment/order/create")
    Observable<JSONObject> createAliPayOrder(@Body JSONObject jsonObject);

    @GET("bizServer/service/payment/order/queryPayState?sysTradeNo={tradeNo}")
    Observable<JSONObject> getPayResult(@Path("tradeNo") String string);
}
