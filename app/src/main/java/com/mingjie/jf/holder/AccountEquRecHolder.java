package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.AccountEquRecBean;
import com.mingjie.jf.utils.StringUtils;

import java.text.SimpleDateFormat;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-26 16:01
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AccountEquRecHolder extends BaseHolder<AccountEquRecBean.DataBean.ReceiptTransferPagerBean.ListBean>
{
    private ImageView creditor_loan_info_iv;    // 转让标类型
    private TextView tv_zhuanrang_title;        // 标题
    private TextView tv_zhuanrang_total;        // 转让总额
    private TextView tv_zhuanrang_income;       // 到期净收益率
    private TextView tv_zhuanrang_startmoney;   // 原始本金
    private TextView tv_zhuanrang_startlilv;    // 原始利率
    private ImageView iv_check_seal; //印章

    private TextView tv_01_yuan;
    private TextView tv_03_yuan;

    public AccountEquRecHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext, R.layout.item_activity_equ_rec, null);
        creditor_loan_info_iv = (ImageView) view.findViewById(R.id.creditor_loan_info_iv);
        tv_zhuanrang_title = (TextView) view.findViewById(R.id.tv_zhuanrang_title);
        tv_zhuanrang_total = (TextView) view.findViewById(R.id.tv_zhuanrang_total);
        tv_zhuanrang_income = (TextView) view.findViewById(R.id.tv_zhuanrang_income);
        tv_zhuanrang_startmoney = (TextView) view.findViewById(R.id.tv_zhuanrang_startmoney);
        tv_zhuanrang_startlilv = (TextView) view.findViewById(R.id.tv_zhuanrang_startlilv);
        iv_check_seal = (ImageView) view.findViewById(R.id.iv_check_seal);

        tv_01_yuan = (TextView) view.findViewById(R.id.tv_01_yuan);
        tv_03_yuan = (TextView) view.findViewById(R.id.tv_03_yuan);

        return view;
    }

    @Override
    protected void refreshUI(AccountEquRecBean.DataBean.ReceiptTransferPagerBean.ListBean data, int position)
    {
        creditor_loan_info_iv.setImageLevel(data.getAssetType());

        tv_zhuanrang_title.setText(data.getTitle());
        tv_zhuanrang_total.setText(StringUtils.dou2Str(data.getSuccessAmount()));   // 转让价格 successAmount

        if (data.getRepurchaseMode() == 1 || data.getRepurchaseMode() == 2)
        {
            tv_zhuanrang_income.setText(data.getTransferNumber() + "");  // 剩余期数 transferNumber
        }
        else if (data.getRepurchaseMode() == 3)
        {
            tv_zhuanrang_income.setText(1 + "");  // 剩余期数 transferNumber
        }
        tv_zhuanrang_startmoney.setText(data.getRateOfDiscount()); // 折价率 rateOfDiscount
        tv_zhuanrang_startlilv.setText(new SimpleDateFormat("yyyy-MM-dd").format(data.getCreatedDate()));// createdDate
        tv_01_yuan.setText("元");
        tv_03_yuan.setText("%");
        iv_check_seal.setVisibility(View.GONE);
        switch (data.getStateInt())
        {
            case 1:  // 申请中
                iv_check_seal.setImageResource(R.mipmap.equ_1);
                iv_check_seal.setVisibility(View.VISIBLE);
                break;
            case 2:  // 等待结果
                iv_check_seal.setImageResource(R.mipmap.equ_2);
                iv_check_seal.setVisibility(View.VISIBLE);
                break;
            case 3:  // 未通过
                iv_check_seal.setImageResource(R.mipmap.equ_3);
                iv_check_seal.setVisibility(View.VISIBLE);
                break;
            case 4:  // 转让中
                iv_check_seal.setImageResource(R.mipmap.equ_4);
                iv_check_seal.setVisibility(View.VISIBLE);
                break;
            case 5:  // 已失效
                iv_check_seal.setImageResource(R.mipmap.equ_5);
                iv_check_seal.setVisibility(View.VISIBLE);
                break;
            case 6:  // 已撤销
                iv_check_seal.setImageResource(R.mipmap.equ_6);
                iv_check_seal.setVisibility(View.VISIBLE);
                break;
            case 7:  // 已完成
                iv_check_seal.setImageResource(R.mipmap.equ_7);
                iv_check_seal.setVisibility(View.VISIBLE);
                break;
            case 8:  // 撤销中
                iv_check_seal.setImageResource(R.mipmap.equ_8);
                iv_check_seal.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
