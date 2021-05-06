package com.xana.acg.fac.model.music.search;

import com.xana.acg.fac.model.music.Video;

import java.util.List;

public class SearchVideo implements ISearch {
    private int videoCount;
    private List<Video<List<Video.Creator>>> videos;

    @Override
    public List getDatas() {
        return videos;
    }

    @Override
    public int getCount() {
        return videoCount;
    }
}
