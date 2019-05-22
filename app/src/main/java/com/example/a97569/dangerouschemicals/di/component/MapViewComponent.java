package com.example.a97569.dangerouschemicals.di.component;

import com.example.a97569.dangerouschemicals.di.module.MapViewModule;
import com.example.a97569.dangerouschemicals.di.scope.PerActivityScope;
import com.example.a97569.dangerouschemicals.mvp.view.activity.MapViewActivity;

import dagger.Component;

/**
 * Created by 97569 on 2018/11/17.
 */

@PerActivityScope
@Component(dependencies = NetComponent.class,modules = MapViewModule.class)
public interface MapViewComponent {
    void inject(MapViewActivity mapViewActivity);
}
