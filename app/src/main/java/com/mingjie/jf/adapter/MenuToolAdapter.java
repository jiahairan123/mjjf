package com.mingjie.jf.adapter;

import android.content.Context;
import android.widget.ListView;

import com.mingjie.jf.bean.CalculateBean;
import com.mingjie.jf.holder.BaseHolder;
import com.mingjie.jf.holder.MenuToolHolder;

import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 收益计算
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-06 10:44
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class MenuToolAdapter extends BaseListViewAdapter<CalculateBean>
{
    public MenuToolAdapter(Context mContext, List<CalculateBean> result, ListView mListView)
    {
        super(mContext, result, mListView);
    }

    @Override
    protected BaseHolder<CalculateBean> getItemHolder()
    {
        return new MenuToolHolder(this, mContext, mListView);
    }
}
