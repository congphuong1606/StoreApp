package com.example.mypc.stores.di.component;

import com.example.mypc.stores.di.module.StorageModule;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.main.fragment.editpost.EditPostFragment;
import com.example.mypc.stores.ui.main.fragment.imageviewer.ImageViewFragment;
import com.example.mypc.stores.ui.main.fragment.listpost.ListPostFragment;
import com.example.mypc.stores.ui.main.fragment.posthistory.PostHistoryFragment;
import com.example.mypc.stores.ui.main.fragment.usermanager.UserManagerFragment;
import com.example.mypc.stores.ui.register.RegisActivity;
import com.example.mypc.stores.ui.main.fragment.cmt.CmtFragment;
import com.example.mypc.stores.ui.main.fragment.newpost.NewPostFragment;
import com.example.mypc.stores.ui.main.MainActivity;
import com.example.mypc.stores.ui.login.LoginActivity;
import com.example.mypc.stores.ui.main.fragment.detailstorefragment.DetailStoreFragment;

import dagger.Subcomponent;

/**
 * Created by MyPC on 02/08/2017.
 */
@Subcomponent(modules = {ViewModule.class, StorageModule.class})
public interface SubComponent {
    void injectTo(MainActivity activity);
    void injectTo(NewPostFragment newPostFragment);
    void injectTo(CmtFragment cmtFragment);
    void injectTo(LoginActivity loginActivity);
    void injectTo(RegisActivity regisActivity);
    void injectTo(ListPostFragment listPostFragment);
    void injectTo(UserManagerFragment userManagerFragment);
    void injectTo(EditPostFragment editPostFragment);
    void injectTo(ImageViewFragment imageViewFragment);
    void injectTo(DetailStoreFragment detailStoreFragment);
    void injectTo(PostHistoryFragment postHistoryFragment);
}
