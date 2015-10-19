package com.ninetowns.tootooplus.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ninetowns.tootooplus.fragment.CreateSecondActivityFragment;
/**
 * 
* @ClassName: CreateActSecondFragmentAdapter 
* @Description: 动态创建界面
* @author wuyulong
* @date 2015-2-9 下午2:53:04 
*
 */
public class CreateActSecondFragmentAdapter extends FragmentStatePagerAdapter{
	private List<CreateSecondActivityFragment> listFragment;

	public CreateActSecondFragmentAdapter(FragmentManager fm,List<CreateSecondActivityFragment> listFragment) {
		super(fm);
		this.listFragment=listFragment;
	}

	@Override
	public Fragment getItem(int arg0) {
		return listFragment.get(arg0);
	}

	@Override
	public int getCount() {
		return listFragment.size();
	}

}
