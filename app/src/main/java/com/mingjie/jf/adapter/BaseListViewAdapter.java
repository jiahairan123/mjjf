package com.mingjie.jf.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.mingjie.jf.holder.BaseHolder;

import java.util.List;


/*
 *  @项目名：  CaffcooP2P 
 *  @包名：    caffp2p.android.adapters
 *  @类名:    BaseListViewAdapter
 *  @创建者:   陈小平
 *  @创建时间:  2015/10/27 14:56
 *  @描述：    listView Adapter基类
 */
public abstract class BaseListViewAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mResult;

    protected ListView mListView;

    public BaseListViewAdapter(Context mContext, List<T> result, ListView mListView) {
        this.mContext = mContext;
        this.mResult = result;
        this.mListView = mListView;
    }

    @Override
    public int getCount() {
        if (mResult != null) {
            return mResult.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mResult != null) {
            return mResult.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<T> holder = null;
        if (convertView == null) {
            // 1. 新建holder
            holder = getItemHolder();
            // 2. 加载RootView视图
            convertView = holder.getRootView();
        } else {
            // 复用convertView
            holder = (BaseHolder) convertView.getTag();
        }

        // 给holder添加数据

        T data = mResult.get(position);
        // 给holder设置每一条数据
        holder.setData(data, position, mResult);
        return convertView;
    }

    protected abstract BaseHolder<T> getItemHolder();

    public void setDataChanged(List<T> mResult) {

        this.mResult = mResult;
        this.notifyDataSetChanged();
    }
}

