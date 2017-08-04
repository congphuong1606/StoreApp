package com.example.mypc.stores.ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.events.RecyclerViewClickListener;
import com.example.mypc.stores.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MyPC on 02/08/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostviewHoder> {
    ArrayList<Post> posts;
    Context mContext;



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
    public void onBindViewHolder(PostviewHoder holder, int position) {
        Post post = posts.get(position);
        holder.tvStoreName.setText(post.getPostStoreName() + "");
        holder.tvPostTime.setText(post.getPostTime() + "");
        holder.tvPostLove.setText(post.getPostLove() + " yêu thích");
        Glide.with(mContext).load(post.getPostStoreAvatar()).into(holder.imvAvatarPostStore);
        holder.tvPostCmt.setText(post.getPostComment() + " comment");
        holder.tvPostContent.setText(post.getPostContent() + "");
        holder.btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)mContext).onLoadCmtFragment();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostviewHoder extends RecyclerView.ViewHolder  {
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
        @BindView(R.id.btn_love)
        Button btnLove;
        @BindView(R.id.btn_cmt)
        Button btnCmt;
        @BindView(R.id.btn_share)
        Button btnShare;
        @BindView(R.id.tv_post_content)
        TextView tvPostContent;
        private RecyclerViewClickListener mListener;

        public PostviewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
