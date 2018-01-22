package com.mingjie.jf.constant;

/**
 * Created by zhangJin on 2016/7/25.
 */
public class ServiceName {
    // 注册
    public static final String APP_USER_REGISTER = "CG0001";
    // 登录
    public static final String APP_USER_LOGIN = "CG0002";
    // 短信验证码
    public static final String REGISTER_PHONE_CODE = "CG0003";
    public static final String APP_USER_LOGOUT = "CG0004"; // 退出
    public static final String RESET_MODIFY_PASSWORD = "CG0005"; // 修改登录密码
    // 忘记密码第一步
    public static final String CGKX_DOFORGETPWD = "CG0006";
    public static final String CGKX_DOFORGETPWD2 = "CG0007"; // 忘记密码第二步确定
    public static final String RESET_PHONE_CODE = "CG0008"; // 重置密码短信验证码
    public static final String RESET_MODIFY_MOBILE = "CG0009"; // 修改手机
    public static final String RESET_MODIFY_MOBILE2 = "CG0010";
    public static final String RESET_MODIFY_EMAIL = "CG0011"; // 绑定邮箱第一步
    public static final String RESET_MODIFY_EMAIL2 = "CG0012"; // 绑定邮箱第二步
    /**
     * 反馈意见
     */
    public static final String CGKX_ADDUSERSUGGESTION = "CG0014";
    // 版本升级
    public static final String UPDATEVERSIONS = "CG0015";
    // 更多里的公告列表
    public static final String CGKX_ANNOUNCELIST = "CG0016";
    public static final String CGKX_ANNOUNCELIST_NUM = "CG0017"; // 公告详情
    // APP配置信息（客服电话）
    public static final String CGKX_CONFIGINFO = "CG0018";
    // 首页广告和公告
    public static final String CGKX_INDEXCONTENT = "CG0019";
    public static final String HOME_PRODUCTlIST = "CG0020"; // 首页标的情况
    public static final String INVESTMENT_LIST = "CG0021"; // 债权转让
    public static final String INVESTMENT_ACQUISITION = "CG0022"; // 债权转让详情
    public static final String INVESTMENT_BORROWINGINFO = "CG0023"; // 借款信息
    public static final String CGKX_ACCOUNT = "CG0024"; // 账户概况
    public static final String CGKX_FLOW = "CG0025"; // 交易记录
    public static final String CGKX_USERINFO = "CG0026"; // 账户设置
    public static final String CGKX_OPENGHBANKACCOUNT = "CG0027"; // 开通存管
    public static final String CGKX_QUERYCREATEACCRESULT = "CG0028"; // 开户结果查询
    public static final String CGKX_BOUNDACC = "CG0029"; // 去绑卡
    public static final String CGKX_RECHARGELIST = "CG0030"; // 充值列表查询
    public static final String CGKX_RECHARGE = "CG0031"; // 充值
    public static final String CGKX_CHECKRECHARGE = "CG0032"; // 充值结果查询
    public static final String CGKX_WITHDRAWLIST = "CG0033"; // 提现记录
    public static final String CGKX_WITHDRAW = "CG0034"; // 提现
    public static final String CGKX_CHECKWITHDRAW = "CG0035"; // 提现结果查询
    public static final String CGKX_INVESTMENTPROCESS = "CG0037"; // 投资处理中
    // 我的账户-我的投资
    public static final String CGKX_MYINVESTMENT = "CG0038";
    // 我的账户-我的投资-投资中-撤标
    public static final String CGKX_WITHDRAWBID = "CG0039";
    public static final String CGKX_RECEIPTLIST = "CG0040"; // 收款计划
    public static final String CGKX_GETAGENCYS = "CG0041"; // 承接公司
    public static final String CGKX_ADDRESERVEBORROW = "CG0042"; // 申请借款
    public static final String CGKX_RECOMMEND = "CG0043"; // 我的推荐
    public static final String CGKX_COMMISSIONLISTDATA = "CG0044"; // 创业小伙提成列表
    public static final String CGKX_COMMISSIONFRIEND = "CG0045"; // 创业小伙伴申请状态
    public static final String CGKX_APPLYCOMMISSION = "CG0046"; // 创业小伙伴申请
    // 自动投资设置(CG0047)
    // 自动投资列表(CG0048)
    // 标的详情
    public static final String CGKX_PRODUCTDETAIL = "CG0049";
    // 标的详情-投资
    public static final String SCATTERED_RECORD = "CG0050";
    // 标的详情-还款计划
    public static final String SCATTERED_REPAYMENT = "CG0051";
    // 标的详情-立即投标
    public static final String SCATTERED_BID = "CG0052";
    // 投标结果查询
    public static final String SCATTERED_SEARCHBIDRESULT = "CG0053";
    public static final String BORROW_REPAYMENT = "CG0054"; // 还款中
    public static final String BORROW_PRODUCTINREVIEW = "CG0055"; // 审核中
    public static final String BORROW_PRODUCTAPPROVED = "CG0056"; // 审核通过
    public static final String BORROW_PROUDCTFINISHED = "CG0057"; // 已完结
    public static final String REPAYMENT_GETREPAYLIST = "CG0058"; // 还款计划
    public static final String REPAYMENT_SUBMITREPAY = "CG0059"; // 提交还款
    public static final String REPAYMENT_GETRECEIPTLIST = "CG0060"; // 收款计划明细
    public static final String REPAYMENT_SUBMITRESULT = "CG0061"; // 还款结果查询
    // 私房钱流水
    public static final String WALLETFLOWPAGE = "CG0067";
    // 申请提现
    public static final String APPLYWITHDRAWALS = "CG0068";
    // 提交申请提现
    public static final String WITHDRAW = "CG0069";
    // 提现记录
    public static final String WITHDRAWALSFLOWPAGE = "CG0070";
    // 撤销
    public static final String CANCELWITHDRAWALS = "CG0071";
    // 绑卡
    public static final String BINDBANKCARD = "CG0072";
    // 银行卡信息查询
    public static final String BANKCARDINFO = "CG0073";
    // 删除银行卡信息
    public static final String DELBANKCARD = "CG0074";
    public static final String QUERY_DEBT = "CG0075"; // 账户 - 债权转让
    public static final String CHECK_LOAD_RECEIPT_TRANSFER = "CG0076"; // 账户 - 判断是否可转让
    public static final String LOAD_RECEIPT_TRANSFER = "CG0077"; // 账户 - 债权信息
    public static final String CREDITORRIGHTSTRANSFER = "CG0078"; // 账户 - 提交转让
    public static final String QUERY_RECEIPT_TRANSFER_RESULT = "CG0079"; // 账户 - 提交转让
    public static final String CUSTOMERSERVCELIST = "CG0080"; // 客服列表
    public static final String BINDINGCUSTOMERSERVCE = "CG0081"; // 绑定客服
    public static final String FINDUSERCUSTOMERSERVCE = "CG0082"; // 客服详情
    public static final String TEST_INVESTMENT_LIST = "CG0083"; // 测试债权转让
    // 还款日历 月数据
    public static final String REPAYMENTPLAN_CALENDAR = "CG0086";
    // 还款日历 日数据
    public static final String REPAYMENTPLAN_LIST = "CG0087";
    // 还款列表
    public static final String REPAYMENTPLANCALENDAR_LIST = "CG0088";
    // 回款日历 月数据
    public static final String RECEIPTPLAN_CALENDAR = "CG0089";
    // 回款日历 日数据
    public static final String RECEIPTPLAN_LIST = "CG0090";
    // 回款列表
    public static final String RECEIPTPLANCALENDAR_LIST = "CG0091";
    public static final String LOAD_REVOKE_RECEIPT_TRANSFER = "CG0092"; // 账户 - 撤销债权转让
    // public static final String NOTIFY = "CG0093"; // 通知
    // 账户 - 转让记录（占用通知）
    public static final String QUERY_DEBT_LIST = "CG0093";
    public static final String RECEIPT_TRANSFER_DETAIL = "CG0095"; // 账户 - 债权详情
    public static final String PURCHASE_RECORD = "CG0096"; // 账户 - 转让记录
    // 红包列表
    public static final String REDBAGLIST = "CG0097";
    // 现金券列表
    public static final String CASHLIST = "CG0098";
    // 领取红包
    public static final String GETREDBAG = "CG0099";
    public static final String CGKX_MYINVESTMENTRECORD = "CG0100"; // 账户 - 投资记录
    //投标详情获取优惠券列表
    public static final String GET_CASH_LIST = "CG0101";
    //加息券列表
    public static final String COUPONLIST = "CG0102";
    //加息券列表详情
    public static final String COUPONLISTDETAIL = "CG0103";
    public static final String CGKX_SHOUYAOQINGREN = "CG0996"; // 提成记录
    public static final String CGKX_TICHENGGUANLI = "CG0997"; // 受邀请人
    public static final String CGKX_MYINTEGRAL = "CG0998"; // 我的积分
    public static final String CGKX_SIGNDAY = "CG0999"; // 我的积分
    public static final String CGKX_GETCOUPON = ""; //抢劵中心
    public static final String CGKX_INITGETCOUPON = "CG0104"; //初始化抢券界面
    public static final String CGKX_DEALGETCOUPON = "CG0105"; //抢券处理
    public static final String CGKX_DEALGETCOUPONACTIVITY = "CG0106"; //抢券活动处理
    public static final String CGKX_RISK_BEFORE_LOAN = "CG0800"; //投资之前的风险判断
    public static final String CGKX_LOANINFOFOLLOW ="CG0801"; //借款信息跟踪
}
