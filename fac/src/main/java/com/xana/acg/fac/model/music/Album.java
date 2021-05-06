package com.xana.acg.fac.model.music;

import com.xana.acg.fac.model.music.search.Al;

import java.util.List;

public class Album {

    /**
     * picUrl : http://p4.music.126.net/jek9r6I9o6s7eE6UedaYqw==/109951165904734447.jpg
     * company : 索尼音乐
     * subType : 录音室版
     * tags :
     * status : 1
     */

    private String id;
    private String name;
    private List<String> alias;

    private String picUrl;
    private String company;
    private String subType;
    private String tags;
    private int status;
    private boolean isSub;
    private List<Al> artists;

    public String getPicUrl() {
        return picUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getAlias() {
        return alias;
    }

    public String getCompany() {
        return company;
    }

    public String getSubType() {
        return subType;
    }

    public String getTags() {
        return tags;
    }

    public int getStatus() {
        return status;
    }

    public boolean isSub() {
        return isSub;
    }

    public List<Al> getArtists() {
        return artists;
    }
}