package com.mingjie.jf.utils;

import android.util.Log;

import com.mingjie.jf.BuildConfig;

/**
 * 日志接口 <功能详细描述>
 *
 * @author Administrator
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CfLog
{

    private final static String TAG = "CGKX";
    static String className;
    static String methodName;
    static int lineNumber;

    /**
     * 日志控制：发布版本不需要打印日志 当 CURRENT = VERBOSE时所有日志都打印 当CURRENT = NOTHING时 所有日志都不打印
     */
    private static int VERBOSE = 0;
    private static int DEBUG = 1;
    private static int INFO = 2;
    private static int WARNING = 3;
    private static int ERROR = 4;
    private static int NOTHING = 5;
    private static int CURRENT = isDebuggable() ? VERBOSE : NOTHING;

    private CfLog()
    {
    }

    public static boolean isDebuggable()
    {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log)
    {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]---");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements)
    {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(Exception e)
    {
        if (ERROR >= CURRENT)
        {
            if (!isDebuggable())
            {
                Log.e(TAG, e.toString());
                return;
            }
            getMethodNames(new Throwable().getStackTrace());
            Log.e(className, createLog(e.toString()));
            e.printStackTrace();
        }
    }

    public static void e(Exception e, String msg)
    {

        if (ERROR >= CURRENT)
        {
            if (!isDebuggable())
            {
                Log.e(TAG, msg + "\n" + e.toString());
                return;
            }
            getMethodNames(new Throwable().getStackTrace());
            StringBuffer sb = new StringBuffer();
            sb.append(msg + "\n" + e.toString());
            //StackTraceElement[] sElements = new Throwable().getStackTrace();
            //for (int i = 1; i < sElements.length; i++)
            //{
            //    StackTraceElement st = sElements[i];
            //    sb.append("\nat " + st.getClassName() + "." + st.getMethodName() + "(" + st.getFileName() + ":" + st
            //            .getLineNumber() + ")");
            //}
            Log.e(className, createLog(sb.toString()));
            e.printStackTrace();
        }
    }

    public static void e(String message)
    {

        if (ERROR >= CURRENT)
        {
            if (!isDebuggable())
            {
                Log.e(TAG, message + "");
                return;
            }
            getMethodNames(new Throwable().getStackTrace());
            Log.e(className, createLog(message));
        }
    }

    public static void i(String message)
    {
        if (INFO >= CURRENT)
        {
            if (!isDebuggable())
            {
                Log.i(TAG, message + "");
                return;
            }
            getMethodNames(new Throwable().getStackTrace());
            Log.i(className, createLog(message));
        }

    }

    public static void d()
    {
        if (DEBUG >= CURRENT)
        {
            if (!isDebuggable())
            {
                Log.d(TAG, "");
                return;
            }
            getMethodNames(new Throwable().getStackTrace());
            Log.d(className, createLog("--------"));
        }
    }

    public static void d(String message)
    {
        if (DEBUG >= CURRENT)
        {
            if (!isDebuggable())
            {
                Log.d(TAG, message + "");
                return;
            }
            getMethodNames(new Throwable().getStackTrace());
            Log.d(className, createLog(message));
        }
    }

    public static void v(String message)
    {
        if (VERBOSE >= CURRENT)
        {
            if (!isDebuggable())
            {
                Log.v(TAG, message + "");
                return;
            }

            getMethodNames(new Throwable().getStackTrace());
            Log.v(className, createLog(message));
        }
    }

    public static void w(String message)
    {
        if (WARNING >= CURRENT)
        {
            if (!isDebuggable())
            {
                Log.w(TAG, message + "");
                return;
            }

            getMethodNames(new Throwable().getStackTrace());
            Log.w(className, createLog(message));
        }
    }

    public static void wtf(String message)
    {
        if (!isDebuggable())
        {
            Log.wtf(TAG, message + "");
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

}
