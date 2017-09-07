package com.example.mypc.stores.ui.register;

import com.example.mypc.stores.data.model.Account;
import com.example.mypc.stores.network.ApiService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MyPC on 05/08/2017.
 */

public class RegisPresenter {
    private ApiService mApiService;
    private RegisView mRegisView;

    @Inject
    public RegisPresenter(ApiService mApiService,
                          RegisView mRegisView) {
        this.mApiService = mApiService;
        this.mRegisView = mRegisView;
    }

    public void onSigup(Account newAcc) {
        mApiService.saveNewAccount(newAcc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSaveSuccess, this::onSavaFail);

    }

    private void onSaveSuccess(Account account) {
        mRegisView.onSigupSuccess();
    }

    private void onSavaFail(Throwable throwable) {
        mRegisView.onRequestFailure(String.valueOf(throwable));
    }
}
