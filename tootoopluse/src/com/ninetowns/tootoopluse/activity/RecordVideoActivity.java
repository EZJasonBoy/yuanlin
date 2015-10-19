package com.ninetowns.tootoopluse.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lidroid.xutils.ViewUtils;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.fragment.VideoRecommendStoryCreateFragment;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

/**
 * 
 * @ClassName: CreateWishActivity
 * @Description:录制视频
 * @author wuyulong
 * @date 2015-1-23 下午4:14:03
 * 
 */
public class RecordVideoActivity extends FragmentGroupActivity {
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_wish_activity);
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
		// 根据不同的type显示是创建界面 还是 推荐创建界面 还是编辑界面

		clazz = VideoRecommendStoryCreateFragment.class;// 录制视频

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
