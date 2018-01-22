package com.mingjie.jf.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;


/**
 * @QINSIYI
 * @描述 Tab
 */
public class TabIndicator extends FrameLayout {
    private ImageView mIvIcon;
    private TextView mTvTitle;
    private TextView mTvUnread;

    public TabIndicator(Context context) {
        this(context,
             null);
    }

    public TabIndicator(Context context,
                        AttributeSet attrs) {
        super(context,
              attrs);

        //挂载xml布局
        View.inflate(context,
                     R.layout.tab_indicator,
                     this);

        mIvIcon = (ImageView) findViewById(R.id.tab_indicator_iv_icon);
        mTvTitle = (TextView) findViewById(R.id.tab_indicator_tv_title);
        mTvUnread = (TextView) findViewById(R.id.tab_indicator_tv_unread);

        setTabUnread(0);
    }

    public void setTabTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setTabIcon(int resId) {
        mIvIcon.setImageResource(resId);
    }

    public void setTabUnread(int count) {
        if (count <= 0) {
            // 隐藏
            mTvUnread.setVisibility(View.GONE);
        } else {
            if (count > 99) {
                mTvUnread.setText("99+");
            } else {
                mTvUnread.setText("" + count);
            }
            mTvUnread.setVisibility(View.VISIBLE);
        }
    }
}
