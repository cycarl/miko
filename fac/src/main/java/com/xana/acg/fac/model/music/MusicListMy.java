package com.xana.acg.fac.model.music;

import com.xana.acg.fac.model.api.Resp;

import java.util.List;

public class MusicListMy extends Resp {
    private String version;
    private List<MusicList> playlist;

    public List<MusicList> getPlaylist() {
        return playlist;
    }

    public String getVersion() {
        return version;
    }
}
