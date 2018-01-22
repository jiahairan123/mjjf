package com.mingjie.jf.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.IntroducerGainListBean;
import com.mingjie.jf.bean.NewBaseListBean;
import com.mingjie.jf.bean.NewServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahairan on 2017/8/4 17.
 */

public class InvitedManActivity extends BaseActivity implements View.OnClickListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private List<IntroducerGainListBean> datas = new ArrayList<>();
    private IntroducerGainListAdapter adapter;
    private ImageView ivLeftPublic;
    String uid;
    private boolean isRefresh;                 // 是否正在刷新
    private boolean isLoad;
    private boolean isCanLoad = true;
    int currentPage = 1;
    String pageSize = "10";
    private int pages; // 数据总条数
    private RefreshLayout refreshLayout;
    private ListView listView;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_introduce_gain_list);
        TextView titleName = (TextView) findViewById(R.id.tv_content_public);
        titleName.setText("受邀请人");
        ivLeftPublic = (ImageView) findViewById(R.id.iv_Left_public);
        ivLeftPublic.setOnClickListener(this);
        refreshLayout = (RefreshLayout) findViewById(R.id.introduce_gainlist_refresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);
        listView = (ListView) findViewById(R.id.gainlist_lv);
        adapter = new IntroducerGainListAdapter(this);
    }

    @Override
    protected void initData() {

       getData();

    }

    private void getData() {

        if (!isCanLoad){
            return;
        }
        if (1 == currentPage){
            datas.clear();
        }

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        Map params = new HashMap();
        params.put("id", uid);
        params.put("currentPage", String.valueOf(currentPage));
        params.put("pageSize", pageSize);

        HttpManager.getInstance().doPost(ServiceName.CGKX_TICHENGGUANLI, params, new HttpCallBack<NewServerResponse<NewBaseListBean<IntroducerGainListBean>>>() {

            @Override
            public void getHttpCallNull(String str) {
                closeRefresh();
                ToastUtil.showToast(InvitedManActivity.this, getResources().getString(R.string.dataError));
            }

            @Override
            public void getHttpCallBack(NewServerResponse<NewBaseListBean<IntroducerGainListBean>> rsp) {
                closeRefresh();

                if (rsp == null) {
                    ToastUtil.showToast(InvitedManActivity.this, getResources().getString(R.string.dataError));
                    return;
                }
                if (!"000000".equals(rsp.getCode())) {

                    ToastUtil.showToast(InvitedManActivity.this, rsp.getMsg());
                }
                if (rsp.getData() == null) {

                    ToastUtil.showToast(InvitedManActivity.this, getResources().getString(R.string.dataError));
                    return;
                }

                //数据长度为0
                if (rsp.getData().getPageTotal() <= 0) {
                    return;
                }
                pages = rsp.getData().getPages();
                if (pages <= currentPage){
                    isCanLoad = false;
                }
                datas.addAll(rsp.getData().getList());
                adapter.setData(datas);
                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onLoad() {
        if (!isCanLoad){
            refreshLayout.setLoading(false);
            return;
        }
        refreshLayout.setLoading(true);
        currentPage += 1;
        isLoad = true;
        getData();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        refreshLayout.setRefreshing(true);
        isRefresh = true;
        isCanLoad = true;
        getData();
    }


    private void closeRefresh() {
        if (refreshLayout == null)
        {
            return;
        }
        if (refreshLayout.isRefreshing())
        {
            refreshLayout.setRefreshing(false);
        }
        else if (refreshLayout.isLoading())
        {
            refreshLayout.setLoading(false);
        }
    }
}