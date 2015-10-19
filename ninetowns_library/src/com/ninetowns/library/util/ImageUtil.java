package com.ninetowns.library.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ImageUtil {

	public static final String TOO_HOST = Environment
			.getExternalStorageDirectory().getPath() + "/tootoo";

	public static final String PHOTO_HOST = TOO_HOST + File.separator + "photo";
	public static final String IMAGELOADER_CACHE = TOO_HOST + File.separator + "imageloadercache";
	public static final String TOO_INSTALL = TOO_HOST + File.separator + "install";

	private static final String TAG = "ImageUtil";
	
	/**
	 * 缓存存放路径
	 */
	public static File getImageLoaderCacheFile() {

		 File path = new File(IMAGELOADER_CACHE);
		if (!path.exists()) {
			path.mkdirs();
//			path.createNewFile();
		}
		return path;
	}
	/**
	 * 拍照照片所存放的路径
	 */
	public static String getPhotoPath() {

		final File path = new File(PHOTO_HOST);
		if (!path.exists()) {
			path.mkdirs();
		}

		return PHOTO_HOST;
	}

	/**
	 * 创建照片全路径(文件夹和文件名),命名规则为系统时间按"HH_mm_ss"格式
	 * 
	 * @param photoPath
	 * @return
	 */
	public static String makePhotoName(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH_mm_ss");// 设置日期格式
		final String strFileName = df.format(date) + ".JPG";
		String photo_path = getPhotoPath() + File.separator + strFileName;
		return photo_path;
	}

	public static String getTempPhotoPath() {

		return getPhotoPath() + File.separator + "temp.jpg";
	}

	/**
	 * 通过系统ContentResolver提供的信息，获取手机中某张图片的信息
	 * 
	 * @param context
	 * @return
	 */
	public static Bitmap getPicBitmap(Context context, Intent data) {

		Uri mImageCaptureUri = data.getData();
		Bitmap bmp = null;
		if (null != mImageCaptureUri) {
			ContentResolver cr = context.getContentResolver();
			Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找
			if (cursor != null) {
				cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
				String filePath = cursor.getString(cursor
						.getColumnIndex("_data"));// 获取图片路
				String orientation = cursor.getString(cursor
						.getColumnIndex("orientation"));// 获取旋转的角度
				cursor.close();
				if (filePath != null) {
					// Bitmap bitmap = BitmapFactory.decodeFile(filePath);//
					// 根据Path读取资源图片
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
					bitmapOptions.inSampleSize = 2;
					Bitmap bitmap = null;
					try {
						bitmap = BitmapFactory.decodeStream(
								cr.openInputStream(mImageCaptureUri), null,
								bitmapOptions);
						int angle = 0;
						if (orientation != null && !"".equals(orientation)) {
							angle = Integer.parseInt(orientation);
						}

						if (angle == 0) {
							return bitmap;
						} else {
							// 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
							Matrix m = new Matrix();
							int width = bitmap.getWidth();
							int height = bitmap.getHeight();
							m.setRotate(angle); // 旋转angle度

							bmp = Bitmap.createBitmap(bitmap, 0, 0, width,
									height, m, true);// 从新生成图片

							if (null != bitmap && !bitmap.isRecycled()) {
								bitmap.recycle(); // Android开发网再次提示Bitmap操作完应该显示的释放
								bitmap = null;
								System.gc();
							}
						}

					} catch (Exception e) {
						LogUtil.error(TAG, e.getMessage());
					}
				}

			}

			return bmp;
		}
		return bmp;

	}

	public static File savePhotoToSDCard(Bitmap photoBitmap, String path) {
		if (checkSDCardAvailable()) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File photoFile = new File(path);
			if (photoFile.exists()) {
				photoFile.delete();
			}
			try {
				photoFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LogUtil.error(TAG, e.getMessage());
			}
			// return photoFile;
			// }
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 60,
							fileOutputStream)) {
						fileOutputStream.flush();
						// fileOutputStream.close();
						return photoFile;
					}
				}
			} catch (Exception e) {
				photoFile.delete();
				e.printStackTrace();
				LogUtil.error(TAG, e.getMessage());
			} finally {
				try {
					if (null != fileOutputStream)
						fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					LogUtil.error(TAG, e.getMessage());
				}
			}
		}
		return null;
	}

	public static boolean checkSDCardAvailable() {

		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * @param path
	 *            获取图片旋转度数
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
			LogUtil.error(TAG, e.getMessage());
		}
		return degree;
	}

	public static Bitmap getBitmapFromPath(String path, Context context) {

		Bitmap bitmap = null;

		if (!checkSDCardAvailable()) {
			return bitmap;
		}

		File file = new File(path);
		FileInputStream fs = null;
		Bitmap bmp = null;
		try {
			fs = new FileInputStream(file);

			if (!file.exists() || !file.isFile()) {
				return bitmap;
			}
			
			
			bitmap = CompressBitmap(context, bitmap, fs);
			int angle = readPictureDegree(path);
			if(angle==0){
				return bitmap;
			}
			else {
				// 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
				
				Matrix m = new Matrix();
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				m.setRotate(angle); // 旋转angle度

				bmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片

				if (null != bitmap && !bitmap.isRecycled()) {
					bitmap.recycle(); 
					bitmap = null;
					System.gc();
				}
			}

		} catch (Exception e) {
			
			e.printStackTrace();
			LogUtil.error(TAG, e.getMessage());
			bmp = bitmap;
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
					LogUtil.error(TAG, e.getMessage());
				}
			}
		}
		return bmp;
	}
		

		private static Bitmap CompressBitmap(Context context, Bitmap bitmap,
				FileInputStream fs)  {
			Bitmap bmp = null;
			// 根据Path读取资源图片
			BitmapFactory.Options opts = new BitmapFactory.Options();
			// 计算图片缩放比例
			opts.inSampleSize = 2;
			opts.inJustDecodeBounds = false;
//			opts.inInputShareable = true;
//			opts.inPurgeable = true;
	//
//			opts.inDither = false;
//			opts.inPurgeable = true;
			opts.inTempStorage = new byte[16 * 1024];
			if (fs != null) {
				try {
					bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, opts);
				} catch (IOException e) {
					e.printStackTrace();
					LogUtil.error(TAG, e.getMessage());
				}
				bitmap = Bitmap.createBitmap(bmp);
			}
			if(null!=bmp&&!bmp.isRecycled()){
//				bmp.recycle();
				bmp=null;
				System.gc();
			}
			return bitmap;
		}



	/**
	 * 把图片变成正方形
	 * 
	 * @param bmp
	 * @return
	 */
	public static Bitmap cutSquareBmp(Bitmap bmp) {

		Bitmap result;
		// 图片之前的宽度
		int width = bmp.getWidth();
		// 图片之前的高度
		int height = bmp.getHeight();
		int new_width_height = 0;
		if (width > height) {
			// 当宽度大于高度
			new_width_height = height;
			result = Bitmap.createBitmap(bmp, (width - new_width_height) / 2,
					0, new_width_height, new_width_height);
		} else {
			// 当高度大于宽度
			new_width_height = width;
			result = Bitmap.createBitmap(bmp, 0,
					(height - new_width_height) / 2, new_width_height,
					new_width_height);
		}
		return result;
	}

	/***
	 * 
	 * @Title: ImageUtil.java
	 * @Description: 设置图片4：3比例 gridview
	 * @author wuyulong
	 * @date 2014-8-28 上午9:30:31
	 * @param
	 * @return RelativeLayout.LayoutParams
	 */
	public static RelativeLayout.LayoutParams getImageLayoutParms(
			Context context) {
		int mobileWidth = BaseCommonUtil.getWidth(context);
		int itemWidth = mobileWidth / 2;
		int itemHeight = (int) (itemWidth * 0.75);//
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				itemWidth, itemHeight);
		return params;
	}

	public static LinearLayout.LayoutParams getImageLinearLayoutParms(
			Context context) {
		int mobileWidth = BaseCommonUtil.getWidth(context);
		int itemWidth = mobileWidth / 2;
		int itemHeight = (int) (itemWidth * 0.75);//
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				itemWidth, itemHeight);
		return params;
	}
	public static LinearLayout.LayoutParams getImageSquareLinearLayoutParms(
			Context context) {
		int mobileWidth = BaseCommonUtil.getWidth(context);
		int itemHeight = 0, itemWidth = mobileWidth ;
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				itemWidth, itemHeight);
		return params;
	}

	/**
	 * 
	 * @Title: ImageUtil.java
	 * @Description: 缩略图
	 * @author wuyulong
	 * @date 2014-8-28 上午9:44:20
	 * @param
	 * @return RelativeLayout.LayoutParams
	 */
	private static int itemHeight;

	public static RelativeLayout.LayoutParams getImageParms(Context context,
			int margin) {

		int mobileWidth = BaseCommonUtil.getWidth(context);
		int itemWidth = mobileWidth;
		if (margin != 0) {
			itemWidth = itemWidth - margin;
			itemHeight = (int) (itemWidth * 0.75);//
		} else {
			itemHeight = (int) (itemWidth * 0.75);//
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				itemWidth, itemHeight);
		params.setMargins(margin, 0, margin, 0);
		return params;
	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1.
	 * 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2.
	 * 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使 用这个工具生成的图像不会被拉伸。
	 * 
	 * @param imagePath
	 *            图像的路径
	 * @param width
	 *            指定输出图像的宽度
	 * @param height
	 *            指定输出图像的高度
	 * @return 生成的缩略图
	 */
	private Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 * 
	 * @param videoPath
	 *            视频的路径
	 * @param width
	 *            指定输出视频缩略图的宽度
	 * @param height
	 *            指定输出视频缩略图的高度度
	 * @param kind
	 *            参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图
	 */
	public static Bitmap getVideoThumbnail(String videoPath, int width,
			int height, int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		if (bitmap != null) {
			System.out.println("w" + bitmap.getWidth());
			System.out.println("h" + bitmap.getHeight());
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}
		return bitmap;
	}

	/**
	 * 
	 * @Title: ImageUtil.java
	 * @Description: 获取缩略图文件
	 * @author wuyulong
	 * @date 2014-9-12 下午5:31:37
	 * @param
	 * @return File
	 */
	public static File getVideoThumbnailPhoto(String videoPath, int kind,
			int width, int height) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		String upFileName = ImageUtil.makePhotoName(new Date());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		try {
			FileOutputStream out = new FileOutputStream(upFileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		File upFile = new File(upFileName);
		LogUtil.systemlogInfo("文件大小", upFile.length());

		if (bitmap != null) {
			System.out.println("w" + bitmap.getWidth());
			System.out.println("h" + bitmap.getHeight());
		}
		return upFile;
	}
}
