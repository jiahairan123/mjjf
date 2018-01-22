package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我的优惠券-现金券
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-20 17:20
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CashBean
{
    private String id;//现金券id
    private String userId;//用户id
    private String name;//现金券名称
    private double amount;//现金券金额
    private double minInvestmentAmount;//现金券使用门槛
    private long effectiveTime;//生效时间
    private long expireTime;//失效时间
    private String useRange;//投资标的期限
    private long usedDate;//使用时间
//    private int assetType;//投标范围
    private String assetTypeStr;//投标范围
    private long createDate;//创建时间
    private int state;//状态
    private String srcProductId;//来源
    private String usedProductId;//标的id
    private String reserve1;//备用字段，这里用作接受使用范围说明，即标的类型

    private String proportion;//加息券利率
    private int couponType;//4代表加息券

    public CashBean(String id, String userId, String name, double amount, double minInvestmentAmount, long
            effectiveTime, long expireTime, String useRange, long usedDate, String assetTypeStr, long createDate, int
            state, String srcProductId, String usedProductId, String reserve1)
    {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.minInvestmentAmount = minInvestmentAmount;
        this.effectiveTime = effectiveTime;
        this.expireTime = expireTime;
        this.useRange = useRange;
        this.usedDate = usedDate;
        this.assetTypeStr = assetTypeStr;
        this.createDate = createDate;
        this.state = state;
        this.srcProductId = srcProductId;
        this.usedProductId = usedProductId;
        this.reserve1 = reserve1;
        this.proportion = proportion;
        this.couponType = couponType;
    }

    public CashBean()
    {
    }

    public String getAssetTypeStr()
    {
        return assetTypeStr;
    }

    public void setAssetTypeStr(String assetTypeStr)
    {
        this.assetTypeStr = assetTypeStr;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public double getMinInvestmentAmount()
    {
        return minInvestmentAmount;
    }

    public void setMinInvestmentAmount(double minInvestmentAmount)
    {
        this.minInvestmentAmount = minInvestmentAmount;
    }

    public long getEffectiveTime()
    {
        return effectiveTime;
    }

    public void setEffectiveTime(long effectiveTime)
    {
        this.effectiveTime = effectiveTime;
    }

    public long getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(long expireTime)
    {
        this.expireTime = expireTime;
    }

    public String getUseRange()
    {
        return useRange;
    }

    public void setUseRange(String useRange)
    {
        this.useRange = useRange;
    }

    public long getUsedDate()
    {
        return usedDate;
    }

    public void setUsedDate(long usedDate)
    {
        this.usedDate = usedDate;
    }



    public long getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(long createDate)
    {
        this.createDate = createDate;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getSrcProductId()
    {
        return srcProductId;
    }

    public void setSrcProductId(String srcProductId)
    {
        this.srcProductId = srcProductId;
    }

    public String getUsedProductId()
    {
        return usedProductId;
    }

    public void setUsedProductId(String usedProductId)
    {
        this.usedProductId = usedProductId;
    }

    public String getReserve1()
    {
        return reserve1;
    }

    public void setReserve1(String reserve1)
    {
        this.reserve1 = reserve1;
    }

    public String getProportion()
    {
        return proportion;
    }

    public void setProportion(String proportion)
    {
        this.proportion = proportion;
    }

    public int getCouponType()
    {
        return couponType;
    }

    public void setCouponType(int couponType)
    {
        this.couponType = couponType;
    }

    @Override
    public String toString()
    {
        return "CashBean{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", minInvestmentAmount=" + minInvestmentAmount +
                ", effectiveTime=" + effectiveTime +
                ", expireTime=" + expireTime +
                ", useRange='" + useRange + '\'' +
                ", usedDate=" + usedDate +
                ", assetTypeStr=" + assetTypeStr +
                ", createDate=" + createDate +
                ", state=" + state +
                ", srcProductId='" + srcProductId + '\'' +
                ", usedProductId='" + usedProductId + '\'' +
                ", reserve1='" + reserve1 + '\'' +
                ", proportion='" +proportion + '\'' +
                ", couponType='" + couponType + '\'' +
                '}';
    }
}
