package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.activity.ActivityDetailActivity;
import com.ninetowns.tootoopluse.activity.AddressListActivity;
import com.ninetowns.tootoopluse.activity.CreateActSecondStepActivity;
import com.ninetowns.tootoopluse.activity.HomeActivity;
import com.ninetowns.tootoopluse.activity.MyActivityApplyActivity;
import com.ninetowns.tootoopluse.activity.MyConstanctActivity;
import com.ninetowns.tootoopluse.activity.PersonalHomeActivity;
import com.ninetowns.tootoopluse.activity.UpdateActFirstStepActivity;
import com.ninetowns.tootoopluse.activity.UpdateActThirdStepActivity;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.ConVertBean;
import com.ninetowns.tootoopluse.bean.HomePageBean;
import com.ninetowns.tootoopluse.bean.PostAresBean;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.helper.HomePagerCityHelper;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.tootoopluse.util.UIUtils;
import com.ninetowns.ui.widget.text.MarqueeTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 
 * @ClassName: HomePageAdapter
 * @Description: 我的活动首页
 * @author wuyulong
 * @date 2015-2-7 上午10:56:00
 * 
 */
@SuppressLint("NewApi")
public class HomePageAdapter extends BaseAdapter implements OnClickListener{
	private List<HomePageBean> homePageListBean;
	private LayoutInflater mInflater;
	private int currentPosition;
	private Activity activity;
	private HomePageBean homePageBean;
	private List<ConVertBean> convertList;

	public HomePageAdapter(Activity activity,
			List<HomePageBean> homePageListBean) {
		this.homePageListBean = homePageListBean;
		this.activity = activity;
		setInflater();
	}

	@Override
	public int getCount() {
		return homePageListBean.size();
	}

	private void setInflater() {
		mInflater = LayoutInflater.from(TootooPlusEApplication.getAppContext());

	}

	@Override
	public Object getItem(int position) {
		return homePageListBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HomePageBean homePageBean = homePageListBean.get(position);
		HomePageAdatperHolder homePageAdatperHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.homepager_adapter, null);
			homePageAdatperHolder = new HomePageAdatperHolder();
			ViewUtils.inject(homePageAdatperHolder, convertView);
			convertView.setTag(homePageAdatperHolder);
		} else {
			homePageAdatperHolder = (HomePageAdatperHolder) convertView
					.getTag();
		}
		Integer topCoefficient = homePageBean.getTopCoefficient();
		if(topCoefficient!=null){
			if(topCoefficient>ConstantsTooTooEHelper.TopCoefficient){//置顶
				setDefaultViewVisible(homePageAdatperHolder, View.GONE);
				initConvertViewPageAdapter(homePageAdatperHolder, homePageBean,position);
			}else{
				setDefaultViewVisible(homePageAdatperHolder, View.VISIBLE);
				justActivity(homePageAdatperHolder, homePageBean);
				initConvertViewPageAdapter(homePageAdatperHolder, homePageBean,position);
				justUpOrOnLineActivity(homePageAdatperHolder, homePageBean);
				initPhotoInfo(homePageAdatperHolder, homePageBean);
				initLayoutBottomInfo(homePageAdatperHolder, homePageBean,position);
				setOnClickCity(homePageAdatperHolder, homePageBean,position);
				justActivityStatus(homePageAdatperHolder, homePageBean);
				String userid=homePageBean.getUserId();
				if(!TextUtils.isEmpty(userid)){
					homePageAdatperHolder.mIVPhoto.setTag(userid);
				}
				homePageAdatperHolder.mIVPhoto.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						if(!(activity instanceof PersonalHomeActivity)){
							String userid = (String) v.getTag();
							String myUserId = SharedPreferenceHelper
									.getLoginUserId(TootooPlusEApplication.getAppContext());
							if (!TextUtils.isEmpty(userid) && !userid.equals(myUserId)) {
								Intent intent = new Intent(activity,
										PersonalHomeActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								intent.putExtra("userId", userid);
								activity.startActivity(intent);
							} else {
								Bundle bundle=new Bundle();
								Intent intent = new Intent(TootooPlusEApplication.getAppContext(),
										HomeActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								bundle.putInt("tab_index", 3);
								intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
								activity.startActivity(intent);
								
							}
						}

					
						
					
						
					}
				});
			
			
				
				homePageAdatperHolder.mIVAgainPush.setOnClickListener(new MyPushAgain(homePageAdatperHolder,homePageBean));
			
				homePageAdatperHolder.mIVUserInfo.setOnClickListener(new MyUserInfo(homePageAdatperHolder,homePageBean));
				
				homePageAdatperHolder.mLLSwitch.setOnClickListener(new MySwitchButton(homePageAdatperHolder,homePageBean));
			}
			
		}else{
			setDefaultViewVisible(homePageAdatperHolder, View.VISIBLE);
			justActivity(homePageAdatperHolder, homePageBean);
			initConvertViewPageAdapter(homePageAdatperHolder, homePageBean,position);
			justUpOrOnLineActivity(homePageAdatperHolder, homePageBean);
			initPhotoInfo(homePageAdatperHolder, homePageBean);
			initLayoutBottomInfo(homePageAdatperHolder, homePageBean,position);
			setOnClickCity(homePageAdatperHolder, homePageBean,position);
			justActivityStatus(homePageAdatperHolder, homePageBean);
			String userid=homePageBean.getUserId();
			if(!TextUtils.isEmpty(userid)){
				homePageAdatperHolder.mIVPhoto.setTag(userid);
			}
			homePageAdatperHolder.mIVPhoto.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if(!(activity instanceof PersonalHomeActivity)){
						String userid = (String) v.getTag();
						String myUserId = SharedPreferenceHelper
								.getLoginUserId(TootooPlusEApplication.getAppContext());
						if (!TextUtils.isEmpty(userid) && !userid.equals(myUserId)) {
							Intent intent = new Intent(activity,
									PersonalHomeActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra("userId", userid);
							activity.startActivity(intent);
						} else {
							Bundle bundle=new Bundle();
							Intent intent = new Intent(TootooPlusEApplication.getAppContext(),
									HomeActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							bundle.putInt("tab_index", 3);
							intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
							activity.startActivity(intent);
							
						}
					}

				
					
				
					
				}
			});
		
		
			
			homePageAdatperHolder.mIVAgainPush.setOnClickListener(new MyPushAgain(homePageAdatperHolder,homePageBean));
			homePageAdatperHolder.mIVUserInfo.setOnClickListener(new MyUserInfo(homePageAdatperHolder,homePageBean));
			homePageAdatperHolder.mLLSwitch.setOnClickListener(new MySwitchButton(homePageAdatperHolder,homePageBean));
		
		}
		
		return convertView;
	}

/**
 * 
* @ClassName: MySwitchButton 
* @Description: 隐藏显示两个按钮
* @author wuyulong
* @date 2015-5-4 下午1:21:09 
*
 */
	class MySwitchButton implements OnClickListener{
		private HomePageAdatperHolder homePageAdatperHolder;
		private HomePageBean homePageBean;
		public MySwitchButton(HomePageAdatperHolder homePageAdatperHolder,HomePageBean homePageBean) {
			this.homePageAdatperHolder=homePageAdatperHolder;
			this.homePageBean=homePageBean;
		}

		@Override
		public void onClick(View v) {
			switchButton(homePageAdatperHolder,homePageBean);
			
		}

	
		
	}
	/** 
	* @Title: switchButton 
	* @Description: TODO
	* @param  
	* @return   
	* @throws 
	*/
	protected void switchButton(HomePageAdatperHolder homePageAdatperHolder,HomePageBean homePageBean) {
		homePageBean.setActEdiVisible(false);
		String type=homePageBean.getType();
		if(!TextUtils.isEmpty(type)){
			if(type.equals("1")){//0是线上  1 是线下 已经结束的线下活动，封面图不应该有收货地址，仅仅已经结束的线上活动有收货地址
				homePageAdatperHolder.mIVUserInfo.setVisibility(View.VISIBLE);
				//
				homePageAdatperHolder.mIVUserInfo.setImageResource(R.drawable.icon_lxfs);
//				android:src="@drawable/home_icon_user_info"
			}else if(type.equals("0")){
				homePageAdatperHolder.mIVUserInfo.setVisibility(View.VISIBLE);
				homePageAdatperHolder.mIVUserInfo.setImageResource(R.drawable.home_icon_user_info);
			}
		}
		homePageAdatperHolder.mIVAgainPush.setVisibility(View.VISIBLE);
		homePageAdatperHolder.ediTepView.setVisibility(View.GONE);
		homePageAdatperHolder.mIVActivityStatus.setVisibility(View.VISIBLE);
	}
/**
 * 
* @Title: justActivityStatus 
* @Description: 判断活动的状态，如果活动结束了 显示再次发布和查看收获地址的按钮
* @param  
* @return   
* @throws
 */
	public void justActivityStatus(
			HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean) {
		
		LayoutParams ediPar =homePageAdatperHolder.ediTepView.getLayoutParams();
		if(ediPar!=null){
			int width = CommonUtil.getWidth(TootooPlusEApplication.getAppContext());
			int height = width * 2 / 3;
			ediPar.height=height;
			homePageAdatperHolder.ediTepView.setLayoutParams(ediPar);
		}
		
		boolean isVisibleEditView = homePageBean.isActEdiVisible();//默认不显示编辑第几步界面
		String activityStatus=homePageBean.getActivityStatus();
		Resources resource = TootooPlusEApplication.getAppContext().getResources();
		if(!TextUtils.isEmpty(activityStatus)){
			if(activityStatus.equals("0")){//即将开始
				if(isVisibleEditView){
					homePageAdatperHolder.mIVActivityStatus.setVisibility(View.GONE);
					homePageAdatperHolder.ediTepView.setVisibility(View.VISIBLE);
					homePageAdatperHolder.mIVAgainPush.setVisibility(View.GONE);
					homePageAdatperHolder.mIVUserInfo.setVisibility(View.GONE);
				}else{
					homePageAdatperHolder.ediTepView.setVisibility(View.GONE);
					setViewBackGroud(homePageAdatperHolder.mIVActivityStatus, resource.getDrawable(R.drawable.icon_activity_will));
//					homePageAdatperHolder.mIVActivityStatus.setBackground(resource.getDrawable(R.drawable.icon_activity_will));
					homePageAdatperHolder.mCtStatus.setTextColor(resource.getColor(R.color.white));
					homePageAdatperHolder.mCtStatus.setText("即将开始");
					homePageAdatperHolder.mIVAgainPush.setVisibility(View.GONE);
					homePageAdatperHolder.mIVUserInfo.setVisibility(View.GONE);
				}
				
			}else if(activityStatus.equals("1")){//正在进行
				if(!isVisibleEditView){
					homePageAdatperHolder.mCtStatus.setTextColor(resource.getColor(R.color.white));
					homePageAdatperHolder.mCtStatus.setText("正在进行");
					setViewBackGroud(homePageAdatperHolder.mIVActivityStatus, resource.getDrawable(R.drawable.ico_gooding));
//					homePageAdatperHolder.mIVActivityStatus.setBackground(resource.getDrawable(R.drawable.ico_gooding));
					homePageAdatperHolder.mIVAgainPush.setVisibility(View.GONE);
					homePageAdatperHolder.mIVUserInfo.setVisibility(View.GONE);
					homePageAdatperHolder.ediTepView.setVisibility(View.GONE);
				}else{
					homePageAdatperHolder.ediTepView.setVisibility(View.VISIBLE);
				}
			
			}else if(activityStatus.equals("2")){//已经结束
				homePageAdatperHolder.mCtStatus.setText("已经结束");
				homePageAdatperHolder.mCtStatus.setTextColor(resource.getColor(R.color.black));
				setViewBackGroud(homePageAdatperHolder.mIVActivityStatus, resource.getDrawable(R.drawable.icon_activity_already));
//				homePageAdatperHolder.mIVActivityStatus.setBackground(resource.getDrawable(R.drawable.icon_activity_already));
				String myUserId = SharedPreferenceHelper
						.getLoginUserId(TootooPlusEApplication.getAppContext());
				String userid=homePageBean.getUserId();
				if(!TextUtils.isEmpty(myUserId)&&!TextUtils.isEmpty(userid)&&!myUserId.equals(userid)){
					//如果不是自己
					homePageAdatperHolder.mIVAgainPush.setVisibility(View.GONE);
					homePageAdatperHolder.mIVUserInfo.setVisibility(View.GONE);
				}else{//如果是自己的 、status=2活动已经结束的、  是否被点击过
					
					if(isVisibleEditView){
						homePageAdatperHolder.mIVActivityStatus.setVisibility(View.GONE);
						homePageAdatperHolder.ediTepView.setVisibility(View.VISIBLE);
						homePageAdatperHolder.mIVAgainPush.setVisibility(View.GONE);
						homePageAdatperHolder.mIVUserInfo.setVisibility(View.GONE);
					}else{
						homePageAdatperHolder.mIVActivityStatus.setVisibility(View.VISIBLE);
						//如果不显示编辑第几步界面那么判断线上线下
						homePageAdatperHolder.ediTepView.setVisibility(View.GONE);
						String type=homePageBean.getType();
						if(!TextUtils.isEmpty(type)){
							if(type.equals("1")){//0是线上  1 是线下 已经结束的线下活动，封面图不应该有收货地址，仅仅已经结束的线上活动有收货地址
								homePageAdatperHolder.mIVAgainPush.setVisibility(View.VISIBLE);
								homePageAdatperHolder.mIVUserInfo.setVisibility(View.VISIBLE);
								homePageAdatperHolder.mIVUserInfo.setImageResource(R.drawable.icon_lxfs);
								System.out.println("线下》》》》》》》》》"+homePageBean.getActivityName());
							}else if(type.equals("0")){
								homePageAdatperHolder.mIVAgainPush.setVisibility(View.VISIBLE);
								homePageAdatperHolder.mIVUserInfo.setVisibility(View.VISIBLE);
								homePageAdatperHolder.mIVUserInfo.setImageResource(R.drawable.home_icon_user_info);
								System.out.println("线上》》》》》》》》》"+homePageBean.getActivityName());
							}
						}
					}
					
					
					
					
				}
			
				
				
			}
			
		}
		
	}
	private void setViewBackGroud(View v,Drawable drawable){
		if (Build.VERSION.SDK_INT >= 16) {

		    v.setBackground(drawable);

		} else {

		    v.setBackgroundDrawable(drawable);
		}
		
		
	}
	/**
	 * 
	 * @Title: justActivity
	 * @Description: 判断是从哪个activity跳转过来的
	 * @param
	 * @return
	 * @throws
	 */
	private void justActivity(HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean) {
		boolean isMyActivityApplyActivity = activity instanceof MyActivityApplyActivity;
		if(isMyActivityApplyActivity){
			homePageAdatperHolder.mCollect.setVisibility(View.INVISIBLE);
			homePageAdatperHolder.mLine1.setVisibility(View.INVISIBLE);
			homePageAdatperHolder.mLine2.setVisibility(View.INVISIBLE);
			homePageAdatperHolder.mCollect1.setVisibility(View.GONE);
			homePageAdatperHolder.mIVActivityStatus.setVisibility(View.GONE);
			
		}

	}

	/*** 点击线上发货和收货地址列表 ****/
	private void setOnClickCity(
			final HomePageAdatperHolder homePageAdatperHolder,
			final HomePageBean homePageBean,final int position) {
		homePageAdatperHolder.mCTUpLineCity
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						showPopWindownCity(homePageAdatperHolder, homePageBean,position);

					}
				});

	}

	/**** 显示地址下拉列表 *****/
	protected void showPopWindownCity(
			HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean,int position) {
		HomePagerCityHelper homePagerCityHelper = new HomePagerCityHelper(
				homePageAdatperHolder, homePageBean,position);
	}

	/***
	 * 
	 * @Title: initLayoutBottomInfo
	 * @Description: 初始化线下活动地址，线上配送范围，收藏人数，参与人数与限制人数
	 */
	private void initLayoutBottomInfo(
			HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean,int position) {
		Drawable icoCity=TootooPlusEApplication.getAppContext().getResources().getDrawable(R.drawable.icon_location_city);
		String strCountColect = homePageBean.getCountCollent();
		String strPsarticipant = homePageBean.getCountParticipant();
		String strLocationOnLine = homePageBean.getLocation();// 发货地址
		String strPlace = homePageBean.getPlace();
		List<PostAresBean> strPostArsList = homePageBean.getPostAresList();
		if (!TextUtils.isEmpty(strLocationOnLine) && strPostArsList != null) {// 线上的时候
			String strFisrCityName = null;
			if (strPostArsList.size() > 0) {
				PostAresBean firstPostArs = strPostArsList.get(0);
				strFisrCityName = firstPostArs.getAreaName();
			}
			if (!TextUtils.isEmpty(strFisrCityName)) {
				homePageAdatperHolder.mCTUpLineCity.setText(strLocationOnLine
						+ "-" + strFisrCityName);
			} else {
				homePageAdatperHolder.mCTUpLineCity.setText(strLocationOnLine);
			}
			homePageAdatperHolder.mCTUpLineCity.setInnerFocus(true);
			homePageAdatperHolder.mCTUpLineCity.setCompoundDrawablesWithIntrinsicBounds(icoCity, null, null, null);
		}
		if (!TextUtils.isEmpty(strPlace)) {
			homePageAdatperHolder.mCTCity.setText(strPlace);
			homePageAdatperHolder.mCTCity.setInnerFocus(true);
			homePageAdatperHolder.mCTCity.setCompoundDrawablesWithIntrinsicBounds(icoCity, null, null, null);
		}
		if (!TextUtils.isEmpty(strCountColect)) {
			homePageAdatperHolder.mCTCollect.setText(strCountColect);
			homePageAdatperHolder.mCTCollectUp.setText(strCountColect);
		}
		if (!TextUtils.isEmpty(strPsarticipant)) {
			
			UIUtils.subStringToColor(homePageAdatperHolder.mCTpanticatiant, "/", strPsarticipant);
			UIUtils.subStringToColor(homePageAdatperHolder.mCTpanticatiantUP, "/", strPsarticipant);
//			homePageAdatperHolder.mCTpanticatiant.setText(strPsarticipant);
//			homePageAdatperHolder.mCTpanticatiantUP.setText(strPsarticipant);
		}

	}

	/************* 初始化用户信息的数据 ***************/
	private void initPhotoInfo(HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean) {
		String strPhotoUrl = homePageBean.getLogoUrl();
		String strUserName = homePageBean.getUserName();
		String strUserGrade = homePageBean.getUserGrade();
		String strRemainerTime = homePageBean.getRemainingTime();
		if (!TextUtils.isEmpty(strPhotoUrl)) {
			ImageLoader.getInstance().displayImage(strPhotoUrl,
					new ImageViewAware(homePageAdatperHolder.mIVPhoto),
					CommonUtil.OPTIONS_HEADPHOTO);
		}
		if (!TextUtils.isEmpty(strUserName)) {
			homePageAdatperHolder.mCTUserName.setText(strUserName);
		}
		if (!TextUtils.isEmpty(strUserGrade)) {
			CommonUtil.setUserGrade(homePageAdatperHolder.mCTUserName,
					strUserGrade, "right");
		}
		if (!TextUtils.isEmpty(strRemainerTime)) {
			homePageAdatperHolder.mCTRemainningtime.setVisibility(View.VISIBLE);
			homePageAdatperHolder.mCTRemainningtime.setText(strRemainerTime);
		}else{
			homePageAdatperHolder.mCTRemainningtime.setVisibility(View.GONE);
			homePageAdatperHolder.mCTRemainningtime.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}

	}

	/******* 判断是否是线上活动 ********/
	private void justUpOrOnLineActivity(
			HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean) {
		String type = homePageBean.getType();
		if (!TextUtils.isEmpty(type)) {
			if (type.equals("0")) {// 线上
				homePageAdatperHolder.LLUpLine.setVisibility(View.VISIBLE);
				homePageAdatperHolder.mLlDownLineBottom
						.setVisibility(View.GONE);
			} else if (type.equals("1")) {// 线下
				homePageAdatperHolder.LLUpLine.setVisibility(View.GONE);
				homePageAdatperHolder.mLlDownLineBottom
						.setVisibility(View.VISIBLE);
			}
		}

	}

	/**
	 * 
	 * @Title: initViewPageAdapter
	 * @Description: 创建左右活动的viewPager封面图
	 */
	private void initConvertViewPageAdapter(HomePageAdatperHolder holder,
			HomePageBean homePageBean,int position) {
		LayoutParams viewPagerPar = holder.mViewPager.getLayoutParams();
		if(viewPagerPar!=null){
			int width = CommonUtil.getWidth(TootooPlusEApplication.getAppContext());
			int height = width * 2 / 3;
			viewPagerPar.width=width;
			viewPagerPar.height=height;
			holder.mViewPager.setLayoutParams(viewPagerPar);
		}
		int i = 0;
		MyComvertViewPager mViewPager = new MyComvertViewPager(activity,
				currentPosition, homePageBean, holder,position);
		holder.mViewPager.setAdapter(mViewPager);
		mViewPager.notifyDataSetChanged();

	}

	public static class HomePageAdatperHolder {
		@ViewInject(R.id.ll_down_line_bottom)
		public LinearLayout mLlDownLineBottom;// 线下界面布局
		@ViewInject(R.id.ct_down_line_city)
		public MarqueeTextView mCTCity;// 组团线下活动地址 不可点击
		@ViewInject(R.id.ct_down_line_collect)
		public CheckedTextView mCTCollect;// 点击收藏人数 不可点击只显示
		@ViewInject(R.id.ct_down_line_pantictant)
		public CheckedTextView mCTpanticatiant;// 活动参与人数与限制人数
		@ViewInject(R.id.ll_up_line_bottom)
		public LinearLayout LLUpLine;// 线上界面布局
		@ViewInject(R.id.ct_up_line_city)
		public MarqueeTextView mCTUpLineCity;// 线上组团地址可配送的范围可点击
		@ViewInject(R.id.ct_up_line_collect)
		public CheckedTextView mCTCollectUp;// 点击的收藏人数
		@ViewInject(R.id.ct_up_line_pantictant)
		public MarqueeTextView mCTpanticatiantUP;// 活动的参与人数与限制人数
		@ViewInject(R.id.ct_home_username)
		public CheckedTextView mCTUserName;// 用户姓名
		@ViewInject(R.id.ct_time)
		public CheckedTextView mCTRemainningtime;// 剩余时间
		@ViewInject(R.id.viewpager_home)
		public ViewPager mViewPager;
		@ViewInject(R.id.iv_wish_photo)
		public ImageView mIVPhoto;// 头像
		@ViewInject(R.id.ll_activity_status)
		public LinearLayout mIVActivityStatus;// 活动状态标签0即将开始、1正在进行、2已经结束
		@ViewInject(R.id.view_line1)
		public View mLine1;
		@ViewInject(R.id.ll_collect)
		public LinearLayout mCollect;
		@ViewInject(R.id.view_line2)
		public View mLine2;
		@ViewInject(R.id.ll_collect1)
		public LinearLayout mCollect1;
		@ViewInject(R.id.ct_activity_status)
		public  CheckedTextView mCtStatus;
		@ViewInject(R.id.iv_again_push)
		public ImageView mIVAgainPush;//再次发布
		@ViewInject(R.id.iv_user_info)
		public ImageView mIVUserInfo;//跳转到收获地址
		
		@ViewInject(R.id.ll_edi_tep)
		public LinearLayout ediTepView;
		
		@ViewInject(R.id.ll_first)
		public LinearLayout firstTep;
		
		@ViewInject(R.id.ll_second)
		public LinearLayout secondTep;
		
		@ViewInject(R.id.ll_third)
		public LinearLayout thirdTep;
		
		@ViewInject(R.id.ll_edi_switch)
		public LinearLayout mLLSwitch;
		@ViewInject(R.id.ll_username_good)
		public LinearLayout mLLUsernameHeight;
		
		@ViewInject(R.id.iv_wish_photo_stroke)
		public ImageView mIvStroke;
		
		
		
		
		

	}
	/**
	 * 
	* @Title: setDefaultViewVisible 
	* @Description: 设置是否显示
	* @param  
	* @return   
	* @throws
	 */
private void setDefaultViewVisible(HomePageAdatperHolder viewHolder,int ifVisible){
	viewHolder.mLLUsernameHeight.setVisibility(ifVisible);

//	viewHolder.ediTepView.setVisibility(ifVisible);

	viewHolder.mIVAgainPush.setVisibility(ifVisible);

	viewHolder.mIVUserInfo.setVisibility(ifVisible);
	viewHolder.mIvStroke.setVisibility(ifVisible);
	
	viewHolder.mIVPhoto.setVisibility(ifVisible);
	viewHolder.mIVActivityStatus.setVisibility(ifVisible);
	viewHolder.LLUpLine.setVisibility(ifVisible);
	viewHolder.mLlDownLineBottom.setVisibility(ifVisible);
	
}
	class MyComvertViewPager extends PagerAdapter implements
			OnPageChangeListener {
		private int currentPosition;
		private HomePageBean homePageBean;
		private HomePageAdatperHolder holder;
		private Activity activity;
		private View firstTep;
		private View secondTep;
		private View thirdTep;
		private View ediTepView;
		private List<ConVertBean> convertListA;
		private int homePostion;

		public MyComvertViewPager(Activity activity, int currentPosition,
				HomePageBean homePageBean, HomePageAdatperHolder holder,int position) {
			this.currentPosition = currentPosition;
			this.activity = activity;
			this.holder = holder;
			homePostion=position;
			this.homePageBean = homePageBean;
			convertListA= homePageBean.getConvertBean();
			holder.mViewPager.setCurrentItem(currentPosition);
			holder.mViewPager.setOnPageChangeListener(this);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// 状态改变的时候调用 状态有三种 0的时候什么都没有改变 1的时候正在滑动 2的时候滑动完毕

		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View imageTypeTLayout = LayoutInflater.from(
					TootooPlusEApplication.getAppContext()).inflate(
					R.layout.item_viewpager_homepage, container, false);
			ImageView viewPagerImg = (ImageView) imageTypeTLayout
					.findViewById(R.id.iv_convert_image);
			CheckedTextView ctView = (CheckedTextView) imageTypeTLayout
					.findViewById(R.id.ct_storyName);
			
			

			justEditextViewTep(homePageBean, holder,convertListA,homePostion);
			
//			int convertWidth=CommonUtil.getWidth(TootooPlusEApplication.getAppContext());
//			int height=(int) (convertWidth*0.67);
			ImageView ivIconVideo = (ImageView) imageTypeTLayout
					.findViewById(R.id.iv_video_icon);
//			if (position == this.currentPosition) {
//				LogUtil.systemlogInfo("instantiateItem--currentPosition",
//						this.currentPosition);
//				holder.mViewPager.setCurrentItem(this.currentPosition);
//				// 1.获得每个数据的object
//				// getStoryData(itemObject);
//			}
			ConVertBean currentBean = convertListA.get(position);
			String imageUrl = currentBean.getCoverThumb();
//			String storyName = currentBean.getStoryName();
			String storyType = currentBean.getStoryType();
			String storyVideoUrl = currentBean.getStoryVideoUrl();
			if (!TextUtils.isEmpty(imageUrl)) {
				ImageLoader.getInstance().displayImage(imageUrl,
						new ImageViewAware(viewPagerImg),
						CommonUtil.OPTIONS_ALBUM);
			}
			String storyName=homePageBean.getActivityName();
			if (!TextUtils.isEmpty(storyName)) {
				ctView.setText(storyName);

			}
			if (!TextUtils.isEmpty(storyType)) {
				if (storyType.equals("3")) {// 视频
					ivIconVideo.setVisibility(View.VISIBLE);
				} else {
					ivIconVideo.setVisibility(View.GONE);
				}

			}
			if (!TextUtils.isEmpty(storyVideoUrl)) {

			}

			container.addView(imageTypeTLayout, 0);

			setImageViewOnClick(homePageBean, currentBean, viewPagerImg,
					position);
			return imageTypeTLayout;
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
			// 当页面在滑动的时候调用此方法，在滑动被停止之前会一直调用此方法
			// position表示当前界面及你点击滑动的界面的位置
			// arg1当前页面偏移的百分比
			// arg2当前 界面偏移的像素位置
		}

		@Override
		public void onPageSelected(int position) {

			if (position == convertListA.size() - 1) {// 如果是最后一条
				ComponentUtil.showToast(TootooPlusEApplication.getAppContext(),
						TootooPlusEApplication.getAppContext().getResources()
								.getString(R.string.already_last_page));
			}
			if (position == 0) {
				ComponentUtil.showToast(TootooPlusEApplication.getAppContext(),
						TootooPlusEApplication.getAppContext().getResources()
								.getString(R.string.already_first_page));
			}

			currentPosition = position;
		}

		@Override
		public int getCount() {
			int count = 0;
			if (convertListA != null) {
				count = convertListA.size();
			}
			return count;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void finishUpdate(ViewGroup container) {
			super.finishUpdate(container);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			holder.mViewPager.removeView((View) object);
		}

		/**
		 * 
		 * @Title: setImageViewOnClick
		 * @Description: 点击封面图
		 * @param
		 * @return
		 * @throws
		 */
		public void setImageViewOnClick(final HomePageBean homePageBean,
				ConVertBean currentBean, ImageView viewPagerImg,
				final int position) {
			viewPagerImg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String activityId = homePageBean.getActivityId();
					String userId = homePageBean.getUserId();
					Bundle bundle = new Bundle();
					Intent intent = new Intent(activity,
							ActivityDetailActivity.class);
					if(activity instanceof MyActivityApplyActivity){
						bundle.putString("activity", "MyActivityApplyActivity");
					}else{
						bundle.putString("activity", "");
					}
					bundle.putString(ConstantsTooTooEHelper.USERID, userId);
					bundle.putString(ConstantsTooTooEHelper.ACTIVITYID,
							activityId);
					bundle.putInt("currentPosition", position);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					activity.startActivity(intent);
				}
			});

		}

		

	}
/**
 * 
* @Title: justEditextViewTep 
* @Description: TODO
* @param  
* @return   
* @throws
 */
	public void justEditextViewTep(HomePageBean homePageBean, HomePageAdatperHolder holder,List<ConVertBean> convertList,int homePostion) {
		
		this.homePageBean = homePageBean;
		this.convertList=convertList;
		holder.firstTep.setOnClickListener(HomePageAdapter.this);
		holder.secondTep.setOnClickListener(HomePageAdapter.this);
		holder.thirdTep.setOnClickListener(HomePageAdapter.this);
		holder.firstTep.setTag(homePageBean);
		holder.secondTep.setTag(homePageBean);
		holder.thirdTep.setTag(homePageBean);
		homePageBean.setStoryArray(getStoryId(convertList,homePostion));
		
		
		
		
	}

/** 
* @Title: getStoryId 
* @Description: TODO
* @param  
* @return   
* @throws 
*/
private String getStoryId(List<ConVertBean>  convertList,int homepostion) {
	StringBuilder strBuilder=new StringBuilder();
	if(convertList!=null){
	
			for(int j = 0; j< convertList.size(); j++){
				String storyId = convertList.get(j).getStoryId();
				strBuilder.append(storyId).append(",");
			}
			
		if (strBuilder.substring(strBuilder.length() - 1).equals(",")) {
			strBuilder.deleteCharAt(strBuilder.length() - 1);
		}
	}
	
	return strBuilder.toString();
}

	/**
	 * 
	* @ClassName: MyPushAgain 
	* @Description: 再次发布
	* @author wuyulong
	* @date 2015-3-27 上午10:35:41 
	*
	 */
	class MyPushAgain implements View.OnClickListener{
		private HomePageAdatperHolder homePageAdatperHolder;
		private HomePageBean homePageBean;
		public MyPushAgain(HomePageAdatperHolder homePageAdatperHolder,HomePageBean homePageBean){
			this.homePageAdatperHolder=homePageAdatperHolder;
			this.homePageBean=homePageBean;
		}

		@Override
		public void onClick(View v) {
			switchPush(homePageAdatperHolder,homePageBean);
		}

		
	}

	/** 
	* @Title: switchPush 
	* @Description: 点击再次编辑的时候
	* @param  
	* @return   
	* @throws 
	*/
	protected void switchPush(HomePageAdatperHolder homePageAdatperHolder,HomePageBean homePageBean) {
		homePageBean.setActEdiVisible(true);
		homePageAdatperHolder.ediTepView.setVisibility(View.VISIBLE);
		homePageAdatperHolder.mIVAgainPush.setVisibility(View.GONE);
		homePageAdatperHolder.mIVUserInfo.setVisibility(View.GONE);
		homePageAdatperHolder.mIVActivityStatus.setVisibility(View.GONE);
	}
	/**
	 * 
	* @ClassName: MyUserInfo 
	* @Description: 跳转到收获地址
	* @author wuyulong
	* @date 2015-3-27 上午11:05:24 
	*
	 */
	class MyUserInfo implements View.OnClickListener{
		private HomePageAdatperHolder homePageAdatperHolder;
		private HomePageBean homePageBean;
		public MyUserInfo( HomePageAdatperHolder homePageAdatperHolder,HomePageBean homePageBean){
			this.homePageAdatperHolder=homePageAdatperHolder;
			this.homePageBean=homePageBean;
		}

		@Override
		public void onClick(View v) {
			String type=homePageBean.getType();
			if(!TextUtils.isEmpty(type)){
				if(type.equals("1")){//0是线上  1 是线下 已经结束的线下活动，封面图不应该有收货地址，仅仅已经结束的线上活动有收货地址
					Intent intent=new Intent(activity,MyConstanctActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra(ConstantsTooTooEHelper.ACTIVITYID, homePageBean.getActivityId());
					activity.startActivity(intent);
				}else if(type.equals("0")){
					Intent intent=new Intent(activity,AddressListActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra(ConstantsTooTooEHelper.ACTIVITYID, homePageBean.getActivityId());
					activity.startActivity(intent);
				}
			}
		
			
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
			bundle.putBoolean("isAgainPush", true);
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
			bundles.putBoolean("isAgainPush", true);
			bundles.putString(ConstantsTooTooEHelper.STORYID,homePageBean2.getStoryArray());
			intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intents.putExtra(ConstantsTooTooEHelper.BUNDLE, bundles);
			activity.startActivity(intents);

			break;
		case R.id.ll_third:
			HomePageBean homePageBean3=(HomePageBean) v.getTag();
			Intent mEdiThird=new Intent(activity,UpdateActThirdStepActivity.class);
			Bundle bundle1=new Bundle();
			bundle1.putBoolean("isAgainPush", true);
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