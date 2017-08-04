package com.example.mypc.stores.di.component;

import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.home.HomeActivity;

import dagger.Subcomponent;

/**
 * Created by MyPC on 02/08/2017.
 */
@Subcomponent(modules = ViewModule.class)
public interface ViewComponent {
    void InjectTo(HomeActivity activity);
}
