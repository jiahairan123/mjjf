package com.mingjie.jf.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.mingjie.jf.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 忘借款信息
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class LoanDetailLoanInfoActivity extends BaseActivity {

    @BindView(R.id.loan_detail_loan_info_tab)
    TabLayout loanDetailLoanInfoTab;
    //@BindView(R.id.loan_detail_loan_info_pager)
    //ViewPager loanDetailLoanInfoPager;

    private String[] mTitleDates;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loan_detail_loan_info);
        ButterKnife.bind(this);

        mTitleDates = getResources().getStringArray(R.array.loan_info_tab_arr);

        for (int i = 0; i < mTitleDates.length; i++) {
            loanDetailLoanInfoTab.addTab(loanDetailLoanInfoTab.newTab().setText(mTitleDates[i]));
        }

        //List<Fragment> fragments = new ArrayList<>();
        //fragments.add(new LoanDescFragment());
        //fragments.add(new RiskInfoFragment());
        //fragments.add(new UserMaterialFragment());
        //
        //LoanInfoFragmentAdapter adapter = new LoanInfoFragmentAdapter(getSupportFragmentManager(), fragments,
        // mTitleDates);
        //loanDetailLoanInfoPager.setAdapter(adapter);
        //loanDetailLoanInfoTab.setupWithViewPager(loanDetailLoanInfoPager);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}
