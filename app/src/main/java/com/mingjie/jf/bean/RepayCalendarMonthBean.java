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
public class RepayCalendarMonthBean
{

    private List<Integer> list;//当月有回款的日期

    private int sumRepayment;//当月待还总数
    private double sumAmount;//当月待还总金额

    public RepayCalendarMonthBean(List<Integer> list, int sumRepayment, double sumAmount)
    {
        this.list = list;
        this.sumRepayment = sumRepayment;
        this.sumAmount = sumAmount;
    }

    public RepayCalendarMonthBean()
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

    public int getSumRepayment()
    {
        return sumRepayment;
    }

    public void setSumRepayment(int sumRepayment)
    {
        this.sumRepayment = sumRepayment;
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
        return "RepayCalendarMonthBean{" +
                "list=" + list +
                ", sumRepayment=" + sumRepayment +
                ", sumAmount=" + sumAmount +
                '}';
    }
}
