package com.mingjie.jf.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.mingjie.jf.application.BaseApplication;

/**
 * <p>项目名称：CgkxForHtjf
 * <p>包   名： com.minghao.cgkx4htjf.utils
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： Toast 工具类
 * <p>创 建 人： wuxianai
 * <p>邮箱地址： wuxianai@caffcoo.com
 * <p>创建时间： 2016/6/30 19:22
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public final class ToastUtil {

    private static Toast toast;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长
    private ToastUtil() {
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    public static void showToast(Context context, int resId) {
        Toast.makeText(context, context.getResources().getText(resId),
                Toast.LENGTH_SHORT).show();
    }


    public static void showLongToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


    public static void showLongToast(Context context, int resId) {
        Toast.makeText(context, context.getResources().getText(resId),
                Toast.LENGTH_LONG).show();
    }


    public static void showCenterGraToast(String msg){
        if (BaseApplication.getContext() != null) {
            if (toast == null){

                toast = Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }

    }

    public static void showJiaToast(String msg){
        if (BaseApplication.getContext() != null) {
            if (toast == null){
                toast = Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

}
