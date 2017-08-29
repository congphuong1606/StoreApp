package com.example.mypc.stores.ui.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.events.OnEventclickListener;
import com.example.mypc.stores.ui.base.BaseActivity;
import com.example.mypc.stores.ui.main.fragment.cmt.CmtFragment;
import com.example.mypc.stores.ui.main.fragment.editpost.EditPostFragment;
import com.example.mypc.stores.ui.main.fragment.listpost.ListPostFragment;
import com.example.mypc.stores.ui.main.fragment.newpost.NewPostFragment;
import com.example.mypc.stores.ui.main.usermanager.UserManagerFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements MainView, OnEventclickListener {
    boolean isOpenFragment = false;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cimv_acc_avatar)
    CircleImageView cimvAccAvatar;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.fab_new_post)
    FloatingActionButton fabNewPost;
    @BindView(R.id.layout_fragment)
    FrameLayout layoutFragment;

    @Inject
    MainPresenter mainPresenter;
    @BindView(R.id.btn_save)
    Button btnSave;


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
        mainPresenter.setAvatarUser();
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        btnBack.setVisibility(View.GONE);
        setNewFragment(new ListPostFragment());

    }

    @Override
    public void onBackPressed() {
        if (isOpenFragment) {
            unInitViewFragment();
        } else {
            finish();
        }
    }

    private void unInitViewFragment() {
        isOpenFragment = false;
        btnBack.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        fabNewPost.setVisibility(View.VISIBLE);
        cimvAccAvatar.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }


    private void setNewFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction().addToBackStack(null);
        transaction.add(R.id.layout_fragment, fragment);
//        transaction.addToBackStack(tagFragment);
        transaction.commit();
    }


    @Override
    protected void onDestroyComposi() {

    }


    @Override
    public void onClickDelete(long postId) {
        mainPresenter.deletePost(postId);
    }

    @Override
    public void onClickEdit(Post post) {
        initFragment();
        EditPostFragment editPostFragment = new EditPostFragment();
        setNewFragment(editPostFragment);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        editPostFragment.setArguments(bundle);
    }

    public static void onShowDialogFail(String msg) {
//        onShowBuider(msg);
    }

    public void deletePost(Post post) {
        onShowBuiderDelete(this, post);
    }

    public void initCmtFragment(Post post, int adapterPosition) {
        toolbar.setVisibility(View.GONE);
        initFragment();
        CmtFragment cmtFragment = new CmtFragment();
        setNewFragment(cmtFragment);
        Bundle bundle = new Bundle();
        bundle.putLong("postId", post.getPostId());
        bundle.putInt("postPosition", adapterPosition);
        cmtFragment.setArguments(bundle);
    }


    @OnClick({R.id.cimv_acc_avatar, R.id.btn_back, R.id.fab_new_post, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                break;
            case R.id.cimv_acc_avatar:
                setNewFragment(new UserManagerFragment());
                initFragment();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.fab_new_post:
                setNewFragment(new NewPostFragment());
                initFragment();
                break;
        }
    }

    private void initFragment() {
        isOpenFragment = true;
        fabNewPost.setVisibility(View.GONE);
        cimvAccAvatar.setVisibility(View.GONE);
        btnBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetAvatarUserSuccess(String avatarUser) {
        Glide.with(getApplicationContext()).load(avatarUser).into(cimvAccAvatar);
    }

    @Override
    public void onDeletePostSuccess(Long postId) {
        ListPostFragment.notifyPostAdapter(postId);
    }

    @Override
    public void onRequestFailure(String s) {
        onShowDialogFail(s);
    }


}
