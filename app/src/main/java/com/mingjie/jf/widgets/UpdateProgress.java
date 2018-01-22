package com.mingjie.jf.widgets;
/*
 *  @项目名：  UpdateProgress 
 *  @包名：    caffcoo.com.updateprogress
 *  @类名:    UpdateProgress
 *  @创建者:   陈小平
 *  @创建时间:  2015/10/2015/10/29 09:20
 *  @描述：    带百分比的自定义进度条
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class UpdateProgress extends TextView {

    private int mProgress;//进度
    private int mMax;//最大进度
    private Drawable mProgressDrawable;//进度背景

    public UpdateProgress(Context context) {
        this(context, null);
    }

    public UpdateProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setMax(int max) {
        this.mMax = max;
    }

    public void setProgressDrawable(Drawable drawable) {
        this.mProgressDrawable = drawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //        获得控件测量后的宽高
        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();

        int right = 0;
        if (mMax == 0) {
            //            默认Max是100
            right = (int) (width * mProgress / 100f + 0.5f);
        } else {
            right = (int) (width * mProgress * 1f / mMax + 0.5f);
        }

        // 设置矩形
        mProgressDrawable.setBounds(0, 0, right, getMeasuredHeight());
        // 画的操作
        mProgressDrawable.draw(canvas);

        //        父类onDraw是画文字
        super.onDraw(canvas);
    }
}
