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
import com.mingjie.jf.view.progressview.DonutProgressView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 散标投资 holder
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-22 15:29
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class StandardInvestmentHolder extends BaseHolder<InvestList.DataBean.ListBean>
{
    private TextView invest_title_tv;           // 标题
    private TextView invest_total;              // 借款总额
    private TextView invest_year_rate;          // 年化收益
    private TextView invest_anble_money;        // 剩余可投
    private TextView invest_limit;              // 期限/还款方式
    private TextView invest_qishu;              // 期数

    private ImageView iv_product_status;        // 印章
    private DonutProgressView progress_loanDetail;     // 进度条

    private ImageView invest_type_iv;         // 标题左边图标类型

    private NumberFormat nf;


    public StandardInvestmentHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext, R.layout.item_fragment_invest_list, null);
        invest_title_tv = (TextView) view.findViewById(R.id.invest_title_tv);
        invest_total = (TextView) view.findViewById(R.id.invest_total);
        invest_year_rate = (TextView) view.findViewById(R.id.invest_year_rate);
        invest_anble_money = (TextView) view.findViewById(R.id.invest_anble_money);
        invest_limit = (TextView) view.findViewById(R.id.invest_limit);
        iv_product_status = (ImageView) view.findViewById(R.id.iv_product_status);                  // 印章
        progress_loanDetail = (DonutProgressView) view.findViewById(R.id.progress_loanDetail);
        invest_type_iv = (ImageView) view.findViewById(R.id.invest_type_iv);

        invest_qishu = (TextView) view.findViewById(R.id.invest_qishu);
        nf = new DecimalFormat("0.00");
        return view;
    }

    @Override
    protected void refreshUI(InvestList.DataBean.ListBean data, int position)
    {
        invest_title_tv.setText(data.title);
        if (data.productStatus == 10 || data.productStatus == 11 || data.productStatus == 14 || data.productStatus == 20)
        {
            invest_total.setText(StringUtils.dou2Str(data.bidAmount));
            invest_anble_money.setText("0.00");  // 剩余可投
        } else if (data.productStatus != 10 && data.productStatus != 11 && data.productStatus != 14 && data.productStatus != 20)
        {
            invest_total.setText(StringUtils.dou2Str(data.productAmount));
            double amount = data.productAmount - data.bidAmount;
            if (amount < 0)
            {
                invest_anble_money.setText("0.00");  // 剩余可投
            } else
            {
                invest_anble_money.setText(StringUtils.dou2Str(amount));  // 剩余可投
            }
        }
        invest_year_rate.setText(nf.format(data.profit));  // 年化收益
        invest_limit.setText(data.repurchaseModeDesc); // 还款方式

        if (data.repurchaseMode == 1 || data.repurchaseMode == 2)
        {
            invest_qishu.setText(data.productDeadline + "月/"); // 还款期限
        } else if (data.repurchaseMode == 3)
        {
            invest_qishu.setText(data.productDeadline + "日/"); // 还款期限
        }
        // 设置印章状态
//        if(data.productStatus != 7)  // 招标中不显示印章
//        {
        iv_product_status.setVisibility(View.VISIBLE);
//            CfLog.i("data.productStatus = "+data.productStatus);
        iv_product_status.setImageResource(ProductStatusUtil.status2SmallImg(data.productStatus));
//        }else{
//            iv_product_status.setVisibility(View.GONE);
//        }

        if (12 == data.productStatus){
            iv_product_status.setImageResource(ProductStatusUtil.status2SmallImg(11));
        }


        // 设置标题左边箭头
        invest_type_iv.setImageLevel(data.assetType);
//        if (data.assetType == 3)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_3);
//        } else if (data.assetType == 4)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_4);
//        }else if (data.assetType == 5)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_5);
//        }else if(data.assetType == 6)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_6);
//        }else if(data.assetType == 7)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_7);
//        }else if(data.assetType == 8)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_8);
//        }else if(data.assetType == 9)
//        {
//            invest_type_iv.setImageResource(R.mipmap.product_type_9);
//        }
        // 设置进度
        double progress = data.bidAmount * 1.0 / data.productAmount * 100;
//        CfLog.i("data.bidAmount = "+ data.bidAmount +"---"+"data.productAmount = "+data.productAmount+"---"+"data.bidAmount / data.productAmount ="+data.bidAmount / data.productAmount);
//        CfLog.i("progress = "+progress);
        if (progress >= 100)
        {
            progress_loanDetail.setProgress(100);
        } else
        {
            progress_loanDetail.setProgress(progress);
        }
    }
}
