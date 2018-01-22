package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： APP配置信息
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-10-08 16:23
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AppConfigInfoResponse
{
    private String servicePhone;//客服热线

    public AppConfigInfoResponse(String servicePhone)
    {
        this.servicePhone = servicePhone;
    }

    public AppConfigInfoResponse()
    {
    }

    public String getServicePhone()
    {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone)
    {
        this.servicePhone = servicePhone;
    }

    @Override
    public String toString()
    {
        return "AppConfigInfoResponse{" +
                "servicePhone='" + servicePhone + '\'' +
                '}';
    }
}
