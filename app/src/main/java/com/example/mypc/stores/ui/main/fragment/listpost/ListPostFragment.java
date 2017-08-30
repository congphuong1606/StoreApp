package com.example.mypc.stores.ui.main.fragment.listpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.IsLike;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.events.PostAdapterClickListener;
import com.example.mypc.stores.ui.adapter.PostAdapter;
import com.example.mypc.stores.ui.base.BaseFragment;
import com.example.mypc.stores.ui.main.MainActivity;
import com.example.mypc.stores.ui.storedetail.StoreDetailActivity;
import com.example.mypc.stores.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;


public class ListPostFragment extends BaseFragment implements ListPostView ,PostAdapterClickListener {

    @BindView(R.id.rcv_post)
    RecyclerView rcvPost;

    private static ArrayList<Post> posts;
    private static ArrayList<IsLike> isLikes;
    private static PostAdapter mAdapter;
    @Inject
    SharedPreferences mPreferences;
    @Inject
    ListPostPresenter listPostPresenter;
    private long mPostId;
    private int mPosition;
    private long accid;
    private Long islikeId;
    private boolean isCheck;



    @Override
    protected void initView(View view) {
        rcvPost.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        rcvPost.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        accid=mPreferences.getLong(Constants.PREF_ACC_ID,0);
        posts = new ArrayList<>();
        mAdapter = new PostAdapter(posts);
        rcvPost.setAdapter(mAdapter);
//        listPostPresenter.getIsLike(accid);
        listPostPresenter.getPost();
        mAdapter.setPostAdapter(this);
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_list_post;
    }

    @Override
    protected void onDestroyComposi() {

    }

    @Override
    protected void injectDependence(View view) {
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).injectTo(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onLoadPostsSuccess(ArrayList<Post> posts) {
        this.posts.addAll(posts);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String msg) {
        MainActivity.onShowDialogFail(msg);
    }

    @Override
    public void onUpdatePostLoveSuccess(Integer postLove) {
        for (Post p : posts) {
            if (mPostId == p.getPostId()) {
                p.setPostLove(postLove + "");
            }
        }
        mAdapter.notifyItemChanged(mPosition);
    }

    @Override
    public void onDeletePostSuccess(Long postId) {
        int mPostion = 0;
        int dem = -1;
        boolean isCheck = false;
        for (Post p : posts) {
            dem++;
            if (p.getPostId() == postId) {
                mPostion = dem;
                isCheck = true;
            }
        }
        if (isCheck) {
            posts.remove(mPostion);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void islikeSuccess(Integer integer) {
        if(integer==1){
            listPostPresenter.deleteIsLikePost(islikeId);
        }else if(integer==0){
            listPostPresenter.uploadIsLikePost(islikeId,accid,mPostId);
        }
    }

    @Override
    public void onUploadIsLikeSuccess() {
        listPostPresenter.updateCountPostLove(mPostId,0);
    }

    @Override
    public void onDeleteIsLikePostSuccess() {
        listPostPresenter.updateCountPostLove(mPostId,1);
    }

    @Override
    public void isLikePostsSuccess(boolean islike) {
        isCheck=islike;
    }

//    @Override
//    public void onGetAllIsLikeByAccSuccsess(ArrayList<IsLike> isLikes) {
//        this.isLikes.addAll(isLikes);
//    }

    @Override
    public void onClickImvAvatarPostStore(long postStoreId) {
        Intent intent = new Intent(getContext(), StoreDetailActivity.class);
        intent.putExtra("storeId", postStoreId);
        startActivity(intent);
    }

    @Override
    public void onClickBtnCmt(Post post, int adapterPosition) {
        ((MainActivity) getActivity()).showFragmentCmt(post,adapterPosition);
    }

    @Override
    public void onClickBtnLike(long postId, int position) {
        mPostId = postId;
        mPosition = position;
        islikeId=Long.valueOf(String.valueOf(accid).concat(String.valueOf(postId)));
        listPostPresenter.isLike(islikeId);

    }

    @Override
    public void onclickBtnMenu(Post post, int position) {
        mPostId = post.getPostId();
        long storeId = mPreferences.getLong(Constants.PREF_ACC_ID, 0);
        if (storeId == post.getPostStoreId()) {
            ((MainActivity) getActivity()).deletePost(post);
        } else {
            //
        }
    }

    @Override
    public void onClickImvPost(Post post) {
//        Intent intent=new Intent(getContext(), ImageViewActivity.class);
//        intent.putExtra("urlPostImage",postImage);
//        startActivity(intent);
//        startActivity(new Intent());
    ((MainActivity) getActivity()).showFragmentImaeViewer(post);
    }

//    @Override
//    public boolean isCheckIsLikePost() {
//        return isCheck;
//    }
//
//    @Override
//    public void checkLike(long islikeId) {
//        listPostPresenter.isLikePosts(islikeId);
//    }

    public void setNewPost(Post post) {
        posts.add(0,post);
        mAdapter.notifyDataSetChanged();
        rcvPost.smoothScrollToPosition(0);

    }

    public static void updateCountPostCmt(Integer countPostCmt, long cmtPostId, int mPostPosition) {
        for (Post p : posts) {
            if (p.getPostId()==cmtPostId) {
                p.setPostComment(countPostCmt + "");
            }
        }
        mAdapter.notifyItemChanged(mPostPosition);
    }

    public static void notifyPostAdapter(Long postId) {
        boolean isCheck=false;
        int mPostition = 0;
        int dem=-1;
        for (Post p : posts) {
            dem++;
            if (p.getPostId()==postId) {
                mPostition=dem;
               isCheck=true;
            }
        }
        if(isCheck){
            posts.remove(mPostition);
        }
        mAdapter.notifyDataSetChanged();
    }
}
