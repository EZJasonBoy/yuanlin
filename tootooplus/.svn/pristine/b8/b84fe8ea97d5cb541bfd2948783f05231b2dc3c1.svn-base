package com.ninetowns.tootooplus.fragment;

import java.io.File;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.MyTextwatcherUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.VideoActivity;
import com.ninetowns.tootooplus.adapter.DragAdapter;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.ConVertBean;
import com.ninetowns.tootooplus.bean.PhotoSelectOrConvertBean;
import com.ninetowns.tootooplus.bean.StoryDetailListBean;
import com.ninetowns.tootooplus.fragment.CommentDialogFragment.PushListener;
import com.ninetowns.tootooplus.fragment.GoodsTypeDialogFragment.OnScreenGoods;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.StoryWishParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.cooldraganddrop.CoolDragAndDropGridView;
import com.ninetowns.ui.cooldraganddrop.SimpleScrollingStrategy;
import com.ninetowns.ui.cooldraganddrop.SpanVariableGridView.CalculateChildrenPosition;
import com.ninetowns.ui.fragment.BaseFragment;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog.TooDialogCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 
 * @ClassName: CreateWishBaseFragment
 * @Description: 所有的创建心愿故事的基类
 * @author wuyulong
 * @date 2015-1-23 下午4:50:48
 * 
 */
@SuppressLint("InflateParams")
public abstract class CreateWishBaseFragment extends
		BaseFragment<PhotoSelectOrConvertBean, StoryWishParser> implements
		View.OnClickListener, OnScreenGoods,
		CoolDragAndDropGridView.DragAndDropListener, OnItemClickListener,
		OnItemLongClickListener, CalculateChildrenPosition, PushListener {
	public ImageView mIVReturn;// 返回
	public ImageView mIVRight;// 点击发布right
	public ImageView mIVSave;// 保存
	public CheckedTextView mCTMiddle;// 中间显示的文字内容
	public ImageView mIVType;
	// 封面图 当视频的时候只能编辑
	public LinearLayout mLLConvertOneBtn;
	public View mViewLine;// 中间的白线
	public LinearLayout mLLCutBtn;
	// 编辑的文字
	public EditText mEtName;
	// 可拖拽
	public CoolDragAndDropGridView mCoolDragAndDropGridView;
	public ImageView mIVPhoto;// 图片
	public ImageView mIVVideo;// 视频
	public ImageView mIVText;// 文字
	// public RelativeLayout relativeLayoutConvert;// 封面图跟布局
	public ImageView mIVConvertImage;// 封面图的image
	public ScrollView mScrollView;//
	public PopupWindow popupWindow;
	private int screen_width;
	private int screen_height;
	public Bundle bundle;
	private OnApiParamsInterface onApiParamesInterface;
	public PhotoSelectOrConvertBean mConvertAndListWishBean;
	private ConVertBean mConvertBean;
	private RequestParamsNet requestPar;
	private View myView;
	private boolean isLocalConvert;
	private List<StoryDetailListBean> parserResult;
	private String type;
	private LinearLayout dragView;
	private static final int BOTTOM = 1;

	private boolean isBottomTo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getTypeBundle();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myView = inflater.inflate(R.layout.create_wish_base_fragment, null);
		setFindViewById();
		setTitleName();
		setViewOnClickListener();// 注册事件监听器
		createPopWindow();// 创建pop
		getConvertBean();// 获得封面图数据结构
		justIsVideoOrPhoto();// 判断封面图是视频还是图片
		setConvertContent();// 刷新封面图界面
		return myView;
	}

	public void setTitleName() {

	}

	/**
	 * 
	 * @Title: setFindViewById
	 * @Description: 初始化viewid
	 * @param
	 * @return
	 * @throws
	 */
	private void setFindViewById() {
		mIVReturn = (ImageView) myView.findViewById(R.id.iv_left);// 返回
		mIVRight = (ImageView) myView.findViewById(R.id.iv_right);// 点击发布right
		mIVSave = (ImageView) myView.findViewById(R.id.iv_right_second);// 保存
		mCTMiddle = (CheckedTextView) myView.findViewById(R.id.ct_middle);// 中间显示的文字内容
		mIVType = (ImageView) myView.findViewById(R.id.iv_video_icon);
		mLLConvertOneBtn = (LinearLayout) myView.findViewById(R.id.ll_one_btn);
		mViewLine = myView.findViewById(R.id.view_line);// 中间的白线
		mLLCutBtn = (LinearLayout) myView.findViewById(R.id.ll_cut_btn);
		mEtName = (EditText) myView.findViewById(R.id.et_storyname);
		mEtName.setHint("请输入标题");
		mEtName.addTextChangedListener(new MyTextwatcherUtil(
				this.getActivity(), mEtName, null, 20));// 标题
		mCoolDragAndDropGridView = (CoolDragAndDropGridView) myView
				.findViewById(R.id.coolDragAndDropGridView);
		mIVPhoto = (ImageView) myView.findViewById(R.id.iv_photo);// 图片
		mIVVideo = (ImageView) myView.findViewById(R.id.iv_video);// 视频
		mIVText = (ImageView) myView.findViewById(R.id.iv_text);// 文字
		mIVConvertImage = (ImageView) myView.findViewById(R.id.iv_convert);// 封面图的image
		int width = CommonUtil.getWidth(TootooPlusApplication.getAppContext());
		int height = width * 2 / 3;
		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(
				CommonUtil.getWidth(TootooPlusApplication.getAppContext()),
				height);
		mIVConvertImage.setLayoutParams(rlParams);
		mScrollView = (ScrollView) myView.findViewById(R.id.scrollView);//
		dragView = (LinearLayout) myView.findViewById(R.id.ll_dragGridView);
		
		mScrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mEtName.setFocusable(true);
				mEtName.setFocusableInTouchMode(true);
				return false;
			}
		});
		// mCoolDragAndDropGridView.setScrollY(value)
		// mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
	}

	/**
	 * 
	 * @Title: setCollDragGridViewOperate
	 * @Description: 设置拖拽操作属性和监听器
	 * @param
	 * @return
	 * @throws
	 */
	private void setCollDragGridViewOperate() {
		mCoolDragAndDropGridView
				.setScrollingStrategy(new SimpleScrollingStrategy(mScrollView));
		mCoolDragAndDropGridView.setDragAndDropListener(this);
		mCoolDragAndDropGridView.setOnItemLongClickListener(this);
		mCoolDragAndDropGridView.addCalculateChildrenPositionListener(this);
		mCoolDragAndDropGridView.requestCalculateChildrenPositions();// 请求计算
		mCoolDragAndDropGridView.setOnItemClickListener(this);
	}

	/**
	 * 
	 * @Title: setConvertContent
	 * @Description: 填充封面图的内容
	 * @param
	 * @return
	 * @throws
	 */
	private void setConvertContent() {
		if (mConvertBean != null) {
			String strConvertThumb = mConvertBean.getCoverThumb();
			String strStoryName = mConvertBean.getStoryName();
			// String strVideoUrl=mConvertBean.getStoryVideoUrl();
			if(!TextUtils.isEmpty(strConvertThumb)){
				ImageLoader.getInstance().displayImage(strConvertThumb,
						new ImageViewAware(mIVConvertImage),
						CommonUtil.OPTIONS_ALBUM_DETAIL);
			}
		
			if (!TextUtils.isEmpty(strStoryName)) {
				mEtName.setText(strStoryName);
			}
		}

	}

	/**
	 * 
	 * @Title: getTypeBundle
	 * @Description: 获取bundle的值
	 * @param
	 * @return void
	 * @throws
	 */
	private void getTypeBundle() {
		bundle = getActivity().getIntent().getBundleExtra(
				ConstantsTooTooEHelper.BUNDLE);
		type = bundle.getString(TootooeNetApiUrlHelper.TYPE);
		mConvertAndListWishBean = (PhotoSelectOrConvertBean) bundle
				.getSerializable(ConstantsTooTooEHelper.BundleResopnse);
		getBundle(bundle);
	}

	/**
	 * 
	 * @Title: getBundle
	 * @Description: 子类可通过getBundle方法获取bundle
	 * @param
	 * @return
	 * @throws
	 */
	public void getBundle(Bundle bundle) {

	}

	/**
	 * 
	 * @Title: getConvertBean
	 * @Description: 获取封面图的数据结构
	 * @param
	 * @return
	 * @throws
	 */
	private void getConvertBean() {
		if (mConvertAndListWishBean != null) {
			mConvertBean = mConvertAndListWishBean.getConvertBean();

		}
	}

	/**
	 * 
	 * @Title: justIsVideoOrPhoto
	 * @Description: 判断封面图是视频还是图片显示按钮
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	private void justIsVideoOrPhoto() {
		if (mConvertBean != null) {
			if (mConvertBean.getStoryType() != null
					&& mConvertBean.getStoryType().equals("2")) {// 如果是2
																	// 那么是图片显示两个控件
				mViewLine.setVisibility(View.VISIBLE);
				mLLCutBtn.setVisibility(View.VISIBLE);
				mLLConvertOneBtn.setVisibility(View.VISIBLE);
				mIVType.setVisibility(View.INVISIBLE);
			} else {// 一个控件是视频
				mViewLine.setVisibility(View.GONE);
				mLLCutBtn.setVisibility(View.GONE);
				mLLConvertOneBtn.setVisibility(View.VISIBLE);
				mIVType.setVisibility(View.VISIBLE);
			}

		}

	}

	/**
	 * 
	 * @Title: createPopWindow
	 * @Description: 创建popwindow
	 * @param
	 * @return void
	 * @throws
	 */
	private void createPopWindow() {
		popupWindow = new PopupWindow();
		// 屏幕宽度
		screen_width = CommonUtil.getWidth(getActivity());
		// 屏幕高度
		screen_height = CommonUtil.getHeight(getActivity());

	}

	protected void setViewOnClickListener() {
		mIVReturn.setOnClickListener(this);// 返回
		mIVPhoto.setOnClickListener(this);// 图片
		mIVRight.setOnClickListener(this);// 发布
		mIVSave.setOnClickListener(this);// 保存草稿
		mIVText.setOnClickListener(this);// 文字
		mIVVideo.setOnClickListener(this);// 视频
		mLLConvertOneBtn.setOnClickListener(this);// 封面图是视频的时候编辑
		mIVConvertImage.setOnClickListener(this);
		mLLCutBtn.setOnClickListener(this);// 裁剪
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isBottomTo) {
			mScrollView.post(new Runnable() {

				@Override
				public void run() {
					mEtName.clearFocus();
					mEtName.setFocusable(false);
					mEtName.setFocusableInTouchMode(false);
					onScroolState(mScrollView);
				}
			});
		}
	}
	public abstract void onScroolState(ScrollView mScrollView);
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (isGetNetData()) {
			onLoadData(true, false, false);
		} else {
			if (mConvertAndListWishBean != null) {

				List<StoryDetailListBean> adapterListBean = mConvertAndListWishBean
						.getListBean();
				if (adapterListBean != null && adapterListBean.size() > 0) {
					DragAdapter adapter = initAdapter(adapterListBean);
					showCreateDropDialog();
					adapter.setConvertAndListWishBean(mConvertAndListWishBean);
					mCoolDragAndDropGridView.setAdapter(adapter);
					setCollDragGridViewOperate();
					isBottomTo = true;
				} else {
					LogUtil.error("创建的拖拽数据是null", "");
				}

			}

		}
	}

	/**
	 * 
	 * @Title: isGetNetData
	 * @Description:是否是从网络获取
	 * @param
	 * @return
	 * @throws
	 */
	public abstract boolean isGetNetData();

	@Override
	public StoryWishParser setParser(String str) {
		StoryWishParser storyWishParser = new StoryWishParser(str);
		if (onUpdateStoryIdListener != null) {
			String parentStoryId = storyWishParser.getStoryId();
			String parentUpdateStoryId = storyWishParser.getUpdateStoryId();
			if (!TextUtils.isEmpty(parentUpdateStoryId)
					&& !TextUtils.isEmpty(parentStoryId)) {
				onUpdateStoryIdListener.onUpdateStoryIdListener(parentStoryId,
						parentUpdateStoryId);
			}
		}

		return storyWishParser;
	}

	@Override
	public void getParserResult(PhotoSelectOrConvertBean bean) {
		this.mConvertAndListWishBean = bean;
		if (mConvertAndListWishBean != null) {
			parserResult = bean.getListBean();
			mConvertBean = bean.getConvertBean();
			getConvertComment(mConvertBean);
		}
		// 往adapter中装载数据
		if (parserResult != null && parserResult.size() > 0) {
			showCreateDropDialog();
			DragAdapter adapter = initAdapter(parserResult);
			mCoolDragAndDropGridView.setAdapter(adapter);
			setCollDragGridViewOperate();

		} else {
			LogUtil.error("获取的数据是null", "");
		}
		if (isGetNetData()) {
			setConvertContent();
		}

	}

	private void showCreateDropDialog() {
		boolean isFirst = SharedPreferenceHelper
				.getFirstGuideDrop(getActivity());
		if (isFirst) {// 如果第一次
			CommonUtil.showFirstGuideDialog(getActivity(),
					ConstantsTooTooEHelper.FIRST_GUIDE_DROP);
		}

	}

	/**
	 * 
	 * @Title: getConvertComment
	 * @Description: 编辑评论的时候覆盖
	 * @param
	 * @return
	 * @throws
	 */
	protected void getConvertComment(ConVertBean mConvertBean) {

	}

	/*********** 初始化拖拽 ***********/
	public abstract DragAdapter initAdapter(List<StoryDetailListBean> list);

	@Override
	public RequestParamsNet getApiParmars() {
		if (onApiParamesInterface != null) {
			requestPar = onApiParamesInterface.getRequestParamsNet(bundle);
		}

		return requestPar;
	}

	/**
	 * 
	 * @ClassName: OnApiParamrsInterface
	 * @Description: 获取接口参数的接口
	 * @author wuyulong
	 * @date 2015-1-28 上午10:34:37
	 * 
	 */
	public interface OnApiParamsInterface {
		public RequestParamsNet getRequestParamsNet(Bundle bundle);
	}

	/**
	 * 
	 * @Title: setOnApkParamsListener
	 * @Description: 设置参数监听器
	 * @param
	 * @return
	 * @throws
	 */
	public void setOnApkParamsListener(
			OnApiParamsInterface onApiParamesInterface) {
		this.onApiParamesInterface = onApiParamesInterface;
	}

	/**
	 * 
	 * @Title: skipToGoodsType
	 * @Description: 跳转到商品分类,
	 * @param
	 * @return
	 * @throws
	 */
	@SuppressLint("Recycle")
	public void skipToGoodsType() {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		GoodsTypeDialogFragment fragment = new GoodsTypeDialogFragment(
				mConvertBean);
		fragment.setOnScreenGoods(this);
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

	@SuppressLint("Recycle")
	public void skipToCommentType() {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		CommentDialogFragment fragment = createPushCommentDialog();
		fragment.setPushListener(this);
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
	 * @Title: createPushComment
	 * @Description: 创建发布点评
	 * @param
	 * @return
	 * @throws
	 */
	protected CommentDialogFragment createPushCommentDialog() {
		return new CommentDialogFragment();
	}

	/**
	 * 
	 * @Title: backtosave
	 * @Description: 返回
	 * @param
	 * @return
	 * @throws
	 */
	private void backtosave() {

		TooSureCancelDialog tooDialog = new TooSureCancelDialog(
				this.getActivity());
		tooDialog.setDialogTitle(R.string.notice_title);
		tooDialog.setDialogContent(R.string.isdraft);
		tooDialog.setTooDialogCallBack(new TooDialogCallBack() {
			@Override
			public void onTooDialogSure() {
				onDialogSure();

			}

			@Override
			public void onTooDialogCancel() {
				getActivity().finish();

			}
		});
		tooDialog.show();

	}

	public abstract void onDialogSure();

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.iv_left:// 返回
			backtosave();
			break;
		case R.id.iv_right:// 发布
			if(mConvertBean!=null){
				String coverThumb=mConvertBean.getCoverThumb();
				if(!TextUtils.isEmpty(coverThumb)){//封面图为null
					String content = mEtName.getText().toString();
					if (!TextUtils.isEmpty(content)) {
						// 跳转到一个fragmentDialog
						if (type.equals("0")) {// 心愿
							skipToGoodsType();
						} else if (type.equals("1")) {// 点评
							justCanPushData();
						}

					} else {
						ComponentUtil.showToast(getActivity(),
								TootooPlusApplication.getAppContext().getResources()
										.getString(R.string.plase_input_title));
					}
				}else{
					if(isAdded()){
						ComponentUtil.showToast(getActivity(), "您还没有上传封面图");
					}
					
				}
				
			}
			

			break;
		case R.id.iv_right_second:// 草稿
			saveDraft();
			break;
		case R.id.ll_one_btn:// 编辑
			showSelectPopup("pic", true, true);

			break;
		case R.id.ll_cut_btn:// 裁剪 封面图
			skipToClipConvert(mIVConvertImage, mConvertBean);

			break;
		case R.id.iv_photo:// 图片
			showSelectPopup("pic", true, false);
			break;
		case R.id.iv_text:// 文字
			showSelectPopup("text", false, false);
			break;
		case R.id.iv_video:// 视频
			skipVideoView(false);
			break;
		case R.id.iv_convert:// 封面图

			justIfCanPlay();
			break;

		}
	}

	/**
	 * 
	 * @Title: justCanPushData
	 * @Description: 判断是否可以创建点评
	 * @param
	 * @return
	 * @throws
	 */
	protected void justCanPushData() {

	}

	public abstract void saveDraft();

	public abstract void skipToClipConvert(ImageView convertImage,
			ConVertBean convertBean);

	/**
	 * 
	 * @Title: skipVideoView
	 * @Description: 跳转到视频
	 * @param
	 * @return
	 * @throws
	 */
	public abstract void skipVideoView(boolean convert);

	/**
	 * 
	 * @Title: justIfCanPlay
	 * @Description: 判断封面图是否可以播放
	 * @param
	 * @return
	 * @throws
	 */
	private void justIfCanPlay() {
		if (mConvertBean != null) {
			String strVideoUrl = mConvertBean.getStoryVideoUrl();
			String strStoryType = mConvertBean.getStoryType();
			if (!TextUtils.isEmpty(strVideoUrl)
					&& !TextUtils.isEmpty(strStoryType)
					&& strStoryType.equals("3")) {// 视频
				skipPlayVideoView(strVideoUrl);
			}

		}
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
			// LinearLayout ll_video = (LinearLayout) view
			// .findViewById(R.id.ll_video);
			// View v_rec_video = view.findViewById(R.id.v_rec_video);
			//
			// if (isConvert) {
			// ll_video.setVisibility(View.VISIBLE);
			// v_rec_video.setVisibility(View.VISIBLE);
			// } else {
			// ll_video.setVisibility(View.GONE);
			// v_rec_video.setVisibility(View.GONE);
			// }
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
								intent.putExtra("return-data", true);
								intent.putExtra(ImageColumns.ORIENTATION, 0);
								intent.putExtra("storyType", "2");
								startActivityForResult(intent, 1);
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
			// ll_video.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // 视频
			// skipVideoView(true);
			// // skipPlayVideoView();
			//
			// }
			// });

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
	 * @Title: OperateStoryBaseFragment.java
	 * @Description: TODO
	 * @author wuyulong
	 * @date 2014-12-25 上午10:11:09
	 * @param isTitle
	 *            true标题 false正文
	 * @return void
	 */
	public abstract void skipTextEditView(boolean isTitle, boolean isConvert);

	/**
	 * 
	 * @Title: skipLocalPhotoView
	 * @Description: 本地选择相册
	 * @param
	 * @return
	 * @throws
	 */
	public abstract void skipLocalPhotoView(boolean isConvert);

	/**
	 * 
	 * @Title: skipInternetCollect
	 * @Description: 从网址中采集图片
	 * @param
	 * @return
	 * @throws
	 */
	public abstract void skipInternetCollect(boolean isConvert);

	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 1 && arg1 == Activity.RESULT_OK) {

			onCameraDataCallback(arg0, arg1, arg2, isLocalConvert);
		}
	}

	/**
	 * 
	 * @Title: OperateStoryBaseFragment.java
	 * @Description: 拍照数据回调函数
	 * @author wuyulong
	 * @date 2014-12-16 上午10:29:43
	 * @param
	 * @return void
	 */
	public abstract void onCameraDataCallback(int arg0, int arg1, Intent arg2,
			boolean isConvert);

	/**
	 * 
	 * @Title: savestoryPageSory
	 * @Description:保存结构
	 * @param
	 * @return
	 * @throws
	 */
	public void savestoryPageSory(String storyId, String pagelist) {

		if ((NetworkUtil.isNetworkAvaliable(this.getActivity()))) {
			// 显示进度
			showProgressDialog();
			RequestParamsNet requestParamsNet = new RequestParamsNet();

			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.STORYCREATE_STORYID, storyId);
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.PageList, pagelist);// 2是图片3是视频
			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.PAGE_SORT,
					requestParamsNet, new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							closeProgressDialogFragment();
							String strRespone = responseInfo.result;
							if (!TextUtils.isEmpty(strRespone)) {
								try {
									JSONObject jsobj = new JSONObject(
											strRespone);
									if (jsobj.has("Status")) {
										String status = jsobj
												.getString("Status");
										savestatus(status);

									}

								} catch (JSONException e) {
									LogUtil.error("CreateStoryFragment",
											e.toString());
									e.printStackTrace();
								}
							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {
							closeProgressDialogFragment();
							ComponentUtil
									.showToast(
											getActivity(),
											getResources()
													.getString(
															R.string.errcode_network_response_timeout));
						}
					});

		} else {
			ComponentUtil.showToast(getActivity(), this.getResources()
					.getString(R.string.errcode_network_response_timeout));
		}

	}

	public void savestatus(String status) {

	}

	public void saveDraftStatus(String status) {

	}

	private String upstoryid;
	private OnUpdateStoryIdListener onUpdateStoryIdListener;

	/**
	 * 
	 * @Title: saveStoryToDraft
	 * @Description: 保存为草稿
	 * @param
	 * @return
	 * @throws
	 */
	public void saveStoryToDraft(String storyid, String upstoryid) {
		this.upstoryid = upstoryid;
		saveStoryToDraft(storyid);
	}

	public void saveStoryToDraft(String storyid) {

		if ((NetworkUtil.isNetworkAvaliable(this.getActivity()))) {
			// 显示进度
			showProgressDialog();
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.STORYCREATE_STORYID, storyid);
			if (!TextUtils.isEmpty(upstoryid)) {
				requestParamsNet.addQueryStringParameter(
						TootooeNetApiUrlHelper.STORYCREATE_UPDATE_STORYID,
						upstoryid);

			} else {
				LogUtil.systemlogError("保存草稿的时候upstoryid=", "null");
			}
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.USER_ID, SharedPreferenceHelper
							.getLoginUserId(TootooPlusApplication
									.getAppContext()));
			CommonUtil.xUtilsPostSend(
					TootooeNetApiUrlHelper.STORY_DETAIL_SAVE_TO_DRAFT,
					requestParamsNet, new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							closeProgressDialogFragment();
							String strRespone = responseInfo.result;
							if (!TextUtils.isEmpty(strRespone)) {
								try {
									JSONObject jsobj = new JSONObject(
											strRespone);
									if (jsobj.has("Status")) {
										String status = jsobj
												.getString("Status");
										saveDraftStatus(status);

									}

								} catch (JSONException e) {
									LogUtil.error("CreateStoryFragment",
											e.toString());
									e.printStackTrace();
								}
							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {
							closeProgressDialogFragment();
							ComponentUtil
									.showToast(
											getActivity(),
											getResources()
													.getString(
															R.string.errcode_network_response_timeout));
						}
					});

		} else {
			ComponentUtil.showToast(getActivity(), this.getResources()
					.getString(R.string.errcode_network_response_timeout));
		}

	}

	/**
	 * 
	 * @ClassName: OnUpdateStoryIdListener
	 * @Description: 编辑或者草稿返回过来的storyid和updateStoryId监听器
	 * @author wuyulong
	 * @date 2015-2-28 下午3:09:14
	 * 
	 */
	public interface OnUpdateStoryIdListener {
		public void onUpdateStoryIdListener(String storyId, String updateStoryId);

	}

	/**
	 * 
	 * @Title: setOnUpdateStoryIdListener
	 * @Description: 注册编辑或者草稿返回过来的storyid和updateStoryId监听器
	 * @param
	 * @return
	 * @throws
	 */
	public void setOnUpdateStoryIdListener(
			OnUpdateStoryIdListener onUpdateStoryIdListener) {
		this.onUpdateStoryIdListener = onUpdateStoryIdListener;

	}
}
