package com.mingjie.jf.adapter;

import android.content.Context;
import android.widget.ListView;

import com.mingjie.jf.bean.InvestList;
import com.mingjie.jf.holder.BaseHolder;
import com.mingjie.jf.holder.StandardInvestmentHolder;

import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.adapter
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 散标投资adapter
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-05 18:18
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class StandardInvestmentAdapter extends BaseListViewAdapter<InvestList.DataBean.ListBean>
{
    public StandardInvestmentAdapter(Context mContext, List<InvestList.DataBean.ListBean> result,
                                     ListView mListView)
    {
        super(mContext, result, mListView);
    }

    @Override
    protected BaseHolder<InvestList.DataBean.ListBean> getItemHolder()
    {
        return new StandardInvestmentHolder(this, mContext, mListView);
    }
}
