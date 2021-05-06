package com.xana.acg.fac.model.music;

import com.xana.acg.fac.model.api.Resp;

import java.util.List;

public class MusicListDetail extends Resp {
    private PlayList playlist;

    @Override
    public String toString() {
        return "MusicListDetail{" +
                "playlist=" + playlist +
                '}';
    }

    public PlayList getPlaylist() {
        return playlist;
    }

    public static class PlayList{
        /**
         * coverImgUrl : https://p1.music.126.net/UrDMJIvfB5o_migGHIQejg==/109951165496126297.jpg
         * playCount : 6250952192
         * shareCount : 389220
         * trackCount : 35
         * creator : {}
         * createTime : 1577330551437
         * name : 今天从《Beautiful World》听起|私人雷达
         * subscribedCount : 26706015
         * description : 你爱的歌，值得反复聆听
         私人雷达，每日更新，收藏你的最爱
         * id : 3136952023
         * tracks : [{}]
         * commentCount : 811453
         */
        private String id;
        private String name;
        private String description;
        private String coverImgUrl;
        private long playCount;
        private long shareCount;
        private long trackCount;
        private long createTime;
        private long subscribedCount;
        private long commentCount;
        private Musicer creator;
        private List<Music> tracks;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public long getPlayCount() {
            return playCount;
        }

        public long getShareCount() {
            return shareCount;
        }

        public long getTrackCount() {
            return trackCount;
        }

        public long getCreateTime() {
            return createTime;
        }

        public long getSubscribedCount() {
            return subscribedCount;
        }

        public long getCommentCount() {
            return commentCount;
        }

        public Musicer getCreator() {
            return creator;
        }

        public List<Music> getTracks() {
            return tracks;
        }

        @Override
        public String toString() {
            return "PlayList{" +
                    "tracks=" + tracks +
                    '}';
        }
    }
}
