package com.ninetowns.tootoopluse.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lidroid.xutils.ViewUtils;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.fragment.WishDetailFragment;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

/**
 * 
 * @ClassName: WishDetailActivity
 * @Description: 心愿详情
 * @author wuyulong
 * @date 2015-2-5 上午10:08:25
 * 
 */
public class WishDetailActivity extends FragmentGroupActivity {
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wish_detail_activity);
		getType();
		ViewUtils.inject(this);

	}

	/**
	 * 
	 * @Title: getType
	 * @Description: bundle 参数
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	private void getType() {
		bundle = getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);

	}

	@Override
	protected void initPrimaryFragment() {
		switchPrimaryFragment(R.id.fragment_stub);

	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		Class<? extends Fragment> clazz = null;
		clazz = WishDetailFragment.class;

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

}