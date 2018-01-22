package com.mingjie.jf.application;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by qinsiyi on 2016.6.28.
 */
public class ExampleApplication extends Application
{
    private static final String TAG = "123456";
    
    @Override
    public void onCreate()
    {
        Log.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();
        
    }

    //点击输入框时hint文本就自动消失
    public static View.OnFocusChangeListener onFocusAutoClearHintListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText textView = (EditText) v;
            String hint;
            if (hasFocus) {
                hint = textView.getHint().toString();
                textView.setTag(hint);
                textView.setHint("");
            } else {
                hint = textView.getTag().toString();
                textView.setHint(hint);
            }
        }
    };
}