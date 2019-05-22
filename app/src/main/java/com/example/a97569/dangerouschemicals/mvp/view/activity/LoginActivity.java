package com.example.a97569.dangerouschemicals.mvp.view.activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a97569.dangerouschemicals.R;
import com.example.a97569.dangerouschemicals.app.VehicleApplication;
import com.example.a97569.dangerouschemicals.di.component.DaggerWarningComponent;
import com.example.a97569.dangerouschemicals.di.module.WarningModule;
import com.example.a97569.dangerouschemicals.mvp.contract.WarningContract;
import com.example.a97569.dangerouschemicals.mvp.presenter.WarningPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Maker on 2018/12/14.
 */

public class LoginActivity extends BaseActivity implements WarningContract.view{
    @BindView(R.id.ed_password)
     EditText ed_pwd;
    @BindView(R.id.ed_username)
     EditText ed_user;
    @BindView(R.id.clear_txt)
     TextView tv_clear;
    @BindView(R.id.eye_txt)
     TextView     tv_eye;

    boolean eye_tv_check = false;
    @BindView(R.id.login_btn)
     Button login_btn;

    TextView loading_icon;
    @Inject
    WarningPresenter warningPresenter;

    AlertDialog alertDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void preLoad() {
        //加载dialog
        alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        View dialog = LayoutInflater.from(this).inflate(R.layout.login_loading,null);
        alertDialog.setView(dialog);
        alertDialog.setCanceledOnTouchOutside(false);
       loading_icon = dialog.findViewById(R.id.loading);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(loading_icon,"rotation",0,360).setDuration(1000);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.start();

        //判断登录状态
        sharedPreferences = getSharedPreferences("userinfo",MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("flag",false);
        if(isLogin == true){
            UserInfo.plateNumber = sharedPreferences.getString("plateNumber","浙N2312w");
            UserInfo.vehVIN =sharedPreferences.getString("vehVIN","QASDF0878IUY65433");
            UserInfo.username =sharedPreferences.getString("username","name");
   //         Toast.makeText(this, sharedPreferences.getString("username","name"), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,MapViewActivity.class));

            finish();
        }
    }

    @Override
    protected void loadContent() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_user.getText().toString();
                String password = ed_pwd.getText().toString();
                if(username.equals("Controller")&&password.equals("408")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("flag",true);
                    editor.putString("vehVIN",UserInfo.vehVIN);
                    editor.putString("plateNumber",UserInfo.plateNumber);
                    editor.putString("username",ed_user.getText().toString());
                    editor.commit();
                    UserInfo.username = ed_user.getText().toString();
                    startActivity(new Intent(LoginActivity.this,MapViewActivity.class));
                    finish();
                }else {
                    alertDialog.show();
                    warningPresenter.loginConnect(username,password);
                }

            }
        });
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_user.setText("");
            }
        });
        tv_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eye_tv_check == false){
                    eye_tv_check = true;
                    tv_eye.setBackgroundResource(R.mipmap.eye_open);
                    ed_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    eye_tv_check = false;
                    tv_eye.setBackgroundResource(R.mipmap.eye_close);
                    ed_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        ed_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("txt",s.toString());
                if(s.length()==0){
                    tv_eye.setVisibility(View.GONE);
                }else {
                    tv_eye.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("txt",s.toString());
                if(s.length()==0){
                    tv_clear.setVisibility(View.GONE);
                }else {
                    tv_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initload() {

    }

    @Override
    protected void initSDK() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.login;
    }

    @Override
    protected void injectComponent(VehicleApplication application) {
        DaggerWarningComponent.builder()
                .warningModule(new WarningModule(this))
                .netComponent(application.getNetComponent())
                .build()
                .inject(this);
    }

    @Override
    public void loginSuccess(String vehVIN,String plateNumber) {
        alertDialog.dismiss();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("flag",true);
        editor.putString("vehVIN",vehVIN);
        editor.putString("plateNumber",plateNumber);
        editor.putString("username",ed_user.getText().toString());
        editor.commit();
        startActivity(new Intent(LoginActivity.this,MapViewActivity.class));
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void loginFalt() {
        alertDialog.dismiss();
        Toast.makeText(this, "登录失败，请检查登录信息", Toast.LENGTH_SHORT).show();
    }
}
