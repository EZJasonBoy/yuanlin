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
import com.ninetowns.tootooplus.fragment.FirstGuideClickGroupCountDialog.InOnClickListenerDismiss;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.ui.widget.dialog.BaseFragmentDialog;


public class FirstGuideLookGroupCountDialog extends BaseFragmentDialog {
	private View firstGuideGotoGroupCountView;
	@ViewInject(R.id.rl_dismiss)
	private RelativeLayout mRLDismiss;
	public FirstGuideLookGroupCountDialog() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		firstGuideGotoGroupCountView = inflater.inflate(
				R.layout.first_guide_lookkgotogroupcount, null);
		ViewUtils.inject(this, firstGuideGotoGroupCountView);
		return firstGuideGotoGroupCountView;
	}

	@OnClick(R.id.rl_dismiss)
	public void dismissView(View v) {
		dismiss();
		SharedPreferenceHelper.setNoFirstGuideLookGroupCount(getActivity());
		if(listener!=null){
			listener.OnClickListenerDismiss(true);
		}
	}
	private InOnClickListenerDismiss listener;
	public interface InOnClickListenerDismiss{
		void OnClickListenerDismiss(boolean isDissMiss);
		
	}
	public void setInOnClickListenerDismiss(InOnClickListenerDismiss listener){
		this.listener=listener;
		
	}
}