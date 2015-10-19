package com.ninetowns.tootooplus.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.MyFreeGroupFragment;
import com.ninetowns.ui.Activity.BaseActivity;
/**
 * 我的白吃团
 * @author huangchao
 *
 */
public class MyFreeGroupActivity extends BaseActivity implements OnClickListener{
	
	private TextView free_group_not_enter_tv, free_group_enter_tv, free_group_select_tv;
	
	private RelativeLayout free_group_not_enter_layout, free_group_enter_layout, free_group_select_layout;
	
	private View free_group_not_enter_line, free_group_enter_line, free_group_select_line;
	
	private ViewPager free_group_viewPager;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.my_free_group);
		
		LinearLayout two_or_one_btn_head_back = (LinearLayout)findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		TextView two_or_one_btn_head_title = (TextView)findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.my_free_group_title);
		
		free_group_not_enter_layout = (RelativeLayout)findViewById(R.id.free_group_not_enter_layout);
		free_group_enter_layout = (RelativeLayout)findViewById(R.id.free_group_enter_layout);
		free_group_select_layout = (RelativeLayout)findViewById(R.id.free_group_select_layout);
		free_group_not_enter_line = (View)findViewById(R.id.free_group_not_enter_line);
		free_group_enter_line = (View)findViewById(R.id.free_group_enter_line);
		free_group_select_line = (View)findViewById(R.id.free_group_select_line);
		
		free_group_not_enter_tv = (TextView)findViewById(R.id.free_group_not_enter_tv);
		free_group_enter_tv = (TextView)findViewById(R.id.free_group_enter_tv);
		free_group_select_tv = (TextView)findViewById(R.id.free_group_select_tv);
		
		free_group_not_enter_layout.setOnClickListener(this);
		free_group_enter_layout.setOnClickListener(this);
		free_group_select_layout.setOnClickListener(this);
		
		free_group_viewPager = (ViewPager)findViewById(R.id.free_group_viewPager);
		
		List<Fragment> groupFramgentList = new ArrayList<Fragment>();
		groupFramgentList.add(new MyFreeGroupFragment("1"));
		groupFramgentList.add(new MyFreeGroupFragment("2"));
		groupFramgentList.add(new MyFreeGroupFragment("3"));
		
		free_group_viewPager.setAdapter(new GroupItemAdater(getSupportFragmentManager(), groupFramgentList));
		
		free_group_viewPager.setOnPageChangeListener(new GroupItemChangeListener());
		Intent intent = getIntent();
		String group_tab = intent.getStringExtra("group_tab");
		if(group_tab != null && group_tab.equals("2")){
			titleBgChange(2);
			free_group_viewPager.setCurrentItem(2);
		} else {
			titleBgChange(0);
			free_group_viewPager.setCurrentItem(0);
		}
		
	}

	/**
	 * 标题背景的变化
	 * @param clickItem
	 */
	private void titleBgChange(int clickItem){
		if(clickItem == 0){
			free_group_not_enter_tv.setTextColor(getResources().getColor(R.color.btn_org_color));
			free_group_enter_tv.setTextColor(getResources().getColor(R.color.btn_gray_color));
			free_group_select_tv.setTextColor(getResources().getColor(R.color.btn_gray_color));
			free_group_not_enter_line.setVisibility(View.VISIBLE);
			free_group_enter_line.setVisibility(View.GONE);
			free_group_select_line.setVisibility(View.GONE);
		} else if(clickItem == 1){
			free_group_not_enter_tv.setTextColor(getResources().getColor(R.color.btn_gray_color));
			free_group_enter_tv.setTextColor(getResources().getColor(R.color.btn_org_color));
			free_group_select_tv.setTextColor(getResources().getColor(R.color.btn_gray_color));
			free_group_not_enter_line.setVisibility(View.GONE);
			free_group_enter_line.setVisibility(View.VISIBLE);
			free_group_select_line.setVisibility(View.GONE);
		} else if(clickItem == 2){
			free_group_not_enter_tv.setTextColor(getResources().getColor(R.color.btn_gray_color));
			free_group_enter_tv.setTextColor(getResources().getColor(R.color.btn_gray_color));
			free_group_select_tv.setTextColor(getResources().getColor(R.color.btn_org_color));
			free_group_not_enter_line.setVisibility(View.GONE);
			free_group_enter_line.setVisibility(View.GONE);
			free_group_select_line.setVisibility(View.VISIBLE);
		}
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.free_group_not_enter_layout:
			free_group_viewPager.setCurrentItem(0);
			titleBgChange(0);
			break;
		case R.id.free_group_enter_layout:
			free_group_viewPager.setCurrentItem(1);
			titleBgChange(1);
			break;
		case R.id.free_group_select_layout:
			free_group_viewPager.setCurrentItem(2);
			titleBgChange(2);
			break;
		}
	}
	
	
	//数据适配器
	class GroupItemAdater extends FragmentPagerAdapter {
		
		private List<Fragment> list;

		public GroupItemAdater(FragmentManager fm, List<Fragment> list) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(list != null && list.size() > 0){
				return list.size();
			} else {
				return 0;
			}
			
		}
		
	}
	
	// 更改事件监听器
    class GroupItemChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			
			titleBgChange(arg0);
			
		}
    	
    }
}
