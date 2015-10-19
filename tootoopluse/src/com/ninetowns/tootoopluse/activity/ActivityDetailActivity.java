package com.ninetowns.tootoopluse.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.ActivityDetailFragmentAdapter;
import com.ninetowns.tootoopluse.adapter.ActivityDetailFragmentAdapter.OnItemPagelistener;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.ActivityDetailBean;
import com.ninetowns.tootoopluse.bean.GroupChatBean;
import com.ninetowns.tootoopluse.bean.GroupChatList;
import com.ninetowns.tootoopluse.bean.WishDetailBean;
import com.ninetowns.tootoopluse.fragment.ActivityDetailFragment;
import com.ninetowns.tootoopluse.fragment.CommentListFragment;
import com.ninetowns.tootoopluse.fragment.DownLineActivityFragment;
import com.ninetowns.tootoopluse.fragment.OnLineActivityFragment;
import com.ninetowns.tootoopluse.fragment.PhotoUserCollectCountFragment;
import com.ninetowns.tootoopluse.fragment.PhotoUserGroupListFragment;
import com.ninetowns.tootoopluse.fragment.PhotoUserGroupListFragment.OnListenerOfSwitch;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.parser.ActivityDetailParser;
import com.ninetowns.tootoopluse.parser.GetGroupChatParser;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;
import com.ninetowns.ui.widget.WrapRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 
 * @ClassName: ActivityDetailActivity
 * @Description: 活动详情
 * @author wuyulong
 * @date 2015-2-10 下午1:04:05
 * 
 */
public class ActivityDetailActivity extends BaseActivity implements
		OnClickListener ,OnItemPagelistener,OnListenerOfSwitch{
	@ViewInject(R.id.iv_activity_detail_return)
	private ImageView mIVReturn;// 返回
	@ViewInject(R.id.iv_activity_photo)
	private ImageView mIVUserPhoto;// 用户头像
	@ViewInject(R.id.ct_activity_date)
	private CheckedTextView mCTVDate;
	@ViewInject(R.id.iv_activity_detail_right)
	private ImageView mIVUserInfo;
	@ViewInject(R.id.act_det_viewpager)
	private ViewPager mActDesViewPager;
	@ViewInject(R.id.ll_activity_detail)
	private LinearLayout mLLDetailFotter;
	@ViewInject(R.id.activity_online)
	private FrameLayout mActivityInfoOnLine;// 线上
	@ViewInject(R.id.activity_down)
	private FrameLayout mActivityInfoDown;// 线下
	@ViewInject(R.id.tv_count)
	private TextView mTVCount;// 报名人数
	@ViewInject(R.id.rat_comment_bar)
	private WrapRatingBar mRatBar;// 点评指数
	@ViewInject(R.id.ll_comment_view)
	private LinearLayout mLLSkipToCommentView;// 跳转到
	@ViewInject(R.id.ll_group_view)
	private LinearLayout mLLSkipToPhoto;// 所报名人头像页
	@ViewInject(R.id.ll_comment_count)
	private LinearLayout mLLCommentCount;// 显示报名人数和点评指数的布局
	@ViewInject(R.id.activity_comment)
	public FrameLayout mFLCommentListView;// 评论列表界面容器
	@ViewInject(R.id.rl_comment_sub)
	private RelativeLayout mRlComment;// 指数根布局条目
	@ViewInject(R.id.rl_group_sub)
	private RelativeLayout mRlRegistration;// 报名根布局条目
	@ViewInject(R.id.v_line)
	private View line;
	@ViewInject(R.id.v_line2)
	private View line2;
	@ViewInject(R.id.photo_group_user)
	public FrameLayout mFLPhotoUser;

	private String activityId;
	private Bundle bundle;
	private List<ActivityDetailFragment> listFragment = new ArrayList<ActivityDetailFragment>();
	private String userId;
	public int currentPosition=0;
	private String activity;
	private ActivityDetailBean resultData;// 活动详情的数据结构
	private OnLineActivityFragment onLineActivityFragment;
	private DownLineActivityFragment downLineActivityFragment;
	private boolean isContactOfMine = false;// 活动与我有关的\
	private FrameLayout fl_photouser;
	private CheckedTextView mCTCollect;

	private CheckedTextView mCTChat;
	private CheckedTextView mCTGoToBuy;
	private ImageView mIVPhoto;
	private CheckedTextView mCTUserName;
	private CheckedTextView mCTDate;
	/**
	 * 用户userid
	 */
	private List<String> strArr = new ArrayList<String>();
	private String myUserId;
	private PhotoUserCollectCountFragment photoUserAllWishFragment;
	private String commentStoryId;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_detail_activity);
		ViewUtils.inject(this);
		getBundleType();
		mCTCollect = (CheckedTextView) findViewById(R.id.ct_collect);
		mCTChat = (CheckedTextView)findViewById(R.id.ct_chat);
		mCTGoToBuy = (CheckedTextView)findViewById(R.id.ct_gotobuy);
		fl_photouser = (FrameLayout)findViewById(R.id.photo_group_user);
		mIVPhoto = (ImageView)findViewById(R.id.iv_activity_photo);
		mCTUserName = (CheckedTextView)findViewById(R.id.ct_uername);
		mCTDate = (CheckedTextView)findViewById(R.id.ct_activity_date);
		mIVPhoto.setOnClickListener(this);
		mIVUserInfo.setOnClickListener(this);
		mCTChat.setOnClickListener(this);
		mCTGoToBuy.setOnClickListener(this);
		mCTCollect.setOnClickListener(this);
		mIVUserInfo.setImageResource(R.drawable.icon_information);
		myUserId = SharedPreferenceHelper.getLoginUserId(this);
		createData();
	}

	@OnClick(R.id.ll_group_view)
	public void skipToPhoto(View view) {
		if(mRlComment.getVisibility()==View.VISIBLE){//点击点评
			mRlComment.setVisibility(View.GONE);
			line2.setVisibility(View.GONE);
		}
		
		if(mRlRegistration.getVisibility()==View.VISIBLE){
			mRlRegistration.setVisibility(View.INVISIBLE);
			line2.setVisibility(View.INVISIBLE);
		}
		// 跳转到参加组团的
		FragmentTransaction beginTransation = getSupportFragmentManager()
				.beginTransaction();
		PhotoUserGroupListFragment photoUserGroup = new PhotoUserGroupListFragment(
				mFLPhotoUser, activityId, resultData.getGroupMembers(),
				beginTransation);
		photoUserGroup.setOnListenerOfSwitch(this);
		instantCommentListViewFragment(R.id.photo_group_user, photoUserGroup,
				beginTransation);
	}

	@OnClick(R.id.ll_comment_view)
	public void skipToComment(View view) {
		showCommentListDialog();
		
	}
	/** 
	* @Title: showComment 
	* @Description: 这种情况只有从点评点击活动名称的时候出现
	* @param  
	* @return   
	* @throws 
	*/
	private void showComment() {
		if(!TextUtils.isEmpty(activity)&&activity.equals(ConstantsTooTooEHelper.OPEN_COMMENT_LIST)){
			showCommentListDialog();
		}
	}
	/**
	 * 
	* @Title: setDefault 
	* @Description: 设置是否显示
	* @param  
	* @return   
	* @throws
	 */
	private void setDefault(int isVisible){
		//置顶
		mLLDetailFotter.setVisibility(isVisible);
		mIVUserInfo.setVisibility(isVisible);
		mRlRegistration.setVisibility(isVisible);
		line2.setVisibility(isVisible);
		line.setVisibility(isVisible);
		mRlComment.setVisibility(isVisible);
		mIVPhoto.setVisibility(isVisible);
	}
	/**
	 * 
	* @Title: justIsTopCoefficient 
	* @Description: 判断是否置顶
	* @param  
	* @return   
	* @throws
	 */
	private boolean justIsTopCoefficient(){
		boolean isTopCoefficient=false;
		if(resultData!=null){
			Integer topcoefficient = resultData.getTopCoefficient();
			if(topcoefficient!=null){
				if(topcoefficient>ConstantsTooTooEHelper.TopCoefficient){
					isTopCoefficient=true;
					//置顶
//					mLLDetailFotter.setVisibility(View.GONE);
//					mIVUserInfo.setVisibility(View.INVISIBLE);
//					mRlRegistration.setVisibility(View.GONE);
//					line2.setVisibility(View.GONE);
//					line.setVisibility(View.GONE);
//					mRlComment.setVisibility(View.GONE);
				}else{
//					普通
					isTopCoefficient=false;
				}
				
			}
		}
		return isTopCoefficient;
		
	}
	/** 
	* @Title: showCommentListDialog 
	* @Description: TODO
	* @param  
	* @return   
	* @throws 
	*/
	private void showCommentListDialog() {
		// 跳转到评论列表界面
		FragmentTransaction beginTransation = getSupportFragmentManager()
				.beginTransaction();
		if(resultData!=null){
			List<WishDetailBean> wishList = resultData.getListWish();
			if(wishList!=null&&wishList.size()>0){
				WishDetailBean currentItem = wishList.get(currentPosition);
				if(currentItem!=null){
					CommentListFragment commentListFragment = new CommentListFragment(
							activityId, beginTransation, mFLCommentListView,
							currentItem.getCountRecommend(),currentItem.getStoryId());
					instantCommentListViewFragment(R.id.activity_comment,
							commentListFragment, beginTransation);
				}
			}
		}
	}

	@OnClick(R.id.iv_activity_detail_return)
	public void backReturn(View view) {
		finish();
	}

	/***
	 * 
	 * @Title: getBundleType
	 * @Description:获取bundle值
	 * @param
	 * @return
	 * @throws
	 */
	private void getBundleType() {
		bundle = getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
		activity = bundle.getString("activity");
		if (!TextUtils.isEmpty(activity)) {
			if (activity.equals("MyActivityApplyActivity")) {
				mLLDetailFotter.setVisibility(View.GONE);
				mRlRegistration.setVisibility(View.GONE);
				line2.setVisibility(View.GONE);
				mRlComment.setVisibility(View.GONE);
			}else if(activity.equals(ConstantsTooTooEHelper.OPEN_COMMENT_LIST)){
				
				commentStoryId=bundle.getString(TootooeNetApiUrlHelper.STORYID);
			}
		}

		userId = bundle.getString(ConstantsTooTooEHelper.USERID);
		activityId = bundle.getString(TootooeNetApiUrlHelper.ACTIVITYID);
		currentPosition = bundle.getInt("currentPosition");

	}

	/**
	 * 
	 * @Title: createData
	 * @Description: 获取活动详情
	 * @param
	 * @return
	 * @throws
	 */
	private void createData() {
		if ((NetworkUtil.isNetworkAvaliable(this))) {
			// 显示进度
			showProgressDialog(ActivityDetailActivity.this);
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.ACTIVITYID, activityId);
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.USER_ID, myUserId);//userid
			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.ACTIVITY_DETAIL,
					requestParamsNet, new RequestCallBack<String>() {

						private String status;

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							closeProgressDialog(ActivityDetailActivity.this);
							String jsonStr = new String(responseInfo.result);
							ActivityDetailParser detailParser = new ActivityDetailParser(
									jsonStr);
							resultData = detailParser.getParseResult(jsonStr);
							List<WishDetailBean> listWishPage = resultData
									.getListWish();
							if (listWishPage != null && listWishPage.size() > 0) {
								for (int i = 0; i < listWishPage.size(); i++) {
									WishDetailBean viewpagerbean = listWishPage
											.get(i);
									String storyId = viewpagerbean.getStoryId();
									if(!TextUtils.isEmpty(commentStoryId)){
										if(commentStoryId.equals(storyId)){
											currentPosition=i;
										}
									}
									String userIdItem = viewpagerbean
											.getUserId();
									strArr.add(userIdItem);
									ActivityDetailFragment createSecActFragment = new ActivityDetailFragment(
											viewpagerbean, i, resultData);
									listFragment.add(createSecActFragment);
								}

							}
							
							ActivityDetailFragmentAdapter createActAdapter = new ActivityDetailFragmentAdapter(
									getSupportFragmentManager(), listFragment);
							mActDesViewPager.setAdapter(createActAdapter);
							mActDesViewPager.setOffscreenPageLimit(1);//不做缓存加载
							mActDesViewPager.setCurrentItem(currentPosition);
							mActDesViewPager
									.setOnPageChangeListener(createActAdapter);
							createActAdapter.setOnItemPageListener(ActivityDetailActivity.this);
							createActAdapter.notifyDataSetChanged();
							if(justIsTopCoefficient()){
								setDefault(View.GONE);
								//置顶
							}else{
								setDefault(View.VISIBLE);
								justIsMyContact();
								setTitleData(currentPosition);
								setCollectCount();
								setPageWhatCount();
								showComment();	
							}
							
						}

					

						@Override
						public void onFailure(HttpException error, String msg) {
							closeProgressDialog(ActivityDetailActivity.this);
							ComponentUtil
									.showToast(
											ActivityDetailActivity.this,
											getResources()
													.getString(
															R.string.errcode_network_response_timeout));
						}
					});

		} else {
			ComponentUtil.showToast(
					this,
					this.getResources().getString(
							R.string.errcode_network_response_timeout));
		}

	}

	/**
	 * 
	 * @Title: setCollectCount
	 * @Description: 设置收藏的值
	 * @param
	 * @return
	 * @throws
	 */
	private void setCollectCount() {
		if(resultData!=null){
			String collectCount = resultData.getCountCollent();
			if (!TextUtils.isEmpty(collectCount)) {
				mCTCollect.setText(collectCount);
			}
		}
	}
	/**
	 * @Title: justIsMyContact
	 * @Description: 判断活动是否与我有关 如果与我有关则显示，否则不显示,判断是否显示去组团等信息
	 * @param
	 * @return
	 * @throws
	 */
	private void justIsMyContact() {
		if (strArr.size() > 0) {
			for (int i = 0; i < strArr.size(); i++) {
				String userid = strArr.get(i);
				if (!TextUtils.isEmpty(myUserId)
						&& myUserId.equals(userid)) {
					isContactOfMine = true;
					break;

				}

			}
		}
		if (isContactOfMine) {
			mLLDetailFotter.setVisibility(View.VISIBLE);
			justIsVisibleOfCommentCount();
		} else {
			mRlComment.setVisibility(View.GONE);
			line.setVisibility(View.GONE);
			line2.setVisibility(View.GONE);
			mRlRegistration.setVisibility(View.GONE);
			mLLDetailFotter.setVisibility(View.GONE);
		}
		//
	}
	/**
	 * 
	 * @Title: justIsVisibleOfCommentCount
	 * @Description: 判断是否显示参与组团和体验点评
	 * @param
	 * @return
	 * @throws
	 */
	protected void justIsVisibleOfCommentCount() {
		
		if (resultData != null) {
			String strGroupMembers = resultData.getGroupMembers();// 组团人数
		
			if (!TextUtils.isEmpty(strGroupMembers)
					&& !strGroupMembers.equals("0")) {
				mRlRegistration.setVisibility(View.VISIBLE);
				line.setVisibility(View.VISIBLE);
				line2.setVisibility(View.VISIBLE);
				mTVCount.setText(strGroupMembers + " )");
			} else {
				mRlRegistration.setVisibility(View.GONE);
				line2.setVisibility(View.GONE);
			}
			List<WishDetailBean> strWishPage = resultData.getListWish();
			if(strWishPage!=null&&strWishPage.size()>0){
//				String strComment=strWishPage.get(currentPosition).getComment();
				String strCountRecommend = strWishPage.get(currentPosition).getCountRecommend();// 体验点评
				
				if (!TextUtils.isEmpty(strCountRecommend)
						&& !strCountRecommend.equals("0")) {//已点评显示
					mRlComment.setVisibility(View.VISIBLE);
					line.setVisibility(View.VISIBLE);
					line2.setVisibility(View.VISIBLE);
					mRatBar.setRating(Float.valueOf(strCountRecommend));
					
				} else {
					mRlComment.setVisibility(View.GONE);
					line2.setVisibility(View.GONE);
				}
			}
			
		} else {
			line2.setVisibility(View.GONE);
			mLLCommentCount.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_activity_detail_right:// 查看线下还是线上
			// 用两个fragment 碎片显示
			mFLCommentListView.setVisibility(View.GONE);
			mFLPhotoUser.setVisibility(View.GONE);
			justOnlineOrNot();
			break;

		case R.id.ct_collect:
			mFLCommentListView.setVisibility(View.GONE);
			mFLPhotoUser.setVisibility(View.GONE);
			showDialogFragmentPhotp();
			break;
		case R.id.ct_chat:
			// 跳转到聊天室
			skipToChatGroup();
			break;
		case R.id.ct_gotobuy:
			wantBuy();
			break;
	
		case R.id.iv_activity_photo:
			// 点击头像
			skipToUserView(v);
			break;
		}

	}
	public void skipToUserView(View v) {
		if(resultData!=null){
			List<WishDetailBean> wishPage = resultData.getListWish();
			if(wishPage!=null){
				String viewUserId=wishPage.get(currentPosition).getUserId();
				String myUserId = SharedPreferenceHelper
						.getLoginUserId(TootooPlusEApplication.getAppContext());
				if (!TextUtils.isEmpty(viewUserId) && !viewUserId.equals(myUserId)) {
					Intent intent = new Intent(TootooPlusEApplication.getAppContext(), PersonalHomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("userId", viewUserId);
					startActivity(intent);
				} else {
					Bundle bundle = new Bundle();
					Intent intent = new Intent(TootooPlusEApplication.getAppContext(),
							HomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					bundle.putInt("tab_index", 3);
					intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					startActivity(intent);

				}
			}
			
		}

	}
	/**
	 * 
	 * @Title: justOnlineOrNot
	 * @Description: 判断是否是线上type=0是线上活动，type=1是线下活动
	 * @param
	 * @return
	 * @throws
	 */
	private void justOnlineOrNot() {
		if (resultData != null) {
			String type = resultData.getType();
			if (!TextUtils.isEmpty(type)) {
				if (type.equals("0")) {// 线上活动
					mActivityInfoDown.setVisibility(View.GONE);
					mActivityInfoOnLine.setVisibility(View.VISIBLE);
					showOnLineActivity();
				} else if (type.equals("1")) {// 线下 活动
					mActivityInfoDown.setVisibility(View.VISIBLE);
					mActivityInfoOnLine.setVisibility(View.GONE);
					showDownLineActivity();
				} else {
					LogUtil.error("type类型错误", type);
				}

			}

		} else {
			LogUtil.error("详情数据", "错误");
		}

	}

	@SuppressWarnings("unused")
	@SuppressLint("Recycle")
	private void showOnLineActivity() {
		if (onLineActivityFragment != null) {
			instantFragment(R.id.activity_online, onLineActivityFragment);
		} else {
			onLineActivityFragment = new OnLineActivityFragment(resultData,
					mActivityInfoOnLine);
			instantFragment(R.id.activity_online, onLineActivityFragment);
		}
	}

	/**
	 * 
	 * @Title: showDownLineActivity
	 * @Description: 线下
	 * @param
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unused")
	@SuppressLint("Recycle")
	private void showDownLineActivity() {
		if (downLineActivityFragment != null) {
			instantFragment(R.id.activity_down, downLineActivityFragment);
		} else {
			downLineActivityFragment = new DownLineActivityFragment(resultData,
					mActivityInfoDown);
			instantFragment(R.id.activity_down, downLineActivityFragment);
		}
	}

	/**
	 * 
	 * @Title: instantFragment
	 * @Description: 实例化fragment
	 * @param
	 * @return
	 * @throws
	 */
	private void instantFragment(int id, Fragment fragment) {
		FragmentTransaction beginTransation = getSupportFragmentManager()
				.beginTransaction();
		Fragment fragmentId = getSupportFragmentManager().findFragmentById(id);
		if (fragmentId != null) {
			if (id == R.id.activity_online) {
				mActivityInfoOnLine.setVisibility(View.VISIBLE);
			} else {
				mActivityInfoDown.setVisibility(View.VISIBLE);
			}
				beginTransation.attach(fragmentId);
			
		} else {
				beginTransation.replace(id, fragment);
			
		}

		beginTransation.commitAllowingStateLoss();
	}

	/**
	 * 
	 * @Title: instantCommentListViewFragment
	 * @Description: 势力化评论列表的fragment
	 * @param
	 * @return
	 * @throws
	 */
	private void instantCommentListViewFragment(int id, Fragment fragment,
			FragmentTransaction beginTransation) {

		beginTransation.setCustomAnimations(R.anim.push_bottom_in,
				R.anim.push_bottom_out, R.anim.push_bottom_in,
				R.anim.push_bottom_out);
		Fragment fragmentId = getSupportFragmentManager().findFragmentById(id);
		if (fragmentId != null) {

			beginTransation.attach(fragmentId);
		} else {

			beginTransation.replace(id, fragment);
		}
		if (id == R.id.activity_comment) {// 如果是点评的fragment
			mFLCommentListView.setVisibility(View.VISIBLE);
		} else if (id == R.id.photo_group_user) {
			mFLPhotoUser.setVisibility(View.VISIBLE);
			mFLCommentListView.setVisibility(View.GONE);
		} else {
			mFLCommentListView.setVisibility(View.GONE);
		}
		beginTransation.commitAllowingStateLoss();

	}
	/**
	 * 
	 * @Title: setTitleData
	 * @Description: 设置标题栏信息
	 * @param
	 * @return
	 * @throws
	 */
	public void setTitleData(int currentPosition) {
		if(resultData!=null){
			List<WishDetailBean> listWishPage = resultData
					.getListWish();
			WishDetailBean currentItemPage = listWishPage.get(currentPosition);
			String logoUrl = currentItemPage.getLogoUrl();
			String strDate = resultData.getDateUpdate();
			String strName = currentItemPage.getUserName();
			String userGrade=currentItemPage.getUserGrade();
			if (!TextUtils.isEmpty(logoUrl)) {
				ImageLoader.getInstance().displayImage(logoUrl,
						new ImageViewAware(mIVPhoto), CommonUtil.OPTIONS_HEADPHOTO);
			}else{
				mIVPhoto.setImageResource(R.drawable.icon_default_photo);
			}
			if(!TextUtils.isEmpty(strName)){
				mCTUserName.setText(strName);
			}else{
				mCTUserName.setText("");
			}
			
			CommonUtil.setUserGrade(mCTUserName, userGrade, "right");
			if(!TextUtils.isEmpty(strDate)){
				String date=CommonUtil.friendly_time(strDate);
				mCTDate.setText(date);
			}
			
		
		}

		
	
	}
	/**
	 * 
	 * @Title: showDialogFragmentPhotp
	 * @Description: 跳转到用户头像
	 * @param
	 * @return
	 * @throws
	 */
	private void showDialogFragmentPhotp() {
		fl_photouser.setVisibility(View.VISIBLE);
		if (photoUserAllWishFragment == null) {
			photoUserAllWishFragment = new PhotoUserCollectCountFragment(
					fl_photouser, resultData.getActivityId());
			FragmentManager manager = this.getSupportFragmentManager();
			FragmentTransaction beginTransatiion = manager.beginTransaction();
			beginTransatiion.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			beginTransatiion.replace(R.id.photo_group_user, photoUserAllWishFragment);
			beginTransatiion.commit();
		}

	}
	/**
	 * 
	 * @Title: skipToChatGroup
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void skipToChatGroup() {

		if ((NetworkUtil.isNetworkAvaliable(TootooPlusEApplication
				.getAppContext()))) {
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			String userid = SharedPreferenceHelper
					.getLoginUserId(TootooPlusEApplication.getAppContext());
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.USER_ID, userid);
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.ACTIVITYID,
					resultData.getActivityId());
			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.CHAT_GROUP,
					requestParamsNet, new RequestCallBack<String>() {

						private String status;

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String jsonStr = new String(responseInfo.result);
							GetGroupChatParser groupChatParser = new GetGroupChatParser(
									jsonStr);
							List<GroupChatBean> groupChatList = groupChatParser
									.getParseResult(jsonStr);
							if(groupChatList!=null&&groupChatList.size()>0){
								Intent intent = new Intent(TootooPlusEApplication.getAppContext(),
										ChatActivity.class);
								Bundle bundle = new Bundle();
								GroupChatList groupChatListBean = new GroupChatList();
								groupChatListBean.setGroupChatBeans(groupChatList);
								groupChatListBean.setActivityName(resultData.getActivityName());
								groupChatListBean.setActivityId(resultData.getActivityId());
								intent.putExtra("groupchatlist", groupChatListBean);
								startActivity(intent);
							}else{

								int status = groupChatParser.getStatus();
								switch (status) {
								case 1:
									
									break;
								case 3101:
									LogUtil.error("参数缺失", "");
									break;
								case 3102:
									LogUtil.error("参数错误", "");
									break;
								case 3103:
									ComponentUtil.showToast(TootooPlusEApplication.getAppContext(), "会员增加失败");
									break;
								case 3105:
									ComponentUtil.showToast(TootooPlusEApplication.getAppContext(), "无法找到该组");
									break;

								default:
									break;
								}
								
							
							}
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							ComponentUtil.showToast(
									TootooPlusEApplication.getAppContext(),
									getResources()
											.getString(
													R.string.errcode_network_response_timeout));
						}
					});

		} else {
			ComponentUtil.showToast(
					TootooPlusEApplication.getAppContext(),
					this.getResources().getString(
							R.string.errcode_network_response_timeout));
		}

	}
	/**
	 * 
	 * @Title: wantBuy
	 * @Description: 去购买
	 * @param
	 * @return
	 * @throws
	 */
	private void wantBuy() {
		if(resultData!=null){
			List<WishDetailBean> listWishPage = resultData
					.getListWish();
			String shoppingUrl=listWishPage.get(currentPosition).getShoppingUrl();
			if (!TextUtils.isEmpty(shoppingUrl)) {// 可以shopp
				skipToWebView(shoppingUrl);
			} else {
				ComponentUtil.showToast(TootooPlusEApplication.getAppContext(), "暂无购买地址");
			}
		
			
		}
	
		

	}

	/**
	 * 
	 * @Title: skipToWebView
	 * @Description: 跳转到导购连接界面
	 * @param
	 * @return
	 * @throws
	 */
	private void skipToWebView(String shoppingUrl) {
		Intent intent = new Intent(this, GoBuyBrowserActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle bundle = new Bundle();
		bundle.putString("url", shoppingUrl);
		intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
		startActivity(intent);

	}

	@Override
	public void OnItemPageListener(int postion) {
		this.currentPosition=postion;
		setPageWhatCount();
		//设置title
		setTitleData(currentPosition);
		justIsMyContact();
		
	}
	/** 
	* @Title: setPageWhatCount 
	* @Description: 设置滑动界面后显示第几页
	* @param  
	* @return   
	* @throws 
	*/
	private void setPageWhatCount() {
		int pageCount=listFragment.size();
		if(pageCount>0){
			ActivityDetailFragment currentFragment = listFragment.get(currentPosition);
			int whatCount=currentPosition+1;
			if(currentFragment.mTvActivityWhatCount!=null){
				currentFragment.mTvActivityWhatCount.setText(""+whatCount);
			}
			if(currentFragment.mTvActivityPageCount!=null){
				currentFragment.mTvActivityPageCount.setText("/"+pageCount);
			}
			
		}
	}

	@Override
	public void onListenerSwitch(boolean isClose) {
		justIsVisibleOfCommentCount();
		
	}
}
