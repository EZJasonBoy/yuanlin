package com.ninetowns.tootoopluse.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.ui.widget.dialog.BaseFragmentDialog;

/**
 * 
 * @ClassName: FirstGuideActivityDialog
 * @Description: 创建活动的的引导页
 * @author wuyulong
 * @date 2015-4-29 上午9:39:10
 * 
 */
public class FirstGuideActivityDialog extends BaseFragmentDialog {
	private View firstGuideCreateActivity;
	@ViewInject(R.id.rl_dismiss)
	private RelativeLayout mRLDismiss;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		firstGuideCreateActivity = inflater.inflate(
				R.layout.first_guide_create_activity, null);
		ViewUtils.inject(this, firstGuideCreateActivity);
		return firstGuideCreateActivity;
	}

	@OnClick(R.id.rl_dismiss)
	public void dismissView(View v) {
		dismiss();
		SharedPreferenceHelper.setNoFirstGuideCreateActivity(getActivity());
	
	}
	
}
