package com.mingjie.jf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.AnnounceMent;
import com.mingjie.jf.widgets.JRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by QinSiYi on 2016-8-22.
 */
public class ToldAdapter extends RecyclerView.Adapter<ToldAdapter.ViewHolder>
{
    private final SimpleDateFormat mFormat;
    private String mStyle = "yyy-MM-dd HH:mm:ss";//时间格式
    private Context mContext;
    private List<AnnounceMent.DataBean.ListBean> dataBeen;
    private final JRecyclerView jRecyclerView;

    public ToldAdapter(Context context, List<AnnounceMent.DataBean.ListBean> dataBeen, JRecyclerView jRecyclerView)
    {
        this.mContext = context;
        this.dataBeen = dataBeen;
        mFormat = new SimpleDateFormat(mStyle);
        this.jRecyclerView = jRecyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.told_listview, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        AnnounceMent.DataBean.ListBean tolddata = dataBeen.get(position);
        Date date = new Date(tolddata.getCreatedDate());
        viewHolder.time.setText(mFormat.format(date));
        viewHolder.toldtitle.setText(tolddata.getTitle());
        viewHolder.arrow.setImageResource(R.mipmap.right_arrow_icon);

    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public int getItemCount()
    {
        return dataBeen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView toldtitle;
        TextView time;
        ImageView arrow;

        public ViewHolder(View itemView)
        {
            super(itemView);
            toldtitle = (TextView) itemView.findViewById(
                    R.id.told_title);
            time = (TextView) itemView.findViewById(
                    R.id.tv_sysnotice_time);
            arrow = (ImageView) itemView.findViewById(
                    R.id.iv_home_arrow);
        }
    }
}
