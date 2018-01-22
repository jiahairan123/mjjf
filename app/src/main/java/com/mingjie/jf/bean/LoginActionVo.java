package com.mingjie.jf.bean;

import java.io.Serializable;

/**
 * Created by QinSiYi on 2016-8-10.
 */
public class LoginActionVo implements Serializable {



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;//用户名
    String password;//密码

    public String getVcode()
    {
        return vcode;
    }

    public void setVcode(String vcode)
    {
        this.vcode = vcode;
    }

    String vcode;//图形验证码

    @Override
    public String toString()
    {
        return "LoginActionVo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", vcode='" + vcode + '\'' +
                '}';
    }
}
