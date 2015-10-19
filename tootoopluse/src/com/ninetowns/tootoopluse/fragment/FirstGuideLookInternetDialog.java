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
 * @ClassName: FirstGuideLookInternetDialog
 * @Description: 查看网络连接地址
 * @author wuyulong
 * @date 2015-4-29 上午9:39:10
 * 
 */
public class FirstGuideLookInternetDialog extends BaseFragmentDialog {
	private View firstGuideLookLink;
	@ViewInject(R.id.rl_dismiss)
	private RelativeLayout mRLDismiss;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		firstGuideLookLink = inflater.inflate(
				R.layout.first_guide_look_link, null);
		ViewUtils.inject(this, firstGuideLookLink);
		return firstGuideLookLink;
	}

	@OnClick(R.id.rl_dismiss)
	public void dismissView(View v) {
		dismiss();
		SharedPreferenceHelper.setNoFirstGuideLookLink(getActivity());
		
	}
}
