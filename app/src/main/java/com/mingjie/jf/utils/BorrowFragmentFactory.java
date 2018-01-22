package com.mingjie.jf.utils;

import android.support.v4.util.SparseArrayCompat;

import com.mingjie.jf.fragment.BaseInvestFragment;
import com.mingjie.jf.fragment.BorrowEndFragment;
import com.mingjie.jf.fragment.CheckFragment;
import com.mingjie.jf.fragment.CheckPassFragment;
import com.mingjie.jf.fragment.RepayFragment;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.utils
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我的借款 fragment 工厂
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-27 16:10
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class BorrowFragmentFactory
{
    private static SparseArrayCompat<BaseInvestFragment> mCaches = new SparseArrayCompat<BaseInvestFragment>();

    public static BaseInvestFragment getFragment(int position)
    {
        BaseInvestFragment fragment = mCaches.get(position);
        if (fragment != null)
        {
            // 直接使用缓存
            return fragment;
        }

        switch (position)
        {
            //    还款中
            case 0:
                fragment = new RepayFragment();
                break;
            case 1://审核中
                fragment = new CheckFragment();
                break;
            case 2: //审核通过
                fragment = new CheckPassFragment();
                break;
            case 3: //已完结
                fragment = new BorrowEndFragment();
                break;
            default:
                break;
        }

        // 缓存
        mCaches.put(position, fragment);

        return fragment;
    }

    /**
     * 清空mCaches中的fragment
     * 防止切换账户时，Activity销毁，fragment没销毁，导致数据不更新
     * 该方法这里针对给MyBorrowActivity调用
     */
    public static void clearCache()
    {
        mCaches.clear();
    }
}
