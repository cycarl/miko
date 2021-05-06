package com.xana.acg.fac.model.anime;

import java.util.List;
import java.util.SortedSet;

public class Bangumi implements Comparable<Bangumi> {

    /**
     * date : 2021-05-15
     * is_today : true
     * updates : [{"update_to":"第78话","cover_url":"http://i0.hdslb.com/bfs/bangumi/image/d72ec1662201e5fe82a53fdc806537a1a011bece.jpg","update_time":"2021-05-15 10:00:00","title":"小仓鼠笛笛的直播日记"}]
     * day_of_week : 6
     */
    private String date;
    private boolean is_today;
    private List<Update> updates;
    private String day_of_week;

    public String getDate() {
        return date;
    }

    public boolean isIs_today() {
        return is_today;
    }

    public List<Update> getUpdates() {
        return updates;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    @Override
    public String toString() {
        return "Bangumi{" +
                "date='" + date + '\'' +
                ", is_today=" + is_today +
                ", day_of_week='" + day_of_week + '\'' +
                '}';
    }

    @Override
    public int compareTo(Bangumi b) {
        return -date.compareTo(b.date);
    }

    public static class Update {
        /**
         * update_to : 第78话
         * cover_url : http://i0.hdslb.com/bfs/bangumi/image/d72ec1662201e5fe82a53fdc806537a1a011bece.jpg
         * update_time : 2021-05-15 10:00:00
         * title : 小仓鼠笛笛的直播日记
         */
        private String update_to;
        private String cover_url;
        private String update_time;
        private String title;

        public String getUpdate_to() {
            return update_to;
        }

        public String getCover_url() {
            return cover_url;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "Update{" +
                    "update_to='" + update_to + '\'' +
                    ", cover_url='" + cover_url + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
