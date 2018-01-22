package com.mingjie.jf.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.LoanDetailActivity;
import com.mingjie.jf.adapter.StandardInvestmentAdapter;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.InvestList;
import com.mingjie.jf.bean.ZhaiQuanParams;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.InvestIndicator;
import com.mingjie.jf.widgets.LoadingPager;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 散标投资fragment
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class StandardInvestmentFragment extends BaseInvestFragment implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener, InvestIndicator.OnTabChangeListener, AdapterView.OnItemClickListener
{

    @BindView(R.id.tabs)
    InvestIndicator mTabs;
    @BindView(R.id.listview_investlist)
    ListView mListView;
    @BindView(R.id.swipe_container)
    RefreshLayout mRefreshView;//刷新控件

    private static final int DEFAULT = 0;//默认，最新
    private static final int RATE = 1;//利率
    private static final int LIMIT = 2;//期限
    private static final int MONEY = 3;//金额
    private StandardInvestmentAdapter mAdapter;
    private List<InvestList.DataBean.ListBean> mDatas = new ArrayList<>();  // 散标投资listview的数据

    private int currentPage = 1;                //参数
    private static final int PAGER_TYPE = 2;   // 参数  -  债权转让
    private ZhaiQuanParams params;
    private int pageTotal;                     //总数据条数

    private boolean isRefresh;                 // 是否正在刷新
    private boolean isLoad;                    // 是否正在加载

    private int mCurrentTab = DEFAULT;//当前tab
    private boolean mCurrentSoft;//当前排序方式，降序/升序
    private View foot;

    // FragmentActivity activity;
    private boolean isTest;

    @Override
    protected View onLoadSuccessView()
    {
        View mView = View.inflate(context, R.layout.controller_invest_list, null);
        ButterKnife.bind(this, mView);
        foot = View.inflate(context, R.layout.header, null);
        mAdapter = new StandardInvestmentAdapter(context, mDatas, mListView);
        mListView.addFooterView(foot);      // 不加这段代码在低版本（4.1）会崩溃
        mListView.setAdapter(mAdapter);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setOnLoadListener(this);
        mTabs.setOnTabViewItemClickListener(this);
        mListView.setOnItemClickListener(this);
        // activity = getActivity();
        return mView;
    }

    @Override
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
    protected LoadingPager.LoadedResult onLoadingData()
    {
        if (getArguments() != null)
        {
            isTest = getArguments().getBoolean("is_test", false);
        }
        CfLog.d("StandardInvestmentFragment-istest=" + isTest);
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
        params = getParams();
        String serverName = isTest ? ServiceName.TEST_INVESTMENT_LIST : ServiceName.INVESTMENT_LIST;
        try
        {
            InvestList investList = (InvestList) HttpManager.getInstance().doPost(serverName, params, InvestList.class);
            if (null == investList)
            {
                return LoadingPager.LoadedResult.ERROR;
            }
            else if ("000000".equals(investList.code))
            {
                mDatas = investList.data.list;
                pageTotal = investList.data.pageTotal;
                //                    currentPage += 1;
                CfLog.i("pageTotal ===" + pageTotal);
                CfLog.i("mDatas  === " + mDatas.size());
                return checkData(mDatas);
            }
            else
            {
                return LoadingPager.LoadedResult.ERROR;
            }
        }
        catch (Exception e)
        {
            CfLog.e("访问错误:" + e.getMessage());
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onEvent(Event event)
    {
        //收购债权后，刷新界面
        if (event.eventType == Event.INVEST)
        {
            CfLog.i("散标界面收到收购债权event , 刷新界面");
            getData();
        }
    }

    /**
     * @return 返回债权转让参数
     */
    public ZhaiQuanParams getParams()
    {
        ZhaiQuanParams params = new ZhaiQuanParams();
        params.setPageSize(10);
        params.setCurrentPage(currentPage);
        params.setProductType(PAGER_TYPE);
        switch (mCurrentTab)
        {
            case DEFAULT:
                break;
            case RATE:
            {
                params.setReserve1(!mCurrentSoft ? "profit ASC" : "profit DESC");
            }
            break;
            case LIMIT:
            {
                params.setReserve1(!mCurrentSoft ? "a.repurchase_mode=1||a.repurchase_mode=2,product_deadline ASC" :
                        "a.repurchase_mode=3,product_deadline DESC");
            }
            break;
            case MONEY:
            {
                params.setReserve1(!mCurrentSoft ? "product_amount ASC" : "product_amount DESC");
            }
            break;
            default:
                break;
        }
        return params;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent = new Intent(context, LoanDetailActivity.class);  // 跳转详情页
        intent.putExtra("productId", mDatas.get(i).id);
        startActivity(intent);
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

    public void getData()
    {
        if (!Utilities.canNetworkUseful(getContext()))//如果当前网络不可用
        {
            UIUtils.showToast(context, R.string.network_unused_retry_pls);
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
        // 上拉加载 下拉刷新 调用加载数据的方法
        CfLog.i("currentPage = " + currentPage);
        params = getParams();
        String serverName = isTest ? ServiceName.TEST_INVESTMENT_LIST : ServiceName.INVESTMENT_LIST;
        HttpManager.getInstance().doPost(serverName, params, new HttpCallBack<InvestList>()
        {
            @Override
            public void getHttpCallNull(String str)
            {  // 数据为空
                UIUtils.showToast(context, R.string.dataError);
                closeRefresh();
            }

            @Override
            public void getHttpCallBack(InvestList list)
            {  // 成功
                closeRefresh();
                if (null != list)
                {
                    if ("000000".equals(list.code))
                    {
                        pageTotal = list.data.pageTotal;
                        if (isLoad)
                        {
                            isLoad = false;
                            mDatas.addAll(list.data.list);
                        }
                        else if (isRefresh)
                        {
                            isRefresh = false;
                            mDatas.clear();
                            mDatas.addAll(list.data.list);
                            UIUtils.showToast(context, R.string.refreshed);
                            CfLog.i("pageTotal ===" + pageTotal);
                            CfLog.i("mDatas  === " + mDatas.size());
                        }
                        else
                        {
                            mDatas.clear();
                            mDatas.addAll(list.data.list);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        UIUtils.showToast(context, list.msg);
                    }
                }
                else
                {
                    UIUtils.showToast(context, "数据为空");
                }
            }
        });
    }

    @Override
    public void setOnTabChangeListener(int position, boolean isUpSoft)
    {
        //        clearFile();
        mCurrentSoft = isUpSoft;
        currentPage = 1;
        switch (position)
        {
            case 1:
                mCurrentTab = DEFAULT;
                break;
            case 2:
                mCurrentTab = RATE;
                break;

            case 3:
                mCurrentTab = LIMIT;
                break;
            case 4:
                mCurrentTab = MONEY;
                break;
            default:
                break;
        }

        mRefreshView.setRefreshing(true);
        getData();
    }

    @Override
    public void onLoad()
    {
        // 上拉加载更多
        if (mDatas.size() >= pageTotal)
        {
            mRefreshView.setLoading(false);
            UIUtils.showToast(context, "没有更多数据");
        }
        else
        {
            mRefreshView.setLoading(true);
            currentPage += 1;
            isLoad = true;
            getData();
        }
    }
}
