package com.mingjie.jf.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.EquitableDetailActivity;
import com.mingjie.jf.activity.LoanDetailActivity;
import com.mingjie.jf.adapter.MyInvestAdapter;
import com.mingjie.jf.bean.MyInvestResponse;
import com.mingjie.jf.bean.MyInvestVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.InvestIndicator;
import com.mingjie.jf.widgets.JRecyclerView;
import com.mingjie.jf.widgets.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 我的账户-我的投资fragment
 * <p>创 建 人：hexiangjun
 * <p>创建时间： 2016/9/20
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class MyInvestRecordFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        InvestIndicator.OnTabChangeListener, JRecyclerView.OnLoadMoreListener, JRecyclerView.OnItemClickListener
{
    private final int PAGE_SIZE = 10;//单页请求条数
    private int currentPage = 1;
    private int totalCount;//条目总数
    private String currentTab = "1";//当前选中的tab，String作为参数传给服武器
    private String currentSort = "1";//当前排序 0升序 1降序

    private List<MyInvestResponse.ItemMyInvest> mDatas = new ArrayList<>();
    private MyInvestVo mInvestVo;//网络请求对象

    private InvestIndicator mTab;
    private SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新控件
    private JRecyclerView mJRecyclerView;//带加载功能的recyclerview
    private View mErrorView;//加载出问题时的布局
    private ImageView ivError;//错误提示图片
    private TextView tvError;//错误提示信息

    /**
     * @param status 状态，用来区分是还款中0、招标中1，还是已完结2
     */
    public static MyInvestRecordFragment newInstance(int status)
    {
        MyInvestRecordFragment fragment = new MyInvestRecordFragment();
        Bundle arg = new Bundle();
        arg.putInt("status", status);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    protected View initView()
    {
        View mView = View.inflate(mActivity, R.layout.controller_account_list_repay, null);
        mTab = (InvestIndicator) mView.findViewById(R.id.tabs);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swiperefreshlayout);
        mJRecyclerView = (JRecyclerView) mView.findViewById(R.id.recyclerview);
        mErrorView = mView.findViewById(R.id.linear_error);
        ivError = (ImageView) mView.findViewById(R.id.iv_error_pic);
        tvError = (TextView) mView.findViewById(R.id.tv_error_msg);

        mTab.setOnTabViewItemClickListener(this);//添加tab点击事件
        mSwipeRefreshLayout.setOnRefreshListener(this);//添加刷新事件
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);//设置刷新圆圈颜色
        mJRecyclerView.setOnLoadMoreListener(this);//设置加载更多监听
        mJRecyclerView.setLoadMoreView(R.layout.refresh_footer);//设置加载更多的布局
        mJRecyclerView.setOnItemClickListener(this);//设置条目点击监听
        mJRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mJRecyclerView.setAdapter(new MyInvestAdapter(mActivity, mDatas, getArguments().getInt("status"),
                mJRecyclerView));
        //加载出问题后，点击重新加载
        mErrorView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });

        return mView;
    }

    @Override
    protected void initData()
    {
        mInvestVo = new MyInvestVo();
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
    public void onRefresh()
    {
        CfLog.i("onRefresh");
        currentPage = 1;
        mJRecyclerView.setLoadMore(false);//刷新时候 禁止加载更多
        getData();
    }

    @Override
    public void onLoadMore()
    {
        currentPage++;
        getData();
    }

    @Override
    public void setOnTabChangeListener(int position, boolean isUpSoft)
    {
        mJRecyclerView.setLoadMore(true);
        this.currentTab = position + "";
        this.currentSort = isUpSoft ? "1" : "0";
        if (position == 1)
        {
            this.currentSort = "1";
        }
        mSwipeRefreshLayout.setRefreshing(true);
        mDatas.clear();//在切换tab时清空集合里面内容
        mJRecyclerView.getAdapter().notifyDataSetChanged();
        onRefresh();
    }

    /**
     * 获取数据
     */
    private void getData()
    {
        if (!Utilities.canNetworkUseful(mActivity))//如果当前网络不可用
        {
            mDatas.clear();
            //这里注意不能用MyInvestAdapter来刷新数据，在JRecyclerView内部对adapter进行了再封装，
            // 只能拿到它内部的adapter才能刷新数据。
            mJRecyclerView.getAdapter().notifyDataSetChanged();
            ToastUtil.showToast(mActivity, "当前网络不可用，请检查！");
            showError(R.mipmap.net_error, "当前网络不可用，点击重试！");
            if (mSwipeRefreshLayout.isRefreshing())
            {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            return;
        }
        initParams();
        HttpManager.getInstance().doPost(ServiceName.CGKX_MYINVESTMENT, mInvestVo, new
                HttpCallBack<ServerResponse<MyInvestResponse>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                if (mSwipeRefreshLayout.isRefreshing())
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if(isAdded())
                {
                    showError(R.mipmap.net_error, getContext().getResources().getString(R.string.dataError));
                }
                //  UIUtils.showToast(mActivity,"请求异常,点击重试！");
            }

            @Override
            public void getHttpCallBack(ServerResponse<MyInvestResponse> rsp)
            {
                if (rsp == null)
                {
                    showError(R.mipmap.net_error, "请求异常,点击重试！");
                    //      UIUtils.showToast(mActivity,"服务器异常");
                }
                else if (null != rsp.getCode() && !rsp.getCode().equals("000000"))
                {
                    showError(R.mipmap.net_error, rsp.getMsg());
                    //      UIUtils.showToast(mActivity,"服务器异常");
                }
                else
                {
                    MyInvestResponse myInvest = rsp.getData();
                    totalCount = myInvest.pageTotal;
                    CfLog.i("totalCount=" + totalCount);
                    if (mSwipeRefreshLayout.isRefreshing())
                    {
                        mJRecyclerView.setLoadMore(true);//刷新完成  允许加载更多
                        if (myInvest.list == null || myInvest.list.isEmpty())
                        {
                            showError(R.mipmap.null_data, "暂无数据");
                            // UIUtils.showToast(mActivity, "暂无数据");
                        }
                        else
                        {
                            hideError();
                            mDatas.clear();
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            mDatas.addAll(myInvest.list);
                            CfLog.i("size=" + mDatas.size());
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            UIUtils.showToast(mActivity, "刷新完成");
                        }
                        if (mDatas.size() >= totalCount)// 如果当前没有更多数据，则关闭下拉加载功能
                        {
                            mJRecyclerView.setLoadMore(false);
                        }
                    }
                    if (mJRecyclerView.isLoading())
                    {
                        mJRecyclerView.loadingSuccess();
                        if (myInvest.list.size() == 0)
                        {
                            mJRecyclerView.setLoadMore(false);
                        }
                        else
                        {
                            hideError();
                            mDatas.addAll(myInvest.list);
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            if (mDatas.size() >= totalCount)
                            {
                                mJRecyclerView.setLoadMore(false);
                            }
                        }
                    }
                }
                if (mSwipeRefreshLayout.isRefreshing())
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    //初始化参数
    private void initParams()
    {
        mInvestVo.currentPage = currentPage;
        mInvestVo.status = getArguments().getInt("status") + "";
        mInvestVo.pageSize = PAGE_SIZE;
        mInvestVo.reserve1 = currentTab;
        mInvestVo.reserve2 = currentSort;
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

    //隐藏数据加载出错布局
    private void hideError()
    {
        mErrorView.setVisibility(View.GONE);
        mJRecyclerView.setVisibility(View.VISIBLE);
        tvError.setText("");
    }

    @Override
    public void onItemClick(int position)
    {
        if (mDatas.get(position).productType == 1)
        {
            Intent intent = new Intent(mActivity, EquitableDetailActivity.class);
            intent.putExtra("productId", mDatas.get(position).productId);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(mActivity, LoanDetailActivity.class);
            intent.putExtra("productId", mDatas.get(position).productId);
            startActivity(intent);
        }
    }
}
