package com.mingjie.jf.activity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.utils.CfLog;

import butterknife.BindView;

/**
 * 描   述：  网页展示界面
 * <p/>
 * 创 建 人： wuxianai
 * 创建时间： 2015/12/18 17:17
 * 当前版本： V3.0.0
 * 修订历史： (版本、修改时间、修改人、修改内容)
 */
public class WebDesActivity extends BaseActivity implements View.OnClickListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    //    @BindView(R.id.public_title_rootview)
    //    RelativeLayout publicTitleRootview;
    @BindView(R.id.web_pb)
    ProgressBar webPb;
    @BindView(R.id.web_des)
    WebView mWebDes;

    private String url;
    private String mBannerTitle;

    protected void initView()
    {
        setContentView(R.layout.activity_web_des);
        ivLeftPublic.setOnClickListener(this);
    }

    protected void initData()
    {

        url = getIntent().getStringExtra("url");
        CfLog.i("url---" + url);
        mBannerTitle = getIntent().getStringExtra("bannerTitle");

        tvContentPublic.setText(mBannerTitle);
        ivLeftPublic.setOnClickListener(this);
        mWebDes.setInitialScale(100);
        mWebDes.getSettings().setJavaScriptEnabled(true);
        mWebDes.getSettings().setSupportZoom(true);
        mWebDes.getSettings().setBuiltInZoomControls(true);
        mWebDes.getSettings().setUseWideViewPort(true);
        mWebDes.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebDes.setWebChromeClient(new WebsViewClient());
        mWebDes.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);   //在当前的webView中跳转到新的url
                return true;
            }
        });

        mWebDes.loadUrl(url);

    }


    private class WebsViewClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress)
        {
            webPb.setProgress(newProgress);
            if (newProgress == 100)
            {
                webPb.setVisibility(View.GONE);
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
            default:
                break;
        }
    }
}
