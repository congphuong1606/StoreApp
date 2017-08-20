package com.example.mypc.stores.di.module;

import com.example.mypc.stores.network.ApiService;
import com.example.mypc.stores.network.ApiUtils;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MyPC on 05/08/2017.
 */
@Module
public class NetworkModule {
    @Provides
    public ApiService getApiService(){
        return ApiUtils.getIapiService();
    }
}
