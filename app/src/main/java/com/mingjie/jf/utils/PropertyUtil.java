package com.mingjie.jf.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.utils
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： Properties 文件读取公共类
 * <p>创 建 人：ZhangJin
 * <p>创建时间： 2016-11-22 14:27
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class PropertyUtil
{
    private static Properties pro = null;

    private final static String FILE_NAME = "cfg.properties";

    private static Properties getProperties(Context ctx)
    {
        if (pro != null)
        {
            return pro;
        }

        pro = new Properties();
        try
        {
            InputStream is = ctx.getAssets().open(FILE_NAME);
            pro.load(is);
            // pro.load(FileLoad.class.getResourceAsStream("/assets/test.properties"));
        }
        catch (IOException e)
        {
            CfLog.e(e.toString());
        }
        return pro;
    }

    public static String getString(Context ctx, String key)
    {
        return getProperties(ctx).getProperty(key);
    }

    public static String getString(Context ctx, String key, String dev)
    {
        return getProperties(ctx).getProperty(key, dev);
    }

}
