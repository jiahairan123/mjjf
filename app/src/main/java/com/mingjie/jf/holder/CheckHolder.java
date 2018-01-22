package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.CheckBean;
import com.mingjie.jf.utils.ProductStatusUtil;
import com.mingjie.jf.utils.StringUtils;

import java.text.SimpleDateFormat;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.holder
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-27 17:14
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CheckHolder extends BaseHolder<CheckBean.DataBean.ListBean>
{
    private ImageView iv_type;  // 左上角图标
    private TextView tv_title;  // 标题
    private ImageView iv_check_seal; // 印章
    private TextView tv_check_money; // 借款金额
    private TextView tv_year_lilv;   // 年利率
    private TextView tv_check_qixian;// 期限
    private TextView tv_check_repay_way; // 还款方式
    private TextView tv_check_times;     //申请借款时间

    public CheckHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView) {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mContext , R.layout.fragment_check_item , null);
        iv_type = (ImageView)view.findViewById(R.id.iv_type);
        tv_title = (TextView)view.findViewById(R.id.tv_title);
        iv_check_seal = (ImageView)view.findViewById(R.id.iv_check_seal);
        tv_check_money = (TextView)view.findViewById(R.id.tv_check_money);
        tv_year_lilv = (TextView)view.findViewById(R.id.tv_year_lilv);
        tv_check_qixian = (TextView)view.findViewById(R.id.tv_check_qixian);
        tv_check_repay_way = (TextView)view.findViewById(R.id.tv_check_repay_way);
        tv_check_times = (TextView)view.findViewById(R.id.tv_check_times);
        iv_check_seal.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    protected void refreshUI(CheckBean.DataBean.ListBean data, int position)
    {
        iv_type.setImageLevel(data.getAssetType());
        //标题
        tv_title.setText(data.title);
        //借款金额
        tv_check_money.setText(StringUtils.dou2Str(data.productAmount));
        //年利率
        tv_year_lilv.setText(String.valueOf(data.profit));
        //期限
        tv_check_qixian.setText(data.repurchaseMode == 3? String.valueOf(data.productDeadline) + "日" : String.valueOf(data.productDeadline) + "个月");
        //还款方式
        tv_check_repay_way.setText(data.repurchaseModeDesc);
        //申请借款时间
        tv_check_times.setText(new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss").format(data.createdDate));
        //设置印章
        iv_check_seal.setImageResource(ProductStatusUtil.status2SmallImg(data.productStatus));
    }
}
