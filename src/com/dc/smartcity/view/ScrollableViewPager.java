package com.dc.smartcity.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollableViewPager extends ViewPager {

	boolean	isScroll;

	public ScrollableViewPager(Context context) {
		super(context);

	}

	public ScrollableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScanScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isScroll) {
			return super.onTouchEvent(arg0);
		}
		else {
			return false;
		}

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (isScroll) {
			return super.onInterceptTouchEvent(arg0);
		}
		else {
			return false;
		}

	}
}
