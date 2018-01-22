package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;

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
public class PopupHolder extends BaseHolder<String>
{

    private TextView tv;

    public PopupHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View mRootView = View.inflate(mContext, R.layout.holder_popupwindow, null);
        tv = (TextView) mRootView.findViewById(R.id.tv_holder_popup);
        return mRootView;
    }

    @Override
    protected void refreshUI(String data, int position)
    {
        tv.setText(data);
    }
}
