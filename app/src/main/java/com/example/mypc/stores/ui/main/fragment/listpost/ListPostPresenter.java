package com.example.mypc.stores.ui.main.fragment.listpost;

import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.network.ApiService;
import com.example.mypc.stores.ui.main.MainView;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by congp on 8/28/2017.
 */

public class ListPostPresenter {
    private ApiService mApiService;
    private CompositeDisposable mDisposable;
    private ListPostView mListPostView;

    @Inject
    public ListPostPresenter(ListPostView view, ApiService apiService,
                         CompositeDisposable mDisposable) {
        this.mApiService = apiService;
        this.mListPostView = view;
        this.mDisposable = mDisposable;
    }


    public void getPost() {
        mDisposable.add(mApiService.getListAllPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError));
    }

    private void onError(Throwable throwable) {
        mListPostView.onFail(String.valueOf(throwable));
    }

    private void onSuccess(ArrayList<Post> posts) {
        mListPostView.onLoadPostsSuccess(posts);
    }


    public void onDestroy() {
        mDisposable.dispose();
    }


    public void updateCountPostLove(long postId) {
        mDisposable.add(mApiService.updatePostLove(postId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updatePostLoveSuccsess, this::onError));
    }

    private void updatePostLoveSuccsess(Integer countPostLove) {
        mListPostView.onUpdatePostLoveSuccess(countPostLove);
    }

    public void deletePost(long postId) {
        mDisposable.add(mApiService.deletePost(postId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDeletePostSuccess, this::onError));
    }

    private void onDeletePostSuccess(Long postId) {
        mListPostView.onDeletePostSuccess(postId);
    }
}
