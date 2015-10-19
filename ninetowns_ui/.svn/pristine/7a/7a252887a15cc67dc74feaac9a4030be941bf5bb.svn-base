package com.ninetowns.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/** 
* @ClassName: ScrollControlViewPager 
* @Description: TODO 可控制滑动的viewpager
* @author zhou
* @date 2015-1-22 下午2:22:06 
*  
*/
public class ScrollControlViewPager extends ViewPager {

	public ScrollControlViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollControlViewPager(Context context) {
		super(context);
	}

	private boolean mIsDispatch = false;

	/** 
	* @Title: setDispatch 
	* @Description: TODO 控制 viewpager可滑动 
	* @param @param touchEnable    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setDispatch(boolean touchEnable) {
		this.mIsDispatch = touchEnable;
	}

	public boolean getDispatch() {
		return mIsDispatch;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (getDispatch()) {//消费了 
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}

}
