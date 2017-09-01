package com.example.mypc.stores.ui.base;

/**
 * Created by congp on 9/1/2017.
 */

public interface BaseActivityView {
    void onIsConnectSuccess();
    void onRequestFail(String stringConectFail);
    void onShowErorr(String s);
    void onHideLoading();
    void onShowLoading();
}
