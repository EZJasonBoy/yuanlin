package com.ninetowns.ui.widget.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.EditText;

public class LineEditText extends EditText {/*
    // 画笔 用来画下划线
    private Paint paint;

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb(190, 190, 190));
        // 开启抗锯齿 较耗内存
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 得到总行数
        int lineCount = getLineCount();
        // 得到每行的高度
        int lineHeight = getLineHeight();
        // 根据行数循环画线
        for (int i = 0; i < lineCount; i++) {
            int lineY = (i + 1) * lineHeight;
            canvas.drawLine(0, lineY, this.getWidth(), lineY, paint);
        }

    }

*/

    Context mContext;
    public LineEditText(Context context) {
     super(context);
     mContext = context;
    }
    public LineEditText(Context context, AttributeSet attrs) {
     super(context, attrs);
     mContext = context;
    }
    public LineEditText(Context context, AttributeSet attrs, int defStyle) {
     super(context, attrs, defStyle);
     mContext = context;
    }
    @Override
    protected void onDraw(Canvas canvas) {
     WindowManager wm = (WindowManager) mContext.getSystemService("window");
     int windowWidth = wm.getDefaultDisplay().getWidth();
     int windowHeight = wm.getDefaultDisplay().getHeight();
     Paint paint = new Paint();
     paint.setStyle(Paint.Style.FILL);
     paint.setColor(Color.rgb(190, 190, 190));
     int paddingTop = getPaddingTop();
     int paddingBottom = getPaddingBottom();
     int scrollY = getScrollY();
     int scrollX = getScrollX() + windowWidth;
     int innerHeight = scrollY + getHeight() - paddingBottom;
     int lineHeight = getLineHeight();
     int baseLine = scrollY
       + (lineHeight - ((scrollY - paddingTop) % lineHeight));
     int x = 8;
     while (baseLine < innerHeight) {
      canvas.drawLine(x, baseLine, scrollX - x, baseLine, paint);
      baseLine += lineHeight;
     }
     super.onDraw(canvas);
    }  

}