package com.mingjie.jf.bean;

import java.io.Serializable;

/**
 * Created by QinSiYi on 2016-8-8.
 */
public class SmsCodeActionVo implements Serializable {
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getVcode()
    {
        return vcode;
    }

    public void setVcode(String vcode)
    {
        this.vcode = vcode;
    }

    /**
     * "imgKey": "ca25d40a415a4e19ac0b88b8340b7196",
     * "phoneNum": "18682102222",
     * "vcodeImage": "54p5"
     */
    String phone;//号码
    String vcode;//验证码

    @Override
    public String toString()
    {
        return "UserRegisterActionVo{" +
                ", vcodeImage='" + vcode + '\'' +
                ", phoneNum='" + phone + '\'' +

                '}';
    }



}

