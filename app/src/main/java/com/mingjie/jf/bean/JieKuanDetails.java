package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-19 18:58
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class JieKuanDetails {


    /**
     * code : 000000
     * data : {"attachmentList":[{"batchId":1473647024739,"createdDate":1473647024000,"currentPage":1,"deleteFlag":0,
     * "fileName":"5555.jpg","filePath":"product/74530040772b11e686dc6c92bf20d033/e698f114463443c6928f6636bece161b
     * /2D8ED71A08644D28931A15D846A7D320.jpg","fileSize":35299,"fileType":"jpg",
     * "id":"2D8ED71A08644D28931A15D846A7D320","pageSize":6,"sourceTerminal":"PC","version":1473647024000}],
     * "companysName":"**融资租赁","product":{"activeId":"","appId":1,"assetType":3,"attachmentId":"1473647024739",
     * "auditStatus":0,"autoType":0,"bankStatus":"0","bidAmount":0,"bidFavorable":0,"bidServerFee":0,
     * "borrowName":"jiangjiwu0208","borrowUserId":"74530040772b11e686dc6c92bf20d033",
     * "borrowerDesc":"存管快线\u2014员工体验标","channelDate":"20160913","createdBy":"436E20E4F15D4FACAA09C53B933EAF9E",
     * "createdDate":1473697502000,"currentPage":1,"custodyType":1,"deleteFlag":0,"description":"存管快线\u2014员工体验标",
     * "ensureOutfit":"c62eb17688d8437e9816b8bd38507731","id":"356d670cbbb3465a93a16c7fec7036c4",
     * "investBeginDate":1473697502007,"investEndDate":1473783902007,"maxBidAmount":2,"minBidAmount":1,
     * "myReqseqno":"P2P04120160913051JcFRIWkfOl8","oriBorrowerId":"74d3506e772b11e686dc6c92bf20d033","pageSize":6,
     * "productAmount":89,"productDeadline":2,"productStatus":7,"productStatusDesc":"招标中","productType":1,
     * "profit":15,"repayDate":1474275730000,"repurchaseMode":1,"repurchaseModeDesc":"按月等额本息",
     * "resjnlno":"OGW01201609131011545747OGW040187","riskNote":"存管快线\u2014员工体验标",
     * "rootProductId":"e698f114463443c6928f6636bece161b","serviceAmount":0.22,"serviceFee":0.02,
     * "sourceTerminal":"PC","statusName":"","timeCountValid":1,"title":"存管快线\u2014员工体验标2","transdt":"20160913",
     * "transferAmount":89,"transferDeadline":2,"transferProfit":15,"transtm":"002502","userType":1,
     * "userTypeDesc":"个人消费","version":1473696721000}}
     * msg : 成功
     * success : true
     */

    public String code;

    public DataBean data;
    public String msg;
    public boolean success;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        public String companysName;
        /**
         * activeId : 
         * appId : 1
         * assetType : 3
         * attachmentId : 1473647024739
         * auditStatus : 0
         * autoType : 0
         * bankStatus : 0
         * bidAmount : 0.0
         * bidFavorable : 0
         * bidServerFee : 0
         * borrowName : jiangjiwu0208
         * borrowUserId : 74530040772b11e686dc6c92bf20d033
         * borrowerDesc : 存管快线—员工体验标
         * channelDate : 20160913
         * createdBy : 436E20E4F15D4FACAA09C53B933EAF9E
         * createdDate : 1473697502000
         * currentPage : 1
         * custodyType : 1
         * deleteFlag : 0
         * description : 存管快线—员工体验标
         * ensureOutfit : c62eb17688d8437e9816b8bd38507731
         * id : 356d670cbbb3465a93a16c7fec7036c4
         * investBeginDate : 1473697502007
         * investEndDate : 1473783902007
         * maxBidAmount : 2.0
         * minBidAmount : 1.0
         * myReqseqno : P2P04120160913051JcFRIWkfOl8
         * oriBorrowerId : 74d3506e772b11e686dc6c92bf20d033
         * pageSize : 6
         * productAmount : 89.0
         * productDeadline : 2
         * productStatus : 7
         * productStatusDesc : 招标中
         * productType : 1
         * profit : 15.0
         * repayDate : 1474275730000
         * repurchaseMode : 1
         * repurchaseModeDesc : 按月等额本息
         * resjnlno : OGW01201609131011545747OGW040187
         * riskNote : 存管快线—员工体验标
         * rootProductId : e698f114463443c6928f6636bece161b
         * serviceAmount : 0.22
         * serviceFee : 0.02
         * sourceTerminal : PC
         * statusName : 
         * timeCountValid : 1
         * title : 存管快线—员工体验标2
         * transdt : 20160913
         * transferAmount : 89.0
         * transferDeadline : 2
         * transferProfit : 15.0
         * transtm : 002502
         * userType : 1
         * userTypeDesc : 个人消费
         * version : 1473696721000
         */

        public ProductBean product;
        /**
         * batchId : 1473647024739
         * createdDate : 1473647024000
         * currentPage : 1
         * deleteFlag : 0
         * fileName : 5555.jpg
         * filePath : product/74530040772b11e686dc6c92bf20d033/e698f114463443c6928f6636bece161b/2D8ED71A08644D28931A15D846A7D320.jpg
         * fileSize : 35299
         * fileType : jpg
         * id : 2D8ED71A08644D28931A15D846A7D320
         * pageSize : 6
         * sourceTerminal : PC
         * version : 1473647024000
         */

        public List<AttachmentListBean> attachmentList;

        public String getCompanysName() {
            return companysName;
        }

        public void setCompanysName(String companysName) {
            this.companysName = companysName;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public List<AttachmentListBean> getAttachmentList() {
            return attachmentList;
        }

        public void setAttachmentList(List<AttachmentListBean> attachmentList) {
            this.attachmentList = attachmentList;
        }

        public static class ProductBean {
            public String activeId;
            public int appId;
            public int assetType;
            public String attachmentId;
            public int auditStatus;
            public int autoType;
            public String bankStatus;
            public double bidAmount;
            public int bidFavorable;
            public int bidServerFee;
            public String borrowName;
            public String borrowUserId;
            public String borrowerDesc;
            public String channelDate;
            public String createdBy;
            public long createdDate;
            public int currentPage;
            public int custodyType;
            public int deleteFlag;
            public String description;
            public String ensureOutfit;
            public String id;
            public long investBeginDate;
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
            public String riskNote;
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

            public List<String> getBorrowNameList()
            {
                return borrowNameList;
            }

            public void setBorrowNameList(List<String> borrowNameList)
            {
                this.borrowNameList = borrowNameList;
            }

            public List<String> borrowNameList;
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

            public int getBidFavorable() {
                return bidFavorable;
            }

            public void setBidFavorable(int bidFavorable) {
                this.bidFavorable = bidFavorable;
            }

            public int getBidServerFee() {
                return bidServerFee;
            }

            public void setBidServerFee(int bidServerFee) {
                this.bidServerFee = bidServerFee;
            }

            public String getBorrowName() {
                return borrowName;
            }

            public void setBorrowName(String borrowName) {
                this.borrowName = borrowName;
            }

            public String getBorrowUserId() {
                return borrowUserId;
            }

            public void setBorrowUserId(String borrowUserId) {
                this.borrowUserId = borrowUserId;
            }

            public String getBorrowerDesc() {
                return borrowerDesc;
            }

            public void setBorrowerDesc(String borrowerDesc) {
                this.borrowerDesc = borrowerDesc;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

            public String getRiskNote() {
                return riskNote;
            }

            public void setRiskNote(String riskNote) {
                this.riskNote = riskNote;
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

        public static class AttachmentListBean {
            public long batchId;
            public long createdDate;
            public int currentPage;
            public int deleteFlag;
            public String fileName;
            public String smallFilePath;
            public String filePath;
            public int fileSize;
            public String fileType;
            public String id;
            public int pageSize;
            public String sourceTerminal;
            public long version;

            public String getSmallFilePath()
            {
                return smallFilePath;
            }

            public void setSmallFilePath(String smallFilePath)
            {
                this.smallFilePath = smallFilePath;
            }

            public long getBatchId() {
                return batchId;
            }

            public void setBatchId(long batchId) {
                this.batchId = batchId;
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

            public int getDeleteFlag() {
                return deleteFlag;
            }

            public void setDeleteFlag(int deleteFlag) {
                this.deleteFlag = deleteFlag;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getFilePath() {
                return filePath;
            }

            public void setFilePath(String filePath) {
                this.filePath = filePath;
            }

            public int getFileSize() {
                return fileSize;
            }

            public void setFileSize(int fileSize) {
                this.fileSize = fileSize;
            }

            public String getFileType() {
                return fileType;
            }

            public void setFileType(String fileType) {
                this.fileType = fileType;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public String getSourceTerminal() {
                return sourceTerminal;
            }

            public void setSourceTerminal(String sourceTerminal) {
                this.sourceTerminal = sourceTerminal;
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
