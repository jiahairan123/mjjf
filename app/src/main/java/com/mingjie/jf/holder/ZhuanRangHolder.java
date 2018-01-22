package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.InvestList;
import com.mingjie.jf.utils.ProductStatusUtil;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 债权转让 界面的holder
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-13 14:19
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ZhuanRangHolder extends BaseHolder<InvestList.DataBean.ListBean>
{

    private TextView tv_zhuanrang_title;        // 标题
    private TextView tv_zhuanrang_total;        // 转让总额
    private TextView tv_zhuanrang_income;       // 到期净收益率
    private TextView tv_zhuanrang_surplustime;  // 剩余期数
    private TextView tv_zhuanrang_startmoney;   // 原始本金
    private TextView tv_zhuanrang_startlilv;    // 原始利率
    private TextView tv_zhuanrang_totaltime;    // 总期数

    private ImageView creditor_loan_info_iv;    // 右上角图标类型
    private ImageView iv_product_status;    // 印章

    // private Button buy_btn;                     // 收购按钮
    private NumberFormat nf;


    public ZhuanRangHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext, R.layout.item_fragment_transfer_list, null);
        tv_zhuanrang_title = (TextView) view.findViewById(R.id.tv_zhuanrang_title);
        tv_zhuanrang_total = (TextView) view.findViewById(R.id.tv_zhuanrang_total);
        tv_zhuanrang_income = (TextView) view.findViewById(R.id.tv_zhuanrang_income);
        tv_zhuanrang_surplustime = (TextView) view.findViewById(R.id.tv_zhuanrang_surplustime);
        tv_zhuanrang_startmoney = (TextView) view.findViewById(R.id.tv_zhuanrang_startmoney);
        tv_zhuanrang_startlilv = (TextView) view.findViewById(R.id.tv_zhuanrang_startlilv);
        tv_zhuanrang_totaltime = (TextView) view.findViewById(R.id.tv_zhuanrang_totaltime);

        creditor_loan_info_iv = (ImageView) view.findViewById(R.id.creditor_loan_info_iv);
        iv_product_status = (ImageView) view.findViewById(R.id.iv_product_status);

//        buy_btn = (Button) view.findViewById(R.id.buy_btn);
        nf = new DecimalFormat("0.00");
        return view;
    }

    @Override
    protected void refreshUI(InvestList.DataBean.ListBean data, int position)
    {
        tv_zhuanrang_title.setText(data.title);
        tv_zhuanrang_total.setText(StringUtils.dou2Str(data.productAmount)); // 转让总额
        tv_zhuanrang_income.setText(nf.format(data.transferProfit) + "%");   // 到期净收益率

        if (data.repurchaseMode == 1 || data.repurchaseMode == 2)
        {
            tv_zhuanrang_surplustime.setText(String.valueOf(data.productDeadline));// 剩余期数(月标)
        } else if (data.repurchaseMode == 3)
        {
            tv_zhuanrang_surplustime.setText("1");// 剩余期数（天标）
        }
        tv_zhuanrang_startmoney.setText(StringUtils.dou2Str(data.transferAmount)); // 原始本金
        tv_zhuanrang_startlilv.setText(data.getRepurchaseModeDesc());// 还款方式
        tv_zhuanrang_totaltime.setText(TimeFormatUtil.data2StrTime2(data.createdDate));  // 转让日期

        //标的类型
        //    if (data.getAssetType() == 3)
        //    {
        //        creditor_loan_info_iv.setImageResource(R.mipmap.product_type_3);
        //    }
        //    else if (data.getAssetType() == 4)
        //    {
        //        creditor_loan_info_iv.setImageResource(R.mipmap.product_type_4);
        //    }
        //    else if (data.getAssetType() == 5)
        //    {
        //        creditor_loan_info_iv.setImageResource(R.mipmap.product_type_5);
        //    }
        //    else if (data.getAssetType() == 6)
        //    {
        //        creditor_loan_info_iv.setImageResource(R.mipmap.product_type_6);
        //    }
        //    else if (data.getAssetType() == 7)
        //    {
        //        creditor_loan_info_iv.setImageResource(R.mipmap.product_type_7);
        //    }
        //    else if (data.getAssetType() == 8)
        //    {
        //        creditor_loan_info_iv.setImageResource(R.mipmap.product_type_8);
        //    }
        //    else if (data.getAssetType() == 9)
        //    {
        //        creditor_loan_info_iv.setImageResource(R.mipmap.product_type_9);
        //    }
        creditor_loan_info_iv.setImageResource(R.mipmap.zhuan);
        iv_product_status.setVisibility(View.VISIBLE);
        iv_product_status.setImageResource(ProductStatusUtil.status2SmallImg(data.productStatus));
    }

}
