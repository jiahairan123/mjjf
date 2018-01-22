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

import com.mingjie.jf.R;
import com.mingjie.jf.utils.StringUtils;


/**
 * @包名: caffp2p.android.widgets
 * @类名: InputView
 * @描述 输入框
 */
public class InputView extends RelativeLayout implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener
{

    // private RelativeLayout mRootView;
    private ImageView mLetView;
    private EditText mEditText;
    private ImageView mClear;

    public InputView(Context context)
    {
        super(context);
    }

    public InputView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.widget_inputview, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputView);

        initView(typedArray);

    }


    private void initView(TypedArray typedArray)
    {
        //        获取属性值
        int leftIcon = typedArray.getResourceId(R.styleable.InputView_left_icon, 0);
        int rightIcon = typedArray.getResourceId(R.styleable.InputView_right_icon, 0);
        boolean isHintLeft = typedArray.getBoolean(R.styleable.InputView_left_Hint, false);
        boolean isHintRight = typedArray.getBoolean(R.styleable.InputView_right_Hint, false);
        boolean isShowRight = typedArray.getBoolean(R.styleable.InputView_right_Show, false);
        String hint = typedArray.getString(R.styleable.InputView_edtext_hint);
        int inputType = typedArray.getInt(R.styleable.InputView_edtext_type, 3);

        // mRootView = (RelativeLayout) findViewById(R.id.inputview_rootview);
        mLetView = (ImageView) findViewById(R.id.inputview_iv);
        // View cutView = findViewById(R.id.inputview_cut);
        mEditText = (EditText) findViewById(R.id.inputview_et);
        mClear = (ImageView) findViewById(R.id.inputview_clear);

        initInputType(inputType);
        mLetView.setImageResource(leftIcon);
        mClear.setImageResource(rightIcon);
        mEditText.setHint(hint);
        mEditText.setTextSize(14);
        //Color.rgb(255, 255, 255)
        mEditText.setHintTextColor(Color.rgb(188, 188, 188));

        //        隐藏左边、右边图片
        mLetView.setVisibility(isHintLeft ? View.GONE : View.VISIBLE);
        if (isShowRight)
        {
            mClear.setVisibility(VISIBLE);
        }
        //        cutView.setVisibility(isHintLeft ? View.GONE : View.VISIBLE);

        //        如果清除view不可见，不用监听输入变化
        if (!isHintRight && !isShowRight)
        {
            mEditText.addTextChangedListener(this);
            mEditText.setOnFocusChangeListener(this);
            mClear.setOnClickListener(this);
        }

        typedArray.recycle();

    }

    private void initInputType(int inputType)
    {
        if (inputType == 1)
        {
            mEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        else if (inputType == 2)
        {
            mEditText.setInputType(0x81);
            mEditText.addTextChangedListener(new TextWatcher()
            {
                String tmp = "";

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {
                    tmp = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    String str = s.toString();
                    if (str.equals(tmp))
                    {
                        return;
                    }
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < str.length(); i++)
                    {
                        String s1 = String.valueOf(str.charAt(i));
                        if (!StringUtils.isChineseChart(s1))
                        {
                            sb.append(str.charAt(i));
                        }
                    }
                    tmp = sb.toString();
                    mEditText.setText(tmp);
                    mEditText.setSelection(tmp.length());
                }
            });
        }
        else if (inputType == 3)
        {
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        else if (inputType == 8192)
        {
            mEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            mEditText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {

                }

                @Override
                public void afterTextChanged(Editable editable)
                {
                    String temp = editable.toString();

                    //以字符"."开头的，在"."前加0
                    if (temp.startsWith("."))
                    {
                        editable.insert(0, "0.", 0, 1);
                    }

                    //以“00”开头的，替换为“0.”
                    if (temp.startsWith("00"))
                    {
                        editable.replace(1, 2, ".");
                    }

                    // //剔除非法字符
                    // if (temp.startsWith(".") || temp.startsWith("0")) {
                    //     editable.clear();
                    // }

                    //保留两位小数
                    if (temp.contains("."))
                    {
                        int posDot = temp.indexOf(".");
                        if (temp.length() - posDot - 1 > 2)
                        {
                            //  UIUtils.showToast("精确到两位小数！");
                            editable.delete(posDot + 3, posDot + 4);
                        }
                    }
                }
            });
        }

    }

    public void setInputType(int inputType)
    {
        initInputType(inputType);
    }

    public void setHint(String hint)
    {
        mEditText.setHint(hint);
    }

    /**
     * 清除
     *
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        mEditText.setText("");
    }

    public String getText()
    {
        return mEditText.getText().toString().trim();
    }

    public void setText(String text)
    {
        mEditText.setText(text);
        mEditText.setSelection(text.length());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        String text = s.toString();
        mClear.setVisibility(text.length() > 0 ? View.VISIBLE : View.GONE);

    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        mClear.setVisibility((hasFocus && mEditText.length() > 0) ? View.VISIBLE : View.GONE);

    }
}
