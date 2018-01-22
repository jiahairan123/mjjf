package com.mingjie.jf.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.action.AbsSmsCodeAction;
import com.mingjie.jf.action.NextSafePhoneAction;
import com.mingjie.jf.bean.OtherSmsCodeActionVo;
import com.mingjie.jf.bean.SafePhoneActionVo;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;
import com.mingjie.jf.widgets.QuitDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 修改手机下一步
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class NextSafePhoneActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener,
        QuitDialog.OnEnterListener, QuitDialog.OnCancelListener
{

    String verfycode;//短信验证码
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.btn_next)
    MateralButton btnnext;
    @BindView(R.id.button)
    MateralButton surebtn;
    @BindView(R.id.new_phone)
    EditText newPhone;//新手机
    @BindView(R.id.phone_smscode)
    EditText phoneSmscode;//手机短信验证码
    @BindView(R.id.clean)
    ImageView clean;

    private TimeCount mTiemTimeCount;
    private UserVoValible user;
    private boolean isHidden = false;
    private QuitDialog quitDialog;

    //    @Override
    //    public void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_modify_celphone_next);
    //        ButterKnife.bind(this);
    //        user = CacheUtils.getUserinfo(this);
    //        initView();
    //        initData();
    //    }

    protected void initView()
    {
        setContentView(R.layout.activity_modify_celphone_next);
        ButterKnife.bind(this);
        user = CacheUtils.getUserinfo(this);
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("修改手机");
        btnnext.setOnClickListener(this);
        surebtn.setOnClickListener(this);
        newPhone.setOnFocusChangeListener(this);
        clean.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //                hideBtn();// 隐藏按钮
                newPhone.setText("");// 设置输入框内容为空
            }
        });
        //计时器
        mTiemTimeCount = new TimeCount(60000, 1000, surebtn);

    }

    protected void initData()
    {

    }

    //    public void hideBtn()
    //    {
    //        // 设置按钮不可见
    //        if (clean.isShown())
    //            clean.setVisibility(View.GONE);
    //    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;

            case R.id.btn_next://点击下一步
                if (checkdata())
                {
                    quitDialog = new QuitDialog(this, "提示", "请确保手机号码与E账户行方预留手机号码一致？", "取消", "确定");
                    quitDialog.setEnterListener(this);
                    quitDialog.setCancelListener(this);
                    quitDialog.show();

                }
                break;
            case R.id.button:
                //                mTiemTimeCount.start();//启动计时器
                if (newPhone.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(this, "手机不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!StringUtils.isPhoneNumber(newPhone.getText().toString().trim()))
                {
                    UIUtils.showToast(this, "请输入合法的手机号码!");
                    return;
                }
                if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                {
                    UIUtils.showToast(this, "当前网络不可用，请重试！");
                    return;
                }
                else
                {
                    surebtn.setEnabled(false);
                    OtherSmsCodeActionVo votwo = new OtherSmsCodeActionVo();
                    votwo.setPhone(newPhone.getText().toString());
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
                                    phoneSmscode.setText(verfycode);
                                }
                            }
                        }
                        public void setSmsCodeError()
                        {
                            surebtn.setEnabled(true);
                        }
                    }.othersmsCodeAction(votwo);
                }
                break;
            default:
                break;
        }

    }

    private boolean checkdata()
    {
        if (newPhone.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this, "手机不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!StringUtils.isPhoneNumber(newPhone.getText().toString().trim()))
        {
            UIUtils.showToast(this, "请输入合法的手机号码!");
            return false;
        }
        else if (newPhone.getText().toString().trim().isEmpty())
        {
            UIUtils.showToast(this, "手机号码不能为空!");
            return false;
        }
        //        if (!(verfycode+"").equals(phoneSmscode.getText().toString().trim()) && !phoneSmscode.getText()
        // .toString().trim()
        //                .trim().isEmpty())
        //        {
        //            UIUtils.showToast(this, "验证码错误!");
        //            return false;
        //        }
        else if (phoneSmscode.getText().toString().trim().isEmpty())
        {
            UIUtils.showToast(this, "验证码不能为空!");
            return false;
        }
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        switch (view.getId())
        {
            case R.id.new_phone:
                //获取到焦点
                if (b)
                {
                    clean.setVisibility(View.VISIBLE);
                }
                else
                {
                    clean.setVisibility(View.GONE);
                }
                break;
            default:
        }
    }

    @Override
    public void onCancelListener()
    {
        quitDialog.dismiss();
    }

    @Override
    public void onEnterListener()
    {
        UIUtils.showLoading(this);
        SafePhoneActionVo safevo = new SafePhoneActionVo();
        //                    safevo.setToken(user.getToken());
        safevo.setNewPhone(newPhone.getText().toString());
        safevo.setPcode(phoneSmscode.getText().toString());
        new NextSafePhoneAction(this, mTiemTimeCount).safePhonenextAction(safevo);

    }
}



