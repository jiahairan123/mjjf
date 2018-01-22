package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-20 15:43
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CheBiaoVo
{
    public String id;//投标详情ID
    public String productId;//标的ID

    public CheBiaoVo(String id, String productId)
    {
        this.id = id;
        this.productId = productId;
    }

    @Override
    public String toString()
    {
        return "CheBiaoVo{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
