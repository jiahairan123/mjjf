package com.mingjie.jf.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.ToBankActivity;
import com.mingjie.jf.bean.CallBankResponse;
import com.mingjie.jf.bean.RepayCalendarDayBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.StringUtils;
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
 * <p>描   述： 日历adapter
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-12 14:11
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RepayCalendarAdapter extends BaseAdapter
{
    private final Context context;
    private final List<RepayCalendarDayBean> data;

    public RepayCalendarAdapter(Context context, List<RepayCalendarDayBean> data)
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
            convertView = View.inflate(context, R.layout.item_calendar, null);
            TextView tv01 = (TextView) convertView.findViewById(R.id.tv_01);
            TextView tv02 = (TextView) convertView.findViewById(R.id.tv_02);
            TextView tv03 = (TextView) convertView.findViewById(R.id.tv_03);
            TextView tv04 = (TextView) convertView.findViewById(R.id.tv_04);
            holder = new ViewHolder(tv01, tv02, tv03, tv04);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        final RepayCalendarDayBean bean = data.get(position);
        holder.tv01.setText(StringUtils.dou2Str(bean.getCapital() + bean.getInterest()));
        holder.tv02.setText(StringUtils.dou2Str(bean.getCapital()));
        holder.tv03.setText(StringUtils.dou2Str(bean.getInterest()));
//        if (bean.getState() == 2 || bean.getState() == 13)
//        {
//            holder.tv04.setText("已还款");
//            holder.tv04.setTextColor(context.getResources().getColor(R.color.color_public_red));
//            holder.tv04.setBackgroundResource(R.color.transparent);
//            holder.tv04.setClickable(false);
//            holder.tv04.setVisibility(View.INVISIBLE);
//        }
        /*
        state==1  待还款
||   state==5  已垫付
||   state==10  还款失败,显示还款按钮
         */
        if (bean.getState() == 1 || bean.getState() == 5 || bean.getState() == 10||bean.getState()==3)
        {
            CfLog.i("---+bean.getstate"+bean.getState());
            holder.tv04.setText("去还款");
//            holder.tv04.setVisibility(View.VISIBLE);
            holder.tv04.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv04.setBackgroundResource(R.drawable.shape_blue_button);
            holder.tv04.setClickable(true);
            holder.tv04.setOnClickListener(new View.OnClickListener()  // 还款
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
        }
        else
        {
            holder.tv04.setText(bean.getStatusDesc());
            holder.tv04.setTextColor(context.getResources().getColor(R.color.color_black_dark));
            holder.tv04.setBackgroundResource(R.color.transparent);
            holder.tv04.setClickable(false);
        }
        return convertView;
    }

    /**
     * 还款
     */
    private void repayment(final RepayCalendarDayBean data)
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
        TextView tv01;
        TextView tv02;
        TextView tv03;
        TextView tv04;

        public ViewHolder(TextView tv01, TextView tv02, TextView tv03, TextView tv04)
        {
            this.tv01 = tv01;
            this.tv02 = tv02;
            this.tv03 = tv03;
            this.tv04 = tv04;
        }
    }

}
