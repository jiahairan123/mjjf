package com.mingjie.jf.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.CallBankResponse;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.TransferMessgBean;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 收购债权界面
 * <p>创 建 人： qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class PuchaseActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.iv_right_public)
    ImageView ivRightPublic;

    @BindView(R.id.bt_ok)
    MateralButton bt_ok;

    @BindView(R.id.tv_zhaiquan_amount)
    TextView tv_zhaiquan_amount;         //债权价值
    @BindView(R.id.tv_zhaiquan_lixi)
    TextView tv_zhaiquan_lixi;          // 当前至今利息
    @BindView(R.id.tv_zhaiquan_benxi)
    TextView tv_zhaiquan_benxi;          // 剩余本息
    @BindView(R.id.tv_zhaiquan_time)
    TextView tv_zhaiquan_time;          // 剩余时间
    @BindView(R.id.tv_zhaiquan_return_date)
    TextView tv_zhaiquan_return_date;    //下期回款日期

    @BindView(R.id.et_zhaiquan_amount)
    EditText et_zhaiquan_amount;       // 转让价格
    @BindView(R.id.et_zhaiquan_time)
    EditText et_zhaiquan_time;         // 转让时效
    @BindView(R.id.tv_zhejia)
    TextView tv_zhejia;                 //折价
    @BindView(R.id.tv_sunshi)
    TextView tv_sunshi;                 //损失
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;  //刷新控件


    private String id;                          // 传递的id参数
    private NumberFormat nf;

    private String minPrice;

    private String recentRepaymentDate;  // 最近待收日期
    private String productId;
    private String transferCapital;
    private String transferAmount;
    private String transferorlntertest;
    private String surplusDays;
    private int transferMaxDays;

    private Map params;

    protected void initView()
    {
        setContentView(R.layout.activity_loan_detail_repayment_plan);
        ButterKnife.bind(this);
        ivLeftPublic.setOnClickListener(this);
        ivRightPublic.setOnClickListener(this);
        tvContentPublic.setText("转让信息");
        bt_ok.setOnClickListener(this);
        refresh_layout.setOnRefreshListener(this);
        id = getIntent().getStringExtra("id");
        nf = new DecimalFormat("0.00");
    }

    protected void initData()
    {
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
        UIUtils.showLoading(this);
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            showToast("当前网络不可用，请重试！");
            return;
        }
        params = getParams();
        HttpManager.getInstance().doPost(ServiceName.LOAD_RECEIPT_TRANSFER, params, new
                HttpCallBack<TransferMessgBean>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        closeRefresh();
                        UIUtils.dimissLoading();
                        showToast(R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(TransferMessgBean transferMessgBean)
                    {
                        closeRefresh();
                        UIUtils.dimissLoading();
                        if (null != transferMessgBean)
                        {
                            if ("000000".equals(transferMessgBean.getCode()))
                            {
                                if (TextUtils.isEmpty(transferMessgBean.getData().getIncreaseInterest()))
                                {
                                    tv_zhaiquan_amount.setText(StringUtils.dou2Str(Double.parseDouble(transferMessgBean
                                            .getData()
                                            .getCurrentValue())) + "元");
                                }
                                else
                                {
                                    tv_zhaiquan_amount.setText(StringUtils.dou2Str(Double.parseDouble(transferMessgBean
                                            .getData()
                                            .getCurrentValue())) + "元" +"+"+transferMessgBean.getData()
                                            .getIncreaseInterest() + "元" + "（加息收益）");  // 债权价值
                                }
                                tv_zhaiquan_lixi.setText(StringUtils.dou2Str(Double.parseDouble(transferMessgBean
                                        .getData()
                                        .getToTodayInterest())) + "元"); //当前至今利息
                                tv_zhaiquan_benxi.setText(StringUtils.dou2Str(Double.parseDouble(transferMessgBean
                                        .getData()
                                        .getCapital())) + "元");  // 剩余本金
                                tv_zhaiquan_time.setText(transferMessgBean.getData().getSurplusTime());  // 剩余时间
                                tv_zhaiquan_return_date.setText(new SimpleDateFormat("yyyy-MM-dd").format
                                        (transferMessgBean
                                        .getData().getRecentRepaymentDate())); //下期回款日期
                                minPrice = transferMessgBean.getData().getMinPrice();

                                recentRepaymentDate = new SimpleDateFormat("yyyy-MM-dd").format(transferMessgBean
                                        .getData()
                                        .getRecentRepaymentDate());
                                productId = transferMessgBean.getData().getBidDetail().getProductId();
                                transferCapital = transferMessgBean.getData().getCapital();
                                transferAmount = transferMessgBean.getData().getCurrentValue();
                                transferorlntertest = transferMessgBean.getData().getToTodayInterest();
                                surplusDays = transferMessgBean.getData().getSurplusDays();
                                transferMaxDays = transferMessgBean.getData().getTransferMaxDays();
                            }
                            else
                            {
                                UIUtils.showToast(transferMessgBean.getMsg());
                            }
                        }
                        else
                        {
                            showToast("数据为空");
                        }
                    }
                });

        // 监听转让价格
        et_zhaiquan_amount.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                double str = Double.parseDouble(s.toString().trim().isEmpty() ? "0" : s.toString().trim());
                double amount = Double.parseDouble(transferorlntertest) + Double.parseDouble(transferCapital);
                if (str > amount)
                {
                    et_zhaiquan_amount.setText(amount + "");
                    UIUtils.showToast("转让价格需小于" + amount + "元(剩余本金+当前至今利息)");
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                double amount = Double.parseDouble(et_zhaiquan_amount.getText().toString().trim().isEmpty() ? "0" :
                        et_zhaiquan_amount.getText().toString().trim());

                double zheJia = ((amount - Double.parseDouble(transferorlntertest)) / Double.parseDouble
                        (transferCapital)) * 100; // （转让价格 - 当前至今利息）/ 本金 *100
                if (zheJia<=0)
                {
                    tv_zhejia.setText("折价率：---");   // 折价
                }
                else
                {
                    tv_zhejia.setText("折价率：" + StringUtils.dou2Str(zheJia) + "%");   // 折价
                }

                double sunShi = Double.parseDouble(transferCapital) - ((amount - Double.parseDouble
                        (transferorlntertest)));
                tv_sunshi.setText("损失本金金额：" + StringUtils.dou2Str(sunShi) + "元");  // 损失

                if (et_zhaiquan_amount.getText().toString().trim().isEmpty())
                {
                    tv_zhejia.setText("折价率:---");   // 折价
                    tv_sunshi.setText("损失本金金额:---");  // 损失
                }
            }
        });
    }

    public void onEvent(Event event)
    {
        //收购债权后，刷新界面
        if (event.eventType == Event.TRANSFER)
        {
            CfLog.i("转让信息收到收购债权event , 关闭界面");
            finish();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().unregister(this);
        }
    }

    private void closeRefresh()
    {
        if (refresh_layout == null)
        {
            return;
        }
        if (refresh_layout.isRefreshing())
        {
            refresh_layout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh()
    {
        initData();
    }

    /**
     * @return 返回债权转让详情参数
     */
    public Map getParams()
    {
        Map params = new HashMap();
        params.put("bidDetailId", id);
        return params;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.bt_ok:  // 确认转让
                if (checkParams())
                {
                    getToBankData();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 检查前台参数
     */
    private boolean checkParams()
    {
        String amountStr = et_zhaiquan_amount.getText().toString();
        String timeStr = et_zhaiquan_time.getText().toString();
        if (TextUtils.isEmpty(amountStr))
        {
            UIUtils.showToast("请输入转让价格！");
            return false;
        }
        if (TextUtils.isEmpty(timeStr))
        {
            UIUtils.showToast("请输入转让时效！");
            return false;
        }

        double amount = Double.valueOf(amountStr);
        int time = Integer.parseInt(timeStr);
        if (amount < Double.parseDouble(minPrice))
        {
            UIUtils.showToast("转让价格需大于" + Double.parseDouble(minPrice) + "元");
            return false;
        }
        if (amount > Double.parseDouble(transferCapital) + Double.parseDouble(transferorlntertest))
        {
            UIUtils.showToast("转让价格需小于" + (Double.parseDouble(transferCapital) + Double.parseDouble
                    (transferorlntertest)) + "元(剩余本金+当前至今利息)");
            return false;
        }
        if (time < 1)
        {
            UIUtils.showToast("转让时效至少一天");
            return false;
        }
        if (time > 10)
        {
            UIUtils.showToast("转让时效不能超过10天");
            return false;
        }
        if (time > transferMaxDays)
        {
            UIUtils.showToast("转让时效不能超过" + transferMaxDays + "天");
            return false;
        }
        return true;
    }

    /**
     * 获取跳转银行参数
     */
    private void getToBankData()
    {
        UIUtils.showLoading(this);
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            showToast("当前网络不可用，请重试！");
            return;
        }
        params = getToBankParams();
        HttpManager.getInstance().doPost(ServiceName.CREDITORRIGHTSTRANSFER, params, new
                HttpCallBack<ServerResponse<CallBankResponse>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        UIUtils.dimissLoading();
                        showToast(R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<CallBankResponse> rsp)
                    {
                        UIUtils.dimissLoading();
                        if (null != rsp)
                        {
                            if ("000000".equals(rsp.getCode()))
                            {
                                // 请求成功，跳转银行
                                CallBankResponse callBank = rsp.getData();
                                Intent intent = new Intent(PuchaseActivity.this, ToBankActivity.class);
                                intent.putExtra("transUrl", callBank.getHxHostUrl());
                                intent.putExtra("transCode", callBank.getTransCode());
                                intent.putExtra("requestData", callBank.getRequestData());
                                intent.putExtra("channelFlow", callBank.getChannelFlow());
                                intent.putExtra("receiptTransferId", callBank.getReceiptTransferId());
                                startActivity(intent);
                            }
                            else
                            {
                                UIUtils.showToast(rsp.getMsg());
                            }
                        }
                        else
                        {
                            showToast("数据为空");
                        }
                    }
                });
    }

    private Map getToBankParams()
    {
        Map params = new HashMap();
        // 获取输入转让价格
        String amount = et_zhaiquan_amount.getText().toString().trim().isEmpty() ? "0" : et_zhaiquan_amount.getText()
                .toString().trim();
        // 获取输入装让时效
        String day = et_zhaiquan_time.getText().toString().trim().isEmpty() ? "0" : et_zhaiquan_time.getText()
                .toString().trim();
        params.put("bidDetailId", id);
        params.put("successAmount", amount);
        params.put("validDay", day);
        params.put("recentRepaymentDate", recentRepaymentDate);
        params.put("productId", productId);
        params.put("userId", CacheUtils.getUserinfo(this).getId());
        params.put("transferCapital", transferCapital);
        params.put("transferAmount", transferAmount);
        params.put("transferorlntertest", transferorlntertest);
        params.put("surplusDays", surplusDays);
        params.put("channel", 4);
        params.put("returnUrl", Constants.BANK_RETURN_URL);
        return params;
    }

    /**
     * 立即购买
     */
    //    private void invest()
    //    {
    //        UIUtils.showLoading(this);
    //        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
    //        {
    //            showToast("当前网络不可用，请重试！");
    //            return;
    //        }
    //        Map<String, String> params = new HashMap<>(2);
    //        params.put("productId", id);
    ////        params.put("amount", buy_money);
    //        params.put("from", "android");
    //
    //        HttpManager.getInstance().doPost(ServiceName.SCATTERED_BID, params, new
    //                HttpCallBack<ServerResponse<CallBankResponse>>()
    //        {
    //            @Override
    //            public void getHttpCallNull(String str)
    //            {
    //                UIUtils.dimissLoading();
    //                showToast(R.string.dataError);
    //            }
    //
    //            @Override
    //            public void getHttpCallBack(ServerResponse<CallBankResponse> rsp)
    //            {
    //                UIUtils.dimissLoading();
    //                if (rsp == null)
    //                {
    //                    showToast(R.string.service_err);
    //                } else if (!rsp.getCode().equals("000000"))
    //                {
    //                    showToast(rsp.getMsg());
    //                } else
    //                {
    //                    CallBankResponse callBank = rsp.getData();
    //                    Intent intent = new Intent(PuchaseActivity.this, ToBankActivity.class);
    //                    intent.putExtra("transUrl", callBank.getHxHostUrl());
    //                    intent.putExtra("transCode", callBank.getTransCode());
    //                    intent.putExtra("requestData", callBank.getRequestData());
    //                    intent.putExtra("channelFlow", callBank.getChannelFlow());
    //                    startActivity(intent);
    //                }
    //            }
    //        });
    //    }
}
