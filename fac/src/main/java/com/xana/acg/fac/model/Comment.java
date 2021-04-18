package com.xana.acg.fac.model;

import java.util.Date;
import java.util.Set;

public class Comment {
    String cid;
    Integer cuserId;
    String cuser;
    String cPic;
    Integer ruserId;
    String ruser;
    Date cTime;
    String cText;
    Integer stars;
    Set<Comment> replyList;
}
