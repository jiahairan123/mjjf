package com.mingjie.jf.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.BorrowAgreementActivity;
import com.mingjie.jf.activity.RepayPlanActivity;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.RepayBean;
import com.mingjie.jf.utils.ProductStatusUtil;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UrlUtil;

import java.text.SimpleDateFormat;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-27 14:24
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RePayHolder extends BaseHolder<RepayBean.DataBean.ListBean>
{

    private ImageView iv_type;
    private TextView tv_title;
    private TextView tv_repay_qixian;
    private TextView tv_repay_money;
    private TextView tv_true_repay_money;
    private TextView tv_repay_times;
    //private TextView tv_repay_plan;
    //private TextView tv_repay_deal;

    private LinearLayout ll_repay_plan;
    private LinearLayout ll_borrow_agreement;

    private ImageView iv_product_status;  // 印章

    public RePayHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView) {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext , R.layout.fragment_repay_item , null);
        iv_type = (ImageView) view.findViewById(R.id.iv_type);                  // 左上角图标类型
        tv_title = (TextView)view.findViewById(R.id.tv_title);                  // title
        tv_repay_qixian = (TextView)view.findViewById(R.id.tv_repay_qixian);  // 期数
        tv_repay_money = (TextView)view.findViewById(R.id.tv_repay_money);    // 借款金额
        tv_true_repay_money = (TextView)view.findViewById(R.id.tv_true_repay_money);  // 实际借款金额
        tv_repay_times = (TextView)view.findViewById(R.id.tv_repay_times);  // 还款时间
        // tv_repay_plan = (TextView)view.findViewById(R.id.tv_repay_plan);  // 还款计划
        // tv_repay_deal = (TextView)view.findViewById(R.id.tv_repay_deal);  // 借款协议
        ll_repay_plan = (LinearLayout)view.findViewById(R.id.ll_repay_plan);
        ll_borrow_agreement = (LinearLayout)view.findViewById(R.id.ll_borrow_agreement);
        iv_product_status = (ImageView)view.findViewById(R.id.iv_product_status);    // 印章

        return view;
    }

    @Override
    protected void refreshUI(RepayBean.DataBean.ListBean data, int position)
    {
        iv_type.setImageLevel(data.getAssetType());
        // title
        tv_title.setText(data.title);
        // 期数
        tv_repay_qixian.setText(data.repurchaseMode == 3?String.valueOf(data.productDeadline)+"日" : String.valueOf(data.productDeadline)+"个月");
        // 借款金额
        tv_repay_money.setText(StringUtils.dou2Str(data.productAmount));
        // 实际借款金额
        tv_true_repay_money.setText(StringUtils.dou2Str(data.bidAmount));
        // 还款时间
        tv_repay_times.setText(new SimpleDateFormat("yyyy-MM-dd").format(data.repaymentDate));

        iv_product_status.setVisibility(View.VISIBLE);
        iv_product_status.setImageResource(ProductStatusUtil.status2SmallImg(data.productStatus));

        initEvent(data);
    }

    public void initEvent(final RepayBean.DataBean.ListBean data){  // 处理收购按钮点击事件
        ll_repay_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent repayPlanIntent = new Intent(mContext, RepayPlanActivity.class);  // 还款计划
                repayPlanIntent.putExtra("productId" , data.id);
                mContext.startActivity(repayPlanIntent);
            }
        });
        ll_borrow_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //借款协议
                Intent intent = new Intent(mContext , BorrowAgreementActivity.class);
                intent.putExtra("productId" , UrlUtil.getBorrowUrl() + data.id+"&agreementType="+2);
                mContext.startActivity(intent);
            }
        });
    }
}
