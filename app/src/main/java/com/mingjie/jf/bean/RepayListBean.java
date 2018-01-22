package com.mingjie.jf.bean;
/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-15 19:57
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RepayListBean
{
    private String productTitle;//标题
    private int state ;//状态（2,5,13已还，其余的还款）
    private String stateDesc;//标的状态说明
    private String id;// 还款计划id
    private String productId;// 标的id
    private double capital;//本金
    private double interest;//利息
    private Long expireDate;//到期还款时间
    private Long repaidDate;//实际还款时间
    private int assetType;//标的类型
    private double serviceFee;//服务费

    public RepayListBean()
    {
    }

    public RepayListBean(String productTitle, int state, String stateDesc, String id, String productId, double
            capital, double interest, Long expireDate, Long repaidDate, int assetType, double serviceFee)
    {
        this.productTitle = productTitle;
        this.state = state;
        this.stateDesc = stateDesc;
        this.id = id;
        this.productId = productId;
        this.capital = capital;
        this.interest = interest;
        this.expireDate = expireDate;
        this.repaidDate = repaidDate;
        this.assetType = assetType;
        this.serviceFee = serviceFee;
    }

    public String getProductTitle()
    {
        return productTitle;
    }

    public void setProductTitle(String productTitle)
    {
        this.productTitle = productTitle;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getStateDesc()
    {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc)
    {
        this.stateDesc = stateDesc;
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

    public Long getExpireDate()
    {
        return expireDate;
    }

    public void setExpireDate(Long expireDate)
    {
        this.expireDate = expireDate;
    }

    public Long getRepaidDate()
    {
        return repaidDate;
    }

    public void setRepaidDate(Long repaidDate)
    {
        this.repaidDate = repaidDate;
    }

    public int getAssetType()
    {
        return assetType;
    }

    public void setAssetType(int assetType)
    {
        this.assetType = assetType;
    }

    public double getServiceFee()
    {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee)
    {
        this.serviceFee = serviceFee;
    }

    @Override
    public String toString()
    {
        return "RepayListBean{" +
                "productTitle='" + productTitle + '\'' +
                ", state=" + state +
                ", stateDesc='" + stateDesc + '\'' +
                ", id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", capital=" + capital +
                ", interest=" + interest +
                ", expireDate=" + expireDate +
                ", repaidDate=" + repaidDate +
                ", assetType=" + assetType +
                ", serviceFee=" + serviceFee +
                '}';
    }
}
