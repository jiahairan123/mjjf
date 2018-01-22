package com.mingjie.jf.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.EquitableRecordAdapter;
import com.mingjie.jf.bean.EquitableRecordBean;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 转让记录 -
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-19 20:14
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class EquitableRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View
        .OnClickListener
{
    private RefreshLayout mRefreshView;  // 刷新控件
    private ListView lvEquitable;
    private boolean isRefresh;
    private ImageView iv_Left_public;  //返回键
    private TextView tv_content_public; //标题

    private String productId;
    private List<EquitableRecordBean.DataBean> mDatas = new ArrayList<>();
    private EquitableRecordAdapter mAdapter;
    private View foot;
    private View llError;//加载出错时展示布局
    private ImageView ivError;//出错图片
    private TextView tvError;//出错提示信息


    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_equitable_record);
        foot = View.inflate(this, R.layout.header, null);
        iv_Left_public = (ImageView) findViewById(R.id.iv_Left_public);
        tv_content_public = (TextView) findViewById(R.id.tv_content_public);
        mRefreshView = (RefreshLayout) findViewById(R.id.refresh_layout);
        lvEquitable = (ListView) findViewById(R.id.lv_equitable);
        iv_Left_public.setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        tv_content_public.setText("转让记录");
        lvEquitable.addFooterView(foot);
        mAdapter = new EquitableRecordAdapter(this, mDatas, lvEquitable);
        lvEquitable.setAdapter(mAdapter);

        llError = findViewById(R.id.linear_error);
        llError.setOnClickListener(this);

        ivError = (ImageView) findViewById(R.id.iv_error_pic);
        tvError = (TextView) findViewById(R.id.tv_error_msg);
    }

    @Override
    protected void initData()
    {
        productId = getIntent().getStringExtra("id");
        getData();
    }

    /**
     * 加载出错时 展示错误布局<br/>
     *
     * @param errImgId 错误图片id<br/>
     * @param errMsg   错误信息<br/>
     */
    private void showError(int errImgId, String errMsg)
    {
        mRefreshView.setVisibility(View.GONE);
        llError.setVisibility(View.VISIBLE);
        ivError.setImageResource(errImgId);
        tvError.setText(errMsg);
    }

    /**
     * 隐藏错误布局<br/>
     */
    private void hideError()
    {
        mRefreshView.setVisibility(View.VISIBLE);
        llError.setVisibility(View.GONE);
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
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.linear_error://加载出错时，重新加载
                mRefreshView.setRefreshing(true);
                getData();
                break;
            default:
                break;
        }
    }

    private void getData()
    {
        UIUtils.showLoading(this);
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, R.string.network_unused_retry_pls);
            if (isRefresh)
            {
                isRefresh = false;
                closeRefresh();
            }
            return;
        }
        Map params = new HashMap();
        params.put("id", productId);
        HttpManager.getInstance().doPost(ServiceName.PURCHASE_RECORD, params, new HttpCallBack<EquitableRecordBean>()
        {
            @Override
            public void getHttpCallNull(String str)
            {  // 数据为空
                UIUtils.dimissLoading();
                UIUtils.showToast(EquitableRecordActivity.this, R.string.dataError);
                closeRefresh();
                showError(R.mipmap.net_error, getResources().getString(R.string.netError));
            }

            @Override
            public void getHttpCallBack(EquitableRecordBean list)
            {  // 成功
                UIUtils.dimissLoading();
                closeRefresh();
                if (null != list)
                {
                    if ("000000".equals(list.getCode()))
                    {
                        if (list.getData() == null || list.getData().size() == 0)
                        {
                            showError(R.mipmap.null_data, "无更多数据！");
                            CfLog.i("list.getData().size() = "+list.getData().size());
                        }
                        else
                        {
                            CfLog.i("list.getData().size() = "+list.getData().size());
                            hideError();
                            if (isRefresh)
                            {
                                isRefresh = false;
                                mDatas.clear();
                                mDatas.addAll(list.getData());
                                UIUtils.showToast(EquitableRecordActivity.this, R.string.refreshed);
                            }
                            else
                            {
                                mDatas.clear();
                                mDatas.addAll(list.getData());
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        showError(R.mipmap.net_error, getResources().getString(R.string.netError));
                        UIUtils.showToast(list.getMsg());
                    }
                }
                else
                {
                    showError(R.mipmap.null_data, "无更多数据！");
                    UIUtils.showToast("数据为空");
                }
            }
        });
    }
}