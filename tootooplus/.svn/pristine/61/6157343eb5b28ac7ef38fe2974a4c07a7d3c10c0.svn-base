package com.ninetowns.tootooplus.activity;

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
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.MyActDownLineCommentFragment;
import com.ninetowns.tootooplus.fragment.MyActOnLineCommentFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

/**
 * 
 * @ClassName: MyActivityCommentActivity
 * @Description: 我的活动
 * @author wuyulong
 * @date 2015-4-17 下午2:27:23
 * 
 */
public class MyActivityCommentActivity extends FragmentGroupActivity implements
		OnCheckedChangeListener {
	@ViewInject(R.id.iv_left)
	private ImageView mIVBack;// 返回上一级
	@ViewInject(R.id.rg_my_act)
	private RadioGroup mRGMyAct;
	@ViewInject(R.id.rbtn_online_free_act)
	private RadioButton mRbtnOnLineAct;// 组团线上白吃
	@ViewInject(R.id.rbtn_downline_free_act)
	private RadioButton mRbtnDownLineAct;// 组团先下白吃
	@ViewInject(R.id.fl_container)
	private FrameLayout mFlContainer;

	
	
	
	
	@OnClick(R.id.iv_left)
	public void backReturn(View view) {
		finish();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_activity_comment);
		ViewUtils.inject(this);
		mRGMyAct.setOnCheckedChangeListener(this);
	}

	@Override
	protected void initPrimaryFragment() {
		switchTab(R.id.rbtn_online_free_act);

	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {

		Class<? extends Fragment> clazz = null;
		switch (fragmentId) {
		case R.id.rbtn_online_free_act:// 线上
			clazz = MyActOnLineCommentFragment.class;
			break;
		case R.id.rbtn_downline_free_act://线下
			clazz = MyActDownLineCommentFragment.class;
			break;

		default:
			throw new IllegalArgumentException();
		}
		return clazz;

	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		return R.id.fragment_stub;
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
		case R.id.rbtn_online_free_act:
			mRGMyAct.setBackgroundResource(R.drawable.applay_yes);

			break;
		case R.id.rbtn_downline_free_act:
			mRGMyAct.setBackgroundResource(R.drawable.applay_no);
			break;

		}
		switchPrimaryFragment(checkedId);
	}

}
