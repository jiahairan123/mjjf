package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.CashBean;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.View
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-06 14:09
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class Popup2Holder extends BaseHolder<CashBean>
{

    private TextView tvAmount;//金额
    private TextView tvStartAmount;//起投金额
    private TextView tvLoseTime;//失效日期

    public Popup2Holder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View mRootView = View.inflate(mContext, R.layout.holder_popupwindow2, null);
        tvAmount = (TextView) mRootView.findViewById(R.id.tv_amount);
        tvStartAmount = (TextView) mRootView.findViewById(R.id.tv_start_amount);
        tvLoseTime = (TextView) mRootView.findViewById(R.id.tv_lose_time);
        return mRootView;
    }

    @Override
    protected void refreshUI(CashBean data, int position)
    {
        if (4 == data.getCouponType())
        {
            tvAmount.setText(String.format(mContext.getString(R.string.jiaxi), StringUtils.double2Str(data.getAmount())));
        }
        else if (1 == data.getCouponType())
        {
            tvAmount.setText(String.format(mContext.getString(R.string.yuan_money), StringUtils.dou2Str(data.getAmount())));
        }
        else
        {
            tvAmount.setText(String.format(mContext.getString(R.string.yuan2), StringUtils.dou2Str(data.getAmount())));
        }
        tvStartAmount.setText(String.format(mContext.getString(R.string.yuan2),
                StringUtils.dou2Str(data.getMinInvestmentAmount())));
        tvLoseTime.setText(TimeFormatUtil.data2StrTime4(data.getExpireTime()));
    }
//        tvAmount.setText(String.format(mContext.getString(R.string.yuan2), StringUtils.dou2Str(data.getAmount())));
//        tvStartAmount.setText(String.format(mContext.getString(R.string.yuan2),
//                StringUtils.dou2Str(data.getMinInvestmentAmount())));
//        tvLoseTime.setText(TimeFormatUtil.data2StrTime4(data.getExpireTime()));
//    }
}
