package com.example.a97569.dangerouschemicals.mvp.presenter;

import android.util.Log;

import com.example.a97569.dangerouschemicals.di.component.DataStatisticsCompoent;
import com.example.a97569.dangerouschemicals.di.component.NetComponent;
import com.example.a97569.dangerouschemicals.mvp.contract.DataStatisticsContrat;
import com.example.a97569.dangerouschemicals.mvp.model.api.ApiURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by 97569 on 2018/12/20.
 */

public class DataStatisticsPresenter extends NetPresenter implements DataStatisticsContrat.info {

    ApiURL apiURL;
    DataStatisticsContrat.view view;

    @Inject
    public DataStatisticsPresenter(ApiURL apiURL, DataStatisticsContrat.view view) {
        this.apiURL = apiURL;
        this.view = view;
    }

    @Override
    public void showmanage(String s) {
        apiURL.dataStatistics(s)
                .compose(applySchedulers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            JSONObject jsonObject=new JSONObject(responseBody.string());
                            String data=jsonObject.getString("data");
                            JSONArray jsonArray=new JSONArray(data);
                            String warning = null;
                            List<String> listnumber=new ArrayList<>();
                            List<String> listwarning=new ArrayList<>();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONArray jsonArray1=jsonArray.getJSONArray(i);
                                int number=jsonArray1.getJSONObject(0).getInt("number");
                                int warningFlag=jsonArray1.getJSONObject(0).getInt("warningFlag");
                                if (warningFlag==1){
                                    warning="温度";
                                }else if (warningFlag==2){
                                    warning="压力";
                                }else if (warningFlag==3){
                                    warning="液位";
                                }else if (warningFlag==4){
                                    warning="速度";
                                }else if (warningFlag==5){
                                    warning="疲劳报警";
                                }

                                Log.d("test", String.valueOf(number));
                                listnumber.add(String.valueOf(number));
                                listwarning.add(warning);
                            }
                            view.showdatamanage(listnumber,listwarning);
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
    public void showsubmitted(String s) {
        apiURL.hasbeensubmit(s)
                .compose(applySchedulers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            JSONObject jsonObject=new JSONObject(responseBody.string());
                            String data=jsonObject.getString("data");
                            JSONArray jsonArray=new JSONArray(data);
                            List<String> number=new ArrayList<>();
                            List<String> name=new ArrayList<>();
                            String flag=null;
                            for (int i=0;i<jsonArray.length();i++){
                                JSONArray jsonArray1=jsonArray.getJSONArray(i);
                                String txtnumber = jsonArray1.getJSONObject(0).getString("number");
                                int txtflag = jsonArray1.getJSONObject(0).getInt("flag");
                                if (txtflag==0){
                                    flag="未解决";
                                }else if (txtflag==1){
                                    flag="自行解决";
                                }else if (txtflag==2){
                                    flag="已解决";
                                }
                                number.add(txtnumber);
                                name.add(flag);
                            }
                            view.showdatasubmitted(number,name);
                        } catch (JSONException e) {
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

}
