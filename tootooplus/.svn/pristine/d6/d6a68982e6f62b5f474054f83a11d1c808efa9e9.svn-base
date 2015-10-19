package com.ninetowns.tootooplus.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.WishBean;

public class DraftAdapter extends WishAdapter{
	private Activity activity;

	public DraftAdapter(Activity activity, List<WishBean> wishListBean) {
		super(activity, wishListBean);
		this.activity=activity;
	}
	@Override
	public void justIsDraftOrMyWishView(WishHolder wishHolder,
			WishBean selectedWishBean) {
		wishHolder.mRlStoryId.setVisibility(View.GONE);
		wishHolder.mIvDelOrEdiOne.setVisibility(View.VISIBLE);
		wishHolder.mIvDelOrEdiTwo.setVisibility(View.VISIBLE);
		wishHolder.mIvDelOrEdiOne.setImageResource(R.drawable.icon_edit);
		wishHolder.mIvDelOrEdiTwo.setImageResource(R.drawable.icon_del);
	}
}
