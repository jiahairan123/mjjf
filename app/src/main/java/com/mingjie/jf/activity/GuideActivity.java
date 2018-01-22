package com.mingjie.jf.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.GuidePagerAdapter;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.view.MateralButton;
import com.mingjie.jf.widgets.CirclePageIndicator;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @描述 引导页面activity
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.pager_guide)
    ViewPager mViewPager;
    @BindView(R.id.indicator_guide)
    CirclePageIndicator mIndicator;
    @BindView(R.id.btn_guide)
    MateralButton mBtnHome;

    private int[] images = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3,R.mipmap.guide_4,R.mipmap.guide_5};

    @Override
    protected void initView() {

        // 去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        closeImmerseStyle();
        setContentView(R.layout.activity_guide);
        // ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mViewPager.setAdapter(new GuidePagerAdapter(this, images));
        mIndicator.setViewPager(mViewPager);

        mIndicator.setOnPageChangeListener(this);
    }

    @OnClick(R.id.btn_guide)
    public void startBtn(View v) {
        goHome();
    }

    private void goHome() {
        // 设置已经开启过应用
        CacheUtils.setBoolean(this, Constants.KEY_FIRST_START, false);
        //  进入首页
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private Animation initAnimation() {
        return AnimationUtils.loadAnimation(this, R.anim.fadein);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        // 滑动到了最后一张图片，显示进入首页按钮
        if (position == images.length - 1) {
            mBtnHome.setVisibility(View.VISIBLE);
            mBtnHome.startAnimation(initAnimation());
        } else {
            mBtnHome.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}