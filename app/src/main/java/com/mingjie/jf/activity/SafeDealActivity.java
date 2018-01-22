package com.mingjie.jf.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;

import butterknife.BindView;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述：安全保障
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-20 11:39
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class SafeDealActivity extends BaseActivity implements View.OnClickListener
{
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;//返回
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;//标题

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_safe);
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("安全保障");
    }

    @Override
    protected void initData()
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            default:
                break;
        }
    }
}
