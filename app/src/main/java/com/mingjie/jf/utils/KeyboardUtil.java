package com.mingjie.jf.utils;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.mingjie.jf.R;

import java.util.List;

public class KeyboardUtil
{
    //	private Context ctx;
    //	private Activity act;
    private KeyboardView keyboardView;
    private Keyboard k1;// 字母键盘
    private Keyboard k2;// 数字键盘
    private Keyboard k3;// 符号键盘
    public boolean isnun = false;// 是否数据键盘
    public boolean isupper = false;// 是否大写

    private EditText ed;

    //    public KeyboardUtil(Activity act, Context ctx, EditText edit)
    //    {
    //        //		this.act = act;
    //        //		this.ctx = ctx;
    //        this.ed = edit;
    //        k1 = new Keyboard(ctx, R.xml.qwerty);
    //        k2 = new Keyboard(ctx, R.xml.symbols);
    //        keyboardView = (KeyboardView) act.findViewById(R.id.keyboard_view);
    //        keyboardView.setKeyboard(k1);
    //        keyboardView.setEnabled(true);
    //        keyboardView.setPreviewEnabled(true);
    //        keyboardView.setOnKeyboardActionListener(listener);
    //    }

    public KeyboardUtil(Context ctx, KeyboardView view, final EditText edit)
    {
        this.ed = edit;
        k1 = new Keyboard(ctx, R.xml.qwerty); //qwerty
        k2 = new Keyboard(ctx, R.xml.symbols); //symbols
        k3 = new Keyboard(ctx, R.xml.kbd_symbols); // kbd_symbols
        keyboardView = view; //(KeyboardView) act.findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false); // keyPreviewHeight=0
        keyboardView.setOnKeyboardActionListener(listener);
        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            edit.setShowSoftInputOnFocus(false);
        }
        edit.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    edit.setSelection(edit.length());
                }
            }
        });
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener()
    {
        @Override
        public void swipeUp()
        {
        }

        @Override
        public void swipeRight()
        {
        }

        @Override
        public void swipeLeft()
        {
        }

        @Override
        public void swipeDown()
        {
        }

        @Override
        public void onText(CharSequence text)
        {
            // CfLog.d(String.valueOf(text));
        }

        @Override
        public void onRelease(int primaryCode)
        {
        }

        @Override
        public void onPress(int primaryCode)
        {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes)
        {
            // CfLog.d(primaryCode + ", length: " + keyCodes.length);
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL)
            {// 完成
                hideKeyboard();
            }
            else if (primaryCode == Keyboard.KEYCODE_DELETE)
            {// 回退
                if (editable != null && editable.length() > 0)
                {
                    if (start > 0)
                    {
                        editable.delete(start - 1, start);
                    }
                }
            }
            else if (primaryCode == Keyboard.KEYCODE_SHIFT)
            {// 大小写切换
                changeKey();
                keyboardView.setKeyboard(k1);

            }
            else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE)
            {// 数字键盘切换
                if (isnun)
                {
                    isnun = false;
                    keyboardView.setKeyboard(k1);
                }
                else
                {
                    isnun = true;
                    keyboardView.setKeyboard(k2);
                }
            }
            else if (primaryCode == 6300)
            {
                keyboardView.setKeyboard(k3);
            }
            else if (primaryCode == 57419)
            { // go left
                if (start > 0)
                {
                    ed.setSelection(start - 1);
                }
            }
            else if (primaryCode == 57421)
            { // go right
                if (start < ed.length())
                {
                    ed.setSelection(start + 1);
                }
            }
            else
            {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey()
    {
        List<Key> keylist = k1.getKeys();
        if (isupper)
        {//大写切换小写
            isupper = false;
            for (Key key : keylist)
            {
                if (key.label != null && isWord(key.label.toString()))
                {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        }
        else
        {//小写切换大写
            isupper = true;
            for (Key key : keylist)
            {
                if (key.label != null && isWord(key.label.toString()))
                {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }

    public void showKeyboard()
    {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE)
        {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard()
    {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE)
        {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isWord(String str)
    {
        String wordStr = "abcdefghijklmnopqrstuvwxyz";
        if (wordStr.indexOf(str.toLowerCase()) > -1)
        {
            return true;
        }
        return false;
    }

}
