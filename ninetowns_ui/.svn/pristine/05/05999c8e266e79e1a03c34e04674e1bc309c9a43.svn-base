package com.ninetowns.ui.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/** 
* @ClassName: LinearLayoutExpandListView 
* @Description: 自定义线性布局实现expanlistview效果
* @author zhou
* @date 2015-4-17 下午6:16:51 
*  
*/
public class LinearLayoutExpandListView extends LinearLayout {
	
	public LinearLayoutExpandListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private BaseAdapter baseAdapter;

	
	
	/**
	 * 绑定布局
	 */
	public void bindLinearLayout() {
        int count = baseAdapter.getCount();
        this.removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = baseAdapter.getView(i, null, null);
            addView(v, i);
        }
    }
	
	/**
	 * 设置适配器
	 * @param baseAdapter
	 */
	public void setLayoutAdapter(BaseAdapter baseAdapter){
		this.baseAdapter = baseAdapter;
		bindLinearLayout();
	}

}
