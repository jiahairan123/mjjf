package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 私房钱返回实体
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-11-23 16:29
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class CaseManager
{
    private WalletBean wallet;
    private WalletFlowBean WalletFlow;

    public WalletBean getWallet()
    {
        return wallet;
    }

    public void setWallet(WalletBean wallet)
    {
        this.wallet = wallet;
    }

    public WalletFlowBean getWalletFlow()
    {
        return WalletFlow;
    }

    public void setWalletFlow(WalletFlowBean WalletFlow)
    {
        this.WalletFlow = WalletFlow;
    }

    public static class WalletBean
    {
        private double amount;
        private double frozenAmount;

        public double getAmount()
        {
            return amount;
        }

        public void setAmount(double amount)
        {
            this.amount = amount;
        }

        public double getFrozenAmount()
        {
            return frozenAmount;
        }

        public void setFrozenAmount(double frozenAmount)
        {
            this.frozenAmount = frozenAmount;
        }
    }

    public static class WalletFlowBean
    {
        private int pageCurrent;
        private int pageSize;
        private int pageTotal;
        private int pages;
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
            private double amount;
            private long createDate;
            private int currentPage;
            private int deleteFlag;
            private String flowNo;
            private int flowType;
            private String flowTypeDesc;
            private String id;
            private int pageSize;
            private String sourceTerminal;
            private int state;
            private String userName;
            private long version;
            private String description;//来源备注

            public String getDescription()
            {
                return description;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }



            public double getAmount()
            {
                return amount;
            }

            public void setAmount(double amount)
            {
                this.amount = amount;
            }

            public long getCreateDate()
            {
                return createDate;
            }

            public void setCreateDate(long createDate)
            {
                this.createDate = createDate;
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

            public String getFlowNo()
            {
                return flowNo;
            }

            public void setFlowNo(String flowNo)
            {
                this.flowNo = flowNo;
            }

            public int getFlowType()
            {
                return flowType;
            }

            public void setFlowType(int flowType)
            {
                this.flowType = flowType;
            }

            public String getFlowTypeDesc()
            {
                return flowTypeDesc;
            }

            public void setFlowTypeDesc(String flowTypeDesc)
            {
                this.flowTypeDesc = flowTypeDesc;
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

            public String getUserName()
            {
                return userName;
            }

            public void setUserName(String userName)
            {
                this.userName = userName;
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
