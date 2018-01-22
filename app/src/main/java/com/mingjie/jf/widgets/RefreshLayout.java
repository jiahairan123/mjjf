package com.mingjie.jf.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.mingjie.jf.R;

/**
 * @项目名: CaffcooP2P
 * @包名: caffp2p.android.widgets
 * @类名: RefreshLayout
 * @创建者: chenxiaoping
 * @创建时间 2015/09/22 13:13
 * @描述 在google原生SwipeRefreshLayout基础上添加上拉加载功能，
 * 使用：
 * 1、默认为开启加载功能，不需要时，通过{@link #closeLoadMore}方法
 * 2、{@link #setOnLoadListener}设置加载监听方法
 * 3、onLoad方法里面实现加载的具体方法，{@link #setLoading}true开始，加载完成置为false
 * 4、是否为最后一页在实现时进行过滤
 */
public class RefreshLayout extends SwipeRefreshLayout
{
    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;
    /**
     * listview实例
     */
    private ListView mListView;

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

    /**
     * ListView的加载中footer
     */
    private View mListViewFooter;

    /**
     * 按下时的y坐标
     */
    private int mDownY;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;
    private boolean mIsCloseLoadMore = false;//是否关闭加载功能，默认不关闭

    /**
     * @param context
     */
    public RefreshLayout(Context context)
    {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //google建议的速率，当速率达到该值时，认为用户是在执行某种操作
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //        footView
        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.refresh_footer, null,
                false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);

        // 如果开启勒加载功能，初始化ListView对象，没开启则使用默认的SwipeRefreshLayout
        if (!mIsCloseLoadMore)
        {
            getListView();
        }
    }

    /**
     * 获取ListView对象
     */
    private void getListView()
    {
        int childs = getChildCount();
        if (childs > 0)
        {
            for (int i = 0; i < childs; i++)
            {
                if (getChildAt(i) instanceof ListView)
                {
                    mListView = (ListView) getChildAt(i);//获取到ListView
//                    mListView.setOnScrollListener(this);
                    return;
                }
            }
        }
    }

    /**
     * 是否正在加载
     *
     * @return
     */
    public boolean isLoading()
    {
        return isLoading;
    }

    /**
     * 关闭上拉加载功能
     */
    public void closeLoadMore()
    {
        this.mIsCloseLoadMore = true;
    }

    /**
     * 原生SwipeRefreshLayout使用的是事件拦截方法onInterceptTouchEvent()实现刷新，
     * 所以我们通过分发方法dispatchTouchEvent（）实现加载
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        final int action = event.getAction();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                // 按下时的Y值坐标
                mDownY = (int) event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动时的Y值坐标
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                //当条件达到可以加载时，既进行加载操作
                if (canLoad())
                {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 是否可以加载更多
     * 条件：
     * 1、当前不在加载状态{@link #isLoading}&
     * 2、符合上拉行为{@link #isPullUp()}&
     * 3、当前不在刷新状态{@link #isRefreshing()}&
     * 4、没有关闭加载功能{@link #mIsCloseLoadMore}&
     * 5、当前滑动到勒最后一个item{@link #isBottomItem}
     *
     * @return 只有上面条件全部满足时，才可以执行加载操作
     */
    private boolean canLoad()
    {
        return !isLoading && isPullUp() && !isRefreshing() && !mIsCloseLoadMore && isBottomItem();
    }

    /**
     * 判断是否到了最底部的item
     */
    private boolean isBottomItem()
    {
        if (mListView != null && mListView.getAdapter() != null)
        {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * 是否是上拉操作，只有在Down时的Y值大于Move时Y值且速率达到一定值，才算是上拉
     *
     * @return
     */
    private boolean isPullUp()
    {
        return (mDownY - mLastY) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData()
    {
        if (mOnLoadListener != null && !mIsCloseLoadMore)
        {
            //回调加载
            mOnLoadListener.onLoad();
        }
    }

    /**
     * 控制footView隐藏或显示
     *
     * @param loading
     */
    public void setLoading(boolean loading)
    {
        isLoading = loading;
        if (isLoading)
        {
            mListView.addFooterView(mListViewFooter);
        }
        else
        {
            if (mListView.getFooterViewsCount() > 0)
            {
                mListView.removeFooterView(mListViewFooter);
                mDownY = 0;
                mLastY = 0;
            }
        }
    }

    /**
     * 设置加载回调接口{@link OnLoadListener}的方法
     *
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener)
    {
        mOnLoadListener = loadListener;
    }

//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState)
//    {
//
//    }
//
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
//    {
//        //        if (canLoad()) {
//        //            loadData();
//        //        }
//    }

    /**
     * 加载回调接口
     *
     * @author mrsimple
     */
    public interface OnLoadListener
    {
        void onLoad();
    }
}
