package com.mingjie.jf.widgets;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 * <p>项目名称：Css
 * <p>包   名： com.minghao.css.ui.widget
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 自定义事件选择dialog
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-08 11:08
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class CustomerDatePickerDialog extends DatePickerDialog
{
    public CustomerDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int
            dayOfMonth)
    {
        super(context, callBack, year, monthOfYear, dayOfMonth);
//        this.setTitle(year + "年" + (monthOfYear + 1) + "月");
//        ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).
//                setVisibility(View.GONE);
    }

//    @Override
//    public void onDateChanged(DatePicker view, int year, int month, int day)
//    {
//        super.onDateChanged(view, year, month, day);
//        setTitle(year + "年" + (month + 1) + "月");
//    }

    @Override
    protected void onStop()
    {
//        super.onStop();
    }
}
