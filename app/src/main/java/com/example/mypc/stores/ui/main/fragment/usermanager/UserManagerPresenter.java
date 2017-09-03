package com.example.mypc.stores.ui.main.fragment.usermanager;

import com.example.mypc.stores.data.model.Location;
import com.example.mypc.stores.network.ApiService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by congp on 8/28/2017.
 */

public class UserManagerPresenter {
    UserManagerView userManagerView;
    ApiService apiService;

    @Inject
    public UserManagerPresenter(UserManagerView userManagerView, ApiService apiService) {
        this.userManagerView = userManagerView;
        this.apiService = apiService;

    }


    public void getLocationStore(Long accId) {
       apiService.getLocation(accId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: onLoadLocationSuccess,this::onloadFail);
    }

    private void onLoadLocationSuccess(Location location) {

    }

    private void onloadFail(Throwable throwable) {
userManagerView.onRequestFailure(String.valueOf(throwable));
    }

}
