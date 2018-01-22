package com.mingjie.jf.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * <p>项目名称：CgkxForHtjf
 * <p>包   名： com.minghao.cgkx4htjf.utils
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 单位转换工具类
 * <p>创 建 人： wuxianai
 * <p>邮箱地址： wuxianai@caffcoo.com
 * <p>创建时间： 2016/6/21 20:42
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public final class UnitConversionUtil {
    private UnitConversionUtil() {
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
