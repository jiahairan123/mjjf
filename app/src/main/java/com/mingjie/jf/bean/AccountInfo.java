package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-27 17:32
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class AccountInfo
{
    String availableBal;//可用余额
    String totalReceipt;//待回款金额
    String frozBl;
    String totalInterest;//累计收益金额
    String totalAmount;//个人总资产
    String acctBal;
    String interestPending; //待回款收益
    String states; //是否签到了

    public AccountInfo(String states) {
        this.states = states;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public AccountInfo(String availableBal, String totalReceipt, String frozBl, String totalInterest, String totalAmount, String acctBal, String interestPending, String rechargeAmountCount) {
        this.availableBal = availableBal;
        this.totalReceipt = totalReceipt;
        this.frozBl = frozBl;
        this.totalInterest = totalInterest;
        this.totalAmount = totalAmount;
        this.acctBal = acctBal;
        this.interestPending = interestPending;
        this.rechargeAmountCount = rechargeAmountCount;
    }

    public String getInterestPending() {
        return interestPending;
    }

    public void setInterestPending(String interestPending) {
        this.interestPending = interestPending;
    }

    public String getRechargeAmountCount()
    {
        return rechargeAmountCount;
    }

    public void setRechargeAmountCount(String rechargeAmountCount)
    {
        this.rechargeAmountCount = rechargeAmountCount;
    }

    String rechargeAmountCount;//累计充值金额

    public String getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getAvailableBal()
    {
        return availableBal;
    }

    public void setAvailableBal(String availableBal)
    {
        this.availableBal = availableBal;
    }

    public String getTotalReceipt()
    {
        return totalReceipt;
    }

    public void setTotalReceipt(String totalReceipt)
    {
        this.totalReceipt = totalReceipt;
    }

    public String getFrozBl()
    {
        return frozBl;
    }

    public void setFrozBl(String frozBl)
    {
        this.frozBl = frozBl;
    }

    public String getTotalInterest()
    {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest)
    {
        this.totalInterest = totalInterest;
    }

    public String getAcctBal()
    {
        return acctBal;
    }

    public void setAcctBal(String acctBal)
    {
        this.acctBal = acctBal;
    }

    @Override
    public String toString()
    {
        return "AccountInfo{" +
                "availableBal='" + availableBal + '\'' +
                ", totalReceipt='" + totalReceipt + '\'' +
                ", frozBl='" + frozBl + '\'' +
                ", totalInterest='" + totalInterest + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", acctBal='" + acctBal + '\'' +
                '}';
    }

}
