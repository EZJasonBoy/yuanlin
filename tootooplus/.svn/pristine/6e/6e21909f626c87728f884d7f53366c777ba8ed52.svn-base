package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.activity.WishDetailActivity;
import com.ninetowns.tootooplus.bean.WishBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
/**
 * 
* @ClassName: PassedWishFragment 
* @Description: 我要白吃
* @author wuyulong
* @date 2015-4-20 上午10:41:42 
*
 */
public class MyWantFreeFragment extends BaseWishFragment{

	@Override
	protected void setDifferentParam(RequestParamsNet requestParamNet) {
		requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.TYPE,
				"3");//1是通过，2是未通过 ,3我要白吃
	}

	@Override
	protected void onItemClickToSkip(View view, int position,
			List<WishBean> parserData) {
		if (position != -1) {
			WishBean selectionItem = parserData.get(position - 1);
			Intent intentToDetail = new Intent(getActivity(),
					WishDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString(ConstantsTooTooEHelper.USERID,
					selectionItem.getUserId());
			bundle.putString(ConstantsTooTooEHelper.STORYID,
					selectionItem.getStoryId());
			intentToDetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intentToDetail.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
			startActivity(intentToDetail);
		}
	}

}
