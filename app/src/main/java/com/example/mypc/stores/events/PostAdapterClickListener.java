package com.example.mypc.stores.events;

import android.view.View;

import com.example.mypc.stores.data.model.Post;

/**
 * Created by MyPC on 03/08/2017.
 */

public interface PostAdapterClickListener {
    void onClickImvAvatarPostStore(long postStoreId);
    void onClickBtnCmt(long postId, int adapterPosition);
    void onClickBtnLove(long postId, int position);
}
