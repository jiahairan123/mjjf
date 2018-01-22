package com.mingjie.jf.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.BankTransCode;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * 跳转银行页面
 */
public class ToBankActivity extends BaseActivity
{
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;
    private final static int FILE_CAMERA_RESULT_CODE = 1100;
    private final static int REQ_CHOOSE = 1101;

    @BindView(R.id.webView)
    WebView mWebView;

    String transUrl; // 银行URL
    String transCode; // 交易码
    String requestData; // 交易数据
    String channelFlow;//流水号
    String receiptTransferId;

    String id; // 标的id

    int submitCount = 0; // 提交次数

    private String serverName;

    private Map<String, String> params;

    private String borrowId;//借款id，“借款人单笔还款”需要用到。

    private ValueCallback<Uri> uploadMessage;

    private ValueCallback<Uri[]> uploadMessageAboveL;



    String mCurrentPhotoPath = null;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_to_bank);
        mTitleView.setOnClickLitenerLeft(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                result2Server();
            }
        });
    }

    @Override
    protected void initData()
    {
        transUrl = getIntent().getStringExtra("transUrl");
        transCode = getIntent().getStringExtra("transCode");
        requestData = getIntent().getStringExtra("requestData");
        channelFlow = getIntent().getStringExtra("channelFlow");
        receiptTransferId = getIntent().getStringExtra("receiptTransferId");

        id = getIntent().getStringExtra("id");

        borrowId = getIntent().getStringExtra("borrowId");

        mWebView.loadUrl("file:///android_asset/to_bank.html");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // mWebView.loadData("", "text/html", "UTF-8");

        //webview上传文件
        mWebView.setWebChromeClient(new WebChromeClient()
        {
            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback)
            {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType)
            {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //For Android >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture)
            {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams)
            {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }
        });

        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
            {
                // webView默认是不处理https请求的，页面显示空白，需要进行如下设置
                handler.proceed(); //表示等待证书响应
                // handler.cancel(); //表示挂起连接，为默认方式
                // handler.handleMessage(null); //可做其他处理
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if (!checkReturnUrl(url))
                {
                    //打开网页时不调用系统浏览器， 而是在本WebView中显示
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                // super.onPageFinished(view, url);
                // 载入页面完成的事件
                // 通过java代码调用javascript
                CfLog.d("onPageFinished=" + url + "%%submitCount=" + submitCount);
                if ("file:///android_asset/to_bank.html".equals(url))
                {
                    if (1 == ++submitCount)
                    {
                        //                    mWebView.addJavascriptInterface(new JSInterface(), "onSubmit");
                        String params = "'" + transUrl + "','" + transCode + "','" + requestData + "'";
                        mWebView.loadUrl("javascript:onSubmit(" + params + ")");
                    }
                    else
                    {
                        finish();
                    }
                }

            }
        });

    }

    private void openImageChooserActivity()
    {
        //    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        //    i.addCategory(Intent.CATEGORY_OPENABLE);
        //    i.setType("image/*");
        //    startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);

        takePhoto(); // 这里只打开照相机 2017-04-29
    }

    private void takePhoto()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            Uri imageUri = null;
            try
            {
                imageUri = Uri.fromFile(createImageFile());
            }
            catch (IOException e)
            {
                CfLog.i(e.toString());
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, FILE_CAMERA_RESULT_CODE);
        }
    }

    /**
     * 本地相册选择图片
     */
    private void chosePic() {
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        String IMAGE_UNSPECIFIED = "image/*";
        innerIntent.setType(IMAGE_UNSPECIFIED); // 查看类型
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQ_CHOOSE);
    }

    private File createImageFile() throws IOException
    {
        File cacheDir = getExternalCacheDir();
        if (!cacheDir.exists())
        {
            cacheDir.mkdirs();
        }

        // /sdcard/Android/data/com.***.cgkx/cache/***.png
        mCurrentPhotoPath = cacheDir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".png";
        CfLog.d(mCurrentPhotoPath);
        return new File(mCurrentPhotoPath);
    }

    /**
     * 检查返回的url
     *
     * @param url
     * @return 返回是否匹配到returnUrl
     */
    private boolean checkReturnUrl(String url)
    {
        CfLog.d("拦截到url=" + url);
        //拦截到银行的returnUrl，执行对应的Action 通知后台
        if (Constants.BANK_RETURN_URL.equals(url) || (Constants.BANK_RETURN_URL + "/").equals(url))
        {
            CfLog.d("拦截到");
            result2Server();
            return true;
        }
        return false;
    }

    /**
     * 通知后台银行操作完成
     */
    private void result2Server()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            finish();
            return;
        }
        //获取请求参数,如果不需要回调，则直接关闭当前页面
        if (!initParams())
        {
            finish();
            return;
        }
        UIUtils.showLoading(this);
        HttpManager.getInstance().doPost(serverName, params, new HttpCallBack<ServerResponse<Object>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                //该请求不关心返回结果，只触发后台
                UIUtils.dimissLoading();
                finish();
            }

            @Override
            public void getHttpCallBack(ServerResponse<Object> rsp)
            {
                //该请求不关心返回结果，只触发后台
                UIUtils.dimissLoading();
                postEvent();
                //    if (rsp != null && "000000".equals(rsp.getCode()))
                //    {
                //        postEvent();
                //    }
                //    else
                //    {
                //        if (serverName.equals(ServiceName.QUERY_RECEIPT_TRANSFER_RESULT))
                //        {
                //            CfLog.i("债权转让 失败回调");
                //            // 提交债权转让信息不管成功与否都触发时间
                // (防止回调失败 ，未关闭提交转让界面 用户继续提交导致服务器内部错误)
                //            Event event = new Event();
                //            event.eventType = Event.TRANSFER;
                //            EventBus.getDefault().post(event);
                //        }
                //    }
                finish();
            }
        });
    }

    /**
     * 银行操作回调后,发出对应的event，让对应页面刷新
     */
    private void postEvent()
    {
        Event event = new Event();
        if (BankTransCode.OPEN_ACCOUNT.equals(transCode))//开通存管
        {
            event.eventType = Event.OPEN_ACCOUNT;
        }
        if (BankTransCode.RECHARGE.equals(transCode))//充值
        {
            event.eventType = Event.RECHARGE;
        }
        if (BankTransCode.WITHDRAW_DEPOSIT.equals(transCode))//提现
        {
            event.eventType = Event.WITHDRAW;
        }
        if (BankTransCode.INVEST.equals(transCode))//单笔投标
        {
            event.eventType = Event.INVEST;
        }
        if (BankTransCode.REPAYMENT.equals(transCode))//借款人单笔还款
        {
            event.eventType = Event.REPAYMENT;
        }
        if (BankTransCode.ASSIGNMENT.equals(transCode)) // 债权转让
        {
            event.eventType = Event.TRANSFER;
        }
        EventBus.getDefault().post(event);
    }

    /**
     * 根据带到该页面的transCode来处理请求参数；
     *
     * @return 是否需要回调
     */
    private boolean initParams()
    {
        params = new HashMap<>();
        if (BankTransCode.OPEN_ACCOUNT.equals(transCode))//开通存管
        {
            serverName = ServiceName.CGKX_QUERYCREATEACCRESULT;
            params.put("id", CacheUtils.getUserinfo(this).getId());
            return true;
        }
        else if (BankTransCode.BIND_CARD.equals(transCode))//绑卡
        {
            //绑卡操作不需要回调
        }
        else if (BankTransCode.RECHARGE.equals(transCode))//充值
        {
            serverName = ServiceName.CGKX_CHECKRECHARGE;
            params.put("flowNo", channelFlow);
            return true;
        }
        else if (BankTransCode.WITHDRAW_DEPOSIT.equals(transCode))//提现
        {
            serverName = ServiceName.CGKX_CHECKWITHDRAW;
            params.put("flowNo", channelFlow);
            return true;
        }
        else if (BankTransCode.INVEST.equals(transCode))//单笔投标
        {
            serverName = ServiceName.SCATTERED_SEARCHBIDRESULT;
            params.put("channelFlow", channelFlow);
            return true;
        }
        else if (BankTransCode.REPAYMENT.equals(transCode))//借款人单笔还款
        {
            serverName = ServiceName.REPAYMENT_SUBMITRESULT;
            params.put("id", borrowId);
            return true;
        }
        else if (BankTransCode.ASSIGNMENT.equals(transCode)) //债权转让
        {
            serverName = ServiceName.QUERY_RECEIPT_TRANSFER_RESULT;
            params.put("receiptTransferId", receiptTransferId);
            return true;
        }
        else
        {
            return false;
        }
        return false;
    }

    //    private final class JSInterface
    //    {
    //        JSInterface()
    //        {
    //            onSubmit();
    //        }
    //
    //        @JavascriptInterface
    //        public void onSubmit()
    //        {
    //            mHandler.post(new Runnable()
    //            {
    //                @Override
    //                public void run()
    //                {
    //                    CfLog.d("------to bank---------------");
    //                    // requestData = URLEncoder.encode(requestData, "UTF-8"); //不用转义
    //                    // 这里用单引号
    //                    String params = "'" + transUrl + "','" + transCode + "','" + requestData + "'";
    //                    mWebView.loadUrl("javascript:onSubmit(" + params + ")");
    //                }
    //            });
    //
    //        }
    //
    //    }

    @Override
    protected void onDestroy()
    {
        if (mWebView != null)
        {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null)
            {
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            result2Server();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        CfLog.d(requestCode + ", " + resultCode);

        if (requestCode == FILE_CHOOSER_RESULT_CODE || requestCode == FILE_CAMERA_RESULT_CODE)
        {

            if (null == uploadMessage && null == uploadMessageAboveL)
            {
                return;
            }
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();

            if (uploadMessageAboveL != null)
            {

                onActivityResultAboveL(requestCode, resultCode, data);
            }
            else if (uploadMessage != null)
            {

                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent)
    {
        CfLog.d(requestCode + ", " + resultCode);
        // if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
        if (uploadMessageAboveL == null)
        {
            CfLog.i("STOP--------------");
            return;
        }

        Uri[] results = null;

        if (resultCode == Activity.RESULT_OK)
        {

            if (intent != null)
            {

                if (requestCode == FILE_CHOOSER_RESULT_CODE)
                {
                    String dataString = intent.getDataString();
                    ClipData clipData = intent.getClipData();

                    if (clipData != null)
                    {

                        results = new Uri[clipData.getItemCount()];
                        for (int i = 0; i < clipData.getItemCount(); i++)
                        {
                            ClipData.Item item = clipData.getItemAt(i);
                            results[i] = item.getUri();
                        }
                    }
                    if (dataString != null){

                        results = new Uri[]{Uri.parse(dataString)};
                    }

                }

            }
            else if (requestCode == FILE_CAMERA_RESULT_CODE)
            {

                String desPath = getExternalCacheDir().getAbsolutePath() + "/IMG_" + System.currentTimeMillis() + ".jpg";
                CfLog.i(desPath);
                compressPicture(mCurrentPhotoPath, desPath);

                new File(mCurrentPhotoPath).delete();

                File file = new File(desPath);
                Uri localUri = Uri.fromFile(file);
                Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                sendBroadcast(localIntent);
                results = new Uri[]{Uri.fromFile(file)};

            }

        }

        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;

    }

    private void compressPicture(String srcPath, String desPath)
    {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 480f;// 这里设置高度为 480f
        float ww = 480f;// 这里设置宽度为 480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
        {
            be = 1;
        }
        newOpts.inSampleSize = be;// 设置缩放比例
        CfLog.i("src: w: " + w + ", h: " + h + ", inSampleSize: " + be);
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        try
        {
            FileOutputStream fos = new FileOutputStream(desPath);
            if (bitmap != null)
            {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
        }
        catch (FileNotFoundException e)
        {
            CfLog.e(e);
        }
    }

}
