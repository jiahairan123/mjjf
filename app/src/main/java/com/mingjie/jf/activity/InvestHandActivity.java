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
import com.mingjie.jf.adapter.InvestHandAdapter;
import com.mingjie.jf.bean.BaseListBean;
import com.mingjie.jf.bean.InvestHandBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我的投资 -  投资记录activity
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-29 09:53
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class InvestHandActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener, RefreshLayout.OnLoadListener, AdapterView.OnItemClickListener
{

    @BindView(R.id.iv_Left_public)
    ImageView iv_Left_public;           // 返回键
    @BindView(R.id.tv_content_public)
    TextView tv_content_public;         //title
    @BindView(R.id.swipe_container)
    RefreshLayout mRefreshView;      //刷新控件
    @BindView(R.id.listview_investlist)
    ListView listview_investlist;

    @BindView(R.id.linear_error)
    LinearLayout linear_error;      // 没有数据界面布局
    @BindView(R.id.iv_error_pic)
    ImageView iv_error_pic;         // 没有数据显示的图片
    @BindView(R.id.tv_error_msg)
    TextView tv_error_msg;          // 没有数据显示的文字

    private int pageTotal;                     //总数据条数
    private int currentPage = 1;

    private boolean isRefresh;                 // 是否正在刷新
    private boolean isLoad;                    // 是否正在加载
    private View foot;

    private List<InvestHandBean> mDatas = new ArrayList<>();
    private InvestHandAdapter adapter;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_invest_hand);
        ButterKnife.bind(this);
        iv_Left_public.setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setOnLoadListener(this);
        linear_error.setOnClickListener(this);
        listview_investlist.setOnItemClickListener(this);
    }

    /**
     * 显示空界面
     */
    private void showErrorPage(int image, String str)
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(InvestHandActivity.this, LoanDetailActivity.class);
        intent.putExtra("productId", mDatas.get(position).productId);
        startActivity(intent);
    }

    @Override
    protected void initData()
    {
        UIUtils.showLoading(this);
        tv_content_public.setText("投资记录");
        Map params = new HashMap<>();
        params.put("pageSize", 10);
        params.put("currentPage", currentPage);
        params.put("userId", CacheUtils.getUserinfo(this).getId());
        HttpManager.getInstance().doPost(ServiceName.CGKX_MYINVESTMENTRECORD, params, new
                HttpCallBack<ServerResponse<BaseListBean<InvestHandBean>>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {   // 数据为空
                showErrorPage(R.mipmap.net_error, getResources().getString(R.string.dataError));
                UIUtils.dimissLoading();
                showToast(R.string.dataError);
            }

            @Override
            public void getHttpCallBack(ServerResponse<BaseListBean<InvestHandBean>> list)
            {   // 成功
                UIUtils.dimissLoading();
                if (null != list)
                {
                    if ("000000".equals(list.getCode()))
                    {
                        hintErrorPage();
                        pageTotal = list.getData().pageTotal;
                        mDatas = list.getData().list;
                        adapter = new InvestHandAdapter(InvestHandActivity.this, mDatas, listview_investlist);
                        foot = View.inflate(InvestHandActivity.this, R.layout.header, null);
                        listview_investlist.addFooterView(foot);
                        listview_investlist.setAdapter(adapter);
                        if (mDatas.size() == 0)
                        {
                            showErrorPage(R.mipmap.nodata, "数据为空");
                        }
                    }
                    else
                    {
                        showErrorPage(R.mipmap.net_error, "服务器异常");
                        showToast(list.getMsg());
                    }
                }
                else
                {
                    showErrorPage(R.mipmap.nodata, "数据为空");
                    UIUtils.showToast(getBaseContext(), R.string.service_err);
                }
            }
        });
    }

    private void getData()
    {
        if (!Utilities.canNetworkUseful(InvestHandActivity.this))//如果当前网络不可用
        {
            showToast("当前网络不可用，请重试！");
            if (isLoad)
            {
                isLoad = false;
                mRefreshView.setLoading(false);
            }
            else if (isRefresh)
            {
                isRefresh = false;
                closeRefresh();
            }
            return;
        }
        Map params = new HashMap<>();
        params.put("pageSize", 10);
        params.put("currentPage", currentPage);
        params.put("userId", CacheUtils.getUserinfo(this).getId());
        HttpManager.getInstance().doPost(ServiceName.CGKX_MYINVESTMENTRECORD, params, new
                HttpCallBack<ServerResponse<BaseListBean<InvestHandBean>>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {  // 数据为空
                showToast("提交失败！请重试");
                closeRefresh();
            }

            @Override
            public void getHttpCallBack(ServerResponse<BaseListBean<InvestHandBean>> list)
            {  // 成功
                closeRefresh();
                if (null != list)
                {
                    if ("000000".equals(list.getCode()))
                    {
                        pageTotal = list.getData().pageTotal;
                        if (isLoad)
                        {
                            isLoad = false;
                            mDatas.addAll(list.getData().getList());
                        }
                        else if (isRefresh)
                        {
                            isRefresh = false;
                            mDatas.clear();
                            mDatas.addAll(list.getData().list);
                            showToast("刷新完成");
                        }
                        else
                        {
                            mDatas.clear();
                            mDatas.addAll(list.getData().list);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        showToast(list.getMsg());
                    }
                }
                else
                {
                    showToast("数据为空");
                }
            }
        });
    }

    @Override
    public void onRefresh()
    {
        // 下拉刷新
        currentPage = 1;
        mRefreshView.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue, R.color.red);
        mRefreshView.setRefreshing(true);
        isRefresh = true;
        getData();
    }

    @Override
    public void onLoad()
    {
        // 上拉加载更多
        if (mDatas.size() >= pageTotal)
        {
            mRefreshView.setLoading(false);
            showToast("没有更多数据");
        }
        else
        {
            mRefreshView.setLoading(true);
            currentPage += 1;
            isLoad = true;
            getData();
        }
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
