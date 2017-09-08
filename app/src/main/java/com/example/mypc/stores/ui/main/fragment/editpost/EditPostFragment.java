package com.example.mypc.stores.ui.main.fragment.editpost;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.events.BtnSaveClickListenner;
import com.example.mypc.stores.ui.base.BaseFragment;
import com.example.mypc.stores.ui.main.MainActivity;
import com.example.mypc.stores.ui.main.fragment.listpost.ListPostFragment;
import com.example.mypc.stores.ui.main.utils.DialogUtils;
import com.example.mypc.stores.utils.TimeControler;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class EditPostFragment extends BaseFragment implements EditPostview, BtnSaveClickListenner {
    ListPostFragment mListPostFragment;
    @BindView(R.id.imv_edit_avatar_post_store)
    CircleImageView imvEditAvatarPostStore;
    @BindView(R.id.tv_edit_store_name)
    TextView tvEditStoreName;
    @BindView(R.id.tv_edit_post_time)
    TextView tvEditPostTime;
    @BindView(R.id.edt_edit_post_content)
    EditText edtEditPostContent;
    @BindView(R.id.imv_edit_post_image)
    ImageView imvEditPostImage;
    private Post post;
    private int mPosition;

    @Inject
    EditPostPresenter mEditPostPresenter;


    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        mListPostFragment = ListPostFragment.getIntans();
        ((MainActivity) getActivity()).setClickSaveListener(this);
        post = (Post) getArguments().getSerializable("post");
        mPosition = getArguments().getInt("position");
        tvEditStoreName.setText(post.getPostStoreName());
        tvEditPostTime.setText(post.getPostTime());
        edtEditPostContent.setText(post.getPostContent());
        Glide.with(getActivity().getApplicationContext()).load(post.getPostStoreAvatar()).into(imvEditAvatarPostStore);
        if (post.getPostImage() != null) {
            Glide.with(getActivity().getApplicationContext()).load(post.getPostImage()).into(imvEditPostImage);
        }
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_edit_post;
    }

    @Override
    protected void onDestroyComposi() {

    }

    @Override
    protected void injectDependence(View view) {
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).injectTo(this);
    }


    @Override
    public void onRequestFailure(String s) {
        DialogUtils.showErorr(getContext(), s);
    }

    @Override
    public void onUpdatePostSuccess(Post post) {
        mListPostFragment.notifyPostPosition(post, mPosition);
    }

    @Override
    public void onClickBtnSaveLisener() {
        TimeControler timeControler = new TimeControler();
        String postTime = timeControler.getCurentTime() + "";
        String postContent = edtEditPostContent.getText().toString().trim();
        post.setPostTime(postTime);
        post.setPostContent(postContent);
        mEditPostPresenter.updatePost(post);
    }
}
