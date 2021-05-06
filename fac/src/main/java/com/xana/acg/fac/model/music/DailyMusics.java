package com.xana.acg.fac.model.music;

import com.xana.acg.fac.model.api.Resp;

import java.util.List;

public class DailyMusics extends Resp {
    private DailySong data;

    public DailySong getData() {
        return data;
    }

    public class DailySong{
        private List<Music> dailySongs;
        public List<Music> getDailySongs() {
            return dailySongs;
        }
    }
}


