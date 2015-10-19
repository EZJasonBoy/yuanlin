package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.view.View;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.bean.HomePageBean;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
/**
 * 
* @ClassName: NoCommentDownLineActFragment 
* @Description: 未点评线下
* @author wuyulong
* @date 2015-4-17 下午4:08:48 
*
 */
public class NoCommentDownLineActFragment extends MyActivityGroupCommentFragment{

	@Override
	public void setDifferentParam(RequestParamsNet requestParamNet) {
		requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.TYPE, "1");//线下
		requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.STATUS, "2");//未点评
		
	}

	@Override
	public void onItemClickToSkip(View view, int position,
			List<HomePageBean> parserData) {
		// TODO Auto-generated method stub
		
	}

}
