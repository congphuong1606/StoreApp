package com.example.mypc.stores.ui.main;

import android.content.SharedPreferences;

import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.network.ApiService;
import com.example.mypc.stores.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {
    SharedPreferences mPreferences;
    MainView mainView;

    @Inject
    public MainPresenter(SharedPreferences mPreferences, MainView mainView) {
        this.mPreferences = mPreferences;
        this.mainView = mainView;
    }

    public void setAvatarUser() {
        String avatarUser = mPreferences.getString(Constants.PREF_ACC_AVATAR, "");
        mainView.onGetAvatarUserSuccess(avatarUser);
    }
}