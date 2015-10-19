package com.ninetowns.tootoopluse.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetowns.tootoopluse.R;
import com.ninetowns.ui.Activity.BaseActivity;

public class FirstGuideActivity extends BaseActivity {
	
	private List<View> itemViews = null;
	
	private ImageView first_guide_mark_one, first_guide_mark_two, first_guide_mark_three, 
		first_guide_mark_four,first_guide_mark_five;
	
	private ViewPager first_guide_viewPager;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.first_guide);
		itemViews = new ArrayList<View>();
		View item_one_view = LayoutInflater.from(this).inflate(R.layout.first_guide_item_one, null);
		itemViews.add(item_one_view);
		View item_two_view = LayoutInflater.from(this).inflate(R.layout.first_guide_item_two, null);
		itemViews.add(item_two_view);
		View item_three_view = LayoutInflater.from(this).inflate(R.layout.first_guide_item_three, null);
		itemViews.add(item_three_view);
		View item_four_view = LayoutInflater.from(this).inflate(R.layout.first_guide_item_four, null);
		itemViews.add(item_four_view);
		View item_five_view = LayoutInflater.from(this).inflate(R.layout.first_guide_item_five, null);
		itemViews.add(item_five_view);
		
		first_guide_mark_one = (ImageView)findViewById(R.id.first_guide_mark_one);
		first_guide_mark_two = (ImageView)findViewById(R.id.first_guide_mark_two);
		first_guide_mark_three = (ImageView)findViewById(R.id.first_guide_mark_three);
		first_guide_mark_four = (ImageView)findViewById(R.id.first_guide_mark_four);
		first_guide_mark_five = (ImageView)findViewById(R.id.first_guide_mark_five);
		//默认情况下
		first_guide_mark_one.setImageResource(R.drawable.icon_dot_white);
		first_guide_mark_two.setImageResource(R.drawable.icon_dot_gray);
		first_guide_mark_three.setImageResource(R.drawable.icon_dot_gray);
		first_guide_mark_four.setImageResource(R.drawable.icon_dot_gray);
		first_guide_mark_five.setImageResource(R.drawable.icon_dot_gray);
		
		TextView first_guide_register = (TextView)findViewById(R.id.first_guide_register);
		first_guide_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//注册
				Intent intent = new Intent(FirstGuideActivity.this, MobileRegisterActivity.class);
				startActivity(intent);
			}
		});
		TextView first_guide_login = (TextView)findViewById(R.id.first_guide_login);
		first_guide_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//登录
				Intent intent = new Intent(FirstGuideActivity.this, MobileLoginActivity.class);
				startActivity(intent);
			}
		});
		
		first_guide_viewPager = (ViewPager)findViewById(R.id.first_guide_viewPager);
		first_guide_viewPager.setAdapter(new FirstGuideItem(itemViews));
		    
		first_guide_viewPager.setOnPageChangeListener(new FirstGuideItemChangeListener());
	}
	

	// 指引页面数据适配器
	class FirstGuideItem extends PagerAdapter {
		
		private List<View> views;
		
		public FirstGuideItem(List<View> views){
			this.views = views;
		}
		
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager)arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			// TODO Auto-generated method stub
			((ViewPager)arg0).addView(views.get(arg1));
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	// 指引页面更改事件监听器
    class FirstGuideItemChangeListener implements OnPageChangeListener{

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
			if(arg0 == 0){
				first_guide_mark_one.setImageResource(R.drawable.icon_dot_white);
				first_guide_mark_two.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_three.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_four.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_five.setImageResource(R.drawable.icon_dot_gray);
			} else if(arg0 == 1){
				first_guide_mark_one.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_two.setImageResource(R.drawable.icon_dot_white);
				first_guide_mark_three.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_four.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_five.setImageResource(R.drawable.icon_dot_gray);
			} else if(arg0 == 2){
				first_guide_mark_one.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_two.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_three.setImageResource(R.drawable.icon_dot_white);
				first_guide_mark_four.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_five.setImageResource(R.drawable.icon_dot_gray);
			} else if(arg0 == 3){
				first_guide_mark_one.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_two.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_three.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_five.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_four.setImageResource(R.drawable.icon_dot_white);
			}else{
				first_guide_mark_one.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_two.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_three.setImageResource(R.drawable.icon_dot_gray);
				first_guide_mark_five.setImageResource(R.drawable.icon_dot_white);
				first_guide_mark_four.setImageResource(R.drawable.icon_dot_gray);
			}
			
		}
    	
    }
}
