package com.mingjie.jf.activity;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

public class DetailedActivity extends BaseActivity implements View.OnClickListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_time)
    TextView detailTime;
    @BindView(R.id.wb_content)
    WebView mWebDes;
    @BindView(R.id.web_pb)
    ProgressBar mWebPb;
    private String mStyle = "yyy/MM/dd  HH:mm:ss";
    private String title, url;
    long date;

    protected void initView()
    {
        setContentView(R.layout.activity_detaile);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        date = intent.getLongExtra("date", 0);
        CfLog.d("---" + date);
        url = intent.getStringExtra("url");
        ivLeftPublic.setOnClickListener(this);
    }

    protected void initData()
    {

        tvContentPublic.setText("公告详情");
        SimpleDateFormat format = new SimpleDateFormat(mStyle);
        detailTime.setText(format.format(new Date(date)));
        detailTitle.setText(title);
        //
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
                view.loadUrl(url);   //在当前的webview中跳转到新的url
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
            mWebPb.setProgress(newProgress);
            if (newProgress == 100)
            {
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
            default:
                break;
        }
    }

}
