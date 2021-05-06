package com.xana.acg.fac.model.music;


import com.xana.acg.fac.model.api.Resp;
import com.xana.acg.fac.model.music.search.Al;

import java.util.List;

public class NewMusicCat extends Resp {

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public static class Data{
        /**
         * artists : [{}]
         * name : 1995
         * alias : ["节目「RUN！RUN！RAMPAGE！！」主题曲"]
         * id : 1838505247
         */
        private List<Al> artists;
        private String name;
        private List<String> alias;
        private String id;

        private Album album;

        public List<Al> getArtists() {
            return artists;
        }

        public String getName() {
            return name;
        }

        public List<String> getAlias() {
            return alias;
        }

        public String getId() {
            return id;
        }

        public Album getAlbum() {
            return album;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "artists=" + artists +
                    ", name='" + name + '\'' +
                    ", alias=" + alias +
                    ", id='" + id + '\'' +
                    ", album=" + album +
                    '}';
        }
    }


}
