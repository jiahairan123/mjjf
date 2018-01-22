package com.mingjie.jf.view;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.LoanInfoActivity;
import com.mingjie.jf.bean.JieKuanDetails;
import com.mingjie.jf.utils.UIUtils;


/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.fragment
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 借款信息
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-18 15:59
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class LoanInfoDetailPage extends BaseTagPage {

    private JieKuanDetails bean;
    private TextView tv_loaninfo_name;    // 借款人姓名
    private TextView tv_loanuse;          // 借款用途
    private WebView web_loan_info_desc;   // 借款详情
    private String html;

    public LoanInfoDetailPage(LoanInfoActivity activity , JieKuanDetails bean){
        super(activity);
        this.bean = bean;
    }

    @Override
    protected View initContentView(LoanInfoActivity activity) {
        View mView = View.inflate(activity, R.layout.fragment_loan_info_about_loan_desc, null);
        tv_loaninfo_name = (TextView) mView.findViewById(R.id.tv_loaninfo_name);
        tv_loanuse = (TextView)mView.findViewById(R.id.tv_loanuse);
        web_loan_info_desc = (WebView) mView.findViewById(R.id.web_loan_info_desc);
        return mView;
    }

    @Override
    public void initData() {

        if (bean.data.product.borrowNameList != null)
        {
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < bean.data.product.borrowNameList.size(); i++)
            {
                sb.append(UIUtils.hintSingleUserName(bean.data.product.borrowNameList.get(i)) + ",");
            }
            String text = sb.toString();
//            text.substring(0,text.length()-1);
            tv_loaninfo_name.setText(text.substring(0,text.length()-1));
        }

//        UIUtils.hintUserName(tv_loaninfo_name , bean.data.product.borrowName);
        tv_loanuse.setText(bean.data.product.userTypeDesc);
        html = bean.data.product.description;
        if (html.contains("src=\"/p"))
        {
            html = html.replace("src=\"/p", "src=\"http://car.zhuji.net/p");
        }
        web_loan_info_desc.loadDataWithBaseURL(null , html, "text/html", "utf-8", null);
        super.initData();
    }
}
