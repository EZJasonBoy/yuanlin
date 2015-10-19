package com.ninetowns.tootoopluse.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lidroid.xutils.ViewUtils;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.fragment.MyJoinMemberFragment;
import com.ninetowns.tootoopluse.fragment.MyTempJoinMemberFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;
/**
 * 我的白吃团招募
 *
 */
public class MyJoinMemberActivity extends FragmentGroupActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.only_fragment_layout);
		
		ViewUtils.inject(this);
		
	}

	@Override
	protected void initPrimaryFragment() {
		switchPrimaryFragment(R.id.only_fragment_frame);

	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
        Class<? extends Fragment> clazz = null;
        //根据不同的type显示是创建界面 还是 推荐创建界面 还是编辑界面
//        	clazz=MyJoinMemberFragment.class;
        clazz = MyTempJoinMemberFragment.class;

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
