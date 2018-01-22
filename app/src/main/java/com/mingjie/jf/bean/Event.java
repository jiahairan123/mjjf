package com.mingjie.jf.bean;


public class Event {
    /**
     * 登录成功
     */
    public final static int LOGIN_SUCCESS = 100;

    /**
     * 开通存管
     */
    public final static int OPEN_ACCOUNT = 101;

    /**
     * 充值
     */
    public final static int RECHARGE = 102;

    /**
     * 提现
     */
    public final static int WITHDRAW = 103;

    /**
     * 投标
     */
    public final static int INVEST = 104;

    /**
     * 还款
     */
    public final static int REPAYMENT = 105;

    /**
     * 债权转让
     */
    public final static int TRANSFER = 106;
    /**
     * 撤销转让
     */
    public final static int CHEXIAO_TRANSFER = 107;

    /**
     * 成功领取红包
     */
    public final static int GET_REDPACKET = 108;

    /**
     * 账户刷新
     */
    public final static int REFRESH_ACCOUNT = 200;

    /**
     *退出登录
     */
    public final static int LOGOUT = 201;


    /**
     *提交申请
     */
    public final static int TIJIAO = 300;


    /**
     *撤销
     */
    public final static int CHEXIAO =400;
//    /**
//     *删除银行卡
//     */
//    public final static int DELETE = 400;
//    /**
//     *申请绑定
//     */
//    public final static int BIND = 500;
    public int eventType;
    public Object object;

}
