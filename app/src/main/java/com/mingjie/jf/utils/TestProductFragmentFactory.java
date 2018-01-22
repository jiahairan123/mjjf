package com.mingjie.jf.utils;

import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;

import com.mingjie.jf.fragment.BaseInvestFragment;
import com.mingjie.jf.fragment.EquitableAssignmentFragment;
import com.mingjie.jf.fragment.StandardInvestmentFragment;

public class TestProductFragmentFactory
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
                Bundle args = new Bundle();
                args.putBoolean("is_test",true);
                fragment.setArguments(args);
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
