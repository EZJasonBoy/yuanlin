package com.ninetowns.tootooplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.baiduplayer.BaiduPlayer;
import com.ninetowns.ui.Activity.BaseActivity;

/** 
* @ClassName: VideoPlayActivity 
* @Description: 视频播放界面
* @author zhou
* @date 2015-2-11 下午4:58:57 
*  
*/
public class VideoPlayActivity extends BaseActivity {
	
	
	/** 
	* @Fields mBaiduPlayer :baidu播放器
	*/ 
	private BaiduPlayer mBaiduPlayer;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_videoplay);
		RelativeLayout viewHolder=(RelativeLayout) findViewById(R.id.videoviewholder);
		mBaiduPlayer=BaiduPlayer.getInstance(viewHolder, this);
		
		Intent intent=getIntent();
		if(null!=intent){
			String videoUri=intent.getStringExtra("videoUri");
			if(!StringUtils.isEmpty(videoUri)){
				mBaiduPlayer.play(intent.getStringExtra("videoUri"));
			}
			
		}
	
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mBaiduPlayer.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mBaiduPlayer.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(null!=mBaiduPlayer){
			mBaiduPlayer.onStop();
			mBaiduPlayer=null;
		}
		
	}
	

	

}
