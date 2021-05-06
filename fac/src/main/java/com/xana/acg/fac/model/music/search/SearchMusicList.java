package com.xana.acg.fac.model.music.search;

import com.xana.acg.fac.model.music.MusicList;

import java.util.List;

public class SearchMusicList implements ISearch {
    private List<MusicList> playlists;
    private int playlistCount;

    @Override
    public List getDatas() {
        return playlists;
    }

    @Override
    public int getCount() {
        return playlistCount;
    }
}
