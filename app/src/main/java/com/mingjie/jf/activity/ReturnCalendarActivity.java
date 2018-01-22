package com.mingjie.jf.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.ReturnCalendarAdapter;
import com.mingjie.jf.bean.ReturnCalendarDayBean;
import com.mingjie.jf.bean.ReturnCalendarMonthBean;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.view.TitleView;
import com.mingjie.jf.widgets.CustomerDatePickerDialog;
import com.mingjie.jf.widgets.RefreshLayout;
import com.mingjie.jf.widgets.datepicker.DateUtils;
import com.mingjie.jf.widgets.datepicker.MonthDateView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 回款日历
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-12 11:48
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ReturnCalendarActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View
        .OnClickListener
{
    private RefreshLayout refreshCalendar;
    private ListView lvCalendar;
    private LayoutInflater inflater;
    private List<Integer> thingDays = new ArrayList();
    private List<ReturnCalendarDayBean> data = new ArrayList<>();
    private ReturnCalendarAdapter adapter;
    private ImageView ivLeft;
    private ImageView ivRight;
    private MonthDateView monthDateView;
    private TextView tvDate;
    private TextView tvToday;
    private TextView tvReceivable;
    private TextView tvDuein;
    private TextView tvTodayDate;
    private TextView tvSelectDate;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_return_calendar);
        TitleView viewTitle = (TitleView) findViewById(R.id.view_title);
        viewTitle.setImgRight(R.mipmap.calendar_list);
        viewTitle.setOnClickLitenerRight(this);
        refreshCalendar = (RefreshLayout) findViewById(R.id.refresh_calendar);
        refreshCalendar.setOnRefreshListener(this);
        refreshCalendar.setColorSchemeResources(R.color.color_public_blue, R.color.green, R.color.color_public_red);

        lvCalendar = (ListView) findViewById(R.id.lv_calendar);
        //初始化listview头部分
        inflater = LayoutInflater.from(this);
        initHeader();
        adapter = new ReturnCalendarAdapter(this, data);
        lvCalendar.setAdapter(adapter);

    }

    /**
     * 初始化头部日历部分
     */
    private void initHeader()
    {
        View header = inflater.inflate(R.layout.header_return_calendar, lvCalendar, false);
        ivLeft = (ImageView) header.findViewById(R.id.iv_last);
        ivLeft.setOnClickListener(this);
        ivRight = (ImageView) header.findViewById(R.id.iv_next);
        ivRight.setOnClickListener(this);
        monthDateView = (MonthDateView) header.findViewById(R.id.monthDateView);
        monthDateView.setThingColor(getResources().getColor(R.color.color_public_red));

        tvDate = (TextView) header.findViewById(R.id.date_text);
        tvDate.setOnClickListener(this);
        tvToday = (TextView) header.findViewById(R.id.tv_today);
        tvToday.setOnClickListener(this);
        monthDateView.setTextView(tvDate, null);
        //点击日历，切换选中日期
        monthDateView.setDateClick(new MonthDateView.DateClick()
        {
            @Override
            public void onClickOnDate()
            {
                int year = monthDateView.getmSelYear();
                int month = monthDateView.getmSelMonth();
                int day = monthDateView.getmSelDay();
                //如果点击的为上个月或下个月的日期不做任何处理
                if (day > 0 && day <= DateUtils.getMonthDays(year, month))
                {
                    tvSelectDate.setText(TimeFormatUtil.data2StrTime2(getSelectTime()));
                    UIUtils.showLoading(ReturnCalendarActivity.this);
                    getDayData();
                }
            }
        });

        tvReceivable = (TextView) header.findViewById(R.id.tv_receivable);//应收总额
        tvDuein = (TextView) header.findViewById(R.id.tv_duein);//待收总额
        tvSelectDate = (TextView) header.findViewById(R.id.tv_select_date);//选中日期
        tvSelectDate.setText(TimeFormatUtil.data2StrTime2(getSelectTime()));

        lvCalendar.addHeaderView(header);
    }

    @Override
    protected void initData()
    {
        UIUtils.showLoading(this);
        getMonthData();
    }

    /**
     * 获取月数据
     */
    private void getMonthData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            closeRefresh();
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            return;
        }
        Map params = new HashMap(1);
        params.put("startTime", getSelectTime() + "");
        HttpManager.getInstance().doPost(ServiceName.RECEIPTPLAN_CALENDAR, params, new
                HttpCallBack<ServerResponse<ReturnCalendarMonthBean>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        closeRefresh();
                        ToastUtil.showToast(ReturnCalendarActivity.this, getResources().getString(R.string.dataError));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<ReturnCalendarMonthBean> rsp)
                    {
                        if (rsp == null)
                        {
                            ToastUtil.showToast(ReturnCalendarActivity.this, getResources().getString(R.string
                                    .dataError));
                            closeRefresh();
                            return;
                        }
                        if (!"000000".equals(rsp.getCode()))
                        {
                            ToastUtil.showToast(ReturnCalendarActivity.this, rsp.getMsg());
                        }
                        if (rsp.getData() == null)
                        {
                            ToastUtil.showToast(ReturnCalendarActivity.this, getResources().getString(R.string
                                    .dataError));
                            closeRefresh();
                            return;
                        }

                        showMonthData(rsp.getData());
                        getDayData();
                    }
                });
    }

    /**
     * 展示月数据
     *
     * @param bean
     */
    private void showMonthData(ReturnCalendarMonthBean bean)
    {
        thingDays.clear();
        thingDays.addAll(bean.getList());

        monthDateView.setDaysHasThingList(thingDays);
        tvReceivable.setText(StringUtils.dou2Str(bean.getSumAmount()));
        tvDuein.setText(StringUtils.dou2Str(bean.getWaitAmountSum()));
    }

    /**
     * 获取天数据
     */
    private void getDayData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            closeRefresh();
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            return;
        }
        Map params = new HashMap(1);
        params.put("startTime", getSelectTime() + "");
        HttpManager.getInstance().doPost(ServiceName.RECEIPTPLAN_LIST, params, new
                HttpCallBack<ServerResponse<List<ReturnCalendarDayBean>>>()
                {
                    @Override
                    public void getHttpCallNull(String str)
                    {
                        closeRefresh();
                        ToastUtil.showToast(ReturnCalendarActivity.this, getResources().getString(R.string.dataError));
                    }

                    @Override
                    public void getHttpCallBack(ServerResponse<List<ReturnCalendarDayBean>> rsp)
                    {
                        closeRefresh();
                        if (rsp == null)
                        {
                            ToastUtil.showToast(ReturnCalendarActivity.this, getResources().getString(R.string
                                    .dataError));

                            return;
                        }
                        if (!"000000".equals(rsp.getCode()))
                        {
                            ToastUtil.showToast(ReturnCalendarActivity.this, rsp.getMsg());
                            return;
                        }

                        if (rsp.getData() == null)
                        {
                            ToastUtil.showToast(ReturnCalendarActivity.this, getResources().getString(R.string
                                    .dataError));
                            return;
                        }
                        data.clear();
                        data.addAll(rsp.getData());
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onRefresh()
    {
        getMonthData();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_right_public:

                startActivity(new Intent(ReturnCalendarActivity.this, ReturnMoneyListActivity.class));
                overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out_left);
                finish();
                break;

            case R.id.iv_last://上个月
                monthDateView.onLeftClick();
                selectMonth();
                break;
            case R.id.iv_next://下个月
                monthDateView.onRightClick();
                selectMonth();
                break;
            case R.id.tv_today://回到今天
                monthDateView.setTodayToView();
                selectMonth();
                break;

            case R.id.date_text://点击选中年月，切换月份
                selectDate();
                break;

            default:
                break;
        }
    }

    /**
     * 点击年月选择年月
     */
    private void selectDate()
    {
        CustomerDatePickerDialog pickerDialog = new CustomerDatePickerDialog(ReturnCalendarActivity.this,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth);
                        //设置展示选中的时间
                        tvDate.setText(TimeFormatUtil.data2StrTime3(c.getTimeInMillis()));
                        //将日历对应日期选中
                        monthDateView.setSelectYearMonth(year, monthOfYear, dayOfMonth);
                        //请求对应日期数据
                        selectMonth();
                    }
                }, monthDateView.getmSelYear(), monthDateView.getmSelMonth(), monthDateView.getmSelDay());
        pickerDialog.show();
    }

    /**
     * 切换月份时候 获取数据
     */
    private void selectMonth()
    {
        thingDays.clear();
        tvSelectDate.setText(TimeFormatUtil.data2StrTime2(getSelectTime()));
        monthDateView.setDaysHasThingList(thingDays);
        UIUtils.showLoading(this);
        getMonthData();
    }

    /**
     * 获取日历当前选中时间 时间戳
     */
    private Long getSelectTime()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(monthDateView.getmSelYear(), monthDateView.getmSelMonth(), monthDateView.getmSelDay());
        return calendar.getTimeInMillis();
    }

    /**
     * 关闭刷新
     */
    private void closeRefresh()
    {
        if (refreshCalendar.isRefreshing())
        {
            refreshCalendar.setRefreshing(false);
        }
        UIUtils.dimissLoading();
    }
}
