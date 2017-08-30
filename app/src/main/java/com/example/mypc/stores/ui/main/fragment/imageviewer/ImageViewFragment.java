package com.example.mypc.stores.ui.main.fragment.imageviewer;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.base.BaseFragment;
import com.example.mypc.stores.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.relex.photodraweeview.PhotoDraweeView;


public class ImageViewFragment extends BaseFragment implements ImvView {
    String urlImage;
    @BindView(R.id.photoDraweeView)
    PhotoDraweeView photoDraweeView;
    @BindView(R.id.tv_post_content_image)
    TextView tvPostContentImage;
    @BindView(R.id.btn_like_image)
    Button btnLikeImage;
    @BindView(R.id.btn_cmt_image)
    Button btnCmtImage;
    @BindView(R.id.btn_share_image)
    Button btnShareImage;
    @BindView(R.id.tv_count_cmt_image)
    TextView tvCountCmtImage;
    @Inject
    ImvPresenter mImvPresenter;

    @Override
    protected void injectDependence(View view) {
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).injectTo(this);
    }


    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        Post post = (Post) getArguments().getSerializable("post");
        urlImage = post.getPostImage();
        tvPostContentImage.setText(post.getPostContent());
        tvCountCmtImage.setText(post.getPostComment()+"comment");
        photoDraweeView.setPhotoUri(Uri.parse(urlImage));
        photoDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
               mImvPresenter.likePost(post.getPostId());
                return true;
            }
        });

    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_image_view;
    }

    @Override
    protected void onDestroyComposi() {

    }






    @OnClick({R.id.tv_post_content_image, R.id.btn_like_image, R.id.btn_cmt_image, R.id.btn_share_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_post_content_image:
                tvCountCmtImage.setMaxLines(15);
                break;
            case R.id.btn_like_image:
                break;
            case R.id.btn_cmt_image:
                break;
            case R.id.btn_share_image:
                break;
        }
    }

}
