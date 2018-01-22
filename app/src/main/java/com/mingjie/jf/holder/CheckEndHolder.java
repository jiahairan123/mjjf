package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.CheckEndBean;
import com.mingjie.jf.utils.ProductStatusUtil;
import com.mingjie.jf.utils.StringUtils;

import java.text.SimpleDateFormat;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-27 19:08
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CheckEndHolder extends BaseHolder<CheckEndBean.DataBean.ListBean>
{
    private ImageView iv_type;       //右上角图标
    private TextView tv_title;       //标题
    private TextView tv_repay_time;  // 还款时间
    private TextView tv_borrow_money;// 借款金额
    private TextView tv_true_borrow_money;// 实际招标金额
    private TextView tv_year_lilv;  //年利率
    private TextView tv_check_qixian;// 期限
    private TextView tv_check_repay_way;//还款方式

    private ImageView iv_check_seal;    // 印章

    public CheckEndHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView) {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext , R.layout.fragment_borrowend_item , null);
        iv_type = (ImageView) view.findViewById(R.id.iv_type);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_repay_time = (TextView)view.findViewById(R.id.tv_repay_time);
        tv_borrow_money = (TextView) view.findViewById(R.id.tv_borrow_money);
        tv_true_borrow_money = (TextView) view.findViewById(R.id.tv_true_borrow_money);
        tv_year_lilv = (TextView) view.findViewById(R.id.tv_year_lilv);
        tv_check_qixian = (TextView) view.findViewById(R.id.tv_check_qixian);
        tv_check_repay_way = (TextView) view.findViewById(R.id.tv_check_repay_way);
        iv_check_seal = (ImageView)view.findViewById(R.id.iv_check_seal);
        iv_check_seal.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    protected void refreshUI(CheckEndBean.DataBean.ListBean data, int position)
    {
        iv_type.setImageLevel(data.getAssetType());
        tv_title.setText(data.title);
        tv_repay_time.setText(new SimpleDateFormat("yyyy-MM-dd").format(data.createdDate));
        tv_borrow_money.setText(StringUtils.dou2Str(data.productAmount));
        tv_true_borrow_money.setText(StringUtils.dou2Str(data.bidAmount));
        tv_year_lilv.setText(StringUtils.dou2Str(data.profit));
        tv_check_qixian.setText(data.repurchaseMode == 3? String.valueOf(data.productDeadline) + "日" : String.valueOf(data.productDeadline) + "个月");
        tv_check_repay_way.setText(data.repurchaseModeDesc);
        iv_check_seal.setImageResource(ProductStatusUtil.status2SmallImg(data.productStatus));
    }
}
