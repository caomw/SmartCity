package com.dc.smartcity.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

/**
 * 广告
 * 
 * @author check_000
 *
 */
public class Adadapter extends PagerAdapter {

	List<ImageView> list;

	public Adadapter(List<ImageView> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		if (null == list) {
			return 0;
		}
		return list.size();
	}

	@Override
	public void destroyItem(View arg0, int postion, Object arg2) {
		if (null == list || list.size() < postion + 1) {
			return;
		}
		((ViewPager) arg0).removeView(list.get(postion));
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		if (null == view || null == obj) {
			return false;
		}
		return view == obj;
	}

	@Override
	public View instantiateItem(View arg0, int postion) {
		((ViewPager) arg0).addView(list.get(postion), 0);
		return list.get(postion);
	}

}
