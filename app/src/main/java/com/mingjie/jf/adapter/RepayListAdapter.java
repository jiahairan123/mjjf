package com.mingjie.jf.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.ToBankActivity;
import com.mingjie.jf.bean.CallBankResponse;
import com.mingjie.jf.bean.RepayListBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.QuitDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.adapter
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 回款列表
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-15 15:09
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RepayListAdapter extends BaseAdapter
{

    private final Context context;
    private final List<RepayListBean> data;

    public RepayListAdapter(Context context, List<RepayListBean> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = View.inflate(context, R.layout.item_repay_list, null);

            ImageView ivObjType = (ImageView) convertView.findViewById(R.id.iv_objtype);//标的类型
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);//标的标题tv_title
            TextView tvRepay = (TextView) convertView.findViewById(R.id.tv_repay);//还款tv_repay
            TextView tvRepayAmount = (TextView) convertView.findViewById(R.id.tv_repay_amount);//应还总额
            TextView tvPrincipal = (TextView) convertView.findViewById(R.id.tv_principal);//本金
            TextView tvInterest = (TextView) convertView.findViewById(R.id.tv_interest);//利息
            TextView tvOughtRepayTime = (TextView) convertView.findViewById(R.id.tv_ought_repay_time);//应还时间
            TextView tvRealityRepayTime = (TextView) convertView.findViewById(R.id.tv_reality_repay_time);//实际还款时间
            TextView tvStatus = (TextView) convertView.findViewById(R.id.tv_status);//状态
            holder = new ViewHolder(ivObjType, tvTitle, tvRepay, tvRepayAmount, tvPrincipal, tvInterest,
                    tvOughtRepayTime, tvRealityRepayTime, tvStatus);
            convertView.setTag(holder);
        }
        final RepayListBean bean = data.get(position);
        holder = (ViewHolder) convertView.getTag();
        holder.ivObjType.setImageLevel(bean.getAssetType());
        holder.tvTitle.setText(bean.getProductTitle());
        holder.tvStatus.setText(bean.getStateDesc());
        //标的状态
        if (bean.getState() == 2 || bean.getState() == 13)
        {
            holder.tvRepay.setVisibility(View.GONE);
        }
        else if (bean.getState() == 1 || bean.getState() == 5 || bean.getState() == 10||bean.getState()==3)
        {
            holder.tvRepay.setVisibility(View.VISIBLE);
        }
        else {
            holder.tvRepay.setVisibility(View.GONE);
        }

        holder.tvRepay.setOnClickListener(new View.OnClickListener()  // 还款
        {
            @Override
            public void onClick(View v)
            {
                final QuitDialog dialog = new QuitDialog(context, "还款", "确认要归还该期" +
                        StringUtils.dou2Str(bean.getCapital() + bean.getInterest()) + "元吗？", "是", "否");
                dialog.show();
                dialog.setCancelListener(new QuitDialog.OnCancelListener()
                {
                    @Override
                    public void onCancelListener()   // 处理还款逻辑
                    {
                        dialog.dismiss();
                        repayment(bean);
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
        holder.tvRepayAmount.setText(StringUtils.dou2Str(bean.getCapital() + bean.getInterest()));
        holder.tvPrincipal.setText(StringUtils.dou2Str(bean.getCapital()));
        holder.tvInterest.setText(StringUtils.dou2Str(bean.getInterest()));
        if (bean.getExpireDate() != null)
        {
            holder.tvOughtRepayTime.setText(TimeFormatUtil.data2StrTime2(bean.getExpireDate()));
        }
        if (bean.getRepaidDate() != null)
        {
            holder.tvRealityRepayTime.setText(TimeFormatUtil.data2StrTime2(bean.getRepaidDate()));
        }
        return convertView;
    }

    /**
     * 立即投资
     */
    private void repayment(final RepayListBean data)
    {
        UIUtils.showLoading(context);
        if (!Utilities.canNetworkUseful(context))//如果当前网络不可用
        {
            UIUtils.showToast("当前网络不可用，请重试！");
            return;
        }
        Map<String, String> params = new HashMap<>(2);
        params.put("id", data.getId());
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
                            Intent intent = new Intent(context, ToBankActivity.class);
                            intent.putExtra("borrowId", data.getId());
                            intent.putExtra("transUrl", callBank.getHxHostUrl());
                            intent.putExtra("transCode", callBank.getTransCode());
                            intent.putExtra("requestData", callBank.getRequestData());
                            intent.putExtra("channelFlow", callBank.getChannelFlow());
                            context.startActivity(intent);
                        }
                    }
                });
    }

    static class ViewHolder
    {
        ImageView ivObjType;//标的类型
        TextView tvTitle;//标的标题tv_title
        TextView tvRepay;//还款tv_repay
        TextView tvRepayAmount;//应还总额tv_repay_amount
        TextView tvPrincipal;//本金tv_principal
        TextView tvInterest;//利息tv_interest
        TextView tvOughtRepayTime;//应还时间tv_ought_repay_time
        TextView tvRealityRepayTime;//实际还款时间tv_reality_repay_time
        TextView tvStatus;//状态tv_status

        public ViewHolder(ImageView ivObjType, TextView tvTitle, TextView tvRepay, TextView tvRepayAmount, TextView
                tvPrincipal, TextView tvInterest, TextView tvOughtRepayTime, TextView tvRealityRepayTime, TextView
                                  tvStatus)
        {
            this.ivObjType = ivObjType;
            this.tvTitle = tvTitle;
            this.tvRepay = tvRepay;
            this.tvRepayAmount = tvRepayAmount;
            this.tvPrincipal = tvPrincipal;
            this.tvInterest = tvInterest;
            this.tvOughtRepayTime = tvOughtRepayTime;
            this.tvRealityRepayTime = tvRealityRepayTime;
            this.tvStatus = tvStatus;
        }
    }
}
