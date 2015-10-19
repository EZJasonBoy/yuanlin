package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.activity.CreateActSecondStepActivity;
import com.ninetowns.tootoopluse.activity.UpdateActFirstStepActivity;
import com.ninetowns.tootoopluse.activity.UpdateActThirdStepActivity;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.ConVertBean;
import com.ninetowns.tootoopluse.bean.HomePageBean;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;

/**
 * 
 * @ClassName: EditextTepAdapter
 * @Description: 编辑活动
 * @author wuyulong
 * @date 2015-3-13 下午5:40:02
 * 
 */
public class EditextTepAdapter extends HomePageAdapter {
	private HomePageBean homePageBean;
	private Activity activity;
	private List<ConVertBean> convertList;

	public EditextTepAdapter(Activity activity,
			List<HomePageBean> homePageListBean) {
		super(activity, homePageListBean);
		this.activity = activity;
	}
	@Override
	protected void switchButton(HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean) {
		homePageBean.setActEdiVisible(false);
		homePageAdatperHolder.mIVAgainPush.setVisibility(View.VISIBLE);
		homePageAdatperHolder.ediTepView.setVisibility(View.GONE);
	
		
	}
	@Override
	protected void switchPush(HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean) {
		homePageBean.setActEdiVisible(true);
		homePageAdatperHolder.mIVAgainPush.setVisibility(View.GONE);
		homePageAdatperHolder.ediTepView.setVisibility(View.VISIBLE);
	}
	@Override
	public void justActivityStatus(HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean) {
		this.homePageBean=homePageBean;
		homePageAdatperHolder.mIVAgainPush.setVisibility(View.VISIBLE);
		homePageAdatperHolder.mIVUserInfo.setVisibility(View.GONE);
		homePageAdatperHolder.mIVActivityStatus.setVisibility(View.GONE);
		homePageAdatperHolder.mCTRemainningtime.setVisibility(View.INVISIBLE);
		LayoutParams ediPar =homePageAdatperHolder.ediTepView.getLayoutParams();
		if(ediPar!=null){
			int width = CommonUtil.getWidth(TootooPlusEApplication.getAppContext());
			int height = width * 2 / 3;
			ediPar.height=height;
			homePageAdatperHolder.ediTepView.setLayoutParams(ediPar);
		}
		
		boolean isVisibleEditView = homePageBean.isActEdiVisible();//默认不显示编辑第几步界面
		
		if(isVisibleEditView){
		
			homePageAdatperHolder.ediTepView.setVisibility(View.VISIBLE);
			homePageAdatperHolder.mIVAgainPush.setVisibility(View.GONE);
		}else{
			
			homePageAdatperHolder.ediTepView.setVisibility(View.GONE);
			homePageAdatperHolder.mIVAgainPush.setVisibility(View.VISIBLE);
			
		}
		
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ll_first:
			HomePageBean homePageBean1=(HomePageBean) v.getTag();
			Intent intent = new Intent(TootooPlusEApplication.getAppContext(),
					UpdateActFirstStepActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString(ConstantsTooTooEHelper.ACTIVITYID,
					homePageBean1.getActivityId());
			bundle.putBoolean("isAgainPush", false);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
			activity.startActivity(intent);
			break;
		case R.id.ll_second:
			HomePageBean homePageBean2=(HomePageBean) v.getTag();
			Intent intents = new Intent(TootooPlusEApplication.getAppContext(),
					CreateActSecondStepActivity.class);
			Bundle bundles = new Bundle();
			bundles.putString(ConstantsTooTooEHelper.ACTIVITYID,
					homePageBean2.getActivityId());
			bundles.putBoolean("isEditextAct", true);
			bundles.putBoolean("isAgainPush", false);
			bundles.putBoolean("isEditextSecond", true);
			bundles.putString(ConstantsTooTooEHelper.STORYID, homePageBean2.getStoryArray());
			intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intents.putExtra(ConstantsTooTooEHelper.BUNDLE, bundles);
			activity.startActivity(intents);

			break;
		case R.id.ll_third:
			HomePageBean homePageBean3=(HomePageBean) v.getTag();
			Intent mEdiThird=new Intent(activity,UpdateActThirdStepActivity.class);
			Bundle bundle1=new Bundle();
			bundle1.putBoolean("isAgainPush", false);
//			bundle1.putString(ConstantsTooTooEHelper.STORYID, storyId);
			bundle1.putString(ConstantsTooTooEHelper.ACTIVITYID, homePageBean3.getActivityId());
			if(!TextUtils.isEmpty(homePageBean3.getActivityName())){
				bundle1.putString(ConstantsTooTooEHelper.ACTIVITY_NAME, homePageBean3.getActivityName());
			}else{
				bundle1.putString(ConstantsTooTooEHelper.ACTIVITY_NAME, "");
			}
			mEdiThird.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			mEdiThird.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle1);
			activity.startActivity(mEdiThird);
			break;

		default:
			break;
		}

	
		
	}
}
