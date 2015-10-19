package com.ninetowns.tootooplus.adapter;

import java.util.List;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.ninetowns.tootooplus.bean.PriorityCodeBean;

/** 
* @ClassName: BasePriorityCodeAdapter 
* @Description: 
* @author zhou
* @date 2015-4-22 下午4:57:33 
*  
*/
public abstract class BasePriorityCodeAdapter extends BaseAdapter {

	protected Activity mContext;
	protected List<PriorityCodeBean> mPriorityCodeBeans;

	public BasePriorityCodeAdapter(Activity mContext,
			List<PriorityCodeBean> mPriorityCodeBeans) {
		this.mContext=mContext;
		this.mPriorityCodeBeans=mPriorityCodeBeans;
	}

	@Override
	public int getCount() {
		return this.mPriorityCodeBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
