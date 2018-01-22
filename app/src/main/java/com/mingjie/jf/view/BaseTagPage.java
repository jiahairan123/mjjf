package com.mingjie.jf.view;

import android.view.View;

import com.mingjie.jf.activity.LoanInfoActivity;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.fragment
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 借款信息父页面
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-18 15:59
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public abstract class BaseTagPage
{

    private LoanInfoActivity activity;
    private View mRootView;

    public BaseTagPage(LoanInfoActivity activity)
    {
        this.activity = activity;
        mRootView = initView();
        initEvent();
    }

    public void initEvent()
    {

    }

    private View initView()
    {
        View contentView = initContentView(activity);
        return contentView;
    }

    protected abstract View initContentView(LoanInfoActivity activity);


    /**
     * 在页面显示的时候在调用
     */
    public void initData()
    {

    }

    public View getRoot()
    {
        return mRootView;
    }

}
