package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述：标的详情-投资记录
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-24 10:37
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class InvestRecordResponse
{

//    name	String			投标人
//    created_date	Date			最近投标时间
//    bidAmount	BigDecimal			意向投标金额
//    bidType	Integer			1自动投标  0手动投标

    private String name;//投标人
    private long createdDate;//			最近投标时间
    private double bidAmount;//			意向投标金额
    private int bidType;//			投标方式

    public int getBidSource()
    {
        return bidSource;
    }

    public void setBidSource(int bidSource)
    {
        this.bidSource = bidSource;
    }

    private int bidSource;//投标来源
    public String getBidSourceDesc()
    {
        return bidSourceDesc;
    }

    public void setBidSourceDesc(String bidSourceDesc)
    {
        this.bidSourceDesc = bidSourceDesc;
    }

    //    private int bidCount;//			投标次数
private String bidSourceDesc;//投标来源
    public InvestRecordResponse()
    {
    }

    public InvestRecordResponse(String userName, long lastestbidtime, double amount, int reserve1)
    {
        this.name = userName;
        this.createdDate = lastestbidtime;
        this.bidAmount = amount;
        this.bidType = reserve1;
//        this.bidSourceDesc = bidSourceDesc;
//        this.bidCount = bidCount;
    }

    public void setUserName(String userName)
    {
        this.name = userName;
    }

    public void setLastestbidtime(long lastestbidtime)
    {
        this.createdDate = lastestbidtime;
    }

    public void setAmount(double amount)
    {
        this.bidAmount = amount;
    }

    public void setReserve1(int reserve1)
    {
        this.bidType = reserve1;
    }

//    public void setBidCount(int bidCount)
//    {
//        this.bidCount = bidCount;
//    }

    public String getUserName()
    {
        return name;
    }

    public long getLastestbidtime()
    {
        return createdDate;
    }

    public double getAmount()
    {
        return bidAmount;
    }

    public int getReserve1()
    {
        return bidType;
    }

//    public int getBidCount()
//    {
//        return bidCount;
//    }

    @Override
    public String toString()
    {
        return "InvestRecordResponse{" +
                "userName='" + name + '\'' +
                ", lastestbidtime=" + createdDate +
                ", amount=" + bidAmount +
                ", reserve1='" + bidType  +
                ", bidSourceDesc='" + bidSourceDesc +
                '}';
    }
}
