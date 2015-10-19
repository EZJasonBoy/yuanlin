package com.ninetowns.tootoopluse.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.fragment.ActivityNoPassFragment;
import com.ninetowns.tootoopluse.fragment.PendingFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

/**
 * 
 * @ClassName: MyActivityApplyActivity
 * @Description: 我的活动申请
 * @author wuyulong
 * @date 2015-2-11 上午10:26:10
 * 
 */
public class MyActivityApplyActivity extends FragmentGroupActivity implements
		OnCheckedChangeListener {
	@ViewInject(R.id.iv_left)
	private ImageView mIVBack;// 返回上一级
	@ViewInject(R.id.rg_apply)
	private RadioGroup mRGapply;
	@ViewInject(R.id.rbtn_panding_audit)
	private RadioButton mRbtnPandingAudit;// 待审核
	@ViewInject(R.id.rbtn_no_throut)
	private RadioButton mRbtnNoThrout;// 未通过
	@ViewInject(R.id.fl_container)
	private FrameLayout mFlContainer;
	public String status = "";

	@OnClick(R.id.iv_left)
	public void backReturn(View view) {
		finish();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_activity_apply_activity);
		ViewUtils.inject(this);
		mRGapply.setOnCheckedChangeListener(this);
	}

	@Override
	protected void initPrimaryFragment() {
		switchTab(R.id.rbtn_panding_audit);

	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {

		Class<? extends Fragment> clazz = null;
		switch (fragmentId) {
		case R.id.rbtn_panding_audit:// 待审核
			status = "1";
			clazz = PendingFragment.class;
			break;
		case R.id.rbtn_no_throut:// 未通过
			status = "-1";
			clazz = ActivityNoPassFragment.class;
			break;

		default:
			throw new IllegalArgumentException();
		}
		return clazz;

	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		// TODO Auto-generated method stub
		return R.id.fl_container;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switchTab(checkedId);

	}

	/**
	 * 
	 * @Title: switchTab
	 * @Description: 切换tab页
	 * @param
	 * @return
	 * @throws
	 */
	private void switchTab(int checkedId) {
		switch (checkedId) {
		case R.id.rbtn_panding_audit:
			mRGapply.setBackgroundResource(R.drawable.applay_yes);

			break;
		case R.id.rbtn_no_throut:
			mRGapply.setBackgroundResource(R.drawable.applay_no);
			break;

		}
		switchPrimaryFragment(checkedId);
	}

}
