package com.mingjie.jf.activity;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mingjie.jf.R;

/**
 * Created by jiahairan on 2017/12/25 15.
 *
 * 借款协议
 */

public class StaticProctolActivity extends BaseActivity implements View.OnClickListener {
    private TextView contentTv, tvTitlePublic;
    private ImageView ivLeftBack;
    private ScrollView scrollView;


    @Override
    protected void initView() {

        setContentView(R.layout.activity_proctol_static);
        scrollView = (ScrollView) findViewById(R.id.aps_scroll_view);
        contentTv = (TextView) findViewById(R.id.tv_static_content);
        tvTitlePublic = (TextView) findViewById(R.id.tv_content_public);
        tvTitlePublic.setText(getIntent().getStringExtra("title"));
        contentTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        ivLeftBack = (ImageView) findViewById(R.id.iv_Left_public);
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
