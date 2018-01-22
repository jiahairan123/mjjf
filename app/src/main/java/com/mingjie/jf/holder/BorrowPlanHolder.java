package com.mingjie.jf.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.BorrowDetailActivity;
import com.mingjie.jf.activity.ToBankActivity;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.CallBankResponse;
import com.mingjie.jf.bean.RepayPlanBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.QuitDialog;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我的借款 还款计划
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-27 21:00
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class BorrowPlanHolder extends BaseHolder<RepayPlanBean.DataBean>
{
    private ImageView iv_type;    //右上角图标
    private TextView tv_title;     //title
    private TextView tv_repay_money;//还款金额
    private TextView tv_benjin;     // 本金
    private TextView tv_repay_interest; //利息
    private TextView tv_check_repay_way;//期限
    private TextView tv_check_times;    //还款时间
    private ImageView iv_check_seal;    //印章

    private TextView tv_repay;         // 还款
    private TextView tv_repay_detail;  //收款明细

    private LinearLayout ll_repay_money;
    private LinearLayout ll_pay_detail;

    public BorrowPlanHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView)
    {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext, R.layout.activity_repayplan_item, null);
        iv_type = (ImageView) view.findViewById(R.id.iv_type);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_repay_money = (TextView) view.findViewById(R.id.tv_repay_money);
        tv_benjin = (TextView) view.findViewById(R.id.tv_benjin);
        tv_repay_interest = (TextView) view.findViewById(R.id.tv_repay_interest);
        tv_check_repay_way = (TextView) view.findViewById(R.id.tv_check_repay_way);
        tv_check_times = (TextView) view.findViewById(R.id.tv_check_times);
        iv_check_seal = (ImageView) view.findViewById(R.id.iv_check_seal);

        tv_repay = (TextView) view.findViewById(R.id.tv_repay);//还款按钮
        tv_repay_detail = (TextView) view.findViewById(R.id.tv_repay_detail);

        ll_repay_money = (LinearLayout) view.findViewById(R.id.ll_repay_money);
        ll_pay_detail = (LinearLayout) view.findViewById(R.id.ll_pay_detail);

        // tv_repay.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        // tv_repay.getPaint().setAntiAlias(true);//抗锯齿

        // tv_repay_detail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        // tv_repay_detail.getPaint().setAntiAlias(true);//抗锯齿
        return view;
    }

    @Override
    protected void refreshUI(RepayPlanBean.DataBean data, int position)
    {
        iv_type.setImageLevel(data.getAssetType());
        //标题
        tv_title.setText(data.productTitle);
        //还款金额
        tv_repay_money.setText(StringUtils.dou2Str(data.capital + data.interest));
        //本金
        tv_benjin.setText(StringUtils.dou2Str(data.capital));
        //利息
        tv_repay_interest.setText(StringUtils.dou2Str(data.interest));
        //期限
        tv_check_repay_way.setText(data.planIndex + "/" + (Integer.parseInt(data.repurchaseMode) == 3 ? data.planIndex
                : data.productDeadline));
        //还款日期
        tv_check_times.setText(new SimpleDateFormat("yyyy-MM-dd").format(data.expireDate));
        //印章
        iv_check_seal.setVisibility(View.VISIBLE);

        if (data.state==1||data.state==3||data.state==5||data.state==10){
            iv_check_seal.setImageResource(R.drawable.repayment_level);
            iv_check_seal.setImageLevel(data.state);
            ll_repay_money.setEnabled(true);
            tv_repay.setVisibility(View.VISIBLE);
        }else {
            iv_check_seal.setImageResource(R.drawable.repayment_level);
            iv_check_seal.setImageLevel(data.state);
            ll_repay_money.setEnabled(false);
            tv_repay.setVisibility(View.GONE);
        }
//        if (data.state == 1)
//        {
//            iv_check_seal.setImageResource(R.mipmap.repay_1);
//            tv_repay.setVisibility(View.VISIBLE);
//        }
//        else if (data.state == 2)
//        {
//            iv_check_seal.setImageResource(R.mipmap.obj_status_s11);
//            ll_repay_money.setEnabled(false);
//            tv_repay.setVisibility(View.GONE);
//        }
//        else if (data.state == 3)
//        {
//            iv_check_seal.setImageResource(R.mipmap.obj_status_s12);
//            tv_repay.setVisibility(View.VISIBLE);
//        }
//        else if (data.state == 4)
//        {
//            iv_check_seal.setImageResource(R.mipmap.repay_4);
//            ll_repay_money.setEnabled(false);
//        }
//        else if (data.state == 5)
//        {
//            iv_check_seal.setImageResource(R.mipmap.repay_5);
//            tv_repay.setVisibility(View.VISIBLE);
//        }
//        else if (data.state == 7)
//        {
//            iv_check_seal.setImageResource(R.mipmap.repay_7);
//            ll_repay_money.setEnabled(false);
//        }
//        else if (data.state == 8)
//        {
//            iv_check_seal.setImageResource(R.mipmap.repay_8);
//            ll_repay_money.setEnabled(false);
//        }
//        else if (data.state == 9)
//        {
//            iv_check_seal.setImageResource(R.mipmap.obj_status_s10);
//            ll_repay_money.setEnabled(false);
//        }
//        else if (data.state == 10)
//        {
//            iv_check_seal.setImageResource(R.mipmap.repay_10);
//            tv_repay.setVisibility(View.VISIBLE);
//        }
//        else if (data.state == 11)
//        {
//            iv_check_seal.setImageResource(R.mipmap.repay_11);
//            ll_repay_money.setEnabled(false);
//        }
//        else if (data.state == 12)//收益失败
//        {
//            iv_check_seal.setImageResource(R.mipmap.repay_12);
//            ll_repay_money.setEnabled(false);
//        }
//        else if (data.state == 13)
//        {
//            iv_check_seal.setImageResource(R.mipmap.repay_13);
//            ll_repay_money.setEnabled(false);
//        }
//        else
//        {
//            iv_check_seal.setVisibility(View.GONE);
//        }
        initEvent(data);
    }

    public void initEvent(final RepayPlanBean.DataBean data)
    {
        ll_pay_detail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, BorrowDetailActivity.class);
                intent.putExtra("Id", data.id);
                mContext.startActivity(intent);
            }
        });
        ll_repay_money.setOnClickListener(new View.OnClickListener()  // 还款
        {
            @Override
            public void onClick(View v)
            {
                final QuitDialog dialog = new QuitDialog(mContext, "还款", "确认要归还该期" +
                        StringUtils.dou2Str(data.capital + data.interest + data.serviceFee) + "元吗？", "是", "否");
                dialog.show();
                dialog.setCancelListener(new QuitDialog.OnCancelListener()
                {
                    @Override
                    public void onCancelListener()   // 处理还款逻辑
                    {
                        dialog.dismiss();
                        invest(data);
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
     * 去还款
     */
    private void invest(final RepayPlanBean.DataBean data)
    {
        UIUtils.showLoading(mContext);
        if (!Utilities.canNetworkUseful(mContext))//如果当前网络不可用
        {
            UIUtils.showToast("当前网络不可用，请重试！");
            return;
        }
        Map<String, String> params = new HashMap<>(2);
        params.put("id", data.id);
        params.put("returnUrl", Constants.BANK_RETURN_URL);
        HttpManager.getInstance().doPost(ServiceName.REPAYMENT_SUBMITREPAY, params, new
                HttpCallBack<ServerResponse<CallBankResponse>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.dimissLoading();
                UIUtils.showToast("服务器异常，请稍后重试！");
            }

            @Override
            public void getHttpCallBack(ServerResponse<CallBankResponse> rsp)
            {
                UIUtils.dimissLoading();
                if (rsp == null)
                {
                    UIUtils.showToast("服务器异常，请稍后重试！");
                }
                else if (!rsp.getCode().equals("000000"))
                {
                    UIUtils.showToast(rsp.getMsg());
                }
                else
                {
                    CallBankResponse callBank = rsp.getData();
                    Intent intent = new Intent(mContext, ToBankActivity.class);
                    intent.putExtra("borrowId", data.id);
                    intent.putExtra("transUrl", callBank.getHxHostUrl());
                    intent.putExtra("transCode", callBank.getTransCode());
                    intent.putExtra("requestData", callBank.getRequestData());
                    intent.putExtra("channelFlow", callBank.getChannelFlow());
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
