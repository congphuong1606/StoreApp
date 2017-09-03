package com.example.mypc.stores.ui.main.fragment.detailstorefragment;

import com.example.mypc.stores.data.model.Account;
import com.example.mypc.stores.data.model.Location;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.network.ApiService;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by congp on 8/23/2017.
 */

public class StoreDetailPresenter {
    private StoreDetailView mStoreDetailView;
    private ApiService mApiService;

    @Inject
    public StoreDetailPresenter(StoreDetailView mStoreDetailView,
                                ApiService mApiService) {
        this.mStoreDetailView = mStoreDetailView;
        this.mApiService = mApiService;

    }
    public void getStoreData(long storeId) {
        mApiService.getDetailAcc(storeId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetDetailAccSuccess, this::onError);

    }


    private void onGetLocationSuccess(Location location) {
        mStoreDetailView.onLoadLocationSuccess(location);
    }

    private void onGetDetailAccSuccess(Account account) {
               mStoreDetailView.onLoadDetailSuccess(account);
    }

    private void onLoadPostsSuccess(ArrayList<Post> posts) {
              mStoreDetailView.onLoadPostsSuccess(posts);
    }

    private void onError(Throwable throwable) {
              mStoreDetailView.onRequestFailure(String.valueOf(throwable));
    }


    public void getStoreLocation(long storeId) {
        mApiService.getLocation(storeId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetLocationSuccess, this::onError);
    }

    public void getStorePosts(long storeId) {
        mApiService.getListPostStore(storeId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadPostsSuccess, this::onError);
    }
}
