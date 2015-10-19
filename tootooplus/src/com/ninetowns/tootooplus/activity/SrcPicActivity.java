package com.ninetowns.tootooplus.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.photoview.PhotoView;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.UIUtils;
import com.ninetowns.ui.Activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * @ClassName: SrcPicActivity
 * @Description: 加载原图 界面
 * @author zhou
 * @date 2015-2-9 下午6:46:46
 * 
 */
public class SrcPicActivity extends BaseActivity {

	// private PhotoView mCustomImageView;

	/**
	 * @Fields mProgressBar : 进度条
	 */
	// private ProgressBar mProgressBar;

	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView(R.layout.activity_show_big_image);
		/**
		 * @Fields mPicPhotoView : 自定义imageView;
		 */
		PhotoView mCustomImageView = (PhotoView) findViewById(R.id.showbigimag_image);
		/**
		 * @Fields mProgressBar : 进度条
		 */
		final ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.showbigimag_progressbar);

		String srcPicUri = getIntent().getStringExtra("srcpicuri");

		ImageLoader.getInstance().displayImage(srcPicUri, mCustomImageView,
				CommonUtil.OPTIONS_ALBUM, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						UIUtils.setViewVisible(mProgressBar);

					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						UIUtils.setViewGone(mProgressBar);

					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						UIUtils.setViewGone(mProgressBar);

					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
						UIUtils.setViewGone(mProgressBar);

					}
				});
	}

}
