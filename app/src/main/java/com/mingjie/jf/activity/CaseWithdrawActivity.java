package com.mingjie.jf.activity;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.action.AbsSmsCodeAction;
import com.mingjie.jf.bean.CaseBankCd;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.OtherSmsCodeActionVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CashierInputFilter;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.DES3EncryptAndDecrypt;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 申请提现
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-11-22 10:16
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class CaseWithdrawActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener,
        SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    //    @BindView(R.id.swiperefreshlayout)
    //    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.iv_bankicon)
    ImageView mIvIcon;//银行标识
    @BindView(R.id.tv_bank)
    TextView mTvBankName;//银行名称
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;//开户名称
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;//开户银行尾号
    @BindView(R.id.btn_login)
    MateralButton btnLogin;//提交申请
    @BindView(R.id.et_case_money)
    EditText etCaseMoney;//提现金额
    @BindView(R.id.tv_count_yuer)
    TextView tvCountYuer;
    @BindView(R.id.clean)
    ImageView clean;
    @BindView(R.id.tv_max)
    TextView tvMax;
    @BindView(R.id.tv_min)
    TextView tvMin;
    @BindView(R.id.tv_tixian_num)
    TextView tvTixianNum;
    @BindView(R.id.et_loginpsd)
    EditText etLoginpsd;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.verificationcode)
    MateralButton verificationcode;
    //    @BindView(R.id.swiperefreshlayout)
    //    SwipeRefreshLayout srlRecharge;
    //是否是初次加载数据
    private boolean isFirstLoad = true;
    private CaseBankCd rdetail;

    private String[] mBankNameDic = UIUtils.getStringArray(R.array.bankList);//银行名称
    private int[] mBankIconDic;//银行icon
    private String amount;
    private TimeCount mTiemTimeCount;

    private boolean isFromGetCode;

    private final int GETCODE_TYPE = 0;
    private final int APPLY_TYPE = 1;
    private String verfycode;
    private String loginPsd;
    private String msgcode;
    private String telphone;

    private double wallet;
    private SwipeRefreshLayout srlRecharge;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_apply_case);
        // ButterKnife.bind(this);
        srlRecharge = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        srlRecharge.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);
        srlRecharge.setOnRefreshListener(this);
        setClickAndTouchListener(vkbd, etLoginpsd);
        ivBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        clean.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                etCaseMoney.setText("");
            }
        });

        wallet = getIntent().getDoubleExtra("wallet", 0);
        CfLog.i("wallet:" + wallet);

        //计时器
        mTiemTimeCount = new TimeCount(60000, 1000, verificationcode);
        isFromGetCode = false;
        verificationcode.setOnClickListener(this);
        tvCountYuer.setText(wallet + "");
        etCaseMoney.setOnFocusChangeListener(this);
        InputFilter[] filters = {new CashierInputFilter()};
        etCaseMoney.setFilters(filters);

        SharedPreferences sharedata = getSharedPreferences("teldata", 0);
        telphone = sharedata.getString("tel", null);
        //        UIUtils.showToast(telphone);
    }

    @Override
    protected void initData()
    {
        getCaseWithdrawAccount();
    }

    private void getCaseWithdrawAccount()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showLoading(this);
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            UIUtils.dimissLoading();
            finish();
            return;
        }
        HttpManager.getInstance().doPost(ServiceName.APPLYWITHDRAWALS, null, new
                HttpCallBack<ServerResponse<CaseBankCd>>()
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
                        UIUtils.showToast(CaseWithdrawActivity.this, R.string.dataError);

                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<CaseBankCd> rsp)
                    {
                        closeRefresh();
                        if (isFirstLoad)
                        {
                            UIUtils.dimissLoading();
                            isFirstLoad = false;
                        }
                        //                        closeRefresh();
                        if (rsp == null || null == rsp.getData())
                        {
                            //                            closeRefresh();
                            cleanBank();
                            UIUtils.showToast(CaseWithdrawActivity.this, R.string.service_err);
                            return;
                        }
                        if (null == rsp.getCode())
                        {
                            //                            closeRefresh();
                            UIUtils.showToast(CaseWithdrawActivity.this, R.string.service_err);
                            cleanBank();
                        }
                        else if (!rsp.getCode().equals("000000"))
                        {
                            //                            closeRefresh();
                            cleanBank();
                            UIUtils.showToast(CaseWithdrawActivity.this, rsp.getMsg());
                            return;
                        }
                        rdetail = rsp.getData();//获取账户银行卡信息成功
                        if (rdetail == null)
                        {
                            cleanBank();
                            UIUtils.showToast(CaseWithdrawActivity.this, R.string.service_err);
                        }
                        else
                        {
                            showBank(rdetail);
                        }
                    }
                });
    }

    private void showBank(CaseBankCd rdetail)
    {
        if (null != rdetail)
        {
            this.rdetail = rdetail;
            if (null != rdetail.getWalletBankcard())
            {
                tvBankName.setText(rdetail.getWalletBankcard().getRealName());
                tvBankNumber.setText(rdetail.getWalletBankcard().getCardNo());
                if (!TextUtils.isEmpty(rdetail.getWalletBankcard().getBankNameByNo()))
                {
                    mTvBankName.setText(rdetail.getWalletBankcard().getBankNameByNo());//开户的所属银行
                }
                TypedArray iconDic = this.getResources().obtainTypedArray(R.array.bankIcon);
                mBankIconDic = new int[iconDic.length()];
                for (int i = 0; i < iconDic.length(); i++)
                {
                    mBankIconDic[i] = iconDic.getResourceId(i, 0);
                }
                if (!TextUtils.isEmpty(rdetail.getWalletBankcard().getBankNameByNo()))
                {
                    String data = rdetail.getWalletBankcard().getBankNameByNo();
                    for (int i = 0; i < mBankNameDic.length; i++)
                    {
                        if (data.equals(mBankNameDic[i]))
                        {
                            mIvIcon.setImageResource(mBankIconDic[i]);
                        }
                    }

                }
            }
            tvMax.setText("每次最大提现金额" + rdetail.getMax_withdraw_amount());
            tvMin.setText("每次最小提现金额" + rdetail.getMin_withdraw_amount());
            tvTixianNum.setText("每日提现次数" + rdetail.getWithdraw_number());
        }
        else
        {

        }
    }

    private void cleanBank()
    {
        tvBankName.setText("");
        tvBankNumber.setText("");
        mTvBankName.setText("");//开户的所属银行
        tvMax.setText("每次最大提现金额");
        tvMin.setText("每次最小提现金额");
        tvTixianNum.setText("每日提现次数");
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_back:
                finish();
                break;

            case R.id.verificationcode:
                isFromGetCode = true;
                if (checkData())
                {
                    verificationcode.setEnabled(false);
                    //获取短信验证码
                    OtherSmsCodeActionVo smsvo = new OtherSmsCodeActionVo();
                    smsvo.setPhone(telphone);
                    new AbsSmsCodeAction(this, mTiemTimeCount)
                    {
                        public void setSmsCode(String smsCode)
                        {
                            mTiemTimeCount.start();//启动计时器
                            //验证码
                            verfycode = smsCode;
                            if (UrlUtil.isSmsDebug())
                            {
                                edtCode.setText(verfycode);
                            }
                        }

                        public void setSmsCodeError()
                        {
                            verificationcode.setEnabled(true);
                        }
                    }.othersmsCodeAction(smsvo);
                }
                break;
            case R.id.btn_login:
                isFromGetCode = false;
                if (checkData())
                {
                    getWithdraw();
                }
                break;
            default:
                break;
        }
    }

    private boolean checkData()
    {

        loginPsd = etLoginpsd.getText().toString().trim();//登录密码
        msgcode = edtCode.getText().toString().trim();//验证码

        //提现金额不能为空
        if (etCaseMoney.getText().toString().trim().isEmpty())
        {
            UIUtils.showToast(this, "提现金额不能为空");
            return false;
        }
        //提现金额不能为0
        if (Double.parseDouble(etCaseMoney.getText().toString().trim()) == 0.00)
        {
            UIUtils.showToast(this, "提现金额不能为0");
            return false;
        }

        if (wallet < Double.parseDouble(etCaseMoney.getText().toString().trim()))
        {
            CfLog.i("---" + wallet);
            CfLog.i("---" + etCaseMoney.getText().toString());
            UIUtils.showToast(this, "提现金额大于可用余额");
            return false;
        }

        if (TextUtils.isEmpty(etLoginpsd.getText().toString().trim()))
        {
            UIUtils.showToast("请输入登录密码");
            return false;
        }
        if (isFromGetCode)
        {
            return true;
        }
        if (TextUtils.isEmpty(edtCode.getText().toString().trim()))
        {
            UIUtils.showToast("请输入短信验证码!");
            return false;
        }
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            return false;
        }
        return true;
    }

    //提交申请button
    private void getWithdraw()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(CaseWithdrawActivity.this, "当前网络不可用，请重试！");
            return;
        }
        UIUtils.showLoading(CaseWithdrawActivity.this);
        HashMap<String, String> map = new HashMap<>();
        map.put("name", CacheUtils.getString(this, "name"));
        map.put("HHmm", TimeFormatUtil.getSimpleTime());
        map.put("HH", TimeFormatUtil.getHour());
        map.put("amount", "" + Double.valueOf(etCaseMoney.getText().toString().trim()).intValue());
        MobclickAgent.onEvent(this, ServiceName.WITHDRAW, map); // 私房钱提现

        Map<String, String> params = new HashMap<>();
        params.put("amount", etCaseMoney.getText().toString().trim());
//        params.put("password", etLoginpsd.getText().toString().trim());
        params.put("password", DES3EncryptAndDecrypt.des3EncryptMode(etLoginpsd.getText().toString().trim()));
        params.put("pcode", edtCode.getText().toString().trim());
        HttpManager.getInstance().doPost(ServiceName.WITHDRAW, params, new
                HttpCallBack<ServerResponse>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        UIUtils.dimissLoading();
                        UIUtils.showToast(CaseWithdrawActivity.this, R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse rsp)
                    {
                        UIUtils.dimissLoading();
                        if (null == rsp)
                        {
                            UIUtils.showToast(CaseWithdrawActivity.this, R.string.dataError);
                            return;
                        }
                        if (null == rsp.getCode())
                        {
                            UIUtils.showToast(CaseWithdrawActivity.this, R.string.service_err);
                            return;
                        }
                        else if (!"000000".equals(rsp.getCode()) && null != rsp.getMsg())
                        {
                            UIUtils.showToast(CaseWithdrawActivity.this, rsp.getMsg());
                        }
                        else if ("000000".equals(rsp.getCode()) && null != rsp.getMsg())
                        {
                            UIUtils.showToast(CaseWithdrawActivity.this, "提交申请" + rsp.getMsg());

                            tvCountYuer.setText((wallet - Double.valueOf(etCaseMoney.getText().toString().trim())) +
                                    "");
                            finish();
                            Event event = new Event();
                            event.eventType = Event.TIJIAO;
                            EventBus.getDefault().post(event);

                        }
                        else
                        {
                            UIUtils.showToast(CaseWithdrawActivity.this, R.string.service_err);
                        }
                    }
                });

    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        switch (view.getId())
        {
            case R.id.et_case_money:
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

    @Override
    public void onRefresh()
    {
        getCaseWithdrawAccount();
    }

    //    private void getResultAccount()
    //    {
    //        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
    //        {
    //            closeRefresh();
    //            UIUtils.showToast(this, "当前网络不可用，请重试！");
    //            UIUtils.dimissLoading();
    //            finish();
    //            return;
    //        }
    //        Map<String, String> params = new HashMap<>();
    //        params.put("currentPage", "1");
    //        params.put("pageSize", "10");
    //        HttpManager.getInstance().doPost(ServiceName.WALLETFLOWPAGE, params, new
    // HttpCallBack<ServerResponse<CaseManager>>()
    //                {
    //                    @Override
    //                    public void getHttpCallNull(String str)
    //                    {
    //                        closeRefresh();
    //                    }
    //
    //                    @Override
    //                    public void getHttpCallBack(ServerResponse<CaseManager> rsp)
    //                    {
    //                        closeRefresh();
    //                        if (null != rsp&&null!=rsp.getData().getWallet())
    //                        {
    //                            tvCountYuer.setText(rsp.getData().getWallet()+"");
    //
    //                            return;
    //                        }
    //                        else {
    //
    //                        }
    //                    }
    //                });
    //    }

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
}
