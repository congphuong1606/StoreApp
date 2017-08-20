package com.example.mypc.stores.ui.home.Fragment.newpost;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.base.BaseFragment;
import com.example.mypc.stores.ui.home.HomeActivity;
import com.example.mypc.stores.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewPostFragment extends BaseFragment implements NewPostView {

    @BindView(R.id.imv_store_post_avatar)
    CircleImageView imvStorePostAvatar;
    @BindView(R.id.tv_store_post)
    TextView tvStorePost;
    @BindView(R.id.edt_new_post)
    EditText edtNewPost;
    @BindView(R.id.btn_sent_post)
    Button btnSentPost;

    @Inject
    SharedPreferences mPreferences;

    @Inject
    NewPostPresenter mPresenter;

    private String avatar;
    private String accFullName;
    private long accId;


    @Override
    protected void injectDependence(View view) {
        MyApplication.get().getAppComponent()
                .plus(new ViewModule(this)).InjectTo(this);
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        avatar = mPreferences.getString(Constants.PREF_ACC_AVATAR, "");
        accFullName = mPreferences.getString(Constants.PREF_ACC_FULLNAME, "");
        accId = mPreferences.getLong(Constants.PREF_ACC_ID,0);

        Glide.with(getContext()).load(avatar).into(imvStorePostAvatar);
        tvStorePost.setText(accFullName);
    }

    @Override
    public Unbinder bindingView(View view) {
        return ButterKnife.bind(this, view);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_new_post;
    }

    @Override
    protected void onDestroyComposi() {

    }


    @Override
    public void onLoadPostSuccess(Post post) {
        getActivity().onBackPressed();
        ((HomeActivity)getActivity()).setNewPost(post);

    }

    @Override
    public void onFail(String s) {

    }

    @OnClick(R.id.btn_sent_post)
    public void onViewClicked() {
        String postContent = edtNewPost.getText().toString().trim();
        mPresenter.onUploadPost(accId,avatar,accFullName,postContent);
        edtNewPost.setText("");
        hideKeyboard();
    }
}
