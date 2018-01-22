package com.mingjie.jf.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.ToldAdapter;
import com.mingjie.jf.bean.AnnounceMent;
import com.mingjie.jf.bean.ToldActionVo;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.SimpleSwipeRefreshLayout;
import com.mingjie.jf.widgets.JRecyclerView;
import com.mingjie.jf.widgets.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 公告列表
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ToldActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        JRecyclerView.OnLoadMoreListener, JRecyclerView.OnItemClickListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.swipe_container)
    SimpleSwipeRefreshLayout mRefreshView;
    @BindView(R.id.iv_error_pic)
    ImageView ivError;
    @BindView(R.id.tv_error_msg)
    TextView tvError;
    @BindView(R.id.linear_error)
    LinearLayout mErrorView;

    private int PAGE_SIZE = 20;//单页请求条数
    private int currentPage = 1;

    private ToldActionVo vo;
    //    private List<AnnounceMent.DataBean> rDetail;
    private JRecyclerView mJRecyclerView;

    private List<AnnounceMent.DataBean.ListBean> rDetail = new ArrayList<>();
    private int totalCount;
    private List<AnnounceMent.DataBean.ListBean> el_bill;
    //    private List<AnnounceMent.DataBean> el_bill;

    protected void initView()
    {
        setContentView(R.layout.activity_told_list);
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("公告列表");
        mJRecyclerView = (JRecyclerView) findViewById(R.id.listview_investlist);
        //加载出问题后，点击重新加载
        mErrorView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mRefreshView.setRefreshing(true);
                onRefresh();
            }
        });
    }

    protected void initData()
    {
        //        mRefreshView.setViewGroup(investlist);
        vo = new ToldActionVo();
        mRefreshView.setOnRefreshListener(this);//添加刷新事件
        mRefreshView.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);//设置刷新圆圈颜色
        mJRecyclerView.setOnLoadMoreListener(this);//设置加载更多监听
        mJRecyclerView.setOnItemClickListener(this);//设置条目点击监听
        mJRecyclerView.setLoadMoreView(R.layout.refresh_footer);//设置加载更多的布局
        mJRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mJRecyclerView.setAdapter(new ToldAdapter(this, rDetail, mJRecyclerView));
        mRefreshView.post(new Runnable()
        {
            @Override
            public void run()
            {
                mRefreshView.setRefreshing(true);
                onRefresh();
            }
        });
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
                break;
        }

    }

    @Override
    public void onRefresh()
    {
        currentPage = 1;
        mJRecyclerView.setLoadMore(false);//刷新时候 禁止加载更多
        getTold();
    }

    private void getTold()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            rDetail.clear();
            mJRecyclerView.getAdapter().notifyDataSetChanged();
            showError(R.mipmap.net_error, "当前网络不可用，点击重试！");
            if (mRefreshView.isRefreshing())
            {
                mRefreshView.setRefreshing(false);
            }
            return;
        }
        initParams();
        HttpManager.getInstance().doPost(ServiceName.CGKX_ANNOUNCELIST, vo, new HttpCallBack<AnnounceMent>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                closeRefresh();
                showError(R.mipmap.net_error, getResources().getString(R.string.dataError2));
            }

            @Override
            public void getHttpCallBack(AnnounceMent rsp)
            {

                if (rsp == null)
                {
                    closeRefresh();
                    showError(R.mipmap.net_error, getResources().getString(R.string.netError));
                    return;
                }
                if (null != rsp.getCode() && !rsp.getCode().equals("000000"))
                {
                    closeRefresh();
                    showError(R.mipmap.net_error, getResources().getString(R.string.netError));
                }
                else
                {
                    if (null != rsp.getData())
                    {
                        if (0 == rsp.getData().getList().size())
                        {
                            mJRecyclerView.setLoadMore(false);
                            showToast("暂无更多数据");
                        }
                    }
                    el_bill = rsp.getData().getList();
                    totalCount = rsp.getData().getPageTotal();
                    CfLog.i("---el_bill"+el_bill);
                    CfLog.i("---el_bill.size()"+totalCount);
                    if (mRefreshView.isRefreshing())
                    {
                        mJRecyclerView.setLoadMore(true);//刷新完成  允许加载更多
                        if (el_bill.size() == 0)
                        {
                            showError(R.mipmap.null_data, "暂无数据");
                        }
                        else
                        {
                            hideError();
                            rDetail.clear();
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            rDetail.addAll(el_bill);
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            UIUtils.showToast(ToldActivity.this, "刷新完成");
                            if (rDetail.size() >= totalCount)// 如果当前没有更多数据，则关闭下拉加载功能
                            {
                                mJRecyclerView.setLoadMore(false);
                            }
//                            if (rDetail.size()<7){
//                                mJRecyclerView.setLoadMore(false);
//                                mRefreshView.setRefreshing(false);
//                                return;
//                            }
                        }
                    }
                    if (mJRecyclerView.isLoading())
                    {
                        mJRecyclerView.loadingSuccess();
                        if (rDetail.size() == 0)
                        {
                            mJRecyclerView.setLoadMore(false);
                        }
                        else
                        {
                            hideError();
                            rDetail.addAll(el_bill);
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            if (rDetail.size() >= totalCount)
                            {
                                CfLog.i("---" + 7);
                                mJRecyclerView.setLoadMore(false);
                            }
                        }
                    }
                }
                mRefreshView.setRefreshing(false);
            }
        });
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

    //展示数据加载出错布局
    private void showError(int errIconId, String errMsg)
    {
        mJRecyclerView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        ivError.setImageResource(errIconId);
        tvError.setText(errMsg);
    }

    /**
     * 关闭刷新
     */
    private void closeRefresh()
    {
        if (mRefreshView.isRefreshing())
        {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(int position)
    {
        Intent intent = new Intent(this, AccountDataActivity.class);
        intent.putExtra("title", rDetail.get(position).getTitle());//公告title
        intent.putExtra("date", rDetail.get(position).getCreatedDate());
        intent.putExtra("id", rDetail.get(position).getId());
        startActivity(intent);
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMore()
    {
        currentPage++;
        CfLog.i("---" + currentPage);
        getTold();
    }
}


