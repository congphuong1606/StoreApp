package com.example.mypc.stores.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.events.BtnSaveClickListenner;
import com.example.mypc.stores.events.OnEventclickListener;
import com.example.mypc.stores.ui.base.BaseActivity;
import com.example.mypc.stores.ui.main.fragment.cmt.CmtFragment;
import com.example.mypc.stores.ui.main.fragment.detailstorefragment.DetailStoreFragment;
import com.example.mypc.stores.ui.main.fragment.editpost.EditPostFragment;
import com.example.mypc.stores.ui.main.fragment.imageviewer.ImageViewFragment;
import com.example.mypc.stores.ui.main.fragment.listpost.ListPostFragment;
import com.example.mypc.stores.ui.main.fragment.newpost.NewPostFragment;
import com.example.mypc.stores.ui.main.fragment.posthistory.PostHistoryFragment;
import com.example.mypc.stores.ui.main.fragment.usermanager.UserManagerFragment;
import com.example.mypc.stores.ui.main.utils.DialogUtils;
import com.example.mypc.stores.ui.main.utils.KeyBoardUtils;
import com.example.mypc.stores.ui.main.utils.ToolBarUtils;
import com.example.mypc.stores.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements MainView, OnEventclickListener {
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    private ListPostFragment mListPostFragment;
    private BtnSaveClickListenner mListener;
    private ToolBarUtils mToolBarUtils;
    private boolean isOpenFragment = false;
    private boolean isOpenImageView = false;
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
    CoordinatorLayout.LayoutParams appBarLayoutParams;
    AppBarLayout.LayoutParams params;
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
        MyApplication.get().getAppComponent()
                .plus(new ViewModule(this)).injectTo(this);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mListPostFragment = ListPostFragment.getIntans();
       params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
    }
public  void setToolBarOffScroll(){
    params.setScrollFlags(0);
    appBarLayoutParams.setBehavior(null);
    appBarLayout.setLayoutParams(appBarLayoutParams);
}
public void setToolBarOnScroll(){
    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
    appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
    appBarLayout.setLayoutParams(appBarLayoutParams);
}
    @Override
    protected void initView() {
        setUserAvatar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        mToolBarUtils = new ToolBarUtils(this);
        mToolBarUtils.setToolbar(toolbar, btnBack, tvToolbarTitle, cimvAccAvatar, btnSave);
        btnBack.setVisibility(View.GONE);
        setViewForAcc();
        showFragmentListPost(mPreferences.getLong(Constants.PREF_ACC_ID, 0), Constants.LIST_ALL_POST);
    }

    private void setViewForAcc() {
        accType = mPreferences.getString(Constants.PREF_ACC_TYPE, "");
        if (accType.equals("user")) {
            fabNewPost.setVisibility(View.GONE);
        } else if (accType.equals(("store"))) {
            fabNewPost.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        KeyBoardUtils.hideKeyboard(this);
        if (isOpenFragment) {
            unInitViewFragment();
            if (isOpenImageView) {
                mToolBarUtils.setToolBarMain();
                fabNewPost.setVisibility(View.GONE);
            }

        } else {
            if (isOpenImageView) {
                mToolBarUtils.setToolBarFragmentDetailStore();
                super.onBackPressed();
                isOpenImageView = false;

            } else {
                finish();

            }
        }
    }

    public void unInitViewFragment() {
        isOpenFragment = false;
        mToolBarUtils.setToolBarMain();
        setToolBarOnScroll();
        setFabBtn();
        super.onBackPressed();
    }

    private void setFabBtn() {
        if (accType.equals("user")) {
            fabNewPost.setVisibility(View.GONE);
        } else {
            fabNewPost.setVisibility(View.VISIBLE);
        }
    }


    private void setNewFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction().addToBackStack(null);
        transaction.add(R.id.layout_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void onDeletePostSuccess(Long postId) {
        mainPresenter.deletePostHistory(mPreferences.getLong(Constants.PREF_ACC_ID, 0), postId);
        mListPostFragment = ListPostFragment.getIntans();
        mListPostFragment.notifyPostAdapter(postId);
    }

    @Override
    public void onRequestFailure(String s) {
        DialogUtils.showErorr(this, s);
    }

    @Override
    public void onSavePostHistorySuccess() {
        Toast.makeText(this, "success!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClickDelete(long postId) {
        mainPresenter.deletePost(postId);

    }


    @Override
    public void onClickSavePostHistory(long postId) {
        mainPresenter.savePostToHistory(postId);

    }

    @Override
    public void onClickRePortPost(Post post) {

    }


    public void postOptions(Post post, int type, int position) {
        onShowBuiderPostPotion(this, post, type, position);
    }

    @Override
    public void onClickEdit(Post post, int position) {
        showFragmentEditPost(post, position);
    }


    @OnClick({R.id.cimv_acc_avatar, R.id.btn_back,
            R.id.fab_new_post, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                mListener.onClickBtnSaveLisener();
                break;
            case R.id.cimv_acc_avatar:
                showFragmentUserManager();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.fab_new_post:
                showFragmentNewPost();
                break;
        }
    }

    private void showFragmentUserManager() {
        setNewFragment(new UserManagerFragment());
        mToolBarUtils.setToolBarFragmentUsermanager();
        setOpenFragment();
    }


    public void showFragmentCmt(Post post, int adapterPosition) {
        CmtFragment cmtFragment = new CmtFragment();
        setNewFragment(cmtFragment);
        Bundle bundle = new Bundle();
        bundle.putLong("postId", post.getPostId());
        bundle.putInt("postPosition", adapterPosition);
        cmtFragment.setArguments(bundle);
        mToolBarUtils.setToolBarFragmentComment();
        setOpenFragment();
    }

    public void showFragmentImaeViewer(Post post, int position) {
        setToolBarOffScroll();
        ImageViewFragment imageViewFragment =
                new ImageViewFragment();
        setNewFragment(imageViewFragment);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        bundle.putInt("position", position);
        imageViewFragment.setArguments(bundle);
        mToolBarUtils.setToolBarFragmentImageView();
        fabNewPost.setVisibility(View.GONE);
        setOpenFragment();
    }

    public void showFagmentSaveHistory() {
        setOpenFragment();
        PostHistoryFragment postHistoryFragment =
                new PostHistoryFragment();
        setNewFragment(postHistoryFragment);
        mToolBarUtils.setToolbarFragmentHistory();
    }

    public void showDetailStoreFragment(long postStoreId) {
        DetailStoreFragment detailStoreFragment = new DetailStoreFragment();
        setNewFragment(detailStoreFragment);
        Bundle bundle = new Bundle();
        bundle.putLong("storeId", postStoreId);
        detailStoreFragment.setArguments(bundle);
        mToolBarUtils.setToolBarFragmentDetailStore();
        setOpenFragment();
    }


    public void showFragmentListPost(long id, int typeList) {
        ListPostFragment listPostFragment = new ListPostFragment();
        setNewFragment(listPostFragment);
        Bundle bundle = new Bundle();
        bundle.putInt("typeList", typeList);
        bundle.putLong("id", id);
        listPostFragment.setArguments(bundle);
        if (typeList == Constants.LIST_POST_STORE) {
            mToolBarUtils.setToolBarListPostStore();

        }

    }

    private void showFragmentEditPost(Post post, int position) {
        setToolBarOffScroll();
        EditPostFragment editPostFragment = new EditPostFragment();
        setNewFragment(editPostFragment);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        bundle.putInt("position", position);
        editPostFragment.setArguments(bundle);
        mToolBarUtils.setToolBarFragmentEditPost();
        setOpenFragment();
    }

    private void showFragmentNewPost() {
       setToolBarOffScroll();
        setNewFragment(new NewPostFragment());
        setOpenFragment();
        mToolBarUtils.setToolBarFragmentNewPost();
    }

    public void setOpenFragment() {
        isOpenFragment = true;
        fabNewPost.setVisibility(View.GONE);
    }

    public void setUserAvatar() {
        Glide.with(this)
                .load(mPreferences.getString(Constants.PREF_ACC_AVATAR, ""))
                .error(getResources().getDrawable(R.drawable.ic_noavatar))
                .into(cimvAccAvatar);
    }

    public void setOpenFragmentImageView() {
        isOpenImageView = true;
    }


}
