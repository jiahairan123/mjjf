package com.mingjie.jf.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mingjie.jf.R;
import com.mingjie.jf.bean.CallBankResponse;
import com.mingjie.jf.bean.PagerVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserVo;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity implements View.OnClickListener
{

    //    String urlImgCode = "http://192.168.1.120:8080/cgkx-api/imageVcode";
    //    //    static final String URL = "http://192.168.1.120:8080/cgkx-api/api/mobileInterfaces";
    //    static final String URL = "http://192.168.1.120:8080/cgkx-api/doSendRegisterCode";
    //    static final String URL_IMG_CODE = "http://192.168.1.120:8080/cgkx-api/imageVcode";

    @BindView(R.id.edt_url)
    EditText edt_url;

    @BindView(R.id.edt_url_img)
    EditText edt_url_img;

    @BindView(R.id.edt_transUrl)
    EditText edt_transUrl;
    @BindView(R.id.edt_transCode)
    EditText edt_transCode;
    @BindView(R.id.edt_requestData)
    EditText edt_requestData;

    @BindView(R.id.edt_svc_name)
    EditText edt_svc_name;


    @BindView(R.id.btn_ok)
    Button btn_ok;
    @BindView(R.id.btn_add)
    Button btn_add;

    @BindView(R.id.btn_minus)
    Button btn_minus;

    @BindView(R.id.ll_params)
    LinearLayout ll_params;

    @BindView(R.id.btn_Code2)
    View btn_Code2;

    @BindView(R.id.ivw_code)
    ImageView ivw_code;

    @BindView(R.id.tvw_result)
    TextView tvw_result;

    View view;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_help);
        //  ButterKnife.bind(this);  // BaseActivity# setContentView 已经绑定

        // accountCustody.setOnClickListener(this);
        view = LayoutInflater.from(this).inflate(R.layout.widget_help_kv, null);
        //        view = view.findViewWithTag("k2");

        edt_url_img.setText(UrlUtil.getImgUrl());
        edt_url.setText(UrlUtil.getUrl());

        SharedPreferences sp = getSharedPreferences("cgkx", Context.MODE_PRIVATE);
        String svcName = sp.getString("serviceName", "");
        if (!TextUtils.isEmpty(svcName))
        {
            edt_svc_name.setText(svcName);
        }
    }

    @Override
    protected void initData()
    {

    }

    @OnClick({R.id.btn_ok, R.id.btn_add, R.id.btn_minus, R.id.ivw_code, R.id.btn_Code2, R.id.btn_submit, R.id
            .btn_to_bank})
    @Override
    public void onClick(View v)
    {
        String url = edt_url.getText().toString().trim();
        //        String imgUrl = edt_url_img.getText().toString().trim();
        if ("minus".equals(v.getTag()))
        {
            ll_params.removeView((View) v.getParent());
            return;
        }

        switch (v.getId())
        {
            case R.id.ivw_code:
            {
                CfLog.e("ivw_code");
                HttpManager.getInstance().displayImageCode(ivw_code, null, null);
                break;
            }

            case R.id.btn_Code2:
            {
                CfLog.d("clear tag");
                ivw_code.setTag(null);
                ivw_code.setImageResource(R.color.text_gray);
                break;
            }

            case R.id.btn_ok:
            {
                UrlUtil.setUrl(url);
                //                UrlUtil.setImgUrl(imgUrl);
                break;
            }
            case R.id.btn_submit:
            {
                doSubmit();
                tvw_result.setText("Request---");
                CfLog.e(new Gson().toJson(getParams()));
                break;
            }

            case R.id.btn_add:
            {
                view = LayoutInflater.from(this).inflate(R.layout.widget_help_kv, null);
                view.findViewWithTag("minus").setOnClickListener(this);
                ll_params.addView(view);
                break;
            }

            case R.id.btn_to_bank:
            {
                Intent it = new Intent(getBaseContext(), ToBankActivity.class);
                String transUrl = edt_transUrl.getText().toString().trim();
                String transCode = edt_transCode.getText().toString().trim();
                String requestData = edt_requestData.getText().toString().trim();
                it.putExtra("transUrl", transUrl);
                it.putExtra("transCode", transCode);
                it.putExtra("requestData", requestData);
                startActivity(it);
                break;
            }

            default:
                break;

        }

    }


    private void doSubmit()
    {

        String svcName = edt_svc_name.getText().toString().trim(); //CGKX_BID
        // 查对象
        //        HttpCallBack<ServerResponse<FeedbackVo>> callBack = new HttpCallBack<ServerResponse<FeedbackVo>>()
        //        {
        //            @Override
        //            public void getHttpCallNull(String str)
        //            {
        //
        //            }
        //
        //            @Override
        //            public void getHttpCallBack(ServerResponse<FeedbackVo> rsp)
        //            {
        //                if ("000000".equals(rsp.getCode()))
        //                {
        //                    FeedbackVo vo = rsp.getData();
        //
        //                }
        //                else if ("000001".equals(rsp.getCode()))
        //                {
        //                    Toast.makeText(getBaseContext(), "操作失败:" + rsp.getMsg() + "(" + rsp.getCode() + ")", Toast
        //                            .LENGTH_SHORT).show();
        //                }
        //                else
        //                {
        //
        //                }
        //            }
        //        };
        //
        //        // 查列表 带分页
        //        HttpCallBack<ServerResponse<PagerVo<FeedbackVo>>> callBack3 = new
        //                HttpCallBack<ServerResponse<PagerVo<FeedbackVo>>>()
        //                {
        //                    @Override
        //                    public void getHttpCallNull(String str)
        //                    {
        //
        //                    }
        //
        //                    @Override
        //                    public void getHttpCallBack(ServerResponse<PagerVo<FeedbackVo>> rsp)
        //                    {
        //
        //                    }
        //
        //
        //                };

        HttpCallBack<ServerResponse<Map<String, Object>>> mCallBack2 = new
                HttpCallBack<ServerResponse<Map<String, Object>>>()
                {

                    @Override
                    public void getHttpCallNull(String str)
                    {
                        // tvw_result.setText(str);

                        tvw_result.setText(Html.fromHtml(str));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<Map<String, Object>> vo)
                    {
                        if (null != vo)
                        {
                            //                                    vo.getCode();
                            tvw_result.setText(new Gson().toJson(vo));

                            String str = new Gson().toJson(vo);
                            tvw_result.setText(str);
                            //                            tvw_result.setText(new Gson().toJson(vo.getData()));
                            //                            vo.getData().getLoginName();
                            //                            tvw_result.setText(vo.getLoginName());
                        }
                        else
                        {
                            tvw_result.setText("------------*****");
                        }
                    }
                };

        HttpManager.getInstance().doPost(svcName, getParams(), mCallBack2);
        //        HttpManager.getInstance().doPost(svcName, getParams(), getCallBank());

        //        HttpManager.getInstance().doPost(svcName, getParams(), ServerResponse.class);
        //                url = edt_url.getText().toString().trim();
        //                String name = edt_svc_name.getText().toString().trim();
        //                String json = new Gson().toJson(getParams());
        //                NewOkhttp.getInstance().getOkPost(url + name, json, getMyActivity(), mCallBack2);


    }

    public HttpCallBack<ServerResponse<CallBankResponse>> getCallBank()
    {
        return new HttpCallBack<ServerResponse<CallBankResponse>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                //                UIUtils.dimissLoading();
                //                        UIUtils.showToast(RechargeActivity.this, "服务器异常，请重试！");
            }

            @Override
            public void getHttpCallBack(ServerResponse<CallBankResponse> rsp)
            {
                UIUtils.dimissLoading();
                if (rsp == null || !rsp.getCode().equals("000000"))
                {
                    //                            UIUtils.showToast(RechargeActivity.this,
                    // "服务器异常，请重试！");
                }
                else
                {
                    CallBankResponse rdetail = rsp.getData();
                    if (rdetail == null)
                    {
                        //                                UIUtils.showToast(RechargeActivity.this,
                        // "服务器异常，请重试！");

                    }
                    else
                    {
                        // rDetail = rdetail;
                        Intent bankData = new Intent(getBaseContext(), ToBankActivity.class);
                        bankData.putExtra("transUrl", rsp.getData().getHxHostUrl());
                        bankData.putExtra("transCode", rsp.getData().getTransCode());
                        bankData.putExtra("requestData", rsp.getData().getRequestData());
                        bankData.putExtra("channelFlow", rsp.getData().getChannelFlow());
                        startActivity(bankData);
                    }
                }
            }
        };

    }


    private Map getParams()
    {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < ll_params.getChildCount(); i++)
        {
            View ll = ll_params.getChildAt(i);
            String k = ((EditText) ll.findViewWithTag("edt_k")).getText().toString().trim();
            String v = ((EditText) ll.findViewWithTag("edt_v")).getText().toString();
            if (!TextUtils.isEmpty(k))
            {
                map.put(k, v);
            }
        }

        // String svcName = edt_svc_name.getText().toString().trim();
        if (null != ivw_code.getTag())
        {
            map.put("imgKey", ivw_code.getTag().toString());
        }

        //        String str = null;
        if (!map.isEmpty())
        {
            //            str = new Gson().toJson(map);
            return map;
        }
        return null;
    }

    @Override
    protected void onDestroy()
    {
        HttpCallBack<ServerResponse<PagerVo<UserVo>>> mCallBack = null;
        String svcName = edt_svc_name.getText().toString().trim();
        if (!TextUtils.isEmpty(svcName))
        {
            SharedPreferences sp = getSharedPreferences("cgkx", Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = sp.edit();
            edt.putString("serviceName", edt_svc_name.getText().toString().trim());
            edt.commit();
        }

        super.onDestroy();
    }

}
