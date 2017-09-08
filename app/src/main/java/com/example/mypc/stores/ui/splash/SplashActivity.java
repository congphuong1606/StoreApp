package com.example.mypc.stores.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.network.ApiService;
import com.example.mypc.stores.ui.login.LoginActivity;
import com.example.mypc.stores.ui.main.MainActivity;
import com.example.mypc.stores.utils.Constants;
import com.example.mypc.stores.utils.RealmUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class SplashActivity extends AppCompatActivity {

    @Inject
    ApiService mApiService;
    @Inject
    SharedPreferences mSharedPreferences;
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.realm = RealmUtils.with(this).getRealm();
        Handler handler = new Handler();
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).injectTo(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                connect();
            }
        }, 4000);
    }

    private void connect() {
        mApiService.isConnect()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::success, this::fail);
    }

    private void fail(Throwable throwable) {
        startActtivity();
    }

    private void startActtivity() {
        if (!mSharedPreferences.getString(Constants.PREF_TOKEN, "").isEmpty()) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void success(Integer integer) {
        if (integer == 1) {
            RealmUtils.with(SplashActivity.this).deletePosts();
            startActtivity();
        }
    }
}
