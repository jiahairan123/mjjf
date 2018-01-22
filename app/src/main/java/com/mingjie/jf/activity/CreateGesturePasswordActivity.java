package com.mingjie.jf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjie.jf.R;
import com.mingjie.jf.application.BaseApplication;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.Constants;
import com.mingjie.jf.utils.LockPatternUtils;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.widgets.LockPatternView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QinSiYi on 2016-8-11.
 */
public class CreateGesturePasswordActivity extends ActionBarActivity
{
    private static final int ID_EMPTY_MESSAGE = -1;
    private static final String KEY_UI_STAGE = "uiStage";
    private static final String KEY_PATTERN_CHOICE = "chosenPattern";
    private LockPatternView mLockPatternView;
    protected TextView mCreateTitle;
    protected List<LockPatternView.Cell> mChosenPattern = null;
    private Toast mToast;
    private Stage mUiStage = Stage.Introduction;
    private View mPreviewViews[][] = new View[3][3];
    /**
     * The patten used during the help screen to show how to draw a pattern.
     */
    private final List<LockPatternView.Cell> mAnimatePattern = new ArrayList<LockPatternView.Cell>();
    private TextView mTvAffim;
    private TextView mTvError;
    private String isResetpass;

    /**
     * The states of the left footer button.
     */
    enum LeftButtonMode
    {
        Cancel(android.R.string.cancel, true), CancelDisabled(android.R.string.cancel, false), Retry(R.string
            .lockpattern_retry_button_text, true), RetryDisabled(R.string.lockpattern_retry_button_text, false), Gone
            (ID_EMPTY_MESSAGE, false);

        /**
         * @param text    The displayed text for this mode.
         * @param enabled Whether the button should be enabled.
         */
        LeftButtonMode(int text, boolean enabled)
        {
            this.text = text;
            this.enabled = enabled;
        }

        final int text;
        final boolean enabled;
    }

    /**
     * The states of the right button.
     */
    enum RightButtonMode
    {
        //        继续
        Continue(R.string.lockpattern_continue_button_text, true), ContinueDisabled(R.string
            .lockpattern_continue_button_text, false), Confirm(R.string.lockpattern_confirm_button_text, true),
        ConfirmDisabled(R.string.lockpattern_confirm_button_text, false), Ok(android.R.string.ok, true);

        /**
         * @param text    The displayed text for this mode.
         * @param enabled Whether the button should be enabled.
         */
        RightButtonMode(int text, boolean enabled)
        {
            this.text = text;
            this.enabled = enabled;
        }

        final int text;
        final boolean enabled;
    }

    /**
     * Keep track internally of where the user is in choosing a pattern.
     */
    protected enum Stage
    {

        //        绘制解锁图案、
        Introduction(R.string.lockpattern_recording_intro_header, LeftButtonMode.Cancel, RightButtonMode
                .ContinueDisabled, ID_EMPTY_MESSAGE, true), HelpScreen(R.string
            .lockpattern_settings_help_how_to_record, LeftButtonMode.Gone, RightButtonMode.Ok, ID_EMPTY_MESSAGE,
            false), ChoiceTooShort(R.string.lockpattern_recording_incorrect_too_short, LeftButtonMode.Retry,
            RightButtonMode.ContinueDisabled, ID_EMPTY_MESSAGE, true), FirstChoiceValid(R.string
            .lockpattern_pattern_entered_header, LeftButtonMode.Retry, RightButtonMode.Continue,
            ID_EMPTY_MESSAGE, false), NeedToConfirm(R.string.lockpattern_need_to_confirm, LeftButtonMode.Cancel,
            RightButtonMode.ConfirmDisabled, ID_EMPTY_MESSAGE, true), ConfirmWrong(R.string
            .lockpattern_need_to_unlock_wrong, LeftButtonMode.Cancel, RightButtonMode.ConfirmDisabled,
            ID_EMPTY_MESSAGE, true), ChoiceConfirmed(R.string.lockpattern_pattern_confirmed_header,
            LeftButtonMode.Cancel, RightButtonMode.Confirm, ID_EMPTY_MESSAGE, false);

        /**
         * @param headerMessage  The message displayed at the top.
         * @param leftMode       The mode of the left button.
         * @param rightMode      The mode of the right button.
         * @param footerMessage  The footer message.
         * @param patternEnabled Whether the pattern widget is enabled.
         */
        Stage(int headerMessage, LeftButtonMode leftMode, RightButtonMode rightMode, int footerMessage,
              boolean patternEnabled)
        {
            this.headerMessage = headerMessage;
            this.leftMode = leftMode;
            this.rightMode = rightMode;
            this.footerMessage = footerMessage;
            this.patternEnabled = patternEnabled;
        }

        final int headerMessage;
        final LeftButtonMode leftMode;
        final RightButtonMode rightMode;
        final int footerMessage;
        final boolean patternEnabled;
    }

    private void showToast(CharSequence message)
    {
        if (null == mToast)
        {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        }
        else
        {
            mToast.setText(message);
        }

        mToast.show();
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
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // 去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.gesturepassword_creat);
        // 初始化演示动画
        mAnimatePattern.add(LockPatternView.Cell.of(0, 0));
        mAnimatePattern.add(LockPatternView.Cell.of(0, 1));
        mAnimatePattern.add(LockPatternView.Cell.of(1, 1));
        mAnimatePattern.add(LockPatternView.Cell.of(2, 1));
        mAnimatePattern.add(LockPatternView.Cell.of(2, 2));

        mLockPatternView = (LockPatternView) this.findViewById(R.id.gesturepwd_create_lockview);
        mCreateTitle = (TextView) findViewById(R.id.tv_gesturepwd_create_text);//设置手势密码
        mTvError = (TextView) findViewById(R.id.tv_error);
        mTvAffim = (TextView) findViewById(R.id.tv_affim_input);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);

        isResetpass = getIntent().getStringExtra("resetpassFlag");

        initPreviewViews();
        if (savedInstanceState == null)
        {
            updateStage(Stage.Introduction);
        }
        else
        {
            // restore from previous state
            final String patternString = savedInstanceState.getString(KEY_PATTERN_CHOICE);
            if (patternString != null)
            {
                mChosenPattern = LockPatternUtils.stringToPattern(patternString);
            }
            updateStage(Stage.values()[savedInstanceState.getInt(KEY_UI_STAGE)]);
        }

    }

    private void initPreviewViews()
    {
        mPreviewViews = new View[3][3];
        mPreviewViews[0][0] = findViewById(R.id.gesturepwd_setting_preview_0);
        mPreviewViews[0][1] = findViewById(R.id.gesturepwd_setting_preview_1);
        mPreviewViews[0][2] = findViewById(R.id.gesturepwd_setting_preview_2);
        mPreviewViews[1][0] = findViewById(R.id.gesturepwd_setting_preview_3);
        mPreviewViews[1][1] = findViewById(R.id.gesturepwd_setting_preview_4);
        mPreviewViews[1][2] = findViewById(R.id.gesturepwd_setting_preview_5);
        mPreviewViews[2][0] = findViewById(R.id.gesturepwd_setting_preview_6);
        mPreviewViews[2][1] = findViewById(R.id.gesturepwd_setting_preview_7);
        mPreviewViews[2][2] = findViewById(R.id.gesturepwd_setting_preview_8);
    }

    private void updatePreviewViews()
    {
        if (mChosenPattern == null)
            return;
        for (LockPatternView.Cell cell : mChosenPattern)
        {
            mPreviewViews[cell.getRow()][cell.getColumn()].setBackgroundResource(R.mipmap.preview_select);

        }
    }


    /**
     * 清除预览
     */
    private void clearPreviews()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                mPreviewViews[i][j].setBackgroundResource(R.mipmap.preview_normal);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_UI_STAGE, mUiStage.ordinal());
        if (mChosenPattern != null)
        {
            outState.putString(KEY_PATTERN_CHOICE, LockPatternUtils.patternToString(mChosenPattern));
        }
    }

    //    @Override
    //    public boolean onKeyDown(int keyCode, KeyEvent event) {
    //        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
    //            if (mUiStage == Stage.HelpScreen) {
    //                updateStage(Stage.Introduction);
    //                return true;
    //            }
    //        }
    //        return false;
    //    }

    private Runnable mClearPatternRunnable = new Runnable()
    {
        public void run()
        {
            mLockPatternView.clearPattern();
        }
    };

    protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener()
    {

        public void onPatternStart()
        {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            mTvError.setVisibility(View.INVISIBLE);
            patternInProgress();
        }

        public void onPatternCleared()
        {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }

        @Override
        public void onPatternCellAdded(List<LockPatternView.Cell> pattern)
        {
            clearPreviews();
            for (LockPatternView.Cell cell : pattern)
            {
                mPreviewViews[cell.getRow()][cell.getColumn()].setBackgroundResource(R.mipmap.preview_select);

            }
        }

        public void onPatternDetected(List<LockPatternView.Cell> pattern)
        {
            if (pattern == null)
                return;
            if (mUiStage == Stage.NeedToConfirm || mUiStage == Stage.ConfirmWrong)
            {
                if (mChosenPattern == null)
                    throw new IllegalStateException("null chosen pattern in stage 'need to confirm");
                if (mChosenPattern.equals(pattern))
                {
                    //                    两次绘制相同
                    updateStage(Stage.ChoiceConfirmed);
                }
                else
                {
                    //                    两次绘制不同
                    updateStage(Stage.ConfirmWrong);
                }
            }
            else if (mUiStage == Stage.Introduction || mUiStage == Stage.ChoiceTooShort)
            {
                if (pattern.size() < LockPatternUtils.MIN_LOCK_PATTERN_SIZE)
                {
                    updateStage(Stage.ChoiceTooShort);
                }
                else
                {
                    //                    第一次绘制
                    mChosenPattern = new ArrayList<LockPatternView.Cell>(pattern);
                    updateStage(Stage.FirstChoiceValid);
                }
            }
            else
            {
                throw new IllegalStateException("Unexpected stage " + mUiStage + " when " + "entering the pattern.");
            }
        }

        private void patternInProgress()
        {
            mCreateTitle.setText(R.string.lockpattern_recording_inprogress);
        }

    };


    /**
     * 更新状态
     *
     * @param stage
     */
    private void updateStage(Stage stage)
    {
        mUiStage = stage;
        //        绘制点数少于4个
        if (stage == Stage.ChoiceTooShort)
        {
            mCreateTitle.setText(getResources().getString(stage.headerMessage, LockPatternUtils.MIN_LOCK_PATTERN_SIZE));
        }
        else
        {
            mCreateTitle.setText(stage.headerMessage);
        }
        if (stage.patternEnabled)
        {
            mLockPatternView.enableInput();
        }
        else
        {
            mLockPatternView.disableInput();
        }

        mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);

        switch (mUiStage)
        {
            //            图案已记录
            case Introduction:
                //        如果Intent带了参数过来，属于重设密码
                if (isResetpass != null)
                {
                    mCreateTitle.setText("修改手势密码");
                    mTvAffim.setVisibility(View.VISIBLE);
                    mTvAffim.setText("请设置新密码");
                }
                mLockPatternView.clearPattern();
                break;
            //            帮助，如何绘制解锁
            case HelpScreen:
                mLockPatternView.setPattern(LockPatternView.DisplayMode.Animate, mAnimatePattern);
                break;
            //            绘制密码过短，少于4个点
            case ChoiceTooShort:
                mTvError.setVisibility(View.INVISIBLE);
                mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                clearPreviews();
                postClearPatternRunnable();
                break;
            //            第一次绘制
            case FirstChoiceValid:
                updateStage(Stage.NeedToConfirm);
                break;
            //            继续
            case NeedToConfirm:
                mTvError.setVisibility(View.INVISIBLE);
                mLockPatternView.clearPattern();
                mTvAffim.setVisibility(View.VISIBLE);
                mTvAffim.setText("请再输入一次确认");
                updatePreviewViews();
                break;
            //            与前一次绘制不一致
            case ConfirmWrong:
                mTvAffim.setVisibility(View.INVISIBLE);
                mTvError.setVisibility(View.VISIBLE);
                mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                mLockPatternView.clearPattern();
                mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
                postClearPatternRunnable();
                UIUtils.showToast(this, "两次绘制图案不同，请重新绘制！");
                updateStage(Stage.Introduction);

                break;
            //            确定保存新图案
            case ChoiceConfirmed:
                saveChosenPatternAndFinish();
                //                startActivity(new Intent(this, UnlockGesturePasswordActivity.class));
                break;
        }

    }

    // clear the wrong pattern unless they have started a new one
    // already
    private void postClearPatternRunnable()
    {
        mLockPatternView.removeCallbacks(mClearPatternRunnable);
        mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
    }

    private void saveChosenPatternAndFinish()
    {
        MobclickAgent.onEvent(this, "CG0066");
        BaseApplication.getLockPatternUtils().saveLockPattern(mChosenPattern, CacheUtils.getUserinfo(this).getName());
        CacheUtils.setBoolean(this, Constants.LOCK, true);
        Intent intent = new Intent();
        intent.putExtra("isSuccess", true);
        setResult(Activity.RESULT_OK, intent);
        showToast(isResetpass == null ? "密码设置成功" : "密码修改成功");
        finish();
    }
}

