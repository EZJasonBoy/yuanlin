package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewGroup;

import com.ninetowns.tootoopluse.fragment.ActivityDetailFragment;

/**
 * 
 * @ClassName: CreateActSecondFragmentAdapter
 * @Description: 动态创建界面
 * @author wuyulong
 * @date 2015-2-9 下午2:53:04
 * 
 */
public class ActivityDetailFragmentAdapter extends FragmentStatePagerAdapter
		implements OnPageChangeListener {
	private List<ActivityDetailFragment> listFragment;
	public int currentPosition;

	public ActivityDetailFragmentAdapter(FragmentManager fm,
			List<ActivityDetailFragment> listFragment) {
		super(fm);
		this.listFragment = listFragment;
	}

	@Override
	public Fragment getItem(int arg0) {
		ActivityDetailFragment activityFragment = listFragment.get(arg0);
		return activityFragment;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}

	@Override
	public int getCount() {
		return listFragment.size();
	}

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
		currentPosition = arg0;
		if (listener != null) {
			listener.OnItemPageListener(currentPosition);
		}

	}

	private OnItemPagelistener listener;

	public interface OnItemPagelistener {
		public void OnItemPageListener(int postion);
	}

	public void setOnItemPageListener(OnItemPagelistener listener) {
		this.listener = listener;
	}

}
