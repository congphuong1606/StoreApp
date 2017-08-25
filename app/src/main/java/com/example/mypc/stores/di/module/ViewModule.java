package com.example.mypc.stores.di.module;

import com.example.mypc.stores.ui.register.RegisView;
import com.example.mypc.stores.ui.storedetail.StoreDetailView;
import com.example.mypc.stores.ui.home.fragment.cmt.CmtFragmentView;
import com.example.mypc.stores.ui.home.fragment.newpost.NewPostView;
import com.example.mypc.stores.ui.home.HomeView;
import com.example.mypc.stores.ui.login.LoginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MyPC on 02/08/2017.
 */
@Module
public class ViewModule {
    HomeView homeView;
    NewPostView newPostView;
    CmtFragmentView cmtFragmentView;
    LoginView loginView;
    RegisView regisView;
    StoreDetailView storeDetailView;

    public ViewModule(RegisView regisView) {
        this.regisView = regisView;
    }

    public ViewModule(LoginView loginView) {
        this.loginView = loginView;
    }

    public ViewModule(CmtFragmentView cmtFragmentView) {
        this.cmtFragmentView = cmtFragmentView;
    }

    public ViewModule(NewPostView newPostView) {
        this.newPostView = newPostView;
    }

    public ViewModule(HomeView view) {
        this.homeView = view;
    }

    public ViewModule(StoreDetailView storeDetailView) {
        this.storeDetailView = storeDetailView;
    }

    @Provides
    public StoreDetailView getStoreDetailView() {
        return storeDetailView;
    }

    @Provides
    public RegisView getRegisView() {
        return regisView;
    }

    @Provides
    public LoginView getLoginView() {
        return loginView;
    }

    @Provides
    public NewPostView getNewPostView() {
        return newPostView;
    }

    @Provides
    public HomeView getHomeView() {
        return homeView;
    }

    @Provides
    public CmtFragmentView getCmtFragmentView() {
        return cmtFragmentView;
    }
}
