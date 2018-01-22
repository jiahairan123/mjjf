package com.mingjie.jf.activity;

import android.content.ClipData;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.OpenCtResult;
import com.mingjie.jf.bean.UserInfo;
import com.mingjie.jf.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 实名认证
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class RealNameActivity extends BaseActivity implements View.OnClickListener
{
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.acno)
    TextView acno;//华兴E账户
    @BindView(R.id.acname)
    TextView acname;//姓名
    @BindView(R.id.idtype)
    TextView idtype;//身份证类型
    @BindView(R.id.idno)
    TextView idno;//身份证号
    @BindView(R.id.ext_filed1)
    TextView extFiled1;//温馨提示
    @BindView(R.id.fuzhi)
    TextView fuzhi;
    private OpenCtResult rDetail;//开户结果查询
    private UserInfo userinfo;
    private String account, realname, idnumber;

    //    @Override
    //    public void onCreate(Bundle savedInstanceState)
    //    {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_check_deposit);
    //        ButterKnife.bind(this);
    //        account = getIntent().getStringExtra("acno");
    //        realname = getIntent().getStringExtra("realname");
    //        idnumber = getIntent().getStringExtra("idnumber");
    //        initView();
    //        initData();
    //    }

    private void showOpenCtDetail()
    {
        acno.setText(account);
        if (null != realname)
        {
            acname.setText(getStarString(realname, 1, realname.length()));
        }
        if (null != idnumber)
        {
            idno.setText(getStarString2(idnumber, 5, 2));
        }
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

    protected void initView()
    {
        setContentView(R.layout.activity_check_deposit);
        ButterKnife.bind(this);
        account = getIntent().getStringExtra("acno");
        realname = getIntent().getStringExtra("realname");
        idnumber = getIntent().getStringExtra("idnumber");
        ivLeftPublic.setOnClickListener(this);
        tvContentPublic.setText("认证信息");
        fuzhi.setOnClickListener(this);
    }

    protected void initData()
    {
        showOpenCtDetail();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.fuzhi://复制

                if(getSDKVersionNumber() >= 11){
                    android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText(account);
                }else{
                    // 得到剪贴板管理器
                    android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, account));
                }
                UIUtils.showToast("已复制到粘贴板");
                break;
            default:
                break;
        }

    }

    /**
     * 获取手机操作系统版本
     * @return
     * @author SHANHY
     * @date   2015年12月4日
     */
    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.parseInt(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }

}

