package com.ninetowns.tootooplus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.AddressListFragment;
import com.ninetowns.tootooplus.fragment.PhoneNameListFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

public class PhoneNameListActivity extends FragmentGroupActivity {
	
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
		return PhoneNameListFragment.class;
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
