package com.mingjie.jf.adapter;

import android.content.Context;
import android.widget.ListView;

import com.mingjie.jf.bean.TransferingBean;
import com.mingjie.jf.holder.BaseHolder;
import com.mingjie.jf.holder.TransferingHolder;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.adapter
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 账户 - 转让中 adapter
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-15 11:25
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class TransferingAdapter extends BaseListViewAdapter<TransferingBean.DataBean.ReceiptTransferPagerBean.ListBean>
{
    private int type;

    public TransferingAdapter(Context mContext, List<TransferingBean.DataBean.ReceiptTransferPagerBean.ListBean>
            result, ListView mListView , int type)
    {
        super(mContext, result, mListView);
        this.type = type;
    }

    @Override
    protected BaseHolder<TransferingBean.DataBean.ReceiptTransferPagerBean.ListBean> getItemHolder()
    {
        return new TransferingHolder(this, mContext, mListView , type);
    }
}
