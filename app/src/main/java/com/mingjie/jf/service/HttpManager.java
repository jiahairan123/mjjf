package com.mingjie.jf.service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.mingjie.jf.activity.LoginActivity;
import com.mingjie.jf.activity.MainActivity;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.NetWorkBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserVo;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.DES3EncryptAndDecrypt;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import de.greenrobot.event.EventBus;

/**
 * Created by ZhangJin on 2016/7/20.
 */
public class HttpManager
{
    String JSESSIONID = "";
    public String cookie = "";
    public String userCookie = "";
    private String tmpCookie = ""; // 上一次的cookie,比较两个cookie是否相同时用
    private long timeDeviation = 0L; // 手机时间和服务器时间偏差
    private long lastSyncTime = 0L; // 上一次对比手机和服务器的时间
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * 登录超时时的任务栈
     */

    private Stack<NetWorkBean> mStack = new Stack<>();

    /**
     * 出现超时时是否发起登录
     */
    private boolean isAutoLogin = false;

    private HttpManager()
    {
        client.setConnectTimeout(8, TimeUnit.SECONDS);
        client.setWriteTimeout(8, TimeUnit.SECONDS);
        client.setReadTimeout(8, TimeUnit.SECONDS);
        overlockCard();
    }

    private volatile static HttpManager single = null;

    public static HttpManager getInstance()
    {
        if (single == null)
        {
            synchronized (HttpManager.class)
            {
                if (single == null)
                {
                    single = new HttpManager();
                }
            }
        }
        return single;
    }

    public OkHttpClient getClient()
    {
        return client;
    }

    /**
     * 忽略所有https证书
     */
    private void overlockCard()
    {
        X509TrustManager xtm = new X509TrustManager()
        {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
            {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
            {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers()
            {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };

        SSLContext sslContext = null;
        try
        {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
        }
        catch (NoSuchAlgorithmException e)
        {
            CfLog.e(e.toString());
        }
        catch (KeyManagementException e)
        {
            CfLog.e(e.toString());
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier()
        {
            @Override
            public boolean verify(String hostname, SSLSession session)
            {
                return true;
            }
        };
        client.setSslSocketFactory(sslContext.getSocketFactory());
        client.setHostnameVerifier(DO_NOT_VERIFY);
    }

    private Request buildPostRequest(String serviceName, Object obj)
    {
        CfLog.d("buildPostRequest-cookie:" + cookie);
        CfLog.d("URL:" + UrlUtil.getInterfacesUrl());
        CfLog.d("serviceName:" + serviceName);

        FormEncodingBuilder builder = new FormEncodingBuilder();
        // builder.add("serviceName", serviceName);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("serviceName", serviceName);
        map.put("params", obj);

        map.put("time", System.currentTimeMillis() + timeDeviation); // 保证每次加密时，密文都不一样
        // map.put("productName", "CGKX");
        map.put("from", "android");
        map.put("version", UIUtils.getAppVersionName()); // 1.x.x
        map.put("versionCode", UIUtils.getAppVersionNum());
        // 20170303 增加了会话重放安全验证
        map.put("appVersionDate", "20170303");

        String str = new Gson().toJson(map);
        CfLog.d("param:" + str);

        String md5 = DES3EncryptAndDecrypt.toMD5(str);
        //CfLog.d("param md5:" + md5);
        str = DES3EncryptAndDecrypt.des3EncryptMode(str);
        //CfLog.d("param en:" + str);

        builder.add("param", str);
        builder.add("md5", md5);
        RequestBody requestBody = builder.build();
        // RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request.Builder rBuild = new Request.Builder();
        rBuild.url(UrlUtil.getInterfacesUrl());
        if (null != cookie)
        {
            rBuild.addHeader("Cookie", cookie);

            CfLog.d("------------------------------------- cookie: "+ cookie + "-------------------------------------");
        }



        rBuild.addHeader("serviceName", serviceName);
        //        .addHeader("version", "1.0.0")
        //        .addHeader("productName", "cunGuan");

        rBuild.post(requestBody);
        //        Request rst = new Request.Builder().url(URL)
        //                .header("User-Agent", "OkHttp Headers.java")
        ////                .addHeader("Accept", "application/json; q=0.5")
        ////            .addHeader("Accept", "application/vnd.github.v3+json")
        //                .addHeader("token", token)
        //                .addHeader("serviceName", serviceName)
        //                .addHeader("version", "1.0.0")
        //                .addHeader("productName", "cunGuan")
        //                .post(requestBody)
        //                .build();

        Request rst = rBuild.build();

        // return new Request.Builder().url(url).post(requestBody).build();
        return rst;
    }

    /**
     * 显示图形验证码
     *
     * @param ivw
     * @param svcName imageVcode
     * @param obj
     */
    public void displayImageCode(final ImageView ivw, final String svcName, final Object obj)
    {
        displayImageCode(ivw, svcName, obj, 0);
    }

    public void displayImageCode(final ImageView view, final String svcName, final Object obj, final int errorResId)
    {

        final Handler mHandler = new Handler(new Handler.Callback()
        {
            @Override
            public boolean handleMessage(Message msg)
            {
                //                DialogFactory.mDialog.dismiss();
                CfLog.d("");
                switch (msg.what)
                {
                    case 0:// 数据为空
                        //                        DialogFactory.mDialog.dismiss();
                        if (errorResId > 0)
                        {
                            view.setImageResource(errorResId);
                        }
                        break;
                    case 1:// 请求成功
                        //                        DialogFactory.mDialog.dismiss();
                        HashMap<String, Object> map = (HashMap<String, Object>) msg.obj;
                        // view.setTag(map.get("imgKey"));
                        view.setImageBitmap((Bitmap) map.get("bmp"));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        //        final Request request = buildPostRequest(svcName, obj);
        Request.Builder rBuild = new Request.Builder();
        rBuild.url(UrlUtil.getImgUrl());
        if (!TextUtils.isEmpty(cookie))
        {
            rBuild.addHeader("Cookie", cookie);
        }
        final Request request = rBuild.build();
        //        final Request request = new Request.Builder().url(UrlUtil.getImgUrl()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                CfLog.e(e.toString());
                mHandler.sendMessage(mHandler.obtainMessage(0));
            }

            @Override
            public void onResponse(Response response)
            {
                setCookie(response);
                setTimeDeviation(response);
                //                CfLog.i("setCookie = " + setCookie);
                //                if (setCookie != null)
                //                {
                //                    cookie = setCookie.split(";")[0];
                //                }

                CfLog.d("cookie:" + cookie);
                InputStream inputStream = null;
                try
                {
                    if (response.isSuccessful())
                    {
                        inputStream = response.body().byteStream();
                        BufferedInputStream in = new BufferedInputStream(inputStream);
                        Bitmap bmp = BitmapFactory.decodeStream(in);

                        if (null != bmp && null != view)
                        {
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            // map.put("imgKey", imgKey);
                            map.put("bmp", bmp);
                            mHandler.sendMessage(mHandler.obtainMessage(1, map));
                        }

                    }

                }
                catch (Exception e)
                {
                    CfLog.e(e.toString());
                    mHandler.sendMessage(mHandler.obtainMessage(0));
                }
                finally
                {
                    if (inputStream != null)
                    {
                        try
                        {
                            inputStream.close();
                            response.body().close();
                        }
                        catch (IOException e)
                        {
                            CfLog.e(e.toString());
                        }
                    }
                }
            }
        });
    }

    public Object doPost(String svcName, Object obj, Class clazz) throws Exception
    {
        Request request = buildPostRequest(svcName, obj);
        //        try
        //        {
        Response response = client.newCall(request).execute();
        setCookie(response);
        for (int i = 0; i < response.headers().size(); i++)
        {
            // CfLog.i("（" + response.headers().name(i) + "," + response.headers().value(i) + ")");
        }

        CfLog.d("doPost-cookie=" + cookie);
        if (response.isSuccessful())
        {
            String string = response.body().string();

            Gson gson = new Gson();
            ServerResponse resp = gson.fromJson(string, ServerResponse.class);
            String data = (String) resp.getData();
            if (!TextUtils.isEmpty(data))
            {
                String str = DES3EncryptAndDecrypt.des3DecryptMode(data);
                string = string.replace("\"" + data + "\"", str);
                // CfLog.d("string=" + string);
            }
            CfLog.d(string);
            return gson.fromJson(string, clazz);
        }
        CfLog.e("数据为空:" + response.code());
        //        }
        //        catch (Exception e)
        //        {
        //            CfLog.e("访问错误:" + e.getMessage());
        //            return "error";
        //        }
        return null;
    }

    public void doPost(String svcName, final HttpCallBack mCallBack)
    {
        doPost(svcName, null, mCallBack);
    }

    public void doPost(final String svcName, Object params, final HttpCallBack mCallBack)
    {
        final Handler handler = new Handler(new Handler.Callback()
        {
            @Override
            public boolean handleMessage(Message msg)
            {
                //    DialogFactory.mDialog.dismiss();
                switch (msg.what)
                {
                    case 0:// 数据为空
                        //                        DialogFactory.mDialog.dismiss();
                        CfLog.i("request fail");
                        mCallBack.getHttpCallNull(msg.obj.toString());
                        break;
                    case 1:// 请求成功
                        //                        DialogFactory.mDialog.dismiss();
                        CfLog.i("request success");
                        if (ServiceName.APP_USER_LOGOUT.equals(svcName))
                        {
                            CfLog.i("doPost--logout");
                            cookie = "";
                        }
                        mCallBack.getHttpCallBack(msg.obj);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        Request request = buildPostRequest(svcName, params);
        //        mStack.add(request);//请求入栈
        //这里将请求参数带到结果解析是为了处理登录超时时需要这些信息重新打造request
        deliveryResult(svcName, params, request, mCallBack, handler);
    }

    private void deliveryResult(final String svcName, final Object params, final Request request,
                                final HttpCallBack mCallBack, final Handler handler)
    {

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(final Request request, final IOException e)
            {
                CfLog.e(e.toString());
                handler.sendMessage(handler.obtainMessage(0, "---onFailure---"));

            }

            @Override
            public void onResponse(final Response response)
            {
                //for (int i = 0; i < response.headers().size(); i++)
                //{
                //    CfLog.d("rsp.head[" + i + "] " + response.headers().name(i) + "=" + response.headers().value(i));
                //}
                setCookie(response);
                CfLog.d("---------------------------------response:" + response + "=" + "---------------------------------------");
                setTimeDeviation(response);
                CfLog.d("serviceName:" + request.header("serviceName"));
                CfLog.d("cookie = " + cookie);
                String string = null;
                try
                {
                    string = response.body().string();
                    // CfLog.d(string);
                    Gson gson = new Gson();
                    ServerResponse rsp = gson.fromJson(string, ServerResponse.class);
                    if (rsp == null)
                    {
                        handler.sendMessage(handler.obtainMessage(0, "response == null"));
                        return;
                    }
                    //登录超时
                    if ("100030".equals(rsp.getCode()))
                    {
                        CfLog.d(string);
                        CfLog.i(" ****** login timeout ****** " + svcName);
                        cookie = "";
                        // timeDeviation = 0L;
                        doLoginTimeOut2(string, mCallBack, handler);

                        // doLoginTimeOut(svcName, params, string, mCallBack, handler);
                    }
                    else//登录未超时
                    {
                        if (mCallBack.mType == String.class)
                        {
                            CfLog.d(string);
                            handler.sendMessage(handler.obtainMessage(1, string));
                        }
                        else
                        {
                            ServerResponse resp = gson.fromJson(string, ServerResponse.class);
                            String data = null == resp.getData() ? null : (String) resp.getData();
                            if (!TextUtils.isEmpty(data))
                            {
                                String str = DES3EncryptAndDecrypt.des3DecryptMode(data);
                                string = string.replace("\"" + data + "\"", str);
                            }
                            CfLog.d(string);
                            handler.sendMessage(handler.obtainMessage(1, gson.fromJson(string, mCallBack.mType)));
                        }
                    }

                }
                catch (IOException e)
                {
                    CfLog.e(e.toString());
                    handler.sendMessage(handler.obtainMessage(0, "---IOException---"));
                }
                catch (com.google.gson.JsonParseException e)
                {
                    // Json解析的错误
                    CfLog.e(e, string);
                    handler.sendMessage(handler.obtainMessage(0, "---JsonParseException:" + string));
                }

            }
        });

    }


    /**
     * 登录超时处理，直接跳登录页面，适合单点登录
     *
     * @param json      json
     * @param mCallBack HttpCallBack
     * @param handler   Handler
     */
    private void doLoginTimeOut2(String json, HttpCallBack mCallBack, Handler handler)
    {
        CacheUtils.clearUserInfo();
        // Intent intent = new Intent(MainActivity.context, LoginActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // intent.putExtra(LoginActivity.KEY_START_TYPE, LoginActivity.START_TYPE_FINISH);
        // MainActivity.context.startActivity(intent);
        // 自动登录失败，通知其他页面刷新页面（刷新成未登录），清除用户缓存信息
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                Event event = new Event();
                event.eventType = Event.LOGOUT;
                EventBus.getDefault().post(event);
            }
        });

        Object obj = new Gson().fromJson(json, mCallBack.mType);
        handler.sendMessage(handler.obtainMessage(1, obj));
    }

    /**
     * 登录超时处理，先自动登录，失败再跳登录页面
     */
    private void doLoginTimeOut(String svcName, Object params, String json, HttpCallBack mCallBack, Handler handler)
    {
        //将超时的请求加入栈中
        mStack.add(new NetWorkBean(svcName, params, mCallBack, handler));
        //有超时请求，且未发起自动登录
        if (!isAutoLogin)
        {
            isAutoLogin = true;
            Map<String, String> param = new HashMap<>(2);
            param.put("name", CacheUtils.getString(Constants.USER_NAME, ""));
            // param.put("password", CacheUtils.getString(Constants.PASSWORD, "")); // 新客户可用这行因为缓存加密了两次
            // 2017-03-29 (针对老版本)密码 先解密再加密，是因为从缓存里面取出来的，如果是以前登录过的老用户则是明文，
            // 如果是新注册用户（或者老用户注销重新登录）则是密文的。
            // 新/老版本 统一解密、加密一次，确保传到后台的是密文，后台记录的访问日志也是密文
            String pwd = CacheUtils.getString(Constants.PASSWORD, "");
            pwd = DES3EncryptAndDecrypt.des3EncryptMode(DES3EncryptAndDecrypt.des3DecryptMode(pwd));
            param.put("password", pwd);
            try
            {
                UserVo user = (UserVo) HttpManager.getInstance().doPost(ServiceName.APP_USER_LOGIN,
                        param, UserVo.class);
                if (user == null)
                {
                    // 自动登录，返回出错
                    handler.sendMessage(handler.obtainMessage(0, "user == null"));
                    mStack.clear();
                    isAutoLogin = false;
                }
                else
                {
                    if ("000000".equals(user.getCode()))
                    {
                        //循环执行超时栈内的请求
                        for (int i = 0; i < mStack.size(); i++)
                        {
                            NetWorkBean nwBean = mStack.pop();
                            Request rqt = buildPostRequest(nwBean.getSvcName(), nwBean.getParams());
                            deliveryResult(nwBean.getSvcName(), nwBean.getParams(), rqt, nwBean
                                    .getCallBack(), nwBean.getHandler());
                        }
                        //一次超时流程完成，状态复位
                        isAutoLogin = false;
                    }
                    else if ("100003".equals(user.getCode()) || "100002".equals(user.getCode()))
                    // 登录信息错误，这里调起登录页面
                    {
                        CfLog.d("----100003-----");
                        Intent intent = new Intent(MainActivity.context, LoginActivity.class);
                        intent.putExtra(LoginActivity.KEY_START_TYPE, LoginActivity.START_TYPE_FINISH);
                        MainActivity.context.startActivity(intent);
                        //自动登录失败，通知其他页面刷新页面（刷新成未登录），清除用户缓存信息
                        handler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Event event = new Event();
                                event.eventType = Event.LOGOUT;
                                EventBus.getDefault().post(event);
                            }
                        });
                        CacheUtils.clearUserInfo();
                        Object obj = new Gson().fromJson(json, mCallBack.mType);
                        handler.sendMessage(handler.obtainMessage(1, obj));
                        mStack.clear();
                        //这里存在并发问题，handler发送msg存在延迟，这里直接将状态改为false，可能导致并
                        // 发时多次调起登录页面，现解决方案：前台规避
                        isAutoLogin = false;
                    }
                    else
                    {
                        handler.sendMessage(handler.obtainMessage(0, "AUTO LOGIN ERROR"));
                        mStack.clear();
                        isAutoLogin = false;
                    }
                }
            }
            catch (Exception e)
            {
                CfLog.e(e);
                // 自动登录，返回出错
                handler.sendMessage(handler.obtainMessage(0, "user == null"));
                mStack.clear();
                isAutoLogin = false;
            }
        }
    }

    /**
     * 设置手机和服务器端的时间差（服务器-手机）
     *
     * @param rsp Response
     */
    private void setTimeDeviation(Response rsp)
    {
        // 如果 3分钟内设置过时间差，就不用再设置了
        if (System.currentTimeMillis() - lastSyncTime < 3 * 60000)
        {
            return;
        }

        // Fri, 03 Mar 2017 04:32:16 GMT
        String stringDate = rsp.header("Date", "0");
        String sentMillis = rsp.header("OkHttp-Sent-Millis", "0");
        String receivedMillis = rsp.header("OkHttp-Received-Millis", "0");
        try
        {
            long date = TimeFormatUtil.gmt2utc(stringDate);
            long s = Long.parseLong(sentMillis);
            long r = Long.parseLong(receivedMillis);
            timeDeviation = date - s - (r - s) / 2;
            lastSyncTime = System.currentTimeMillis();
            CfLog.i("timeDeviation:" + timeDeviation + ", lastSyncTime:" + lastSyncTime);
        }
        catch (Exception e)
        {
            CfLog.i(e.toString());
        }
    }

    /**
     * 通过rsp构造cookie、
     *
     * @param response
     */
    private void setCookie(Response response)
    {
        tmpCookie = cookie;
        String setCookie = response.header("Set-Cookie");
        //if (setCookie != null && !cookie.contains("JSESSIONID="))
        if (setCookie != null)
        {
            cookie = "";
            for (String sCookie : response.headers("Set-Cookie"))
            {
                if (!TextUtils.isEmpty(sCookie) && sCookie.contains("JSESSIONID="))
                {
                    cookie += "," + sCookie;
                }
            }
        }

        // 如果返回的 headers 里面的cookie异常,则cookie使用上一个请求的
        if ("".equals(cookie))
        {
            cookie = tmpCookie;
        }

        if (!tmpCookie.equals(cookie))
        {
            CfLog.i("********* Cookie is change *********");
        }

        CfLog.i("********* userCookie " + userCookie + "*********");


        if (userCookie.length() == 0 || "".equals(userCookie)){
            return;
        }

        cookie =  "JSESSIONID=" + userCookie;

        CfLog.i("c--------------- cookie:" + cookie + "---------------");

    }


    /**
     * @param url               下载链接
     * @param savePath          下载文件保存路径
     * @param saveFileName      保存文件名
     * @param onDowloadListener 下载回调监听
     */
    public void download(String url, final String savePath, final String saveFileName,
                         final OnDowloadListener onDowloadListener)
    {
        final Handler mHandler = new Handler();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, final IOException e)
            {
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        onDowloadListener.onDownloadFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException
            {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;//单次下载字节数
                FileOutputStream fos = null;
                try
                {
                    final long tatolSize = response.body().contentLength();//文件总长度
                    long currentSize = 0;//当前下载文件长度
                    is = response.body().byteStream();
                    File file = new File(savePath, saveFileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1)
                    {
                        fos.write(buf, 0, len);
                        currentSize += len;
                        final long tmpCurrSize = currentSize;//临时记录下载长度
                        mHandler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                onDowloadListener.onDownloadProgress((int) (tmpCurrSize * 100 / tatolSize));
                            }
                        });

                    }
                    fos.flush();

                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // 下载文件成
                            onDowloadListener.onDownloadSucc();
                        }
                    });
                }
                catch (final IOException e)
                {
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            onDowloadListener.onDownloadFail(e);
                        }
                    });
                }
                finally
                {
                    try
                    {
                        if (is != null)
                            is.close();
                    }
                    catch (final IOException e)
                    {
                        mHandler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                onDowloadListener.onDownloadFail(e);
                            }
                        });
                    }
                    try
                    {
                        if (fos != null)
                            fos.close();
                    }
                    catch (final IOException e)
                    {
                        mHandler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                onDowloadListener.onDownloadFail(e);
                            }
                        });
                    }
                }
            }
        });
    }

    public interface OnDowloadListener
    {

        /**
         * 下载成功
         */
        void onDownloadSucc();

        /**
         * @param progress 下载进度(1-100)
         */
        void onDownloadProgress(int progress);

        /**
         * 下载失败
         */
        void onDownloadFail(Exception e);
    }

}
