package com.mingjie.jf.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 忘记密码1
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ChangePasswordFirstActivity extends BaseActivity implements View.OnClickListener, View
        .OnFocusChangeListener {

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.input_yonghu)
    EditText changepsw;//用户名
    @BindView(R.id.psw_input_resetpass_pass_affirm)
    EditText confirm;//手机号
    @BindView(R.id.input_resetpass_verifycode)
    EditText inputResetpassVerifycode;//图形验证码编辑框
    @BindView(R.id.imverificationcode)
    ImageView ivLoginVerifycode;//验证码图片
    @BindView(R.id.btn_login)
    MateralButton btnLogin;//立即验证
    @BindView(R.id.clean)
    ImageView clean;
    @BindView(R.id.clean2)
    ImageView clean2;

    private String phone;//手机号码
    private String name;//用户名
    private String verfycode;//图形验证码
    //
    //    @Override
    //    protected void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_change_password_first);
    //        ButterKnife.bind(this);
    //        HttpManager.getInstance().displayImageCode(ivLoginVerifycode, null, null);
    //        iniview();
    //        initData();
    //
    //    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_password_first);
        ButterKnife.bind(this);
        HttpManager.getInstance().displayImageCode(ivLoginVerifycode, null, null);
        ivLoginVerifycode.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ivLeftPublic.setOnClickListener(this);
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changepsw.setText("");
            }
        });
        clean2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm.setText("");
            }
        });
        confirm.setOnFocusChangeListener(this);//手机号
        changepsw.setOnFocusChangeListener(this);//用户名
    }

    @Override
    protected void initData() {
        tvContentPublic.setText("重置密码");
    }

    //    private void iniview()
    //    {
    //        ivLoginVerifycode.setOnClickListener(this);
    //        btnLogin.setOnClickListener(this);
    //        ivLeftPublic.setOnClickListener(this);
    //        clean.setOnClickListener(new View.OnClickListener()
    //        {
    //            @Override
    //            public void onClick(View view)
    //            {
    //                changepsw.setText("");
    //            }
    //        });
    //        clean2.setOnClickListener(new View.OnClickListener()
    //        {
    //            @Override
    //            public void onClick(View view)
    //            {
    //                confirm.setText("");
    //            }
    //        });
    //        confirm.setOnFocusChangeListener(this);//手机号
    //        changepsw.setOnFocusChangeListener(this);//用户名
    //
    //    }

    //    private void initData()
    //    {
    //        tvContentPublic.setText("重置密码");
    //
    //    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_Left_public:
                this.finish();
                break;
            case R.id.tv_register:
                //免费注册(跳转用户注册)
                //ivLoginVerifycode.getTag().toString()
                Intent register = new Intent(this, UserRegisterActivity.class);
                startActivity(register);
                break;
            case R.id.btn_login://立即验证按钮
                if (checkInfo()) {
                    //提交验证信息是否跳到下一步
                    goNext();
                    //                    ForgetPassWordActionVo vo = new ForgetPassWordActionVo();
                    //                    vo.setName(changepsw.getText().toString());
                    //                    vo.setPhone(confirm.getText().toString());
                    //                    vo.setVcode(inputResetpassVerifycode.getText().toString());
                    //                    new ForgetPassWordAction(this).forgetPassWordAction(vo, changepsw.getText()
                    // .toString(), confirm
                    //                            .getText().toString());
                }
                break;
            case R.id.imverificationcode:
                //显示图形验证码
                HttpManager.getInstance().displayImageCode(ivLoginVerifycode, null, null);
                break;
            default:

        }
    }
    //点击 下一步
    private void goNext() {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(getBaseContext(), R.string.network_unused_retry_pls);
            return;
        }
        UIUtils.showLoading(this);
        Map<String, String> params = new HashMap<>();
        params.put("name", changepsw.getText().toString());
        params.put("phone", confirm.getText().toString());
        params.put("vcode", inputResetpassVerifycode.getText().toString());
        HttpManager.getInstance().doPost(ServiceName.CGKX_DOFORGETPWD, params, new
                HttpCallBack<ServerResponse>() {
                    @Override
                    public void getHttpCallNull(String str) {
                        UIUtils.dimissLoading();
                        UIUtils.showToast(ChangePasswordFirstActivity.this, R.string.dataError);
                        HttpManager.getInstance().displayImageCode(ivLoginVerifycode, null, null);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse rsp) {
                        UIUtils.dimissLoading();
                        if (rsp == null) {
                            UIUtils.showToast(ChangePasswordFirstActivity.this, R.string.dataError);
                            HttpManager.getInstance().displayImageCode(ivLoginVerifycode, null, null);

                        } else {
                            if (!rsp.getCode().equals("000000")) {
                                UIUtils.showToast(ChangePasswordFirstActivity.this, rsp.getMsg());
                                HttpManager.getInstance().displayImageCode(ivLoginVerifycode, null, null);
                            } else {
                                Intent intent = new Intent(ChangePasswordFirstActivity.this,
                                        ChangePasswordTwoActivity.class);
                                intent.putExtra("Vcode", inputResetpassVerifycode.getText().toString().trim());
                                // intent.putExtra("imgKey", obj.getImgKey());
                                intent.putExtra("phone", confirm.getText().toString().trim());
                                intent.putExtra("name", changepsw.getText().toString().trim());
                                startActivity(intent);
                                finish();
                                // }
                            }
                        }
                    }
                });
    }

    //检查 用户名手机号 验证码
    private boolean checkInfo() {
        name = changepsw.getText().toString();
        phone = confirm.getText().toString();
        verfycode = inputResetpassVerifycode.getText().toString();
//        if (TextUtils.isEmpty(name))
//        {
//            UIUtils.showToast(this, "请输入用户名");
//            return false;
//        }
//        else if (name.length() < 2 || name.length() > 20) {
//            UIUtils.showToast(this, "用户名长度2-20");
//            return false;
//        } else
            if (TextUtils.isEmpty(phone)) {
            UIUtils.showToast(this, "请输入手机号码");
            return false;
        } else if (!StringUtils.isPhoneNumber(phone)) {
            UIUtils.showToast(this, "请输入合法的手机号码!");
            return false;
        } else if (TextUtils.isEmpty(verfycode)) {

            UIUtils.showToast(this, "请输入图形验证码");
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.psw_input_resetpass_pass_affirm:
                if (b) {
                    //显示
                    clean2.setVisibility(View.VISIBLE);
                } else {
                    //隐藏
                    clean2.setVisibility(View.GONE);
                }
                break;
            case R.id.input_yonghu:
                if (b) {
                    //显示
                    clean.setVisibility(View.VISIBLE);

                } else {
                    //隐藏
                    clean.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }
}
