package com.mingjie.jf.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.CashBean;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.RedPacketBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.fragment.CouponListFragment;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.adapter
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-20 16:58
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CouponAdapter extends BaseAdapter
{
    private Context context;
    private List data;
    private int type;

    public CouponAdapter(Context context, List data, int type)
    {
        this.context = context;
        this.data = data;
        this.type = type;
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
        //现金券
        if (type == CouponListFragment.TYPE_CASH)
        {
            CashBean bean = (CashBean) data.get(position);
            CashViewHolder holder;
            if (convertView == null)
            {
                convertView = View.inflate(context, R.layout.item_cash, null);
                View llState = convertView.findViewById(R.id.ll_state);
                TextView tvAmount = (TextView) convertView.findViewById(R.id.tv_amount);
                TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                TextView tvLimitMinAmount = (TextView) convertView.findViewById(R.id.tv_limit_min_amount);
                TextView tvLimitProductType = (TextView) convertView.findViewById(R.id.tv_limit_product_type);
                TextView tvLimitProductDeadline = (TextView) convertView.findViewById(R.id.tv_limit_product_deadline);
                TextView tvExpiryDate = (TextView) convertView.findViewById(R.id.tv_expiry_date);
                holder = new CashViewHolder(llState, tvAmount, tvTitle, tvLimitMinAmount, tvLimitProductType,
                        tvLimitProductDeadline, tvExpiryDate);
                convertView.setTag(holder);
            }
            holder = (CashViewHolder) convertView.getTag();
            //state 现金券状态  1,未使用,2 3已使用,4,已过期
            if (bean.getState() == 1)
            {
                holder.llState.setBackgroundResource(R.mipmap.cash_v1);//状态背景
                holder.tvExpiryDate.setText(String.format(context.getString(R.string.expiry_date),
                        TimeFormatUtil.data2StrTime(bean.getExpireTime())));//失效日期
            }
            else if (bean.getState() == 2 || bean.getState() == 3)
            {
                holder.llState.setBackgroundResource(R.mipmap.cash_v2);
                holder.tvExpiryDate.setText(String.format(context.getString(R.string.use_date),
                        TimeFormatUtil.data2StrTime(bean.getUsedDate())));//使用日期
            }
            else if (bean.getState() == 4)
            {
                holder.llState.setBackgroundResource(R.mipmap.red_packet_v3);
                holder.tvExpiryDate.setText(String.format(context.getString(R.string.expiry_date),
                        TimeFormatUtil.data2StrTime(bean.getExpireTime())));
            }
            else
            {
            }

            //设置金额字体
            String amount = String.format(context.getString(R.string.coupon_amount),
                    StringUtils.dou2Str(bean.getAmount()));
            SpannableString styledText = new SpannableString(amount);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_style0), 0, 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_style1), 1, amount.length() - 2,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_style2), amount.length() - 2,
                    amount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvAmount.setText(styledText, TextView.BufferType.SPANNABLE);

            holder.tvTitle.setText(bean.getName());//现金券标题

            holder.tvLimitMinAmount.setText(String.format(context.getString(R.string.amount_limit),
                    StringUtils.dou2Str(bean.getMinInvestmentAmount())));//投资门槛

            holder.tvLimitProductType.setText(String.format(context.getString(R.string.use_scope),
                    bean.getAssetTypeStr()));//使用范围

            holder.tvLimitProductDeadline.setText(String.format(context.getString(R.string.date_limit),
                    bean.getUseRange()));//投资标的期限

            return convertView;
        }

        //红包
        if (type == CouponListFragment.TYPE_RED_PACKET)
        {
            final RedPacketBean bean = (RedPacketBean) data.get(position);
            RedPacketHolder holder;
            if (convertView == null)
            {
                convertView = View.inflate(context, R.layout.item_red_packet, null);
                View llState = convertView.findViewById(R.id.ll_state);// 红包使用状态
                TextView tvAmount = (TextView) convertView.findViewById(R.id.tv_amount);// 红包金额
                TextView tvGet = (TextView) convertView.findViewById(R.id.tv_get);// 立即领取红包
                TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);// 红包标题
                TextView tvBestowalDate = (TextView) convertView.findViewById(R.id.tv_bestowal_date);// 红包发放日期
                TextView tvExpiryDate = (TextView) convertView.findViewById(R.id.tv_expiry_date);// 红包失效日期
                holder = new RedPacketHolder(llState, tvAmount, tvGet, tvTitle, tvBestowalDate, tvExpiryDate);
                convertView.setTag(holder);
            }
            holder = (RedPacketHolder) convertView.getTag();
            //state 红包状态  1未领取,2已领取,3已过期
            if (bean.getState() == 1)
            {
                holder.llState.setBackgroundResource(R.mipmap.red_packet_v1);//状态背景
                holder.tvGet.setVisibility(View.VISIBLE);//领取按钮
                holder.tvExpiryDate.setText(String.format(context.getString(R.string.expiry_date), TimeFormatUtil
                        .data2StrTime(bean.getExpireTime())));//过期时间
            }
            else if (bean.getState() == 2)
            {
                holder.llState.setBackgroundResource(R.mipmap.red_packet_v2);
                holder.tvGet.setVisibility(View.GONE);
                holder.tvExpiryDate.setText(String.format(context.getString(R.string.get_date), TimeFormatUtil
                        .data2StrTime(bean.getUsedTime())));//领取时间
            }
            else if (bean.getState() == 3)
            {
                holder.llState.setBackgroundResource(R.mipmap.red_packet_v3);
                holder.tvGet.setVisibility(View.GONE);
                holder.tvExpiryDate.setText(String.format(context.getString(R.string.expiry_date), TimeFormatUtil
                        .data2StrTime(bean.getExpireTime())));
            }
            else
            {
            }

            //领取红包
            holder.tvGet.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    getRedPacket(bean.getId());
                }
            });

            //设置金额字体
            String amount = String.format(context.getString(R.string.coupon_amount),
                    StringUtils.dou2Str(bean.getAmount()));
            SpannableString styledText = new SpannableString(amount);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_style0), 0, 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_style1), 1, amount.length() - 2,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_style2), amount.length() - 2,
                    amount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvAmount.setText(styledText, TextView.BufferType.SPANNABLE);

            holder.tvTitle.setText(bean.getName());

            //设置发放时间
            holder.tvBestowalDate.setText(String.format(context.getString(R.string.bestowal_date), TimeFormatUtil
                    .data2StrTime(bean.getCreatedDate())));
            return convertView;

        }

        //加息券
        if (type == CouponListFragment.TYPE_COUPON)
        {
            CashBean bean = (CashBean) data.get(position);
            CashViewHolder holder;
            if (convertView == null)
            {
                convertView = View.inflate(context, R.layout.item_cash, null);
                View llState = convertView.findViewById(R.id.ll_state);
                TextView tvAmount = (TextView) convertView.findViewById(R.id.tv_amount);
                TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                TextView tvLimitMinAmount = (TextView) convertView.findViewById(R.id.tv_limit_min_amount);
                TextView tvLimitProductType = (TextView) convertView.findViewById(R.id.tv_limit_product_type);
                TextView tvLimitProductDeadline = (TextView) convertView.findViewById(R.id.tv_limit_product_deadline);
                TextView tvExpiryDate = (TextView) convertView.findViewById(R.id.tv_expiry_date);
                holder = new CashViewHolder(llState, tvAmount, tvTitle, tvLimitMinAmount, tvLimitProductType,
                        tvLimitProductDeadline, tvExpiryDate);
                convertView.setTag(holder);
            }
            holder = (CashViewHolder) convertView.getTag();
            //state 现金券状态  1,未使用,2 3已使用,4,已过期
            if (bean.getState() == 1)
            {
                holder.llState.setBackgroundResource(R.mipmap.cash_v1);//状态背景
                holder.tvExpiryDate.setText(String.format(context.getString(R.string.expiry_date),
                        TimeFormatUtil.data2StrTime(bean.getExpireTime())));//失效日期
            }
            else if (bean.getState() == 2 || bean.getState() == 3)
            {
                holder.llState.setBackgroundResource(R.mipmap.cash_v2);
                holder.tvExpiryDate.setText(String.format(context.getString(R.string.use_date),
                        TimeFormatUtil.data2StrTime(bean.getUsedDate())));//使用日期
            }
            else if (bean.getState() == 4)
            {
                holder.llState.setBackgroundResource(R.mipmap.red_packet_v3);
                holder.tvExpiryDate.setText(String.format(context.getString(R.string.expiry_date),
                        TimeFormatUtil.data2StrTime(bean.getExpireTime())));
            }
            else
            {
            }

            //设置金额字体
            //            String amount = StringUtils.double2Str(bean.getProportion())+"%";
            //            String amount = StringUtils.double2Str(Double.parseDouble(bean.getProportion()))+"%";
            String amount = bean.getProportion() + "%";
            SpannableString styledText = new SpannableString(amount);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_style1), 0, amount.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvAmount.setText(styledText, TextView.BufferType.SPANNABLE);

            holder.tvTitle.setText(bean.getName());//现金券标题

            holder.tvLimitMinAmount.setText(String.format(context.getString(R.string.amount_limit),
                    StringUtils.dou2Str(bean.getMinInvestmentAmount())));//投资门槛

            holder.tvLimitProductType.setText(String.format(context.getString(R.string.use_scope),
                    bean.getAssetTypeStr()));//使用范围

            holder.tvLimitProductDeadline.setText(String.format(context.getString(R.string.date_limit),
                    bean.getUseRange()));//投资标的期限

            return convertView;
        }
        return null;
    }

    /**
     * 领取红包
     *
     * @param id 红包id
     */
    private void getRedPacket(String id)
    {
        if (!Utilities.canNetworkUseful(context))//如果当前网络不可用
        {
            UIUtils.showToast(context, context.getString(R.string.notNet));
            return;
        }
        UIUtils.showLoading(context);
        Map params = new HashMap();
        params.put("id", id);
        HttpManager.getInstance().doPost(ServiceName.GETREDBAG, params, new HttpCallBack<ServerResponse>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.dimissLoading();
                ToastUtil.showToast(context, context.getResources().getString(R.string.dataError));
            }

            @Override
            public void getHttpCallBack(ServerResponse rsp)
            {
                UIUtils.dimissLoading();
                if (rsp == null)
                {
                    ToastUtil.showToast(context, context.getResources().getString(R.string.dataError));
                    return;
                }
                if (!"000000".equals(rsp.getCode()))
                {
                    ToastUtil.showToast(context, rsp.getMsg());
                }
                else
                {
                    ToastUtil.showToast(context, context.getResources().getString(R.string.get_success));
                    Event event = new Event();
                    event.eventType = Event.GET_REDPACKET;
                    EventBus.getDefault().post(event);
                    notifyDataSetChanged();
                }
            }
        });
    }

    static class CashViewHolder
    {
        View llState;//ll_state 现金券使用状态
        TextView tvAmount;//tv_amount 现金券金额
        TextView tvTitle;//tv_title 现金券标题
        TextView tvLimitMinAmount;//tv_limit_min_amount 现金券使用门槛
        TextView tvLimitProductType;//tv_limit_product_type 现金券使用范围
        TextView tvLimitProductDeadline;//tv_limit_product_deadline 现金券使用期限
        TextView tvExpiryDate;//tv_expiry_date 现金券失效日期

        public CashViewHolder(View llState, TextView tvAmount, TextView tvTitle, TextView tvLimitMinAmount, TextView
                tvLimitProductType, TextView tvLimitProductDeadline, TextView tvExpiryDate)
        {
            this.llState = llState;
            this.tvAmount = tvAmount;
            this.tvTitle = tvTitle;
            this.tvLimitMinAmount = tvLimitMinAmount;
            this.tvLimitProductType = tvLimitProductType;
            this.tvLimitProductDeadline = tvLimitProductDeadline;
            this.tvExpiryDate = tvExpiryDate;
        }
    }

    static class RedPacketHolder
    {
        View llState;//ll_state 红包使用状态
        TextView tvAmount;//tv_amount 红包金额
        TextView tvGet;//tv_get 立即领取红包
        TextView tvTitle;//tv_title 红包标题
        TextView tvBestowalDate;//tv_bestowal_date 红包发放日期
        TextView tvExpiryDate;//tv_expiry_date 红包失效日期

        public RedPacketHolder(View llState, TextView tvAmount, TextView tvGet, TextView tvTitle, TextView
                tvBestowalDate, TextView tvExpiryDate)
        {
            this.llState = llState;
            this.tvAmount = tvAmount;
            this.tvGet = tvGet;
            this.tvTitle = tvTitle;
            this.tvBestowalDate = tvBestowalDate;
            this.tvExpiryDate = tvExpiryDate;
        }
    }

}
