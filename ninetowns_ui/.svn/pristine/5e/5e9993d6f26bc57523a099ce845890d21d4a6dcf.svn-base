package com.ninetowns.ui.widget.clipimageview;


import com.ninetowns.ui.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 裁剪边框
 * 
 */
public class ClipView extends View {
	
	/**裁剪框的宽度**/
	private int clipWidth = 0;
	/**裁剪框的高度**/
	private int clipHeight = 0;
	
	private Paint mPaint;
	
	public ClipView(Context context) {
		this(context, null);
	}

	public ClipView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ClipView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mPaint = new Paint();
	}
	/**
	 * 设置裁剪框的宽高
	 * @param clipWidth
	 * @param clipHeight
	 */
	public void setClipViewAttrs(int clipWidth, int clipHeight){
		this.clipWidth = clipWidth;
		this.clipHeight = clipHeight;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = this.getWidth();
		int height = this.getHeight();

		mPaint.setColor(getResources().getColor(R.color.clip_view_dark_color));
		// 以下绘制透明暗色区域
		//顶部矩形
		canvas.drawRect(0, 0, width, (height - clipHeight) / 2, mPaint);
		//底部矩形
		canvas.drawRect(0, (height + clipHeight) / 2, width, height, mPaint);
		//左边矩形
		canvas.drawRect(0, (height - clipHeight) / 2, (width - clipWidth) / 2, (height + clipHeight) / 2, mPaint);
		//右边矩形
		canvas.drawRect((width + clipWidth) / 2, (height - clipHeight) / 2, width, (height + clipHeight) / 2, mPaint);
		
		// 以下绘制边框线
		mPaint.setColor(getResources().getColor(R.color.white));
		mPaint.setStrokeWidth(2.0f);
		//顶部白线
		canvas.drawLine((width - clipWidth) / 2, (height - clipHeight) / 2, (width + clipWidth) / 2, (height - clipHeight) / 2, mPaint);
		//底部白线
		canvas.drawLine((width - clipWidth) / 2, (height + clipHeight) / 2, (width - clipWidth) / 2, (height + clipHeight) / 2, mPaint);
		//左边白线
		canvas.drawLine((width - clipWidth) / 2, (height - clipHeight) / 2, (width - clipWidth) / 2, (height + clipHeight) / 2, mPaint);
		//右边白线
		canvas.drawLine((width + clipWidth) / 2, (height - clipHeight) / 2, (width + clipWidth) / 2, (height + clipHeight) / 2, mPaint);
	}

}
