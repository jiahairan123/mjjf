package com.mingjie.jf.bean;

/**
 * Created by zhangJin on 2016/6/29.
 */
public class ServerResponse<T>
{

    private String code; //状态码

    private String msg;  //成功还是失败

    private T data; //数据

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }
}
