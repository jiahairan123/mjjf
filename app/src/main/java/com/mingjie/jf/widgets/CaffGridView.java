package com.mingjie.jf.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


public class CaffGridView extends GridView {

    public CaffGridView(Context context) {
        super(context);
    }

    public CaffGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CaffGridView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
