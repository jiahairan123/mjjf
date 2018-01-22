package com.mingjie.jf.activity;

import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.action.AbsSmsCodeAction;
import com.mingjie.jf.action.SafePhoneAction;
import com.mingjie.jf.bean.OtherSmsCodeActionVo;
import com.mingjie.jf.bean.SafePhoneActionVo;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.DES3EncryptAndDecrypt;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 忘绑定手机
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class SafePhoneActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener
{
    String verfycode;//短信验证码
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.btn_next)
    MateralButton btnnext;

    UserVoValible user;
    @BindView(R.id.telephone)
    TextView telephone;//原手机号码
    @BindView(R.id.change_button)
    Button changeButton;//修改手机第一步短信验证码
    @BindView(R.id.et_safephone)
    EditText etSafephone;
    @BindView(R.id.edt_user_register_password)
    EditText edtUserRegisterPassword;
    @BindView(R.id.re_visiable)
    ImageView reVisiable;
    private TimeCount mTiemTimeCount;
    private String loginpswd;//登录密码
    private boolean isHidden = false;

    String safemes, passWord;

    //    @Override
    //    public void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_modify_celphone);
    //        ButterKnife.bind(this);
    //        user = CacheUtils.getUserinfo(this);
    //
    //        initView();
    //        initData();
    //
    //    }

    protected void initView()
    {
        setContentView(R.layout.activity_modify_celphone);
        ButterKnife.bind(this);
        user = CacheUtils.getUserinfo(this);
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("修改手机");
        btnnext.setOnClickListener(this);
        edtUserRegisterPassword.setOnFocusChangeListener(this);
        if (getIntent().getStringExtra("phone") != null)
        {
            telephone.setText(getIntent().getStringExtra("phone").substring(0, 3) + "****" + getIntent()
                    .getStringExtra("phone").substring(7, getIntent().getStringExtra("phone").length()));
        }
        changeButton.setOnClickListener(this);
        //计时器
        mTiemTimeCount = new TimeCount(60000, 1000, changeButton);
        setClickAndTouchListener(vkbd, edtUserRegisterPassword);
    }

    protected void initData()
    {

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.btn_next://点击下一步
                if (checkData())
                {
                    if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                    {
                        UIUtils.showToast(this, "当前网络不可用，请重试！");
                        return;
                    }
                    else
                    {
                        SafePhoneActionVo safevo = new SafePhoneActionVo();
                        safevo.setToken(user.getToken());
                        safevo.setPcode(etSafephone.getText().toString());
//                        safevo.setPassword(edtUserRegisterPassword.getText().toString());
                        safevo.setPassword(DES3EncryptAndDecrypt.des3EncryptMode(edtUserRegisterPassword.getText().toString()));
                        new SafePhoneAction(this, mTiemTimeCount).safePhonenextAction(safevo);
                    }
                }
                break;
            case R.id.change_button:
                changeButton.setEnabled(false);
                if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                {
                    UIUtils.showToast(SafePhoneActivity.this, "当前网络不可用，请重试！");
                    return;
                }
                else
                {
                    changeButton.setEnabled(false);
                    OtherSmsCodeActionVo votwo = new OtherSmsCodeActionVo();
                    votwo.setPhone(getIntent().getStringExtra("phone"));
                    new AbsSmsCodeAction(this, mTiemTimeCount)
                    {
                        public void setSmsCode(String smsCode)
                        {
                            mTiemTimeCount.start();//启动计时器
                            if (!smsCode.isEmpty())
                            {
                                //验证码
                                verfycode = smsCode;

                                if (UrlUtil.isSmsDebug())
                                {
                                    etSafephone.setText(verfycode);
                                }
                            }
                        }
                        public void setSmsCodeError()
                        {
                            changeButton.setEnabled(true);
                        }
                    }.othersmsCodeAction(votwo);
                    break;
                }
        }

    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        switch (view.getId())
        {
            case R.id.edt_user_register_password:
                //获取到焦点
                if (b)
                {
                    reVisiable.setVisibility(View.VISIBLE);
                    reVisiable.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            if (!isHidden)
                            {
                                //设置EditText文本为可见的
                                edtUserRegisterPassword.setTransformationMethod(HideReturnsTransformationMethod
                                        .getInstance());
                                reVisiable.setImageResource(R.mipmap.visiable_open);
                            }
                            else
                            {
                                //设置EditText文本为隐藏的
                                edtUserRegisterPassword.setTransformationMethod(PasswordTransformationMethod
                                        .getInstance());
                                reVisiable.setImageResource(R.mipmap.visiable_close);
                            }
                            isHidden = !isHidden;
                            edtUserRegisterPassword.postInvalidate();
                            //切换后将EditText光标置于末尾
                            CharSequence charSequence = edtUserRegisterPassword.getText();
                            if (charSequence instanceof Spannable)
                            {
                                Spannable spanText = (Spannable) charSequence;
                                Selection.setSelection(spanText, charSequence.length());
                            }
                        }
                    });
                }
                else
                {
                    reVisiable.setVisibility(View.GONE);
                }
                break;
            default:
        }
    }

    private boolean checkData()
    {
        safemes = etSafephone.getText().toString();
        passWord = edtUserRegisterPassword.getText().toString();
        if (TextUtils.isEmpty(safemes))
        {
            UIUtils.showToast(this, "验证码不能为空");
            return false;
        }
        else if (TextUtils.isEmpty(passWord))
        {
            UIUtils.showToast(this, "登录密码不能为空");
            return false;
        }
        //        if (!(verfycode+"").equals(etSafephone.getText().toString()))
        //        {
        //            UIUtils.showToast(this, "验证码错误");
        //            return false;
        //        }

        return true;
    }
}



