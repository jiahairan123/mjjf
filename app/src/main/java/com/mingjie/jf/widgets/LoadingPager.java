package com.mingjie.jf.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mingjie.jf.R;
import com.mingjie.jf.utils.UIUtils;

/*
 *  @项目名：  CaffcooP2P 
 *  @包名：    caffp2p.android.widgets
 *  @类名:    LoadingPager
 *  @创建者:   陈小平
 *  @创建时间:  2016/01/20 14:32
 *  @描述：    TODO
 */
public abstract class LoadingPager extends FrameLayout implements View.OnClickListener {
    private final static int STATE_NONE = 0;            // 加载中的状态
    private final static int STATE_LOADING = 1;          // 加载中的状态
    private final static int STATE_EMPTY = 2;            // 空的状态
    private final static int STATE_ERROR = 3;            // 错误的状态
    private final static int STATE_SUCCESS = 4;           // 成功的状态

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mSuccessView;

        private int mCurrentState = STATE_ERROR;    // 默认是错误界面
//    private int mCurrentState = STATE_SUCCESS;
    private ImageView mError;

    public LoadingPager(Context context) {
        super(context);
        initView();
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        //加载中
        if (mLoadingView == null) {
            mLoadingView = View.inflate(getContext(), R.layout.widget_loading, null);
            addView(mLoadingView);
        }

        // 空页面
        if (mEmptyView == null) {
            mEmptyView = View.inflate(getContext(), R.layout.widget_empty, null);
            addView(mEmptyView);
        }

        // 错误界面
        if (mErrorView == null) {
            mErrorView = View.inflate(getContext(), R.layout.widget_error, null);
            addView(mErrorView);
        }

        mError = (ImageView) mErrorView.findViewById(R.id.avloadingIndicatorView);
        ImageView mIv = (ImageView) mEmptyView.findViewById(R.id.avloadingIndicatorView);
        mError.setOnClickListener(this);
        mIv.setOnClickListener(this);
        // 通过状态更新View的显示
        safeUpdateUI();
    }

    private void safeUpdateUI() {
        UIUtils.post(new Runnable() {

            @Override
            public void run() {
                updateUI();
            }
        });

    }

    private void updateUI() {
        mLoadingView.setVisibility((mCurrentState == STATE_NONE || mCurrentState == STATE_LOADING) ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility(mCurrentState == STATE_EMPTY ? View.VISIBLE : View.GONE);
        mErrorView.setVisibility(mCurrentState == STATE_ERROR ? View.VISIBLE : View.GONE);

        if (mCurrentState == STATE_SUCCESS && mSuccessView == null) {
            // 需要创造成功的View
            mSuccessView = initSuccessView();

            addView(mSuccessView);
        }

        // 成功的view
        if (mSuccessView != null) {
            mSuccessView.setVisibility(mCurrentState == STATE_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 加载数据
     */
    public void loadData() {
        // 如果现在是成功状态就不去加载
        if (mCurrentState != STATE_SUCCESS && mCurrentState != STATE_LOADING) {
            mCurrentState = STATE_LOADING;
            safeUpdateUI();
            new Thread(new LoadDataTask()).start();
        }
    }

    /**
     * 初始化成功显示的view
     *
     * @return
     */
    protected abstract View initSuccessView();

    protected abstract LoadedResult onLoadData();

    class LoadDataTask implements Runnable {

        @Override
        public void run() {
//            // 加载数据
            LoadedResult result = onLoadData();
            mCurrentState = result.getState();
            // 在子线程中执行ui操作
            safeUpdateUI();
        }
    }

    public enum LoadedResult {
        EMPTY(STATE_EMPTY), ERROR(STATE_ERROR), SUCCESS(STATE_SUCCESS) , LOADING(STATE_LOADING);

        private int state;

        LoadedResult(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

    @Override
    public void onClick(View v) {
        loadData();
    }
}
