package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 账户 - 债权转让
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-14 16:53
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AccountDebtBean {
    private String code;
    private DataBean data;
    private String msg;

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

    public static class DataBean {
        private BidPagerBean bidPager;
        private int searchType;

        public BidPagerBean getBidPager() {
            return bidPager;
        }

        public void setBidPager(BidPagerBean bidPager) {
            this.bidPager = bidPager;
        }

        public int getSearchType() {
            return searchType;
        }

        public void setSearchType(int searchType) {
            this.searchType = searchType;
        }

        public static class BidPagerBean {
            private int pageCurrent;
            private int pageSize;
            private int pageTotal;
            private List<ListBean> list;

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

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private int allSurplusReceiptIndex;
                private double amount;
                private int assetType;
                private int cardTypeInt;
                private long createdDate;
                private int currentPage;
                private int deleteFlag;
                private String detailId;
                private long fullBidDate;
                private long latelyRepaymentDate;
                private int leftSurplusReceiptIndex;
                private int pageSize;
                private String productId;
                private double profit;
                private int repurchaseMode;
                private String repurchaseModeDesc;
                private String sourceTerminal;
                private String surplusTotal;
                private String title;
                private String userId;
                private double waitCapital;
                private double waitCapitalInterest;
                private double waitInterest;
                private double waitServiceFee;

                public int getAllSurplusReceiptIndex() {
                    return allSurplusReceiptIndex;
                }

                public void setAllSurplusReceiptIndex(int allSurplusReceiptIndex) {
                    this.allSurplusReceiptIndex = allSurplusReceiptIndex;
                }

                public double getAmount() {
                    return amount;
                }

                public void setAmount(double amount) {
                    this.amount = amount;
                }

                public int getAssetType() {
                    return assetType;
                }

                public void setAssetType(int assetType) {
                    this.assetType = assetType;
                }

                public int getCardTypeInt() {
                    return cardTypeInt;
                }

                public void setCardTypeInt(int cardTypeInt) {
                    this.cardTypeInt = cardTypeInt;
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

                public String getDetailId() {
                    return detailId;
                }

                public void setDetailId(String detailId) {
                    this.detailId = detailId;
                }

                public long getFullBidDate() {
                    return fullBidDate;
                }

                public void setFullBidDate(long fullBidDate) {
                    this.fullBidDate = fullBidDate;
                }

                public long getLatelyRepaymentDate() {
                    return latelyRepaymentDate;
                }

                public void setLatelyRepaymentDate(long latelyRepaymentDate) {
                    this.latelyRepaymentDate = latelyRepaymentDate;
                }

                public int getLeftSurplusReceiptIndex() {
                    return leftSurplusReceiptIndex;
                }

                public void setLeftSurplusReceiptIndex(int leftSurplusReceiptIndex) {
                    this.leftSurplusReceiptIndex = leftSurplusReceiptIndex;
                }

                public int getPageSize() {
                    return pageSize;
                }

                public void setPageSize(int pageSize) {
                    this.pageSize = pageSize;
                }

                public String getProductId() {
                    return productId;
                }

                public void setProductId(String productId) {
                    this.productId = productId;
                }

                public double getProfit() {
                    return profit;
                }

                public void setProfit(double profit) {
                    this.profit = profit;
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

                public String getSourceTerminal() {
                    return sourceTerminal;
                }

                public void setSourceTerminal(String sourceTerminal) {
                    this.sourceTerminal = sourceTerminal;
                }

                public String getSurplusTotal() {
                    return surplusTotal;
                }

                public void setSurplusTotal(String surplusTotal) {
                    this.surplusTotal = surplusTotal;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public double getWaitCapital() {
                    return waitCapital;
                }

                public void setWaitCapital(double waitCapital) {
                    this.waitCapital = waitCapital;
                }

                public double getWaitCapitalInterest() {
                    return waitCapitalInterest;
                }

                public void setWaitCapitalInterest(double waitCapitalInterest) {
                    this.waitCapitalInterest = waitCapitalInterest;
                }

                public double getWaitInterest() {
                    return waitInterest;
                }

                public void setWaitInterest(double waitInterest) {
                    this.waitInterest = waitInterest;
                }

                public double getWaitServiceFee() {
                    return waitServiceFee;
                }

                public void setWaitServiceFee(double waitServiceFee) {
                    this.waitServiceFee = waitServiceFee;
                }
            }
        }
    }
}
