package com.example.a97569.dangerouschemicals.di.module;

import android.content.Context;

import com.example.a97569.dangerouschemicals.mvp.model.db.DatabaseOpenHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    private String name;

    public DBModule(String name){
        this.name=name;
    }

    @Singleton
    @Provides
    DatabaseOpenHelper provideOpenHelper(Context context){
        return new DatabaseOpenHelper(context,name);
    }
}
