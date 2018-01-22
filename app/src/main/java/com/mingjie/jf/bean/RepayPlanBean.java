package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-27 21:16
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RepayPlanBean
{

    /**
     * code : 000000
     * data : [{"appId":3,"assetType":3,"autoType":0,"bidServerFee":0,"borrowName":"wangtao_5098","capital":55,"createdDate":1473808977000,"currentPage":1,"deleteFlag":0,"expireDate":1474957377000,"id":"EB73F48297F0452E8012A749167EF798","interest":0.05,"lateFee":0,"pageSize":6,"payAmount":0,"planIndex":1,"planType":1,"productDeadline":1,"productId":"9577286137fb4940bb101234d11742a7","productTitle":"存管快线-内部测试11","repaidAmount":0,"repurchaseMode":"1","serviceFee":0,"sourceTerminal":"PC","state":10,"stateDesc":"还款失败","statusDesc":"还款失败","userId":"74530040772b11e686dc6c92bf20d033","userPhone":"13066921081","version":1474959851000}]
     * msg : 成功
     * success : true
     */

    public String code;
    public String msg;
    public boolean success;
    /**
     * appId : 3
     * assetType : 3
     * autoType : 0
     * bidServerFee : 0.0
     * borrowName : wangtao_5098
     * capital : 55.0
     * createdDate : 1473808977000
     * currentPage : 1
     * deleteFlag : 0
     * expireDate : 1474957377000
     * id : EB73F48297F0452E8012A749167EF798
     * interest : 0.05
     * lateFee : 0.0
     * pageSize : 6
     * payAmount : 0.0
     * planIndex : 1
     * planType : 1
     * productDeadline : 1
     * productId : 9577286137fb4940bb101234d11742a7
     * productTitle : 存管快线-内部测试11
     * repaidAmount : 0.0
     * repurchaseMode : 1
     * serviceFee : 0.0
     * sourceTerminal : PC
     * state : 10
     * stateDesc : 还款失败
     * statusDesc : 还款失败
     * userId : 74530040772b11e686dc6c92bf20d033
     * userPhone : 13066921081
     * version : 1474959851000
     */

    public List<DataBean> data;

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
        public int appId;
        public int assetType;
        public int autoType;
        public double bidServerFee;
        public String borrowName;
        public double capital;
        public long createdDate;
        public int currentPage;
        public int deleteFlag;
        public long expireDate;
        public String id;
        public double interest;
        public double lateFee;
        public int pageSize;
        public double payAmount;
        public int planIndex;
        public int planType;
        public int productDeadline;
        public String productId;
        public String productTitle;
        public double repaidAmount;
        public String repurchaseMode;
        public double serviceFee;
        public String sourceTerminal;
        public int state;
        public String stateDesc;
        public String statusDesc;
        public String userId;
        public String userPhone;
        public long version;

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

        public int getAutoType()
        {
            return autoType;
        }

        public void setAutoType(int autoType)
        {
            this.autoType = autoType;
        }

        public double getBidServerFee()
        {
            return bidServerFee;
        }

        public void setBidServerFee(double bidServerFee)
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

        public double getCapital()
        {
            return capital;
        }

        public void setCapital(double capital)
        {
            this.capital = capital;
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

        public double getLateFee()
        {
            return lateFee;
        }

        public void setLateFee(double lateFee)
        {
            this.lateFee = lateFee;
        }

        public int getPageSize()
        {
            return pageSize;
        }

        public void setPageSize(int pageSize)
        {
            this.pageSize = pageSize;
        }

        public double getPayAmount()
        {
            return payAmount;
        }

        public void setPayAmount(double payAmount)
        {
            this.payAmount = payAmount;
        }

        public int getPlanIndex()
        {
            return planIndex;
        }

        public void setPlanIndex(int planIndex)
        {
            this.planIndex = planIndex;
        }

        public int getPlanType()
        {
            return planType;
        }

        public void setPlanType(int planType)
        {
            this.planType = planType;
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

        public String getProductTitle()
        {
            return productTitle;
        }

        public void setProductTitle(String productTitle)
        {
            this.productTitle = productTitle;
        }

        public double getRepaidAmount()
        {
            return repaidAmount;
        }

        public void setRepaidAmount(double repaidAmount)
        {
            this.repaidAmount = repaidAmount;
        }

        public String getRepurchaseMode()
        {
            return repurchaseMode;
        }

        public void setRepurchaseMode(String repurchaseMode)
        {
            this.repurchaseMode = repurchaseMode;
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

        public int getState()
        {
            return state;
        }

        public void setState(int state)
        {
            this.state = state;
        }

        public String getStateDesc()
        {
            return stateDesc;
        }

        public void setStateDesc(String stateDesc)
        {
            this.stateDesc = stateDesc;
        }

        public String getStatusDesc()
        {
            return statusDesc;
        }

        public void setStatusDesc(String statusDesc)
        {
            this.statusDesc = statusDesc;
        }

        public String getUserId()
        {
            return userId;
        }

        public void setUserId(String userId)
        {
            this.userId = userId;
        }

        public String getUserPhone()
        {
            return userPhone;
        }

        public void setUserPhone(String userPhone)
        {
            this.userPhone = userPhone;
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
