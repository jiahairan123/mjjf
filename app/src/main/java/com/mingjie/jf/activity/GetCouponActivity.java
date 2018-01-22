package com.mingjie.jf.activity;

import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.GetCouponAdapter;
import com.mingjie.jf.bean.CouponStateBean;
import com.mingjie.jf.bean.NewGetCouponBean;
import com.mingjie.jf.bean.NewRspBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.widgets.OnPressListener;
import com.necer.ndialog.NDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahairan on 2017/8/23 13.
 */

public class GetCouponActivity extends BaseActivity implements View.OnClickListener, OnPressListener {

    TextView titleText;
    ImageView ivLeftPublic;//返回
    ListView listView;
    GetCouponAdapter adapter;
    int currentPage = 1;//
    String phone;
    String enterPhone;
    String isTime = "";
    View view, footView;
    private List<NewGetCouponBean.GetCouponBean1> datas = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_get_coupon);
        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.header_view, null);
        footView = LayoutInflater.from(getBaseContext()).inflate(R.layout.footer_view, null);
        titleText = (TextView) findViewById(R.id.tv_content_public);
        ivLeftPublic = (ImageView) findViewById(R.id.iv_Left_public);

        listView = (ListView) findViewById(R.id.lv_get_coupon);
        listView.addHeaderView(view);
        listView.addFooterView(footView);

    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
        adapter = new GetCouponAdapter(this);
        adapter.setOnPressListener(this);
        ivLeftPublic.setOnClickListener(this);
        titleText.setText("抢券中心");
        getData();
    }

    private void getData() {

        HttpManager.getInstance().doPost(ServiceName.CGKX_INITGETCOUPON, new HttpCallBack<NewRspBean<NewGetCouponBean>>() {

            @Override
            public void getHttpCallNull(String str) {

                ToastUtil.showToast(GetCouponActivity.this, "抢券中心数据获取失败");
            }

            @Override
            public void getHttpCallBack(NewRspBean<NewGetCouponBean> rsp) {
                datas.clear();



                if (rsp == null) {
                    ToastUtil.showToast(GetCouponActivity.this, "抢券中心数据为空");
                    return;
                }
                if (!"000000".equals(rsp.getCode())) {

                    ToastUtil.showToast(GetCouponActivity.this, rsp.getMsg());
                }
                if (rsp.getData() == null) {

                    ToastUtil.showToast(GetCouponActivity.this, "抢券中心数据列表为空");
                    return;
                }

                isTime = rsp.getData().getIsTime();

                if (isTime.equals("") || isTime.equals("null")){
                    isTime = "0";
                    ToastUtil.showToast(GetCouponActivity.this, "发券时间获取失败");
                }

                adapter.setIsTime(isTime);
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
    public void onClick(final int position) {

        if (isTime.equals("0")){
            ToastUtil.showCenterGraToast("未到抢券时间");
            return;
        }

        if (!checkIsLogin()) {
//        } else {
//
//        }
//        if ("".equals(phone)){
            new NDialog(this).setTitle("请输入手机号码")
                    .setInputType(InputType.TYPE_CLASS_PHONE)
                    .setInputTextSize(14)
                    .setPositiveButtonText("确定")
                    .setNegativeButtonText("取消")
                    .setNegativeTextColor(Color.parseColor("#00ff00"))
                    .setOnInputListener(new NDialog.OnInputListener() {
                        @Override
                        public void onClick(String inputText, int which) {
                            switch (which) {
                                //which,0代表NegativeButton，1代表PositiveButton
                                case 1:
                                    enterPhone = inputText;
                                    Map params = new HashMap();
                                    params.put("from", "android");
                                    params.put("source", "android");
                                    params.put("phone", enterPhone);
                                    params.put("couponRuleId", datas.get(position).getRewardType());
                                    params.put("type", "2");

                                    HttpManager.getInstance().doPost(ServiceName.CGKX_DEALGETCOUPON, params, new HttpCallBack<ServerResponse<CouponStateBean>>() {

                                        @Override
                                        public void getHttpCallNull(String str) {
                                            ToastUtil.showCenterGraToast(str);
                                        }

                                        @Override
                                        public void getHttpCallBack(ServerResponse<CouponStateBean> rsp) {

                                            if ("".equals(rsp.getData().getVoucherState()) || rsp.getData().getVoucherState() == null) {
                                                ToastUtil.showToast(GetCouponActivity.this, rsp.getData().getState());
                                            } else {
                                                ToastUtil.showToast(GetCouponActivity.this, rsp.getData().getVoucherState());
                                            }
                                            getData();
                                        }

                                    });

                                    break;
                                case 0:
                                    break;
                            }
                        }
                    }).create(NDialog.INPUT).show();


        } else {

            Map params = new HashMap();
            params.put("from", "android");
            params.put("source", "android");
            params.put("phone", phone);
            params.put("couponRuleId", datas.get(position).getRewardType());
            params.put("type", "2");

            HttpManager.getInstance().doPost(ServiceName.CGKX_DEALGETCOUPON, params, new HttpCallBack<ServerResponse<CouponStateBean>>() {

                @Override
                public void getHttpCallNull(String str) {
                    ToastUtil.showCenterGraToast(str);
                }

                @Override
                public void getHttpCallBack(ServerResponse<CouponStateBean> rsp) {

                    if ("".equals(rsp.getData().getVoucherState()) || rsp.getData().getVoucherState() == null) {
                        ToastUtil.showToast(GetCouponActivity.this, rsp.getData().getState());
                    } else {
                        ToastUtil.showToast(GetCouponActivity.this, rsp.getData().getVoucherState());
                    }

                    getData();

                }

            });
        }
    }

    private boolean checkIsLogin() {

        if (!CacheUtils.isLogin()) {
            return false;
        }
        return CacheUtils.isLogin();
    }
}
