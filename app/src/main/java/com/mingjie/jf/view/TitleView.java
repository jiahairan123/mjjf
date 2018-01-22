package com.mingjie.jf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;

/**
 * Created by zhangjin on 2016/8/10.
 */
public class TitleView extends RelativeLayout
{

    TextView tvwTitle;
    TextView tvwRight;

    ImageView ivwLeft;
    ImageView ivwRight;

    public TitleView(Context context)
    {
        super(context);
    }

    public TitleView(final Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.public_title_blue, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.titleView);

        initView(typedArray);
    }

    private void initView(TypedArray typedArray)
    {
        // 获取属性值
        int iconLeft = typedArray.getResourceId(R.styleable.titleView_icon_left, 0);
        int iconRight = typedArray.getResourceId(R.styleable.titleView_icon_right, 0);
        String title = typedArray.getString(R.styleable.titleView_title_text);
        String textRight = typedArray.getString(R.styleable.titleView_text_right);
        boolean showRightText = typedArray.getBoolean(R.styleable.titleView_showRightText, false);
        int bgColor = typedArray.getColor(R.styleable.titleView_title_bg, 0);

        ivwLeft = (ImageView) findViewById(R.id.iv_Left_public);
        ivwRight = (ImageView) findViewById(R.id.iv_right_public);
        tvwTitle = (TextView) findViewById(R.id.tv_content_public);
        tvwRight = (TextView) findViewById(R.id.tv_right_public);

        tvwTitle.setText(title);

        if (iconLeft > 0)
        {
            ivwLeft.setImageResource(iconLeft);
        }

        if (iconRight > 0)
        {
            ivwRight.setImageResource(iconLeft);
        }

        if (!TextUtils.isEmpty(textRight))
        {
            tvwRight.setText(textRight);
            tvwRight.setVisibility(View.VISIBLE);
        }

        if (bgColor > 0)
        {
            this.setBackgroundColor(bgColor);
        }

        if (showRightText)
        {
            tvwRight.setVisibility(VISIBLE);
        }

        typedArray.recycle();
    }

    public void setOnClickLitenerLeft(OnClickListener lsn)
    {
        ivwLeft.setOnClickListener(lsn);
    }

    public void setTitle(String text)
    {
        tvwTitle.setText(text);
    }

    public void setTextRight(String text)
    {
        tvwRight.setText(text);
        tvwRight.setVisibility(VISIBLE);
    }

    public void setImgRight(int resId)
    {
        ivwRight.setImageResource(resId);
    }

    public void setOnClickLitenerRight(OnClickListener lsn)
    {
        ivwRight.setOnClickListener(lsn);
    }
}
