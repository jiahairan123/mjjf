package com.mingjie.jf.bean;

/**
 * Created by jiahairan on 2017/8/25 10.
 */

public class CouponStateBean {
    String state;
    String voucherState;

    public String getVoucherState() {
        return voucherState;
    }

    public void setVoucherState(String voucherState) {
        this.voucherState = voucherState;
    }

    public CouponStateBean(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
