package com.example.mypc.stores.ui.storedetail;

import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.network.ApiService;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by congp on 8/23/2017.
 */

public class StoreDetailPresenter {
    private StoreDetailView mStoreDetailView;
    private ApiService mApiService;
    private CompositeDisposable mDisposable;

    @Inject
    public StoreDetailPresenter(StoreDetailView mStoreDetailView, ApiService mApiService, CompositeDisposable mDisposable) {
        this.mStoreDetailView = mStoreDetailView;
        this.mApiService = mApiService;
        this.mDisposable = mDisposable;
    }

    public void getPostStoreData(long postStoreId) {
        Observable<ArrayList<Post>> posts = mApiService.getListPostStore(postStoreId);
        mDisposable.add(posts.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadPostsSuccess, this::onError));

    }
    private void onLoadPostsSuccess(ArrayList<Post> posts) {
        mStoreDetailView.onLoadDataSuccess(posts);
    }

    private void onError(Throwable throwable) {
mStoreDetailView.onRequestFailure(String.valueOf(throwable));
    }



}
