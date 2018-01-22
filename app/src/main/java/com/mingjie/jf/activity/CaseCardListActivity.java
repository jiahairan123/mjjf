package com.mingjie.jf.activity;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.CaseCardBean;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.QuitDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 银行卡列表
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-11-22 10:16
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class CaseCardListActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener, QuitDialog
        .OnEnterListener, QuitDialog.OnCancelListener
{

    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.has_data)
    LinearLayout hasData;
    @BindView(R.id.iv_error_pic)
    ImageView ivError;
    @BindView(R.id.tv_error_msg)
    TextView tvError;
    @BindView(R.id.linear_error)
    LinearLayout mErrorView;
    @BindView(R.id.iv_bankicon)//银行图标
            ImageView ivBankicon;
    @BindView(R.id.tv_bankname)//所属银行
            TextView tvBankname;
    @BindView(R.id.tv_bank_name)//开户的客户名字
            TextView tvBankCustomName;
    @BindView(R.id.tv_bank_number)//银行的尾号
            TextView tvBankNumber;
    @BindView(R.id.tv_banklist_unBind)
    TextView tvBanklistUnBind;//删除按钮
    @BindView(R.id.swipe_account_fragment)
    SwipeRefreshLayout mRefreshView;
    @BindView(R.id.tv_realname)
    TextView tvRealname;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    private UserVoValible user;
    private CaseCardBean cardList;

    private String[] mBankNameDic = UIUtils.getStringArray(R.array.bankList);//银行名称
    private int[] mBankIconDic;//银行icon
    private QuitDialog quitDialog;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_casecard_list);
        ButterKnife.bind(this);
        user = CacheUtils.getUserinfo(this);
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("银行卡");
        llName.setVisibility(View.VISIBLE);
        tvBanklistUnBind.setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);
    }

    @Override
    protected void initData()
    {
        if (mRefreshView.isRefreshing())
        {
            mRefreshView.setRefreshing(false);
        }
        mRefreshView.post(new Runnable()
        {
            @Override
            public void run()
            {
                mRefreshView.setRefreshing(true);
                getBindList();
            }
        });

    }

    //获取绑卡的数据
    private void getBindList()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            showError(R.mipmap.net_error, "当前网络不可用，点击重试！");
            closeRefresh();
            return;
        }
        mRefreshView.setRefreshing(true);
        Map<String, String> params = new HashMap<>();
        params.put("userId", user.getId());
        HttpManager.getInstance().doPost(ServiceName.BANKCARDINFO, params, new
                HttpCallBack<ServerResponse<CaseCardBean>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        showError(R.mipmap.net_error, getResources().getString(R.string.dataError2));
                        closeRefresh();
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<CaseCardBean> rsp)
                    {
                        closeRefresh();
                        if (rsp == null)
                        {
                            showError(R.mipmap.net_error, R.string.service_err2);
                        }
                        else if (rsp.getCode().equals("000000"))
                        {
                            cardList = rsp.getData();
                            if (null != cardList)
                            {
                                //银行卡信息获取成功
                                showBindCard(cardList);
                            }
                            else if (null == rsp.getData())
                            {
                                showError(R.mipmap.nodata, R.string.nodata);
                            }
                        }
                        else
                        {
                            showError(R.mipmap.net_error, R.string.service_err2);
                        }
                    }
                });
    }

    private void closeRefresh()
    {
        if (mRefreshView == null)
        {
            return;
        }
        if (mRefreshView.isRefreshing())
        {
            mRefreshView.setRefreshing(false);
        }
    }

    //展示银行卡信息
    private void showBindCard(CaseCardBean cardList)
    {
        if (null != cardList)
        {
            this.cardList = cardList;
            tvBanklistUnBind.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(cardList.getBankNameByNo()))
            {
                tvBankname.setText(cardList.getBankNameByNo());//开户的所属银行

                TypedArray iconDic = null;
                try
                {
                    iconDic = this.getResources().obtainTypedArray(R.array.bankIcon);

                    mBankIconDic = new int[iconDic.length()];
                    for (int i = 0; i < iconDic.length(); i++)
                    {
                        mBankIconDic[i] = iconDic.getResourceId(i, 0);
                    }
                    String data = cardList.getBankNameByNo();
                    for (int i = 0; i < mBankNameDic.length; i++)
                    {
                        if (null != data && data.equals(mBankNameDic[i]))
                        {
                            ivBankicon.setImageResource(mBankIconDic[i]);
                        }
                    }
                }
                catch (Resources.NotFoundException e)
                {
                    CfLog.e(e);
                }

            }
            tvBankCustomName.setText(cardList.getRealName());//开户人姓名
            tvRealname.setText(cardList.getRealName());
            tvBankNumber.setText(cardList.getCardNo());//尾号
            //        ivBankicon.setImageResource();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.tv_banklist_unBind:
                quitDialog = new QuitDialog(this, "温馨提示", "确定解除此卡绑定", "取消", "确定");
                quitDialog.setEnterListener(this);
                quitDialog.setCancelListener(this);
                quitDialog.show();
                break;
            default:
        }
    }

    //删除绑定的银行卡

    private void deletBank()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            showError(R.mipmap.net_error, "当前网络不可用，点击重试！");
            closeRefresh();
            return;
        }
        mRefreshView.setRefreshing(true);
        HashMap<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("HHmm", TimeFormatUtil.getSimpleTime());
        map.put("HH", TimeFormatUtil.getHour());
        MobclickAgent.onEvent(this, ServiceName.DELBANKCARD, map); // 私房钱删卡

        Map<String, String> params = new HashMap<>();
        params.put("userId", user.getId());
        HttpManager.getInstance().doPost(ServiceName.DELBANKCARD, params, new
                HttpCallBack<ServerResponse>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        mRefreshView.setRefreshing(false);
                        UIUtils.showToast(getResources().getString(R.string.dataError2));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse rsp)
                    {
                        mRefreshView.setRefreshing(false);
                        if (rsp == null)
                        {
                            UIUtils.showToast(getResources().getString(R.string.dataError2));
                        }
                        else if (!rsp.getCode().equals("000000"))
                        {
                            UIUtils.showToast(rsp.getMsg());
                        }
                        else if (rsp.getCode().equals("000000"))
                        {
                            finish();
                            //关闭当前页
                            //刷新账户
                            Event event = new Event();
                            event.eventType = Event.REFRESH_ACCOUNT;
                            EventBus.getDefault().post(event);
                        }
                        else
                        {
                            showError(R.mipmap.net_error, R.string.service_err2);
                        }
                    }
                });
    }

    private void showError(int errIconId, int id)
    {
        showError(errIconId, getBaseContext().getResources().getString(id));
    }

    //展示数据加载出错布局
    private void showError(int errIconId, String errMsg)
    {
        CfLog.i("showError" + errMsg);
        mErrorView.setVisibility(View.VISIBLE);
        hasData.setVisibility(View.GONE);
        ivError.setImageResource(errIconId);
        tvError.setText(errMsg);
    }

    @Override
    public void onRefresh()
    {
        getBindList();
    }

    @Override
    public void onCancelListener()
    {
        quitDialog.dismiss();
    }

    @Override
    public void onEnterListener()
    {
        quitDialog.dismiss();
        deletBank();
    }
}
