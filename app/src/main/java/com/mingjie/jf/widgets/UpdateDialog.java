package com.mingjie.jf.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.mingjie.jf.R;

/*
 *  @项目名：  CaffcooP2P 
 *  @包名：    caffp2p.android.widgets
 *  @类名:    UpdateDialog
 *  @创建者:   陈小平
 *  @创建时间:  2015/10/29 10:10
 *  @描述：    升级更新对话框
 */public class UpdateDialog extends AlertDialog {

    private UpdateProgress mUpdateProgress;
    private Button mBtnQuitApp;

    public UpdateDialog(Context context) {
        this(context, 0);
    }

    public UpdateDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_update_dialog);

        //***************5.0以上适配**************
        //        解决5.0以上的手机上，自定义对话框出现多余边框
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(layoutParams);
        setCancelable(false);
        //***************5.0以上适配**************

        mUpdateProgress = (UpdateProgress) findViewById(R.id.progressBar);
        mBtnQuitApp = (Button) findViewById(R.id.btn_dialog_quitapp);


    }

    /**
     * 设置进度背景
     *
     * @param drawable
     */
    public void setProgressDrawable(Drawable drawable) {
        mUpdateProgress.setProgressDrawable(drawable);
    }

    public void setText(String text) {
        mUpdateProgress.setText(text);
    }

    public void setBtnText(String text) {
        mBtnQuitApp.setText(text);
    }

    /**
     * 设置时时进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        mUpdateProgress.setProgress(progress);
    }

    /**
     * 设置进度条的上限，默认为100
     *
     * @param max
     */
    public void setMax(int max) {
        mUpdateProgress.setMax(max);
    }

    /**
     * 退出按钮回调
     *
     * @param listener
     */
    public void setOnQuitClickListener(View.OnClickListener listener) {
        mBtnQuitApp.setOnClickListener(listener);
    }

}
