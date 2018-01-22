package com.mingjie.jf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.NewGetCouponBean;
import com.mingjie.jf.widgets.OnPressListener;

import java.util.List;

/**
 * Created by jiahairan on 2017/8/23 13.
 */

public class GetCouponAdapter extends BaseAdapter {
    Context context;
    OnPressListener onPressListener;
    String isTime;

    public void setIsTime(String isTime) {
        this.isTime = isTime;
    }

    public void setOnPressListener(OnPressListener onPressListener) {
        this.onPressListener = onPressListener;
    }

    List<NewGetCouponBean.GetCouponBean1> data;

    public void setData(List<NewGetCouponBean.GetCouponBean1> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public GetCouponAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // type 1 2 现金券  he 加息券
        //fixedAmount   investmentPercent
        NewGetCouponBean.GetCouponBean1 bean = data.get(position);
        final GetCouponHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_getcoupon, null);
            holder = new GetCouponHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GetCouponHolder) convertView.getTag();
        }

        holder.couponActivity.setText(bean.getAssetName()); // 活动专享

        // 投资期限
        if ("".equals(bean.getDayUserRange()) && !"".equals(bean.getMonthUserRange())) {
            holder.couponTime.setText("投资期限 " + bean.getMonthUserRange() + "月可用");
        } else {
            holder.couponTime.setText("投资期限 " + bean.getDayUserRange() + "天可用");
        }

        // 单笔投资
        if (!bean.getMaxInvestmentAmountStr().equals("null") && !bean.getMinInvestmentAmountStr().equals("null")){
            holder.couponAmount.setText(bean.getMinInvestmentAmountStr()+ "元 ≤单笔投资≤ " + bean.getMaxInvestmentAmountStr() + "元可用");
        }
        else if (!bean.getMaxInvestmentAmountStr().equals("null") && bean.getMinInvestmentAmountStr().equals("null")){
        holder.couponAmount.setText("单笔投资≤" + bean.getMaxInvestmentAmountStr() + "元可用");
        }
        else if (!bean.getMinInvestmentAmountStr().equals("null") && bean.getMaxInvestmentAmountStr().equals("null")){
            holder.couponAmount.setText("单笔投资≥" + bean.getMinInvestmentAmountStr() + "元可用");
        }
        holder.couponState.setText("立即抢券");

// 根据类型   背景颜色 按钮颜色 和 单位
        if ("1".equals(bean.getType())) { //现金券
            holder.relativeLayout.setBackgroundResource(R.mipmap.item_cash_coupon);
            holder.couponState.setBackgroundResource(R.mipmap.item_cash_coupon_button);
            holder.couponMoney.setText(bean.getFixedAmountStr()); //现金券金额
            holder.couponType.setText("现金券");
            holder.couponUnit.setText(" 元"); // 单位
        } else {  //加息
            holder.relativeLayout.setBackgroundResource(R.mipmap.item_interest_coupon);
            holder.couponState.setBackgroundResource(R.mipmap.item_interest_coupon_button);
            holder.couponMoney.setText(bean.getInvestmentPercentStr()); //加息券数额
            holder.couponType.setText("加息券"); //
            holder.couponUnit.setText(" %加息");
        }

        holder.couponRest.setText("剩余 " + bean.getQuantityStr() + " 张");//剩余

        if ("0".equals(bean.getQuantityStr())) {
            holder.relativeLayout.setBackgroundResource(R.mipmap.item_finish_coupon);
            holder.couponState.setBackgroundResource(R.mipmap.item_coupon_button);
            holder.couponState.setText("已抢光");
        } else {
            holder.couponState.setText("立即抢券");
        }

        if (isTime.equals("0")){
            holder.relativeLayout.setBackgroundResource(R.mipmap.item_finish_coupon);
            holder.couponState.setBackgroundResource(R.mipmap.item_coupon_button);
            holder.couponState.setText("未开始");
        }



        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPressListener.onClick(position);
            }
        });
        return convertView;
    }

    class GetCouponHolder {

        RelativeLayout relativeLayout;
        TextView couponState; //立即抢券
        TextView couponType; //类型 现现金券 还是加息券 1 2
        TextView couponMoney; //抢券金额 或者加息券数额
        TextView couponUnit; //单位 元或者是百分比
        TextView couponRest; // 剩余 多少张
        TextView couponActivity;    // 投资
        TextView couponTime;   // 投资期限
        TextView couponAmount; // 单笔投资
        TextView couponUseTime; //使用期限

        public GetCouponHolder(View view) {
            relativeLayout = (RelativeLayout) view.findViewById(R.id.item_rl); // 设置背景颜色
            couponState = (TextView) view.findViewById(R.id.tv_coupon_state); //立即抢  已抢完
            couponType = (TextView) view.findViewById(R.id.item_coupon_type); // 左上角 加息券 还是现金券
            couponMoney = (TextView) view.findViewById(R.id.item_coupon_money); // 金额 或是 加息的数额
            couponUnit = (TextView) view.findViewById(R.id.item_coupon_unit);  // 金额的单位
            couponRest = (TextView) view.findViewById(R.id.item_coupon_rest);  // 剩余 多少张
            couponActivity = (TextView) view.findViewById(R.id.item_coupon_activity); //活动类型
            couponTime = (TextView) view.findViewById(R.id.item_coupon_time); //投资期限
            couponAmount = (TextView) view.findViewById(R.id.item_coupon_amount); //投资金额限制
            couponUseTime = (TextView) view.findViewById(R.id.tv_coupon_time_canuse); //使用期限
        }
    }
}

