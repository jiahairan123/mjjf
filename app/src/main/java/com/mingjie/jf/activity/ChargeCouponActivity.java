package com.mingjie.jf.activity;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.ChargeCouponStateBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.necer.ndialog.NDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiahairan on 2017/8/25 10.
 * <p>
 * //兑换是type 1； 抢 是2
 */

public class ChargeCouponActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;
    String id;
    String phone, inputphone;
    String exchangeCode;
    Button sureBtn;
    private EditText editText, phoneEt;
    private RelativeLayout phoneRl;
    TextView titleText;
    ImageView ivLeftPublic;//返回


    @Override
    protected void initView() {
        setContentView(R.layout.get_coupon_head);
    }

    @Override
    protected void initData() {
        titleText = (TextView) findViewById(R.id.tv_content_public);
        ivLeftPublic = (ImageView) findViewById(R.id.iv_Left_public);
        ivLeftPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleText.setText("兑换中心");
        editText = (EditText) findViewById(R.id.getcoupon_head_et);

        phoneRl = (RelativeLayout) findViewById(R.id.phone_rl_chargecoupon);

        phoneEt = (EditText) findViewById(R.id.getcoupon_head_phone);

        sureBtn = (Button) findViewById(R.id.getcoupon_head_btn);
        sureBtn.setOnClickListener(this);
        //接传过来的 id 和 phone
        id = getIntent().getStringExtra("userId");
        phone = getIntent().getStringExtra("phone");


        if (!checkIsLogin()) {
//            phoneRl.setVisibility(View.VISIBLE);

        } else {
//            phoneRl.setVisibility(View.GONE);
            phoneEt.setText(phone);
        }

    }


    @Override
    public void onClick(View v) {

        exchangeCode = editText.getText().toString();
        inputphone = phoneEt.getText().toString();
        //点击兑换按钮时候 判断 兑换码是否为空 为空 不发起请求

//        if ("".equals(phone) ){ //没有登录
        if (!checkIsLogin()) {
            if (exchangeCode.length() == 0) {

                ToastUtil.showJiaToast("请输入您的兑换码!");
                return;
            }
            if (inputphone.isEmpty()) {
                ToastUtil.showJiaToast("请输入您的手机号码!");
            } else if (inputphone.length() != 11) {
                ToastUtil.showJiaToast("请输入正确的手机号码!");

            } else {
                Map params = new HashMap();

                params.put("from", "android");
                params.put("source", "android");
                params.put("phone", inputphone);
                params.put("type", "1");
                params.put("exchangeCode", exchangeCode);

                HttpManager.getInstance().doPost(ServiceName.CGKX_DEALGETCOUPONACTIVITY, params, new HttpCallBack<ServerResponse<ChargeCouponStateBean>>() {
                    @Override
                    public void getHttpCallNull(String str) {
                        ToastUtil.showCenterGraToast(str);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<ChargeCouponStateBean> rsp) {

                        if (rsp == null) {
                            ToastUtil.showToast(ChargeCouponActivity.this, getResources().getString(R.string.dataError));
                            return;
                        }
                        if (!"000000".equals(rsp.getCode())) {

                            ToastUtil.showToast(ChargeCouponActivity.this, rsp.getMsg());
                        }
                        if (rsp.getData() == null) {

                            ToastUtil.showToast(ChargeCouponActivity.this, getResources().getString(R.string.dataError));
                            return;
                        }
                        // 显示 兑换的状态
                        ToastUtil.showJiaToast(rsp.getData().getExchangeState());
                    }
                });

            }

        } else {
            if (exchangeCode.length() == 0) {
                ToastUtil.showJiaToast("请输入您的兑换码!");
                return;
            }
            //登录状态 phone 不为空 兑换码不为空
            Map map = new HashMap();
            map.put("from", "android");
            map.put("source", "android");
            map.put("phone", inputphone);
            map.put("userId", id);
            map.put("type", "1");
            map.put("exchangeCode", exchangeCode);
            HttpManager.getInstance().doPost(ServiceName.CGKX_DEALGETCOUPONACTIVITY, map, new HttpCallBack<ServerResponse<ChargeCouponStateBean>>() {
                @Override
                public void getHttpCallNull(String str) {
                    ToastUtil.showToast(ChargeCouponActivity.this, str);
                }

                @Override
                public void getHttpCallBack(ServerResponse<ChargeCouponStateBean> rsp) {

                    if (rsp == null) {
                        ToastUtil.showToast(ChargeCouponActivity.this, getResources().getString(R.string.dataError));
                        return;
                    }
                    if (!"000000".equals(rsp.getCode())) {

                        ToastUtil.showToast(ChargeCouponActivity.this, rsp.getMsg());
                    }
                    if (rsp.getData() == null) {

                        ToastUtil.showToast(ChargeCouponActivity.this, getResources().getString(R.string.dataError));
                        return;
                    }

                    if ("".equals(rsp.getData().getExchangeState()) || rsp.getData().getState() != null) {

                        ToastUtil.showJiaToast(rsp.getData().getState());
                    } else {
                        ToastUtil.showJiaToast(rsp.getData().getExchangeState());
                    }


                }

            });

        }


//        //判断 phone 是否为空 为空就是未登录状态
//        if ("".equals(phone)) { //未登录
//            new NDialog(this).setTitle("请输入手机号码")
//                    .setInputTextSize(14)
//                    .setInputType(InputType.TYPE_CLASS_PHONE)
//                    .setPositiveButtonText("确定")
//                    .setNegativeButtonText("取消")
//                    .setNegativeTextColor(Color.parseColor("#aaaaaa"))
//                    .setOnInputListener(new NDialog.OnInputListener() {
//                        @Override
//                        public void onClick(String inputText, int which) {
//                            switch (which){
//                                case 1:
//
//                                    if (inputText.length() == 11){
//
//                                    phone = inputText;
//                                    Map params = new HashMap();
//                                    params.put("phone", inputText);
//                                    params.put("type", "1");
//                                    params.put("exchangeCode", exchangeCode);
//
//                                    HttpManager.getInstance().doPost(ServiceName.CGKX_DEALGETCOUPONACTIVITY, params, new HttpCallBack<ServerResponse<ChargeCouponStateBean>>() {
//                                        @Override
//                                        public void getHttpCallNull(String str) {
//                                            ToastUtil.showCenterGraToast(str);
//                                        }
//
//                                        @Override
//                                        public void getHttpCallBack(ServerResponse<ChargeCouponStateBean> rsp) {
//
//                                            if (rsp == null) {
//                                                ToastUtil.showToast(ChargeCouponActivity.this, getResources().getString(R.string.dataError));
//                                                return;
//                                            }
//                                            if (!"000000".equals(rsp.getCode())) {
//
//                                                ToastUtil.showToast(ChargeCouponActivity.this, rsp.getMsg());
//                                            }
//                                            if (rsp.getData() == null) {
//
//                                                ToastUtil.showToast(ChargeCouponActivity.this, getResources().getString(R.string.dataError));
//                                                return;
//                                            }
//                                            // 显示 兑换的状态
//                                            ToastUtil.showJiaToast(rsp.getData().getExchangeState());
//
//
//                                        }
//                                    });
//                                    } else {
//                                        ToastUtil.showJiaToast("请输入正确的手机号码！");
//                                    }
//                                    break;
//                                case 0:
//                                    break;
//
//                            }
//                        }
//                    }).create(NDialog.INPUT).show();
//
//        } else {
//            //phone 不为空
//            Map map = new HashMap();
//            map.put("phone", phone);
//            map.put("userId", id);
//            map.put("type", "1");
//            map.put("exchangeCode", exchangeCode);
//            HttpManager.getInstance().doPost(ServiceName.CGKX_DEALGETCOUPONACTIVITY, map, new HttpCallBack<ServerResponse<ChargeCouponStateBean>>() {
//                @Override
//                public void getHttpCallNull(String str) {
//                    ToastUtil.showToast(ChargeCouponActivity.this, str);
//                }
//
//                @Override
//                public void getHttpCallBack(ServerResponse<ChargeCouponStateBean> rsp) {
//
//                    if (rsp == null) {
//                        ToastUtil.showToast(ChargeCouponActivity.this, getResources().getString(R.string.dataError));
//                        return;
//                    }
//                    if (!"000000".equals(rsp.getCode())) {
//
//                        ToastUtil.showToast(ChargeCouponActivity.this, rsp.getMsg());
//                    }
//                    if (rsp.getData() == null) {
//
//                        ToastUtil.showToast(ChargeCouponActivity.this, getResources().getString(R.string.dataError));
//                        return;
//                    }
//
//                    ToastUtil.showJiaToast(rsp.getData().getExchangeState());
//
//                }
//
//            });
//        }
    }


    private boolean checkIsLogin() {

        if (!CacheUtils.isLogin()) {
            return false;
        }
        return CacheUtils.isLogin();
    }
}
