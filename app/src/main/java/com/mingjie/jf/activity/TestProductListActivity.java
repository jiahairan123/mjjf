package com.mingjie.jf.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mingjie.jf.R;
import com.mingjie.jf.fragment.InvestFragment;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 测试标的列表
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-07 14:05
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class TestProductListActivity extends BaseActivity
{
    // private FrameLayout flContent;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_test_product_list);

        InvestFragment f = new InvestFragment();
        Bundle args = new Bundle();
        args.putBoolean("is_test",true);
        f.setArguments(args);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_content,f);
        fragmentTransaction.commit();
    }

    @Override
    protected void initData()
    {

    }
}
