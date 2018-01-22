package com.mingjie.jf.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by qinsiyi on 2016.6.28.
 */
public class Utils
{
    
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    
    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5f);
    }
    
    /**
     * 获取状态栏高度＋标题栏高度
     *
     * @param activity
     * @return
     */
    public static int getTopBarHeight(Activity activity)
    {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }
    
    /**
     * 获得设备的屏幕高度
     *
     * @param context
     * @return
     */
    public static int getDeviceHeight(Context context)
    {
        return getDeviceSize(context).y;
    }
    
    /**
     * 获得设备的屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getDeviceWidth(Context context)
    {
        return getDeviceSize(context).x;
    }
    
    /**
     * 获取设备的高宽
     *
     * @param context
     * @return
     */
    public static Point getDeviceSize(Context context)
    {
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            point.x = manager.getDefaultDisplay().getWidth();
            point.y = manager.getDefaultDisplay().getHeight();
        }
        else
        {
            manager.getDefaultDisplay().getSize(point);
        }
        return point;
    }
}
