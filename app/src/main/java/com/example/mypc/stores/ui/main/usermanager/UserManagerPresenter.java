package com.example.mypc.stores.ui.main.usermanager;

import com.example.mypc.stores.data.model.Location;
import com.example.mypc.stores.network.ApiService;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by congp on 8/28/2017.
 */

public class UserManagerPresenter {
    UserManagerView userManagerView;
    ApiService apiService;
    CompositeDisposable mDisposable;
    @Inject
    public UserManagerPresenter(UserManagerView userManagerView, ApiService apiService, CompositeDisposable mDisposable) {
        this.userManagerView = userManagerView;
        this.apiService = apiService;
        this.mDisposable = mDisposable;
    }


    public void getLocationStore(Long accId) {
        mDisposable.add(apiService.getLocation(accId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: onLoadLocationSuccess,this::onloadFail));
    }

    private void onLoadLocationSuccess(Location location) {
        userManagerView.onLoadLocationSuccess(location);
    }

    private void onloadFail(Throwable throwable) {

    }

}
