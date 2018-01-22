package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 回款日历月数据
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-15 15:46
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ReturnCalendarMonthBean
{

    private List<Integer> list;//当月有回款的日期

    private double waitAmountSum;//当月待收总额
    private double sumAmount;//当月应收总金额

    public ReturnCalendarMonthBean(List list, double waitAmountSum, double sumAmount)
    {
        this.list = list;
        this.waitAmountSum = waitAmountSum;
        this.sumAmount = sumAmount;
    }

    public ReturnCalendarMonthBean()
    {
    }

    public List<Integer> getList()
    {
        return list;
    }

    public void setList(List<Integer> list)
    {
        this.list = list;
    }

    public double getWaitAmountSum()
    {
        return waitAmountSum;
    }

    public void setWaitAmountSum(double waitAmountSum)
    {
        this.waitAmountSum = waitAmountSum;
    }

    public double getSumAmount()
    {
        return sumAmount;
    }

    public void setSumAmount(double sumAmount)
    {
        this.sumAmount = sumAmount;
    }

    @Override
    public String toString()
    {
        return "ReturnCalendarMonthBean{" +
                "list=" + list +
                ", waitAmountSum=" + waitAmountSum +
                ", sumAmount=" + sumAmount +
                '}';
    }
}
