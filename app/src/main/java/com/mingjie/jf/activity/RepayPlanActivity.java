package com.mingjie.jf.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BorrowPlanAdapter;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.RepayPlanBean;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 还款计划 activity
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-27 20:01
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RepayPlanActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener
{

    @BindView(R.id.swipe_container)
    RefreshLayout mRefreshView;     // 刷新控件
    @BindView(R.id.listview_investlist)
    ListView listview_investlist;
    @BindView(R.id.iv_Left_public)
    ImageView iv_Left_public;       // 返回箭头
    @BindView(R.id.tv_content_public)
    TextView tv_content_public;     // 标题文字

    @BindView(R.id.linear_error)
    LinearLayout linear_error;      // 没有数据界面布局
    @BindView(R.id.iv_error_pic)
    ImageView iv_error_pic;         // 没有数据显示的图片
    @BindView(R.id.tv_error_msg)
    TextView tv_error_msg;          // 没有数据显示的文字

    private BorrowPlanAdapter adapter;
    private String productId;       // 标的id

    private boolean isRefresh;                 // 是否正在刷新

    private List<RepayPlanBean.DataBean> mDatas = new ArrayList<>();

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_repayplan);
        ButterKnife.bind(this);
        iv_Left_public.setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        linear_error.setOnClickListener(this);
    }

    /**
     * 显示空界面
     */
    private void showErrorPage(int image, int str)
    {
        linear_error.setVisibility(View.VISIBLE);
        iv_error_pic.setImageResource(image);
        tv_error_msg.setText(str);
        mRefreshView.setVisibility(View.GONE);
    }

    /**
     * 隐藏错误界面
     */
    private void hintErrorPage()
    {
        linear_error.setVisibility(View.GONE);
        mRefreshView.setVisibility(View.VISIBLE);
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

    public void onEvent(Event event)
    {
        //还款后，刷新界面
        if (event.eventType == Event.REPAYMENT)
        {
            CfLog.i("还款计划收到event , 刷新界面");
            getData();
        }
    }

    @Override
    protected void initData()
    {
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
        UIUtils.showLoading(this);
        tv_content_public.setText("还款计划");
        productId = getIntent().getStringExtra("productId");
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        HttpManager.getInstance().doPost(ServiceName.REPAYMENT_GETREPAYLIST, params, new HttpCallBack<RepayPlanBean>()
        {
            @Override
            public void getHttpCallNull(String str)
            {   // 数据为空
                showErrorPage(R.mipmap.net_error, R.string.dataError);
                UIUtils.dimissLoading();
                UIUtils.showToast(getBaseContext(), R.string.dataError);

            }

            @Override
            public void getHttpCallBack(RepayPlanBean list)
            {   // 成功
                UIUtils.dimissLoading();
                if (null != list)
                {
                    if ("000000".equals(list.code))
                    {
                        hintErrorPage();
                        mDatas = list.data;
                        adapter = new BorrowPlanAdapter(RepayPlanActivity.this, mDatas, listview_investlist);
                        listview_investlist.setAdapter(adapter);
                        if (mDatas.size() == 0)
                        {
                            showErrorPage(R.mipmap.nodata, R.string.nullDatas);
                        }
                    }
                    else
                    {
                        showErrorPage(R.mipmap.net_error, R.string.service_err);
                        UIUtils.showToast(list.msg);
                    }
                }
                else
                {
                    showErrorPage(R.mipmap.nodata, R.string.service_err);
                    UIUtils.showToast(getBaseContext(), getResources().getString(R.string.service_err));
                }
            }
        });
    }

    protected void closeRefresh()
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

    @Override
    public void onRefresh()
    {
        // 下拉刷新
        mRefreshView.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue, R.color.red);
        mRefreshView.setRefreshing(true);
        isRefresh = true;
        getData();
    }

    public void getData()
    {  // 上拉加载 下拉刷新 调用加载数据的方法
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        HttpManager.getInstance().doPost(ServiceName.REPAYMENT_GETREPAYLIST, params, new HttpCallBack<RepayPlanBean>()
        {
            @Override
            public void getHttpCallNull(String str)
            {   // 数据为空
                showErrorPage(R.mipmap.net_error, R.string.dataError);
                UIUtils.dimissLoading();
                UIUtils.showToast(getBaseContext(), R.string.dataError);
                closeRefresh();
            }

            @Override
            public void getHttpCallBack(RepayPlanBean list)
            {  // 成功
                closeRefresh();
                if (null != list)
                {
                    if ("000000".equals(list.code))
                    {
                        hintErrorPage();
                        mDatas = list.data;
                        adapter = new BorrowPlanAdapter(RepayPlanActivity.this, mDatas, listview_investlist);
                        listview_investlist.setAdapter(adapter);
                        if (mDatas.size() == 0)
                        {
                            showErrorPage(R.mipmap.nodata, R.string.nullDatas);
                        }
                    }
                    else
                    {
                        showErrorPage(R.mipmap.net_error, R.string.service_err);
                        UIUtils.showToast(list.msg);
                    }
                }
                else
                {
                    showErrorPage(R.mipmap.nodata, R.string.service_err);
                    UIUtils.showToast(getBaseContext(), getResources().getString(R.string.service_err));
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.linear_error:
                initData();
                break;
            default:
                break;
        }
    }
}
