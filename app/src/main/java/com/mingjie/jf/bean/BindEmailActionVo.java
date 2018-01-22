package com.mingjie.jf.bean;

/**
 * Created by QinSiYi on 2016-8-29.
 */
public class BindEmailActionVo {
    /**
     * "email": "test@mh.com",
     "from": "android",
     "sendEmailCode": "0",
     "token": "62a9ecf30613442ca08c7711025bcf1f"
     */
    String email;//邮箱
    String sendEmailCode;//1-重新发送，0-默认，默认为空
    String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSendEmailCode() {
        return sendEmailCode;
    }

    public void setSendEmailCode(String sendEmailCode) {
        this.sendEmailCode = sendEmailCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
