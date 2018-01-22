package com.mingjie.jf.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.AccountEquRecActivity;
import com.mingjie.jf.activity.AccountSetActivity;
import com.mingjie.jf.activity.AutoInvestActivity;
import com.mingjie.jf.activity.CaseCardActivity;
import com.mingjie.jf.activity.CaseCardListActivity;
import com.mingjie.jf.activity.CaseManagerActivity;
import com.mingjie.jf.activity.CaseWithdrawRecordActivity;
import com.mingjie.jf.activity.CashCouponActivity;
import com.mingjie.jf.activity.ChargeCouponActivity;
import com.mingjie.jf.activity.DepositActivity;
import com.mingjie.jf.activity.ElectronicBillActivity;
import com.mingjie.jf.activity.EquitableActivity;
import com.mingjie.jf.activity.ExclusiveQrCodeActivity;
import com.mingjie.jf.activity.GetCouponActivity;
import com.mingjie.jf.activity.IntegralMallActivity;
import com.mingjie.jf.activity.InvitedManActivity;
import com.mingjie.jf.activity.TiChengListActivity;
import com.mingjie.jf.activity.InvestHandActivity;
import com.mingjie.jf.activity.LoginActivity;
import com.mingjie.jf.activity.MainActivity;
import com.mingjie.jf.activity.ManagerMoneyActivity;
import com.mingjie.jf.activity.MoreActivity;
import com.mingjie.jf.activity.MyBorrowActivity;
import com.mingjie.jf.activity.MyIntegralActivity;
import com.mingjie.jf.activity.MyInvestActivity;
import com.mingjie.jf.activity.OldVersionActivity;
import com.mingjie.jf.activity.PaternerCommisionActivity;
import com.mingjie.jf.activity.PrivateActivity;
import com.mingjie.jf.activity.RechargeActivity;
import com.mingjie.jf.activity.RechargeManagerMoneyActivity;
import com.mingjie.jf.activity.RepayCalendarActivity;
import com.mingjie.jf.activity.ReturnCalendarActivity;
import com.mingjie.jf.activity.VenturePartnersActivity;
import com.mingjie.jf.bean.AccountInfo;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.SignBean;
import com.mingjie.jf.bean.UserInfo;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.PropertyUtil;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.AccountMenuContentItemView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.mingjie.jf.activity.CashCouponActivity.TYPE_CASH;
import static com.mingjie.jf.activity.CashCouponActivity.TYPE_GAIN_COUPON;
import static com.mingjie.jf.activity.CashCouponActivity.TYPE_RED_PACKET;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 账户fragment
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AccountFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private final int TYPE_INVEST = 0;//投资
    private final int TYPE_CASE_DOUGH = 1;//私房钱
    private final int TYPE_RECOMMEND_AWRARD = 2;//推荐奖励
    private final int TYPE_BORROW = 3;//我的借款
    private final int TYPE_EQU = 4;//债权转让
    private final int TYPE_COUPON = 5;//我的优惠券
    private final int TYPE_INTEGRAL = 6;//我的积分
    private final int TYPE_INTRODUCERGAIN = 7;
    //    private final int TYPE_GETCOUPON = 8;
    private PopupWindow myPopupWindow;

    @BindView(R.id.tv_right_public2)
    TextView returnOldVersion; //返回旧版

    @BindView(R.id.pop_background)
    LinearLayout background; //背景

    View view;
    @BindView(R.id.account_user_pic)
    CircleImageView accountUserPic;//用户头像

    @BindView(R.id.recharge)
    Button recharge;//充值
    @BindView(R.id.revest)
    Button revest;//投资

    @BindView(R.id.relativerevest)
    RelativeLayout relativerevest;//我的投资
    @BindView(R.id.rl_coupon)
    RelativeLayout rlCoupon;//我的优惠券
    @BindView(R.id.relative_introduce)
    RelativeLayout relativeintroducer; //提成管理

    @BindView(R.id.ll_introduce)
    LinearLayout linearintroducer; //子布局总共
    @BindView(R.id.ll_get_coupon)
    LinearLayout linearGetCoupon;
//    @BindView(R.id.get_coupon_arrow)
//    ImageView getCouponIv;

    @BindView(R.id.introduce_arrow_right)
    ImageView introduceRightArrow;

    @BindView(R.id.ll_coupon)
    LinearLayout ll_coupon;   // 优惠券隐藏布局
    @BindView(R.id.userinfo_iv_coupon)
    ImageView userinfo_iv_coupon;  //我的优惠券旋转按钮
    @BindView(R.id.av_cash_coupon)
    AccountMenuContentItemView av_cash_coupon; //现金券
    @BindView(R.id.av_gain_coupon)
    AccountMenuContentItemView av_gain_coupon; //加息券
    @BindView(R.id.av_red_packet)
    AccountMenuContentItemView av_red_packet; //红包

    @BindView(R.id.relativeattom)
    RelativeLayout relativeattom;//债权转让（开关按钮）
    @BindView(R.id.ll_equ)
    LinearLayout ll_equ;   // 债权转让隐藏布局
    @BindView(R.id.userinfo_iv_equiteable_assignment_arrow)
    ImageView userinfo_iv_equiteable_assignment_arrow;  //债权转让旋转按钮
    @BindView(R.id.av_equ_but)
    AccountMenuContentItemView av_equ_but; //债权转让
    @BindView(R.id.av_equrec_but)
    AccountMenuContentItemView av_equrec_but; //转让记录

    @BindView(R.id.rl_integral)
    RelativeLayout relativeIntegral;//我的积分大标题
    @BindView(R.id.ll_integral)
    LinearLayout ll_integral;//子标题的总布局
    @BindView(R.id.av_my_integral)
    AccountMenuContentItemView av_integral_btn;//我的积分
    @BindView(R.id.av_my_integral_mall)
    AccountMenuContentItemView av_integral_mall_btn;//积分商城
    @BindView(R.id.iv_integral_arrow)
    ImageView iv_integral_arrows; //总标题右侧的箭头

    @BindView(R.id.rl_sign_get_integral)
    RelativeLayout relativesigngetintegtral;
    @BindView(R.id.sign_tv_msg)
    TextView sign_tv_msg_textview;


    @BindView(R.id.rl_get_coupon)   //兑换中心
    RelativeLayout relativeLayoutgetCoupon;
    @BindView(R.id.rl_charge_getcoupon)   //兑换中心
    RelativeLayout relativerl_charge_getcoupon;

    @BindView(R.id.relativerecharge)
    RelativeLayout relativerecharge;//充值管理
    @BindView(R.id.relativewithdraw)
    RelativeLayout relativewithdraw;//提现管理
    @BindView(R.id.relativebill)
    RelativeLayout relativebill;//电子账单
    @BindView(R.id.relativeaccount)
    RelativeLayout relativeaccount;//账户设置
    @BindView(R.id.swipe_account_fragment)
    SwipeRefreshLayout mRefreshView;

    @BindView(R.id.tv_content_public)
    TextView title_center;
    @BindView(R.id.iv_Left_public)
    ImageView back;
    @BindView(R.id.relative_nologin)
    RelativeLayout Nologin;
    @BindView(R.id.relative_login)
    RelativeLayout login;
    @BindView(R.id.account_info)//个人账户详情
            LinearLayout accountInfo;
    @BindView(R.id.tv_account_nologin)//点击登录
            TextView tvClickLogin;

    @BindView(R.id.open_deposit)
    Button openDeposit;//开通存管账户
    @BindView(R.id.tv_account_username)
    TextView tvAccountUsername;
    @BindView(R.id.rl_autoinvest)
    RelativeLayout rlAutoinvest;//自动投资
    @BindView(R.id.rl_recommend_award)
    RelativeLayout rlRecommendAward;//推荐奖励
    @BindView(R.id.account_my_recommendation_child_layout)
    LinearLayout accountMyRecommendationChildLayout;//推荐奖励下的子项
    @BindView(R.id.iv_recommend_award_arrow)
    ImageView ivRecommendAwardArrow;//
    @BindView(R.id.account_business_partner)
    AccountMenuContentItemView accountBusinessPartner;//创业小伙伴
    @BindView(R.id.account_business_partner_deduct)
    AccountMenuContentItemView accountBusinessPartnerDeduct;//创业小伙伴提成
    @BindView(R.id.account_exclusive_qr_code)
    AccountMenuContentItemView accountExclusiveQrCode;//我的推荐
    @BindView(R.id.rl_my_borrow)
    RelativeLayout rl_my_borrow;    // 我的借款
    @BindView(R.id.iv_my_borrow_arrows)
    ImageView iv_my_borrow_arrows;    // 我的借款
    @BindView(R.id.ll_my_borrow)
    LinearLayout ll_my_borrow;    // 我的借款
    @BindView(R.id.av_my_borrow)
    AccountMenuContentItemView av_repay_calendar;    // 我的借款
    @BindView(R.id.av_repay_calendar)
    AccountMenuContentItemView av_return_calendar;    // 还款日历
    @BindView(R.id.invest_hand)
    AccountMenuContentItemView invest_hand; //投资处理中
    @BindView(R.id.invest_finish)
    AccountMenuContentItemView invest_finish; //成功投资
    @BindView(R.id.return_calendar)
    AccountMenuContentItemView return_calendar; //回款日历
    @BindView(R.id.ll_invest)
    LinearLayout ll_invest;                  // 投资 折叠的布局
    @BindView(R.id.userinfo_iv_invest_arrow1)
    ImageView userinfo_iv_invest_arrow1;        // 投资旋转箭头
    @BindView(R.id.iv_person)
    ImageView ivPerson;
    @BindView(R.id.iv_emaile)
    ImageView ivEmaile;
    @BindView(R.id.phone)
    ImageView phone;
    @BindView(R.id.iv_account_email)
    ImageView ivAccountEmail;
    @BindView(R.id.relativeamore)
    RelativeLayout relativeamore;
    @BindView(R.id.rl_case_dough)
    RelativeLayout rlCaseDough;
    @BindView(R.id.av_case_manager)
    AccountMenuContentItemView avCaseManager;
    @BindView(R.id.av_case_card)
    AccountMenuContentItemView avCaseCard;
    @BindView(R.id.av_case_withdraw)
    AccountMenuContentItemView avCaseWithdraw;
    @BindView(R.id.ll_casedough)
    LinearLayout llCasedough;//私房钱折叠部分
    @BindView(R.id.userinfo_iv_case_dough)
    ImageView userinfoIvCaseDough;
    @BindView(R.id.bt_openv3)
    Button btOpenv3;//进入v3系统
    @BindView(R.id.risk_test)
    AccountMenuContentItemView risk_test; // 风险测评

//    @BindView(R.id.av_get_coupon)
//    AccountMenuContentItemView getCouponAv;
//    @BindView(R.id.av_charge_coupon)
//    AccountMenuContentItemView chargeCouponAv;

    @BindView(R.id.introducer_list)
    AccountMenuContentItemView introducerList;
    @BindView(R.id.introducer_gain_history)
    AccountMenuContentItemView gainHistory;

    private boolean isOpenRecom = false;
    private boolean isOpenInvest = false;
    private boolean isOpenCase = false;
    private boolean isOpenBorrow = false;
    private boolean isOpenEqu = false;
    private boolean isOpenCoupon = false;
    private boolean isOpenIntegral = false;
    private boolean isOpenIntroducer = false;
//    private boolean isOpenGetCoupon = false;

    private boolean isShowRecom = false;
    private boolean isShowInvest = false;
    private boolean isShowCase = false;
    private boolean isShowBorrow = false;
    private boolean isShowEqu = false;
    private boolean isShowCoupon = false;
    private boolean isShowIntegral = false;
    private boolean isShowIntroduce = false;
//    private boolean isShowgetCoupon = false;

    @BindView(R.id.user_money)
    TextView userMoney;
    @BindView(R.id.user_available)
    TextView userAvailable;
    @BindView(R.id.user_back_money)
    TextView userBackMoney;
    @BindView(R.id.user_total)
    TextView userTotal;
    @BindView(R.id.tv_sign_day)
    TextView tvSignday;
    @BindView(R.id.user_iceprice)
    TextView userIcePrice;
    @BindView(R.id.user_reaygain_money)
    TextView userRedayGain;

    private UserVoValible user;

    private Context mContext; // FragmentActivity
    private AccountInfo rdetail;
    private UserInfo account;
    private Intent getCouponIntent;
    private String states = "false";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mContext = getContext();

        user = CacheUtils.getUserinfo(mContext);

        if (view == null) {
            CfLog.d("view==null");
            view = inflater.inflate(R.layout.fragment_account, container, false);
            ButterKnife.bind(this, view);
            initView();
            initData();
            // 直接修改高度
            ViewGroup.LayoutParams invest = ll_invest.getLayoutParams();
            invest.height = 0;
            ll_invest.setLayoutParams(invest);

            // 直接修改高度
            ViewGroup.LayoutParams recom = accountMyRecommendationChildLayout.getLayoutParams();
            recom.height = 0;
            accountMyRecommendationChildLayout.setLayoutParams(recom);

            // 直接修改高度
            ViewGroup.LayoutParams casedough = llCasedough.getLayoutParams();
            casedough.height = 0;
            llCasedough.setLayoutParams(casedough);

            // 直接修改高度
            ViewGroup.LayoutParams borrow = ll_my_borrow.getLayoutParams();
            borrow.height = 0;
            ll_my_borrow.setLayoutParams(borrow);

            // 直接修改高度
            ViewGroup.LayoutParams equ = ll_equ.getLayoutParams();
            equ.height = 0;
            ll_equ.setLayoutParams(equ);

//            // 优惠券
            ViewGroup.LayoutParams coupon = ll_coupon.getLayoutParams();
            coupon.height = 0;
            ll_coupon.setLayoutParams(coupon);

            //我的积分
            ViewGroup.LayoutParams integral = ll_integral.getLayoutParams();
            integral.height = 0;
            ll_integral.setLayoutParams(integral);

            //邀请人
            ViewGroup.LayoutParams introducer = linearintroducer.getLayoutParams();
            introducer.height = 0;
            linearintroducer.setLayoutParams(introducer);

            //获取优惠券
//            ViewGroup.LayoutParams getCoupon = linearGetCoupon.getLayoutParams();
//            getCoupon.height = 0;
//            linearGetCoupon.setLayoutParams(getCoupon);


        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (null != parent) {
            parent.removeView(view);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(getActivity());
    }

    private void initData() {
        //注册eventbus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (mRefreshView.isRefreshing()) {
            mRefreshView.setRefreshing(false);
        }
        refreshLoginState();
        mRefreshView.post(new Runnable() {
            @Override
            public void run() {
                mRefreshView.setRefreshing(true);
                getResult();
            }
        });

    }

    /**
     * 刷新登录状态
     */
    private void refreshLoginState() {
        if (CacheUtils.isLogin()) {
            Nologin.setVisibility(View.GONE);
            accountInfo.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
        } else {
            Nologin.setVisibility(View.VISIBLE);
            accountInfo.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
        }
    }

    public void onEvent(Event event) {
        //这里发出的Event都通知这里，刷新用户信息
        refreshLoginState();

        isShowRecom = false;
        isShowInvest = false;
        isShowCase = false;
        isShowBorrow = false;
        isShowEqu = false;
        isShowCoupon = false;
        isShowIntroduce = false;
//        isShowgetCoupon = false;

        isOpenRecom = false;
        isOpenInvest = false;
        isOpenCase = false;
        isOpenBorrow = false;
        isOpenEqu = false;
        isOpenCoupon = false;
        isOpenIntroducer = false;
//        isOpenGetCoupon = false;


        if (!isOpenInvest) {
            ObjectAnimator.ofFloat(userinfo_iv_invest_arrow1, "rotation", 90, 0).start();
            // 直接修改高度
            ViewGroup.LayoutParams invest = ll_invest.getLayoutParams();
            invest.height = 0;
            ll_invest.setLayoutParams(invest);
        }
        if (!isOpenRecom) {
            ObjectAnimator.ofFloat(ivRecommendAwardArrow, "rotation", 90, 0).start();
            // 直接修改高度
            ViewGroup.LayoutParams recom = accountMyRecommendationChildLayout.getLayoutParams();
            recom.height = 0;
            accountMyRecommendationChildLayout.setLayoutParams(recom);
        }
        if (!isOpenCase) {
            ObjectAnimator.ofFloat(userinfoIvCaseDough, "rotation", 90, 0).start();
            // 直接修改高度
            ViewGroup.LayoutParams recom = llCasedough.getLayoutParams();
            recom.height = 0;
            llCasedough.setLayoutParams(recom);
        }
        if (!isOpenEqu) {
            ObjectAnimator.ofFloat(userinfo_iv_equiteable_assignment_arrow, "rotation", 90, 0).start();
            // 直接修改高度
            ViewGroup.LayoutParams invest = ll_equ.getLayoutParams();
            invest.height = 0;
            ll_equ.setLayoutParams(invest);
        }
        if (!isOpenCoupon) {
            ObjectAnimator.ofFloat(userinfo_iv_coupon, "rotation", 90, 0).start();
            // 直接修改高度
            ViewGroup.LayoutParams invest = ll_coupon.getLayoutParams();
            invest.height = 0;
            ll_coupon.setLayoutParams(invest);
        }
        if (!isOpenIntegral) {
            ObjectAnimator.ofFloat(iv_integral_arrows, "rotation", 90, 0).start();
            ViewGroup.LayoutParams integral = ll_integral.getLayoutParams();
            integral.height = 0;
            ll_integral.setLayoutParams(integral);
        }
        if (!isOpenIntroducer) {
            ObjectAnimator.ofFloat(introduceRightArrow, "rotation", 90, 0).start();
            ViewGroup.LayoutParams introducer = linearintroducer.getLayoutParams();
            introducer.height = 0;
            linearintroducer.setLayoutParams(introducer);
        }
//        if (!isOpenGetCoupon) {
//            ObjectAnimator.ofFloat(getCouponIv, "rotation", 90, 0).start();
//            ViewGroup.LayoutParams getCouponll = linearGetCoupon.getLayoutParams();
//            getCouponll.height = 0;
//            linearGetCoupon.setLayoutParams(getCouponll);
//        }

        if (CacheUtils.isLogin()) {
            getResult();
        }
    }

    //初始化数据
    private void initView() {
//        btOpenv3.setVisibility(View.VISIBLE);
//        getCouponAv.setOnClickListener(this);
//        chargeCouponAv.setOnClickListener(this);
        relativerl_charge_getcoupon.setOnClickListener(this);
        relativeLayoutgetCoupon.setOnClickListener(this);
        relativesigngetintegtral.setOnClickListener(this);
        accountUserPic.setOnClickListener(this);
        returnOldVersion.setVisibility(View.VISIBLE);
        returnOldVersion.setOnClickListener(this);
        returnOldVersion.setText("返回旧版");
        recharge.setOnClickListener(this);//充值
        revest.setOnClickListener(this);//投资
        relativerevest.setOnClickListener(this);//我的投资
        rlCoupon.setOnClickListener(this);//我的投资
        relativeattom.setOnClickListener(this);//债权转让
        relativerecharge.setOnClickListener(this);//充值管理
        relativewithdraw.setOnClickListener(this);//提现管理
        relativebill.setOnClickListener(this);//电子账单
        relativeaccount.setOnClickListener(this);//账户设置
        tvClickLogin.setOnClickListener(this);//登录
        rlCaseDough.setOnClickListener(this);//私房钱
        avCaseManager.setOnClickListener(this);//私房钱管理
        avCaseCard.setOnClickListener(this);//银行卡
        avCaseWithdraw.setOnClickListener(this);//提现

        relativeintroducer.setOnClickListener(this);
        introducerList.setOnClickListener(this);
        gainHistory.setOnClickListener(this);


        title_center.setText("我的账户");
        back.setVisibility(View.INVISIBLE);
        openDeposit.setOnClickListener(this);
        rlAutoinvest.setOnClickListener(this);
        rlRecommendAward.setOnClickListener(this);
        accountBusinessPartner.setOnClickListener(this);
        accountBusinessPartnerDeduct.setOnClickListener(this);
        accountExclusiveQrCode.setOnClickListener(this);
        rl_my_borrow.setOnClickListener(this);
        av_repay_calendar.setOnClickListener(this);
        av_return_calendar.setOnClickListener(this);

        invest_hand.setOnClickListener(this);
        invest_finish.setOnClickListener(this);
        return_calendar.setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);
        relativeamore.setOnClickListener(this);

        relativeIntegral.setOnClickListener(this);//我的积分大标题
        av_integral_btn.setOnClickListener(this);//我的积分
        av_integral_mall_btn.setOnClickListener(this);//积分商城
//        av_equ_but.setOnClickListener(this); //债权转让
        av_equrec_but.setOnClickListener(this); //转让记录
        av_cash_coupon.setOnClickListener(this); //现金券
        av_gain_coupon.setOnClickListener(this); //加息券
        av_red_packet.setOnClickListener(this); //红包
        btOpenv3.setOnClickListener(this);
        risk_test.setOnClickListener(this); //风险测评
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.rl_charge_getcoupon:
                Intent chargeCouponIntent = new Intent(getContext(), ChargeCouponActivity.class);
                chargeCouponIntent.putExtra("phone", user.getPhone());
                chargeCouponIntent.putExtra("userId", user.getId());
                startActivity(chargeCouponIntent);
                break;


            //签到获取积分
            case R.id.rl_sign_get_integral:
                if (checkIsLoginOrCanNet()) {

                    Map<String, String> params = new HashMap<>();
                    params.put("userId", user.getId());
                    HttpManager.getInstance().doPost(ServiceName.CGKX_SIGNDAY, params, new HttpCallBack<ServerResponse<SignBean>>() {
                        @Override
                        public void getHttpCallNull(String str) {
                            ToastUtil.showCenterGraToast(str);
                        }

                        @Override
                        public void getHttpCallBack(ServerResponse<SignBean> rsp) {
                            String msg = rsp.getData().getResultMsg();
                            ToastUtil.showCenterGraToast(msg);
                        }
                    });
                }

                break;

            case R.id.tv_right_public2:
                //返回老版本
                Intent oldVersionIntent = new Intent(mContext, OldVersionActivity.class);
                startActivity(oldVersionIntent);
                break;

            case R.id.bt_openv3:
                // 通过包名获取要跳转的app，创建intent对象
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(PropertyUtil.getString
                        (getActivity(), "oldPackageName", null));
                if (intent != null) {
                    intent.putExtra("isLogin", CacheUtils.isLogin());
                    intent.putExtra("userName", CacheUtils.getUserinfo(mContext).getName());
                    intent.putExtra("pwd", CacheUtils.getString(Constants.PASSWORD, ""));
                    intent.putExtra("fromV4", true);
                    //传
                    startActivity(intent);
                } else {
                    // UIUtils.showLoading(getActivity());
                    // 调v3的登录接口，成功的话进入view/account/fund/my_account.html（账户界面），失败的话跳转user/weblogout.html
                    Intent weiintent = new Intent(mContext, PrivateActivity.class);
                    weiintent.putExtra("data", UrlUtil.WEIXIN_LOGIN);
                    weiintent.putExtra("bannerTitle", getContext().getResources().getString(R.string.denglu));
                    startActivity(weiintent);
                }
                break;
            case R.id.recharge:
                if (checkIsLoginOrCanNet()) {
                    //跳转充值界面
                    Intent register = new Intent(getContext(), RechargeActivity.class);
                    register.putExtra("recharge", "充值");
                    startActivity(register);
                }
                break;
            case R.id.relativeattom:  //债权
                if (checkIsLoginOrCanNet()) {
                    if (!isShowEqu) {
                        toggle(isOpenEqu, ll_equ, userinfo_iv_equiteable_assignment_arrow, TYPE_EQU);
                    }
                }
                break;
            case R.id.av_equ_but:  // 债权转让
                if (checkIsLoginOrCanNet()) {
                    //跳转债权转让
                    Intent relativeattom = new Intent(getContext(), EquitableActivity.class);
                    startActivity(relativeattom);
                }
                break;
            case R.id.av_equrec_but: // 转让记录
                if (checkIsLoginOrCanNet()) {
                    //跳转转让记录
                    Intent equRec = new Intent(getContext(), AccountEquRecActivity.class);
                    startActivity(equRec);
                }
                break;
            case R.id.av_cash_coupon: // 现金券
                if (checkIsLoginOrCanNet()) {
                    //跳转现金券
                    Intent equRec = new Intent(getContext(), CashCouponActivity.class);
                    equRec.putExtra("type", TYPE_CASH);
                    startActivity(equRec);
                }
                break;

            case R.id.av_gain_coupon: // 加息券
                if (checkIsLoginOrCanNet()) {
                    //跳转加息券
                    Intent equRec = new Intent(getContext(), CashCouponActivity.class);
                    equRec.putExtra("type", TYPE_GAIN_COUPON);
                    startActivity(equRec);
                }
                break;
            case R.id.av_red_packet: // 红包
                if (checkIsLoginOrCanNet()) {
                    //跳转红包
                    Intent equRec = new Intent(getContext(), CashCouponActivity.class);
                    equRec.putExtra("type", TYPE_RED_PACKET);
                    startActivity(equRec);
                }
                break;
            case R.id.relativewithdraw:
                if (checkIsLoginOrCanNet()) {
                    //跳转提现记录界面
                    Intent relativewithdraw = new Intent(getContext(), ManagerMoneyActivity.class);
                    startActivity(relativewithdraw);
                }
                break;
            case R.id.relativebill:
                if (checkIsLoginOrCanNet()) {
                    //跳转电子账单
                    Intent relativebill = new Intent(getContext(), ElectronicBillActivity.class);
                    startActivity(relativebill);
                }
                //  }
                break;
            case R.id.relativeaccount:
                if (checkIsLoginOrCanNet()) {
                    //跳转账户设置
                    Intent relativeaccount = new Intent(getContext(), AccountSetActivity.class);
                    startActivity(relativeaccount);
                }
                break;

            case R.id.rl_get_coupon:
//                if (!isShowgetCoupon){
//                    toggle(isOpenGetCoupon, linearGetCoupon, getCouponIv, TYPE_GETCOUPON);
//                }
                Intent getCouponIntent = new Intent(getContext(), GetCouponActivity.class);
                getCouponIntent.putExtra("phone", user.getPhone());
                startActivity(getCouponIntent);
                break;



            case R.id.relative_introduce:
                //提成管理
                if (checkIsLoginOrCanNet()) {
                    if (!isShowIntroduce) {
                        toggle(isOpenIntroducer, linearintroducer, introduceRightArrow, TYPE_INTRODUCERGAIN);
                    }
                }
                break;


            //跳转我的投资
            case R.id.relativerevest:
                if (checkIsLoginOrCanNet()) {
                    if (!isShowInvest) {
                        toggle(isOpenInvest, ll_invest, userinfo_iv_invest_arrow1, TYPE_INVEST);
                    }
                }
                break;
            //跳转我的优惠券
            case R.id.rl_coupon:
                if (checkIsLoginOrCanNet()) {
                    if (!isShowCoupon) {
                        toggle(isOpenCoupon, ll_coupon, userinfo_iv_coupon, TYPE_COUPON);
                    }
                }
                break;


            //新加的我的积分
            case R.id.rl_integral:
                if (checkIsLoginOrCanNet()) {
                    if (!isShowIntegral) {
                        toggle(isOpenIntegral, ll_integral, iv_integral_arrows, TYPE_INTEGRAL);
                    }
                }
                break;

            //提成
            case R.id.introducer_list:
                if (checkIsLoginOrCanNet()) {
                    Intent introducedIntent = new Intent(getContext(), TiChengListActivity.class);
                    introducedIntent.putExtra("uid", user.getId());
                    startActivity(introducedIntent);
                }
                break;

            //受邀请人
            case R.id.introducer_gain_history:

                if (checkIsLoginOrCanNet()) {
                    Intent gainListIntent = new Intent(getContext(), InvitedManActivity.class);
                    gainListIntent.putExtra("uid", user.getId());
                    startActivity(gainListIntent);
                }
                break;


            //我的积分 enter
            case R.id.av_my_integral:
                if (checkIsLoginOrCanNet()) {

                    Intent integralIntent = new Intent(getContext(), MyIntegralActivity.class);
                    integralIntent.putExtra("uid", user.getId());
                    startActivity(integralIntent);
                }
                break;
            // 我的积分商城 enter
            case R.id.av_my_integral_mall:
                if (checkIsLoginOrCanNet()) {
                    Intent turnIntent = new Intent(getContext(), IntegralMallActivity.class);
                    turnIntent.putExtra("uid", user.getId());
                    startActivity(turnIntent);
                }
                break;


            //跳转私房钱
            case R.id.rl_case_dough:
                if (checkIsLoginOrCanNet()) {
                    if (!isShowCase) {
                        toggle(isOpenCase, llCasedough, userinfoIvCaseDough, TYPE_CASE_DOUGH);
                    }
                }
                break;
            case R.id.av_case_manager: // 私房钱管理
                if (null != account && checkIsLoginOrCanNet()) {
                    Intent hand = new Intent(getContext(), CaseManagerActivity.class);
                    hand.putExtra("isbind", account.getIsBindBankcard());
                    hand.putExtra("isFundDepository", account.getUserStates().getIsFundDepository());
                    hand.putExtra("phonenumbertixian", account.getUser().getPhone());
                    CfLog.i("---phonenumbertixian" + account.getUser().getPhone());
                    getContext().startActivity(hand); // getContext 解决可能出现的空指针异常
                }
                break;
            case R.id.av_case_card: // 银行卡
                if (null != account && checkIsLoginOrCanNet()) {
                    if ("0".equals(account.getIsBindBankcard()))//0是未绑卡
                    {
                        Intent hand = new Intent(getContext(), CaseCardActivity.class);
                        hand.putExtra("accounttel_phone", account.getUser().getPhone());
                        hand.putExtra("name", account.getUser().getRealname());
                        CfLog.i("---" + account.getUser().getPhone());
                        startActivity(hand);
                        return;
                    } else if ("1".equals(account.getIsBindBankcard())) {
                        Intent hand = new Intent(getContext(), CaseCardListActivity.class);
                        hand.putExtra("name", account.getUser().getRealname());
                        startActivity(new Intent(getContext(), CaseCardListActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), DepositActivity.class));
                    }
                }
                break;
            case R.id.av_case_withdraw: // 提现记录
                if (checkIsLoginOrCanNet()) {
                    Intent hand = new Intent(getContext(), CaseWithdrawRecordActivity.class);
                    startActivity(hand);
                }
                break;
            case R.id.invest_hand: // 投资记录
                if (checkIsLoginOrCanNet()) {
                    Intent hand = new Intent(getContext(), InvestHandActivity.class);
                    startActivity(hand);
                }
                break;
            case R.id.invest_finish: // 我的投资
                if (checkIsLoginOrCanNet()) {
                    Intent myaccount = new Intent(getContext(), MyInvestActivity.class);
                    startActivity(myaccount);
                }
                break;
            case R.id.return_calendar: // 回款日历
                if (checkIsLoginOrCanNet()) {
                    Intent myaccount = new Intent(getContext(), ReturnCalendarActivity.class);
                    startActivity(myaccount);
                }
                break;

            case R.id.risk_test:  // 风险测评
                if (checkIsLoginOrCanNet()) {
                    Intent risk = new Intent(getContext(), PrivateActivity.class);
                    risk.putExtra("bannerTitle", "风险测评");
                    risk.putExtra("data", UrlUtil.getRiskTestUrl());
                    startActivity(risk);
                }
                break;
            case R.id.revest:
                //跳转投资
                MainActivity.setCurrentTab(1);
                break;

            //充值管理
            case R.id.relativerecharge:
                if (checkIsLoginOrCanNet()) {
                    Intent managermoney = new Intent(getContext(), RechargeManagerMoneyActivity.class);
                    startActivity(managermoney);
                }
                break;
            //点击登录
            case R.id.tv_account_nologin:
                Intent login = new Intent(getContext(), LoginActivity.class);
                startActivity(login);
                break;
            //开通存管
            case R.id.open_deposit:
                if (checkIsLoginOrCanNet()) {
                    Intent opendeposit = new Intent(getContext(), DepositActivity.class);
                    startActivity(opendeposit);
                }
                break;
            //自动投资
            case R.id.rl_autoinvest:
                Intent autoinvest = new Intent(getContext(), AutoInvestActivity.class);
                startActivity(autoinvest);
                break;
            //推荐奖励
            case R.id.rl_recommend_award:
                if (checkIsLoginOrCanNet()) {
                    if (!isShowRecom) {
                        toggle(isOpenRecom, accountMyRecommendationChildLayout, ivRecommendAwardArrow,
                                TYPE_RECOMMEND_AWRARD);
                    }
                }
                break;
            case R.id.account_business_partner://创业小伙伴
                if (checkIsLoginOrCanNet()) {
                    if (null == account) {
                        UIUtils.showToast("请先刷新界面");
                        return;
                    }
                    if (null != account.getUser()) {
                        Intent partner = new Intent(getContext(), VenturePartnersActivity.class);
                        partner.putExtra("realname", account.getUser().getRealname());
                        startActivity(partner);
                    }
                }
                break;
            case R.id.account_business_partner_deduct://创业小伙伴提成
                if (checkIsLoginOrCanNet()) {
                    startActivity(new Intent(getContext(), PaternerCommisionActivity.class));
                }
                break;
            case R.id.account_exclusive_qr_code://我的推荐
                if (checkIsLoginOrCanNet()) {
                    Intent exclusiveQrCode = new Intent(getContext(), ExclusiveQrCodeActivity.class);
                    exclusiveQrCode.putExtra("phone", account.getUser().getPhone());
                    startActivity(exclusiveQrCode);
                }
                break;
            case R.id.rl_my_borrow:    // 我的借款
                if (checkIsLoginOrCanNet()) {
                    if (!isShowBorrow) {
                        toggle(isOpenBorrow, ll_my_borrow, iv_my_borrow_arrows, TYPE_BORROW);
                    }
                }
                break;

            case R.id.av_my_borrow:    // 我的借款
                if (checkIsLoginOrCanNet()) {
                    Intent myborrow = new Intent(getContext(), MyBorrowActivity.class);
                    startActivity(myborrow);
                }
                break;

            case R.id.av_repay_calendar:    // 还款日历
                if (checkIsLoginOrCanNet()) {
                    startActivity(new Intent(mContext, RepayCalendarActivity.class));
                }
                break;

            case R.id.relativeamore:    // 更多
                if (checkIsLoginOrCanNet()) {
                    Intent mymore = new Intent(getContext(), MoreActivity.class);
                    startActivity(mymore);
                }
                break;

            default:
                CfLog.i("default被执行");
                break;
        }
    }

    /**
     * 账户信息相关操作需要先检查是否已经登录且网络正常
     *
     * @return
     */
    private boolean checkIsLoginOrCanNet() {
        if (!Utilities.canNetworkUseful(mContext))//如果当前网络不可用
        {
            UIUtils.showToast("当前网络不可用，请重试！");
            return false;
        }
        if (!CacheUtils.isLogin()) {
            UIUtils.showToast(mContext, "未登录，请先登录！");
            Intent loginActivity = new Intent(mContext, LoginActivity.class);
            loginActivity.putExtra(LoginActivity.KEY_START_TYPE, LoginActivity.START_TYPE_FINISH);
            startActivity(loginActivity);
        }
        return CacheUtils.isLogin();
    }

    /**
     * 我的投资 折叠动画
     */
    private void toggle(boolean isOpen, LinearLayout ll, View view, int type) {
        if (isOpen) {// 折叠
            //应有的高度-->0
            ll.measure(0, 0);
            doAnimation(ll.getMeasuredHeight(), 0, ll);
        } else {// 展开
            //             0-->应有的高度
            ll.measure(0, 0);
            doAnimation(0, ll.getMeasuredHeight(), ll);
        }
        // 箭头的旋转动画
        if (isOpen) {
            ObjectAnimator.ofFloat(view, "rotation", 90, 0).start();
        } else {
            ObjectAnimator.ofFloat(view, "rotation", 0, 90).start();
        }
        switch (type) {
            case TYPE_INVEST://投资
                isOpenInvest = !isOpen;
                break;
            case TYPE_CASE_DOUGH://私房钱
                isOpenCase = !isOpen;
                break;
            case TYPE_RECOMMEND_AWRARD://推荐奖励
                isOpenRecom = !isOpen;
                break;
            case TYPE_BORROW://我的借款
                isOpenBorrow = !isOpen;
                break;
            case TYPE_EQU: //债权转让
                isOpenEqu = !isOpen;
                break;
            case TYPE_COUPON: //我的优惠券
                isOpenCoupon = !isOpen;
                break;
            case TYPE_INTEGRAL: //我的积分
                isOpenIntegral = !isOpen;
                break;
            case TYPE_INTRODUCERGAIN:
                isOpenIntroducer = !isOpen;
                break;
//            case TYPE_GETCOUPON:
//                isOpenGetCoupon = !isOpen;
//                break;
            default:
                break;
        }

    }

    public void doAnimation(int start, int end, final LinearLayout mLayout) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.start();// 开始动画
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator value) {

                int height = (Integer) value.getAnimatedValue();
                // 通过layoutParams,修改高度
                ViewGroup.LayoutParams params = mLayout.getLayoutParams();
                params.height = height;
                mLayout.setLayoutParams(params);
            }
        });
    }

    //监听刷新
    @Override
    public void onRefresh() {
        getResult();
    }

    private void getResultAccount() {
        if (!Utilities.canNetworkUseful(getContext()))//如果当前网络不可用
        {
            UIUtils.showToast(getContext(), "当前网络不可用，请重试！");
            closeRefresh();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", user.getId());
        HttpManager.getInstance().doPost(ServiceName.CGKX_ACCOUNT, params, new
                HttpCallBack<ServerResponse<AccountInfo>>() {
                    @Override
                    public void getHttpCallNull(String str) {
                        UIUtils.showToast(getContext(), R.string.dataError);
                        closeRefresh();
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<AccountInfo> rsp) {
                        closeRefresh();
                        if (rsp == null || rsp.getCode() == null || rsp.getData() == null) {
                            UIUtils.showToast(getContext(), R.string.service_err);
                            return;
                        }

                        if (!"000000".equals(rsp.getCode())) {
                            UIUtils.showToast(getContext(), rsp.getMsg());
                            return;
                        }

                        rdetail = rsp.getData();
                        updataUserMoneyUI(rdetail);
                        UIUtils.showToast(getContext(), "刷新完成");

                    }
                });
    }

    //更新用户的余额
    private void updataUserMoneyUI(AccountInfo rdetail) {
        this.rdetail = rdetail;

        /**
         * availableBal;//可用余额   totalReceipt;//待回款金额 frozBl;
         * totalInterest;//累计收益金额    totalAmount;//个人总资产
         * acctBal;   String interestPending; //待汇款
         */

        /**
         * true 不可签到  false 可以签到
         */
        states = rdetail.getStates();
        if ("false".equals(states)){
            //按钮夜色正常
        } else {
            //按钮颜色变灰
            sign_tv_msg_textview.setText("今日您已签到");
        }

        String userMoneyStr = rdetail.getTotalAmount() == null ? "0.00" : rdetail.getTotalAmount();
        userMoney.setText(userMoneyStr);    //个人总资产
        String userAvailableStr = rdetail.getAvailableBal() == null ? "0.00" : rdetail.getAvailableBal();
        userAvailable.setText(userAvailableStr); //可用余额
        String userBackMoneyStr = rdetail.getTotalReceipt() == null ? "0.00" : rdetail.getTotalReceipt();
        userBackMoney.setText(userBackMoneyStr); // 待回款金额
        String userTotalStr = rdetail.getTotalInterest() == null ? "0.00" : rdetail.getTotalInterest();
        userTotal.setText(userTotalStr);  // 累计收益金额

        String userRedayGainStr = rdetail.getInterestPending() == null ? "0.00" : rdetail.getInterestPending();
        userRedayGain.setText(userRedayGainStr); // interestPending; //待回收收益

        String userIcePriceStr = rdetail.getFrozBl() == null ? "0.00" : rdetail.getFrozBl();
        userIcePrice.setText(userIcePriceStr);  //冻结资金
    }

    private void closeRefresh() {
        if (mRefreshView == null) {
            return;
        }
        if (mRefreshView.isRefreshing()) {
            mRefreshView.setRefreshing(false);
        }
    }

    private void getResult() {
        if (!CacheUtils.isLogin()) {//未登录，不发起网络请求
            closeRefresh();
            return;
        }
        user = CacheUtils.getUserinfo(mContext);
        if (!Utilities.canNetworkUseful(getContext()))//如果当前网络不可用
        {
            UIUtils.showToast(getContext(), "当前网络不可用，请重试！");
            closeRefresh();
            return;
        }
        mRefreshView.setRefreshing(true);
        Map<String, String> params = new HashMap<>();
        params.put("id", user.getId());
        HttpManager.getInstance().doPost(ServiceName.CGKX_USERINFO, params, new HttpCallBack<ServerResponse<UserInfo>>() {
            @Override
            public void getHttpCallNull(String str) {
                UIUtils.showToast(getContext(), R.string.dataError);
                closeRefresh();
                //                        getResultAccount();
            }

            @Override
            public void getHttpCallBack(ServerResponse<UserInfo> rsp) {
                // getResultAccount();
                if (rsp == null || null == rsp.getCode()) {
                    closeRefresh();
                    UIUtils.showToast(getContext(), R.string.service_err);
                    return;
                } else if (!rsp.getCode().equals("000000")) {
                    closeRefresh();
                    UIUtils.showToast(getContext(), rsp.getMsg());
                    return;
                }

                if (null == rsp.getData()) {
                    closeRefresh();
                    UIUtils.showToast(getContext(), R.string.service_err);
                }

                getResultAccount();
                account = rsp.getData();
                CfLog.i("---" + account);
                if (account == null) {
                    UIUtils.showToast(getContext(), R.string.service_err);
                } else {
                    showUserBean(account);

                    //保存是否开通存管账户
                    SharedPreferences.Editor sharedata = getContext().getSharedPreferences("delposite", 0).edit();
                    sharedata.putInt("del", account.getUserStates().getIsFundDepository());
                    sharedata.commit();

                }

            }
        });
    }

    //设置用户的相关信息
    private void showUserBean(UserInfo account) {
        this.account = account;
        if (null != account.getUser()) {
            //刷新账户成功之后，将电话号码和真实姓名保存
            SharedPreferences.Editor sharedata = getContext().getSharedPreferences("teldata", 0).edit();
            sharedata.putString("tel", account.getUser().getPhone());
            sharedata.putString("realname", account.getUser().getRealname());
            sharedata.commit();

            //1是 员工    0不是员工
//            LoginAction action = new LoginAction(getActivity());
//            if ("1".equals(action.information)){
//                relativeintroducer.setVisibility(View.GONE);
//            }

            if ("1".equals(account.getUser().getWealthManagerFlg())) {
                relativeintroducer.setVisibility(View.VISIBLE);
            } else {
                relativeintroducer.setVisibility(View.GONE);
            }

            if (null == account.getUser().getPhone()) {
                phone.setImageResource(R.mipmap.phone_white);
            } else {
                phone.setImageResource(R.mipmap.phone_black);
            }
            if (null == account.getUser().getEmail()) {
                ivEmaile.setImageResource(R.mipmap.email_white);
            } else {
                ivEmaile.setImageResource(R.mipmap.emaile_black);
            }
            if (null != account.getUser().getName()) {
                tvAccountUsername.setText(account.getUser().getName());
            } else {
                tvAccountUsername.setText("你好");
            }
        }
        if (null != account.getUserStates()) {
            if (1 == account.getUserStates().getIsFundDepository()) {
                openDeposit.setVisibility(View.GONE);
                recharge.setVisibility(View.VISIBLE);
                revest.setVisibility(View.VISIBLE);
                ivPerson.setImageResource(R.mipmap.person_info_black);
            } else {
                openDeposit.setVisibility(View.VISIBLE);
                recharge.setVisibility(View.GONE);
                revest.setVisibility(View.GONE);
                ivPerson.setImageResource(R.mipmap.person_info_white);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void showPopWindow(View view) {


        if (background.getVisibility() == View.GONE) {
            background.setVisibility(View.VISIBLE);
        }
        myPopupWindow = new PopupWindow(getActivity());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.head_img_check, null);
        myPopupWindow.setContentView(v);
        myPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        myPopupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        myPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        myPopupWindow.setOutsideTouchable(true);
        myPopupWindow.setFocusable(false);
        myPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        initPopWindow(v);
        myPopupWindow.update();
        myPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                background.setVisibility(View.GONE);

            }
        });
    }

    private void initPopWindow(View v) {
        LinearLayout takePhoto = (LinearLayout) v.findViewById(R.id.auth_photo);
        LinearLayout checkPhoto = (LinearLayout) v.findViewById(R.id.check_photo);
        LinearLayout exitPhoto = (LinearLayout) v.findViewById(R.id.exit_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPopupWindow.dismiss();
                Toast.makeText(getContext(), "click take photo btn", Toast.LENGTH_SHORT).show();

//                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(new File(Environment
//                                .getExternalStorageDirectory().getAbsolutePath()
//                                + "/user/image/", "avatarImage.jpg")));
//                takeIntent.addCategory(Intent.CATEGORY_DEFAULT);
            }
        });

        checkPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPopupWindow.dismiss();
                Toast.makeText(getContext(), "click check photo btn", Toast.LENGTH_SHORT).show();
            }
        });
        exitPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPopupWindow.dismiss();

            }
        });

    }


}
