package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.mingjie.jf.adapter.BaseListViewAdapter;

import java.util.List;


/**
 * @项目名: CaffcooP2P
 * @包名: caffp2p.android.holder
 * @类名: BaseHolder
 * @创建者: 陈小平
 * @创建时间 2015/8/19 11:20
 * @描述 BaseHolder
 */
public abstract class BaseHolder<T> {
	protected List<T> mDatas;
	protected View mRootView; // 根视图
	protected T mData; // 数据
	protected Context mContext;
	protected BaseListViewAdapter mAdapter;

	protected ListView mListView;

	public BaseHolder(BaseListViewAdapter adapter, Context mContext,
					  ListView mListView) {
		this.mContext = mContext;
		mRootView = initView();
		this.mListView = mListView;
		this.mAdapter = adapter;
		// 设置标记
		mRootView.setTag(this);
	}

	/**
	 * 实现view的布局
	 *
	 * @return
	 */
	protected abstract View initView();

	/**
	 * 让子类根据数据来刷新自己的视图
	 *
	 * @param data
	 */
	protected abstract void refreshUI(T data, int position);

	/**
	 * 获取根布局
	 *
	 * @return
	 */
	public View getRootView() {
		return mRootView;
	}

	/** 设置数据 **/
	public void setData(T data, int position, List<T> mDatas) {
		// 保存数据
		this.mData = data;
		this.mDatas = mDatas;
		// 通过数据来改变UI显示
		refreshUI(data, position);
	}

}
