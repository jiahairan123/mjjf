package com.mingjie.jf.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.adapter.SelectServiceAdapter;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.ServiceBean;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.QuitDialog;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述：客服管理系统
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-12-08 14:31
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class ServicePeoPleActivity extends BaseActivity implements View.OnClickListener, QuitDialog.OnEnterListener,
        QuitDialog.OnCancelListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener

{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.listview_service)
    ListView mListView;//客服列表
    @BindView(R.id.tv_service_name)
    TextView mTvSerciceName;//客服称呼
    @BindView(R.id.tv_service_number)
    TextView mTvServiceNumber;//客服手机号
    @BindView(R.id.tv_service_qq)
    TextView mTvServiceQQ;//客服QQ
    @BindView(R.id.tv_service_desc)
    TextView mTvServiceDesc;//客服自述
    @BindView(R.id.iv_service_icon)
    ImageView mIvServiceIcon;//客服头像
    @BindView(R.id.linear_callme)
    LinearLayout mCallMe;//请呼我
    @BindView(R.id.service_refresh)
    RefreshLayout mRefreshView;
    @BindView(R.id.linear_myservicel)
    LinearLayout mMyService;//我的客服
    @BindView(R.id.iv_error_pic)
    ImageView ivError;
    @BindView(R.id.tv_error_msg)
    TextView tvError;
    @BindView(R.id.linear_error)
    LinearLayout mErrorView;
    private String serviceId;//客服id(如果客服id==null?选择客服:查看客服详情)

    private boolean isRefresh;                 // 是否正在刷新
    //private boolean isLoad;                    // 是否正在加载
    //private int pageTotal;

    private BaseListViewAdapter<ServiceBean.ListBean> mAdapter;
    private List<ServiceBean.ListBean> customlv;
    private ServiceBean.ListBean customBean;
    private QuitDialog dialog;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_custom_service);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData()
    {
        serviceId = getIntent().getStringExtra("serviceId");
        mRefreshView.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue, R.color.red);

        initState(serviceId);

        mListView.setOnItemClickListener(this);
        mCallMe.setOnClickListener(this);
        ivLeftPublic.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mTvServiceNumber.setOnClickListener(this);
        mErrorView.setOnClickListener(this);

        //        //进来首先加载一次数据
        //        mRefreshView.post(new Runnable()
        //        {
        //            @Override
        //            public void run()
        //            {
        //                mRefreshView.setRefreshing(true);
        //                onRefresh();
        //            }
        //        });
    }

    private void initState(String serviceId)
    {
        if (TextUtils.isEmpty(serviceId))
        {
            tvContentPublic.setText("选择客服");
            mRefreshView.setVisibility(View.VISIBLE);
            mMyService.setVisibility(View.GONE);
            mRefreshView.post(new Runnable()
            {
                @Override
                public void run()
                {
                    mRefreshView.setRefreshing(true);
                }
            });
            mMyService.setVisibility(View.GONE);
            getcustomlist();
        }
        else
        {
            tvContentPublic.setText("我的客服");
            mMyService.setVisibility(View.VISIBLE);
            mRefreshView.setVisibility(View.GONE);
            getMyCustom();
        }
    }

    /**
     * 关闭刷新
     */
    public void closeRefresh()
    {
        if (mRefreshView == null)
        {
            return;
        }
        if (mRefreshView.isRefreshing())
        {
            mRefreshView.setRefreshing(false);
        }
        else if (mRefreshView.isLoading())
        {
            mRefreshView.setLoading(false);
        }
    }

    //获取我的客服数据
    private void getMyCustom()
    {
        UIUtils.showLoading(this);
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, R.string.network_unused_retry_pls);
            UIUtils.dimissLoading();
            showError(R.mipmap.net_error, "当前网络不可用！");
            return;
        }
        HttpManager.getInstance().doPost(ServiceName.FINDUSERCUSTOMERSERVCE, null, new
                HttpCallBack<ServerResponse<ServiceBean>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        // 数据为空
                        UIUtils.dimissLoading();
                        UIUtils.showToast(ServicePeoPleActivity.this, R.string.dataError);
                        showError(R.mipmap.net_error, R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<ServiceBean> mycustom)
                    {  // 成功
                        UIUtils.dimissLoading();
                        if (null != mycustom)
                        {
                            if ("000000".equals(mycustom.getCode()))
                            {
                                showCustomUI(mycustom);
                            }
                            else
                            {
                                UIUtils.showToast(ServicePeoPleActivity.this, mycustom.getMsg());
                            }
                        }
                        else
                        {
                            UIUtils.showToast(ServicePeoPleActivity.this, R.string.dataError);
                            showError(R.mipmap.net_error, R.string.dataError);

                        }
                    }
                });

    }

    //展示我的客服信息
    private void showCustomUI(ServerResponse<ServiceBean> mycustom)
    {
        Glide.with(this).load(mycustom.getData().getList().get(0).getPath()).centerCrop().error(R.mipmap
                .default_myservice).crossFade().into(mIvServiceIcon);
        mTvSerciceName.setText(mycustom.getData().getList().get(0).getName());
        mTvServiceNumber.setText(mycustom.getData().getList().get(0).getPhone());
        mTvServiceQQ.setText(mycustom.getData().getList().get(0).getQq());
        mTvServiceDesc.setText(mycustom.getData().getList().get(0).getMotto());
    }

    //获取客服列表的数据
    private void getcustomlist()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, R.string.network_unused_retry_pls);
            showError(R.mipmap.net_error, "当前网络不可用！");
            if (isRefresh)
            {
                isRefresh = false;
                closeRefresh();
            }
            return;
        }
        // 上拉加载 下拉刷新 调用加载数据的方法
        HttpManager.getInstance().doPost(ServiceName.CUSTOMERSERVCELIST, null, new
                HttpCallBack<ServerResponse<ServiceBean>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {  // 数据为空
                        UIUtils.showToast(ServicePeoPleActivity.this, R.string.dataError);
                        showError(R.mipmap.net_error, R.string.dataError);
                        closeRefresh();
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<ServiceBean> customlist)
                    {  // 成功
                        closeRefresh();
                        if (null != customlist)
                        {
                            if ("000000".equals(customlist.getCode()))
                            {
                                //                               rDetail = rsp.getData();
                                if (TextUtils.isEmpty(customlist.getData().getList().toString()))
                                {
                                    closeRefresh();
                                    showError(R.mipmap.null_data, "暂无数据");
                                    //                                    showError(R.mipmap.null_data, "数据为空，点击重试！");
                                }
                                else
                                {
                                    closeRefresh();
                                    if (0 != customlist.getData().getList().size())
                                    {
                                        showList(customlist.getData().getList());
                                    }
                                }
                            }
                            else
                            {
                                UIUtils.showToast(ServicePeoPleActivity.this, customlist.getMsg());
                            }
                        }
                        else
                        {
                            UIUtils.showToast(ServicePeoPleActivity.this, R.string.dataError);
                            showError(R.mipmap.net_error, R.string.dataError);
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
        mMyService.setVisibility(View.GONE);
        mRefreshView.setVisibility(View.GONE);
        ivError.setImageResource(errIconId);
        tvError.setText(errMsg);
    }

    /**
     * 展示客服列表
     *
     * @param list
     */
    private void showList(List<ServiceBean.ListBean> list)
    {
        this.customlv = list;
        UIUtils.showToast(ServicePeoPleActivity.this, R.string.refreshed);
        mAdapter = new SelectServiceAdapter(this, customlv, mListView);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onCancelListener()
    {
        dialog.dismiss();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            //            拨号
            case R.id.tv_service_number:
            case R.id.linear_callme:
                if (null != mTvServiceNumber.getText().toString().trim())
                {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mTvServiceNumber.getText()
                            .toString().trim()
                    ));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;

            //点击错误布局时
            case R.id.linear_error:
                if ("我的客服".equals(tvContentPublic.getText()))
                {
                    mErrorView.setVisibility(View.GONE);
                    mMyService.setVisibility(View.VISIBLE);
                    mRefreshView.setVisibility(View.GONE);
                    getMyCustom();
                }
                else
                {
                    /**
                     * 清除数据
                     */
                    customlv.clear();
                    mAdapter.notifyDataSetChanged();
                    mRefreshView.setVisibility(View.VISIBLE);
                    mRefreshView.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            mRefreshView.setRefreshing(true);
                        }
                    });
                    mMyService.setVisibility(View.GONE);
                    getcustomlist();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onEnterListener()
    {
        dialog.dismiss();
        bindCustom();
    }

    /**
     * 绑定相应的客服
     */
    private void bindCustom()
    {
        UIUtils.showLoading(ServicePeoPleActivity.this);
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {

            UIUtils.showToast(this, R.string.network_unused_retry_pls);
            showError(R.mipmap.net_error, "当前网络不可用！");
            UIUtils.dimissLoading();
            if (isRefresh)
            {
                isRefresh = false;
                closeRefresh();
            }
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("Id", customBean.getId());//客服id
        CfLog.i("----" + customBean.getId());
        // 上拉加载 下拉刷新 调用加载数据的方法
        HttpManager.getInstance().doPost(ServiceName.BINDINGCUSTOMERSERVCE, params, new
                HttpCallBack<ServerResponse<String>>()
                {

                    @Override
                    public void getHttpCallNull(String str)
                    {  // 数据为空
                        UIUtils.dimissLoading();
                        UIUtils.showToast(ServicePeoPleActivity.this, R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<String> isBind)
                    {  // 成功
                        UIUtils.dimissLoading();
                        if ("000000".equals(isBind.getCode()))
                        {
                            UIUtils.showToast("绑定成功");
                            Event event = new Event();
                            event.eventType = Event.REFRESH_ACCOUNT;
                            EventBus.getDefault().post(event);
                            finish();
                        }
                        else if (!"000000".equals(isBind.getCode()))
                        {
                            UIUtils.showToast(isBind.getMsg());
                        }
                        else
                        {
                            UIUtils.showToast(ServicePeoPleActivity.this, R.string.dataError);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        customBean = customlv.get(position);
        dialog = new QuitDialog(this, "选择客服", "确定选择客服" + customBean.getName(), "取消", "确定");
        dialog.setEnterListener(this);
        dialog.setCancelListener(this);
        dialog.show();
    }

    @Override
    public void onRefresh()
    {
        isRefresh = true;
        getcustomlist();
    }

}
