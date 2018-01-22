package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.BorrowDetailBean;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UIUtils;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-28 10:02
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class BorrowDetailHolder extends BaseHolder<BorrowDetailBean.DataBean>
{
    private TextView tv_name;
    private TextView tv_borrow_money;
    private TextView tv_benjin;
    private TextView tv_lixi;
    private TextView tv_repay_type;

    public BorrowDetailHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext , R.layout.borrow_detail_item , null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_borrow_money = (TextView) view.findViewById(R.id.tv_borrow_money);
        tv_benjin = (TextView) view.findViewById(R.id.tv_benjin);
        tv_lixi = (TextView) view.findViewById(R.id.tv_lixi);
        tv_repay_type = (TextView) view.findViewById(R.id.tv_repay_type);
        return view;
    }

    @Override
    protected void refreshUI(BorrowDetailBean.DataBean data, int position)
    {
//        tv_name.setText(data.name);
        UIUtils.hintUserName(tv_name, data.name);//投资人加密 2017-4-25
        tv_borrow_money.setText(StringUtils.dou2Str(data.capital + data.interest));
        tv_benjin.setText(StringUtils.dou2Str(data.capital));
        tv_lixi.setText(StringUtils.dou2Str(data.interest));
        tv_repay_type.setText(data.repurchaseModeDesc);
    }
}
