package com.mingjie.jf.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.InvestRecordAdapter;
import com.mingjie.jf.bean.InvestRecordResponse;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述： 投标记录
 * <p>创 建 人：qinsiyi
 * <p>邮箱地址： qinsiyi@ming-hao.cn
 * <p>创建时间： 2016/8/5
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ThrowMarkHistoryActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout
        .OnRefreshListener
{

    TextView tvContent;//activity标题
    private SwipeRefreshLayout mSwipeRefreshLayout;//刷新控件
    private RecyclerView mRecyclerView;//
    private List<InvestRecordResponse> mDatas = new ArrayList<>();
    private View llError;//加载出错时展示布局
    private ImageView ivError;//出错图片
    private TextView tvError;//出错提示信息
    private String id;//标的id

    protected void initView()
    {
        setContentView(R.layout.activity_mark_history);
        findViewById(R.id.iv_Left_public).setOnClickListener(this);

        tvContent = (TextView) findViewById(R.id.tv_content_public);
        tvContent.setText("投标记录");

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new InvestRecordAdapter(this, mDatas));

        llError = findViewById(R.id.linear_error);
        llError.setOnClickListener(this);

        ivError = (ImageView) findViewById(R.id.iv_error_pic);
        tvError = (TextView) findViewById(R.id.tv_error_msg);

    }

    //初始化数据
    protected void initData()
    {
        id = getIntent().getStringExtra("id");

        mSwipeRefreshLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                mSwipeRefreshLayout.setRefreshing(true);
                getData();
            }
        });
    }

    private void getData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            if (mSwipeRefreshLayout.isRefreshing())
            {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            showError(R.mipmap.net_error, "当前网络不可用，请重试！");
            return;
        }

        if (id == null || id.isEmpty())//当id不存在时
        {
            if (mSwipeRefreshLayout.isRefreshing())
            {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            return;
        }

        Map<String, String> params = new HashMap<>(1);
        params.put("id", id);
        HttpManager.getInstance().doPost(ServiceName.SCATTERED_RECORD, params, new
                HttpCallBack<ServerResponse<List<InvestRecordResponse>>>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                if (mSwipeRefreshLayout.isRefreshing())
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                showError(R.mipmap.net_error, getResources().getString(R.string.netError));
            }

            @Override
            public void getHttpCallBack(ServerResponse<List<InvestRecordResponse>> rsp)
            {

                if (mSwipeRefreshLayout.isRefreshing())
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (rsp == null || !rsp.getCode().equals("000000"))
                {
                    showError(R.mipmap.net_error, getResources().getString(R.string.netError));
                }
                else
                {
                    if (rsp.getData() == null || rsp.getData().size() == 0)
                    {
                        showError(R.mipmap.null_data, "无更多数据！");
                    }
                    else
                    {
                        mDatas.clear();
                        mDatas.addAll(rsp.getData());
                        hideError();//当有数据的时候隐藏错误布局显示列表
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.iv_Left_public://返回
                finish();
                break;

            case R.id.linear_error://加载出错时，重新加载
                mSwipeRefreshLayout.setRefreshing(true);
                getData();
                break;
            default:
                break;
        }

    }

    @Override
    public void onRefresh()
    {
        getData();
    }

    /**
     * 加载出错时 展示错误布局<br/>
     *
     * @param errImgId 错误图片id<br/>
     * @param errMsg   错误信息<br/>
     */
    private void showError(int errImgId, String errMsg)
    {
        mRecyclerView.setVisibility(View.GONE);
        llError.setVisibility(View.VISIBLE);
        ivError.setImageResource(errImgId);
        tvError.setText(errMsg);
    }

    /**
     * 隐藏错误布局<br/>
     */
    private void hideError()
    {
        mRecyclerView.setVisibility(View.VISIBLE);
        llError.setVisibility(View.GONE);
    }
}
