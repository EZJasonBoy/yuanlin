package com.ninetowns.tootooplus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.MyInvitationFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

/**
 * @ClassName: MyCollectionActivity
 * @Description: 我的 收藏
 * @author zhou
 * @date 2015-4-20 上午11:21:28
 * 
 */
public class MyInvitationActivity extends FragmentGroupActivity  {
	private ImageView mBackImageView;
	
	private TextView mTitleTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinvitation);
		
		Button btnButton=(Button) findViewById(R.id.about_version_code);
		mBackImageView=(ImageView) findViewById(R.id.setting_iv_back);
		mTitleTextView=(TextView) findViewById(R.id.commontitlebar_tv_title);
		mTitleTextView.setText(getResources().getString(R.string.myinvitation));
		mBackImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyInvitationActivity.this.finish();
			}
		});
	}
	@Override
	protected void initPrimaryFragment() {
		// TODO Auto-generated method stub
		switchPrimaryFragment(R.id.only_fragment_frame);
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		// TODO Auto-generated method stub
		// return MyCollectionFragment.class;
		return MyInvitationFragment.class;
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
