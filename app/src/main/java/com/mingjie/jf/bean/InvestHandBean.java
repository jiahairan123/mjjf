package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 投资处理中 返回参数
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-29 11:37
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class InvestHandBean
{
    public String title; //标的标题
    public int assetType;//
    public double bidAmount;//投标金额
    public String stateName;//投标记录状态
    public double profit; //年化率
    public int productDeadline;//期限
    public String modeName; //还款方式
    public long createdDate; //投标时间
    public int pageTotal;
    public String productId;
    public int mode;

    public int getMode()
    {
        return mode;
    }

    public void setMode(int mode)
    {
        this.mode = mode;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public int getPageTotal()
    {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal)
    {
        this.pageTotal = pageTotal;
    }

    public double getBidAmount()
    {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount)
    {
        this.bidAmount = bidAmount;
    }

    public long getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(long createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getModeName()
    {
        return modeName;
    }

    public void setModeName(String modeName)
    {
        this.modeName = modeName;
    }

    public int getProductDeadline()
    {
        return productDeadline;
    }

    public void setProductDeadline(int productDeadline)
    {
        this.productDeadline = productDeadline;
    }

    public double getProfit()
    {
        return profit;
    }

    public void setProfit(double profit)
    {
        this.profit = profit;
    }

    public String getStateName()
    {
        return stateName;
    }

    public void setStateName(String stateName)
    {
        this.stateName = stateName;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getAssetType()
    {
        return assetType;
    }

    public void setAssetType(int assetType)
    {
        this.assetType = assetType;
    }
}
