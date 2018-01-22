package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.EquitableRecordBean;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 转让记录
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-19 20:59
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class EquitableRecordHolder extends BaseHolder<EquitableRecordBean.DataBean>
{
    private TextView tv_buy;
    private TextView tv_sell;
    private TextView tv_dealamount;
    private TextView tv_dealtime;

    public EquitableRecordHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext, R.layout.item_equ_record, null);
        tv_buy = (TextView) view.findViewById(R.id.tv_buy);
        tv_sell = (TextView) view.findViewById(R.id.tv_sell);
        tv_dealamount = (TextView) view.findViewById(R.id.tv_dealamount);
        tv_dealtime = (TextView) view.findViewById(R.id.tv_dealtime);

        return view;
    }

    @Override
    protected void refreshUI(EquitableRecordBean.DataBean data, int position)
    {
        UIUtils.hintUserName(tv_buy, data.getNewUserName());
        UIUtils.hintUserName(tv_sell, data.getUserName());
        tv_dealamount.setText(StringUtils.dou2Str(data.getSuccessAmount()));
        tv_dealtime.setText(TimeFormatUtil.data2StrTime2(data.getSuccessDate()));
    }
}
