package com.mingjie.jf.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.GainCouponAdapter;
import com.mingjie.jf.bean.BaseListBean;
import com.mingjie.jf.bean.CashDetailBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 加息券收益明细界面
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2017-02-23 11:35
 * <p>当前版本： ${version}
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class GainCouponActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener, RefreshLayout.OnLoadListener, AdapterView.OnItemClickListener
{

    TextView tvContent;//activity标题
    private RefreshLayout refreshReturnList;//刷新控件
    private ImageView ivErrPic;//出错图片
    private TextView tvErrMsg;//出错提示信息
    private ListView lvReturnList;
    private LinearLayout llError;//加载出错时展示布局

    private final int PAGE_SIZE = 10;
    private int current_page = 1;

    private boolean isRefresh = false;

    private List<CashDetailBean> datas = new ArrayList<>();
    private GainCouponAdapter adapter;

    private boolean canLoad = true;

    @Override
    protected void initView()
    {
        setContentView(R.layout.fragment_coupon_list);
        findViewById(R.id.iv_Left_public).setOnClickListener(this);

        tvContent = (TextView) findViewById(R.id.tv_content_public);
        tvContent.setText(" 加息券收益明细");

        refreshReturnList = (RefreshLayout) this.findViewById(R.id.refresh_return_list);
        tvContent = (TextView) findViewById(R.id.tv_content_public);
        refreshReturnList.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue);
        refreshReturnList.setOnRefreshListener(this);
        refreshReturnList.setOnLoadListener(this);
        lvReturnList = (ListView) this.findViewById(R.id.lv_return_list);
        View foot = View.inflate(this, R.layout.header, null);
        lvReturnList.addFooterView(foot);
        adapter = new GainCouponAdapter(this, datas);
        lvReturnList.setAdapter(adapter);
        lvReturnList.setOnItemClickListener(this);

        llError = (LinearLayout) this.findViewById(R.id.linear_error);
        llError.setOnClickListener(this);
        ivErrPic = (ImageView) this.findViewById(R.id.iv_error_pic);
        tvErrMsg = (TextView) this.findViewById(R.id.tv_error_msg);
    }

    @Override
    protected void initData()
    {
        refreshReturnList.post(new Runnable()
        {
            @Override
            public void run()
            {
                refreshReturnList.setRefreshing(true);
            }
        });
        getDetailData();
    }

    /**
     * 获取加息券收益列表详情
     */
    private void getDetailData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            closeRefresh();
            showError(R.mipmap.net_error, "网络异常，请检查！");
            UIUtils.showToast(GainCouponActivity.this, "当前网络不可用，请重试！");
            return;
        }
        Map params = new HashMap();
        params.put("currentPage", current_page);
        params.put("pageSize", PAGE_SIZE);
        HttpManager.getInstance().doPost(ServiceName.COUPONLISTDETAIL, params, new
                HttpCallBack<ServerResponse<BaseListBean<CashDetailBean>>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        closeRefresh();
                        showError(R.mipmap.net_error, "请求异常,点击重试！");
                        ToastUtil.showToast(GainCouponActivity.this, getResources().getString(R.string.dataError));
                        CfLog.i("---"+1);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<BaseListBean<CashDetailBean>> rsp)
                    {
                        closeRefresh();
                        if (rsp == null)
                        {
                            ToastUtil.showToast(GainCouponActivity.this, getResources().getString(R.string.dataError));
                            showError(R.mipmap.net_error, "请求异常,点击重试！");
                            CfLog.i("---"+1);
                            return;
                        }
                        if (!"000000".equals(rsp.getCode()))
                        {
                            showError(R.mipmap.net_error, "请求异常,点击重试！");
//                            ToastUtil.showToast(GainCouponActivity.this, rsp.getMsg());
                            CfLog.i("---"+1);
                            return;
                        }
                        if (rsp.getData() == null)
                        {
                            showError(R.mipmap.net_error, "请求异常,点击重试！");
                            ToastUtil.showToast(GainCouponActivity.this, getResources().getString(R.string.dataError));
                            CfLog.i("---"+1);
                            return;
                        }

                        //数据长度为0
                        if (rsp.getData().getPageTotal() <= 0)
                        {
                            isRefresh = false;
                            showError(R.mipmap.null_data, "暂无数据");
                            return;
                        }
                        hideError();
                        if (isRefresh)
                        {
                            datas.clear();
                        }
                        datas.addAll(rsp.getData().getList());
                        if (datas.size() >= rsp.getData().getPageTotal())
                        {
                            canLoad = false;
                        }
                        adapter.notifyDataSetChanged();
                        isRefresh = false;
                    }
                });
    }



    @Override
    public void onRefresh()
    {
        isRefresh = true;
        current_page = 1;
        getDetailData();
    }

    //展示数据加载出错布局
    private void showError(int errIconId, String errMsg)
    {
        datas.clear();
        adapter.notifyDataSetChanged();
        llError.setVisibility(View.VISIBLE);
        refreshReturnList.setVisibility(View.GONE);
        ivErrPic.setImageResource(errIconId);
        tvErrMsg.setText(errMsg);
    }

    //隐藏数据加载出错布局
    private void hideError()
    {
        llError.setVisibility(View.GONE);
        refreshReturnList.setVisibility(View.VISIBLE);
        tvErrMsg.setText("");
    }

    @Override
    public void onLoad()
    {

        if (canLoad)
        {
            current_page++;
            isRefresh = false;
            refreshReturnList.setLoading(true);
            getDetailData();
        }
        else if (datas.size() > 5)
        {
            UIUtils.showToast(getString(R.string.not_more_data));
        }
    }

    /**
     * 关闭刷新
     */
    private void closeRefresh()
    {
        if (refreshReturnList.isRefreshing())
        {
            refreshReturnList.setRefreshing(false);
        }
        if (refreshReturnList.isLoading())
        {
            refreshReturnList.setLoading(false);
        }
        UIUtils.dimissLoading();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.linear_error://点击错误布局
                hideError();
                UIUtils.showLoading(this);
                getDetailData();
                break;
            case R.id.iv_Left_public://返回
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        Intent intent = new Intent(this, LoanDetailActivity.class);   // 跳转标的详情页
        intent.putExtra("productId", datas.get(position).getProductId());
        startActivity(intent);
    }
}
