package com.example.mypc.stores.ui.login;

import com.example.mypc.stores.data.model.Account;
import com.example.mypc.stores.ui.base.BaseActivityView;

/**
 * Created by MyPC on 04/08/2017.
 */

public interface LoginView extends BaseActivityView {
    void onLoginSuccess(Account account);
    void onFail(String msg);
}
