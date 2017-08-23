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

    @GET("/posts/all")
    Observable<ArrayList<Post>> getListAllPost();


    @GET("/cmts/{cmtPostId}")
    Observable<ArrayList<Comment>> getPostCmts(@Path("cmtPostId") long cmtPostId);

    @GET("/accs/all")
    Observable<ArrayList<Account>> getAccounts();

    @POST("/accs/add")
    Observable<Account> saveNewAccount(@Body Account account);

    @POST("/posts/add")
    Observable<Post> saveNewPost(@Body Post post);

    @POST("/cmts/add")
    Observable<Comment> saveNewCmt(@Body Comment cmt);

    @GET("/posts/{postStoreId}")
    Observable<ArrayList<Post>> getListPostStore(@Path("postStoreId") long postStoreId);


}
