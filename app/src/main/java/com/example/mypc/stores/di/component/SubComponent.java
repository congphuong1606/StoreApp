package com.example.mypc.stores.di.component;

import com.example.mypc.stores.di.module.RxModule;
import com.example.mypc.stores.di.module.StorageModule;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.Register.RegisActivity;
import com.example.mypc.stores.ui.home.Fragment.cmt.CmtFragment;
import com.example.mypc.stores.ui.home.Fragment.cmt.CmtFragmentView;
import com.example.mypc.stores.ui.home.Fragment.newpost.NewPostFragment;
import com.example.mypc.stores.ui.home.HomeActivity;
import com.example.mypc.stores.ui.login.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by MyPC on 02/08/2017.
 */
@Subcomponent(modules = {ViewModule.class, StorageModule.class})
public interface SubComponent {
    void InjectTo(HomeActivity activity);
    void InjectTo(NewPostFragment newPostFragment);
    void InjectTo(CmtFragment cmtFragment);
    void InjectTo(LoginActivity loginActivity);
    void InjectTo(RegisActivity regisActivity);
}
