package com.mingjie.jf.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.MenuToolAdapter;
import com.mingjie.jf.bean.CalculateBean;
import com.mingjie.jf.utils.CalculatorMonth;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.view.IncomeCalculatorItemView;
import com.mingjie.jf.view.PoupupSelectView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 收益计算器
 * <p>创 建 人： wuxianai
 * <p>邮箱地址： wuxianai@ming-hao.cn
 * <p>创建时间： 2016/6/30 15:16
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、2016/9/6、hexiangjun、实现收益计算器功能)
 */
public class IncomeCalculatorActivity extends BaseActivity implements View.OnClickListener, PoupupSelectView
        .OnListItemListener, PoupupSelectView.OnDisMissListener
{

    @BindView(R.id.iv_Left_public)
    ImageView mIvLeft;
    @BindView(R.id.tv_content_public)
    TextView mTitle;
    @BindView(R.id.btn_counter)
    Button mBtnCompute;//计算

    @BindView(R.id.counter_repayment)
    RelativeLayout mRepaymentMethod;//还款方式
    @BindView(R.id.tv_counter_fashion_result1)
    TextView mTvMethod;
    @BindView(R.id.iv_counter_arrow1)
    ImageView mIvarrow;//箭头
    @BindView(R.id.counter_money)
    IncomeCalculatorItemView mInvestMoney;//投资金额
    @BindView(R.id.counter_year_rate)
    IncomeCalculatorItemView mYearRate;//年利率
    @BindView(R.id.counter_limit)
    IncomeCalculatorItemView mInvestLimit;//投资期限
    @BindView(R.id.counter_service_rate)
    IncomeCalculatorItemView mServiceRate;//年利率

    @BindView(R.id.tv_counter_amount)
    TextView mTotailIncome;//总收益
    @BindView(R.id.tv_accrual_result)
    TextView mCleanIncome;//净赚利息
    @BindView(R.id.tv_accrual)
    TextView mServiceMoney;//服务费
    @BindView(R.id.listview_counter)
    ListView mDetailslList;//详情列表
    private PopupWindow window;
    private List<CalculateBean> mCalculaResult;//计算结果
    private double mTotalInterest = 0;//总收益利息

    private List<String> mDatas = new ArrayList<String>();

    /**
     * 还款方式
     */
    private int mMethod;

    /**
     * 投资金额
     */
    private String counter_money;

    /**
     * 年收益率
     */
    private String yearRate;

    /**
     * 投资期限
     */
    private String limit;

    /**
     * 投资服务费率
     */
    private String serviceRate;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_income_calculator);
    }

    protected void initData()
    {
        mDatas.add("按月等额本息还款");
        mDatas.add("按月付息，到期还本");
        mDatas.add("按日计息，到期还本息");

        mTitle.setText("收益计算器");
        mTvMethod.setOnClickListener(this);
        mIvLeft.setOnClickListener(this);
        mRepaymentMethod.setOnClickListener(this);
        mBtnCompute.setOnClickListener(this);
        mMethod = getIntent().getIntExtra("repurchaseMode", 0) - 1;
        limit = getIntent().getStringExtra("term");
        yearRate = getIntent().getStringExtra("interestRate");
        serviceRate = getIntent().getStringExtra("bidServerFee");
        if (mMethod != -1)
        {
            switch (mMethod)
            {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    mMethod = 0;
            }
            mYearRate.setContentText(yearRate);
            mInvestLimit.setContentText(limit);
            mInvestLimit.setTvUint(mMethod == 2 ? "天" : "月");
            mServiceRate.setContentText(serviceRate);
            mTvMethod.setText(mDatas.get(mMethod));
        }
        else
        {
            mMethod = 0;
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;

            case R.id.tv_counter_fashion_result1:
                doAnimation(mIvarrow);
                PoupupSelectView mPopupWindow = new PoupupSelectView(this, mDatas, mTvMethod);
                mPopupWindow.showPopupWindow();
                mPopupWindow.setOnListItemClickListener(this);
                mPopupWindow.setOnDisMissListener(this);
                break;

            case R.id.btn_counter:
                counter_money = mInvestMoney.getContentText();//投资金额
                yearRate = mYearRate.getContentText();//年利率
                limit = mInvestLimit.getContentText();//投资期限
                serviceRate = mServiceRate.getContentText();//服务费率

                if (TextUtils.isEmpty(counter_money))
                {
                    ToastUtil.showToast(IncomeCalculatorActivity.this, getString(R.string.menuTool_toast1));
                    return;
                }
                else if (TextUtils.isEmpty(yearRate))
                {
                    ToastUtil.showToast(IncomeCalculatorActivity.this, getString(R.string.menuTool_toast2));
                    return;
                }
                else if (TextUtils.isEmpty(limit))
                {
                    ToastUtil.showToast(IncomeCalculatorActivity.this, getString(R.string.menuTool_toast3));
                    return;
                }
                else if (TextUtils.isEmpty(serviceRate))
                {
                    ToastUtil.showToast(IncomeCalculatorActivity.this, getString(R.string.menuTool_toast6));
                    return;
                }

                if (Double.parseDouble(counter_money) > Constants.MAX_MONEY || Double.parseDouble(counter_money) < 1.00)
                {
                    ToastUtil.showToast(IncomeCalculatorActivity.this, getString(R.string.menuTool_toast4));
                    return;
                }
                else if (Float.parseFloat(yearRate) > Constants.MAX_RATE || Float.parseFloat(yearRate) < 1.00)
                {
                    ToastUtil.showToast(IncomeCalculatorActivity.this, getString(R.string.menuTool_toast7));
                    return;
                }
                else if ((mMethod != 2 && Float.parseFloat(limit) > Constants.MAX_MONTH) || Float.parseFloat(limit) <
                        1.00)
                {
                    ToastUtil.showToast(IncomeCalculatorActivity.this, getString(R.string.menuTool_toast8));
                    return;
                }
                else if ((mMethod == 2 && Float.parseFloat(limit) > Constants.MAX_DAY) || 1.00 > Float.parseFloat
                        (limit))
                {
                    ToastUtil.showToast(IncomeCalculatorActivity.this, getString(R.string.menuTool_toast9));
                    return;
                }
                else if (Float.parseFloat(serviceRate) > Constants.MAX_RATE || Float.parseFloat(serviceRate) < 0.00)
                {
                    ToastUtil.showToast(IncomeCalculatorActivity.this, getString(R.string.menuTool_toast10));
                    return;
                }

                Double tempRate = new Double(yearRate) / 100;
                BigDecimal amount = new BigDecimal(counter_money);
                BigDecimal rate = new BigDecimal(tempRate.toString());
                int planLimit = (int) Float.parseFloat(limit);

                if (mMethod == 0)
                {// 按月等额本息还款
                    mCalculaResult = CalculatorMonth.getInterestAndCapitalPerMonth1(amount, rate, planLimit);
                }
                else if (mMethod == 1)
                {//按月付息，到期还本
                    mCalculaResult = CalculatorMonth.getInterestAndCapitalPerMonth2(amount, rate, planLimit);
                }
                else if (mMethod == 2)
                {//按日计息，到期还本息
                    mCalculaResult = CalculatorMonth.getInterestAndCapitalPerDay(amount, rate, planLimit);
                }
                else
                {
                    ToastUtil.showToast(IncomeCalculatorActivity.this, getString(R.string.menuTool_toast5));
                    return;
                }
                //                计算总额
                for (CalculateBean bean : mCalculaResult)
                {
                    double temp = Double.parseDouble(bean.mLixi);
                    mTotalInterest += temp;
                }

                //                算手续费
                double pourboire = mTotalInterest * Double.parseDouble(serviceRate) * 0.01;//10%手续费
                double tempPourboire = mTotalInterest - pourboire;//净赚利息
                //                save2digit（）保留两位小数
                mTotailIncome.setText(StringUtils.dou2Str(mTotalInterest));
                mCleanIncome.setText(StringUtils.dou2Str(tempPourboire));
                mServiceMoney.setText(StringUtils.dou2Str(pourboire));

                mDetailslList.setAdapter(new MenuToolAdapter(IncomeCalculatorActivity.this, mCalculaResult,
                        mDetailslList));

                //                隐藏输入法
                UIUtils.hideInput(this, mBtnCompute);

                //                计算一次完成清零
                mCalculaResult = null;
                mTotalInterest = 0;
                break;
            default:
                break;

        }
    }

    //还款方式箭头旋转动画
    private void doAnimation(ImageView mIvarrow)
    {
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(350);//设置动画持续时间
        animation.setFillAfter(true);
        mIvarrow.startAnimation(animation);
    }

    @Override
    public void onDisMissListener()
    {
        RotateAnimation animation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(350);//设置动画持续时间
        animation.setFillAfter(true);
        mIvarrow.startAnimation(animation);
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, TextView view, int position, long id)
    {
        String text = view.getText().toString();
        mMethod = position;
        mInvestLimit.setTvUint(mMethod == 2 ? "天" : "月");
        mTvMethod.setText(text);
    }
}
