package com.wiriamubin.service.chat;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.widget.ImageView;

import com.lidroid.xutils.util.LogUtils;
import com.ninetowns.tootoopluse.R;

/** 
* @ClassName: VoicePlayer 
* @Description: 语音播放控制
* @author zhou
* @date 2015-2-3 下午1:12:15 
*  
*/
public class VoicePlayer {
	private MediaPlayer mMediaPlayer = null;
	public static VoicePlayer sInstancePlayer;
	
	ImageView voiceIconView;
	private AnimationDrawable voiceAnimation = null;
	private boolean isReceiver;
	public static boolean isPlaying = false;

	private VoicePlayer() {
	};

	public static VoicePlayer getInstance() {
		if (null == sInstancePlayer) {
			sInstancePlayer = new VoicePlayer();
		}
		return sInstancePlayer;
	}

	/** 
	* @Title: playVoice 
	* @Description: TODO 播放语音 
	* @param @param name
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	*/
	public void playVoice(String name,final boolean isReceiver,ImageView voiceIconView) throws Exception {
		if(isPlaying==true){
			stopPlayVoice();
		}
		
		LogUtils.i("playVoice called...........");
		this.voiceIconView=voiceIconView;
		this.isReceiver=isReceiver;
		if(null==mMediaPlayer){
			mMediaPlayer=new MediaPlayer();
		}
			if (mMediaPlayer!=null&&mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(name);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			isPlaying=true;
			showAnimation();
			mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					stopPlayVoice(); // stop animation
				}

			});
	}
	
	
	private void showAnimation() {
		// play voice, and start animation
		if (isReceiver) {
			voiceIconView.setImageResource(R.anim.voice_from_icon);
		} else {
			voiceIconView.setImageResource(R.anim.voice_to_icon);
		}
		voiceAnimation = (AnimationDrawable) voiceIconView.getDrawable();
		voiceAnimation.start();
	}
	/** 
	* @Title: stopPlayVoice 
	* @Description: 停止语音播放
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void stopPlayVoice() {
		voiceAnimation.stop();
		if (isReceiver) {
			voiceIconView.setImageResource(R.drawable.chatfrom_voice_playing);
		} else {
			voiceIconView.setImageResource(R.drawable.chatto_voice_playing);
		}
		// stop play voice
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer=null;
		}
		isPlaying = false;
	}

}
