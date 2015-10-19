package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.view.View;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.bean.HomePageBean;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;

/**
 * 
 * @ClassName: NoCommentOnLineCommentFragment
 * @Description: 线上 未点评
 * @author wuyulong
 * @date 2015-4-16 下午2:16:52
 * 
 */
public class NoCommentOnLineCommentFragment extends MyActivityGroupCommentFragment{

@Override
public void setDifferentParam(RequestParamsNet requestParamNet) {
	requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.TYPE, "0");//
	requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.STATUS, "2");//
	
}

@Override
public void onItemClickToSkip(View view, int position,
		List<HomePageBean> parserData) {
	// TODO Auto-generated method stub
	
}

}