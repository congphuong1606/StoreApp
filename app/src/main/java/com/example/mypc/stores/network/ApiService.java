package com.example.mypc.stores.network;

import com.example.mypc.stores.data.model.Account;
import com.example.mypc.stores.data.model.Comment;
import com.example.mypc.stores.data.model.IsLike;
import com.example.mypc.stores.data.model.Location;
import com.example.mypc.stores.data.model.Post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MyPC on 02/08/2017.
 */

public interface ApiService {
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

    @PUT("/posts/coundCmt/{postId}")
    Observable<Integer> updatePostCmt(@Path("postId") long postId);

    @PUT("/posts/coundlove/{postId}")
    Observable<Integer> updatePostLove(@Path("postId") long postId, @Body int i);

    @DELETE("/posts/{postId}")
    Observable<Long> deletePost(@Path("postId") long postId);

    @GET("locations/{locationStoreId}")
    Observable<Location> getLocation(@Path("locationStoreId") long accId);

    @PUT("/accs/{accId}")
    Observable<Account> updateIsOnline(@Path("accId") long accId);

    @PUT("/posts/update")
    Observable<Post> updatePost(@Body Post post);

    @GET("/islike/one/{islikeId}")
    Observable<Integer> isLike(@Path("islikeId") long islikeId);

    @POST("/islike/liked")
    Observable<Integer> uploadIsLike(@Body IsLike isLike);

    @DELETE("/islike/{islikeId}")
    Observable<Integer> deleteIsLikePost(@Path("islikeId") long islikeId);

}
