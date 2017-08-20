package com.example.mypc.stores.ui.home;

import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.network.ApiService;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter {
    private ApiService mApiService;
    private CompositeDisposable mDisposable;
    private HomeView view;

    @Inject
    public HomePresenter(HomeView view,ApiService apiService,
                         CompositeDisposable mDisposable) {
        this.mApiService=apiService;
        this.view = view;
        this.mDisposable=mDisposable;
    }
    public void getPost() {
//        mDisposable=new CompositeDisposable();
        mDisposable.add(mApiService.getListAllPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError));

    }

    private void onError(Throwable throwable) {
        view.onFail(String.valueOf(throwable));
    }

    private void onSuccess(ArrayList<Post> posts) {
        view.onLoadPostsSuccess(posts);
    }


    public void onDestroy() {
        mDisposable.dispose();
    }
}