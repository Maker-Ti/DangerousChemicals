package com.example.a97569.dangerouschemicals.di.component;

import com.example.a97569.dangerouschemicals.di.module.DBModule;
import com.example.a97569.dangerouschemicals.di.module.VehicleModule;
import com.example.a97569.dangerouschemicals.mvp.model.db.DatabaseOpenHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DBModule.class,VehicleModule.class})
public interface DBComponent {
    DatabaseOpenHelper getOpenHelper();
}
