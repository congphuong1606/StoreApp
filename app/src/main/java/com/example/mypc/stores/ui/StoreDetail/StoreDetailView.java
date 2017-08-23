package com.example.mypc.stores.ui.StoreDetail;

import com.example.mypc.stores.data.model.Post;

import java.util.ArrayList;

/**
 * Created by congp on 8/23/2017.
 */

public interface StoreDetailView {
    void onLoadDataSuccess(ArrayList<Post> posts);
    void onFail(String msg);
}
