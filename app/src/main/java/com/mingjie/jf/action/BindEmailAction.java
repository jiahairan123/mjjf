package com.mingjie.jf.action;

import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.BindEmailActivity;
import com.mingjie.jf.bean.BindEmailActionVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.UIUtils;

/**
 * Created by QinSiYi on 2016-8-27.
 */
public class BindEmailAction
{
    private BindEmailActivity act;

    public BindEmailAction(BindEmailActivity act)
    {
        this.act = act;
    }

    public void bindEmaile(final BindEmailActionVo obj)
    {
        //注意目前返回类型不确定(对服务端返回的数据要进行封装)
        HttpManager.getInstance().doPost(ServiceName.RESET_MODIFY_EMAIL2, obj, new
                HttpCallBack<ServerResponse<String>>()
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
                            if (null != rsp.getCode() && "000000".equals(rsp.getCode()))
                            {
                                act.show();
                                return;
                            }
                            else if (null != rsp.getCode() && !"000000".equals(rsp.getCode()))
                            {
                                if ("000001".equals(rsp.getCode()))
                                {
                                    UIUtils.showToast("绑定" + rsp.getMsg());
                                }
                                else
                                {
                                    UIUtils.showToast(rsp.getMsg());
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(act, R.string.dataError, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
