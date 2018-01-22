package com.mingjie.jf.action;

import android.content.Intent;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.BindEmailActivity;
import com.mingjie.jf.activity.ConfirmBindActivity;
import com.mingjie.jf.bean.SafePhoneActionVo;
import com.mingjie.jf.bean.UserVo;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.TimeCount;

/**
 * Created by QinSiYi on 2016-8-27.
 */
public class ConfirmBindAction
{
    private ConfirmBindActivity act;
    private TimeCount mTiemTimeCount;

    public ConfirmBindAction(ConfirmBindActivity act, TimeCount mTiemTimeCount)
    {
        this.act = act;
        this.mTiemTimeCount = mTiemTimeCount;

    }

    public void safebindnextAction(final SafePhoneActionVo obj)
    {

        //注意目前返回类型不确定
        HttpManager.getInstance().doPost(ServiceName.RESET_MODIFY_EMAIL, obj, new HttpCallBack<UserVo>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                Toast.makeText(act, R.string.dataError, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getHttpCallBack(UserVo rsp)
            {
                if (null != rsp)
                {
                    if (null != rsp.getCode() && "000000".equals(rsp.getCode()))
                    {
                        Toast.makeText(act, "跳到绑定邮箱第二步", Toast.LENGTH_SHORT).show();
                        act.startActivity(new Intent(act, BindEmailActivity.class));
                        act.finish();
                    }
                    else if (null != rsp.getCode() && !"000000".equals(rsp.getCode()))
                    {
                        Toast.makeText(act, rsp.getMsg(), Toast
                                .LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(act, R.string.service_err, Toast.LENGTH_SHORT).show();
                    }
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