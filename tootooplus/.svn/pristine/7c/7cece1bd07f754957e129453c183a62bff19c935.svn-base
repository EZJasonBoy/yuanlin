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
 * @ClassName: FirstGoToBuyGuideDialog
 * @Description: 去购买的提示
 * @author wuyulong
 * @date 2015-4-29 上午9:39:10
 * 
 */
public class FirstGoToBuyGuideDialog extends BaseFragmentDialog {
	private View firstGuideWriteCommentView;
	@ViewInject(R.id.rl_dismiss)
	private RelativeLayout mRLDismiss;
	public FirstGoToBuyGuideDialog() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		firstGuideWriteCommentView = inflater.inflate(
				R.layout.first_guide_go_act_buy, null);
		ViewUtils.inject(this, firstGuideWriteCommentView);
		return firstGuideWriteCommentView;
	}

	@OnClick(R.id.rl_dismiss)
	public void dismissView(View v) {
		dismiss();
		if(isAdded())
		SharedPreferenceHelper.setFirstGuideGoBuyAct(getActivity());
	}
}
