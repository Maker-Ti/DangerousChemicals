package com.example.a97569.dangerouschemicals.di.module;

import com.example.a97569.dangerouschemicals.di.scope.PerActivityScope;
import com.example.a97569.dangerouschemicals.mvp.contract.DataStatisticsContrat;
import com.example.a97569.dangerouschemicals.mvp.model.api.ApiURL;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by 97569 on 2018/12/20.
 */

@Module
public class DataStatisticsModule {
    DataStatisticsContrat.view dataStatistics;

    public DataStatisticsModule(DataStatisticsContrat.view dataStatistics) {
        this.dataStatistics = dataStatistics;
    }

    @PerActivityScope
    @Provides
    DataStatisticsContrat.view providesdata(){
        return dataStatistics;
    }

    @PerActivityScope
    @Provides
    ApiURL providedata(Retrofit retrofit){
        return retrofit.create(ApiURL.class);
    }
}
