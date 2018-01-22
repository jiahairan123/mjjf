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
import com.mingjie.jf.adapter.BuyTransferAdapter;
import com.mingjie.jf.adapter.TransferingAdapter;
import com.mingjie.jf.adapter.UserTransferAdapter;
import com.mingjie.jf.bean.AccountDebtBean;
import com.mingjie.jf.bean.EquitableBuyBean;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.TransferingBean;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.InvestIndicator;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 用户中心 - 债权转让
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class EquitableActivity extends BaseActivity implements RefreshLayout.OnLoadListener, SwipeRefreshLayout
        .OnRefreshListener, InvestIndicator.OnTabChangeListener, AdapterView.OnItemClickListener, View.OnClickListener
{

    private static final int DEFAULT = 0;//可转让
    private static final int RATE = 1;//转让中
    private static final int LIMIT = 2;//已转出
    private static final int MONEY = 3;//已购买

    private ImageView ivLeftPublic;//返回
    private TextView tvContentPublic;//内容
    private InvestIndicator mTabs;
    private RefreshLayout mRefreshView;
    private ListView listview_investlist;

    private boolean isRefresh;                 // 是否正在刷新
    private boolean isLoad;                    // 是否正在加载

    private int currentPage = 1;               //参数

    private Map params;
    private int pageTotal;                     //总数据条数

    private LinearLayout linear_error;  // 错误布局
    private ImageView iv_error_pic;  // 错误图片
    private TextView tv_error_msg;   // 错误描述

    private List<AccountDebtBean.DataBean.BidPagerBean.ListBean> mDatas = new ArrayList<>();  // 债权转让 - 可转让 -
    // listview的数据
    private List<TransferingBean.DataBean.ReceiptTransferPagerBean.ListBean> mDatas2 = new ArrayList<>();  // 债权转让 -
    // 转让中 && 已转出 -
    private List<EquitableBuyBean.DataBean.BidPagerBean.ListBean> mDatas3 = new ArrayList<>();  // 债权转让 - 已购买
    // listview的数据
    private UserTransferAdapter userTransferAdapter;  // 可转让 adapter
    private TransferingAdapter transferingAdapter;  // 转让中 已转出 adapter
    private BuyTransferAdapter buyTransferAdapter;  // 已购买 adapter

    private int mCurrentTab = DEFAULT;//当前tab
    private int searchType;     // 转让类型
    private View foot;

    protected void initView()
    {
        setContentView(R.layout.fragment_ivest_notitle);
        foot = View.inflate(this, R.layout.header, null);
        ivLeftPublic = (ImageView) findViewById(R.id.iv_Left_public);
        tvContentPublic = (TextView) findViewById(R.id.tv_content_public);
        ivLeftPublic = (ImageView) findViewById(R.id.iv_Left_public);

        linear_error = (LinearLayout) findViewById(R.id.linear_error);
        iv_error_pic = (ImageView) findViewById(R.id.iv_error_pic);
        tv_error_msg = (TextView) findViewById(R.id.tv_error_msg);

        mTabs = (InvestIndicator) findViewById(R.id.tabs);
        mRefreshView = (RefreshLayout) findViewById(R.id.swipe_container);
        listview_investlist = (ListView) findViewById(R.id.listview_investlist);
        listview_investlist.addFooterView(foot);
        tvContentPublic.setText("债权转让");
        mTabs.hideArrow();
        ivLeftPublic.setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setOnLoadListener(this);
        mTabs.setOnTabViewItemClickListener(this);
        listview_investlist.setOnItemClickListener(this);

        iv_error_pic.setOnClickListener(this);
    }

    protected void initData()
    {
        UIUtils.showLoading(this);
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
        getDataForNet1();   // 可转让
    }

    public void onEvent(Event event)
    {
        //收购债权后，刷新界面
        if (event.eventType == Event.TRANSFER)
        {
            currentPage = 1;
            CfLog.i("账户 - 债权转让收到event , 刷新界面");
            switch (searchType)
            {
                case 1:
                    UIUtils.showLoading(this);
                    getDataForNet1();
                    break;
                case 2:
                    UIUtils.showLoading(this);
                    getDataForNet2();
                    break;
                case 3:
                    UIUtils.showLoading(this);
                    getDataForNet2();
                    break;
                case 4:
                    UIUtils.showLoading(this);
                    getDataForNet3();
                    break;
                default:
                    break;
            }
        }
        if (event.eventType == Event.CHEXIAO_TRANSFER)
        {
            currentPage = 1;
            CfLog.i("账户 - 债权收到 撤销 event , 刷新界面");
            switch (searchType)
            {
                case 1:
                    UIUtils.showLoading(this);
                    getDataForNet1();
                    break;
                case 2:
                    UIUtils.showLoading(this);
                    getDataForNet2();
                    break;
                case 3:
                    UIUtils.showLoading(this);
                    getDataForNet2();
                    break;
                case 4:
                    UIUtils.showLoading(this);
                    getDataForNet3();
                    break;
                default:
                    break;
            }
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
     * 显示错误布局
     */
    public void showErrorPage(int pic, String msg)
    {
        mRefreshView.setVisibility(View.GONE);
        linear_error.setVisibility(View.VISIBLE);
        iv_error_pic.setImageResource(pic);
        tv_error_msg.setText(msg);
    }

    /**
     * 隐藏错误布局
     */
    public void hintErrorPage()
    {
        linear_error.setVisibility(View.GONE);
        mRefreshView.setVisibility(View.VISIBLE);
    }

    /**
     * 获取可转让数据
     */
    private void getDataForNet1()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            showErrorPage(R.mipmap.net_error, "网络异常！");
            showToast("当前网络不可用，请重试！");
            return;
        }
        params = getParams();
        searchType = (int) params.get("searchType");
        HttpManager.getInstance().doPost(ServiceName.QUERY_DEBT, params, new HttpCallBack<AccountDebtBean>()
        {
            @Override
            public void getHttpCallNull(String str)
            {  // 数据为空
                UIUtils.showToast(getBaseContext(), R.string.dataError);
                UIUtils.dimissLoading();
                closeRefresh();
                showErrorPage(R.mipmap.net_error, "数据获取失败！");
            }

            @Override
            public void getHttpCallBack(AccountDebtBean accountDebtBean)
            {  // 成功
                UIUtils.dimissLoading();
                closeRefresh();
                if (null != accountDebtBean)
                {
                    hintErrorPage();
                    if ("000000".equals(accountDebtBean.getCode()))
                    {
                        pageTotal = accountDebtBean.getData().getBidPager().getPageTotal();
                        if (pageTotal <= 0)
                        {
                            showErrorPage(R.mipmap.empty, "数据为空");
                        }
                        if (isLoad)
                        {
                            isLoad = false;
                            mDatas.addAll(accountDebtBean.getData().getBidPager().getList());
                        }
                        else if (isRefresh)
                        {
                            isRefresh = false;
                            mDatas.clear();
                            mDatas.addAll(accountDebtBean.getData().getBidPager().getList());
                            UIUtils.showToast(EquitableActivity.this, R.string.refreshed);
                        }
                        else
                        {
                            mDatas = accountDebtBean.getData().getBidPager().getList();
                            CfLog.i("pageTotal = " + pageTotal);
                            userTransferAdapter = new UserTransferAdapter(EquitableActivity.this, mDatas,
                                    listview_investlist);
                            listview_investlist.setAdapter(userTransferAdapter);
                        }
                        userTransferAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        showErrorPage(R.mipmap.net_error, "数据获取失败！");
                        UIUtils.showToast(accountDebtBean.getMsg());
                    }
                }
                else
                {
                    showErrorPage(R.mipmap.empty, "数据为空");
                    UIUtils.showToast("数据为空");
                }
            }
        });
    }

    /**
     * 获取转让中数据
     */
    private void getDataForNet2()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            showErrorPage(R.mipmap.net_error, "网络异常！");
            showToast("当前网络不可用，请重试！");
            return;
        }
        params = getParams();
        searchType = (int) params.get("searchType");
        HttpManager.getInstance().doPost(ServiceName.QUERY_DEBT, params, new HttpCallBack<TransferingBean>()
        {
            @Override
            public void getHttpCallNull(String str)
            {  // 数据为空
                UIUtils.showToast(getBaseContext(), R.string.dataError);
                UIUtils.dimissLoading();
                closeRefresh();
                showErrorPage(R.mipmap.net_error, "数据获取失败！");
            }

            @Override
            public void getHttpCallBack(TransferingBean accountDebtBean)
            {  // 成功
                UIUtils.dimissLoading();
                closeRefresh();
                if (null != accountDebtBean)
                {
                    hintErrorPage();
                    if ("000000".equals(accountDebtBean.getCode()))
                    {
                        pageTotal = accountDebtBean.getData().getReceiptTransferPager().getPageTotal();
                        if (pageTotal <= 0)
                        {
                            showErrorPage(R.mipmap.empty, "数据为空");
                        }
                        if (isLoad)
                        {
                            isLoad = false;
                            mDatas2.addAll(accountDebtBean.getData().getReceiptTransferPager().getList());
                        }
                        else if (isRefresh)
                        {
                            isRefresh = false;
                            mDatas2.clear();
                            mDatas2.addAll(accountDebtBean.getData().getReceiptTransferPager().getList());
                            UIUtils.showToast(EquitableActivity.this, R.string.refreshed);
                        }
                        else
                        {
                            mDatas2 = accountDebtBean.getData().getReceiptTransferPager().getList();
                            CfLog.i("pageTotal = " + pageTotal);
                            transferingAdapter = new TransferingAdapter(EquitableActivity.this, mDatas2,
                                    listview_investlist, accountDebtBean.getData().getSearchType());
                            listview_investlist.setAdapter(transferingAdapter);
                        }
                        transferingAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        showErrorPage(R.mipmap.net_error, "数据获取失败！");
                        UIUtils.showToast(accountDebtBean.getMsg());
                    }
                }
                else
                {
                    showErrorPage(R.mipmap.empty, "数据为空");
                    UIUtils.showToast("数据为空");
                }
            }
        });
    }

    /**
     * 获取已购买数据
     */
    private void getDataForNet3()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            showErrorPage(R.mipmap.net_error, "网络异常！");
            showToast("当前网络不可用，请重试！");
            return;
        }
        params = getParams();
        searchType = (int) params.get("searchType");
        HttpManager.getInstance().doPost(ServiceName.QUERY_DEBT, params, new HttpCallBack<EquitableBuyBean>()
        {
            @Override
            public void getHttpCallNull(String str)
            {  // 数据为空
                UIUtils.showToast(getBaseContext(), R.string.dataError);
                UIUtils.dimissLoading();
                closeRefresh();
                showErrorPage(R.mipmap.net_error, "数据获取失败！");
            }

            @Override
            public void getHttpCallBack(EquitableBuyBean equitableBuyBean)
            {

                // 成功
                UIUtils.dimissLoading();
                closeRefresh();
                if (null != equitableBuyBean)
                {
                    hintErrorPage();
                    if ("000000".equals(equitableBuyBean.getCode()))
                    {
                        pageTotal = equitableBuyBean.getData().getBidPager().getPageTotal();
                        if (pageTotal <= 0)
                        {
                            showErrorPage(R.mipmap.empty, "数据为空");
                        }
                        if (isLoad)
                        {
                            isLoad = false;
                            mDatas3.addAll(equitableBuyBean.getData().getBidPager().getList());
                        }
                        else if (isRefresh)
                        {
                            isRefresh = false;
                            mDatas3.clear();
                            mDatas3.addAll(equitableBuyBean.getData().getBidPager().getList());
                            UIUtils.showToast(EquitableActivity.this, R.string.refreshed);
                        }
                        else
                        {
                            mDatas3 = equitableBuyBean.getData().getBidPager().getList();
                            CfLog.i("pageTotal = " + pageTotal);
                            buyTransferAdapter = new BuyTransferAdapter(EquitableActivity.this, mDatas3,
                                    listview_investlist);
                            listview_investlist.setAdapter(buyTransferAdapter);
                        }
                        buyTransferAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        showErrorPage(R.mipmap.net_error, "数据获取失败！");
                        UIUtils.showToast(equitableBuyBean.getMsg());
                    }
                }
                else
                {
                    showErrorPage(R.mipmap.empty, "数据为空");
                    UIUtils.showToast("数据为空");
                }
            }
        });
    }

    /**
     * @return 返回债权转让参数
     */
    public Map getParams()
    {
        Map params = new HashMap();
        params.put("currentPage", currentPage);
        params.put("pageSize", 10);
        switch (mCurrentTab)
        {
            case DEFAULT:
                params.put("searchType", 1);  //可转让
                break;
            case RATE:
                params.put("searchType", 2);  //转让中
                break;
            case LIMIT:
                params.put("searchType", 3);  //已转出
                break;
            case MONEY:
                params.put("searchType", 4);  //已购买
                break;
            default:
                break;
        }
        return params;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.iv_error_pic:  // 点击错误界面图片
                switch (searchType)
                {
                    case 1:
                        UIUtils.showLoading(this);
                        getDataForNet1();
                        break;
                    case 2:
                        UIUtils.showLoading(this);
                        getDataForNet2();
                        break;
                    case 3:
                        UIUtils.showLoading(this);
                        getDataForNet2();
                        break;
                    case 4:
                        UIUtils.showLoading(this);
                        getDataForNet3();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh()
    {
        currentPage = 1;
        mRefreshView.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue, R.color.red);
        mRefreshView.setRefreshing(true);
        isRefresh = true;
        switch (searchType)
        {
            case 1:
                getDataForNet1();
                break;
            case 2:
                getDataForNet2();
                break;
            case 3:
                getDataForNet2();
                break;
            case 4:
                getDataForNet3();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoad()
    {
        if (mDatas.size() >= pageTotal)
        {
            mRefreshView.setLoading(false);
            UIUtils.showToast("没有更多数据");
        }
        else if (mDatas2.size() >= pageTotal)
        {
            mRefreshView.setLoading(false);
            UIUtils.showToast("没有更多数据");
        }
        else if (mDatas3.size() >= pageTotal)
        {
            mRefreshView.setLoading(false);
            UIUtils.showToast("没有更多数据");
        }
        else
        {
            mRefreshView.setLoading(true);
            currentPage += 1;
            isLoad = true;
            switch (searchType)
            {
                case 1:
                    getDataForNet1();
                    break;
                case 2:
                    getDataForNet2();
                    break;
                case 3:
                    getDataForNet2();
                    break;
                case 4:
                    getDataForNet3();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 关闭刷新
     */
    public void closeRefresh()
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

    /**
     * 复位
     */
    private void clearFile()
    {
        currentPage = 1;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {

        switch (searchType)
        {
            case 1:
                Intent intent = new Intent(this, LoanDetailActivity.class);   // 跳转标的详情页
                intent.putExtra("productId", mDatas.get(i).getProductId());
                startActivity(intent);
                break;
            case 2:
                Intent intent1 = new Intent(this, EquitableDetailActivity.class);   // 跳转转让详情页
                intent1.putExtra("productId", mDatas2.get(i).getProductId());
                startActivity(intent1);
                break;
            case 3:
                Intent intent2 = new Intent(this, EquitableDetailActivity.class);   // 跳转转让详情页
                intent2.putExtra("productId", mDatas2.get(i).getProductId());
                startActivity(intent2);
                break;
            case 4:
                Intent intent3 = new Intent(this, EquitableDetailActivity.class);   // 跳转转让详情页
                intent3.putExtra("productId", mDatas3.get(i).getProductId());
                startActivity(intent3);
                break;
            default:
                break;
        }

    }

    @Override
    public void setOnTabChangeListener(int position, boolean isUpSoft)
    {
        clearFile();
        switch (position)
        {
            case 1:
                mCurrentTab = DEFAULT;
                UIUtils.showLoading(this);
                getDataForNet1();
                break;
            case 2:
                mCurrentTab = RATE;
                UIUtils.showLoading(this);
                getDataForNet2();
                break;
            case 3:
                mCurrentTab = LIMIT;
                UIUtils.showLoading(this);
                getDataForNet2();
                break;
            case 4:
                mCurrentTab = MONEY;
                UIUtils.showLoading(this);
                getDataForNet3();
                break;
            default:
                break;
        }
    }
}
