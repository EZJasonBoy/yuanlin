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
 * @ClassName: FirstGuideClickGroupCountDialog
 * @Description:沱沱平台赠送优先码
 * 
 */
public class FirstGuideClickGroupCountDialog extends BaseFragmentDialog {
	private View firstGuideClickGroupCountView;
	@ViewInject(R.id.rl_dismiss)
	private RelativeLayout mRLDismiss;
	public FirstGuideClickGroupCountDialog() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		firstGuideClickGroupCountView = inflater.inflate(
				R.layout.first_guide_clickgotogroupcount, null);
		ViewUtils.inject(this, firstGuideClickGroupCountView);
		return firstGuideClickGroupCountView;
	}

	@OnClick(R.id.rl_dismiss)
	public void dismissView(View v) {
		dismiss();
		SharedPreferenceHelper.setNoFirstGuideClickGroupCount(getActivity());
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
