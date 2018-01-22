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
import com.mingjie.jf.fragment.MyInvestRecordFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述：我的投资
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class MyInvestActivity extends FragmentActivity implements View.OnClickListener
{

    ImageView ivLeftPublic;//返回
    TextView tvContentPublic;//内容
    PagerSlidingTabStrip investTabs;
    ViewPager viewpagerIvest;
    private String[] mTitleDates;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ivest);
        mTitleDates = getResources().getStringArray(R.array.mytouai_tab);
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
        investTabs = (PagerSlidingTabStrip) findViewById(R.id.invest_tabs);
        viewpagerIvest = (ViewPager) findViewById(R.id.viewpager_ivest);
        fragmentList.add(MyInvestRecordFragment.newInstance(0));
        fragmentList.add(MyInvestRecordFragment.newInstance(1));
        fragmentList.add(MyInvestRecordFragment.newInstance(2));
    }

    protected void initData()
    {
        tvContentPublic.setText("我的投资");
        investTabs.setShouldExpand(true);
        viewpagerIvest.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));
        investTabs.setViewPager(viewpagerIvest);
        int selectedColor = getResources().getColor(R.color.text_blue);
        investTabs.setTextColor(Color.BLACK, selectedColor);
        investTabs.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        investTabs.setUnderlineColor(Color.TRANSPARENT);
        investTabs.setDividerColor(Color.TRANSPARENT);
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

    class MainFragmentPagerAdapter extends FragmentStatePagerAdapter
    {

        public MainFragmentPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return fragmentList.get(position);
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