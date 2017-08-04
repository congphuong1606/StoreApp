package com.example.mypc.stores;

import android.app.Application;

import com.example.mypc.stores.di.component.AppComponent;
import com.example.mypc.stores.di.component.DaggerAppComponent;
import com.example.mypc.stores.di.module.AppModule;

/**
 * Created by MyPC on 02/08/2017.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        appComponent= DaggerAppComponent.builder().appModule(new AppModule(this))
                .build();



    }
    public AppComponent getAppComponent(){
        return appComponent;
    }

    public static synchronized MyApplication get() {
        return myApplication;
    }
}
