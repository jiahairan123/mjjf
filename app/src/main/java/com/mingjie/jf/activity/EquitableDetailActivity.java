package com.mingjie.jf.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.AccountInfo;
import com.mingjie.jf.bean.CallBankResponse;
import com.mingjie.jf.bean.EquitableDatailBean;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.ProductStatusUtil;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.AccountMenuContentItemView;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 债权详情页
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-15 21:02
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class EquitableDetailActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener
{
    @BindView(R.id.swipe_product_detail)
    SwipeRefreshLayout swipeDetail;//刷新控件
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;//返回
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;//标题
    @BindView(R.id.iv_right_public)
    ImageView ivRightPublic;//计算器

    @BindView(R.id.invest_type_iv)
    ImageView invest_type_iv; // 标的类型图标
    @BindView(R.id.invest_title_tv)
    TextView invest_title_tv; // 标名
    @BindView(R.id.tv_first_zhaiquan)
    TextView tv_first_zhaiquan;  // 原始债权点击按钮
    @BindView(R.id.tv_rate)
    TextView tv_rate;      // 往期年化收益
    @BindView(R.id.tv_loan_money)
    TextView tv_loan_money;     // 债权价值
    @BindView(R.id.tv_loan_rate)
    TextView tv_loan_rate;   // 转让价格
    @BindView(R.id.tv_loan_limit)
    TextView tv_loan_limit;  //转让折价率
    @BindView(R.id.tv_loan_user)
    TextView tv_loan_user;  // 剩余期数
    @BindView(R.id.tv_loan_method)
    TextView tv_loan_method;  //本期持有天数
    @BindView(R.id.tv_loaninvest_scope)
    TextView tv_loaninvest_scope;  //还款方式
    @BindView(R.id.tv_loan_surplusTime)
    TextView tv_loan_surplusTime;   //转让日期
    @BindView(R.id.tv_nex_return_money)
    TextView tv_nex_return_money;   // 下期还款日
    @BindView(R.id.tv_surplusTime)
    TextView tv_surplusTime;    // 剩余时间
    @BindView(R.id.iv_loan_state)
    ImageView iv_loan_state;   // 印章
    @BindView(R.id.tv_available_balance)
    TextView tv_available_balance;  // 可用余额
    @BindView(R.id.recharge)
    Button recharge;               // 充值按钮
    @BindView(R.id.et_loan_investMoney)
    EditText et_loan_investMoney;   // 输入的投资金额
    @BindView(R.id.tv_loan_Income)
    TextView tv_loan_Income;     // 预计净收益
    @BindView(R.id.btn_loan_buy)
    Button btn_loan_buy;         // 投资按钮
    @BindView(R.id.cb_loan_protocol)
    ImageView cb_loan_protocol;    // 单选框图片
    @BindView(R.id.ll_check)
    LinearLayout llCheck;//同意服务协议
    @BindView(R.id.tv_loan_protocol)
    TextView tv_loan_protocol;   // 服务协议按钮
    @BindView(R.id.loan_investHistory)
    AccountMenuContentItemView loan_investHistory;  // 投标记录
    @BindView(R.id.loan_return_plan)
    AccountMenuContentItemView loan_return_plan; // 还款计划
    @BindView(R.id.loan_info)
    AccountMenuContentItemView loan_info;  // 借款信息
    @BindView(R.id.equit_record)
    AccountMenuContentItemView equit_record;  // 转让记录
    @BindView(R.id.safe_deal)
    AccountMenuContentItemView safe_deal; // 安全保障
    @BindView(R.id.et_loan_psd)
    EditText etLoanPsd;//投标密码
    @BindView(R.id.linear_buy)
    View linearBuy;//立即购买布局
    @BindView(R.id.linear_loan_nologin)
    View linearNoLogin;//未登陆布局
    @BindView(R.id.linear_loan_unInvest)
    View linearUnInvest;//不能投标布局
    @BindView(R.id.tv_loan_error)
    TextView tvLoanError;//当前不可投

    private boolean isAgree = true;//是否同意服务协议

    private String productId;
    private Map params;

    private boolean isSucc = false;//标的详情是否加载成功，只有加载成功才能点击“投标记录”，“还款计划”，“借款信息”
    private EquitableDatailBean.DataBean equitableDatai;
    // private String title;
    private double availableBal;//可用余额

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_equitble_detail);
        productId = getIntent().getStringExtra("productId");
        if (productId == null || productId.isEmpty())//进入该页面必须带有productId
        {
            UIUtils.showToast(this, "未携带productId");
        }
        swipeDetail.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);
        swipeDetail.setOnRefreshListener(this);
        ivLeftPublic.setOnClickListener(this);
        ivRightPublic.setOnClickListener(this);
        //2016-9-7 将计算器图片替换为selector
        ivRightPublic.setImageResource(R.drawable.button_selector_calculator);
        tvContentPublic.setText("项目详情");

        loan_investHistory.setOnClickListener(this);
        loan_return_plan.setOnClickListener(this);
        loan_info.setOnClickListener(this);
        equit_record.setOnClickListener(this);
        safe_deal.setOnClickListener(this);
        llCheck.setOnClickListener(this);
        tv_loan_protocol.setOnClickListener(this);
        btn_loan_buy.setOnClickListener(this);
        recharge.setOnClickListener(this);
        linearNoLogin.setOnClickListener(this);
        tv_first_zhaiquan.setOnClickListener(this);

        tv_first_zhaiquan.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_first_zhaiquan.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void initData()
    {
        EventBus.getDefault().register(this);
        getDataForNet();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_Left_public:  // 返回按钮
                finish();
                break;
            case R.id.iv_right_public: // 收益计算器
                if (equitableDatai != null)
                {
                    Intent intent = new Intent(this, IncomeCalculatorActivity.class);
                    intent.putExtra("term", String.valueOf(equitableDatai.getProduct().getProductDeadline()));
//                    intent.putExtra("bidServerFee", StringUtils.dou2Str(equitableDatai.getBidServiceFee()));
                    intent.putExtra("interestRate", StringUtils.dou2Str(equitableDatai.getProduct().getProfit()));
                    intent.putExtra("repurchaseMode", equitableDatai.getProduct().getRepurchaseMode());
                    startActivity(intent);
                }
                else
                {
                    startActivity(new Intent(this, IncomeCalculatorActivity.class));
                }
                break;
            case R.id.loan_investHistory:  // 投标记录
                if (isSucc)
                {
                    Intent loan_Historyintent = new Intent(this, ThrowMarkHistoryActivity.class);
                    loan_Historyintent.putExtra("id", equitableDatai.getReceiptTransfer().getProductId());
                    startActivity(loan_Historyintent);
                }
                else
                {
                    UIUtils.showToast(this, "页面未加载完成！");
                }
                break;
            case R.id.loan_return_plan:   //还款计划
                if (isSucc)
                {
                    Intent loan_return_plan = new Intent(this, RefundPlanActivity.class);
                    loan_return_plan.putExtra("id", productId);
                    startActivity(loan_return_plan);
                }
                else
                {
                    UIUtils.showToast(this, "页面未加载完成！");
                }
                break;
            case R.id.loan_info:   // 借款信息
                if (isSucc)
                {
                    Intent loan_info = new Intent(this, LoanInfoActivity.class);
                    loan_info.putExtra("id", productId);
                    startActivity(loan_info);
                }
                else
                {
                    UIUtils.showToast(this, "页面未加载完成！");
                }
                break;
            case R.id.equit_record:  //转让记录
                if (isSucc)
                {
                    Intent equ_record = new Intent(this, EquitableRecordActivity.class);
                    equ_record.putExtra("id", productId);
                    startActivity(equ_record);
                }
                else
                {
                    UIUtils.showToast(this, "页面未加载完成！");
                }
                break;
            case R.id.ll_check:  // 单选框点击事件
                isAgree = !isAgree;
                cb_loan_protocol.setImageResource(isAgree ? R.mipmap.deal_select_true : R.mipmap.deal_select);
                break;
            case R.id.tv_loan_protocol:  // 服务协议
                Intent intent = new Intent(this, PrivateActivity.class);
                intent.putExtra("bannerTitle", "服务协议");
                intent.putExtra("data", UrlUtil.getPrivateUrl()); //协议h5的网址
                startActivity(intent);
                break;
            case R.id.recharge:
                Intent charge = new Intent(this, RechargeActivity.class);
                charge.putExtra("recharge", "充值");
                startActivity(charge);
                break;
            case R.id.btn_loan_buy: //立即投资
                if (isAgree)
                {
                    if (equitableDatai != null)
                    {
                        invest();
                    }
                }
                else
                {
                    UIUtils.showToast(this, "未同意服务协议不可投标！");
                }
                break;
            case R.id.safe_deal:  // 安全保障
                startActivity(new Intent(this, SafeDealActivity.class));
                break;
            case R.id.linear_loan_nologin://立即登录
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.putExtra(LoginActivity.KEY_START_TYPE, LoginActivity.START_TYPE_FINISH);
                startActivity(loginIntent);
                break;
            case R.id.tv_first_zhaiquan:  // 原始借款
                if (isSucc)
                {
                    Intent loan_info = new Intent(this, LoanDetailActivity.class);
                    loan_info.putExtra("productId", equitableDatai.getReceiptTransfer().getProductId());
                    startActivity(loan_info);
                }
                else
                {
                    UIUtils.showToast(this, "页面未加载完成！");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 立即投资
     */
    private void invest()
    {
        //超出可用余额
        if (equitableDatai.getProduct().getProductAmount() > availableBal)
        {
            ToastUtil.showToast(this, "账户可用余额不足!");
            return;
        }
        if ((!TextUtils.isEmpty(equitableDatai.getProduct().getPasswords())) && TextUtils.isEmpty(etLoanPsd.getText()
                .toString().trim()))
        {
            ToastUtil.showToast(this, "请输入投标密码！");
            return;
        }
        UIUtils.showLoading(this);
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(EquitableDetailActivity.this, "当前网络不可用，请重试！");
            UIUtils.dimissLoading();
            closeRefresh();
            return;
        }
        Map<String, String> params = new HashMap<>(2);
        params.put("productId", productId);
        params.put("amount", equitableDatai.getProduct().getProductAmount() + "");
        params.put("returnUrl", Constants.BANK_RETURN_URL);
        params.put("bidSource", Integer.toString(4));
        params.put("from", "android");
        params.put("productType", equitableDatai.getProduct().getAssetType() + "");
        params.put("passwords", etLoanPsd.getText().toString().trim());//输入的投标密码

        HashMap<String, String> map = new HashMap<>();
        map.put("productId", productId);
        map.put("name", CacheUtils.getUserinfo(this).getName());
        map.put("HHmm", TimeFormatUtil.getSimpleTime());
        map.put("HH", TimeFormatUtil.getHour() + "H");
        map.put("amount", equitableDatai.getProduct().getProductAmount() + "");
        MobclickAgent.onEvent(this, ServiceName.SCATTERED_BID, map);

        HttpManager.getInstance().doPost(ServiceName.SCATTERED_BID, params, new
                HttpCallBack<ServerResponse<CallBankResponse>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.dimissLoading();
                UIUtils.showToast(EquitableDetailActivity.this, R.string.dataError);
            }

            @Override
            public void getHttpCallBack(ServerResponse<CallBankResponse> rsp)
            {
                UIUtils.dimissLoading();
                if (rsp == null)
                {
                    UIUtils.showToast(EquitableDetailActivity.this, R.string.service_err);
                }
                else if (!"000000".equals(rsp.getCode()))
                {
                    UIUtils.showToast(EquitableDetailActivity.this, rsp.getMsg());
                }
                else
                {
                    CallBankResponse callBank = rsp.getData();
                    if (null == callBank)
                    {
                        UIUtils.showToast(EquitableDetailActivity.this, R.string.service_err);
                        return;
                    }
                    Intent intent = new Intent(EquitableDetailActivity.this, ToBankActivity.class);
                    intent.putExtra("transUrl", callBank.getHxHostUrl());
                    intent.putExtra("transCode", callBank.getTransCode());
                    intent.putExtra("requestData", callBank.getRequestData());
                    intent.putExtra("channelFlow", callBank.getChannelFlow());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onRefresh()
    {
        getDataForNet();
    }

    /**
     * 加载转让详情页网络数据
     */
    private void getDataForNet()
    {
        isSucc = false;
        UIUtils.showLoading(this);
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            closeRefresh();
            return;
        }
        params = getParams();
        HttpManager.getInstance().doPost(ServiceName.RECEIPT_TRANSFER_DETAIL, params, new
                HttpCallBack<EquitableDatailBean>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                closeRefresh();
                showToast(R.string.dataError);
            }

            @Override
            public void getHttpCallBack(EquitableDatailBean rsp)
            {
                if (rsp == null || !rsp.getCode().equals("000000"))
                {
                    closeRefresh();
                    UIUtils.showToast(EquitableDetailActivity.this, R.string.service_err);
                }
                else
                {
                    EquitableDatailBean.DataBean bean = rsp.getData();
                    if (bean == null)
                    {
                        closeRefresh();
                        UIUtils.showToast(EquitableDetailActivity.this, R.string.service_err);
                    }
                    else
                    {
                        isSucc = true;
                        closeRefresh();
                        equitableDatai = bean;
                        showProductDetail();
                    }
                }
            }
        });
    }

    /**
     * 显示标的详情
     */
    private void showProductDetail()
    {
        //标的类型
//        if (equitableDatai.getProduct().getAssetType() == 3)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_3);
//        }
//        else if (equitableDatai.getProduct().getAssetType() == 4)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_4);
//        }
//        else if (equitableDatai.getProduct().getAssetType() == 5)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_5);
//        }
//        else if (equitableDatai.getProduct().getAssetType() == 6)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_6);
//        }
//        else if (equitableDatai.getProduct().getAssetType() == 7)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_7);
//        }
//        else if (equitableDatai.getProduct().getAssetType() == 8)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_8);
//        }
//        else if (equitableDatai.getProduct().getAssetType() == 9)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_9);
//        }
        invest_type_iv.setImageResource(R.mipmap.zhuan);
        //判断是否需要显示投标密码
        if (!TextUtils.isEmpty(equitableDatai.getProduct().getPasswords()))
        {
            etLoanPsd.setVisibility(View.VISIBLE);
        }
        invest_title_tv.setText(equitableDatai.getProduct().getTitle()); // 标题
        tv_rate.setText(StringUtils.double2Str(equitableDatai.getProduct().getProfit())); // 往期年化收益
        tv_loan_money.setText(StringUtils.dou2Str(equitableDatai.getProduct().getTransferAmount())); // 债权价值
        tv_loan_rate.setText(StringUtils.dou2Str(equitableDatai.getProduct().getProductAmount()));  //转让价格 productAmount
        tv_loan_limit.setText(equitableDatai.getReceiptTransfer().getRateOfDiscount() + "%");  //转让折价率 rateOfDiscount

        if (equitableDatai.getProduct().getRepurchaseMode() == 1 || equitableDatai.getProduct().getRepurchaseMode() == 2)
        {
            tv_loan_user.setText(equitableDatai.getProduct().getProductDeadline() + "期");  //剩余期数 productDeadline
        } else if (equitableDatai.getProduct().getRepurchaseMode() == 3)
        {
            tv_loan_user.setText(1 + "期");  //剩余期数 productDeadline
        }
        tv_loan_method.setText(equitableDatai.getReceiptTransfer().getSurplusDays() + "天"); //本期持有天数 surplusDays
        tv_loaninvest_scope.setText(equitableDatai.getProduct().getRepurchaseModeDesc()); //还款方式 repurchaseModeDesc
        tv_loan_surplusTime.setText(TimeFormatUtil.data2StrTime2(equitableDatai.getProduct().getCreatedDate()));
        //转让日期 createdDate
        tv_nex_return_money.setText(TimeFormatUtil.data2StrTime2(equitableDatai.getReceiptTransfer()
                .getRecentRepaymentTime())); // 下期还款日  recentRepaymentTime

        et_loan_investMoney.setText("一次性认购：" + StringUtils.dou2Str(equitableDatai.getProduct().getProductAmount()));
        // 一次性认购金额

        long surplusTime = equitableDatai.getProduct().getInvestEndDate() - System.currentTimeMillis();
        //时间在当前时间之前，或者当前标的状态不可投
        if (surplusTime <= 0 || equitableDatai.getProduct().getProductStatus() != 7)
        {
            tv_surplusTime.setText("已结束");//剩余时间
        }
        else
        {
            int day = (int) (surplusTime / 1000 / (60 * 60 * 24));
            int hour = (int) (surplusTime / 1000 % (60 * 60 * 24)) / (60 * 60);
            int minute = (int) ((surplusTime / 1000 % (60 * 60 * 24)) % (60 * 60)) / 60;
            tv_surplusTime.setText(day + "天" + hour + "时" + minute + "分");//剩余时间
        }
        iv_loan_state.setImageResource(ProductStatusUtil.status2BigImg(equitableDatai.getProduct().getProductStatus()
        ));//设置印章

        String income = StringUtils.dou2Str(equitableDatai.getProduct().getTransferAmount() - equitableDatai
                .getProduct().getProductAmount());
        tv_loan_Income.setText("预计净收益：" + income + "元");  // 预计净收益

        refreshLoginState();
    }

    /**
     * 刷新登录状态
     */
    private void refreshLoginState()
    {
        linearBuy.setVisibility(View.GONE);
        linearNoLogin.setVisibility(View.GONE);
        linearUnInvest.setVisibility(View.GONE);
        if (ProductStatusUtil.isCanInvest(equitableDatai.getProduct().getProductStatus()))//如果当前标可投，需要判断登陆状态
        {
            if (CacheUtils.isLogin())//如果当前是登录状态，显示可直接投标布局
            {
                linearBuy.setVisibility(View.VISIBLE);
                getAvail();

            }
            else
            {
                closeRefresh();
                linearNoLogin.setVisibility(View.VISIBLE);//如果当前是未登录状态，显示提示登录布局
            }
        }
        else
        {
            closeRefresh();
            linearUnInvest.setVisibility(View.VISIBLE);//当前不可投
            tvLoanError.setText("对不起！该标当前状态为“" + ProductStatusUtil.status2String(equitableDatai.getProduct()
                    .getProductStatus()) +
                    "”不可进行投标");
        }
    }

    public void onEvent(Event event)
    {
        CfLog.d("登录成功-LoanDetailActivity");
        if (event.eventType == Event.LOGIN_SUCCESS)
        {
            refreshLoginState();
            UIUtils.showLoading(this);
        }
        if (event.eventType == Event.INVEST)//投资成功后刷新详情页面；
        {
            getDataForNet();
            //清空页面操作数据
            et_loan_investMoney.setText("");
            cb_loan_protocol.setImageResource(R.mipmap.deal_select_true);
            isAgree = true;
            tv_loan_Income.setText("预计净收益：0.00元");
        }

    }

    /**
     * 获取账户可用余额
     */
    private void getAvail()
    {
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
                HttpCallBack<ServerResponse<AccountInfo>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                closeRefresh();
                UIUtils.showToast(EquitableDetailActivity.this, R.string.dataError);
            }

            @Override
            public void getHttpCallBack(ServerResponse<AccountInfo> rsp)
            {
                closeRefresh();
                if (rsp == null || !rsp.getCode().equals("000000"))
                {
                    UIUtils.showToast(EquitableDetailActivity.this, R.string.service_err);
                }
                else
                {
                    AccountInfo rdetail = rsp.getData();
                    if (rdetail == null)
                    {
                        UIUtils.showToast(EquitableDetailActivity.this, R.string.service_err);
                    }
                    else
                    {
                        tv_available_balance.setText("可用余额：" + rdetail.getAvailableBal() + "元");
                        availableBal = Double.parseDouble(rdetail.getAvailableBal().replace(",", "").replace("，", ""));
                    }
                }
            }
        });
    }

    //如果swipeDetail正在刷新，则关闭刷新
    private void closeRefresh()
    {
        if (swipeDetail.isRefreshing())
        {
            swipeDetail.setRefreshing(false);
        }
        UIUtils.dimissLoading();
    }

    /**
     * 请求参数
     */
    private Map getParams()
    {
        Map params = new HashMap();
        params.put("id", productId);
        return params;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().unregister(this);
        }
    }
}
