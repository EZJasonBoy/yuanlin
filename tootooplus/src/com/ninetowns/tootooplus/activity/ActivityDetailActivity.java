package com.ninetowns.tootooplus.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.adapter.ActivityDetailFragmentAdapter;
import com.ninetowns.tootooplus.adapter.ActivityDetailFragmentAdapter.OnItemPagePostion;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.ActivityDetailBean;
import com.ninetowns.tootooplus.bean.GroupChatBean;
import com.ninetowns.tootooplus.bean.GroupChatList;
import com.ninetowns.tootooplus.bean.UserLocBean;
import com.ninetowns.tootooplus.bean.WishDetailBean;
import com.ninetowns.tootooplus.fragment.ActivityDetailFragment;
import com.ninetowns.tootooplus.fragment.CommentListFragment;
import com.ninetowns.tootooplus.fragment.DownLineActivityFragment;
import com.ninetowns.tootooplus.fragment.FirstLookinfoDialog;
import com.ninetowns.tootooplus.fragment.FirstLookinfoDialog.OnDialogStatus;
import com.ninetowns.tootooplus.fragment.FirstViewPagerGuideDialog;
import com.ninetowns.tootooplus.fragment.FirstViewPagerGuideDialog.OnViewPagerDialogStatus;
import com.ninetowns.tootooplus.fragment.OnLineActivityFragment;
import com.ninetowns.tootooplus.fragment.PhotoUserCollectCountFragment;
import com.ninetowns.tootooplus.fragment.PhotoUserGroupListFragment;
import com.ninetowns.tootooplus.fragment.PhotoUserGroupListFragment.OnListenerOfSwitch;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.ActivityDetailParser;
import com.ninetowns.tootooplus.parser.GetGroupChatParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.ShareUtils;
import com.ninetowns.ui.Activity.BaseActivity;
import com.ninetowns.ui.widget.WrapRatingBar;
import com.ninetowns.ui.widget.arcmenu.FloatingActionMenu;
import com.ninetowns.ui.widget.dialog.GpsDialog;
import com.ninetowns.ui.widget.dialog.GpsDialog.OnDialogOpera;
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
@SuppressLint({ "ResourceAsColor", "NewApi" })
public class ActivityDetailActivity extends BaseActivity implements
		OnClickListener, ViewTreeObserver.OnScrollChangedListener,
		View.OnLayoutChangeListener, OnItemPagePostion,
		OnViewPagerDialogStatus, OnListenerOfSwitch, OnDialogStatus,PlatformActionListener {
	@ViewInject(R.id.iv_activity_detail_return)
	private ImageView mIVReturn;// 返回
	@ViewInject(R.id.iv_activity_photo)
	private ImageView mIVUserPhoto;// 用户头像
	@ViewInject(R.id.ct_activity_date)
	private CheckedTextView mCTVDate;
	@ViewInject(R.id.iv_activity_detail_right)
	private ImageView mIVShare;
	
	@ViewInject(R.id.iv_activity_detail_right_group)
	private ImageView mIVGoGroup;
	@ViewInject(R.id.act_det_viewpager)
	private ViewPager mActDesViewPager;
	@ViewInject(R.id.ll_activity_detail)
	private LinearLayout mLLDetailFotter;// 默认隐藏 gone
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
	public int currentPosition;
	private String activity;
	private ActivityDetailBean resultData;// 活动详情的数据结构
	private OnLineActivityFragment onLineActivityFragment;
	private DownLineActivityFragment downLineActivityFragment;

	@ViewInject(R.id.ct_collect)
	private CheckedTextView mCTCollect;// 收藏

	@ViewInject(R.id.ct_chat)
	private CheckedTextView mCTChat;// 聊天

	@ViewInject(R.id.ct_group_or_comment)
	private CheckedTextView mCTGroupComment;// 评论 组团
	@ViewInject(R.id.ll_buy_act_group1)
	private LinearLayout mLLGroup;

	@ViewInject(R.id.ct_goto_act_buy)
	private CheckedTextView mCTGoToOrBuy;

	@ViewInject(R.id.ll_buy_act_wish_baground)
	private LinearLayout mLLBuy;

	@ViewInject(R.id.ct_uername)
	private CheckedTextView mCTUserName;

	@ViewInject(R.id.ct_activity_date)
	private CheckedTextView mCTDate;

	@ViewInject(R.id.iv_activity_second)
	private ImageView mIVUserInfo;
	@ViewInject(R.id.rl_photo)
	private RelativeLayout mRLPhoto;// 隐藏 默认 哟invisible

	@ViewInject(R.id.act_detail_title)
	// 活动详情
	private LinearLayout mLLDetail;

	@ViewInject(R.id.act_face_toface_detail_title)
	private LinearLayout mLLFaceToFaceTitle;// 面对面的标题

	@ViewInject(R.id.iv_activity_face_to_detail_return)
	private ImageView mIVReturnFace;

	@ViewInject(R.id.ct_face_title)
	private CheckedTextView mCTFaceToFaceTitle;

	@ViewInject(R.id.rl_stroke_round)
	// 面对面几种样式
	private RelativeLayout rl_stroke_round;

	@ViewInject(R.id.ct_status)
	private CheckedTextView mCTFaceToFaceStatus;

	@ViewInject(R.id.ll_detail_fotter_along)
	private LinearLayout mLLFotter;// 面对面的dibulan

	@ViewInject(R.id.rl_facetoface_fotter)
	// 面对面几种样式
	private RelativeLayout mRlFaceToFaceFotter;

	private final int MENU_VIDEO = Menu.FIRST;

	private final int MENU_PHOTO = Menu.FIRST + 1;
	private final int MENU_CAMERA = MENU_PHOTO + 1;
	private final int MENU_INTERNET = MENU_CAMERA + 1;

	private boolean isDismiss;

	private ArrayList<FloatingActionMenu> menus;
	private FloatingActionMenu currentMenu;
	private FloatingActionMenu bottomMenu;
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	boolean isFirstLoc = true;// 是否首次定位
	/**
	 * 用户userid
	 */
	private List<String> strArr = new ArrayList<String>();
	private String myUserId;
	private PhotoUserCollectCountFragment photoUserAllWishFragment;
	private int clickStatus = -1;
	private PopupWindow popupWindow;
	private int screen_width;
	private int screen_height;
	private String localCity;
	private String commentStoryId;
	private GpsDialog gpsDialog;
	private String group_status = "";
	private String groupId;
	protected FirstLookinfoDialog dialog;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_detail_activity);
		ViewUtils.inject(this);
		popupWindow = new PopupWindow();
		// 屏幕宽度
		screen_width = CommonUtil.getWidth(this);
		// 屏幕高度
		screen_height = CommonUtil.getHeight(this);
		getBundleType();
		// 从网页跳转
		mCTCollect.setOnClickListener(this);
		mCTChat.setOnClickListener(this);
		mCTGoToOrBuy.setOnClickListener(this);
		mCTGroupComment.setOnClickListener(this);
		mIVUserPhoto.setOnClickListener(this);

		mIVUserInfo.setOnClickListener(this);
		mIVShare.setOnClickListener(this);
		mIVGoGroup.setOnClickListener(this);
		mIVShare.setImageResource(R.drawable.icon_share_btn);
		mIVUserInfo.setImageResource(R.drawable.icon_information);
		myUserId = SharedPreferenceHelper.getLoginUserId(this);
		// initArcMenu(arcMenu, ITEM_DRAWABLES);
		createData();
	}

	private void setBaiDuGps() {
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		// 设置gps优先
		option.setPriority(LocationClientOption.GpsFirst);

		option.setPoiNumber(10);
		option.disableCache(true);
		mLocClient.setLocOption(option);

		mLocClient.start();
	}

	/**
	 * 
	 * @Title: showViewPager
	 * @Description: 如果第一次显示图片引导
	 * @param
	 * @return
	 * @throws
	 */
	private void showViewPager(boolean isZhiding) {
		boolean isFirst = SharedPreferenceHelper
				.getFirstGuideCreateViewPager(this);
		if (isFirst) {// 如果第一次
			if (listFragment.size() > 1) {// 如果大于一条数据
				FirstViewPagerGuideDialog dialog = (FirstViewPagerGuideDialog) CommonUtil
						.showFirstGuideDialog(this,
								ConstantsTooTooEHelper.FIRST_GUIDE_VIEWPAGER);
				dialog.setOnViewPagerDialogStatus(this, isZhiding);
			} else {
				if (isZhiding) {
					return;
				} else {
					justShowGotoGroupOrWrite();
				}
			}

		} else {
			if (isZhiding) {
				return;
			} else {
				justShowGotoGroupOrWrite();
			}

			// 判断是否置顶的 然后再判断是否显示
			// justShowBigSmallGuide();
		}

	}

	/**
	 * @Title: justShowGotoGroupOrWrite
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void justShowGotoGroupOrWrite() {
		List<WishDetailBean> listWishPage = resultData.getListWish();
		WishDetailBean currentItem = listWishPage.get(currentPosition);
		String comment = currentItem.getComment();
		String activityStatus = resultData.getActivityStatus();
		if (!TextUtils.isEmpty(activityStatus)) {
			if (activityStatus.equals("1")) {// "即将开始"

			} else if (activityStatus.equals("2")) {// "组团报名"
				justShowBigSmallGuide();
			} else if (activityStatus.equals("3")) {// "已报名"

			} else if (activityStatus.equals("4")) {
				if (!TextUtils.isEmpty(comment)) {
					// 是否评论过 0已结束（未参与活动不能点评）1未评论 2已评论
					if (comment.equals("0")) {// "已结束"

					} else if (comment.equals("1")) {// "去评论
						// ==2 去点评

						boolean isFirst1 = SharedPreferenceHelper
								.getFirstGuideWriteComment(this);
						if (isFirst1) {
							CommonUtil
									.showFirstGuideDialog(
											this,
											ConstantsTooTooEHelper.FIRST_GUIDE_WRITE_COMMENT);
						}

					} else if (comment.equals("2")) {// "已评论"

					}
				}

			} else if (activityStatus.equals("5")) {// "组团报名"

			} else {
				LogUtil.error("activityStatus", "获取失败");
			}

		} else {

		}
	}

	public void showSelectPopup() {

		if (!popupWindow.isShowing()) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.create_wish_bottom_popup_layout, null);
			popupWindow.setContentView(view);
			// 窗口的宽带和高度根据情况定义
			popupWindow.setWidth(screen_width * 9 / 10);
			popupWindow.setHeight(screen_height / 3);

			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable(0));

			// 窗口进入和退出的效果
			popupWindow.setAnimationStyle(R.style.win_ani_top_bottom);

			popupWindow.showAtLocation(
					LayoutInflater.from(this).inflate(R.layout.home_activity,
							null),
					// 位置可以按要求定义
					Gravity.NO_GRAVITY, screen_width / 20,
					screen_height * 4 / 5 - 15);

			LinearLayout rec_bot_popup_total_layout = (LinearLayout) view
					.findViewById(R.id.rec_bot_popup_total_layout);
			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) rec_bot_popup_total_layout
					.getLayoutParams();
			linearParams.height = screen_height / 3;
			rec_bot_popup_total_layout.setLayoutParams(linearParams);

			LinearLayout llCreateWishPhoto = (LinearLayout) view
					.findViewById(R.id.ll_create_wish_photo);
			llCreateWishPhoto.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 拍照
					cameraWish();
				}

			});

			LinearLayout llCreateWishVideo = (LinearLayout) view
					.findViewById(R.id.ll_create_wish_video);
			llCreateWishVideo.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// 拍视频
					createVideo();
					popupWindow.dismiss();

				}

			});

			LinearLayout llCreateWishAlbum = (LinearLayout) view
					.findViewById(R.id.ll_create_wish_album);

			llCreateWishAlbum.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// 相册
					photoWish();

				}

			});

			LinearLayout llCreateWishInternet = (LinearLayout) view
					.findViewById(R.id.ll_create_wish_internet);
			llCreateWishInternet.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					intetnetWish();
				}

			});
			LinearLayout llCreateWishCancel = (LinearLayout) view
					.findViewById(R.id.ll_create_wish_cancel);
			llCreateWishCancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// 取消
					popupWindow.dismiss();
				}
			});
		}
	}

	/**
	 * @Title: cameraWish
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void cameraWish() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(ImageUtil.getTempPhotoPath()));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra(ImageColumns.ORIENTATION, 0);
		intent.putExtra("storyType", "2");// 封面图
		startActivityForResult(intent, 1);
		popupWindow.dismiss();
	}

	/**
	 * @Title: photoWish
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void photoWish() {
		Intent intent3 = new Intent(this, CoverRecommendSelectActivity.class);// 封面图的activity
		Bundle bundle = new Bundle();
		justIsCommentOrWish(bundle);
		ConstantsTooTooEHelper.putView(
				ConstantsTooTooEHelper.isConvertRecommendView, bundle);
		intent3.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
		intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent3);
		popupWindow.dismiss();
	}

	/**
	 * @Title: createVideo
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void createVideo() {
		Intent intent1 = new Intent(this, RecordVideoActivity.class);
		Bundle bundle = new Bundle();
		justIsCommentOrWish(bundle);
		ConstantsTooTooEHelper.putView(
				ConstantsTooTooEHelper.isConvertRecommendView, bundle);
		intent1.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
		intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent1);
	}

	/**
	 * @Title: intetnetWish
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void intetnetWish() {
		// 网址
		Intent intent4 = new Intent(this, InternetBrowserActivity.class);// 封面图的activity
		Bundle bundle = new Bundle();
		justIsCommentOrWish(bundle);
		ConstantsTooTooEHelper.putView(
				ConstantsTooTooEHelper.isConvertRecommendView, bundle);
		intent4.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
		intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent4);
		popupWindow.dismiss();
	}

	@OnClick(R.id.rl_facetoface_fotter)
	public void skipFaceToFace(View v) {// 确认加入
		if (isSureGo) {

			showProgressDialog(ActivityDetailActivity.this);
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.ACT_DETAILS_CREATE_GROUP_ACT_ID,
					resultData.getActivityId());
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.USERID, SharedPreferenceHelper
							.getLoginUserId(ActivityDetailActivity.this));
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_GROUPID,
					groupId);
			requestParamsNet.addQueryStringParameter("Latitude",
					String.valueOf(latitude));// 纬度
			requestParamsNet.addQueryStringParameter("Longitude",
					String.valueOf(longitude));// 经度

			CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.SURE_GO,
					requestParamsNet, new RequestCallBack<String>() {
//				Status=0  Info="您所在位置与团长所发起组团位置不在同一城市，建议与团长同城组团！"
//						  Info="地理位置错误！"
//						  Info="您所在位置与团长发起组团位置距离已经超出6公里，建议与团长在同一写字楼、小区、学校等场所参与组团。"
//						  Info="活动错误！"
//
//					Status=2  Info="已参团" //以及参团并且是团员身份
//
//					Status=7  Info="已参团" //已经成团
//
//					Status=8  Info="参团人数已满"
//
//					Status=1  //增加会员成功
//
//					Status=3  Info="会员加入失败"
//					Status=5  Info="会员加入失败"	
//					Status=6  Info="已提交"  //已提交不能加入会员
//

						private String info;

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							closeProgressDialog(ActivityDetailActivity.this);
							String jsonStr = new String(responseInfo.result);
							try {
								JSONObject json = new JSONObject(jsonStr);
								String add_status = "";
								if (json.has("Status")) {
									add_status = json.getString("Status");
								}
								if (json.has("Info")) {
									info = json.getString("Info");
								}
								if (add_status.equals("1")) {
									Intent member_intent = new Intent(
											ActivityDetailActivity.this,
											JoinMemberActivity.class);
									if (!TextUtils.isEmpty(groupId)) {
										member_intent.putExtra("group_id",
												groupId);
									} else {
										member_intent.putExtra("group_id", "");
									}

									member_intent.putExtra("act_id",
											resultData.getActivityId());
									member_intent.putExtra("act_name",
											resultData.getActivityName());
									// "0"为未报名; "1"为已报名，待审核;
									// "-1"已报名，但审核失败;
									// "2"为被选中
									member_intent.putExtra("group_status",
											group_status);
									member_intent.putExtra("act_type",
											resultData.getType());
									member_intent.putExtra(
											"group_countParticipant", "");
									startActivity(member_intent);
								} else  {//
									if (!TextUtils.isEmpty(info))
										ComponentUtil.showToast(
												ActivityDetailActivity.this,
												info);

								}

							} catch (Exception e) {
								e.printStackTrace();
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

		}

	}

	@OnClick(R.id.rl_group_sub)
	public void skipToPhoto(View view) {
		if (mRlComment.getVisibility() == View.VISIBLE) {//
			mRlComment.setVisibility(View.GONE);
			line2.setVisibility(View.GONE);
		}

		if (mRlRegistration.getVisibility() == View.VISIBLE) {
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
	 * @Title: showCommentListDialog
	 * @Description: 显示评论列表界面
	 * @param
	 * @return
	 * @throws
	 */
	private void showCommentListDialog() {
		// 跳转到评论列表界面
		FragmentTransaction beginTransation = getSupportFragmentManager()
				.beginTransaction();
		if (resultData != null) {
			List<WishDetailBean> wishList = resultData.getListWish();
			if (wishList != null && wishList.size() > 0) {
				WishDetailBean currentItem = wishList.get(currentPosition);
				System.out.println("点击传递的storyid" + currentItem.getStoryId());
				if (currentItem != null) {
					CommentListFragment commentListFragment = new CommentListFragment(
							activityId, beginTransation, mFLCommentListView,
							currentItem.getCountRecommend(),
							currentItem.getStoryId());
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

	@OnClick(R.id.iv_activity_face_to_detail_return)
	public void backReturnFace(View view) {
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
		if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
			// 通过网页打开该页面
			Intent intent = getIntent();
			// Manifest中配置的scheme
			String scheme = intent.getScheme();
			Uri uri = intent.getData();
			activityId = uri.getQueryParameter("ActivityId");
			currentPosition = 0;
		} else {

			bundle = getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
			activity = bundle.getString("activity");
			if (!TextUtils.isEmpty(activity)) {
				if (activity.equals("MyActivityApplyActivity")) {
					mLLDetailFotter.setVisibility(View.GONE);
				} else if (activity
						.equals(ConstantsTooTooEHelper.OPEN_COMMENT_LIST)) {
					commentStoryId = bundle
							.getString(TootooeNetApiUrlHelper.STORYID);
				} else if (activity.equals("FaceToFaceGroupEnterActivity")) {
					group_status = bundle.getString("group_status");
					groupId = bundle.getString("groupid");
				}
			}

			activityId = bundle.getString(TootooeNetApiUrlHelper.ACTIVITYID);
			currentPosition = bundle.getInt("currentPosition");
		}
	}

	private void setDefaultView(int isVisible) {
		// 全不显示
		// 置顶
		mLLDetailFotter.setVisibility(isVisible);
		mRLPhoto.setVisibility(isVisible);

		mIVUserPhoto.setVisibility(isVisible);
		mIVUserInfo.setVisibility(isVisible);
		mIVShare.setVisibility(isVisible);
		mIVGoGroup.setVisibility(isVisible);
		mRlRegistration.setVisibility(isVisible);
		line2.setVisibility(isVisible);
		line.setVisibility(isVisible);
		mRlComment.setVisibility(isVisible);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		
		setGps();
	}
@Override
protected void onResume() {
	super.onResume();
	if(!(!TextUtils.isEmpty(activity)&&activity.equals("FaceToFaceGroupEnterActivity"))){
		justButtonStatus();
	}
	
}
	/** 
	* @Title: justIsFirstGoGroup 
	* @Description: TODO
	* @param  
	* @return   
	* @throws 
	*/
	private void justIsFirstGoGroup() {
		boolean isFirst = SharedPreferenceHelper
				.getFirstGuideLookNumberGroup(ActivityDetailActivity.this);
			boolean isFirstGroup= SharedPreferenceHelper.getFirstGroup(ActivityDetailActivity.this);
			if(isFirstGroup){
				//如果是第一次
				if (isFirst) {
					CommonUtil
							.showFirstGuideDialog(
									ActivityDetailActivity.this,
									ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_ALREADY_GROUP);
				}
			}
	}

	private void setGps() {
		CommonUtil.openGPS(this);// 强制打开gps
		// boolean isGpsOpen = CommonUtil.isOPen(this);
		setBaiDuGps();
		// if (isGpsOpen) {
		// // 百度定位存城市
		// if()
		// setBaiDuGps();
		// } else {
		// showDialogGps();
		//
		// }
	}

	/**
	 * @Title: showDialogGps
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void showDialogGps() {
		// 提示
		if (gpsDialog == null) {
			gpsDialog = new GpsDialog(this);
			gpsDialog.setOnDialogOpera(new OnDialogOpera() {

				@Override
				public void skipOpera(GpsDialog gpsDialog) {

					Intent intent = null;
					// 先判断当前系统版本
					if (android.os.Build.VERSION.SDK_INT > 10) { // 3.0以上
						intent = new Intent(
								android.provider.Settings.ACTION_SETTINGS);
					} else {
						intent = new Intent();
						intent.setClassName("com.android.settings",
								"com.android.settings.Settings");
					}
					startActivity(intent);

				}

				@Override
				public void cancelDialog(GpsDialog gpsDialog) {
					finish();

				}

				@Override
				public void skipHelp(GpsDialog gpsDialog) {
					Intent intent = new Intent(ActivityDetailActivity.this,
							HelpActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);

				}
			});
			gpsDialog.show();
		} else {
			if (!gpsDialog.isShowing()) {
				gpsDialog.show();
			}
		}
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
			setGps();
		} else {
			ComponentUtil.showToast(
					this,
					this.getResources().getString(
							R.string.errcode_network_response_timeout));
		}

	}

	/**
	 * @Title: getNetData
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void getNetData() {
		// 显示进度
		showProgressDialog(ActivityDetailActivity.this);
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.ACTIVITYID, activityId);
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.USER_ID, myUserId);
		UserLocBean local = SharedPreferenceHelper
				.getUserLocation(ActivityDetailActivity.this);
		if (local != null) {
			localCity = local.getLocCity();
		}
		if (!TextUtils.isEmpty(localCity)) {
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.CITY_NAME, localCity);
		} else {
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.CITY_NAME, "");
		}

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
								if (!TextUtils.isEmpty(commentStoryId)) {
									if (commentStoryId.equals(storyId)) {
										currentPosition = i;
									}
								}
								// commentStoryId
								String userIdItem = viewpagerbean.getUserId();
								strArr.add(userIdItem);
								ActivityDetailFragment createSecActFragment = new ActivityDetailFragment(
										viewpagerbean, i, resultData);
								listFragment.add(createSecActFragment);
							}

						}
						if (resultData == null) {
							return;
						}
						ActivityDetailFragmentAdapter createActAdapter = new ActivityDetailFragmentAdapter(
								getSupportFragmentManager(), listFragment,
								resultData, currentPosition);
						createActAdapter
								.setOnPagePostion(ActivityDetailActivity.this);
						mActDesViewPager.setAdapter(createActAdapter);
						mActDesViewPager.setCurrentItem(currentPosition);
						mActDesViewPager
								.setOnPageChangeListener(createActAdapter);
						createActAdapter.notifyDataSetChanged();
						if (!TextUtils.isEmpty(activity)&&activity.equals("FaceToFaceGroupEnterActivity")) {
							mLLFaceToFaceTitle.setVisibility(View.GONE);
							mLLDetail.setVisibility(View.VISIBLE);
							mLLFotter.setVisibility(View.GONE);
							mRlFaceToFaceFotter.setVisibility(View.VISIBLE);
							mIVReturnFace.setVisibility(View.GONE);
							mIVShare.setVisibility(View.GONE);
							
							mRLPhoto.setVisibility(View.VISIBLE);
							justFaceToFaceButtonStatus();
							mIVUserInfo.setVisibility(View.VISIBLE);
							
							mIVUserPhoto.setVisibility(View.VISIBLE);
							setTitleData(currentPosition);
						} else {
							mLLFaceToFaceTitle.setVisibility(View.GONE);
							mLLDetail.setVisibility(View.VISIBLE);
							mLLFotter.setVisibility(View.VISIBLE);
							mRlFaceToFaceFotter.setVisibility(View.GONE);
							boolean isFirst1 = SharedPreferenceHelper
									.getFirstGuideLookInfo(ActivityDetailActivity.this);
							if (isFirst1) {

								if (isZhiDing()) {
									showViewPager(true);
									// 全不显示
									// 置顶
									setDefaultView(View.GONE);

								} else {
								
									
									setDefaultView(View.VISIBLE);
									 dialog = (FirstLookinfoDialog) CommonUtil
											.showFirstGuideDialog(
													ActivityDetailActivity.this,
													ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_INFO);
									dialog.setOnDialogStatus(ActivityDetailActivity.this);
									setTitleData(currentPosition);
									justContent2();
								}
							} else {
								justContent();
							}
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
	}

	/**
	 * @Title: showComment
	 * @Description: 这种情况只有从点评点击活动名称的时候出现
	 * @param
	 * @return
	 * @throws
	 */
	private void showComment() {
		if (!TextUtils.isEmpty(activity)
				&& activity.equals(ConstantsTooTooEHelper.OPEN_COMMENT_LIST)) {
			showCommentListDialog();
		}
	}
@Override
public void onBackPressed() {
	super.onBackPressed();
//	boolean isFirst1 = SharedPreferenceHelper
//			.getFirstGuideLookInfo(ActivityDetailActivity.this);
//	if(dialog!=null)
}
	/**
	 * @Title: justContent
	 * @Description: 判断是否第一次显示i信息之后继续判断的逻辑
	 * @param
	 * @return
	 * @throws
	 */
	private void justContent() {
		if (isZhiDing()) {
			showViewPager(true);
			// 全不显示
			// 置顶
			setDefaultView(View.GONE);

		} else {
			setDefaultView(View.VISIBLE);
			
			justButtonStatus();
			justIsVisibleOfCommentCount();
			setTitleData(currentPosition);
			setCollectCount();
			justCollected();
			justShopping();
			setPageWhatCount();
			showComment();
			showViewPager(false);
//			boolean showShare = SharedPreferenceHelper.getFirstShowShare(this);
//			if(mIVShare.getVisibility()==View.VISIBLE){
//				if(showShare){
//					CommonUtil.showFirstGuideDialog(this, ConstantsTooTooEHelper.FIRST_GUIDE_SHARE);
//				}
//			}
			
		}
	}
	private void justContent2() {
		if (isZhiDing()) {
			// 全不显示
			// 置顶
			setDefaultView(View.GONE);

		} else {
			setDefaultView(View.VISIBLE);

			justButtonStatus();
			justIsVisibleOfCommentCount();
			setTitleData(currentPosition);
			setCollectCount();
			justCollected();
			justShopping();
			setPageWhatCount();
			showComment();
		/*	boolean showShare = SharedPreferenceHelper.getFirstShowShare(this);
			if(mIVShare.getVisibility()==View.VISIBLE){
				if(showShare){
					CommonUtil.showFirstGuideDialog(this, ConstantsTooTooEHelper.FIRST_GUIDE_SHARE);
				}
			}*/
		}
	}

	/**
	 * 
	 * @Title: isZhiDing
	 * @Description: 判断是否置顶
	 * @param
	 * @return
	 * @throws
	 */
	private boolean isZhiDing() {
		boolean isZhiDing = false;
		Integer topcoefficient = resultData.getTopCoefficient();
		if (topcoefficient != null) {
			if (topcoefficient > ConstantsTooTooEHelper.TopCoefficient) {
				// 置顶
				isZhiDing = true;
			} else {
				isZhiDing = false;
			}

		}
		return isZhiDing;
	}

	/**
	 * @Title: setPageWhatCount
	 * @Description: 设置滑动界面后显示第几页
	 * @param
	 * @return
	 * @throws
	 */
	private void setPageWhatCount() {
		int pageCount = listFragment.size();
		if (pageCount > 0) {
			ActivityDetailFragment currentFragment = listFragment
					.get(currentPosition);
			int whatCount = currentPosition + 1;
			if (currentFragment.mTvActivityWhatCount != null) {
				currentFragment.mTvActivityWhatCount.setText("" + whatCount);
			}
			if (currentFragment.mTvActivityPageCount != null) {
				currentFragment.mTvActivityPageCount.setText("/" + pageCount);
			}

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
		if (resultData != null) {
			String collectCount = resultData.getCountCollent();
			if (!TextUtils.isEmpty(collectCount)) {
				mCTCollect.setText(collectCount);
			}
		}
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
			if (strWishPage != null && strWishPage.size() > 0) {
				// String
				// strComment=strWishPage.get(currentPosition).getComment();
				String strCountRecommend = strWishPage.get(currentPosition)
						.getCountRecommend();// 体验点评

				if (!TextUtils.isEmpty(strCountRecommend)
						&& !strCountRecommend.equals("0")) {// 已点评显示
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
		case R.id.iv_activity_detail_right:// 分享
			if (resultData != null) {
				String actName = resultData.getActivityName();
				if (!TextUtils.isEmpty(actName)) {
					List<WishDetailBean> wishList = resultData.getListWish();
					if (wishList != null && wishList.size() > 0) {
						WishDetailBean currentItem = wishList.get(0);
						String imageUrl = currentItem.getCoverThumb();
						ShareUtils.showShareActivity(this, actName,
								resultData.getActivityId(), imageUrl);
					}

				} else {
					// ShareUtils.showShareActivity(this,"这个活动",resultData.getActivityId());
				}

			}

			break;
		case R.id.iv_activity_second:// 查看线下还是线上
			// 用两个fragment 碎片显示
			mFLCommentListView.setVisibility(View.GONE);
			mFLPhotoUser.setVisibility(View.GONE);
			justOnlineOrNot();
			break;

		case R.id.ct_collect:
			onClickTheCollect();

			break;
		case R.id.ct_chat:
			// 跳转到聊天室
			skipToChatGroup();
			break;
		case R.id.ct_goto_act_buy:
			wantBuy();
			break;
		case R.id.iv_activity_photo:
			// 点击头像
			skipToUserView(v);
			break;
		case R.id.ct_group_or_comment:
			// 判断显示拿些按钮
			justClickStatus();

			break;
		case R.id.iv_activity_detail_right_group://面对面组团
			Intent intent =new Intent(this,FaceToFaceGroupEnterActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		}

	}

	/**
	 * 
	 * @Title: justShopping
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void justShopping() {
		if (resultData != null) {
			List<WishDetailBean> listWishPage = resultData.getListWish();
			String shoppingUrl = listWishPage.get(currentPosition)
					.getShoppingUrl();
			if (!TextUtils.isEmpty(shoppingUrl)) {// 可以shopp
				
				mCTGoToOrBuy.setCompoundDrawablesWithIntrinsicBounds(null,
						getResources().getDrawable(R.drawable.icon_buy_yes),
						null, null);
				mCTGoToOrBuy.setTextColor(getResources().getColor(
						R.color.btn_gray_color));
				boolean isGoBuyNotice = SharedPreferenceHelper.getFirstGuideGoBuyAct(this);
				if(isGoBuyNotice){
					DialogFragment isShopping = CommonUtil.showFirstGuideDialog(this, ConstantsTooTooEHelper.First_GUIDE_GO_BUY_ACT);
				}
				

				// mCTGoToOrBuy.setBackground(getResources().getDrawable(
				// R.drawable.icon_buy_yes));
			} else {
				mCTGoToOrBuy.setCompoundDrawablesWithIntrinsicBounds(null,
						getResources().getDrawable(R.drawable.icon_buy_no),
						null, null);
				mCTGoToOrBuy.setTextColor(getResources().getColor(
						R.color.guide_line));
				// mCTGoToOrBuy.setBackground(getResources().getDrawable(
				// R.drawable.icon_buy_no));
			}
			// if (!TextUtils.isEmpty(shoppingUrl)) {// 可以shopp
			//
			// mLLBuy.setBackgroundColor(R.color.white);
			// } else {
			// mLLBuy.setBackgroundColor(R.color.gray);
			// }
		}
	}

	private void wantBuy() {
		if (currentPosition != -1) {
			if (resultData != null) {
				List<WishDetailBean> listWishPage = resultData.getListWish();
				String shoppingUrl = listWishPage.get(currentPosition)
						.getShoppingUrl();
				if (!TextUtils.isEmpty(shoppingUrl)) {// 可以shopp
					requeBrowseCount(shoppingUrl);

				} else {
					ComponentUtil.showToast(
							TootooPlusApplication.getAppContext(), "暂无购买地址");
				}
			}

		} else {
			if (resultData != null) {
				List<WishDetailBean> listWishPage = resultData.getListWish();
				String shoppingUrl = listWishPage.get(currentPosition)
						.getShoppingUrl();
				if (!TextUtils.isEmpty(shoppingUrl)) {// 可以shopp
					requeBrowseCount(shoppingUrl);
				} else {
					ComponentUtil.showToast(
							TootooPlusApplication.getAppContext(), "暂无购买地址");
				}
			}

		}

	}

	private void requeBrowseCount(final String shoppingUrl) {
		// 创建封面图
		if ((NetworkUtil.isNetworkAvaliable(TootooPlusApplication
				.getAppContext()))) {
			// 显示进度
			RequestParamsNet requestParamsNet = new RequestParamsNet();

			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.TYPE,
					ConstantsTooTooEHelper.BROWSE_TYPE_ACT);
			if (!TextUtils.isEmpty(activityId)) {
				requestParamsNet.addQueryStringParameter(
						ConstantsTooTooEHelper.ACTIVITYID, activityId);
			}

			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.GOTOBUY,
					requestParamsNet, new RequestCallBack<String>() {

						private String status;

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {

							String jsonStr = new String(responseInfo.result);
							skipToWebView(shoppingUrl);
							try {
								JSONObject jsobj = new JSONObject(jsonStr);
								if (jsobj.has("Status")) {
									status = jsobj.getString("Status");
									if (status.equals("1")) {

									} else if (jsonStr.equals("1223")) {
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {
							skipToWebView(shoppingUrl);
						}
					});

		} else {
			skipToWebView(shoppingUrl);
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
		Intent intent = new Intent(TootooPlusApplication.getAppContext(),
				GoBuyBrowserActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle bundle = new Bundle();
		bundle.putString("url", shoppingUrl);
		GoBuyBrowserActivity.skipPar("0", activityId);
		intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
		startActivity(intent);

	}

	/**
	 * 
	 * @Title: justCollected
	 * @Description: 判断是否点击过收藏
	 * @param
	 * @return
	 * @throws
	 */
	private void justCollected() {
		String collent = resultData.getCollent();// 0未点击过 1点击过
		if (!TextUtils.isEmpty(collent)) {
			if (collent.equals("0")) {
				Drawable clickDrawable = getResources().getDrawable(
						R.drawable.icon_collect2);
				mCTCollect.setCompoundDrawablesWithIntrinsicBounds(null,
						clickDrawable, null, null);
				mCTCollect.setTextColor(getResources().getColor(
						R.color.text_black));
			} else {
				Drawable clickDrawable = getResources().getDrawable(
						R.drawable.icon_clicked);
				mCTCollect.setCompoundDrawablesWithIntrinsicBounds(null,
						clickDrawable, null, null);
				mCTCollect.setTextColor(getResources().getColor(
						R.color.btn_org_color));
			}

		} else {
			LogUtil.systemlogError("判断是否点击过收藏的字段没有获得", "");
		}
	}

	/**
	 * @Title: onClickTheCollect
	 * @Description: 点击收藏的方法
	 * @param
	 * @return
	 * @throws
	 */
	private void onClickTheCollect() {
		String collent = resultData.getCollent();// 0未点击过 1点击过
		if (!TextUtils.isEmpty(collent)) {
			if (collent.equals("0")) {
				clickCollect();
			} else {
				// showDialogFragmentPhotp();
			}

		} else {
			LogUtil.systemlogError("判断是否点击过收藏的字段没有获得", "");
		}
		mFLCommentListView.setVisibility(View.GONE);
		mFLPhotoUser.setVisibility(View.GONE);
	}

	/**
	 * 
	 * @Title: showDialogFragmentPhotp
	 * @Description: 跳转到用户头像
	 * @param
	 * @return
	 * @throws
	 */
	/*
	 * private void showDialogFragmentPhotp() {
	 * fl_photouser.setVisibility(View.VISIBLE); if (photoUserAllWishFragment ==
	 * null) { photoUserAllWishFragment = new PhotoUserCollectCountFragment(
	 * fl_photouser, resultData.getActivityId()); FragmentManager manager =
	 * this.getSupportFragmentManager(); FragmentTransaction beginTransatiion =
	 * manager.beginTransaction(); beginTransatiion
	 * .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	 * beginTransatiion.add(R.id.photo_user, photoUserAllWishFragment);
	 * beginTransatiion.commit(); }
	 * 
	 * }
	 */

	/**
	 * 
	 * @Title: clickCollect
	 * @Description: 点击收藏
	 * @param
	 * @return
	 * @throws
	 */
	private void clickCollect() {
		if (NetworkUtil.isNetworkAvaliable(this)) {
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			String userid = SharedPreferenceHelper.getLoginUserId(this);
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.USER_ID, userid);
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.ACTIVITYID,
					resultData.getActivityId());
			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.CLICK_COLLECT,
					requestParamsNet, new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String strCallBack = responseInfo.result;
							if (!TextUtils.isEmpty(strCallBack)) {
								try {
									JSONObject object = new JSONObject(
											strCallBack);
									if (object.has("Status")) {
										if (object.getString("Status").equals(
												"1")) {

											String collectCount = resultData
													.getCountCollent();
											if (!TextUtils
													.isEmpty(collectCount)) {
												int count = Integer
														.valueOf(collectCount) + 1;
												resultData.setCountCollent(String
														.valueOf(count));
												resultData.setCollent("1");// 设置已经点击过
												mCTCollect.setText(String
														.valueOf(count));
												mCTCollect
														.setTextColor(getResources()
																.getColor(
																		R.color.btn_org_color));
												Drawable clickDrawable = getResources()
														.getDrawable(
																R.drawable.icon_clicked);
												mCTCollect
														.setCompoundDrawablesWithIntrinsicBounds(
																null,
																clickDrawable,
																null, null);
											}

											// showDialogFragmentPhotp();

										} else if (object.getString("Status")
												.equals("4366")) {
											ComponentUtil.showToast(
													getApplicationContext(),
													"您已经点击过");
										}
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {

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

	protected Fragment mCurrentPrimaryFragment;
	private boolean isSureGo = false;

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
		// if (mCurrentPrimaryFragment != null) {// 如果当前的fragment
		// // mCurrentPrimaryFragment不为null
		// // 那么删除当前的fragment
		// beginTransation.detach(mCurrentPrimaryFragment);
		// }
		// if (fragmentId != null) {
		//
		// beginTransation.attach(fragmentId);
		// } else {

		beginTransation.replace(id, fragment);
		// }
		if (id == R.id.activity_comment) {// 如果是点评的fragment
			mFLCommentListView.setVisibility(View.VISIBLE);
		} else if (id == R.id.photo_group_user) {
			mFLPhotoUser.setVisibility(View.VISIBLE);
			mFLCommentListView.setVisibility(View.GONE);
		} else {
			mFLCommentListView.setVisibility(View.GONE);
		}
		mCurrentPrimaryFragment = fragment;
		beginTransation.commitAllowingStateLoss();

	}

	/**
	 * 
	 * @Title: setTitleData
	 * @Description: 设置标题
	 * @param
	 * @return
	 * @throws
	 */
	public void setTitleData(int currentPosition) {
		// this.currentPosition=currentPosition;

		if (resultData != null) {
			List<WishDetailBean> listWishPage = resultData.getListWish();
			if (listWishPage != null && listWishPage.size() > 0) {

				WishDetailBean currentItemPage = listWishPage
						.get(currentPosition);
				String logoUrl = currentItemPage.getLogoUrl();
				String strDate = resultData.getDateUpdate();
				String strName = currentItemPage.getUserName();
				String userGrade = currentItemPage.getUserGrade();
				if (!TextUtils.isEmpty(logoUrl)) {
					ImageLoader.getInstance().displayImage(logoUrl,
							new ImageViewAware(mIVUserPhoto),
							CommonUtil.OPTIONS_HEADPHOTO);
				} else {
					mIVUserPhoto
							.setImageResource(R.drawable.icon_default_photo);
				}
				if (!TextUtils.isEmpty(strName)) {
					mCTUserName.setText(strName);
				} else {
					mCTUserName.setText("");
				}

				CommonUtil.setUserGrade(mCTUserName, userGrade, "right");

				if (!TextUtils.isEmpty(strDate)) {
					String date = CommonUtil.friendly_time(strDate);
					if(!TextUtils.isEmpty(date)){
						mCTDate.setText(date);
					}else{
						mCTDate.setText(strDate);
					}
					
				}

			}
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

		if ((NetworkUtil.isNetworkAvaliable(TootooPlusApplication
				.getAppContext()))) {
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			String userid = SharedPreferenceHelper
					.getLoginUserId(TootooPlusApplication.getAppContext());
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
							if (groupChatList != null
									&& groupChatList.size() > 0) {
								Intent intent = new Intent(
										TootooPlusApplication.getAppContext(),
										ChatActivity.class);
								Bundle bundle = new Bundle();
								GroupChatList groupChatListBean = new GroupChatList();
								groupChatListBean
										.setGroupChatBeans(groupChatList);
								groupChatListBean.setActivityName(resultData
										.getActivityName());
								groupChatListBean.setActivityId(resultData
										.getActivityId());
								intent.putExtra("groupchatlist",
										groupChatListBean);
								startActivity(intent);
							} else {

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
									ComponentUtil.showToast(
											TootooPlusApplication
													.getAppContext(), "会员增加失败");
									break;
								case 3105:
									ComponentUtil.showToast(
											TootooPlusApplication
													.getAppContext(), "无法找到该组");
									break;

								default:
									break;
								}

							}
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							ComponentUtil.showToast(
									TootooPlusApplication.getAppContext(),
									getResources()
											.getString(
													R.string.errcode_network_response_timeout));
						}
					});

		} else {
			ComponentUtil.showToast(
					TootooPlusApplication.getAppContext(),
					this.getResources().getString(
							R.string.errcode_network_response_timeout));
		}

	}

	public void skipToUserView(View v) {
		/*
		 * //得到当前userid if(listFragment.size()>0){ ActivityDetailFragment
		 * currentFragment=listFragment.get(currentPosition);
		 * 
		 * List<WishDetailBean> listWishPage = resultData .getListWish();
		 * 
		 * }
		 */
		if (resultData != null) {
			List<WishDetailBean> listWishPage = resultData.getListWish();
			if (listWishPage != null && listWishPage.size() > 0) {
				WishDetailBean currentItem = listWishPage.get(currentPosition);
				String viewUserId = currentItem.getUserId();
				String myUserId = SharedPreferenceHelper
						.getLoginUserId(TootooPlusApplication.getAppContext());
				if (!TextUtils.isEmpty(viewUserId)
						&& !viewUserId.equals(myUserId)) {
					Intent intent = new Intent(
							TootooPlusApplication.getAppContext(),
							PersonalHomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("userId", viewUserId);
					startActivity(intent);
				} else {
					Bundle bundle = new Bundle();
					Intent intent = new Intent(
							TootooPlusApplication.getAppContext(),
							HomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					bundle.putInt("tab_index", ConstantsTooTooEHelper.TAB_FIVE);
					intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					startActivity(intent);

				}
			}
		}

	}

	protected void justFaceToFaceButtonStatus() {
		Drawable orgDra = getResources().getDrawable(
				R.drawable.stroke_round_org_backgroud);
		Drawable graDra = getResources().getDrawable(
				R.drawable.stroke_round_gray_backgroud);
		int textBlack = getResources().getColor(R.color.text_black);
		int textOrg = getResources().getColor(R.color.btn_org_color);
		if (!TextUtils.isEmpty(group_status)) {
			if (group_status.equals("1101")) {// 已提交
				mCTFaceToFaceStatus.setText("团长已提交");
				mCTFaceToFaceStatus.setTextColor(textBlack);
				setViewBackGroud(rl_stroke_round, graDra);
			} else if (group_status.equals("1102")) {// 已结束
				mCTFaceToFaceStatus.setText("活动已结束");
				mCTFaceToFaceStatus.setTextColor(textBlack);
				setViewBackGroud(rl_stroke_round, graDra);
			} else if (group_status.equals("1103")) {// 参团人数已满
				mCTFaceToFaceStatus.setText("参团人数已满");
				mCTFaceToFaceStatus.setTextColor(textBlack);
				setViewBackGroud(rl_stroke_round, graDra);
			} else {
				setButtonStatus(orgDra, graDra, textBlack, textOrg);
			}
		} else {
			setButtonStatus(orgDra, graDra, textBlack, textOrg);
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
	 * @Title: setButtonStatus
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void setButtonStatus(Drawable orgDra, Drawable graDra,
			int textBlack, int textOrg) {
		if (resultData != null) {
			List<WishDetailBean> listWishPage = resultData.getListWish();
			if (listWishPage != null && listWishPage.size() > 0) {
				WishDetailBean currentItem = listWishPage.get(currentPosition);
				String comment = currentItem.getComment();
				String activityStatus = resultData.getActivityStatus();
				if (!TextUtils.isEmpty(activityStatus)) {
					if (activityStatus.equals("1")) {
						mCTFaceToFaceStatus.setText("即将开始");
						mCTFaceToFaceStatus.setTextColor(textBlack);
						setViewBackGroud(rl_stroke_round, graDra);
					} else if (activityStatus.equals("2")) {
						mCTFaceToFaceStatus.setText("确认加入");
						isSureGo = true;
						mCTFaceToFaceStatus.setTextColor(textOrg);
						setViewBackGroud(rl_stroke_round, graDra);
					} else if (activityStatus.equals("3")) {
						mCTFaceToFaceStatus.setText("已报名");
						mCTFaceToFaceStatus.setTextColor(textBlack);
						setViewBackGroud(rl_stroke_round, graDra);

					} else if (activityStatus.equals("4")) {
						if (!TextUtils.isEmpty(comment)) {
							// 是否评论过 0已结束（未参与活动不能点评）1未评论 2已评论
							if (comment.equals("0")) {
								mCTFaceToFaceStatus.setText("已结束");
								mCTFaceToFaceStatus.setTextColor(textBlack);
								setViewBackGroud(rl_stroke_round, graDra);
							} else if (comment.equals("1")) {
								mCTFaceToFaceStatus.setText("去评论");
								mCTFaceToFaceStatus.setTextColor(textBlack);
								setViewBackGroud(rl_stroke_round, graDra);
							} else if (comment.equals("2")) {
								mCTFaceToFaceStatus.setText("已评论");
								mCTFaceToFaceStatus.setTextColor(textBlack);
								setViewBackGroud(rl_stroke_round, graDra);
							}
						}

					} else if (activityStatus.equals("5")) {
						mCTFaceToFaceStatus.setText("不能组团");
						mCTFaceToFaceStatus.setTextColor(textBlack);
						setViewBackGroud(rl_stroke_round, graDra);
					} else {
					}

				} else {
				}
			}
		}
	}
	/**
	 * 
	* @Title: justShowButton 
	* @Description: 判断是否显示面对面入口
	* @param  
	* @return   
	* @throws
	 */
protected void justShowButton(boolean isShow){
	
		if(!(!TextUtils.isEmpty(activity)&&activity.equals("FaceToFaceGroupEnterActivity"))){
			if(isShow){
			mIVGoGroup.setVisibility(View.VISIBLE);
		}else{
			mIVGoGroup.setVisibility(View.GONE);
		}
	}
	
}
	/**
	 * 
	 * @Title: justButtonStatus
	 * @Description: 即将开始组团，组团，已组团，已结束
	 * @param 1即将开始 2组团 3已组团
	 *        4已结束（当ActivityStatus==4，查看StoryList中Comment的值，心愿可单独点评 ）
	 * @return
	 * @throws
	 */
	protected void justButtonStatus() {
		if (resultData != null) {
			List<WishDetailBean> listWishPage = resultData.getListWish();
			if (listWishPage != null && listWishPage.size() > 0) {
				WishDetailBean currentItem = listWishPage.get(currentPosition);
				String comment = currentItem.getComment();
				String activityStatus = resultData.getActivityStatus();
				if (!TextUtils.isEmpty(activityStatus)) {
					if (activityStatus.equals("1")) {//
//						是
						justShowButton(true);
						
						mCTGroupComment.setText("即将开始");
						Drawable group_begin = TootooPlusApplication
								.getAppContext().getResources()
								.getDrawable(R.drawable.icon_go_to_group_begin);// 暂时先用这个图标
						mLLGroup.setBackgroundColor(TootooPlusApplication
								.getAppContext().getResources()
								.getColor(R.color.white));
						mCTGroupComment.setTextColor(TootooPlusApplication
								.getAppContext().getResources()
								.getColor(R.color.gray_line));
						mCTGroupComment
								.setCompoundDrawablesWithIntrinsicBounds(null,
										group_begin, null, null);

					} else if (activityStatus.equals("2")) {//只有这种状态的时候
						clickStatus = 1;
						if(!(!TextUtils.isEmpty(activity)&&activity.equals("FaceToFaceGroupEnterActivity"))){
							justIsFirstGoGroup();
						}
						justShowButton(true);
						mCTGroupComment.setText("组团报名");
						Drawable can_gotogroup = TootooPlusApplication
								.getAppContext().getResources()
								.getDrawable(R.drawable.icon_gotogroup);// 暂时先用这个图标
						mLLGroup.setBackgroundColor(TootooPlusApplication
								.getAppContext().getResources()
								.getColor(R.color.btn_org_color));
						// mCTGroupComment
						// .setCompoundDrawablesWithIntrinsicBounds(null,
						// can_gotogroup, null, null);
					} else if (activityStatus.equals("3")) {
						justShowButton(false);
						mCTGroupComment.setText("已报名");
						Drawable can_gotogroup = TootooPlusApplication
								.getAppContext().getResources()
								.getDrawable(R.drawable.icon_gotogroup_already);// 暂时先用这个图标
						mLLGroup.setBackgroundColor(TootooPlusApplication
								.getAppContext().getResources()
								.getColor(R.color.white));
						mCTGroupComment.setTextColor(TootooPlusApplication
								.getAppContext().getResources()
								.getColor(R.color.gray_line));
						mCTGroupComment
								.setCompoundDrawablesWithIntrinsicBounds(null,
										can_gotogroup, null, null);

					} else if (activityStatus.equals("4")) {
						justShowButton(false);
						if (!TextUtils.isEmpty(comment)) {
							// 是否评论过 0已结束（未参与活动不能点评）1未评论 2已评论
							if (comment.equals("0")) {
								mCTGroupComment.setText("已结束");
								Drawable can_groupover = TootooPlusApplication
										.getAppContext()
										.getResources()
										.getDrawable(R.drawable.icon_group_over);// 暂时先用这个图标
								mLLGroup.setBackgroundColor(TootooPlusApplication
										.getAppContext().getResources()
										.getColor(R.color.white));
								mCTGroupComment
										.setCompoundDrawablesWithIntrinsicBounds(
												null, can_groupover, null, null);
								mCTGroupComment
										.setTextColor(TootooPlusApplication
												.getAppContext().getResources()
												.getColor(R.color.gray_line));
							} else if (comment.equals("1")) {
								clickStatus = 2;
								mCTGroupComment.setText("去评论");
								Drawable can_groupover = TootooPlusApplication
										.getAppContext()
										.getResources()
										.getDrawable(R.drawable.icon_comment_up);// 暂时先用这个图标
								mLLGroup.setBackgroundColor(TootooPlusApplication
										.getAppContext().getResources()
										.getColor(R.color.white));
								mCTGroupComment
										.setTextColor(TootooPlusApplication
												.getAppContext()
												.getResources()
												.getColor(
														R.color.btn_gray_color));
								mCTGroupComment
										.setCompoundDrawablesWithIntrinsicBounds(
												null, can_groupover, null, null);
							} else if (comment.equals("2")) {
								mCTGroupComment.setText("已评论");
								mCTGroupComment
										.setTextColor(TootooPlusApplication
												.getAppContext().getResources()
												.getColor(R.color.gray_line));
								Drawable can_groupover = TootooPlusApplication
										.getAppContext()
										.getResources()
										.getDrawable(
												R.drawable.icon_comment_down);// 暂时先用这个图标
								mLLGroup.setBackgroundColor(TootooPlusApplication
										.getAppContext().getResources()
										.getColor(R.color.white));
								mCTGroupComment
										.setCompoundDrawablesWithIntrinsicBounds(
												null, can_groupover, null, null);
							}
						}

					} else if (activityStatus.equals("5")) {
						justShowButton(false);
						mLLGroup.setBackgroundColor(TootooPlusApplication
								.getAppContext().getResources()
								.getColor(R.color.gray_line));
						mCTGroupComment.setText("组团报名");
						// Drawable can_groupover = TootooPlusApplication
						// .getAppContext().getResources()
						// .getDrawable(R.drawable.icon_group_false);// 暂时先用这个图标

						// mCTGroupComment
						// .setCompoundDrawablesWithIntrinsicBounds(null,
						// can_groupover, null, null);
					} else {
						LogUtil.error("activityStatus", "获取失败");
					}

				} else {
				}
			}
		}

	}

	/**
	 * @Title: justClickStatus
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void justClickStatus() {
		if (resultData != null) {
			String activityStatus = resultData.getActivityStatus();
			if (clickStatus != -1) {
				if (clickStatus == 1) {// 去组团
					gotoGroup();
				} else {// ==2 去点评
					gotoComment();
				}

			} else {
				return;
			}
		}

	}

	/**
	 * 
	 * @Title: gotoGroup
	 * @Description: 去组团
	 * @param
	 * @return
	 * @throws
	 */
	private void gotoGroup() {
		if (SharedPreferenceHelper.getUserLocation(ActivityDetailActivity.this)
				.getLocLatitude().equals("4.9E-324")
				|| SharedPreferenceHelper
						.getUserLocation(ActivityDetailActivity.this)
						.getLocLatitude().equals("0.0")) {

			ComponentUtil.showToast(ActivityDetailActivity.this, getResources()
					.getString(R.string.open_gps_hint));

		} else {
			showShareDetail(ConstantsTooTooEHelper.FIRSTGOGROUP);
		}
	}

	/**
	 * @Title: showShareDetail
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	public void showShareDetail(String clicksource) {
		showProgressDialog(ActivityDetailActivity.this);
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.ACT_DETAILS_CREATE_GROUP_ACT_ID,
				resultData.getActivityId());
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.ACT_DETAILS_CREATE_GROUP_MEMBER,
				SharedPreferenceHelper
						.getLoginUserId(ActivityDetailActivity.this));
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.ACT_DETAILS_CREATE_GROUP_ADMIN,
				SharedPreferenceHelper
						.getLoginUserId(ActivityDetailActivity.this));
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.ACT_DETAILS_CREATE_GROUP_LATITUDE,
				SharedPreferenceHelper.getUserLocation(
						ActivityDetailActivity.this).getLocLatitude());
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.ACT_DETAILS_CREATE_GROUP_LONGITUDE,
				SharedPreferenceHelper.getUserLocation(
						ActivityDetailActivity.this).getLocLongitude());
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.ACT_DETAILS_CREATE_GROUP_SOURCE,
				clicksource);

		CommonUtil.xUtilsGetSend(
				TootooeNetApiUrlHelper.ACT_DETAILS_CREATE_GROUP_URL,
				requestParamsNet, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						closeProgressDialog(ActivityDetailActivity.this);
						String jsonStr = new String(responseInfo.result);
						try {
							JSONObject json = new JSONObject(jsonStr);
							String add_status = "";
							if (json.has("Status")) {
								add_status = json.getString("Status");
							}

							if (add_status.equals("1")) {
								String group_id = "";
								String is_first = "";
								String group_status = "";
								String group_CountParticipant = "";
								if (json.has("GroupId")) {
									group_id = json.getString("GroupId");
								}

								if (json.has("First")) {
									is_first = json.getString("First");
								}

								if (json.has("GroupStatus")) {
									group_status = json
											.getString("GroupStatus");
								}

								if (json.has("CountParticipant")) {
									group_CountParticipant = json
											.getString("CountParticipant");
								}

								if (is_first.equals("0")) {
									nofirst(group_id, group_status,
											group_CountParticipant);
//									ShareUtils.showShareActivityDetail(
//											ActivityDetailActivity.this,
//											resultData.getActivityId(),
//											group_id,
//											resultData.getActivityName(),
//											new MyFaceToFaceOnClistener(ActivityDetailActivity.this),ActivityDetailActivity.this);

								} else {// 是第一次
									ShareUtils.showShareActivityDetail(
											ActivityDetailActivity.this,
											resultData.getActivityId(),
											group_id,
											resultData.getActivityName(),
											new MyFaceToFaceOnClistener(ActivityDetailActivity.this),ActivityDetailActivity.this);
								}
							} else {
								ComponentUtil
										.showToast(
												ActivityDetailActivity.this,
												getResources()
														.getString(
																R.string.act_details_create_group_fail));
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					/**
					 * @Title: nofirst
					 * @Description: TODO
					 * @param
					 * @return
					 * @throws
					 */
					private void nofirst(String group_id, String group_status,
							String group_CountParticipant) {
					
						Intent member_intent = new Intent(
								ActivityDetailActivity.this,
								JoinMemberActivity.class);
						member_intent.putExtra("group_id", group_id);
						member_intent.putExtra("act_id",
								resultData.getActivityId());
						member_intent.putExtra("act_name",
								resultData.getActivityName());
						// "0"为未报名; "1"为已报名，待审核;
						// "-1"已报名，但审核失败;
						// "2"为被选中
						member_intent
								.putExtra("group_status", group_status);
						member_intent.putExtra("act_type",
								resultData.getType());
						member_intent.putExtra("group_countParticipant",
								group_CountParticipant);
						startActivity(member_intent);
						
						
						
					
						
					
					}

				

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						closeProgressDialog(ActivityDetailActivity.this);
						ComponentUtil
								.showToast(
										ActivityDetailActivity.this,
										getResources()
												.getString(
														R.string.errcode_network_response_timeout));
					}
				});
	}
	
	/**
	 * 
	 * @Title: gotoComment
	 * @Description: 去点评
	 * @param
	 * @return
	 * @throws
	 */
	private void gotoComment() {
		// menus = new ArrayList<FloatingActionMenu>();
		// initArcMenu(ITEM_DRAWABLES);
		// mLLArcMenu.setVisibility(View.VISIBLE);
		// mLLArcMenu.setBackgroundColor(R.color.transparent);
		showSelectPopup();
	}

	/**
	 * 
	 * @Title: initArcMenu
	 * @Description: 初始化创建点评的条目
	 * @param
	 * @return
	 * @throws
	 */

	@Override
	public void onLayoutChange(View v, int left, int top, int right,
			int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
		// update the position of the menu when the main layout changes size on
		// events like soft keyboard open/close
		if (right - left != 0
				&& bottom - top != 0
				&& (oldLeft != left || oldTop != top || oldRight != right || oldBottom != bottom)
				&& bottomMenu != null) {
			bottomMenu.updateItemPositions();
		}
	}

	@Override
	public void onScrollChanged() {

	}

	@Override
	public void OnItemPagePostion(int position) {
		this.currentPosition = position;
		setPageWhatCount();
		setTitleData(currentPosition);
		justButtonStatus();
		justIsVisibleOfCommentCount();
		justShopping();
		// showFirstDialog();
		justShowGotoGroupOrWrite();

	}

	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 1 && arg1 == Activity.RESULT_OK) {
			Bundle bundle = new Bundle();
			Intent intent = new Intent(this, PhotoCameraActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			justIsCommentOrWish(bundle);
			bundle.putString("picPath", ImageUtil.getTempPhotoPath());// 拍照图片的路径
			ConstantsTooTooEHelper.putView(
					ConstantsTooTooEHelper.isConvertRecommendView, bundle);
			intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
			startActivity(intent);
		}
	}

	/**
	 * @Title: justIsCommentOrWish
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void justIsCommentOrWish(Bundle bundle) {
		bundle.putString(TootooeNetApiUrlHelper.TYPE, "1");
		if (resultData != null) {
			List<WishDetailBean> listWishPage = resultData.getListWish();
			if (listWishPage != null && listWishPage.size() > 0) {
				WishDetailBean currentItem = listWishPage.get(currentPosition);
				String activityid = resultData.getActivityId();
				String currentStoryId = currentItem.getStoryId();
				if (!TextUtils.isEmpty(activityid)) {
					bundle.putString(TootooeNetApiUrlHelper.ACTIVITY_CREATE_ID,
							activityid);
				} else {
					bundle.putString(TootooeNetApiUrlHelper.STORY_CREATE_ID, "");
				}

				if (!TextUtils.isEmpty(currentStoryId)) {
					bundle.putString(TootooeNetApiUrlHelper.STORY_CREATE_ID,
							currentStoryId);

				} else {
					bundle.putString(TootooeNetApiUrlHelper.STORY_CREATE_ID, "");
				}

			}

		}
	}

	@Override
	public void onViewPagerDialogStatus(boolean isDismiss, boolean isZhiding) {
		this.isDismiss = isDismiss;
		if (isDismiss) {
			// showFirstDialog
			if (isZhiding) {
				return;
			} else {
				justShowGotoGroupOrWrite();
			}

		}
	}

	private void showFirstDialog() {
		if (resultData != null) {
			if (clickStatus != -1) {
				if (clickStatus == 1) {
					justShowBigSmallGuide();

				} else {// ==2 去点评

					boolean isFirst = SharedPreferenceHelper
							.getFirstGuideWriteComment(this);
					if (isFirst) {
						CommonUtil
								.showFirstGuideDialog(
										this,
										ConstantsTooTooEHelper.FIRST_GUIDE_WRITE_COMMENT);
					}

				}

			} else {
				return;
			}

		}

	}

	/**
	 * @Title: justShowBigSmallGuide
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void justShowBigSmallGuide() {
		if (resultData != null) {
			Integer topcoefficient = resultData.getTopCoefficient();
			if (topcoefficient != null) {
				if (topcoefficient > ConstantsTooTooEHelper.TopCoefficient) {
					// 置顶 就不显示引导了 呵呵

				} else {
					// 普通
					// 去组团
					String type = resultData.getType();
					if (!TextUtils.isEmpty(type)) {
						if (type.equals("0")) {// 线上小白痴
							boolean isFirstSmall = SharedPreferenceHelper
									.getFirstGuideSmallGoToGroup(this);
							if (isFirstSmall) {
								CommonUtil
										.showFirstGuideDialog(
												this,
												ConstantsTooTooEHelper.FIRST_GUIDE_SMALL_REGISTER);
							}
						} else {
							boolean isFirstBig = SharedPreferenceHelper
									.getFirstGuideGoToGroup(this);
							if (isFirstBig) {
								CommonUtil
										.showFirstGuideDialog(
												this,
												ConstantsTooTooEHelper.FIRST_GUIDE_REGISTER);
							}
						}

					}
				}

			}

		}
	}

	@Override
	public void onListenerSwitch(boolean isClose) {
		justIsVisibleOfCommentCount();
	}

	private double latitude;
	public double longitude;

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			
			
			
			if (isFirstLoc) {
				// 如果拒绝打开GPS, 定位结果为4.9E-324
				isFirstLoc = false;
				if (location.getCity() != null) {
					SharedPreferenceHelper.saveUserLocation(
							ActivityDetailActivity.this, location.getCity(),
							String.valueOf(location.getLatitude()),
							String.valueOf(location.getLongitude()));
					latitude = location.getLatitude();
					longitude = location.getLongitude();
					getNetData();
				} else {
					showDialogGps();
				}

			}
//			FIRST_GUIDE_FACE_TO_FACE_DETAIL
			
			
			
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	public void onDialogStatusListener(boolean isDismiss) {
		if (isDismiss) {
			boolean isDetailFaceToFace = SharedPreferenceHelper.getFirstGuideFaceToFaceDetailAct(ActivityDetailActivity.this);
			if(isDetailFaceToFace){
				if(mIVGoGroup!=null&&mIVGoGroup.getVisibility()==View.VISIBLE){
					DialogFragment isFaceToFactGuide = CommonUtil.showFirstGuideDialog(ActivityDetailActivity.this, ConstantsTooTooEHelper.FIRST_GUIDE_FACE_TO_FACE_DETAIL);
				}
				
			}
			justContent();

		}

	}

	@Override
	public void onCancel(Platform arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
		if(arg0!=null){
			String name=arg0.getName();
			if(name.equals(Wechat.NAME)){//微信好友
				showShareDetail(ConstantsTooTooEHelper.WEIXIN);
			}else if(name.equals(WechatMoments.NAME)){//微信朋友圈
				showShareDetail(ConstantsTooTooEHelper.WEIXIN);
			}
		}
		
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		if(arg0!=null){
			String name=arg0.getName();
			
		
	}
	}
}
