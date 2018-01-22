package com.mingjie.jf.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.utils.Utilities;


/**
 * Created by Administrator on 2015/12/22.
 */
public class DialogFactory {


    public static void showAlertDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setPositiveButton("确定", null).create().show();
    }


    /**
     * 点击登录按钮后，弹出验证对话框
     */
    public static Dialog mDialog = null;

    /**
     * 弹出查询对话框，记得用完回收mDialog
     */
    public static Dialog creatRequestDialog(Context context, String loadingText) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = Utilities.getScreenWidth(context);
        lp.width = (int) (0.6 * width);
        TextView titleTxtv = (TextView) dialog.findViewById(R.id.tvLoad);
        if (TextUtils.isEmpty(loadingText)) {
            titleTxtv.setText(R.string.sending_request);
        } else {
            titleTxtv.setText(loadingText);
        }
        dialog.setCancelable(false);
        return dialog;
    }

    /**
     * 弹出查询对话框，记得用完回收mDialog
     */
    public static void showRequestDialog(Context context, String title) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = DialogFactory.creatRequestDialog(context, title);
        mDialog.show();
    }

    /**
     * 弹出查询对话框，记得用完回收mDialog
     */
    public static void showRequestDialog(Context context) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = DialogFactory.creatRequestDialog(context, null);
        mDialog.show();
    }


}
