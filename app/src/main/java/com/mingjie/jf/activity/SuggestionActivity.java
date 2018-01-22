package com.mingjie.jf.activity;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.action.FeedbackAction;
import com.mingjie.jf.bean.FeedbackVo;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;

import butterknife.BindView;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 建议
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class SuggestionActivity extends BaseActivity implements View.OnClickListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.edt_content)
    EditText edtContent;//提交内容
    @BindView(R.id.edt_title)
    EditText edtTitle;//反馈标题
    @BindView(R.id.edt_name)
    TextView edt_name;    //用户名
    @BindView(R.id.edt_phone)
    TextView edt_phone;   //用户电话
    private FeedbackAction feedbackaction;
    private String phone;
    private String realname;

    protected void initView()
    {
        setContentView(R.layout.activity_feedback);
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("意见反馈");
        btnSubmit.setOnClickListener(this);
    }

    protected void initData()
    {
        feedbackaction = new FeedbackAction(this);
        if(null != CacheUtils.getUserinfo(this))
        {
            edt_name.setText(CacheUtils.getUserinfo(this).getName());
            edt_phone.setText(CacheUtils.getUserinfo(this).getPhone());
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.btn_submit:
                //提交按钮
                if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                {
                    UIUtils.showToast(this, "当前网络不可用，请重试！");
                    return;
                }
                else
                {
                    SharedPreferences sp = this.getSharedPreferences("teldata", 0);
                    phone = sp.getString("tel", "");
                    realname = sp.getString("realname", edt_name.getText().toString());
                    FeedbackVo vo = new FeedbackVo();
                    vo.setContent(edtContent.getText().toString().trim());
                    vo.setTitle(edtTitle.getText().toString().trim());
                    vo.setType(2);
                    vo.setPhone(phone);
                    vo.setRealname(realname);
                    feedbackaction.feedback(vo);
                    break;
                }
        }

    }

}

