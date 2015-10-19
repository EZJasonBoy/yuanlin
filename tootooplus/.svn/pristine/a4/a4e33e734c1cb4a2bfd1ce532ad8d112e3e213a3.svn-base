package com.ninetowns.tootooplus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.FaceToFaceGroupEnterFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;
/**
 * 
* @ClassName: FaceToFaceGroupEnterActivity 
* @Description: 面对面组团输入界面
* @author wuyulong
* @date 2015-7-17 下午3:21:42 
*
 */
public class FaceToFaceGroupEnterActivity extends FragmentGroupActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.only_fragment_layout);
	}


	@Override
	protected void initPrimaryFragment() {
		switchPrimaryFragment(R.id.only_fragment_frame);
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		return FaceToFaceGroupEnterFragment.class;
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
