package com.mingjie.jf.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.AccountInfo;
import com.mingjie.jf.bean.CallBankResponse;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserInfo;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CashierInputFilter;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 充值和提现界面
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener,
        View.OnFocusChangeListener
{

    @BindView(R.id.iv_back)
    ImageView ivBack;//返回
    @BindView(R.id.tv_title)
    TextView textView;//标题
    @BindView(R.id.current_money)
    TextView current_money;//当前金额
    @BindView(R.id.recharge_money)
    EditText rechargeMoney;//充值金额
    @BindView(R.id.btn_login)
    MateralButton btnRecharge;//确定按钮
    @BindView(R.id.tv_bind_bank)
    TextView tvbindbank;//绑定银行卡
    @BindView(R.id.clean)
    ImageView clean;
    SwipeRefreshLayout srlRecharge;//刷新控件
    @BindView(R.id.tv_chongzhi)
    TextView tvChongzhi;
    @BindView(R.id.tv_tixian)
    TextView tvTixian;
    @BindView(R.id.tv_total_number)
    TextView tvTotalNumber;//累计充值金额
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.tv_tixian_guide)
    TextView tvTixianGuide;
    @BindView(R.id.tv_tixian_guidego)
    TextView tvTixianGuidego;

    private String rechargetitle;//共用充值界面的title
    private TextView tvTitle;
    private CallBankResponse rDetail;

    private UserVoValible user;
    private AccountInfo rdetail;    // 账户信息

    //是否是初次加载数据
    private boolean isFirstLoad = true;

    private int RECHARGE = 1;//充值标识符
    private int TIXIAN = 2;//提现标识符
    private int isOpenDeposite;
    private UserInfo userinfo;

    //    @Override
    //    protected void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_recharge);
    //        ButterKnife.bind(this);
    //        rechargetitle = getIntent().getStringExtra("recharge");
    //        user = CacheUtils.getUserinfo(this);
    //        initView();
    //        initData();
    //    }

    protected void initView()
    {
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        rechargetitle = getIntent().getStringExtra("recharge");
        user = CacheUtils.getUserinfo(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(rechargetitle);
        btnRecharge.setOnClickListener(this);
        tvbindbank.setOnClickListener(this);
        srlRecharge = (SwipeRefreshLayout) findViewById(R.id.srl_recharge);
        srlRecharge.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);
        srlRecharge.setOnRefreshListener(this);
        clean.setOnClickListener(this);
        tvTixianGuidego.setOnClickListener(this);

        if ("充值".equals(rechargetitle))
        {
            rechargeMoney.setHint("单笔充值不超过1000000");
            tvChongzhi.setVisibility(View.VISIBLE);
        }
        else if ("提现".equals(rechargetitle))
        {
            rechargeMoney.setHint("请输入提现金额");
            tvTixian.setVisibility(View.VISIBLE);
            tvTixianGuide.setVisibility(View.VISIBLE);
            tvTixianGuidego.setVisibility(View.VISIBLE);
            //            tvTixianGuidego.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
        rechargeMoney.setOnFocusChangeListener(this);
        InputFilter[] filters = {new CashierInputFilter()};
        rechargeMoney.setFilters(filters);

    }

    protected void initData()
    {
        UIUtils.showLoading(this);
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
        if ("提现".equals(rechargetitle))
        {
            viewLine.setVisibility(View.GONE);
            llTotal.setVisibility(View.GONE);
        }
        getResultAccount();
        getUserInfo();
    }

    private void getResultAccount()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            closeRefresh();
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            UIUtils.dimissLoading();
            finish();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", user.getId());
        HttpManager.getInstance().doPost(ServiceName.CGKX_ACCOUNT, params, new
                HttpCallBack<ServerResponse<AccountInfo>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        if (isFirstLoad)
                        {
                            UIUtils.dimissLoading();
                            isFirstLoad = false;
                        }
                        closeRefresh();
                        UIUtils.showToast(RechargeActivity.this, R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<AccountInfo> rsp)
                    {
                        if (isFirstLoad)
                        {
                            UIUtils.dimissLoading();
                            isFirstLoad = false;
                        }
                        closeRefresh();
                        if (null == rsp)
                        {
                            UIUtils.showToast(RechargeActivity.this, R.string.dataError);
                            return;
                        }
                        else if (!"000000".equals(rsp.getCode()) && null != rsp.getMsg())
                        {
                            UIUtils.showToast(RechargeActivity.this, rsp.getMsg());
                        }
                        else
                        {
                            rdetail = rsp.getData();//获取账户信息成功
                            CfLog.i("---" + rdetail);
                            if (rdetail == null)
                            {
                                UIUtils.showToast(RechargeActivity.this, R.string.service_err);
                            }
                            else
                            {
                                current_money.setText(rdetail.getAvailableBal());
                                tvTotalNumber.setText(rdetail.getRechargeAmountCount());
                            }
                        }
                    }
                });
    }

    private void getResult()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(RechargeActivity.this, "当前网络不可用，请重试！");
            return;
        }
        UIUtils.showLoading(RechargeActivity.this);
        if ("充值".equals(rechargetitle))
        {
            Map<String, String> params = new HashMap<>();
            params.put("rechargeAmount", rechargeMoney.getText().toString().trim());
            params.put("returnUrl", Constants.BANK_RETURN_URL);
            params.put("from", "android");
            CfLog.d("---1" + rechargetitle);

            HashMap<String, String> map = new HashMap<>();
            map.put("name", user.getName());
            map.put("HHmm", TimeFormatUtil.getSimpleTime());
            map.put("HH", TimeFormatUtil.getHour() + "H");
            map.put("rechargeAmount", "" + Double.valueOf(rechargeMoney.getText().toString().trim()).intValue());
            MobclickAgent.onEvent(this, ServiceName.CGKX_RECHARGE, map);

            HttpManager.getInstance().doPost(ServiceName.CGKX_RECHARGE, params, new
                    HttpCallBack<ServerResponse<CallBankResponse>>()
                    {
                        @Override
                        public void getHttpCallNull(String str)
                        {
                            UIUtils.dimissLoading();
                            UIUtils.showToast(RechargeActivity.this, R.string.dataError);

                        }

                        @Override
                        public void getHttpCallBack(ServerResponse<CallBankResponse> rsp)
                        {
                            UIUtils.dimissLoading();
                            if (null == rsp)
                            {
                                UIUtils.showToast(RechargeActivity.this, R.string.dataError);

                                return;
                            }
                            if (null == rsp.getCode())
                            {
                                UIUtils.showToast(RechargeActivity.this, R.string.service_err);
                                return;
                            }
                            else if (!"000000".equals(rsp.getCode()) && null != rsp.getMsg())
                            {
                                UIUtils.showToast(RechargeActivity.this, rsp.getMsg());
                            }
                            else
                            {
                                rDetail = rsp.getData();
                                if (rDetail == null)
                                {
                                    UIUtils.showToast(RechargeActivity.this, R.string.service_err);
                                }
                                else
                                {
                                    Intent bankData = new Intent(RechargeActivity.this, ToBankActivity.class);
                                    bankData.putExtra("transUrl", rsp.getData().getHxHostUrl());
                                    bankData.putExtra("transCode", rsp.getData().getTransCode());
                                    bankData.putExtra("requestData", rsp.getData().getRequestData());
                                    bankData.putExtra("channelFlow", rsp.getData().getChannelFlow());
                                    startActivity(bankData);
                                }
                            }
                        }
                    });
        }
        else if ("提现".equals(rechargetitle))
        {
            Map<String, String> params = new HashMap<>();
            params.put("withdrawAmount", rechargeMoney.getText().toString().trim());
            params.put("returnUrl", Constants.BANK_RETURN_URL);
            params.put("from", "android");
            CfLog.d("---2" + rechargetitle);

            HashMap<String, String> map = new HashMap<>();
            map.put("name", user.getName());
            map.put("HHmm", TimeFormatUtil.getSimpleTime());
            map.put("HH", TimeFormatUtil.getHour() + "H");
            map.put("rechargeAmount", "" + Double.valueOf(rechargeMoney.getText().toString().trim()).intValue());
            MobclickAgent.onEvent(this, ServiceName.CGKX_WITHDRAW, map);

            HttpManager.getInstance().doPost(ServiceName.CGKX_WITHDRAW, params, new
                    HttpCallBack<ServerResponse<CallBankResponse>>()
                    {
                        @Override
                        public void getHttpCallNull(String str)
                        {
                            UIUtils.dimissLoading();
                            UIUtils.showToast(RechargeActivity.this, R.string.dataError);
                        }

                        @Override
                        public void getHttpCallBack(ServerResponse<CallBankResponse> rsp)
                        {
                            UIUtils.dimissLoading();
                            if (null == rsp)
                            {
                                UIUtils.showToast(RechargeActivity.this, R.string.dataError);
                                return;
                            }
                            if (null == rsp.getCode())
                            {
                                UIUtils.showToast(RechargeActivity.this, R.string.service_err);
                                return;
                            }
                            else if (!"000000".equals(rsp.getCode()) && null != rsp.getMsg())
                            {
                                UIUtils.showToast(RechargeActivity.this, rsp.getMsg());
                            }
                            else
                            {
                                rDetail = rsp.getData();
                                if (rDetail == null)
                                {
                                    UIUtils.showToast(RechargeActivity.this, R.string.service_err);
                                }
                                else
                                {
                                    Intent bankData = new Intent(RechargeActivity.this, ToBankActivity.class);
                                    bankData.putExtra("transUrl", rsp.getData().getHxHostUrl());
                                    bankData.putExtra("transCode", rsp.getData().getTransCode());
                                    bankData.putExtra("requestData", rsp.getData().getRequestData());
                                    bankData.putExtra("channelFlow", rsp.getData().getChannelFlow());
                                    startActivity(bankData);
                                }
                            }
                        }
                    });
        }
    }

    @OnClick({R.id.iv_back, R.id.recharge_money, R.id.btn_login, R.id.tv_bind_bank})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.clean:
                rechargeMoney.setText("");// 设置输入框内容为空
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.recharge_money:
                break;
            case R.id.btn_login://点击确定之前先判断数值是否满足条件

                if ("充值".equals(rechargetitle))
                {
                    //                    SharedPreferences sharedata = this.getSharedPreferences("delposite", 0);
                    //                    isOpenDeposite = sharedata.getInt("del", 2);
                    CfLog.i("---isOpenDeposite" + isOpenDeposite);
                    //判断是否开通存管账号
                    if (1 == isOpenDeposite)
                    {
                        if (checkData(RECHARGE))
                        {
                            disabledView(btnRecharge);
                            getResult();
                        }
                    }
                    else if (0 == isOpenDeposite)
                    {
                        showToast("未开通存管账户");
                        //                        finish();
                        startActivity(new Intent(this, DepositActivity.class));
                    }
                    else
                    {

                    }
                }
                else if ("提现".equals(rechargetitle))
                {
                    if (checkData(TIXIAN))
                    {
                        disabledView(btnRecharge);
                        getResult();
                    }
                }
                break;
            case R.id.tv_bind_bank:
                if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                {
                    UIUtils.showToast(RechargeActivity.this, "当前网络不可用，请重试！");
                }
                else
                {
                    //绑定银行卡
                    UIUtils.showLoading(this);
                    bindback();
                    break;
                }
            case R.id.tv_tixian_guidego://跳转提现指引
                Intent intent3 = new Intent(this, AdvFlowActivity.class);
                intent3.putExtra("type", 3);
                startActivity(intent3);
                break;
            default:
        }
    }

    private boolean checkData(int type)
    {
        if (TIXIAN == type)
        {
            if (rdetail == null)
            {
                CfLog.i("---" + rdetail);
                UIUtils.dimissLoading();
                UIUtils.showToast("当前可用余额获取失败，不能提现");
                return false;
            }
        }

        //充值/提现金额不能为空
        if (rechargeMoney.getText().toString().trim().isEmpty())
        {
            UIUtils.showToast(this, rechargetitle + "金额不能为空");
            return false;
        }
        //充值/提现金额不能为0
        if (Double.parseDouble(rechargeMoney.getText().toString().trim()) == 0.00)
        {
            UIUtils.showToast(this, rechargetitle + "金额不能为0");
            return false;
        }
        if ("提现".equals(rechargetitle))
        {
            if (Double.parseDouble(rdetail.getAvailableBal()) < Double.parseDouble(rechargeMoney.getText().toString()
                    .trim()))
            {
                UIUtils.showToast(this, "提现金额大于可用余额");
                return false;
            }
            return true;
        }
        if ("充值".equals(rechargetitle))
        {
            if (Double.parseDouble(rechargeMoney.getText().toString().trim()) > 1000000)
            {
                UIUtils.showToast(this, "单笔充值不能超过1000000");
                return false;
            }
        }
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            return false;
        }
        return true;
    }

    private void bindback()
    {
        Map<String, String> params = new HashMap<>();
        // params.put("acno", acno);
        params.put("id", user.getId());
        params.put("returnUrl", Constants.BANK_RETURN_URL);
        params.put("from", "android");
        CfLog.d("---id:" + user.getId());

        HashMap<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        MobclickAgent.onEvent(this, ServiceName.CGKX_BOUNDACC, map);

        HttpManager.getInstance().doPost(ServiceName.CGKX_BOUNDACC, params, new
                HttpCallBack<ServerResponse<CallBankResponse>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        UIUtils.dimissLoading();
                        UIUtils.showToast(RechargeActivity.this, R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<CallBankResponse> rsp)
                    {
                        UIUtils.dimissLoading();
                        if (rsp == null)
                        {
                            UIUtils.showToast(RechargeActivity.this, R.string.service_err);
                        }
                        else if (!"000000".equals(rsp.getCode()))
                        {
                            UIUtils.showToast(RechargeActivity.this, rsp.getMsg());
                        }
                        else
                        {
                            rDetail = rsp.getData();
                            if (rDetail == null)
                            {
                                UIUtils.dimissLoading();
                                UIUtils.showToast(RechargeActivity.this, rsp.getMsg());
                            }
                            else
                            {
                                UIUtils.dimissLoading();
                                Intent bankData = new Intent(RechargeActivity.this, ToBankActivity.class);
                                bankData.putExtra("transUrl", rsp.getData().getHxHostUrl());
                                bankData.putExtra("transCode", rsp.getData().getTransCode());
                                bankData.putExtra("requestData", rsp.getData().getRequestData());
                                bankData.putExtra("channelFlow", rsp.getData().getChannelFlow());
                                startActivity(bankData);
                            }
                        }
                    }
                });
    }

    @Override
    public void onRefresh()
    {
        getResultAccount();
        getUserInfo();
    }

    /**
     * 关闭刷新
     */
    private void closeRefresh()
    {
        if (srlRecharge.isRefreshing())
        {
            srlRecharge.setRefreshing(false);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        switch (view.getId())
        {
            case R.id.recharge_money:
                if (b)
                {
                    clean.setVisibility(View.VISIBLE);
                }
                else
                {
                    clean.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }

    public void onEvent(Event event)
    {
        if (event.eventType == Event.OPEN_ACCOUNT || event.eventType == Event.RECHARGE || event.eventType == Event
                .WITHDRAW)
        {
            srlRecharge.setRefreshing(true);
            getUserInfo();
            getResultAccount();
        }
    }

    private void getUserInfo()
    {
        user = CacheUtils.getUserinfo(this);
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            closeRefresh();
            return;
        }
        //        mRefreshView.setRefreshing(true);
        Map<String, String> params = new HashMap<>();
        params.put("id", user.getId());
        HttpManager.getInstance().doPost(ServiceName.CGKX_USERINFO, params, new HttpCallBack<ServerResponse<UserInfo>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                closeRefresh();
            }

            @Override
            public void getHttpCallBack(ServerResponse<UserInfo> rsp)
            {
                closeRefresh();
                if (rsp == null || null == rsp.getData())
                {
                    return;
                }
                if (null == rsp.getCode())
                {
                    return;
                }
                else if (!rsp.getCode().equals("000000"))
                {
                    return;
                }

                userinfo = rsp.getData();
                CfLog.i("---" + userinfo);
                if (userinfo == null)
                {
                    //                    UIUtils.showToast(this, R.string.service_err);
                }
                else
                {
                    //保存是否开通存管账户
                    SharedPreferences.Editor sharedata = getSharedPreferences("delposite", 0).edit();
                    isOpenDeposite = userinfo.getUserStates().getIsFundDepository();
                    sharedata.putInt("del", userinfo.getUserStates().getIsFundDepository());
                    sharedata.commit();

                }

            }
        });
    }

    /**
     * 防重复提交
     * @param v
     */
    public static void disabledView(final View v) {
        v.setClickable(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                v.setClickable(true);
//                UIUtils.showToast("请不要重复提交");
            }
        }, 3000);
    }
}
