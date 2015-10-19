package com.ninetowns.tootooplus.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;

import com.ninetowns.tootooplus.bean.AlbumPhotoBean;
import com.ninetowns.tootooplus.bean.MobileAlbumBean;

public class AlbumUtil {

	private static final String TAG = "AlbumUtils";

	/**
	 * 通过系统ContentResolver提供的信息，获取手机中所有图片的信息
	 * 
	 * @param context
	 * @return
	 */
	public static List<MobileAlbumBean> albumImageInfo(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		List<MobileAlbumBean> mobileAlbumBeans = new ArrayList<MobileAlbumBean>();
		String[] projection = { BaseColumns._ID,
				ImageColumns.BUCKET_DISPLAY_NAME, MediaColumns.DATA,
				"COUNT (" + BaseColumns._ID + ") AS count" };
		Cursor cursor = contentResolver.query(Media.EXTERNAL_CONTENT_URI,
				projection, "0==0) GROUP BY ("
						+ ImageColumns.BUCKET_DISPLAY_NAME, null, null);
		while (cursor.moveToNext()) {
			MobileAlbumBean mobileAlumBean = new MobileAlbumBean();
			mobileAlumBean.setMob_album_photo_id(cursor.getInt(cursor
					.getColumnIndex(BaseColumns._ID)));
			mobileAlumBean.setMob_album_folder_name(cursor.getString(cursor
					.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME)));
			mobileAlumBean.setMob_album_photo_count(cursor.getString(cursor
					.getColumnIndex("count")));
			mobileAlumBean.setMob_album_photo_path(cursor.getString(cursor
					.getColumnIndex(MediaColumns.DATA)));
			mobileAlbumBeans.add(mobileAlumBean);
		}

		return mobileAlbumBeans;
	}

	/**
	 * 根据文件夹名，通过系统ContentResolver提供的信息，获取手机中所有图片的信息
	 * 
	 * @param context
	 * @param folder_name
	 * @return
	 */
	public static List<AlbumPhotoBean> albumPhotoInfo(Context context,
			String folder_name) {
		ContentResolver contentResolver = context.getContentResolver();
		List<AlbumPhotoBean> albumPhotoBeans = new ArrayList<AlbumPhotoBean>();
		String[] projection = { BaseColumns._ID, MediaColumns.DATA };
		Cursor cursor = contentResolver.query(Media.EXTERNAL_CONTENT_URI,
				projection, ImageColumns.BUCKET_DISPLAY_NAME + "= ?",
				new String[] { folder_name }, "date_added desc");
		while (cursor.moveToNext()) {
			AlbumPhotoBean alumPhotoBean = new AlbumPhotoBean();
			alumPhotoBean.setAlbum_photo_id(cursor.getInt(cursor
					.getColumnIndex(BaseColumns._ID)));
			alumPhotoBean.setAlbum_photo_path(cursor.getString(cursor
					.getColumnIndex(MediaColumns.DATA)));
			albumPhotoBeans.add(alumPhotoBean);
		}

		return albumPhotoBeans;
	}

	/**
	 * 
	 * @Title: AlbumUtils.java
	 * @Description: 根据当前的字段
	 * @author wuyulong
	 * @date 2014-11-14 下午3:31:16
	 * @param
	 * @return ArrayList<HashMap<String,String>>
	 */
	public static ArrayList<HashMap<String, String>> getSystemPotos(
			Context context, String folder_name) {
		ContentResolver contentResolver = context.getContentResolver();
		String[] projection = { BaseColumns._ID, MediaColumns.DATA };
		Cursor cursor = contentResolver.query(Media.EXTERNAL_CONTENT_URI,
				projection, ImageColumns.BUCKET_DISPLAY_NAME + "= ?",
				new String[] { folder_name }, null);
		ArrayList<HashMap<String, String>> potoList = new ArrayList<HashMap<String, String>>();

		if (cursor != null) {
			cursor.moveToFirst();
			while (cursor.getPosition() != cursor.getCount()) {

				HashMap<String, String> maps = new HashMap<String, String>();

				maps.put("imageId", cursor.getString(cursor
						.getColumnIndex(BaseColumns._ID)));
				maps.put("imagePath", cursor.getString(cursor
						.getColumnIndexOrThrow(MediaColumns.DATA)));
				potoList.add(maps);
				cursor.moveToNext();
			}

			cursor.close();
		}

		return potoList;
	}

	/**
	 * 根据图片id，通过系统ContentResolver提供的信息，获取手机中某张图片的信息
	 * 
	 * @param context
	 * @param photo_id
	 * @return
	 */
	public static String photoSelectInfo(Context context, String photo_id) {
		ContentResolver contentResolver = context.getContentResolver();
		String photo_path = "";
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = contentResolver.query(Media.EXTERNAL_CONTENT_URI,
				projection, BaseColumns._ID + "= ?", new String[] { photo_id },
				null);
		if (cursor.moveToFirst()) {
			photo_path = cursor.getString(cursor
					.getColumnIndex(MediaColumns.DATA));
		}
		return photo_path;
	}

	/**
	 * 通过系统ContentResolver提供的信息，获取手机中某张图片的信息
	 * 
	 * @param context
	 * @return
	 */
	public static String getPicPath(Context context, Intent data) {

		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaColumns.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);

		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			if (cursor.getCount() > 0 && cursor.moveToFirst()) {
				String picturePath = cursor.getString(column_index);
				return picturePath;
			}
		}
		return null;
	}

}
