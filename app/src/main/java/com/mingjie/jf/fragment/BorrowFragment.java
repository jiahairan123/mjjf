package com.mingjie.jf.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.BorrowFlowActivity;
import com.mingjie.jf.activity.PrivateActivity;
import com.mingjie.jf.bean.OrderApplyBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.SubmitApplyBean;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.view.LimitEditText;
import com.mingjie.jf.view.MateralButton;
import com.mingjie.jf.view.PoupupSelectView;
import com.mingjie.jf.widgets.QuitDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.fragment
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我要借款
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-22 11:42
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class BorrowFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener, AdapterView.OnItemClickListener, QuitDialog.OnEnterListener, QuitDialog.OnCancelListener
{
    @BindView(R.id.iv_Left_public)      //返回
            ImageView ivLeftBack;
    @BindView(R.id.tv_content_public)   //中间内容
            TextView tvTitlePublic;
    @BindView(R.id.tv_right_public)
    TextView tv_right_public;           // 右边的文字
    @BindView(R.id.rl_borrow_item_edit)
    RelativeLayout rl_borrow_item_edit; // 输入框所在布局的父布局
    @BindView(R.id.view_line)
    View view_line;
    @BindView(R.id.btn_submit)
    MateralButton btn_submit;           // 提交申请按钮
    @BindView(R.id.iv_down_chengjie)
    RelativeLayout iv_down_chengjie;    // 承接公司输入框右边展开箭头
    @BindView(R.id.chengjie_line)
    View chengjie_line;                 // 承接公司下方的分割线

    private List<String> mListData;
    private SubmitApplyBean params;
    private QuitDialog dialog;

    @BindView(R.id.edt_user_register_name)
    LimitEditText edt_user_register_name;                          // 姓名
    @BindView(R.id.register_input_resetpass_pass_affirm)
    EditText register_input_resetpass_pass_affirm;            // 手机号码
    @BindView(R.id.edt_user_register_sex)
    EditText edt_user_register_sex;                           // 性别
    @BindView(R.id.input_resetpass_verifycode)
    EditText input_resetpass_verifycode;            // 年龄
    @BindView(R.id.input_borrow_use)
    EditText input_borrow_use;                      // 用途
    @BindView(R.id.input_chengjie_company)
    EditText input_chengjie_company;                // 承接公司
    @BindView(R.id.input_borrow_money)
    EditText input_borrow_money;                    // 借款金额
    @BindView(R.id.input_register_verifycode)
    EditText input_register_verifycode;             // 验证码
    @BindView(R.id.res_imverificationcode)
    ImageView res_imverificationcode;               // 验证码图片
    @BindView(R.id.tv_private_policy)
    TextView tv_private_policy;                     // 隐私政策

    @BindView(R.id.iv_sex_arrows)
    ImageView iv_sex_arrows;                        // 性别选择箭头
    @BindView(R.id.iv_chengjie_unit_down)
    ImageView iv_chengjie_unit_down;                // 承接公司箭头

    private String name;
    private String phone;
    private String sex;
    private String age;
    private String use;
    private String unit;
    private String money;
    private String code;

    private String unit_id;   // 承接公司id
    private List<OrderApplyBean.DataBean> mData;   // 存放承接公司数据的容器
    private List<String> chengjieData = new ArrayList<String>();

    @BindView(R.id.swipe_account_fragment)
    SwipeRefreshLayout mRefreshView;                //刷新控件


    @Override
    protected View initView()
    {
        View mView = View.inflate(getContext(), R.layout.fragment_borrow, null);
        ButterKnife.bind(this, mView);
        // 添加数据
        mListData = new ArrayList<String>();
        mListData.add("男");
        mListData.add("女");
        ivLeftBack.setVisibility(View.INVISIBLE);
        tv_right_public.setVisibility(View.VISIBLE);
        tv_right_public.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        iv_down_chengjie.setOnClickListener(this);
        res_imverificationcode.setOnClickListener(this);
        tv_private_policy.setOnClickListener(this);
        tvTitlePublic.setText("我要借款");
        tv_right_public.setText("借款流程");
        mRefreshView.setOnRefreshListener(this);
        rl_borrow_item_edit.setOnClickListener(this);

        mRefreshView.setRefreshing(true);
        onRefresh();
        return mView;
    }

    @Override
    public void onRefresh()
    {
        cleandata();
        try
        {
            HttpManager.getInstance().displayImageCode(res_imverificationcode, null, null);
            HttpManager.getInstance().doPost(ServiceName.CGKX_GETAGENCYS, new HttpCallBack<OrderApplyBean>()
            {
                @Override
                public void getHttpCallNull(String str)
                {
                    UIUtils.showToast("当前网络不可用，请重试！");
                    closeRefresh();
                }

                @Override
                public void getHttpCallBack(OrderApplyBean bean)
                {
                    closeRefresh();
                    if (null != bean)
                    {
                        if ("000000".equals(bean.code))
                        {
                            //                            mData.clear();
                            mData = bean.data;
                            UIUtils.showToast("刷新成功");
                        }
                        else
                        {
                            UIUtils.showToast(bean.msg);
                        }
                    }
                    else
                    {
                        UIUtils.showToast("提交失败,请重试");
                    }
                }
            });
        }
        catch (Exception e)
        {
            CfLog.i("异常" + e.getMessage());
            UIUtils.showToast("获取数据失败");
        }
    }

    protected void closeRefresh()
    {
        if (mRefreshView == null)
        {
            return;
        }
        if (mRefreshView.isRefreshing())
        {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    protected void initData()
    {
        mRefreshView.post(new Runnable()
        {
            @Override
            public void run()
            {
                mRefreshView.setRefreshing(true);
                cleandata();
                mRefreshView.setRefreshing(false);
            }
        });

        input_borrow_money.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0)
                {
                    return;
                }
                if (temp.length() - posDot - 1 > 2)
                {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        input_chengjie_company.setText(mData.get(position).name);
        unit_id = mData.get(position).id;
    }

    /**
     * 检查用户输入信息是否正确
     *
     * @return
     */
    private boolean checkInfo()
    {
        name = edt_user_register_name.getText().toString().trim();
        phone = register_input_resetpass_pass_affirm.getText().toString().trim();
        sex = edt_user_register_sex.getText().toString().trim();
        age = input_resetpass_verifycode.getText().toString().trim();
        use = input_borrow_use.getText().toString().trim();
        unit = input_chengjie_company.getText().toString().trim();
        money = input_borrow_money.getText().toString().trim();
        code = input_register_verifycode.getText().toString().trim();

        if (TextUtils.isEmpty(name))
        {
            UIUtils.showToast(mActivity, "请输入用户名");
            return false;
        }
        else if (TextUtils.isEmpty(phone))
        {
            UIUtils.showToast(mActivity, "请输入手机号码");
            return false;
        }
        else if (!StringUtils.isPhoneNumber(phone))
        {
            UIUtils.showToast(mActivity, "请输入合法的手机号码!");
            return false;
        }
        else if (TextUtils.isEmpty(sex))
        {
            UIUtils.showToast(mActivity, "请输入性别");
            return false;
        }
        else if (TextUtils.isEmpty(age))
        {
            UIUtils.showToast(mActivity, "请输入年龄");
            return false;
        }
        else if (!Pattern.compile("[0-9]*").matcher(age).matches())
        {
            UIUtils.showToast(mActivity, "请输入合法年龄");
            return false;
        }
        else if (TextUtils.isEmpty(use))
        {
            UIUtils.showToast(mActivity, "请输入借款用途");
            return false;
        }
//        else if (TextUtils.isEmpty(unit))
//        {
//            UIUtils.showToast(mActivity, "请输入承接公司");
//            return false;
//        }
        else if (TextUtils.isEmpty(money))
        {
            UIUtils.showToast(mActivity, "请输入借款金额");
            return false;
        }
        else if (TextUtils.isEmpty(code))
        {
            UIUtils.showToast(mActivity, "请输入验证码");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_right_public:      // 申请流程
                Intent intent = new Intent(getActivity(), BorrowFlowActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_borrow_item_edit:   // 展开选择性别
                doAnimation(iv_sex_arrows);
                PoupupSelectView mPopupWindow = new PoupupSelectView(getContext(), mListData, edt_user_register_name);
                mPopupWindow.setDatas(mListData, "选择性别", edt_user_register_name);
                mPopupWindow.showPopupWindow();
                mPopupWindow.setOnListItemClickListener(new PoupupSelectView.OnListItemListener()
                {
                    @Override
                    public void onListItemClick(AdapterView<?> parent, TextView view, int position, long id)
                    {
                        String text = view.getText().toString();
                        edt_user_register_sex.setText(text);
                    }
                });
                mPopupWindow.setOnDisMissListener(new PoupupSelectView.OnDisMissListener()
                {
                    @Override
                    public void onDisMissListener()
                    {
                        RotateAnimation animation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(350);//设置动画持续时间
                        animation.setFillAfter(true);
                        iv_sex_arrows.startAnimation(animation);
                    }
                });
                break;
            case R.id.btn_submit:           // 提交申请
                if (checkInfo())
                {
                    dialog = new QuitDialog(mActivity, "提示", "是否提交借款申请?", "取消", "确定");
                    dialog.setCancelListener(this);
                    dialog.setEnterListener(this);
                    dialog.show();
                }
                break;
            case R.id.iv_down_chengjie:     // 承接公司右边箭头按钮
                chengjieData.clear();
                if (mData == null || mData.size() == 0)
                {
                    return;
                }
                for (int i = 0; i < mData.size(); i++)
                {
                    chengjieData.add(mData.get(i).getName() + "(" + mData.get(i).getLocation() + ")");
                }
                doAnimation(iv_chengjie_unit_down);
                PoupupSelectView chengjiePop = new PoupupSelectView(getContext(), chengjieData, input_chengjie_company);
                chengjiePop.setDatas(chengjieData, "选择承接公司", input_chengjie_company);
                chengjiePop.showPopupWindow();
                chengjiePop.setOnListItemClickListener(new PoupupSelectView.OnListItemListener()
                {
                    @Override
                    public void onListItemClick(AdapterView<?> parent, TextView view, int position, long id)
                    {
                        input_chengjie_company.setText(mData.get(position).getName());
                        unit_id = mData.get(position).id;
                    }
                });
                chengjiePop.setOnDisMissListener(new PoupupSelectView.OnDisMissListener()
                {
                    @Override
                    public void onDisMissListener()
                    {
                        RotateAnimation animation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(350);//设置动画持续时间
                        animation.setFillAfter(true);
                        iv_chengjie_unit_down.startAnimation(animation);
                    }
                });

                break;
            case R.id.res_imverificationcode:  // 点击图形验证码
                HttpManager.getInstance().displayImageCode(res_imverificationcode, null, null);
                break;
            case R.id.tv_private_policy:
            {
                Intent printent = new Intent(mActivity, PrivateActivity.class);
                printent.putExtra("bannerTitle", "隐私政策");
                printent.putExtra("data", UrlUtil.getPrivateUrl());
                startActivity(printent);
                break;
            }
            default:
                break;
        }
    }

    //箭头旋转动画
    private void doAnimation(ImageView mIvarrow)
    {
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(350);//设置动画持续时间
        animation.setFillAfter(true);
        mIvarrow.startAnimation(animation);
    }

    @Override
    public void onCancelListener()  // 取消
    {
        dialog.dismiss();
    }

    @Override
    public void onEnterListener()   // 确定
    {
        dialog.dismiss();
        params = getParams();
        HttpManager.getInstance().doPost(ServiceName.CGKX_ADDRESERVEBORROW, params, new HttpCallBack<ServerResponse>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.showToast("申请失败");
            }

            @Override
            public void getHttpCallBack(ServerResponse rsp)
            {
                if (null != rsp)
                {
                    if ("000000".equals(rsp.getCode()))
                    {
                        UIUtils.showToast("申请成功");
                        // 申请成功 清空数据
                        cleandata();
                    }
                    else
                    {
                        UIUtils.showToast(rsp.getMsg());
                    }
                }
                else
                {
                    UIUtils.showToast("提交失败！请重试");
                }
            }
        });
    }

    public void cleandata()
    {
        edt_user_register_name.setText("");
        register_input_resetpass_pass_affirm.setText("");
        edt_user_register_sex.setText("");//性别
        input_resetpass_verifycode.setText("");
        input_borrow_use.setText("");
        input_chengjie_company.setText("");//承接公司
        input_borrow_money.setText("");
        input_register_verifycode.setText("");

    }

    /**
     * @return 返回债权转让参数
     */
    public SubmitApplyBean getParams()
    {
        SubmitApplyBean params = new SubmitApplyBean();
        params.setRealName(name);
        params.setPhoneNumber(phone);
        if ("男".equals(sex))
        {
            params.setSexint(1);
        }
        else if ("女".equals(sex))
        {
            params.setSexint(2);
        }
        params.setAgeint(Integer.parseInt(age));
        params.setUsages(use);
        params.setUndertakeCompany(unit_id);
        params.setBorrowAmount(Double.parseDouble(money));
        params.setReserve1(code);
        return params;
    }
}
