package com.example.mypc.stores.di.module;

import com.example.mypc.stores.ui.home.HomeView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MyPC on 02/08/2017.
 */
@Module
public class ViewModule {
    HomeView homeView;

    public ViewModule(HomeView view){
        this.homeView =view;
    }

    @Provides
    public HomeView getHomeView() {
        return homeView;
    }
}
