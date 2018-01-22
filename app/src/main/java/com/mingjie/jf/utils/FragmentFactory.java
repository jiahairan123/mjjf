package com.mingjie.jf.utils;

import android.support.v4.util.SparseArrayCompat;

import com.mingjie.jf.fragment.BaseInvestFragment;
import com.mingjie.jf.fragment.EquitableAssignmentFragment;
import com.mingjie.jf.fragment.StandardInvestmentFragment;

/*
 *  @项目名：  CaffcooP2P 
 *  @包名：    caffp2p.android.utils
 *  @类名:    FragmentFactory
 *  @创建者:   陈小平
 *  @创建时间:  2016/01/20 14:25
 *  @描述：    TODO
 */
public class FragmentFactory
{
    private static SparseArrayCompat<BaseInvestFragment> mCaches = new SparseArrayCompat<BaseInvestFragment>();

    public static BaseInvestFragment getFragment(int position)
    {
        BaseInvestFragment fragment = mCaches.get(position);
        if (fragment != null)
        {
            //            LogUtils.d("使用" + position + "的缓存");
            // 直接使用缓存
            return fragment;
        }

        switch (position)
        {
            //    散标投资
            case 0:
                fragment = new StandardInvestmentFragment();
                break;
            case 1://债权转让
                fragment = new EquitableAssignmentFragment();
                break;
            default:
        }

        // 缓存
        mCaches.put(position, fragment);

        return fragment;
    }
}
