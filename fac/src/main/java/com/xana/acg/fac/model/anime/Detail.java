package com.xana.acg.fac.model.anime;

import java.util.List;

public class Detail {

    /**
     * cover_url : http://puui.qpic.cn/fans_admin/0/3_1206436828_1578036245160/0
     * module : api.anime.zzzfun
     * description : 菜月昴（小林裕介 配音）是一位平凡的十七岁高中男生，某日，离开便利店准备回家的他突然被召唤到了异世界。人生地不熟的菜月昂很快就遇到了麻烦，在此危急时刻，名为爱蜜莉雅（高桥李依 配音）的神秘少女带着灵猫帕克（内山夕实 配音）出现在了菜月昂的面前，成为了他的救星，为了报答少 女的恩情，菜月昂和她结伴而行。让两人没有想到的是，他们竟然遭到了攻击不幸丧命，当菜月昂再度睁开双眼时，却发现自己回到了故事的最初。拥有着“死亡回归”能力的菜月昂决定拯救和少女的命运。菜月昂究竟为何会受到召唤，他又是否能够凭借着自己的特异能力，实现报恩的愿望呢？
     * category : 动画/奇幻/冒险
     * title : Re：从零开始的异世界生活
     */
    private String cover_url;
    private String module;
    private String description;
    private String category;
    private String title;
    private List<Src> play_lists;

    public String getCover_url() {
        return cover_url;
    }

    public String getModule() {
        return module;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public List<Src> getPlay_lists() {
        return play_lists;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "cover_url='" + cover_url + '\'' +
                ", module='" + module + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", play_lists=" + play_lists +
                '}';
    }

    static public class Src{

        /**
         * num : 25
         * name : Ⅰ
         */
        private String num;
        private String name;
        List<Eposide> video_list;

        public String getNum() {
            return num;
        }

        public String getName() {
            return name;
        }

        public List<Eposide> getVideo_list() {
            return video_list;
        }

        @Override
        public String toString() {
            return "Src{" +
                    "num='" + num + '\'' +
                    ", name='" + name + '\'' +
                    ", video_list=" + video_list +
                    '}';
        }
    }

    public static class Eposide{
        /**
         * name : 01
         * info : http://60.205.204.182:6001/anime/7a7a7a66756e7c31323238/0/0
         * player : http://60.205.204.182:6001/anime/7a7a7a66756e7c31323238/0/0/player
         */
        private String name;
        private String info;
        private String player;

        public String getName() {
            return name;
        }

        public String getInfo() {
            return info;
        }

        public String getPlayer() {
            return player;
        }

        @Override
        public String toString() {
            return "Eposide{" +
                    "name='" + name + '\'' +
                    ", info='" + info + '\'' +
                    ", player='" + player + '\'' +
                    '}';
        }
    }
}
