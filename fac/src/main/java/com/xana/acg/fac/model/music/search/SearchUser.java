package com.xana.acg.fac.model.music.search;

import com.xana.acg.fac.model.account.Profile;

import java.util.List;

public class SearchUser implements ISearch{
    private int userprofileCount;
    private List<Profile> userprofiles;

    @Override
    public List getDatas() {
        return userprofiles;
    }

    @Override
    public int getCount() {
        return userprofileCount;
    }
}
