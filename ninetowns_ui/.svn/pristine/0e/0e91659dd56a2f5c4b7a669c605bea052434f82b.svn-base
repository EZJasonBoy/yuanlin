package com.ninetowns.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class HeightChangedLayout extends RelativeLayout {
    private final static String TAG = "HeightChangedLinearLayout";
    private LayoutSizeChangedListener mLayoutSizeChangedListener;

    public HeightChangedLayout(Context context) {
        super(context);
    }

    public HeightChangedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        final int fw = w;
        final int fh = h;
        final int foldw = oldw;
        final int foldh = oldh;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mLayoutSizeChangedListener != null) {
                    mLayoutSizeChangedListener.onLayoutSizeChanged(fw, fh,
                            foldw, foldh);
                }
            }
        }).start();
    }

    public interface LayoutSizeChangedListener {
        public void onLayoutSizeChanged(int w, int h, int oldw, int oldh);
    }

    public void setLayoutSizeChangedListener(LayoutSizeChangedListener l) {
        mLayoutSizeChangedListener = l;
    }
}
