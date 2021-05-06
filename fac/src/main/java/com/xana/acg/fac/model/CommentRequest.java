package com.xana.acg.fac.model;

import java.util.Date;

public class CommentRequest {
    String cid;
    String uid;
    String aid;
    String commentText;
    Date commentTime = new Date();
    String replyId="0";
    Integer stars=0;

    public CommentRequest(String cid, String uid, String aid, String commentText, Date commentTime, String replyId) {
        this.cid = cid;
        this.uid = uid;
        this.aid = aid;
        this.commentText = commentText;
        this.commentTime = commentTime;
        this.replyId = replyId;
    }

    public CommentRequest(String cid, String uid, String aid, String commentText) {
        this.cid = cid;
        this.uid = uid;
        this.aid = aid;
        this.commentText = commentText;
    }

    public String getCid() {
        return cid;
    }

    public String getUid() {
        return uid;
    }

    public String getAid() {
        return aid;
    }

    public String getCommentText() {
        return commentText;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public String getReplyId() {
        return replyId;
    }

    public Integer getStars() {
        return stars;
    }
}
