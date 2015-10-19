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
 * @ClassName: FirstCreateCommentGuideDialog
 * @Description: 创建点评 跳转到未点评的引导页
 * @author wuyulong
 * @date 2015-4-29 上午9:39:10
 * 
 */
public class FirstViewPagerGuideDialog extends BaseFragmentDialog {
	private View firstGuideViewPagerView;
	@ViewInject(R.id.rl_dismiss)
	private RelativeLayout mRLDismiss;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		firstGuideViewPagerView = inflater.inflate(
				R.layout.first_guide_viewpager, null);
		ViewUtils.inject(this, firstGuideViewPagerView);
		return firstGuideViewPagerView;
	}

	@OnClick(R.id.rl_dismiss)
	public void dismissView(View v) {
		dismiss();
		SharedPreferenceHelper.setNoFirstGuideCreateViewPager(getActivity());
		if(onviewpagerDialogStatus!=null){
			onviewpagerDialogStatus.onViewPagerDialogStatus(true);
		}
		
	}
	public interface OnViewPagerDialogStatus{
		public void onViewPagerDialogStatus(boolean isDismiss);
		
	}
	private OnViewPagerDialogStatus onviewpagerDialogStatus;
	
	public void setOnViewPagerDialogStatus(OnViewPagerDialogStatus onviewpagerDialogStatus){
		this.onviewpagerDialogStatus=onviewpagerDialogStatus;
		
	}
}
