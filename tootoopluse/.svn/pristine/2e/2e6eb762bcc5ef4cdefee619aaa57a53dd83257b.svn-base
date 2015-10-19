package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.ninetowns.tootoopluse.fragment.CreateSecondActivityFragment;
/**
 * 
* @ClassName: CreateActSecondFragmentAdapter 
* @Description: 动态创建界面 FragmentStatePagerAdapter 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，应该使用FragmentStatePagerAdapter
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

	@Override//
	public Fragment getItem(int arg0) {//如果需要向 Fragment 对象传递相对静态的数据时，我们一般通过 Fragment.setArguments() 来进行
		return listFragment.get(arg0);
	}

	@Override
	public int getCount() {
		return listFragment.size();
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

}
