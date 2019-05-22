package com.example.a97569.dangerouschemicals.di.module;

import com.example.a97569.dangerouschemicals.di.scope.PerActivityScope;
import com.example.a97569.dangerouschemicals.mvp.contract.MapViewContract;
import com.example.a97569.dangerouschemicals.mvp.model.api.ApiURL;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by 97569 on 2018/11/17.
 */
@Module
public class MapViewModule {
    MapViewContract.view mapViewContract;
    public MapViewModule(MapViewContract.view mapViewContract) {
        this.mapViewContract = mapViewContract;
    }

    @PerActivityScope
    @Provides
    MapViewContract.view provideMapView(){
        return mapViewContract;
    }

    @PerActivityScope
    @Provides
    ApiURL provoideMapViewUser(Retrofit retrofit){
        return retrofit.create(ApiURL.class);
    }
}
