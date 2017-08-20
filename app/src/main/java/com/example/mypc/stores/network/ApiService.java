package com.example.mypc.stores.network;

import com.example.mypc.stores.data.model.Account;
import com.example.mypc.stores.data.model.Comment;
import com.example.mypc.stores.data.model.Post;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MyPC on 02/08/2017.
 */

public interface ApiService {
    @GET("post/{type}")
    Observable<ArrayList<Post>> getListPost(@Path("type") String type);

    @GET("posts")
    Observable<ArrayList<Post>> getListAllPost();

    @POST("post")
    Observable<Object> loadPost();

    @GET("cmts/{cmtPostId}")
    Observable<ArrayList<Comment>> getPostCmts(
            @Path("cmtPostId") long cmtPostId);

    @GET("accs")
    Observable<ArrayList<Account>> getAccounts();

    @POST("accs")
    Observable<Account> saveNewAccount(@Body Account account);

    @POST("posts")
    Observable<Post> saveNewPost(@Body Post post);
    @POST("cmts")
    Observable<Comment> saveNewCmt(@Body Comment cmt);
}
