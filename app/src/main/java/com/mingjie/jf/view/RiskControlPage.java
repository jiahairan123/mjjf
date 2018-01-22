package com.mingjie.jf.view;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.LoanInfoActivity;
import com.mingjie.jf.bean.JieKuanDetails;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.fragment
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 风控信息
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-18 15:59
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RiskControlPage extends BaseTagPage {

    private JieKuanDetails bean;
    private TextView tv_loaninfo_name;      // 担保机构
    private WebView web_fengkong_desc;
    private String html;

    public RiskControlPage(LoanInfoActivity activity , JieKuanDetails bean){
        super(activity);
        this.bean = bean;
    }

    @Override
    protected View initContentView(LoanInfoActivity activity) {
        View mView = View.inflate(activity, R.layout.fragment_loan_info_about_risk_info, null);
        tv_loaninfo_name = (TextView)mView.findViewById(R.id.tv_loaninfo_name);
        web_fengkong_desc = (WebView) mView.findViewById(R.id.web_fengkong_desc);
        return mView;
    }

    @Override
    public void initData() {
        tv_loaninfo_name.setText(bean.data.companysName);
        html = bean.data.product.riskNote;
        if (html.contains("src=\"/p"))
        {
            html = html.replace("src=\"/p", "src=\"http://car.zhuji.net/p");
        }
        web_fengkong_desc.loadDataWithBaseURL(null , html, "text/html", "utf-8", null);
        super.initData();
    }
}
