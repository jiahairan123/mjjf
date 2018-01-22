package com.mingjie.jf.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mingjie.jf.R;
import com.mingjie.jf.activity.ImageDetailActivity;
import com.mingjie.jf.activity.LoanInfoActivity;
import com.mingjie.jf.bean.JieKuanDetails;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.fragment
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 用户材料
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-18 15:59
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class PepoleInfoPage extends BaseTagPage
{
    /**
     * 展示图片的GridView
     */
    private GridView gridView_userinfo;
    /**
     * 包含图片url的list数据
     */
    private List<JieKuanDetails.DataBean.AttachmentListBean> bean;
    private LoanInfoActivity activity;
    /**
     * 显示其他界面（数据为空时）
     */
    private LinearLayout iv_userinfo;

    public PepoleInfoPage(LoanInfoActivity activity, List<JieKuanDetails.DataBean.AttachmentListBean> bean)
    {
        super(activity);
        this.activity = activity;
        this.bean = bean;
    }

    @Override
    protected View initContentView(LoanInfoActivity activity)
    {
        View mView = View.inflate(activity, R.layout.fragment_loan_info_about_user_material, null);
        iv_userinfo = (LinearLayout) mView.findViewById(R.id.iv_userinfo);
        gridView_userinfo = (GridView) mView.findViewById(R.id.gridView_userinfo);
        gridView_userinfo.setSelector(new ColorDrawable(Color.TRANSPARENT));    // 取消gridview选中背景

        return mView;
    }

    @Override
    public void initEvent()
    {

        super.initEvent();
    }

    @Override
    public void initData()
    {
        if (null != bean)
        {
            if (bean.size() != 0)
            {
                gridView_userinfo.setVisibility(View.VISIBLE);
                iv_userinfo.setVisibility(View.GONE);
                gridView_userinfo.setAdapter(new UserInfoAdapter());
            }
            else
            {
                gridView_userinfo.setVisibility(View.GONE);
                iv_userinfo.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            gridView_userinfo.setVisibility(View.GONE);
            iv_userinfo.setVisibility(View.VISIBLE);
        }
        super.initData();
    }

    /**
     * 展示图片的adapter
     */
    class UserInfoAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            if (bean != null)
            {
                return bean.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            return bean.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {

            final ViewHolder holder;
            if (convertView == null)
            {
                convertView = View.inflate(activity, R.layout.holder_userinfo, null);
                holder = new ViewHolder();
                holder.mIv = (ImageView) convertView.findViewById(R.id.iv_userinfo);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mIv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(activity, ImageDetailActivity.class);
                    intent.putExtra("position", position);
                    String[] urls = new String[bean.size()];
                    for (int i = 0; i < bean.size(); i++)
                    {
                        urls[i] = bean.get(i).filePath;
                    }
                    intent.putExtra("urls", urls);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            // 加载图片
            Glide.with(activity)
                    .load(bean.get(position).smallFilePath)
                    .centerCrop().error(R.mipmap.default_userinfo)
                    .crossFade()
                    .into(holder.mIv);
            return convertView;
        }
    }

    static class ViewHolder
    {
        ImageView mIv;
    }
}
