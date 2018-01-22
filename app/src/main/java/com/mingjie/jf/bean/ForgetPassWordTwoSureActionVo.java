package com.mingjie.jf.bean;

/**
 * Created by QinSiYi on 2016-8-24.
 */
public class ForgetPassWordTwoSureActionVo {
    String name;//用户名
    String password;//密码
    String pcode;//验证码
    String phone;//电话
    String rePassword;//确认密码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
