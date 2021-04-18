package com.xana.acg.fac.model.music;

import com.xana.acg.fac.model.api.Result;

import java.util.List;

public class SearchResult extends Result {
    private List<Song> songs;
    private int songCount;

    public List<Song> getSongs() {
        return songs;
    }

    public int getSongCount() {
        return songCount;
    }
    public class Song {
        @Override
        public String toString() {
            return "Song{" +
                    "name='" + name + '\'' +
                    ", mv=" + mv +
                    ", id='" + id + '\'' +
                    ", alia=" + alia +
                    ", ar=" + ar +
                    ", al=" + al +
                    '}';
        }
        /**
         * name : Believe in you
         * mv : 10956201
         * id : 1824473220
         * alia : ["TV动画《Re:从零开始的异世界生活 第2期后半部分》ED2"]
         */

        private String name;
        private int mv;
        private String id;
        private List<String> alia;
        private List<Ar> ar;
        private Al al;

        public String getName() {
            return name;
        }

        public int getMv() {
            return mv;
        }

        public String getId() {
            return id;
        }

        public List<String> getAlia() {
            return alia;
        }

        public List<Ar> getAr() {
            return ar;
        }

        public Al getAl() {
            return al;
        }

        public class Ar {
            /**
             * name : nonoc
             * id : 30460993
             */
            private String name;
            private int id;

            public String getName() {
                return name;
            }

            public int getId() {
                return id;
            }

            @Override
            public String toString() {
                return "Ar{" +
                        "name='" + name + '\'' +
                        ", id=" + id +
                        '}';
            }
        }

        public class Al {
            /**
             * picUrl : http://p3.music.126.net/VB5U0oYegoCv_lwlL-IRJg==/109951165289173397.jpg
             * name : Memento
             * id : 94823877
             */
            private String picUrl;
            private String name;
            private int id;

            public String getPicUrl() {
                return picUrl;
            }

            public String getName() {
                return name;
            }

            public int getId() {
                return id;
            }

            @Override
            public String toString() {
                return "Al{" +
                        "picUrl='" + picUrl + '\'' +
                        ", name='" + name + '\'' +
                        ", id=" + id +
                        '}';
            }
        }
    }

}
