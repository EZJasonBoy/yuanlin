package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.activity.HomeActivity;
import com.ninetowns.tootoopluse.activity.PersonalHomeActivity;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.CommentListBean;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.widget.WrapRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 
 * @ClassName: CommentListAdapter
 * @Description: 点评列表数据适配器
 * @author wuyulong
 * @date 2015-3-24 下午2:10:14
 * 
 */
public class CommentListAdapter extends BaseAdapter {
	private List<CommentListBean> commentList;
	private Activity activity;
	private LayoutInflater mInflater;

	public CommentListAdapter(Activity activity,
			List<CommentListBean> commentList) {
		this.activity = activity;
		this.commentList = commentList;
		setInflater();
	}

	@Override
	public int getCount() {
		return commentList.size();
	}

	@Override
	public Object getItem(int position) {
		return commentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private void setInflater() {
		mInflater = LayoutInflater.from(TootooPlusEApplication.getAppContext());

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommentListBean commentListBean = commentList.get(position);
		CommentHolder commentHolder = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.comment_list_lv_item, null);
			commentHolder = new CommentHolder();
			ViewUtils.inject(commentHolder, convertView);
			convertView.setTag(commentHolder);
		} else {
			commentHolder = (CommentHolder) convertView.getTag();
		}
		String userid=commentListBean.getUserId();
		if (!TextUtils.isEmpty(userid)) {
			commentHolder.mIVPhoto.setTag(userid);

		}
		commentHolder.mIVPhoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userid = (String) v.getTag();
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
					Intent intent = new Intent(TootooPlusEApplication.getAppContext(),
							HomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					bundle.putInt("tab_index", 3);
					intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					activity.startActivity(intent);
					
				}
				
			}
		});
		setItemData(commentHolder, commentListBean);
		return convertView;
	}

	/**
	 * 
	 * @Title: setItemData
	 * @Description: 设置条目的数据
	 * @param
	 * @return
	 * @throws
	 */
	public void setItemData(CommentHolder commentHolder,
			CommentListBean commentListBean) {
		setConvertParam(commentHolder);
		String strCountLike = commentListBean.getCountLike();
		String strCountRecommend = commentListBean.getCountRecommend();
		String strCommentConvertThumb = commentListBean.getCoverThumb();
		String strCommentLogoUrl = commentListBean.getLogoUrl();
		String strCommentStoryId = commentListBean.getStoryId();
		String strStoryName = commentListBean.getStoryName();
		String strStoryType = commentListBean.getStoryType();
		String strUserGrade = commentListBean.getUserGrade();
		String strStoryVideoUrl = commentListBean.getStoryVideoUrl();
		String strUserId = commentListBean.getUserId();
		String strUserName = commentListBean.getUserName();
		String strIsRecommend=commentListBean.getIsRecommend();
		if (!TextUtils.isEmpty(strCountLike)) {
			commentHolder.mCTLike.setText(strCountLike);//设置点赞
		}
		if (!TextUtils.isEmpty(strCountRecommend)) {
			commentHolder.mRatBar.setRating(Float.valueOf(strCountRecommend));//设置评论指数

		}
		if (!TextUtils.isEmpty(strCommentConvertThumb)) {//判断并设置封面图
			ImageLoader.getInstance().displayImage(strCommentConvertThumb,
					new ImageViewAware(commentHolder.mIVConvert),
					CommonUtil.OPTIONS_ALBUM);

		}
		if (!TextUtils.isEmpty(strCommentLogoUrl)) {//设置头像
			ImageLoader.getInstance().displayImage(strCommentLogoUrl,
					new ImageViewAware(commentHolder.mIVPhoto),
					CommonUtil.OPTIONS_HEADPHOTO);
		}else{
			commentHolder.mIVPhoto.setImageResource(R.drawable.icon_default_photo);
		}
		if (!TextUtils.isEmpty(strStoryName)) {
			commentHolder.mTVStoryName.setText(strStoryName);

		}
		if (!TextUtils.isEmpty(strStoryType)&&strStoryType.equals("3")) {//如果是视频
			commentHolder.mIVVideoIcon.setVisibility(View.VISIBLE);
		}else{
			commentHolder.mIVVideoIcon.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(strUserGrade)) {//设置用户等级
			setUserGrade(commentHolder, strUserGrade);

		}
		if (!TextUtils.isEmpty(strUserName)) {
			commentHolder.mCTUserName.setText(strUserName);

		}
		if (!TextUtils.isEmpty(strIsRecommend)&&strIsRecommend.equals("1")) {//是否是推荐  1是推荐
			commentHolder.mIVRecommendIcon.setVisibility(View.VISIBLE);
		}else{
			commentHolder.mIVRecommendIcon.setVisibility(View.GONE);
		}

	}
	/**
	 * 
	* @Title: setUserGrade 
	* @Description: 设置用户等级
	* @param  
	* @return   
	* @throws
	 */
	private void setUserGrade(CommentHolder wishHolder, String userGrade) {
		if (!TextUtils.isEmpty(userGrade)) {

			if (userGrade.equals("1")) {
				Drawable drawable = TootooPlusEApplication.getAppContext()
						.getResources().getDrawable(R.drawable.icon_vip_1);
				wishHolder.mCTUserName.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
			} else if (userGrade.equals("2")) {
				Drawable drawable = TootooPlusEApplication.getAppContext()
						.getResources().getDrawable(R.drawable.icon_vip_2);
				wishHolder.mCTUserName.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
			} else if (userGrade.equals("3")) {
				Drawable drawable = TootooPlusEApplication.getAppContext()
						.getResources().getDrawable(R.drawable.icon_vip_3);
				wishHolder.mCTUserName.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
			} else if (userGrade.equals("4")) {
				Drawable drawable = TootooPlusEApplication.getAppContext()
						.getResources().getDrawable(R.drawable.icon_vip_4);
				wishHolder.mCTUserName.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
			} else if (userGrade.equals("5")) {
				Drawable drawable = TootooPlusEApplication.getAppContext()
						.getResources().getDrawable(R.drawable.icon_vip_5);
				wishHolder.mCTUserName.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
			} else if (userGrade.equals("6")) {
				Drawable drawable = TootooPlusEApplication.getAppContext()
						.getResources().getDrawable(R.drawable.icon_vip_6);
				wishHolder.mCTUserName.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
			} else if (userGrade.equals("7")) {
				Drawable drawable = TootooPlusEApplication.getAppContext()
						.getResources().getDrawable(R.drawable.icon_vip_7);
				wishHolder.mCTUserName.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
			} else if (userGrade.equals("8")) {
				Drawable drawable = TootooPlusEApplication.getAppContext()
						.getResources().getDrawable(R.drawable.icon_vip_8);
				wishHolder.mCTUserName.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
			} else if (userGrade.equals("9")) {
				Drawable drawable = TootooPlusEApplication.getAppContext()
						.getResources().getDrawable(R.drawable.icon_vip_8);
				wishHolder.mCTUserName.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
			} else if (userGrade.equals("10")) {
				Drawable drawable = TootooPlusEApplication.getAppContext()
						.getResources().getDrawable(R.drawable.icon_vip_10);
				wishHolder.mCTUserName.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
			} else {
				wishHolder.mCTUserName.setVisibility(View.INVISIBLE);
			}

		}
	}
	
/**
 * 
* @Title: setConvertParam 
* @Description: 设置封面图的宽高比是3：2
* @param  CommentHolder
* @return   
* @throws
 */
	private void setConvertParam(CommentHolder commentHolder) {
		int convertWidth = CommonUtil.getWidth(TootooPlusEApplication
				.getAppContext());
		int height = convertWidth * 2 / 3;
		LayoutParams convertPa = commentHolder.mIVConvert.getLayoutParams();
		convertPa.height = height;
		convertPa.width = convertWidth;
		commentHolder.mIVConvert.setLayoutParams(convertPa);
	}

	/**
	 * 
	 * @ClassName: CommentHolder
	 * @Description: 体验点评列表viewholder
	 * @author wuyulong
	 * @date 2015-3-24 下午1:47:52
	 * 
	 */
	public static class CommentHolder {
		@ViewInject(R.id.ct_user_name)
		public CheckedTextView mCTUserName;// 用户 等级
		@ViewInject(R.id.ct_like)
		public CheckedTextView mCTLike;// 点赞
		@ViewInject(R.id.iv_comment_convert)
		public ImageView mIVConvert;// 封面图
		@ViewInject(R.id.iv_comment_photo)
		public ImageView mIVPhoto;// 头像
		@ViewInject(R.id.tv_story_name)
		public TextView mTVStoryName;// 心愿名字
		@ViewInject(R.id.rb_comment)
		public WrapRatingBar mRatBar;// 好评指数
		@ViewInject(R.id.iv_video_icon)
		public ImageView mIVVideoIcon;// 视频小图标
		@ViewInject(R.id.iv_wish_recommend_icon)
		public ImageView mIVRecommendIcon;// 活动状态图标

	}
}
