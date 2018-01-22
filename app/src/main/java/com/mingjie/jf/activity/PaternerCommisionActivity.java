package com.mingjie.jf.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.FriendPushVo;
import com.mingjie.jf.bean.RecommendParams;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.RefreshLayout;
import com.mingjie.jf.widgets.ScrollViewNestListview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 创业小伙伴提成
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-09 16:19
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class PaternerCommisionActivity extends BaseActivity implements View.OnClickListener, RefreshLayout
        .OnLoadListener, SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.lv_friend_push)
    ScrollViewNestListview lv_friend_push;
    @BindView(R.id.tv_sum_money)
    TextView tv_sum_money;                      // 累计待净收总额
    @BindView(R.id.tv_sum_commision_number)
    TextView tv_sum_commision_number;           // 累计提成
    @BindView(R.id.tv_direct_commision_number)
    TextView tv_direct_commision_number;         // 直接推荐提成
    @BindView(R.id.tv_tv_recevie_money_number)
    TextView tv_tv_recevie_money_number;         // 本月提成
    @BindView(R.id.tv_total_sum_number)
    TextView tv_total_sum_number;               // 间接推荐提成

    @BindView(R.id.swipe_container)
    RefreshLayout mRefreshView;                //刷新控件

    private int currentPage = 1;                   //参数
    private int pageTotal;                     //总数据条数

    private boolean isRefresh;                 // 是否正在刷新
    private boolean isLoad;                    // 是否正在加载

    private RecommendParams params;
    private List<FriendPushVo.DataBean.ListBeans.ListBean> mData = new ArrayList<>(); // 存放listview数据的容器
    private SimpleDateFormat dateFormater;

    private UserVoValible userinfo; // 用户id
    private MyAdapter adapter;

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

    @Override
    public void onLoad()
    {
        // 上拉加载更多
        if (mData.size() >= pageTotal)
        {
            mRefreshView.setLoading(false);
            UIUtils.showToast(getBaseContext(), "没有更多数据");
        }
        else
        {
            mRefreshView.setLoading(true);
            currentPage += 1;
            isLoad = true;
            getData();
        }
    }

    private void getData()
    {
        params = getParams();
        HttpManager.getInstance().doPost(ServiceName.CGKX_COMMISSIONLISTDATA, params, new HttpCallBack<FriendPushVo>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.showToast(getBaseContext(), R.string.dataError);
                closeRefresh();
            }

            @Override
            public void getHttpCallBack(FriendPushVo recomment)
            {
                closeRefresh();
                if (null != recomment)
                {
                    if ("000000".equals(recomment.code))
                    {
                        pageTotal = recomment.data.list.pageTotal;
                        if (isLoad)
                        {
                            isLoad = false;
                            tv_sum_money.setText(StringUtils.dou2Str(recomment.data.cleanReceiptSum));
                            tv_sum_commision_number.setText(StringUtils.dou2Str(recomment.data.commissionSums));
                            tv_direct_commision_number.setText(StringUtils.dou2Str(recomment.data.loginUserAmount));
                            tv_tv_recevie_money_number.setText(StringUtils.dou2Str(recomment.data
                                    .thisMonthCommissionSum));
                            tv_total_sum_number.setText(StringUtils.dou2Str(recomment.data.thisMonthAmountSum));
                            mData.addAll(recomment.data.list.list);
                        }
                        else if (isRefresh)
                        {
                            isRefresh = false;
                            mData.clear();
                            tv_sum_money.setText(StringUtils.dou2Str(recomment.data.cleanReceiptSum));
                            tv_sum_commision_number.setText(StringUtils.dou2Str(recomment.data.commissionSums));
                            tv_direct_commision_number.setText(StringUtils.dou2Str(recomment.data.loginUserAmount));
                            tv_tv_recevie_money_number.setText(StringUtils.dou2Str(recomment.data
                                    .thisMonthCommissionSum));
                            tv_total_sum_number.setText(StringUtils.dou2Str(recomment.data.thisMonthAmountSum));
                            mData.addAll(recomment.data.list.list);
                            UIUtils.showToast(getBaseContext(), "刷新完成");
                        }
                        else
                        {
                            mData.clear();
                            tv_sum_money.setText(StringUtils.dou2Str(recomment.data.cleanReceiptSum));
                            tv_sum_commision_number.setText(StringUtils.dou2Str(recomment.data.commissionSums));
                            tv_direct_commision_number.setText(StringUtils.dou2Str(recomment.data.loginUserAmount));
                            tv_tv_recevie_money_number.setText(StringUtils.dou2Str(recomment.data
                                    .thisMonthCommissionSum));
                            tv_total_sum_number.setText(StringUtils.dou2Str(recomment.data.thisMonthAmountSum));
                            mData.addAll(recomment.data.list.list);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        UIUtils.showToast(getBaseContext(), recomment.msg);
                    }
                }
                else
                {
                    UIUtils.showToast(getBaseContext(), R.string.service_err);
                }
            }
        });
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

    public class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            if (mData.size() != 0)
            {
                return mData.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            if (null != mData)
            {
                return mData.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder = null;
            if (convertView == null)
            {
                convertView = View.inflate(PaternerCommisionActivity.this, R.layout.item_commission_history,
                        null);
                holder = new ViewHolder();
                holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                holder.tv_commision = (TextView) convertView.findViewById(R.id.tv_commision);
//                holder.tv_mark_tab3 = (TextView) convertView.findViewById(R.id.tv_mark_tab3);
//                holder.tv_mark_tab4 = (TextView) convertView.findViewById(R.id.tv_mark_tab4);
                holder.tv_mark_tab5 = (TextView) convertView.findViewById(R.id.tv_mark_tab5);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_date.setText(dateFormater.format(mData.get(position).expectDate));
            holder.tv_commision.setText(StringUtils.dou2Str(mData.get(position).amount));
//            holder.tv_mark_tab3.setText(StringUtils.dou2Str(mData.get(position).directAmount));
//            holder.tv_mark_tab4.setText(StringUtils.dou2Str(mData.get(position).indirectAmount));
            holder.tv_mark_tab5.setText(mData.get(position).stateDesc);
            return convertView;
        }
    }

    static class ViewHolder
    {
        TextView tv_date;       // 发放提成日期
        TextView tv_commision;  // 提成金额
        // TextView tv_mark_tab3;      // 直接推荐提成
        // TextView tv_mark_tab4;    // 间接推荐提成
        TextView tv_mark_tab5;    // 状态
    }

    protected void initView()
    {
        setContentView(R.layout.activity_commission);
        ivLeftPublic.setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setOnLoadListener(this);
    }

    protected void initData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            return;
        }
        UIUtils.showLoading(this);
        tvContentPublic.setText("创业小伙伴提成");
        dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        userinfo = CacheUtils.getUserinfo(this);
        params = getParams();
        adapter = new MyAdapter();
        HttpManager.getInstance().doPost(ServiceName.CGKX_COMMISSIONLISTDATA, params, new HttpCallBack<FriendPushVo>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.showToast(getBaseContext(), R.string.dataError);
                UIUtils.dimissLoading();
            }

            @Override
            public void getHttpCallBack(FriendPushVo recomment)
            {
                UIUtils.dimissLoading();
                if (null != recomment)
                {
                    if ("000000".equals(recomment.code))
                    {
                        pageTotal = recomment.data.list.pageTotal;
                        mData = recomment.data.list.list;
                        tv_sum_money.setText(StringUtils.dou2Str(recomment.data.cleanReceiptSum));
                        tv_sum_commision_number.setText(StringUtils.dou2Str(recomment.data.commissionSums));
                        tv_direct_commision_number.setText(StringUtils.dou2Str(recomment.data.loginUserAmount));
                        tv_tv_recevie_money_number.setText(StringUtils.dou2Str(recomment.data.thisMonthCommissionSum));
                        tv_total_sum_number.setText(StringUtils.dou2Str(recomment.data.thisMonthAmountSum));
                        lv_friend_push.setAdapter(adapter);
                    }
                    else
                    {
                        UIUtils.showToast(getBaseContext(), recomment.msg);
                    }
                }
                else
                {
                    UIUtils.showToast(getBaseContext(), R.string.service_err);
                }
            }
        });
    }

    /**
     * @return 返回我的推荐参数
     */
    public RecommendParams getParams()
    {
        RecommendParams params = new RecommendParams();
        params.setPageSize(10);
        params.setCurrentPage(currentPage);
        params.setUserId(userinfo.getId());
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
            default:
                break;
        }
    }
}
