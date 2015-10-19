package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.activity.HomeActivity;
import com.ninetowns.tootoopluse.activity.PersonalHomeActivity;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.GridViewPhotoBean;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 
 * @ClassName: GridViewPhotoAdapter
 * @Description: 白吃用户列表
 * @author wuyulong
 * @date 2015-2-5 下午5:58:44
 * 
 */
public class GridViewPhotoAdapter extends BaseAdapter {
	private List<GridViewPhotoBean> parserResult;
	private LayoutInflater mInflater;
	private Activity activity;

	public GridViewPhotoAdapter(Activity activity,List<GridViewPhotoBean> parserResult) {
		this.parserResult = parserResult;
		this.activity=activity;
		setInflater();
	}

	private void setInflater() {
		mInflater = LayoutInflater.from(TootooPlusEApplication.getAppContext());

	}

	@Override
	public int getCount() {
		return parserResult.size();
	}

	@Override
	public Object getItem(int position) {
		return parserResult.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GridViewPhotoBean photoBean = parserResult.get(position);
		PhotoViewHolder photoViewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_gridview_photo, null);
			photoViewHolder = new PhotoViewHolder();
			ViewUtils.inject(photoViewHolder, convertView);
			convertView.setTag(photoViewHolder);
		} else {
			photoViewHolder = (PhotoViewHolder) convertView.getTag();
		}
		photoViewHolder.ivPhoto.setOnClickListener(new MyOnClickListener(photoBean));
		setUserInfo(photoBean, photoViewHolder);
		return convertView;
	}

	private void setUserInfo(GridViewPhotoBean photoBean,
			PhotoViewHolder photoViewHolder) {
		String logurl = photoBean.getLogoUrl();
		String userGrade = photoBean.getUserGrade();
		String userName = photoBean.getUserName();
		if (!TextUtils.isEmpty(logurl)) {
			ImageLoader.getInstance().displayImage(logurl,
					new ImageViewAware(photoViewHolder.ivPhoto),
					CommonUtil.OPTIONS_HEADPHOTO);
		}
		if (!TextUtils.isEmpty(userGrade)) {
			CommonUtil.setUserGrade(photoViewHolder.mCtUserInfo, userGrade,"bottom");
		}
		if (!TextUtils.isEmpty(userName)) {
			photoViewHolder.mCtUserInfo.setText(userName);
		}
	}

	public static class PhotoViewHolder {
		@ViewInject(R.id.iv_photo_list)
		public ImageView ivPhoto;
		@ViewInject(R.id.ct_user_info)
		public CheckedTextView mCtUserInfo;

	}
	/**
	 * 
	* @ClassName: MyOnClickListener 
	* @Description: 跳转到个人主页
	* @author wuyulong
	* @date 2015-3-23 下午1:25:29 
	*
	 */
	class MyOnClickListener implements View.OnClickListener{
		private GridViewPhotoBean photoBean;
		public MyOnClickListener(GridViewPhotoBean photoBean) {
			this.photoBean=photoBean;
		}

		@Override
		public void onClick(View v) {

			String userid=photoBean.getUserId();

				String myUserId = SharedPreferenceHelper
						.getLoginUserId(TootooPlusEApplication.getAppContext());
				if (!TextUtils.isEmpty(userid) && !userid.equals(myUserId)) {
					Intent intent = new Intent(activity,
							PersonalHomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("userId", userid);
					activity.startActivity(intent);
				} else {
					Bundle bundle=new Bundle();
					Intent intent = new Intent(activity,
							HomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					bundle.putInt("tab_index", 3);
					intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					activity.startActivity(intent);
					
				}
		}
		
	}
}
