package com.example.mypc.stores.ui.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Comment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MyPC on 02/08/2017.
 */

public class CmtAdapter extends RecyclerView.Adapter<CmtAdapter.CmtViewHolder> {

    View v;
    Context context;
    ArrayList<Comment> comments;

    public CmtAdapter(ArrayList<Comment> comments) {
        this.comments = comments;

    }

    @Override
    public CmtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.
                getContext()).inflate(R.layout.item_cmt_layout, parent, false);
        context = v.getContext();
        return new CmtViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CmtViewHolder holder, int position) {
        Comment comment = comments.get(position);
        Glide.with(context).load(comment.getCmtAvatarUser()).into(holder.imvCmtUser);
        holder.tvCmtContent.setText(comment.getCmtContent());
        holder.tvCmtTime.setText(comment.getCmtTime());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CmtViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_cmt_user)
        CircleImageView imvCmtUser;
        @BindView(R.id.tv_cmt_content)
        TextView tvCmtContent;
        @BindView(R.id.tv_cmt_time)
        TextView tvCmtTime;


        public CmtViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

