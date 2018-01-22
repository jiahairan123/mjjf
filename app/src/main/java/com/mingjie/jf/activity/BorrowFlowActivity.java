package com.mingjie.jf.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.view.MateralButton;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： borrow_flow activity
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-22 15:58
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class BorrowFlowActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView iv_Left_public;//返回
    private TextView tv_content_public;//中间内容
    private MateralButton btn_apply;   // 填写借款申请

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_borrow_flow);
        iv_Left_public = (ImageView)findViewById(R.id.iv_Left_public);
        tv_content_public = (TextView)findViewById(R.id.tv_content_public);
        btn_apply = (MateralButton)findViewById(R.id.btn_apply);

    }

    @Override
    protected void initData()
    {
        tv_content_public.setText("借款流程");
        iv_Left_public.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.btn_apply:
                finish();
                break;
            default:
                break;
        }
    }
}
