package com.mingjie.jf.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.mingjie.jf.R;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.FragmentFactory;
import com.mingjie.jf.utils.TestProductFragmentFactory;
import com.mingjie.jf.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 投资fragment
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址：qinsiyi@ming-hao.cn
 * <p>创建时间：2016/8/5
 * <p>当前版本：V1.0.0
 * <p>修订历史：(版本、修改时间、修改人、修改内容)
 */
public class InvestFragment extends BaseFragment implements ViewPager.OnPageChangeListener, View.OnClickListener
{

    @BindView(R.id.invest_tabs1)
    PagerSlidingTabStrip investTabs;
    @BindView(R.id.viewpager_ivest1)
    ViewPager viewpagerIvest;

    @BindView(R.id.iv_Left_public)//返回
            ImageView ivLeftBack;
    @BindView(R.id.tv_content_public)//中间内容
            TextView tvTitlePublic;


    //散标投资  债权转让
    private String[] mTitleDates = UIUtils.getStringArray(R.array.invest_tab);

    /**
     * 是否第一次加载
     */
    private boolean isFirstLoading = true;
    private boolean isTest;//是否是加载测试标列表

    @Override
    protected View initView()
    {
        View mView = View.inflate(mActivity, R.layout.fragment_ivest_new, null);
        ButterKnife.bind(this, mView);
        return mView;

    }

    @Override
    protected void initData()
    {
        if (getArguments() != null)
        {
            isTest = getArguments().getBoolean("is_test", false);
        }
        //是否第一次加载
        if (isFirstLoading)
        {
            // 设置指针的文本的颜色
            int selectedColor = getContext().getResources().getColor(R.color.text_blue);
            ivLeftBack.setVisibility(View.INVISIBLE);
            tvTitlePublic.setText("投资");
            investTabs.setShouldExpand(true);
            viewpagerIvest.setAdapter(new MainFragmentPagerAdapter(getChildFragmentManager()));
            investTabs.setViewPager(viewpagerIvest);
            investTabs.setTextColor(Color.BLACK, selectedColor);
            //        investTabs.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            investTabs.setUnderlineColor(Color.TRANSPARENT);
            investTabs.setDividerColor(Color.TRANSPARENT);
            investTabs.setOnPageChangeListener(this);
            isFirstLoading = false;
        }

    }

    @Override
    public void onClick(View view)
    {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {

        //        mainFragment.INVEST_TAB = position;
        //        if (position == 0) {
        //            mainActivity.getMenuDrawer().setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);//2
        //        } else {
        //            mainActivity.getMenuDrawer().setTouchMode(MenuDrawer.TOUCH_MODE_NONE);//0
        //        }

        // 当选中的时候去加载数据
        BaseInvestFragment fragment;
        CfLog.d("Investfragment-istest=" + isTest);
        if (isTest)
        {
            fragment = TestProductFragmentFactory.getFragment(position);
        }
        else
        {
            fragment = FragmentFactory.getFragment(position);
        }

        fragment.closeRefresh();
        fragment.loadData();
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    @Override
    public void onResume()
    {
        //        viewpagerIvest.setCurrentItem(mainFragment.INVEST_TAB);
        super.onResume();
    }

    class MainFragmentPagerAdapter extends FragmentStatePagerAdapter
    {

        public MainFragmentPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment;
            if (isTest)
            {
                fragment = TestProductFragmentFactory.getFragment(position);
            }
            else
            {
                fragment = FragmentFactory.getFragment(position);
            }
            return fragment;
        }

        @Override
        public int getCount()
        {
            if (mTitleDates != null)
            {
                return mTitleDates.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            if (mTitleDates != null)
            {
                return mTitleDates[position];
            }
            return super.getPageTitle(position);
        }

    }
}
