package com.example.a97569.dangerouschemicals.di.module;

import com.example.a97569.dangerouschemicals.di.scope.PerActivityScope;
import com.example.a97569.dangerouschemicals.mvp.contract.History_ViewPagerContract;
import com.example.a97569.dangerouschemicals.mvp.model.api.ApiURL;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by 97569 on 2018/12/1.
 */

@Module
public class History_ViewpagerModule {
    History_ViewPagerContract.view viewpagerContract;

    public History_ViewpagerModule(History_ViewPagerContract.view contract) {
        this.viewpagerContract = contract;
    }

    @PerActivityScope
    @Provides
    History_ViewPagerContract.view providesHistory(){
        return viewpagerContract;
    }

    @PerActivityScope
    @Provides
    ApiURL provideHistory(Retrofit retrofit){
        return retrofit.create(ApiURL.class);
    }
}
