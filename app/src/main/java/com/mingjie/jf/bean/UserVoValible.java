package com.mingjie.jf.bean;

import java.io.Serializable;

/**
 * Created by QinSiYi on 2016-8-25.
 */
public class UserVoValible implements Serializable
{
    String name;//用户名
    String phone;//电话号码
    String password;//密码

    String refcode;

    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
    }

    int isForbitBid;
    int isForbitReceiptTransfer;
    int isForbitWithdraw;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    String email;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    String token;

    public int getIsForbitBid()
    {
        return isForbitBid;
    }

    public void setIsForbitBid(int isForbitBid)
    {
        this.isForbitBid = isForbitBid;
    }

    public int getIsForbitReceiptTransfer()
    {
        return isForbitReceiptTransfer;
    }

    public void setIsForbitReceiptTransfer(int isForbitReceiptTransfer)
    {
        this.isForbitReceiptTransfer = isForbitReceiptTransfer;
    }

    public int getIsForbitWithdraw()
    {
        return isForbitWithdraw;
    }

    public void setIsForbitWithdraw(int isForbitWithdraw)
    {
        this.isForbitWithdraw = isForbitWithdraw;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    String id;

    public int getIsFundDepository()
    {
        return isFundDepository;
    }

    public void setIsFundDepository(int isFundDepository)
    {
        this.isFundDepository = isFundDepository;
    }

    int isFundDepository;//是否开通存管

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
