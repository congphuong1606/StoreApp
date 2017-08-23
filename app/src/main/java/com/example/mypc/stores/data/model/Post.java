package com.example.mypc.stores.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {

    @SerializedName("postId")
    @Expose
    private long postId;
    @SerializedName("postContent")
    @Expose
    private String postContent;
    @SerializedName("postTime")
    @Expose
    private String postTime;
    @SerializedName("postStoreName")
    @Expose
    private String postStoreName;
    @SerializedName("postLove")
    @Expose
    private String postLove;
    @SerializedName("postStoreId")
    @Expose
    private long postStoreId;
    @SerializedName("postStoreAvatar")
    @Expose
    private String postStoreAvatar;
    @SerializedName("postComment")
    @Expose
    private String postComment;
    @SerializedName("postImage")
    @Expose
    private String postImage;

    /**
     * No args constructor for use in serialization
     *
     */
    public Post() {
    }

    public Post(long postId,
                String postContent,
                String postTime,
                String postComment,
                String postLove,
                long postStoreId,
                String postStoreName,
                String postStoreAvatar,
                String postImage) {
        super();
        this.postId = postId;
        this.postContent = postContent;
        this.postTime = postTime;
        this.postStoreName = postStoreName;
        this.postLove = postLove;
        this.postStoreId = postStoreId;
        this.postStoreAvatar = postStoreAvatar;
        this.postComment = postComment;
            this.postImage=postImage;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostStoreName() {
        return postStoreName;
    }

    public void setPostStoreName(String postStoreName) {
        this.postStoreName = postStoreName;
    }

    public String getPostLove() {
        return postLove;
    }

    public void setPostLove(String postLove) {
        this.postLove = postLove;
    }

    public long getPostStoreId() {
        return postStoreId;
    }

    public void setPostStoreId(long postStoreId) {
        this.postStoreId = postStoreId;
    }

    public String getPostStoreAvatar() {
        return postStoreAvatar;
    }

    public void setPostStoreAvatar(String postStoreAvatar) {
        this.postStoreAvatar = postStoreAvatar;
    }

    public String getPostComment() {
        return postComment;
    }

    public void setPostComment(String postComment) {
        this.postComment = postComment;
    }

}