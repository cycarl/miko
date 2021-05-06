package com.xana.acg.fac.model.music.search;

import com.xana.acg.fac.model.music.MV;

import java.util.List;

public class SearchMV implements ISearch{
    private int mvCount;
    private List<MV> mvs;

    @Override
    public List getDatas() {
        return mvs;
    }

    @Override
    public int getCount() {
        return mvCount;
    }
}
