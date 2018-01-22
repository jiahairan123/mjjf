package com.mingjie.jf.activity;

import android.content.Intent;
import android.net.http.SslError;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.mingjie.jf.R;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UrlUtil;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiahairan on 2017/7/25 09.
 */

public class IntegralMallActivity extends BaseActivity implements View.OnClickListener {

    String url;
    @BindView(R.id.wv_integral_mall)
    WebView web;
    @BindView(R.id.tv_content_public)
    TextView titleText;
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;//返回

    @Override
    protected void initView() {

        setContentView(R.layout.activity_integral_mall);
        ButterKnife.bind(this);
        ivLeftPublic.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        titleText.setText("积分商城");
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
//        https://mingjiecf.com/mobile/duiba/login
//        String urls = "http://jiahairan123.55555.io:12752/mobile/mobile/duiba/login";

        String urls = UrlUtil.BASE_URL + "/mobile/duiba/login";
        OkHttpUtils.post().url(urls).addParams("uid", uid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    url = object.getString("url");
                    WebSettings ws = web.getSettings();
                    ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    // 设置支持缩放
                    ws.setSupportZoom(true);
                    ws.setUseWideViewPort(true);
                    ws.setJavaScriptEnabled(true);
                    ws.setLoadWithOverviewMode(true);

                    web.loadUrl(url);

                    // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
                    web.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                            view.loadUrl(url);
                            return true;
                        }

                        @Override
                        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                            handler.proceed();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
                web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                web.goBack();
                return true;
            }

            return super.onKeyDown(keyCode, event);
        };

    @Override
    public void onClick(View v) {
        finish();
    }

}
