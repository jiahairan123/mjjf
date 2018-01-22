package com.mingjie.jf.action;

import android.content.Intent;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.NextSafePhoneActivity;
import com.mingjie.jf.activity.SafePhoneActivity;
import com.mingjie.jf.bean.SafePhoneActionVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.UIUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by QinSiYi on 2016-8-25.
 */
public class SafePhoneAction
{
    private SafePhoneActivity act;
    private TimeCount mTiemTimeCount;

    public SafePhoneAction(SafePhoneActivity act, TimeCount mTiemTimeCount)
    {
        this.act = act;
        this.mTiemTimeCount = mTiemTimeCount;
    }

    public void safePhonenextAction(final SafePhoneActionVo obj)
    {

        MobclickAgent.onEvent(act, ServiceName.RESET_MODIFY_MOBILE);

        HttpManager.getInstance().doPost(ServiceName.RESET_MODIFY_MOBILE, obj, new
                HttpCallBack<ServerResponse<String>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {

                        Toast.makeText(act, R.string.dataError, Toast.LENGTH_SHORT).show();
                        mTiemTimeCount.cancel();
                        mTiemTimeCount.onFinish();

                    }

                    @Override
                    public void getHttpCallBack(ServerResponse rsp)
                    {
                        if (null == rsp)
                        {
                            Toast.makeText(act, R.string.service_err, Toast.LENGTH_SHORT).show();
                            mTiemTimeCount.cancel();
                            mTiemTimeCount.onFinish();
                            return;
                        }
                        if (null != rsp.getCode() && !"000000".equals(rsp.getCode()))
                        {
                            UIUtils.showToast(act, rsp.getMsg());
                            mTiemTimeCount.cancel();
                            mTiemTimeCount.onFinish();
                        }
                        else if (null != rsp.getCode() && "000000".equals(rsp.getCode()))
                        {
                            act.startActivity(new Intent(act, NextSafePhoneActivity.class));
                            act.finish();
                        }
                        else
                        {
                            Toast.makeText(act, R.string.service_err, Toast.LENGTH_SHORT).show();
                            mTiemTimeCount.cancel();
                            mTiemTimeCount.onFinish();
                        }
                    }
                });
    }
}
