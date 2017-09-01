package com.example.mypc.stores.ui.main.fragment.imageviewer;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.base.BaseFragment;
import com.example.mypc.stores.ui.main.MainActivity;
import com.example.mypc.stores.ui.main.fragment.listpost.ListPostFragment;
import com.example.mypc.stores.ui.main.utils.PostItemUtils;

import javax.inject.Inject;

import butterknife.BindView;
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
    @BindView(R.id.tv_count_like_post)
    TextView tvCountLikePost;
    @BindView(R.id.view_heart)
    View viewHeart;
    Unbinder unbinder;

    private Post post;
    private int mPosition;

    @Override
    protected void injectDependence(View view) {
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).injectTo(this);
    }


    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        post = (Post) getArguments().getSerializable("post");
        mPosition = getArguments().getInt("position");
        setView();
        photoDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mImvPresenter.isLike(post.getPostId());
                return true;
            }
        });

    }

    private void setView() {
        if (Integer.valueOf(post.getPostComment()) > 0) {
            tvCountCmtImage.setText(post.getPostComment() + " comment");
        }
        if (Integer.valueOf(post.getPostLove()) > 0) {
            tvCountLikePost.setText(post.getPostLove() + " yêu thích");
        }
        tvPostContentImage.setText(post.getPostContent());
        urlImage = post.getPostImage();
        photoDraweeView.setPhotoUri(Uri.parse(urlImage));
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_image_view;
    }

    @Override
    protected void onDestroyComposi() {
    }

    @OnClick({R.id.tv_post_content_image, R.id.btn_like_image,
            R.id.btn_cmt_image, R.id.btn_share_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_post_content_image:
                tvCountCmtImage.setMaxLines(15);
                break;
            case R.id.btn_like_image:
                mImvPresenter.isLike(post.getPostId());
                break;
            case R.id.btn_cmt_image:
                ((MainActivity) getActivity()).showFragmentCmt(post, mPosition);
                ((MainActivity) getActivity()).setOpenFragment();
                break;
            case R.id.btn_share_image:
                PostItemUtils.sendImageToFriendFaceBook(getActivity(), post.getPostImage());
                break;
        }
    }

    @Override
    public void onIsLikeSuccess(Integer integer) {
        if (integer == 1) {
            mImvPresenter.updateCountLike(post.getPostId(), 1);
        } else mImvPresenter.updateCountLike(post.getPostId(), 0);
    }

    @Override
    public void onRequestFailure(String smg) {
        onShowErorr(smg);
    }

    @Override
    public void onUpdateCountLikeSuccess(Integer countLove) {
        if (countLove == 0) {
            tvCountLikePost.setText("");
        } else tvCountLikePost.setText(countLove + " yêu thích");
        if (countLove > Integer.valueOf(post.getPostLove())) {
            mImvPresenter.addLikePost(post.getPostId());
        } else {
            mImvPresenter.deleteLikePost(post.getPostId());
        }
        post.setPostLove(String.valueOf(countLove));

    }


    @Override
    public void onUpdateIslikeSuccess(Integer integer) {
        if (integer == 1) {
            showAnimationLove();
            ListPostFragment.notifyPostPosition(post, mPosition);
        } else if (integer == 0) {
            ListPostFragment.notifyPostPosition(post, mPosition);
        }
    }

    private void showAnimationLove() {
        PostItemUtils.showAnimationHeart(getActivity(), viewHeart);
    }


}