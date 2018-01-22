package com.mingjie.jf.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.reflect.TypeToken;
import com.mingjie.jf.R;
import com.mingjie.jf.activity.AccountDataActivity;
import com.mingjie.jf.activity.EquitableDetailActivity;
import com.mingjie.jf.activity.LoanDetailActivity;
import com.mingjie.jf.activity.OldVersionActivity;
import com.mingjie.jf.activity.ToldActivity;
import com.mingjie.jf.activity.UserRegisterActivity;
import com.mingjie.jf.adapter.BannerAdapter;
import com.mingjie.jf.bean.HomeFragmentVo;
import com.mingjie.jf.bean.ProdouctList;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.JsonUtils;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.progressview.ArcProgressView;
import com.mingjie.jf.widgets.AutoScrollViewPager;
import com.mingjie.jf.widgets.JRecyclerView;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 首页fragment
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener,
        SwipeRefreshLayout.OnRefreshListener {


    //    @BindView(R.id.swipe_home)
    SwipeRefreshLayout mSwipeRefreshLayout;
    //    @BindView(R.id.li_ji_tou_biao_btn)//立即投标按钮

    @BindView(R.id.tv_home_totalrenCount)//总人数
            TextView totalrenCount;
    @BindView(R.id.tv_sunamount)//总金额
            TextView sunamount;
    @BindView(R.id.fra_noDatas)
    FrameLayout mFraNoDatas;
    @BindView(R.id.relative_showBanner)
    RelativeLayout mShowBanner;
    @BindView(R.id.viewFlipper)
    ViewFlipper mFlipper;
    @BindView(R.id.ll_home_container_dot)
    LinearLayout mDotContainer;//装点的容器
    @BindView(R.id.autoViewPager_home)
    AutoScrollViewPager mAutoViewPager;
    //    @BindView(R.id.swipe_container)
    RefreshLayout swipeContainer;
    //散标相关信息
    @BindView(R.id.product_title_iv)
    ImageView productTitleIv;
    @BindView(R.id.product_title_tv)
    TextView productTitleTv;
    @BindView(R.id.av_profit)
    ArcProgressView avProfit;
    @BindView(R.id.tv_loan_total)
    TextView tvLoanTotal;
    @BindView(R.id.tv_time_method)
    TextView tvTimeMethod;
    //债权表相关信息
    @BindView(R.id.debts_product_title_iv)
    ImageView debtsProductTitleIv;
    @BindView(R.id.debts_product_title_tv)
    TextView debtsProductTitleTv;
    @BindView(R.id.debts_av_profit)
    ArcProgressView debtsAvProfit;
    @BindView(R.id.debts_tv_loan_total)
    TextView debtsTvLoanTotal;
    @BindView(R.id.debts_tv_time_method)
    TextView debtsTvTimeMethod;
    //    @BindView(R.id.iv_home_arrow)
    ImageView ivHomeArrow;
    //    @BindView(R.id.im_register)
    ImageView imRegister;
    //    @BindView(R.id.iv_Left_public)
    //    ImageView ivLeftPublic;
    //    @BindView(R.id.tv_content_public)
    //    TextView tvContentPublic;




    /**
     * 是否第一次加载
     */

    private boolean isFirstLoading = true;
    private int oldPosition = 0;

    private String sumuser;//总人数
    private String summoney;//投资总金额
    private String mWaitMoney;//截取到小数点之前的投资总金额
    private List<HomeFragmentVo.BannerListBean> mViewPageDatas;//广告图片轮播
    private ImageView[] imageViews;//
    private List<HomeFragmentVo.AnnounceListBean> mSysNoticeList;//竖条公告
    private List<HomeFragmentVo.StatisticsBean> centerdata;//中间数据

    private JRecyclerView mJRecyclerView;
    private List<ProdouctList> product;
    private LinearLayout debts;
    private LinearLayout error;
    private HomeFragmentVo homeVo;
    private View buybtn;
    private View buybtn2;
    private View viewObj0;//标的0
    private View viewObj1;//标的1
    private TextView tvShengYu1;
    private TextView tvShengYu2;
    private TextView returnToOld;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_home, null);
        ButterKnife.bind(mActivity, view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_home);
        mJRecyclerView = (JRecyclerView) view.findViewById(R.id.recyclerview);
        buybtn = view.findViewById(R.id.li_ji_tou_biao_btn);
        buybtn2 = view.findViewById(R.id.debts_li_ji_tou_biao_btn);
        debts = (LinearLayout) view.findViewById(R.id.debts);
        error = (LinearLayout) view.findViewById(R.id.linear_error);
        imRegister = (ImageView) view.findViewById(R.id.im_register);
        ivHomeArrow = (ImageView) view.findViewById(R.id.iv_home_arrow);
        tvShengYu1 = (TextView) view.findViewById(R.id.tv_shengyu1);
        tvShengYu2 = (TextView) view.findViewById(R.id.tv_shengyu2);
        returnToOld = (TextView) view.findViewById(R.id.return_to_old);
        returnToOld.setOnClickListener(this);

        ivHomeArrow.setOnClickListener(this); //公告右侧的箭头  点击 进入 公告列表
        imRegister.setOnClickListener(this);

        //        ivLeftPublic = (ImageView) view.findViewById(R.id.iv_Left_public);
        //        tvContentPublic = (TextView) view.findViewById(R.id.tv_content_public);
        //        ivLeftPublic.setVisibility(View.GONE);
        //        tvContentPublic.setText("首页");
        //        ivHomeArrow.setOnClickListener(new View.OnClickListener()
        //        {
        //            @Override
        //            public void onClick(View view)
        //            {
        //                startActivity(new Intent(mActivity, ToldActivity.class));
        //            }
        //        });
        //        imRegister.setOnClickListener(new View.OnClickListener()
        //        {
        //            @Override
        //            public void onClick(View view)
        //            {
        //                startActivity(new Intent(mActivity, UserRegisterActivity.class));
        //            }
        //        });
        viewObj0 = view.findViewById(R.id.layout_obj0);
        viewObj1 = view.findViewById(R.id.layout_obj1);
        viewObj0.setOnClickListener(this);
        viewObj1.setOnClickListener(this);

        //        new HomeFragmentAction(HomeFragment.this).webData();
        //        new HomeFragmentAction(HomeFragment.this).bannerData();
        //        getOtherData();
        //        getData();

        /**
         * cookie :
         */

        if ("".equals(HttpManager.getInstance().cookie)){
            CacheUtils.clearUserInfo();
        }
        return view;
    }

    /**
     * 获取缓存数据
     */
    private void getCacheData() {
        // 加载缓存（首页标以外的数据）
        if (CacheUtils.getHomeData(mActivity) != null) {
            HomeFragmentVo homeData = JsonUtils.deserialize(CacheUtils.getHomeData(mActivity), HomeFragmentVo
                    .class);
            initBanner(homeData.getBannerList());
            //            if (homeData.getStatistics() != null)
            //            {
            //                if (homeData.getStatistics().getTotalBid() != null)
            //                {
            //                    mWaitMoney = homeData.getStatistics().getTotalBid().substring(0, homeData
            //                            .getStatistics().getTotalBid().indexOf("."));
            //                    initWaitMoney(mWaitMoney);
            //                }
            //                if (homeData.getStatistics().getSumUser() != null)
            //                {
            //                    initTotalCount(homeData.getStatistics().getSumUser());
            //                }
            //            }
            initNoticeList(homeData.getAnnounceList());
        }

        // 加载缓存(首页标的数据)
        if (CacheUtils.getHomeBiaoData(mActivity) != null) {
            product = JsonUtils.deserialize(CacheUtils.getHomeBiaoData(mActivity),
                    new TypeToken<List<ProdouctList>>() {
                    }.getType());
            showList(product);
        }
    }

    //除标以外的信息
    private void getOtherData() {

        if (!Utilities.canNetworkUseful(mActivity))//如果当前网络不可用
        {
            UIUtils.showToast(mActivity, "当前网络不可用，请重试！");
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            return;
        }
        HttpManager.getInstance().doPost(ServiceName.CGKX_INDEXCONTENT, null, new
                HttpCallBack<ServerResponse<HomeFragmentVo>>() {
                    @Override
                    public void getHttpCallNull(String str) {
                        getData();
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<HomeFragmentVo> rsp) {
                        getData();
                        if (rsp == null) {
                            UIUtils.showToast(mActivity, R.string.service_err);
                        } else if (!"000000".equals(rsp.getCode()) && null != rsp.getMsg()) {
                            UIUtils.showToast(mActivity, rsp.getMsg());
                        } else {
                            if (null == rsp.getData()) {
                                UIUtils.showToast(mActivity, R.string.service_err);
                            } else if (null != rsp.getData()) {
                                homeVo = rsp.getData();
                                // 保存首页信息( 标以外数据 )
                                CacheUtils.setHomeData(mActivity, JsonUtils.serialize(homeVo));

                                initBanner(homeVo.getBannerList());
                                //                                if (null != homeVo.getStatistics())
                                //                                {
                                //                                    if (null != homeVo.getStatistics().getTotalBid())
                                //                                    {
                                //                                        mWaitMoney = homeVo.getStatistics()
                                // .getTotalBid().substring(0, homeVo
                                //                                                .getStatistics().getTotalBid()
                                // .indexOf("."));
                                //                                        initWaitMoney(mWaitMoney);
                                //                                    }
                                //                                    if (null != homeVo.getStatistics().getSumUser())
                                //                                    {
                                //                                        initTotalCount(homeVo.getStatistics()
                                // .getSumUser());
                                //                                    }
                                //                                }
                                initNoticeList(homeVo.getAnnounceList());
                            }
                        }
                    }
                });
    }

    //首页获取标的情况  网络请求
    private void getData() {
        if (!Utilities.canNetworkUseful(mActivity))//如果当前网络不可用
        {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            UIUtils.showToast(mActivity, "当前网络不可用，请重试！");
            return;
        }


        HttpManager.getInstance().doPost(ServiceName.HOME_PRODUCTlIST, null, new
                HttpCallBack<ServerResponse<List<ProdouctList>>>() {
                    @Override
                    public void getHttpCallNull(String str) {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        UIUtils.showToast(mActivity, R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<List<ProdouctList>> rsp) {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        if (null == rsp) {
                            showWrong();
                            return;
                        }
                        if (null != rsp.getCode() && rsp.getCode().equals("000000")) {
                            UIUtils.showToast(mActivity, "刷新完成");
                            product = rsp.getData();
                            // 保存首页信息( 标的数据 )
                            CacheUtils.setHomeBiaoData(mActivity, JsonUtils.serialize(product));
                            showList(product);
                        } else {
                            showWrong();
                        }

                    }
                });
    }

    private void showWrong() {
        //显示错误的时候 标的 12 隐藏
        error.setVisibility(View.VISIBLE);
        viewObj0.setVisibility(View.GONE);
        viewObj1.setVisibility(View.GONE);
    }

    //显示首页标
    private void showList(List<ProdouctList> product) {
        viewObj0.setVisibility(View.GONE);
        viewObj1.setVisibility(View.GONE);
        //未返回标的,直接退出
        if (product == null || product.size() <= 0) {
            return;
        }

        //返回超过一条数据时
        if (product.size() >= 1) {
            viewObj0.setVisibility(View.VISIBLE);
            // 设置进度
            double progress = product.get(0).getBidAmount() * 1.0 / product.get
                    (0).getProductAmount() * 100;
            productTitleTv.setText(product.get(0).getTitle());
            tvLoanTotal.setText(product.get(0).getProductAmount() + "");
            String unit = product.get(0).getRepurchaseMode() == 3 ? "日" : "个月";
            tvTimeMethod.setText(String.valueOf(product.get(0).getProductDeadline() + unit + "/" +
                    product.get(0).getRepurchaseModeDesc()));
            avProfit.setCustomText(String.valueOf(product.get(0).getProfit()));
            //不为招标中的标的隐藏立即投标
            buybtn.setVisibility(product.get(0).getProductStatus() == 7 ? View.VISIBLE : View.GONE);
            double serplus = product.get(0).getProductAmount() - product.get(0).getBidAmount();
            if (product.get(0).getProductStatus() == 10 || product.get(0).getProductStatus() == 11 || product.get(0)
                    .getProductStatus() == 14 || product.get(0).getProductStatus() == 20) {
                tvShengYu1.setText("剩余" + StringUtils.dou2Str(serplus) + getString(R.string.yuan));//可投金额
            } else if (product.get(0).getProductStatus() != 10 && product.get(0).getProductStatus() != 11 && product
                    .get(0).getProductStatus() != 14 && product.get(0).getProductStatus() != 20) {
                if (serplus > 0) {
                    tvShengYu1.setText("剩余" + StringUtils.dou2Str(serplus) + getString(R.string.yuan));//可投金额
                } else {
                    tvShengYu1.setText("剩余" + "0.00" + getString(R.string.yuan));//可投金额
                }
            }
            avProfit.setProgress((int) progress);
            CfLog.i("---" + "剩余" + StringUtils.dou2Str(product.get(0).getProductAmount() - product.get(0)
                    .getBidAmount()) + "元");
        }
        //返回超过2条数据时
        if (product.size() >= 2) {
            viewObj1.setVisibility(View.VISIBLE);
            double progress = product.get(1).getBidAmount() * 1.0 / product.get(1).getProductAmount() * 100;
            debtsProductTitleTv.setText(product.get(1).getTitle());
            debtsTvLoanTotal.setText(product.get(1).getProductAmount() + "");
            String unit = product.get(1).getRepurchaseMode() == 3 ? "日" : "个月";
            debtsTvTimeMethod.setText(String.valueOf(product.get(1).getProductDeadline()) + unit + "/" +
                    product.get(1).getRepurchaseModeDesc());
            //不为招标中的标的隐藏立即投标
            buybtn2.setVisibility(product.get(1).getProductStatus() == 7 ? View.VISIBLE : View.GONE);
            debtsAvProfit.setCustomText(String.valueOf(product.get(1).getProfit()));
            double serplus = product.get(1).getProductAmount() - product.get(1).getBidAmount();
            CfLog.i("---" + serplus);
            if (product.get(1).getProductStatus() == 10 || product.get(1).getProductStatus() == 11 || product.get(1)
                    .getProductStatus() == 14 || product.get(1).getProductStatus() == 20) {
                tvShengYu2.setText("剩余" + StringUtils.dou2Str(serplus) + getString(R.string.yuan));
            } else if (product.get(1).getProductStatus() != 10 && product.get(1).getProductStatus() != 11 && product
                    .get(1).getProductStatus() != 14 && product.get(1).getProductStatus() != 20) {
                if (serplus > 0) {
                    tvShengYu2.setText("剩余" + StringUtils.dou2Str(serplus) + getString(R.string.yuan));//可投金额
                } else {
                    tvShengYu2.setText("剩余" + "0.00" + getString(R.string.yuan));//可投金额
                }
            }
            debtsAvProfit.setProgress((int) progress);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initData() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        //是否第一次加载
        if (isFirstLoading) {
            //            buybtn.setOnClickListener(this);
            //            buybtn2.setOnClickListener(this);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue, R
                    .color.red);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            //            new HomeFragmentAction(HomeFragment.this).webData();
            //            new HomeFragmentAction(HomeFragment.this).bannerData();

            getCacheData();

            getOtherData();
            //            getData();
            isFirstLoading = false;
        }
    }

    //初始化轮播图
    private void initBanner(List<HomeFragmentVo.BannerListBean> mViewPageDatas) {
        //轮播图的时间
        mAutoViewPager.setInterval(4000);
        mFraNoDatas.setOnClickListener(this);   //暂无数据时候显示的图片
        mAutoViewPager.setOnPageChangeListener(this);
        oldPosition = 0;
        mAutoViewPager.startAutoScroll();
        //                获取到banner图片数据
        if (mViewPageDatas != null && mViewPageDatas.size() != 0) {

            //                    有banner数据，显示banner，隐藏默认图片
            mFraNoDatas.setVisibility(View.GONE);
            mShowBanner.setVisibility(View.VISIBLE);

            //                    初始化点
            imageViews = new ImageView[mViewPageDatas.size()];
            mDotContainer.removeAllViews();
            for (int i = 0; i < mViewPageDatas.size(); i++) {
                ImageView imageView = new ImageView(mActivity);
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(UIUtils.dip2px(5), UIUtils.dip2px(5));
                layout.setMargins(UIUtils.dip2px(5), 0, 0, 0);
                imageView.setLayoutParams(layout);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageViews[i] = imageView;
                if (i == 0) {
                    imageViews[i].setBackgroundResource(R.drawable.shape_dot_select);
                } else {
                    imageViews[i].setBackgroundResource(R.drawable.shape_dot_normal);
                }
                mDotContainer.addView(imageViews[i]);
            }
            //                    设置adapter
            mAutoViewPager.setAdapter(new BannerAdapter(mActivity, mViewPageDatas));
        } else {
            mAutoViewPager.setAdapter(new BannerAdapter(mActivity, mViewPageDatas));
        }

    }

    // FlipperView
    private void initNoticeList(final List<HomeFragmentVo.AnnounceListBean> mSysNoticeList) {
        if (mSysNoticeList == null) {
            return;
        }
        int size = mSysNoticeList.size();
        if (size > 1) {
            mFlipper.setInAnimation(mActivity, R.anim.push_up_in);
            mFlipper.setOutAnimation(mActivity, R.anim.push_up_out);
            mFlipper.setFlipInterval(3000);
            mFlipper.startFlipping();
        }

        for (int i = 0; i < size; i++) {
            TextView tv = new TextView(mActivity);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setSingleLine(true);
            tv.setText(mSysNoticeList.get(i).getTitle());
            tv.setTextColor(Color.BLACK);
            final int finalI = i;
            tv.setOnClickListener(this);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tabTitleID = mSysNoticeList.get(finalI).getTitle();
                    Intent intent = new Intent(mActivity, AccountDataActivity.class);
                    intent.putExtra("title", tabTitleID);
                    intent.putExtra("id", mSysNoticeList.get(finalI).getId());
                    intent.putExtra("title", mSysNoticeList.get(finalI).getTitle());
                    startActivity(intent);
                }
            });
            mFlipper.addView(tv);
        }
    }

    private void initTotalCount(String sumuser) {
        String totalCountStr;
        if (TextUtils.isEmpty(sumuser)) {
            SpannableString styledText = new SpannableString("__万__人");
            totalrenCount.setText(styledText, TextView.BufferType.SPANNABLE);

        } else {
            String wanStr = (Long.parseLong(sumuser) / 10000) + "";
            String otherStr = (Long.parseLong(sumuser) % 10000) + "";

            if (wanStr.equals("0")) {
                totalCountStr = otherStr + "人";
                SpannableString styledText = new SpannableString(totalCountStr);
                styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                        0, otherStr.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                        otherStr.length(), otherStr.length() + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                        otherStr.length() + 1, totalCountStr.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                totalrenCount.setText(styledText, TextView.BufferType.SPANNABLE);

            } else {
                totalCountStr = wanStr + "万" + otherStr + "人";
                SpannableString styledText = new SpannableString(totalCountStr);
                styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                        0, wanStr.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                        wanStr.length(), wanStr.length() + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                        wanStr.length() + 1, wanStr.length() + otherStr.length() + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                        wanStr.length() + otherStr.length() + 1, totalCountStr.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                totalrenCount.setText(styledText, TextView.BufferType.SPANNABLE);
            }

        }
    }

    private void initWaitMoney(String mWaitMoney) {
        String waitMoneyStr;
        String yiStr;
        String wanStr;
        String otherStr;

        if (mWaitMoney == null) {
            SpannableString styledText = new SpannableString("__万__元");
            sunamount.setText(styledText, TextView.BufferType.SPANNABLE);
        } else {
            yiStr = (Long.parseLong(mWaitMoney) / 100000000) + "";
            wanStr = (Long.parseLong(mWaitMoney) % 100000000 / 10000) + "";
            otherStr = (Long.parseLong(mWaitMoney) % 100000000 % 10000) + "";
            if (yiStr.equals("0")) {
                if (wanStr.equals("0")) {
                    waitMoneyStr = otherStr + "元";
                    SpannableString styledText = new SpannableString(waitMoneyStr);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                            0, otherStr.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                            otherStr.length(), otherStr.length() + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                            otherStr.length() + 1, waitMoneyStr.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sunamount.setText(styledText, TextView.BufferType.SPANNABLE);
                } else {
                    waitMoneyStr = wanStr + "万" + otherStr + "元";
                    SpannableString styledText = new SpannableString(waitMoneyStr);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                            0, wanStr.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                            wanStr.length(), wanStr.length() + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                            wanStr.length() + 1, wanStr.length() + otherStr.length() + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                            wanStr.length() + otherStr.length() + 1, waitMoneyStr.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sunamount.setText(styledText, TextView.BufferType.SPANNABLE);
                }

            } else {
                if (wanStr.equals("0")) {
                    waitMoneyStr = yiStr + "亿" + otherStr + "元";
                    SpannableString styledText = new SpannableString(waitMoneyStr);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                            0, yiStr.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                            yiStr.length(), yiStr.length() + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                            yiStr.length() + 1, yiStr.length() + otherStr.length() + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                            yiStr.length() + otherStr.length() + 1, waitMoneyStr.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sunamount.setText(styledText, TextView.BufferType.SPANNABLE);

                } else {
                    waitMoneyStr = yiStr + "亿" + wanStr + "万" + otherStr + "元";
                    SpannableString styledText = new SpannableString(waitMoneyStr);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                            0, yiStr.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                            yiStr.length(), yiStr.length() + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                            yiStr.length() + 1, yiStr.length() + wanStr.length() + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                            yiStr.length() + wanStr.length() + 1, yiStr.length() + wanStr.length() + 2,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_big_style),
                            yiStr.length() + wanStr.length() + 2, yiStr.length() + wanStr.length() + otherStr.length
                                    () + 2,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.home_tv_small_style),
                            yiStr.length() + wanStr.length() + otherStr.length() + 2, waitMoneyStr.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sunamount.setText(styledText, TextView.BufferType.SPANNABLE);

                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (!Utilities.canNetworkUseful(mActivity))//如果当前网络不可用 ， 不让用户点击进入标的详情界面
        {
            UIUtils.showToast(mActivity, "当前网络不可用，请重试！");
            return;
        }
        switch (view.getId()) {
            case R.id.layout_obj0://标的0
                if (product.size() > 0) {
                    toObjDetail(product.get(0).getProductType(), 0);
                }
                break;
            case R.id.layout_obj1://标的1
                if (product.size() > 1) {
                    toObjDetail(product.get(1).getProductType(), 1);
                }
                break;
            case R.id.im_register:
                startActivity(new Intent(mActivity, UserRegisterActivity.class));
                break;
            //公告列表
            case R.id.iv_home_arrow:
                startActivity(new Intent(mActivity, ToldActivity.class));
                break;

            case R.id.return_to_old:
                startActivity(new Intent(getContext(), OldVersionActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 根据标的type去对应详情页面
     */
    private void toObjDetail(int type, int position) {
        Intent intent;
        if (type == 1) {
            intent = new Intent(mActivity, EquitableDetailActivity.class);
        } else {
            intent = new Intent(mActivity, LoanDetailActivity.class);
        }
        if (position == 0) {
            intent.putExtra("productId", product.get(0).getId());
        } else if (position == 1) {
            intent.putExtra("productId", product.get(1).getId());
        }
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        imageViews[oldPosition].setBackgroundResource(R.drawable.shape_dot_normal);
        imageViews[position].setBackgroundResource(R.drawable.shape_dot_select);
        oldPosition = position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mSwipeRefreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        //        new HomeFragmentAction(HomeFragment.this).webData();
        //        new HomeFragmentAction(HomeFragment.this).bannerData();
        getOtherData();
        //        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    //    //设置投资人数累计
    //    @Override
    //    public void setSumUser(String user)
    //    {
    //        if (mRefreshView.isRefreshing())
    //        {
    //            mRefreshView.setRefreshing(false);
    //            UIUtils.showToast(getContext(), UIUtils.getString(R.string.refreshFinsh));
    //        }
    //        sumuser = user;
    //        initTotalCount();
    //    }
    //
    //    //设置累计投资金额
    //    @Override
    //    public void setSumMoney(String money)
    //    {
    //        if (mRefreshView.isRefreshing())
    //        {
    //            mRefreshView.setRefreshing(false);
    //            UIUtils.showToast(getContext(), UIUtils.getString(R.string.refreshFinsh));
    //        }
    //        summoney = money;
    //        if (summoney.contains("."))
    //        {
    //            mWaitMoney = StringUtils.double2Str(Double.valueOf(summoney)).substring(0, summoney.indexOf("."));
    //        }
    //        else
    //        {
    //            mWaitMoney = summoney;
    //        }
    //        initWaitMoney();
    //    }
    //
    //    //失败的原因
    //    @Override
    //    public void setDataWrong(String msg)
    //    {
    //        if (mRefreshView.isRefreshing())
    //        {
    //            mRefreshView.setRefreshing(false);
    //            UIUtils.showToast(getContext(), msg);
    //        }
    //
    //    }
    //
    //    //设置公告列表
    //    @Override
    //    public void setBannerList(List<HomeFragmentVo.AnnounceDataBean> bannerList)
    //    {
    //        this.mSysNoticeList = bannerList;
    //        initNoticeList(mSysNoticeList);
    //    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
