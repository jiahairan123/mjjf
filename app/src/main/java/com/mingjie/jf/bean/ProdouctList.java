package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-29 12:39
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class ProdouctList
{
    private String id;//标的id
    private int productStatus;//状态描述(7表示招标中，10-还款中)
    private String title;//产品标题
    private double profit; //年化收益率
    private double bidAmount;// 借款总额
    private int productDeadline;// 期限
    private int repurchaseMode;// 还款方式
    private String repurchaseModeDesc;// 还款方式
    private double productAmount;// 产品金额
    private int productType;//1表示债权 2表示借款

    public ProdouctList()
    {
    }

    public int getRepurchaseMode()
    {
        return repurchaseMode;
    }

    public void setRepurchaseMode(int repurchaseMode)
    {
        this.repurchaseMode = repurchaseMode;
    }

    public ProdouctList(String id, int productStatus, String title, double profit, double bidAmount,
                        int productDeadline,
                        int repurchaseMode, String repurchaseModeDesc, double productAmount, int productType)
    {
        this.id = id;
        this.productStatus = productStatus;
        this.title = title;
        this.profit = profit;
        this.bidAmount = bidAmount;
        this.productDeadline = productDeadline;
        this.repurchaseMode = repurchaseMode;
        this.repurchaseModeDesc = repurchaseModeDesc;
        this.productAmount = productAmount;
        this.productType = productType;
    }

    public int getProductStatus()
    {
        return productStatus;
    }

    public void setProductStatus(int productStatus)
    {
        this.productStatus = productStatus;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public double getProfit()
    {
        return profit;
    }

    public void setProfit(double profit)
    {
        this.profit = profit;
    }

    public double getBidAmount()
    {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount)
    {
        this.bidAmount = bidAmount;
    }

    public int getProductDeadline()
    {
        return productDeadline;
    }

    public void setProductDeadline(int productDeadline)
    {
        this.productDeadline = productDeadline;
    }

    public String getRepurchaseModeDesc()
    {
        return repurchaseModeDesc;
    }

    public void setRepurchaseModeDesc(String repurchaseModeDesc)
    {
        this.repurchaseModeDesc = repurchaseModeDesc;
    }

    public double getProductAmount()
    {
        return productAmount;
    }

    public void setProductAmount(double productAmount)
    {
        this.productAmount = productAmount;
    }

    public int getProductType()
    {
        return productType;
    }

    public void setProductType(int productType)
    {
        this.productType = productType;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override

    public String toString()
    {
        return "ProdouctList{" +
                "id=" + id +
                "productStatus=" + productStatus +
                ", title='" + title + '\'' +
                ", profit=" + profit +
                ", bidAmount=" + bidAmount +
                ", productDeadline=" + productDeadline +
                ", repurchaseModeDesc='" + repurchaseModeDesc + '\'' +
                ", repurchaseMode='" + repurchaseMode + '\'' +
                ", productAmount=" + productAmount +
                ", productType=" + productType +
                '}';
    }
}
