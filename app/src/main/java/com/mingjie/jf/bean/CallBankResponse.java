package com.mingjie.jf.bean;

import java.io.Serializable;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 调用银行前，后台返回加密数据实体类
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-24 14:13
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CallBankResponse implements Serializable
{
    /**
     *华信接口的URL
     */
    private String hxHostUrl;
    /**
     * 请求加密后的数据
     */
    private String requestData;
    /**
     * 交易码
     */
    private String transCode;

    /**
     * 交易流水号
     */
    private String channelFlow;

    private String receiptTransferId;

//    private String channelDate;

    public CallBankResponse()
    {
    }

    public CallBankResponse(String hxHostUrl, String requestData, String transCode, String channelFlow)
    {
        this.hxHostUrl = hxHostUrl;
        this.requestData = requestData;
        this.transCode = transCode;
        this.channelFlow = channelFlow;
    }

    public String getHxHostUrl()
    {
        return hxHostUrl;
    }

    public void setHxHostUrl(String hxHostUrl)
    {
        this.hxHostUrl = hxHostUrl;
    }

    public String getRequestData()
    {
        return requestData;
    }

    public void setRequestData(String requestData)
    {
        this.requestData = requestData;
    }

    public String getTransCode()
    {
        return transCode;
    }

    public void setTransCode(String transCode)
    {
        this.transCode = transCode;
    }

    public String getChannelFlow()
    {
        return channelFlow;
    }

    public void setChannelFlow(String channelFlow)
    {
        this.channelFlow = channelFlow;
    }

    public String getReceiptTransferId()
    {
        return receiptTransferId;
    }

    public void setReceiptTransferId(String receiptTransferId)
    {
        this.receiptTransferId = receiptTransferId;
    }

    @Override
    public String toString()
    {
        return "CallBankResponse{" +
                "hxHostUrl='" + hxHostUrl + '\'' +
                ", requestData='" + requestData + '\'' +
                ", transCode='" + transCode + '\'' +
                ", channelFlow='" + channelFlow + '\'' +
                '}';
    }
}
