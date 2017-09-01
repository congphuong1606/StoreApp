package com.example.mypc.stores.ui.storedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.events.ProductAdapterClickListener;
import com.example.mypc.stores.ui.adapter.ProductAdapter;
import com.example.mypc.stores.ui.base.BaseActivity;
import com.example.mypc.stores.ui.storedetail.fagment.mapfragment.MapsFragment;
import com.example.mypc.stores.ui.storedetail.fagment.postdetail.PostDetailFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoreDetailActivity extends BaseActivity implements StoreDetailView, ProductAdapterClickListener {


    @BindView(R.id.ic_back)
    Button icBack;
    @BindView(R.id.civ_avatar_store)
    CircleImageView civAvatarStore;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.btn_call)
    Button btnCall;
    @BindView(R.id.btn_msg)
    Button btnMsg;
    @BindView(R.id.btn_location)
    Button btnLocation;
    @BindView(R.id.rcv_post_store)
    RecyclerView rcvPostStore;
    @BindView(R.id.fl_map)
    FrameLayout flMap;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    private long storeId;

    private ArrayList storePosts;
    private ProductAdapter mAdapter;

    @Inject
    StoreDetailPresenter mPresenter;
    private FragmentTransaction transaction;


    @Override
    protected void injectDependence() {
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).injectTo(this);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.activity_store_detail;
    }

    @Override
    protected void initView() {
        rcvPostStore.setLayoutManager(new GridLayoutManager(this, 2));
        rcvPostStore.setHasFixedSize(true);
        Intent intent = getIntent();
        storeId = intent.getLongExtra("storeId", 0);

    }

    @Override
    protected void initData() {
        transaction = getSupportFragmentManager().beginTransaction();
        storePosts = new ArrayList<>();
        mAdapter = new ProductAdapter(storePosts);
        rcvPostStore.setAdapter(mAdapter);
        mPresenter.getPostStoreData(storeId);
        mAdapter.setClickListener(this);

    }

    @Override
    public void onLoadDataSuccess(ArrayList<Post> storePosts) {
        this.storePosts.addAll(storePosts);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String msg) {

    }

    @Override
    public void onRequestFailure(String s) {
        onShowErorr(s);
    }


    @Override
    protected void onDestroyComposi() {

    }


    @OnClick({R.id.ic_back, R.id.btn_call, R.id.btn_msg, R.id.btn_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_back:
                finish();
                break;
            case R.id.btn_call:
                break;
            case R.id.btn_msg:
                break;
            case R.id.btn_location:
                transaction.add(R.id.fl_map, MapsFragment.newInstance());
                transaction.commit();
                break;
        }
    }

    @Override
    public void onItemClick(Post post) {
        transaction.add(R.id.fl_map, PostDetailFragment.newInstance());
        transaction.commit();
    }


}
