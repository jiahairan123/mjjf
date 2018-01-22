package com.mingjie.jf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.utils.ToastUtil;


/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.View
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 计算器输入框自定义View
 * <p>创 建 人： wuxianai
 * <p>邮箱地址： wuxianai@ming-hao.cn
 * <p>创建时间： 2016/6/30 11:35
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class IncomeCalculatorItemView extends RelativeLayout {

    private TextView mTvLeft;
    private EditText mEdContent;
    private TextView mTvUnit;
    private ImageView mIvArrow;

    public IncomeCalculatorItemView(Context context) {
        super(context);
    }

    public IncomeCalculatorItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.widget_income_calculator, this, true);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IncomeCalculatorItemView);

        initView(typedArray);
    }

    private void initView(TypedArray typedArray) {

        mTvLeft = (TextView) findViewById(R.id.tv_counter_fashion);
        mEdContent = (EditText) findViewById(R.id.et_counter_fashion_result);
        mTvUnit = (TextView) findViewById(R.id.tv_counter_unit);
        mIvArrow = (ImageView) findViewById(R.id.iv_counter_arrow);

        String leftText = typedArray.getString(R.styleable.IncomeCalculatorItemView_Left_text);
        String content = typedArray.getString(R.styleable.IncomeCalculatorItemView_content_text);
        String rightText = typedArray.getString(R.styleable.IncomeCalculatorItemView_right_text);
        boolean isShowArrow = typedArray.getBoolean(R.styleable.IncomeCalculatorItemView_isShowArrow, true);

        int inputType = typedArray.getInt(R.styleable.IncomeCalculatorItemView_calculator_ed_type, 3);
        initInputType(inputType);

        mEdContent.setHint(content);
        mTvLeft.setText(leftText == null ? "还款方式" : leftText);
        mTvLeft.setTextColor(Color.rgb(93,93,93));
        mTvUnit.setTextColor(Color.rgb(93,93,93));
//        mTvUnit.setHintTextColor(Color.rgb(93,93,93));
        mTvUnit.setText(rightText == null ? "元" : rightText);
        if (isShowArrow) {
            //显示箭头
            mIvArrow.setVisibility(View.VISIBLE);
            mTvUnit.setVisibility(View.GONE);
            mEdContent.setEnabled(false);
        } else {

            // 显示单位
            mTvUnit.setVisibility(VISIBLE);
            mIvArrow.setVisibility(View.GONE);
            mEdContent.setEnabled(true);
        }

        typedArray.recycle();
    }

    private void initInputType(int inputType) {
        if (inputType == 1) {
            mEdContent.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (inputType == 2) {
            mEdContent.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else if (inputType == 3) {
            mEdContent.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (inputType == 8192) {
            mEdContent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            mEdContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String temp = editable.toString();

                    //以字符"."开头的，在"."前加0
                    if (temp.startsWith(".")) {
                        editable.insert(0, "0.", 0, 1);
                    }

                    //以“00”开头的，替换为“0.”
                    if (temp.startsWith("00")) {
                        editable.replace(1, 2, ".");
                    }

                    //保留两位小数
                    if (temp.contains(".")) {
                        int posDot = temp.indexOf(".");
                        if (temp.length() - posDot - 1 > 2) {
                            ToastUtil.showToast(getContext(), "精确到两位小数！");
                            editable.delete(posDot + 3, posDot + 4);
                        }
                    }
                }
            });
        }

    }

    /**
     * 箭头点击事件
     *
     * @param listener
     */
    public void setArrowListener(OnClickListener listener) {
        mIvArrow.setOnClickListener(listener);
    }

    /**
     * 设置右边单位
     *
     * @param string
     */
    public void setContentText(String string) {
        mEdContent.setText(string);
    }

    /**
     * 设置结果
     *
     * @param string
     */
    public void setTvUint(String string) {
        mTvUnit.setText(string);
    }


    /**
     * 获得输入的计算数据
     *
     * @return
     */
    public String getContentText() {
        return mEdContent.getText().toString().trim();
    }
}
