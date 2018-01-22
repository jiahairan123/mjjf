package com.mingjie.jf.bean;

/**
 * Created by QinSiYi on 2016-8-25.
 */
public class SafePhoneActionVo {

    /**
     * password : 123456
     * pcode : 658002
     * token : 7b97f036e36945b7bb08e6a9c01eb39f
     */

    private String password;//密码
    private String pcode;//验证码
    private String token;

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    private String newPhone;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
