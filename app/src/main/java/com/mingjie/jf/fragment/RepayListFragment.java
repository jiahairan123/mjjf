package com.mingjie.jf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.LoanDetailActivity;
import com.mingjie.jf.adapter.RepayListAdapter;
import com.mingjie.jf.bean.BaseListBean;
import com.mingjie.jf.bean.RepayListBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.fragment
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 回款列表
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-15 14:42
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RepayListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout
        .OnLoadListener, View.OnClickListener,AdapterView.OnItemClickListener
{
    private final static String KEY_TYPE = "key_type";
    public static final int TYPE_ALL = 0;//全部
    public static final int TYPE_DUE_IN = 1;//待还
    public static final int TYPE_RECEIVED = 2;//已还
    private final int PAGE_SIZE = 10;//单页条目数

    private RefreshLayout refreshReturnList;
    private ListView lvReturnList;
    private LinearLayout llError;
    private ImageView ivErrPic;
    private TextView tvErrMsg;

    private int CURRENT_PAGE = 1;//当页页码
    private RepayListAdapter adapter;
    private int type;//数据类型，TYPE_ALL：全部；TYPE_DUE_IN：待收；TYPE_RECEIVED：已收
    private List<RepayListBean> datas = new ArrayList<>();

    private boolean isRefresh = false;//当前是否为刷新
    private boolean canLoad = true;//是否能加载更多

    public static RepayListFragment getInstance(int type)
    {
        RepayListFragment fragment = new RepayListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(getContext(), R.layout.fragment_return_list, null);
        refreshReturnList = (RefreshLayout) view.findViewById(R.id.refresh_return_list);
        refreshReturnList.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue);
        refreshReturnList.setOnRefreshListener(this);
        refreshReturnList.setOnLoadListener(this);
        lvReturnList = (ListView) view.findViewById(R.id.lv_return_list);
        lvReturnList.setOnItemClickListener(this);
        View foot = View.inflate(getContext(), R.layout.header, null);
        lvReturnList.addFooterView(foot);
        adapter = new RepayListAdapter(mActivity, datas);
        lvReturnList.setAdapter(adapter);

        llError = (LinearLayout) view.findViewById(R.id.linear_error);
        llError.setOnClickListener(this);
        ivErrPic = (ImageView) view.findViewById(R.id.iv_error_pic);
        tvErrMsg = (TextView) view.findViewById(R.id.tv_error_msg);
        return view;
    }

    @Override
    protected void initData()
    {
        isRefresh = true;
        type = getArguments().getInt(KEY_TYPE);
        UIUtils.showLoading(mActivity);
        getData();
    }

    @Override
    public void onRefresh()
    {
        isRefresh = true;
        CURRENT_PAGE = 1;
        getData();
    }

    @Override
    public void onLoad()
    {
        isRefresh = false;
        refreshReturnList.setLoading(canLoad);
        if (canLoad)
        {
            CURRENT_PAGE++;
            getData();
        }
        else if (datas.size() > PAGE_SIZE)
        {
            UIUtils.showToast("无更多数据");
        }
    }

    /**
     * 获取网络数据
     */
    private void getData()
    {
        if (!Utilities.canNetworkUseful(mActivity))//如果当前网络不可用
        {
            closeRefresh();
            showError(R.mipmap.net_error,"网络异常，请检查！");
            UIUtils.showToast(mActivity, "当前网络不可用，请重试！");
            return;
        }
        Map params = new HashMap();
        params.put("currentPage", CURRENT_PAGE);
        params.put("pageSize", PAGE_SIZE);
        params.put("state", type);
        HttpManager.getInstance().doPost(ServiceName.REPAYMENTPLANCALENDAR_LIST, params, new
                HttpCallBack<ServerResponse<BaseListBean<RepayListBean>>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        closeRefresh();
                        showError(R.mipmap.net_error, "请求异常,点击重试！");
                        ToastUtil.showToast(mActivity, getResources().getString(R.string.dataError));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<BaseListBean<RepayListBean>> rsp)
                    {
                        closeRefresh();
                        if (rsp == null)
                        {
                            ToastUtil.showToast(mActivity, getResources().getString(R.string.dataError));
                            showError(R.mipmap.net_error, "请求异常,点击重试！");
                            return;
                        }
                        if (!"000000".equals(rsp.getCode()))
                        {
                            showError(R.mipmap.net_error, "请求异常,点击重试！");
                            ToastUtil.showToast(mActivity, rsp.getMsg());
                        }
                        if (rsp.getData() == null)
                        {
                            showError(R.mipmap.net_error, "请求异常,点击重试！");
                            ToastUtil.showToast(mActivity, getResources().getString(R.string.dataError));
                            return;
                        }

                        //数据长度为0
                        if (rsp.getData().getPageTotal() <= 0)
                        {
                            showError(R.mipmap.null_data, "暂无数据");
                            return;
                        }
                        hideError();
                        if (isRefresh)
                        {
                            datas.clear();
                        }
                        datas.addAll(rsp.getData().getList());
                        adapter.notifyDataSetChanged();
                        canLoad = datas.size() < rsp.getData().getPageTotal();
                    }
                });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.linear_error://点击错误布局
                hideError();
                UIUtils.showLoading(mActivity);
                getData();
                break;
            default:
                break;
        }
    }

    //展示数据加载出错布局
    private void showError(int errIconId, String errMsg)
    {
        llError.setVisibility(View.VISIBLE);
        refreshReturnList.setVisibility(View.GONE);
        ivErrPic.setImageResource(errIconId);
        tvErrMsg.setText(errMsg);
    }

    //隐藏数据加载出错布局
    private void hideError()
    {
        llError.setVisibility(View.GONE);
        refreshReturnList.setVisibility(View.VISIBLE);
        tvErrMsg.setText("");
    }

    /**
     * 关闭刷新
     */
    private void closeRefresh()
    {
        if (refreshReturnList.isRefreshing())
        {
            refreshReturnList.setRefreshing(false);
        }
        if (refreshReturnList.isLoading())
        {
            refreshReturnList.setLoading(false);
        }
        UIUtils.dimissLoading();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        RepayListBean bean = datas.get(position);
        if(bean!=null)
        {
            Intent intent = new Intent(mActivity, LoanDetailActivity.class);
            intent.putExtra("productId",bean.getProductId());
            startActivity(intent);
        }
    }
}
