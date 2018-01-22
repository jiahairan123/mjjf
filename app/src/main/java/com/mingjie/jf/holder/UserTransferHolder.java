package com.mingjie.jf.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.PuchaseActivity;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.AccountDebtBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 用户中心 债权转让 holder
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-21 10:27
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class UserTransferHolder extends BaseHolder<AccountDebtBean.DataBean.BidPagerBean.ListBean>
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

    private TextView tv_03_yuan;

    private Button buy_btn;                     // 收购按钮
    private String bidDetailId;

    public UserTransferHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
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
        tv_03_yuan = (TextView) view.findViewById(R.id.tv_03_yuan);

        buy_btn = (Button) view.findViewById(R.id.buy_btn);
        return view;
    }

    @Override
    protected void refreshUI(AccountDebtBean.DataBean.BidPagerBean.ListBean data, int position)
    {
        buy_btn.setText("转让");
//        creditor_loan_info_iv.setImageLevel(data.getAssetType());
        creditor_loan_info_iv.setImageResource(R.mipmap.zhuan);
        tv_zhuanrang_title.setText(data.getTitle());
        tv_zhuanrang_total.setText(data.getProfit() + "%");   // 年化利率

        if (data.getRepurchaseMode() == 1 || data.getRepurchaseMode() == 2)
        {
            tv_zhuanrang_income.setText(data.getLeftSurplusReceiptIndex()+"");  // 剩余期数
        }
        else if (data.getRepurchaseMode() == 3)
        {
            tv_zhuanrang_income.setText("1");  // 剩余期数
        }
        tv_zhuanrang_startmoney.setText(StringUtils.dou2Str(data.getWaitCapitalInterest())); // 待收本息
        tv_zhuanrang_startlilv.setText(new SimpleDateFormat("yyyy-MM-dd").format(data.getLatelyRepaymentDate()));  //
        // data.transferDeadline

        tv01.setText("年化利率");
        tv02.setText("剩余期数");
        tv03.setText("待收本息");
        tv04.setText("下期回收款日期");
        tv_03_yuan.setText("元");
        bidDetailId = data.getDetailId();
        initEvent(bidDetailId);
    }

    public void initEvent(final String bidDetailId)
    {  // 处理收购按钮点击事件
        buy_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkLoadReceiptTransfer(bidDetailId);
            }
        });
    }

    /**
     * 检查当前标是否可转让
     */
    public void checkLoadReceiptTransfer(final String bidDetailId)
    {
        if (!Utilities.canNetworkUseful(mContext))//如果当前网络不可用
        {
            UIUtils.showToast("当前网络不可用，请重试！");
            return;
        }
        UIUtils.showLoading(mContext);
        Map params = new HashMap();
        params.put("bidDetailId", bidDetailId);
        HttpManager.getInstance().doPost(ServiceName.CHECK_LOAD_RECEIPT_TRANSFER, params, new HttpCallBack<ServerResponse>(){

            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.dimissLoading();
                UIUtils.showToast(mContext, R.string.dataError);
            }

            @Override
            public void getHttpCallBack(ServerResponse rsp)
            {
                UIUtils.dimissLoading();
                // 跳债权信息
                if(rsp.getMsg().isEmpty())
                {
                    Intent buyintent = new Intent(mContext, PuchaseActivity.class);
                    buyintent.putExtra("id", bidDetailId);
                    mContext.startActivity(buyintent);
                }else
                {
                    UIUtils.showToast(rsp.getMsg());
                }
            }
        });
    }
}
