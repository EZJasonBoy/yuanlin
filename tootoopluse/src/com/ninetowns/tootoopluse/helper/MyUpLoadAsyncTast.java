package com.ninetowns.tootoopluse.helper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.FileFolderUtils;
import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.UpLoadFileBean;
import com.ninetowns.tootoopluse.fragment.UpLoadViewDialogFragment;
import com.ninetowns.tootoopluse.helper.CustomMultiPartEntityHelper.ProgressListener;

public class MyUpLoadAsyncTast extends
		AsyncTask<Integer, Integer, UpLoadFileBean> {
	private ProgressDialog mProgressDialog;
	private Bundle mBundle;
	private long mTotalSize;
	private Context mContext;
	private String url;
	private HashMap<String, String> map;
	private String tag = "[MyUpLoadAsyncTast]";
	private Handler mHandler;
	private String imageFileUrl;
	private UpLoadViewDialogFragment mUpDialog;
	private static final int COUNT_PAGE = 2;

	public String getImageFileUrl() {
		return imageFileUrl;
	}

	public void setImageFileUrl(String imageFileUrl) {
		this.imageFileUrl = imageFileUrl;
	}

	private int position;
	private File mFile;
	private ProgressDialog mPb;

	/********* 记录上传显示多少张图片 **********/
	private Handler mLocalHanlder = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case COUNT_PAGE:
				int position = (Integer) msg.obj;
				mProgressDialog.setMessage(mContext.getResources().getString(
						R.string.uploading)
						+ position + "张图片");
				break;

			default:
				break;
			}

		};

	};
	private int countpage = 0;
	private int count = 0;
	private File ftmp;

	public MyUpLoadAsyncTast(Context context, String url, File file,
			HashMap<String, String> map, Handler handler,
			UpLoadViewDialogFragment upDialog, ProgressDialog pb, ImageView iv) {
		this.mContext = context;
		this.map = map;
		this.url = url;
		this.mHandler = handler;
		this.mFile = file;
		this.mUpDialog = upDialog;
		this.mProgressDialog = pb;

	}

	/**
	 * @param context
	 * @param url
	 * @param listFile
	 * @param map
	 * @param handler
	 * @param upDialog
	 * @param bundle
	 *            判断上传的是编辑、创建、还是封面图、草稿
	 */
	public MyUpLoadAsyncTast(Context context, String url, File file,
			HashMap<String, String> map, Handler handler,
			UpLoadViewDialogFragment upDialog, Bundle bundle, ProgressDialog pb) {
		this.mContext = context;
		this.map = map;
		this.url = url;
		this.mHandler = handler;
		this.mUpDialog = upDialog;
		this.mBundle = bundle;
		this.mFile = file;
		this.mProgressDialog = pb;

	}

	@Override
	protected UpLoadFileBean doInBackground(Integer... params) {
		count = params[0];
		countpage = params[1];
		Message msg = Message.obtain();
		msg.obj = count + 1;
		msg.what = COUNT_PAGE;
		mLocalHanlder.sendMessage(msg);
		return getNetDataResult(count);
	}

	private UpLoadFileBean getNetDataResult(int count) {
		UpLoadFileBean mUpLoaderFileBean = null;
		// File ftmp = null;
		HttpParams params = new BasicHttpParams();
		// 设置一些基本参数
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpProtocolParams
				.setUserAgent(
						params,
						"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
								+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
		// 超时设置
		/* 从连接池中取连接的超时时间 */
		ConnManagerParams.setTimeout(params, 8000);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(params, 8000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(params, 8000);

		// 设置我们的HttpClient支持HTTP和HTTPS两种模式
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));

		// 使用线程安全的连接管理来创建HttpClient
		ThreadSafeClientConnManager conMgr = new ThreadSafeClientConnManager(
				params, schReg);
		DefaultHttpClient httpClient = new DefaultHttpClient(conMgr, params);
		// DefaultHttpClient httpClient = new DefaultHttpClient(params);
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url);
		try {
			CustomMultiPartEntityHelper multipartContent = new CustomMultiPartEntityHelper(
					new ProgressListener() {
						@Override
						public void transferred(long num) {//
							publishProgress((int) ((num / (float) mTotalSize) * 100));
						}
					});

			if (mFile != null && mFile.exists()) {
				strinkImage(multipartContent);
			}
			setParamPart(multipartContent, count + 1);
			mTotalSize = multipartContent.getContentLength();
			httpPost.setEntity(multipartContent);
			HttpResponse response = httpClient.execute(httpPost, httpContext);
			String json;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity reentity = response.getEntity();
				if (reentity != null) {
					json = EntityUtils.toString(reentity, "utf-8");
					mUpLoaderFileBean = updata(json);// 进度条销毁了

				}
				if (reentity != null) {
					reentity.consumeContent();
					reentity = null;
				}
			} else {
				System.err.println("error"
						+ response.getStatusLine().getStatusCode());
			}
			return mUpLoaderFileBean;
		}

		catch (Exception e) {
			LogUtil.systemlogError(tag, "doInBackground 上传失败" + e.toString());
		} finally {
			if (httpClient != null) {
				LogUtil.systemlogError(tag, "关闭http的连接");
				httpClient.clearResponseInterceptors();// 关闭连接
				httpClient.clearRequestInterceptors();
				httpClient = null;
				LogUtil.info(tag, "删除临时文件");
				FileFolderUtils.deleteFile(ftmp.getAbsolutePath());
			}
		}

		return mUpLoaderFileBean;
	}

	/**
	 * @Title: strinkImage
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void strinkImage(CustomMultiPartEntityHelper multipartContent)
			throws IOException {
		ftmp = new File(ImageUtil.getTempPhotoPath());
		// 图片压缩
		saveBitmapToFile(BitmapFactory.decodeFile(mFile.getPath()),
				ftmp.getAbsolutePath());
		LogUtil.systemlogInfo("压缩后图片大小", ftmp.length());
		multipartContent.addPart("File1", new FileBody(ftmp, "image/jpeg",
				"utf-8"));
	}

	/**
	 * 将文件解析为bitmap
	 */
	public static void saveBitmapToFile(Bitmap bitmap, String _file)
			throws IOException {
		BufferedOutputStream os = null;
		try {
			File file = new File(_file);
			int end = _file.lastIndexOf(File.separator);
			String _filePath = _file.substring(0, end);
			File filePath = new File(_filePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			os = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private UpLoadFileBean updata(String ui) {
		UpLoadFileBean upLoad = null;
		if (!StringUtils.isEmpty(ui)) {
			try {
				JSONObject jsobj = new JSONObject(ui);
				if (jsobj.has("Status")) {
					String status = jsobj.getString("Status");
					if (status.equals("1")) {
						if (jsobj.has("Data")) {
							upLoad = new UpLoadFileBean();
							String datastr = jsobj.getString("Data");
							LogUtil.error(tag + "返回上传数据 ", datastr);
							if (!StringUtils.isEmpty(datastr)) {
								JSONObject objData = new JSONObject(datastr);
								if (objData.has("FileUrl")) {
									upLoad.setFileUrl(objData
											.getString("FileUrl"));
								}
								if (objData.has("ThumbFileUrl")) {
									upLoad.setThumbFileUrl(objData
											.getString("ThumbFileUrl"));
								}
								if (objData.has("ListCoverImg")) {
									upLoad.setListCoverImg(objData
											.getString("ListCoverImg"));
								}
								if (objData.has("DragRectangleImg")) {
									upLoad.setDragRectangleImg(objData
											.getString("DragRectangleImg"));

								}
								if (objData.has("DragSquareImg")) {
									upLoad.setDragSquareImg(objData
											.getString("DragSquareImg"));

								}
								if (objData.has("TailorSquareImg")) {
									upLoad.setTailorSquareImg(objData
											.getString("TailorSquareImg"));

								}
								if (objData.has("ImgFileUrl")) {
									upLoad.setImageFileUrl(objData
											.getString("ImgFileUrl"));
								}
								if (objData.has("DragSquareBigImg")) {
									upLoad.setDragSquareBigImg(objData
											.getString("DragSquareBigImg"));
								}
								if (objData.has("DragRectangleBigImg")) {
									upLoad.setDragRectangleBigImg(objData
											.getString("DragRectangleBigImg"));
								}
								if (objData.has("DefaultType")) {
									upLoad.setDefaultType(objData
											.getString("DefaultType"));
								}

							} else {
								ComponentUtil.showToast(
										mContext,
										mContext.getResources().getString(
												R.string.upload_error));
							}
						}
					} else if (status.equals("1002")) {
						upLoad = null;
						ComponentUtil.showToast(
								mContext,
								mContext.getResources().getString(
										R.string.upload_error_overbig));
					} else if (status.equals("1003")) {
						upLoad = null;
						ComponentUtil.showToast(
								mContext,
								mContext.getResources().getString(
										R.string.upload_error_type));
					} else {
						upLoad = null;
						LogUtil.systemlogError(
								tag,
								status
										+ mContext.getResources().getString(
												R.string.upload_error));
					}

				}

			} catch (JSONException e) {
				LogUtil.error(tag + "updata json error", e.toString());
				e.printStackTrace();
			}
		} else {
			LogUtil.error(tag + "返回的json ", "null ");
		}
		return upLoad;
	}

	public void setParamPart(CustomMultiPartEntityHelper multipartContent,
			int count) {
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				try {
					multipartContent.addPart(entry.getKey(), new StringBody(
							entry.getValue()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// mProgressDialog = new ProgressDialog(mContext);
		 mProgressDialog.setMessage(mContext.getResources().getString(
		 R.string.uploading)
		 + "第" + position + "张图片");
		// mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// mProgressDialog.setCancelable(true);
		mProgressDialog.show();

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		System.out.println("服务关闭");
	}

	@Override
	protected void onPostExecute(UpLoadFileBean result) {
		super.onPostExecute(result);
		count++;
		countpage--;
		Message msg = Message.obtain();
		msg.obj = result;
		msg.arg1 = count;
		msg.arg2 = countpage;
		msg.what = UpLoadViewDialogFragment.ADD_FILE_LIST;
		mHandler.sendMessage(msg);

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		mProgressDialog.setProgress((values[0]));
	}

}
