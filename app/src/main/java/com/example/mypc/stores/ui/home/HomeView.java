package com.example.mypc.stores.ui.home;

import com.example.mypc.stores.data.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 02/08/2017.
 */

public interface HomeView {
    void onUpdatePostsSuccess(ArrayList<Post> posts);
    void onFail(String msg);
}
