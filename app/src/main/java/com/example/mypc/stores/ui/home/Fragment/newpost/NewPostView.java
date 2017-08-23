package com.example.mypc.stores.ui.home.Fragment.newpost;

import com.example.mypc.stores.data.model.Post;

/**
 * Created by MyPC on 04/08/2017.
 */

public interface NewPostView {
    void onLoadPostSuccess(Post post);
    void onFail(String s);
    void onUploadPicSuccess(String picUrl);
}
