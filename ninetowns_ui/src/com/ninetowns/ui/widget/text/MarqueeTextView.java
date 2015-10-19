package com.ninetowns.ui.widget.text;
import java.lang.reflect.Field;  

import android.content.Context;  
import android.graphics.Rect;  
import android.text.TextUtils;  
import android.util.AttributeSet;  
import android.widget.CheckedTextView;
/**
 * 
* @ClassName: MarqueeTextView 
* @Description: 跑马灯
* @author wuyulong
* @date 2015-3-31 上午10:05:05 
*
 */
public class MarqueeTextView extends CheckedTextView  
{  
    public MarqueeTextView(Context context) {  
        super(context);  
  
        Initialize();  
    }  
  
    public MarqueeTextView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
  
        Initialize();  
    }  
  
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
  
        Initialize();  
    }  
  
    private void Initialize() {  
        setFocusable(true);  
        setFocusableInTouchMode(true);  
  
        setSingleLine();  
        setEllipsize(TextUtils.TruncateAt.MARQUEE);  
        setMarqueeRepeatLimit(-1);  
    }  
  
    @Override  
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {  
        /** 
         *  requestFocus和clearFocus均导致这里被调用 
         */  
        if( focused ) {  
            super.onFocusChanged(focused, direction, previouslyFocusedRect);      
        } else {  
            if( !mBMyFocus ) {  
                super.onFocusChanged(focused, direction, previouslyFocusedRect);  
            }  
        }  
    }  
  
//  @Override  
//  public void onWindowFocusChanged(boolean focused) {  
//      super.onWindowFocusChanged(focused);  
//  }  
  
    @Override  
    public boolean isFocused() {  
        return mBMyFocus;  
    }  
  
    ////////////////////////////////////////////////////  
    private boolean mBMyFocus = false;  

    public void setInnerFocus( boolean bFocus ) {  
        mBMyFocus = bFocus;  
        if( mBMyFocus ) {  
            requestFocus();  
        } else {  
            Field field;  
              
            try {  
                field = getClass().getSuperclass().getSuperclass().getDeclaredField("mPrivateFlags");  
                field.setAccessible(true);  
                  
                field.set(this, new Integer(2));  
                  
                clearFocus();  
            } catch (NoSuchFieldException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IllegalArgumentException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IllegalAccessException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
    }  
  
  
}  