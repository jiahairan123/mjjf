package com.mingjie.jf.action;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.OtherSmsCodeActionVo;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.SmsCodeActionVo;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.UIUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.action
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-05 15:20
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public abstract class AbsSmsCodeAction
{

    private TimeCount mTiemTimeCount;

    private TimeCount mTiemTimeCount1;

    public abstract void setSmsCode(String smsCode);
    public abstract void setSmsCodeError();

    private Activity act;
    ImageView resImverificationcode;

    public AbsSmsCodeAction(Activity act, ImageView resImverificationcode, TimeCount mTiemTimeCount)
    {
        this.act = act;
        this.resImverificationcode = resImverificationcode;
        this.mTiemTimeCount = mTiemTimeCount;
    }

    public AbsSmsCodeAction(Activity act)
    {
        this.act = act;
    }

    public AbsSmsCodeAction(Activity act, TimeCount mTiemTimeCount1)
    {
        this.act = act;
        this.mTiemTimeCount1 = mTiemTimeCount1;
    }

    //注册用获取短信验证码
    public void registersmsCodeAction(final SmsCodeActionVo obj)
    {

        HttpManager.getInstance().doPost(ServiceName.REGISTER_PHONE_CODE, obj, new
                HttpCallBack<ServerResponse<String>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        setSmsCodeError();
                        Toast.makeText(act, R.string.dataError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse rsp)
                    {
                        if (null == rsp)
                        {
                            setSmsCodeError();
                            Toast.makeText(act,  R.string.service_err, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (null != rsp.getCode())
                            {

                                if ("000000".equals(rsp.getCode()))
                                {
                                    UIUtils.showToast("验证码发送成功");
                                    setSmsCode(rsp.getData().toString());
                                    mTiemTimeCount.start();

                                }
                                if (!"000000".equals(rsp.getCode()))
                                {
                                    setSmsCodeError();
                                    UIUtils.showToast(act, rsp.getMsg());
                                    HttpManager.getInstance().displayImageCode(resImverificationcode, null, null);
                                }
                            }
                            else
                            {
                                setSmsCodeError();
                                Toast.makeText(act, R.string.dataError, Toast.LENGTH_SHORT).show();
                                HttpManager.getInstance().displayImageCode(resImverificationcode, null, null);
                            }
                        }
                    }
                });

    }

    public void othersmsCodeAction(final OtherSmsCodeActionVo obj)
    {

        HashMap<String, String> map = new HashMap<>();
        map.put("phone", obj.getPhone());
        MobclickAgent.onEvent(act, ServiceName.RESET_PHONE_CODE, map);

        HttpManager.getInstance().doPost(ServiceName.RESET_PHONE_CODE, obj, new HttpCallBack<ServerResponse<String>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                setSmsCodeError();
                Toast.makeText(act, "提交失败，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getHttpCallBack(ServerResponse rsp)
            {
                if (null != rsp)
                {
                    if (null != rsp.getCode() && "000000".equals(rsp.getCode()))
                    {
                        setSmsCode(rsp.getData().toString());
                        UIUtils.showToast("验证码发送成功");
                    }
                    else if (null != rsp.getCode() &&!"000000".equals(rsp.getCode()))
                    {
                        setSmsCodeError();
                        Toast.makeText(act, rsp.getMsg(), Toast
                                .LENGTH_SHORT).show();
                    }
                    else
                    {
                        setSmsCodeError();
                        Toast.makeText(act, "网络错误", Toast
                                .LENGTH_SHORT).show();

                    }
                }
                else
                {
                    setSmsCodeError();
                    Toast.makeText(act, "连接异常", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
