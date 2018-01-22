package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-15 16:04
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ReturnCalendarDayBean
{
    private String productId;//id
    private String id;//id
    private double capital;//本金
    private double interest;//利息
    private int state;//状态（2、5、13 为已还，其他的待还）

    public ReturnCalendarDayBean(String productId, String id, double capital, double interest, int state)
    {
        this.productId = productId;
        this.id = id;
        this.capital = capital;
        this.interest = interest;
        this.state = state;
    }

    public ReturnCalendarDayBean()
    {
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public double getCapital()
    {
        return capital;
    }

    public void setCapital(double capital)
    {
        this.capital = capital;
    }

    public double getInterest()
    {
        return interest;
    }

    public void setInterest(double interest)
    {
        this.interest = interest;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    @Override
    public String toString()
    {
        return "ReturnCalendarDayBean{" +
                "productId='" + productId + '\'' +
                ", id='" + id + '\'' +
                ", capital=" + capital +
                ", interest=" + interest +
                ", state=" + state +
                '}';
    }
}
