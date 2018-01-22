package com.mingjie.jf.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.ApplyStateVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.MateralButton;
import com.mingjie.jf.widgets.QuitDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 创业小伙伴
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-10 11:26
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class VenturePartnersActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener, QuitDialog.OnCancelListener, QuitDialog.OnEnterListener
{
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.tv_username)
    TextView tv_username;           // 用户姓名
    @BindView(R.id.tv_truename)
    TextView tv_truename;           // 真实姓名
    @BindView(R.id.tv_phonenumber)
    TextView tv_phonenumber;        //手机号码
    @BindView(R.id.btn_submit)
    MateralButton btn_submit;       // 提交按钮
    @BindView(R.id.ll_user_info)
    LinearLayout ll_user_info;    // 用户信息布局
    @BindView(R.id.tv_apply_state)
    TextView tv_apply_state;        // 申请状态textview
    @BindView(R.id.iv_apply_state)
    ImageView iv_apply_state;       // 申请状态图片
    @BindView(R.id.ll_apply_state)
    LinearLayout ll_apply_state;    // 状态提示文字布局
    @BindView(R.id.celebrate)
    ImageView celebrate;
    @BindView(R.id.ll_tishi_text)
    LinearLayout ll_tishi_text;     // 温馨提示上方数据信息
    @BindView(R.id.tv_direct_recommend)
    TextView tv_direct_recommend;       // 直接推荐提成
    @BindView(R.id.tv_indirect_recommend)
    TextView tv_indirect_recommend;     // 间接推荐提成
    @BindView(R.id.swipe_account_fragment)
    SwipeRefreshLayout swipe_account_fragment; //刷新组件
    @BindView(R.id.tv_remark)
    TextView tv_remark;                        //备注信息

    private UserVoValible userinfo; // 用户id
    private int openState;          // 开通状态
    private QuitDialog dialog;
    private String realName;        //真实姓名
    private LinearLayout llRemark;//温馨提示

    //    @Override
    //    protected void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_venturepartner);
    //        ButterKnife.bind(this);
    //        iniview();
    //        initData();
    //    }


    @Override
    public void onRefresh()
    {
        initData();
    }

    protected void initView()
    {
        setContentView(R.layout.activity_venturepartner);
        ButterKnife.bind(this);
        //        iniview();
        //        initData();
        ivLeftPublic.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        swipe_account_fragment.setOnRefreshListener(this);
        llRemark=(LinearLayout)findViewById(R.id.ll_remark);
    }

    protected void initData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, R.string.notNet);
            return;
        }
        UIUtils.showLoading(this);
        realName = getIntent().getStringExtra("realname");
        tvContentPublic.setText("创业小伙伴");
        userinfo = CacheUtils.getUserinfo(this);
        Map<String, String> params = new HashMap<>();
        params.put("userId", userinfo.getId());
        HttpManager.getInstance().doPost(ServiceName.CGKX_COMMISSIONFRIEND, params, new HttpCallBack<ApplyStateVo>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.showToast(getBaseContext(), R.string.dataError);
                UIUtils.dimissLoading();
                swipe_account_fragment.setRefreshing(false);
            }

            @Override
            public void getHttpCallBack(ApplyStateVo state)
            {
                UIUtils.dimissLoading();
                swipe_account_fragment.setRefreshing(false);
                if (null != state)
                {
                    if ("000000".equals(state.code))
                    {
                        UIUtils.showToast("刷新成功");
                        // 判断存管开通状态
                        if (state.data.isFundDepository == 0)     // 未开通存管账号
                        {
                            openState = 0;
                            btn_submit.setVisibility(View.VISIBLE);
                            ll_apply_state.setVisibility(View.VISIBLE);
                            ll_user_info.setVisibility(View.VISIBLE);
                            tv_username.setText(userinfo.getName());
                            tv_phonenumber.setText(userinfo.getPhone());
                        }
                        else if (state.data.isFundDepository == 1)   // 已开通存管
                        {
                            openState = 1;
                            btn_submit.setVisibility(View.VISIBLE);
                            ll_apply_state.setVisibility(View.VISIBLE);
                            ll_user_info.setVisibility(View.VISIBLE);
                            tv_username.setText(userinfo.getName());
                            tv_truename.setText(realName);
                            tv_phonenumber.setText(userinfo.getPhone());
                        }
                        else
                        {
                            tv_username.setText("");
                            tv_truename.setText("");
                            tv_phonenumber.setText("");
                        }
                        // 判断申请状态
                        if (state.data.stateInt == 1)  // 审核中
                        {
                            celebrate.setVisibility(View.GONE);
                            ll_user_info.setVisibility(View.GONE);
                            ll_apply_state.setVisibility(View.VISIBLE);
                            tv_apply_state.setText("您提交的申请正在审核中,请耐心等待!");
                            iv_apply_state.setImageResource(R.mipmap.recommend_explain);
                            btn_submit.setVisibility(View.GONE);
                            ll_tishi_text.setVisibility(View.VISIBLE);
                            tv_remark.setVisibility(View.GONE);
                            tv_direct_recommend.setText((state.data.directCommissionRate * 100) + "%");
                            tv_indirect_recommend.setText((state.data.indirectCommissionRate * 100) + "%");
                            llRemark.setVisibility(View.GONE);
                        }
                        else if (state.data.stateInt == 2) // 审核通过
                        {
                            celebrate.setVisibility(View.VISIBLE);
                            ll_user_info.setVisibility(View.GONE);
                            ll_apply_state.setVisibility(View.VISIBLE);
                            tv_apply_state.setText("恭喜您已成为创业小伙伴!");
                            iv_apply_state.setImageResource(R.mipmap.recommend_explain);
                            btn_submit.setVisibility(View.GONE);
                            ll_tishi_text.setVisibility(View.VISIBLE);
                            tv_remark.setVisibility(View.GONE);
                            llRemark.setVisibility(View.GONE);
                            if (null == state.data.note || state.data.note.equals(""))
                            {
                                tv_remark.setVisibility(View.GONE);
                            }
                            else
                            {
                                tv_remark.setVisibility(View.VISIBLE);
                                tv_remark.setText("备注：" + state.data.note);
                            }
                            tv_direct_recommend.setText((state.data.directCommissionRate * 100) + "%");
                            tv_indirect_recommend.setText((state.data.indirectCommissionRate * 100) + "%");
                        }
                        else if (state.data.stateInt == 3) // 审核拒绝
                        {
                            celebrate.setVisibility(View.GONE);
                            ll_user_info.setVisibility(View.VISIBLE);
                            ll_apply_state.setVisibility(View.VISIBLE);
                            tv_apply_state.setText("审核拒绝!");
                            iv_apply_state.setImageResource(R.mipmap.apply_data);
                            btn_submit.setVisibility(View.VISIBLE);
                            ll_tishi_text.setVisibility(View.GONE);
                            if (null == state.data.note || state.data.note.equals(""))
                            {
                                tv_remark.setVisibility(View.GONE);
                            }
                            else
                            {
                                tv_remark.setVisibility(View.VISIBLE);
                                tv_remark.setText("备注：" + state.data.note);
                            }
                        }
                        else if (state.data.stateInt == 4)  // 审核失效
                        {
                            celebrate.setVisibility(View.GONE);
                            ll_user_info.setVisibility(View.VISIBLE);
                            ll_apply_state.setVisibility(View.VISIBLE);
                            tv_apply_state.setText("审核失效!");
                            iv_apply_state.setImageResource(R.mipmap.apply_data);
                            btn_submit.setVisibility(View.VISIBLE);
                            ll_tishi_text.setVisibility(View.GONE);
                            if (null == state.data.note || state.data.note.equals(""))
                            {
                                tv_remark.setVisibility(View.GONE);
                            }
                            else
                            {
                                tv_remark.setVisibility(View.VISIBLE);
                                tv_remark.setText("备注：" + state.data.note);
                            }
                        }
                        else if (state.data.stateInt == 0)   // 未申请过创业小伙伴
                        {
                            celebrate.setVisibility(View.VISIBLE);
                            ll_user_info.setVisibility(View.VISIBLE);
                            ll_apply_state.setVisibility(View.INVISIBLE);
                            btn_submit.setVisibility(View.VISIBLE);
                            ll_tishi_text.setVisibility(View.GONE);
                            tv_remark.setVisibility(View.GONE);
                        }
                    }
                    else
                    {
                        UIUtils.showToast(state.msg);
                    }
                }
                else
                {
                    UIUtils.showToast(getBaseContext(), R.string.service_err);
                }
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.btn_submit:        // 申请按钮
                if (openState == 0)        // 未开通存管账号
                {
                    dialog = new QuitDialog(this, "温馨提示", "请先开通存管账号", "去开通", "取消");
                    dialog.setCancelListener(this);
                    dialog.setEnterListener(this);
                    dialog.show();
                }
                else if (openState == 1)  // 已开通存管
                {
                    UIUtils.showLoading(this);
                    // 提交申请
                    Map<String, String> params = new HashMap<>();
                    params.put("userId", userinfo.getId());
                    HttpManager.getInstance().doPost(ServiceName.CGKX_APPLYCOMMISSION, params, new
                            HttpCallBack<ServerResponse>() // 创业小伙伴发起申请
                            {
                                @Override
                                public void getHttpCallNull(String str)
                                {
                                    UIUtils.dimissLoading();
                                    UIUtils.showToast("网络异常");
                                }

                                @Override
                                public void getHttpCallBack(ServerResponse rsp)
                                {
                                    UIUtils.dimissLoading();
                                    if (null != rsp)
                                    {
                                        if ("000000".equals(rsp.getCode()))
                                        {
                                            initData();
                                        }
                                        else
                                        {
                                            UIUtils.showToast(rsp.getMsg());
                                        }
                                    }
                                    else
                                    {
                                        UIUtils.showToast("请求为空");
                                    }
                                }
                            });
                }
                break;
            default:
                CfLog.i("default");
                break;
        }
    }

    @Override
    public void onCancelListener()  // 取消
    {
        dialog.dismiss();
        Intent intent = new Intent(this, DepositActivity.class);   // 调转开通存管界面
        startActivity(intent);
    }

    @Override
    public void onEnterListener()  // 确定
    {
        dialog.dismiss();

    }
}

