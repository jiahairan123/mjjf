package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我的账户-我的投资（返回）
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-19 15:29
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class MyInvestResponse
{
    public int pageCurrent;//当前页
    public int pageTotal;//总条数
    public int pageSize;//单页请求条数
    public List<ItemMyInvest> list;//标的集合

    @Override
    public String toString()
    {
        return "MyInvestResponse{" +
                "currentPage=" + pageCurrent +
                ", totalSize=" + pageTotal +
                ", pageSize=" + pageSize +
                '}';
    }

    public class ItemMyInvest
    {
        public String productId;//标的id
        public int productType;
        public int assetType;//借款类型
        public String title;//标的名称
        public double bidAmount;//投标金额
        public double profit;//年化收益
        public int productDeadline;//期限
        public double availableAmount;//待收本息
        public double favorableAmount;//已收本息
        public String modeName;//还款方式
        public int mode;//还款方式
        public long repayDate;//还款日期
        public int status;//标的状态
        public int state;//标的类型（4：已转让）
        public String id;//投标详情ID (撤标需要)
        public int planIndex;//已还期数
        public long createdDate;//投标时间
        public String rootProductId;//

        @Override
        public String toString()
        {
            return "ItemMyInvest{" +
                    "productId='" + productId + '\'' +
                    "productType='" + productType + '\'' +
                    ", assetType=" + assetType +
                    ", title='" + title + '\'' +
                    ", bidAmount=" + bidAmount +
                    ", profit=" + profit +
                    ", productDeadline=" + productDeadline +
                    ", availableAmount=" + availableAmount +
                    ", favorableAmount=" + favorableAmount +
                    ", modeName='" + modeName + '\'' +
                    ", mode='" + mode + '\'' +
                    ", repayDate=" + repayDate +
                    ", status=" + status +
                    ", planIndex=" + planIndex +
                    ", id=" + id +
                    '}';
        }
    }
}
