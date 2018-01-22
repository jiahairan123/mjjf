package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述：提现记录流水返回实体
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-11-23 16:29
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class WithDrawRecord
{

    /**
     * amount : 20.0
     * bankNo : 9
     * bankNoDesc : 广发银行
     * bankcardId : 4ee7edcebd0e4c72a551137ed943446e
     * cardNo : 9999
     * createDate : 1479884627000
     * currentPage : 1
     * deleteFlag : 0
     * flowNo : P2P04120161123045DSeZItcl94w
     * id : 8e6d6ae5bd9348378e2b2b72c991fd32
     * pageSize : 6
     * sourceTerminal : PC
     * state : 1
     * stateDesc : 待审核
     * userId : d5c89bb6ff934716b63c4db41a6cad64
     * version : 1479884627000
     */

    private List<ListBean> list;
    /**
     * pageCurrent : 1
     * pageSize : 8
     * pageTotal : 30
     * pages : 4
     */

    private int pageCurrent;
    private int pageSize;
    private int pageTotal;
    private int pages;

    public List<ListBean> getList()
    {
        return list;
    }

    public void setList(List<ListBean> list)
    {
        this.list = list;
    }

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

    public static class ListBean
    {
        private double amount;
        private String bankNo;
        private String bankNoDesc;
        private String bankcardId;
        private String cardNo;
        private long createDate;
        private int currentPage;
        private int deleteFlag;
        private String flowNo;
        private String id;
        private int pageSize;
        private String sourceTerminal;
        private int state;
        private String stateDesc;
        private String userId;
        private long version;

        public double getAmount()
        {
            return amount;
        }

        public void setAmount(double amount)
        {
            this.amount = amount;
        }

        public String getBankNo()
        {
            return bankNo;
        }

        public void setBankNo(String bankNo)
        {
            this.bankNo = bankNo;
        }

        public String getBankNoDesc()
        {
            return bankNoDesc;
        }

        public void setBankNoDesc(String bankNoDesc)
        {
            this.bankNoDesc = bankNoDesc;
        }

        public String getBankcardId()
        {
            return bankcardId;
        }

        public void setBankcardId(String bankcardId)
        {
            this.bankcardId = bankcardId;
        }

        public String getCardNo()
        {
            return cardNo;
        }

        public void setCardNo(String cardNo)
        {
            this.cardNo = cardNo;
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

        public String getStateDesc()
        {
            return stateDesc;
        }

        public void setStateDesc(String stateDesc)
        {
            this.stateDesc = stateDesc;
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
