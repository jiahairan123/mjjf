package com.mingjie.jf.activity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @项目名: cunguan
 * @包名: cunguan
 * @类名: SplashActivity
 * @创建者: qinsiyi
 * @描述 启动页面
 */
public class SplashActivity extends BaseActivity implements Animation.AnimationListener
{

    @BindView(R.id.rl_splash_rootview)
    RelativeLayout mLinearRootView;
    //    @BindView(R.id.splsh_denglu)
    //    Button splshDenglu;//登录
    //    @BindView(R.id.splsh_zhuce)
    //    Button splshZhuce;//注册
    //    @BindView(R.id.pass)
    //    TextView pass;//跳过

    @Override
    protected void initView()
    {
//      CacheUtils.clearUserInfo();
        // 去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        closeImmerseStyle();
        //        设置布局
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        // pass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        // pass.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void initData()
    {
        EventBus.getDefault().register(this);
        //如果是登录状态，直接跳主页，关闭该页面
        CfLog.d("CacheUtils.isLogin()=" + CacheUtils.isLogin());
        //是否设置锁
        boolean isLock = CacheUtils.getBoolean(UIUtils.getContext(), Constants.LOCK, false);
        //  是否第一次打开app
        boolean isFirstOpen = CacheUtils.getBoolean(UIUtils.getContext(), Constants.KEY_FIRST_START, true);

        if (isFirstOpen)
        {
            //          第一次打开应用, 进入引导页
            startActivity(new Intent(this, GuideActivity.class));
            finish();
        }
        else if (CacheUtils.isLogin() && isLock)
        {
            startActivity(new Intent(this, UnlockGesturePasswordActivity.class));
            finish();
        }
        else if (CacheUtils.isLogin() && !isLock)
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if (!CacheUtils.isLogin())
        {
            mLinearRootView.startAnimation(initAnimation());
        }
    }

    private Animation initAnimation()
    {
        // 渐变展示启动屏
        AlphaAnimation alpha = new AlphaAnimation(0.4f, 1.0f);
        alpha.setDuration(2000);
        alpha.setAnimationListener(this);
        return alpha;
    }

    @Override
    public void onAnimationStart(Animation animation)
    {

    }

    @Override
    public void onAnimationEnd(Animation animation)
    {

        //        //  是否第一次打开app
        //        boolean isFirstOpen = CacheUtils.getBoolean(UIUtils.getContext(), Constants.KEY_FIRST_START, true);
        //        //是否设置锁
        //        boolean isLock = CacheUtils.getBoolean(UIUtils.getContext(), Constants.LOCK, false);
        //        if (isFirstOpen) {
        //            //          第一次打开应用
        //            startActivity(new Intent(this, GuideActivity.class));
        //        } else {
        //            //            不是第一次打开，判断是否设置了手势密码
        //            startActivity(new Intent(this, isLock ? UnlockGesturePasswordActivity.class : MainActivity
        // .class));
        //        }
    }


    @Override
    public void onAnimationRepeat(Animation animation)
    {

    }

    public void onEvent(Event event)
    {
        CfLog.d("登录成功-SplashActivity");
        if (event.eventType == Event.LOGIN_SUCCESS)
        {
            finish();
        }
    }

    // 登录 注册 和跳过   的 点击事件
    @OnClick({R.id.splsh_denglu, R.id.splsh_zhuce, R.id.pass})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.splsh_denglu:
                startActivity(new Intent(this, LoginActivity.class));
                //                finish();
                break;
            case R.id.splsh_zhuce:
                startActivity(new Intent(this, UserRegisterActivity.class));
                //                finish();
                break;
            case R.id.pass:
                //                //  是否第一次打开app
                //                boolean isFirstOpen = CacheUtils.getBoolean(UIUtils.getContext(), Constants
                // .KEY_FIRST_START, true);
                //                if (isFirstOpen) {
                //                    //          第一次打开应用
                //                    startActivity(new Intent(this, GuideActivity.class));
                //                }
                CacheUtils.setBoolean(this, "isLogin", false);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(MainActivity.KEY_NEED_AUTOLOGIN, false);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().unregister(this);
        }
    }
}
