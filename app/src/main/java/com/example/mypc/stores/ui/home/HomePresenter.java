package com.example.mypc.stores.ui.home;

import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.network.ApiService;
import com.example.mypc.stores.network.ApiUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter {

    private ApiService mApiService = ApiUtils.getIapiService();
    CompositeDisposable compositeDisposable;
    private HomeView view;

    @Inject
    public HomePresenter(HomeView view) {
        this.view = view;
    }

    public void getPost() {
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(mApiService.getListAllPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError));

    }

    private void onError(Throwable throwable) {
        view.onFail(String.valueOf(throwable));
    }

    private void onSuccess(ArrayList<Post> posts) {
        view.onUpdatePostsSuccess(posts);
    }


    public void onDestroy() {
        compositeDisposable.clear();
    }
}