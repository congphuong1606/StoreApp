package com.example.mypc.stores.ui.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Account;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.base.BaseActivity;
import com.example.mypc.stores.ui.main.utils.DialogUtils;
import com.example.mypc.stores.utils.UtilDatas;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisActivity extends BaseActivity implements RegisView {
    @BindView(R.id.edt_name_regis)
    EditText edtNameRegis;
    @BindView(R.id.edt_number_regis)
    EditText edtNumberRegis;
    @BindView(R.id.edt_pass_regis)
    EditText edtPassRegis;
    private DialogUtils mDialogUtils;
    private ProgressDialog mDialog;

    @BindView(R.id.ck_user)
    CheckBox ckUser;
    @BindView(R.id.ck_store)
    CheckBox ckStore;
    @BindView(R.id.btn_sigup)
    Button btnRegis;
    @BindView(R.id.btn_back_login)
    Button btnBackLogin;

    @Inject
    RegisPresenter regisPresenter;
    private Account newAcc;

    @Override
    protected void injectDependence() {
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).injectTo(this);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.activity_regis;
    }

    @Override
    protected void initData() {
        mDialogUtils = new DialogUtils(mDialog, this);

    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.ck_user, R.id.ck_store,
            R.id.btn_sigup, R.id.btn_back_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ck_user:
                ckUser.setChecked(true);
                ckStore.setChecked(false);
                break;
            case R.id.ck_store:
                ckUser.setChecked(false);
                ckStore.setChecked(true);
                break;
            case R.id.btn_sigup:

                getDataLocal();

                break;
            case R.id.btn_back_login:
                finish();
                break;
        }
    }

    private void getDataLocal() {
        String accType = "user";
        if (ckStore.isChecked()) {
            accType = "store";
        }
        if (UtilDatas.checkInPutRegis(edtNameRegis,edtNumberRegis, edtPassRegis, this)) {
            String accName = edtNameRegis.getText().toString().trim();
            String accPass = edtPassRegis.getText().toString().trim();
            String accNumber = edtNumberRegis.getText().toString().trim();
            newAcc = new Account(Long.valueOf(accNumber),accType, accNumber, accName, accName, accPass, "");
            regis();

        }

    }

    private void regis() {
        mDialogUtils.showLoading();
        regisPresenter.onSigup(newAcc);
    }

    @Override
    public void onSigupSuccess() {
        mDialogUtils.hideLoading();
        finish();
    }

    @Override
    public void onRequestFailure(String msg) {
        mDialogUtils.hideLoading();
        DialogUtils.showErorr(this, msg);
    }


}
