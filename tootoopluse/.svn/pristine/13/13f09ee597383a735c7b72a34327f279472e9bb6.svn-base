package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;

import com.ninetowns.tootoopluse.bean.WishBean;
/**
 * 
* @ClassName: WishHomeAdapter 
* @Description: 心愿列表
* @author wuyulong
* @date 2015-3-3 下午2:45:05 
*
 */
public class WishHomeAdapter extends WishAdapter{
	private Activity activity;

	public WishHomeAdapter(Activity activity, List<WishBean> wishListBean) {
		super(activity, wishListBean);
		this.activity=activity;
	}
	@Override
	public void justIsDraftOrMyWishView(WishHolder wishHolder,
			WishBean selectedWishBean) {
		wishHolder.mRlStoryId.setVisibility(View.GONE);
		wishHolder.mIvDelOrEdiTwo.setVisibility(View.INVISIBLE);
		wishHolder.mIvDelOrEdiOne.setVisibility(View.INVISIBLE);
	}

}
