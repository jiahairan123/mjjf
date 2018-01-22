package com.mingjie.jf.action;

import android.content.Intent;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.LoginActivity;
import com.mingjie.jf.activity.ModifyPassActivity;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.ModifyPassActivityVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.UIUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by QinSiYi on 2016-8-27.
 */
public class ModifyPassAction
{
    private ModifyPassActivity act;
    private TimeCount mTiemTimeCount;

    public ModifyPassAction(ModifyPassActivity act, TimeCount mTiemTimeCount)
    {
        this.act = act;
        this.mTiemTimeCount = mTiemTimeCount;
    }

    // 修改登录密码
    public void modifyPassNextAction(final ModifyPassActivityVo obj)
    {

        HttpManager.getInstance().doPost(ServiceName.RESET_MODIFY_PASSWORD, obj, new
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
                        if (null != rsp)
                        {
                            if (null != rsp.getCode() && "000000".equals(rsp.getCode()))
                            {
                                Toast.makeText(act, "修改成功", Toast.LENGTH_SHORT).show();
                                act.startActivity(new Intent(act, LoginActivity.class));
                                UIUtils.dimissLoading();
                                CacheUtils.clearUserInfo();

                                Event event = new Event();
                                event.eventType = Event.LOGOUT;
                                EventBus.getDefault().post(event);

                                act.finish();
                            }
                            else if (null != rsp.getCode() && "100012".equals(rsp.getCode()))
                            {
                                UIUtils.showToast("原始" + rsp.getMsg());
                                mTiemTimeCount.cancel();
                                mTiemTimeCount.onFinish();

                            }
                            else
                            {
                                Toast.makeText(act, rsp.getMsg(), Toast
                                        .LENGTH_SHORT).show();
                                mTiemTimeCount.cancel();
                                mTiemTimeCount.onFinish();
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
