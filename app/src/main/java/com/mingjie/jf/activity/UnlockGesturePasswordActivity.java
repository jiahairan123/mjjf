package com.mingjie.jf.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.application.BaseApplication;
import com.mingjie.jf.bean.Event;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.LockPatternUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.widgets.LockPatternView;
import com.mingjie.jf.widgets.QuitDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 修改密码
 */
public class UnlockGesturePasswordActivity extends ActionBarActivity implements OnClickListener, QuitDialog.OnEnterListener, QuitDialog.OnCancelListener {
    private LockPatternView mLockPatternView;
    private int mFailedPatternAttemptsSinceLastTimeout = 0;
    private CountDownTimer mCountdownTimer = null;
    private Handler mHandler = new Handler();
    private TextView mHeadTextView;
    private Animation mShakeAnim;
    private TextView gesturepwd_unlock_forget;


    private Toast mToast;
    private TextView mTvOtherLogin;
    private QuitDialog quitDialog;
    private String mRestPassFlag;

    private void showToast(CharSequence message) {
        if (null == mToast) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }

        mToast.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_figure_lock);

        mLockPatternView = (LockPatternView) this.findViewById(R.id.gesturepwd_unlock_lockview);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);

        //        增加代码
        mRestPassFlag = getIntent().getStringExtra("resetpass");

        mLockPatternView.setTactileFeedbackEnabled(false);

        mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_failtip);
        mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
        gesturepwd_unlock_forget = (TextView) findViewById(R.id.tv_forget_picpass);//忘记密码
        TextView mTvUnlockTitle = (TextView) findViewById(R.id.tv_unlock_title);
        mTvOtherLogin = (TextView) findViewById(R.id.tv_other_login);//其他账户登陆
        LinearLayout preview = (LinearLayout) findViewById(R.id.preview_unlock);
        ImageView mIvLockIcon = (ImageView) findViewById(R.id.iv_unlock);
        preview.setVisibility(View.INVISIBLE);
        gesturepwd_unlock_forget.setOnClickListener(this);
        mTvOtherLogin.setOnClickListener(this);

        //        修改密码
        if (mRestPassFlag != null) {
            mIvLockIcon.setVisibility(View.GONE);
            mTvUnlockTitle.setText("请输入原始密码");
            preview.setVisibility(View.VISIBLE);
            mHeadTextView.setVisibility(View.INVISIBLE);
            mTvOtherLogin.setVisibility(View.INVISIBLE);
            gesturepwd_unlock_forget.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面
        MobclickAgent.onResume(this);       //统计时长
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountdownTimer != null) mCountdownTimer.cancel();
    }

    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            mLockPatternView.clearPattern();
        }
    };

    protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

        public void onPatternStart() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            mHeadTextView.setVisibility(View.INVISIBLE);
            patternInProgress();
        }

        public void onPatternCleared() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }

        @Override
        public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

        }

        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
            mHeadTextView.setVisibility(View.VISIBLE);
            if (pattern == null) {
                return;
            }
            if (BaseApplication.getLockPatternUtils().checkPattern(pattern)) {
                mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);

                if (BaseApplication.isActive()) {
                    //                    showToast("解锁成功");
                    BaseApplication.setActive(false);
                    finish();
                } else {

                    if (mRestPassFlag != null) {
                        //                        重置密码
                        Intent resetPass = new Intent(UnlockGesturePasswordActivity.this, CreateGesturePasswordActivity.class);
                        resetPass.putExtra("resetpassFlag", "resetpass");
                        startActivity(resetPass);
                        finish();
                    } else {
                        //                        验证成功,进入首页
                        startActivity(new Intent(UnlockGesturePasswordActivity.this, MainActivity.class));
                        finish();
                    }
                }

            } else {
                mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
                    mFailedPatternAttemptsSinceLastTimeout++;
                    int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT - mFailedPatternAttemptsSinceLastTimeout;
                    if (retry >= 0) {
                        if (retry == 0) showToast("您已5次输错密码，请30秒后再试");
                        int count = 5 - retry;
                        mHeadTextView.setText("密码错误" + count + "次(5次失败后需要重新登录)");
                        //mHeadTextView.setTextColor(Color.RED);
                        mHeadTextView.startAnimation(mShakeAnim);
                    }

                } else {
                    showToast("输入长度不够，请重试");
                    mHeadTextView.setVisibility(View.INVISIBLE);
                }

                if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
                    //连续输错5次手势密码，清除用户缓存信息，弹出登录页面
                    CacheUtils.clearUserInfo();
                    Event event = new Event();
                    event.eventType = Event.LOGOUT;
                    EventBus.getDefault().post(event);
                    Intent loginIntent = new Intent(UnlockGesturePasswordActivity.this,LoginActivity.class);
                    loginIntent.putExtra(LoginActivity.KEY_START_TYPE,LoginActivity.START_TYEP_2MAIN);
                    startActivity(loginIntent);
                    finish();
                    // mHandler.postDelayed(attemptLockout, 1000);
                } else {
                    mLockPatternView.postDelayed(mClearPatternRunnable, 1000);
                }
            }
        }


        private void patternInProgress() {

        }
    };
    Runnable attemptLockout = new Runnable() {

        @Override
        public void run() {
            mHeadTextView.setVisibility(View.VISIBLE);
            mLockPatternView.clearPattern();
            mLockPatternView.setEnabled(false);
            mCountdownTimer = new CountDownTimer(LockPatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
                    if (secondsRemaining > 0) {
                        mHeadTextView.setText("请" + secondsRemaining + " 秒后重试");
                        mHeadTextView.setTextColor(Color.WHITE);
                    } else {
                        mHeadTextView.setText("");
                        //mHeadTextView.setTextColor(Color.WHITE);
                    }

                }

                @Override
                public void onFinish() {
                    mLockPatternView.setEnabled(true);
                    mFailedPatternAttemptsSinceLastTimeout = 0;
                }
            }.start();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //            忘记密码
            case R.id.tv_forget_picpass:
                quitDialog = new QuitDialog(this, "温馨提示", "是否确定重新登陆账号？", "取消", "确定");
                quitDialog.setEnterListener(this);
                quitDialog.setCancelListener(this);
                quitDialog.show();
                break;
            //            其他账户登陆
            case R.id.tv_other_login:
                BaseApplication.getLockPatternUtils().clearLock();
                CacheUtils.clearUserInfo();
                Intent login = new Intent(UnlockGesturePasswordActivity.this, LoginActivity.class);
                startActivity(login);
                break;
            default:
                break;
        }
    }

    @Override
    public void onEnterListener() {
        Intent login = new Intent(UnlockGesturePasswordActivity.this, LoginActivity.class);
        login.putExtra(LoginActivity.KEY_START_TYPE,LoginActivity.START_TYEP_2MAIN);
        login.putExtra("mCurrentFlag", Constants.PIC_PASS);
        startActivity(login);
        quitDialog.dismiss();
        CacheUtils.clearUserInfo();
        CacheUtils.setBoolean(this,CacheUtils.getUserinfo(this).getName()+"_"+Constants.IS_OPEN_GESTURE,false);
        finish();
    }

    @Override
    public void onCancelListener() {
        UIUtils.showToast(this,"取消");
        quitDialog.dismiss();
    }
}
