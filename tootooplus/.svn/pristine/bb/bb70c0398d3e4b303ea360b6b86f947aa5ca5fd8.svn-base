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
import com.ninetowns.tootooplus.fragment.FirstViewPagerGuideDialog.OnViewPagerDialogStatus;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.ui.widget.dialog.BaseFragmentDialog;

/**
 * 
 * @ClassName: FirstLookinfoDialog
 * @Description: 查看白吃活动信息
 * @author wuyulong
 * @date 2015-4-29 上午9:39:10
 * 
 */
public class FirstLookinfoDialog extends BaseFragmentDialog {
	private View firstGuideLookinfoView;
	@ViewInject(R.id.rl_dismiss)
	private RelativeLayout mRLDismiss;
	public FirstLookinfoDialog() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		firstGuideLookinfoView = inflater.inflate(
				R.layout.first_guide_look_info, null);
		ViewUtils.inject(this, firstGuideLookinfoView);
		return firstGuideLookinfoView;
	}

	@OnClick(R.id.rl_dismiss)
	public void dismissView(View v) {
		dismiss();
		SharedPreferenceHelper.setNoFirstGuideLookInfo(getActivity());
		if(onDialogStatus!=null){
			onDialogStatus.onDialogStatusListener(true);
		}
	}
	
	public interface OnDialogStatus{
		public void onDialogStatusListener(boolean isDismiss);
		
	}
	private OnDialogStatus onDialogStatus;
	
	public void setOnDialogStatus(OnDialogStatus onDialogStatus){
		this.onDialogStatus=onDialogStatus;
		
	}
}
