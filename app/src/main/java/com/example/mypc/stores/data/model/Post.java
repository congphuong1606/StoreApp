package com.example.mypc.stores.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("postId")
    @Expose
    private String postId;
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
    private String postStoreId;
    @SerializedName("postStoreAvatar")
    @Expose
    private String postStoreAvatar;
    @SerializedName("postComment")
    @Expose
    private String postComment;

    /**
     * No args constructor for use in serialization
     *
     */
    public Post() {
    }

    /**
     *
     * @param postLove
     * @param postStoreName
     * @param postComment
     * @param postContent
     * @param postStoreAvatar
     * @param postStoreId
     * @param postId
     * @param postTime
     */
    public Post(String postId, String postContent, String postTime, String postStoreName, String postLove, String postStoreId, String postStoreAvatar, String postComment) {
        super();
        this.postId = postId;
        this.postContent = postContent;
        this.postTime = postTime;
        this.postStoreName = postStoreName;
        this.postLove = postLove;
        this.postStoreId = postStoreId;
        this.postStoreAvatar = postStoreAvatar;
        this.postComment = postComment;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
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

    public String getPostStoreId() {
        return postStoreId;
    }

    public void setPostStoreId(String postStoreId) {
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