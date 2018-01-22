package com.mingjie.jf.bean;

/**
 * Created by jiahairan on 2017/8/7 14.
 */

public class IntroducedManBean {
    String realname;
    String title;
    String bidAmount;
    String bidDateStr;
    String amount;

    public IntroducedManBean(String realname, String title, String bidAmount, String bidDateStr, String amount) {
        this.realname = realname;
        this.title = title;
        this.bidAmount = bidAmount;
        this.bidDateStr = bidDateStr;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "IntroducedManBean{" +
                "realname='" + realname + '\'' +
                ", title='" + title + '\'' +
                ", bidAmount='" + bidAmount + '\'' +
                ", bidDateStr='" + bidDateStr + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getBidDateStr() {
        return bidDateStr;
    }

    public void setBidDateStr(String bidDateStr) {
        this.bidDateStr = bidDateStr;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
