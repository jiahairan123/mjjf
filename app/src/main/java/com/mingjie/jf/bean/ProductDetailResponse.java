package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 标的详情
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-20 17:40
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ProductDetailResponse
{
    private String productId;//标的ID
    private String   title;//标的名称
    private double productAmount;//	产品金额
    private double profit; //利率
    private int productDeadline;//借款期限
    private int productStatus;//标的状态
    private int assetType;//标的类型
    private double bidAmount;//	投标金额
    private String borrowName;//	借款人名称
    private int repurchaseMode;//还款方式
    private String repurchaseModeDesc;//还款方式描述
    private double minBidAmount;//	最小投标金额
    private double maxBidAmount;//	最大投标金额
    private long investEndDate;//投资截止日期
    private float bidServerFee;//服务费率
    private int transferSize;
    private String beginTime; //开始时间

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public int getTransferSize()
    {
        return transferSize;
    }

    public void setTransferSize(int transferSize)
    {
        this.transferSize = transferSize;
    }

    public List<String> getBorrowNameList()
    {
        return borrowNameList;
    }

    public void setBorrowNameList(List<String> borrowNameList)
    {
        this.borrowNameList = borrowNameList;
    }

    private List<String> borrowNameList;

    public String getPasswords()
    {
        return passwords;
    }

    public void setPasswords(String passwords)
    {
        this.passwords = passwords;
    }
    private String passwords;

    public ProductDetailResponse(String productId, String title, double productAmount, float profit,
                                 int productDeadline,
                                 int productStatus, int assetType, double bidAmount, String borrowName,
                                 int repurchaseMode,
                                 String repurchaseModeDesc, double minBidAmount, double maxBidAmount,
                                 long investEndDate,float bidServerFee, String beginTime)
    {
        this.productId = productId;
        this.title = title;
        this.productAmount = productAmount;
        this.profit = profit;
        this.productDeadline = productDeadline;
        this.productStatus = productStatus;
        this.assetType = assetType;
        this.bidAmount = bidAmount;
        this.borrowName = borrowName;
        this.repurchaseMode = repurchaseMode;
        this.repurchaseModeDesc = repurchaseModeDesc;
        this.minBidAmount = minBidAmount;
        this.maxBidAmount = maxBidAmount;
        this.investEndDate = investEndDate;
        this.bidServerFee = bidServerFee;
        this.beginTime = beginTime;
    }

    public ProductDetailResponse()
    {
    }

    public void setBidServiceFee(float bidServerFee)
    {
        this.bidServerFee = bidServerFee;
    }

    public float getBidServiceFee()
    {
        return bidServerFee;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setProductAmount(double productAmount)
    {
        this.productAmount = productAmount;
    }

    public void setProfit(double profit)
    {
        this.profit = profit;
    }

    public void setProductDeadline(int productDeadline)
    {
        this.productDeadline = productDeadline;
    }

    public void setProductStatus(int productStatus)
    {
        this.productStatus = productStatus;
    }

    public void setAssetType(int assetType)
    {
        this.assetType = assetType;
    }

    public void setBidAmount(double bidAmount)
    {
        this.bidAmount = bidAmount;
    }

    public void setBorrowName(String borrowName)
    {
        this.borrowName = borrowName;
    }

    public void setRepurchaseMode(int repurchaseMode)
    {
        this.repurchaseMode = repurchaseMode;
    }

    public void setRepurchaseModeDesc(String repurchaseModeDesc)
    {
        this.repurchaseModeDesc = repurchaseModeDesc;
    }

    public void setMinBidAmount(double minBidAmount)
    {
        this.minBidAmount = minBidAmount;
    }

    public void setMaxBidAmount(double maxBidAmount)
    {
        this.maxBidAmount = maxBidAmount;
    }

    public void setInvestEndDate(long investEndDate)
    {
        this.investEndDate = investEndDate;
    }

    public String getProductId()
    {
        return productId;
    }

    public String getTitle()
    {
        return title;
    }

    public double getProductAmount()
    {
        return productAmount;
    }

    public double getProfit()
    {
        return profit;
    }

    public int getProductDeadline()
    {
        return productDeadline;
    }

    public int getProductStatus()
    {
        return productStatus;
    }

    public int getAssetType()
    {
        return assetType;
    }

    public double getBidAmount()
    {
        return bidAmount;
    }

    public String getBorrowName()
    {
        return borrowName;
    }

    public int getRepurchaseMode()
    {
        return repurchaseMode;
    }

    public String getRepurchaseModeDesc()
    {
        return repurchaseModeDesc;
    }

    public double getMinBidAmount()
    {
        return minBidAmount;
    }

    public double getMaxBidAmount()
    {
        return maxBidAmount;
    }

    public long getInvestEndDate()
    {
        return investEndDate;
    }

    @Override
    public String toString()
    {
        return "ProductDetailResponse{" +
                "productId='" + productId + '\'' +
                ", title='" + title + '\'' +
                ", productAmount=" + productAmount +
                ", profit=" + profit +
                ", productDeadline=" + productDeadline +
                ", productStatus=" + productStatus +
                ", assetType=" + assetType +
                ", bidAmount=" + bidAmount +
                ", borrowName='" + borrowName + '\'' +
                ", repurchaseMode=" + repurchaseMode +
                ", repurchaseModeDesc='" + repurchaseModeDesc + '\'' +
                ", minBidAmount=" + minBidAmount +
                ", maxBidAmount=" + maxBidAmount +
                ", investEndDate=" + investEndDate +
                ", bidServerFee=" + bidServerFee +
                ", beginTime=" + beginTime +
                '}';
    }
}

