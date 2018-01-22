package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 投资界面的数据 bean
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-13 09:58
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class InvestList {


    /**
     * msg : 成功
     * code : 000000
     * data : {"list":[{"activeId":"3131adaa84c24e9f853be2c34209720d","appId":1,"assetType":3,"attachmentId":"","auditStatus":0,"autoType":0,"bankStatus":"0","bidAmount":0,"bidFavorable":1,"bidServerFee":0,"borrowUserId":"74471ed5772b11e686dc6c92bf20d033","channelDate":"20160911","createdBy":"436E20E4F15D4FACAA09C53B933EAF9E","createdDate":1473594300000,"currentPage":1,"custodyType":1,"deleteFlag":0,"ensureOutfit":"c62eb17688d8437e9816b8bd38507731","id":"1cea75a6ad724ecfb60714a6bc6ede6f","investBeginDate":1473594300328,"investEndDate":1474026300328,"maxBidAmount":10,"minBidAmount":1,"myReqseqno":"P2P04120160911051h8PsfDjcrhQ","oriBorrowerId":"74d3506e772b11e686dc6c92bf20d033","pageSize":6,"productAmount":79,"productDeadline":3,"productStatus":7,"productStatusDesc":"招标中","productType":1,"profit":10,"repayDate":1474275730000,"repurchaseMode":2,"repurchaseModeDesc":"按月先息后本","resjnlno":"OGW01201609111011023567OGW846004","rootProductId":"c7ee516f43e64d15b433ca4653820d4a","serviceAmount":0.04,"serviceFee":0.02,"sourceTerminal":"PC","statusName":"","timeCountValid":5,"title":"存管快线-内测标（优）","transdt":"20160911","transferAmount":79,"transferDeadline":3,"transferProfit":10,"transtm":"194500","userType":2,"userTypeDesc":"短期周转","version":1473600241000}],"pageCurrent":2,"pageSize":6,"pageTotal":11,"pages":2}
     * success : true
     */

    public String msg;
    public String code;
    /**
     * list : [{"activeId":"3131adaa84c24e9f853be2c34209720d","appId":1,"assetType":3,"attachmentId":"","auditStatus":0,"autoType":0,"bankStatus":"0","bidAmount":0,"bidFavorable":1,"bidServerFee":0,"borrowUserId":"74471ed5772b11e686dc6c92bf20d033","channelDate":"20160911","createdBy":"436E20E4F15D4FACAA09C53B933EAF9E","createdDate":1473594300000,"currentPage":1,"custodyType":1,"deleteFlag":0,"ensureOutfit":"c62eb17688d8437e9816b8bd38507731","id":"1cea75a6ad724ecfb60714a6bc6ede6f","investBeginDate":1473594300328,"investEndDate":1474026300328,"maxBidAmount":10,"minBidAmount":1,"myReqseqno":"P2P04120160911051h8PsfDjcrhQ","oriBorrowerId":"74d3506e772b11e686dc6c92bf20d033","pageSize":6,"productAmount":79,"productDeadline":3,"productStatus":7,"productStatusDesc":"招标中","productType":1,"profit":10,"repayDate":1474275730000,"repurchaseMode":2,"repurchaseModeDesc":"按月先息后本","resjnlno":"OGW01201609111011023567OGW846004","rootProductId":"c7ee516f43e64d15b433ca4653820d4a","serviceAmount":0.04,"serviceFee":0.02,"sourceTerminal":"PC","statusName":"","timeCountValid":5,"title":"存管快线-内测标（优）","transdt":"20160911","transferAmount":79,"transferDeadline":3,"transferProfit":10,"transtm":"194500","userType":2,"userTypeDesc":"短期周转","version":1473600241000}]
     * pageCurrent : 2
     * pageSize : 6
     * pageTotal : 11
     * pages : 2
     */

    public DataBean data;
    public boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        public int pageCurrent;
        public int pageSize;
        public int pageTotal;
        public int pages;
        /**
         * activeId : 3131adaa84c24e9f853be2c34209720d
         * appId : 1
         * assetType : 3
         * attachmentId : 
         * auditStatus : 0
         * autoType : 0
         * bankStatus : 0
         * bidAmount : 0
         * bidFavorable : 1
         * bidServerFee : 0
         * borrowUserId : 74471ed5772b11e686dc6c92bf20d033
         * channelDate : 20160911
         * createdBy : 436E20E4F15D4FACAA09C53B933EAF9E
         * createdDate : 1473594300000
         * currentPage : 1
         * custodyType : 1
         * deleteFlag : 0
         * ensureOutfit : c62eb17688d8437e9816b8bd38507731
         * id : 1cea75a6ad724ecfb60714a6bc6ede6f
         * investBeginDate : 1473594300328
         * investEndDate : 1474026300328
         * maxBidAmount : 10
         * minBidAmount : 1
         * myReqseqno : P2P04120160911051h8PsfDjcrhQ
         * oriBorrowerId : 74d3506e772b11e686dc6c92bf20d033
         * pageSize : 6
         * productAmount : 79
         * productDeadline : 3
         * productStatus : 7
         * productStatusDesc : 招标中
         * productType : 1
         * profit : 10
         * repayDate : 1474275730000
         * repurchaseMode : 2
         * repurchaseModeDesc : 按月先息后本
         * resjnlno : OGW01201609111011023567OGW846004
         * rootProductId : c7ee516f43e64d15b433ca4653820d4a
         * serviceAmount : 0.04
         * serviceFee : 0.02
         * sourceTerminal : PC
         * statusName : 
         * timeCountValid : 5
         * title : 存管快线-内测标（优）
         * transdt : 20160911
         * transferAmount : 79
         * transferDeadline : 3
         * transferProfit : 10
         * transtm : 194500
         * userType : 2
         * userTypeDesc : 短期周转
         * version : 1473600241000
         */

        public List<ListBean> list;

        public int getPageCurrent() {
            return pageCurrent;
        }

        public void setPageCurrent(int pageCurrent) {
            this.pageCurrent = pageCurrent;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            public String beginTime;

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
            public String channelDate;
            public String createdBy;
            public long createdDate;
            public int currentPage;
            public int custodyType;
            public int deleteFlag;
            public String ensureOutfit;
            public String id;
            public long investBeginDate; //项目开始时间
            public long investEndDate;
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
            public int repurchaseMode;
            public String repurchaseModeDesc;
            public String resjnlno;
            public String rootProductId;
            public double serviceAmount;
            public double serviceFee;
            public String sourceTerminal;
            public String statusName;
            public int timeCountValid;
            public String title;
            public String transdt;
            public double transferAmount;
            public int transferDeadline;
            public double transferProfit;
            public String transtm;
            public int userType;
            public String userTypeDesc;
            public long version;

            public String getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(String beginTime) {
                this.beginTime = beginTime;
            }

            public String getActiveId() {
                return activeId;
            }

            public void setActiveId(String activeId) {
                this.activeId = activeId;
            }

            public int getAppId() {
                return appId;
            }

            public void setAppId(int appId) {
                this.appId = appId;
            }

            public int getAssetType() {
                return assetType;
            }

            public void setAssetType(int assetType) {
                this.assetType = assetType;
            }

            public String getAttachmentId() {
                return attachmentId;
            }

            public void setAttachmentId(String attachmentId) {
                this.attachmentId = attachmentId;
            }

            public int getAuditStatus() {
                return auditStatus;
            }

            public void setAuditStatus(int auditStatus) {
                this.auditStatus = auditStatus;
            }

            public int getAutoType() {
                return autoType;
            }

            public void setAutoType(int autoType) {
                this.autoType = autoType;
            }

            public String getBankStatus() {
                return bankStatus;
            }

            public void setBankStatus(String bankStatus) {
                this.bankStatus = bankStatus;
            }

            public double getBidAmount() {
                return bidAmount;
            }

            public void setBidAmount(double bidAmount) {
                this.bidAmount = bidAmount;
            }

            public double getBidFavorable() {
                return bidFavorable;
            }

            public void setBidFavorable(double bidFavorable) {
                this.bidFavorable = bidFavorable;
            }

            public double getBidServerFee() {
                return bidServerFee;
            }

            public void setBidServerFee(double bidServerFee) {
                this.bidServerFee = bidServerFee;
            }

            public String getBorrowUserId() {
                return borrowUserId;
            }

            public void setBorrowUserId(String borrowUserId) {
                this.borrowUserId = borrowUserId;
            }

            public String getChannelDate() {
                return channelDate;
            }

            public void setChannelDate(String channelDate) {
                this.channelDate = channelDate;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public long getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(long createdDate) {
                this.createdDate = createdDate;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getCustodyType() {
                return custodyType;
            }

            public void setCustodyType(int custodyType) {
                this.custodyType = custodyType;
            }

            public int getDeleteFlag() {
                return deleteFlag;
            }

            public void setDeleteFlag(int deleteFlag) {
                this.deleteFlag = deleteFlag;
            }

            public String getEnsureOutfit() {
                return ensureOutfit;
            }

            public void setEnsureOutfit(String ensureOutfit) {
                this.ensureOutfit = ensureOutfit;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public long getInvestBeginDate() {
                return investBeginDate;
            }

            public void setInvestBeginDate(long investBeginDate) {
                this.investBeginDate = investBeginDate;
            }

            public long getInvestEndDate() {
                return investEndDate;
            }

            public void setInvestEndDate(long investEndDate) {
                this.investEndDate = investEndDate;
            }

            public double getMaxBidAmount() {
                return maxBidAmount;
            }

            public void setMaxBidAmount(double maxBidAmount) {
                this.maxBidAmount = maxBidAmount;
            }

            public double getMinBidAmount() {
                return minBidAmount;
            }

            public void setMinBidAmount(double minBidAmount) {
                this.minBidAmount = minBidAmount;
            }

            public String getMyReqseqno() {
                return myReqseqno;
            }

            public void setMyReqseqno(String myReqseqno) {
                this.myReqseqno = myReqseqno;
            }

            public String getOriBorrowerId() {
                return oriBorrowerId;
            }

            public void setOriBorrowerId(String oriBorrowerId) {
                this.oriBorrowerId = oriBorrowerId;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public double getProductAmount() {
                return productAmount;
            }

            public void setProductAmount(double productAmount) {
                this.productAmount = productAmount;
            }

            public int getProductDeadline() {
                return productDeadline;
            }

            public void setProductDeadline(int productDeadline) {
                this.productDeadline = productDeadline;
            }

            public int getProductStatus() {
                return productStatus;
            }

            public void setProductStatus(int productStatus) {
                this.productStatus = productStatus;
            }

            public String getProductStatusDesc() {
                return productStatusDesc;
            }

            public void setProductStatusDesc(String productStatusDesc) {
                this.productStatusDesc = productStatusDesc;
            }

            public int getProductType() {
                return productType;
            }

            public void setProductType(int productType) {
                this.productType = productType;
            }

            public double getProfit() {
                return profit;
            }

            public void setProfit(double profit) {
                this.profit = profit;
            }

            public long getRepayDate() {
                return repayDate;
            }

            public void setRepayDate(long repayDate) {
                this.repayDate = repayDate;
            }

            public int getRepurchaseMode() {
                return repurchaseMode;
            }

            public void setRepurchaseMode(int repurchaseMode) {
                this.repurchaseMode = repurchaseMode;
            }

            public String getRepurchaseModeDesc() {
                return repurchaseModeDesc;
            }

            public void setRepurchaseModeDesc(String repurchaseModeDesc) {
                this.repurchaseModeDesc = repurchaseModeDesc;
            }

            public String getResjnlno() {
                return resjnlno;
            }

            public void setResjnlno(String resjnlno) {
                this.resjnlno = resjnlno;
            }

            public String getRootProductId() {
                return rootProductId;
            }

            public void setRootProductId(String rootProductId) {
                this.rootProductId = rootProductId;
            }

            public double getServiceAmount() {
                return serviceAmount;
            }

            public void setServiceAmount(double serviceAmount) {
                this.serviceAmount = serviceAmount;
            }

            public double getServiceFee() {
                return serviceFee;
            }

            public void setServiceFee(double serviceFee) {
                this.serviceFee = serviceFee;
            }

            public String getSourceTerminal() {
                return sourceTerminal;
            }

            public void setSourceTerminal(String sourceTerminal) {
                this.sourceTerminal = sourceTerminal;
            }

            public String getStatusName() {
                return statusName;
            }

            public void setStatusName(String statusName) {
                this.statusName = statusName;
            }

            public int getTimeCountValid() {
                return timeCountValid;
            }

            public void setTimeCountValid(int timeCountValid) {
                this.timeCountValid = timeCountValid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTransdt() {
                return transdt;
            }

            public void setTransdt(String transdt) {
                this.transdt = transdt;
            }

            public double getTransferAmount() {
                return transferAmount;
            }

            public void setTransferAmount(double transferAmount) {
                this.transferAmount = transferAmount;
            }

            public int getTransferDeadline() {
                return transferDeadline;
            }

            public void setTransferDeadline(int transferDeadline) {
                this.transferDeadline = transferDeadline;
            }

            public double getTransferProfit() {
                return transferProfit;
            }

            public void setTransferProfit(double transferProfit) {
                this.transferProfit = transferProfit;
            }

            public String getTranstm() {
                return transtm;
            }

            public void setTranstm(String transtm) {
                this.transtm = transtm;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public String getUserTypeDesc() {
                return userTypeDesc;
            }

            public void setUserTypeDesc(String userTypeDesc) {
                this.userTypeDesc = userTypeDesc;
            }

            public long getVersion() {
                return version;
            }

            public void setVersion(long version) {
                this.version = version;
            }
        }
    }
}
