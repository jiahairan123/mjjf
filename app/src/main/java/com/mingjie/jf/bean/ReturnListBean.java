package com.mingjie.jf.bean;

import java.util.List;

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
public class ReturnListBean
{
    private String title;//标题
    private int state;//状态（2,5,13已还，其余的待还）
    private int productDeadline;//总期数
    private int productType;//标的类型
    private int planIndexStr;//当前期数
    private double capital;//本金
    private double interest;//利息
    private long expireDate;//收款时间
    private long repaidDate;//实际还款时间
    private String stateDesc;//标的状态说明
    private String productId;//标的id
    private double serviceFee;//服务费
    private String allName;//借款人姓名
    private List<String> nameList;//借款人姓名集合
    private int assetType;//标的类型

    public ReturnListBean(String title, int state, int productDeadline, int planIndexStr, double capital, double
            interest, long expireDate, long repaidDate, String stateDesc, double serviceFee, String allName,
                          List<String> nameList, int assetType)
    {
        this.title = title;
        this.state = state;
        this.productDeadline = productDeadline;
        this.planIndexStr = planIndexStr;
        this.capital = capital;
        this.interest = interest;
        this.expireDate = expireDate;
        this.repaidDate = repaidDate;
        this.stateDesc = stateDesc;
        this.serviceFee = serviceFee;
        this.allName = allName;
        this.nameList = nameList;
        this.assetType = assetType;
    }

    public ReturnListBean()
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getProductDeadline()
    {
        return productDeadline;
    }

    public void setProductDeadline(int productDeadline)
    {
        this.productDeadline = productDeadline;
    }

    public int getPlanIndexStr()
    {
        return planIndexStr;
    }

    public void setPlanIndexStr(int planIndexStr)
    {
        this.planIndexStr = planIndexStr;
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

    public long getExpireDate()
    {
        return expireDate;
    }

    public void setExpireDate(long expireDate)
    {
        this.expireDate = expireDate;
    }

    public long getRepaidDate()
    {
        return repaidDate;
    }

    public void setRepaidDate(long repaidDate)
    {
        this.repaidDate = repaidDate;
    }

    public String getStateDesc()
    {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc)
    {
        this.stateDesc = stateDesc;
    }

    public double getServiceFee()
    {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee)
    {
        this.serviceFee = serviceFee;
    }

    public String getAllName()
    {
        return allName;
    }

    public void setAllName(String allName)
    {
        this.allName = allName;
    }

    public List<String> getNameList()
    {
        return nameList;
    }

    public void setNameList(List<String> nameList)
    {
        this.nameList = nameList;
    }

    public int getProductType()
    {
        return productType;
    }

    public void setProductType(int productType)
    {
        this.productType = productType;
    }

    public int getAssetType()
    {
        return assetType;
    }

    public void setAssetType(int assetType)
    {
        this.assetType = assetType;
    }

    @Override
    public String toString()
    {
        return "ReturnListBean{" +
                "title='" + title + '\'' +
                ", state=" + state +
                ", productDeadline=" + productDeadline +
                ", planIndexStr=" + planIndexStr +
                ", capital=" + capital +
                ", interest=" + interest +
                ", expireDate=" + expireDate +
                ", repaidDate=" + repaidDate +
                ", stateDesc='" + stateDesc + '\'' +
                ", productId='" + productId + '\'' +
                ", serviceFee=" + serviceFee +
                ", allName='" + allName + '\'' +
                ", nameList=" + nameList +
                ", assetType=" + assetType +
                '}';
    }
}
