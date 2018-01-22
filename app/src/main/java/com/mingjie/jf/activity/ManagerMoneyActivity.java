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
import com.mingjie.jf.adapter.ManagerMoneyAdapter;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.Pager;
import com.mingjie.jf.bean.ToldActionVo;
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
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 提现管理记录
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ManagerMoneyActivity extends BaseActivity implements View.OnClickListener,
        JRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, JRecyclerView.OnItemClickListener
{
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

    private TextView tvContentPublic;
    private ImageView ivLeftpublic;
    private TextView tvrightpublic;
    private ToldActionVo vo;

    List<Pager.DataBean.ListBean> mDatas;
    private ManagerMoneyAdapter mAdapter;

    private final int PAGE_SIZE = 10;//单页请求条数
    private int currentPage = 1;
    private List<Pager.DataBean.ListBean> el_Bill = new ArrayList<>();
    private int totalCount;
    private List<Pager.DataBean.ListBean> el_bill;

//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cash_manager);
//        ButterKnife.bind(this);
//        initview();
//        initData();
//    }

//    private void initData()
//    {
//        EventBus.getDefault().register(this);
//        tvContentPublic.setText("提现管理");
//        tvrightpublic.setText("提现");
//        tvrightpublic.setTextSize(15);
//        vo = new ToldActionVo();
//        //进来首先加载一次数据
//        mSwipeRefreshLayout.post(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                mSwipeRefreshLayout.setRefreshing(true);
//                onRefresh();
//            }
//        });
//    }

    public void onEvent(Event event)
    {
        if (event.eventType == Event.WITHDRAW)
        {
            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

//    private void initview()
//    {
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
//        mJRecyclerView = (JRecyclerView) findViewById(R.id.recyclerview);
//        mErrorView = (LinearLayout) findViewById(R.id.linear_error);
//        ivError = (ImageView) findViewById(R.id.iv_error_pic);
//        tvError = (TextView) findViewById(R.id.tv_error_msg);
//        mSwipeRefreshLayout.setOnRefreshListener(this);//添加刷新事件
//        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);//设置刷新圆圈颜色
//        mJRecyclerView.setOnLoadMoreListener(this);//设置加载更多监听
//
//        mJRecyclerView.setOnItemClickListener(this);//设置条目点击监听
//
//        mJRecyclerView.setLoadMoreView(R.layout.refresh_footer);//设置加载更多的布局
//        mJRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mJRecyclerView.setAdapter(new ManagerMoneyAdapter(this, el_Bill, mJRecyclerView));
//        //加载出问题后，点击重新加载
//        mErrorView.setOnClickListener(this);
//        tvContentPublic = (TextView) findViewById(R.id.tv_content_public);
//        findViewById(R.id.iv_Left_public).setOnClickListener(this);
//        tvrightpublic = (TextView) findViewById(R.id.tv_right_public);
//        tvrightpublic.setOnClickListener(this);
//        tvrightpublic.setVisibility(View.VISIBLE);
//    }

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
            case R.id.tv_right_public:
                //跳转充值界面
                Intent recharge = new Intent(this, RechargeActivity.class);
                recharge.putExtra("recharge", "提现");
                startActivity(recharge);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadMore()
    {
        currentPage++;
        CfLog.i("---" + currentPage);
        getManagerData();

    }

    private void getManagerData()
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
        HttpManager.getInstance().doPost(ServiceName.CGKX_WITHDRAWLIST, vo, new HttpCallBack<Pager>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                if (mSwipeRefreshLayout.isRefreshing())
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                showError(R.mipmap.net_error, R.string.dataError2);
            }

            @Override
            public void getHttpCallBack(Pager rsp)
            {
                if (rsp == null || rsp.getData() == null)
                {
                    showError(R.mipmap.net_error, R.string.service_err2);
                }
                else if (!rsp.getCode().equals("000000"))
                {
                    showError(R.mipmap.net_error, R.string.service_err2);
                }
                else
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
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            UIUtils.showToast(ManagerMoneyActivity.this, "刷新完成");
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
                mSwipeRefreshLayout.setRefreshing(false);
                //                }
            }
        });
    }

    @Override
    public void onRefresh()
    {
        currentPage = 1;
        mJRecyclerView.setLoadMore(false);//刷新时候 禁止加载更多
        getManagerData();
    }

    private void showError(int errIconId, int sId)
    {
        CfLog.d("showError:" + getResources().getString(sId));
        showError(errIconId, getResources().getString(sId));
    }

    //展示数据加载出错布局
    private void showError(int errIconId, String msg)
    {
        CfLog.d("showError: " + msg);
        mErrorView.setVisibility(View.VISIBLE);
        mJRecyclerView.setVisibility(View.GONE);
        ivError.setImageResource(errIconId);
        tvError.setText(msg);
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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_cash_manager);
        ButterKnife.bind(this);
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
        mJRecyclerView.setAdapter(new ManagerMoneyAdapter(this, el_Bill, mJRecyclerView));
        //加载出问题后，点击重新加载
        mErrorView.setOnClickListener(this);
        tvContentPublic = (TextView) findViewById(R.id.tv_content_public);
        findViewById(R.id.iv_Left_public).setOnClickListener(this);
        tvrightpublic = (TextView) findViewById(R.id.tv_right_public);
        tvrightpublic.setOnClickListener(this);
        tvrightpublic.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData()
    {
        EventBus.getDefault().register(this);
        tvContentPublic.setText("提现管理");
        tvrightpublic.setText("提现");
        tvrightpublic.setTextSize(15);
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

    @Override
    public void onItemClick(int position)
    {

    }
}
