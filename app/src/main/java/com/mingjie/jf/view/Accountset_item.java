package com.mingjie.jf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;


/**
 * 账户设置自定义item
 */
public class Accountset_item extends RelativeLayout {

    private TextView mTvAcStatus;
    private TextView mTvAction;
    private TextView mTvTitle;
    private ImageView mIvLeft;

    public Accountset_item(Context context) {
        super(context);
    }

    public Accountset_item(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.widget_account_set_item, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AccountsetItemView);

        initView(typedArray);
    }

    private void initView(TypedArray typedArray) {
        //        获取属性值
        int leftIcon = typedArray.getResourceId(R.styleable.AccountsetItemView_Accountset_Left_icon, 0);
        boolean isShowStatus = typedArray.getBoolean(R.styleable.AccountsetItemView_isShowStatus, false);
        String title = typedArray.getString(R.styleable.AccountsetItemView_Accountset_content_text);
        String action = typedArray.getString(R.styleable.AccountsetItemView_Accountset_action);
        String status = typedArray.getString(R.styleable.AccountsetItemView_Accountset_status);
        int color = typedArray.getColor(R.styleable.AccountsetItemView_Status_color, getResources().getColor(R.color.color_accountset_text));

        mIvLeft = (ImageView) findViewById(R.id.iv_acc_status);
        mTvTitle = (TextView) findViewById(R.id.tv_acc_title);
        mTvAction = (TextView) findViewById(R.id.tv_account_action);
        mTvAcStatus = (TextView) findViewById(R.id.tv_account_status);

        mTvAcStatus.setTextColor(color);
        mIvLeft.setImageResource(leftIcon);
        mTvTitle.setText(title);
        mTvAction.setText(action);
        mTvAction.setTextColor(color);
        if (isShowStatus) {
            mTvAcStatus.setVisibility(VISIBLE);
            mTvAcStatus.setText(status);
        } else {
            mTvAcStatus.setVisibility(GONE);
        }
        typedArray.recycle();
    }

    public void setStateText(String text) {
        mTvAcStatus.setText(text);
    }

    public void setActionText(String text) {
        mTvAction.setText(text);
    }

}
