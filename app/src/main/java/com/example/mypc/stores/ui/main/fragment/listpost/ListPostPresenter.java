package com.example.mypc.stores.ui.main.fragment.listpost;

import com.example.mypc.stores.data.model.IsLike;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.network.ApiService;

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
        mApiService.getListAllPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }

    private void onError(Throwable throwable) {
        mListPostView.onRequestFailure(String.valueOf(throwable));
    }

    private void onSuccess(ArrayList<Post> posts) {
        mListPostView.onLoadPostsSuccess(posts);
    }


    public void onDestroy() {
        mDisposable.dispose();
    }


    public void updateCountPostLove(long postId, int i) {
        mApiService.updatePostLove(postId, i).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updatePostLoveSuccsess, this::onError);
    }

    private void updatePostLoveSuccsess(Integer countPostLove) {
        mListPostView.onUpdatePostLoveSuccess(countPostLove);
    }


    public void uploadIsLikePost(Long islikeId, long accId, long postId) {
        IsLike isLike = new IsLike(islikeId, accId, postId);
        mApiService.uploadIsLike(isLike).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::uploadIsLikeSuccsess, this::onError);
    }

    private void uploadIsLikeSuccsess(Integer integer) {
        if (integer == 1) {
            mListPostView.onUploadIsLikeSuccess();
        }

    }

    public void isLike(long islikeId) {
        mApiService.isLike(islikeId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::isLikeSuccsess, this::onError);
    }

    private void isLikeSuccsess(Integer integer) {
        mListPostView.islikeSuccess(integer);
    }

    public void deleteIsLikePost(long islikeId) {
        mApiService.deleteIsLikePost(islikeId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::deleteIsLikeSuccsess, this::onError);
    }

    private void deleteIsLikeSuccsess(Integer integer) {
        mListPostView.onDeleteIsLikePostSuccess();
    }


}
