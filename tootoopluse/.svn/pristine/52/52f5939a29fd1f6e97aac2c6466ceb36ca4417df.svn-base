package com.ninetowns.tootoopluse.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.fragment.AddressListFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

public class AddressListActivity extends FragmentGroupActivity {
	
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
		return AddressListFragment.class;
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
