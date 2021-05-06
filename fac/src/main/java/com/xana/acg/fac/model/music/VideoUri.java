package com.xana.acg.fac.model.music;

import com.xana.acg.fac.model.api.Resp;

import java.util.List;

public class VideoUri extends Resp {
    /**
     * urls : [{"validityTime":1200,"r":720,"size":50786886,"needPay":false,"id":"CE557682C57AF11A7EF4E21C5838F9F3","payInfo":null,"url":"http://vodkgeyttp9.vod.126.net/cloudmusic/4siGyMmA_2368894297_shd.mp4?ts=1621155348&rid=89AA8D402F56D23728AA107E1149641F&rl=3&rs=PKYeTzUEnYKtkIZACtYopwqXHPquZtAf&sign=bc03d5c78ea521129cce484a21f392b3&ext=PU89HBYvKVsQzhJ7AELaUqRdhAiHBxj0%2BHzGerVLsj1OFxXcbtNsYBcsV7WhSG9qBTUNLG7YXgNUSNHvSeGZvYDWT2OHXhlS7KUQpPsScCDj9PQ08FDu1rz0asDbJqnqSLCFGk7g%2B49p%2BeaU8bdxuxPGXHuBDmsAUYYoBDFU0X0%2Fv8K6c0IJrLLSjuugYf5ofrrHKnCOUaUiu2jrkFBdNGO6gIvthUXuwJtpDSsX3hSyOoDrT6negcHGfdk8PscT"}]
     * code : 200
     */
    private List<Uri> urls;
    public String getUri(){
        return urls.get(0).getUrl();
    }
}

class Uri {
    /**
     * validityTime : 1200
     * r : 720
     * size : 50786886
     * needPay : false
     * id : CE557682C57AF11A7EF4E21C5838F9F3
     * payInfo : null
     * url : http://vodkgeyttp9.vod.126.net/cloudmusic/4siGyMmA_2368894297_shd.mp4?ts=1621155348&rid=89AA8D402F56D23728AA107E1149641F&rl=3&rs=PKYeTzUEnYKtkIZACtYopwqXHPquZtAf&sign=bc03d5c78ea521129cce484a21f392b3&ext=PU89HBYvKVsQzhJ7AELaUqRdhAiHBxj0%2BHzGerVLsj1OFxXcbtNsYBcsV7WhSG9qBTUNLG7YXgNUSNHvSeGZvYDWT2OHXhlS7KUQpPsScCDj9PQ08FDu1rz0asDbJqnqSLCFGk7g%2B49p%2BeaU8bdxuxPGXHuBDmsAUYYoBDFU0X0%2Fv8K6c0IJrLLSjuugYf5ofrrHKnCOUaUiu2jrkFBdNGO6gIvthUXuwJtpDSsX3hSyOoDrT6negcHGfdk8PscT
     */
    private int validityTime;
    private int r;
    private int size;
    private boolean needPay;
    private String id;
    private String payInfo;
    private String url;

    public void setValidityTime(int validityTime) {
        this.validityTime = validityTime;
    }

    public int getValidityTime() {
        return validityTime;
    }

    public int getR() {
        return r;
    }

    public int getSize() {
        return size;
    }

    public boolean isNeedPay() {
        return needPay;
    }

    public String getId() {
        return id;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public String getUrl() {
        return url;
    }
}
