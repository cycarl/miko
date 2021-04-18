package com.xana.acg.fac.model.music;

public class SongList {
    /**
     * picUrl : https://p2.music.126.net/p868nK-_3WwLcFZhO3lFQg==/109951165854538793.jpg
     * playCount : 738224
     * trackCount : 181
     * canDislike : true
     * name : 原神OST(收录至《漩涡、落星与冰山》)
     * copywriter : 热门推荐
     * highQuality : false
     * id : 897784673
     * type : 0
     * trackNumberUpdateTime : 1617337161750
     * alg : cityLevel_unknow
     */
    private String picUrl;
    private int playCount;
    private int trackCount;
    private boolean canDislike;
    private String name;
    private String copywriter;
    private boolean highQuality;
    private String id;
    private int type;
    private long trackNumberUpdateTime;
    private String alg;

    public String getPicUrl() {
        return picUrl;
    }

    public int getPlayCount() {
        return playCount;
    }

    public int getTrackCount() {
        return trackCount;
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

    public boolean isHighQuality() {
        return highQuality;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public long getTrackNumberUpdateTime() {
        return trackNumberUpdateTime;
    }

    public String getAlg() {
        return alg;
    }

    @Override
    public String toString() {
        return "SongList{" +
                "picUrl='" + picUrl + '\'' +
                ", playCount=" + playCount +
                ", trackCount=" + trackCount +
                ", canDislike=" + canDislike +
                ", name='" + name + '\'' +
                ", copywriter='" + copywriter + '\'' +
                ", highQuality=" + highQuality +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", trackNumberUpdateTime=" + trackNumberUpdateTime +
                ", alg='" + alg + '\'' +
                '}';
    }
}


