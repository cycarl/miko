package com.xana.acg.fac.model.anime;

import com.xana.acg.fac.model.api.Resp;

public class IptvUri implements Comparable<IptvUri>{

    /**
     * name : CCTV-10科教
     * url : http://ivi.bupt.edu.cn/hls/cctv10.m3u8
     */
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "IptvUri{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int compareTo(IptvUri uri) {
        return name.compareTo(uri.name);
    }
}
