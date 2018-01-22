package com.mingjie.jf.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.AppConfigInfoResponse;
import com.mingjie.jf.bean.AppUpdateResponse;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.fragment.AccountFragment;
import com.mingjie.jf.fragment.BorrowFragment;
import com.mingjie.jf.fragment.HomeFragment;
import com.mingjie.jf.fragment.InvestFragment;
import com.mingjie.jf.jpush.ExampleUtil;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.AppUpdateUtil;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.TabIndicator;

import cn.jpush.android.api.JPushInterface;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 主界面
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener
{
    //这里将MainActivity的context提为全局静态，本身MainActivity就为常驻activity，所以不存在内存泄漏问题
    public static Context context;

    public static final String KEY_NEED_AUTOLOGIN = "keyNeedAutoLogin";

    private static final String TAG = "MainActivity";
    private static final String TAG_HOME = "home";

    private static final String TAG_INVEST = "invest";

    private static final String TAG_ACCOUNT = "account";

    private static final String TAG_BORROW = "bowwow";

    LinearLayout mContainer;

    public static FragmentTabHost mTabHost;

    private FragmentManager mManager;

    //for receive customer msg from jpush server
    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private MessageReceiver mMessageReceiver;

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }


    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                Toast.makeText(getApplicationContext(), showMsg.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void initView()
    {
        setContentView(R.layout.fragment_main);
        registerMessageReceiver();

        String rid = JPushInterface.getRegistrationID(getApplicationContext());

        if (!rid.isEmpty()) {
        } else {

//            Toast.makeText(this, "Get registration fail, JPush init failed!", Toast.LENGTH_SHORT).show();
        }

        initTabHost();

    }



    @Override
    protected void initData()
    {
        context = this;
        checkVersion();
        getConfigInfo();
    }

    /**
     * 获取app配置信息，并存入缓存
     * 客服电话
     */
    private void getConfigInfo()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            //这里检查未能让用户感知，所以这里不用Toast
            return;
        }
        HttpManager.getInstance().doPost(ServiceName.CGKX_CONFIGINFO, new
                HttpCallBack<ServerResponse<AppConfigInfoResponse>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                    }

                    @Override
                    public void getHttpCallBack(final ServerResponse<AppConfigInfoResponse> rsp)
                    {
                        if (rsp != null && "000000".equals(rsp.getCode()))
                        {
                            if (null==rsp.getData())
                            {
                                return;
                            }
                            CacheUtils.saveAppConfigInfo(rsp.getData());
                        }
                    }
                });
    }

    /**
     * 检查版本
     */

    private void checkVersion()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            //这里检查未能让用户感知，所以这里不用Toast
            return;
        }
        HttpManager.getInstance().doPost(ServiceName.UPDATEVERSIONS, new
                HttpCallBack<ServerResponse<AppUpdateResponse>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                    }

                    @Override
                    public void getHttpCallBack(final ServerResponse<AppUpdateResponse> rsp)
                    {
                        if (rsp != null && "000000".equals(rsp.getCode()))
                        {
                            if (rsp.getData() == null)
                            {
                                return;
                            }
                            AppUpdateUtil updateUtil = new AppUpdateUtil(MainActivity.this);
                            updateUtil.appUpdate(rsp.getData(), true);
                        }
                    }
                });
    }

    /**
     * 初始化TabHost
     */
    private void initTabHost()
    {
        mContainer = (LinearLayout) findViewById(R.id.ll_main_container);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setOnTabChangedListener(this);
        // 获取fragmentManager用来管理子fragment
        mManager = getSupportFragmentManager();


        // 1. 初始化
        mTabHost.setup(this, mManager, R.id.fra_main_content_container);// TabHost管理的内容view

        // 首页
        TabHost.TabSpec homeSpec = mTabHost.newTabSpec(TAG_HOME);
        TabIndicator chatIndicator = newIndicator("首页", R.drawable.select_tab_home);
        homeSpec.setIndicator(chatIndicator);
        mTabHost.addTab(homeSpec, HomeFragment.class, null);

        // 投资
        TabHost.TabSpec investSpec = mTabHost.newTabSpec(TAG_INVEST);
        TabIndicator investIndicator = newIndicator("投资", R.drawable.select_tab_touzi);
        investSpec.setIndicator(investIndicator);
        mTabHost.addTab(investSpec, InvestFragment.class, null);

        // 借款
        TabHost.TabSpec borrowSpec = mTabHost.newTabSpec(TAG_BORROW);
        TabIndicator borrowIndicator = newIndicator("借款", R.drawable.select_tab_jiekuan);
        borrowSpec.setIndicator(borrowIndicator);
        mTabHost.addTab(borrowSpec, BorrowFragment.class, null);

        // 账户
        TabHost.TabSpec accountSpec = mTabHost.newTabSpec(TAG_ACCOUNT);
        TabIndicator accountIndicator = newIndicator("账户", R.drawable.select_tab_account);
        accountSpec.setIndicator(accountIndicator);
        mTabHost.addTab(accountSpec, AccountFragment.class, null);
    }

    private TabIndicator newIndicator(String title, int iconId)
    {
        TabIndicator indicator = new TabIndicator(this);
        indicator.setTabTitle(title);
        indicator.setTabIcon(iconId);
        return indicator;
    }

    @Override
    public void onTabChanged(String tabId)
    {
        CfLog.d(tabId);
        switch (tabId)
        {
            case TAG_HOME:
                break;
            case TAG_ACCOUNT:
                break;
            case TAG_BORROW:
                break;
            case TAG_INVEST:
                break;
            default:
                break;
        }
    }

    public static void setCurrentTab(int tab)
    {
        mTabHost.setCurrentTab(tab);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 是否退出
    private Boolean isExit = false;

    /**
     * 双击退出
     */
    private void exitBy2Click()
    {

        // 如果菜单显示，先关闭菜单显示
        //        if (mDrawer.isMenuVisible()) {
        //            mDrawer.closeMenu(true);
        //            return;
        //        }
        if (!isExit)
        {
            isExit = true; // 准备退出
            new CountDownTimer(2000, 1000)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    UIUtils.showToast(MainActivity.this, "再按一次退出程序");
                }

                @Override
                public void onFinish()
                {
                    isExit = false;
                }
            }.start();
        }
        else
        {
            finish();
            BaseActivity.exitApp();
//                        CacheUtils.setBoolean(this, Constants.IS_LOGIN, false);
            //            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {

        mManager = null;
        unregisterReceiver(mMessageReceiver);
        finish();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragmentByTag = mManager.findFragmentByTag(TAG_ACCOUNT);
        fragmentByTag.onActivityResult(requestCode, resultCode, data);
    }




}
