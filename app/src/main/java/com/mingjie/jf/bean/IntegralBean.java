package com.mingjie.jf.bean;

/**
 * Created by jiahairan on 2017/8/2 12.
 */

public class IntegralBean {
    String insDateStr;
    String back1Str;

    String usedPointStr;

    public IntegralBean(String insDateStr, String back1Str, String usedPointStr) {
        this.insDateStr = insDateStr;
        this.back1Str = back1Str;
        this.usedPointStr = usedPointStr;
    }

    public String getInsDateStr() {
        return insDateStr;
    }

    public void setInsDateStr(String insDateStr) {
        this.insDateStr = insDateStr;
    }

    public String getBack1Str() {
        return back1Str;
    }

    public void setBack1Str(String back1Str) {
        this.back1Str = back1Str;
    }

    public String getUsedPointStr() {
        return usedPointStr;
    }

    public void setUsedPointStr(String usedPointStr) {
        this.usedPointStr = usedPointStr;
    }
}