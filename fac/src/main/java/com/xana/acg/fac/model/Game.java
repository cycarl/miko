package com.xana.acg.fac.model;
import java.util.Date;
import java.util.List;

public class Game extends ACG{
    Date sellTime;
    String tags;
/*    List<String> pics;*/
    /*剧情简介*/
    String des1;
    /*汉化staff*/
    String des2;
    String views = "9999";
    String comments = "9999";

    public List<String> imgList;
    public UpUser upUser;
    public Statistic statistic;


    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getPic() {
        return super.getPic();
    }

    public Date getSellTime() {
        return sellTime;
    }

    public String getTags() {
        return tags;
    }

    public String getDes1() {
        return des1;
    }

    public String getDes2() {
        return des2;
    }

    public String getViews() {
        return views;
    }

    public String getComments() {
        return comments;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public UpUser getUpUser() {
        return upUser;
    }

    public Statistic getStatistic() {
        return statistic;
    }
}
