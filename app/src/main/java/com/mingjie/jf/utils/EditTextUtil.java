package com.mingjie.jf.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Switch;

import com.mingjie.jf.R;

/**
 * Created by jiahairan on 2017/6/30 11.
 */

public class EditTextUtil extends EditText {

    private Drawable mDrawableRight;
    private boolean isOpen = false;

    public EditTextUtil(Context context) {
        super(context);
    }

    public EditTextUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init(){
        mDrawableRight = getCompoundDrawables()[2];
        if (mDrawableRight == null) {

        }
    }

    public void setmDrawableRight(boolean isOpen) {
        int drawabed;
        if (!isOpen){
            drawabed = R.mipmap.visiable_close;
        } else {
            drawabed = R.mipmap.visiable_open;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawableRight = getResources().getDrawable(drawabed, null);
        } else {
            mDrawableRight = getResources().getDrawable(drawabed);
        }
        mDrawableRight.setBounds(0, 0, mDrawableRight.getIntrinsicWidth(), mDrawableRight.getIntrinsicHeight());
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawableRight, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                Drawable drawable = getCompoundDrawables()[2];
                boolean isTouchRight = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (drawable != null && isTouchRight) {

                    // 记录状态
                    isOpen = !isOpen;
                    // 刷新DrawableRight
                    setmDrawableRight(isOpen);
                    // 执行点击事件-隐藏或显示
                    if (isOpen)
                        setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    else
                        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    // 设置光标位置
                    setSelection(getText().length());
                }
                break;
        }

        return super.onTouchEvent(event);
    }
}
