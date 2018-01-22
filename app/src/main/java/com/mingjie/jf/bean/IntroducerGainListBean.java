package com.mingjie.jf.bean;

/**
 * Created by jiahairan on 2017/8/4 17.
 * 提成记录
 */

public class IntroducerGainListBean {

    String realname; //客户姓名
    String phone;   //标的名称
    String isFundSepositoryStr;  //实名状态
    String introducerInsDateStr; //关系建立日期

    public IntroducerGainListBean(String realname, String phone, String isFundSepositoryStr, String introducerInsDateStr) {
        this.realname = realname;
        this.phone = phone;
        this.isFundSepositoryStr = isFundSepositoryStr;
        this.introducerInsDateStr = introducerInsDateStr;

    }

    @Override
    public String toString() {
        return "IntroducerGainListBean{" +
                "realname='" + realname + '\'' +
                ", phone='" + phone + '\'' +
                ", isFundSepositoryStr='" + isFundSepositoryStr + '\'' +
                ", introducerInsDateStr='" + introducerInsDateStr + '\'' +
                '}';
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsFundSepositoryStr() {
        return isFundSepositoryStr;
    }

    public void setIsFundSepositoryStr(String isFundSepositoryStr) {
        this.isFundSepositoryStr = isFundSepositoryStr;
    }

    public String getIntroducerInsDateStr() {
        return introducerInsDateStr;
    }

    public void setIntroducerInsDateStr(String introducerInsDateStr) {
        this.introducerInsDateStr = introducerInsDateStr;
    }
}
