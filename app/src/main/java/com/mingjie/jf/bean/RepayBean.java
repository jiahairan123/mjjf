package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 个人中心 - 我的借款 - 还款中fragment 返回数据bean
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-27 14:15
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RepayBean
{

    /**
     * code : 000000
     * data : {"list":[{"activeId":"","appId":1,"assetType":3,"attachmentId":"1473808890792","auditStatus":0,"autoType":0,"bankStatus":"0","bidAmount":55,"bidFavorable":0,"bidServerFee":0,"borrowUserId":"74530040772b11e686dc6c92bf20d033","borrowerDesc":"存管快线-内部测试11","capital":55,"channelDate":"20160914","createdBy":"3E887B96AF1146279ED62BBC82C7E4C0","createdDate":1473808891000,"creditLevel":0,"currentPage":1,"custodyType":1,"deadline":"1/1","deleteFlag":0,"description":"存管快线-内部测试11","ensureOutfit":"c62eb17688d8437e9816b8bd38507731","fullBidDate":1473808977000,"id":"9577286137fb4940bb101234d11742a7","interest":0.05,"investBeginDate":1473808924426,"investEndDate":1473895324426,"latelyIndex":1,"maxBidAmount":100,"minBidAmount":50,"myReqseqno":"P2P04120160914051o5TfrQoSd1f","oriBorrowerId":"74d3506e772b11e686dc6c92bf20d033","pageSize":6,"productAmount":100,"productDeadline":1,"productStatus":10,"productStatusDesc":"还款中","productType":2,"profit":1,"repayDate":1473810300000,"repaymentDate":1474957377000,"repurchaseMode":1,"repurchaseModeDesc":"按月等额本息","reserve2":"0","resjnlno":"OGW01201609141012320051OGW869820","riskNote":"存管快线-内部测试11","serviceAmount":1.1,"serviceFee":0.02,"sourceTerminal":"PC","statusName":"","timeCountValid":1,"title":"存管快线-内部测试11","transdt":"20160914","transtm":"072204","userType":1,"userTypeDesc":"个人消费","version":1473810300000}],"pageCurrent":1,"pageSize":6,"pageTotal":13,"pages":3}
     * msg : 成功
     * success : true
     */

    public String code;
    /**
     * list : [{"activeId":"","appId":1,"assetType":3,"attachmentId":"1473808890792","auditStatus":0,"autoType":0,"bankStatus":"0","bidAmount":55,"bidFavorable":0,"bidServerFee":0,"borrowUserId":"74530040772b11e686dc6c92bf20d033","borrowerDesc":"存管快线-内部测试11","capital":55,"channelDate":"20160914","createdBy":"3E887B96AF1146279ED62BBC82C7E4C0","createdDate":1473808891000,"creditLevel":0,"currentPage":1,"custodyType":1,"deadline":"1/1","deleteFlag":0,"description":"存管快线-内部测试11","ensureOutfit":"c62eb17688d8437e9816b8bd38507731","fullBidDate":1473808977000,"id":"9577286137fb4940bb101234d11742a7","interest":0.05,"investBeginDate":1473808924426,"investEndDate":1473895324426,"latelyIndex":1,"maxBidAmount":100,"minBidAmount":50,"myReqseqno":"P2P04120160914051o5TfrQoSd1f","oriBorrowerId":"74d3506e772b11e686dc6c92bf20d033","pageSize":6,"productAmount":100,"productDeadline":1,"productStatus":10,"productStatusDesc":"还款中","productType":2,"profit":1,"repayDate":1473810300000,"repaymentDate":1474957377000,"repurchaseMode":1,"repurchaseModeDesc":"按月等额本息","reserve2":"0","resjnlno":"OGW01201609141012320051OGW869820","riskNote":"存管快线-内部测试11","serviceAmount":1.1,"serviceFee":0.02,"sourceTerminal":"PC","statusName":"","timeCountValid":1,"title":"存管快线-内部测试11","transdt":"20160914","transtm":"072204","userType":1,"userTypeDesc":"个人消费","version":1473810300000}]
     * pageCurrent : 1
     * pageSize : 6
     * pageTotal : 13
     * pages : 3
     */

    public DataBean data;
    public String msg;
    public boolean success;

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
        public int pageCurrent;
        public int pageSize;
        public int pageTotal;
        public int pages;
        /**
         * activeId : 
         * appId : 1
         * assetType : 3
         * attachmentId : 1473808890792
         * auditStatus : 0
         * autoType : 0
         * bankStatus : 0
         * bidAmount : 55.0
         * bidFavorable : 0
         * bidServerFee : 0
         * borrowUserId : 74530040772b11e686dc6c92bf20d033
         * borrowerDesc : 存管快线-内部测试11
         * capital : 55.0
         * channelDate : 20160914
         * createdBy : 3E887B96AF1146279ED62BBC82C7E4C0
         * createdDate : 1473808891000
         * creditLevel : 0
         * currentPage : 1
         * custodyType : 1
         * deadline : 1/1
         * deleteFlag : 0
         * description : 存管快线-内部测试11
         * ensureOutfit : c62eb17688d8437e9816b8bd38507731
         * fullBidDate : 1473808977000
         * id : 9577286137fb4940bb101234d11742a7
         * interest : 0.05
         * investBeginDate : 1473808924426
         * investEndDate : 1473895324426
         * latelyIndex : 1
         * maxBidAmount : 100.0
         * minBidAmount : 50.0
         * myReqseqno : P2P04120160914051o5TfrQoSd1f
         * oriBorrowerId : 74d3506e772b11e686dc6c92bf20d033
         * pageSize : 6
         * productAmount : 100.0
         * productDeadline : 1
         * productStatus : 10
         * productStatusDesc : 还款中
         * productType : 2
         * profit : 1.0
         * repayDate : 1473810300000
         * repaymentDate : 1474957377000
         * repurchaseMode : 1
         * repurchaseModeDesc : 按月等额本息
         * reserve2 : 0
         * resjnlno : OGW01201609141012320051OGW869820
         * riskNote : 存管快线-内部测试11
         * serviceAmount : 1.1
         * serviceFee : 0.02
         * sourceTerminal : PC
         * statusName : 
         * timeCountValid : 1
         * title : 存管快线-内部测试11
         * transdt : 20160914
         * transtm : 072204
         * userType : 1
         * userTypeDesc : 个人消费
         * version : 1473810300000
         */

        public List<ListBean> list;

        public int getPageCurrent()
        {
            return pageCurrent;
        }

        public void setPageCurrent(int pageCurrent)
        {
            this.pageCurrent = pageCurrent;
        }

        public int getPageSize()
        {
            return pageSize;
        }

        public void setPageSize(int pageSize)
        {
            this.pageSize = pageSize;
        }

        public int getPageTotal()
        {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal)
        {
            this.pageTotal = pageTotal;
        }

        public int getPages()
        {
            return pages;
        }

        public void setPages(int pages)
        {
            this.pages = pages;
        }

        public List<ListBean> getList()
        {
            return list;
        }

        public void setList(List<ListBean> list)
        {
            this.list = list;
        }

        public static class ListBean
        {
            public String activeId;
            public int appId;
            public int assetType;
            public String attachmentId;
            public int auditStatus;
            public int autoType;
            public String bankStatus;
            public double bidAmount;
            public double bidFavorable;
            public double bidServerFee;
            public String borrowUserId;
            public String borrowerDesc;
            public double capital;
            public String channelDate;
            public String createdBy;
            public long createdDate;
            public int creditLevel;
            public int currentPage;
            public int custodyType;
            public String deadline;
            public int deleteFlag;
            public String description;
            public String ensureOutfit;
            public long fullBidDate;
            public String id;
            public double interest;
            public long investBeginDate;
            public long investEndDate;
            public int latelyIndex;
            public double maxBidAmount;
            public double minBidAmount;
            public String myReqseqno;
            public String oriBorrowerId;
            public int pageSize;
            public double productAmount;
            public int productDeadline;
            public int productStatus;
            public String productStatusDesc;
            public int productType;
            public double profit;
            public long repayDate;
            public long repaymentDate;
            public int repurchaseMode;
            public String repurchaseModeDesc;
            public String reserve2;
            public String resjnlno;
            public String riskNote;
            public double serviceAmount;
            public double serviceFee;
            public String sourceTerminal;
            public String statusName;
            public int timeCountValid;
            public String title;
            public String transdt;
            public String transtm;
            public int userType;
            public String userTypeDesc;
            public long version;

            public String getActiveId()
            {
                return activeId;
            }

            public void setActiveId(String activeId)
            {
                this.activeId = activeId;
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

            public double getBidFavorable()
            {
                return bidFavorable;
            }

            public void setBidFavorable(double bidFavorable)
            {
                this.bidFavorable = bidFavorable;
            }

            public double getBidServerFee()
            {
                return bidServerFee;
            }

            public void setBidServerFee(double bidServerFee)
            {
                this.bidServerFee = bidServerFee;
            }

            public String getBorrowUserId()
            {
                return borrowUserId;
            }

            public void setBorrowUserId(String borrowUserId)
            {
                this.borrowUserId = borrowUserId;
            }

            public String getBorrowerDesc()
            {
                return borrowerDesc;
            }

            public void setBorrowerDesc(String borrowerDesc)
            {
                this.borrowerDesc = borrowerDesc;
            }

            public double getCapital()
            {
                return capital;
            }

            public void setCapital(double capital)
            {
                this.capital = capital;
            }

            public String getChannelDate()
            {
                return channelDate;
            }

            public void setChannelDate(String channelDate)
            {
                this.channelDate = channelDate;
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

            public String getDeadline()
            {
                return deadline;
            }

            public void setDeadline(String deadline)
            {
                this.deadline = deadline;
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

            public double getInterest()
            {
                return interest;
            }

            public void setInterest(double interest)
            {
                this.interest = interest;
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

            public int getLatelyIndex()
            {
                return latelyIndex;
            }

            public void setLatelyIndex(int latelyIndex)
            {
                this.latelyIndex = latelyIndex;
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

            public long getRepaymentDate()
            {
                return repaymentDate;
            }

            public void setRepaymentDate(long repaymentDate)
            {
                this.repaymentDate = repaymentDate;
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
        }
    }
}
