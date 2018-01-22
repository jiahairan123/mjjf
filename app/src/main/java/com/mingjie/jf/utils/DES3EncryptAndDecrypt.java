package com.mingjie.jf.utils;

import android.util.Base64;

import com.mingjie.jf.application.BaseApplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

//import org.apache.commons.codec.binary.Base64;

public class DES3EncryptAndDecrypt
{

    private static String ALGORITHM = "DESede";
    private static final String CHARSET_NAME = "UTF-8";

    //3des应用加密密码
    private static String DES3KEY = null; // "AF0***A998";

    /**
     * 用原始KEY生成配置文件要用的KEY
     *
     * @param realKey
     */
    public static void setKey(String realKey)
    {
        CfLog.i("realKey=" + realKey);
        String key1, key2, key3, key4, key5, key6, uuid;
        int index = new Random().nextInt(realKey.length() / 2) + 1;

        uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        key2 = des3EncryptMode(uuid, realKey);
        key1 = key2.substring(0, index);
        key2 = key2.substring(index);
        CfLog.i("uuid=" + uuid + "\nkey1=" + key1 + "\nkey2=" + key2);

        key3 = UUID.randomUUID().toString().replace("-", "");
        key4 = realKey.substring(0, index);
        key5 = realKey.substring(index);

        try
        {
            String tmp = new String(Base64.encode(key3.getBytes(CHARSET_NAME), Base64.DEFAULT), CHARSET_NAME);
            CfLog.i("config key3=" + tmp);
        }
        catch (Exception e)
        {

        }
        key3 = toMD5(key3);
        key4 = des3EncryptMode(key4, key3);
        key5 = des3EncryptMode(key5, key3);

        index = new Random().nextInt(key5.length() / 2) + 1;
        key6 = key5.substring(index);
        key5 = key5.substring(0, index);

        CfLog.i("config key4=" + key6 + "," + key5 + "," + key4);
    }

    /**
     * 从配置文件读取KEY，用于解密
     *
     * @return
     */
    public static String getKey()
    {
        if (null != DES3KEY)
        {
            return DES3KEY;
        }

        String key3 = PropertyUtil.getString(BaseApplication.getContext(), "key3", "");
        try
        {
            key3 = new String(Base64.decode(key3, Base64.DEFAULT), CHARSET_NAME);
        }
        catch (UnsupportedEncodingException e)
        {
        }
        CfLog.d("key3=" + key3);
        key3 = toMD5(key3);
        String key4 = PropertyUtil.getString(BaseApplication.getContext(), "key4");
        CfLog.d(key4);
        String key6 = key4.split(",")[0];
        String key5 = key4.split(",")[1];
        key4 = key4.split(",")[2];
        key4 = DES3EncryptAndDecrypt.des3DecryptMode(key4, key3);
        key5 = DES3EncryptAndDecrypt.des3DecryptMode(key5 + key6, key3);

        DES3KEY = key4 + key5; // AF0***A998
        CfLog.d("DES3KEY=" + DES3KEY);
        return DES3KEY;
    }

    public static String des3EncryptMode(String src)
    {
        return des3EncryptMode(src, getKey());
    }

    /**
     * @param @param  src
     * @param @return
     * @param @throws Exception    设定文件
     * @return String    返回类型
     * @throws
     * @Title: DES3EncryptMode
     * @Description: (DES3加密)
     */
    public static String des3EncryptMode(String src, String key)
    {
        if (null == src)
        {
            return src;
        }

        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            DESedeKeySpec desKeySpec = new DESedeKeySpec(key.getBytes(CHARSET_NAME));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeToString(cipher.doFinal(src.getBytes(CHARSET_NAME)), Base64.DEFAULT);
            // return new String(Base64.encode(cipher.doFinal(src.getBytes(CHARSET_NAME))));
        }
        catch (NoSuchAlgorithmException e)
        {
            CfLog.e(e.toString());
        }
        catch (Exception e)
        {
            CfLog.e(e.toString());
        }

        return src;
    }

    public static String des3DecryptMode(String src)
    {
        return des3DecryptMode(src, getKey());
    }

    public static String des3DecryptMode(String src, String key)
    {

        if (null == src || "".equals(src))
        {
            return src;
        }

        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            DESedeKeySpec desKeySpec = new DESedeKeySpec(key.getBytes(CHARSET_NAME));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] dd = cipher.doFinal(Base64.decode(src.getBytes(CHARSET_NAME), Base64.DEFAULT));
            return new String(dd, CHARSET_NAME);
        }
        catch (NoSuchAlgorithmException e)
        {
            CfLog.e(e.toString());
        }
        catch (Exception e)
        {
            CfLog.e(e, key + "\n" + "src:" + src + "\n" + e.toString());
        }

        return src;
    }

    /***
     * MD5加码 生成32位md5码，中文转UTF-8
     */
    public static String toMD5(String str)
    {
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (Exception e)
        {
            CfLog.e(e.toString());
            return "";
        }

        byte[] input = new byte[0];
        try
        {
            input = str.getBytes(CHARSET_NAME);
        }
        catch (UnsupportedEncodingException e)
        {
            CfLog.e(e.toString());
        }
        byte[] md5Bytes = md5.digest(input);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++)
        {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
            {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    /**
     * 余额查询
     **/
    public static String ztsa54q67()
    {
        String TDATA = "<TRANSCODE>OGW00041</TRANSCODE><MERCHANTID>HT</MERCHANTID><APPID>PC</APPID><TRSTYPE>0" +
                "</TRSTYPE><ACNO>6245648316545554</ACNO><MOBILE_NO>15920047376</MOBILE_NO><EXT_FILED1></EXT_FILED1" +
                "><EXT_FILED2></EXT_FILED2><EXT_FILED3></EXT_FILED3>";
        String XMLPARA = des3EncryptMode(TDATA);
        return XMLPARA;
    }

    public static void main(String args[])
    {
        //        String encrypt = ztsa54q67();
        //        encrypt = "aaa";
        //        encrypt = des3EncryptMode(encrypt);
        //        CfLog.d(encrypt);
        //        encrypt = des3DecryptMode(encrypt);
        //        CfLog.d(encrypt);

        // System.out.println("加密后的内容：     " + encrypt);
        // System.out.println("加密后的内容：     " + encrypt);
        // System.out.println("解密后的内容：     " + DES3DecryptMode(encrypt));
    }
}
