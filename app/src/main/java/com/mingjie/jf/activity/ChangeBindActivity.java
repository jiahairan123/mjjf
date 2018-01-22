package com.mingjie.jf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;

import butterknife.BindView;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 绑定邮箱下一步
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ChangeBindActivity extends BaseActivity implements View.OnClickListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;

    protected void initView()
    {
        setContentView(R.layout.activity_change_bind_email);
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("修改邮箱");
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

            case R.id.btn_next:
                startActivity(new Intent(this, NextChangeBindActivity.class));
                break;
            default:
                break;
        }

    }
}

