package com.ninetowns.tootoopluse.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.SkipPhotoViewPagerBean;
import com.ninetowns.tootoopluse.bean.WishDetailPageListBean;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.Activity.FragmentGroupActivity;
import com.ninetowns.ui.widget.dialog.ProgressiveDialog;
import com.ninetowns.ui.wiget.gesture.image.PhotoView;
import com.ninetowns.ui.wiget.gesture.image.PhotoViewAttacher.OnPhotoTapListener;
import com.ninetowns.ui.wiget.gesture.image.ViewPagerFixed;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 
 * @Title: StoryDetailViewPagerActivity.java
 * @Description: 左右滑动故事页
 * @author wuyulong
 * @date 2015-1-8 上午11:00:50
 * @version V1.0
 */
public class StoryDetailViewPagerActivity extends FragmentGroupActivity
		implements OnClickListener {
	private String tag = "StoryDetailViewPagerActivity";
	private List<WishDetailPageListBean> locallist = new ArrayList<WishDetailPageListBean>();
	// private ViewPager mViewPager;
	private MyViewPagerAdapter adapter;
	private ProgressiveDialog progressDialog;
	private int position;
	private int count;
	@ViewInject(R.id.viewpager)
	private ViewPagerFixed mViewPager;
	private int currentPosition;
	@ViewInject(R.id.iv_back)
	private ImageView mImBtnBack;

	
	private SkipPhotoViewPagerBean skipPhotoBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_story_viewpager);
		ViewUtils.inject(this);
		
		// 1.当前位置position 传入过来，把数据缓存或者是传入
		// 创建adapter
		// 2.根据当前位置从数据里面找到对应的位置，并且判断与展示
		// 3.滑动的时候请求数据，每滑动一个判断界面的类型
		// 4.界面的类型 将要动态创建的界面用FrameLayout 创建三种类型的界面
		// 5.初始化id 是在动态创建的时候初始化，注意为null的情况
		// 7.当视频的时候可以点击进入播放
		skipPhotoBean = (SkipPhotoViewPagerBean) getIntent().getExtras()
				.getSerializable("photolist");
		if (skipPhotoBean != null) {
			currentPosition = skipPhotoBean.getPosition();
			locallist = skipPhotoBean.getPhotoList();
			count = locallist.size();
		}
		initListener();
		initAdapter();
	}

	private void initAdapter() {
		adapter = new MyViewPagerAdapter(this, currentPosition);
		mViewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		mViewPager.setCurrentItem(currentPosition);//

	}

	/**
	 * 
	 * @Title: StoryDetailItemTypeActivity.java
	 * @Description: 根据跳转类型跳到相应的fragemnt中
	 * @author wuyulong
	 * @date 2014-8-1 下午4:51:02
	 * @param
	 * @return void
	 */

	private void initListener() {
		mImBtnBack.setOnClickListener(this);

	}

	@Override
	protected void initPrimaryFragment() {

		return;
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		return null;
	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		return 0;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.gc();
	}

	class MyViewPagerAdapter extends PagerAdapter implements
			OnPageChangeListener {
		private StoryDetailViewPagerActivity activity;
		private int currentPosition;
		private ProgressBar spinner;

		public MyViewPagerAdapter(StoryDetailViewPagerActivity activity,
				int currentPosition) {
			this.activity = activity;
			this.currentPosition = currentPosition;
			mViewPager.setOnPageChangeListener(this);
			mViewPager.setCurrentItem(this.currentPosition);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// 状态改变的时候调用 状态有三种 0的时候什么都没有改变 1的时候正在滑动 2的时候滑动完毕
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View imageTypeTLayout = getLayoutInflater().inflate(
					R.layout.item_pager_image, container, false);
			imageTypeTLayout.findViewById(R.id.rl_image).setVisibility(
					View.VISIBLE);
			PhotoView mImageView = (PhotoView) imageTypeTLayout
					.findViewById(R.id.iv_image_type);
			ProgressBar lodding = (ProgressBar) imageTypeTLayout.findViewById(R.id.loading);
			// 1.获得每个数据的object
			WishDetailPageListBean itemObject = locallist.get(position);
//			// 2.根据type获取数据
//			if (position == this.currentPosition) {
//				LogUtil.systemlogInfo("instantiateItem--currentPosition",
//						this.currentPosition);
//				mViewPager.setCurrentItem(this.currentPosition);
//				// 1.获得每个数据的object
//				// getStoryData(itemObject);
//			}
			String pageImage = itemObject.getPageImg();
			if (!TextUtils.isEmpty(pageImage)) {
				if (mImageView != null) {
					ImageLoader.getInstance().displayImage(pageImage,
							new ImageViewAware(mImageView),
							CommonUtil.OPTIONS_ALBUM);
				}

			}
			mImageView.setFocusableInTouchMode(true);
			mImageView.setOnPhotoTapListener(new OnPhotoTapListener() {
				
				@Override
				public void onPhotoTap(View view, float x, float y) {
					finish();
					
				}
			});
			container.addView(imageTypeTLayout, 0);
			return imageTypeTLayout;
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// 当页面在滑动的时候调用此方法，在滑动被停止之前会一直调用此方法
			// position表示当前界面及你点击滑动的界面的位置
			// arg1当前页面偏移的百分比
			// arg2当前 界面偏移的像素位置
		}

		@Override
		public void onPageSelected(int position) {
			// 1.获得每个数据的object

			if (position == locallist.size() - 1) {// 如果是最后一条
				ComponentUtil.showToast(StoryDetailViewPagerActivity.this,
						StoryDetailViewPagerActivity.this.getResources()
								.getString(R.string.already_last_page));
			}
			if (position == 0) {
				ComponentUtil.showToast(StoryDetailViewPagerActivity.this,
						StoryDetailViewPagerActivity.this.getResources()
								.getString(R.string.already_first_page));
			}

			currentPosition = position;
		}

		@Override
		public int getCount() {
			int count = 0;
			if (locallist != null) {
				count = locallist.size();
			}
			return count;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void finishUpdate(ViewGroup container) {
			super.finishUpdate(container);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			mViewPager.removeView((View) object);
		}
	}

	private boolean isAddView = true;

	/**
	 * 
	 * @Title: StoryDetailViewPagerActivity.java
	 * @Description: 从服务器获取数据
	 * @author wuyulong
	 * @date 2014-9-24 上午9:09:02
	 * @param
	 * @return void
	 */

	public void showProgressDialog() {
		if ((!StoryDetailViewPagerActivity.this.isFinishing())
				&& (progressDialog == null)) {
			progressDialog = new ProgressiveDialog(
					StoryDetailViewPagerActivity.this);
		}
		if (progressDialog != null) {
			progressDialog.setMessage(R.string.loading);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();

		}

	}

	public void closeProgressDialog() {
		if (progressDialog != null) {
			if (progressDialog.isShowing())
				progressDialog.dismiss();
		}
	}
}
