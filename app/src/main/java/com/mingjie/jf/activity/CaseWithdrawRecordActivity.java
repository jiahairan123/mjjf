package com.mingjie.jf.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.WithDrawRecordAdapter;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.ToldActionVo;
import com.mingjie.jf.bean.WithDrawRecord;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.JRecyclerView;
import com.mingjie.jf.widgets.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 提现记录
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-11-22 10:16
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class CaseWithdrawRecordActivity extends BaseActivity implements View.OnClickListener, JRecyclerView
        .OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, JRecyclerView.OnItemClickListener
{
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.recyclerview)
    JRecyclerView mJRecyclerView;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.iv_error_pic)
    ImageView ivError;
    @BindView(R.id.tv_error_msg)
    TextView tvError;
    @BindView(R.id.linear_error)
    LinearLayout mErrorView;
    private ToldActionVo vo;
    private int currentPage = 1;
    private final int PAGE_SIZE = 8;//单页请求条数
    private int totalCount;
    private List<WithDrawRecord.ListBean> el_bill;
    private List<WithDrawRecord.ListBean> el_Bill = new ArrayList<>();

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_case_withdraw);
        ButterKnife.bind(this);
        tvContentPublic.setText("提现记录");
        ivLeftPublic.setOnClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);//添加刷新事件
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);//设置刷新圆圈颜色
        mJRecyclerView.setOnLoadMoreListener(this);//设置加载更多监听
        mJRecyclerView.setOnItemClickListener(this);//设置条目点击监听
        mJRecyclerView.setLoadMoreView(R.layout.refresh_footer);//设置加载更多的布局
        mJRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mJRecyclerView.setAdapter(new WithDrawRecordAdapter(this, el_Bill));
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

    }

    @Override
    protected void initData()
    {
        //        if (!EventBus.getDefault().isRegistered(this))
        //        {
        //            EventBus.getDefault().register(this);
        //        }
        vo = new ToldActionVo();
        //进来首先加载一次数据
        mSwipeRefreshLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });

    }

    //    public void onEventMainThread(Event event)
    //    {
    //        if (event.eventType == Event.CHEXIAO)
    //        {
    //            onRefresh();
    //        }
    //    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //        if (EventBus.getDefault().isRegistered(this))
        //        {
        //            EventBus.getDefault().unregister(this);
        //        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            default:
        }
    }

    @Override
    public void onItemClick(int position)
    {

    }

    @Override
    public void onLoadMore()
    {
        currentPage++;
        getWithdrawRecord();
    }

    @Override
    public void onRefresh()
    {
        currentPage = 1;
        mJRecyclerView.setLoadMore(false);//刷新时候 禁止加载更多
        getWithdrawRecord();
    }

    private void getWithdrawRecord()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            el_Bill.clear();
            mJRecyclerView.getAdapter().notifyDataSetChanged();
            showError(R.mipmap.net_error, "当前网络不可用，点击重试！");
            if (mSwipeRefreshLayout.isRefreshing())
            {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            return;
        }
        initParams();
        HttpManager.getInstance().doPost(ServiceName.WITHDRAWALSFLOWPAGE, vo, new
                HttpCallBack<ServerResponse<WithDrawRecord>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        if (mSwipeRefreshLayout.isRefreshing())
                        {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        showError(R.mipmap.net_error, getResources().getString(R.string.dataError2));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<WithDrawRecord> rsp)
                    {
                        if (rsp == null)
                        {
                            showError(R.mipmap.net_error, R.string.service_err2);
                        }
                        else if (!rsp.getCode().equals("000000"))
                        {
                            showError(R.mipmap.net_error, R.string.service_err2);
                            // UIUtils.showToast(rsp.getMsg());
                        }
                        else if (rsp.getCode().equals("000000"))
                        {
                            if (null != rsp.getData())
                            {
                                el_bill = rsp.getData().getList();
                                totalCount = rsp.getData().getPageTotal();
                                if (mSwipeRefreshLayout.isRefreshing())
                                {
                                    mJRecyclerView.setLoadMore(true);//刷新完成  允许加载更多
                                    if (el_bill.size() == 0)
                                    {
                                        showError(R.mipmap.null_data, "暂无数据");
                                    }
                                    else
                                    {
                                        hideError();
                                        el_Bill.clear();
                                        mJRecyclerView.getAdapter().notifyDataSetChanged();
                                        el_Bill.addAll(el_bill);
                                        CfLog.i("size=" + el_Bill.size());
                                        mJRecyclerView.getAdapter().notifyDataSetChanged();
                                        UIUtils.showToast(CaseWithdrawRecordActivity.this, "刷新完成");
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
                            else
                            {
                                showError(R.mipmap.net_error, R.string.service_err2);
                            }
                        }

                        mSwipeRefreshLayout.setRefreshing(false);
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
        CfLog.i("showError: " + errMsg);
        mErrorView.setVisibility(View.VISIBLE);
        mJRecyclerView.setVisibility(View.GONE);
        ivError.setImageResource(errIconId);
        tvError.setText(errMsg);
    }

    private void initParams()
    {
        //初始化参数
        vo.currentPage = currentPage;
        vo.pageSize = PAGE_SIZE;
    }

    //隐藏数据加载出错布局
    private void hideError()
    {
        mErrorView.setVisibility(View.GONE);
        mJRecyclerView.setVisibility(View.VISIBLE);
        tvError.setText("");
    }
}
