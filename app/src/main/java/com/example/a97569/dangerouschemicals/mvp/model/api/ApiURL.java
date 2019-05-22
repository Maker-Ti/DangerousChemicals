package com.example.a97569.dangerouschemicals.mvp.model.api;


import com.baidu.mapapi.map.OverlayOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 *******************************************************************************
 * @FileName:  ApiURL
 * @Package com.yidaoyun.newenergyvehicleandroid.http
 * @Description:  用户模块网络请求类
 * @author: lpz
 * @date:   2018/10/8  14:04
 * @version V1.0
 *******************************************************************************
 */

public interface ApiURL {
    @Headers("Cache-Control: public, max-age=0")
    @GET("dcam/station/getAllStoreSite")//获取所有站点的位置信息
    Observable<ResponseBody> MapViewMessage();

    @Headers("Cache-Control: public, max-age=0")
    @GET("dcam/vehinfo/getVehByplateNumber?")//根据车牌获取某一辆车的所有信息
    Observable<ResponseBody> CarManagerInfo(@Query("plateNumber") String string);

    @Headers("Cache-Control: public, max-age=0")
    @GET("dcam/vehinfo/getHistoryByNumber?")//获取危化品历史信息
    Observable<ResponseBody> History_manage(@Query("plateNumber") String s);

    @Headers("Cache-Control: public, max-age=0")
    @GET("dcam/vehinfo/getEWarningBypNumber?")//根据车牌获取未处理的预警列表
    Observable<ResponseBody> GeiWarningList(@Query("plateNumber") String s);

    @Headers("Cache-Control: public, max-age=0")
    @GET("dcam/vehinfo/getHistoryBypNumberApi?")//根据时间来查询危化品的历史信息
    Observable<ResponseBody> datahistorymanage(@Query("startTime") String start,
                                               @Query("endTime") String end,
                                               @Query("plateNumber") String number);
    @Headers("Cache-Control: public, max-age=0")
    @GET("dcam/ew/updatesummit?")//更新预警表
    Observable<ResponseBody> setWarningStatus(@Query("warnId") String warnId,
                                              @Query("ewID") String ewID,
                                              @Query("flag") String flag);
    @Headers("Cache-Control: public, max-age=0")
    @GET("dcam/vti/addvti?")//更新故障表
    Observable<ResponseBody> offerWarning(@Query("plateNumber") String plateNumber,
                                              @Query("info") String info,
                                              @Query("vehVIN") String vehVIN,
                                              @Query("warnId") String warnId);


    @Headers("Cache-Control: public, max-age=0")
    @GET("dcam/ew/getWarningTypeApi?")//根据车辆查询故障统计
    Observable<ResponseBody> dataStatistics(@Query("plateNumber")String s);

    @Headers("Cache-Control: public, max-age=0")
    @GET("dcam/ew/getResolveWarningApi?")//根据车辆查询提交次数
    Observable<ResponseBody> hasbeensubmit(@Query("plateNumber")String s);

}
