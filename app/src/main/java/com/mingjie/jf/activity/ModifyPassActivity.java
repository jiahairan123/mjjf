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
import com.mingjie.jf.action.ModifyPassAction;
import com.mingjie.jf.bean.ModifyPassActivityVo;
import com.mingjie.jf.bean.OtherSmsCodeActionVo;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.DES3EncryptAndDecrypt;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 修改登录密码
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ModifyPassActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener
{

    private static String safemodifyverfycode;
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    //    @BindView(R.id.input_modify_password)
    //    InputView inputModifyPassword;
    //    @BindView(R.id.input_modify_newpassword)
    //    InputView inputModifyNewpassword;
    //    @BindView(R.id.input_modify_affimpassword)
    //    InputView inputModifyAffimpassword;
    //    @BindView(R.id.input_modify_verifycode)
    //    InputView inputModifyVerifycode;
    //    @BindView(R.id.btn_modify_getcode)
    //    Button btnModifyGetcode;
    //    @BindView(R.id.btn_modify)
    //    Button btnModify;
    @BindView(R.id.input_modify_password)
    EditText inputModifyPassword;//原始登录密码
    @BindView(R.id.re_visiable)
    ImageView reVisiable;
    @BindView(R.id.input_modify_newpassword)
    EditText inputModifyNewpassword;//新的登录密码
    @BindView(R.id.re_visiable1)
    ImageView reVisiable1;
    @BindView(R.id.input_modify_affimpassword)//确认新的登录密码
            EditText inputModifyAffimpassword;
    @BindView(R.id.re_visiable2)
    ImageView reVisiable2;
    @BindView(R.id.input_modify_verifycode)
    EditText inputModifyVerifycode;//短信验证码
    @BindView(R.id.btn_modify_getcode)
    MateralButton btnModifyGetcode;//获取短信验证码按钮
    @BindView(R.id.btn_modify)
    MateralButton btnModify;//确定修改登录密码按钮

    private TimeCount mTiemTimeCount;
    UserVoValible user;
    private String mOldPassText, mNewPassText, mAffimNewPassText, mCheckCodeText;
    private boolean isGetCode;//是否点击验证码
    private boolean isHidden = false;

    //    @Override
    //    public void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_modify_pwd);
    //        ButterKnife.bind(this);
    //        user = CacheUtils.getUserinfo(this);
    //        initView();
    //        initData();
    //    }

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.bind(this);
        user = CacheUtils.getUserinfo(this);
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("修改登录密码");
        btnModifyGetcode.setOnClickListener(this);
        mTiemTimeCount = new TimeCount(60000, 1000, btnModifyGetcode);
        btnModify.setOnClickListener(this);
        inputModifyPassword.setOnFocusChangeListener(this);
        inputModifyAffimpassword.setOnFocusChangeListener(this);
        inputModifyNewpassword.setOnFocusChangeListener(this);

        setClickAndTouchListener(vkbd, inputModifyPassword);
        setClickAndTouchListener(vkbd, inputModifyAffimpassword);
        setClickAndTouchListener(vkbd, inputModifyNewpassword);
    }

    @Override
    protected void initData()
    {

    }

    //    protected void initView()
    //    {
    //        ivLeftPublic.setOnClickListener(this);
    //        tvContentPublic.setText("修改登录密码");
    //        btnModifyGetcode.setOnClickListener(this);
    //        mTiemTimeCount = new TimeCount(60000, 1000, btnModifyGetcode);
    //        btnModify.setOnClickListener(this);
    //        inputModifyPassword.setOnFocusChangeListener(this);
    //        inputModifyAffimpassword.setOnFocusChangeListener(this);
    //        inputModifyNewpassword.setOnFocusChangeListener(this);
    //    }
    //
    //    protected void initData()
    //    {
    //
    //    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.btn_modify_getcode://短信验证码
                isGetCode = true;
                if (checkData())
                {
                    if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                    {
                        UIUtils.showToast(this, "当前网络不可用，请重试！");
                        return;
                    }
                    else
                    {
                        btnModifyGetcode.setEnabled(false);
                        //                mTiemTimeCount.start();
                        OtherSmsCodeActionVo votwo = new OtherSmsCodeActionVo();
                        votwo.setPhone(user.getPhone());
                        new AbsSmsCodeAction(this)
                        {
                            public void setSmsCode(String smsCode)
                            {

                                mTiemTimeCount.start();
                                //验证码
                                safemodifyverfycode = smsCode;

                                if (UrlUtil.isSmsDebug())
                                {
                                    inputModifyVerifycode.setText(safemodifyverfycode);
                                }

                            }
                            public void setSmsCodeError()
                            {
                                btnModifyGetcode.setEnabled(true);
                            }
                        }.othersmsCodeAction(votwo);
                    }
                }

                break;
            case R.id.btn_modify:
                isGetCode = false;
                if (checkData())
                {
                    if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                    {
                        UIUtils.showToast(this, "当前网络不可用，请重试！");
                        return;
                    }
                    else
                    {
                        UIUtils.showLoading(this);
                        ModifyPassActivityVo vo = new ModifyPassActivityVo();
                        vo.setPcode(inputModifyVerifycode.getText().toString().trim());
//                        vo.setOldPassword(inputModifyPassword.getText().toString());
//                        vo.setNewPassword(inputModifyNewpassword.getText().toString());
//                        vo.setReNewPassword(inputModifyAffimpassword.getText().toString());
                        vo.setOldPassword(DES3EncryptAndDecrypt.des3EncryptMode(inputModifyPassword.getText().toString()));
                        vo.setNewPassword(DES3EncryptAndDecrypt.des3EncryptMode(inputModifyNewpassword.getText().toString()));
                        vo.setReNewPassword(DES3EncryptAndDecrypt.des3EncryptMode(inputModifyAffimpassword.getText().toString()));
                        new ModifyPassAction(this, mTiemTimeCount).modifyPassNextAction(vo);
                    }

                }
                break;
            default:
                break;
        }

    }

    private boolean checkData()
    {
        mOldPassText = inputModifyPassword.getText().toString();//原始密码
        mNewPassText = inputModifyNewpassword.getText().toString();//新密码
        mAffimNewPassText = inputModifyAffimpassword.getText().toString();//确认新密码
        mCheckCodeText = inputModifyVerifycode.getText().toString();//验证码

        if (TextUtils.isEmpty(mOldPassText))
        {
            CfLog.d("---" + mOldPassText);
            UIUtils.showToast(this, "原始登录密码不能为空!");
            return false;
        }
        else if (mOldPassText.length() < 6 || mOldPassText.length() > 20)
        {
            UIUtils.showToast(this, "输入的密码为6-20位");
            return false;
        }
        else if (TextUtils.isEmpty(mNewPassText))
        {
            UIUtils.showToast(this, "请输入新密码!");
            return false;
        }
        else if (mNewPassText.length() < 6 || mNewPassText.length() > 20)
        {
            UIUtils.showToast(this, "新密码为6-20位!");
            return false;
        }
        else if (!Pattern.compile("^(?![^0-9]+$)(?![^a-zA-Z]+$).+$").matcher(mNewPassText).matches())
        {
            UIUtils.showToast(this, "密码必须包含数字和字母!");
            return false;
        }
        else if (mOldPassText.equals(mNewPassText))
        {
            UIUtils.showToast(this, "原密码和新密码一致，请重新输入!");
            return false;
        }
        else if (TextUtils.isEmpty(mAffimNewPassText))
        {
            UIUtils.showToast(this, "请再次输入新密码!");
            return false;
        }
        else if (!mNewPassText.equals(mAffimNewPassText))
        {
            UIUtils.showToast(this, "两次设置的密码不一致!");
            return false;
        }
        else if (isGetCode)
        {

            return true;
        }
        else if (TextUtils.isEmpty(mCheckCodeText))
        {
            UIUtils.showToast(this, "请输入验证码!");
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        switch (view.getId())
        {
            case R.id.input_modify_password:
                if (b)
                {
                    inputModifyPassword.setSelection(inputModifyPassword.length());
                    reVisiable.setVisibility(View.VISIBLE);
                    reVisiable.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                            if (!isHidden)
                            {
                                //设置EditText文本为可见的
                                inputModifyPassword.setTransformationMethod(HideReturnsTransformationMethod
                                        .getInstance());
                                reVisiable.setImageResource(R.mipmap.visiable_open);
                            }
                            else
                            {
                                //设置EditText文本为隐藏的
                                inputModifyPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                reVisiable.setImageResource(R.mipmap.visiable_close);
                            }
                            isHidden = !isHidden;
                            inputModifyPassword.postInvalidate();
                            //切换后将EditText光标置于末尾
                            CharSequence charSequence = inputModifyPassword.getText();
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
            case R.id.input_modify_newpassword:
                if (b)
                {
                    inputModifyNewpassword.setSelection(inputModifyNewpassword.length());
                    reVisiable1.setVisibility(View.VISIBLE);
                    reVisiable1.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                            if (!isHidden)
                            {
                                //设置EditText文本为可见的
                                inputModifyNewpassword.setTransformationMethod(HideReturnsTransformationMethod
                                        .getInstance());
                                reVisiable1.setImageResource(R.mipmap.visiable_open);
                            }
                            else
                            {
                                //设置EditText文本为隐藏的
                                inputModifyNewpassword.setTransformationMethod(PasswordTransformationMethod
                                        .getInstance());
                                reVisiable1.setImageResource(R.mipmap.visiable_close);
                            }
                            isHidden = !isHidden;
                            inputModifyNewpassword.postInvalidate();
                            //切换后将EditText光标置于末尾
                            CharSequence charSequence = inputModifyNewpassword.getText();
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
                    reVisiable1.setVisibility(View.GONE);
                }
                break;
            case R.id.input_modify_affimpassword:
                if (b)
                {
                    inputModifyAffimpassword.setSelection(inputModifyAffimpassword.length());
                    reVisiable2.setVisibility(View.VISIBLE);
                    reVisiable2.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                            if (!isHidden)
                            {
                                //设置EditText文本为可见的
                                inputModifyAffimpassword.setTransformationMethod(HideReturnsTransformationMethod
                                        .getInstance());
                                reVisiable2.setImageResource(R.mipmap.visiable_open);
                            }
                            else
                            {
                                //设置EditText文本为隐藏的
                                inputModifyAffimpassword.setTransformationMethod(PasswordTransformationMethod
                                        .getInstance());
                                reVisiable2.setImageResource(R.mipmap.visiable_close);
                            }
                            isHidden = !isHidden;
                            inputModifyAffimpassword.postInvalidate();
                            //切换后将EditText光标置于末尾
                            CharSequence charSequence = inputModifyAffimpassword.getText();
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
                    reVisiable2.setVisibility(View.GONE);
                }
                break;
            default:
        }
    }
}
