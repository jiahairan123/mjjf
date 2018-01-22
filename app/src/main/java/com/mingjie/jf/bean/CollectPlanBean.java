package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-10-12 13:47
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CollectPlanBean
{

    /**
     * code : 000000
     * data : {"list":[{"bankStatus":0,"bidId":"01894e634b704bcabcec7f74192b1ddf","borrowerId":"d5c89bb6ff934716b63c4db41a6cad64","capital":200,"createdDate":1476091416000,"currentPage":1,"deleteFlag":0,"expireDate":1476091416000,"id":"45D1521C3E75466282A2286A69E78109","interest":0.17,"investAccount":"6236882280000032198","investName":"路汐家","name":"cgzh_01","pageSize":6,"planIndex":1,"planType":1,"productDeadline":1,"productId":"9f24596b78304c85af9d55e65e5c0c14","repayFlow":"P2P04120161011074un6elqh4yQ4","repaymentPlanId":"8FB9F550CECC47F7AFABC511B4FF05FE","repurchaseMode":1,"repurchaseModeDesc":"按月等额本息","serviceFee":0.0017,"sourceTerminal":"PC","state":9,"stateDesc":"银行还款处理当中","title":"微信还款测试标","userId":"ec0590646b1844c9bbac6b6834c8f898","version":1476165162000}]}
     * msg : 成功
     */

    public String code;
    public DataBean data;
    public String msg;

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

    public static class DataBean
    {
        /**
         * bankStatus : 0
         * bidId : 01894e634b704bcabcec7f74192b1ddf
         * borrowerId : d5c89bb6ff934716b63c4db41a6cad64
         * capital : 200.0
         * createdDate : 1476091416000
         * currentPage : 1
         * deleteFlag : 0
         * expireDate : 1476091416000
         * id : 45D1521C3E75466282A2286A69E78109
         * interest : 0.17
         * investAccount : 6236882280000032198
         * investName : 路汐家
         * name : cgzh_01
         * pageSize : 6
         * planIndex : 1
         * planType : 1
         * productDeadline : 1
         * productId : 9f24596b78304c85af9d55e65e5c0c14
         * repayFlow : P2P04120161011074un6elqh4yQ4
         * repaymentPlanId : 8FB9F550CECC47F7AFABC511B4FF05FE
         * repurchaseMode : 1
         * repurchaseModeDesc : 按月等额本息
         * serviceFee : 0.0017
         * sourceTerminal : PC
         * state : 9
         * stateDesc : 银行还款处理当中
         * title : 微信还款测试标
         * userId : ec0590646b1844c9bbac6b6834c8f898
         * version : 1476165162000
         */

        public List<ListBean> list;

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
            public int bankStatus;
            public String bidId;
            public String borrowerId;
            public double capital;
            public long createdDate;
            public int currentPage;
            public int deleteFlag;
            public long expireDate;
            public String id;
            public double interest;
            public String investAccount;
            public String investName;
            public String name;
            public int pageSize;
            public int planIndex;
            public int planType;
            public int productDeadline;
            public String productId;
            public String repayFlow;
            public String repaymentPlanId;
            public int repurchaseMode;
            public String repurchaseModeDesc;
            public double serviceFee;
            public String sourceTerminal;
            public int state;
            public String stateDesc;
            public String title;
            public String userId;
            public long version;

            public int getAssetType()
            {
                return assetType;
            }

            public void setAssetType(int assetType)
            {
                this.assetType = assetType;
            }

            public int assetType;

            public long getRepaidDate()
            {
                return repaidDate;
            }

            public void setRepaidDate(long repaidDate)
            {
                this.repaidDate = repaidDate;
            }

            public long repaidDate;

            public int getBankStatus()
            {
                return bankStatus;
            }

            public void setBankStatus(int bankStatus)
            {
                this.bankStatus = bankStatus;
            }

            public String getBidId()
            {
                return bidId;
            }

            public void setBidId(String bidId)
            {
                this.bidId = bidId;
            }

            public String getBorrowerId()
            {
                return borrowerId;
            }

            public void setBorrowerId(String borrowerId)
            {
                this.borrowerId = borrowerId;
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

            public String getInvestAccount()
            {
                return investAccount;
            }

            public void setInvestAccount(String investAccount)
            {
                this.investAccount = investAccount;
            }

            public String getInvestName()
            {
                return investName;
            }

            public void setInvestName(String investName)
            {
                this.investName = investName;
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

            public String getRepayFlow()
            {
                return repayFlow;
            }

            public void setRepayFlow(String repayFlow)
            {
                this.repayFlow = repayFlow;
            }

            public String getRepaymentPlanId()
            {
                return repaymentPlanId;
            }

            public void setRepaymentPlanId(String repaymentPlanId)
            {
                this.repaymentPlanId = repaymentPlanId;
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

            public String getTitle()
            {
                return title;
            }

            public void setTitle(String title)
            {
                this.title = title;
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
