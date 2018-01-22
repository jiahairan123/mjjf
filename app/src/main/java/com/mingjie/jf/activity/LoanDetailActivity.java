package com.mingjie.jf.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mingjie.jf.R;
import com.mingjie.jf.bean.AccountInfo;
import com.mingjie.jf.bean.CalculateBean;
import com.mingjie.jf.bean.CallBankResponse;
import com.mingjie.jf.bean.CashBean;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ProductDetailResponse;
import com.mingjie.jf.bean.RiskBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.fragment.RiskInfoFragment;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CalculatorMonth;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.JsonUtils;
import com.mingjie.jf.utils.ProductStatusUtil;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.AccountMenuContentItemView;
import com.mingjie.jf.view.PoupupSelect2View;
import com.mingjie.jf.view.progressview.DonutProgressView;
import com.mingjie.jf.widgets.QuitDialog;
import com.necer.ndialog.NDialog;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 借款详情
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class LoanDetailActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener {

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;//返回
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;//标题
    @BindView(R.id.cb_loan_protocol)
    ImageView cbloanprotocol;//同意服务协议
    @BindView(R.id.ll_check)
    LinearLayout llCheck;//同意服务协议
    @BindView(R.id.iv_right_public)
    ImageView ivRightPublic;//计算器
    @BindView(R.id.loan_investHistory)
    AccountMenuContentItemView loanInvestHistory;//投标记录
    @BindView(R.id.loan_return_plan)
    AccountMenuContentItemView loanReturnPlan;//还款计划
    @BindView(R.id.loan_info)
    AccountMenuContentItemView loanInfo;//借款信息
    @BindView(R.id.recharge)
    Button loan_recharge;//充值
    @BindView(R.id.invest_type_iv)
    ImageView obj_type;//标的类型
    @BindView(R.id.invest_title_tv)
    TextView obj_title;//标的标题
    @BindView(R.id.progress_loanDetail)
    DonutProgressView invest_progress;//投资进度
    @BindView(R.id.tv_loan_money)
    TextView loan_money;//借款金额
    @BindView(R.id.tv_loan_rate)
    TextView loan_rate;//年化收益
    @BindView(R.id.tv_loan_limit)
    TextView loan_limit;//借款期限
    @BindView(R.id.tv_loan_surplus)
    TextView loan_surplus;//剩余可投
    @BindView(R.id.tv_loan_user)
    TextView loan_user;//借款人
    @BindView(R.id.tv_loan_method)
    TextView loan_method;//还款方式
    @BindView(R.id.tv_loaninvest_scope)
    TextView loaninvest_scope;//可投范围
    @BindView(R.id.tv_loan_surplusTime)
    TextView loan_surplusTime;//剩余时间
    @BindView(R.id.iv_loan_state)
    ImageView ivLoanState;//标的状态（印章）
    @BindView(R.id.tv_available_balance)
    TextView available_balance;//可用余额
    @BindView(R.id.et_loan_investMoney)
    EditText loan_investMoney;//投资金额
    @BindView(R.id.tv_loan_Income)
    TextView loan_income;//预计收益
    @BindView(R.id.btn_loan_buy)
    Button loan_buy;//立即购买

    @BindView(R.id.tv_loan_protocol)
    TextView loan_protocol;//服务协议

    @BindView(R.id.tv_jiekuan_protocol)
    TextView jiekuan_protocol;//借款协议

    @BindView(R.id.tv_zixunfuwu_protocol)
    TextView zixunfuwu_protocol;//咨询服务协议

    @BindView(R.id.tv_loan_tologin)
    TextView tvToLogin;//立即登录
    @BindView(R.id.tv_loan_error)
    TextView tvLoanError;//当前不可投
    @BindView(R.id.linear_buy)
    View linearBuy;//立即购买布局
    @BindView(R.id.linear_loan_nologin)
    View linearNoLogin;//未登陆布局
    @BindView(R.id.linear_loan_unInvest)
    View linearUnInvest;//不能投标布局
    @BindView(R.id.swipe_product_detail)
    SwipeRefreshLayout swipeDetail;//
    @BindView(R.id.tv_sever_fee)
    TextView tvServerFee;
    @BindView(R.id.rl_coupon)
    RelativeLayout rlCoupon;//优惠券
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;//优惠券
    @BindView(R.id.iv_counter_arrow1)
    ImageView ivCounterArrow;
    @BindView(R.id.et_loan_psd)
    EditText etLoanPsd;//投标密码
    @BindView(R.id.equit_record)
    AccountMenuContentItemView equit_record; //转让记录
    @BindView(R.id.safe_deal)
    AccountMenuContentItemView safe_deal; // 安全保障
    @BindView(R.id.loan_person_info_follow)
    AccountMenuContentItemView loan_info_follow;

    private String productId;
    private boolean isAgree = true;//是否同意服务协议
    private ProductDetailResponse productDetail;//标的详情

    private boolean isSucc = false;//标的详情是否加载成功，只有加载成功才能点击“投标记录”，“还款计划”，“借款信息”
    private double availableBal;//可用余额
    private String name;
    private Date date = new Date();

    private List<CashBean> cashs = new ArrayList<>();//现金券集合
    private CashBean cash;//选择的现金券
    private String investMoney;
    private String character_str = "";//判断所选的是加息券还是现金券
    private long beginTime;
    private String riskStr;


    //初始化控件
    protected void initView() {
        setContentView(R.layout.activity_loan_detail);
        productId = getIntent().getStringExtra("productId");
        //        productId = "d83fcecacb6b48bdae69c16980c49ad9";
        if (productId == null || productId.isEmpty())//进入该页面必须带有productId
        {
            UIUtils.showToast(this, "未携带productId");
            //            finish();
        }

        swipeDetail.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);
        swipeDetail.setOnRefreshListener(this);
        ivLeftPublic.setOnClickListener(this);
        ivRightPublic.setOnClickListener(this);
        tvContentPublic.setText("项目详情");
        //2016-9-7 将计算器图片替换为selector
        ivRightPublic.setImageResource(R.drawable.button_selector_calculator);
        loanInvestHistory.setOnClickListener(this);
        loanReturnPlan.setOnClickListener(this);
        loanInfo.setOnClickListener(this);
        //        cbloanprotocol.setOnClickListener(this);
        loan_recharge.setOnClickListener(this);
        linearNoLogin.setOnClickListener(this);
        loan_protocol.setOnClickListener(this);
        jiekuan_protocol.setOnClickListener(this);
        zixunfuwu_protocol.setOnClickListener(this);
        llCheck.setOnClickListener(this);
        rlCoupon.setOnClickListener(this);
        loan_buy.setOnClickListener(this);//立即充值
        equit_record.setOnClickListener(this);
        safe_deal.setOnClickListener(this);
        loan_info_follow.setOnClickListener(this);

        loan_investMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                investMoney = loan_investMoney.getText().toString().trim();
                if ("".equals(investMoney) || ".".equals(investMoney)) {
                    setTotalEarnings(0);
                    //                    loan_income.setText("预计净收益：" + 0.00 + "元");
                    return;
                }
                //保留2位小数点
                if (investMoney.contains(".")) {
                    if (investMoney.substring(investMoney.indexOf("."), investMoney.length()).length() > 3) {
                        loan_investMoney.setText(investMoney.substring(0, investMoney.indexOf(".") + 3));
                        loan_investMoney.setSelection(loan_investMoney.getText().length());
                    }
                }
                if (cash != null) {
                    setTotalEarnings2(calculateIncome(Double.parseDouble(investMoney), productDetail.getProfit()),
                            cash);
                } else {
                    setTotalEarnings(calculateIncome1(Double.parseDouble(investMoney), productDetail.getProfit()));
                }
                //                String income = calculateIncome(Double.parseDouble(investMoney));
                //                loan_income.setText("预计净收益：" + income + "元");
                if (Double.parseDouble(investMoney) > availableBal)//可用余额不足
                {
                    UIUtils.showToast(LoanDetailActivity.this, "可用余额不足！");
                }
                if (productDetail != null) {
                    if (productDetail.getMaxBidAmount() != 0 && Double.parseDouble(investMoney) > productDetail
                            .getMaxBidAmount())//可用余额不足
                    {
                        UIUtils.showToast(LoanDetailActivity.this, "已超出最大投资金额！");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setTotalEarnings(0);
    }

    //初始化数据
    protected void initData() {
        EventBus.getDefault().register(this);
        UIUtils.showLoading(this);
        getProductDetail();
    }

    @Override
    public void onRefresh() {
        getProductDetail();
    }

    /**
     * 获取标的详情
     */
    private void getProductDetail() {
        isSucc = false;
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(LoanDetailActivity.this, "当前网络不可用，请重试！");
            closeRefresh();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);

        MobclickAgent.onEvent(this, ServiceName.CGKX_PRODUCTDETAIL, params);

        HttpManager.getInstance().doPost(ServiceName.CGKX_PRODUCTDETAIL, params, new
                HttpCallBack<ServerResponse<ProductDetailResponse>>() {
                    @Override
                    public void getHttpCallNull(String str) {
                        closeRefresh();
                        UIUtils.showToast(LoanDetailActivity.this, R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<ProductDetailResponse> rsp) {
                        if (rsp == null) {
                            closeRefresh();
                            UIUtils.showToast(LoanDetailActivity.this, R.string.service_err);
                        } else if (!"000000".equals(rsp.getCode())) {
                            closeRefresh();
                            UIUtils.showToast(LoanDetailActivity.this, rsp.getMsg());
                        } else {
                            ProductDetailResponse pdetail = rsp.getData();
                            if (pdetail == null) {
                                closeRefresh();
                                UIUtils.showToast(LoanDetailActivity.this, R.string.service_err);
                            } else {
                                isSucc = true;
                                productDetail = pdetail;
                                showProductDetail();
                            }
                        }
                    }
                });
    }

    /**
     * 展示productDetail
     */
    private void showProductDetail() {


        //标的类型
        //        if (productDetail.getAssetType() == 3)
        //        {
        //            obj_type.setImageResource(R.mipmap.product_type_3);
        //        }
        //        else if (productDetail.getAssetType() == 4)
        //        {
        //            obj_type.setImageResource(R.mipmap.product_type_4);
        //        }
        //        else if (productDetail.getAssetType() == 5)
        //        {
        //            obj_type.setImageResource(R.mipmap.product_type_5);
        //        }
        //        else if (productDetail.getAssetType() == 6)
        //        {
        //            obj_type.setImageResource(R.mipmap.product_type_6);
        //        }
        //        else if (productDetail.getAssetType() == 7)
        //        {
        //            obj_type.setImageResource(R.mipmap.product_type_7);
        //        }
        //        else if (productDetail.getAssetType() == 8)
        //        {
        //            obj_type.setImageResource(R.mipmap.product_type_8);
        //        }
        //        else if (productDetail.getAssetType() == 9)
        //        {
        //            obj_type.setImageResource(R.mipmap.product_type_9);
        //        }
        obj_type.setImageLevel(productDetail.getAssetType());


        //获取当前时间的时间戳

        //将时间戳转化为时间的方法
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
//        String stringTime = sdf.format(date);
//        String beginText = sdf.format(beginTime);

        beginTime = Long.parseLong(productDetail.getBeginTime());

        if (beginTime > System.currentTimeMillis()) {
            loan_buy.setBackgroundColor(Color.GRAY);
            loan_buy.setText(sdf.format(beginTime) + "开始");

        } else if (productDetail.getInvestEndDate() <= System.currentTimeMillis()) {
            loan_buy.setText("已结束");
            loan_buy.setBackgroundColor(Color.GRAY);
        }




//        Toast.makeText(LoanDetailActivity.this, "" + stringTime + "开始", Toast.LENGTH_LONG).show();
//        Toast.makeText(LoanDetailActivity.this, "" + date.getTime() , Toast.LENGTH_LONG).show();

        //判断是否需要显示投标密码
        if (!TextUtils.isEmpty(productDetail.getPasswords())) {
            etLoanPsd.setVisibility(View.VISIBLE);
        }
        obj_title.setText(productDetail.getTitle());//标的标题
        if (productDetail.getBidAmount() == 0 || productDetail.getProductAmount() == 0)  // 避免除0异常
        {
            invest_progress.setProgress(0.00);
        } else {
            invest_progress.setProgress((productDetail.getBidAmount() / productDetail.getProductAmount() * 100));
        }
        //投资进度
        loan_money.setText(StringUtils.dou2Str(productDetail.getProductAmount()) + getString(R.string.yuan));//借款金额
        loan_rate.setText(productDetail.getProfit() + "%");//年化收益

        String unit = productDetail.getRepurchaseMode() == 3 ? "日" : "个月";

        loan_limit.setText(productDetail.getProductDeadline() + unit);//还款期限
        double serplus = productDetail.getProductAmount() - productDetail.getBidAmount();
        if (productDetail.getProductStatus() == 10 || productDetail.getProductStatus() == 11 || productDetail
                .getProductStatus() == 14 || productDetail.getProductStatus() == 20) {
            loan_surplus.setText(StringUtils.dou2Str(serplus) + getString(R.string.yuan));//可投金额
        } else if (productDetail.getProductStatus() != 10 && productDetail.getProductStatus() != 11 && productDetail
                .getProductStatus() != 14 && productDetail.getProductStatus() != 20) {
            if (serplus > 0) {
                loan_surplus.setText(StringUtils.dou2Str(serplus) + getString(R.string.yuan));//可投金额
            } else {
                loan_surplus.setText("0.00" + getString(R.string.yuan));//可投金额
            }
        }

        //                UIUtils.hintUserName(loan_user, productDetail.getBorrowName());
        if (productDetail.getBorrowNameList() != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < productDetail.getBorrowNameList().size(); i++) {
                sb.append(UIUtils.hintSingleUserName(productDetail.getBorrowNameList().get(i)) + ",");
            }
            String text = sb.toString();
            loan_user.setText(text.substring(0, text.length() - 1));
        }

        //        loan_user.setText(productDetail.getBorrowName());//借款人
        tvServerFee.setText(productDetail.getBidServiceFee() + "%");
        loan_method.setText(productDetail.getRepurchaseModeDesc());//还款方式
        loaninvest_scope.setText(StringUtils.dou2Str(productDetail.getMinBidAmount()) + "-" + StringUtils.dou2Str
                (productDetail.getMaxBidAmount()) + getString(R.string.yuan));//可投范围

        long surplusTime = productDetail.getInvestEndDate() - System.currentTimeMillis();
        //时间在当前时间之前，或者当前标的状态不可投
        if (productDetail.getProductStatus() == 2 || productDetail.getProductStatus() == 3) {
            loan_surplusTime.setText("待发布");//剩余时间
        } else if (surplusTime <= 0 || productDetail.getProductStatus() != 7) {
            loan_surplusTime.setText("已结束");//剩余时间
        } else {
            int day = (int) (surplusTime / 1000 / (60 * 60 * 24));
            int hour = (int) (surplusTime / 1000 % (60 * 60 * 24)) / (60 * 60);
            int minute = (int) ((surplusTime / 1000 % (60 * 60 * 24)) % (60 * 60)) / 60;
            loan_surplusTime.setText(day + "天" + hour + "时" + minute + "分");//剩余时间
        }

        ivLoanState.setImageResource(ProductStatusUtil.status2BigImg(productDetail.getProductStatus()));//设置印章

        if (12 == productDetail.getProductStatus()) {
            ivLoanState.setImageResource(ProductStatusUtil.status2BigImg(11));//设置印章
        }

        if (productDetail.getTransferSize() > 0) {
            equit_record.setVisibility(View.VISIBLE);
        } else {
            equit_record.setVisibility(View.GONE);
        }
        refreshLoginState();
    }

    /**
     * 刷新登录状态
     */
    private void refreshLoginState() {
        linearBuy.setVisibility(View.GONE);
        linearNoLogin.setVisibility(View.GONE);
        linearUnInvest.setVisibility(View.GONE);

        if (ProductStatusUtil.isCanInvest(productDetail.getProductStatus()))//如果当前标可投，需要判断登陆状态
        {
            if (CacheUtils.isLogin())//如果当前是登录状态，显示可直接投标布局
            {
                linearBuy.setVisibility(View.VISIBLE);
                getAvail();
            } else {
                closeRefresh();
                linearNoLogin.setVisibility(View.VISIBLE);//如果当前是未登录状态，显示提示登录布局
            }
        } else if (12 == productDetail.getProductStatus()) {
            closeRefresh();
            linearUnInvest.setVisibility(View.VISIBLE);//当前不可投
            tvLoanError.setText("对不起！该标当前状态为“已结清”不可进行投标");
        } else {
            closeRefresh();
            linearUnInvest.setVisibility(View.VISIBLE);//当前不可投
            tvLoanError.setText("对不起！该标当前状态为“" + ProductStatusUtil.status2String(productDetail.getProductStatus()) +
                    "”不可进行投标");
        }
    }

    /**
     * 获取账户可用余额
     */
    private void getAvail() {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            closeRefresh();
            return;
        }
        Map<String, String> params = new HashMap<>();
        UserVoValible userInfo = CacheUtils.getUserinfo(this);
        params.put("id", userInfo.getId());
        HttpManager.getInstance().doPost(ServiceName.CGKX_ACCOUNT, params, new
                HttpCallBack<ServerResponse<AccountInfo>>() {
                    @Override
                    public void getHttpCallNull(String str) {
                        UIUtils.showToast(LoanDetailActivity.this, R.string.dataError);
                        closeRefresh();
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<AccountInfo> rsp) {
                        if (rsp == null || !rsp.getCode().equals("000000")) {
                            closeRefresh();
                            UIUtils.showToast(LoanDetailActivity.this, R.string.service_err);
                        } else if (!"000000".equals(rsp.getCode())) {
                            closeRefresh();
                            UIUtils.showToast(LoanDetailActivity.this, rsp.getMsg());
                        } else {
                            AccountInfo rdetail = rsp.getData();
                            if (rdetail == null) {
                                closeRefresh();
                                UIUtils.showToast(LoanDetailActivity.this, R.string.service_err);
                            } else {
                                available_balance.setText("可用余额：" + rdetail.getAvailableBal() + "元");
                                availableBal = Double.parseDouble(rdetail.getAvailableBal().replace(",", "").replace
                                        ("，", ""));
                                getCashList();

                            }
                        }
                    }
                });
    }

    /**
     * 获取优惠券列表
     */
    private void getCashList() {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            closeRefresh();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        HttpManager.getInstance().doPost(ServiceName.GET_CASH_LIST, params, new
                HttpCallBack<ServerResponse<List<CashBean>>>() {
                    @Override
                    public void getHttpCallNull(String str) {
                        UIUtils.showToast(LoanDetailActivity.this, R.string.refreshFinsh);
                        closeRefresh();
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<List<CashBean>> rsp) {
                        closeRefresh();
                        UIUtils.showToast(LoanDetailActivity.this, R.string.refreshFinsh);
                        if (rsp == null || !rsp.getCode().equals("000000")) {
                            return;
                        }
                        cashs.clear();
                        cashs.addAll(rsp.getData());
                        if (cashs.size() <= 0)//无优惠券时，提示无可用优惠券，且按钮不可点击
                        {
                            tvCoupon.setText(getString(R.string.no_cash));
                            rlCoupon.setClickable(false);
                        } else {
                            tvCoupon.setText(getString(R.string.select_cash));
                            rlCoupon.setClickable(true);
                        }
                    }
                });

    }

    public void onEvent(Event event) {
        CfLog.d("登录成功-LoanDetailActivity");
        if (event.eventType == Event.LOGIN_SUCCESS) {
            refreshLoginState();
            UIUtils.showLoading(this);
        }
        if (event.eventType == Event.INVEST)//投资成功后刷新详情页面；
        {
            getProductDetail();
            //清空页面操作数据
            loan_investMoney.setText("");
            cbloanprotocol.setImageResource(R.mipmap.deal_select_true);
            isAgree = true;
            loan_income.setText("预计净收益：0.00元");
        }
        if (event.eventType == Event.LOGOUT) {
            refreshLoginState();
            UIUtils.showLoading(this);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.iv_right_public:
                if (productDetail != null) {
                    Intent intent = new Intent(this, IncomeCalculatorActivity.class);
                    intent.putExtra("term", String.valueOf(productDetail.getProductDeadline()));
                    intent.putExtra("bidServerFee", StringUtils.dou2Str(productDetail.getBidServiceFee()));
                    intent.putExtra("interestRate", StringUtils.dou2Str(productDetail.getProfit()));
                    intent.putExtra("repurchaseMode", productDetail.getRepurchaseMode());
                    startActivity(intent);
                } else {
                    startActivity(new Intent(this, IncomeCalculatorActivity.class));
                }
                break;

            case R.id.rl_coupon://选择优惠券
                selectCash();
                break;

            case R.id.loan_investHistory: //投标记录界面
                if (isSucc) {
                    Intent loan_Historyintent = new Intent(this, ThrowMarkHistoryActivity.class);
                    loan_Historyintent.putExtra("id", productId);
                    startActivity(loan_Historyintent);
                } else {
                    UIUtils.showToast(this, "页面未加载完成！");
                }
                break;
            case R.id.loan_return_plan: //还款计划界面
                if (isSucc) {
                    Intent loan_return_plan = new Intent(this, RefundPlanActivity.class);
                    loan_return_plan.putExtra("id", productId);
                    startActivity(loan_return_plan);
                } else {
                    UIUtils.showToast(this, "页面未加载完成！");
                }
                break;
            case R.id.loan_info://借款信息界面
                if (isSucc) {
                    Intent loan_info = new Intent(this, LoanInfoActivity.class);
                    loan_info.putExtra("id", productId);
                    startActivity(loan_info);
                } else {
                    UIUtils.showToast(this, "页面未加载完成！");
                }
                break;
            case R.id.ll_check://同意服务协议
                isAgree = !isAgree;
                cbloanprotocol.setImageResource(isAgree ? R.mipmap.deal_select_true : R.mipmap.deal_select);
                break;

            case R.id.tv_jiekuan_protocol :
                Intent intentjiekuan = new Intent(this, StaticProctolActivity.class);
                intentjiekuan.putExtra("title", "借款协议");
                startActivity(intentjiekuan);
                break;


            case R.id.tv_zixunfuwu_protocol :
                Intent intentzixun = new Intent(this, ZiXunFuWuActivity.class);
                intentzixun.putExtra("title", "咨询服务协议");
                startActivity(intentzixun);
                break;


            case R.id.tv_loan_protocol:
                Intent intent = new Intent(this, PrivateActivity.class);
                intent.putExtra("bannerTitle", "服务协议");
                intent.putExtra("data", UrlUtil.getPrivateUrl());//协议h5的网址
                startActivity(intent);
                break;



            case R.id.recharge:
                Intent charge = new Intent(this, RechargeActivity.class);
                charge.putExtra("recharge", "充值");
                startActivity(charge);
                break;

            case R.id.btn_loan_buy://立即投资
                //发标开始时间 与系统时间比较
                beginTime = Long.parseLong(productDetail.getBeginTime());

                if (beginTime > System.currentTimeMillis()) {
                    UIUtils.showToast(this, "未到发标时间！");

                } else if (productDetail.getInvestEndDate() <= System.currentTimeMillis()) {

                    UIUtils.showToast(this, "已结束！");
                } else {

                    if (isAgree) {
                        if (productDetail != null) {

//                        AlertDialog.Builder dialog = new AlertDialog.Builder(LoanDetailActivity.this);
//
//                        dialog.setTitle("风险告知书");
//
//                        dialog.setMessage("市场有风险，投资需谨慎\n");
//                        dialog.setCancelable(true);
//                        dialog.setPositiveButton("我已知晓", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                invest();
//                            }
//                        });
//                        dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                        dialog.show();


                            Map<String, String> param = new HashMap<>();
                            param.put("", "");
                            HttpManager.getInstance().doPost(ServiceName.CGKX_RISK_BEFORE_LOAN, param, new HttpCallBack<ServerResponse>() {


                                @Override
                                public void getHttpCallNull(String str) {

                                }

                                @Override
                                public void getHttpCallBack(ServerResponse rsp) {

                                    if (rsp == null) {
                                        ToastUtil.showToast(LoanDetailActivity.this, getResources().getString(R.string.dataError));
                                        return;
                                    }
                                    if (!"000000".equals(rsp.getCode())) {

                                        ToastUtil.showToast(LoanDetailActivity.this, rsp.getMsg());
                                    }
                                    if (rsp.getData() == null) {

                                        ToastUtil.showToast(LoanDetailActivity.this, getResources().getString(R.string.dataError));
                                        return;
                                    }


                                    if (rsp.getData() == "0" || rsp.getData().equals("0")){
                                        showRiskDialog();
                                    }
                                    else {
                                        showAnnounce();
                                    }

                                }
                            });


                            }
                        } else {
                            UIUtils.showToast(this, "未同意协议不可投标！");
                        }

                }
                break;

            case R.id.linear_loan_nologin://立即登录
                Intent loginIntent = new Intent(LoanDetailActivity.this, LoginActivity.class);
                loginIntent.putExtra(LoginActivity.KEY_START_TYPE, LoginActivity.START_TYPE_FINISH);
                startActivity(loginIntent);
                break;
            case R.id.equit_record:  //转让记录
                if (isSucc) {
                    Intent equ_record = new Intent(this, EquitableRecordActivity.class);
                    equ_record.putExtra("id", productId);
                    startActivity(equ_record);
                } else {
                    UIUtils.showToast(this, "页面未加载完成！");
                }
                break;
            case R.id.safe_deal: //安全保障
                startActivity(new Intent(this, SafeDealActivity.class));
                break;

            case R.id.loan_person_info_follow:
                Intent loan_info_follow_intnet = new Intent(this, LoanInfoFollowActivity.class);
                loan_info_follow_intnet.putExtra("id",productId);
                startActivity(loan_info_follow_intnet);
                break;
            default:
                break;
        }
    }

    private void showAnnounce() {
        new NDialog(LoanDetailActivity.this).setTitle("风险告知书").setTitleSize(18)
                .setTitleCenter(true).setTitleColor(Color.parseColor("#000000"))
                .setMessageCenter(false).setMessageColor(Color.parseColor("#000000"))
                .setMessageSize(16).setMessage("市场有风险，投资需谨慎\n" +
                "您需充分了解通过铭捷金服及铭捷金服网站进行交易的风险，您应对自己的资金状况、交易期限、收益预期、风险承受能力和风险偏好做出客观合理的评估后，再做出是否进行交易的决定，一旦您在铭捷金服网站进行交易，即视为您对在铭捷金服网站进行交易的风险有合理预期及承受能力，自行承担借贷产生的本息损失，铭捷金服作为服务平台，不承诺任何保本和最低收益，也不会就投资可能产生的亏损，承担任何赔偿责任。铭捷金服投资中存在的风险，揭示如下：\n" +
                "1.宏观经济风险因宏观经济形势变化，可能引起异常波动，您出借资金有可能遭受损失；\n" +
                "2.政策风险有关法律、法规及相关政策、规则发生变化，可能导致交易行为受限或违反有关规定，您有可能遭受损失；\n" +
                "3.违约风险因其他交易方无力或无意愿按时足额履约，您有可能遭受损失；\n" +
                "4.流动性风险因交易项下投资不能迅速转变为现金，或者不能应付可能出现的您大规模集中提现等而造成您损失；\n" +
                "5.技术风险因铭捷金服设备、系统故障或缺陷、病毒、黑客攻击、网络故障、网络中断、网络升级、网站提供商技术调整、致使铭捷金服不能按约履行本协议项下义务，可能导致会员损失；因互联网传输原因，会员交易指令出现中断、停顿、延迟、数据错误等情况而造成会员不能正常转账而引发会员损失；\n" +
                "6.不可抗力因素导致的风险不可抗力的情形包括但不限于：地震、台风、水灾、海啸、雷电、火灾、瘟疫、流行病、战争、恐怖主义、敌对行为、暴动、罢工等；\n" +
                "7.非因铭捷金服原因导致的任何损失包括但不限于：决策失误、操作不当、遗忘或泄露密码、银行卡被盗、非本人交易、密码被他人破解、会员使用的计算机系统被第三方侵入、会员委托他人代理交易时他人恶意或不当操作而造成的损失。\n" +
                "特别提示:本《风险告知书》无法揭示从事互联网金融交易的所有风险。故请您在交易之前，全面了解互联网金融交易法律法规及相关规定，审慎投资。若您对《风险告知书》内容存在疑问，请及时向铭捷金服服务人员反映，由平台负责解释。")
                .setNegativeTextColor(Color.parseColor("#c1c1c1"))
                .setPositiveTextColor(Color.parseColor("#000000"))
                .setButtonCenter(false)
                .setButtonSize(14)
                .setCancleable(true)
                .setOnConfirmListener(new NDialog.OnConfirmListener() {

                    @Override
                    public void onClick(int which) {
                        switch (which) {
                            case 1:
                                invest();
                                break;

                        }

                    }
                }).create(NDialog.CONFIRM).show();
    }

    private void showRiskDialog() {

        new NDialog(LoanDetailActivity.this).setTitle("温馨提示").setTitleSize(18)
                .setTitleCenter(true).setTitleColor(Color.parseColor("#000000"))
                .setMessageCenter(false).setMessageColor(Color.parseColor("#000000"))
                .setMessageSize(16).setMessage("您尚未进行风险承受能力评估，测评后才能投资!")
                .setNegativeTextColor((Color.parseColor("#c1c1c1")))
                .setPositiveTextColor(Color.parseColor("#000000"))
                .setButtonCenter(false)
                .setButtonSize(14)
                .setCancleable(true)
                .setOnConfirmListener(new NDialog.OnConfirmListener() {
                    @Override
                    public void onClick(int which) {
                        switch (which) {
                            case 1:
                                Intent intent = new Intent(LoanDetailActivity.this, PrivateActivity.class);
                                intent.putExtra("bannerTitle", "风险测评");
                                intent.putExtra("data", UrlUtil.RISK_BEFORE);
                                startActivity(intent);
                                break;


                            case 0:
                                return;

                                default:
                                    break;

                        }

                    }
                }).create(NDialog.CONFIRM).show();






    }

    /**
     * 选择优惠券
     */
    private void selectCash() {
        doAnimation(ivCounterArrow);
        PoupupSelect2View mPopupWindow = new PoupupSelect2View(this, cashs, ivCounterArrow);
        mPopupWindow.showPopupWindow();
        mPopupWindow.setOnListItemClickListener(new PoupupSelect2View.OnListItemListener() {
            @Override
            public void onListItemClick(AdapterView<?> parent, int position, long id) {
                cash = cashs.get(position);
                setInputCash(cash.getAmount(), cash.getCouponType());
                if (TextUtils.isEmpty(loan_investMoney.getText())) {
                    setTotalEarnings2(0, cash);
                } else {
                    setTotalEarnings2(calculateIncome(Double.parseDouble(loan_investMoney.getText().toString()),
                            productDetail.getProfit()), cash);
                }
            }
        });
        mPopupWindow.setOnDisMissListener(new PoupupSelect2View.OnDisMissListener() {
            @Override
            public void onDisMissListener() {
                RotateAnimation animation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(350);//设置动画持续时间
                animation.setFillAfter(true);
                ivCounterArrow.startAnimation(animation);
            }
        });
    }

    //现金券箭头旋转动画
    private void doAnimation(ImageView mIvarrow) {
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(350);//设置动画持续时间
        animation.setFillAfter(true);
        mIvarrow.startAnimation(animation);
    }

    /**
     * 立即投资
     */
    private void invest() {
        if (loan_investMoney.getText() == null || loan_investMoney.getText().toString().trim().equals("")) {
            ToastUtil.showToast(this, "请输入投资金额！");
            return;
        }
        //超出可用余额
        if (Double.parseDouble(loan_investMoney.getText().toString()) > availableBal) {
            ToastUtil.showToast(this, "账户可用余额不足!");
            return;
        }
        //投资低于最低可投范围
        if (Double.parseDouble(loan_investMoney.getText().toString()) < productDetail.getMinBidAmount()) {
            ToastUtil.showToast(this, "该标的最低投资：" + productDetail.getMinBidAmount() + "元");
            return;
        }
        //投资金额高于最高可投范围
        if (Double.parseDouble(loan_investMoney.getText().toString()) > productDetail.getMaxBidAmount()) {
            ToastUtil.showToast(this, "该标的最高投资：" + productDetail.getMaxBidAmount() + "元");
            return;
        }
        //投资金额高于剩余可投金额
        if (Double.parseDouble(loan_investMoney.getText().toString()) > (productDetail.getProductAmount() -
                productDetail.getBidAmount())) {
            ToastUtil.showToast(this, "该标的剩余可投：" + StringUtils.dou2Str(productDetail.getProductAmount() -
                    productDetail.getBidAmount()) + "元");
            return;
        }

        if ((!TextUtils.isEmpty(productDetail.getPasswords())) && TextUtils.isEmpty(etLoanPsd.getText().toString()
                .trim())) {
            ToastUtil.showToast(this, "请输入投标密码！");
            return;
        }
        //所投金额小于现金券最小投资金额
        if (cash != null && cash.getMinInvestmentAmount() > Double.parseDouble(loan_investMoney.getText().toString())) {
            ToastUtil.showToast(this, String.format(getString(R.string.err_min_amount), cash.getMinInvestmentAmount()));
            return;
        }

        //如果有优惠券 且用户未选择
        if (cashs.size() > 0 && cash == null) {
            final QuitDialog dialog = new QuitDialog(this, "确认投资", "您有可用优惠券，确认不使用直接投资？", "取消", "确认");
            dialog.show();
            dialog.setEnterListener(new QuitDialog.OnEnterListener() {
                @Override
                public void onEnterListener() {
                    dialog.dismiss();
                    investToNet();

                }
            });
            dialog.setCancelListener(new QuitDialog.OnCancelListener() {
                @Override
                public void onCancelListener() {
                    dialog.dismiss();
                }
            });
        } else {
            investToNet();
        }

    }

    /**
     * 发起投资请求
     */
    private void investToNet() {
        UIUtils.showLoading(this);
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(LoanDetailActivity.this, "当前网络不可用，请重试！");
            UIUtils.dimissLoading();
            closeRefresh();
            return;
        }
        Map<String, String> params = new HashMap<>(2);
        params.put("productId", productId);
        params.put("amount", loan_investMoney.getText().toString());
        params.put("returnUrl", Constants.BANK_RETURN_URL);
        params.put("bidSource", Integer.toString(4));
        params.put("from", "android");
        params.put("productType", productDetail.getAssetType() + "");
        if (character_str.contains("加息券")) {
            params.put("couponType", 4 + "");
        } else {
            params.put("couponType", 1 + "");
        }
        params.put("passwords", etLoanPsd.getText().toString().trim());//输入的投标密码
        //传入代金券ID，投标回调处使用
        //couponId修改为reserve1  日期：2016-12-29
        if (cash != null) {
            params.put("reserve1", cash.getId());
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("productId", productId);
        map.put("name", CacheUtils.getUserinfo(this).getName());
        map.put("HHmm", TimeFormatUtil.getSimpleTime());
        map.put("HH", TimeFormatUtil.getHour() + "H");
        map.put("amount", "" + Double.valueOf(loan_investMoney.getText().toString()).intValue());
        MobclickAgent.onEvent(this, ServiceName.SCATTERED_BID, map);

        HttpManager.getInstance().doPost(ServiceName.SCATTERED_BID, params, new
                HttpCallBack<ServerResponse<CallBankResponse>>() {
                    @Override
                    public void getHttpCallNull(String str) {
                        UIUtils.dimissLoading();
                        UIUtils.showToast(LoanDetailActivity.this, R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<CallBankResponse> rsp) {
                        UIUtils.dimissLoading();
                        if (rsp == null) {
                            UIUtils.showToast(LoanDetailActivity.this, R.string.service_err);
                        } else if (!"000000".equals(rsp.getCode())) {
                            UIUtils.showToast(LoanDetailActivity.this, rsp.getMsg());
                        } else {
                            CallBankResponse callBank = rsp.getData();
                            if (null == callBank) {
                                UIUtils.showToast(LoanDetailActivity.this, R.string.service_err);
                                return;
                            }
                            Intent intent = new Intent(LoanDetailActivity.this, ToBankActivity.class);
                            intent.putExtra("transUrl", callBank.getHxHostUrl());
                            intent.putExtra("transCode", callBank.getTransCode());
                            intent.putExtra("requestData", callBank.getRequestData());
                            intent.putExtra("channelFlow", callBank.getChannelFlow());
                            startActivity(intent);
                        }
                    }
                });
    }

    //如果swipeDetail正在刷新，则关闭刷新
    private void closeRefresh() {
        if (swipeDetail.isRefreshing()) {
            swipeDetail.setRefreshing(false);
        }
        UIUtils.dimissLoading();
    }

    /**
     * 计算收益
     *
     * @param tempAmount
     * @return
     */
    private double calculateIncome(Double tempAmount, Double profit) {
        if (productDetail == null || profit <= 0) {
            return 0;
        }
        // String tempIncomeStr = "";
        double totalInterest = 0;
        List<CalculateBean> calculaResult = null;
        Double tempRate = profit / 100;
        BigDecimal rate = new BigDecimal(tempRate.toString());
        BigDecimal amount = new BigDecimal(tempAmount);
        //        BigDecimal rate = new BigDecimal(profit / 100);
        switch (productDetail.getRepurchaseMode()) {
            case 0:
                break;
            case 1:
                calculaResult = CalculatorMonth.getInterestAndCapitalPerMonth1(amount, rate, productDetail
                        .getProductDeadline());
                break;
            case 2:
                calculaResult = CalculatorMonth.getInterestAndCapitalPerMonth2(amount, rate, productDetail
                        .getProductDeadline());
                break;
            case 3:
                calculaResult = CalculatorMonth.getInterestAndCapitalPerDay(amount, rate, productDetail
                        .getProductDeadline());
                break;
            default:
                break;
        }

        if (calculaResult != null) {
            for (CalculateBean bean : calculaResult) {
                double temp = Double.parseDouble(bean.mLixi);
                totalInterest += temp;
            }

            // 算手续费
            //             double pourboire = totalInterest * productDetail.getBidServiceFee() / 100;//根据产品服务费率
            //             double tempPourboire = totalInterest - pourboire;//净赚收益
            //            // tempIncomeStr = StringUtils.dou2Str(tempPourboire);
        }
        return totalInterest;
    }

    /**
     * 计算收益
     *
     * @param tempAmount
     * @return
     */
    private double calculateIncome1(Double tempAmount, Double profit) {
        if (productDetail == null || profit <= 0) {
            return 0;
        }
        // String tempIncomeStr = "";
        double totalInterest = 0;
        List<CalculateBean> calculaResult = null;
        Double tempRate = profit / 100;
        BigDecimal rate = new BigDecimal(tempRate.toString());
        BigDecimal amount = new BigDecimal(tempAmount);
        //        BigDecimal rate = new BigDecimal(profit / 100);
        switch (productDetail.getRepurchaseMode()) {
            case 0:
                break;
            case 1:
                calculaResult = CalculatorMonth.getInterestAndCapitalPerMonth1(amount, rate, productDetail
                        .getProductDeadline());
                break;
            case 2:
                calculaResult = CalculatorMonth.getInterestAndCapitalPerMonth2(amount, rate, productDetail
                        .getProductDeadline());
                break;
            case 3:
                calculaResult = CalculatorMonth.getInterestAndCapitalPerDay(amount, rate, productDetail
                        .getProductDeadline());
                break;
            default:
                break;
        }

        if (calculaResult != null) {
            for (CalculateBean bean : calculaResult) {
                double temp = Double.parseDouble(bean.mLixi);
                totalInterest += temp;
            }

            totalInterest = totalInterest * (1 - productDetail.getBidServiceFee() / 100);
            // 算手续费
            //             double pourboire = totalInterest * productDetail.getBidServiceFee() / 100;//根据产品服务费率
            //             double tempPourboire = totalInterest - pourboire;//净赚收益
            //            // tempIncomeStr = StringUtils.dou2Str(tempPourboire);
        }
        return totalInterest;
    }

    /**
     * 设置选择的优惠券颜色
     */
    private void setInputCash(double cashAmount, int type) {
        String amount = StringUtils.dou2Str(cashAmount);
        if (4 == type)//加息券
        {
            character_str = String.format(getString(R.string.use_proportion), amount);
        } else//现金券
        {
            character_str = String.format(getString(R.string.use_coupon), amount);
        }
        SpannableString styledText = new SpannableString(character_str);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style3), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style4), 5, character_str.length() - 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style3), character_str.length() - 1,
                character_str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCoupon.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 设置总收益颜色
     */
    private void setTotalEarnings(double earning) {
        String amount = StringUtils.dou2Str(earning);
        //设置金额字体
        String str = String.format(getString(R.string.total_earnings), amount);
        SpannableString styledText = new SpannableString(str);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style5), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style6), 4, str.length() - 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style5), str.length() - 1, str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loan_income.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 设置总收益颜色（带现金券）
     */
    private void setTotalEarnings2(double earning, CashBean cashbean) {
        //如果现金券金额小于等于0，则按规则1设置
        if (cashbean.getAmount() <= 0) {
            setTotalEarnings(earning);
            return;
        }
        String totlEarnings;
        double feelv = earning * (productDetail.getBidServiceFee() / 100);//服务费率
        String interestStr = StringUtils.dou2Str(earning - feelv);//利息
        String cashStr;
        String str;
        if (4 == cashbean.getCouponType())//加息券
        {
            double totlEarnings2 = investMoney == null ? 0 : calculateIncome(Double.parseDouble(investMoney),
                    cashbean.getAmount() + productDetail.getProfit());
            CfLog.i("---" + cashbean.getAmount());
            CfLog.i("---" + productDetail.getProfit());
            totlEarnings = StringUtils.dou2Str(totlEarnings2 - feelv);//总收益
            cashStr = StringUtils.dou2Str(totlEarnings2 - earning);//加息券收益
            str = String.format(getString(R.string.total_earnings3), totlEarnings, interestStr, cashStr);
        } else//现金券
        {
            totlEarnings = StringUtils.dou2Str(earning + cashbean.getAmount());
            cashStr = StringUtils.dou2Str(cashbean.getAmount());
            str = String.format(getString(R.string.total_earnings2), totlEarnings, interestStr, cashStr);
        }

        SpannableString styledText = new SpannableString(str);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style5), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style6), 4, 4 + totlEarnings.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style5), 4 + totlEarnings.length(),
                4 + totlEarnings.length() + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style6), 4 + totlEarnings.length() + 4,
                4 + totlEarnings.length() + 4 + interestStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style5),
                4 + totlEarnings.length() + 4 + interestStr.length(),
                4 + totlEarnings.length() + 4 + interestStr.length() + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style6),
                4 + totlEarnings.length() + 4 + interestStr.length() + 5,
                4 + totlEarnings.length() + 4 + interestStr.length() + 5 + cashStr.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.text_style5),
                4 + totlEarnings.length() + 4 + interestStr.length() + 5 + +cashStr.length(),
                4 + totlEarnings.length() + 4 + interestStr.length() + 5 + cashStr.length() + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loan_income.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
    }

}
