package com.mingjie.jf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserInfo;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.ActivityForResultUtil;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.Accountset_item;
import com.mingjie.jf.view.ToggleButton;
import com.mingjie.jf.widgets.QuitDialog;
import com.mingjie.jf.widgets.RefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 账户设置
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AccountSetActivity extends BaseActivity implements View.OnClickListener, ToggleButton.OnToggleChanged,
        QuitDialog.OnEnterListener, QuitDialog.OnCancelListener, SwipeRefreshLayout.OnRefreshListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.account_custody)
    Accountset_item accountCustody;//存管账户
    @BindView(R.id.realName)
    Accountset_item realName;//实名认证
    @BindView(R.id.safephone)
    Accountset_item safephone;//安全手机
    @BindView(R.id.bindEmail)
    Accountset_item bindEmail;//绑定邮箱
    @BindView(R.id.modify_loginPass)
    Accountset_item modifyLoginPass;//登录密码
    @BindView(R.id.iv_accountset_status)
    ImageView ivAccountsetStatus;
    @BindView(R.id.tv_accountset_title)
    TextView tvAccountsetTitle;
    @BindView(R.id.toggle_btn)
    ToggleButton mToggleBtn;//手势密码按钮
    @BindView(R.id.accountset_picpass)
    RelativeLayout accountsetPicpass;//手势密码相对
    @BindView(R.id.modify_picpass)
    Accountset_item mModifyPicPass;//修改手势密码
    @BindView(R.id.more)
    Accountset_item more;//更多
    @BindView(R.id.logout)
    Accountset_item logout;//账户退出
    @BindView(R.id.swipe_container)
    RefreshLayout mRefreshView;
    @BindView(R.id.my_service)
    Accountset_item myService;//选择客服

    private boolean isLock;
    private QuitDialog quitDialog;
    private UserVoValible user;
    private UserInfo rdetail;

    protected void initView()
    {
        setContentView(R.layout.activity_set_account);
        ButterKnife.bind(this);
        user = CacheUtils.getUserinfo(this);

        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("账户设置");
        accountCustody.setOnClickListener(this);
        realName.setOnClickListener(this);
        safephone.setOnClickListener(this);
        bindEmail.setOnClickListener(this);
        modifyLoginPass.setOnClickListener(this);
        mModifyPicPass.setOnClickListener(this);// 修改手势密码
        more.setOnClickListener(this);
        logout.setOnClickListener(this);
        myService.setOnClickListener(this);//客服福利
        mRefreshView.setOnRefreshListener(this);

    }

    protected void initData()
    {
        //   是否开启了手势密码
        isLock = CacheUtils.getBoolean(this, Constants.LOCK, false);
        if (isLock)
        {
            mToggleBtn.setToggleOn();
            mModifyPicPass.setVisibility(View.VISIBLE);
        }
        else
        {
            mToggleBtn.setToggleOff();
            mModifyPicPass.setVisibility(View.GONE);
            isLock = false;
        }
        mToggleBtn.setOnToggleChanged(this);
        UIUtils.showLoading(this);
        getResult();

        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }

    }

    private void getResult()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.dimissLoading();
            UIUtils.showToast(AccountSetActivity.this, R.string.network_unused_retry_pls);
            finish();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", user.getId());
        HttpManager.getInstance().doPost(ServiceName.CGKX_USERINFO, params, new
                HttpCallBack<ServerResponse<UserInfo>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        UIUtils.dimissLoading();
                        UIUtils.showToast(AccountSetActivity.this, R.string.dataError);
                        closeRefresh();
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<UserInfo> rsp)
                    {
                        UIUtils.dimissLoading();
                        if (rsp == null)
                        {
                            UIUtils.showToast(AccountSetActivity.this, R.string.service_err);
                            closeRefresh();
                            return;
                        }
                        if (null != rsp.getCode() && !rsp.getCode().equals("000000"))
                        {
                            showToast(rsp.getMsg());
                            closeRefresh();
                        }
                        else
                        {
                            if (null == rsp.getData())
                            {
                                UIUtils.dimissLoading();
                                UIUtils.showToast(AccountSetActivity.this, R.string.dataError);
                            }
                            else if (null != rsp.getData())
                            {
                                rdetail = rsp.getData();
                                // CfLog.i("---" + rdetail);
                                showOpenCtDetail(rdetail);
                            }
                            closeRefresh();
                        }
                    }
                });
    }

    //更新界面
    private void showOpenCtDetail(UserInfo rdetail)
    {
        if (null != rdetail.getUser())
        {
            if (TextUtils.isEmpty(rdetail.getUser().getAcno()))
            {
                accountCustody.setStateText("未开通");
                accountCustody.setActionText("开通");
            }
            else
            {
                accountCustody.setStateText(rdetail.getUser().getAcno());
            }
            if (TextUtils.isEmpty(rdetail.getUser().getRealname()) || 0 == rdetail.getUserStates()
                    .getIsFundDepository())
            {
                realName.setStateText("未认证");
                realName.setActionText("认证");
            }
            else
            {
                if (rdetail.getUser().getRealname().length() == 1)
                {
                    realName.setStateText(rdetail.getUser().getRealname());
                    return;
                }
                realName.setStateText(getStarString(rdetail.getUser().getRealname(), 1, rdetail.getUser()
                        .getRealname()
                        .length()));
            }
            if (TextUtils.isEmpty(rdetail.getUser().getPhone()))
            {
                safephone.setStateText("未绑定");
                safephone.setActionText("绑定");
            }
            else
            {
                safephone.setStateText(rdetail.getUser().getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            }
            if (TextUtils.isEmpty(rdetail.getUser().getEmail()))
            {
                bindEmail.setStateText("未绑定");
                bindEmail.setActionText("绑定");
            }
            else
            {
                bindEmail.setStateText(rdetail.getUser().getEmail().replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\" +
                        ".[a-z]+)?)", "$1****$3$4"));
            }
            if (TextUtils.isEmpty(rdetail.getUser().getServiceId()))
            {
                myService.setStateText("未选择");
                myService.setActionText("选择");
            }
            else
            {
                myService.setStateText(rdetail.getUser().getServiceName());
            }
        }

    }

    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     *
     * @param content 传入的字符串
     * @param begin   开始位置
     * @param end     结束位置
     * @return
     */
    private static String getStarString(String content, int begin, int end)
    {

        if (begin >= content.length() || begin < 0)
        {
            return content;
        }
        if (end > content.length() || end < 0)
        {
            return content;
        }
        if (begin >= end)
        {
            return content;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = begin; i < end; i++)
        {
            sb.append("*");
        }
        String starStr = sb.toString();
        return content.substring(0, begin) + starStr + content.substring(end, content.length());

    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content  传入的字符串
     * @param frontNum 保留前面字符的位数
     * @param endNum   保留后面字符的位数
     * @return 带星号的字符串
     */

    private static String getStarString2(String content, int frontNum, int endNum)
    {

        if (frontNum >= content.length() || frontNum < 0)
        {
            return content;
        }
        if (endNum >= content.length() || endNum < 0)
        {
            return content;
        }
        if (frontNum + endNum >= content.length())
        {
            return content;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < (content.length() - frontNum - endNum); i++)
        {
            sb.append("*");
        }
        String starStr = sb.toString();
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }

    private void closeRefresh()
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

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.account_custody:
                //如果已经开通存管，不能让用户再次进入开通存管页面
//                CfLog.d("rdetail.getUser().getIsFundDepository()=" + rdetail.getUser().getIsFundDepository());
                if (null != rdetail && null != rdetail.getUserStates())
                {
                    if (rdetail.getUserStates().getIsFundDepository() == 1)
                    {
                        ToastUtil.showToast(AccountSetActivity.this, "已开通存管账户！");
                        Intent realName = new Intent(this, RealNameActivity.class);
                        realName.putExtra("acno", rdetail.getUser().getAcno());
                        realName.putExtra("realname", rdetail.getUser().getRealname());
                        realName.putExtra("idnumber", rdetail.getUser().getIdnumber());
                        startActivity(realName);
                    }
                    else//如果没开通存管，调到开通存管页面
                    {
                        //跳转开通存管账户
                        Intent intentdeposit = new Intent(this, DepositActivity.class);
                        startActivity(intentdeposit);
                    }
                }
                else
                {
                    UIUtils.showToast(this, R.string.dataError);
                }
                break;
            case R.id.realName:
                if (null != rdetail.getUserStates())
                {
                    if (rdetail.getUserStates().getIsFundDepository() == 1)
                    {
                        Intent realName = new Intent(this, RealNameActivity.class);
                        realName.putExtra("acno", rdetail.getUser().getAcno());
                        realName.putExtra("realname", rdetail.getUser().getRealname());
                        realName.putExtra("idnumber", rdetail.getUser().getIdnumber());
                        startActivity(realName);
                    }
                    else
                    {
                        startActivity(new Intent(this, DepositActivity.class));
                    }
                }
                else
                {
                    UIUtils.showToast(this, R.string.dataError);
                }
                break;
            case R.id.safephone:
                if (null != rdetail.getUser())
                {
                    if (rdetail.getUser().getPhone() != null)
                    {
                        Intent safephone = new Intent(this, SafePhoneActivity.class);
                        safephone.putExtra("phone", rdetail.getUser().getPhone());
                        startActivity(safephone);
                    }
                }
                else
                {
                    UIUtils.showToast(this, R.string.dataError);
                }
                break;
            case R.id.bindEmail:
                if (null != rdetail.getUser())
                {
                    Intent bindEmail = new Intent(this, ConfirmBindActivity.class);
                    bindEmail.putExtra("emaile", rdetail.getUser().getEmail());
                    bindEmail.putExtra("phone", rdetail.getUser().getPhone());
                    startActivity(bindEmail);
                }
                else
                {
                    UIUtils.showToast(this, R.string.dataError);
                }
                break;
            case R.id.modify_loginPass:
                startActivity(new Intent(this, ModifyPassActivity.class));
                break;
            case R.id.toggle_btn:
                break;
            case R.id.accountset_picpass:
                break;
            case R.id.modify_picpass:
                Intent setting = new Intent(this, UnlockGesturePasswordActivity.class);
                setting.putExtra("resetpass", "1");
                startActivity(setting);
                break;
            case R.id.more:
                startActivity(new Intent(this, MoreActivity.class));
                break;
            case R.id.logout://退出登录
                quitDialog = new QuitDialog(this, "温馨提示", "请问您现在要注销账户吗？", "取消", "退出");
                quitDialog.setEnterListener(this);
                quitDialog.setCancelListener(this);
                quitDialog.show();
                break;
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.my_service://选择客服列表
                if (null != rdetail.getUser())
                {
                        Intent intent = new Intent(this, ServicePeoPleActivity.class);
                        intent.putExtra("serviceId", rdetail.getUser().getServiceId());
                        startActivity(intent);
                }
                else
                {
                    UIUtils.showToast(this, R.string.dataError);
                }
                break;
            default:
                break;
        }
    }

    public void onEvent(Event event)
    {
        //账户设置页面，涉及账户数据，现发送的event都为刷新账户，所以这里直接刷新
        //        mRefreshView.setRefreshing(true);
        if (event.eventType == Event.OPEN_ACCOUNT || event.eventType == Event.REFRESH_ACCOUNT)
        {
            onRefresh();
        }
        else if (event.eventType == Event.LOGOUT)
        {
            this.finish();
        }
    }

    @Override
    public void onToggle(boolean on)
    {
        if (on)
        {
            startActivityForResult(new Intent(this, CreateGesturePasswordActivity.class), ActivityForResultUtil
                    .REQUESTCODE_SETPICPASS);
        }
        else
        {
            CacheUtils.setBoolean(this, Constants.LOCK, false);
            isLock = false;
            mToggleBtn.setToggleOff();
            mModifyPicPass.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityForResultUtil.REQUESTCODE_SETPICPASS)
        {
            isLock = data == null ? false : data.getBooleanExtra("isSuccess", false);
            if (isLock)
            {
                mModifyPicPass.setVisibility(View.VISIBLE);
                mToggleBtn.setToggleOn();
            }
            else
            {
                mModifyPicPass.setVisibility(View.GONE);
                mToggleBtn.setToggleOff();
            }
            CacheUtils.setBoolean(this, Constants.LOCK, isLock);
        }
    }

    @Override
    public void onEnterListener()
    {
        UIUtils.showLoading(this);

        logout();

    }

    private void logout()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            showToast(R.string.network_unused_retry_pls);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", user.getId());
        CfLog.i("---" + user.getId());

        HashMap<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        MobclickAgent.onEvent(this, ServiceName.APP_USER_LOGOUT, map);

        HttpManager.getInstance().doPost(ServiceName.APP_USER_LOGOUT, params, new
                HttpCallBack<ServerResponse<String>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        UIUtils.dimissLoading();
                        UIUtils.showToast(AccountSetActivity.this, R.string.dataError);
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<String> rsp)
                    {
                        UIUtils.dimissLoading();
                        if (rsp == null)
                        {
                            UIUtils.showToast(AccountSetActivity.this, R.string.service_err);
                        }
                        else if (!"000000".equals(rsp.getCode()))
                        {
                            showToast(rsp.getMsg());
                        }
                        else if ("000000".equals(rsp.getCode()))
                        {

                            // 友盟账号统计登出
                            MobclickAgent.onProfileSignOff();

                            saveGesture();
//                            HttpManager.getInstance().userCookie = "";
                            Intent intent = new Intent(AccountSetActivity.this, LoginActivity.class);
                            intent.putExtra("logout", true);
                            startActivity(intent);
                            CacheUtils.clearUserInfo();
                            CfLog.i("---isLogin" + CacheUtils.isLogin());
                            CfLog.i("---id=" + CacheUtils.getUserinfo(AccountSetActivity.this).getId());
                            quitDialog.dismiss();

                            Event event = new Event();
                            event.eventType = Event.LOGOUT;
                            EventBus.getDefault().post(event);
                            finish();
                        }
                        else
                        {
                            showToast(R.string.dataError);
                        }
                    }
                });
    }

    /**
     * 保存手势密码
     */
    private void saveGesture()
    {
        //将手势密码的状态保存到对应用户下
        CacheUtils.setBoolean(this, CacheUtils.getUserinfo(this).getName() + "_" + Constants.IS_OPEN_GESTURE,
                CacheUtils.getBoolean(this, Constants.LOCK));
    }

    @Override
    public void onCancelListener()
    {
        quitDialog.dismiss();
    }

    @Override
    public void onRefresh()
    {
        mRefreshView.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue, R.color.red);
        mRefreshView.setRefreshing(true);
        getResult();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
    }
}

