package com.example.mypc.stores.ui.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.events.ProductAdapterClickListener;
import com.example.mypc.stores.ui.storedetail.StoreDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by congp on 8/25/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductviewHoder> {
    ArrayList<Post> posts;
    Context mContext;
    ProductAdapterClickListener mListener;


    public void setClickListener(ProductAdapterClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public ProductAdapter(ArrayList<Post> posts) {
        this.posts = posts;


    }

    @Override
    public ProductviewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_layout, parent, false);
        mContext = view.getContext();

        Display display = ((StoreDetailActivity) mContext).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int h = size.y;

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.height = h / 5;
        view.setLayoutParams(layoutParams);
        return new ProductviewHoder(view);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }


    @Override
    public void onBindViewHolder(ProductviewHoder holder, int position) {
        Post post = posts.get(position);
        String s = post.getPostImage();
        if (post.getPostImage() != null) {
            Glide.with(mContext).load(s).into(holder.imvPostStoreImage);
        }
        holder.tvTitlePost.setText(post.getPostContent() + "");
    }

    public class ProductviewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_post_store_image)
        ImageView imvPostStoreImage;
        @BindView(R.id.tv_title_post)
        TextView tvTitlePost;

        public ProductviewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(posts.get(getAdapterPosition()));
                }
            });
        }

    }
}
