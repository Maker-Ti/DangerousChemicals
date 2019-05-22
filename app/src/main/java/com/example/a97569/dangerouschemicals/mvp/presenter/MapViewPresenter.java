package com.example.a97569.dangerouschemicals.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.a97569.dangerouschemicals.mvp.contract.MapViewContract;
import com.example.a97569.dangerouschemicals.mvp.model.api.ApiURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by 97569 on 2018/11/17.
 */

public class MapViewPresenter extends NetPresenter implements MapViewContract.info{
    MapViewContract.view mapViewContract;
    ApiURL apiURL;

    @Inject
    public MapViewPresenter(MapViewContract.view mapViewContract, ApiURL apiURL) {
        this.mapViewContract = mapViewContract;
        this.apiURL = apiURL;
    }
    public void LoadTimer(){
        mapViewContract.startLoadTimer();
    }
    public void LoadMainContet(){
        mapViewContract.loadMainContent();
    }
    @Override
    public void loadCarManage(String vehVIN) {
        apiURL.CarManagerInfo(vehVIN).compose(applySchedulers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("lip","onSubscribe:"+d.toString());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Map<String,String> map=new HashMap<>();
                        List<LatLng> latLngs=new ArrayList<>();
                        JSONObject jsonObject1;
                        try {
                            JSONObject jsonObject=new JSONObject(responseBody.string());
                            Log.d("lip",responseBody.string());
                            String data = jsonObject.getString("data");
                            JSONArray jData = new JSONArray(data);
                            //车辆速度
                            map.put("vehSpeed",jData.getJSONObject(0).getString("vehSpeed"));
                            //获取时间
                            map.put("realTime",jData.getJSONObject(0).getString("realTime"));
                            //危化品压力
                            jsonObject1=new JSONObject(jData.getJSONObject(0).getString("vehDanChemPressure"));
                            map.put("pressures",jsonObject1.getString("pressures"));
                            map.put("pressure",jsonObject1.getString("pressure"));
                            //温度
                            jsonObject1=new JSONObject(jData.getJSONObject(0).getString("vehDanChemTems"));
                            map.put("tem",jsonObject1.getString("tem"));
                            map.put("temperature",jsonObject1.getString("temperature"));
                            //车架号
                            map.put("vehVIN",jData.getJSONObject(0).getString("vehVIN"));
                            //经纬度
                            jsonObject1=new JSONObject(jData.getJSONObject(0).getString("vehPosition"));
                            latLngs.add(new LatLng(jsonObject1.getDouble("longitude"),jsonObject1.getDouble("latitude")));
                            //车队名称
                            jsonObject1=new JSONObject(jData.getJSONObject(0).getString("motorCade"));
                            map.put("motorCadeName",jsonObject1.getString("motorCadeName"));
                            //液位
                            jsonObject1=new JSONObject(jData.getJSONObject(0).getString("vehDanChemLiquidLevel"));
                            map.put("liquid",jsonObject1.getString("liquid"));
                            map.put("liquidLevel",jsonObject1.getString("liquidLevel"));
                            //驾驶员状态
                            jsonObject1=new JSONObject(jData.getJSONObject(0).getString("drStatus"));
                            map.put("status",jsonObject1.getString("status"));
                            //车牌号
                            jsonObject1=new JSONObject(jData.getJSONObject(0).getString("vehicle"));
                            map.put("plateNumber",jsonObject1.getString("plateNumber"));
                            mapViewContract.CarManage(map,latLngs);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getWarning(String s) {
        apiURL.GeiWarningList(s)
                .compose(applySchedulers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("historyMap","there");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        List<Map<String,String>> data0 = new ArrayList<>();
                        List<Map<String,String>> data1 = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(responseBody.string());
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.e("historyMap",jsonArray.getString(i));
                                String solve = jsonObject.getString("solve");
                                Log.e("historyMap",solve);
                                String info = jsonObject.getString("info");
                                String ewID = jsonObject.getString("ewID");
                                if(solve.equals("1")){
                                    Map<String,String> map = new HashMap<>();
                                    map.put("num",""+(data1.size()+1));
                                    map.put("info",info);
                                    map.put("ewID",ewID);
                                    data1.add(map);
                                    Log.e("historyMap",map.get("num"));
                                }else if(solve.equals("0")){
                                    Map<String,String> map = new HashMap<>();
                                    map.put("num",""+(data0.size()+1));
                                    map.put("info",info);
                                    map.put("ewID",ewID);
                                    data0.add(map);
                                    Log.e("historyMap1",map.get("num"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mapViewContract.setWarningStatus(data1,data0);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void setWarningStatus(List<Map<String, String>> dataUnOffer) {
        int radomNum= (int) (Math.random()*10);
        Log.e("mapPresenter",""+dataUnOffer.size());
        for(int i=0;i<dataUnOffer.size();i++){
            apiURL.setWarningStatus("0",dataUnOffer.get(i).get("ewID"),"1")
                    .compose(applySchedulers())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {

                            try {
                                String info = responseBody.string();
                                mapViewContract.successToast(info);
                              //  Log.e("mapViewINfo",info);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    public void showMapViewContract() {
        apiURL.MapViewMessage()
                .compose(applySchedulers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        List<LatLng> options =new ArrayList<>();
                        mapViewContract.loadInfoView(options);
                    }
                    @Override
                    public void onNext(ResponseBody jsonObject) {
                        List<LatLng> options =new ArrayList<>();
                        //条件筛选
                       /* try {
                            JSONObject jsonObjectall=new JSONObject(jsonObject.string());
                            String data=jsonObjectall.getString("data");
                            JSONArray jsonArray=new JSONArray(data);
                            Log.d("daike", jsonArray.getJSONObject(0).toString());
                            for (int i=0;i<jsonArray.length();i++){
                                String msg=jsonArray.getJSONObject(i).toString();
                                String msg1=jsonArray.getJSONObject(i).getString("siteName");
                                if (msg1.equals("苏州站")) {
                                    JSONObject jsonObject1 = new JSONObject(msg);
                                    String remarks = jsonObject1.getString("remarks");
                                    Double longitude = jsonObject1.getDouble("longitude");
                                    Double latitude = jsonObject1.getDouble("latitude");
                                    String siteID = jsonObject1.getString("siteID");
                                    String siteNumber = jsonObject1.getString("siteNumber");
                                    String siteName = jsonObject1.getString("siteName");
                                    options.add(new LatLng(longitude, latitude));
                                }
                            }
                        } catch (Exception e) {
                            onError(e);
                        }*/

                        try {
                            JSONObject jsonObjectall=new JSONObject(jsonObject.string());
                            String data=jsonObjectall.getString("data");
                            JSONArray jsonArray=new JSONArray(data);
                            Log.d("daike", jsonArray.getJSONObject(0).toString());
                            for (int i=0;i<jsonArray.length();i++){
                                String msg=jsonArray.getJSONObject(i).toString();
                                JSONObject jsonObject1 = new JSONObject(msg);
                                String remarks = jsonObject1.getString("remarks");
                                Double longitude = jsonObject1.getDouble("longitude");
                                Double latitude = jsonObject1.getDouble("latitude");
                                String siteID = jsonObject1.getString("siteID");
                                String siteNumber = jsonObject1.getString("siteNumber");
                                String siteName = jsonObject1.getString("siteName");
                                options.add(new LatLng(longitude, latitude));
                            }
                        } catch (Exception e) {
                            onError(e);
                        }
                        mapViewContract.loadInfoView(options);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
