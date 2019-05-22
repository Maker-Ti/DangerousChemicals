package com.example.a97569.dangerouschemicals.di.component;

import com.example.a97569.dangerouschemicals.di.module.WarningModule;
import com.example.a97569.dangerouschemicals.di.scope.PerActivityScope;
import com.example.a97569.dangerouschemicals.mvp.view.activity.LoginActivity;

import dagger.Component;

/**
 * Created by Maker on 2018/12/14.
 */

@PerActivityScope
@Component(dependencies = NetComponent.class,modules = WarningModule.class)
public interface WarningComponent {
    void inject(LoginActivity warningActivity);
}
