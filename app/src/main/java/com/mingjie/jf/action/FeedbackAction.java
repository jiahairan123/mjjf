package com.mingjie.jf.action;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.FeedbackVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.UIUtils;

/**
 * 意见反馈
 * Created by zhangjin on 2016/7/25.
 */
public class FeedbackAction
{
    private Activity act;

    public FeedbackAction(Activity act)
    {
        this.act = act;
    }

    public void feedback(FeedbackVo obj)
    {
        if (TextUtils.isEmpty(obj.getTitle()))
        {
            Toast.makeText(act, "反馈标题不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(obj.getContent()))
        {
            Toast.makeText(act, "反馈内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            UIUtils.showLoading(act);
            HttpCallBack<ServerResponse<String>> mCallBack = new HttpCallBack<ServerResponse<String>>()
            {

                @Override
                public void getHttpCallNull(String str)
                {
                    UIUtils.dimissLoading();
                    Toast.makeText(act, R.string.dataError, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void getHttpCallBack(ServerResponse<String> vo)
                {
                    UIUtils.dimissLoading();
                    if (null != vo)
                    {
                        if ("000000".equals(vo.getCode()))
                        {
                            Toast.makeText(act, "提交成功", Toast.LENGTH_SHORT).show();
                            act.finish();
                        }
                        else
                        {
                            Toast.makeText(act, "提交失败\n" + vo.getMsg(), Toast
                                    .LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(act, R.string.service_err, Toast.LENGTH_SHORT).show();
                    }
                }
            };

            HttpManager.getInstance().doPost(ServiceName.CGKX_ADDUSERSUGGESTION, obj, mCallBack);

        }


    }


}
