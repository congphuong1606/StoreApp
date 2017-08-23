package com.example.mypc.stores.ui.StoreDetail;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
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
import com.example.mypc.stores.ui.Adapter.PostAdapter;
import com.example.mypc.stores.ui.StoreDetail.fagment.MapFragment;
import com.example.mypc.stores.ui.base.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoreDetailActivity extends BaseActivity implements StoreDetailView {


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
    private long storeId;

    private ArrayList posts;
    private PostAdapter mAdapter;

    @Inject
    StoreDetailPresenter mPresenter;
    @Override
    protected void injectDependence() {
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).InjectTo(this);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.activity_store_detail;
    }

    @Override
    protected void initView() {
        rcvPostStore.setLayoutManager(new GridLayoutManager(this, 1));
        rcvPostStore.setHasFixedSize(true);
        Intent intent = getIntent();
        storeId = intent.getLongExtra("storeId", 0);

    }

    @Override
    protected void initData() {
        posts = new ArrayList<>();
        mAdapter = new PostAdapter(posts);
        rcvPostStore.setAdapter(mAdapter);
        mPresenter.getPostStoreData(storeId);

    }

    @Override
    public void onLoadDataSuccess(ArrayList<Post> posts) {
        this.posts.addAll(posts);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String msg) {

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
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.fl_map, MapFragment.newInstance());
                transaction.commit();
                break;
        }
    }


}
