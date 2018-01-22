package com.mingjie.jf.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.view
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 解决刷新控件包含的子控件不是只有listview时滑动冲突的刷新控件
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-11-15 10:03
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class SimpleSwipeRefreshLayout extends SwipeRefreshLayout
{

    private View view;

    public SimpleSwipeRefreshLayout(Context context)
    {
        super(context);
    }

    public SimpleSwipeRefreshLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setViewGroup(View view) {
        this.view = view;
    }

    @Override
    public boolean canChildScrollUp()
    {
        if (view != null && view instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) view;
            return absListView.getChildCount() > 0
                    && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                    .getTop() < absListView.getPaddingTop());
        }
        return super.canChildScrollUp();
    }
}
