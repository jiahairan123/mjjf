package com.mingjie.jf.bean;

import java.io.Serializable;

/**
 * Created by zhangJin on 2016/7/25.
 */
public class FeedbackVo implements Serializable
{

    private static final long serialVersionUID = -3028245701661956349L;

    private String title; // 标题

    private String content; // 内容
    private int type; //来源 2:android/3:IOS/4:WeChat/PC

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    private String phone;//电话号码
    private String realname;//姓名


    public String getRealname()
    {
        return realname;
    }

    public void setRealname(String realname)
    {
        this.realname = realname;
    }


    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }


    private String version; // 客户端版本

    @Override
    public String toString()
    {
        return "FeedbackVo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", phone='" + phone + '\'' +
                ", realname='" + realname + '\'' +

                '}';
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }
}
