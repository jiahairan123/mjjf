package com.mingjie.jf.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.LoadInfoFollowAdapter;
import com.mingjie.jf.bean.LoanPersonFollow;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahairan on 2018/1/4 10.
 * 借款人借款信息跟踪
 */

public class LoanInfoFollowActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvContent;//activity标题
    private RecyclerView recyclerView;
    private List<LoanPersonFollow> mDatas = new ArrayList<>();
    String id;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_loan_info_follow);
        tvContent = (TextView) findViewById(R.id.tv_content_public);
        tvContent.setText("借款人借款信息跟踪");
        findViewById(R.id.iv_Left_public).setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_loan_infofollow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new LoadInfoFollowAdapter(this, mDatas));
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        if (!id.isEmpty() && !id.equals("null")) {
            getData();
        }
    }

    private void getData() {

        if (!Utilities.canNetworkUseful(this)) {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        HttpManager.getInstance().doPost(ServiceName.CGKX_LOANINFOFOLLOW, params, new HttpCallBack<ServerResponse<List<LoanPersonFollow>>>() {


            @Override
            public void getHttpCallNull(String str) {
                ToastUtil.showCenterGraToast(str);
            }

            @Override
            public void getHttpCallBack(ServerResponse<List<LoanPersonFollow>> rsp) {
                if (rsp == null) {
                    ToastUtil.showCenterGraToast("借款人借款信息跟踪返回数据为空！");
                    return;
                }

                if (!rsp.getCode().equals("000000")) {
                    ToastUtil.showCenterGraToast(rsp.getMsg());
                    return;
                }

                if (rsp.getData() == null || rsp.getData().size() == 0) {
                    ToastUtil.showCenterGraToast("借款人借款信息跟踪返回数据列表为零！");
                    return;
                }

                mDatas.clear();
                mDatas.addAll(rsp.getData());
                recyclerView.getAdapter().notifyDataSetChanged();

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_Left_public:
                finish();
                break;


            default:
                break;
        }
    }

}
