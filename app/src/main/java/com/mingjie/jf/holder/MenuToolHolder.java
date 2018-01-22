package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.CalculateBean;
import com.mingjie.jf.utils.StringUtils;

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
public class MenuToolHolder extends BaseHolder<CalculateBean>
{

    private TextView mTvPeriods;
    private TextView mTvBenjin;
    private TextView mTvLixi;
    private TextView mTvReturnMoney;

    public MenuToolHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View mView = View.inflate(mContext, R.layout.holder_menu_tool, null);
        mTvPeriods = (TextView) mView.findViewById(R.id.tv_periods);
        mTvBenjin = (TextView) mView.findViewById(R.id.tv_benjin);
        mTvLixi = (TextView) mView.findViewById(R.id.tv_lixi);
        mTvReturnMoney = (TextView) mView.findViewById(R.id.tv_returnMoney);

        return mView;
    }

    @Override
    protected void refreshUI(CalculateBean data, int position)
    {

        int count = position + 1;
        mTvPeriods.setText(String.valueOf(count));
        mTvBenjin.setText(String.valueOf(StringUtils.dou2Str(Double.parseDouble(data.mBenjin))));
        mTvLixi.setText(String.valueOf(StringUtils.dou2Str(Double.parseDouble(data.mLixi))));
        mTvReturnMoney.setText(String.valueOf(StringUtils.dou2Str(Double.parseDouble(data.mTotal))));
    }
}
