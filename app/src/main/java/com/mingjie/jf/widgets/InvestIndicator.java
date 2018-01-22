package com.mingjie.jf.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingjie.jf.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/*
 *  @项目名：  CaffcooP2P 
 *  @包名：    caffp2p.android.widgets
 *  @类名:    InvestIndicator
 *  @创建者:   陈小平
 *  @创建时间:  2015/10/23 16:02
 *  @描述：    我要投资页面tab
 */public class InvestIndicator extends LinearLayout implements View.OnClickListener {

    private String[] mTabNameArray;
    private int mCurrenChecked = 1;//当前选中Tab
    private LinearLayout mTabRootView1;
    private LinearLayout mTabRootView2;
    private LinearLayout mTabRootView3;
    private LinearLayout mTabRootView4;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;

    private boolean mTab1IsSelect = true;
    private boolean mTab2IsSelect = false;
    private boolean mTab3IsSelect = false;
    private boolean mTab4IsSelect = false;

    private Map<TextView, LinearLayout> mAddedView;
    private boolean isSelect = false;
    private OnTabChangeListener listener;
    private boolean isHideArrow = false;

    private int mTabCount = 4;//tab的个数，默认4个

    public InvestIndicator(Context context) {
        super(context);
    }

    public InvestIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.public_invest_select, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InvestIndicator);

        //        tab个数
        int childs = typedArray.getInteger(R.styleable.InvestIndicator_invest_tabs, 2);
        String tabNames = typedArray.getString(R.styleable.InvestIndicator_invest_tabNames);
        mTabNameArray = tabNames.split(",");

        mAddedView = new HashMap<>();

        mTabRootView1 = (LinearLayout) findViewById(R.id.invest_tab1);
        mTabRootView2 = (LinearLayout) findViewById(R.id.invest_tab2);
        mTabRootView3 = (LinearLayout) findViewById(R.id.invest_tab3);
        mTabRootView4 = (LinearLayout) findViewById(R.id.invest_tab4);

        mTv1 = (TextView) findViewById(R.id.tv_invest_tab_new);
        mTv2 = (TextView) findViewById(R.id.tv_invest_tab_rate);
        mTv3 = (TextView) findViewById(R.id.tv_invest_tab_limit);
        mTv4 = (TextView) findViewById(R.id.tv_invest_tab_money);
        if (mTabNameArray.length == 2) {
            mTv1.setText(mTabNameArray[0]);
            mTv4.setText(mTabNameArray[1]);
        } else if (mTabNameArray.length == 3) {
            mTv1.setText(mTabNameArray[0]);
            mTv2.setText(mTabNameArray[1]);
            mTv4.setText(mTabNameArray[2]);
        } else if (mTabNameArray.length == 4) {
            mTv1.setText(mTabNameArray[0]);
            mTv2.setText(mTabNameArray[1]);
            mTv3.setText(mTabNameArray[2]);
            mTv4.setText(mTabNameArray[3]);
        }

        mTabRootView1.setOnClickListener(this);
        mTabRootView2.setOnClickListener(this);
        mTabRootView3.setOnClickListener(this);
        mTabRootView4.setOnClickListener(this);

        initViews(childs);
        mTv1.setTextSize(15);
        mTv2.setTextSize(15);
        mTv3.setTextSize(15);
        mTv4.setTextSize(15);

    }

    private void initViews(int childs) {
        switch (childs) {
            case 2:
                mAddedView.put(mTv1, mTabRootView1);
                mAddedView.put(mTv4, mTabRootView4);
                mTabRootView2.setVisibility(GONE);
                mTabRootView3.setVisibility(GONE);

                mTabCount = 2;

                break;
            case 3:
                mAddedView.put(mTv1, mTabRootView1);
                mAddedView.put(mTv2, mTabRootView2);
                mAddedView.put(mTv4, mTabRootView4);
                mTabRootView3.setVisibility(GONE);

                mTabCount = 3;
                break;
            case 4:
                mAddedView.put(mTv1, mTabRootView1);
                mAddedView.put(mTv2, mTabRootView2);
                mAddedView.put(mTv3, mTabRootView3);
                mAddedView.put(mTv4, mTabRootView4);
                break;
            default:
                break;
        }
    }

    enum TABCOUNT {
        ONE(1), TWO(2), THREE(3), FOR(4);

        public int count;

        TABCOUNT(int count) {
            this.count = count;
        }

    }

    public void setTabCount(TABCOUNT count) {

        initViews(count.count);
    }

    private void unSelect(TextView v) {
        Set<TextView> textViews = mAddedView.keySet();
        Iterator<TextView> iterator = textViews.iterator();
        while (iterator.hasNext()) {
            TextView next = iterator.next();
            if (next.getId() != v.getId()) {
                updateView(mAddedView.get(next), next, "normal");
            }
        }
    }

    public void hideArrow() {
        mTv1.setCompoundDrawables(null, null, null, null);
        mTv2.setCompoundDrawables(null, null, null, null);
        mTv3.setCompoundDrawables(null, null, null, null);
        mTv4.setCompoundDrawables(null, null, null, null);

        isHideArrow = true;
    }

    /**
     * 设置tab的名字
     *
     * @param texts
     */
    public void setTabText(String[] texts) {
        TextView[] textViewList = new TextView[]{mTv1, mTv2, mTv3, mTv4};
        for (int i = 0; i < texts.length; i++) {
            textViewList[i].setText(texts[i]);
        }
//        for (int i = 0; i < texts.length; i++) {
//            if (i == 0) {
//                mTv1.setText(texts[i]);
//            } else if (i == 1) {
//                if (mTabCount == 2) {
//                    mTv4.setText(texts[i]);
//                    return;
//                }
//                mTv2.setText(texts[i]);
//            } else if (i == 2) {
//                if (mTabCount == 3) {
//                    mTv4.setText(texts[i]);
//                    return;
//                }
//                mTv3.setText(texts[i]);
//            } else {
//                mTv4.setText(texts[i]);
//            }
//        }
    }

    private void updateView(LinearLayout mRootView, TextView view, String status) {

        switch (status) {
            case "normal":
                if (view == mTv1) {
                    mTv1.setSelected(false);
                    mRootView.setBackgroundResource(R.drawable.shape_invest_tab_left_normal);
                } else if (view == mTv2) {
                    mTv2.setSelected(false);
                    mRootView.setBackgroundResource(R.drawable.shape_invest_tab_mid_normal);
                } else if (view == mTv3) {
                    mTv3.setSelected(false);
                    mRootView.setBackgroundResource(R.drawable.shape_invest_tab_mid_normal);
                } else {
                    mTv4.setSelected(false);
                    mRootView.setBackgroundResource(R.drawable.shape_invest_tab_right_normal);
                }
                view.setTextColor(Color.BLACK);
                break;
            case "select":
                if (view == mTv1) {
                    mTv1.setSelected(!mTv1.isSelected());
                    mRootView.setBackgroundResource(R.drawable.shape_invest_tab_left_select);
                } else if (view == mTv2) {
                    if (!isHideArrow) {
                        if (mTv2.isSelected()) {
                            Drawable drawable = getResources().getDrawable(R.mipmap.invest_arrow_up);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            mTv2.setCompoundDrawables(null, null, drawable, null);
                            mTv2.setSelected(false);
                        } else {
                            Drawable drawable = getResources().getDrawable(R.mipmap.invest_arrow_down);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            mTv2.setCompoundDrawables(null, null, drawable, null);
                            mTv2.setSelected(true);
                        }
                    }
                    mRootView.setBackgroundResource(R.drawable.shape_invest_tab_mid_select);
                } else if (view == mTv3) {
                    if (!isHideArrow) {
                        if (mTv3.isSelected()) {
                            Drawable drawable = getResources().getDrawable(R.mipmap.invest_arrow_up);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            mTv3.setCompoundDrawables(null, null, drawable, null);
                            mTv3.setSelected(false);
                        } else {
                            Drawable drawable = getResources().getDrawable(R.mipmap.invest_arrow_down);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            mTv3.setCompoundDrawables(null, null, drawable, null);
                            mTv3.setSelected(true);
                        }
                    }
                    mRootView.setBackgroundResource(R.drawable.shape_invest_tab_mid_select);
                } else {

                    if (!isHideArrow) {
                        if (mTv4.isSelected()) {
                            Drawable drawable = getResources().getDrawable(R.mipmap.invest_arrow_up);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            mTv4.setCompoundDrawables(null, null, drawable, null);
                            mTv4.setSelected(false);
                        } else {
                            Drawable drawable = getResources().getDrawable(R.mipmap.invest_arrow_down);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            mTv4.setCompoundDrawables(null, null, drawable, null);
                            mTv4.setSelected(true);
                        }
                    }
                    mRootView.setBackgroundResource(R.drawable.shape_invest_tab_right_select);
                }
                view.setTextColor(Color.WHITE);
                break;
            default:
                break;
        }

    }

    /**
     * 设置Tab点击事件
     *
     * @param listener
     */
    public void setOnTabViewItemClickListener(OnTabChangeListener listener) {

        this.listener = listener;
    }

    public interface OnTabChangeListener {
        /**
         * @param position 当前tab
         * @param isUpSoft true为升序，false为降序
         */
        void setOnTabChangeListener(int position, boolean isUpSoft);
    }

    private boolean mCurrentSelect;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invest_tab1:
                updateView(mTabRootView1, mTv1, "select");
                unSelect(mTv1);
                mCurrenChecked = 1;
                mCurrentSelect = mTv1.isSelected();
                break;
            case R.id.invest_tab2:
                updateView(mTabRootView2, mTv2, "select");
                unSelect(mTv2);
                mCurrentSelect = mTv2.isSelected();
                mCurrenChecked = 2;
                break;
            case R.id.invest_tab3:
                updateView(mTabRootView3, mTv3, "select");
                unSelect(mTv3);
                mCurrentSelect = mTv3.isSelected();
                mCurrenChecked = 3;
                break;
            case R.id.invest_tab4:
                updateView(mTabRootView4, mTv4, "select");
                unSelect(mTv4);
                mCurrentSelect = mTv4.isSelected();
                mCurrenChecked = 4;
                break;
            default:
                break;
        }

        if (listener != null) {
            listener.setOnTabChangeListener(mCurrenChecked, mCurrentSelect);
        }

    }
}
