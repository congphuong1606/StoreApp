package com.example.mypc.stores.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.mypc.stores.R;
import com.example.mypc.stores.events.OnEventclickListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by MyPC on 02/08/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayoutID());
        mUnbinder= ButterKnife.bind(this);
        injectDependence();
        initView();
        initData();
    }




    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }
    protected abstract void injectDependence();
    protected abstract int getContentLayoutID();
    protected abstract void initData();
    protected abstract void  initView();

    public void showLoading() {
        if (dialog != null) {
            if (dialog.isShowing()) dialog.dismiss();
            dialog.show();
            return;
        }
        dialog = ProgressDialog
                .show(this, "", "Loading. Please wait...", true);
    }

    public void hideLoading() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public void onStartActivity(Class aClass) {
        startActivity(new Intent(this, aClass));
    }

    public void onShowBuider(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông Báo");
        builder.setMessage(msg);
        builder.setIcon(R.drawable.logo_app);
        builder.setCancelable(true);
        final AlertDialog  dialog =builder.create();
        builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onShowBuiderEvent(final String item1, final String item2, final OnEventclickListener eventClick) {
        final CharSequence[] items = {item1, item2};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.titleavatar));
        builder.setIcon(R.drawable.logo_app);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals(item1)) {
                   // eventClick.eventChoosePhoto();
                } else if (items[i].equals(item2)) {
                   // eventClick.eventTakePicture();
                }
            }
        });
        builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });
        builder.create().show();
    }





}
