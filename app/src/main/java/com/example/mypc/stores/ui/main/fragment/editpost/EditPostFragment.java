package com.example.mypc.stores.ui.main.fragment.editpost;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class EditPostFragment extends BaseFragment {
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


    public static EditPostFragment newInstance() {
        Bundle args = new Bundle();
        EditPostFragment fragment = new EditPostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        post = (Post) getArguments().getSerializable("post");
        tvEditStoreName.setText(post.getPostStoreName());
        tvEditPostTime.setText(post.getPostTime());
        edtEditPostContent.setText(post.getPostContent());
        Glide.with(getActivity()).load(post.getPostStoreAvatar()).into(imvEditAvatarPostStore);
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

    }

}
