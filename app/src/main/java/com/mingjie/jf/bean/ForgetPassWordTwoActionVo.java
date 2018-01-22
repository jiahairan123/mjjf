package com.mingjie.jf.bean;

import java.io.Serializable;

/**
 * Created by QinSiYi on 2016-7-26.
 */
public class ForgetPassWordTwoActionVo implements Serializable{

    public String getImgkey() {
        return imgkey;
    }

    public void setImgkey(String imgkey) {
        this.imgkey = imgkey;
    }



    public String getVcodeImage() {
        return vcodeImage;
    }

    public void setVcodeImage(String vcodeImage) {
        this.vcodeImage = vcodeImage;
    }

    String imgkey;//用户名

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String phone;//用户安全手机号码
    String vcodeImage;//图形验证码





}
