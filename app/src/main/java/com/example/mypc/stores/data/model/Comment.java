package com.example.mypc.stores.data.model;

/**
 * Created by MyPC on 02/08/2017.
 */

public class Comment {
    String cmtId;
    String cmtUser;
    String cmtAvatarUser;
    String cmtPost;
    String cmtContent;
    String cmtTime;

    public Comment(String cmtId, String cmtUser,String cmtAvatarUser, String cmtPost, String cmtContent, String cmtTime) {
        this.cmtId = cmtId;
        this.cmtUser = cmtUser;
        this.cmtAvatarUser=cmtAvatarUser;
        this.cmtPost = cmtPost;
        this.cmtContent = cmtContent;
        this.cmtTime = cmtTime;
    }

    public String getCmtAvatarUser() {
        return cmtAvatarUser;
    }

    public void setCmtAvatarUser(String cmtAvatarUser) {
        this.cmtAvatarUser = cmtAvatarUser;
    }

    public String getCmtId() {
        return cmtId;
    }

    public void setCmtId(String cmtId) {
        this.cmtId = cmtId;
    }

    public String getCmtUser() {
        return cmtUser;
    }

    public void setCmtUser(String cmtUser) {
        this.cmtUser = cmtUser;
    }

    public String getCmtPost() {
        return cmtPost;
    }

    public void setCmtPost(String cmtPost) {
        this.cmtPost = cmtPost;
    }

    public String getCmtContent() {
        return cmtContent;
    }

    public void setCmtContent(String cmtContent) {
        this.cmtContent = cmtContent;
    }

    public String getCmtTime() {
        return cmtTime;
    }

    public void setCmtTime(String cmtTime) {
        this.cmtTime = cmtTime;
    }

}
