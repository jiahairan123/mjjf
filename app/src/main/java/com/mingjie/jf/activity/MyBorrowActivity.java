package com.mingjie.jf.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.mingjie.jf.R;
import com.mingjie.jf.fragment.BaseInvestFragment;
import com.mingjie.jf.utils.BorrowFragmentFactory;
import com.umeng.analytics.MobclickAgent;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 个人中心 - 我的借款activity
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-26 09:24
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class MyBorrowActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener
{
    ImageView ivLeftPublic;//返回
    TextView tvContentPublic;//内容
    PagerSlidingTabStrip investTabs;
    ViewPager viewpagerIvest;
    private String[] mTitleDates;
    // List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrow);
        mTitleDates = getResources().getStringArray(R.array.my_borrow);
        initView();
        initData();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面
        MobclickAgent.onResume(this);       //统计时长
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    protected void initView()
    {
        ivLeftPublic = (ImageView) findViewById(R.id.iv_Left_public);
        tvContentPublic = (TextView) findViewById(R.id.tv_content_public);
        findViewById(R.id.iv_Left_public).setOnClickListener(this);
        investTabs = (PagerSlidingTabStrip) findViewById(R.id.loadinfo_tabs);
        viewpagerIvest = (ViewPager) findViewById(R.id.viewpager_loadinfo);
    }

    protected void initData()
    {
        tvContentPublic.setText("我的借款");
        investTabs.setShouldExpand(true);
        viewpagerIvest.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));
        investTabs.setViewPager(viewpagerIvest);
        int selectedColor = getResources().getColor(R.color.text_blue);
        investTabs.setTextColor(Color.BLACK, selectedColor);
        investTabs.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        investTabs.setUnderlineColor(Color.TRANSPARENT);
        investTabs.setDividerColor(Color.TRANSPARENT);
        investTabs.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        // 当选中的时候去加载数据
        BaseInvestFragment fragment = BorrowFragmentFactory.getFragment(position);
        fragment.loadData();
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

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
            return BorrowFragmentFactory.getFragment(position);
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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        BorrowFragmentFactory.clearCache();
    }
}
