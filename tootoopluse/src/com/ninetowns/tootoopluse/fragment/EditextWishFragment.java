package com.ninetowns.tootoopluse.fragment;

import android.os.Bundle;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;

/**
 * 
 * @ClassName: EditextWishFragment
 * @Description: 编辑故事
 * @author wuyulong
 * @date 2015-2-28 上午9:36:20
 * 
 */
public class EditextWishFragment extends RecommendWishFragment implements
		CreateWishBaseFragment.OnApiParamsInterface {

	@Override
	public void getBundleType(Bundle bundle) {
		super.bundle=bundle;
		setOnApkParamsListener(this);
	}

	@Override
	public boolean isGetNetData() {
		return true;
	}
@Override
protected void showGuide() {
	showCreateDropDialog();
}
private void showCreateDropDialog() {
	boolean isFirst = SharedPreferenceHelper.getFirstGuideDrop(getActivity());
	if(isFirst){//如果第一次
		CommonUtil.showFirstGuideDialog(getActivity(), ConstantsTooTooEHelper.FIRST_GUIDE_DROP);
	}

}
	@Override
	public RequestParamsNet getRequestParamsNet(Bundle bundle) {
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.EDITEXT_WISH);
		String userId = SharedPreferenceHelper.getLoginUserId(getActivity());
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USERID,
				userId);
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.STORYID, storyid);
		return requestParamsNet;
	}


}
