package com.mingjie.jf.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.CaseManager;
import com.mingjie.jf.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-11-23 16:44
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class CaseManagerAdapter extends RecyclerView.Adapter<CaseManagerAdapter.MyViewHolder>
{
    //private final SimpleDateFormat mFormat;
    // private String mStyle = "yyy-MM-dd HH:mm:ss";//时间格式
    private Context mContext;
    private List<CaseManager.WalletFlowBean.ListBean> list;
    // private final JRecyclerView jRecyclerView;
    private CaseManager.WalletFlowBean.ListBean casedata;
    // private String beizhu;

    public CaseManagerAdapter(Context context, List<CaseManager.WalletFlowBean.ListBean> list)
    {
        this.mContext = context;
        this.list = list;
        // mFormat = new SimpleDateFormat(mStyle);
        // this.jRecyclerView = jRecyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mark_history, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        casedata = list.get(position);

        //时间
        holder.tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(casedata.getCreateDate())));
        holder.tvMoney.setText(casedata.getDescription());//备注
        holder.tvMoney.setTextColor(Color.parseColor("#434343"));

        if (casedata.getState() == 1)
        {
            // holder.tvMoney.setTextColor(Color.parseColor("#ef6963"));
            holder.tvValid.setText("+" + StringUtils.dou2Str(casedata.getAmount()));//金额
        }
        else if (casedata.getState() == 2)
        {
            // holder.tvMoney.setTextColor(Color.parseColor("#248ADE"));
            holder.tvValid.setText("-" + StringUtils.dou2Str(casedata.getAmount()));
        }
        else
        {
            holder.tvValid.setText(StringUtils.dou2Str(casedata.getAmount()));
        }
        holder.tvName.setText(casedata.getFlowTypeDesc());
        holder.tvFrom.setTextColor(Color.parseColor("#ef6963"));
        holder.tvFrom.setText("成功");//增加来源备注
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName;//交易类型
        TextView tvTime;//时间
        TextView tvValid;//金额
        TextView tvMoney;//状态
        TextView tvFrom;//来源备注

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_markhis_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_markhis_time);
            tvValid = (TextView) itemView.findViewById(R.id.tv_markhis_valid);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_markhis_money);
            tvFrom = (TextView) itemView.findViewById(R.id.tv_from);
        }
    }
}
