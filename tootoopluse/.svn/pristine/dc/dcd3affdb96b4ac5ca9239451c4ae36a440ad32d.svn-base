package com.ninetowns.tootoopluse.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.fragment.ActDataFragment;
import com.ninetowns.tootoopluse.fragment.WishDataFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

public class MyLookDataActivity extends FragmentGroupActivity implements OnClickListener{
	
	private LinearLayout look_data_select_check_layout;
	
	private LinearLayout look_data_head_back;
	
	private TextView look_data_act_tv;
	
	private TextView look_data_wish_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_look_data);
		
		look_data_select_check_layout = (LinearLayout)findViewById(R.id.look_data_select_check_layout);
		
		look_data_act_tv = (TextView)findViewById(R.id.look_data_act_tv);
		look_data_wish_tv = (TextView)findViewById(R.id.look_data_wish_tv);
		
		look_data_head_back = (LinearLayout)findViewById(R.id.look_data_head_back);
		
		look_data_act_tv.setOnClickListener(this);
		
		look_data_wish_tv.setOnClickListener(this);
		look_data_head_back.setOnClickListener(this);
		
	}

	@Override
	protected void initPrimaryFragment() {
		// TODO Auto-generated method stub
		switchTab(R.id.look_data_act_tv);
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		// TODO Auto-generated method stub
		Class<? extends Fragment> clazz = null;
		switch (fragmentId) {
		case R.id.look_data_act_tv:// 活动数据
			clazz = ActDataFragment.class;
			break;
		case R.id.look_data_wish_tv:// 心愿数据
			clazz = WishDataFragment.class;
			break;
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
		return R.id.look_data_content_layout;
	}

	
	private void switchTab(int index_id){
		switch(index_id){
		case R.id.look_data_act_tv:
			look_data_select_check_layout.setBackgroundResource(R.drawable.applay_yes);
			look_data_act_tv.setTextColor(getResources().getColor(R.color.white));
			look_data_wish_tv.setTextColor(getResources().getColor(R.color.title_textcolor));
			
			break;
		case R.id.look_data_wish_tv:
			look_data_select_check_layout.setBackgroundResource(R.drawable.applay_no);
			look_data_act_tv.setTextColor(getResources().getColor(R.color.title_textcolor));
			look_data_wish_tv.setTextColor(getResources().getColor(R.color.white));
			
			break;
			
		}
		switchPrimaryFragment(index_id);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.look_data_head_back:
			finish();
			break;
			
		case R.id.look_data_act_tv:
			switchTab(R.id.look_data_act_tv);
			break;
			
		case R.id.look_data_wish_tv:
			switchTab(R.id.look_data_wish_tv);
			break;
		}
	}
}
