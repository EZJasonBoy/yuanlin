package com.ninetowns.tootoopluse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;

import com.ninetowns.library.util.LogUtil;

public class FileUtils {
	private static final String TAG = "FileUtils";

	private String mRootDir = null;

	private static FileUtils mFileUtils = null;

	public static FileUtils getInstance() {
		if (mFileUtils == null) {
			mFileUtils = new FileUtils();
		}
		return mFileUtils;
	}

	public static void deleteFile(File file) {

		if (null != file && file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						deleteFile(file2);
					} else {
						file2.deleteOnExit();
					}
				}
			}
			file.deleteOnExit();
		}

	}

	public static long getSDCardAvailaleSize() {

		File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径

		StatFs stat = new StatFs(path.getPath());

		long blockSize = stat.getBlockSize();

		long availableBlocks = stat.getAvailableBlocks();

		return availableBlocks * blockSize;

		// (availableBlocks * blockSize)/1024 KIB 单位

		// (availableBlocks * blockSize)/1024 /1024 MIB单位

	}

	private FileUtils() {
		mRootDir = YiFileUtils.getStorePath() + "tuotuoplus/";
	}

	public String getStoreRootPath() {
		return mRootDir;
	}

	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	
	
	public static Bitmap getBitmapFromPath(String path, Context context) {

		Bitmap bitmap = null;

		if (!isExitsSdcard()) {
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
			BitmapFactory.Options opts = new BitmapFactory.Options();
			// 计算图片缩放比例
			opts.inSampleSize = 1;
			opts.inJustDecodeBounds = false;
			opts.inTempStorage = new byte[16 * 1024];
			bitmap=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, opts);
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
	
	public static File savePhotoToSDCard(Bitmap photoBitmap, String path) {
		if (isExitsSdcard()) {
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
					if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
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

	/**
	 * 从用户相册中选择照片
	 */
	public static void doChoicePhoto(Activity activty, int outputSize,
			int requestCode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		intent.putExtra("return-data", true);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputSize);
		intent.putExtra("outputY", outputSize);
		intent.putExtra("scale", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		activty.startActivityForResult(intent, requestCode);
	}

	/**
	 * 从用户相册中选择照片
	 */
	public static void doChoicePhoto(Activity activty, int requestCode) {
		Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
		openAlbumIntent.putExtra(ImageColumns.ORIENTATION, 0);
		openAlbumIntent.putExtra("return-data", true);
		openAlbumIntent.setType("image/*");
		// Intent intent = new Intent(Intent.ACTION_PICK,
		// android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// intent.addCategory(Intent.CATEGORY_OPENABLE);
		// intent.setType("image/*");
		// intent.putExtra("return-data", true);
		activty.startActivityForResult(openAlbumIntent, requestCode);
	}

	/**
	 * 拍照
	 */
	public static void doTakePhoto(Activity activity, int requestCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// action is
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 拍照
	 */
	public static void doTakePhoto(Activity activity, Uri imageUri,
			int requestCode) {
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//		intent.putExtra(ImageColumns.ORIENTATION, 0);
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 生成拍照缓存文件
	 */
	public static Uri generateImageUri() {
		File dir = new File(YiFileUtils.getStorePath() + "tootooplus/im");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd-HH-mm-ss-SSS");

		File file = new File(dir.getAbsolutePath() + "/"
				+ format.format(Calendar.getInstance().getTime()) + ".jpg");
		return Uri.fromFile(file);
	}

	public static File getVoiceCacheFile(String name) {
		File dir = new File(YiFileUtils.getStorePath() + "tootooplus/voice");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir.getAbsolutePath() + "/" + name);
		return file;
	}

	// public sta

	/**
	 * 从本地选择视频
	 */
	public static void doChoiceVideo(Activity activty, int requestCode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("video/*");
		intent.putExtra("return-data", true);
		activty.startActivityForResult(intent, requestCode);
	}

	/**
	 * 获取照片路径
	 */
	public static String getPath(Context context, Uri uri) {// content://com.android.providers.media.documents/document/image%3A79959
		ContentResolver cr = context.getContentResolver();
		String strPath = "";
		Cursor c = null;
		try {
			if ("content".equalsIgnoreCase(uri.getScheme())) {
				c = cr.query(uri,
						new String[] { MediaStore.Images.Media.DATA }, null,
						null, null);
				if (c != null && c.getCount() > 0) {
					if (c.moveToFirst()) {
						strPath = c.getString((c
								.getColumnIndex(MediaStore.Images.Media.DATA)));
					}
				}
			} else {
				strPath = uri.toString();
			}

			if (strPath.length() != 0) {
				if (strPath.startsWith("file:")) {
					strPath = strPath.replaceFirst("file:", "");
				}
			}
		} catch (Exception ex) {

		} finally {
			if (c != null) {
				c.close();
				c = null;
			}
		}
		return strPath;
	}
}
