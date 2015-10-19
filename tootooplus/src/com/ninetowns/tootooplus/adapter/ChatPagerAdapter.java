package com.ninetowns.tootooplus.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class ChatPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> mFragmentsList;
	private FragmentManager fm;

	public ChatPagerAdapter(FragmentManager fm,
			ArrayList<Fragment> mFragmentsList) {
		super(fm);
		this.fm = fm;
		this.mFragmentsList = mFragmentsList;
	}

	private int mChildCount = 0;
	private OnReloadListener mListener;

	@Override
	public void notifyDataSetChanged() {
		mChildCount = getCount();
		super.notifyDataSetChanged();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}

//	public void setPagerItems(ArrayList<Fragment> items) {
//		if (items != null) {
//			for (int i = 0; i < mFragmentsList.size(); i++) {
//				fm.beginTransaction().remove(mFragmentsList.get(i)).commit();
//			}
//			mFragmentsList = items;
//		}
//	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return mFragmentsList.get(arg0);
	}

	@Override
	public int getItemPosition(Object object) {
		if (mChildCount > 0) {
			mChildCount--;
			return POSITION_NONE;
		}
		return super.getItemPosition(object);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragmentsList == null ? 0 : mFragmentsList.size();
	}

	public interface OnReloadListener {
		public void onReload();
	}

	public void setOnReloadListener(OnReloadListener listener) {
		this.mListener = listener;
	}

	/**
	 * 当页面数据发生改变时你可以调用此方法
	 * 
	 * 重新载入数据，具体载入信息由回调函数实现
	 */
	public void reLoad() {
		if (mListener != null) {
			mListener.onReload();
		}
		this.notifyDataSetChanged();// 不可少，通知系统数据改变
	}
}
