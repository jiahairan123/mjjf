package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 回款日历日数据
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-15 16:04
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RepayCalendarDayBean
{
    private String id;//id
    private String productId;//id
    private double sumRepayment;//当月待还总数
    private double sumAmount;//当月待还总金额
    private double capital;//本金
    private double interest;//利息
    private double serviceFee;//服务费
    private int state;//状态（2、5、13 为已还款，其他的去还款）

    private String statusDesc;//状态

    public RepayCalendarDayBean(String id, String productId, double sumRepayment, double sumAmount, double capital,
                                double interest, double serviceFee, int state, String statusDesc)
    {
        this.id = id;
        this.productId = productId;
        this.sumRepayment = sumRepayment;
        this.sumAmount = sumAmount;
        this.capital = capital;
        this.interest = interest;
        this.serviceFee = serviceFee;
        this.state = state;
        this.statusDesc = statusDesc;
    }

    public RepayCalendarDayBean()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public double getSumRepayment()
    {
        return sumRepayment;
    }

    public void setSumRepayment(double sumRepayment)
    {
        this.sumRepayment = sumRepayment;
    }

    public double getSumAmount()
    {
        return sumAmount;
    }

    public void setSumAmount(double sumAmount)
    {
        this.sumAmount = sumAmount;
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

    public double getServiceFee()
    {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee)
    {
        this.serviceFee = serviceFee;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getStatusDesc()
    {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc)
    {
        this.statusDesc = statusDesc;
    }

    @Override
    public String toString()
    {
        return "RepayCalendarDayBean{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", sumRepayment=" + sumRepayment +
                ", sumAmount=" + sumAmount +
                ", capital=" + capital +
                ", interest=" + interest +
                ", serviceFee=" + serviceFee +
                ", state=" + state +
                ", statusDesc=" + statusDesc +
                '}';
    }
}
