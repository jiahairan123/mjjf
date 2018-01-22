package com.mingjie.jf.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by qinsiyi on 2016.6.28.
 */
public class TimeCount extends CountDownTimer {

    Button button;

    public TimeCount(long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        this.button = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        button.setEnabled(false);
        button.setBackgroundColor(Color.GRAY);
        button.setText(millisUntilFinished / 1000 + "秒后重送");
    }

    @Override
    public void onFinish() {
        button.setText("获取验证码");
        button.setEnabled(true);
        //#47a9f2
        button.setBackgroundColor(Color.parseColor("#47a9f2"));
    }
}
