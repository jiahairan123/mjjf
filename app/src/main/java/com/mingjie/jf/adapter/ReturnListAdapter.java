package com.mingjie.jf.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.ReturnListBean;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;

import java.util.List;

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
public class ReturnListAdapter extends BaseAdapter
{

    private final Context context;
    private final List<ReturnListBean> data;

    public ReturnListAdapter(Context context, List<ReturnListBean> data)
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = View.inflate(context, R.layout.item_return_list, null);
            ImageView ivObjType = (ImageView) convertView.findViewById(R.id.iv_objtype);
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            TextView tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            TextView tvReceivable = (TextView) convertView.findViewById(R.id.tv_receivable);
            TextView tvPrincipal = (TextView) convertView.findViewById(R.id.tv_principal);
            TextView tvInterest = (TextView) convertView.findViewById(R.id.tv_interest);
            TextView tvServerFee = (TextView) convertView.findViewById(R.id.tv_server_fee);
            TextView tvEarnings = (TextView) convertView.findViewById(R.id.tv_earnings);
            TextView tvBorrower = (TextView) convertView.findViewById(R.id.tv_borrower);
            TextView tvReturnTime = (TextView) convertView.findViewById(R.id.tv_return_time);
            TextView tvIssue = (TextView) convertView.findViewById(R.id.tv_issue);
            holder = new ViewHolder(ivObjType, tvTitle, tvStatus, tvReceivable, tvPrincipal, tvInterest, tvServerFee,
                    tvEarnings, tvBorrower, tvReturnTime, tvIssue);
            convertView.setTag(holder);
        }
        ReturnListBean bean = data.get(position);
        holder = (ViewHolder) convertView.getTag();

        if (bean.getProductType() == 1)
        {
            holder.ivObjType.setImageResource(R.mipmap.zhuan);
        }
        else
        {
            holder.ivObjType.setImageResource(R.drawable.invest_type_level);
            holder.ivObjType.setImageLevel(bean.getAssetType()); //标的类型
        }

        holder.tvTitle.setText(bean.getTitle());//标的标题
        //标的状态
        if (bean.getState() == 2 || bean.getState() == 5 || bean.getState() == 13)
        {
            holder.tvStatus.setText("已收");
        }
        else
        {
            holder.tvStatus.setText("待收");
        }
        holder.tvReceivable.setText(StringUtils.dou2Str(bean.getCapital() + bean.getInterest()));//应收总额
        holder.tvPrincipal.setText(StringUtils.dou2Str(bean.getCapital()));//本金
        holder.tvInterest.setText(StringUtils.dou2Str(bean.getInterest()));//利息
        holder.tvServerFee.setText(StringUtils.dou2Str(bean.getServiceFee()));//服务费
        holder.tvEarnings.setText(StringUtils.dou2Str(bean.getInterest() - bean.getServiceFee()));//净赚收益
        String name = bean.getNameList().size() > 1 ? bean.getNameList().get(0) + "..." : bean.getNameList().get(0);
        holder.tvBorrower.setText(name);//借款人
        holder.tvReturnTime.setText(TimeFormatUtil.data2StrTime2(bean.getExpireDate()));//收款日期
        holder.tvIssue.setText(String.format(context.getString(R.string.issue), bean.getPlanIndexStr() + "", bean
                .getProductDeadline() + ""));//收款日期
        return convertView;
    }

    static class ViewHolder
    {
        ImageView ivObjType;//标的类型
        TextView tvTitle;//标的标题
        TextView tvStatus;//标的状态
        TextView tvReceivable;//应收总额tv_receivable
        TextView tvPrincipal;//本金tv_principal
        TextView tvInterest;//利息tv_interest
        TextView tvServerFee;//服务费tv_server_fee
        TextView tvEarnings;//净赚收益tv_earnings
        TextView tvBorrower;//借款人tv_borrower
        TextView tvReturnTime;//收款日期tv_return_time
        TextView tvIssue;//期次tv_issue

        public ViewHolder(ImageView ivObjType, TextView tvTitle, TextView tvStatus, TextView tvReceivable, TextView
                tvPrincipal, TextView tvInterest, TextView tvServerFee, TextView tvEarnings, TextView tvBorrower,
                          TextView tvReturnTime, TextView tvIssue)
        {
            this.ivObjType = ivObjType;
            this.tvTitle = tvTitle;
            this.tvStatus = tvStatus;
            this.tvReceivable = tvReceivable;
            this.tvPrincipal = tvPrincipal;
            this.tvInterest = tvInterest;
            this.tvServerFee = tvServerFee;
            this.tvEarnings = tvEarnings;
            this.tvBorrower = tvBorrower;
            this.tvReturnTime = tvReturnTime;
            this.tvIssue = tvIssue;
        }
    }
}
