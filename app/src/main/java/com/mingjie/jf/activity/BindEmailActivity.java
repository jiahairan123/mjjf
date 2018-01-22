package com.mingjie.jf.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.action.BindEmailAction;
import com.mingjie.jf.bean.BindEmailActionVo;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;
import com.mingjie.jf.widgets.QuitDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 绑定邮箱
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class BindEmailActivity extends BaseActivity implements View.OnClickListener, QuitDialog.OnEnterListener,
        QuitDialog.OnCancelListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.et_input_modify_password)
    EditText etBindEmaile;//绑定邮箱第二步（输入的邮箱）
    @BindView(R.id.bind_button)
    MateralButton bindButton;
    // private String bindemaile;//输入的新邮箱

    UserVoValible user;
    private QuitDialog quitDialog;
    private String emaile2;
    private int BINDEMAILE=0;

    //
    //    @Override
    //    public void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_modify_email);
    //        ButterKnife.bind(this);
    //        user = CacheUtils.getUserinfo(this);
    //        initView();
    //        initData();
    //    }

    protected void initView()
    {
        setContentView(R.layout.activity_modify_email);
        ButterKnife.bind(this);
        user = CacheUtils.getUserinfo(this);
        ivLeftPublic.setOnClickListener(this);
        emaile2=getIntent().getStringExtra("emaile2");
        tvContentPublic.setText("绑定邮箱");
        // bindemaile = etBindEmaile.getText().toString();
        bindButton.setOnClickListener(this);
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
                if (checkdata())
                {
                    if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                    {
                        UIUtils.showToast(this, "当前网络不可用，请重试！");
                        return;
                    }
                    else
                    {
                        UIUtils.showLoading(this);
                        //绑定新邮箱
                        BindEmailActionVo votwo = new BindEmailActionVo();
                        votwo.setEmail(etBindEmaile.getText().toString());
//                        if (!TextUtils.isEmpty(emaile2))
//                        {
                            CfLog.i("---"+getIntent().getStringExtra("emaile2"));
//                            votwo.setSendEmailCode("1");
//                        }else {
                            votwo.setSendEmailCode("0");
//                        }
                        new BindEmailAction(this).bindEmaile(votwo);
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean checkdata()
    {
        if (etBindEmaile.getText().toString().trim().isEmpty())
        {
            UIUtils.showToast(this, "邮箱不能为空");
            return false;
        }
        return true;
    }

    //绑定成功。提醒前往qq邮箱确认
    public void show()
    {
        quitDialog = new QuitDialog(this, "温馨提示", "验证信息已发往你的邮箱，请前往验证", "返回账户", "确定");
        quitDialog.setEnterListener(this);
        quitDialog.setCancelListener(this);
        quitDialog.show();
    }

    @Override
    public void onEnterListener()
    {
        quitDialog.dismiss();
        this.finish();
    }

    @Override
    public void onCancelListener()
    {
        quitDialog.dismiss();
        this.finish();
    }
}



