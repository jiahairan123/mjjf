package com.mingjie.jf.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.utils
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 时间 转换工具
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-10-11 10:56
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class TimeFormatUtil
{

    public static String getHour()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return sdf.format(new Date());
    }

    /**
     * 取时刻，精确到15分钟
     * @return
     */
    public static String getSimpleTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:");
        String h = sdf.format(new Date());
        sdf = new SimpleDateFormat("mm");
        String m = sdf.format(new Date());
        int min = Integer.parseInt(m) / 15 * 15;

        if (min == 0)
        {
            return h + "00";
        }

        return h + min;
    }

    /**
     * 时间转换成 yyyy-MM-dd hh:mm:ss格式
     *
     * @param data
     * @return
     */
    public static String data2StrTime(Object data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(data);
    }

    /**
     * 时间转换成 yyyy-MM-dd格式
     *
     * @param data
     * @return
     */
    public static String data2StrTime2(Object data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(data);
    }

    /**
     * 时间转换成 yyyy年MM月格式
     *
     * @param data
     * @return
     */
    public static String data2StrTime3(Object data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        return sdf.format(data);
    }

    /**
     * 时间转换成 yyyy-MM-dd HH:mm 格式
     *
     * @param data
     * @return
     */
    public static String data2StrTime4(Object data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(data);
    }

    /**
     * 将yyyy-MM-dd hh:mm:ss时间 转换成毫秒
     *
     * @param strTime
     * @return
     */
    public static long strTime2Data(String strTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            return sdf.parse(strTime).getTime();
        }
        catch (ParseException e)
        {
            CfLog.e(e.toString());
        }
        return 0;
    }

    /**
     * 将yyyy-MM-dd时间 转换成毫秒
     *
     * @param strTime
     * @return
     */
    public static long strTime2Data2(String strTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            return sdf.parse(strTime).getTime();
        }
        catch (ParseException e)
        {
            CfLog.e(e.toString());
        }
        return 0;
    }

    /**
     * 将yyyy年MM月dd日时间 转换成毫秒
     *
     * @param strTime
     * @return
     */
    public static long strTime2Data3(String strTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try
        {
            return sdf.parse(strTime).getTime();
        }
        catch (ParseException e)
        {
            CfLog.e(e.toString());
        }
        return 0;
    }

    public static long gmt2utc(String str)
    {
        // Fri, 03 Mar 2017 04:32:16 GMT [china: 2017-03-03 12:32:16]
        String timeStyle = "EEE, dd MMM yyyy HH:mm:ss z";
        SimpleDateFormat sdf = new SimpleDateFormat(timeStyle, Locale.US);
        Date date;
        try
        {
            date = sdf.parse(str);
        }
        catch (ParseException e)
        {
            CfLog.i(e.toString());
            return 0;
        }
        return date.getTime();
    }

}
