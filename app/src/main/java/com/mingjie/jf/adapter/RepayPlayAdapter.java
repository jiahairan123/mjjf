package com.mingjie.jf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.RepayPlayResponse;
import com.mingjie.jf.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.adapter
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 标的详情-还款计划
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-23 17:26
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RepayPlayAdapter extends RecyclerView.Adapter<RepayPlayAdapter.MyViewHolder>
{

    private final Context mContext;
    private final List<RepayPlayResponse.ItemData> mDatas;

    public RepayPlayAdapter(Context context, List<RepayPlayResponse.ItemData> datas)
    {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_loan_detail_repayment_plan_list,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        RepayPlayResponse.ItemData data = mDatas.get(position);
        holder.ivAssetType.setImageLevel(data.getAssetType());
        holder.tvTitle.setText(data.getProductTitle());
        //如果还款方式是天为单位，期数为1
        if ("3".equals(data.getRepurchaseMode()))
        {
            holder.tvTerm.setText(data.getPlanIndex() + "/1");
        }
        else
        {
            holder.tvTerm.setText(data.getPlanIndex() + "/" + data.getProductDeadline());
        }
        holder.tvRepayDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(data.getExpireDate())));
        holder.tvPrincipal.setText(StringUtils.dou2Str(data.getCapital()));
        holder.tvInterest.setText(StringUtils.dou2Str(data.getInterest()));
        holder.tvFaXi.setText(StringUtils.dou2Str(data.getLateFee()));
        holder.tvStatus.setText(data.getStateDesc());
        holder.tvYingHuanZongE.setText(StringUtils.dou2Str(data.getInterest() + data.getCapital()));
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvYingHuanZongE;//应还总额
        TextView tvStatus;//状态
        TextView tvFaXi;//罚息
        TextView tvInterest;//利息
        TextView tvPrincipal;//本金
        TextView tvRepayDate;//还款日期
        TextView tvTerm;//期数
        TextView tvTitle;//标的标题
        ImageView ivAssetType;//标的类型

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ivAssetType = (ImageView) itemView.findViewById(R.id.creditor_loan_info_iv);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTerm = (TextView) itemView.findViewById(R.id.term_tv);
            tvRepayDate = (TextView) itemView.findViewById(R.id.tv_repay_date);
            tvPrincipal = (TextView) itemView.findViewById(R.id.tv_principal);
            tvInterest = (TextView) itemView.findViewById(R.id.tv_interest);
            tvFaXi = (TextView) itemView.findViewById(R.id.tv_faxi);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
            tvYingHuanZongE = (TextView) itemView.findViewById(R.id.tv_yinghuanzonge);
        }
    }
}
