package com.mingjie.jf.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mingjie.jf.R;

/**
 * Created by jiahairan on 2017/12/26 09.
 */

public class ZiXunFuWuActivity extends BaseActivity implements View.OnClickListener {

    private TextView contentTv, tvTitlePublic;
    private ImageView ivLeftBack;
    private ScrollView scrollView;
    @Override
    protected void initView() {

        setContentView(R.layout.activity_zixunfuwu);
        scrollView = (ScrollView) findViewById(R.id.srcoll_zixunfuwu_activity);
        ivLeftBack = (ImageView) findViewById(R.id.iv_Left_public);
        tvTitlePublic = (TextView) findViewById(R.id.tv_content_public);
        tvTitlePublic.setText("出借人咨询服务协议");
        ivLeftBack.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_Left_public :
                finish();
                break;
        }
    }
}
