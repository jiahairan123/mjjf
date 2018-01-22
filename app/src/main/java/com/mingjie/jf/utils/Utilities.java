package com.mingjie.jf.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by qinsiyi on 2016.6.28.
 */
public class Utilities
{

    /*
     * 测试地址
     */
    public final static String StrUrl = "http://192.168.1.167:8080/cgkx-api/api/mobileInterfaces";

    /**
     * 获取图形验证码
     */

    public final static String URL_IMG_CODE = "http://192.168.1.167:8080/cgkx-api/imageVcode";

    //加载h5页面
    public final static String WebUrl = "";

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str)
    {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        return p.matcher(str).matches();
    }

    /**
     * long类型时间格式化
     */
    public static String convertToTime(long time)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return df.format(date);
    }

    /**
     * 获取软件相关信息
     *
     * @return
     */
    public static String initVersion(Context context)
    {
        try
        {
            // 获取的版本名称
            String verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return verName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return "1.0.0";
        }
    }

    /**
     * 获取软件相关信息
     *
     * @return
     */
    public static int versionCode(Context context)
    {
        try
        {
            // 获取的软件版本号
            int verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            return verCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return 1;
        }
    }

    /**
     * 判断edittext是否null
     */
    public static String checkEditText(EditText editText)
    {
        if (editText != null && editText.getText() != null && !(editText.getText().toString().trim().equals("")))
        {
            return editText.getText().toString().trim();
        }
        else
        {
            return "";
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 設置View的寬度（像素），若設置爲自適應則應該傳入MarginLayoutParams.WRAP_CONTENT
     *
     * @param view
     * @param width
     */
    public static void setLayoutWidth(View view, int width)
    {
        if (view.getParent() instanceof FrameLayout)
        {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
            lp.width = width;
            view.setLayoutParams(lp);
            // view.setX(x);
            view.requestLayout();
        }
        else if (view.getParent() instanceof RelativeLayout)
        {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
            lp.width = width;
            view.setLayoutParams(lp);
            // view.setX(x);
            view.requestLayout();
        }
        else if (view.getParent() instanceof LinearLayout)
        {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            lp.width = width;
            view.setLayoutParams(lp);
            // view.setX(x);
            view.requestLayout();
        }
    }

    /**
     * 設置View的寬度（像素），若設置爲自適應則應該傳入MarginLayoutParams.WRAP_CONTENT
     *
     * @param view
     * @param height
     */
    public static void setLayoutHeight(View view, int height)
    {
        if (view.getParent() instanceof FrameLayout)
        {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
            lp.height = height;
            view.setLayoutParams(lp);
            // view.setX(x);
            view.requestLayout();
        }
        else if (view.getParent() instanceof RelativeLayout)
        {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
            lp.height = height;
            view.setLayoutParams(lp);
            // view.setX(x);
            view.requestLayout();
        }
        else if (view.getParent() instanceof LinearLayout)
        {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            lp.height = height;
            view.setLayoutParams(lp);
            // view.setX(x);
            view.requestLayout();
        }
    }

    /**
     * 获取网落图片资源
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url)
    {
        URL myFileURL;
        Bitmap bitmap = null;
        try
        {
            myFileURL = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            // 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            // 连接设置获得数据流
            conn.setDoInput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            // 这句可有可无，没有影响
            // conn.connect();
            // 得到数据流
            InputStream is = conn.getInputStream();
            // 解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            // 关闭数据流
            is.close();
        }
        catch (Exception e)
        {
            CfLog.e(e.toString());
        }
        return bitmap;
    }

    // 生成图片名称
    public static String getPhotoFileName()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return "DCIM/Camera/" + dateFormat.format(date) + ".jpg";
    }

    // 生成图片名称
    public static String getPhotoFileNames()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG1'_yyyyMMdd_HHmmss");
        return "DCIM/Camera/" + dateFormat.format(date) + ".jpg";
    }

    public static boolean saveBitmap2file(Bitmap bmp)
    {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try
        {
            stream = new FileOutputStream(getPhotoFileNames());
        }
        catch (FileNotFoundException e)
        {
            CfLog.e(e.toString());
        }
        return bmp.compress(format, quality, stream);
    }

    /**
     * url转bitmap
     *
     * @param url
     * @return
     */
    public final static Bitmap returnBitMap(String url)
    {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try
        {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        }
        catch (MalformedURLException e)
        {
            CfLog.e(e.toString());
        }
        catch (IOException e)
        {
            CfLog.e(e.toString());
        }
        return bitmap;
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap)
    {

        String result = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);// 参数100表示不压缩
        byte[] bytes = baos.toByteArray();
        result = Base64.encodeToString(bytes, Base64.DEFAULT);
        try
        {
            baos.flush();
            baos.close();
        }
        catch (IOException e)
        {
            CfLog.e(e.toString());
        }
        return result;
    }

    /**
     * Gps是否打开
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context)
    {
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = locationManager.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;

    }

    /**
     * wifi是否打开
     */
    public static boolean isWifiEnabled(Context context)
    {
        ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null
                && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
                || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 判断当前网络是否是wifi网络 if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
     *
     * @param context
     * @return boolean
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI)
        {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有网络连接
     *
     * @param context 上下文Context
     * @return false没有联网，true已经联网
     */
    public static boolean canNetworkUseful(Context context)
    {
        // ConnectivityManager 获取当前系统服务信息
        // getNetworkInfo 分别获取 类型为MOBILE和WIFI两种联网方式的信息。
        if (context == null)
        {
            return false;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null)
        {
            return false;
        }
        // 获取当前活动的网络状态（如果同时开启3G和WIFI，则当前活动网络是WIFI），如果没有连接网络，将会返回null
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable())
        {
            return false;
        }
        return true;
    }

    public static final int Message_Cancel = 0;

    public static final int Message_Succeed = 1;

    /**
     * 如果没有联网，显示提示联网对话框；已经联网，返回false
     *
     * @param context context
     * @return false没有联网，true已经联网
     */
    public static boolean showCheckNetworkDialog(final Context context)
    {
        if (!canNetworkUseful(context))
        {
            new AlertDialog.Builder(context).setMessage("当前网络不可用，请检查你的网络设置。")
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            // 在安卓3.0以上的版本中，wifi联网的设置被放置在了主设置页面中。在之前的版本中，wifi联网在wifi设置页面中。根据不同的版本进行不同的页面跳转
                            if (android.os.Build.VERSION.SDK_INT > 10)
                            {
                                context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                            }
                            else
                            {
                                context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                            }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            // handler.sendEmptyMessage(Message_Cancel);
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);
                        }
                    })
                    .show();
            return false;
        }
        return true;
    }

    public static boolean showGpsworkDialog(final Context context)
    {
        if (!isGpsEnabled(context))
        {
            new AlertDialog.Builder(context).setMessage("，为了精确定位，请打开GPS")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            // 在安卓3.0以上的版本中，wifi联网的设置被放置在了主设置页面中。在之前的版本中，wifi联网在wifi设置页面中。根据不同的版本进行不同的页面跳转
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .show();
            return false;
        }
        return true;
    }
}
