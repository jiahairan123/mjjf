package com.mingjie.jf.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.CouponAdapter;
import com.mingjie.jf.bean.BaseListBean;
import com.mingjie.jf.bean.CashBean;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.RedPacketBean;
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

import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.fragment
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 优惠券
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-15 14:42
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CouponListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, RefreshLayout.OnLoadListener
{
    private final static String KEY_TYPE = "key_type";
    public static final int TYPE_CASH = 0;//现金券
    public static final int TYPE_RED_PACKET = 1;//红包
    public static final int TYPE_COUPON = 2;//加息券

    private final int PAGE_SIZE = 10;
    private int current_page = 1;
    private boolean isRefresh = false;
    private boolean canLoad = true;

    private RefreshLayout refreshReturnList;
    private ListView lvReturnList;
    private LinearLayout llError;
    private ImageView ivErrPic;
    private TextView tvErrMsg;

    private CouponAdapter adapter;
    private int type;//数据类型，TYPE_CASH：现金券；TYPE_RED_PACKET：红包
    private List datas = new ArrayList<>();

    public static CouponListFragment getInstance(int type)
    {
        CouponListFragment fragment = new CouponListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initView()
    {
        type = getArguments().getInt(KEY_TYPE);
        View view = View.inflate(getContext(), R.layout.fragment_coupon_list, null);
        refreshReturnList = (RefreshLayout) view.findViewById(R.id.refresh_return_list);
        refreshReturnList.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue);
        refreshReturnList.setOnRefreshListener(this);
        refreshReturnList.setOnLoadListener(this);
        lvReturnList = (ListView) view.findViewById(R.id.lv_return_list);
        View foot = View.inflate(getContext(), R.layout.header, null);
        lvReturnList.addFooterView(foot);
        adapter = new CouponAdapter(mActivity, datas, type);
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
        if(!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
        UIUtils.showLoading(mActivity);
        isRefresh = true;
        getData();
    }

    public void onEvent(Event event)
    {
        if (event.eventType == Event.GET_REDPACKET)
        {
            UIUtils.showLoading(mActivity);
            isRefresh = true;
            getData();
        }
    }

    @Override
    public void onRefresh()
    {
        isRefresh = true;
        current_page = 1;
        getData();
    }

    @Override
    public void onLoad()
    {
        if (canLoad)
        {
            current_page++;
            isRefresh = false;
            refreshReturnList.setLoading(true);
            getData();
        }
        else if(datas.size()>5)
        {
            UIUtils.showToast(getString(R.string.not_more_data));
        }
    }

    /**
     * 获取数据
     */
    private void getData()
    {
        if (type == TYPE_RED_PACKET)
        {
            getRedPacketData();
        }
        else if (type == TYPE_CASH)
        {
            getCashData();
        }
        else
        {
        }
    }

    /**
     * 获取现金券数据
     */
    private void getCashData()
    {
        if (!Utilities.canNetworkUseful(mActivity))//如果当前网络不可用
        {
            closeRefresh();
            showError(R.mipmap.net_error, "网络异常，请检查！");
            UIUtils.showToast(mActivity, "当前网络不可用，请重试！");
            return;
        }
        Map params = new HashMap();
        params.put("currentPage", current_page);
        params.put("pageSize", PAGE_SIZE);
        HttpManager.getInstance().doPost(ServiceName.CASHLIST, params, new
                HttpCallBack<ServerResponse<BaseListBean<CashBean>>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        isRefresh = false;
                        closeRefresh();
                        showError(R.mipmap.net_error, "请求异常,点击重试！");
                        ToastUtil.showToast(mActivity, getResources().getString(R.string.dataError));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<BaseListBean<CashBean>> rsp)
                    {
                        closeRefresh();
                        if (rsp == null)
                        {
                            isRefresh = false;
                            ToastUtil.showToast(mActivity, getResources().getString(R.string.dataError));
                            showError(R.mipmap.net_error, "请求异常,点击重试！");
                            return;
                        }
                        if (!"000000".equals(rsp.getCode()))
                        {
                            isRefresh = false;
                            showError(R.mipmap.net_error, "请求异常,点击重试！");
                            ToastUtil.showToast(mActivity, rsp.getMsg());
                        }
                        if (rsp.getData() == null)
                        {
                            isRefresh = false;
                            showError(R.mipmap.net_error, "请求异常,点击重试！");
                            ToastUtil.showToast(mActivity, getResources().getString(R.string.dataError));
                            return;
                        }

                        //数据长度为0
                        if (rsp.getData().getPageTotal() <= 0)
                        {
                            isRefresh = false;
                            showError(R.mipmap.null_data, "暂无数据");
                            return;
                        }
                        hideError();
                        if (isRefresh)
                        {
                            datas.clear();
                        }
                        datas.addAll(rsp.getData().getList());
                        if (datas.size() >= rsp.getData().getPageTotal())
                        {
                            canLoad = false;
                        }
                        adapter.notifyDataSetChanged();
                        isRefresh = false;
                    }
                });
    }

    /**
     * 获取红包数据
     */
    private void getRedPacketData()
    {
        if (!Utilities.canNetworkUseful(mActivity))//如果当前网络不可用
        {
            closeRefresh();
            showError(R.mipmap.net_error, "网络异常，请检查！");
            UIUtils.showToast(mActivity, "当前网络不可用，请重试！");
            return;
        }
        Map params = new HashMap();
        params.put("currentPage", current_page);
        params.put("pageSize", PAGE_SIZE);
        HttpManager.getInstance().doPost(ServiceName.REDBAGLIST, params, new
                HttpCallBack<ServerResponse<BaseListBean<RedPacketBean>>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        closeRefresh();
                        showError(R.mipmap.net_error, "请求异常,点击重试！");
                        ToastUtil.showToast(mActivity, getResources().getString(R.string.dataError));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<BaseListBean<RedPacketBean>> rsp)
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
                            isRefresh = false;
                            showError(R.mipmap.null_data, "暂无数据");
                            return;
                        }
                        hideError();
                        if (isRefresh)
                        {
                            datas.clear();
                        }
                        datas.addAll(rsp.getData().getList());
                        if (datas.size() >= rsp.getData().getPageTotal())
                        {
                            canLoad = false;
                        }
                        adapter.notifyDataSetChanged();
                        isRefresh = false;
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
        datas.clear();
        adapter.notifyDataSetChanged();
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
    public void onDestroy()
    {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().unregister(this);
        }
    }
}
