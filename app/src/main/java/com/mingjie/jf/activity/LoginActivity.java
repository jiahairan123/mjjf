package com.mingjie.jf.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.action.LoginAction;
import com.mingjie.jf.bean.LoginActionVo;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.DES3EncryptAndDecrypt;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 登录界面
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener
{
    /**
     * 启动loginactivity方式，
     * START_TYEP_2MAIN:登录后去主页（默认）
     * START_TYPE_FINISH：关闭当前登录页面
     */
    public static final int START_TYEP_2MAIN = 0;
    public static final int START_TYPE_FINISH = 1;


    //启动activity方式的key值
    public static final String KEY_START_TYPE = "keyStartType";

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.input_username)
    EditText inputUsername;//用户名
    @BindView(R.id.input_password)
    EditText inputPassword;//密码
    @BindView(R.id.visiable)
    ImageView visiable;//密码是否可见
    @BindView(R.id.et_check)
    EditText etCheck;//验证码
    @BindView(R.id.code)
    ImageView code;//验证码显示
    @BindView(R.id.btn_login)
    MateralButton btnLogin;//登录
    @BindView(R.id.tv_forget_pass_login)
    LinearLayout tvForgetPassLogin;//忘记密码
    @BindView(R.id.tv_register)
    LinearLayout tvRegister;//用户注册
    @BindView(R.id.clean)
    ImageView clean;

    //    SharedPreferences sharedPreferences;// 取值

    private String userName;
    private String passWord;
    private String checkCode;

    private boolean isHidden = false;
    private LoginAction loginaction;
    private static RelativeLayout rlimagevcode;
    private static ImageView yzhcode;//错误次数五次，显示图形验证码
    private static View codepcture;
    private int startType;//启动loginactivity的type，决定关闭loginactivity后去哪

    // private Boolean logout;
    // private InputMethodManager mManager;

    public void hideBtn()
    {
        // 设置按钮不可见
        if (clean.isShown())
            clean.setVisibility(View.GONE);
    }

    public void showBtn()
    {
        // 设置按钮可见
        if (!clean.isShown())
            clean.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initView()
    {
        // mManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginaction = new LoginAction(this);
        // logout = getIntent().getBooleanExtra("logout", true);
        ivLeftPublic.setVisibility(View.VISIBLE);
        ivLeftPublic.setOnClickListener(this);
        tvForgetPassLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        rlimagevcode = (RelativeLayout) findViewById(R.id.rl_imagevcode);
        yzhcode = (ImageView) findViewById(R.id.code);
        codepcture = findViewById(R.id.codepc);
        yzhcode.setOnClickListener(this);
        clean.setOnClickListener(this);
        visiable.setOnClickListener(this);

        setClickAndTouchListener(vkbd, inputPassword);
//        inputPassword.setOnFocusChangeListener(this);

        inputUsername.setText(CacheUtils.getString(Constants.USER_NAME, ""));
        inputPassword.setText(DES3EncryptAndDecrypt.des3DecryptMode(CacheUtils.getString(Constants.PASSWORD, "")));



        // 当输入框状态改变时，会调用相应的方法
        TextWatcher tw = new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            // 在文字改变后调用
            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 0)
                {
                    hideBtn();// 隐藏按钮
                }
                else
                {
                    showBtn();// 显示按钮
                    inputPassword.setText("");
                }
            }
        };
        inputUsername.addTextChangedListener(tw);
        // 为输入框绑定一个监听文字变化的监听器
    }

    protected void initData()
    {
        tvContentPublic.setText("用户登录");
        startType = getIntent().getIntExtra(KEY_START_TYPE, START_TYEP_2MAIN);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_forget_pass_login:
                //忘记密码(跳转到重置密码)
                Intent intent = new Intent(this, ChangePasswordFirstActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register:
                //免费注册(跳转用户注册)
                Intent intentregister = new Intent(this, UserRegisterActivity.class);
                startActivity(intentregister);
                break;
            case R.id.btn_login://用户登录
                if (checkData())
                {
                    UIUtils.showLoading(this);
                    LoginActionVo vo = new LoginActionVo();

                    vo.setName(inputUsername.getText().toString());
                    vo.setPassword(DES3EncryptAndDecrypt.des3EncryptMode(inputPassword.getText().toString()));
                    vo.setVcode(etCheck.getText().toString());
                    // CfLog.d("---" + vo);
                    loginaction.loginAction(vo, startType);
                }
                break;
            case R.id.code:
                HttpManager.getInstance().displayImageCode(yzhcode, null, null);
                break;
            case R.id.iv_Left_public:
                //                if (startType == START_TYEP_2MAIN)
                //                {
                //                    startActivity(new Intent(this, MainActivity.class));
                //                }
                this.finish();
                break;
            case R.id.clean:
                inputUsername.setText("");// 设置输入框内容为空
                break;
            case R.id.visiable:

                if (!isHidden)
                {
                    //设置EditText文本为可见的
                    inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visiable.setImageResource(R.mipmap.visiable_open);
                }
                else
                {
                    //设置EditText文本为隐藏的
                    inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visiable.setImageResource(R.mipmap.visiable_close);
                }
                isHidden = !isHidden;
                inputPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = inputPassword.getText();
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

    private boolean checkData()
    {
        userName = inputUsername.getText().toString();
        passWord = inputPassword.getText().toString();
        checkCode = etCheck.getText().toString();

        if (TextUtils.isEmpty(userName))
        {
            UIUtils.showToast(this, "用户名不能为空");
            return false;
        }
        else if (TextUtils.isEmpty(passWord))
        {
            UIUtils.showToast(this, "密码不能为空");
            return false;
        }
        if (rlimagevcode.getVisibility() == View.VISIBLE && TextUtils.isEmpty(checkCode))
        {
            UIUtils.showToast(this, "验证码不能为空");
            return false;
        }
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(LoginActivity.this, "当前网络不可用，请重试！");
            return false;
        }
        return true;
    }

    public static void wrongaccount()
    {
        rlimagevcode.setVisibility(View.VISIBLE);
        codepcture.setVisibility(View.VISIBLE);
        HttpManager.getInstance().displayImageCode(yzhcode, null, null);
    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        switch (view.getId())
        {
            case R.id.input_password:
                //获取到焦点
                if (b)
                {
//                    inputPassword.setSelection(inputPassword.length());
                    visiable.setVisibility(View.VISIBLE);
                    visiable.setOnClickListener(this);
                    //                    visiable.setOnClickListener(new View.OnClickListener()
                    //                    {
                    //                        @Override
                    //                        public void onClick(View v)
                    //                        {
                    //
                    //                            if (!isHidden)
                    //                            {
                    //                                //设置EditText文本为可见的
                    //                                inputPassword.setTransformationMethod
                    // (HideReturnsTransformationMethod.getInstance());
                    //                                visiable.setImageResource(R.mipmap.visiable_open);
                    //                            }
                    //                            else
                    //                            {
                    //                                //设置EditText文本为隐藏的
                    //                                inputPassword.setTransformationMethod
                    // (PasswordTransformationMethod.getInstance());
                    //                                visiable.setImageResource(R.mipmap.visiable_close);
                    //                            }
                    //                            isHidden = !isHidden;
                    //                            inputPassword.postInvalidate();
                    //                            //切换后将EditText光标置于末尾
                    //                            CharSequence charSequence = inputPassword.getText();
                    //                            if (charSequence instanceof Spannable)
                    //                            {
                    //                                Spannable spanText = (Spannable) charSequence;
                    //                                Selection.setSelection(spanText, charSequence.length());
                    //                            }
                    //                        }
                    //                    });
                }
                else
                {
                    visiable.setVisibility(View.GONE);
                }
                break;
            default:
        }
    }

    /**
     * 所有子类
     * 点击键盘外空白,键盘消失
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            hideKeyboard();
            vkbd.setVisibility(View.GONE);
        }
        return super.onTouchEvent(event);
    }
}
