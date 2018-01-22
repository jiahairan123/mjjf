package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 转让记录
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-19 20:42
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class EquitableRecordBean
{
    private String code;
    private String msg;
    private boolean success;

    private List<DataBean> data;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public List<DataBean> getData()
    {
        return data;
    }

    public void setData(List<DataBean> data)
    {
        this.data = data;
    }

    public static class DataBean
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
        private String newUserName;
        private int pageSize;
        private String productId;
        private double profit;
        private String recentRepaymentDate;
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
        private String userName;
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

        public String getNewUserName()
        {
            return newUserName;
        }

        public void setNewUserName(String newUserName)
        {
            this.newUserName = newUserName;
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

        public String getRecentRepaymentDate()
        {
            return recentRepaymentDate;
        }

        public void setRecentRepaymentDate(String recentRepaymentDate)
        {
            this.recentRepaymentDate = recentRepaymentDate;
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

        public String getUserName()
        {
            return userName;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
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
