package com.example.a97569.dangerouschemicals.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.baidu.mapapi.map.MapView;
import com.example.a97569.dangerouschemicals.app.VehicleApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *******************************************************************************
 * @FileName:  BaseActivity<p/>
 * @Package com.yidaoyun.newenergyvehicleandroid.base<p/>
 * @Description:  页面视图Activity基类<p/>
 * @author: lpz<p/>
 * @date:   2018/10/8  13:57 <p/>
 * @version V1.0 <p/>
 *******************************************************************************
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;//butterknife绑定器

    @Override
    protected void onResume() {
        super.onResume();
        preLoad();
    }

    protected abstract void preLoad();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSDK();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        initload();
        setContentView(setLayoutId());
        unbinder=ButterKnife.bind(this);
        injectComponent((VehicleApplication) getApplication());
        loadContent();
    }

    protected abstract void loadContent();

    protected abstract void initload();

    protected abstract void initSDK();

    //设置页面布局id
    protected abstract int setLayoutId();

    //将全局实例注入到各activity中
    protected abstract void injectComponent(VehicleApplication application);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder!=null)unbinder.unbind();
    }
}
