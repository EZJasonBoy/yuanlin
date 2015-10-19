package com.ninetowns.tootooplus.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.ui.widget.dialog.BaseFragmentDialog;

/**
 * 
* @ClassName: FirstShareDialog 
* @Description: 显示分享
* @author wuyulong
* @date 2015-8-10 下午1:08:12 
*
 */
public class FirstShareDialog extends BaseFragmentDialog {
	private View firstGuideShareView;
	@ViewInject(R.id.rl_dismiss)
	private RelativeLayout mRLDismiss;
	public FirstShareDialog() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		firstGuideShareView = inflater.inflate(
				R.layout.first_guide_share, null);
		ViewUtils.inject(this, firstGuideShareView);
		return firstGuideShareView;
	}

	@OnClick(R.id.rl_dismiss)
	public void dismissView(View v) {
		dismiss();
		if(status!=null){
			SharedPreferenceHelper.setIsFirstShowShare(getActivity());
			status.OnStatusDissMissListener(true);
		}

	}
	private OnStatusDissMiss status;
	public interface OnStatusDissMiss{
	public void OnStatusDissMissListener(boolean isDissmiss);
	}
	public void setOnStatusDissMissListener(OnStatusDissMiss status){
		this.status=status;
	}
}