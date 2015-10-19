package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.view.View;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.bean.HomePageBean;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
/**
 * 
* @ClassName: JoinOnLineActFragment 
* @Description: 已参加活动线下
* @author wuyulong
* @date 2015-4-17 下午4:08:48 
*
 */
public class JoinDownLineActFragment extends MyActivityGroupCommentFragment{

	@Override
	public void setDifferentParam(RequestParamsNet requestParamNet) {
		requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.TYPE, "1");//
		requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.STATUS, "1");//
		
	}

	@Override
	public void onItemClickToSkip(View view, int position,
			List<HomePageBean> parserData) {
		// TODO Auto-generated method stub
		
	}

}
