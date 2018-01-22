package com.mingjie.jf.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.mingjie.jf.R;
import com.mingjie.jf.bean.JieKuanDetails;
import com.mingjie.jf.bean.ZhaiQuanDetailsParams;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.view.BaseTagPage;
import com.mingjie.jf.view.LoanInfoDetailPage;
import com.mingjie.jf.view.PepoleInfoPage;
import com.mingjie.jf.view.RiskControlPage;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 借款信息
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class LoanInfoActivity extends FragmentActivity implements View.OnClickListener
{

    ImageView ivLeftPublic;//返回
    TextView tvContentPublic;//内容
    PagerSlidingTabStrip investTabs;
    ViewPager viewpagerIvest;
    private String[] mTitleDates;
    private List<BaseTagPage> page = new ArrayList<>();    // 存放三个页面的容器

    private String id;
    private ZhaiQuanDetailsParams params;
    private static final int SUCCESSDATA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_detail_loan_info);
        mTitleDates = getResources().getStringArray(R.array.loan_peple);
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

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case SUCCESSDATA:
                    JieKuanDetails list = (JieKuanDetails) msg.obj;
                    page.add(new LoanInfoDetailPage(LoanInfoActivity.this, list));    // 借款描述
                    page.add(new RiskControlPage(LoanInfoActivity.this, list));               // 风控信息
                    page.add(new PepoleInfoPage(LoanInfoActivity.this, list.data.attachmentList)); // 用户材料
                    viewpagerIvest.setAdapter(new MyAdapter());
                    investTabs.setViewPager(viewpagerIvest);
                    UIUtils.dimissLoading();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

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
        UIUtils.showLoading(this);
        tvContentPublic.setText("借款信息");
        investTabs.setShouldExpand(true);
        int selectedColor = getResources().getColor(R.color.text_blue);
        investTabs.setTextColor(Color.BLACK, selectedColor);
        investTabs.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        investTabs.setUnderlineColor(Color.TRANSPARENT);
        investTabs.setDividerColor(Color.TRANSPARENT);
        //        id = getIntent().getExtras().getString("id");
        id = getIntent().getStringExtra("id");
        params = getParams();
        final Message msg = handler.obtainMessage();
        HttpManager.getInstance().doPost(ServiceName.INVESTMENT_BORROWINGINFO, params, new
                HttpCallBack<JieKuanDetails>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {  // 失败
                        UIUtils.showToast(getBaseContext(), R.string.dataError);
                        UIUtils.dimissLoading();
                    }

                    @Override
                    public void getHttpCallBack(JieKuanDetails list)
                    {  // 成功
                        if (null == list)
                        {
                            CfLog.i("获取数据成功");
                        }
                        else if ("000000".equals(list.code))
                        {
                            CfLog.i("发送消息");
                            msg.what = SUCCESSDATA;
                            msg.obj = list;
                            msg.sendToTarget();
                        }
                        else
                        {
                            UIUtils.showToast(list.msg);
                            UIUtils.dimissLoading();
                        }
                    }
                });
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

    /**
     * @return 返回债权转让参数
     */
    public ZhaiQuanDetailsParams getParams()
    {
        ZhaiQuanDetailsParams params = new ZhaiQuanDetailsParams();
        params.setId(id);
        return params;
    }

    public class MyAdapter extends PagerAdapter
    {

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
            if (null != mTitleDates)
            {
                return mTitleDates[position];
            }
            return super.getPageTitle(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            CfLog.i("page size = " + page.size());
            BaseTagPage tagPage = page.get(position);
            View root = tagPage.getRoot();
            container.addView(root);
            tagPage.initData();
            return root;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }
}