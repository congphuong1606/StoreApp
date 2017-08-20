package com.example.mypc.stores.ui.home.Fragment.newpost;

import com.example.mypc.stores.data.model.Account;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.network.ApiService;
import com.example.mypc.stores.utils.TimeControler;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MyPC on 04/08/2017.
 */

public class NewPostPresenter {
    TimeControler timeControler = new TimeControler();
    private ApiService mApiService;
    private NewPostView mPostView;
    private CompositeDisposable mDisposable;

    @Inject
    public NewPostPresenter(ApiService mApiService, NewPostView mPostView, CompositeDisposable compositeDisposable) {
        this.mPostView = mPostView;
        this.mApiService = mApiService;
        this.mDisposable = compositeDisposable;
    }
    public void onUploadPost(long accId, String accAvatar, String accFullName, String postContent) {
        long postId = accId + timeControler.getLongCurentTime();
        String postTime = timeControler.getCurentTime() + "";
        Post post = new Post(postId, postContent, postTime, "0", "0", accId, accFullName, accAvatar);
        mDisposable.add(mApiService.saveNewPost(post)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onFail));
    }

    private void onSuccess(Post post) {
        mPostView.onLoadPostSuccess(post);
    }

    private void onFail(Throwable throwable) {
        mPostView.onFail(String.valueOf(throwable));
    }

}
