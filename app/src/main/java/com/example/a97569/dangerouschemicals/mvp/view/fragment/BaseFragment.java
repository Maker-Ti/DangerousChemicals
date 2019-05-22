package com.example.a97569.dangerouschemicals.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a97569.dangerouschemicals.app.VehicleApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *******************************************************************************
 * @FileName:  BaseFragment
 * @Package com.yidaoyun.newenergyvehicleandroid.base
 * @Description:  自定义Fragment基类
 * @author: lpz
 * @date:   2018/10/9  17:13
 * @version V1.0
 *******************************************************************************
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;//butterknife绑定器

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(setLayoutId(),container,false);
        unbinder=ButterKnife.bind(this,view);
        injectComponent((VehicleApplication) getContext().getApplicationContext());
        return view;
    }

    //设置页面视图布局id
    protected abstract int setLayoutId();

    protected abstract void injectComponent(VehicleApplication application);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
