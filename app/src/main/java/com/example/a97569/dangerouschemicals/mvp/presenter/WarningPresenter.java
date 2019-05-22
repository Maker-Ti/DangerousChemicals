package com.example.a97569.dangerouschemicals.mvp.presenter;

import android.util.Log;

import com.example.a97569.dangerouschemicals.mvp.contract.WarningContract;
import com.example.a97569.dangerouschemicals.mvp.model.api.ApiWarning;
import com.example.a97569.dangerouschemicals.mvp.view.activity.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by Maker on 2018/12/14.
 */

public class WarningPresenter extends NetPresenter implements WarningContract.info {
    WarningContract.view warningContract;
    ApiWarning apiWarning;

    @Inject
    public WarningPresenter(WarningContract.view warningContract,ApiWarning apiWarning){
        this.apiWarning = apiWarning;
        this.warningContract = warningContract;
    }

    @Override
    public void loginConnect(String username, String password) {
        apiWarning.getLogin(username,password)
                .compose(applySchedulers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String info = responseBody.string();
                            JSONObject jsonObject = new JSONObject(info);
                            String vehVIN = jsonObject.getString("vehVIN");
                            UserInfo.vehVIN = vehVIN;
                            JSONObject jsonObject1 = jsonObject.getJSONObject("vehicle");
                            String plateNumber = jsonObject1.getString("plateNumber");
                            UserInfo.plateNumber = plateNumber;
                            Log.e("loginPresnter",plateNumber+vehVIN);
                            warningContract.loginSuccess(vehVIN,plateNumber);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        warningContract.loginFalt();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
