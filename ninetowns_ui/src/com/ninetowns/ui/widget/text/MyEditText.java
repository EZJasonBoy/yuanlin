package com.ninetowns.ui.widget.text;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
/**
 * 
* @ClassName: MyEditText 
* @Description: 监听软键盘
* @author wuyulong
* @date 2015-6-2 下午5:01:04 
*
 */
public class MyEditText extends EditText {

	public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyEditText(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchKeyEventPreIme(KeyEvent event) {
			// when the softinput display
			// 处理事件
			if(onEditTextKeyListener!=null){
				onEditTextKeyListener.onEditTextKey(event);
		}
		return super.dispatchKeyEventPreIme(event);
	}
	private OnEditTextKeyListener onEditTextKeyListener;
	public interface OnEditTextKeyListener{
		public void onEditTextKey(KeyEvent event);
	}
	public void setEdiOnKeyListener(OnEditTextKeyListener onEditTextKeyListener){
		this.onEditTextKeyListener=onEditTextKeyListener;
	}
	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyPreIme(keyCode, event);
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyMultiple(keyCode, repeatCount, event);
	}

}
