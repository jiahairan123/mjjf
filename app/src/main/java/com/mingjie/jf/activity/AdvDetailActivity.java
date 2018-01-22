package com.mingjie.jf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 开通存管详情页面
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-11-09 18:13
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AdvDetailActivity extends BaseActivity implements View.OnClickListener
{
    // private int screenWidth;
    private TextView tvContentPublic;
    private ImageView iv01;    // 开通关联个人E账户
    private ImageView iv02;    // 充值
    private ImageView iv03;    // 提现
    private ImageView iv04;    // 投资

    private ImageView back;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_adv_detail);
        tvContentPublic = (TextView) findViewById(R.id.tv_content_public);
        back = (ImageView) findViewById(R.id.iv_Left_public);
        iv01 = (ImageView) findViewById(R.id.iv_01);
        iv02 = (ImageView) findViewById(R.id.iv_02);
        iv03 = (ImageView) findViewById(R.id.iv_03);
        iv04 = (ImageView) findViewById(R.id.iv_04);
        iv01.setOnClickListener(this);
        iv02.setOnClickListener(this);
        iv03.setOnClickListener(this);
        iv04.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    protected void initData()
    {
        tvContentPublic.setText("存管指引");

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_01:
                Intent intent1 = new Intent(this , AdvFlowActivity.class);
                intent1.putExtra("type" , 1);
                startActivity(intent1);
                break;
            case R.id.iv_02:
                Intent intent2 = new Intent(this , AdvFlowActivity.class);
                intent2.putExtra("type" , 2);
                startActivity(intent2);
                break;
            case R.id.iv_03:
                Intent intent3 = new Intent(this , AdvFlowActivity.class);
                intent3.putExtra("type" , 3);
                startActivity(intent3);
                break;
            case R.id.iv_04:
                Intent intent4 = new Intent(this , AdvFlowActivity.class);
                intent4.putExtra("type" , 4);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }
}
