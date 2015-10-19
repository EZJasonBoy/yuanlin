package com.ninetowns.tootoopluse.baiduplayer;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.util.Log;
import android.view.ViewGroup;

import com.baidu.cyberplayer.core.BVideoView;
import com.baidu.cyberplayer.core.BVideoView.OnCompletionListener;
import com.baidu.cyberplayer.core.BVideoView.OnErrorListener;
import com.baidu.cyberplayer.core.BVideoView.OnInfoListener;
import com.baidu.cyberplayer.core.BVideoView.OnPreparedListener;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootoopluse.activity.VideoPlayActivity;

public class BaiduPlayer implements OnPreparedListener, OnCompletionListener, OnErrorListener, OnInfoListener {
	private static BaiduPlayer sInstance;
	private static final String POWER_LOCK = "BaiduPlayer";
	private static final String TAG =POWER_LOCK;
	private final int EVENT_PLAY = 0;
	
	
	private EventHandler mEventHandler;
	private HandlerThread mHandlerThread;
	
	private final Object SYNC_Playing = new Object();
	private  enum PLAYER_STATUS {
		PLAYER_IDLE, PLAYER_PREPARING, PLAYER_PREPARED,
	}
	/**
	 * 播放状态
	 */
	private PLAYER_STATUS mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
	private BVideoView mVV = null;
	/**
	 * 记录播放位置
	 */
	private int mLastPos = 0;
	
	/**
	 * 您的ak 
	 */
	private String AK = "1GRG3IEKGOuSbNaYALC6NS6d";
	/**
	 * //您的sk的前16位
	 */
	private String SK = "2dDfd4IPxl1K8ZvbF4Geq90p1lRy0uFb";
	private WakeLock mWakeLock = null;
	private String mVideoSource;
	private Activity context;

	public static BaiduPlayer getInstance(ViewGroup viewHolder,VideoPlayActivity  context) {
		synchronized (BaiduPlayer.class) {
			if (sInstance == null) {
				/* First call */
				sInstance = new BaiduPlayer(context,viewHolder);
			}
		}
		return sInstance;
	}
	
	
	class EventHandler extends Handler {
	

		public EventHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case EVENT_PLAY:
				/**
				 * 如果已经播放了，等待上一次播放结束
				 */
				if (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE) {
					synchronized (SYNC_Playing) {
						try {
							SYNC_Playing.wait();
							Log.v(TAG, "wait player status to idle");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				/**
				 * 设置播放url
				 */
				playVideo();

				mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARING;
				break;
			default:
				break;
			}
		}

		private void playVideo() {
			mVV.setVideoPath(mVideoSource);
			
			/**
			 * 续播，如果需要如此
			 */
			if (mLastPos > 0) {

				mVV.seekTo(mLastPos);
				mLastPos = 0;
			}

			/**
			 * 显示或者隐藏缓冲提示 
			 */
			mVV.showCacheInfo(true);
			
			/**
			 * 开始播放
			 */
			mVV.start();
		}
	}
	
	public void onResume(){
		Log.v(TAG, "onResume");
		
		if (null != mWakeLock && (!mWakeLock.isHeld())) {
			mWakeLock.acquire();
		}
		
		if(!StringUtils.isEmpty(mVideoSource)){
			mEventHandler.sendEmptyMessage(EVENT_PLAY);
		}
		
	}
	public BaiduPlayer(Activity context, ViewGroup viewHolder) {
		this.context=context;
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, POWER_LOCK);
		init(context,viewHolder);
	}

	private void init(Context context, ViewGroup viewHolder) {
		/**
		 * 设置ak及sk的前16位
		 */
		BVideoView.setAKSK(AK, SK);
		
		/**
		 *创建BVideoView和BMediaController
		 */
		mVV = new BVideoView(context);
		viewHolder.addView(mVV);
		
		/**
		 * 注册listener
		 */
		mVV.setOnPreparedListener(this);
		mVV.setOnCompletionListener(this);
		mVV.setOnErrorListener(this);
		mVV.setOnInfoListener(this);
		
		mVV.setDecodeMode(BVideoView.DECODE_SW);
		
		mHandlerThread = new HandlerThread("event handler thread",
				Process.THREAD_PRIORITY_BACKGROUND);
		mHandlerThread.start();
		mEventHandler = new EventHandler(mHandlerThread.getLooper());
	}

	
	public void onPause(){
		Log.v(TAG, "onPause");
		if (mPlayerStatus == PLAYER_STATUS.PLAYER_PREPARED) {
			mLastPos = mVV.getCurrentPosition();
			mVV.stopPlayback();
		}
	}
	
	public void onStop(){
		Log.v(TAG, "onStop");
		if(null!=mHandlerThread){
			mHandlerThread.quit();
			mHandlerThread=null;
			sInstance=null;
			context.finish();
		}
		
	}
	@Override
	public boolean onInfo(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onError(int arg0, int arg1) {
		Log.v(TAG, "onError");
		synchronized (SYNC_Playing) {
			SYNC_Playing.notify();
		}
		mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
		return true;
	}

	@Override
	public void onCompletion() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCompletion");
		
		synchronized (SYNC_Playing) {
			SYNC_Playing.notify();
		}
		mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
		 onStop();
	}
	
	
	

	public void setVideoPlayListener(VideoPlayListener videoPlayListener ){
		
		videoPlayListener.onVideoPlayCompletion();
	}
	
	public static interface VideoPlayListener{
		void onVideoPlayCompletion();
	}
	/**
	 * 播放准备就绪
	 */
	@Override
	public void onPrepared() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onPrepared");				
		mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARED;
	}
	public void play(String mVideoSource) {
		this.mVideoSource=mVideoSource;
//		mEventHandler.sendEmptyMessage(EVENT_PLAY);
	}
}
