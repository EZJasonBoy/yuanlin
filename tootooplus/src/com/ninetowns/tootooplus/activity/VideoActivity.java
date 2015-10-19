package com.ninetowns.tootooplus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lidroid.xutils.ViewUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.VideoFragment;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.ui.Activity.FragmentGroupActivity;
/**
 * 
* @ClassName: VideoActivity 
* @Description: 视频播放
* @author wuyulong
* @date 2015-3-4 上午11:09:23 
*
 */
public class VideoActivity extends FragmentGroupActivity {
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story_detail_item_type_activity);
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
		clazz = VideoFragment.class;

		return clazz;
	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		return R.id.only_fragment_frame;
	}

}