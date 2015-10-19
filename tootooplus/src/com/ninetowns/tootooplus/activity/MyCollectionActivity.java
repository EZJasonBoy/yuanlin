package com.ninetowns.tootooplus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lidroid.xutils.ViewUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.HomePageFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

/** 
* @ClassName: MyCollectionActivity 
* @Description: 我的 收藏 
* @author zhou
* @date 2015-4-20 上午11:21:28 
*  
*/
public class MyCollectionActivity extends FragmentGroupActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.only_fragment_layout);
		ViewUtils.inject(this);
	}

	@Override
	protected void initPrimaryFragment() {
		// TODO Auto-generated method stub
		switchPrimaryFragment(R.id.only_fragment_frame);
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		// TODO Auto-generated method stub
//		return MyCollectionFragment.class;
		return HomePageFragment.class;
	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		// TODO Auto-generated method stub
		return R.id.only_fragment_frame;
	}
	
}
	