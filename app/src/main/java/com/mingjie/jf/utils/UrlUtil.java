package com.mingjie.jf.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.mingjie.jf.BuildConfig;
import com.mingjie.jf.application.BaseApplication;

/**
 * Created by Administrator on 2016/7/25.
 */
public class UrlUtil {
    private static final String CGKX = "cgkx";
    private static final String CUR_URL = "curUlr";

    // https://192.168.1.100/mobile/imageVcode/getImageVcode
    // https://192.168.1.100/mobile/mobileInterfaces
    // https://192.168.1.100/mobile/content/getUserAgreement

    // URL 前半段
    public static final String BASE_URL = "https://mingjiecf.com";

//    97
//    public static final String BASE_URL = "https://123.56.207.97";

//    public static final String BASE_URL = "https://dev.mingjiecf.com";

    //姐
//    public static final String BASE_URL = "http://1700g595r8.iok.la:10419/mobile";

    //杨哥电脑
//    public static final String BASE_URL = "http://192.168.1.68:8080/mobile";

    //浩姐电脑
//    public static final String BASE_URL = "http://192.168.1.113:8080/mobile";

    //杨哥
//    public static final String BASE_URL = "http://devtest.mingjiecf.com:20101";
    //
    //97 测试服
//    public static final String BASE_URL = "http://192.168.1.213:8080/mobile";
//      public static final String BASE_URL = "http://jiahairan123.55555.io:12752/mobile";
    // private static final String BASE_URL = "https://192.168.1.167";
    // private static final String BASE_URL = "http://192.168.100.247:8080/cgkx-mobile";
    // private static String BASE_URL = "http://192.168.99.133:8080/cgkx-mobile";

    // 总接口
    private static final String INTERFACES_URL = "/mobile/mobileInterfaces";

    // 图形验证码
    private static final String IMG_URL = "/mobile/imageVcode/getImageVcode";

    // 隐私政策
    private static final String PRIVATE_URL = "/mobile/content/getUserAgreement";

    // 借款协议
//  private static String BORROW_AGREEMENT = "/mobile/content/getBorrowAgreement?productId=";
    private static String BORROW_AGREEMENT = "/mobile/newContent/toBorrowAgreement?productId=";

    //风险测评
    private static String RISK_TEST = "/mobile/risk/jumpToWXRisk";
    public static String RISK_BEFORE =  UrlUtil.BASE_URL + "/mobile/risk/jumpToWXRiskQuestion";
    // 跳转微信登录页面(测试环境)
    //    public static String WEIXIN_LOGIN_TEST = "https://192.168.1.167/";
    // 跳转微信登录页面(正式环境)
    public static final String WEIXIN_LOGIN = "https://demo.ming-hao.cn";
    
    static String baseUrl = null;
    static String interfacesUrl = null;
    static String imgUrl = null;
    static String privateUrl = null;
    static String borrowUrl = null;
    static String riskUrl = null;

    public static String getInterfacesUrl() {
        if (null == interfacesUrl || "".equals(interfacesUrl)) {
            SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(CGKX, Context.MODE_PRIVATE);
            interfacesUrl = sp.getString(CUR_URL, BASE_URL) + INTERFACES_URL;
        }
        // CfLog.i("----" + interfacesUrl);
        return interfacesUrl;
    }

    public static String getImgUrl() {
        if (null == imgUrl || "".equals(imgUrl)) {
            SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(CGKX, Context.MODE_PRIVATE);
            imgUrl = sp.getString(CUR_URL, BASE_URL) + IMG_URL;
        }
        CfLog.d(imgUrl);
        return imgUrl;
    }

    public static String getPrivateUrl() {
        if (null == privateUrl || "".equals(privateUrl)) {
            SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(CGKX, Context.MODE_PRIVATE);
            privateUrl = sp.getString(CUR_URL, BASE_URL) + PRIVATE_URL;
        }
        CfLog.d(privateUrl);
        return privateUrl;
    }

    public static String getBorrowUrl() {
        if (null == borrowUrl || "".equals(baseUrl)) {
            SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(CGKX, Context.MODE_PRIVATE);
            borrowUrl = sp.getString(CUR_URL, BASE_URL) + BORROW_AGREEMENT;
        }
        CfLog.d(borrowUrl);
        return borrowUrl;
    }

    public static String getRiskTestUrl() {
        if (null == riskUrl || "".equals(riskUrl)) {
            SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(CGKX, Context.MODE_PRIVATE);
            riskUrl = sp.getString(CUR_URL, BASE_URL) + RISK_TEST;
        }
        CfLog.d(riskUrl);
        return riskUrl;
    }

    public static String getDefaultUrl() {
        return BASE_URL;
    }

    /**
     * 获取接口的前半段
     *
     * @return
     */
    public static String getUrl() {
        if (null == baseUrl || "".equals(baseUrl)) {
            SharedPreferences sp = BaseApplication.getContext().getSharedPreferences("cgkx", Context.MODE_PRIVATE);
            baseUrl = sp.getString(CUR_URL, BASE_URL);
        }
        // CfLog.d("----" + baseUrl);
        return baseUrl;
    }

    /**
     * 设置前半段
     *
     * @param url
     */
    public static void setUrl(String url) {
        baseUrl = url;
        resetUrl();
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(CGKX, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = sp.edit();
        edt.putString(CUR_URL, url);
        edt.commit();
    }

    public static void removeUrl() {
        baseUrl = BASE_URL;
        resetUrl();
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(CGKX, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = sp.edit();
        edt.remove(CUR_URL);
        edt.commit();
    }

    private static void resetUrl() {
        interfacesUrl = baseUrl + INTERFACES_URL;
        imgUrl = baseUrl + IMG_URL;
        privateUrl = baseUrl + PRIVATE_URL;
        borrowUrl = baseUrl + BORROW_AGREEMENT;
        riskUrl = baseUrl + RISK_TEST;
    }

    /**
     * 是否短信调试模式
     */
    public static boolean isSmsDebug() {
        if (getUrl().contains("192.168.") || getUrl().toLowerCase().contains("test")) {
            return true;
        }
        return BuildConfig.DEBUG;
    }
}
