package com.example.mypc.stores.di.module;

import com.example.mypc.stores.ui.main.MainView;
import com.example.mypc.stores.ui.main.fragment.listpost.ListPostView;
import com.example.mypc.stores.ui.main.usermanager.UserManagerView;
import com.example.mypc.stores.ui.register.RegisView;
import com.example.mypc.stores.ui.storedetail.StoreDetailView;
import com.example.mypc.stores.ui.main.fragment.cmt.CmtFragmentView;
import com.example.mypc.stores.ui.main.fragment.newpost.NewPostView;
import com.example.mypc.stores.ui.login.LoginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MyPC on 02/08/2017.
 */
@Module
public class ViewModule {
    MainView mainView;
    NewPostView newPostView;
    CmtFragmentView cmtFragmentView;
    LoginView loginView;
    RegisView regisView;
    StoreDetailView storeDetailView;
    ListPostView listPostView;
    UserManagerView userManagerView;

    public ViewModule(UserManagerView userManagerView) {
        this.userManagerView = userManagerView;
    }

    public ViewModule(ListPostView listPostView) {
        this.listPostView = listPostView;
    }

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

    public ViewModule(MainView view) {
        this.mainView = view;
    }

    public ViewModule(StoreDetailView storeDetailView) {
        this.storeDetailView = storeDetailView;
    }

    @Provides
    public UserManagerView getUserManagerView() {
        return userManagerView;
    }

    @Provides
    public ListPostView getListPostView() {
        return listPostView;
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
    public MainView getMainView() {
        return mainView;
    }

    @Provides
    public CmtFragmentView getCmtFragmentView() {
        return cmtFragmentView;
    }
}
