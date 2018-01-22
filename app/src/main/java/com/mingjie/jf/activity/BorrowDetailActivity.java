package com.mingjie.jf.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BorrowDetailAdapter;
import com.mingjie.jf.bean.BorrowDetailBean;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 收款明细
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-28 09:45
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class BorrowDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View
        .OnClickListener
{
    @BindView(R.id.swipe_container)
    RefreshLayout mRefreshView;     // 刷新控件
    @BindView(R.id.listview_investlist)
    ListView listview_investlist;

    @BindView(R.id.iv_Left_public)
    ImageView iv_Left_public;       // 返回箭头
    @BindView(R.id.tv_content_public)
    TextView tv_content_public;     // 标题文字

    // private boolean isRefresh;                 // 是否正在刷新

    private List<BorrowDetailBean.DataBean> mDatas = new ArrayList<>();
    private String id;
    private BorrowDetailAdapter adapter;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_borrow_detail);
        ButterKnife.bind(this);
        mRefreshView.setOnRefreshListener(this);
        iv_Left_public.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            default:
                break;
        }
    }

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
    public void onRefresh()
    {
        // 下拉刷新
        mRefreshView.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue, R.color.red);
        mRefreshView.setRefreshing(true);
        // isRefresh = true;
        getData();
    }

    public void getData()
    {  // 上拉加载 下拉刷新 调用加载数据的方法
        Map<String, String> params = new HashMap<>();
        params.put("Id", id);
        HttpManager.getInstance().doPost(ServiceName.REPAYMENT_GETRECEIPTLIST, params, new
                HttpCallBack<BorrowDetailBean>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {  // 数据为空
                        showToast(R.string.dataError);
                        closeRefresh();
                    }

                    @Override
                    public void getHttpCallBack(BorrowDetailBean list)
                    {  // 成功
                        closeRefresh();
                        if (null != list)
                        {
                            if ("000000".equals(list.code))
                            {
                                // isRefresh = false;
                                mDatas.clear();
                                mDatas.addAll(list.data);
                                showToast("刷新完成");
                            }
                            else
                            {
                                showToast(list.msg);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            showToast(R.string.dataError);
                        }
                    }
                });
    }

    @Override
    protected void initData()
    {
        UIUtils.showLoading(this);
        tv_content_public.setText("收款明细");
        id = getIntent().getStringExtra("Id");
        Map<String, String> params = new HashMap<>();
        params.put("Id", id);
        HttpManager.getInstance().doPost(ServiceName.REPAYMENT_GETRECEIPTLIST, params, new
                HttpCallBack<BorrowDetailBean>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {   // 数据为空
                        UIUtils.dimissLoading();
                        showToast(R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(BorrowDetailBean list)
                    {   // 成功
                        UIUtils.dimissLoading();
                        if (null != list)
                        {
                            if ("000000".equals(list.code))
                            {
                                mDatas = list.data;
                                adapter = new BorrowDetailAdapter(BorrowDetailActivity.this, mDatas,
                                        listview_investlist);
                                listview_investlist.setAdapter(adapter);
                            }
                            else
                            {
                                showToast(list.msg);
                            }
                        }
                        else
                        {
                            showToast(R.string.dataError);
                        }
                    }
                });
    }
}
