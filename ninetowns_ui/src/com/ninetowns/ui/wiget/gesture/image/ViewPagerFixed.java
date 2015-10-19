package com.ninetowns.ui.wiget.gesture.image;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 
 * @ClassName: ViewPagerFixed
 * @Description: 自定义viewpager 解决放大缩小java.lang.IllegalArgumentException:
 *               pointerIndex out of range
 * @author wuyulong
 * @date 2015-4-2 上午10:32:45 com.ninetowns.ui.wiget.gesture.image.ViewPagerFixed
 */
public class ViewPagerFixed extends android.support.v4.view.ViewPager {

	public ViewPagerFixed(Context context) {
		super(context);
	}

	public ViewPagerFixed(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		try {
			return super.onTouchEvent(ev);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}