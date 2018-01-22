package com.mingjie.jf.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mingjie.jf.R;
import com.mingjie.jf.adapter.BaseListViewAdapter;
import com.mingjie.jf.bean.ServiceBean;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-12-08 15:50
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class SelectServiceLvHolder extends BaseHolder<ServiceBean.ListBean> {

    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvQQ;
    private TextView mTvDesc;

    public SelectServiceLvHolder(BaseListViewAdapter adapter, Context mContext, ListView mListView) {
        super(adapter, mContext, mListView);
    }

    @Override
    protected View initView() {
        View mView = View.inflate(mContext, R.layout.holder_servicelv, null);
        mIvIcon = (ImageView) mView.findViewById(R.id.iv_servicelist_icon);
        mTvName = (TextView) mView.findViewById(R.id.tv_servicelist_name);
        mTvPhone = (TextView) mView.findViewById(R.id.tv_servicelist_phone);
        mTvQQ = (TextView) mView.findViewById(R.id.tv_servicelist_qq);
        mTvDesc = (TextView) mView.findViewById(R.id.tv_servicelist_desc);
        return mView;
    }

    @Override
    protected void refreshUI(ServiceBean.ListBean data, int position) {

        Glide.with(mContext).load(data.getPath()).centerCrop().error(R.mipmap.default_myservice).crossFade().into(mIvIcon);
        mTvName.setText(data.getName());
        mTvPhone.setText(data.getPhone());
        mTvQQ.setText(data.getQq());
        mTvDesc.setText(data.getMotto());

    }
}
