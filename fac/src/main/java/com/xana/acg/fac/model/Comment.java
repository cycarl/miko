package com.xana.acg.fac.model;

import java.util.Date;
import java.util.Set;

public class Comment {
    String cid;
    String cuserId;
    String cuser;
    String cpic;
    String ruserId;
    String ruser;
    Date ctime;
    String ctext;
    Integer stars;
    Set<Comment> replyList;


    public Comment(String cid, String cuserId, String cuser, String cpic, String ruserId, String ruser, Date ctime, String ctext) {
        this.cid = cid;
        this.cuserId = cuserId;
        this.cuser = cuser;
        this.cpic = cpic;
        this.ruserId = ruserId;
        this.ruser = ruser;
        this.ctime = ctime;
        this.ctext = ctext;
    }

    public Comment(String cid, String cuserId, String cuser, String cpic, Date ctime, String ctext) {
        this.cid = cid;
        this.cuserId = cuserId;
        this.cuser = cuser;
        this.cpic = cpic;
        this.ctime = ctime;
        this.ctext = ctext;
    }


    public String getCid() {
        return cid;
    }

    public String getCuserId() {
        return cuserId;
    }

    public String getCuser() {
        return cuser;
    }

    public String getCpic() {
        return cpic;
    }

    public String getRuserId() {
        return ruserId;
    }

    public String getRuser() {
        return ruser;
    }

    public Date getCtime() {
        return ctime;
    }

    public String getCtext() {
        return ctext;
    }

    public Integer getStars() {
        return stars;
    }

    public Set<Comment> getReplyList() {
        return replyList;
    }

    public void setRuserId(String ruserId) {
        this.ruserId = ruserId;
    }

    public void setRuser(String ruser) {
        this.ruser = ruser;
    }
}
