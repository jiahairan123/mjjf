package com.mingjie.jf.action;

import android.content.Intent;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.ChangePasswordTwoActivity;
import com.mingjie.jf.activity.LoginActivity;
import com.mingjie.jf.bean.ForgetPassWordTwoSureActionVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;

/**
 * Created by QinSiYi on 2016-8-24.
 */
public class ForgetPassWordTwoSureAction
{

    private ChangePasswordTwoActivity act;

    public ForgetPassWordTwoSureAction(ChangePasswordTwoActivity act)
    {
        this.act = act;
    }

    //忘记密码第二步，获取短信验证码
    public void forgetPassWordTwoSureAction(final ForgetPassWordTwoSureActionVo obj)
    {

        HttpManager.getInstance().doPost(ServiceName.CGKX_DOFORGETPWD2, obj, new HttpCallBack<ServerResponse<String>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.dimissLoading();
                Toast.makeText(act, R.string.dataError, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getHttpCallBack(ServerResponse rsp)
            {
                UIUtils.dimissLoading();
                if (null != rsp)
                {
                    if ("000000".equals(rsp.getCode()))
                    {
                        Toast.makeText(act, "修改成功", Toast.LENGTH_SHORT).show();

                        act.startActivity(new Intent(act, LoginActivity.class));
                        act.finish();
                    }
                    else
                    {
                        Toast.makeText(act, "提交失败，请重试！\n" + rsp.getMsg(), Toast
                                .LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(act, R.string.service_err, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}

