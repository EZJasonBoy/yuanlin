package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.widget.BaseAdapter;

import com.ninetowns.tootooplus.adapter.HasPresentedPriorityAdapter;
import com.ninetowns.tootooplus.bean.PriorityCodeBean;

/** 
* @ClassName: HasPresentedPriorityCodeFragment 
* @Description: 已经赠送的优先码
* @author zhou
* @date 2015-4-22 下午6:20:14 
*  
*/
public class HasPresentedPriorityCodeFragment extends BasePriorityCodeFragment{

	@Override
	protected String getType() {
		return PRIORITYCODE_HASPRESENTED_TYPE;
	}

	@Override
	protected BaseAdapter getAdapter(
			List<PriorityCodeBean> mNotUserPriorityCodeBeans) {
		// TODO Auto-generated method stub
		return new HasPresentedPriorityAdapter(getActivity(), mNotUserPriorityCodeBeans);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	



}
