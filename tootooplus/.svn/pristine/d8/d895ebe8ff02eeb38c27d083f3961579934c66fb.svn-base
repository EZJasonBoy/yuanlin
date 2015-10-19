package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.view.View;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.bean.HomePageBean;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;

/**
 * 
 * @ClassName: CommentedOnLineCommentFragment
 * @Description: 线上 已点评
 * @author wuyulong
 * @date 2015-4-16 下午2:16:52
 * 
 */
public class CommentedOnLineCommentFragment extends MyActivityGroupCommentFragment{

@Override
public void setDifferentParam(RequestParamsNet requestParamNet) {
	requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.TYPE, "0");//
	requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.STATUS, "3");//
	
}

@Override
public void onItemClickToSkip(View view, int position,
		List<HomePageBean> parserData) {
	// TODO Auto-generated method stub
	
}

}