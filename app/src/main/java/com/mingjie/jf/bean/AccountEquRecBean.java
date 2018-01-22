package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-26 15:55
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AccountEquRecBean
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
        private ReceiptTransferPagerBean receiptTransferPager;

        public ReceiptTransferPagerBean getReceiptTransferPager()
        {
            return receiptTransferPager;
        }

        public void setReceiptTransferPager(ReceiptTransferPagerBean receiptTransferPager)
        {
            this.receiptTransferPager = receiptTransferPager;
        }

        public static class ReceiptTransferPagerBean
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
                private int assetType;
                private long createdDate;
                private int currentPage;
                private int deadLine;
                private int deleteFlag;
                private String id;
                private int pageSize;
                private int productDeadline;
                private String productId;
                private int productStatus;
                private double profit;
                private String rateOfDiscount;
                private int repurchaseMode;
                private double serviceFeeAmount;
                private String sourceTerminal;
                private String stateDesc;
                private int stateInt;
                private double successAmount;
                private int surplusReceiptIndex;
                private String title;
                private double transferAmount;
                private double transferCapital;
                private int transferNumber;

                public int getAssetType()
                {
                    return assetType;
                }

                public void setAssetType(int assetType)
                {
                    this.assetType = assetType;
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

                public int getDeadLine()
                {
                    return deadLine;
                }

                public void setDeadLine(int deadLine)
                {
                    this.deadLine = deadLine;
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

                public int getProductStatus()
                {
                    return productStatus;
                }

                public void setProductStatus(int productStatus)
                {
                    this.productStatus = productStatus;
                }

                public double getProfit()
                {
                    return profit;
                }

                public void setProfit(double profit)
                {
                    this.profit = profit;
                }

                public String getRateOfDiscount()
                {
                    return rateOfDiscount;
                }

                public void setRateOfDiscount(String rateOfDiscount)
                {
                    this.rateOfDiscount = rateOfDiscount;
                }

                public int getRepurchaseMode()
                {
                    return repurchaseMode;
                }

                public void setRepurchaseMode(int repurchaseMode)
                {
                    this.repurchaseMode = repurchaseMode;
                }

                public double getServiceFeeAmount()
                {
                    return serviceFeeAmount;
                }

                public void setServiceFeeAmount(double serviceFeeAmount)
                {
                    this.serviceFeeAmount = serviceFeeAmount;
                }

                public String getSourceTerminal()
                {
                    return sourceTerminal;
                }

                public void setSourceTerminal(String sourceTerminal)
                {
                    this.sourceTerminal = sourceTerminal;
                }

                public String getStateDesc()
                {
                    return stateDesc;
                }

                public void setStateDesc(String stateDesc)
                {
                    this.stateDesc = stateDesc;
                }

                public int getStateInt()
                {
                    return stateInt;
                }

                public void setStateInt(int stateInt)
                {
                    this.stateInt = stateInt;
                }

                public double getSuccessAmount()
                {
                    return successAmount;
                }

                public void setSuccessAmount(double successAmount)
                {
                    this.successAmount = successAmount;
                }

                public int getSurplusReceiptIndex()
                {
                    return surplusReceiptIndex;
                }

                public void setSurplusReceiptIndex(int surplusReceiptIndex)
                {
                    this.surplusReceiptIndex = surplusReceiptIndex;
                }

                public String getTitle()
                {
                    return title;
                }

                public void setTitle(String title)
                {
                    this.title = title;
                }

                public double getTransferAmount()
                {
                    return transferAmount;
                }

                public void setTransferAmount(double transferAmount)
                {
                    this.transferAmount = transferAmount;
                }

                public double getTransferCapital()
                {
                    return transferCapital;
                }

                public void setTransferCapital(double transferCapital)
                {
                    this.transferCapital = transferCapital;
                }

                public int getTransferNumber()
                {
                    return transferNumber;
                }

                public void setTransferNumber(int transferNumber)
                {
                    this.transferNumber = transferNumber;
                }
            }
        }
    }
}
