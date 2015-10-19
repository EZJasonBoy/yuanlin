package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.widget.BaseAdapter;

import com.ninetowns.tootooplus.adapter.HasUsedPriorityAdapter;
import com.ninetowns.tootooplus.bean.PriorityCodeBean;



/** 
* @ClassName: HasUsedPriorityCodeFragment 
* @Description: 已使用的优先码
* @author zhou
* @date 2015-4-22 下午4:12:30 
*  
*/
public class HasUsedPriorityCodeFragment extends BasePriorityCodeFragment{

	@Override
	protected String getType() {
		// TODO Auto-generated method stub
		return PRIORITYCODE_HASUSED_TYPE;
	}

	@Override
	protected BaseAdapter getAdapter(
			List<PriorityCodeBean> mNotUserPriorityCodeBeans) {
		return new HasUsedPriorityAdapter(getActivity(),mNotUserPriorityCodeBeans);
	}
	



}
