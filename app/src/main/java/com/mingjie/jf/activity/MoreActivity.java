package com.mingjie.jf.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.AppConfigInfoResponse;
import com.mingjie.jf.bean.AppUpdateResponse;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.AppUpdateUtil;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 更多
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class MoreActivity extends BaseActivity implements View.OnClickListener,SwipeRefreshLayout
        .OnRefreshListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.told)//公共列
            RelativeLayout told;
    @BindView(R.id.suggestion)//意见反馈
            RelativeLayout suggestion;
    @BindView(R.id.service)//服务条款
            RelativeLayout service;
    //    @BindView(R.id.version)//关于版本
    //            RelativeLayout rlUpgrade;
    @BindView(R.id.version)//关于版本
            RelativeLayout version;
    @BindView(R.id.check_version)//关于版本
            RelativeLayout check_version;
    @BindView(R.id.tv_version)
    TextView tvVersion;//版本号
    @BindView(R.id.tv_service_phone)
    TextView tv_service_phone;          // 客服电话
    private SwipeRefreshLayout mSwipeRefreshLayout;//刷新控件

    @BindView(R.id.jubao_text)
    TextView jubaoPhone;

    @Override
    //    public void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_mycount_more);
    //        ButterKnife.bind(this);
    //        initView();
    //        initData();
    //    }

    protected void initView()
    {
        setContentView(R.layout.activity_mycount_more);
        ButterKnife.bind(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("更多");
        tvVersion.setText("V"+UIUtils.getAppVersionName());
        told.setOnClickListener(this);
        jubaoPhone.setText("01059245952");
//        jubaoPhone.setOnClickListener(this);
        suggestion.setOnClickListener(this);
        service.setOnClickListener(this);
        version.setOnClickListener(this);
        check_version.setOnClickListener(this);
    }

    protected void initData()
    {
        AppConfigInfoResponse appConfigInfo = CacheUtils.getAppConfigInfo();
        tv_service_phone.setText(appConfigInfo.getServicePhone());
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.told:
                Intent safephone = new Intent(this, ToldActivity.class);
                startActivity(safephone);
                break;
            case R.id.suggestion:
                if (checkIsLogin())
                {
                    Intent suggestion = new Intent(this, SuggestionActivity.class);
                    startActivity(suggestion);
                }
                break;
            case R.id.service:
                Intent service = new Intent(this, PrivateActivity.class);
                service.putExtra("bannerTitle", "服务条款");
                service.putExtra("data", UrlUtil.getPrivateUrl());
                startActivity(service);
                break;
            case R.id.check_version:
                checkVersion();
                break;
            case R.id.version:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            default:
                break;
        }

    }

    /**
     * 检查版本
     */
    private void checkVersion()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            ToastUtil.showToast(this, "网络异常，请检查！");
            return;
        }

        MobclickAgent.onEvent(this, ServiceName.UPDATEVERSIONS);

        HttpManager.getInstance().doPost(ServiceName.UPDATEVERSIONS, new
                HttpCallBack<ServerResponse<AppUpdateResponse>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        ToastUtil.showToast(MoreActivity.this, "数据获取异常，请稍后重试！");
                    }

                    @Override
                    public void getHttpCallBack(final ServerResponse<AppUpdateResponse> rsp)
                    {
                        if (rsp != null)
                        {
                            if ("000000".equals(rsp.getCode()))
                            {
                                if (rsp.getData() == null)
                                {
                                    ToastUtil.showToast(MoreActivity.this, "数据获取异常，请稍后重试！");
                                    return;
                                }

                                AppUpdateUtil updateUtil = new AppUpdateUtil(MoreActivity.this);
                                updateUtil.appUpdate(rsp.getData(), false);
                            }else{
                                ToastUtil.showToast(MoreActivity.this, rsp.getMsg());
                            }
                        }
                        else
                        {
                            ToastUtil.showToast(MoreActivity.this, "数据获取异常，请稍后重试！");
                        }
                    }
                });
    }

    private boolean checkIsLogin()
    {
        if (!CacheUtils.isLogin())
        {
            UIUtils.showToast(this, "未登录，请先登录！");
            Intent loginActivity = new Intent(this, LoginActivity.class);
            loginActivity.putExtra(LoginActivity.KEY_START_TYPE, LoginActivity.START_TYPE_FINISH);
            startActivity(loginActivity);
        }
        return CacheUtils.isLogin();
    }

    @Override
    public void onRefresh()
    {
        //刷新电话号码
        getTelInfo();
    }

    private void getTelInfo()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {

            if (mSwipeRefreshLayout.isRefreshing())
            {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            //这里检查未能让用户感知，所以这里不用Toast
            return;
        }
        HttpManager.getInstance().doPost(ServiceName.CGKX_CONFIGINFO, new
                HttpCallBack<ServerResponse<AppConfigInfoResponse>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        if (mSwipeRefreshLayout.isRefreshing())
                        {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void getHttpCallBack(final ServerResponse<AppConfigInfoResponse> rsp)
                    {
                        if (mSwipeRefreshLayout.isRefreshing())
                        {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        if (rsp != null && "000000".equals(rsp.getCode()))
                        {
                            if (null==rsp.getData())
                            {
                                return;
                            }
                            tv_service_phone.setText(rsp.getData().getServicePhone());
                        }
                    }
                });
    }

}

