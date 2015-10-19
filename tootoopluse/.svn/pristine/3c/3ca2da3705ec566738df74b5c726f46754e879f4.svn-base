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
 * @ClassName: FirstQianDaoGuideDialog
 * @Description: 签到
 * @author wuyulong
 * @date 2015-6-18 下午5:10:00
 * 
 */
public class FirstQianDaoGuideDialog extends BaseFragmentDialog {
	private View firstGuideQDView;
	@ViewInject(R.id.rl_dismiss)
	private RelativeLayout mRLDismiss;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		firstGuideQDView = inflater.inflate(R.layout.first_guide_qiandao_link,
				null);
		ViewUtils.inject(this, firstGuideQDView);
		return firstGuideQDView;
	}

	@OnClick(R.id.rl_dismiss)
	public void dismissView(View v) {
		dismiss();
		if (getActivity() != null)
			SharedPreferenceHelper.setNoFirstGuideQD(getActivity());

	}

}
