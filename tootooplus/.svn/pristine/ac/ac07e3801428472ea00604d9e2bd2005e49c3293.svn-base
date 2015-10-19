package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.widget.BaseAdapter;

import com.ninetowns.tootooplus.adapter.NotUsedPriorityAdapter;
import com.ninetowns.tootooplus.bean.PriorityCodeBean;



/** 
* @ClassName: NotUsedPriorityCodeFragment 
* @Description: 未使用的优先码
* @author zhou
* @date 2015-4-22 下午4:12:30 
*  
*/
public class NotUsedPriorityCodeFragment extends BasePriorityCodeFragment{

	@Override
	protected String getType() {
		// TODO Auto-generated method stub
		return PRIORITYCODE_NOTUSED_TYPE;
	}

	@Override
	protected BaseAdapter getAdapter(
			List<PriorityCodeBean> mNotUserPriorityCodeBeans) {
		return new NotUsedPriorityAdapter(getActivity(), mNotUserPriorityCodeBeans);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



}
