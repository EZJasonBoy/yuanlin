package com.ninetowns.tootoopluse.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ninetowns.tootoopluse.fragment.GroupChatFragment;


public class ParentGroupChatFragmentPagerAdapter extends FragmentPagerAdapter {

	private FragmentManager mFragmentManager;
	private ArrayList<GroupChatFragment> fragmentsList;

	public ParentGroupChatFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	public ParentGroupChatFragmentPagerAdapter(FragmentManager fragmentManager,
			ArrayList<GroupChatFragment> fragments) {
		super(fragmentManager);
		this.mFragmentManager = fragmentManager;
		this.fragmentsList = fragments;
	}
	
	@Override
	public Fragment getItem(int arg0) {
		return fragmentsList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentsList==null?0:fragmentsList.size();
	}

}
