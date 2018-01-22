package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.TransferingBean;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.widgets.QuitDialog;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 账户 - 转让中 adapter
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-15 11:27
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class TransferingHolder extends BaseHolder<TransferingBean.DataBean.ReceiptTransferPagerBean.ListBean>
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

    private Button buy_btn;                     // 收购按钮
    private int type;

    public TransferingHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView, int type)
    {
        super(adapter, mContext, mListView);
        this.type = type;
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
        buy_btn = (Button) view.findViewById(R.id.buy_btn);
        tv_01_yuan = (TextView) view.findViewById(R.id.tv_01_yuan);
        return view;
    }

    @Override
    protected void refreshUI(TransferingBean.DataBean.ReceiptTransferPagerBean.ListBean data, int position)
    {
        creditor_loan_info_iv.setImageResource(R.mipmap.zhuan);
        tv_zhuanrang_title.setText(data.getTitle());
        tv_zhuanrang_total.setText(StringUtils.dou2Str(data.getSuccessAmount()));
        tv_zhuanrang_income.setText(data.getRateOfDiscount() + "%");
        if (data.getRepurchaseMode() == 1 || data.getRepurchaseMode() == 2)
        {
            tv_zhuanrang_startmoney.setText(data.getTransferNumber()+"");
        }
        else if (data.getRepurchaseMode() == 3)
        {
            tv_zhuanrang_startmoney.setText("1");
        }
        tv_zhuanrang_startlilv.setText(new SimpleDateFormat("yyyy-MM-dd").format(data.getSuccessDate()));

        if (data.getStateInt() == 8)
        {
            buy_btn.setText("撤销中");
            buy_btn.setEnabled(false);
            buy_btn.setTextColor(mContext.getResources().getColor(R.color.text_gr));
            buy_btn.setBackgroundResource(R.drawable.btn_bg);
        }
        else if (data.getProductStatus() == 14 || data.getProductStatus() == 20)
        {
            buy_btn.setText("放款中");
            buy_btn.setBackground(null);
            buy_btn.setEnabled(false);
        }
        else if ((data.getStateInt() == 1 || data.getStateInt() == 2 || data.getStateInt() == 4) && (data
                .getProductStatus() != 14 && data.getProductStatus() != 20))
        {
            buy_btn.setText("撤销");
            buy_btn.setEnabled(true);
        }

        tv_01_yuan.setText("元");
        tv01.setText("转让价格");
        tv02.setText("折价率");
        tv03.setText("剩余期数");
        if (type == 2)
        {
            buy_btn.setVisibility(View.VISIBLE);
            tv04.setText("申请时间");
        }
        else if (type == 3)
        {
            buy_btn.setVisibility(View.GONE);
            tv04.setText("转让时间");
        }
        initEvent(data);
    }

    public void initEvent(final TransferingBean.DataBean.ReceiptTransferPagerBean.ListBean data)
    {
        // 处理撤销债权按钮点击事件
        buy_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final QuitDialog dialog = new QuitDialog(mContext, "提醒", "确认要撤销债权？", "是", "否");
                dialog.show();
                dialog.setCancelListener(new QuitDialog.OnCancelListener()
                {
                    @Override
                    public void onCancelListener()   // 处理取消债权逻辑
                    {
                        dialog.dismiss();
                        RepealTransfer(data);
                    }
                });
                dialog.setEnterListener(new QuitDialog.OnEnterListener()
                {
                    @Override
                    public void onEnterListener()
                    {
                        dialog.dismiss();

                    }
                });
            }
        });
    }

    /**
     * 撤销债权
     */
    private void RepealTransfer(TransferingBean.DataBean.ReceiptTransferPagerBean.ListBean data)
    {
        // 撤销债权
        UIUtils.showLoading(mContext);
        Map params = new HashMap();
        params.put("bidDetailId", data.getId());
        HttpManager.getInstance().doPost(ServiceName.LOAD_REVOKE_RECEIPT_TRANSFER, params, new
                HttpCallBack<ServerResponse<Object>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {  // 数据为空
                UIUtils.showToast(mContext, R.string.dataError);
                UIUtils.dimissLoading();
            }

            @Override
            public void getHttpCallBack(ServerResponse<Object> object)
            {  // 成功
                UIUtils.dimissLoading();
                if (null != object)
                {
                    if ("000000".equals(object.getCode()))
                    {
                        // 撤销成功( 发送撤销event )
                        Event event = new Event();
                        event.eventType = Event.CHEXIAO_TRANSFER;
                        EventBus.getDefault().post(event);
                    }
                    else
                    {
                        UIUtils.showToast(object.getMsg());
                    }
                }
                else
                {
                    UIUtils.showToast("数据为空");
                }
            }
        });
    }
}
