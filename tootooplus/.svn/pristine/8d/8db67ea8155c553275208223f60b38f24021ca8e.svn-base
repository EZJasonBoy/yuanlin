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
 * @ClassName: FirstWriteCommentGuideDialog
 * @Description: 写点评
 * @author wuyulong
 * @date 2015-4-29 上午9:39:10
 * 
 */
public class FirstWriteCommentGuideDialog extends BaseFragmentDialog {
	private View firstGuideWriteCommentView;
	@ViewInject(R.id.rl_dismiss)
	private RelativeLayout mRLDismiss;
	public FirstWriteCommentGuideDialog() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		firstGuideWriteCommentView = inflater.inflate(
				R.layout.first_guide_write_comment, null);
		ViewUtils.inject(this, firstGuideWriteCommentView);
		return firstGuideWriteCommentView;
	}

	@OnClick(R.id.rl_dismiss)
	public void dismissView(View v) {
		dismiss();
		SharedPreferenceHelper.setNoFirstGuideWriteComment(getActivity());
	}
}
