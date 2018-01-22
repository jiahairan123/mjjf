package com.mingjie.jf.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mingjie.jf.application.BaseApplication;
import com.mingjie.jf.bean.AppConfigInfoResponse;
import com.mingjie.jf.bean.UserVo;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.encrypt.DESUtil;
import com.mingjie.jf.widgets.LockPatternView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.utils
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 工具类
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/15
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CacheUtils
{
    private final static String SP_NAME = "cgkx";
    private static SharedPreferences mPreferences;        // SharedPreferences的实例
    private final static String HOME_DATA = "homedata";  //首页数据
    private final static String HOME_BIAO_DATA = "homebiaodata";

    /**
     * 用户信息
     */
    public final static String UserInfo = "user";

    private static SharedPreferences getSp(Context context)
    {
        if (mPreferences == null)
        {
            mPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return mPreferences;
    }

    /**
     * 通过SP获得boolean类型的数据，没有默认为false
     *
     * @param context : 上下文
     * @param key     : 存储的key
     * @return
     */
    public static boolean getBoolean(Context context, String key)
    {
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key, false);
    }

    /**
     * 通过SP获得boolean类型的数据，没有默认为false
     *
     * @param context  : 上下文
     * @param key      : 存储的key
     * @param defValue : 默认值
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue)
    {
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 设置boolean的缓存数据
     *
     * @param context
     * @param key     :缓存对应的key
     * @param value   :缓存对应的值
     */
    public static void setBoolean(Context context, String key, boolean value)
    {
        SharedPreferences sp = getSp(context);
        Editor edit = sp.edit();// 获取编辑器
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * 通过SP获得String类型的数据，没有默认为null
     *
     * @param context : 上下文
     * @param key     : 存储的key
     * @return
     */
    public static String getString(Context context, String key)
    {
        SharedPreferences sp = getSp(context);
        return DES3EncryptAndDecrypt.des3DecryptMode(sp.getString(key, null));
    }

    /**
     * 通过SP获得String类型的数据
     *
     * @param context  : 上下文
     * @param key      : 存储的key
     * @param defValue : 默认值
     * @return
     */
    public static String getString(Context context, String key, String defValue)
    {
        SharedPreferences sp = getSp(context);
        if (Constants.PASSWORD.equals(key))
        {
            String pwdEncrypt = sp.getString(key, defValue);
            String pwd = DESUtil.decrypt(pwdEncrypt);
            // 如果是明文保存密码，改成加密保存密码
            if (pwd != null && pwd.equals(pwdEncrypt))
            {
                setString(context, key, pwdEncrypt);
            }

            return pwd == null ? "NULL" : pwd;
        }
        return DES3EncryptAndDecrypt.des3DecryptMode(sp.getString(key, defValue));
        //        return null;
    }

    /**
     * 通过SP获得String类型的数据
     *
     * @param key      : 存储的key
     * @param defValue : 默认值
     * @return
     */
    public static String getString(String key, String defValue)
    {
        SharedPreferences sp = getSp(BaseApplication.getContext());

        if (Constants.PASSWORD.equals(key))
        {
            String pwdEncrypt = sp.getString(key, defValue);
            String pwd = DESUtil.decrypt(pwdEncrypt);
            // 如果是明文保存密码，改成加密保存密码
            if (pwd != null && pwd.equals(pwdEncrypt))
            {
                setString(BaseApplication.getContext(), key, pwdEncrypt);
            }

            return pwd == null ? "NULL" : pwd;
        }
        return DES3EncryptAndDecrypt.des3DecryptMode(sp.getString(key, defValue));

    }

    /**
     * 设置String的缓存数据
     *
     * @param context
     * @param key     :缓存对应的key
     * @param value   :缓存对应的值
     */
    public static void setString(Context context, String key, String value)
    {
        SharedPreferences sp = getSp(context);
        Editor edit = sp.edit();// 获取编辑器

        /**
         * 将密码加密
         */
        if (Constants.PASSWORD.equals(key))
        {
            edit.remove(key);
            edit.putString(key, DESUtil.encrypt(value));
        }
        else
        {
            edit.putString(key, DES3EncryptAndDecrypt.des3EncryptMode(value));
        }

        edit.commit();
    }


//    public static void setSeeString(Context context, String key, String value)
//    {
//        SharedPreferences sp = getSp(context);
//        Editor edit = sp.edit();// 获取编辑器
//
//
//        if (Constants.SEE_PASSWORD.equals(key))
//        {
//            edit.remove(key);
//            edit.putString(key, value);
//        }
//        else
//        {
//            edit.putString(key, value);
//        }
//
//        edit.commit();
//    }



//    public static String getSeeString(String key, String defValue)
//    {
//        SharedPreferences sp = getSp(BaseApplication.getContext());
//        if (Constants.SEE_PASSWORD.equals(key))
//        {
//            String pwdEncrypt = sp.getString(key, defValue);
//            String pwd = pwdEncrypt;
//            // 如果是明文保存密码，改成加密保存密码
//            if (pwd != null && pwd.equals(pwdEncrypt))
//            {
//                setString(BaseApplication.getContext(), key, pwdEncrypt);
//            }
//
//            return pwd == null ? "NULL" : pwd;
//        }
//        return sp.getString(key, defValue);
//        //        return null;
//    }


    public static void remove(Context context, String key)
    {
        SharedPreferences sp = getSp(context);
        Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }

    /**
     * 通过SP获得Long类型的数据，没有默认为-1
     *
     * @param context : 上下文
     * @param key     : 存储的key
     * @return
     */
    public static long getLong(Context context, String key)
    {
        SharedPreferences sp = getSp(context);
        return sp.getLong(key, -1);
    }

    /**
     * 通过SP获得Long类型的数据
     *
     * @param context  : 上下文
     * @param key      : 存储的key
     * @param defValue : 默认值
     * @return
     */
    public static long getLong(Context context, String key, long defValue)
    {
        SharedPreferences sp = getSp(context);
        return sp.getLong(key, defValue);
    }

    public static int getInt(Context context, String key)
    {
        SharedPreferences sp = getSp(context);
        return sp.getInt(key, 0);
    }

    public static int getInt(Context context, String key, int defValue)
    {
        SharedPreferences sp = getSp(context);
        return sp.getInt(key, defValue);
    }

    /**
     * 设置Long的缓存数据
     *
     * @param context
     * @param key     :缓存对应的key
     * @param value   :缓存对应的值
     */
    public static void setLong(Context context, String key, long value)
    {
        SharedPreferences sp = getSp(context);
        Editor edit = sp.edit();// 获取编辑器
        edit.putLong(key, value);
        edit.commit();
    }

    public static void setInt(Context context, String key, int value)
    {
        SharedPreferences sp = getSp(context);
        Editor edit = sp.edit();// 获取编辑器
        edit.putInt(key, value);
        edit.commit();
    }

    /**
     * 保存首页数据 （ 标以外的数据 ）
     */
    public static void setHomeData(Context context, String homeData)
    {
        SharedPreferences sp = context.getSharedPreferences(CacheUtils.HOME_DATA, Context.MODE_PRIVATE);
        Editor editor = sp.edit();//获取编辑器
        editor.putString(Constants.HOME_DATA, DES3EncryptAndDecrypt.des3EncryptMode(homeData));
        editor.commit();
    }

    /**
     * 获取首页数据 （ 标以外的数据 ）
     *
     * @param context
     */
    public static String getHomeData(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(CacheUtils.HOME_DATA, Context.MODE_PRIVATE);
        String homeData = DES3EncryptAndDecrypt.des3DecryptMode(sp.getString(Constants.HOME_DATA, null));
        return homeData;
    }

    /**
     * 保存首页数据 （ 标的数据 ）
     *
     * @param context
     * @param homeBiaoData
     */
    public static void setHomeBiaoData(Context context, String homeBiaoData)
    {
        SharedPreferences sp = context.getSharedPreferences(CacheUtils.HOME_BIAO_DATA, Context.MODE_PRIVATE);
        Editor editor = sp.edit();//获取编辑器
        editor.putString(Constants.HOME_BIAO_DATA, DES3EncryptAndDecrypt.des3EncryptMode(homeBiaoData));
        editor.commit();
    }

    public static String getHomeBiaoData(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(CacheUtils.HOME_BIAO_DATA, Context.MODE_PRIVATE);
        String homebiaoData = DES3EncryptAndDecrypt.des3DecryptMode(sp.getString(Constants.HOME_BIAO_DATA, null));
        return homebiaoData;
    }

    /**
     * 保存用户信息
     */
    public static void saveUserInfo(Context context, UserVo userInfoEntity)
    {
        UserVo.DataBean.UserBean vo = userInfoEntity.getData().getUser();
        SharedPreferences sharedPreferences = context.getSharedPreferences(CacheUtils.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString(Constants.IDNUMBER, DES3EncryptAndDecrypt.des3EncryptMode(vo.getId()));
        editor.putString(Constants.NAME, DES3EncryptAndDecrypt.des3EncryptMode(vo.getName()));
        editor.putString(Constants.PHONE_NUM, DES3EncryptAndDecrypt.des3EncryptMode(vo.getPhone()));
//                editor.putString(Constants.PASSWORD, UserInfoEntity.getData().getUser().getPassword());
        editor.putInt(Constants.ISFUNDDEPOSITY, vo.getIsFundDepository());
        editor.putInt(Constants.ISFORBITWITHDRAW, vo.getIsForbitWithdraw());
        editor.putInt(Constants.ISFORBITRECEIPTTRANFER, vo.getIsForbitReceiptTransfer());
        // editor.putString(Constants.TOKEN, UserInfoEntity.getData().getToken());
        editor.putString(Constants.EMAILE, DES3EncryptAndDecrypt.des3EncryptMode(vo.getEmail()));
        editor.putString("refcode", DES3EncryptAndDecrypt.des3EncryptMode(vo.getRefcode()));
        editor.commit();//提交修改
    }

    /**
     * 设置进用户对象
     *
     * @param context
     * @return
     */
    public static UserVoValible getUserinfo(Context context)
    {
        UserVoValible userInfoEntity = new UserVoValible();
        SharedPreferences sp = context.getSharedPreferences(CacheUtils.SP_NAME, Context.MODE_PRIVATE);
        userInfoEntity.setId(DES3EncryptAndDecrypt.des3DecryptMode(sp.getString(Constants.IDNUMBER, null)));
        userInfoEntity.setName(DES3EncryptAndDecrypt.des3DecryptMode(sp.getString(Constants.NAME, null)));
        userInfoEntity.setPhone(DES3EncryptAndDecrypt.des3DecryptMode(sp.getString(Constants.PHONE_NUM, null)));
        // userInfoEntity.setToken(sharedPreferences.getString(Constants.TOKEN, null));
        // userInfoEntity.setPassword(DES3EncryptAndDecrypt.des3DecryptMode(sp.getString(Constants.PASSWORD, null)));
        userInfoEntity.setPassword(sp.getString(Constants.PASSWORD, null));
        userInfoEntity.setEmail(DES3EncryptAndDecrypt.des3DecryptMode(sp.getString(Constants.EMAILE, null)));
        userInfoEntity.setRefcode(DES3EncryptAndDecrypt.des3DecryptMode(sp.getString("refcode", null)));
        return userInfoEntity;
    }

    /**
     * 清楚用户数据
     */
    public static void clearUserInfo()
    {
        HttpManager.getInstance().userCookie = "";
        setString(UIUtils.getContext(),"refcode", "");
        setString(UIUtils.getContext(), Constants.NAME, "");
        setString(UIUtils.getContext(), Constants.EMAILE, "");
        setString(UIUtils.getContext(), Constants.IDNUMBER, "");
        setString(UIUtils.getContext(), Constants.PHONE_NUM, "");
        setInt(UIUtils.getContext(), Constants.ISFUNDDEPOSITY, 0);
        setInt(UIUtils.getContext(), Constants.ISFORBITBID, 0);
        setInt(UIUtils.getContext(), Constants.ISFORBITWITHDRAW, 0);
        setInt(UIUtils.getContext(), Constants.ISFORBITRECEIPTTRANFER, 0);
        setBoolean(UIUtils.getContext(), Constants.LOCK, false);
        setBoolean(UIUtils.getContext(), Constants.IS_LOGIN, false);
        BaseApplication.getLockPatternUtils().clearLock();
    }

    //保存密码
    public static void savePassword(String passwordStr)
    {
        //        if(passwordStr!=null && passwordStr.trim().length()!=0){
        //            try {
        //                String encPassword = DESUtil.encrypt(passwordStr);
        //                //Log.e("encPassword", encPassword);
        //                setString(UIUtils.getContext(), Constants.PASSWORD, encPassword);
        //            } catch (Exception e) {
        //
        //            }
        //
        //        }

    }

    //获取密码
    public static String getPassword()
    {
        //        String decPassword;
        //        String encPassword = getString(UIUtils.getContext(), Constants.PASSWORD, "userpassword=null");
        //        if("userpassword=null".equals(encPassword)){
        //            decPassword =  "userpassword=null";
        //        }else {
        //            decPassword = DESUtil.decrypt(encPassword);
        //        }
        //
        //        //Log.e("decPassword", decPassword);
        return null;
    }

    /**
     * @return 当前登陆状态
     */
    public static boolean isLogin()
    {
        return getBoolean(BaseApplication.getContext(), Constants.IS_LOGIN);
    }

    /**
     * 保存APP配置信息
     *
     * @param appInfo
     */
    public static void saveAppConfigInfo(AppConfigInfoResponse appInfo)
    {
        setString(UIUtils.getContext(), Constants.SERVICE_LINE, appInfo.getServicePhone());
    }

    /**
     * 获取APP配置信息
     */
    public static AppConfigInfoResponse getAppConfigInfo()
    {
        AppConfigInfoResponse appInfo = new AppConfigInfoResponse();
        appInfo.setServicePhone(getString(BaseApplication.getContext(), Constants.SERVICE_LINE, "400-***-5859"));
        return appInfo;
    }

    /**
     * 保存手势密码
     *
     * @param pattern  手势密码
     * @param userName 保存key为 userName_gesture_pwd
     */
    public static void saveGesturePwd(List<LockPatternView.Cell> pattern, String userName)
    {
        try
        {
            FileOutputStream fileOutput = BaseApplication.getContext().openFileOutput(userName + "_" + Constants
                    .GESTURE_PWD, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutput);
            objectOutputStream.writeObject(pattern);
            objectOutputStream.close();
            fileOutput.close();
        }
        catch (FileNotFoundException e)
        {
            CfLog.e(e.toString());
        }
        catch (IOException e)
        {
            CfLog.e(e.toString());
        }
    }

    /**
     * 根据用户名获取手势密码缓存
     *
     * @param userName
     * @return
     */
    public static List<LockPatternView.Cell> getGesturePwd(String userName)
    {
        try
        {
            FileInputStream fileInputStream = BaseApplication.getContext().openFileInput(userName + "_" + Constants
                    .GESTURE_PWD);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (List<LockPatternView.Cell>) objectInputStream.readObject();
        }
        catch (FileNotFoundException e)
        {
            CfLog.e(e.toString());
        }
        catch (StreamCorruptedException e)
        {
            CfLog.e(e.toString());
        }
        catch (IOException e)
        {
            CfLog.e(e.toString());
        }
        catch (ClassNotFoundException e)
        {
            CfLog.e(e.toString());
        }
        return null;
    }
}
