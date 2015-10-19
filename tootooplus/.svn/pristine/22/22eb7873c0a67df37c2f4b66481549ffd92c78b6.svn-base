package com.ninetowns.tootooplus.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.ninetowns.tootooplus.bean.ActivityDetailBean;
import com.ninetowns.tootooplus.fragment.ActivityDetailFragment;

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
	private ActivityDetailBean resultData;// 活动详情的数据结构

	public ActivityDetailFragmentAdapter(FragmentManager fm,
			List<ActivityDetailFragment> listFragment,
			ActivityDetailBean resultData, int currentPostion) {
		super(fm);
		this.listFragment = listFragment;
		this.resultData = resultData;
		this.currentPosition = currentPostion;
	}

	@Override
	public Fragment getItem(int arg0) {
		ActivityDetailFragment activityFragment = listFragment.get(arg0);
		return activityFragment;
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
		if (onItemPagePosition != null) {
			onItemPagePosition.OnItemPagePostion(currentPosition);
		}
		// if(listFragment.get(currentPosition)!=null){
		// ActivityDetailFragment currentPositionFragment =
		// listFragment.get(currentPosition);
		// if(currentPositionFragment!=null){
		// currentPositionFragment.setSelectListener(currentPosition);
		// }
		//
		// }

	}

	private OnItemPagePostion onItemPagePosition;

	public interface OnItemPagePostion {
		public void OnItemPagePostion(int position);

	}

	public void setOnPagePostion(OnItemPagePostion onItemPagePosition) {
		this.onItemPagePosition = onItemPagePosition;
	}

}
