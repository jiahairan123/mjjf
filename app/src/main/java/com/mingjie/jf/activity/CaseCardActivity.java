package com.mingjie.jf.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mingjie.jf.R;
import com.mingjie.jf.action.AbsSmsCodeAction;
import com.mingjie.jf.bean.AreaBean;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.bean.OtherSmsCodeActionVo;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeCount;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.PoupupSelectView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 绑定银行卡
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-11-22 10:16
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class CaseCardActivity extends BaseActivity implements View.OnClickListener, PoupupSelectView.OnListItemListener
{

    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    @BindView(R.id.tv_card_selectprovince)//所在省
            TextView mTvProvince;
    @BindView(R.id.tv_card_selectcity)//所在市
            TextView mTvCity;
    @BindView(R.id.btn_tobind)
    Button btnTobind;
    @BindView(R.id.btn_card_getcode)
    Button btnCardGetcode;//获取短信验证码按钮
    @BindView(R.id.et_card_verifycode)
    EditText mEtCheckCode;//短信验证码编辑框
    @BindView(R.id.tv_card_selectbank)//所属银行
            TextView mTvSelectBank;
    @BindView(R.id.et_card_banknumber)//银行卡号
            EditText mEtCardNumber;
    @BindView(R.id.tv_card_bank)//银行卡开户行
            EditText tvCardBank;
    @BindView(R.id.tv_realname)
    TextView tvRealname;
    @BindView(R.id.ll_name)
    LinearLayout llName;//是否显示开户名
    //    @BindView(R.id.swipe_account_fragment)
    //    SwipeRefreshLayout mRefreshView;

    private int mProvinceId;
    private int mCityId;

    private Map<String, Integer> mProvinces = new HashMap<>();//省份列表，名称+id
    private List<String> mProvinceNames = new ArrayList<>();//省份名称
    private Map<String, Integer> mCities = new HashMap<>();//城市列表，名称+id
    private List<String> mCityNames = new ArrayList<>();//城市名称
    private Map<Integer, List<AreaBean.DataEntity.CityBean>> mAreas = new HashMap<>();//区域列表
    private PoupupSelectView poupupSelectView;
    private TextView mPopupTextView;
    private int mCurrentSelect;
    private final int TYPE_PROVINCE = 1000;
    private final int TYPE_CITY = 1001;

    private TimeCount mTiemTimeCount;
    private String verfycode;

    private boolean isFromGetCode;

    private String mCurrentBank;//所属银行
    private String mCurrentProvince;//省
    private String mCurrentCity;//城市
    private String mBranchBank;//开户支行
    private String mCardNumber;//卡号
    private String mCheckCode;//短信验证码

    private UserVoValible user;

    private String[] mListData = UIUtils.getStringArray(R.array.bankList);//银行数据

    int mMaxLenth = 50;//设置允许输入的字符长度

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_casecard);
        ButterKnife.bind(this);
        user = CacheUtils.getUserinfo(this);
        tvContentPublic.setText("银行卡");
        ivLeftPublic.setOnClickListener(this);
        mTvProvince.setOnClickListener(this);
        mTvCity.setOnClickListener(this);
        mTvSelectBank.setOnClickListener(this);
        btnTobind.setOnClickListener(this);

        btnCardGetcode.setOnClickListener(this);
        llName.setVisibility(View.GONE);
        if (null != getIntent().getStringExtra("name"))
        {
            //            tvRealname.setText(getIntent().getStringExtra("name"));
            tvRealname.setText(getStarString(getIntent().getStringExtra("name"), 1, getIntent().getStringExtra("name")
                    .length()));
        }
        //计时器
        mTiemTimeCount = new TimeCount(60000, 1000, btnCardGetcode);
        isFromGetCode = false;

        /**
         * 限制编辑框输入特殊字符
         */
        tvCardBank.addTextChangedListener(new TextWatcher()
        {
            private int cou = 0;
            int selectionEnd = 0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count)
            {
                cou = before + count;
                String editable = tvCardBank.getText().toString();
                String str = stringFilter(editable); //过滤特殊字符
                if (!editable.equals(str))
                {
                    tvCardBank.setText(str);
                }
                tvCardBank.setSelection(tvCardBank.length());
                cou = tvCardBank.length();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (cou > mMaxLenth)
                {
                    selectionEnd = tvCardBank.getSelectionEnd();
                    s.delete(mMaxLenth, selectionEnd);
                }
            }
        });

    }

    public static String stringFilter(String str) throws PatternSyntaxException
    {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}\\[\\]‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find())
        {
            UIUtils.showToast("不允许输入特殊符号!");
        }
        return m.replaceAll("");
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

    @Override
    protected void initData()
    {
        //        获取地区列表
        mAreas.clear();
        mProvinces.clear();
        String json = StringUtils.getJson(this, "area.json");
        Gson gson = new Gson();
        AreaBean areaBean = gson.fromJson(json, AreaBean.class);
        List<AreaBean.DataEntity> data = areaBean.data;
        for (AreaBean.DataEntity d : data)
        {
            mAreas.put(d.provinceId, d.cityList);
            mProvinces.put(d.provinceType, d.provinceId);
        }
        // CfLog.i("---" + mAreas);
        CfLog.i("---" + mProvinces);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            case R.id.btn_tobind://点击绑卡
                isFromGetCode = false;
                if (checkData())
                {
                    getBind();
                }
                //                startActivity(new Intent(this, CaseCardListActivity.class));
                break;
            case R.id.tv_card_selectbank:   // 展开选择银行
                PoupupSelectView mPopupWindow = new PoupupSelectView(this, Arrays.asList(mListData), mTvSelectBank);
                mPopupWindow.setmTitle("选择银行");
                mPopupWindow.showPopupWindow();
                mPopupWindow.setOnListItemClickListener(new PoupupSelectView.OnListItemListener()
                {
                    @Override
                    public void onListItemClick(AdapterView<?> parent, TextView view, int position, long id)
                    {
                        String text = view.getText().toString();
                        mTvSelectBank.setText(text);
                    }
                });
                break;
            case R.id.tv_card_selectprovince:
                mCurrentSelect = TYPE_PROVINCE;
                mProvinceNames.clear();
                mProvinceNames.addAll(mProvinces.keySet());
                showPopupWindow(mTvProvince, mProvinceNames, "选择省份");
                break;
            case R.id.tv_card_selectcity:
                mCurrentSelect = TYPE_CITY;
                String provinceKey = mTvProvince.getText().toString().trim();
                CfLog.i("---" + provinceKey);
                if (provinceKey != null && !"所在地".equals(provinceKey))
                {
                    mCityNames.clear();
                    mCities.clear();
                    CfLog.i("---" + mProvinces);
                    mProvinceId = mProvinces.get(provinceKey);
                    getCitys();
                }
                else
                {
                    UIUtils.showToast("请先选择省份");
                }
                break;
            //获取短信验证码
            case R.id.btn_card_getcode:
                isFromGetCode = true;
                if (checkData())
                {
                    if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
                    {
                        UIUtils.showToast(CaseCardActivity.this, "当前网络不可用，请重试！");
                        return;
                    }
                    else
                    {
                        btnCardGetcode.setEnabled(false);
                        OtherSmsCodeActionVo votwo = new OtherSmsCodeActionVo();
                        if (null != getIntent().getStringExtra("accounttel_phone"))
                        {
                            votwo.setPhone(getIntent().getStringExtra("accounttel_phone"));
                        }
                        else
                        {
                            votwo.setPhone(getIntent().getStringExtra("CaseCardphonenumber"));
                        }
                        CfLog.i("---" + getIntent().getStringExtra("tel_phone"));
                        CfLog.i("---" + getIntent().getStringExtra("CaseCardphonenumber"));
                        new AbsSmsCodeAction(this, mTiemTimeCount)
                        {
                            public void setSmsCode(String smsCode)
                            {
                                mTiemTimeCount.start();//启动计时器
                                if (!smsCode.isEmpty())
                                {
                                    //验证码
                                    verfycode = smsCode;

                                    if (UrlUtil.isSmsDebug())
                                    {
                                        mEtCheckCode.setText(verfycode);
                                    }
                                }
                            }

                            public void setSmsCodeError()
                            {
                                btnCardGetcode.setEnabled(true);
                            }
                        }.othersmsCodeAction(votwo);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 检查输入数据
     */
    private boolean checkData()
    {
        mCurrentBank = mTvSelectBank.getText().toString().trim();
        mCurrentProvince = mTvProvince.getText().toString().trim();
        mCurrentCity = mTvCity.getText().toString().trim();
        mBranchBank = tvCardBank.getText().toString().trim();
        mCardNumber = mEtCardNumber.getText().toString().trim();
        mCheckCode = mEtCheckCode.getText().toString().trim();

        if ("所属银行".equals(mCurrentBank))
        {
            UIUtils.showToast("您还没选择所属银行哦!");
            return false;
        }
        else if (TextUtils.isEmpty(mCardNumber))
        {
            UIUtils.showToast("请输入银行卡号!");
            return false;
        }
        else if ("所在地".equals(mCurrentProvince))
        {
            UIUtils.showToast("您还没有选择省份哦!");
            return false;
        }
        else if ("".equals(mCurrentCity))
        {
            UIUtils.showToast("您还没有选择城市哦!");
            return false;
        }
        else if (TextUtils.isEmpty(mBranchBank))
        {
            UIUtils.showToast("请输入开户支行");
            return false;
        }
        else if (isFromGetCode)
        {
            return true;
        }
        else if (TextUtils.isEmpty(mCheckCode))
        {
            UIUtils.showToast("请输入验证码!");
            return false;
        }
        if (mCardNumber.length() < 10)
        {
            UIUtils.showToast("合法的银行卡号不能少于10位!");
            return false;
        }
        return true;
    }

    /**
     * 弹选择框
     *
     * @param tv
     * @param mDatas
     * @param title
     */
    private void showPopupWindow(TextView tv, List<String> mDatas, String title)
    {
        poupupSelectView = new PoupupSelectView(this, mDatas, tv);
        poupupSelectView.setDatas(mDatas, title, tv);
        poupupSelectView.showPopupWindow();
        mPopupTextView = tv;
        poupupSelectView.setOnListItemClickListener(this);

    }

    @Override
    public void onListItemClick(AdapterView<?> parent, TextView view, int position, long id)
    {
        String select = view.getText().toString().trim();
        mPopupTextView.setText(select);
        CfLog.i("---" + select);
        switch (mCurrentSelect)
        {
            case TYPE_PROVINCE:
                mProvinceId = mProvinces.get(select);
                CfLog.i("---" + mProvinceId);
                getCitys();
                mTvCity.setText(mCityNames.get(0));
                CfLog.i("---" + mTvCity);
                mCityId = mCities.get(mCityNames.get(0));
                break;
            default:
                mCityId = mCities.get(select);
                CfLog.i("---" + mCityId);
                break;

        }
    }

    private void getCitys()
    {
        mCityNames.clear();
        mCities.clear();
        List<AreaBean.DataEntity.CityBean> cityBeans = mAreas.get(mProvinceId);
        for (AreaBean.DataEntity.CityBean c : cityBeans)
        {
            mCityNames.add(c.cityType);
            mCities.put(c.cityType, c.cityId);
        }
        mCurrentSelect = TYPE_CITY;
        showPopupWindow(mTvCity, mCityNames, "选择城市");
    }

    //提交绑卡请求
    private void getBind()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            //            closeRefresh();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("HHmm", TimeFormatUtil.getSimpleTime());
        map.put("HH", TimeFormatUtil.getHour());
        map.put("province", mCurrentProvince);
        map.put("city", mCurrentCity);
        map.put("bankNo", mCurrentBank);
        map.put("cardNo", mCardNumber.substring(0, 4) + "***" + mCardNumber.substring(mCardNumber.length() - 4));
        MobclickAgent.onEvent(this, ServiceName.BINDBANKCARD, map); // 私房钱绑卡

        Map<String, String> params = new HashMap<>();
        params.put("userId", user.getId());
        params.put("cardNo", mCardNumber);
        params.put("bankNo", mCurrentBank);
        params.put("province", mCurrentProvince);
        params.put("city", mCurrentCity);
        params.put("bankName", mBranchBank);
        params.put("reserve2", mEtCheckCode.getText().toString().trim());
        HttpManager.getInstance().doPost(ServiceName.BINDBANKCARD, params, new
                HttpCallBack<ServerResponse>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        UIUtils.showToast(CaseCardActivity.this, R.string.dataError);
                        //                        closeRefresh();
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse rsp)
                    {
                        //                        closeRefresh();
                        if (rsp == null)
                        {
                            UIUtils.showToast(CaseCardActivity.this, R.string.service_err);
                            return;
                        }

                        if (!"000000".equals(rsp.getCode()))
                        {
                            UIUtils.showToast(CaseCardActivity.this, rsp.getMsg());
                            return;
                        }
                        else if ("000000".equals(rsp.getCode()))
                        {
                            UIUtils.showToast(rsp.getMsg());
                            //绑定成功后刷新账户
                            Event event = new Event();
                            event.eventType = Event.REFRESH_ACCOUNT;
                            EventBus.getDefault().post(event);
                            finish();
                        }
                        else
                        {
                            UIUtils.showToast(CaseCardActivity.this, R.string.service_err);
                        }

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //    private void closeRefresh()
    //    {
    //        if (mRefreshView == null)
    //        {
    //            return;
    //        }
    //        if (mRefreshView.isRefreshing())
    //        {
    //            mRefreshView.setRefreshing(false);
    //        }
    //    }
}
