package com.ninetowns.tootooplus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.FansFragment;
import com.ninetowns.tootooplus.fragment.FollowFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

public class FollowFansActivity extends FragmentGroupActivity implements OnClickListener{
	
	//关注按钮, 粉丝按钮
	private LinearLayout follow_fans_follow_layout, follow_fans_fans_layout;
	//关注, 粉丝
	private TextView follow_fans_follow, follow_fans_fans;
	//"follow"表示跳转到关注页, "fans"表示跳转到粉丝页
	private String follow_or_fans = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.follow_fans);
		
		init();
	}
	
	private void init(){
		follow_or_fans = getIntent().getStringExtra("follow_or_fans");
		//返回
		LinearLayout follow_fans_back = (LinearLayout)findViewById(R.id.follow_fans_back);
		
		follow_fans_follow_layout = (LinearLayout)findViewById(R.id.follow_fans_follow_layout);
		
		follow_fans_fans_layout = (LinearLayout)findViewById(R.id.follow_fans_fans_layout);
		
		follow_fans_follow = (TextView)findViewById(R.id.follow_fans_follow);
		
		follow_fans_fans = (TextView)findViewById(R.id.follow_fans_fans);
		
		
		follow_fans_back.setOnClickListener(this);
		follow_fans_follow_layout.setOnClickListener(this);
		follow_fans_fans_layout.setOnClickListener(this);
	}

	@Override
	protected void initPrimaryFragment() {
		// TODO Auto-generated method stub
		if(follow_or_fans.equals("fans")){
			switchTab(R.id.follow_fans_fans_layout);
		} else {
			switchTab(R.id.follow_fans_follow_layout);
		}
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		// TODO Auto-generated method stub
		Class<? extends Fragment> clazz=null;
		switch (fragmentId) {
		case R.id.follow_fans_follow_layout:
			clazz = FollowFragment.class;
			
			break;
		case R.id.follow_fans_fans_layout:
			clazz = FansFragment.class;
			break;

		default:
			throw new IllegalArgumentException();
		}
		return clazz;
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
	
	/**
	 * 切换标签时标签背景变化处理
	 * @param vId
	 */
	private void switchTab(int vId){
		switch(vId){
			case R.id.follow_fans_follow_layout:
				follow_fans_follow_layout.setBackgroundResource(R.drawable.btn_msg_black);
				follow_fans_follow.setTextColor(getResources().getColor(R.color.white));
				follow_fans_fans_layout.setBackgroundResource(R.drawable.btn_notice_white);
				follow_fans_fans.setTextColor(getResources().getColor(R.color.black));
				
			break;
			
			case R.id.follow_fans_fans_layout:
				follow_fans_follow_layout.setBackgroundResource(R.drawable.btn_msg_white);
				follow_fans_follow.setTextColor(getResources().getColor(R.color.black));
				follow_fans_fans_layout.setBackgroundResource(R.drawable.btn_notice_black);
				follow_fans_fans.setTextColor(getResources().getColor(R.color.white));
				
			break;
		}
		switchPrimaryFragment(vId);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.follow_fans_back:
			this.finish();
			break;
		case R.id.follow_fans_follow_layout:
			switchTab(v.getId());
			break;
		case R.id.follow_fans_fans_layout:
			switchTab(v.getId());
			break;
		}
	}

}
