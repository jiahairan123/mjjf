package com.mingjie.jf.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.action.AbsSmsCodeAction;
import com.mingjie.jf.action.ConfirmBindAction;
import com.mingjie.jf.bean.OtherSmsCodeActionVo;
import com.mingjie.jf.bean.SafePhoneActionVo;
import com.mingjie.jf.bean.UserVoValible;
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
 * <p>描   述： 验证邮箱
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ConfirmBindActivity extends BaseActivity implements View.OnClickListener
{

    String verfycode;//短信验证码
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.btn_modify_getcode)
    Button btnModifyGetcode;//绑定邮箱第一步获取短信验证码
    @BindView(R.id.bind_button)
    MateralButton bindButton;//第一步验证邮箱
    @BindView(R.id.et_input_modify_password)
    EditText etInputCode;//短信验证码
    @BindView(R.id.tel)
    TextView tel;
    private TimeCount mTiemTimeCount;
    // String emaile;

    UserVoValible user;

    //    @Override
    //    public void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_confirm_bind_email);
    //        ButterKnife.bind(this);
    //        //        user = CacheUtils.getUserinfo(this);
    //
    //        initView();
    //        initData();
    //    }

    protected void initView()
    {
        setContentView(R.layout.activity_confirm_bind_email);
        ButterKnife.bind(this);
        ivLeftPublic.setOnClickListener(this);
        bindButton.setOnClickListener(this);
        tvContentPublic.setText("修改邮箱");
        //        tel.setText(user.getPhone().substring(0,3)+"****"+user.getPhone().substring(7,user.getPhone()
        // .length()));
        if (getIntent().getStringExtra("phone") != null)
        {
            tel.setText(getIntent().getStringExtra("phone").substring(0, 3) + "****" + getIntent().getStringExtra
                    ("phone").substring(7, getIntent().getStringExtra("phone").length()));
        }
        btnModifyGetcode.setOnClickListener(this);
        //计时器
        mTiemTimeCount = new TimeCount(60000, 1000, btnModifyGetcode);

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
            case R.id.bind_button:
                if (etInputCode.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                {
                    UIUtils.showToast(this, "当前网络不可用，请重试！");
                    return;
                }
                else
                {
                    SafePhoneActionVo safevo = new SafePhoneActionVo();
                    safevo.setPcode(etInputCode.getText().toString());
                    new ConfirmBindAction(this, mTiemTimeCount).safebindnextAction(safevo);
                }
                break;
            case R.id.btn_modify_getcode://获取短信验证码
                if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                {
                    UIUtils.showToast(this, "当前网络不可用，请重试！");
                    return;
                }
                btnModifyGetcode.setEnabled(false);
                //                mTiemTimeCount.start();//启动计时器
                OtherSmsCodeActionVo votwo = new OtherSmsCodeActionVo();
                votwo.setPhone(getIntent().getStringExtra("phone"));
                new AbsSmsCodeAction(this)
                {
                    public void setSmsCode(String smsCode)
                    {
                        mTiemTimeCount.start();
                        if (!smsCode.isEmpty())
                        {
                            //验证码
                            verfycode = smsCode;
                            if (UrlUtil.isSmsDebug())
                            {
                                etInputCode.setText(verfycode);
                            }
                        }
                    }
                    public void setSmsCodeError()
                    {
                        btnModifyGetcode.setEnabled(true);
                    }
                }.othersmsCodeAction(votwo);
                break;
            default:
                break;
        }
    }
}

