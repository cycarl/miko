package com.xana.acg.fac.model.music.search;

import com.xana.acg.fac.model.music.Music;

import java.util.List;

public class SearchSingleMusic implements ISearch{
    private List<Music> songs;
    private int songCount;

    @Override
    public List getDatas() {
        return songs;
    }

    @Override
    public int getCount() {
        return songCount;
    }
}
