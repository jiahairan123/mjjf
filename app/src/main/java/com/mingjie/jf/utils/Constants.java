package com.mingjie.jf.utils;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 常量
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 */
public interface Constants {

    //    短信debug开关false为关闭短信debug模式，true为开启短信debug模式
//    boolean SMS_DEBUG = true;//开启debug模式
    boolean SMS_DEBUG = false;//关闭debug模式

//    String KEY_ACNO="key_acno";//存管账号 key
    //    int类型
    int MOBEILS_NET = 1;//移动网络
    int WIFI_NET = 2;//wifi网络
    int NO_NET = 3;//没有网络
    int MAX_RATE = 24;//最大年利率
    int MAX_MONTH = 60;//最大年投资期限，60个月
    int MAX_DAY = 720;//最大天投资期限，720天
    int FREEZE_TIME = 60000;//获取短信验证码冻结时间
    int COUNTDOWN = 1000;//倒计时频率，默认当前为1秒
    int STEP_ONE = 1;//忘记密码第一步
    int STEP_TWO = 2;//忘记密码第二步

    //    String类型
    String SEE_PASSWORD = "see_password";
    String CHARSET_UTF8 = "UTF-8";//UTF-8编码
    String KEY_FIRST_START = "isFirstIntoApp";//是否第一次进入app
    String LOCK = "figure_lock"; //    图形锁设置
    String BANNER = "banner";//banner
    String IS_LOGIN = "islogin";//是否登陆
    String USER_NAME = "username";//用户名
    String REAL_NAME = "realname";//真实姓名
    String PHONE_NUM = "phoneNum";//手机号码
    String IS_COOPERATION_FRIEND = "isCooperationFriend"; //是否是创业小伙伴
    String PASSWORD = "password";//密码
    String TOKEN = "token";//用户token
    String AVATARURL = "avatarUrl";//头像url
    String USER_POINT = "userPoint";//积分
    String REALNAME_STATE = "realNameStatus";//实名认证状态
    String HAS_SignIn = "hasSignedIn";//是否签到
    String BIND_PHONE = "bindPhoneStatus";//是否认证手机
    String BIND_EAMIL = "bindMailboxStatus";//是否绑定邮箱
    String BIND_CARD = "bankCardStatus";//是否绑定银行卡
    String WAIT_MONEY = "waitingAmount";//待收总额
    String NEW_MSG_COUNT = "newMsgCount";//新消息条数
    String INVEST_MONEY = "investAmount";//投资总额
    String HAS_NEWMSG = "hasNewMsg";//是否有新消息
    String CLEAN_MONEY = "earnedAmount";//净收总额
    String REALNAME = "realname";//真实姓名
    String AVAILABLEAMOUNT = "availableAmount";//可用余额
    String INTEGRAL = "integral"; //用户积分
    String FROZENAMOUNT = "frozenAmount"; //冻结金额
    String NAME = "name"; //冻结金额
    String IS_FORBITBID = "isForbitBid"; //是否禁止投标
    String IS_FORBITWITHDRAW = "isForbitWithdraw"; //是否禁止提现
    String SERVICE_LINE = "serviceLine";//客服电话

    String COOKIE = "Cookie";//cookie

    //登录后的状态值
    String ISFUNDDEPOSITY="isFundDepository";//是否开通存管
    String ISFORBITBID="isForbitBid";//是否可以投标
    String ISFORBITWITHDRAW="isForbitWithdraw";//是否可以提现
    String ISFORBITRECEIPTTRANFER="isForbitReceiptTransfer";//是否可以债权转让

    //    long类型
    long MAX_MONEY = 8000000;//收益计算器，最大计算金额


    //    Intent Flags标记
    int NOTICE_FLAG = 2001;//系统公告的flag
    int HELP_CHILD_FLAG = 2002;//系统公告的flag
    int LOAN_FLAG = 2003;//借款详情
    int DEB_FLAG = 2004;//债权详情
    int ACCOUNT_DEB = 2005;//账户里的投资债权
    int ACCOUNT_BIAODI = 2006;//账户里的投资标的
    int BINDCARD = 2007;//绑定银行卡
    int NOBINDCARD = 2009;//没有银行卡
    int SHOW_CARDLIST = 2010;//已绑定银行卡，显示列表
    int TO_REALNAME = 2012;//去实名
    int OK_REALNAME = 2013;//实名认证ok
    int REFUNDPLAN = 2014;//还款计划
    int BUYBACKPLAN = 2015;//回购计划
    int PIC_PASS = 2016;//手势密码

    //    Intent Extra
    String CONTENTID = "contentId";//帮助详情，tabID
    String LOANDETAILID = "loandetailID";//借款详情 ID
    String ASSETTYPE = "assetType";//
    String MONTH = "month";//

    /**
     * 平安银行账户号
     */
    String PINGAN_ACCOUNT = "11014815006008";

    String REFERRER_CODE = "referrerCode";
    String REFERRER_LINK = "referrerLink";
    String WECHAT_LINK = "wechatRlink";

    String IDNUMBER = "idnumber";//身份证
    String  EMAILE="email";//邮箱

    /**
     * 提交到银行的returnUrl
     */
//    String BANK_RETURN_URL = "https://demo.ming-hao.cn";
    String BANK_RETURN_URL = UrlUtil.BASE_URL;
    //    public final static String BANK_RETURN_URL = "file:///android_asset/to_exit.html";

    String APP_NAME = "cgkx";

    /**
     * 首页数据 (标以外的数据)
     */
    String HOME_DATA = "homedata";
    /**
     * 首页数据 (标的数据)
     */
    String HOME_BIAO_DATA = "homebiaodata";

    /**
     * 手势密码缓存key后部分
     */
    String GESTURE_PWD = "gesture_pwd";

    /**
     * 对应用户是否打开手势密码(后部分)
     */
    String IS_OPEN_GESTURE = "is_open_gesture";
}