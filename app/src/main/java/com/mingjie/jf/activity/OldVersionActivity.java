package com.mingjie.jf.activity;

import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiahairan on 2017/6/12 09.
 */

public class OldVersionActivity extends BaseActivity {
    //跳转到微信端页面
    @BindView(R.id.wx_webView)
    WebView webview;

    @BindView(R.id.oldversion_web_pb)
    ProgressBar progressBar;

    @BindView(R.id.iv_Left_public)
    ImageView returnIv;

//    @BindView(R.id.tv_content_public)
//    TextView tvContentPublic;

    @BindView(R.id.return_to_new_tv)
    TextView tvReturnNew;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_old_version);
        ButterKnife.bind(this);

//        tvContentPublic.setText("铭捷金服");
        tvReturnNew.setVisibility(View.VISIBLE);
        tvReturnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        returnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            return;
        }

        WebSettings webSettings = webview.getSettings();
        webSettings.setUseWideViewPort(true);
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webview.setInitialScale(25); // 设置网页的初始比例
        //设置Web视图
        webview.setWebViewClient(new webViewClient());

        webview.getSettings().setDomStorageEnabled(true);

        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
//
//                if (newProgress == 100){
//                    progressBar.setVisibility(View.GONE);
//                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webview.loadUrl("http://www.mingjiecf.cn/wapp");

    }


    private class webViewClient extends WebViewClient
    {

        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if (url.contains("/wx/account/toAccount"))   // 防止H5界面跳转WX账户界面
            {
                finish();
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
        {
            handler.proceed();  // 接受所有网站的证书
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
        {
            super.onReceivedError(view, request, error);
            //            webview.setVisibility(View.GONE);
            //            ll_error_page.setVisibility(View.VISIBLE);
            CfLog.i("执行 onReceivedError()");
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

}
