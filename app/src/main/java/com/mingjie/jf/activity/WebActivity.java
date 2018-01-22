package com.mingjie.jf.activity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mingjie.jf.R;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.DialogFactory;


/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述：web
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class WebActivity extends BaseActivity {
    private WebView webView;
    boolean result = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        Intent intent = getIntent();
        final String data = Utilities.WebUrl + intent.getStringExtra("data");
        // String title = intent.getStringExtra("");
        webView = (WebView) this.findViewById(R.id.webViewurl);
        webView.loadUrl(data);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(data);
                return true;
                // return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
            {
                handler.proceed();  // 接受所有网站的证书
            }

        });
        // 支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 页面加载完成
                    DialogFactory.mDialog.dismiss();
                } else {
                    // 加载中
                    if (result) {
                        DialogFactory.showRequestDialog(WebActivity.this, null);
                        result = false;
                    }
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        // 优先使用缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}







