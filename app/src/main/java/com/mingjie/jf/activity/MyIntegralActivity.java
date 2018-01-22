package com.mingjie.jf.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.IntegralAdapter;
import com.mingjie.jf.bean.BaseListBean;
import com.mingjie.jf.bean.IntegralBean;
import com.mingjie.jf.bean.ServerResponse;
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
 * Created by jiahairan on 2017/6/30 10.
 */

public class MyIntegralActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {

    private List<IntegralBean> datas = new ArrayList<>();
    private IntegralAdapter adapter;
    private ListView listView;
    private View view;
    private TextView convertedPoint, frozePoint, usablePoint;
    private ImageView ivLeftPublic;
    private TextView titleTv;
    RefreshLayout mRefreshView;
    private boolean isRefresh;                 // 是否正在刷新
    private boolean isLoad;
    private boolean isCanLoad = true;
    int currentPage = 1 ;//
    private int pages; // 数据总条数

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_integral);
        ivLeftPublic = (ImageView) findViewById(R.id.iv_Left_public);
        titleTv = (TextView) findViewById(R.id.tv_content_public);
        ivLeftPublic.setOnClickListener(this);
        //头布局
        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.lv_head, null);
        //布局
        mRefreshView = (RefreshLayout) findViewById(R.id.integral_swipe);
        mRefreshView.setOnLoadListener(this);
        mRefreshView.setOnRefreshListener(this);
        listView = (ListView) findViewById(R.id.lv_integral_detail);

        convertedPoint = (TextView) view.findViewById(R.id.tv_convertPoint);
        frozePoint = (TextView) view.findViewById(R.id.tv_frozePoint);
        usablePoint = (TextView) view.findViewById(R.id.tv_usablePoint);

        listView.addHeaderView(view);
        adapter = new IntegralAdapter(this);
    }

    @Override
    protected void initData() {
        //设置title
        titleTv.setText("我的积分");
        //网络请求
        getData();
    }

    //
    private void getData() {

        if (!isCanLoad){
            return;
        }
        if (1 == currentPage){
            datas.clear();

        }
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        Map params = new HashMap();
        params.put("id", uid);
        params.put("currentPage",currentPage);
        params.put("pageSize", 12);

        HttpManager.getInstance().doPost(ServiceName.CGKX_MYINTEGRAL, params,
                new HttpCallBack<ServerResponse<BaseListBean<IntegralBean>>>() {
                    @Override
                    public void getHttpCallNull(String str) {
                        closeRefresh();
                        ToastUtil.showToast(MyIntegralActivity.this, getResources().getString(R.string.dataError));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<BaseListBean<IntegralBean>> rsp) {

                        closeRefresh();
                        if (rsp == null) {
                            ToastUtil.showToast(MyIntegralActivity.this, getResources().getString(R.string.dataError));
                            return;
                        }
                        if (!"000000".equals(rsp.getCode())) {

                            ToastUtil.showToast(MyIntegralActivity.this, rsp.getMsg());
                        }
                        if (rsp.getData() == null) {

                            ToastUtil.showToast(MyIntegralActivity.this, getResources().getString(R.string.dataError));
                            return;
                        }

                        //数据长度为0
                        if (rsp.getData().getPageTotal() <= 0) {
                            return;
                        }
                        //获取总共的页数
                        pages = rsp.getData().getPages();
                        //总页数2    当前页1
                        if (pages <= currentPage){
                            isCanLoad = false;
                        }

                        convertedPoint.setText(rsp.getData().getConvertedPoint());
                        frozePoint.setText(rsp.getData().getFrozePoint());
                        usablePoint.setText(rsp.getData().getUsablePoint());

                        datas.addAll(rsp.getData().getList());
                        adapter.setData(datas);
                        listView.setAdapter(adapter);

                    }
                });
    }

    private void closeRefresh() {
        if (mRefreshView == null)
        {
            return;
        }
        if (mRefreshView.isRefreshing())
        {
            mRefreshView.setRefreshing(false);
        }
        else if (mRefreshView.isLoading())
        {
            mRefreshView.setLoading(false);
        }
    }

    @Override
    public void onClick(View v) {
        finish();

    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        mRefreshView.setRefreshing(true);
        isRefresh = true;
        isCanLoad = true;
        getData();
    }

    @Override
    public void onLoad() {

        if (!isCanLoad){
            mRefreshView.setLoading(false);
            return;
        }
            mRefreshView.setLoading(true);
            currentPage += 1;
            isLoad = true;
            getData();
//        }
    }
}


