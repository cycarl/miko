package com.xana.acg.fac.model.music;

public class Musicer {

    /**
     * birthday : 653732121603
     * backgroundUrl : http://p1.music.126.net/OzaguBWbUx-4W7WvUyR6Lw==/109951165831817203.jpg
     * gender : 1
     * avatarUrl : http://p1.music.126.net/UnVOQd2uQ5yv4LGbJw3xTQ==/109951165781028035.jpg
     * signature :
     * nickname : 素人库
     * userId : 505343731
     */
    private long birthday;
    private String backgroundUrl;
    private int gender;
    private String avatarUrl;
    private String signature;
    private String nickname;
    private int userId;

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getBirthday() {
        return birthday;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public int getGender() {
        return gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getSignature() {
        return signature;
    }

    public String getNickname() {
        return nickname;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Musicer{" +
                "birthday=" + birthday +
                ", backgroundUrl='" + backgroundUrl + '\'' +
                ", gender=" + gender +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", signature='" + signature + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userId=" + userId +
                '}';
    }
}
