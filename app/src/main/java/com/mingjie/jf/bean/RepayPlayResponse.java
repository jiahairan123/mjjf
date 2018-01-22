package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 标的详情-还款计划详情
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-23 15:31
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RepayPlayResponse
{

    private List<ItemData> repayPage;

    public void setRepayPage(List<ItemData> repayPage)
    {
        this.repayPage = repayPage;
    }

    public List<ItemData> getRepayPage()
    {
        return repayPage;
    }

    public class ItemData
    {
        private String productTitle;//标题
        private long expireDate;//还款时间
        private double capital;//本金
        private double interest;//利息
        private double lateFee;//罚息
        private String stateDesc;//状态
        private int productDeadline;//总期数
        private int planIndex;//当前期数
        private int assetType;//产品类型
        private String repurchaseMode;//还款方式

        public ItemData()
        {
        }

        public ItemData(String productTitle, long expireDate, double capital, double interest, double lateFee,
                        String stateDesc, int productDeadline, int planIndex, int assetType, String repurchaseMode)
        {
            this.productTitle = productTitle;
            this.expireDate = expireDate;
            this.capital = capital;
            this.interest = interest;
            this.lateFee = lateFee;
            this.stateDesc = stateDesc;
            this.productDeadline = productDeadline;
            this.planIndex = planIndex;
            this.assetType = assetType;
            this.repurchaseMode = repurchaseMode;
        }

        public String getProductTitle()
        {
            return productTitle;
        }

        public void setProductTitle(String productTitle)
        {
            this.productTitle = productTitle;
        }

        public long getExpireDate()
        {
            return expireDate;
        }

        public void setExpireDate(long expireDate)
        {
            this.expireDate = expireDate;
        }

        public double getCapital()
        {
            return capital;
        }

        public void setCapital(double capital)
        {
            this.capital = capital;
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

        public String getStateDesc()
        {
            return stateDesc;
        }

        public void setStateDesc(String stateDesc)
        {
            this.stateDesc = stateDesc;
        }

        public int getProductDeadline()
        {
            return productDeadline;
        }

        public void setProductDeadline(int productDeadline)
        {
            this.productDeadline = productDeadline;
        }

        public int getPlanIndex()
        {
            return planIndex;
        }

        public void setPlanIndex(int planIndex)
        {
            this.planIndex = planIndex;
        }

        public int getAssetType()
        {
            return assetType;
        }

        public void setAssetType(int assetType)
        {
            this.assetType = assetType;
        }

        public String getRepurchaseMode()
        {
            return repurchaseMode;
        }

        public void setRepurchaseMode(String repurchaseMode)
        {
            this.repurchaseMode = repurchaseMode;
        }

        @Override
        public String toString()
        {
            return "RepayPlayResponse{" +
                    "productTitle='" + productTitle + '\'' +
                    ", expireDate=" + expireDate +
                    ", capital=" + capital +
                    ", interest=" + interest +
                    ", lateFee=" + lateFee +
                    ", stateDesc='" + stateDesc + '\'' +
                    ", productDeadline=" + productDeadline +
                    ", planIndex=" + planIndex +
                    ", assetType=" + assetType +
                    ", repurchaseMode=" + repurchaseMode +
                    '}';
        }
    }
}
