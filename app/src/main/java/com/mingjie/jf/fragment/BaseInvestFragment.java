package com.mingjie.jf.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mingjie.jf.widgets.LoadingPager;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Map;



/*
 *  @项目名：  CaffcooP2P 
 *  @包名：    caffp2p.android.ui.fragment
 *  @类名:
 *  @创建者:   陈小平
 *  @创建时间:  2016/01/20 15:16
 *  @描述：    TODO
 */
public abstract class BaseInvestFragment extends Fragment {
    private LoadingPager mLoadingPager;

    protected Context context;

    @Override
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (mLoadingPager == null)
        {
            mLoadingPager = new LoadingPager(getContext()) {

                @Override
                protected View initSuccessView()
                {
                    return onLoadSuccessView();
                }

                @Override
                protected LoadedResult onLoadData()
                {
                    return onLoadingData();
                }
            };
        }
        else
        {
            ViewParent parent = mLoadingPager.getParent();
            if (parent != null && parent instanceof ViewGroup)
            {
                ((ViewGroup) parent).removeView(mLoadingPager);
            }
        }

        return mLoadingPager;
    }

    protected LoadingPager.LoadedResult checkData(Object data)
    {
        if (data == null) { return LoadingPager.LoadedResult.EMPTY; }

        if (data instanceof List)
        {
            if (((List) data).size() == 0) { return LoadingPager.LoadedResult.EMPTY; }
        }

        if (data instanceof Map)
        {
            if (((Map) data).size() == 0) { return LoadingPager.LoadedResult.EMPTY; }
        }

        return LoadingPager.LoadedResult.SUCCESS;
    }

    public void loadData()
    {
        if (mLoadingPager != null)
        {
            mLoadingPager.loadData();
        }
    }

    protected abstract View onLoadSuccessView();
    protected abstract void  closeRefresh();
    protected abstract LoadingPager.LoadedResult onLoadingData();
}
