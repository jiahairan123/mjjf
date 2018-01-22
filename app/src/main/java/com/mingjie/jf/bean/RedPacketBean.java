package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我的优惠券-红包
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-20 17:25
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RedPacketBean
{

    private double amount;//红包金额
    private long createdDate;//创建日期
    private long effectiveTime;//生效时间
    private long expireTime;//失效时间
    private long usedTime;//领取时间
    private String id;//红包ID
    private String name;//红包名称
    private int state;//红包状态
    private String userId;//用户ID

    public RedPacketBean(double amount, long createdDate, long effectiveTime, long expireTime, long usedTime, String
            id, String name, int state, String userId)
    {
        this.amount = amount;
        this.createdDate = createdDate;
        this.effectiveTime = effectiveTime;
        this.expireTime = expireTime;
        this.usedTime = usedTime;
        this.id = id;
        this.name = name;
        this.state = state;
        this.userId = userId;
    }

    public RedPacketBean()
    {
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public long getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(long createdDate)
    {
        this.createdDate = createdDate;
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

    public long getUsedTime()
    {
        return usedTime;
    }

    public void setUsedTime(long usedTime)
    {
        this.usedTime = usedTime;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "RedPacketBean{" +
                "amount=" + amount +
                ", createdDate=" + createdDate +
                ", effectiveTime=" + effectiveTime +
                ", expireTime=" + expireTime +
                ", usedTime=" + usedTime +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", userId='" + userId + '\'' +
                '}';
    }
}
