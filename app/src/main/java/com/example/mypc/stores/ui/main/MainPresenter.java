package com.example.mypc.stores.ui.main;

import android.content.SharedPreferences;

import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.network.ApiService;
import com.example.mypc.stores.utils.Constants;
import com.example.mypc.stores.utils.TimeControler;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {
    SharedPreferences mPreferences;
    MainView mainView;
    CompositeDisposable mDisposable;
    ApiService mApiService;

    @Inject
    public MainPresenter(SharedPreferences mPreferences, MainView mainView, CompositeDisposable mDisposable, ApiService mApiService) {
        this.mPreferences = mPreferences;
        this.mainView = mainView;
        this.mDisposable = mDisposable;
        this.mApiService = mApiService;
    }

    public void setAvatarUser() {
        String avatarUser = mPreferences.getString(Constants.PREF_ACC_AVATAR, "");
        mainView.onGetAvatarUserSuccess(avatarUser);
    }

    public void deletePost(long postId) {
        mDisposable.add(mApiService.deletePost(postId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDeletePostSuccess, this::onError));
    }

    private void onError(Throwable throwable) {
        mainView.onRequestFailure(String.valueOf(throwable));
    }

    private void onDeletePostSuccess(Long postId) {
        mainView.onDeletePostSuccess(postId);
    }

}