package com.ninetowns.tootooplus.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.MyTextwatcherUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.CoverRecommendSelectActivity;
import com.ninetowns.tootooplus.activity.CreateActSecondStepActivity;
import com.ninetowns.tootooplus.activity.CreateActiveFirstStepActivity;
import com.ninetowns.tootooplus.activity.InternetBrowserActivity;
import com.ninetowns.tootooplus.activity.PhotoCameraActivity;
import com.ninetowns.tootooplus.activity.RecordVideoActivity;
import com.ninetowns.tootooplus.activity.TextStoryActivity;
import com.ninetowns.tootooplus.activity.VideoActivity;
import com.ninetowns.tootooplus.adapter.DragAdapter;
import com.ninetowns.tootooplus.adapter.DragAdapter.ViewHolder;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.ConVertBean;
import com.ninetowns.tootooplus.bean.CreateActivitySecondBean;
import com.ninetowns.tootooplus.bean.SecondStepStoryBean;
import com.ninetowns.tootooplus.bean.StoryDetailListBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.cooldraganddrop.CoolDragAndDropGridView;
import com.ninetowns.ui.cooldraganddrop.SimpleScrollingStrategy;
import com.ninetowns.ui.cooldraganddrop.SpanVariableGridView.CalculateChildrenPosition;
import com.ninetowns.ui.widget.popwindow.StoryItemPopupWindow;
import com.ninetowns.ui.widget.popwindow.StoryItemPopupWindow.PopWindowItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 
 * @ClassName: WishDetailFragment
 * @Description:心愿详情列表
 * @author wuyulong
 * @date 2015-2-5 上午11:03:31
 * 
 */
public class CreateSecondActivityFragment extends Fragment implements
		View.OnClickListener, CoolDragAndDropGridView.DragAndDropListener,
		OnItemClickListener, OnItemLongClickListener, CalculateChildrenPosition {
	private View mWishDetailFragment;
	private Bundle bundle;
	@ViewInject(R.id.iv_convert)
	private ImageView mIvConvertImage;// 封面图
	@ViewInject(R.id.iv_video_icon)
	private ImageView mIVVideoIcon;
	@ViewInject(R.id.coolDragAndDropGridView)
	public CoolDragAndDropGridView mCoolDragAndrDropView;
	@ViewInject(R.id.scrollView)
	private ScrollView mScrollView;
	@ViewInject(R.id.ll_one_btn)
	private LinearLayout mLLEditext;// 编辑
	@ViewInject(R.id.ll_cut_btn)
	private LinearLayout mLLClip;// 裁剪
	@ViewInject(R.id.view_line)
	private View mViewLine;// 线
	@ViewInject(R.id.rl_convert)
	private RelativeLayout mRlConvert;
	@ViewInject(R.id.et_activity_name)
	public EditText mActivityName;
	private CreateActivitySecondBean wishDetailBean;
	private List<StoryDetailListBean> wishPageList = new ArrayList<StoryDetailListBean>();
	private ConVertBean convertBean;
	private ImageView mIVPhoto;
	private ImageView mIVVideo;
	private ImageView mIVText;
	public DragAdapter wishDetailAdapter;
	private int currentPosition;
	private PopupWindow popupWindow;
	private int screen_width;
	private int screen_height;
	private boolean isLocalConvert;
	private View mLLleft;
	// private View mLLRight;
	private TextView mTVTitle;
	private SecondStepStoryBean secondStepStoryBean;
	private boolean isActivityEditext = false;// 判断是否是追加修改多个故事
	private String storyId;
	private String updateId = "0";
	private StoryItemPopupWindow spw;
	private String currentStoryId = "";

	public CreateSecondActivityFragment() {
	}

	public CreateSecondActivityFragment(
			SecondStepStoryBean secondStepStoryBean,
			CreateActivitySecondBean wishDetailBean, int currentPosition) {
		this.wishDetailBean = wishDetailBean;
		this.secondStepStoryBean = secondStepStoryBean;

		convertBean = wishDetailBean.getConvertBean();

		if (convertBean != null) {
			storyId = convertBean.getStoryId();
		}

		this.currentPosition = currentPosition;
		List<StoryDetailListBean> localList = wishDetailBean
				.getWishDetailBean();
		if (localList != null && localList.size() > 0) {
			wishPageList.addAll(localList);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getBundleType();
	}

	/**
	 * 
	 * @Title: setCollDragGridViewOperate
	 * @Description: 注册
	 * @param
	 * @return
	 * @throws
	 */
	private void setCollDragGridViewOperate() {
		mCoolDragAndrDropView.setScrollingStrategy(new SimpleScrollingStrategy(
				mScrollView));
		mCoolDragAndrDropView.setDragAndDropListener(this);
		mCoolDragAndrDropView.setOnItemClickListener(this);
		mCoolDragAndrDropView.setOnItemLongClickListener(this);
		mCoolDragAndrDropView.addCalculateChildrenPositionListener(this);
		mCoolDragAndrDropView.requestCalculateChildrenPositions();// 请求计算
		mCoolDragAndrDropView.setOnItemClickListener(this);
	}

	private void setViewListener() {
		mIvConvertImage.setOnClickListener(this);
		mIVPhoto.setOnClickListener(this);
		mIVVideo.setOnClickListener(this);
		mIVText.setOnClickListener(this);
		mLLEditext.setOnClickListener(this);
		mLLClip.setOnClickListener(this);
		mLLleft.setOnClickListener(this);
		// mLLRight.setOnClickListener(this);

	}

	private void getBundleType() {
		bundle = getActivity().getIntent().getBundleExtra(
				ConstantsTooTooEHelper.BUNDLE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mWishDetailFragment = inflater.inflate(
				R.layout.create_activity_second_step_fragment, null);
		ViewUtils.inject(this, mWishDetailFragment); // 注入view和事件
		mActivityName
				.addTextChangedListener(new MyTextwatcherUtil(
						TootooPlusApplication.getAppContext(), mActivityName,
						null, 20));
		getActivityId();
		setViewListener();
		createPopWindow();
		setImageConvertParams();
		if (convertBean != null) {
			justIsVideo(convertBean);
		}

		setConvertContent();
		setDragAdapter();
		setCollDragGridViewOperate();
		return mWishDetailFragment;
	}

	private void createPopWindow() {
		if (popupWindow == null) {
			popupWindow = new PopupWindow();
			// 屏幕宽度
			screen_width = CommonUtil.getWidth(getActivity());
			// 屏幕高度
			screen_height = CommonUtil.getHeight(getActivity());
		}

	}

	/**
	 * 点击底部图片或者文字弹出框
	 * 
	 * @param type
	 *            "pic"表示选择图片，"text"表示文字的选择
	 * @param isInternet
	 *            true表示有网址选择, false表示无视频选项
	 */
	public void showSelectPopup(final String type, boolean isInternet,
			final boolean isConvert) {
		isLocalConvert = isConvert;
		if (!popupWindow.isShowing()) {
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.rec_bottom_popup_layout, null);
			popupWindow.setContentView(view);
			// 窗口的宽带和高度根据情况定义
			popupWindow.setWidth(screen_width * 9 / 10);
			if (isInternet) {
				popupWindow.setHeight(screen_height / 4);
			} else {
				popupWindow.setHeight(screen_height / 5);
			}
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable(0));

			// 窗口进入和退出的效果
			popupWindow.setAnimationStyle(R.style.win_ani_top_bottom);
			LinearLayout ll_video = (LinearLayout) view
					.findViewById(R.id.ll_video);
			View v_rec_video = view.findViewById(R.id.v_rec_video);

			if (isConvert) {
				ll_video.setVisibility(View.VISIBLE);
				v_rec_video.setVisibility(View.VISIBLE);
			} else {
				ll_video.setVisibility(View.GONE);
				v_rec_video.setVisibility(View.GONE);
			}
			if (isInternet) {
				popupWindow.showAtLocation(LayoutInflater.from(getActivity())
						.inflate(R.layout.home_activity, null),
						// 位置可以按要求定义
						Gravity.NO_GRAVITY, screen_width / 20,
						screen_height * 3 / 4 - 15);
			} else {
				popupWindow.showAtLocation(LayoutInflater.from(getActivity())
						.inflate(R.layout.home_activity, null),
						// 位置可以按要求定义
						Gravity.NO_GRAVITY, screen_width / 20,
						screen_height * 4 / 5 - 15);
			}

			LinearLayout rec_bot_popup_total_layout = (LinearLayout) view
					.findViewById(R.id.rec_bot_popup_total_layout);
			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) rec_bot_popup_total_layout
					.getLayoutParams();
			if (isInternet) {
				linearParams.height = screen_height / 4;
			} else {
				linearParams.height = screen_height / 5;
			}

			rec_bot_popup_total_layout.setLayoutParams(linearParams);

			LinearLayout rec_bot_popup_internet_layout = (LinearLayout) view
					.findViewById(R.id.rec_bot_popup_internet_layout);
			View rec_bot_popup_internet_line = (View) view
					.findViewById(R.id.rec_bot_popup_internet_line);
			if (isInternet) {
				rec_bot_popup_internet_layout.setVisibility(View.VISIBLE);
				rec_bot_popup_internet_line.setVisibility(View.VISIBLE);
			} else {
				rec_bot_popup_internet_layout.setVisibility(View.GONE);
				rec_bot_popup_internet_line.setVisibility(View.GONE);
			}
			rec_bot_popup_internet_layout
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							skipInternetCollect(isConvert);
							popupWindow.dismiss();
						}
					});

			TextView rec_bot_popup_camera_hint = (TextView) view
					.findViewById(R.id.rec_bot_popup_camera_hint);
			if (type.equals("pic")) {
				rec_bot_popup_camera_hint
						.setText(R.string.want_rec_menu_camera);
			} else if (type.equals("text")) {
				rec_bot_popup_camera_hint
						.setText(R.string.rec_bottom_popup_title_hint);
			}

			LinearLayout rec_bot_popup_camera_layout = (LinearLayout) view
					.findViewById(R.id.rec_bot_popup_camera_layout);
			rec_bot_popup_camera_layout
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							if (type.equals("pic")) {
								// 拍摄
								Intent intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								Uri imageUri = Uri.fromFile(new File(ImageUtil
										.getTempPhotoPath()));
								intent.putExtra(MediaStore.EXTRA_OUTPUT,
										imageUri);
								intent.putExtra(ImageColumns.ORIENTATION, 0);
								intent.putExtra("storyType", "2");
								startActivityForResult(intent,
										getActivity().RESULT_FIRST_USER);
							} else if (type.equals("text")) {
								skipTextEditView(true, isConvert);
							}

							popupWindow.dismiss();

						}
					});

			TextView rec_bot_popup_local_hint = (TextView) view
					.findViewById(R.id.rec_bot_popup_local_hint);
			if (type.equals("pic")) {
				rec_bot_popup_local_hint
						.setText(R.string.rec_bottom_popup_select_from_local_hint);
			} else if (type.equals("text")) {
				rec_bot_popup_local_hint
						.setText(R.string.rec_bottom_popup_content_hint);
			}

			LinearLayout rec_bot_popup_local_layout = (LinearLayout) view
					.findViewById(R.id.rec_bot_popup_local_layout);

			rec_bot_popup_local_layout
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							if (type.equals("pic")) {
								skipLocalPhotoView(isConvert);
							} else if (type.equals("text")) {
								// 正文
								skipTextEditView(false, isConvert);
							}

							popupWindow.dismiss();

						}
					});
			ll_video.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// 拍视频
					skipVideoView(isConvert);

				}
			});

			LinearLayout rec_bot_popup_cancel_layout = (LinearLayout) view
					.findViewById(R.id.rec_bot_popup_cancel_layout);
			rec_bot_popup_cancel_layout
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// 取消
							popupWindow.dismiss();
						}
					});
		}
	}

	/**
	 * 
	 * @Title: skipLocalPhotoView
	 * @Description: 跳转到
	 * @param
	 * @return
	 * @throws
	 */
	protected void skipLocalPhotoView(boolean isConvert) {
		Intent intent = new Intent(this.getActivity(),
				CoverRecommendSelectActivity.class);
		skipToActivity(isConvert, intent);

	}

	protected void skipTextEditView(boolean isTitle, boolean isConvert) {
		Intent intent = new Intent(this.getActivity(), TextStoryActivity.class);
		bundle.putBoolean("isTitle", isTitle);
		skipToActivity(isConvert, intent);
	}

	protected void skipInternetCollect(boolean isConvert) {
		Intent intent = new Intent(this.getActivity(),
				InternetBrowserActivity.class);
		skipToActivity(isConvert, intent);
	}

	/**
	 * 
	 * @Title: skipToActivity
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void skipToActivity(boolean isConvert, Intent intent) {
		String activityName = mActivityName.getText().toString();
		secondStepStoryBean.setActivityName(activityName);
		getCurrentStoryId();
		bundle.putString("storyid", currentStoryId);
		LogUtil.debug("跳转修改", currentStoryId);
		bundle.putString("UpdateStoryId", "-1");
		CreateActSecondStepActivity createActSec = (CreateActSecondStepActivity) getActivity();
		bundle.putInt("currentPosition", createActSec.currentPosition);
		bundle.putSerializable(ConstantsTooTooEHelper.BundleResopnse,
				secondStepStoryBean);
		if (isConvert) {
			ConstantsTooTooEHelper.putView(
					ConstantsTooTooEHelper.isConvertView, bundle);
		} else {
			putParamType(bundle);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		bundle.putString("picPath", ImageUtil.getTempPhotoPath());// 拍照图片的路径
		intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
		startActivity(intent);
	}

	/**
	 * 
	 * @Title: putParamType
	 * @Description: 跳转的不同类型
	 * @param
	 * @return
	 * @throws
	 */
	public void putParamType(Bundle bundle) {
		ConstantsTooTooEHelper.putView(ConstantsTooTooEHelper.isCreateView,
				bundle);
	}

	/***
	 * 
	 * @Title: getActivityId
	 * @Description: 获取activity的id
	 * @param
	 * @return
	 * @throws
	 */
	private void getActivityId() {

		mIVPhoto = (ImageView) getActivity().findViewById(R.id.iv_photo);
		mIVVideo = (ImageView) getActivity().findViewById(R.id.iv_video);
		mIVText = (ImageView) getActivity().findViewById(R.id.iv_text);
		mLLleft = getActivity().findViewById(R.id.ll_left);

		mTVTitle = (TextView) getActivity().findViewById(R.id.tv_title);

		// mLLRight = getActivity().findViewById(R.id.ll_right);

	}

	private void setConvertContent() {
		if (convertBean != null) {
			String strConvertThumb = convertBean.getCoverThumb();
			// String strVideoUrl=mConvertBean.getStoryVideoUrl();
			ImageLoader.getInstance().displayImage(strConvertThumb,
					new ImageViewAware(mIvConvertImage),
					CommonUtil.OPTIONS_ALBUM_DETAIL);
			String activityName = secondStepStoryBean.getActivityName();
			if (!TextUtils.isEmpty(activityName)) {
				mActivityName.setText(activityName);
			}

		}

	}

	private void setDragAdapter() {
		if (wishDetailAdapter == null) {
			wishDetailAdapter = new DragAdapter(wishPageList);
			mCoolDragAndrDropView.setAdapter(wishDetailAdapter);
			wishDetailAdapter.notifyDataSetChanged();
		} else {
			mCoolDragAndrDropView.setAdapter(wishDetailAdapter);
			wishDetailAdapter.notifyDataSetChanged();
		}

	}

	/**
	 * 
	 * @Title: setImageConvertParams
	 * @Description: 设置封面图的大小
	 * @param
	 * @return
	 * @throws
	 */
	private void setImageConvertParams() {
		int with = CommonUtil.getWidth(TootooPlusApplication.getAppContext());
		int height = (int) (with * 0.75);
		RelativeLayout.LayoutParams convertParam = new RelativeLayout.LayoutParams(
				with, height);
		mIvConvertImage.setLayoutParams(convertParam);
	}

	/**
	 * 
	 * @Title: justIsVideo
	 * @Description: 判断是否是视频
	 * @param
	 * @return
	 * @throws
	 */
	private void justIsVideo(ConVertBean parserResult) {
		if (parserResult != null) {

			String storyStype = parserResult.getStoryType();
			if (!TextUtils.isEmpty(storyStype)) {
				if (storyStype.equals("3")) {// 视频
					mIVVideoIcon.setVisibility(View.VISIBLE);
					mViewLine.setVisibility(View.GONE);
					mLLClip.setVisibility(View.GONE);
				} else {
					mIVVideoIcon.setVisibility(View.INVISIBLE);
					mViewLine.setVisibility(View.VISIBLE);
					mLLClip.setVisibility(View.VISIBLE);
				}
			} else {
				mIVVideoIcon.setVisibility(View.INVISIBLE);
				mViewLine.setVisibility(View.VISIBLE);
				mLLClip.setVisibility(View.VISIBLE);
			}
		} else {
			throw new IllegalArgumentException();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_convert:// 点击封面图
			justIfCanPlay();
			break;
		case R.id.iv_photo:// 图片
			showSelectPopup("pic", true, false);
			break;
		case R.id.iv_video:// 视频
			skipVideoView(false);
			break;
		case R.id.iv_text:// 文字
			showSelectPopup("text", false, false);
			break;
		case R.id.ll_one_btn:// 编辑
			showSelectPopup("pic", true, true);
			break;
		case R.id.ll_cut_btn:// 裁剪
			if (convertBean != null) {
				skipToClipConvert(mIvConvertImage, convertBean);
			}

			break;
		case R.id.ll_left:
			// 上一步
			Intent intent = new Intent(getActivity(),
					CreateActiveFirstStepActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			getActivity().finish();
			break;

		}

	}

	private void skipVideoView(boolean isConvert) {
		Intent intent = new Intent(TootooPlusApplication.getAppContext(),
				RecordVideoActivity.class);
		skipToActivity(isConvert, intent);

	}

	/**
	 * 
	 * @Title: skipToClipConvert
	 * @Description: 裁剪封面图
	 * @param
	 * @return
	 * @throws
	 */
	private void skipToClipConvert(ImageView mIvConvertImage2,
			ConVertBean convertBean2) {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		getCurrentStoryId();
		ClipViewDialogFragment fragment = new ClipViewDialogFragment(this,
				wishDetailAdapter, convertBean2, currentStoryId, mIvConvertImage2);
		instanstFragment(fragmentManager, fragment);
	}

	/**
	 * 
	 * @Title: skipPlayVideoView
	 * @Description: 跳转到播放视频的界面
	 * @param
	 * @return
	 * @throws
	 */
	private void skipPlayVideoView(String videoUrl) {
		Intent intent = new Intent(getActivity(), VideoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(ConstantsTooTooEHelper.VIDEO_URL, videoUrl);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
		startActivity(intent);

	}

	private void justIfCanPlay() {

		if (convertBean != null) {
			String strVideoUrl = convertBean.getStoryVideoUrl();
			String strStoryType = convertBean.getStoryType();
			if (!TextUtils.isEmpty(strVideoUrl)
					&& !TextUtils.isEmpty(strStoryType)
					&& strStoryType.equals("3")) {// 视频
				skipPlayVideoView(strVideoUrl);
			}

		}

	}

	/**
	 * 
	 * @Title: skiptoClipFragment
	 * @Description: 跳转到裁剪
	 * @param
	 * @return
	 * @throws
	 */
	public void skiptoClipFragment(StoryDetailListBean sdlbean, String storyId,
			View view) {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		ClipViewDialogFragment fragment = new ClipViewDialogFragment(this,
				wishDetailAdapter, sdlbean, storyId, view);
		instanstFragment(fragmentManager, fragment);
	}

	/**
	 * 
	 * @Title: instanstFragment
	 * @Description: 实例化fragment
	 * @param
	 * @return
	 * @throws
	 */
	private void instanstFragment(FragmentManager fragmentManager,
			ClipViewDialogFragment fragment) {
		if (fragmentManager != null) {
			// 屏幕较小，以全屏形式显示
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			// 指定一个过渡动画
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.addToBackStack(null);
			// transaction.attach(newFragment);
			fragment.show(fragmentManager, "dialog");
		}
	}

	/**
	 * 
	 * @Title: skipToEditextFragmentDialog
	 * @Description: 编辑文字
	 * @param
	 * @return
	 * @throws
	 */
	public void skipToEditextFragmentDialog(StoryDetailListBean sdlbean,
			String storyId) {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		TextEditextDialogFragment fragment = new TextEditextDialogFragment(
				this, wishDetailAdapter, sdlbean, storyId);
		if (fragmentManager != null) {
			// 屏幕较小，以全屏形式显示
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			// 指定一个过渡动画
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.addToBackStack(null);
			// transaction.attach(newFragment);
			fragment.show(fragmentManager, "dialog");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, final View view,
			final int position, long id) {

		final StoryDetailListBean storyDetailListBean = (StoryDetailListBean) wishDetailAdapter
				.getItem(position);
		final String pageType = storyDetailListBean.getPageType();

		if (!TextUtils.isEmpty(pageType)) {
			spw = new StoryItemPopupWindow(
					TootooPlusApplication.getAppContext(), pageType);
			spw.setOutSideTouch(true);
			spw.show(view);
			spw.setPopWindowItemClickListener(new PopWindowItemClickListener() {

				@Override
				public void popWinOneClick() {
					if (pageType.equals("1")) {
						getCurrentStoryId();
						skipToEditextFragmentDialog(storyDetailListBean,
								currentStoryId);

						//

					} else if (pageType.equals("2")) {

						changeStoryItem(storyDetailListBean, position, view);

					} else {// 视频的时候删除
							// 删除
						deleStory(storyDetailListBean, position);
					}

				}

				@Override
				public void popWinTwoClick() {
					if (pageType.equals("1")) {
						// 删除
						// 删除
						deleStory(storyDetailListBean, position);

					} else if (pageType.equals("2")) {
						getCurrentStoryId();
						skiptoClipFragment(storyDetailListBean, currentStoryId, view);

					} else {

					}

				}

				@Override
				public void popWinThreeClick() {
					if (pageType.equals("1")) {

					} else if (pageType.equals("2")) {
						// 删除
						deleStory(storyDetailListBean, position);
					} else {
						// 删除
						deleStory(storyDetailListBean, position);
					}

				}

			});

		}

	}

	/**
	 * 
	 * @Title: changeStoryItem
	 * @Description: 变换长方形正方形
	 * @param
	 * @return
	 * @throws
	 */
	protected void changeStoryItem(StoryDetailListBean storyDetailListBean,
			int position, View view) {

		ViewHolder viewHolder = (ViewHolder) view.getTag();
		int mSpans = storyDetailListBean.getElementType();

		if (mSpans == 1) {// 长方形变正方形-1
			setRefreshAdapter(mSpans, position, storyDetailListBean);
		} else if (mSpans == 2) {// 正方形编长方形+1
			setRefreshAdapter(mSpans, position, storyDetailListBean);
		}
		wishDetailAdapter.notifyDataSetChanged();

	}

	/**
	 * 
	 * @Title: setRefreshAdapter
	 * @Description: TODO
	 * @param IsRect
	 *            ==1是长方形 2正方形
	 * @return
	 * @throws
	 */
	public void setRefreshAdapter(int IsRect, int position,
			StoryDetailListBean storyDetailListBean) {
		if(wishDetailAdapter!=null){
			List<StoryDetailListBean> mList = wishDetailAdapter.mListDragBean;
			if(mList!=null){
				if (IsRect == 1) {// 长变正
					int prePostion = position - 1;
					int nextPosition = position + 1;
					storyDetailListBean.setElementType(2);
					if (prePostion < 0) {// 说明是第一个
						rectChangeToSquare(mList, nextPosition);
					} else if (prePostion >= 0) {
						int elementType = mList.get(prePostion).getElementType();
						int localtion = mList.get(prePostion).getLocation();
						if (elementType == 2 && localtion == 2) {// 如果前面的是正方形 并且位于右边
							rectChangeToSquare(mList, nextPosition);
						} else if (elementType == 2 && localtion == 1) {// 如果前面的是正方形
																		// 并且位于左边
							for (int i = 0; i < mList.size(); i++) {
								StoryDetailListBean item = mList.get(i);
								if (i >= position) {// 大于等于当前的都要-1
									int nextPositionItemIndex = item.getItemIndex();
									item.setItemIndex(nextPositionItemIndex - 1);
								}

							}
						} else if (elementType == 1) {// 前面的是长方形
							rectChangeToSquare(mList, nextPosition);
						}

					}

				} else if (IsRect == 2) {
					int currentLocation = storyDetailListBean.getLocation();
					storyDetailListBean.setElementType(1);
					if (currentLocation == 2) {// 如果等于2
						for (int i = 0; i < mList.size(); i++) {
							StoryDetailListBean item = mList.get(i);
							if (i >= position) {
								int nextPositionItemIndex = item.getItemIndex();
								item.setItemIndex(nextPositionItemIndex + 1);
							}

						}
					} else if (currentLocation == 1) {
						for (int i = 0; i < mList.size(); i++) {
							StoryDetailListBean item = mList.get(i);
							if (i > position) {
								int nextPositionItemIndex = item.getItemIndex();
								item.setItemIndex(nextPositionItemIndex + 1);
							}

						}

					} else {
						LogUtil.systemlogError("location=", currentLocation + "错误");
					}

				}
				wishDetailAdapter.notifyDataSetChanged();
			}
			
		}
		
	

	}

	private void rectChangeToSquare(List<StoryDetailListBean> mList,
			int nextPosition) {
		for (int i = 0; i < mList.size(); i++) {
			StoryDetailListBean item = mList.get(i);
			if (i >= nextPosition) {
				int nextPositionItemIndex = item.getItemIndex();
				item.setItemIndex(nextPositionItemIndex - 1);
			}

		}
	}

	@Override
	public void onDragItem(int from) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraggingItem(int from, int to) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDropItem(int from, int to) {
		if (from != to) {
			wishPageList.add(to, wishPageList.remove(from));
			wishDetailAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public boolean isDragAndDropEnabled(int position) {
		return true;
	}

	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 1 && arg1 == Activity.RESULT_OK) {

			onCameraDataCallback(arg0, arg1, arg2, isLocalConvert);
		}
	}

	private void onCameraDataCallback(int arg0, int arg1, Intent arg2,
			boolean isLocalConvert2) {
		if (arg0 == 1 && arg1 == Activity.RESULT_OK) {

			Intent intent = new Intent(this.getActivity(),
					PhotoCameraActivity.class);
			skipToActivity(isLocalConvert2, intent);
		}

	}

	@Override
	public void onCalculatePosition(View view, int position, int row, int column) {

		if (wishDetailAdapter != null) {
			List<StoryDetailListBean> localList = wishDetailAdapter.mListDragBean;
			if(localList!=null){
				StoryDetailListBean itemPosition = localList.get(position);
				itemPosition.setItemIndex(row);
				LogUtil.systemlogError("上传参数值",
						"itemindex=" + itemPosition.getItemIndex() + "location="
								+ itemPosition.getLocation() + "elementType="
								+ itemPosition.getElementType() + "imageurl"
								+ itemPosition.getPageImg());
			}
			

		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		mCoolDragAndrDropView.startDragAndDrop();
		return false;
	}

	/**
	 * 
	 * @Title: deleStory
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	protected void deleStory(StoryDetailListBean storyDetailListBean,
			final int position) {
		getCurrentStoryId();
		if ((NetworkUtil.isNetworkAvaliable(getActivity()))) {
			// 显示进度
			RequestParamsNet requestParamsNet = new RequestParamsNet();

			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.STORYID, currentStoryId);
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.PAGE_ID,
					storyDetailListBean.getPageId());
			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.DEL_PAGE_STORY,
					requestParamsNet, new RequestCallBack<String>() {

						private String status;

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String jsonStr = new String(responseInfo.result);
							try {
								JSONObject jsobj = new JSONObject(jsonStr);
								if (jsobj.has("Status")) {
									status = jsobj.getString("Status");
									if (status.equals("1")) {
										wishPageList.remove(position);
										wishDetailAdapter
												.notifyDataSetChanged();

									}

								}

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {
							ComponentUtil
									.showToast(
											getActivity(),
											getActivity()
													.getString(
															R.string.errcode_network_response_timeout));
						}
					});

		} else {
			ComponentUtil.showToast(
					getActivity(),
					getActivity().getString(
							R.string.errcode_network_response_timeout));
		}

	}

	/**
	 * @Title: getCurrentStoryId
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void getCurrentStoryId() {
		CreateActSecondStepActivity activity = (CreateActSecondStepActivity) getActivity();
		if (secondStepStoryBean != null) {
			currentPosition = activity.currentPosition;
			List<CreateActivitySecondBean> storyPageList = secondStepStoryBean
					.getStoryList();
			if (storyPageList != null) {
				CreateActivitySecondBean currentItem = storyPageList
						.get(currentPosition);
				currentStoryId = currentItem.getConvertBean().getStoryId();
			}
		}
	}
}