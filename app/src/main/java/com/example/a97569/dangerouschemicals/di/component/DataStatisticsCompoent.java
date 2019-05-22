package com.example.a97569.dangerouschemicals.di.component;

import com.example.a97569.dangerouschemicals.di.module.DataStatisticsModule;
import com.example.a97569.dangerouschemicals.di.scope.PerActivityScope;
import com.example.a97569.dangerouschemicals.mvp.view.activity.DataStatisticsActivity;

import dagger.Component;

/**
 * Created by 97569 on 2018/12/20.
 */

@PerActivityScope
@Component(dependencies = NetComponent.class,modules = DataStatisticsModule.class)
public interface DataStatisticsCompoent {
    void datastatistics(DataStatisticsActivity dataStatisticsActivity);
}
