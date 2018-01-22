package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述：加息券明细界面
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-20 17:20
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CashDetailBean
{
    /**
     * capital : 26336.09
     * currentPage : 1
     * deleteFlag : 0
     * expireDate : 1492850086000
     * id : b95f72a6c65f4baa8014a9ce982c2b0d
     * notProfitTotal : 407.93
     * pageSize : 6
     * productDeadlineDesc : 2/3
     * productName : 押201702222-01
     * profit : 270.61
     * proportion : 6
     * repurchaseMode : 1
     * sourceTerminal : PC
     * state : 1
     */

    private double capital;//投资金额
    private int currentPage;
    private int deleteFlag;
    private long expireDate;//下期收款日期
    private String id;
    private double notProfitTotal;//待收收益
    private int pageSize;
    private String productDeadlineDesc;//当前期数
    private String productName;//标名
    private double profit;//下期收益
    private double proportion;//加息利率
    private int repurchaseMode;//标的还款方式
    private String sourceTerminal;

    private String productId;//标的id
    private int state;//收益状态 1表示未收，2表示已收

    public double getCapital()
    {
        return capital;
    }

    public void setCapital(double capital)
    {
        this.capital = capital;
    }



    public int getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    public long getExpireDate()
    {
        return expireDate;
    }

    public void setExpireDate(long expireDate)
    {
        this.expireDate = expireDate;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public double getNotProfitTotal()
    {
        return notProfitTotal;
    }

    public void setNotProfitTotal(double notProfitTotal)
    {
        this.notProfitTotal = notProfitTotal;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getProductDeadlineDesc()
    {
        return productDeadlineDesc;
    }

    public void setProductDeadlineDesc(String productDeadlineDesc)
    {
        this.productDeadlineDesc = productDeadlineDesc;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public double getProfit()
    {
        return profit;
    }

    public void setProfit(double profit)
    {
        this.profit = profit;
    }

    public double getProportion()
    {
        return proportion;
    }

    public void setProportion(double proportion)
    {
        this.proportion = proportion;
    }

    public int getRepurchaseMode()
    {
        return repurchaseMode;
    }

    public void setRepurchaseMode(int repurchaseMode)
    {
        this.repurchaseMode = repurchaseMode;
    }

    public String getSourceTerminal()
    {
        return sourceTerminal;
    }

    public void setSourceTerminal(String sourceTerminal)
    {
        this.sourceTerminal = sourceTerminal;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }



    @Override
    public String toString()
    {
        return "CashDetailBean{" +
                "productName='" + productName + '\'' +
                ", capital='" + capital + '\'' +
                ", productDeadlineDesc='" + productDeadlineDesc + '\'' +
                ", proportion=" + proportion +
                ", notProfitTotal=" + notProfitTotal +
                ", profit=" + profit +
                ", expireDate=" + expireDate +
                ", state='" + state + '\'' +
                ", repurchaseMode=" + repurchaseMode +
                ", productId=" + productId +
                '}';
    }
}
