package com.mingjie.jf.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.CaseManagerAdapter;
import com.mingjie.jf.bean.CaseManager;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.ToldActionVo;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.JRecyclerView;
import com.mingjie.jf.widgets.QuitDialog;
import com.mingjie.jf.widgets.WrapContentLinearLayoutManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 私房钱管理
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-11-22 10:16
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class CaseManagerActivity extends BaseActivity implements View.OnClickListener, JRecyclerView
        .OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, JRecyclerView.OnItemClickListener, QuitDialog
        .OnEnterListener, QuitDialog.OnCancelListener
{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recharge)
    RelativeLayout recharge;
    @BindView(R.id.case_withdraw)
    Button caseWithdraw;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private final int PAGE_SIZE = 20;//单页请求条数
    @BindView(R.id.linear_error)
    LinearLayout mErrorView;
    @BindView(R.id.iv_error_pic)
    ImageView ivError;
    @BindView(R.id.tv_error_msg)
    TextView tvError;
    @BindView(R.id.tv_count_yuer)
    TextView tvCountYuer;
    @BindView(R.id.tv_freeze_yuer)
    TextView tvFreezeYuer;
    private int currentPage = 1;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerview)
    JRecyclerView mJRecyclerView;

    ToldActionVo vo;
    private int totalCount;
    private List<CaseManager.WalletFlowBean.ListBean> el_bill;
    private List<CaseManager.WalletFlowBean.ListBean> el_Bill = new ArrayList<>();

    CaseManager.WalletBean wallet2;
    private QuitDialog quitDialog;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_case_manager);
        ButterKnife.bind(this);
        tvTitle.setText("私房钱管理");
        recharge.setOnClickListener(this);
        caseWithdraw.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);//添加刷新事件
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);//设置刷新圆圈颜色
        mJRecyclerView.setOnLoadMoreListener(this);//设置加载更多监听
        mJRecyclerView.setOnItemClickListener(this);//设置条目点击监听
        mJRecyclerView.setLoadMoreView(R.layout.refresh_footer);//设置加载更多的布局
        mJRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mJRecyclerView.setAdapter(new CaseManagerAdapter(this, el_Bill));
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

        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
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
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            //提现
            case R.id.case_withdraw:
                if (1 == getIntent().getIntExtra("isFundDepository", 0))
                {

                    if ("1".equals(getIntent().getStringExtra("isbind")))
                    {
                        Intent intent = new Intent(this, CaseWithdrawActivity.class);
                        if (null != wallet2)
                        {
                            intent.putExtra("wallet", wallet2.getAmount());
                        }
                        startActivity(intent);
                    }
                    else if ("0".equals(getIntent().getStringExtra("isbind")))
                    {
                        quitDialog = new QuitDialog(this, "温馨提示", "当前没有绑定银行卡，是否需要绑卡？", "取消", "确定");
                        quitDialog.setEnterListener(this);
                        quitDialog.setCancelListener(this);
                        quitDialog.show();
                    }else {

                    }
                }
                else if (0 == getIntent().getIntExtra("isFundDepository", 0))
                {
                    startActivity(new Intent(this, DepositActivity.class));
                    finish();
                }
                else
                {

                }
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
        getWalletPage();
    }

    private void getWalletPage()
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
        HashMap<String, String> map = new HashMap<>();
        map.put("name", CacheUtils.getString(this, "name"));
        map.put("HHmm", TimeFormatUtil.getSimpleTime());
        map.put("HH", TimeFormatUtil.getHour());
        MobclickAgent.onEvent(this, ServiceName.WALLETFLOWPAGE, map);

        HttpManager.getInstance().doPost(ServiceName.WALLETFLOWPAGE, vo, new HttpCallBack<ServerResponse<CaseManager>>()
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
            public void getHttpCallBack(ServerResponse<CaseManager> rsp)
            {
                if (rsp == null)
                {
                    showError(R.mipmap.net_error, R.string.service_err2);
                }
                else if (!rsp.getCode().equals("000000") || rsp.getData() == null)
                {
                    showError(R.mipmap.net_error, R.string.service_err2);
                }
                else
                {
                    if (null != rsp.getData().getWallet())
                    {
                        showCase(rsp.getData().getWallet());
                    }
                    el_bill = rsp.getData().getWalletFlow().getList();
                    totalCount = rsp.getData().getWalletFlow().getPageTotal();
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
                            CfLog.i("mDatas.size=" + el_Bill.size());
                            mJRecyclerView.getAdapter().notifyDataSetChanged();
                            UIUtils.showToast(CaseManagerActivity.this, "刷新完成");
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
                                CfLog.i("---" + 7);
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

    //展示冻结提现金额
    private void showCase(CaseManager.WalletBean wallet)
    {
        this.wallet2 = wallet;
        CfLog.i("---" + wallet2);
        tvCountYuer.setText(wallet.getAmount() + "");
        tvFreezeYuer.setText(wallet.getFrozenAmount() + "");
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

    private void initParams()
    {
        //初始化参数
        vo.currentPage = currentPage;
        vo.pageSize = PAGE_SIZE;
    }

    @Override
    public void onRefresh()
    {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue, R.color.red);
        mSwipeRefreshLayout.setRefreshing(true);
        currentPage = 1;
        mJRecyclerView.setLoadMore(false);//刷新时候 禁止加载更多
        getWalletPage();
    }

    public void onEvent(Event event)
    {
        //提交申请成功，刷新上一个界面
        if (event.eventType == Event.TIJIAO)
        {
            onRefresh();
        }
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

    @Override
    public void onCancelListener()
    {
        quitDialog.dismiss();
    }

    @Override
    public void onEnterListener()
    {
        ;
        Intent intent = new Intent(this, CaseCardActivity.class);
        intent.putExtra("CaseCardphonenumber", getIntent().getStringExtra("phonenumbertixian"));
        startActivity(intent);
        finish();
    }
}
