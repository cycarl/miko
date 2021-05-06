package com.xana.acg.fac.model.music;

import com.xana.acg.fac.model.api.Resp;

import java.util.List;

public class MusicListCat extends Resp {
    private long total;
    private String cat;
    private List<MusicList> playlists;

    public long getTotal() {
        return total;
    }

    public String getCat() {
        return cat;
    }
    public List<MusicList> getPlaylists() {
        return playlists;
    }
}
