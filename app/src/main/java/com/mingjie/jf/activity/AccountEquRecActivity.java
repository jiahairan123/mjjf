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
import com.mingjie.jf.adapter.AccountEquRecAdapter;
import com.mingjie.jf.bean.AccountEquRecBean;
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

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 账户 - 转让记录
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-26 15:11
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AccountEquRecActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener, RefreshLayout.OnLoadListener, AdapterView.OnItemClickListener
{
    private ImageView ivLeftPublic;//返回
    private TextView tvContentPublic;//内容
    private InvestIndicator mTabs;
    private RefreshLayout mRefreshView;
    private ListView listview_investlist;

    private boolean isRefresh;                 // 是否正在刷新
    private boolean isLoad;                    // 是否正在加载

    private int currentPage = 1;               //参数

    private int pageTotal;                     //总数据条数

    private LinearLayout linear_error;  // 错误布局
    private ImageView iv_error_pic;  // 错误图片
    private TextView tv_error_msg;   // 错误描述
    private View foot;

    private List<AccountEquRecBean.DataBean.ReceiptTransferPagerBean.ListBean> mDatas = new ArrayList<>();//债权转让-转让记录
    private AccountEquRecAdapter accountEquRecAdapter;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_equ_record);
        foot = View.inflate(this, R.layout.header, null);
        ivLeftPublic = (ImageView) findViewById(R.id.iv_Left_public);
        tvContentPublic = (TextView) findViewById(R.id.tv_content_public);
        ivLeftPublic = (ImageView) findViewById(R.id.iv_Left_public);

        linear_error = (LinearLayout) findViewById(R.id.linear_error);
        iv_error_pic = (ImageView) findViewById(R.id.iv_error_pic);
        tv_error_msg = (TextView) findViewById(R.id.tv_error_msg);

        mRefreshView = (RefreshLayout) findViewById(R.id.swipe_container);
        listview_investlist = (ListView) findViewById(R.id.listview_investlist);
        listview_investlist.addFooterView(foot);
        tvContentPublic.setText("转让记录");

        ivLeftPublic.setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setOnLoadListener(this);
        listview_investlist.setOnItemClickListener(this);

        iv_error_pic.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        UIUtils.showLoading(this);
        getDataForNet1();   // 可转让
    }

    /**
     * 获取转让记录数据
     */
    private void getDataForNet1()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            showErrorPage(R.mipmap.net_error, "网络异常！");
            showToast("当前网络不可用，请重试！");
            return;
        }
        Map params = new HashMap();
        params.put("pageSize", 10);
        params.put("currentPage", currentPage);
        HttpManager.getInstance().doPost(ServiceName.QUERY_DEBT_LIST, params, new HttpCallBack<AccountEquRecBean>()
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
            public void getHttpCallBack(AccountEquRecBean accountEquRecBean)
            {  // 成功
                UIUtils.dimissLoading();
                closeRefresh();
                if (null != accountEquRecBean)
                {
                    hintErrorPage();
                    if ("000000".equals(accountEquRecBean.getCode()))
                    {
                        pageTotal = accountEquRecBean.getData().getReceiptTransferPager().getPageTotal();
                        if (pageTotal <= 0)
                        {
                            showErrorPage(R.mipmap.empty, "数据为空");
                        }
                        if (isLoad)
                        {
                            isLoad = false;
                            mDatas.addAll(accountEquRecBean.getData().getReceiptTransferPager().getList());
                        }
                        else if (isRefresh)
                        {
                            isRefresh = false;
                            mDatas.clear();
                            mDatas.addAll(accountEquRecBean.getData().getReceiptTransferPager().getList());
                            UIUtils.showToast(AccountEquRecActivity.this, R.string.refreshed);
                        }
                        else
                        {
                            mDatas = accountEquRecBean.getData().getReceiptTransferPager().getList();
                            CfLog.i("pageTotal = " + pageTotal);
                            accountEquRecAdapter = new AccountEquRecAdapter(AccountEquRecActivity.this, mDatas,
                                    listview_investlist);
                            listview_investlist.setAdapter(accountEquRecAdapter);
                        }
                        accountEquRecAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        showErrorPage(R.mipmap.net_error, "数据获取失败！");
                        UIUtils.showToast(accountEquRecBean.getMsg());
                    }
                }
                else
                {
                    showErrorPage(R.mipmap.empty, "数据为空");
                }
            }
        });
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_Left_public:  //返回
                finish();
                break;
            case R.id.iv_error_pic:  // 点击错误界面图片
                getDataForNet1();
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
        getDataForNet1();
    }

    @Override
    public void onLoad()
    {
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
            getDataForNet1();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(this, LoanDetailActivity.class);   // 跳转标的详情页
        intent.putExtra("productId", mDatas.get(position).getProductId());
        startActivity(intent);
    }
}
