package com.mingjie.jf.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * <p>项目名称：My Application
 * <p>包   名： com.example.myapplication.widget
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 带上拉加载更多的recyclerview
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-18 15:19
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class JRecyclerView extends RecyclerView
{

    public final static int TYPE_NORMAL = 0;
    public final static int TYPE_FOOTER = 2;//底部--往往是loading_more

    private final Context context;
    private LinearLayoutManager mLayoutManager;
    private boolean canLoad;//是否打开加载更多功能
    private View footView;//加载更多的布局
    private OnLoadMoreListener mOnLoadMoreListener;//上拉加载监听
    private boolean isLoading = false;//当前是否正在加载更多
    private AutoLoadAdapter mAutoLoadAdapter;
    private int footViewId;
    private OnItemClickListener mOnItemClickListener;

    public JRecyclerView(Context context)
    {
        this(context, null);
    }

    public JRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public JRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init()
    {
        mLayoutManager = (LinearLayoutManager) getLayoutManager();
    }

    @Override
    public void onScrolled(int dx, int dy)
    {
        super.onScrolled(dx, dy);
        //        Log.i("hexj", "null != mOnLoadMoreListener" + (null != mOnLoadMoreListener) + ";canLoad=" + canLoad
        // + ";" +
        //                "!isLoading =" + !isLoading + ";mLayoutManager.findLastVisibleItemPosition()=" + (
        //                (LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition());
        if (null != mOnLoadMoreListener && canLoad && !isLoading && dy > 0)
        {
            int lastVisiblePosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
            if (lastVisiblePosition + 1 == mAutoLoadAdapter.getItemCount())
            {
                isLoading = true;
                mOnLoadMoreListener.onLoadMore();
            }
        }
    }

    public class AutoLoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        /**
         * 数据adapter
         */
        private RecyclerView.Adapter mInternalAdapter;

        public AutoLoadAdapter(RecyclerView.Adapter adapter)
        {
            mInternalAdapter = adapter;
        }

        @Override
        public int getItemViewType(int position)
        {
            int footerPosition = getItemCount() - 1;
            if (footerPosition == position && canLoad)
            {
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            if (viewType == TYPE_FOOTER)
            {
                if (footViewId != 0)
                {
                    return new FooterViewHolder(LayoutInflater.from(context).inflate(footViewId, parent, false));
                }
                else
                {
                    TextView tv = new TextView(context);
                    tv.setText("正在加载...");
                    parent.addView(tv);
                    return new FooterViewHolder(tv);
                }
            }
            else
            {
                return mInternalAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        public class FooterViewHolder extends RecyclerView.ViewHolder
        {

            public FooterViewHolder(View itemView)
            {
                super(itemView);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position)
        {
            int type = getItemViewType(position);
            if (type != TYPE_FOOTER)
            {
                mInternalAdapter.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mOnItemClickListener.onItemClick(position);
                    }
                });
            }
        }

        /**
         * 需要计算上加载更多和添加的头部俩个
         *
         * @return
         */
        @Override
        public int getItemCount()
        {
            int count = mInternalAdapter.getItemCount();
            if (canLoad)
            {
                count++;
            }
            return count;
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mOnItemClickListener = listener;
    }

    /**
     * 条目点击监听
     */
    public interface OnItemClickListener
    {
        public void onItemClick(int position);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter)
    {
        if (adapter != null)
        {
            mAutoLoadAdapter = new AutoLoadAdapter(adapter);
        }
        super.swapAdapter(mAutoLoadAdapter, true);
    }

    /**
     * 是否打开上拉刷新功能
     */
    public void setLoadMore(boolean canLoad)
    {
        this.canLoad = canLoad;
    }

    /**
     * 设置加载更多的布局
     */
    public void setLoadMoreView(int footViewId)
    {
        this.footViewId = footViewId;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener)
    {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public interface OnLoadMoreListener
    {
        public void onLoadMore();
    }

    /**
     * 加载更多完成
     */
    public void loadingSuccess()
    {
        this.isLoading = false;
    }

    /**
     * 当前是否正在加载更多
     *
     * @return
     */
    public boolean isLoading()
    {
        return isLoading;
    }
}
