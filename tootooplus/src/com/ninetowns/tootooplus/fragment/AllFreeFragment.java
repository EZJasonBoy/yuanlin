package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.activity.WishDetailActivity;
import com.ninetowns.tootooplus.bean.FreeCommentBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;

/**
 * 
 * @ClassName: AllFreeFragment
 * @Description: 全部
 * @author wuyulong
 * @date 2015-4-17 上午10:00:07
 * 
 */
public class AllFreeFragment extends GroupFreeCommentFragment {

	@Override
	public void setDifferentParam(RequestParamsNet requestParamNet) {
		requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.TYPE,
				"0");// 1是全部

	}

	

	@Override
	public void onItemClickToSkip(View view, int position,
			List<FreeCommentBean> parserData) {
		if (position != -1) {
			FreeCommentBean selectionItem = parserData.get(position - 1);
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
