package com.xana.acg.fac.model.music;

public class Video<T> {

    /**
     * coverUrl : https://p2.music.126.net/Vbqe7GhWlvKStuDl_LpX3A==/109951163574081127.jpg
     * vid : 6F90F1F26221D567E789D197631FFA10
     * shareCount : 534
     * previewUrl : http://vodkgeyttp9.vod.126.net/cloudmusic/preview_1839197228_UzNkMAyn.webp?wsSecret=6981de49997bf95e6ae7c3493e11fb5d&wsTime=1618153886
     * playTime : 384789
     * title : 名侦探柯南【十年后的陌生人】哭惨了多少动漫迷？
     * durationms : 360000
     * praisedCount : 3094
     * commentCount : 279
     */
    private String coverUrl;
    private String vid;
    private long shareCount;
    private String previewUrl;
    private long playTime;
    private String title;
    private long durationms;
    private long praisedCount;
    private long commentCount;

    private T creator;

    public T getCreator() {
        return creator;
    }

    public static class Creator{
        private String userId;
        private String userName;

        public String getUserName() {
            return userName;
        }

        public String getUserId() {
            return userId;
        }
    }

    public void setCreator(T creator) {
        this.creator = creator;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDurationms(int durationms) {
        this.durationms = durationms;
    }

    public void setPraisedCount(int praisedCount) {
        this.praisedCount = praisedCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getVid() {
        return vid;
    }

    public long getShareCount() {
        return shareCount;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }


    public String getTitle() {
        return title;
    }


    public long getPlayTime() {
        return playTime;
    }

    public long getDurationms() {
        return durationms;
    }

    public long getPraisedCount() {
        return praisedCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    @Override
    public String toString() {
        return "Video{" +
                "coverUrl='" + coverUrl + '\'' +
                ", vid='" + vid + '\'' +
                ", shareCount=" + shareCount +
                ", previewUrl='" + previewUrl + '\'' +
                ", playTime=" + playTime +
                ", title='" + title + '\'' +
                ", durationms=" + durationms +
                ", praisedCount=" + praisedCount +
                ", commentCount=" + commentCount +
                ", creator=" + creator +
                '}';
    }
}
