package com.mingjie.jf.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.ReturnCalendarDayBean;
import com.mingjie.jf.utils.StringUtils;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.adapter
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 日历adapter
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-12 14:11
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ReturnCalendarAdapter extends BaseAdapter
{
    private final Context context;
    private final List<ReturnCalendarDayBean> data;

    public ReturnCalendarAdapter(Context context, List<ReturnCalendarDayBean> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = View.inflate(context, R.layout.item_calendar, null);
            TextView tv01 = (TextView) convertView.findViewById(R.id.tv_01);
            TextView tv02 = (TextView) convertView.findViewById(R.id.tv_02);
            TextView tv03 = (TextView) convertView.findViewById(R.id.tv_03);
            TextView tv04 = (TextView) convertView.findViewById(R.id.tv_04);
            holder = new ViewHolder(tv01, tv02, tv03, tv04);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        ReturnCalendarDayBean bean = data.get(position);
        holder.tv01.setText(StringUtils.dou2Str(bean.getCapital()));
        holder.tv02.setText(StringUtils.dou2Str(bean.getInterest()));
        holder.tv03.setText(StringUtils.dou2Str(bean.getCapital() + bean.getInterest()));
        if (bean.getState() == 2 || bean.getState() == 5 || bean.getState() == 13)
        {
            holder.tv04.setText("已还");
        }
        else
        {
            holder.tv04.setText("待还");
        }
        return convertView;
    }

    static class ViewHolder
    {
        TextView tv01;
        TextView tv02;
        TextView tv03;
        TextView tv04;

        public ViewHolder(TextView tv01, TextView tv02, TextView tv03, TextView tv04)
        {
            this.tv01 = tv01;
            this.tv02 = tv02;
            this.tv03 = tv03;
            this.tv04 = tv04;
        }
    }

}
