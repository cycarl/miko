package com.xana.acg.fac.model.music;

public class Data {

    /**
     * displayed : false
     * extAlg : null
     * type : 1
     * alg : onlineHotGroup
     */
    private boolean displayed;
    private String extAlg;
    private int type;
    private String alg;
    private Elite data;

    public Elite getData() {
        return data;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public void setExtAlg(String extAlg) {
        this.extAlg = extAlg;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public String getExtAlg() {
        return extAlg;
    }

    public int getType() {
        return type;
    }

    public String getAlg() {
        return alg;
    }

}
