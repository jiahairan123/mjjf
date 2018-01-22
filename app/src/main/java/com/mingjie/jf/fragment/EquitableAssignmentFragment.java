package com.mingjie.jf.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.EquitableDetailActivity;
import com.mingjie.jf.adapter.ZhuanRangAdapter;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.InvestList;
import com.mingjie.jf.bean.ZhaiQuanParams;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
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
 * <p>描   述： 债权转让fragment
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class EquitableAssignmentFragment extends BaseInvestFragment implements RefreshLayout.OnLoadListener,
        SwipeRefreshLayout.OnRefreshListener, InvestIndicator.OnTabChangeListener, AdapterView.OnItemClickListener
{

    @BindView(R.id.tabs)
    InvestIndicator mTabs;
    @BindView(R.id.listview_investlist)
    ListView mListView;
    @BindView(R.id.swipe_container)
    RefreshLayout mRefreshView;                //刷新控件

    private int currentPage = 1;                   //参数
    private static final int PAGER_TYPE = 1;   // 参数  -  债权转让
    private ZhaiQuanParams params;
    private int pageTotal;                     //总数据条数

    private boolean isRefresh;                 // 是否正在刷新
    private boolean isLoad;                    // 是否正在加载
    private View foot;

    private static final int DEFAULT = 0;//默认，最新
    private static final int RATE = 1;//到期净收益率
    private static final int LIMIT = 2;//剩余期数

    private int mCurrentTab = DEFAULT;//当前tab
    private boolean mCurrentSoft;//当前排序方式，降序/升序

    private List<InvestList.DataBean.ListBean> mDatas = new ArrayList<>();  // 债权转让listview的数据
    private ZhuanRangAdapter adapter;

    @Override
    protected View onLoadSuccessView()
    {
        View mView = View.inflate(getContext(), R.layout.controller_invest_list_lend, null);
        ButterKnife.bind(this, mView);
        if (adapter == null)
        {
            adapter = new ZhuanRangAdapter(getActivity(), mDatas, mListView);
        }
        foot = View.inflate(getContext(), R.layout.header, null);
        mListView.addFooterView(foot);
        mListView.setAdapter(adapter);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setOnLoadListener(this);
        mTabs.setOnTabViewItemClickListener(this);
        mListView.setOnItemClickListener(this);
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
        //        return LoadingPager.LoadedResult.EMPTY;
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
        params = getParams();
        try
        {
            InvestList investList = (InvestList) HttpManager.getInstance().doPost(ServiceName.INVESTMENT_LIST,
                    params, InvestList.class);
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

    public void onEvent(Event event)
    {
        // 购买债权，刷新界面
        if (event.eventType == Event.INVEST)
        {
            CfLog.i("债权界面收到收购债权 event , 刷新界面");
            getData();
        }
        // 债权转让 ， 刷新界面
        if (event.eventType == Event.TRANSFER)
        {
            CfLog.i("债权界面收到债权转让 event , 刷新界面");
            getData();
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
                params.setReserve1(!mCurrentSoft ? "1" : "3");
            }
            break;
            case LIMIT:
            {
                params.setReserve1(!mCurrentSoft ? "2" : "4");
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
        Intent intent = new Intent(getContext(), EquitableDetailActivity.class);   // 跳转转让详情页
        intent.putExtra("productId", mDatas.get(i).getId());
        startActivity(intent);
    }

    @Override
    public void onRefresh()
    {   // 下拉刷新
        currentPage = 1;
        mRefreshView.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue, R.color.red);
        mRefreshView.setRefreshing(true);
        // isRefresh = true;
        getData();
    }

    public void getData()
    {  // 上拉加载 下拉刷新 调用加载数据的方法
        CfLog.i("currentPage = " + currentPage);
        params = getParams();
        HttpManager.getInstance().doPost(ServiceName.INVESTMENT_LIST, params, new HttpCallBack<InvestList>()
        {
            @Override
            public void getHttpCallNull(String str)
            {  // 数据为空
                UIUtils.showToast("提交失败！请重试");
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
                            UIUtils.showToast("刷新完成");
                            CfLog.i("pageTotal ===" + pageTotal);
                        }
                        else
                        {
                            mDatas.clear();
                            mDatas.addAll(list.data.list);
                        }
                        if (adapter == null)
                        {
                            adapter = new ZhuanRangAdapter(getActivity(), mDatas, mListView);
                        }
                    }
                    else
                    {
                        UIUtils.showToast(list.msg);
                    }
                }
                else
                {
                    UIUtils.showToast("数据为空");
                }
            }
        });
    }

    @Override
    public void setOnTabChangeListener(int position, boolean isUpSoft)
    {
        mCurrentSoft = isUpSoft;
        currentPage = 1;
        switch (position)
        {
            case 1:
                mCurrentTab = DEFAULT;    // 最新
                break;
            case 2:
                mCurrentTab = RATE;       // 到期净收益率
                break;
            case 3:
                mCurrentTab = LIMIT;      // 剩余期数
                break;
            default:
                break;
        }
        mRefreshView.setRefreshing(true);
        getData();
    }

    @Override
    public void onLoad()
    {     // 上拉加载更多
        if (mDatas.size() >= pageTotal)
        {
            mRefreshView.setLoading(false);
            UIUtils.showToast("没有更多数据");
        }
        else
        {
            mRefreshView.setLoading(true);
            currentPage += 1;
            // isLoad = true;
            getData();
        }
    }
}