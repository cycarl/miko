package com.xana.acg.fac.model.music.search;

import com.xana.acg.fac.model.music.Album;

import java.util.List;

public class SearchAlbum implements ISearch{
    private int albumCount;
    private List<Album> albums;

    @Override
    public List getDatas() {
        return albums;
    }

    @Override
    public int getCount() {
        return albumCount;
    }
}
