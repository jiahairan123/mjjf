package com.mingjie.jf.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**

 */
public class GuidePagerAdapter extends PagerAdapter
{

	private Context	mContext;
	private int[]	mImages;

	public GuidePagerAdapter(Context context, int[] images) {
		this.mContext = context;
		this.mImages = images.clone();
	}

	@Override
	public int getCount()
	{
		return mImages.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		ImageView item = new ImageView(mContext);
		item.setScaleType(ImageView.ScaleType.FIT_XY);
		item.setImageResource(mImages[position]);
		container.addView(item);
		return item;
	}

	@Override
	public void destroyItem(ViewGroup collection, int position, Object view)
	{
		collection.removeView((View) view);
	}
}
