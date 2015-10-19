package com.ninetowns.tootoopluse.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.lidroid.xutils.ViewUtils;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.fragment.SearchHomePageFragment;
import com.ninetowns.tootoopluse.fragment.WishSearchFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;
/**
 * 
* @ClassName: SearchActivity 
* @Description: 搜索活动、心愿、评论
* @author wuyulong
* @date 2015-4-13 上午10:18:51 
*
 */
public class SearchActivity extends FragmentGroupActivity{
	private String searchType="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		ViewUtils.inject(this);
		searchType=getIntent().getExtras().getString("searchType");
	}

	@Override
	protected void initPrimaryFragment() {
		switchPrimaryFragment(R.id.fragment_stub);
		
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		Class<? extends Fragment> clazz=null;
		if(!TextUtils.isEmpty(searchType)){
			if(searchType.equals("isWish")){
				 clazz = WishSearchFragment.class;
			}else if(searchType.equals("isActivity")){
				 clazz = SearchHomePageFragment.class;
			}else if(searchType.equals("isComment")){
				
			}else {
				LogUtil.error("search activity fragment 是null ","");
				throw new IllegalArgumentException();
			}
			
		}
		return clazz;
	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		return R.id.fragment_stub;
	}

}
