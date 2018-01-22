package com.mingjie.jf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.WithDrawRecord;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.QuitDialog;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mingjie.jf.widgets.QuitDialog.*;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-11-23 18:13
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class WithDrawRecordAdapter extends RecyclerView.Adapter<WithDrawRecordAdapter.MyViewHolder>
{
    private final SimpleDateFormat mFormat;
    private String mStyle = "yyy-MM-dd HH:mm:ss";//时间格式
    private Context mContext;
    private List<WithDrawRecord.ListBean> list;
    private QuitDialog quitDialog;
    // private JRecyclerView jRecyclerView;

    public WithDrawRecordAdapter(Context context, List<WithDrawRecord.ListBean> list)
    {
        this.mContext = context;
        this.list = list;
        mFormat = new SimpleDateFormat(mStyle);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_case_withdraw, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        final WithDrawRecord.ListBean vo = list.get(position);
        //时间
        holder.tvMarkhisTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(vo.getCreateDate())));
        holder.tvTixianYuan.setText(vo.getAmount() + "");
        holder.tvBank.setText(vo.getBankNoDesc());
        holder.tvBankNum.setText(vo.getCardNo());
        holder.tvState.setText(vo.getStateDesc());

        if (1 == vo.getState() || 3 == vo.getState())
        {
            holder.tvChexiao.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.tvChexiao.setVisibility(View.GONE);
        }

        if (5 == vo.getState())
        {
            holder.tvFactMoney.setText(vo.getAmount() + "");
        }
        else
        {
            holder.tvFactMoney.setText("0.00");
        }

        //撤销功能
        holder.tvChexiao.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                quitDialog = new QuitDialog(mContext, "温馨提示", "请问您确定要撤销吗？", "取消", "确定");
                quitDialog.setEnterListener(new OnEnterListener()
                {
                    @Override
                    public void onEnterListener()
                    {
                        setCancel(vo, holder);
                    }
                });
                quitDialog.setCancelListener(new QuitDialog.OnCancelListener()
                {

                    @Override
                    public void onCancelListener()
                    {
                        quitDialog.dismiss();
                    }
                });
                quitDialog.show();
            }
        });

    }

    //提交撤销的申请
    private void setCancel(WithDrawRecord.ListBean vo, final MyViewHolder holder)
    {
        if (!Utilities.canNetworkUseful(mContext))//如果当前网络不可用
        {
            UIUtils.showToast("当前网络不可用");
            return;
        }
        quitDialog.dismiss();
        UIUtils.showLoading(mContext);
        HashMap<String, String> map = new HashMap<>();
        map.put("name", CacheUtils.getString(mContext, "name"));
        map.put("HHmm", TimeFormatUtil.getSimpleTime());
        map.put("HH", TimeFormatUtil.getHour());
        MobclickAgent.onEvent(mContext, ServiceName.CANCELWITHDRAWALS, map); // 私房钱提现撤销

        Map<String, String> params = new HashMap<>();
        params.put("id", vo.getId());
        HttpManager.getInstance().doPost(ServiceName.CANCELWITHDRAWALS, params, new
                HttpCallBack<ServerResponse>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        UIUtils.dimissLoading();
                        UIUtils.showToast(mContext.getResources().getString(R.string.dataError2));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse rsp)
                    {
                        UIUtils.dimissLoading();
                        if (rsp != null && "000000".equals(rsp.getCode()))
                        {
                            CfLog.i("---" + rsp.getCode());
                            //刷新list
                            holder.tvChexiao.setVisibility(View.GONE);
                            holder.tvState.setText("已撤销");

                            //    Event event = new Event();
                            //    event.eventType = Event.CHEXIAO;
                            //    EventBus.getDefault().post(event);
                            UIUtils.showToast(rsp.getMsg());
                        }
                        else
                        {
                            UIUtils.showToast(mContext.getResources().getString(R.string.dataError2));
                        }
                    }
                });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvTixianYuan;
        // TextView tvTixianShouxu;
        TextView tvFactMoney;
        TextView tvBank;
        TextView tvBankNum;
        TextView tvState;
        TextView tvMarkhisTime;
        TextView tvChexiao;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvMarkhisTime = (TextView) itemView.findViewById(R.id.tv_markhis_time);//提现金额
            tvTixianYuan = (TextView) itemView.findViewById(R.id.tv_tixian_yuan);//提现金额
            // tvTixianShouxu = (TextView) itemView.findViewById(R.id.tv_tixian_shouxu);//提现手续费
            tvFactMoney = (TextView) itemView.findViewById(R.id.tv_fact_money);//实际到账金额
            tvBank = (TextView) itemView.findViewById(R.id.tv_bank);//银行名
            tvBankNum = (TextView) itemView.findViewById(R.id.tv_bank_num);//银行卡后四位
            tvState = (TextView) itemView.findViewById(R.id.tv_state);//状态
            tvChexiao = (TextView) itemView.findViewById(R.id.tv_chexiao);//是否可以撤销
        }
    }

}

