package com.mingjie.jf.activity;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.KeyboardUtil;
import com.mingjie.jf.view.TitleView;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * qinsiyi
 *
 * @描述 Activity基类，所有activity使用时都继承该基类
 */
public abstract class BaseActivity extends AutoLayoutActivity
{
    // 对所有的activity进行管理
    private static List<Activity> mActivities = new LinkedList<Activity>();
    // 当前activity
    private static Activity mCurrentActivity;

    private boolean hasTitle = false;

    private InputMethodManager mManager;

    /**
     * 沉浸状态栏效果默认为开启，如果不需要该效果的activity，请在initView中调用关闭方法——>closeImmerseStyle（）
     */
    private boolean isOpenImmerseStyle = true;

//    protected RelativeLayout mTitltRootView;
//    protected ImageView mIvLeft;
//    protected TextView mTvTitle;
//    protected ImageView mIvRight;

    @Nullable
    @BindView(R.id.view_title)
    protected TitleView mTitleView;
    @Nullable
    @BindView(R.id.view_keyboard)
    protected KeyboardView vkbd; // 自定义键盘

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        CfLog.d("--- onCreate: " + this.getClass().getSimpleName());
        mManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        synchronized (mActivities)
        {
            mActivities.add(this);
        }
        initView();
        // 初始化数据
        initData();
    }

    /**
     * 设置显示全屏
     */
    protected void setNoPublicTitle()
    {
        this.hasTitle = true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mCurrentActivity = null;
        synchronized (mActivities)
        {
            mActivities.remove(this);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (null != mTitleView)
        {
            mTitleView.setOnClickLitenerLeft(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mCurrentActivity = this;
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // mCurrentActivity = null;
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    /**
     * 安全的退出应用
     **/
    public static void exitApp()
    {
        //        CacheUtils.remove(getCurrentActivity(), Constants.COOKIE);
        //
        //        ListIterator<Activity> iterator = mActivities.listIterator();
        //        while (iterator.hasNext()) {
        //            Activity next = iterator.next();
        //            next.finish();
        //        }
    }

    /**
     * 获取前台activity
     *
     * @return 返回前台activity
     */
    public static Activity getCurrentActivity()
    {
        return mCurrentActivity;
    }

    protected abstract void initView();

    protected abstract void initData();

    protected void showToast(int rId)
    {
        Toast.makeText(this, rId, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏系统键盘
     */
    protected void hideKeyboard()
    {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null)
        {
            mManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager
                    .HIDE_NOT_ALWAYS);
            // vkbd.setVisibility(View.GONE);
        }
    }

    /**
     * 显示自定义键盘
     *
     * @param vkbd
     * @param edtInput
     */
    private void showViewKeyboard(KeyboardView vkbd, EditText edtInput)
    {
        hideKeyboard(); // 隐藏系统键盘和自定义键盘
        // vkbd.setVisibility(View.GONE);
        int inputType = edtInput.getInputType();
        edtInput.setInputType(InputType.TYPE_NULL);
        new KeyboardUtil(this, vkbd, edtInput).showKeyboard();
        edtInput.setInputType(inputType);
    }

    /**
     * 设置输入框自定义键盘
     *
     * @param vkbd
     * @param edtInput
     */
    protected void setClickAndTouchListener(final KeyboardView vkbd, final EditText edtInput)
    {
        //edtInput.setOnClickListener(new View.OnClickListener()
        //{
        //    @Override
        //    public void onClick(View v)
        //    {
        //        CfLog.d("onClick");
        //        showViewKeyboard(vkbd, edtInput);
        //    }
        //});

        edtInput.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    // CfLog.d("onTouch");
                    showViewKeyboard(vkbd, edtInput);
                }
                return false;
            }
        });

        edtInput.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                edtInput.setSelection(edtInput.length());
                if (!hasFocus)
                {
                    vkbd.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 不采用沉浸状态蓝效果的activity，在initView()里面调用此方法，沉浸效果默认为开启
     */
    protected void closeImmerseStyle()
    {
        isOpenImmerseStyle = false;
    }

    /**
     * 所有子类
     * 点击键盘外空白,键盘消失
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null)
            {
                mManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager
                        .HIDE_NOT_ALWAYS);
            }
        }

        if (null != vkbd)
        {
            vkbd.setVisibility(View.GONE);
        }
        return super.onTouchEvent(event);
    }
}
