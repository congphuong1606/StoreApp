package com.example.mypc.stores.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Unbinder;

/**
 * Created by MyPC on 02/08/2017.
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder mUnbinder;

    protected abstract void initView();
    protected abstract void initData();
    public abstract Unbinder bindingView(View view);
    protected abstract int getLayoutID();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutID(), container, false);
        mUnbinder = bindingView(view);
        return view;
    }

    @Override
    public void onDestroy() {

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }


}
