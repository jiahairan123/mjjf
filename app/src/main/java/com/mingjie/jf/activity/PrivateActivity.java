package com.mingjie.jf.activity;

import android.net.http.SslError;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 隐私政策
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-29 15:31
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class PrivateActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_Left_public)      //返回
            ImageView ivLeftBack;
    @BindView(R.id.tv_content_public)   //中间内容
            TextView tvTitlePublic;
    @BindView(R.id.wv_private)
    WebView webview;
    @BindView(R.id.ll_error_page)
    LinearLayout ll_error_page;         // 错误界面
    @BindView(R.id.avloadingIndicatorView)
    ImageView avloadingIndicatorView;     // 错误图片
    @BindView(R.id.web_pb)
    ProgressBar web_pb;                //加载进度条
    String url1;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_private_policy);
        ButterKnife.bind(this);
        tvTitlePublic.setText(getIntent().getStringExtra("bannerTitle"));
        ivLeftBack.setOnClickListener(this);
        avloadingIndicatorView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            webview.setVisibility(View.GONE);
            ll_error_page.setVisibility(View.VISIBLE);
            return;
        }
        webview.setVisibility(View.VISIBLE);
        ll_error_page.setVisibility(View.GONE);
        CfLog.d(getIntent().getStringExtra("data"));
        syncCookie(getIntent().getStringExtra("data"));

        WebSettings webSettings = webview.getSettings();
        webSettings.setUseWideViewPort(true);
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setBlockNetworkImage(false);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放+
        webSettings.setBuiltInZoomControls(true);
        webview.setInitialScale(25); // 设置网页的初始比例
        //设置Web视图
        webview.setWebViewClient(new webViewClient());

        webview.getSettings().setDomStorageEnabled(true);

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                web_pb.setProgress(newProgress);
                if (newProgress == 100) {
                    web_pb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        // 优先使用缓存
        //webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //加载需要显示的网页
        //http://192.168.1.59:8080/wx/wx/register/toRegister
        webview.loadUrl(getIntent().getStringExtra("data"));
    }

    private void syncCookie(String url) {
        // CfLog.i("---", "syncCookie = " + url);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, HttpManager.getInstance().cookie);
        CfLog.d("HttpManager.getInstance().cookie = " + HttpManager.getInstance().cookie);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.createInstance(this.getApplicationContext());
            CookieSyncManager.getInstance().sync();
        }
    }

    //Web视图

    /**
     * 在这里修改 第二段url
     */
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("/wx/account/toAccount"))   // 防止H5界面跳转WX账户界面
            {
                finish();
            }

//             else if (url.contains("false")) {
//
//                url1 = url.substring(url.indexOf("url:") + 4, url.length());
//                view.loadUrl(url1);
//                return true;
//            }

                return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();  // 接受所有网站的证书
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //            webview.setVisibility(View.GONE);
            //            ll_error_page.setVisibility(View.VISIBLE);
            CfLog.i("执行 onReceivedError()");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.avloadingIndicatorView:
                initData();
                break;
            default:
                break;
        }
    }
}
