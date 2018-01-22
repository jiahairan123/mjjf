package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-15 15:39
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class TransferMessgBean
{



    private String code;


    private DataBean data;
    private String msg;
    private boolean success;

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

    public static class DataBean
    {

        private BidDetailBean bidDetail;
        private String capital;
        private String currentValue;
        private String minPrice;
        private long recentRepaymentDate;
        private String serviceFeeRate;
        private String servicePriceRate;
        private String surplusDays;
        private String surplusTime;
        private String toTodayInterest;
        private int transferMaxDays;

        private String increaseInterest;//加息券收益

        public BidDetailBean getBidDetail()
        {
            return bidDetail;
        }

        public void setBidDetail(BidDetailBean bidDetail)
        {
            this.bidDetail = bidDetail;
        }

        public String getCapital()
        {
            return capital;
        }

        public void setCapital(String capital)
        {
            this.capital = capital;
        }

        public String getCurrentValue()
        {
            return currentValue;
        }

        public void setCurrentValue(String currentValue)
        {
            this.currentValue = currentValue;
        }

        public String getMinPrice()
        {
            return minPrice;
        }

        public void setMinPrice(String minPrice)
        {
            this.minPrice = minPrice;
        }

        public long getRecentRepaymentDate()
        {
            return recentRepaymentDate;
        }

        public void setRecentRepaymentDate(long recentRepaymentDate)
        {
            this.recentRepaymentDate = recentRepaymentDate;
        }

        public String getServiceFeeRate()
        {
            return serviceFeeRate;
        }

        public void setServiceFeeRate(String serviceFeeRate)
        {
            this.serviceFeeRate = serviceFeeRate;
        }

        public String getServicePriceRate()
        {
            return servicePriceRate;
        }

        public void setServicePriceRate(String servicePriceRate)
        {
            this.servicePriceRate = servicePriceRate;
        }

        public String getSurplusDays()
        {
            return surplusDays;
        }

        public void setSurplusDays(String surplusDays)
        {
            this.surplusDays = surplusDays;
        }

        public String getSurplusTime()
        {
            return surplusTime;
        }

        public void setSurplusTime(String surplusTime)
        {
            this.surplusTime = surplusTime;
        }

        public String getToTodayInterest()
        {
            return toTodayInterest;
        }

        public void setToTodayInterest(String toTodayInterest)
        {
            this.toTodayInterest = toTodayInterest;
        }

        public int getTransferMaxDays()
        {
            return transferMaxDays;
        }

        public void setTransferMaxDays(int transferMaxDays)
        {
            this.transferMaxDays = transferMaxDays;
        }

        public String getIncreaseInterest()
        {
            return increaseInterest;
        }

        public void setIncreaseInterest(String increaseInterest)
        {
            this.increaseInterest = increaseInterest;
        }


        public static class BidDetailBean
        {
            private String acno;
            private double bidAmount;
            private long bidDate;
            private String bidSourceDesc;
            private String bidStatusDesc;
            private int bidType;
            private String borrowUserId;
            private String channelFlow;
            private long createdDate;
            private int currentPage;
            private int deleteFlag;
            private String id;
            private int jobSearchCount;
            private int mode;
            private String modeName;
            private String name;
            private int pageSize;
            private int productDeadline;
            private String productId;
            private double profit;
            private String realname;
            private String sourceTerminal;
            private int state;
            private String stateName;
            private int status;
            private String statusName;
            private int timeValid;
            private String title;
            private String tradeId;
            private String userId;
            private long version;

            public String getAcno()
            {
                return acno;
            }

            public void setAcno(String acno)
            {
                this.acno = acno;
            }

            public double getBidAmount()
            {
                return bidAmount;
            }

            public void setBidAmount(double bidAmount)
            {
                this.bidAmount = bidAmount;
            }

            public long getBidDate()
            {
                return bidDate;
            }

            public void setBidDate(long bidDate)
            {
                this.bidDate = bidDate;
            }

            public String getBidSourceDesc()
            {
                return bidSourceDesc;
            }

            public void setBidSourceDesc(String bidSourceDesc)
            {
                this.bidSourceDesc = bidSourceDesc;
            }

            public String getBidStatusDesc()
            {
                return bidStatusDesc;
            }

            public void setBidStatusDesc(String bidStatusDesc)
            {
                this.bidStatusDesc = bidStatusDesc;
            }

            public int getBidType()
            {
                return bidType;
            }

            public void setBidType(int bidType)
            {
                this.bidType = bidType;
            }

            public String getBorrowUserId()
            {
                return borrowUserId;
            }

            public void setBorrowUserId(String borrowUserId)
            {
                this.borrowUserId = borrowUserId;
            }

            public String getChannelFlow()
            {
                return channelFlow;
            }

            public void setChannelFlow(String channelFlow)
            {
                this.channelFlow = channelFlow;
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

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public int getJobSearchCount()
            {
                return jobSearchCount;
            }

            public void setJobSearchCount(int jobSearchCount)
            {
                this.jobSearchCount = jobSearchCount;
            }

            public int getMode()
            {
                return mode;
            }

            public void setMode(int mode)
            {
                this.mode = mode;
            }

            public String getModeName()
            {
                return modeName;
            }

            public void setModeName(String modeName)
            {
                this.modeName = modeName;
            }

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public int getPageSize()
            {
                return pageSize;
            }

            public void setPageSize(int pageSize)
            {
                this.pageSize = pageSize;
            }

            public int getProductDeadline()
            {
                return productDeadline;
            }

            public void setProductDeadline(int productDeadline)
            {
                this.productDeadline = productDeadline;
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

            public String getRealname()
            {
                return realname;
            }

            public void setRealname(String realname)
            {
                this.realname = realname;
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

            public String getStateName()
            {
                return stateName;
            }

            public void setStateName(String stateName)
            {
                this.stateName = stateName;
            }

            public int getStatus()
            {
                return status;
            }

            public void setStatus(int status)
            {
                this.status = status;
            }

            public String getStatusName()
            {
                return statusName;
            }

            public void setStatusName(String statusName)
            {
                this.statusName = statusName;
            }

            public int getTimeValid()
            {
                return timeValid;
            }

            public void setTimeValid(int timeValid)
            {
                this.timeValid = timeValid;
            }

            public String getTitle()
            {
                return title;
            }

            public void setTitle(String title)
            {
                this.title = title;
            }

            public String getTradeId()
            {
                return tradeId;
            }

            public void setTradeId(String tradeId)
            {
                this.tradeId = tradeId;
            }

            public String getUserId()
            {
                return userId;
            }

            public void setUserId(String userId)
            {
                this.userId = userId;
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
