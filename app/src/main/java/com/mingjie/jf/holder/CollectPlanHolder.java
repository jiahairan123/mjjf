package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.CollectPlanBean;
import com.mingjie.jf.utils.ProductStatusUtil;
import com.mingjie.jf.utils.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-10-12 11:49
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CollectPlanHolder extends BaseHolder<CollectPlanBean.DataBean.ListBean>
{

    private TextView tv_zhuanrang_title;        // 标题
    private TextView tv_zhuanrang_total;        // 还款总额
    private TextView tv_zhuanrang_income;       // 本金
    private TextView tv_zhuanrang_surplustime;  // 期限
    private TextView tv_zhuanrang_startmoney;   // 利息
    private TextView tv_zhuanrang_startlilv;    // 还款方式
    private TextView tv_zhuanrang_totaltime;    // 收款日期

    private ImageView creditor_loan_info_iv;    // 右上角图标类型
    private ImageView iv_check_seal;            // 印章

    private NumberFormat nf;


    public CollectPlanHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext, R.layout.item_collect_list, null);
        tv_zhuanrang_title = (TextView) view.findViewById(R.id.tv_zhuanrang_title);
        tv_zhuanrang_total = (TextView) view.findViewById(R.id.tv_zhuanrang_total);
        tv_zhuanrang_income = (TextView) view.findViewById(R.id.tv_zhuanrang_income);
        tv_zhuanrang_surplustime = (TextView) view.findViewById(R.id.tv_zhuanrang_surplustime);
        tv_zhuanrang_startmoney = (TextView) view.findViewById(R.id.tv_zhuanrang_startmoney);
        tv_zhuanrang_startlilv = (TextView) view.findViewById(R.id.tv_zhuanrang_startlilv);
        tv_zhuanrang_totaltime = (TextView) view.findViewById(R.id.tv_zhuanrang_totaltime);

        creditor_loan_info_iv = (ImageView) view.findViewById(R.id.creditor_loan_info_iv);
        iv_check_seal = (ImageView)view.findViewById(R.id.iv_check_seal);

        nf = new DecimalFormat("0.00");
        return view;
    }

    @Override
    protected void refreshUI(CollectPlanBean.DataBean.ListBean data, int position)
    {
        tv_zhuanrang_title.setText(data.title);
        tv_zhuanrang_total.setText(StringUtils.dou2Str(data.capital + data.interest)); // 还款总额
        tv_zhuanrang_income.setText(nf.format(data.capital));   // 本金
        tv_zhuanrang_surplustime.setText(data.planIndex + "/" + (Integer.valueOf(data.repurchaseMode) == 3 ? data.planIndex : data.productDeadline));// 期限
        tv_zhuanrang_startmoney.setText(StringUtils.dou2Str(data.interest)); // 利息
        tv_zhuanrang_startlilv.setText(data.repurchaseModeDesc);// 还款方式

        tv_zhuanrang_totaltime.setText(new SimpleDateFormat("yyyy-MM-dd").format(data.expireDate));  // 收款日期

        creditor_loan_info_iv.setImageLevel(data.assetType);

//        if(data.assetType == 3)
//        {
//            creditor_loan_info_iv.setImageResource(R.mipmap.product_type_3);
//        }else if(data.assetType == 4)
//        {
//            creditor_loan_info_iv.setImageResource(R.mipmap.product_type_4);
//        }

        iv_check_seal.setVisibility(View.VISIBLE);
//        CfLog.i("收款计划 data.state = "+data.state);
//        iv_check_seal.setImageResource(ProductStatusUtil.status2SmallImg(data.state)); // 印章
        if(data.state == 1 || data.state == 3 || data.state == 4 || data.state == 9 || data.state == 10 || data.state == 11 || data.state == 12)
        {
            iv_check_seal.setImageResource(ProductStatusUtil.status2SmallImg(10)); // 还款中
        }else if(data.state == 2 || data.state == 5 || data.state == 13)
        {
            iv_check_seal.setImageResource(ProductStatusUtil.status2SmallImg(11)); // 已还清
        }else{
            iv_check_seal.setVisibility(View.GONE);
        }
    }
}

