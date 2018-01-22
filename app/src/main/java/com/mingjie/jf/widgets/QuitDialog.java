package com.mingjie.jf.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.mingjie.jf.R;


/**
 * @项目名: CaffcooP2P
 * @包名: caffp2p.android.widgets
 * @类名: QuitDialog
 * @创建者: chenxiaoping
 * @创建时间 2015/10/12 16:36
 * @描述
 */
public class QuitDialog extends AlertDialog implements View.OnClickListener {

    private Button enter;
    private Button cancel;
    private TextView mTitle;
    private TextView content;
    // private Typeface minghei;
    // private Typeface mini;

    private String mStrTitle;
    private String mContent;
    private String mLeftText;
    private String mRightText;

    private OnEnterListener mEnterListener;
    private OnCancelListener mCancelListener;

    /**
     * @param context   上下文
     * @param title     对话框标题
     * @param content   对话框内容
     * @param leftText  左边按钮文字，默认为“取消”
     * @param rightText 右边按钮文字，默认为“退出”
     */
    public QuitDialog(Context context, String title, String content, String leftText, String rightText) {
        super(context);
        this.mStrTitle = title;
        this.mContent = content;
        this.mLeftText = leftText;
        this.mRightText = rightText;

    }

    public QuitDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.widget_quit_dialog);

        //***************5.0以上适配**************
        //        解决5.0以上的手机上，自定义对话框出现多余边框
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(layoutParams);
        //***************5.0以上适配**************

        mTitle = (TextView) findViewById(R.id.tv_dialog_title);
        content = (TextView) findViewById(R.id.tv_dialog_content);
        enter = (Button) findViewById(R.id.btn_dialog_enter);
        cancel = (Button) findViewById(R.id.btn_dialog_cancel);


        mTitle.setText(mStrTitle == null ? "温馨提示" : mStrTitle);
        content.setText(mContent == null ? "是否要退出当前账户？" : mContent);
        cancel.setText(mLeftText == null ? "取消" : mLeftText);
        enter.setText(mRightText == null ? "退出" : mRightText);
        enter.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_enter:
                if (mEnterListener != null) {
                    mEnterListener.onEnterListener();
                }
                break;
            case R.id.btn_dialog_cancel:
                if (mCancelListener != null) {
                    mCancelListener.onCancelListener();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置左边按钮的文字，此方法需要在调用show()方法之后使用
     *
     * @param text
     */
    public void setLeftText(String text) {
        cancel.setText(text);
    }

    /**
     * 设置右边按钮的文字，此方法需要在调用show()方法之后使用
     *
     * @param text
     */
    public void setRightText(String text) {
        enter.setText(text);
    }

    /**
     * 设置对话框内容，此方法需要在调用show()方法之后使用
     *
     * @param text
     */
    public void setContentText(String text) {
        content.setText(text);
    }

    /**
     * 确定按钮回调接口
     */
    public interface OnEnterListener {
        void onEnterListener();
    }

    /**
     * 取消按钮回调接口
     */
    public interface OnCancelListener {
        void onCancelListener();
    }

    public void setEnterListener(OnEnterListener enterListener) {
        this.mEnterListener = enterListener;
    }

    public void setCancelListener(OnCancelListener cancelListener) {
        this.mCancelListener = cancelListener;
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setContent(String message) {
        content.setText(message);
    }
}
