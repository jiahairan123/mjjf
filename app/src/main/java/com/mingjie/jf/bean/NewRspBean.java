package com.mingjie.jf.bean;

/**
 * Created by jiahairan on 2017/8/24 11.
 */

public class NewRspBean<T> {
    @Override
    public String toString() {
        return "NewRspBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    private String code; //状态码

    private String msg;  //成功还是失败

    private T data; //数据


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
