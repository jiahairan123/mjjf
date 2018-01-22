package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.EquitableBuyBean;
import com.mingjie.jf.utils.StringUtils;

import java.text.SimpleDateFormat;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-16 11:55
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class BuyTransferHolder extends BaseHolder<EquitableBuyBean.DataBean.BidPagerBean.ListBean>
{
    private ImageView creditor_loan_info_iv;    // 转让标类型
    private TextView tv_zhuanrang_title;        // 标题
    private TextView tv_zhuanrang_total;        // 转让总额
    private TextView tv_zhuanrang_income;       // 到期净收益率
    private TextView tv_zhuanrang_startmoney;   // 原始本金
    private TextView tv_zhuanrang_startlilv;    // 原始利率

    private TextView tv01;
    private TextView tv02;
    private TextView tv03;
    private TextView tv04;

    private TextView tv_01_yuan;

    private Button buy_btn;


    public BuyTransferHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext, R.layout.item_activity_transfer_list, null);
        creditor_loan_info_iv = (ImageView) view.findViewById(R.id.creditor_loan_info_iv);
        tv_zhuanrang_title = (TextView) view.findViewById(R.id.tv_zhuanrang_title);
        tv_zhuanrang_total = (TextView) view.findViewById(R.id.tv_zhuanrang_total);
        tv_zhuanrang_income = (TextView) view.findViewById(R.id.tv_zhuanrang_income);
        tv_zhuanrang_startmoney = (TextView) view.findViewById(R.id.tv_zhuanrang_startmoney);
        tv_zhuanrang_startlilv = (TextView) view.findViewById(R.id.tv_zhuanrang_startlilv);

        tv01 = (TextView) view.findViewById(R.id.tv_01);
        tv02 = (TextView) view.findViewById(R.id.tv_02);
        tv03 = (TextView) view.findViewById(R.id.tv_03);
        tv04 = (TextView) view.findViewById(R.id.tv_04);
        tv_01_yuan = (TextView) view.findViewById(R.id.tv_01_yuan);

        buy_btn = (Button) view.findViewById(R.id.buy_btn);
        return view;
    }

    @Override
    protected void refreshUI(EquitableBuyBean.DataBean.BidPagerBean.ListBean data, int position)
    {
        buy_btn.setVisibility(View.GONE);

        creditor_loan_info_iv.setImageResource(R.mipmap.zhuan);
        tv_zhuanrang_title.setText(data.getTitle());
        tv_zhuanrang_total.setText(StringUtils.dou2Str(data.getAmount()));
        tv_zhuanrang_income.setText(data.getProfit() + "%");
        if (data.getRepurchaseMode() == 1 || data.getRepurchaseMode() == 2)
        {
            tv_zhuanrang_startmoney.setText(data.getAllSurplusReceiptIndex()+"");
        }
        else if (data.getRepurchaseMode() == 3)
        {
            tv_zhuanrang_startmoney.setText("1");
        }
        tv_zhuanrang_startlilv.setText(new SimpleDateFormat("yyyy-MM-dd").format(data.getCreatedDate()));
        tv_01_yuan.setText("元");
        tv01.setText("转让价格");
        tv02.setText("年化利率");
        tv03.setText("剩余期数");
        tv04.setText("购买时间");

    }
}
