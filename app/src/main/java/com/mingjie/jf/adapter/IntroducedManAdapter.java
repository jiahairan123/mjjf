package com.mingjie.jf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.IntroducedManBean;

import java.util.List;

/**
 * Created by jiahairan on 2017/8/7 15.
 */

public class IntroducedManAdapter extends BaseAdapter {

    private Context context;
    private List data;

    public void setData(List data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public IntroducedManAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        IntroducedManBean bean = (IntroducedManBean) data.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_introduced_man, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.amount.setText(bean.getAmount());
        holder.bidAmount.setText(bean.getBidAmount());
        holder.bidDateStr.setText(bean.getBidDateStr());
        holder.realName.setText(bean.getRealname());
        holder.title.setText(bean.getTitle());
        return convertView;
    }

    class ViewHolder {
        TextView realName, title, bidAmount, bidDateStr, amount;

        public ViewHolder(View view) {
            realName = (TextView) view.findViewById(R.id.item_introducedman_name);
            title = (TextView) view.findViewById(R.id.item_introducedman_title);
            bidAmount = (TextView) view.findViewById(R.id.item_introducedman_bidamount);
            bidDateStr = (TextView) view.findViewById(R.id.item_introducedman_time);
            amount = (TextView) view.findViewById(R.id.item_introducedman_gain);
        }

    }

}
