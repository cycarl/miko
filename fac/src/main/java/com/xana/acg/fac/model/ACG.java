package com.xana.acg.fac.model;

public abstract class ACG {
    private String id;
    private String title;
    private String pic;
/*
    private List<Source> sources;
    private List<Comment> comments;
    private UpUser upUser;
    */

    public String getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPic() {
        return pic;
    }
}
