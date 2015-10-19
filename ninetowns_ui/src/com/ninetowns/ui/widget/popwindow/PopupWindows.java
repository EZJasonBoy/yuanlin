package com.ninetowns.ui.widget.popwindow;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

import android.widget.PopupWindow;
import android.content.Context;

public class PopupWindows {
    protected Context mContext;
    public PopupWindow mWindow;
    protected View mRootView;
    protected Drawable mBackground = null;
    protected WindowManager mWindowManager;

    /**
     * Constructor.
     * @param context
     *            Context
     */
    public PopupWindows(Context context) {
        mContext = context;
        mWindow = new PopupWindow(context);
        setTouchInterListener();
        mWindowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
    }

    public void setTouchInterListener() {
        mWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mWindow.dismiss();

                    return true;
                }

                return false;
            }
        });
    }

    /**
     * On dismiss
     */
    protected void onDismiss() {
    	
    }

    /**
     * On show
     */
    protected void onShow() {
    }

    /**
     * On pre show
     */
    @SuppressWarnings("deprecation")
    protected void preShow() {
        if (mRootView == null)
            throw new IllegalStateException(
                    "setContentView was not called with a view to display.");

        onShow();

        if (mBackground == null)
            mWindow.setBackgroundDrawable(new BitmapDrawable());
        else
            mWindow.setBackgroundDrawable(mBackground);

        mWindow.setWidth(LayoutParams.WRAP_CONTENT);
        mWindow.setHeight(LayoutParams.WRAP_CONTENT);
        mWindow.setTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setOutsideTouchable(true);

        mWindow.setContentView(mRootView);
    }

    /**
     * Set background drawable.
     * @param background
     *            Background drawable
     */
    public void setBackgroundDrawable(Drawable background) {
        mBackground = background;
    }
    /**
     * 
    * @Title: PopupWindows.java  
    * @Description: 设置点击其它按钮是否隐藏 
    * @author wuyulong
    * @date 2014-12-19 下午5:06:32  
    * @param 
    * @return void
     */
    public void setOutSideTouch(boolean isOut){
        mWindow.setOutsideTouchable(isOut);
    }

    /**
     * Set content view.
     * @param root
     *            Root view
     */
    public void setContentView(View root) {
        mRootView = root;

        mWindow.setContentView(root);
    }

    /**
     * Set content view.
     * @param layoutResID
     *            Resource id
     */
    public void setContentView(int layoutResID) {
        LayoutInflater inflator = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setContentView(inflator.inflate(layoutResID, null));
    }

    /**
     * Set listener on window dismissed.
     * @param listener
     */
    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mWindow.setOnDismissListener(listener);
    }

    /**
     * Dismiss the popup window.
     */
    public void dismiss() {
        mWindow.dismiss();
    }
}