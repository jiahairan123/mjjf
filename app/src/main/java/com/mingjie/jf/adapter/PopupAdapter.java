package com.mingjie.jf.adapter;

import android.content.Context;
import android.widget.ListView;

import com.mingjie.jf.holder.BaseHolder;
import com.mingjie.jf.holder.PopupHolder;

import java.util.List;

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
public class PopupAdapter extends BaseListViewAdapter<String>
{
    public PopupAdapter(Context mContext, List<String> result, ListView mListView)
    {
        super(mContext, result, mListView);
    }

    @Override
    protected BaseHolder<String> getItemHolder()
    {
        return new PopupHolder(this, mContext, mListView);
    }
}
