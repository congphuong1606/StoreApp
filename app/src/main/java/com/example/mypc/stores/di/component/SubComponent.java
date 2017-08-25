package com.example.mypc.stores.di.component;

import com.example.mypc.stores.di.module.StorageModule;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.Register.RegisActivity;
import com.example.mypc.stores.ui.StoreDetail.StoreDetailActivity;
import com.example.mypc.stores.ui.home.fragment.cmt.CmtFragment;
import com.example.mypc.stores.ui.home.fragment.newpost.NewPostFragment;
import com.example.mypc.stores.ui.home.HomeActivity;
import com.example.mypc.stores.ui.login.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by MyPC on 02/08/2017.
 */
@Subcomponent(modules = {ViewModule.class, StorageModule.class})
public interface SubComponent {
    void injectTo(HomeActivity activity);
    void injectTo(NewPostFragment newPostFragment);
    void injectTo(CmtFragment cmtFragment);
    void injectTo(LoginActivity loginActivity);
    void injectTo(RegisActivity regisActivity);
    void injectTo(StoreDetailActivity storeDetailActivity);
}
