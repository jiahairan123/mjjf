package com.mingjie.jf.activity;

import android.content.Intent;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.action.AbsSmsCodeAction;
import com.mingjie.jf.action.LoginAction;
import com.mingjie.jf.bean.LoginActionVo;
import com.mingjie.jf.bean.Register;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.SmsCodeActionVo;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.DES3EncryptAndDecrypt;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 用户注册
 * <p>创 建 人：QinSiYi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class UserRegisterActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.edt_user_register_name)
    EditText edtUserRegisterName;//用户名
    @BindView(R.id.register_input_resetpass_pass_affirm)
    EditText phone;//手机号
    @BindView(R.id.edt_user_register_password)
    EditText edtPassword;//密码
    @BindView(R.id.input_register_affimPassword)
    EditText confirmPassword;//确认密码
    @BindView(R.id.input_register_verifycode)
    EditText edtVerifycode;//短信验证码
    @BindView(R.id.verificationcode)
    MateralButton verificationcode;//获取短信验证码
    @BindView(R.id.linear_recommend)
    LinearLayout linearRecommend;//推荐人
    @BindView(R.id.recommen_id)
    EditText recommenId;//推荐人id
    @BindView(R.id.ck_register_protocol)
    ImageView ckRegisterProtocol;
    @BindView(R.id.btn_login)
    MateralButton btnLogin;//注册
    @BindView(R.id.ll_recommend)
    RelativeLayout mrecommendID;//推荐人
    @BindView(R.id.iv_recommend)
    ImageView mIvArrowIcon;//推荐人id
    @BindView(R.id.line)
    View line;//推荐人id
    @BindView(R.id.input_resetpass_verifycode)
    EditText inputResetpassVerifycode;//图形验证码输入框
    @BindView(R.id.res_imverificationcode)
    ImageView resImverificationcode;//图形验证码

    @BindView(R.id.tv_use_protocol)
    TextView tvUserProtocol;//协议
    @BindView(R.id.clean_name)
    ImageView cleanName;
    @BindView(R.id.clean_phone)
    ImageView cleanPhone;
    @BindView(R.id.clean_recommend_ph)
    ImageView ivRecommendPh;
    @BindView(R.id.ll_check)
    LinearLayout llCheck;

    private TimeCount mTiemTimeCount;

    @BindView(R.id.re_visiable)
    ImageView visiable;

    @BindView(R.id.re_visiable1)
    ImageView visiable1;

    private boolean isHidden = false;
    private boolean isSecondHidden = false;
    private final int GETCODE_TYPE = 0;
    private final int REGISTER_TYPE = 1;
    private boolean isAgree = true;//是否同意服务协议

    private String phoneNumber, userName, oncePass, twopass, smsCheckCode, recommendID;

    private static String verfycode;

    //    @Override
    //    protected void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_register);
    //        ButterKnife.bind(this);
    //        HttpManager.getInstance().displayImageCode(resImverificationcode, null, null);
    //        iniview();
    //        initData();
    //
    //    }

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        HttpManager.getInstance().displayImageCode(resImverificationcode, null, null);

        verificationcode.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        linearRecommend.setOnClickListener(this);
        ivLeftPublic.setOnClickListener(this);
        resImverificationcode.setOnClickListener(this);
        //        ckRegisterProtocol.setOnClickListener(this);
        llCheck.setOnClickListener(this);
        tvUserProtocol.setOnClickListener(this);
        edtUserRegisterName.setOnFocusChangeListener(this);
        phone.setOnFocusChangeListener(this);
        edtPassword.setOnFocusChangeListener(this);
        recommenId.setOnFocusChangeListener(this);
        cleanName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                edtUserRegisterName.setText("");
            }
        });
        cleanPhone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                phone.setText("");
            }
        });
        ivRecommendPh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                recommenId.setText("");
            }
        });
        //计时器
        mTiemTimeCount = new TimeCount(60000, 1000, verificationcode);

        setClickAndTouchListener(vkbd, edtPassword);
    }

    //    private void iniview()
    //    {
    //        verificationcode.setOnClickListener(this);
    //        btnLogin.setOnClickListener(this);
    //        linearRecommend.setOnClickListener(this);
    //        ivLeftPublic.setOnClickListener(this);
    //        resImverificationcode.setOnClickListener(this);
    //        //        ckRegisterProtocol.setOnClickListener(this);
    //        llCheck.setOnClickListener(this);
    //        tvUserProtocol.setOnClickListener(this);
    //        edtUserRegisterName.setOnFocusChangeListener(this);
    //        phone.setOnFocusChangeListener(this);
    //        Password.setOnFocusChangeListener(this);
    //        cleanName.setOnClickListener(new View.OnClickListener()
    //        {
    //            @Override
    //            public void onClick(View view)
    //            {
    //                edtUserRegisterName.setText("");
    //            }
    //        });
    //        cleanPhone.setOnClickListener(new View.OnClickListener()
    //        {
    //            @Override
    //            public void onClick(View view)
    //            {
    //                phone.setText("");
    //            }
    //        });
    //        ivRecommendPh.setOnClickListener(new View.OnClickListener()
    //        {
    //            @Override
    //            public void onClick(View view)
    //            {
    //                recommenId.setText("");
    //            }
    //        });
    //        //计时器
    //        mTiemTimeCount = new TimeCount(60000, 1000, verificationcode);
    //    }

    protected void initData()
    {
        tvContentPublic.setText("用户注册");
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                this.finish();
                break;
            case R.id.iv_recommend:
                break;
            case R.id.linear_recommend:
                //显示推荐人
                int visibility = mrecommendID.getVisibility();
                if (visibility == View.VISIBLE)
                {
                    mIvArrowIcon.setImageResource(R.mipmap.down);
                    mrecommendID.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);

                }
                else
                {
                    mIvArrowIcon.setImageResource(R.mipmap.up);
                    mrecommendID.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_use_protocol://跳转h5页面
                Intent intent = new Intent(this, PrivateActivity.class);
                intent.putExtra("bannerTitle", getResources().getString(R.string.register_tv2_title));
                intent.putExtra("data", UrlUtil.getPrivateUrl());//协议h5的网址
                startActivity(intent);
                break;
            case R.id.btn_login:
                if (checkInfo(REGISTER_TYPE))
                {
                    UIUtils.showLoading(this);
                    getRegister();
                }
                break;
            case R.id.res_imverificationcode:
                //显示图形验证码
                HttpManager.getInstance().displayImageCode(resImverificationcode, null, null);
                break;
            case R.id.ll_check:
                isAgree = !isAgree;
                ckRegisterProtocol.setImageResource(isAgree ? R.mipmap.deal_select_true : R.mipmap.deal_select);
                break;
            case R.id.verificationcode:
                if (checkInfo(GETCODE_TYPE))
                {
                    verificationcode.setEnabled(false);
                    //获取短信验证码
                    SmsCodeActionVo smsvo = new SmsCodeActionVo();
                    smsvo.setPhone(phone.getText().toString());
                    smsvo.setVcode(inputResetpassVerifycode.getText().toString());
                    new AbsSmsCodeAction(this, resImverificationcode, mTiemTimeCount)
                    {
                        public void setSmsCode(String smsCode)
                        {
                            //验证码
                            verfycode = smsCode;
                            if (UrlUtil.isSmsDebug())
                            {
                                edtVerifycode.setText(verfycode);
                            }
                        }
                        public void setSmsCodeError()
                        {
                            verificationcode.setEnabled(true);
                        }
                    }.registersmsCodeAction(smsvo);
                }
                break;
            case R.id.re_visiable:
                if (!isHidden)
                {
                    //设置EditText文本为可见的
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visiable.setImageResource(R.mipmap.visiable_open);
                }
                else
                {
                    //设置EditText文本为隐藏的
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visiable.setImageResource(R.mipmap.visiable_close);
                }
                isHidden = !isHidden;
                edtPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = edtPassword.getText();
                if (charSequence instanceof Spannable)
                {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            default:
                break;
        }
    }

    //进行注册
    private void getRegister()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(UserRegisterActivity.this, "当前网络不可用，请重试！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("from", "android");
        params.put("name", edtUserRegisterName.getText().toString().replace(" ", ""));
        params.put("phone", phone.getText().toString().trim());
        params.put("sourceInt", "2");
        params.put("vcode", inputResetpassVerifycode.getText().toString().trim());
//        params.put("password", Password.getText().toString());
        params.put("password", DES3EncryptAndDecrypt.des3EncryptMode(edtPassword.getText().toString()));
        params.put("referrer", recommenId.getText().toString().trim());
        //短信验证码
        params.put("pcode", verfycode);

        HashMap<String, String> map = new HashMap<>();
        map.put("name", params.get("name"));
        map.put("HHmm", TimeFormatUtil.getSimpleTime());
        map.put("HH", TimeFormatUtil.getHour() + "H");
        MobclickAgent.onEvent(this, ServiceName.APP_USER_REGISTER, map);

        HttpManager.getInstance().doPost(ServiceName.APP_USER_REGISTER, params, new
                HttpCallBack<ServerResponse<Register>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        UIUtils.dimissLoading();
                        UIUtils.showToast(UserRegisterActivity.this, R.string.dataError);
                        HttpManager.getInstance().displayImageCode(resImverificationcode, null, null);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<Register> rsp)
                    {
                        UIUtils.dimissLoading();
                        if (rsp == null)
                        {
                            UIUtils.showToast(UserRegisterActivity.this, R.string.service_err);
                            HttpManager.getInstance().displayImageCode(resImverificationcode, null, null);
                        }
                        else if (null != rsp.getCode() && !"000000".equals(rsp.getCode()))
                        {
                            UIUtils.showToast(UserRegisterActivity.this, rsp.getMsg());
                            HttpManager.getInstance().displayImageCode(resImverificationcode, null, null);
                            mTiemTimeCount.cancel();//停掉计时器
                            mTiemTimeCount.onFinish();
                            inputResetpassVerifycode.setText("");
                            edtVerifycode.setText("");

                        }
                        else if (null != rsp.getCode() && rsp.getCode().equals("000000"))
                        {
                            //注册成功后直接调用登录接口
                            LoginActionVo vo = new LoginActionVo();
                            vo.setName(edtUserRegisterName.getText().toString().replace(" ", ""));
                            vo.setPassword(DES3EncryptAndDecrypt.des3EncryptMode(edtPassword.getText().toString()));
                            new LoginAction(UserRegisterActivity.this).loginAction(vo, LoginActivity.START_TYEP_2MAIN);
                            //                            Intent intent = new Intent(UserRegisterActivity.this,
                            // LoginActivity.class);
                            //                            startActivity(intent);
                            //                            finish();
                        }
                    }
                });

    }

    private boolean checkInfo(int type)
    {
        phoneNumber = phone.getText().toString();
        userName = edtUserRegisterName.getText().toString();
        oncePass = edtPassword.getText().toString();
        twopass = confirmPassword.getText().toString();
        smsCheckCode = inputResetpassVerifycode.getText().toString();//图形验证码
        recommendID = recommenId.getText().toString();
        verfycode = edtVerifycode.getText().toString();//短信验证码
        if (TextUtils.isEmpty(userName))
        {
            UIUtils.showToast(this, "请输入用户名");
            return false;
        }
        else if (userName.length() < 2 || userName.length() > 20)
        {
            UIUtils.showToast(this, "用户名长度2-20");
            return false;
        }
        else if (TextUtils.isEmpty(phoneNumber))
        {
            UIUtils.showToast(this, "请输入手机号码");
            return false;
        }
        else if (!StringUtils.isPhoneNumber(phoneNumber))
        {
            UIUtils.showToast(this, "请输入合法的手机号码!");
            return false;
        }
        else if (TextUtils.isEmpty(oncePass))
        {
            UIUtils.showToast(this, "请输入登录密码");
            return false;
        }
        else if (oncePass.length() < 6 || oncePass.length() > 20)
        {
            UIUtils.showToast(this, "密码长度6-20");
            return false;
        }
        else if (!Pattern.compile("^(?![^0-9]+$)(?![^a-zA-Z]+$).+$").matcher(oncePass).matches())
        {
            UIUtils.showToast(this, "密码必须包含数字和字母");
            return false;
        }
        else if (TextUtils.isEmpty(smsCheckCode))
        {
            UIUtils.showToast(this, "图形验证码不能为空");
            return false;
        }

        if (type == GETCODE_TYPE)
        {
            if (TextUtils.isEmpty(smsCheckCode))
            {
                UIUtils.showToast(this, "请输入验证码");
                return false;
            }
        }

        if (type == REGISTER_TYPE)
        {
            if (TextUtils.isEmpty(verfycode))
            {
                UIUtils.showToast(this, "请输入短信验证码");
                return false;
            }
            if (!isAgree)
            {
                UIUtils.showToast(this, "请勾选协议");
                return false;
            }
        }
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(UserRegisterActivity.this, "当前网络不可用，请重试！");
            return false;
        }
        return true;
    }

    public static void setSmsCode(String smsdata)
    {
        //验证码
        verfycode = smsdata;
    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        switch (view.getId())
        {
            case R.id.register_input_resetpass_pass_affirm://手机号
                if (b)
                {
                    cleanPhone.setVisibility(View.VISIBLE);
                }
                else
                {
                    cleanPhone.setVisibility(View.GONE);
                }
                break;
            case R.id.edt_user_register_name://用户名
                if (b)
                {
                    cleanName.setVisibility(View.VISIBLE);
                }
                else
                {
                    cleanName.setVisibility(View.GONE);
                }
                break;
            case R.id.recommen_id://推荐人手机号
                if (b)
                {
                    ivRecommendPh.setVisibility(View.VISIBLE);
                }
                else
                {
                    ivRecommendPh.setVisibility(View.GONE);
                }
                break;
            case R.id.edt_user_register_password://登录密码
                if (b)
                {
                    visiable.setVisibility(View.VISIBLE);
                    visiable.setOnClickListener(this);
                }
                else
                {
                    visiable.setVisibility(View.GONE);
                }
                break;
            default:
        }
    }
}

