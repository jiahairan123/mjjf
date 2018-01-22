package com.mingjie.jf.bean;

import android.os.Handler;

import com.mingjie.jf.service.HttpCallBack;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 网络请求实体类，保存request和对应的callback
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-10-18 11:14
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class NetWorkBean
{
    private String svcName;
    private Object params;
    private HttpCallBack callBack;
    private Handler handler;

    public NetWorkBean(String svcName, Object params, HttpCallBack callBack, Handler handler)
    {
        this.svcName = svcName;
        this.params = params;
        this.callBack = callBack;
        this.handler = handler;
    }

    public String getSvcName()
    {
        return svcName;
    }

    public void setSvcName(String svcName)
    {
        this.svcName = svcName;
    }

    public Object getParams()
    {
        return params;
    }

    public void setParams(Object params)
    {
        this.params = params;
    }

    public HttpCallBack getCallBack()
    {
        return callBack;
    }

    public void setCallBack(HttpCallBack callBack)
    {
        this.callBack = callBack;
    }

    public Handler getHandler()
    {
        return handler;
    }

    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }
}

