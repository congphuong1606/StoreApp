package com.example.mypc.stores.ui.login;

import com.example.mypc.stores.data.model.Account;
import com.example.mypc.stores.network.ApiService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MyPC on 04/08/2017.
 */

public class LoginPresenter {
    private String mAccName;
    private String mAccPass;
  
    private ApiService mApiService;
    private LoginView mView;
    boolean isCheckAccount = true;
    int i = 0;
    private FirebaseAuth mAuth;
   

    @Inject
    public LoginPresenter(FirebaseAuth mAuth, 
                          ApiService apiService,
                          LoginView mView) {
        this.mAuth = mAuth;
      
        this.mApiService = apiService;
        this.mView = mView;
    }

    public void onLogin(String accName, String accPass) {
        mAccName = accName;
        mAccPass = accPass;
        mApiService.getAccounts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onFail);

    }

    private void onFail(Throwable throwable) {
        mView.onRequestFail(String.valueOf(throwable));
    }

    private void onSuccess(ArrayList<Account> accounts) {
        int cout = accounts.size();
        i = 0;
        for (Account account : accounts) {
            i++;
            if ((account.getAccName()).equals(mAccName) &&
                    (account.getAccPass()).equals(mAccPass)) {
                mView.onLoginSuccess(account);
                isCheckAccount = true;
                break;
            } else isCheckAccount = false;
        }
        if (i == cout && !isCheckAccount) {
            mView.onRequestFail("tài khỏan hoặc mật khẩu không chính xác !");
        }
    }

    public void onDestroy() {
       
    }

    public void onloginFirebase() {
        mAuth.signInAnonymously();

    }



}
