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
 * Created by QinSiYi on 2016-8-22.
 */
public class ElectBillAdapter extends RecyclerView.Adapter<ElectBillAdapter.ViewHolder>
{
    private final SimpleDateFormat mFormat;
    private String mStyle = "yyy-MM-dd HH:mm";//时间格式
    private Context mContext;
    private List<Pager.DataBean.ListBean> list;
    //传入JRecyclerView用来刷新列表，因为JRcyclerView真正绑定的不是该adapter，而是它内部的adapter
    private final JRecyclerView jRecyclerView;

    public ElectBillAdapter(Context context, List<Pager.DataBean.ListBean> list, JRecyclerView jRecyclerView)
    {
        this.mContext = context;
        this.list = list;
        mFormat = new SimpleDateFormat(mStyle);
        this.jRecyclerView = jRecyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_elect_history, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Pager.DataBean.ListBean rechargelist = list.get(position);
        // CfLog.i("---" + rechargelist);
        Date date = new Date(rechargelist.getCreatedDate());
        holder.createdDate.setText(mFormat.format(date));

        holder.flowcode.setText(rechargelist.getFlowTypeDesc());
        if (rechargelist.getStatus() == 0)
        {
            //收入
            holder.rechargeAmount.setText("+" + String.valueOf(rechargelist.getAmount()) + "元");
        }
        else if (rechargelist.getStatus() == 1)
        {
            //支出
            holder.rechargeAmount.setText("-" + String.valueOf(rechargelist.getAmount()) + "元");
        }
        if (rechargelist.getState() == 1)
        {
            holder.statusName.setText("成功");
            holder.statusName.setTextColor(Color.parseColor("#ef6963"));
        }
        else if (rechargelist.getState() == 0)
        {
            holder.statusName.setText("失败");
            holder.statusName.setTextColor(Color.parseColor("#248ADE"));
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
        TextView flowcode;//交易流水号
        TextView rechargeAmount;//收入/支出
        TextView statusName;//状态
        TextView createdDate;//日期

        public ViewHolder(View itemView)
        {
            super(itemView);
            flowcode = (TextView) itemView.findViewById(R.id.tv_markhis_name);
            rechargeAmount = (TextView) itemView.findViewById(R.id.tv_markhis_valid);
            statusName = (TextView) itemView.findViewById(R.id.tv_markhis_money);
            createdDate = (TextView) itemView.findViewById(R.id.tv_markhis_time);

        }
    }
}
