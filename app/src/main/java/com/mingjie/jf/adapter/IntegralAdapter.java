package com.mingjie.jf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.IntegralBean;

import java.util.List;

/**
 * Created by jiahairan on 2017/8/3 10.
 */

public class IntegralAdapter extends BaseAdapter {
    Context context;
    List data;


    public IntegralAdapter(Context context) {
        this.context = context;

    }

    public void setData(List data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
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
        IntegralBean bean = (IntegralBean) data.get(position);
        IntegralHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_integral, null);
            holder = new IntegralHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (IntegralHolder) convertView.getTag();
        }

        holder.back1.setText(bean.getBack1Str());
        holder.date.setText(bean.getInsDateStr());
        holder.usePoint.setText(bean.getUsedPointStr());

        return convertView;
    }


    class IntegralHolder{
        TextView back1, date, usePoint;

        public IntegralHolder(View view) {
             date = (TextView) view.findViewById(R.id.item_date);
             usePoint = (TextView) view.findViewById(R.id.item_usePoint);
             back1 = (TextView) view.findViewById(R.id.item_back1);
        }
    }


}
