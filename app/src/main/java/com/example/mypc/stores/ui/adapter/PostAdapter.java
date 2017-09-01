package com.example.mypc.stores.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.IsLike;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.events.PostAdapterClickListener;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by MyPC on 02/08/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostviewHoder> {
    ArrayList<Post> posts;
    Context mContext;
    PostAdapterClickListener mListener;



    public void setPostAdapter(PostAdapterClickListener mListener) {
        this.mListener = mListener;

    }

    public PostAdapter(ArrayList<Post> posts) {
        this.posts = posts;



    }

    @Override
    public PostviewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post_layout, parent, false);
        mContext = view.getContext();
        return new PostviewHoder(view);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }


    @Override
    public void onBindViewHolder(PostviewHoder holder, int position) {

        Post post = posts.get(position);
        holder.tvStoreName.setText(post.getPostStoreName() + "");
        holder.tvPostTime.setText(post.getPostTime() + "");
        holder.tvPostLove.setText(post.getPostLove() + " yêu thích");
        holder.tvPostCmt.setText(post.getPostComment() + " comment");
        holder.tvPostContent.setText(post.getPostContent() + "");
        Glide.with(mContext).load(post.getPostStoreAvatar()).into(holder.imvAvatarPostStore);
        if (post.getPostImage() != null) {
//            holder.imvPostImage.setImageURI(Uri.parse(post.getPostImage()));
         Glide.with(mContext).load(post.getPostImage()).into(holder.imvPostImage);
        }
        holder.imvAvatarPostStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickImvAvatarPostStore(post.getPostStoreId());
            }
        });
        holder.tvPostCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickBtnCmt(post, position);
            }
        });
        holder.tvPostLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickBtnLike(post.getPostId(), position,holder.viewheartPost);
            }
        });
        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onclickBtnMenu(post, position);
            }
        });
        holder.tvPostContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvPostContent.setMaxLines(200);
            }
        });
        holder.imvPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickImvPost(post,position);
            }
        });
        holder.imvPostImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mListener.onClickBtnLike(post.getPostId(), position,holder.viewheartPost);
                return true;
            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickBtnShare(post.getPostImage());
            }
        });
//        //set màu cho like
//        int islike=holder.itemView.getContext().getResources().getColor(R.color.red);
//        int dislike=holder.itemView.getContext().getResources().getColor(R.color.text);
//        long islikeId = Long.valueOf(String.valueOf(accId).concat(String.valueOf(post.getPostId())));
//        for (IsLike isLike : isLikes) {
//            if (isLike.getIslikeId() == islikeId) {
//                holder.tvPostLove.setTextColor(islike);
//            }else holder.tvPostLove.setTextColor(dislike);
//        }

//      /*
//        int islike=holder.itemView.getContext().getResources().getColor(R.color.red);
//        int dislike=holder.itemView.getContext().getResources().getColor(R.color.text);
//        long islikeId = Long.valueOf(String.valueOf(accId).concat(String.valueOf(post.getPostId())));
//        mListener.checkLike(islikeId);
//        if (mListener.isCheckIsLikePost()) {
//            holder.tvPostLove.setTextColor(islike);
//        }else holder.tvPostLove.setTextColor(dislike);*/

    }


    public class PostviewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_menu)
        Button btnMenu;
        @BindView(R.id.imv_post_image)
        SimpleDraweeView imvPostImage;
        @BindView(R.id.imv_avatar_post_store)
        CircleImageView imvAvatarPostStore;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.tv_post_time)
        TextView tvPostTime;
        @BindView(R.id.tv_post_love)
        TextView tvPostLove;
        @BindView(R.id.tv_post_cmt)
        TextView tvPostCmt;
        @BindView(R.id.btn_share)
        Button btnShare;
        @BindView(R.id.tv_post_content)
        TextView tvPostContent;
        @BindView(R.id.view_heart_post)
        View viewheartPost;


        public PostviewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            btnShare.setOnClickListener(this);
//            btnCmt.setOnClickListener(this);
//            btnLove.setOnClickListener(this);
////            imvAvatarPostStore.setOnClickListener(this);

        }

    }


}
