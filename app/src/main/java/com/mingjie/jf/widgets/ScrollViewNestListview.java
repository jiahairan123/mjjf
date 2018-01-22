package com.mingjie.jf.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.widgets
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： scrollview 嵌套 listview
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-10-22 11:10
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ScrollViewNestListview extends ListView
{
    public ScrollViewNestListview(Context context) {
        super(context);

    }

    public ScrollViewNestListview(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
