package com.mingjie.jf.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.mingjie.jf.R;
import com.mingjie.jf.widgets.InvestIndicator;
import com.mingjie.jf.widgets.LoadingPager;
import com.mingjie.jf.widgets.RefreshLayout;

import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述：自动投标
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-03 15:53
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class AutoSetFragment extends BaseInvestFragment implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener, InvestIndicator.OnTabChangeListener, AdapterView.OnItemClickListener {


    @Override
    protected View onLoadSuccessView() {
        View mView = View.inflate(getContext(), R.layout.fragment_auto_inverst_show, null);
        ButterKnife.bind(getContext(), mView);
        return mView;
    }

    @Override
    protected void closeRefresh() {

    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        return LoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void setOnTabChangeListener(int position, boolean isUpSoft) {

    }
}
