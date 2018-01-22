package com.mingjie.jf.adapter;

import android.content.Context;
import android.widget.ListView;

import com.mingjie.jf.bean.ServiceBean;
import com.mingjie.jf.holder.BaseHolder;
import com.mingjie.jf.holder.SelectServiceLvHolder;

import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-12-08 15:50
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class SelectServiceAdapter extends BaseListViewAdapter<ServiceBean.ListBean> {
    public SelectServiceAdapter(Context mContext, List<ServiceBean.ListBean> result, ListView mListView) {
        super(mContext, result, mListView);
    }

    @Override
    protected BaseHolder<ServiceBean.ListBean> getItemHolder() {
        return new SelectServiceLvHolder(this, mContext, mListView);
    }
}
