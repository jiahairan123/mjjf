package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-20 13:50
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class Pager
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
        private int pageCurrent;//当前页数
        private int pageSize;//一页加载的条数
        private int pageTotal;//总条数
        private int pages;//总页数

        private List<ListBean> list;

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
            //    {
            //        "accountAmount": 10000,
            //            "createdDate": 1474711276000,
            //            "currentPage": 1,
            //            "currentYear": 2016,
            //            "deleteFlag": 0,
            //            "flowCode": "P2P041201609240926WRmuGfnxPN",
            //            "id": "0EE6CFBEF98543F893FE2E39EEDFBE95",
            //            "pageSize": 6,
            //            "pollingTimes": 0,
            //            "rechargeAmount": 100,
            //            "searchType": 0,
            //            "sourceTerminal": "PC",
            //            "status": 0,
            //            "statusName": "处理中",
            //            "userId": "74d624e9772b11e686dc6c92bf20d033",
            //            "version": 1474711276000
            ////    }
            private double accountAmount;//金额
            private long createdDate;
            private int currentPage;
            private int currentYear;
            private int deleteFlag;
            private long effectiveDate;
            private String flowCode;//流水号
            private String id;
            private int pageSize;
            private int pollingTimes;
            private double rechargeAmount;//充值金额
            private int searchType;
            private String sourceTerminal;

            @Override
            public String toString()
            {
                return "ListBean{" +
                        " id='" + id + '\'' +
                        ", accountAmount=" + accountAmount +
                        ", createdDate=" + createdDate +
                        ", currentPage=" + currentPage +
                        ", currentYear=" + currentYear +
                        ", deleteFlag=" + deleteFlag +
                        ", effectiveDate=" + effectiveDate +
                        ", flowCode='" + flowCode + '\'' +
                        ", pageSize=" + pageSize +
                        ", pollingTimes=" + pollingTimes +
                        ", rechargeAmount=" + rechargeAmount +
                        ", searchType=" + searchType +
                        ", sourceTerminal='" + sourceTerminal + '\'' +
                        ", state=" + state +
                        ", status=" + status +
                        ", statusName='" + statusName + '\'' +
                        ", userId='" + userId + '\'' +
                        ", version=" + version +
                        ", flowTypeDesc='" + flowTypeDesc + '\'' +
                        ", flowType=" + flowType +
                        ", amount=" + amount +
                        ", code='" + code + '\'' +
                        ", withdrawAmount=" + withdrawAmount +
                        '}';
            }

            public int getState()
            {
                return state;
            }

            public void setState(int state)
            {
                this.state = state;
            }

            private int state;//
            private int status;//2--失败，1--成功，0---处理中
            private String statusName;//状态（处理中，等待到账等，失败，成功）
            private String userId;
            private long version;

            public String getFlowTypeDesc()
            {
                return flowTypeDesc;
            }

            public void setFlowTypeDesc(String flowTypeDesc)
            {
                this.flowTypeDesc = flowTypeDesc;
            }

            private String flowTypeDesc;

            public int getFlowType()
            {
                return flowType;
            }

            public void setFlowType(int flowType)
            {
                this.flowType = flowType;
            }

            private int flowType;

            public double getAmount()
            {
                return amount;
            }

            public void setAmount(double amount)
            {
                this.amount = amount;
            }

            private double amount;

            public String getCode()
            {
                return code;
            }

            public void setCode(String code)
            {
                this.code = code;
            }

            private String code;

            public double getWithdrawAmount()
            {
                return withdrawAmount;
            }

            public void setWithdrawAmount(double withdrawAmount)
            {
                this.withdrawAmount = withdrawAmount;
            }

            private double withdrawAmount;

            public double getAccountAmount()
            {
                return accountAmount;
            }

            public void setAccountAmount(double accountAmount)
            {
                this.accountAmount = accountAmount;
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

            public int getCurrentYear()
            {
                return currentYear;
            }

            public void setCurrentYear(int currentYear)
            {
                this.currentYear = currentYear;
            }

            public int getDeleteFlag()
            {
                return deleteFlag;
            }

            public void setDeleteFlag(int deleteFlag)
            {
                this.deleteFlag = deleteFlag;
            }

            public long getEffectiveDate()
            {
                return effectiveDate;
            }

            public void setEffectiveDate(long effectiveDate)
            {
                this.effectiveDate = effectiveDate;
            }

            public String getFlowCode()
            {
                return flowCode;
            }

            public void setFlowCode(String flowCode)
            {
                this.flowCode = flowCode;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public int getPageSize()
            {
                return pageSize;
            }

            public void setPageSize(int pageSize)
            {
                this.pageSize = pageSize;
            }

            public int getPollingTimes()
            {
                return pollingTimes;
            }

            public void setPollingTimes(int pollingTimes)
            {
                this.pollingTimes = pollingTimes;
            }

            public double getRechargeAmount()
            {
                return rechargeAmount;
            }

            public void setRechargeAmount(double rechargeAmount)
            {
                this.rechargeAmount = rechargeAmount;
            }

            public int getSearchType()
            {
                return searchType;
            }

            public void setSearchType(int searchType)
            {
                this.searchType = searchType;
            }

            public String getSourceTerminal()
            {
                return sourceTerminal;
            }

            public void setSourceTerminal(String sourceTerminal)
            {
                this.sourceTerminal = sourceTerminal;
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
