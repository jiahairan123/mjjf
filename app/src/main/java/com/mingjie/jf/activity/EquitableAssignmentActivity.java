package com.mingjie.jf.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;

import butterknife.BindView;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 债权转让界面
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-03 11:54
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class EquitableAssignmentActivity extends BaseActivity implements View.OnClickListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;


    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_assignment);
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("转让信息");
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
            default:
                break;
        }
    }

}