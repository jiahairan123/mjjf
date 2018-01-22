package com.mingjie.jf.utils;

import com.mingjie.jf.bean.CalculateBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 收益计算方法
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-06 10:44
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CalculatorMonth
{

    /**
     * 按月先息后本
     *
     * @param amount         本金
     * @param rate           利息
     * @param planGroupCount 总期数
     * @return
     * @author hexiangjun
     * @date 2016/9/6 15:16
     * @description
     */
    public static List<CalculateBean> getInterestAndCapitalPerMonth2(BigDecimal amount,
                                                                     BigDecimal rate, int planGroupCount)
    {
        List<CalculateBean> mResult = new ArrayList<>();
        BigDecimal capital = new BigDecimal("0.00");
        for (int i = 1; i <= planGroupCount; i++)
        {
            CalculateBean tempBean = new CalculateBean();
            if (i == planGroupCount)
            {//只有最后一期才需要还本金
                capital = amount;
            }
            BigDecimal interest = CalculatorMonth.calculateInterestInner(amount, rate);
            tempBean.mLixi = String.valueOf(interest);
            tempBean.mBenjin = String.valueOf(capital);
            double tempTotal = Double.parseDouble(tempBean.mLixi) + Double.parseDouble(tempBean.mBenjin);
            tempBean.mTotal = String.valueOf(tempTotal);

            mResult.add(tempBean);

        }
        return mResult;
    }

    /**
     * 默认以月为单位的利息计算时，计算的是1个月的利息
     */
    public static BigDecimal calculateInterestInner(BigDecimal amount, BigDecimal rate)
    {
        return amount.multiply(rate)
                .divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
    }

    /**
     * 按月等额本息
     * 计算每期还款金额，按照金融公式：
     * <p>
     * 期利率* (1+期利率)^期数
     * 期还款 = 本金 * -----------------------------------
     * (1+期利率)^期数 -1
     *
     * @param amount         本金
     * @param rate           利息
     * @param totalTimeCount 总期数
     */
    public static BigDecimal getPlanPayment(
            BigDecimal amount,
            BigDecimal rate,
            int totalTimeCount)
    {
        //期利率
        BigDecimal ratePlan = CalculatorMonth.getPlanRate(rate);

        //(1+期利率)^借款期数
        BigDecimal v = ratePlan.add(BigDecimal.valueOf(1)).pow(totalTimeCount);

        //本金*期利率*(1+期利率)^期数
        BigDecimal element = amount.multiply(ratePlan).multiply(v);
        //(1+期利率)^期数 -1
        BigDecimal denominator = v.subtract(BigDecimal.valueOf(1));

        return element.divide(denominator, 6, RoundingMode.HALF_UP);//保留2精度
    }

    public static BigDecimal getPlanRate(BigDecimal rate)
    {
        return rate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);//保留10精度，相当不取舍
    }

    /**
     * 按月等额本息 计算每月利息和本金
     *
     * @param amount     本金
     * @param rate       利率
     * @param totalCount 总期数
     * @return
     * @author hexiangjun
     * @date 2016/9/6 15:16
     * @description
     */
    public static List<CalculateBean> getInterestAndCapitalPerMonth1(BigDecimal amount, BigDecimal rate,
                                                                     int totalCount)
    {
        List<CalculateBean> mResult = new ArrayList<>();
        BigDecimal payPerMonth = getPlanPayment(amount, rate, totalCount);
        BigDecimal capital = new BigDecimal(0);
        BigDecimal interest;
        BigDecimal other= new BigDecimal(0.00);
        for (int i = 1; i <= totalCount - 1; i++)
        {
            BigDecimal a = amount.subtract(capital);
            BigDecimal b = a.multiply(rate);
            interest = b.divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_DOWN);
            BigDecimal benjin1 = payPerMonth.subtract(interest);
            benjin1 = benjin1.setScale(2,BigDecimal.ROUND_DOWN);
            capital = benjin1.add(capital);
            CfLog.i("---" + capital);

            CalculateBean tempBean = new CalculateBean();
            tempBean.mLixi = String.valueOf(interest);
            tempBean.mBenjin = String.valueOf(benjin1);
            other = other.add(BigDecimal.valueOf(Double.parseDouble(tempBean.mBenjin)));
            double tempTotal = Double.parseDouble(tempBean.mLixi) + Double.parseDouble(tempBean.mBenjin);
            tempBean.mTotal = String.valueOf(tempTotal);
            mResult.add(tempBean);

        }
        CalculateBean lastTempBean = new CalculateBean();
        lastTempBean.mBenjin = String.valueOf(amount.doubleValue()-other.doubleValue());
        lastTempBean.mLixi =String.valueOf((amount.subtract(other)).multiply(rate).divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_DOWN));
        lastTempBean.mTotal = String.valueOf(Double.parseDouble(lastTempBean.mBenjin)+Double.parseDouble(lastTempBean.mLixi));

        mResult.add(lastTempBean);
        return mResult;
    }

    /**
     * 按日计息，到期还本息
     *
     * @param amount         本金
     * @param rate           利率
     * @param planGroupCount 期限
     * @return
     * @author hexiangjun
     * @date 2016/9/6 15:16
     * @description
     */
    public static List<CalculateBean> getInterestAndCapitalPerDay(BigDecimal amount,
                                                                  BigDecimal rate, int planGroupCount)
    {
        List<CalculateBean> mResult = new ArrayList<>();
        BigDecimal capital;
        CalculateBean tempBean = new CalculateBean();

        BigDecimal interest = CalculatorMonth.calculateInterestInnerDay(amount, rate, planGroupCount);
        tempBean.mLixi = String.valueOf(interest);
        capital = amount;
        tempBean.mBenjin = String.valueOf(capital);
        double tempTotal = Double.parseDouble(tempBean.mLixi) + Double.parseDouble(tempBean.mBenjin);
        tempBean.mTotal = String.valueOf(tempTotal);

        mResult.add(tempBean);

        return mResult;
    }

    /**
     * 默认以天为单位的利息计算时，计算的是1天的利息
     */
    public static BigDecimal calculateInterestInnerDay(BigDecimal amount, BigDecimal rate, int timeCount)
    {
        return amount.multiply(rate)
                .multiply(new BigDecimal(timeCount))
                .divide(new BigDecimal("360"), 2, RoundingMode.HALF_UP);
    }

}
