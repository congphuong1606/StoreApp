package com.example.mypc.stores.ui.main;

import android.content.SharedPreferences;

import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.network.ApiService;
import com.example.mypc.stores.utils.Constants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {
    MainView mainView;
    ApiService mApiService;
    SharedPreferences mPreferences;

    @Inject
    public MainPresenter(SharedPreferences mPreferences, MainView mainView, ApiService mApiService) {
        this.mainView = mainView;
        this.mApiService = mApiService;
        this.mPreferences=mPreferences;
    }


    public void deletePost(long postId) {
       mApiService.deletePost(postId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDeletePostSuccess, this::onError);
    }

    private void onError(Throwable throwable) {
        mainView.onRequestFailure(String.valueOf(throwable));
    }

    private void onDeletePostSuccess(Long postId) {
        mainView.onDeletePostSuccess(postId);
    }




    public void savePostToHistory(long postId) {
        Long accId=mPreferences.getLong(Constants.PREF_ACC_ID,0);
        mApiService.addHistory(postId,accId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addHistoryPostSuccess, this::onError);
    }

    private void addHistoryPostSuccess(Integer integer) {
        mainView.onSavePostHistorySuccess();
    }

    public void deletePostHistory(long aLong, Long postId) {
        mApiService.deletePostHistory(aLong,postId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDeletePostHistorySuccess, this::onError);
    }

    private void onDeletePostHistorySuccess(Long aLong) {

    }
}