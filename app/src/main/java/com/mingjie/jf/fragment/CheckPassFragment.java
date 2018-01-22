package com.mingjie.jf.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.LoanDetailActivity;
import com.mingjie.jf.adapter.CheckAdapter;
import com.mingjie.jf.bean.CheckBean;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.LoadingPager;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.fragment
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 个人中心 - 我的借款 - 审核通过fragment
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-27 18:13
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CheckPassFragment extends BaseInvestFragment implements RefreshLayout.OnLoadListener, SwipeRefreshLayout
        .OnRefreshListener, AdapterView.OnItemClickListener
{

    @BindView(R.id.listview_investlist)
    ListView mListView;
    @BindView(R.id.swipe_container)
    RefreshLayout mRefreshView;                //刷新控件

    private int currentPage = 1;                   //参数
    private int pageTotal;                     //总数据条数

    private boolean isRefresh;                 // 是否正在刷新
    private boolean isLoad;                    // 是否正在加载
    private View foot;

    private List<CheckBean.DataBean.ListBean> mDatas = new ArrayList<>();  // 还款中listview的数据
    private CheckAdapter adapter;

    @Override
    protected View onLoadSuccessView()
    {
        View mView = View.inflate(context, R.layout.fragment_repay_list, null);
        ButterKnife.bind(this, mView);
        adapter = new CheckAdapter(context, mDatas, mListView);
        foot = View.inflate(context, R.layout.header, null);
        mListView.addFooterView(foot);
        mListView.setAdapter(adapter);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setOnLoadListener(this);
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
        CfLog.i("onLoadingData()");
        try
        {
            Map<String, Integer> params = new HashMap<>();
            params.put("pageSize", 10);
            params.put("currentPage", 1);
            CheckBean checkBean = (CheckBean) HttpManager.getInstance().doPost(ServiceName.BORROW_PRODUCTAPPROVED,
                    params, CheckBean.class);
            if (null == checkBean)
            {
                return LoadingPager.LoadedResult.ERROR;
            }
            else if ("000000".equals(checkBean.code))
            {
                mDatas = checkBean.data.list;
                pageTotal = checkBean.data.pageTotal;
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent = new Intent(getActivity(), LoanDetailActivity.class);
        intent.putExtra("productId", mDatas.get(i).id);
        startActivity(intent);
    }

    @Override
    public void onLoad()
    {
        // 上拉加载更多
        if (mDatas.size() >= pageTotal)
        {
            mRefreshView.setLoading(false);
            UIUtils.showToast("没有更多数据");
        }
        else
        {
            mRefreshView.setLoading(true);
            currentPage += 1;
            isLoad = true;
            getData();
        }
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
        if (!Utilities.canNetworkUseful(getActivity()))//如果当前网络不可用
        {
            UIUtils.showToast("当前网络不可用，请重试！");
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
        Map<String, Integer> params = new HashMap<>();
        params.put("pageSize", 10);
        params.put("currentPage", currentPage);

        HttpManager.getInstance().doPost(ServiceName.BORROW_PRODUCTAPPROVED, params, new HttpCallBack<CheckBean>()
        {
            @Override
            public void getHttpCallNull(String str)
            {  // 数据为空
                UIUtils.showToast("提交失败！请重试");
                closeRefresh();
            }

            @Override
            public void getHttpCallBack(CheckBean list)
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
                            CfLog.i("mDatas  === " + mDatas.size());
                        }
                        else
                        {
                            mDatas.clear();
                            mDatas.addAll(list.data.list);
                        }
                        adapter.notifyDataSetChanged();
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
}
