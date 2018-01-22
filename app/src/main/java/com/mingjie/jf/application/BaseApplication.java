package com.mingjie.jf.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.mingjie.jf.BuildConfig;
import com.mingjie.jf.bean.BannerBean;
import com.mingjie.jf.bean.HomePageBean;
import com.mingjie.jf.utils.LockPatternUtils;
import com.mingjie.jf.utils.UIUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.config.AutoLayoutConifg;

import cn.jpush.android.api.JPushInterface;

/**
 * qinsiyi
 * @描述 应用程序application
 */
public class BaseApplication extends Application {

    private static Context mContext;//全局上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程ID
    private static Looper mMainLooper;//主线程Looper
    private static Handler mMainHandler;//主线程Handler
    private static LockPatternUtils mLockPatternUtils;
    private static boolean isActive = false;
    private static BannerBean mBannerDatas;
    // private static UIUtils uiutils;

    private static HomePageBean.DataEntity.AppVersionEntity mAppVersion;

    @Override
    public void onCreate() {
        super.onCreate();

        //第一步：在build.gradle 里设置
        //极光推送的第二步：如下，初始化
        //第三步：代码部分
        // Ps:现在用的APPKey用的不是正式版的，发布版本需要改为正式版
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);

        MobclickAgent.setDebugMode(BuildConfig.DEBUG); // 测试数据是不统计的
        // 上下文
        mContext = getApplicationContext();

        // 主线程
        mMainThread = Thread.currentThread();

        // mMainThreadId = mMainThread.getId();
        mMainThreadId = android.os.Process.myTid();

        mMainLooper = getMainLooper();

        // 创建主线程的handler
        mMainHandler = new Handler();
        mLockPatternUtils = new LockPatternUtils(UIUtils.getContext());

        AutoLayoutConifg.getInstance().useDeviceSize();

//        CrashHandler catchHandler = CrashHandler.getInstance();
//        catchHandler.init(getApplicationContext());
    }

    public static Context getContext() {
        return mContext;
    }

    public static LockPatternUtils getLockPatternUtils() {
        if (mLockPatternUtils == null) {
            mLockPatternUtils = new LockPatternUtils(UIUtils.getContext());
        }
        return mLockPatternUtils;
    }

    public static void setAppVersion(HomePageBean.DataEntity.AppVersionEntity version) {
        mAppVersion = version;
    }

    public static HomePageBean.DataEntity.AppVersionEntity getAppversion() {
        return mAppVersion;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static BannerBean getBannerDatas() {
        return mBannerDatas;
    }

    public static void setBannerDatas(BannerBean bannerBean) {
        mBannerDatas = bannerBean;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static Handler getMainHandler() {
        return mMainHandler;
    }

    public static boolean isActive() {
        return isActive;
    }

    public static void setActive(boolean isActived) {
        isActive = isActived;
    }
}
