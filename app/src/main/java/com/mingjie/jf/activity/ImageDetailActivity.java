package com.mingjie.jf.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.mingjie.jf.R;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.fragment
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 展示用户信息大图的actuvity
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-22 09:28
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ImageDetailActivity extends BaseActivity
{
    private ViewPager mPager;

    private int firstPosition;//点击第firstposition位置图片进入
    private String[] urls;
    private PhotoView view;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_image_detail);

        firstPosition = getIntent().getIntExtra("position", 0);
        urls = (String[]) getIntent().getCharSequenceArrayExtra("urls");

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mPager.setOffscreenPageLimit(-1);
        mPager.setAdapter(new PagerAdapter()
        {
            @Override
            public int getCount()
            {
                return urls.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object)
            {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                view = new PhotoView(ImageDetailActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //                view.setImageResource(imgsId[position]);
                container.addView(view);
                Glide.with(ImageDetailActivity.this)
                        .load(urls[position]).placeholder(R.mipmap.default_userinfo).error(R.mipmap.default_userinfo)
                        .crossFade()
                        .into(view);
                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        finish();
                    }
                });
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
                container.removeView((View) object);
            }
        });
        mPager.setCurrentItem(firstPosition);

    }

    @Override
    protected void initData()
    {
    }
}
