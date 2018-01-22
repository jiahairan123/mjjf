package com.mingjie.jf.utils;

import android.content.Context;
import android.content.res.AssetManager;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/*
 *  @描述：    字符串处理工具类
 */
public class StringUtils {
    private static StringUtils mStringUtils = null;
  //  private static String[] dictionary = UIUtils.getStringArray(R.array.returnMethod);

    public static StringUtils getInstance() {
        if (mStringUtils != null) {
            return mStringUtils;
        } else {
            return new StringUtils();
        }
    }

    private StringUtils() {
    }

    /**
     * DiskLruCache用
     *
     * @param key
     * @return
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try
        {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes("UTF-8"));
            cacheKey = bytesToHexString(mDigest.digest());
        }
        catch (UnsupportedEncodingException e)
        {
            cacheKey = String.valueOf(key.hashCode());
        }
        catch (NoSuchAlgorithmException e)
        {
            cacheKey = String.valueOf(key.hashCode());
        }

        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static float save2digit(double number) {
        return (float) (Math.round(number * 100)) / 100;
    }

    /**
     * 选择还款方式
     *
     * @param type
     * @return
     */
   // public static String setReturnMethod(int type) {
//        if (type >= dictionary.length) {
//            return null;
//        }
//        return dictionary[type];
  //  }

    /**
     * 将double转换成保留两位小数的百分比形式
     *
     * @param rate
     * @return
     */
    public static String format2Percent(double rate) {
        double tempRate = rate * 100;
        NumberFormat nf = new DecimalFormat("0.00");
        return nf.format(tempRate) + "%";
    }

    public static String format2Percent(double rate, boolean isShowSymbol) {
        double tempRate = rate * 100;
        NumberFormat nf = new DecimalFormat("0.00");
        return isShowSymbol ? nf.format(tempRate) + "%" : nf.format(tempRate);
    }

    /**
     * 读取资源文件本地的json文件
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return
     */
    public static String getJson(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        String json;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bf = null;
        InputStream inputStream = null;
        try {
            bf = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));
            inputStream = assetManager.open(fileName);
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            CfLog.e(e.toString());
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                CfLog.e(e.toString());
            } finally {
                bf = null;
                inputStream = null;
            }
        }

        return stringBuilder.toString();

    }

    /**
     * 将double成金额千位带两位小数形式
     *
     * @param amount
     * @return
     */
    public static String dou2Str(double amount) {
        amount = Math.floor(amount*10000) / 10000;
        NumberFormat nf = new DecimalFormat(",##0.00");
        return nf.format(amount);
    }

    /**
     *
     * @param data
     * @return
     */
    public static String double2Str(double data) {
        data = Math.floor(data*10000) / 10000;
        NumberFormat formater = new DecimalFormat("0.00");
        return formater.format(data);
    }

    public static String doubleStr(double data) {
        data = Math.floor(data*10000) / 10000;
        NumberFormat formater = new DecimalFormat("0.0");
        return formater.format(data);
    }
    /**
     * 校验是否为邮箱
     *
     * @param emailText
     * @return
     */
    public static boolean isEmail(String emailText) {
        String mEmailRules = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        return emailText.matches(mEmailRules);
    }

    /**
     * 校验是否为身份证号码
     */
    public static boolean isIDCardNumber(String idnumber) {
        String mIDnumber = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
        return idnumber.matches(mIDnumber);
    }

    /**
     * 校验是否为汉字
     *
     * @param chinsesName
     * @return
     */
    public static boolean isChineseChart(String chinsesName) {
        String rules = "^[\\u4E00-\\u9FA5]+$";
        return chinsesName.matches(rules);
    }

    /**
     * 校验是否为国内手机号码，支持13、14、15、18、17打头
     *
     * @param number
     * @return
     */
    public static boolean isPhoneNumber(String number) {
        String rules = "0?(13|14|15|18|17)[0-9]{9}";
        return number.matches(rules);
    }
}
