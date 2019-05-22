package com.example.a97569.dangerouschemicals.mvp.presenter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a97569.dangerouschemicals.R;
import com.example.a97569.dangerouschemicals.mvp.contract.History_ViewPagerContract;
import com.example.a97569.dangerouschemicals.mvp.model.api.ApiURL;
import com.example.a97569.dangerouschemicals.mvp.view.activity.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by 97569 on 2018/12/1.
 */

public class History_ViewpagerPresenter extends NetPresenter implements History_ViewPagerContract.info {
    History_ViewPagerContract.view view;
    ApiURL apiURL;

    @Inject
    public History_ViewpagerPresenter(History_ViewPagerContract.view view, ApiURL apiURL) {
        this.view = view;
        this.apiURL = apiURL;
    }
    public void ReferFault(){
        view.referFault();
    }

    //历史数据的解析并加入到List中
    @Override
    public void showhistory(String s) {
        apiURL.History_manage(s)
                .compose(applySchedulers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        List<String> liquideveltime=new ArrayList<>();
                        List<String> tempreaturetime=new ArrayList<>();
                        List<String> pressuretime=new ArrayList<>();
                        List<String> liquidevel=new ArrayList<>();
                        List<String> tempreature=new ArrayList<>();
                        List<String> pressure=new ArrayList<>();
                        JSONObject jsonObject;
                        try {
                            jsonObject=new JSONObject(responseBody.string());
                            String data=jsonObject.getString("data");
                            JSONArray jsonArray=new JSONArray(data);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONArray jsonArray1=jsonArray.getJSONArray(i);
                                if (i==0){
                                    for (int a=0;a<jsonArray1.length();a++){
                                        JSONObject jsonObject1=jsonArray1.getJSONObject(a);
                                        tempreature.add(jsonObject1.getString("temperature"));
                                        tempreaturetime.add(jsonObject1.getString("realTime"));
                                    }
                                }else if (i==1){
                                    for (int a=0;a<jsonArray1.length();a++){
                                        JSONObject jsonObject1=jsonArray1.getJSONObject(a);
                                        liquidevel.add(jsonObject1.getString("liquidLevel"));
                                        liquideveltime.add(jsonObject1.getString("realTime"));
                                    }
                                }else if (i==2){
                                    for (int a=0;a<jsonArray1.length();a++){
                                        JSONObject jsonObject1=jsonArray1.getJSONObject(a);
                                        pressure.add(jsonObject1.getString("pressure"));
                                        pressuretime.add(jsonObject1.getString("realTime"));
                                    }
                                }
                            }
                            view.showhistorymanage(liquideveltime,tempreaturetime,pressuretime,liquidevel,tempreature,pressure);

                        }catch (JSONException e) {
                            e.printStackTrace();
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


    @Override
    public void getWarinig(String plateNumber) {
        apiURL.GeiWarningList(plateNumber)
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
                                if(i==0){
                                    JSONObject jsonObjectnum = jsonArray.getJSONObject(i).getJSONObject("vehicle");
                                    int num = jsonObjectnum.getInt("num");
                                    UserInfo.numTime = num;
                                    Log.e("UserInfo",""+num);

                                }
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
                                    Log.e("historyMap",map.get("num"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        view.setWarningList(data1,data0);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void checkdata(String start, String end, String number) {
        apiURL.datahistorymanage(start,end,number)
                .compose(applySchedulers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        List<String> liquideveltime=new ArrayList<>();
                        List<String> tempreaturetime=new ArrayList<>();
                        List<String> pressuretime=new ArrayList<>();
                        List<String> liquidevel=new ArrayList<>();
                        List<String> tempreature=new ArrayList<>();
                        List<String> pressure=new ArrayList<>();
                        JSONObject jsonObject;
                        try {
                            jsonObject=new JSONObject(responseBody.string());
                            String data=jsonObject.getString("data");
                            JSONArray jsonArray=new JSONArray(data);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONArray jsonArray1=jsonArray.getJSONArray(i);
                                if (i==0){
                                    for (int a=0;a<jsonArray1.length();a++){
                                        JSONObject jsonObject1=jsonArray1.getJSONObject(a);
                                        tempreature.add(jsonObject1.getString("temperature"));
                                        tempreaturetime.add(jsonObject1.getString("realTime"));
                                    }
                                }else if (i==1){
                                    for (int a=0;a<jsonArray1.length();a++){
                                        JSONObject jsonObject1=jsonArray1.getJSONObject(a);
                                        liquidevel.add(jsonObject1.getString("liquidLevel"));
                                        liquideveltime.add(jsonObject1.getString("realTime"));
                                    }
                                }else if (i==2){
                                    for (int a=0;a<jsonArray1.length();a++){
                                        JSONObject jsonObject1=jsonArray1.getJSONObject(a);
                                        pressure.add(jsonObject1.getString("pressure"));
                                        pressuretime.add(jsonObject1.getString("realTime"));
                                    }
                                }
                            }
                            view.checkdata(liquideveltime,tempreaturetime,pressuretime,liquidevel,tempreature,pressure);
                        }catch (JSONException e) {
                            e.printStackTrace();
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

    @Override
    public void test() {
        view.viewtest();
    }

    @Override
    public void offerWarning(List<Map<String,String>> data1) {
        UserInfo.numTime = UserInfo.numTime+1;
        String offerNum = ""+UserInfo.numTime;
        for(int i=0;i<data1.size();i++){
            apiURL.setWarningStatus(offerNum,data1.get(i).get("ewID"),"2")
                    .compose(applySchedulers())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {

                            try {
                                String info = responseBody.string();
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
        String resaon = "";
        for(int j=0;j<data1.size();j++){
            if(j==0){
                resaon = resaon + data1.get(j).get("info");
            }else {
                resaon = resaon + ","+data1.get(j).get("info");
            }
        }

        apiURL.offerWarning(UserInfo.plateNumber,resaon,UserInfo.vehVIN,offerNum)
                .compose(applySchedulers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String rebody = responseBody.string();
                            Log.e("histroyOffer",rebody);
                            view.finishOffer(rebody);
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
