package com.mingjie.jf.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjie.jf.application.BaseApplication;
import com.mingjie.jf.R;

/**
 * @项目名: CaffcooP2P
 * @包名: caffp2p.android.utils
 * @类名: UIUtils
 * @创建者: 陈小平
 * @创建时间 2015/8/10 14:48
 * @描述 UI工具类
 */
public class UIUtils
{

    private static String versionName;
    // private Context mContext;
    /**
     * 上下文
     *
     * @return
     */

    private static Toast mToast;
    private static AlertDialog alertDialog;

    public static Context getContext()
    {
        return BaseApplication.getContext();
    }

    public static Resources getResources()
    {
        if(getContext()!=null)
        {
            return getContext().getResources();
        }
        return null;
    }

    public static String getString(int resId)
    {
        return getResources().getString(resId);
    }

    public static String[] getStringArray(int resId)
    {
        return getResources().getStringArray(resId);
    }

    public static String getPackageName()
    {
        return getContext().getPackageName();
    }

    public static int getColor(int resId)
    {
        return getResources().getColor(resId);
    }

    public static Handler getMainHandler()
    {
        return BaseApplication.getMainHandler();
    }

    public static long getMainThreadId()
    {
        return BaseApplication.getMainThreadId();
    }

    /**
     * 让task在主线程中执行，更新UI直接调用这个方法
     */
    public static void post(Runnable task)
    {
        int myTid = android.os.Process.myTid();

        if (myTid == getMainThreadId())
        {
            // 在主线程中执行的
            task.run();
        }
        else
        {
            // 在子线程中执行的
            getMainHandler().post(task);
        }
    }

    /**
     * 获取app版本号
     *
     * @return app当前版本
     */
    public static int getAppVersionNum()
    {
        try
        {
            PackageInfo info = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            return info.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            CfLog.e(e.toString());
        }
        return 1;
    }

    /**
     * 获取app版本名称
     *
     * @return app当前版本
     */
    public static String getAppVersionName()
    {
        if (null != versionName)
        {
            return versionName;
        }

        try
        {
            PackageInfo info = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            versionName = info.versionName;
            return info.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            CfLog.e(e.toString());
        }
        return "";
    }

    /**
     * dip 转 px
     *
     * @param dip
     * @return
     */
    public static int dip2px(int dip)
    {
        //
        // 公式： dp = px / (dpi / 160)
        // 公式： px = dp * (dpi / 160)
        // dp = px / denisity
        // px = dp * denisity;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dip * density + 0.5f);
    }

    public static int px2dip(int px)
    {
        // dp = px / denisity
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (px / density + 0.5f);
    }

    /**
     * 执行延时任务
     */
    public static void postDelayed(Runnable task, int delayed)
    {
        getMainHandler().postDelayed(task, delayed);
    }

    /**
     * 移除任务
     *
     * @param task
     */
    public static void removeCallbacks(Runnable task)
    {
        getMainHandler().removeCallbacks(task);
    }

    public static String getString(int id, Object... formatArgs)
    {
        return getResources().getString(id, formatArgs);
    }

    public static void showToast(Context mContext, int id)
    {
        if(mContext!=null)
        {
            showToast(mContext, mContext.getResources().getString(id));
        }
    }

    /**
     * 可以在任何线程调用此方法进行UI显示
     *
     * @param message
     */
    public static void showToast(Context mContext, String message)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        }
        else
        {
            mToast.setText(message);
        }
        post(new Runnable()
        {
            @Override
            public void run()
            {
                mToast.show();
            }
        });
    }

    /**
     * 可以在任何线程调用此方法进行UI显示
     *
     * @param message
     */
    public static void showToast(String message)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        }
        else
        {
            mToast.setText(message);
        }
        post(new Runnable()
        {
            @Override
            public void run()
            {
                mToast.show();
            }
        });
    }

    /**
     * 获取屏幕实际宽度
     *
     * @param mContext 上下文，只能用Activity当前上下文
     * @return 设备实际宽度
     */
    public static int getScreenX(Context mContext)
    {
        return ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕实际高度
     *
     * @param mContext 上下文，只能用Activity当前上下文
     * @return 设备实际高度
     */
    public static int getScreenY(Context mContext)
    {
        return ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
    }

    public static void doAlphAnimation(View v, float start, float end, long duration)
    {
        AlphaAnimation alpha = new AlphaAnimation(start, end);
        alpha.setDuration(duration);
        alpha.setFillAfter(true);
        v.startAnimation(alpha);
    }

    public static void setListViewHeightBasedOnChildren(ExpandableListView listView)
    {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
        {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++)
        {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 取消显示Loading对话框
     */
    public static void dimissLoading()
    {
        if (alertDialog != null && alertDialog.isShowing())
        {
            alertDialog.dismiss();
        }
    }

    /**
     * 显示loading对话框
     *
     * @param mContext 上下文
     */
    public static void showLoading(Context mContext)
    {
        if (mContext instanceof Activity && ((Activity) mContext).isFinishing())
        {
            return;
        }

        dimissLoading();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mDialogView = inflater.inflate(R.layout.widget_loading, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setContentView(mDialogView);
        alertDialog.getWindow().setLayout(UIUtils.dip2px(200), UIUtils.dip2px(150));
    }

    public static boolean isLoadingShowing()
    {
        return (alertDialog == null) ? false : alertDialog.isShowing();
    }

    /**
     * 隐藏输入法
     *
     * @param mContext
     */
    public static void hideInput(Context mContext, View view)
    {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen)
        {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showSuccess(Context context, String msg)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View mDialogView = inflater.inflate(R.layout.widget_success, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView mTvMsg = (TextView) mDialogView.findViewById(R.id.toast_tv);
        mTvMsg.setText(msg == null ? "申请提现成功" : msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setContentView(mDialogView);
        alertDialog.getWindow().setLayout(UIUtils.dip2px(200), UIUtils.dip2px(150));
        new CountDownTimer(2000, 500)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {

            }

            @Override
            public void onFinish()
            {
                alertDialog.dismiss();
            }
        }.start();
    }

    public static void hintUserName(TextView textView, String name)
    {
        if (null==textView)
        {
            return;
        }
        if (TextUtils.isEmpty(name))
        {
            textView.setText("");
        }
        else if (name.length() > 0 && name.length() <= 4)
        {
            textView.setText(name.substring(0, 1) + "***");
        }
        else if (name.length() > 4)
        {
            textView.setText(name.substring(0, 2) + "***" + name.substring(name.length() - 2, name.length()));
        }
    }

    public static String hintSingleUserName(String name)
    {
        if (!TextUtils.isEmpty(name))
        {
            if (name.length() <= 4)
            {
                return name.substring(0, 1) + "***";
            }
            else
            {
                return name.substring(0, 2) + "***" + name.substring(name.length() - 2, name.length());
            }
        }
        return "";
    }
}
