package com.mingjie.jf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mingjie.jf.R;
import com.mingjie.jf.activity.LoginActivity;
import com.mingjie.jf.activity.PrivateActivity;
import com.mingjie.jf.bean.HomeFragmentVo;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.ToastUtil;
import com.mingjie.jf.utils.UIUtils;

import java.util.List;
import java.util.Locale;

public class BannerAdapter extends PagerAdapter {


    private Context mContext;
    private List<HomeFragmentVo.BannerListBean> mDatas;

    public BannerAdapter(Context context, List<HomeFragmentVo.BannerListBean> mViewPageDatas) {
        this.mContext = context;
        this.mDatas = mViewPageDatas;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView imageView = new ImageView(UIUtils.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        String imageUrl = mDatas.get(position).getFilePath();

        // CfLog.d("imageUrl------" + imageUrl);
        // CfLog.d("mDatas------" + mDatas);

        if (mDatas.size() == 0) {
            // 没有请求到资源时
            imageView.setImageResource(R.mipmap.banner1);
        } else {
            Glide.with(UIUtils.getContext()).load(imageUrl).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                           boolean isFirstResource) {
                    android.util.Log.d("GLIDE", String.format(Locale.ROOT,
                            "onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                               boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            }).error(R.mipmap.loading).centerCrop().into(imageView);
        }
        container.addView(imageView, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果网址配置的是test 就不跳转
                if (TextUtils.isEmpty(mDatas.get(position).getUrl()) || mDatas.get(position).getUrl().equals("test")) {
                    return;
                }
                //如果未登录 就跳转到登陆界面
                if (!CacheUtils.isLogin()) {
                    ToastUtil.showCenterGraToast("请先登录!");
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    loginIntent.putExtra(LoginActivity.KEY_START_TYPE, LoginActivity.START_TYPE_FINISH);
                    mContext.startActivity(loginIntent);
                    return;
                }

                Intent intent = new Intent(mContext, PrivateActivity.class);
                intent.putExtra("bannerTitle", mDatas.get(position).getTitle());
                intent.putExtra("data", mDatas.get(position).getUrl());
                mContext.startActivity(intent);

            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
