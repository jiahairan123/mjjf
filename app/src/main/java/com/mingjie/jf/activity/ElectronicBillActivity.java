package com.mingjie.jf.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.ElectBillAdapter;
import com.mingjie.jf.bean.BillActionVo;
import com.mingjie.jf.bean.Pager;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.JRecyclerView;
import com.mingjie.jf.widgets.WrapContentLinearLayoutManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 电子账单
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ElectronicBillActivity extends FragmentActivity implements View.OnClickListener,
        JRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, JRecyclerView.OnItemClickListener
{

    ImageView ivLeftPublic;//返回
    TextView tvContentPublic;//内容
    @BindView(R.id.recyclerview)
    JRecyclerView mJRecyclerView;
    @BindView(R.id.iv_error_pic)
    ImageView ivError;
    @BindView(R.id.tv_error_msg)
    TextView tvError;
    @BindView(R.id.linear_error)
    LinearLayout mErrorView;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private UserVoValible user;
    private BillActionVo vo;

    private final int PAGE_SIZE = 20;//单页请求条数
    private int currentPage = 1;
    private List<Pager.DataBean.ListBean> el_Bill = new ArrayList<>();
    private int totalCount;
    private List<Pager.DataBean.ListBean> el_bill;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面
        MobclickAgent.onResume(this);       //统计时长
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    protected void initView()
    {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        mJRecyclerView = (JRecyclerView) findViewById(R.id.recyclerview);
        mErrorView = (LinearLayout) findViewById(R.id.linear_error);
        ivError = (ImageView) findViewById(R.id.iv_error_pic);
        tvError = (TextView) findViewById(R.id.tv_error_msg);
        mSwipeRefreshLayout.setOnRefreshListener(this);//添加刷新事件
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);//设置刷新圆圈颜色
        mJRecyclerView.setOnLoadMoreListener(this);//设置加载更多监听
        mJRecyclerView.setOnItemClickListener(this);//设置条目点击监听
        mJRecyclerView.setLoadMoreView(R.layout.refresh_footer);//设置加载更多的布局
        mJRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mJRecyclerView.setAdapter(new ElectBillAdapter(this, el_Bill, mJRecyclerView));
        //加载出问题后，点击重新加载
        mErrorView.setOnClickListener(this);
        //        mErrorView.setOnClickListener(new View.OnClickListener()
        //        {
        //            @Override
        //            public void onClick(View v)
        //            {
        //                mSwipeRefreshLayout.setRefreshing(true);
        //                onRefresh();
        //            }
        //        });

        ivLeftPublic = (ImageView) findViewById(R.id.iv_Left_public);
        tvContentPublic = (TextView) findViewById(R.id.tv_content_public);
        findViewById(R.id.iv_Left_public).setOnClickListener(this);
        user = CacheUtils.getUserinfo(this);
        return;
    }

    protected void initData()
    {
        tvContentPublic.setText("交易记录");
        vo = new BillActionVo();
        //进来首先加载一次数据
        mSwipeRefreshLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.linear_error:
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
                break;
            default:
                break;
        }

    }

    @Override
    public void onLoadMore()
    {
        currentPage++;
        getData();
    }

    private void getData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            el_Bill.clear();
            //这里注意不能用MyInvestAdapter来刷新数据，在JRecyclerView内部对adapter进行了再封装，
            // 只能拿到它内部的adapter才能刷新数据。
            mJRecyclerView.getAdapter().notifyDataSetChanged();
            showError(R.mipmap.net_error, "当前网络不可用，点击重试！");
            if (mSwipeRefreshLayout.isRefreshing())
            {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            return;
        }
        initParams();

        MobclickAgent.onEvent(this, ServiceName.CGKX_FLOW);

        HttpManager.getInstance().doPost(ServiceName.CGKX_FLOW, vo, new HttpCallBack<Pager>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                if (mSwipeRefreshLayout.isRefreshing())
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                showError(R.mipmap.net_error, getResources().getString(R.string.netError));
                //                        UIUtils.showToast(mActivity,"请求异常,点击重试！");
            }

            @Override
            public void getHttpCallBack(Pager rsp)
            {

                if (rsp == null)
                {
                    if (mSwipeRefreshLayout.isRefreshing())
                    {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    showError(R.mipmap.net_error, getResources().getString(R.string.netError));
                    //                            UIUtils.showToast(mActivity,"服务器异常");
                }
                else if (null != rsp.getCode() && !rsp.getCode().equals("000000"))
                {
                    if (mSwipeRefreshLayout.isRefreshing())
                    {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    UIUtils.showToast(ElectronicBillActivity.this, rsp.getMsg());
                    showError(R.mipmap.net_error, getResources().getString(R.string.netError));
                }
                else if (rsp.getData() == null)
                {
                    if (mSwipeRefreshLayout.isRefreshing())
                    {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    showError(R.mipmap.net_error, getResources().getString(R.string.netError));
                    // UIUtils.showToast(mActivity,"服务器异常");
                }
                else
                {
                    el_bill = rsp.getData().getList();
                    totalCount = rsp.getData().getPageTotal();
                    if (mSwipeRefreshLayout.isRefreshing())
                    {
                        CfLog.i("---isRefreshing");
                        mJRecyclerView.setLoadMore(true);//刷新完成  允许加载更多
                        if (el_bill.size() == 0)
                        {
                            showError(R.mipmap.null_data, "暂无数据");
                            //                                    UIUtils.showToast(mActivity, "暂无数据");
                        }
                        else
                        {
                            CfLog.i("---hideError();");
                            hideError();
                            el_Bill.clear();
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            el_Bill.addAll(el_bill);
                            CfLog.i("mDatas.size=" + el_Bill.size());
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            //                                    UIUtils.showToast(mActivity, "刷新完成");
                        }
                        if (el_Bill.size() >= totalCount)// 如果当前没有更多数据，则关闭下拉加载功能
                        {
                            mJRecyclerView.setLoadMore(false);
                        }
                    }
                    if (mJRecyclerView.isLoading())
                    {
                        mJRecyclerView.loadingSuccess();
                        if (el_Bill.size() == 0)
                        {
                            mJRecyclerView.setLoadMore(false);
                        }
                        else
                        {
                            hideError();
                            el_Bill.addAll(el_bill);
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            if (el_Bill.size() >= totalCount)
                            {
                                mJRecyclerView.setLoadMore(false);
                            }
                        }
                    }
                }
                //                if (mSwipeRefreshLayout.isRefreshing())
                //                {
                //                    mSwipeRefreshLayout.setRefreshing(false);
                //                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //隐藏数据加载出错布局
    private void hideError()
    {
        mErrorView.setVisibility(View.GONE);
        mJRecyclerView.setVisibility(View.VISIBLE);
        tvError.setText("");
    }

    private void initParams()
    {
        //初始化参数
        vo.currentPage = currentPage;
        vo.pageSize = PAGE_SIZE;
    }

    @Override
    public void onRefresh()
    {
        CfLog.i("onRefresh");
        currentPage = 1;
        mJRecyclerView.setLoadMore(false);//刷新时候 禁止加载更多
        getData();
    }

    //展示数据加载出错布局
    private void showError(int errIconId, String errMsg)
    {
        CfLog.i("showError" + errMsg);
        mErrorView.setVisibility(View.VISIBLE);
        mJRecyclerView.setVisibility(View.GONE);
        ivError.setImageResource(errIconId);
        tvError.setText(errMsg);
    }

    @Override
    public void onItemClick(int position)
    {

    }
}