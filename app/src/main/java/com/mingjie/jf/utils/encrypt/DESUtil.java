package com.mingjie.jf.utils.encrypt;

import com.mingjie.jf.application.BaseApplication;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.DES3EncryptAndDecrypt;
import com.mingjie.jf.utils.PropertyUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 项目名称：cgkx
 * 包   名：  cgkx.android.utils.encrypt
 * 版   权：  深圳市铭淏网络科技有限公司 ©2016
 * 描   述：
 * 创 建 人：qinsiyi
 * 邮箱地址： qinsiyi@caffcoo.com
 * 创建时间： 2016/8/15
 * 当前版本： V1.0.0
 * 修订历史： (版本、修改时间、修改人、修改内容)
 */
public final class DESUtil
{
    private DESUtil()
    {
    }

    private static final String ALGORITHM = "DES";

    private static String DEFAULT_KEY = null;

    private static final String DEFAULT_CHARSET_NAME = "UTF-8";

    private static String getKey()
    {
        if (null != DEFAULT_KEY)
        {
            return DEFAULT_KEY;
        }
        String key1 = PropertyUtil.getString(BaseApplication.getContext(), "key1");
        String key2 = PropertyUtil.getString(BaseApplication.getContext(), "key2");
        DEFAULT_KEY = DES3EncryptAndDecrypt.des3DecryptMode(key1 + key2);
        // CfLog.d("DEFAULT_KEY:" + DEFAULT_KEY);
        return DEFAULT_KEY;
    }

    /**
     * 加密文本
     *
     * @param text 要加密的内容
     * @return
     */
    public static String encrypt(String text)
    {
        return encrypt(text, getKey()); //DEFAULT_KEY
    }

    /**
     * 加密文本
     *
     * @param text 要加密的内容
     * @param key  加密key
     * @return
     */
    public static String encrypt(String text, String key)
    {
        return encrypt(text, key, DEFAULT_CHARSET_NAME);
    }

    /**
     * 加密文本
     *
     * @param text        要加密的内容
     * @param key         加密key
     * @param charsetName 编码格式
     * @return
     */
    public static String encrypt(String text, String key, String charsetName)
    {
        if (text == null)
        {
            return null;
        }

        try
        {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(charsetName), ALGORITHM));
            byte[] data = c.doFinal(text.getBytes(charsetName));
            return new String(Base64.encode(data), DEFAULT_CHARSET_NAME);
        }
        catch (Exception e)
        {
            CfLog.e(e.toString());
        }

        return text;
    }

    /**
     * 解密文本
     *
     * @param text 要解密的内容
     * @return
     */
    public static String decrypt(String text)
    {
        return decrypt(text, getKey());
    }

    /**
     * 解密文本
     *
     * @param text 要解密的内容
     * @param key  加密key
     * @return
     */
    public static String decrypt(String text, String key)
    {
        return decrypt(text, key, DEFAULT_CHARSET_NAME);
    }

    /**
     * 解密文本
     *
     * @param text        要解密的内容
     * @param key         加密key
     * @param charsetName 编码格式
     * @return
     */
    public static String decrypt(String text, String key, String charsetName)
    {
        if (text == null)
        {
            return null;
        }

        try
        {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(charsetName), ALGORITHM));
            byte[] data = c.doFinal(Base64.decode(text.getBytes(charsetName)));
            return new String(data, charsetName);
        }
        catch (Exception e)
        {
            CfLog.e(e.toString());
            return text;
        }
    }

}