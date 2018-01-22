package com.mingjie.jf.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.AnnounceData;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-29 10:58
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class AccountDataActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener
{
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_time)
    TextView detailTime;
//    @BindView(R.id.webview_detaile)
//    TextView mWebDes;
    @BindView(R.id.web_pb)
    ProgressBar mWebPb;
    @BindView(R.id.iv_error_pic)
    ImageView ivError;
    @BindView(R.id.tv_error_msg)
    TextView tvError;
    @BindView(R.id.linear_error)
    LinearLayout mErrorView;
    @BindView(R.id.hava_data)
    LinearLayout havaData;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mRefreshView;
    @BindView(R.id.wb_content)
    WebView wbContent;
    private String mStyle = "yyy/MM/dd  HH:mm:ss";
    private String title, url;
    long date;
    private UserVoValible user;
    private String web;
    private String id;
    private AnnounceData rDetail;
    private String html;

    protected void initView()
    {
        setContentView(R.layout.activity_detaile);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        date = intent.getLongExtra("date", 0);
        ivLeftPublic.setOnClickListener(this);
        //加载出问题后，点击重新加载
        mErrorView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //                mRefreshView.setRefreshing(true);
                onRefresh();
            }
        });
        user = CacheUtils.getUserinfo(this);
        onRefresh();
    }

    private void getData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            closeRefresh();
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            showError(R.mipmap.net_error, "当前网络不可用，点击重试！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        HttpManager.getInstance().doPost(ServiceName.CGKX_ANNOUNCELIST_NUM, params, new
                HttpCallBack<ServerResponse<AnnounceData>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        closeRefresh();
                        showError(R.mipmap.net_error, getResources().getString(R.string.dataError));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<AnnounceData> rsp)
                    {
                        if (rsp == null || !rsp.getCode().equals("000000"))
                        {
                            closeRefresh();
                            showError(R.mipmap.net_error, getResources().getString(R.string.dataError));
                        }
                        else
                        {
                            rDetail = rsp.getData();
                            if (rDetail == null)
                            {
                                closeRefresh();
                                showError(R.mipmap.null_data, "数据为空，点击重试！");
                            }
                            else
                            {
                                closeRefresh();
                                //                                web = rsp.getData();
                                CfLog.i("---" + web);
                                //                        title=rsp.getData().getTitle();
                                showWeb(rDetail);
                            }
                        }
                    }
                });
    }

    //展示数据加载出错布局
    private void showError(int errIconId, String errMsg)
    {
        mRefreshView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        ivError.setImageResource(errIconId);
        tvError.setText(errMsg);
    }

    private void showWeb(AnnounceData rDetail)
    {
        mRefreshView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        detailTitle.setText(rDetail.getTitle());
//        mWebDes.setText(Html.fromHtml(rDetail.getContent()));

        html = rDetail.getContent();
        if (html.contains("src=\"/p"))
        {
            html = html.replace("src=\"/p", "src=\"http://car.zhuji.net/p");
        }
        wbContent.loadDataWithBaseURL(null , html, "text/html", "utf-8", null);
//        wbContent.loadData(rDetail.getContent()  ,"text/html", "utf-8");
    }

    protected void initData()
    {
        tvContentPublic.setText("公告详情");

        mRefreshView.setOnRefreshListener(this);//添加刷新事件
        mRefreshView.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);//设置刷新圆圈颜色
        mRefreshView.post(new Runnable()
        {
            @Override
            public void run()
            {
                mRefreshView.setRefreshing(true);
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh()
    {
        getData();
    }
    //
    //    private class WebsViewClient extends WebChromeClient
    //    {
    //        @Override
    //        public void onProgressChanged(WebView view, int newProgress)
    //        {
    //            mWebPb.setProgress(newProgress);
    //            if (newProgress == 100)
    //            {
    //                mWebPb.setVisibility(View.GONE);
    //            }
    //            super.onProgressChanged(view, newProgress);
    //        }
    //    }

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

    /**
     * 关闭刷新
     */
    private void closeRefresh()
    {
        if (mRefreshView.isRefreshing())
        {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
    }



}
