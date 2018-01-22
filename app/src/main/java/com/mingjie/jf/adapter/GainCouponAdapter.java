package com.mingjie.jf.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.CashDetailBean;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;

import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.adapter
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 加息券收益adapter
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2017-02-23 15:06
 * <p>当前版本： ${version}
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class GainCouponAdapter extends BaseAdapter
{

    private Context context;
    private List data;

    public GainCouponAdapter(Context context, List data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup viewGroup)
    {
        CashDetailBean cashDetailBean = (CashDetailBean) data.get(position);
        GainCouponAdapter.CouponViewHolder holder;
        if (itemView == null)
        {
            itemView = View.inflate(context, R.layout.item_gain_coupon_list, null);
            TextView tvCouponName = (TextView) itemView.findViewById(R.id.tv_coupon_name);//标名
            TextView tvInvestMoney = (TextView) itemView.findViewById(R.id.tv_invest_money);//投资金额
            TextView tvAccrual = (TextView) itemView.findViewById(R.id.tv_accrual);//加息利率
            TextView tvPeriods = (TextView) itemView.findViewById(R.id.tv_periods);//期数
            TextView tvIncomeReceived = (TextView) itemView.findViewById(R.id.tv_income_received);//待收收益
            TextView tvNextReturn = (TextView) itemView.findViewById(R.id.tv_next_return);//下期收益
            TextView tvState = (TextView) itemView.findViewById(R.id.tv_state);//状态
            TextView tvCouponTime = (TextView) itemView.findViewById(R.id.tv_coupon_time);//下期收益时间
            holder = new GainCouponAdapter.CouponViewHolder(tvCouponName, tvInvestMoney, tvAccrual, tvPeriods,
                    tvIncomeReceived, tvNextReturn, tvState, tvCouponTime);
            itemView.setTag(holder);
        }
        holder = (GainCouponAdapter.CouponViewHolder) itemView.getTag();
        holder.tvCouponName.setText(cashDetailBean.getProductName());
        holder.tvInvestMoney.setText(StringUtils.dou2Str(cashDetailBean.getCapital()));
        holder.tvAccrual.setText(StringUtils.dou2Str(cashDetailBean.getProportion()));
        holder.tvPeriods.setText(cashDetailBean.getProductDeadlineDesc());
        holder.tvIncomeReceived.setText(StringUtils.dou2Str(cashDetailBean.getNotProfitTotal()));
        holder.tvNextReturn.setText(StringUtils.dou2Str(cashDetailBean.getProfit()));
        //state 收益状态 1表示未收，2表示已收，3表示已转让
        if (1 == cashDetailBean.getState())
        {
            holder.tvState.setText("未收");
        }
        else if (3 == cashDetailBean.getState())
        {
            holder.tvState.setText("已转让");
        }
        else
        {
            holder.tvState.setText("已收");
        }
        holder.tvCouponTime.setText(String.format(context.getString(R.string.expire_time),
                TimeFormatUtil.data2StrTime(cashDetailBean.getExpireDate())));
        return itemView;
    }

    static class CouponViewHolder
    {
        TextView tvCouponName;//标名
        TextView tvInvestMoney;//投资金额
        TextView tvAccrual;//加息利率
        TextView tvPeriods;//期数
        TextView tvIncomeReceived;//待收收益
        TextView tvNextReturn;//下期收益
        TextView tvState;//状态
        TextView tvCouponTime;//下期收益时间

        public CouponViewHolder(TextView tvCouponName, TextView tvInvestMoney, TextView tvAccrual, TextView
                tvPeriods, TextView tvIncomeReceived, TextView tvNextReturn, TextView tvState, TextView tvCouponTime)
        {
            this.tvCouponName = tvCouponName;
            this.tvInvestMoney = tvInvestMoney;
            this.tvAccrual = tvAccrual;
            this.tvPeriods = tvPeriods;
            this.tvIncomeReceived = tvIncomeReceived;
            this.tvNextReturn = tvNextReturn;
            this.tvState = tvState;
            this.tvCouponTime = tvCouponTime;
        }
    }
}

