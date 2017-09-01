package com.example.mypc.stores.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.events.BtnSaveClickListenner;
import com.example.mypc.stores.events.OnEventclickListener;
import com.example.mypc.stores.ui.base.BaseActivity;
import com.example.mypc.stores.ui.main.fragment.imageviewer.ImageViewFragment;
import com.example.mypc.stores.ui.main.fragment.cmt.CmtFragment;
import com.example.mypc.stores.ui.main.fragment.editpost.EditPostFragment;
import com.example.mypc.stores.ui.main.fragment.listpost.ListPostFragment;
import com.example.mypc.stores.ui.main.fragment.newpost.NewPostFragment;
import com.example.mypc.stores.ui.main.fragment.usermanager.UserManagerFragment;
import com.example.mypc.stores.ui.main.utils.KeyBoardUtils;
import com.example.mypc.stores.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements MainView, OnEventclickListener {
    private BtnSaveClickListenner mListener;
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


    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    private AppBarLayout.LayoutParams params;
    private boolean isOpenImageView = false;
    @Inject
    SharedPreferences mPreferences;
    @Inject
    MainPresenter mainPresenter;
    private String accType;


    public void setClickSaveListener(BtnSaveClickListenner mListener) {
        this.mListener = mListener;
    }

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
        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        mainPresenter.setAvatarUser();
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        btnBack.setVisibility(View.GONE);
        setNewFragment(new ListPostFragment());
        setViewForAcc();

    }

    private void setViewForAcc() {
       accType=mPreferences.getString(Constants.PREF_ACC_TYPE,"");
        if(accType.equals("user")){
            fabNewPost.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (isOpenFragment) {
            unInitViewFragment();
            if(isOpenImageView){

                btnBack.setVisibility(View.VISIBLE);
                fabNewPost.setVisibility(View.GONE);
                cimvAccAvatar.setVisibility(View.GONE);
            }
        } else {
            if (isOpenImageView) {
                unInitViewFragment();
                isOpenImageView=false;
            } else finish();
        }
    }

    private void unInitViewFragment() {
        tvToolbarTitle.setText("Stores");
        isOpenFragment = false;
        btnSave.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        cimvAccAvatar.setVisibility(View.VISIBLE);
        if(accType.equals("user")){
            fabNewPost.setVisibility(View.GONE);
        }else {
            fabNewPost.setVisibility(View.VISIBLE);
        }
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

        tvToolbarTitle.setText("Edit Text");
        btnSave.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.GONE);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        editPostFragment.setArguments(bundle);
    }

    @Override
    public void onClickSavePostHistory(long postId) {

    }

    @Override
    public void onClickRePortPost(Post post) {

    }

    public static void onShowDialogFail(String msg) {
//        onShowErorr(msg);
    }

    public void deletePost(Post post, int type) {
        onShowBuiderPostPotion(this, post, type);
    }


    public void showFagmentSaveHistory() {

    }


    @OnClick({R.id.cimv_acc_avatar, R.id.btn_back, R.id.fab_new_post, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                mListener.onclick();
                break;
            case R.id.cimv_acc_avatar:
                setNewFragment(new UserManagerFragment());
                tvToolbarTitle.setText("about");
                initFragment();
                break;
            case R.id.btn_back:
                KeyBoardUtils.hideKeyboard(this);
                tvToolbarTitle.setText("stores");

                onBackPressed();
                break;
            case R.id.fab_new_post:

                params.setScrollFlags(0);
                setNewFragment(new NewPostFragment());
                btnSave.setVisibility(View.VISIBLE);
                tvToolbarTitle.setText("New Post");
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

    public void showFragmentCmt(Post post, int adapterPosition) {
        toolbar.setVisibility(View.GONE);
        initFragment();
        CmtFragment cmtFragment = new CmtFragment();
        setNewFragment(cmtFragment);
        Bundle bundle = new Bundle();
        bundle.putLong("postId", post.getPostId());
        bundle.putInt("postPosition", adapterPosition);
        cmtFragment.setArguments(bundle);
    }

    public void showFragmentImaeViewer(Post post, int position) {
        initFragment();
        params.setScrollFlags(0);
        tvToolbarTitle.setText("Stores");
        ImageViewFragment imageViewFragment = new ImageViewFragment();
        setNewFragment(imageViewFragment);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        bundle.putInt("position", position);
        imageViewFragment.setArguments(bundle);
    }

    public void setOpenFragment() {
        isOpenImageView = true;
    }


}
