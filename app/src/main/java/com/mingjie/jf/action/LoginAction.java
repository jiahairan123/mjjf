package com.mingjie.jf.action;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.LoginActivity;
import com.mingjie.jf.activity.MainActivity;
import com.mingjie.jf.application.BaseApplication;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.LoginActionVo;
import com.mingjie.jf.bean.UserVo;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Created by QinSiYi on 2016-8-10.
 */
public class LoginAction {
    private Activity act;

//     public String information;

    //错误次数
    //    int wrong = 0;
    //    private boolean isLogin;

    public LoginAction(Activity act) {
        this.act = act;
    }

    public void loginAction(final LoginActionVo obj, final int startType) {

        HttpManager.getInstance().doPost(ServiceName.APP_USER_LOGIN, obj, new HttpCallBack<UserVo>() {
            @Override
            public void getHttpCallNull(String str) {
                UIUtils.dimissLoading();
                Toast.makeText(act, R.string.dataError, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getHttpCallBack(UserVo rsp) {
                UIUtils.dimissLoading();

//                information =  rsp.getData().getUser().getWealthManagerFlg();

                if (null != rsp && null != rsp.getCode()) {
                    if ("000000".equals(rsp.getCode())) {
                        if (null == rsp.getData() || null == rsp.getData().getUser()) {
                            Toast.makeText(act, "登录异常，请重试！", Toast.LENGTH_SHORT).show();
                            UIUtils.dimissLoading();
                            return;
                        }


                        HttpManager.getInstance().userCookie = "";
                        if (null != rsp.getData().getSessionId()) {
                            HttpManager.getInstance().userCookie = rsp.getData().getSessionId();
                        }


                        //如果该用户在该手机上有缓存手势密码
                        if (CacheUtils.getBoolean(act, rsp.getData().getUser().getName() + "_" + Constants
                                .IS_OPEN_GESTURE)) {
                            CacheUtils.setBoolean(act, Constants.LOCK, true);
                            BaseApplication.getLockPatternUtils().saveLockPattern(
                                    CacheUtils.getGesturePwd(rsp.getData().getUser().getName()), rsp.getData()
                                            .getUser().getName()
                            );
                        }

                        //保存用户名和密码，下次自动登录
                        CacheUtils.setString(act, Constants.USER_NAME, obj.getName());
                        CacheUtils.setString(act, Constants.PASSWORD, obj.getPassword());

                        CacheUtils.saveUserInfo(act, rsp);
                        if (startType == LoginActivity.START_TYEP_2MAIN) {
                            Intent intent = new Intent(act, MainActivity.class);
                            intent.putExtra(MainActivity.KEY_NEED_AUTOLOGIN, false);
                            act.startActivity(intent);
                        }
                        act.finish();
                        CacheUtils.setBoolean(act, Constants.IS_LOGIN, true);

                        // 友盟统计账号ID
                        MobclickAgent.onProfileSignIn(rsp.getData().getUser().getId());
                        HashMap<String, String> map = new HashMap<>();
                        map.put("name", rsp.getData().getUser().getName());
                        map.put("HHmm", TimeFormatUtil.getSimpleTime());
                        map.put("HH", TimeFormatUtil.getHour());
                        MobclickAgent.onEvent(act, ServiceName.APP_USER_LOGIN, map);

                        //全局通知登录成功
                        Event event = new Event();
                        event.eventType = Event.LOGIN_SUCCESS;
                        EventBus.getDefault().post(event);
                        CfLog.d("登录成功-发送全局通知");
                        UIUtils.dimissLoading();
                    } else if ("100002".equals(rsp.getCode()) || ("100003".equals(rsp.getCode()) && null != rsp.getData
                            () && "needVcode".equals(rsp.getData().getIsNeedVcode()))) {
                        //                        if (rsp.getData().getIsNeedVcode().equals("needVcode"))
                        //                        {
                        //登录页面可能导致图形验证码出现
                        if (act instanceof LoginActivity) {
                            ((LoginActivity) act).wrongaccount();
                        }
                        Toast.makeText(act, rsp.getMsg(), Toast.LENGTH_SHORT).show();
                        UIUtils.dimissLoading();
                    } else {
                        Toast.makeText(act, rsp.getMsg(), Toast.LENGTH_SHORT).show();
                        UIUtils.dimissLoading();
                    }
                } else {
                    Toast.makeText(act, R.string.service_err, Toast.LENGTH_SHORT).show();
                    UIUtils.dimissLoading();
                }
            }
        });
    }
}
