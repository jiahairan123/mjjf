package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 债权详情 返回参数
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-19 17:54
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class EquitableDatailBean
{

    private String msg;
    private String code;
    private DataBean data;
    private boolean success;

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public DataBean getData()
    {
        return data;
    }

    public void setData(DataBean data)
    {
        this.data = data;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public static class DataBean
    {
        private ProductBean product;

        private ReceiptTransferBean receiptTransfer;

        public ProductBean getProduct()
        {
            return product;
        }

        public void setProduct(ProductBean product)
        {
            this.product = product;
        }

        public ReceiptTransferBean getReceiptTransfer()
        {
            return receiptTransfer;
        }

        public void setReceiptTransfer(ReceiptTransferBean receiptTransfer)
        {
            this.receiptTransfer = receiptTransfer;
        }

        public static class ProductBean
        {
            private String allCompanyName;
            private String allName;
            private String allRealName;
            private int appId;
            private int assetType;
            private String attachmentId;
            private int auditStatus;
            private int autoType;
            private String bankStatus;
            private double bidAmount;
            private int bidFavorable;
            private int bidRemainTime;
            private int bidServerFee;
            private String borrowName;
            private String borrowUserId;
            private String channelDate;
            private String counterGuarantee;
            private String createdBy;
            private long createdDate;
            private int creditLevel;
            private int currentPage;
            private int custodyType;
            private int deleteFlag;
            private String description;
            private String ensureOutfit;
            private long fullBidDate;
            private String id;
            private long investBeginDate;
            private long investEndDate;
            private int isEnterpriseBorrow;
            private int isTransfer;
            private double maxBidAmount;
            private double minBidAmount;
            private String myReqseqno;
            private String oriBorrowerId;
            private int pageSize;
            private String passwords;
            private double productAmount;
            private int productDeadline;
            private int productStatus;
            private String productStatusDesc;
            private int productType;
            private double profit;
            private long repayDate;
            private int repurchaseMode;
            private String repurchaseModeDesc;
            private String reserve2;
            private String resjnlno;
            private String riskNote;
            private String rootProductId;
            private double serviceAmount;
            private double serviceFee;
            private String sourceTerminal;
            private String statusName;
            private int timeCountValid;
            private String title;
            private String transdt;
            private double transferAmount;
            private int transferDeadline;
            private double transferProfit;
            private String transtm;
            private int userType;
            private String userTypeDesc;
            private long version;
            private List<String> borrowNameList;

            public String getAllCompanyName()
            {
                return allCompanyName;
            }

            public void setAllCompanyName(String allCompanyName)
            {
                this.allCompanyName = allCompanyName;
            }

            public String getAllName()
            {
                return allName;
            }

            public void setAllName(String allName)
            {
                this.allName = allName;
            }

            public String getAllRealName()
            {
                return allRealName;
            }

            public void setAllRealName(String allRealName)
            {
                this.allRealName = allRealName;
            }

            public int getAppId()
            {
                return appId;
            }

            public void setAppId(int appId)
            {
                this.appId = appId;
            }

            public int getAssetType()
            {
                return assetType;
            }

            public void setAssetType(int assetType)
            {
                this.assetType = assetType;
            }

            public String getAttachmentId()
            {
                return attachmentId;
            }

            public void setAttachmentId(String attachmentId)
            {
                this.attachmentId = attachmentId;
            }

            public int getAuditStatus()
            {
                return auditStatus;
            }

            public void setAuditStatus(int auditStatus)
            {
                this.auditStatus = auditStatus;
            }

            public int getAutoType()
            {
                return autoType;
            }

            public void setAutoType(int autoType)
            {
                this.autoType = autoType;
            }

            public String getBankStatus()
            {
                return bankStatus;
            }

            public void setBankStatus(String bankStatus)
            {
                this.bankStatus = bankStatus;
            }

            public double getBidAmount()
            {
                return bidAmount;
            }

            public void setBidAmount(double bidAmount)
            {
                this.bidAmount = bidAmount;
            }

            public int getBidFavorable()
            {
                return bidFavorable;
            }

            public void setBidFavorable(int bidFavorable)
            {
                this.bidFavorable = bidFavorable;
            }

            public int getBidRemainTime()
            {
                return bidRemainTime;
            }

            public void setBidRemainTime(int bidRemainTime)
            {
                this.bidRemainTime = bidRemainTime;
            }

            public int getBidServerFee()
            {
                return bidServerFee;
            }

            public void setBidServerFee(int bidServerFee)
            {
                this.bidServerFee = bidServerFee;
            }

            public String getBorrowName()
            {
                return borrowName;
            }

            public void setBorrowName(String borrowName)
            {
                this.borrowName = borrowName;
            }

            public String getBorrowUserId()
            {
                return borrowUserId;
            }

            public void setBorrowUserId(String borrowUserId)
            {
                this.borrowUserId = borrowUserId;
            }

            public String getChannelDate()
            {
                return channelDate;
            }

            public void setChannelDate(String channelDate)
            {
                this.channelDate = channelDate;
            }

            public String getCounterGuarantee()
            {
                return counterGuarantee;
            }

            public void setCounterGuarantee(String counterGuarantee)
            {
                this.counterGuarantee = counterGuarantee;
            }

            public String getCreatedBy()
            {
                return createdBy;
            }

            public void setCreatedBy(String createdBy)
            {
                this.createdBy = createdBy;
            }

            public long getCreatedDate()
            {
                return createdDate;
            }

            public void setCreatedDate(long createdDate)
            {
                this.createdDate = createdDate;
            }

            public int getCreditLevel()
            {
                return creditLevel;
            }

            public void setCreditLevel(int creditLevel)
            {
                this.creditLevel = creditLevel;
            }

            public int getCurrentPage()
            {
                return currentPage;
            }

            public void setCurrentPage(int currentPage)
            {
                this.currentPage = currentPage;
            }

            public int getCustodyType()
            {
                return custodyType;
            }

            public void setCustodyType(int custodyType)
            {
                this.custodyType = custodyType;
            }

            public int getDeleteFlag()
            {
                return deleteFlag;
            }

            public void setDeleteFlag(int deleteFlag)
            {
                this.deleteFlag = deleteFlag;
            }

            public String getDescription()
            {
                return description;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }

            public String getEnsureOutfit()
            {
                return ensureOutfit;
            }

            public void setEnsureOutfit(String ensureOutfit)
            {
                this.ensureOutfit = ensureOutfit;
            }

            public long getFullBidDate()
            {
                return fullBidDate;
            }

            public void setFullBidDate(long fullBidDate)
            {
                this.fullBidDate = fullBidDate;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public long getInvestBeginDate()
            {
                return investBeginDate;
            }

            public void setInvestBeginDate(long investBeginDate)
            {
                this.investBeginDate = investBeginDate;
            }

            public long getInvestEndDate()
            {
                return investEndDate;
            }

            public void setInvestEndDate(long investEndDate)
            {
                this.investEndDate = investEndDate;
            }

            public int getIsEnterpriseBorrow()
            {
                return isEnterpriseBorrow;
            }

            public void setIsEnterpriseBorrow(int isEnterpriseBorrow)
            {
                this.isEnterpriseBorrow = isEnterpriseBorrow;
            }

            public int getIsTransfer()
            {
                return isTransfer;
            }

            public void setIsTransfer(int isTransfer)
            {
                this.isTransfer = isTransfer;
            }

            public double getMaxBidAmount()
            {
                return maxBidAmount;
            }

            public void setMaxBidAmount(double maxBidAmount)
            {
                this.maxBidAmount = maxBidAmount;
            }

            public double getMinBidAmount()
            {
                return minBidAmount;
            }

            public void setMinBidAmount(double minBidAmount)
            {
                this.minBidAmount = minBidAmount;
            }

            public String getMyReqseqno()
            {
                return myReqseqno;
            }

            public void setMyReqseqno(String myReqseqno)
            {
                this.myReqseqno = myReqseqno;
            }

            public String getOriBorrowerId()
            {
                return oriBorrowerId;
            }

            public void setOriBorrowerId(String oriBorrowerId)
            {
                this.oriBorrowerId = oriBorrowerId;
            }

            public int getPageSize()
            {
                return pageSize;
            }

            public void setPageSize(int pageSize)
            {
                this.pageSize = pageSize;
            }

            public String getPasswords()
            {
                return passwords;
            }

            public void setPasswords(String passwords)
            {
                this.passwords = passwords;
            }

            public double getProductAmount()
            {
                return productAmount;
            }

            public void setProductAmount(double productAmount)
            {
                this.productAmount = productAmount;
            }

            public int getProductDeadline()
            {
                return productDeadline;
            }

            public void setProductDeadline(int productDeadline)
            {
                this.productDeadline = productDeadline;
            }

            public int getProductStatus()
            {
                return productStatus;
            }

            public void setProductStatus(int productStatus)
            {
                this.productStatus = productStatus;
            }

            public String getProductStatusDesc()
            {
                return productStatusDesc;
            }

            public void setProductStatusDesc(String productStatusDesc)
            {
                this.productStatusDesc = productStatusDesc;
            }

            public int getProductType()
            {
                return productType;
            }

            public void setProductType(int productType)
            {
                this.productType = productType;
            }

            public double getProfit()
            {
                return profit;
            }

            public void setProfit(double profit)
            {
                this.profit = profit;
            }

            public long getRepayDate()
            {
                return repayDate;
            }

            public void setRepayDate(long repayDate)
            {
                this.repayDate = repayDate;
            }

            public int getRepurchaseMode()
            {
                return repurchaseMode;
            }

            public void setRepurchaseMode(int repurchaseMode)
            {
                this.repurchaseMode = repurchaseMode;
            }

            public String getRepurchaseModeDesc()
            {
                return repurchaseModeDesc;
            }

            public void setRepurchaseModeDesc(String repurchaseModeDesc)
            {
                this.repurchaseModeDesc = repurchaseModeDesc;
            }

            public String getReserve2()
            {
                return reserve2;
            }

            public void setReserve2(String reserve2)
            {
                this.reserve2 = reserve2;
            }

            public String getResjnlno()
            {
                return resjnlno;
            }

            public void setResjnlno(String resjnlno)
            {
                this.resjnlno = resjnlno;
            }

            public String getRiskNote()
            {
                return riskNote;
            }

            public void setRiskNote(String riskNote)
            {
                this.riskNote = riskNote;
            }

            public String getRootProductId()
            {
                return rootProductId;
            }

            public void setRootProductId(String rootProductId)
            {
                this.rootProductId = rootProductId;
            }

            public double getServiceAmount()
            {
                return serviceAmount;
            }

            public void setServiceAmount(double serviceAmount)
            {
                this.serviceAmount = serviceAmount;
            }

            public double getServiceFee()
            {
                return serviceFee;
            }

            public void setServiceFee(double serviceFee)
            {
                this.serviceFee = serviceFee;
            }

            public String getSourceTerminal()
            {
                return sourceTerminal;
            }

            public void setSourceTerminal(String sourceTerminal)
            {
                this.sourceTerminal = sourceTerminal;
            }

            public String getStatusName()
            {
                return statusName;
            }

            public void setStatusName(String statusName)
            {
                this.statusName = statusName;
            }

            public int getTimeCountValid()
            {
                return timeCountValid;
            }

            public void setTimeCountValid(int timeCountValid)
            {
                this.timeCountValid = timeCountValid;
            }

            public String getTitle()
            {
                return title;
            }

            public void setTitle(String title)
            {
                this.title = title;
            }

            public String getTransdt()
            {
                return transdt;
            }

            public void setTransdt(String transdt)
            {
                this.transdt = transdt;
            }

            public double getTransferAmount()
            {
                return transferAmount;
            }

            public void setTransferAmount(double transferAmount)
            {
                this.transferAmount = transferAmount;
            }

            public int getTransferDeadline()
            {
                return transferDeadline;
            }

            public void setTransferDeadline(int transferDeadline)
            {
                this.transferDeadline = transferDeadline;
            }

            public double getTransferProfit()
            {
                return transferProfit;
            }

            public void setTransferProfit(double transferProfit)
            {
                this.transferProfit = transferProfit;
            }

            public String getTranstm()
            {
                return transtm;
            }

            public void setTranstm(String transtm)
            {
                this.transtm = transtm;
            }

            public int getUserType()
            {
                return userType;
            }

            public void setUserType(int userType)
            {
                this.userType = userType;
            }

            public String getUserTypeDesc()
            {
                return userTypeDesc;
            }

            public void setUserTypeDesc(String userTypeDesc)
            {
                this.userTypeDesc = userTypeDesc;
            }

            public long getVersion()
            {
                return version;
            }

            public void setVersion(long version)
            {
                this.version = version;
            }

            public List<String> getBorrowNameList()
            {
                return borrowNameList;
            }

            public void setBorrowNameList(List<String> borrowNameList)
            {
                this.borrowNameList = borrowNameList;
            }
        }

        public static class ReceiptTransferBean
        {
            private String biddetailId;
            private int channel;
            private String channelData;
            private long createdDate;
            private int currentPage;
            private int deleteFlag;
            private long expireDate;
            private int failCount;
            private String id;
            private long lastModifyDate;
            private String newProductId;
            private String newUserId;
            private int pageSize;
            private String productId;
            private double profit;
            private String rateOfDiscount;
            private String recentRepaymentDate;
            private long recentRepaymentTime;
            private double serviceFeeAmount;
            private double serviceFeeRate;
            private String sourceTerminal;
            private int stateInt;
            private double successAmount;
            private long successDate;
            private String surplusDays;
            private String topProductId;
            private double transferAmount;
            private int transferNumber;
            private double transferorlntertest;
            private String userId;
            private int validDay;
            private long version;

            public String getBiddetailId()
            {
                return biddetailId;
            }

            public void setBiddetailId(String biddetailId)
            {
                this.biddetailId = biddetailId;
            }

            public int getChannel()
            {
                return channel;
            }

            public void setChannel(int channel)
            {
                this.channel = channel;
            }

            public String getChannelData()
            {
                return channelData;
            }

            public void setChannelData(String channelData)
            {
                this.channelData = channelData;
            }

            public long getCreatedDate()
            {
                return createdDate;
            }

            public void setCreatedDate(long createdDate)
            {
                this.createdDate = createdDate;
            }

            public int getCurrentPage()
            {
                return currentPage;
            }

            public void setCurrentPage(int currentPage)
            {
                this.currentPage = currentPage;
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

            public int getFailCount()
            {
                return failCount;
            }

            public void setFailCount(int failCount)
            {
                this.failCount = failCount;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public long getLastModifyDate()
            {
                return lastModifyDate;
            }

            public void setLastModifyDate(long lastModifyDate)
            {
                this.lastModifyDate = lastModifyDate;
            }

            public String getNewProductId()
            {
                return newProductId;
            }

            public void setNewProductId(String newProductId)
            {
                this.newProductId = newProductId;
            }

            public String getNewUserId()
            {
                return newUserId;
            }

            public void setNewUserId(String newUserId)
            {
                this.newUserId = newUserId;
            }

            public int getPageSize()
            {
                return pageSize;
            }

            public void setPageSize(int pageSize)
            {
                this.pageSize = pageSize;
            }

            public String getProductId()
            {
                return productId;
            }

            public void setProductId(String productId)
            {
                this.productId = productId;
            }

            public double getProfit()
            {
                return profit;
            }

            public void setProfit(double profit)
            {
                this.profit = profit;
            }

            public String getRateOfDiscount()
            {
                return rateOfDiscount;
            }

            public void setRateOfDiscount(String rateOfDiscount)
            {
                this.rateOfDiscount = rateOfDiscount;
            }

            public String getRecentRepaymentDate()
            {
                return recentRepaymentDate;
            }

            public void setRecentRepaymentDate(String recentRepaymentDate)
            {
                this.recentRepaymentDate = recentRepaymentDate;
            }

            public long getRecentRepaymentTime()
            {
                return recentRepaymentTime;
            }

            public void setRecentRepaymentTime(long recentRepaymentTime)
            {
                this.recentRepaymentTime = recentRepaymentTime;
            }

            public double getServiceFeeAmount()
            {
                return serviceFeeAmount;
            }

            public void setServiceFeeAmount(double serviceFeeAmount)
            {
                this.serviceFeeAmount = serviceFeeAmount;
            }

            public double getServiceFeeRate()
            {
                return serviceFeeRate;
            }

            public void setServiceFeeRate(double serviceFeeRate)
            {
                this.serviceFeeRate = serviceFeeRate;
            }

            public String getSourceTerminal()
            {
                return sourceTerminal;
            }

            public void setSourceTerminal(String sourceTerminal)
            {
                this.sourceTerminal = sourceTerminal;
            }

            public int getStateInt()
            {
                return stateInt;
            }

            public void setStateInt(int stateInt)
            {
                this.stateInt = stateInt;
            }

            public double getSuccessAmount()
            {
                return successAmount;
            }

            public void setSuccessAmount(double successAmount)
            {
                this.successAmount = successAmount;
            }

            public long getSuccessDate()
            {
                return successDate;
            }

            public void setSuccessDate(long successDate)
            {
                this.successDate = successDate;
            }

            public String getSurplusDays()
            {
                return surplusDays;
            }

            public void setSurplusDays(String surplusDays)
            {
                this.surplusDays = surplusDays;
            }

            public String getTopProductId()
            {
                return topProductId;
            }

            public void setTopProductId(String topProductId)
            {
                this.topProductId = topProductId;
            }

            public double getTransferAmount()
            {
                return transferAmount;
            }

            public void setTransferAmount(double transferAmount)
            {
                this.transferAmount = transferAmount;
            }

            public int getTransferNumber()
            {
                return transferNumber;
            }

            public void setTransferNumber(int transferNumber)
            {
                this.transferNumber = transferNumber;
            }

            public double getTransferorlntertest()
            {
                return transferorlntertest;
            }

            public void setTransferorlntertest(double transferorlntertest)
            {
                this.transferorlntertest = transferorlntertest;
            }

            public String getUserId()
            {
                return userId;
            }

            public void setUserId(String userId)
            {
                this.userId = userId;
            }

            public int getValidDay()
            {
                return validDay;
            }

            public void setValidDay(int validDay)
            {
                this.validDay = validDay;
            }

            public long getVersion()
            {
                return version;
            }

            public void setVersion(long version)
            {
                this.version = version;
            }
        }
    }
}
