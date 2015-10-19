package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.activity.WishDetailActivity;
import com.ninetowns.tootooplus.adapter.MyFreeCommendDraftAdapter;
import com.ninetowns.tootooplus.bean.RemarkStoryBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;

/**
 * 
* @ClassName: MyFreeCommentDraftPassedFragment 
* @Description: 我的点评草稿
* @author wuyulong
* @date 2015-4-20 下午3:59:01 
*
 */
public class MyFreeCommentDraftPassedFragment extends RemarkStoryFragment{

	
	@Override
	protected void setDiffientPar(RequestParamsNet requestParamsNet) {
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.REMARK_STORY_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.TYPE, "3");//
	}
	@Override
	protected void onItemClickToSkip(View view, int position,
			List<RemarkStoryBean> remarkStoryList2) {
		if(position!=-1){
			RemarkStoryBean vTag = remarkStoryList2.get(position-1);
			Intent intentToDetail=new Intent(getActivity(),WishDetailActivity.class);
			Bundle bundle=new Bundle();
			bundle.putString(ConstantsTooTooEHelper.USERID, vTag.getRemark_userId());
			bundle.putString(ConstantsTooTooEHelper.STORYID, vTag.getRemark_storyId());
			intentToDetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intentToDetail.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
			startActivity(intentToDetail);
		}
	}
	@Override
	protected void setFreeAdapter(RefreshableListView remark_story_lv,
			List<RemarkStoryBean> parserResult) {
		MyFreeCommendDraftAdapter adapter=new MyFreeCommendDraftAdapter(getActivity(), parserResult);
		remark_story_lv.setAdapter(adapter);
	}

}
