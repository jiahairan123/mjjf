package com.mingjie.jf.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.IntroducerGainListBean;

import java.util.List;

/**
 * Created by jiahairan on 2017/8/7 10.
 */

class IntroducerGainListAdapter extends BaseAdapter {
    Context context;
    List data;

    public IntroducerGainListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List data) {
        this.data = data;
        notifyDataSetChanged();
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
        IntroducerGainListBean bean = (IntroducerGainListBean) data.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_introduce_gainlist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.realName.setText(bean.getRealname());
        holder.realStatus.setText(bean.getIsFundSepositoryStr());
        holder.matchTime.setText(bean.getIntroducerInsDateStr());
        holder.phone.setText(bean.getPhone());
        return convertView;
    }

    class ViewHolder {
        TextView realName, phone, realStatus, matchTime;

        public ViewHolder(View view) {
            realName = (TextView) view.findViewById(R.id.item_gainlist_realname);
            phone = (TextView) view.findViewById(R.id.item_gainlist_phone);
            realStatus = (TextView) view.findViewById(R.id.item_gainlist_isFundSepositoryStr);
            matchTime = (TextView) view.findViewById(R.id.item_gainlist_matchtime);
        }

    }

}
