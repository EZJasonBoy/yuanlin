package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.MobileAlbumBean;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class MobileAlbumLvAdapter extends BaseAdapter {
	
	private Context context;
	
	List<MobileAlbumBean> mobileAlbumBeans;
	
	public MobileAlbumLvAdapter(Context context, List<MobileAlbumBean> mobileAlbumBeans){
		this.context = context;
		
		this.mobileAlbumBeans = mobileAlbumBeans;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mobileAlbumBeans != null && mobileAlbumBeans.size() > 0){
			return mobileAlbumBeans.size();
		} else {
			return 0;
		}
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.mobile_album_lv_item, null);
			vh.mob_album_item_photo = (ImageView)convertView.findViewById(R.id.mob_album_item_photo);
			vh.mob_album_item_name = (TextView)convertView.findViewById(R.id.mob_album_item_name);
			vh.mob_album_item_count = (TextView)convertView.findViewById(R.id.mob_album_item_count);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder)convertView.getTag();
		}
		
		if (!TextUtils.isEmpty(mobileAlbumBeans.get(position).getMob_album_photo_path())) {
			
			ImageLoader.getInstance().displayImage("file://" + mobileAlbumBeans.get(position).getMob_album_photo_path(), vh.mob_album_item_photo, CommonUtil.OPTIONS_ALBUM, new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingFailed(String imageUri, View view,
				        FailReason failReason) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					int angle =ImageUtil. readPictureDegree(mobileAlbumBeans.get(position).getMob_album_photo_path());
	                if(angle != 0){
	                    // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
	                    Matrix m = new Matrix();
	                    int width = loadedImage.getWidth();
	                    int height = loadedImage.getHeight();
	                    m.setRotate(angle); // 旋转angle度
	                    loadedImage = Bitmap.createBitmap(loadedImage, 0, 0, width, height, m, true);// 从新生成图片
	                }
					ImageView imageView = (ImageView)view;
					imageView.setImageBitmap(ImageUtil.cutSquareBmp(loadedImage));
				}
				
				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}
		
		
		if(!TextUtils.isEmpty(mobileAlbumBeans.get(position).getMob_album_folder_name())){
			vh.mob_album_item_name.setText(mobileAlbumBeans.get(position).getMob_album_folder_name());
		} else {
			vh.mob_album_item_name.setText("");
		}
		
		if(!TextUtils.isEmpty(mobileAlbumBeans.get(position).getMob_album_photo_count())){
			vh.mob_album_item_count.setText(mobileAlbumBeans.get(position).getMob_album_photo_count() + context.getResources().getString(R.string.mobile_album_count_tv));
		} else {
			vh.mob_album_item_count.setText("0" + context.getResources().getString(R.string.mobile_album_count_tv));
		}
		
		return convertView;
	}

	static class ViewHolder{
		ImageView mob_album_item_photo;
		
		TextView mob_album_item_name, mob_album_item_count;
	}
}
