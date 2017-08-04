package com.example.mypc.stores.network;

import com.example.mypc.stores.data.model.Post;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MyPC on 02/08/2017.
 */

public interface ApiService {
    @GET("post/{type}")
    Observable<ArrayList<Post>> getListPost(@Path("type")String type);
    @GET("post")
    Observable<ArrayList<Post>> getListAllPost();
    @POST("post")
    Observable<Object> loadPost();
}
