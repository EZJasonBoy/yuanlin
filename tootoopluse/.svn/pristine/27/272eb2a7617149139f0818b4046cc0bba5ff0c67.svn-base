package com.ninetowns.tootoopluse.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.baidu.cyberplayer.core.BVideoView;
import com.baidu.cyberplayer.core.BVideoView.OnCompletionListener;
import com.baidu.cyberplayer.core.BVideoView.OnErrorListener;
import com.baidu.cyberplayer.core.BVideoView.OnInfoListener;
import com.baidu.cyberplayer.core.BVideoView.OnPlayingBufferCacheListener;
import com.baidu.cyberplayer.core.BVideoView.OnPreparedListener;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;

/**
 * 
 * @ClassName: VideoFragemnt
 * @Description: 视频播放处理
 * @author wuyulong
 * @date 2015-3-4 上午11:12:01
 * 
 */
public class VideoFragment extends Fragment implements OnClickListener,
		OnPreparedListener, OnCompletionListener, OnErrorListener,
		OnInfoListener, OnPlayingBufferCacheListener {

	// private RelativeLayout rl_video;
	private final String TAG = "StoryDetailVideoFragment";
	private String akCode = ConstantsTooTooEHelper.AK;
	private String skCode = ConstantsTooTooEHelper.SK;
	private View view;
	private FragmentActivity activity;
	private BVideoView surfaceView;
	private View mRlseekbar;
	private ImageView mImagePlay;
	private ProgressBar sfv_progressbar;
	private SeekBar mSeekbar;
	private TextView mTvHasPlayed;
	private TextView mTvDuration;
	private ImageView mIvPlayBackground;
	private ImageView mIvSurfaceBackground;
	private ImageView mIvBig;
	private ImageView mIvVoice;
	private ImageView mIbtnPlayOrPause;
	private RelativeLayout mVideo;
	private ImageView mImBtnBack;
	private View content;
	private Bundle bundle;
	private String type;
	private final int EVENT_PLAY = 0;
	private final int UI_EVENT_UPDATE_CURRPOSITION = 1;
	private final int UI_EVENT_UPDATE_CURRPOSITION_START = 2;
	private final int UI_EVENT_UPDATE_CURRPOSITION_COMPLEMENT = 3;
	private final int UI_EVENT_UPDATE_CURRPOSITION_PAUSE = 4;
	private PLAYER_STATUS mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
	private final Object SYNC_Playing = new Object();

	class EventHandler extends Handler {
		private String url;

		public EventHandler(Looper looper, String url) {
			super(looper);
			this.url = url;
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case EVENT_PLAY:
				// sfv_progressbar.setVisibility(View.GONE);
				/**
				 * 如果已经播放了，等待上一次播放结束
				 */
				if (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE) {
					synchronized (SYNC_Playing) {
						try {
							SYNC_Playing.wait();
							Log.v(TAG, "wait player status to idle");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				/**
				 * 设置播放url
				 */
				surfaceView.setVideoPath(url);
				/**
				 * 续播，如果需要如此
				 */
				if (mLastPos > 0) {

					surfaceView.seekTo(mLastPos);
					mLastPos = 0;
				}

				/**
				 * 显示或者隐藏缓冲提示
				 */
				surfaceView.showCacheInfo(true);

				/**
				 * 开始播放
				 */
				surfaceView.start();

				mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARING;// 准备播放
				break;
			default:
				break;
			}
		}
	}

	private boolean isEnd = false;
	/**
	 * 更新时间
	 */
	Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			/**
			 * 更新进度及时间
			 */
			case UI_EVENT_UPDATE_CURRPOSITION:

				int currPosition = surfaceView.getCurrentPosition();
				int duration = surfaceView.getDuration();
				updateTextViewWithTimeFormat(mTvHasPlayed, currPosition);
				updateTextViewWithTimeFormat(mTvDuration, duration);
				mSeekbar.setMax(duration);
				mSeekbar.setProgress(currPosition);
				mUIHandler.sendEmptyMessageDelayed(
						UI_EVENT_UPDATE_CURRPOSITION, 200);
				break;
			case UI_EVENT_UPDATE_CURRPOSITION_START:
				sfv_progressbar.setVisibility(View.GONE);
				mIvPlayBackground.setVisibility(View.INVISIBLE);
				mIbtnPlayOrPause
						.setImageResource(R.drawable.media_player_pause_button_on);
				mIvSurfaceBackground.setVisibility(View.GONE);
				break;
			case UI_EVENT_UPDATE_CURRPOSITION_COMPLEMENT:// 完成
				mUIHandler.removeMessages(UI_EVENT_UPDATE_CURRPOSITION);
				mIbtnPlayOrPause.setImageResource(R.drawable.btn_play_start);
				mIvPlayBackground.setVisibility(View.VISIBLE);
				isEnd = true;
			case UI_EVENT_UPDATE_CURRPOSITION_PAUSE:
				mUIHandler.removeMessages(UI_EVENT_UPDATE_CURRPOSITION);
				mIbtnPlayOrPause.setImageResource(R.drawable.btn_play_start);
				mIvPlayBackground.setVisibility(View.VISIBLE);
				isEnd = false;// 此时暂停
				break;
			}
		}
	};
	private boolean bJustPlay = true;

	private void updateTextViewWithTimeFormat(TextView view, int second) {
		int hh = second / 3600;
		int mm = second % 3600 / 60;
		int ss = second % 60;
		String strTemp = null;
		if (0 != hh) {
			strTemp = String.format("%02d:%02d:%02d", hh, mm, ss);
		} else {
			strTemp = String.format("%02d:%02d", mm, ss);
		}
		view.setText(strTemp);
	}

	private void orientationLand() {
		if (bJustPlay) {
			setVisable();
			// mHandler.sendEmptyMessageDelayed(FIVE_SEC_HINDE, FIVE_SEC);//
			// 如果刚进入 则显示时间轴 和标题栏，开启handler 五秒后隐藏,暂时先去掉
		} else {
			setHide();
		}
	}

	private void setHide() {
		mRlseekbar.setVisibility(View.GONE);
		bJustPlay = true;
	}

	private void setVisable() {
		mRlseekbar.setVisibility(View.VISIBLE);
		bJustPlay = false;
	}

	/**
	 * 记录播放位置
	 */
	private int mLastPos = 0;
	private WakeLock mWakeLock = null;
	private HandlerThread mHandlerThread;
	private EventHandler mEventHandler;
	private boolean mIsHwDecode;
	private String videoUrl;

	/**
	 * 播放状态
	 */
	private enum PLAYER_STATUS {
		PLAYER_IDLE, PLAYER_PREPARING, PLAYER_PREPARED,
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*** 电源管理 ***/
		PowerManager pm = (PowerManager) this.getActivity().getSystemService(
				Context.POWER_SERVICE);
		/** 这个方法将创建WakeLock对象，通过调用此对象的方法你就可以方便的去控制电源的状态。方法如下： **/
		mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ON_AFTER_RELEASE, TAG);
		getType();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.story_detail_item_type_video_fragment,
				null);
		activity = getActivity();
		initView(view);
		initListener();
		return view;
	}

	@SuppressWarnings("deprecation")
	private void initView(View view) {
		// mTvVideoTitle = (TextView) view.findViewById(R.id.tv_video_title);
		mRlseekbar = view.findViewById(R.id.rl_seekbar);
		mImagePlay = (ImageView) view.findViewById(R.id.iv_video_play);
		surfaceView = (BVideoView) view.findViewById(R.id.sfv_video);
		sfv_progressbar = (ProgressBar) view.findViewById(R.id.sfv_progressbar);
		sfv_progressbar.setVisibility(View.GONE);
		mSeekbar = (SeekBar) view.findViewById(R.id.seekBar);
		mTvHasPlayed = (TextView) view.findViewById(R.id.has_played);
		mTvDuration = (TextView) view.findViewById(R.id.duration);

		mIvPlayBackground = (ImageView) view
				.findViewById(R.id.iv_play_background);
		mIvSurfaceBackground = (ImageView) view
				.findViewById(R.id.iv_surface_background);
		mIvBig = (ImageView) view.findViewById(R.id.btn_big);
		mIvVoice = (ImageView) view.findViewById(R.id.btn_voice);

		mIbtnPlayOrPause = (ImageView) view.findViewById(R.id.iv_play);
		mIbtnPlayOrPause
				.setImageResource(R.drawable.media_player_pause_button_on);
		mVideo = (RelativeLayout) view.findViewById(R.id.rl_weight);
		mImBtnBack = (ImageView) getActivity().findViewById(R.id.iv_back);// 后退
		// mImBtnBack.setImageResource(R.drawable.btn_return);

		content = this.getActivity().findViewById(R.id.rl_content);

		mIsHwDecode = this.getActivity().getIntent()
				.getBooleanExtra("isHW", false);
		// setBlackTitle();
		BVideoView.setAKSK(akCode, skCode);// 设置百度申请的码
		/**
		 * 设置解码模式
		 */
		surfaceView.setDecodeMode(mIsHwDecode ? BVideoView.DECODE_HW
				: BVideoView.DECODE_SW);
	}

	public void getType() {
		try {
			bundle = this.getActivity().getIntent()
					.getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
			videoUrl = bundle.getString(ConstantsTooTooEHelper.VIDEO_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getVideoData();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (null != mWakeLock && (!mWakeLock.isHeld())) {
			mWakeLock.acquire();
		}

	}

	public void getVideoData() {

		if (!TextUtils.isEmpty(videoUrl)) {
			LogUtil.info("播放地址", videoUrl);
			setHandlerThread(videoUrl);
		}

		/*
		 * 
		 * if ((NetworkUtils.isNetworkAvaliable(this.getActivity()))) {
		 * 
		 * if ((NetworkUtils.isNetworkAvaliable(this.getActivity()))) {// 有网络
		 * 
		 * RequestParams params = new RequestParams();
		 * params.put(NetConstants.STORY_PAGE_DETAIL_PAGEID, pageid);
		 * params.put(NetConstants.STORY_PAGE_DETAIL_STORYID, storyid);
		 * NetUtil.get(NetConstants.STORY_PAGE_DETAIL_API, params, new
		 * AsyncHttpResponseHandler() {
		 * 
		 * private StoryDetailTypeModelRespone response; private String mp4url;
		 * 
		 * @Override public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
		 * { if (arg2 != null) { String objstr = StringUtils.ByteToString(arg2);
		 * try { JSONObject obj = new JSONObject(objstr); if (obj.has("Status"))
		 * { int status = obj.getInt("Status"); if (status == 1) { // 成功 } else
		 * { return; } if (obj.has("Data")) { String data =
		 * obj.getString("Data"); JSONObject objjSON = new JSONObject(data);
		 * response = new StoryDetailTypeModelRespone(objjSON);
		 * List<StoryDetailTypeBean> list = response.getList(objjSON);
		 * StoryDetailTypeBean typebean = list.get(0); if
		 * (!StringUtils.isEmpty(typebean.getPageName())) {// 故事页标题 //
		 * mTvVideoTitle.setText(typebean.getPageName()); } if
		 * (!StringUtils.isEmpty(typebean.getPageVideoUrl())) {// 故事页标题 mp4url =
		 * typebean.getPageVideoUrl(); if (!StringUtils.isEmpty(mp4url)) {
		 * LogUtil.info("播放地址", mp4url); setHandlerThread(mp4url); }
		 * 
		 * }
		 * 
		 * } }
		 * 
		 * } catch (JSONException e) { e.printStackTrace(); }
		 * 
		 * } }
		 * 
		 * @Override public void onFailure(int arg0, Header[] arg1, byte[] arg2,
		 * Throwable arg3) { LogUtil.error("getVideoData", arg3.toString()); }
		 * });
		 * 
		 * } else { ComponentUtil.showToast(getActivity(),
		 * this.getActivity().getResources
		 * ().getString(R.string.errcode_network_response_timeout)); }
		 * 
		 * } else { ComponentUtil.showToast(getActivity(),
		 * this.getActivity().getResources
		 * ().getString(R.string.errcode_network_response_timeout)); }
		 */}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.iv_video_play:
			// mImagePlay.setVisibility(View.VISIBLE);
			// mImagePlay.setBackgroundResource(R.drawable.play_circle_normal);
			// mImagePlay.setVisibility(View.GONE);
			break;
		case R.id.sfv_video:
			orientationLand();
			break;
		case R.id.iv_play:

			if (surfaceView.isPlaying()) {
				pauseVideo();
				/**
				 * 暂停播放
				 */
				surfaceView.pause();

			} else {
				playVideo();

				if (isEnd) {
					/**
					 * 如果已经播放了，等待上一次播放结束
					 */
					if (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE) {
						surfaceView.stopPlayback();
					}

					/**
					 * 发起一次新的播放任务
					 */
					if (mEventHandler.hasMessages(EVENT_PLAY)) {
						mEventHandler.removeMessages(EVENT_PLAY);
					}

					mEventHandler.sendEmptyMessage(EVENT_PLAY);
				} else {
					/**
					 * 继续播放,恢复播放
					 */
					if (!mUIHandler.hasMessages(UI_EVENT_UPDATE_CURRPOSITION)) {
						mUIHandler
								.sendEmptyMessage(UI_EVENT_UPDATE_CURRPOSITION);
						/**
						 * 发起一次新的播放任务
						 */
						mEventHandler.sendEmptyMessage(EVENT_PLAY);
					} else {
						surfaceView.resume();
					}

				}

			}
			break;
		case R.id.btn_big:
			getActivity().finish();

			break;
		case R.id.iv_back:
			// 如果是横屏则
			getActivity().finish();
			break;
		case R.id.rl_weight:
			orientationLand();
			break;

		}

	}

	private long mTouchTime;
	private boolean barShow = true;
	private boolean isChange = false;

	public void updateControlBar(boolean show) {
		if (show) {
			mRlseekbar.setVisibility(View.VISIBLE);
			// titleBar.setVisibility(View.VISIBLE);
		} else {
			mRlseekbar.setVisibility(View.GONE);
			// titleBar.setVisibility(View.INVISIBLE);
		}
		barShow = show;
	}

	/**
	 * 
	 * @Title: AlreadyLoginedVideoPlayFragment.java
	 * @Description: vlc 播放
	 * @author wuyulong
	 * @date 2014-7-18 下午4:33:27
	 * @param
	 * @return void
	 */
	private void playVideo() {

		mIvPlayBackground.setVisibility(View.INVISIBLE);
		mIbtnPlayOrPause
				.setImageResource(R.drawable.media_player_pause_button_on);
	}

	/**
	 * 暂停
	 */
	public void pauseVideo() {
		mIbtnPlayOrPause.setImageResource(R.drawable.btn_play_start);
		mIvPlayBackground.setVisibility(View.VISIBLE);
	}

	public void setHandlerThread(String url_video) {
		/**
		 * 开启后台事件处理线程
		 */
		mHandlerThread = new HandlerThread("event handler thread",
				Process.THREAD_PRIORITY_BACKGROUND);
		mHandlerThread.start();
		mEventHandler = new EventHandler(mHandlerThread.getLooper(), url_video);
		/**
		 * 发起一次播放任务,当然您不一定要在这发起
		 */
		mEventHandler.sendEmptyMessage(EVENT_PLAY);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		isChange = true;
	}

	@Override
	public void onPause() {
		super.onPause();

		/**
		 * 在停止播放前 你可以先记录当前播放的位置,以便以后可以续播
		 */
		if (mPlayerStatus == PLAYER_STATUS.PLAYER_PREPARED) {// 如果是暂停则记录
			mLastPos = surfaceView.getCurrentPosition();
			// surfaceView.pause();
			if (isChange) {// 如果是横竖屏qie'h

			} else {
				mUIHandler.removeMessages(UI_EVENT_UPDATE_CURRPOSITION);
				surfaceView.stopPlayback();
			}

		}
	}

	OnSeekBarChangeListener osbc1 = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			// Log.v(TAG, "progress: " + progress);
			updateTextViewWithTimeFormat(mTvHasPlayed, progress);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			/**
			 * SeekBar开始seek时停止更新
			 */
			mUIHandler.removeMessages(UI_EVENT_UPDATE_CURRPOSITION);
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			int iseekPos = seekBar.getProgress();
			/**
			 * SeekBark完成seek时执行seekTo操作并更新界面
			 * 
			 */
			surfaceView.seekTo(iseekPos);
			mUIHandler.sendEmptyMessage(UI_EVENT_UPDATE_CURRPOSITION);
		}
	};

	@Override
	public void onDestroyView() {
		super.onDestroyView();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		/**
		 * 退出后台事件处理线程
		 */
		if (mHandlerThread != null) {
			mHandlerThread.quit();
			mHandlerThread = null;
		}

	}

	private void initListener() {
		mImagePlay.setOnClickListener(this);
		surfaceView.setOnClickListener(this);
		mRlseekbar.setOnClickListener(this);
		mIbtnPlayOrPause.setOnClickListener(this);
		mIvBig.setOnClickListener(this);
		mIvVoice.setOnClickListener(this);
		mVideo.setOnClickListener(this);
		mImBtnBack.setOnClickListener(this);
		/**
		 * 注册listener
		 */
		surfaceView.setOnPreparedListener(this);
		surfaceView.setOnCompletionListener(this);
		surfaceView.setOnErrorListener(this);
		surfaceView.setOnInfoListener(this);
		mSeekbar.setOnSeekBarChangeListener(osbc1);

	}

	@Override
	public void onPlayingBufferCache(int arg0) {
	}

	@Override
	public boolean onInfo(int what, int arg1) {

		// TODO Auto-generated method stub
		switch (what) {
		/**
		 * 开始缓冲
		 */
		case BVideoView.MEDIA_INFO_BUFFERING_START:
			break;
		/**
		 * 结束缓冲
		 */
		case BVideoView.MEDIA_INFO_BUFFERING_END:
			break;
		default:
			break;
		}
		return true;

	}

	@Override
	public boolean onError(int arg0, int arg1) {

		LogUtil.systemlogError(TAG, "onError");
		synchronized (SYNC_Playing) {
			SYNC_Playing.notify();
		}
		mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
		mUIHandler.removeMessages(UI_EVENT_UPDATE_CURRPOSITION);
		return true;
	}

	@Override
	public void onCompletion() {// 完成 有两种情况 一种是播放完毕，一种是记录播放的位置，处于暂停

		LogUtil.systemlogError(TAG, "onCompletion");
		synchronized (SYNC_Playing) {
			SYNC_Playing.notify();
		}
		mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
		if (mUIHandler.hasMessages(UI_EVENT_UPDATE_CURRPOSITION)) {// 进度条是否是暂停
			mUIHandler.sendEmptyMessage(UI_EVENT_UPDATE_CURRPOSITION_PAUSE);
		} else {
			mUIHandler
					.sendEmptyMessage(UI_EVENT_UPDATE_CURRPOSITION_COMPLEMENT);
		}

	}

	@Override
	public void onPrepared() {
		LogUtil.systemlogError(TAG, "onPrepared");
		mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARED;
		mUIHandler.sendEmptyMessage(UI_EVENT_UPDATE_CURRPOSITION);
		mUIHandler.sendEmptyMessage(UI_EVENT_UPDATE_CURRPOSITION_START);

	}

}