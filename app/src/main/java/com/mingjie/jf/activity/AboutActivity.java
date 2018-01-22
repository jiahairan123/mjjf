package com.mingjie.jf.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjie.jf.BuildConfig;
import com.mingjie.jf.R;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.PropertyUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity implements View.OnClickListener
{

    private final String IS_SHOW_DEV_MODEL = "showDevModel";

    @BindView(R.id.edt_url)
    EditText edtUrl;

    @BindView(R.id.btn_ok)
    View btnOK;

    @BindView(R.id.tv_content_public)
    TextView tvwTitle;

    @BindView(R.id.iv_Left_public)
    View vBack;

    @BindView(R.id.tvw_version_name)
    TextView tvwVersionName;

    @BindView(R.id.tvw_inner_name)
    TextView tvwInnerName;

    @BindView(R.id.tvw_channel)
    TextView tvwChannel;

    @BindView(R.id.tvw_url_real)
    TextView tvwUrlReal;

    @BindView(R.id.tvw_inner_vcode)
    TextView tvwVersionCode;

    @BindView(R.id.tvw_app_type)
    TextView tvwAppType;

    @BindView(R.id.ll_version_code)
    View llVersionCode;

    @BindView(R.id.btn_default)
    View btnDefault;

    @BindView(R.id.tvw_url)
    View tvwUrl;
    @BindView(R.id.tvw_dev_tip)
    View tvwDevTip;
    @BindView(R.id.ll_dev_mode)
    View llDevModel;

    @BindView(R.id.ll_set)
    View llSet;

    @BindView(R.id.swt_dev)
    Switch swtDev;

    @BindView(R.id.tv_test_product)
    TextView tvTestProduct;//查看测试标的

    // 上次点击时间
    private long lastClickTime = 0L;

    private int clickCount = 0;

    private int urlClickCount = 0;
    String baseUrl;

    final String urlTest = "https://192.168.1.100";
    final String urlTest2 = "http://192.168.1.133:8081/cgkx-mobile";

    ArrayList<String> list = new ArrayList();

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData()
    {
        // baseUrl = CacheUtils.getDefaultUrl(getBaseContext());
        baseUrl = UrlUtil.getUrl();

        list.add(UrlUtil.getUrl());
        list.add(UrlUtil.getDefaultUrl());
        list.add(urlTest);
        list.add(urlTest2);

        tvwTitle.setText("关于");
        btnOK.setOnClickListener(this);
        vBack.setOnClickListener(this);
        btnDefault.setOnClickListener(this);
        tvwUrl.setOnClickListener(this);
        llVersionCode.setOnClickListener(this);

        tvTestProduct.setOnClickListener(this);
        //        String url = CacheUtils.getUrl(this);
        edtUrl.setText(baseUrl);
        tvwUrlReal.setText(baseUrl);
        tvwVersionName.setText(UIUtils.getAppVersionName());
        tvwVersionCode.setText(String.valueOf(UIUtils.getAppVersionNum()));
        tvwAppType.setText(BuildConfig.DEBUG ? "debug" : "release");

        try
        {
            ApplicationInfo appInfo = this.getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            String channel = appInfo.metaData.getString("UMENG_CHANNEL");
            String innerName = PropertyUtil.getString(this, "innerName", "");
            tvwChannel.setText(channel);
            tvwInnerName.setText(innerName);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            CfLog.e(e.toString());
        }

        boolean showDevModel = CacheUtils.getBoolean(getBaseContext(), IS_SHOW_DEV_MODEL, false);
        if (showDevModel)
        {
            llDevModel.setVisibility(View.VISIBLE);
            tvwDevTip.setVisibility(View.VISIBLE);
            tvTestProduct.setVisibility(View.VISIBLE);
        }

        swtDev.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    llSet.setVisibility(View.VISIBLE);
                    CacheUtils.setBoolean(getBaseContext(), IS_SHOW_DEV_MODEL, true);
                }
                else
                {
                    llSet.setVisibility(View.GONE);
                    CacheUtils.remove(getBaseContext(), IS_SHOW_DEV_MODEL);
                }
            }
        });

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;

            case R.id.btn_default:
                tvwUrlReal.setText(baseUrl);
                edtUrl.setText(baseUrl);
                // CacheUtils.removeUrl(getBaseContext());
                UrlUtil.removeUrl();
                llDevModel.setVisibility(View.GONE);
                tvwDevTip.setVisibility(View.GONE);
                llSet.setVisibility(View.GONE);
                swtDev.setChecked(false);
                Toast.makeText(getBaseContext(), "已恢复默认(线上环境)，请重新打开APP！", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_ok:
                String url = edtUrl.getText().toString().trim();
                // CacheUtils.setUrl(getBaseContext(), url);
                UrlUtil.setUrl(url);
                tvwUrlReal.setText(url);
                if (url.equals(UrlUtil.getDefaultUrl()))
                {
                    UrlUtil.removeUrl();
                }
                Toast.makeText(getBaseContext(), "修改成功，请重新打开APP！", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvw_url:
                urlClickCount++;
                edtUrl.setText(list.get(urlClickCount % list.size()));
                break;

            case R.id.ll_version_code:
                // 连续点击时间不超过1秒
                if (System.currentTimeMillis() - lastClickTime > 1000)
                {
                    lastClickTime = System.currentTimeMillis();
                    clickCount = 1;
                }
                else
                {
                    lastClickTime = System.currentTimeMillis();
                    clickCount++;
                    if (clickCount >= 6)
                    {
                        llDevModel.setVisibility(View.VISIBLE);
                        tvwDevTip.setVisibility(View.VISIBLE);
                        tvTestProduct.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case R.id.tv_test_product://查看测试标的
                startActivity(new Intent(AboutActivity.this, TestProductListActivity.class));
                break;
            default:
                break;
        }
    }
}
