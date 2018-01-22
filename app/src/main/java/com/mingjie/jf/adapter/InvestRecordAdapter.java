package com.mingjie.jf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.InvestRecordResponse;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.adapter
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 标的详情-投资记录
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-24 10:44
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class InvestRecordAdapter extends RecyclerView.Adapter<InvestRecordAdapter.MyViewHolder>
{

    private final Context mContext;
    private final List<InvestRecordResponse> mDatas;

    public InvestRecordAdapter(Context context, List<InvestRecordResponse> mDatas)
    {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mark_history, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        InvestRecordResponse data = mDatas.get(position);
        //        holder.tvName.setText(data.getUserName());//投标人

        UIUtils.hintUserName(holder.tvName, data.getUserName());

        //最后投资时间
        holder.tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(data.getLastestbidtime())));
        holder.tvValid.setText(StringUtils.dou2Str(data.getAmount()) + "元");//意向投标金额
        if (data.getReserve1() == 1)
        {
            holder.tvMoney.setText("自动投标");
        }
        else if (data.getReserve1() == 0)
        {
            holder.tvMoney.setText("手动投标");
        }

        if (data.getBidSource() == 1)
        {
            holder.from.setText("电脑");
        }
        else if (data.getBidSource() == 2)
        {
            holder.from.setText("移动端");
        }
        else if (data.getBidSource() == 3)
        {
            holder.from.setText("微信");
        }
        else if (data.getBidSource() == 4)
        {
            holder.from.setText("安卓");
        }
        else if (data.getBidSource() == 5)
        {
            holder.from.setText("苹果");
        }
        else
        {
            holder.from.setText("电脑");
        }
        //        holder.tvMoney.setText(data.getReserve1());//投标方式   1自动投标  0手动投标
        //        holder.tvCount.setText("投" + data.getBidCount() + "次");
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName;//投标人
        TextView tvTime;//最近投标时间
        TextView tvValid;//意向投标金额
        TextView tvMoney;//投标方式
        TextView from;//投标来源
        //        TextView tvCount;//投标次数

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_markhis_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_markhis_time);
            tvValid = (TextView) itemView.findViewById(R.id.tv_markhis_valid);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_markhis_money);
            from = (TextView) itemView.findViewById(R.id.tv_from);
            //            tvCount = (TextView) itemView.findViewById(R.id.tv_markhis_count);
        }
    }
}
