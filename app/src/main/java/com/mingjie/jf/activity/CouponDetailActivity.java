package com.mingjie.jf.activity;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.CouponStateBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiahairan on 2017/8/25 08.
 */

public class CouponDetailActivity extends BaseActivity {
    String phone, id;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_coupon_detail);
        phone = getIntent().getStringExtra("phone");
        id = getIntent().getStringExtra("couponRuleId");
    }

    //兑换是type 1； 抢 是2
    @Override
    protected void initData() {
        Map params = new HashMap();
        params.put("phone", phone);
        params.put("couponRuleId", id);
        params.put("type", "2");

        HttpManager.getInstance().doPost(ServiceName.CGKX_DEALGETCOUPON, params, new HttpCallBack<ServerResponse<CouponStateBean>>() {
            @Override
            public void getHttpCallNull(String str) {
            }

            @Override
            public void getHttpCallBack(ServerResponse<CouponStateBean> rsp) {
                ToastUtil.showCenterGraToast(rsp.getData().getState());
            }


        });
    }
}
