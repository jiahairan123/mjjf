package com.mingjie.jf.activity;

import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.action.AbsSmsCodeAction;
import com.mingjie.jf.action.ForgetPassWordTwoSureAction;
import com.mingjie.jf.bean.ForgetPassWordTwoSureActionVo;
import com.mingjie.jf.bean.OtherSmsCodeActionVo;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.DES3EncryptAndDecrypt;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;

import butterknife.BindView;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 忘记密码2
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ChangePasswordTwoActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener
{

    private static String verfycode;//忘记密码第二步，短信验证码
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.input_resetpass_pass)
    EditText password;//新密码
    @BindView(R.id.visiable)
    ImageView mm;
    @BindView(R.id.input_resetpass_pass_affirm)
    EditText inputResetpassPassAffirm;//确认密码
    @BindView(R.id.visiable2)
    ImageView mm2;
    @BindView(R.id.input_resetpass_verifycode)
    EditText messagecode;//短信验证码
    @BindView(R.id.verificationcode)
    MateralButton getverificationcode;//获取短信验证码
    @BindView(R.id.sure_btn)
    MateralButton sureBtn;
    private TimeCount mTiemTimeCount;//定时器
    private String user_password, user_password_confirm;
    private final int GETCODE_TYPE = 0;
    private final int FOGETPSWD_TYPE = 1;
    String Vcode;//上个界面传过来的图形验证码
    String imgKey;//上个界面传过来的imagkey
    String phone;//上个界面传过来的电话号码
    String name;
    private boolean isHidden = true;

    private boolean secondhidden = true;
    private String psurepswd;
    private String psd;//
    private int type;

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                this.finish();
                break;
            case R.id.verificationcode://短信验证
                if (checkInfo(GETCODE_TYPE))
                {
                    getverificationcode.setEnabled(false);
                    OtherSmsCodeActionVo votwo = new OtherSmsCodeActionVo();
                    votwo.setPhone(phone);
                    new AbsSmsCodeAction(this)
                    {
                        public void setSmsCode(String smsCode)
                        {
                            mTiemTimeCount.start();//启动计时器
                            //验证码
                            verfycode = smsCode;
                            if (UrlUtil.isSmsDebug())
                            {
                                messagecode.setText(verfycode);
                            }
                        }
                        public void setSmsCodeError()
                        {
                            getverificationcode.setEnabled(true);
                        }
                    }.othersmsCodeAction(votwo);
                }
                break;
            case R.id.sure_btn://确认修改密码
                if (checkInfo(FOGETPSWD_TYPE))
                {
                    //                    UIUtils.showLoading(this);
                    ForgetPassWordTwoSureActionVo surebtn = new ForgetPassWordTwoSureActionVo();
                    surebtn.setPhone(phone);
                    //                    surebtn.setPassword(Vcode);
                    surebtn.setName(name);
                    surebtn.setPcode(verfycode);
                    surebtn.setPassword(DES3EncryptAndDecrypt.des3EncryptMode(password.getText().toString()));
                    surebtn.setRePassword(DES3EncryptAndDecrypt.des3EncryptMode(password.getText().toString()));

                    //todo 新密码
//                    CacheUtils.setString(ChangePasswordTwoActivity.this, Constants.PASSWORD, password.getText().toString());


//                    surebtn.setRePassword(password.getText().toString());
                    //imagkey
                    new ForgetPassWordTwoSureAction(this).forgetPassWordTwoSureAction(surebtn);
                }
                break;
            default:
        }
    }

    private boolean checkInfo(int fogetpswd_type)
    {
        user_password = password.getText().toString();//新密码
        user_password_confirm = inputResetpassPassAffirm.getText().toString();//确认密码
        verfycode = messagecode.getText().toString();//短信验证码
        if (TextUtils.isEmpty(user_password))
        {
            UIUtils.showToast(this, "请输入新密码");
            return false;
        }
        else if (user_password.length() < 6 || user_password.length() > 20)
        {
            UIUtils.showToast(this, "密码长度为6-20");
            return false;
        }
        else if (TextUtils.isEmpty(user_password_confirm))
        {
            UIUtils.showToast(this, "请输入确认密码");
            return false;
        }
        else if (!user_password_confirm.equals(user_password))
        {
            UIUtils.showToast(this, "两次输入的密码不一致");
            return false;
        }
        if (type == GETCODE_TYPE)
        {
            if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
            {
                UIUtils.showToast(ChangePasswordTwoActivity.this, "当前网络不可用，请重试！");
                return false;
            }
        }

        if (type == FOGETPSWD_TYPE)
        {
            if (TextUtils.isEmpty(verfycode))
            {
                UIUtils.showToast("请输入短信验证码");
                return false;
            }
            if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
            {
                UIUtils.showToast(ChangePasswordTwoActivity.this, "当前网络不可用，请重试！");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        switch (view.getId())
        {
            case R.id.input_resetpass_pass:
                if (b)
                {
                    mm.setVisibility(View.VISIBLE);
                }
                else
                {
                    mm.setVisibility(View.GONE);
                }
                break;
            case R.id.input_resetpass_pass_affirm:
                if (b)
                {
                    inputResetpassPassAffirm.setSelection(inputResetpassPassAffirm.length());
                    mm2.setVisibility(View.VISIBLE);
                }
                else
                {
                    mm2.setVisibility(View.GONE);
                }
                break;
            default:
        }

    }

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_change_password_two);
        // ButterKnife.bind(this);
        Vcode = getIntent().getStringExtra("Vcode");
        //        imgKey = getIntent().getStringExtra("imgKey");
        phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("name");
        sureBtn.setOnClickListener(this);
        ivLeftPublic.setOnClickListener(this);
        getverificationcode.setOnClickListener(this);
        password.setOnFocusChangeListener(this);

        setClickAndTouchListener(vkbd, password);
        setClickAndTouchListener(vkbd, inputResetpassPassAffirm);
        inputResetpassPassAffirm.setOnFocusChangeListener(this);

        //todo 修改密码以后 返回登陆的 还是 旧的密码
        CacheUtils.setString(ChangePasswordTwoActivity.this, Constants.PASSWORD, "");

        mm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (isHidden)
                {
                    //设置EditText文本为可见的
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mm.setImageResource(R.mipmap.visiable_open);
                }
                else
                {
                    //设置EditText文本为隐藏的
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mm.setImageResource(R.mipmap.visiable_close);
                }
                isHidden = !isHidden;
                password.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = password.getText();
                if (charSequence instanceof Spannable)
                {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

            }
        });
        mm2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (secondhidden)
                {
                    //设置EditText文本为可见的
                    inputResetpassPassAffirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mm2.setImageResource(R.mipmap.visiable_open);
                }
                else
                {
                    //设置EditText文本为隐藏的
                    inputResetpassPassAffirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mm2.setImageResource(R.mipmap.visiable_close);
                }
                secondhidden = !secondhidden;
                inputResetpassPassAffirm.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = inputResetpassPassAffirm.getText();
                if (charSequence instanceof Spannable)
                {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

            }
        });
        //计时器
        mTiemTimeCount = new TimeCount(60000, 1000, getverificationcode);
    }

    @Override
    protected void initData()
    {
        tvContentPublic.setText("重置密码");
        psd = password.getText().toString();
        psurepswd = inputResetpassPassAffirm.getText().toString();
    }
}

