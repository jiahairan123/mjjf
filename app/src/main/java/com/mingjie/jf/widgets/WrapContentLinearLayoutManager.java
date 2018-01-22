package com.mingjie.jf.widgets;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.mingjie.jf.utils.CfLog;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.widgets
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： RecyclerView有时会报出IndexOutOfBoundsException，这里复写manager将异常捕获
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2017-01-04 13:56
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class WrapContentLinearLayoutManager extends LinearLayoutManager
{
    public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            CfLog.e(e);
        }
    }
}
