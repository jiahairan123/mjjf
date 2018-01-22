package com.mingjie.jf.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.Pager;
import com.mingjie.jf.widgets.JRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-20 14:21
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class RechargeManagerMoneyAdapter extends RecyclerView.Adapter<RechargeManagerMoneyAdapter.ViewHolder>
{
    private final SimpleDateFormat mFormat;
    private String mStyle = "yyy-MM-dd HH:mm:ss";//时间格式
    private Context mContext;
    private List<Pager.DataBean.ListBean> list;
    private final JRecyclerView jRecyclerView;

    public RechargeManagerMoneyAdapter(Context context, List<Pager.DataBean.ListBean> list, JRecyclerView jRecyclerView)
    {
        this.mContext = context;
        this.list = list;
        mFormat = new SimpleDateFormat(mStyle);
        this.jRecyclerView = jRecyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recharge_manager, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        Pager.DataBean.ListBean rechargelist = list.get(position);
        Date date = new Date(rechargelist.getCreatedDate());
        viewHolder.createdDate.setText(mFormat.format(date));
        viewHolder.flowcode.setText(rechargelist.getFlowCode());
//        viewHolder.rechargeAmount.setText(String.valueOf(rechargelist.getRechargeAmount()));
        viewHolder.rechargeAmount.setText(rechargelist.getRechargeAmount()+"");
        if (rechargelist.getStatus() == 1)
        {
            viewHolder.statusName.setText("充值" + String.valueOf(rechargelist.getStatusName()));
            viewHolder.statusName.setTextColor(Color.parseColor("#ef6963"));
        }
        else if (rechargelist.getStatus() == 2)
        {
            viewHolder.statusName.setText("充值" + String.valueOf(rechargelist.getStatusName()));
            viewHolder.statusName.setTextColor(Color.parseColor("#248ADE"));
        }
        else if (rechargelist.getStatus() == 0)
        {
            viewHolder.statusName.setText(String.valueOf(rechargelist.getStatusName()));
            viewHolder.statusName.setTextColor(Color.parseColor("#009944"));
        }
        else
        {
            viewHolder.statusName.setText(String.valueOf(rechargelist.getStatusName()));
            viewHolder.statusName.setTextColor(Color.parseColor("#434343"));
        }
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView flowcode;
        TextView rechargeAmount;
        TextView statusName;
        TextView createdDate;

        public ViewHolder(View itemView)
        {
            super(itemView);
            flowcode = (TextView) itemView.findViewById(R.id.flowCode);
            rechargeAmount = (TextView) itemView.findViewById(R.id.rechargeAmount);
            statusName = (TextView) itemView.findViewById(R.id.statusName);
            createdDate = (TextView) itemView.findViewById(R.id.createdDate);
        }
    }
}
