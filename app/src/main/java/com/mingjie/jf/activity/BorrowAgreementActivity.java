package com.mingjie.jf.activity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
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
 * <p>描   述： 借款协议activity
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-10-17 18:29
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class BorrowAgreementActivity extends BaseActivity implements View.OnClickListener
{
    @BindView(R.id.iv_Left_public)      //返回
            ImageView ivLeftBack;
    @BindView(R.id.tv_content_public)   //中间内容
            TextView tvTitlePublic;
    @BindView(R.id.wv_private)
    WebView mWebDes;
    @BindView(R.id.ll_error_page)
    LinearLayout ll_error_page;         // 错误界面
    @BindView(R.id.avloadingIndicatorView)
    ImageView avloadingIndicatorView;     // 错误图片

    @BindView(R.id.web_pb)
    ProgressBar mWebPb;

    private String url;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_borrow_agreement);
        ButterKnife.bind(this);
        tvTitlePublic.setText(getIntent().getStringExtra("title"));
        ivLeftBack.setOnClickListener(this);
        avloadingIndicatorView.setOnClickListener(this);
    }

    private void syncCookie(String url) {
        Log.i("---","syncCookie = "+url);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, HttpManager.getInstance().cookie);
        CfLog.d("HttpManager.getInstance().cookie = "+HttpManager.getInstance().cookie);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.createInstance(this.getApplicationContext());
            CookieSyncManager.getInstance().sync();
        }
    }

    @Override
    protected void initData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            mWebDes.setVisibility(View.GONE);
            ll_error_page.setVisibility(View.VISIBLE);
            mWebPb.setVisibility(View.GONE);
            return;
        }
        Intent intent = getIntent();
        mWebPb.setVisibility(View.VISIBLE);
        mWebDes.setVisibility(View.VISIBLE);
        ll_error_page.setVisibility(View.GONE);
        url = intent.getStringExtra("productId");

        syncCookie(url);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        mWebDes.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mWebDes.setInitialScale(100);
        mWebDes.getSettings().setJavaScriptEnabled(true);//启用js
        mWebDes.getSettings().setBlockNetworkImage(false);//解决图片不显示
        mWebDes.getSettings().setJavaScriptEnabled(true);
        mWebDes.getSettings().setSupportZoom(true);
        mWebDes.getSettings().setBuiltInZoomControls(true);
        mWebDes.getSettings().setUseWideViewPort(true);
        mWebDes.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebDes.setWebChromeClient(new WebsViewClient());
        mWebDes.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);   //在当前的webview中跳转到新的url
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
            {
                handler.proceed();  // 接受所有网站的证书
            }
        });
        mWebDes.loadUrl(url);
    }

    private class WebsViewClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mWebPb.setProgress(newProgress);
            if(newProgress==100){
                mWebPb.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
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
