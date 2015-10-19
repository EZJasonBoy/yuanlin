package com.ninetowns.tootooplus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.AddressAddFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;
/**
 * 组团进入的的新增
 * @author huangchao
 *
 */
public class AddressAddActivity extends FragmentGroupActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.only_fragment_layout);
	}


	@Override
	protected void initPrimaryFragment() {
		// TODO Auto-generated method stub
		switchPrimaryFragment(R.id.only_fragment_frame);
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		// TODO Auto-generated method stub
		return AddressAddFragment.class;
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
