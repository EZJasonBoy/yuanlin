package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;

import com.ninetowns.tootoopluse.bean.HomePageBean;
/**
 * 
* @ClassName: AuditAdapter 
* @Description: 待审核
* @author wuyulong
* @date 2015-3-27 下午12:53:46 
*
 */
public class AuditAdapter extends HomePageAdapter{

	public AuditAdapter(Activity activity, List<HomePageBean> homePageListBean) {
		super(activity, homePageListBean);
	}
	@Override
	public void justActivityStatus(HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean) {
		homePageAdatperHolder.mIVAgainPush.setVisibility(View.GONE);
		homePageAdatperHolder.mIVUserInfo.setVisibility(View.GONE);
		homePageAdatperHolder.mCTRemainningtime.setVisibility(View.INVISIBLE);
		
	}
}
