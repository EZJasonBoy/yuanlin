package com.wiriamubin.service.chat;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.Environment;

import com.lidroid.xutils.util.LogUtils;

public  class SoundMeter {
	static final private double EMA_FILTER = 0.6;

	private MediaRecorder mRecorder = null;
	private double mEMA = 0.0;
	public void start(String name) throws Exception {
		if (!Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return;
		}
		
		if (mRecorder == null) {
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			
			mRecorder.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public void onError(MediaRecorder mr, int what, int extra) {
					mRecorder.reset();
				}
			});
			mRecorder.setOutputFile(name);
				mRecorder.prepare();
				mRecorder.start();
				mEMA = 0.0;
		}
	}

	public void stop() {
		if (mRecorder != null) {
			try {
				mRecorder.stop();
				mRecorder.reset();
				mRecorder.release();
				mRecorder = null;
			} catch (Exception e) {
			}
			
		}
	}

	
public void 	resetRecord(){
		if(null!=mRecorder){
			mRecorder.reset();
		}
	}
	public void pause() {
		if (mRecorder != null) {
			mRecorder.stop();
		}
	}

	public void start() {
		if (mRecorder != null) {
			mRecorder.start();
		}
	}

	public int getAmplitude() {
		if (mRecorder != null){
			double m=mRecorder.getMaxAmplitude()/2700.0;
			LogUtils.i("getMaxAmplitude========"+mRecorder.getMaxAmplitude());
			LogUtils.i("getMaxAmplitude222========"+m);
			return (int) Math.round(m);
		}
			
		else{
			return 0;
		}
			

	}

	public double getAmplitudeEMA() {
		double amp = getAmplitude();
		mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
		return mEMA;
	}
}
