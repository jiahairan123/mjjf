package com.mingjie.jf.activity;

import android.view.View;
import android.widget.EditText;

import com.mingjie.jf.R;
import com.mingjie.jf.action.FeedbackAction;
import com.mingjie.jf.bean.FeedbackVo;
import com.mingjie.jf.utils.CfLog;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity
{

    //    @BindView(R.id.btn_submit)
    //    View btn_submit;
    @BindView(R.id.edt_title)
    EditText edt_title;
    @BindView(R.id.edt_content)
    EditText edt_content;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_feedback);
    }

    @Override
    protected void initData()
    {

    }

    @OnClick({R.id.btn_submit})
    public void doClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_submit:
                FeedbackVo vo = new FeedbackVo();
                vo.setTitle(edt_title.getText().toString().trim());
                vo.setContent(edt_content.getText().toString().trim());
                vo.setType(2);
                //                vo.setFrom("android");
                //                vo.setVersion("1.0");
                new FeedbackAction(this).feedback(vo);
                break;

            default:
                CfLog.d();
                break;

        }
    }

}
