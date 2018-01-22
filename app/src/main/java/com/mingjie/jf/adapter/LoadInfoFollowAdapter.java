package com.mingjie.jf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.LoanPersonFollow;

import java.util.List;

/**
 * Created by jiahairan on 2018/1/4 11.
 */

public class LoadInfoFollowAdapter extends RecyclerView.Adapter<LoadInfoFollowAdapter.MyViewHolder> {

    private Context context;
    private List<LoanPersonFollow> list;

    public LoadInfoFollowAdapter(Context context, List<LoanPersonFollow> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loan_person_follow, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        LoanPersonFollow data = list.get(position);
        holder.slipindex.setText(data.getSlipindexStr()); //披露期数
        //披露日期
        if (data.getSlipdateyesStr() == null || data.getSlipdateyesStr().isEmpty()){
            holder.slipdateStr.setText(data.getSlipdateStr());
        } else {
            holder.slipdateStr.setText(data.getSlipdateyesStr());
        }
        holder.borrowerfunding.setText(data.getBorrowerfunding()); //资金运用情况
        holder.borrowerfinance.setText(data.getBorrowerfinance()); //经营状况及财务状况
        holder.borrowerrepaystate.setText(data.getBorrowerrepaystate()); //还款能力变化
        holder.borrowerpunish.setText(data.getBorrowerpunish());  //涉诉或收到行政处罚信息

    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        /**
         * 披露期数：slipindex
         * 披露日期：slipdateyesStr--》slipdateStr
         * 资金运用情况：borrowerfunding
         * 经营状况及财务状况：borrowerfinance
         * 还款能力变化：borrowerrepaystate
         * 涉诉或收到行政处罚信息：borrowerpunish
         */
        TextView slipindex;
        TextView slipdateStr;
        TextView borrowerfunding;
        TextView borrowerfinance;
        TextView borrowerrepaystate;
        TextView borrowerpunish;

        public MyViewHolder(View itemView) {
            super(itemView);
            slipindex = (TextView) itemView.findViewById(R.id.tv_slipindex);
            slipdateStr = (TextView) itemView.findViewById(R.id.tv_slipdateStr);
            borrowerfunding = (TextView) itemView.findViewById(R.id.tv_borrowerfunding);
            borrowerfinance = (TextView) itemView.findViewById(R.id.tv_borrowerfinance);
            borrowerrepaystate = (TextView) itemView.findViewById(R.id.tv_borrowerrepaystate);
            borrowerpunish = (TextView) itemView.findViewById(R.id.tv_borrowerpunish);
        }
    }
}
