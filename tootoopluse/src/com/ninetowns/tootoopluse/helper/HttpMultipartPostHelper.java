package com.ninetowns.tootoopluse.helper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
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
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.UpLoadFileBean;
import com.ninetowns.tootoopluse.fragment.UpLoadViewDialogFragment;
import com.ninetowns.tootoopluse.helper.CustomMultiPartEntityHelper.ProgressListener;

public class HttpMultipartPostHelper extends
		AsyncTask<HttpResponse, Integer, String> {
	ProgressDialog pd;
	long totalSize;
	Context context;
	private String json;
	private File file;
	private String url;
	private HashMap<String, String> map;
	private DefaultHttpClient httpClient;
	private UpLoadFileBean upLoad;
	private String tag = "[HttpMultipartPost]";
	private Handler handler;
	private File imageFile;
	private UpLoadViewDialogFragment uploadDialog;
	private boolean isRecommendVideo;

	public boolean isRecommendVideo() {
		return isRecommendVideo;
	}

	public void setRecommendVideo(boolean isRecommendVideo) {
		this.isRecommendVideo = isRecommendVideo;
	}

	private int count = 0;

	public HttpMultipartPostHelper(Context context, String url, File imageFile,
			File file, HashMap<String, String> map, Handler handler) {
		this.context = context;
		this.map = map;
		this.url = url;
		this.handler = handler;
		this.file = file;
		this.imageFile = imageFile;
	}

	public HttpMultipartPostHelper(Context context, String url, File imageFile,
			File file, HashMap<String, String> map, Handler handler,
			UpLoadViewDialogFragment uploadDialog) {
		this.context = context;
		this.map = map;
		this.url = url;
		this.handler = handler;
		this.file = file;
		this.imageFile = imageFile;
		this.uploadDialog = uploadDialog;
	}

	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage(context.getResources().getString(R.string.uploading));
		pd.setCancelable(false);
		pd.show();
	}

	@Override
	protected String doInBackground(HttpResponse... arg0) {
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
		ConnManagerParams.setTimeout(params, 4000);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(params, 4000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(params, 8000);

		// 设置我们的HttpClient支持HTTP和HTTPS两种模式
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));

		// 使用线程安全的连接管理来创建HttpClient
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				params, schReg);
//		httpClient = new DefaultHttpClient(conMgr, params);
		
		httpClient = new DefaultHttpClient(params);
		
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url);
		try {
			CustomMultiPartEntityHelper multipartContent = new CustomMultiPartEntityHelper(
					new ProgressListener() {
						@Override
						public void transferred(long num) {
							publishProgress((int) ((num / (float) totalSize) * 100));
						}
					});
			if (file.exists()) {
				LogUtil.systemlogInfo("视频大小", file.length());
				multipartContent.addPart("File1", new FileBody(file,
						"video/mp4", "utf-8"));

			}
			if (imageFile.exists()) {
				LogUtil.systemlogInfo("图片大小", imageFile.length());
				multipartContent.addPart("File2", new FileBody(imageFile,
						"image/jpeg", "utf-8"));
			}
			count++;
			setMapParams(multipartContent, count);
			// We use FileBody to transfer an VIDEO
			totalSize = multipartContent.getContentLength();

			// Send it
			httpPost.setEntity(multipartContent);
			HttpResponse response = httpClient.execute(httpPost, httpContext);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity reentity = response.getEntity();
				if (reentity != null) {
					json = EntityUtils.toString(reentity, "utf-8");
				}
				if (reentity != null) {
					reentity.consumeContent();
				}

			}

			return json;
		}

		catch (Exception e) {
			LogUtil.systemlogError(tag, "doInBackground 上传失败" + e.toString());
		}
		return json;
	}

	public void setMapParams(CustomMultiPartEntityHelper multipartContent,
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
	protected void onProgressUpdate(Integer... progress) {
		pd.setProgress((progress[0]));
	}

	@Override
	protected void onPostExecute(String ui) {
		if (uploadDialog != null) {
			uploadDialog.dismiss();
		}
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		}
		if (!StringUtils.isEmpty(ui)) {
			try {
				JSONObject jsobj = new JSONObject(ui);
				if (jsobj.has("Status")) {
					String status = jsobj.getString("Status");
					if (status.equals("1")) {
						if (jsobj.has("Data")) {
							upLoad = new UpLoadFileBean();
							String datastr = jsobj.getString("Data");
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
								Message msg = Message.obtain();
								msg.what = TootooeNetApiUrlHelper.UPLOAD_VIDEO;
								msg.obj = upLoad;
								handler.sendMessage(msg);
							} else {
								ComponentUtil.showToast(
										context,
										context.getResources().getString(
												R.string.upload_error));
							}
						}
					} else if (status.equals("1002")) {
						ComponentUtil.showToast(context, context.getResources()
								.getString(R.string.upload_error_overbig));
					} else if (status.equals("1003")) {
						ComponentUtil.showToast(context, context.getResources()
								.getString(R.string.upload_error_type));
					} else {
						LogUtil.systemlogError(
								tag,
								status
										+ context.getResources().getString(
												R.string.upload_error));
					}

				}

			} catch (JSONException e) {
				LogUtil.error(tag, e.toString());
				e.printStackTrace();
			}
		}
		pd.dismiss();
	}

}