package com.xana.acg.fac.model.music;

public class NewMusic {
    /**
     * picUrl : http://p1.music.126.net/WKpANHmD3qE60RX9szS7vQ==/109951165811087237.jpg
     * canDislike : true
     * name : 今天的我已经打烊
     * copywriter : null
     * id : 1830066193
     * type : 4
     * trackNumberUpdateTime : null
     * alg : hot_server
     */
    private String picUrl;
    private boolean canDislike;
    private String name;
    private String copywriter;
    private int id;
    private int type;
    private String trackNumberUpdateTime;
    private String alg;

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setCanDislike(boolean canDislike) {
        this.canDislike = canDislike;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCopywriter(String copywriter) {
        this.copywriter = copywriter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTrackNumberUpdateTime(String trackNumberUpdateTime) {
        this.trackNumberUpdateTime = trackNumberUpdateTime;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public boolean isCanDislike() {
        return canDislike;
    }

    public String getName() {
        return name;
    }

    public String getCopywriter() {
        return copywriter;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getTrackNumberUpdateTime() {
        return trackNumberUpdateTime;
    }

    public String getAlg() {
        return alg;
    }
}
