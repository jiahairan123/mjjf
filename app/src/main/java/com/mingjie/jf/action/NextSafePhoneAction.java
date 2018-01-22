package com.mingjie.jf.action;

import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.NextSafePhoneActivity;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.SafePhoneActionVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.UIUtils;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;

/**
 * Created by QinSiYi on 2016-8-25.
 */
public class NextSafePhoneAction
{
    private NextSafePhoneActivity act;
    private TimeCount mTiemTimeCount;

    public NextSafePhoneAction(NextSafePhoneActivity act, TimeCount mTiemTimeCount)
    {
        this.act = act;
        this.mTiemTimeCount = mTiemTimeCount;
    }

    public void safePhonenextAction(final SafePhoneActionVo obj)
    {
        MobclickAgent.onEvent(act, ServiceName.RESET_MODIFY_MOBILE2);

        HttpManager.getInstance().doPost(ServiceName.RESET_MODIFY_MOBILE2, obj, new
                HttpCallBack<ServerResponse<String>>()
        {

            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.dimissLoading();
                Toast.makeText(act, R.string.dataError, Toast.LENGTH_SHORT).show();
                mTiemTimeCount.cancel();
                mTiemTimeCount.onFinish();
            }

            @Override
            public void getHttpCallBack(ServerResponse rsp)
            {
                UIUtils.dimissLoading();
                if (null == rsp)
                {
                    Toast.makeText(act, R.string.service_err, Toast.LENGTH_SHORT).show();
                    mTiemTimeCount.cancel();
                    mTiemTimeCount.onFinish();
                }
                if (null != rsp)
                {
                    if (null != rsp.getCode() && "000000".equals(rsp.getCode()))
                    {
                        CacheUtils.setString(act, Constants.PHONE_NUM, obj.getNewPhone());
                        Toast.makeText(act, "修改成功", Toast.LENGTH_SHORT).show();
                        //                        act.startActivity(new Intent(act, AccountSetActivity.class));
                        act.finish();

                        Event event = new Event();
                        event.eventType = Event.REFRESH_ACCOUNT;
                        EventBus.getDefault().post(event);

                    }
                    else if (null != rsp.getCode() && !"000000".equals(rsp.getCode()))
                    {
                        Toast.makeText(act, rsp.getMsg(), Toast.LENGTH_SHORT).show();
                        mTiemTimeCount.cancel();
                        mTiemTimeCount.onFinish();
                    }
                    else
                    {
                        Toast.makeText(act, "网络连接异常", Toast.LENGTH_SHORT).show();
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
