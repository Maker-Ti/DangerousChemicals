package com.example.a97569.dangerouschemicals.di.module;

import com.example.a97569.dangerouschemicals.di.scope.PerActivityScope;
import com.example.a97569.dangerouschemicals.mvp.contract.WarningContract;
import com.example.a97569.dangerouschemicals.mvp.model.api.ApiWarning;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Maker on 2018/12/14.
 */


@Module
public class WarningModule  {
    WarningContract.view warningContract;
    public WarningModule(WarningContract.view warningContract){
        this.warningContract = warningContract;
    }
    @PerActivityScope
    @Provides
    WarningContract.view provideView(){
        return warningContract;
    }

    @PerActivityScope
    @Provides
    ApiWarning provideAPI(Retrofit retrofit){
        return retrofit.create(ApiWarning.class);
    }
}
