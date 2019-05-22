package com.example.a97569.dangerouschemicals.di.component;

import com.example.a97569.dangerouschemicals.di.module.History_ViewpagerModule;
import com.example.a97569.dangerouschemicals.di.scope.PerActivityScope;
import com.example.a97569.dangerouschemicals.mvp.view.activity.History_ViewpagerActivity;

import dagger.Component;

/**
 * Created by 97569 on 2018/12/1.
 */

@PerActivityScope
@Component(dependencies = NetComponent.class,modules = History_ViewpagerModule.class)
public interface History_ViewPagerComponent {

    void injectHistoryactivity(History_ViewpagerActivity viewpagerActivity);
}
