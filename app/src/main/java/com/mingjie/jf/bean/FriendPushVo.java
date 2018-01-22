package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 创业小伙伴提成返回数据
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-24 18:00
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class FriendPushVo
{


    /**
     * code : 000000
     * data : {"cleanReceiptSum":0,"commissionSums":0,"list":{"list":[{"amount":0.01,"currentPage":1,"deleteFlag":0,"directAmount":0,"expectDate":1475251200000,"indirectAmount":0,"pageSize":6,"stateDesc":"待发"}],"pageCurrent":1,"pageSize":6,"pageTotal":1,"pages":1},"loginUserAmount":7.01,"thisMonthAmountSum":0,"thisMonthCommissionSum":0.01}
     * msg : 成功
     * success : true
     */

    public String code;
    /**
     * cleanReceiptSum : 0.0
     * commissionSums : 0.0
     * list : {"list":[{"amount":0.01,"currentPage":1,"deleteFlag":0,"directAmount":0,"expectDate":1475251200000,"indirectAmount":0,"pageSize":6,"stateDesc":"待发"}],"pageCurrent":1,"pageSize":6,"pageTotal":1,"pages":1}
     * loginUserAmount : 7.01
     * thisMonthAmountSum : 0.0
     * thisMonthCommissionSum : 0.01
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
        public double cleanReceiptSum;
        public double commissionSums;
        /**
         * list : [{"amount":0.01,"currentPage":1,"deleteFlag":0,"directAmount":0,"expectDate":1475251200000,"indirectAmount":0,"pageSize":6,"stateDesc":"待发"}]
         * pageCurrent : 1
         * pageSize : 6
         * pageTotal : 1
         * pages : 1
         */

        public ListBeans list;
        public double loginUserAmount;
        public double thisMonthAmountSum;
        public double thisMonthCommissionSum;

        public double getCleanReceiptSum()
        {
            return cleanReceiptSum;
        }

        public void setCleanReceiptSum(double cleanReceiptSum)
        {
            this.cleanReceiptSum = cleanReceiptSum;
        }

        public double getCommissionSums()
        {
            return commissionSums;
        }

        public void setCommissionSums(double commissionSums)
        {
            this.commissionSums = commissionSums;
        }

        public ListBeans getList()
        {
            return list;
        }

        public void setList(ListBeans list)
        {
            this.list = list;
        }

        public double getLoginUserAmount()
        {
            return loginUserAmount;
        }

        public void setLoginUserAmount(double loginUserAmount)
        {
            this.loginUserAmount = loginUserAmount;
        }

        public double getThisMonthAmountSum()
        {
            return thisMonthAmountSum;
        }

        public void setThisMonthAmountSum(double thisMonthAmountSum)
        {
            this.thisMonthAmountSum = thisMonthAmountSum;
        }

        public double getThisMonthCommissionSum()
        {
            return thisMonthCommissionSum;
        }

        public void setThisMonthCommissionSum(double thisMonthCommissionSum)
        {
            this.thisMonthCommissionSum = thisMonthCommissionSum;
        }

        public static class ListBeans
        {
            public int pageCurrent;
            public int pageSize;
            public int pageTotal;
            public int pages;
            /**
             * amount : 0.01
             * currentPage : 1
             * deleteFlag : 0
             * directAmount : 0
             * expectDate : 1475251200000
             * indirectAmount : 0
             * pageSize : 6
             * stateDesc : 待发
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
                public double amount;
                public int currentPage;
                public int deleteFlag;
                public double directAmount;
                public long expectDate;
                public double indirectAmount;
                public int pageSize;
                public String stateDesc;

                public double getAmount()
                {
                    return amount;
                }

                public void setAmount(double amount)
                {
                    this.amount = amount;
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

                public double getDirectAmount()
                {
                    return directAmount;
                }

                public void setDirectAmount(double directAmount)
                {
                    this.directAmount = directAmount;
                }

                public long getExpectDate()
                {
                    return expectDate;
                }

                public void setExpectDate(long expectDate)
                {
                    this.expectDate = expectDate;
                }

                public double getIndirectAmount()
                {
                    return indirectAmount;
                }

                public void setIndirectAmount(double indirectAmount)
                {
                    this.indirectAmount = indirectAmount;
                }

                public int getPageSize()
                {
                    return pageSize;
                }

                public void setPageSize(int pageSize)
                {
                    this.pageSize = pageSize;
                }

                public String getStateDesc()
                {
                    return stateDesc;
                }

                public void setStateDesc(String stateDesc)
                {
                    this.stateDesc = stateDesc;
                }
            }
        }
    }
}
