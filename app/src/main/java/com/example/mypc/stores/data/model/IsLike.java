package com.example.mypc.stores.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsLike {

    @SerializedName("islikeId")
    @Expose
    private long islikeId;


    /**
     * No args constructor for use in serialization
     */
    public IsLike() {
    }

    public IsLike(long islikeId) {
        this.islikeId = islikeId;
    }

    public long getIslikeId() {
        return islikeId;
    }

    public void setIslikeId(long islikeId) {
        this.islikeId = islikeId;
    }
}
