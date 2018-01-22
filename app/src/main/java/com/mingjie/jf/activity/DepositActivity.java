package com.mingjie.jf.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.CallBankResponse;
import com.mingjie.jf.bean.DepositActionVo;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.fragment.iActivity.IDepositActivity;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.IdCardUtil;
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
 * <p>描   述： 存管页面
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class DepositActivity extends BaseActivity implements View.OnClickListener, IDepositActivity
{
    @BindView(R.id.deposite_realname)
    EditText depositeRealname;
    @BindView(R.id.deposite_idname)
    EditText depositeIdname;
//    @BindView(R.id.tv_deposite_protocol)
//    TextView tvDepositeProtocol;
    @BindView(R.id.btn_deposite)
    MateralButton btnDeposite;
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.rl_adv_details)
    RelativeLayout rl_adv_details;
    private DepositActionVo vo;
    private UserVoValible user;
    private CallBankResponse rDetail;

//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_deposit);
//        ButterKnife.bind(this);
//        user = CacheUtils.getUserinfo(this);
//        initView();
//        initData();
//
//    }

    protected void initView()
    {
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);
        user = CacheUtils.getUserinfo(this);
        ivLeftPublic.setOnClickListener(this);
        btnDeposite.setOnClickListener(this);
        rl_adv_details.setOnClickListener(this);
    }

    protected void initData()
    {
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
    }

    @OnClick({R.id.btn_deposite})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_deposite:
                if (checkdata())
                {
                    opendeposite();
                }//调后台接口，拿加密数据
                break;
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.rl_adv_details:  // 开通存管详情页
                Intent advDetails = new Intent(DepositActivity.this , AdvDetailActivity.class);
                startActivity(advDetails);
                break;
            default:
                break;
        }
    }

    private boolean checkdata()
    {
        if (depositeRealname.getText().toString().trim().isEmpty())
        {
            UIUtils.showToast("真实姓名不能为空");
            return false;
        }
        if (depositeIdname.getText().toString().trim().isEmpty())
        {
            UIUtils.showToast("身份证不能为空");
            return false;
        }
        else
        {
            boolean isIdCard =  IdCardUtil.isIdCard(depositeIdname.getText().toString().trim());
            if (!isIdCard)
            {
                UIUtils.showToast("身份证格式不正确！");
            }
            return isIdCard;
        }

    }

    //收到开通存管的EventBus后关闭该开通存管的页面
    public void onEvent(Event event)
    {
        if (event.eventType == Event.OPEN_ACCOUNT)
        {
            finish();
        }
    }

    private void opendeposite()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(DepositActivity.this, "当前网络不可用，请重试！");
            return;
        }
        UIUtils.showLoading(this);

        HashMap<String,String> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("HHmm", TimeFormatUtil.getSimpleTime());
        map.put("HH", TimeFormatUtil.getHour());
        String idCard = depositeIdname.getText().toString().trim();
        if (idCard.length() > 12)
        {
            idCard = idCard.substring(0, 12);
        }
        map.put("idCard", "ID" + idCard);
        MobclickAgent.onEvent(this, ServiceName.CGKX_OPENGHBANKACCOUNT , map);

        Map<String, String> params = new HashMap<>();
        params.put("from", "android");
        params.put("id", user.getId());
        params.put("idcard", depositeIdname.getText().toString().trim());
        params.put("returnUrl", Constants.BANK_RETURN_URL);
        params.put("realName", depositeRealname.getText().toString().trim());

        HttpManager.getInstance().doPost(ServiceName.CGKX_OPENGHBANKACCOUNT, params, new
                HttpCallBack<ServerResponse<CallBankResponse>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        UIUtils.dimissLoading();
                        UIUtils.showToast(DepositActivity.this, R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<CallBankResponse> rsp)
                    {
                        UIUtils.dimissLoading();
                        if (rsp == null)
                        {
                            UIUtils.showToast(DepositActivity.this, R.string.service_err);
                            return;
                        }
                        if (null != rsp.getCode() && !rsp.getCode().equals("000000"))
                        {
                            UIUtils.showToast(DepositActivity.this, rsp.getMsg());
                            return;
                        }
                        else if (null == rsp.getData())
                        {
                            UIUtils.showToast(DepositActivity.this, R.string.service_err);
                        }
                        else
                        {
                            rDetail = rsp.getData();
                            Intent bankData = new Intent(DepositActivity.this, ToBankActivity.class);
                            bankData.putExtra("transUrl", rsp.getData().getHxHostUrl());
                            bankData.putExtra("transCode", rsp.getData().getTransCode());
                            bankData.putExtra("requestData", rsp.getData().getRequestData());
                            bankData.putExtra("channelFlow", rsp.getData().getChannelFlow());
                            startActivity(bankData);
                            finish();
                        }
                    }
                });
    }

    @Override
    public Activity getMyActivity()
    {
        return this;
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
