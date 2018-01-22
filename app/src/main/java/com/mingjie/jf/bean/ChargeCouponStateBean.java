package com.mingjie.jf.bean;

/**
 * Created by jiahairan on 2017/8/30 09.
 */

public class ChargeCouponStateBean {
    String exchangeState;
    String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ChargeCouponStateBean{" +
                "exchangeState='" + exchangeState + '\'' +
                '}';
    }

    public ChargeCouponStateBean(String exchangeState) {
        this.exchangeState = exchangeState;
    }

    public String getExchangeState() {

        return exchangeState;
    }

    public void setExchangeState(String exchangeState) {
        this.exchangeState = exchangeState;
    }
}
