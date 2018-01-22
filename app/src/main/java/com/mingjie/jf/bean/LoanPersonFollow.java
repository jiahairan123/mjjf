package com.mingjie.jf.bean;

/**
 * Created by jiahairan on 2018/1/4 11.
 */

public class LoanPersonFollow {

    String slipindexStr;
    String slipdateyesStr;
    String slipdateStr;
    String borrowerfunding;
    String borrowerfinance;
    String borrowerrepaystate;
    String borrowerpunish;


    public String getSlipindexStr() {
        return slipindexStr;
    }

    public void setSlipindexStr(String slipindexStr) {
        this.slipindexStr = slipindexStr;
    }

    public String getSlipdateyesStr() {
        return slipdateyesStr;
    }

    public void setSlipdateyesStr(String slipdateyesStr) {
        this.slipdateyesStr = slipdateyesStr;
    }

    public String getSlipdateStr() {
        return slipdateStr;
    }

    public void setSlipdateStr(String slipdateStr) {
        this.slipdateStr = slipdateStr;
    }

    public String getBorrowerfunding() {
        return borrowerfunding;
    }

    public void setBorrowerfunding(String borrowerfunding) {
        this.borrowerfunding = borrowerfunding;
    }

    public String getBorrowerfinance() {
        return borrowerfinance;
    }

    public void setBorrowerfinance(String borrowerfinance) {
        this.borrowerfinance = borrowerfinance;
    }

    public String getBorrowerrepaystate() {
        return borrowerrepaystate;
    }

    public void setBorrowerrepaystate(String borrowerrepaystate) {
        this.borrowerrepaystate = borrowerrepaystate;
    }

    public String getBorrowerpunish() {
        return borrowerpunish;
    }

    public void setBorrowerpunish(String borrowerpunish) {
        this.borrowerpunish = borrowerpunish;
    }


    public LoanPersonFollow(String slipindexStr, String slipdateyesStr, String slipdateStr, String borrowerfunding, String borrowerfinance, String borrowerrepaystate, String borrowerpunish) {
        this.slipindexStr = slipindexStr;
        this.slipdateyesStr = slipdateyesStr;
        this.slipdateStr = slipdateStr;
        this.borrowerfunding = borrowerfunding;
        this.borrowerfinance = borrowerfinance;
        this.borrowerrepaystate = borrowerrepaystate;
        this.borrowerpunish = borrowerpunish;
    }
}
