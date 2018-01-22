package com.mingjie.jf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;


/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.View
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 自定义我的账户里面的菜单条目View
 * <p>创 建 人： wuxianai
 * <p>邮箱地址： wuxianai@ming-hao.cn
 * <p>创建时间： 2016/6/30 11:35
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AccountMenuContentItemView extends RelativeLayout {

    /**
     * 左侧图标
     */
    private ImageView mIvLeftIcon;

    /**
     * 中部标题
     */
    private TextView mTvMiddleTitle;


    /**
     * 右侧图标
     */
    private ImageView mIvRightIcon;


    public AccountMenuContentItemView(Context context) {
        super(context);
    }

    public AccountMenuContentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int resourceId = -1;
        View view = LayoutInflater.from(context).inflate(R.layout.widget_account_menu_content_item, this, true);

        mIvLeftIcon = (ImageView) view.findViewById(R.id.iv_left_icon);
        mTvMiddleTitle = (TextView) view.findViewById(R.id.tv_middle_title);
        mIvRightIcon = (ImageView) view.findViewById(R.id.iv_right_icon);

        //获取自定义参数，对自定义控件进行初始化设置
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AccountMenuContentItemView);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {

                //设置左侧图标资源属性
                case R.styleable.AccountMenuContentItemView_LeftIconSrc:
                    mIvLeftIcon.setImageResource(ta.getResourceId(R.styleable.AccountMenuContentItemView_LeftIconSrc, 0));
                    break;
                //设置左侧图标大小
                case R.styleable.AccountMenuContentItemView_LeftIconWidthAndHeight:
                    mIvLeftIcon.setMaxWidth(ta.getDimensionPixelSize(R.styleable.AccountMenuContentItemView_LeftIconWidthAndHeight, 35));
                    mIvLeftIcon.setMaxHeight(ta.getDimensionPixelSize(R.styleable.AccountMenuContentItemView_LeftIconWidthAndHeight, 35));
                    break;
                //设置左侧图标是否显示
                case R.styleable.AccountMenuContentItemView_isShowLeftIcon:
                    mIvLeftIcon.setVisibility(ta.getBoolean(R.styleable.AccountMenuContentItemView_isShowLeftIcon, false) ? View.VISIBLE : View.INVISIBLE);
                    break;

                //设置中部Title Text
                case R.styleable.AccountMenuContentItemView_middleTitleText:
                    mTvMiddleTitle.setText(ta.getString(R.styleable.AccountMenuContentItemView_middleTitleText));
                    break;
                //设置中部Title 字体颜色
                case R.styleable.AccountMenuContentItemView_middleTitleTextColor:
                    mTvMiddleTitle.setTextColor(ta.getColor(R.styleable.AccountMenuContentItemView_middleTitleTextColor, Color.BLACK));
                    break;
                //设置中部Title 字体大小
                case R.styleable.AccountMenuContentItemView_middleTitleTextSize:
                    mTvMiddleTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.AccountMenuContentItemView_middleTitleTextSize, 40));
                    break;

                //设置右侧侧图标资源属性
                case R.styleable.AccountMenuContentItemView_rightIconSrc:
                    mIvRightIcon.setImageResource(ta.getResourceId(R.styleable.AccountMenuContentItemView_rightIconSrc, 0));
                    break;
                //设置右侧侧图标大小
                case R.styleable.AccountMenuContentItemView_rightIconWidthAndHeight:
                    mIvRightIcon.setMaxWidth(ta.getDimensionPixelSize(R.styleable.AccountMenuContentItemView_rightIconWidthAndHeight, 35));
                    mIvRightIcon.setMaxHeight(ta.getDimensionPixelSize(R.styleable.AccountMenuContentItemView_rightIconWidthAndHeight, 35));
                    break;
                //设置右侧侧图标是否显示
                case R.styleable.AccountMenuContentItemView_isShowRightIcon:
                    mIvRightIcon.setVisibility(ta.getBoolean(R.styleable.AccountMenuContentItemView_isShowRightIcon, false) ? View.VISIBLE : View.INVISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    public void setRightIconImageResource(int resId) {
        mIvRightIcon.setImageResource(resId);
    }

    public void setLeftIcon(int res) {
        mIvLeftIcon.setImageResource(res);
    }

    public void setText(String text) {
        mTvMiddleTitle.setText(text);
    }
}
