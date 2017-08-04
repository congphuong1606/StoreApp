package com.example.mypc.stores.di.component;

import com.example.mypc.stores.di.module.AppModule;
import com.example.mypc.stores.di.module.ViewModule;

import dagger.Component;
import dagger.Module;

/**
 * Created by MyPC on 02/08/2017.
 */
@Component (modules = AppModule.class)
public interface AppComponent {
    ViewComponent plus(ViewModule viewModule);
}
