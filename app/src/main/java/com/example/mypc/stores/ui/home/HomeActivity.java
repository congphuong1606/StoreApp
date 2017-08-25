package com.example.mypc.stores.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.events.PostAdapterClickListener;
import com.example.mypc.stores.ui.StoreDetail.StoreDetailActivity;
import com.example.mypc.stores.ui.adapter.PostAdapter;
import com.example.mypc.stores.ui.home.fragment.cmt.CmtFragment;
import com.example.mypc.stores.ui.base.BaseActivity;
import com.example.mypc.stores.ui.home.fragment.newpost.NewPostFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeView, NavigationView.OnNavigationItemSelectedListener, PostAdapterClickListener {
    private static ArrayList<Post> posts;
    private static PostAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fbt_new_post)
    FloatingActionButton fbtNewPost;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rcv_post)
    RecyclerView rcvPost;
    @BindView(R.id.layout_cmt)
    FrameLayout layoutFragment;
    boolean isOpenFragment = false;

    @Inject
    HomePresenter mainPresenter;
    private int mPosition;
    private long mPostId;



    @Override
    protected void injectDependence() {
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).injectTo(this);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        posts = new ArrayList<>();
        mAdapter = new PostAdapter(posts);
        rcvPost.setAdapter(mAdapter);
        mainPresenter.getPost();
        mAdapter.setClickListener(this);
    }

    @Override
    protected void initView() {
        layoutFragment.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        rcvPost.setLayoutManager(new GridLayoutManager(this, 1));
        rcvPost.setHasFixedSize(true);

    }

    @Override
    public void onBackPressed() {
        if (isOpenFragment) {
            unInitViewFragment();
        } else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void unInitViewFragment() {
        layoutFragment.setVisibility(View.GONE);
        rcvPost.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        fbtNewPost.setVisibility(View.VISIBLE);
        isOpenFragment = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_topStore:
                break;
            case R.id.nav_love:
                break;
            case R.id.nav_account:
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_email:
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_share:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.fbt_new_post)
    public void onViewClicked() {
        initViewFragment();
        setNewFragment(new NewPostFragment());

    }


    private void initViewFragment() {
        layoutFragment.setVisibility(View.VISIBLE);
        rcvPost.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        fbtNewPost.setVisibility(View.GONE);
        isOpenFragment = true;
    }


    private void setNewFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction().addToBackStack(null);
        transaction.add(R.id.layout_cmt, fragment);
//        transaction.addToBackStack(tagFragment);
        transaction.commit();
    }

    @Override
    public void onLoadPostsSuccess(ArrayList<Post> posts) {
        this.posts.addAll(posts);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String msg) {
        onShowBuider(msg);
    }

    @Override
    public void onUpdatePostLoveSuccess(Integer countPostLove) {
        for (Post p : posts) {
            if (mPostId == p.getPostId()) {
                p.setPostLove(countPostLove + "");
            }
        }
        mAdapter.notifyItemChanged(mPosition);
    }

    @Override
    protected void onDestroyComposi() {
        mainPresenter.onDestroy();
    }


    public void setNewPost(Post post) {
        posts.add(post);
        mAdapter.notifyDataSetChanged();

    }

    public static void updateCountPostCmt(Integer countPostCmt, long postId, int position) {
        for (Post p : posts) {
            if (postId == p.getPostId()) {
                p.setPostComment(countPostCmt + "");
            }
        }
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void onClickImvAvatarPostStore(long postStoreId) {
        Intent intent = new Intent(this, StoreDetailActivity.class);
        intent.putExtra("storeId", postStoreId);
        startActivity(intent);
    }

    @Override
    public void onClickBtnCmt(long postId, int adapterPosition) {
        initViewFragment();
        CmtFragment cmtFragment = new CmtFragment();
        setNewFragment(cmtFragment);
        Bundle bundle = new Bundle();
        bundle.putLong("postId", postId);
        bundle.putInt("postPosition", adapterPosition);
        cmtFragment.setArguments(bundle);
    }

    @Override
    public void onClickBtnLove(long postId, int position) {
        mPostId=postId;
        mainPresenter.updateCountPostLove(postId);
        mPosition=position;
    }

}
