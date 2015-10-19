package com.ninetowns.tootooplus.helper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
import android.os.Handler;
import android.os.Message;

import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.FileFolderUtils;
import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.UpLoadFileBean;
import com.ninetowns.tootooplus.fragment.UpLoadViewDialogFragment;
import com.ninetowns.tootooplus.helper.CustomMultiPartEntityHelper.ProgressListener;

/**
 * 
* @Title: ImageHttpMultipartPost.java  
* @Description: 多图片上传 
* @author wuyulong
* @date 2015-1-8 上午9:59:44  
* @version V1.0
 */
public class ImageHttpMultipartPostHelper extends
        AsyncTask<HttpResponse, Integer, String> {
    ProgressDialog pd;
    long totalSize;
    Context context;
    private String json;
    private File file;
    private String url;
    private HashMap<String, String> map;
    private UpLoadFileBean upLoad;
    private String tag = "[ImageHttpMultipartPostHelper]";
    private Handler handler;
    private String imageFileUrl;
    public String getImageFileUrl() {
        return imageFileUrl;
    }
    public void setImageFileUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
    }
    List<UpLoadFileBean> list = new ArrayList<UpLoadFileBean>();
    private File imageFile;
    private File imageFiellist;
    private int position;
    private int count=0;
    List<File> listFile;
    
    private int iv_mark = 0;
    
    /*********记录上传显示多少张图片**********/
    private Handler localHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 2:
                int position = (Integer) msg.obj;
                pd.setMessage(context.getResources().getString(
                        R.string.uploading)
                        + position + "张图片");
                break;

            default:
                break;
            }

        };

    };

    public ImageHttpMultipartPostHelper(Context context, String url, File imageFile,
            File file, HashMap<String, String> map, Handler handler) {
        this.context = context;
        this.map = map;
        this.url = url;
        this.handler = handler;
        this.file = file;
        this.imageFile = imageFile;
    }
    
    
    public ImageHttpMultipartPostHelper(Context context, String url,
            File imageFiellist, HashMap<String, String> map, Handler handler,
            int position, int count) {
        this.context = context;
        this.map = map;
        this.url = url;
        this.handler = handler;
        this.imageFiellist = imageFiellist;
        this.position = position;
        this.count = count;
    }

    Lock lock = new ReentrantLock();//多线程锁
    public ImageHttpMultipartPostHelper(Context context, String url,
            List<File> listFile, HashMap<String, String> map, Handler handler) {
        this.context = context;
        this.map = map;
        this.url = url;
        this.handler = handler;
        this.listFile = listFile;

    }
    
    
    public ImageHttpMultipartPostHelper(Context context, String url,
            List<File> listFile, HashMap<String, String> map, Handler handler, int iv_mark) {
        this.context = context;
        this.map = map;
        this.url = url;
        this.handler = handler;
        this.listFile = listFile;
        this.iv_mark = iv_mark;
    }
    
    private UpLoadViewDialogFragment  upDialog;
    public ImageHttpMultipartPostHelper(Context context, String url,
            List<File> listFile, HashMap<String, String> map, Handler handler,UpLoadViewDialogFragment  upDialog) {
        this.context = context;
        this.map = map;
        this.url = url;
        this.handler = handler;
        this.listFile = listFile;
        this.upDialog=upDialog;

    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setMessage(context.getResources().getString(R.string.uploading)
                + "第" + position + "张图片");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(HttpResponse... arg0) {
     
        for (int i = 0; i < listFile.size(); i++) {//
            File file = listFile.get(i);
            Message msg = Message.obtain();
            msg.what = 2;
            msg.obj = i + 1;
            localHanlder.sendMessage(msg);
            lock.lock();
            try {
                json = rededata(file);
                LogUtil.systemlogInfo("doInBackground", json);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        if (list != null && list.size() > 0) {
            Message msg = Message.obtain();
            msg.what = TootooeNetApiUrlHelper.UPLOAD_VIDEO;
            msg.obj = list;
            msg.arg1 = iv_mark;
            handler.sendMessage(msg);
            LogUtil.systemlogInfo(tag, json);
        } else {
            LogUtil.systemlogError("list是null", "获取的list数据是null");
        }
//        if(upDialog!=null){
//            upDialog.dismiss();
//        }
        pd.dismiss();
        return json;
    }

    
    
    private String rededata(File imageFiellist) {
    	File ftmp=null;
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
        ThreadSafeClientConnManager conMgr = new ThreadSafeClientConnManager(
                params, schReg);
        DefaultHttpClient httpClient = new DefaultHttpClient(conMgr, params);
        HttpContext httpContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(url);
        try {
            CustomMultiPartEntityHelper multipartContent = new CustomMultiPartEntityHelper(
                    new ProgressListener() {
                        @Override
                        public void transferred(long num) {//
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });
            if (file != null && file.exists()) {
                LogUtil.systemlogInfo("视频大小", file.length());
                multipartContent.addPart("File1", new FileBody(file,
                        "video/mp4", "utf-8"));

            }
            if (imageFile != null && imageFile.exists()) {
                LogUtil.systemlogInfo("图片大小", imageFile.length());
                multipartContent.addPart("File2", new FileBody(imageFile,
                        "image/jpeg", "utf-8"));
            }
            if (imageFiellist != null && imageFiellist.exists()) {
                LogUtil.systemlogInfo("图片大小", imageFiellist.length());
                
                ftmp = new File(ImageUtil.getTempPhotoPath());
              //图片压缩
				saveBitmapToFile(
						BitmapFactory.decodeFile(imageFiellist.getPath()),ftmp.getAbsolutePath());
				LogUtil.systemlogInfo("压缩后图片大小", ftmp.length());
                multipartContent.addPart("File1", new FileBody(ftmp,
                        "image/jpeg", "utf-8"));
            }
        
            count++;
            LogUtil.systemlogInfo("第几次", count);
            setParamPart(multipartContent,count);
            // We use FileBody to transfer an VIDEO
            totalSize = multipartContent.getContentLength();
            // totalSize = multipartContent.getContentLength();
            // 总

            // Send it
            httpPost.setEntity(multipartContent);
            HttpResponse response = httpClient.execute(httpPost, httpContext);
//            Thread.sleep(500);// asynctast 存在线程安全问题
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity reentity = response.getEntity();
//                FileFolderUtils.deleteFile(ftmp.getAbsolutePath());
                if (reentity != null) {
                    json = EntityUtils.toString(reentity, "utf-8");
                    updata(json);// 进度条销毁了
                    LogUtil.systemlogError("json", json);
                    if (upLoad != null) {
                        list.add(upLoad);
                    }else{
                        LogUtil.systemlogInfo("upload数据", "null");  
                    }
                }
                if (reentity != null) {
                    reentity.consumeContent();
                    reentity = null;
                }
            }else{
            	if(upLoad!=null){
            		upLoad=null;
            	}
            }
            return json;
        }

        catch (Exception e) {
        	if(upLoad!=null){
        		upLoad=null;
        	}
            LogUtil.systemlogError(tag, "doInBackground 上传失败" + e.toString());
        } finally {
            if (httpClient != null) {
                LogUtil.systemlogError(tag, "关闭http的连接");
                httpClient.clearResponseInterceptors();// 关闭连接
                httpClient.clearRequestInterceptors();
                httpClient = null;
                LogUtil.info(tag, "删除临时文件");
                if(ftmp!=null)
                FileFolderUtils.deleteFile(ftmp.getAbsolutePath());
            }
        }
        return json;
    }
/**
 * 
* @Title: ImageHttpMultipartPost.java  
* @Description:子类可以去覆盖这个方法 来设置自己的参数
* @author wuyulong
* @date 2015-1-4 下午3:41:01  
* @param 
* @return void
 */
    public void setParamPart(CustomMultiPartEntityHelper multipartContent,int count) {
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                try {
                    multipartContent.addPart(entry.getKey(),
                            new StringBody(entry.getValue()));
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
/**
 * 
* @Title: ImageHttpMultipartPost.java  
* @Description: 解析返回的数据 并且设置到UpLoadFileBean 对象中
* @author wuyulong
* @date 2015-1-4 下午3:42:02  
* @param 
* @return void
 */
    private void updata(String ui) {
        if (!StringUtils.isEmpty(ui)) {
            try {
                JSONObject jsobj = new JSONObject(ui);
                if (jsobj.has("Status")) {
                    String status = jsobj.getString("Status");
                    LogUtil.error(tag+"返回的json  状态 ", status);
                    if (status.equals("1")) {
                        if (jsobj.has("Data")) {
                            upLoad = new UpLoadFileBean();
                            String datastr = jsobj.getString("Data");
                            LogUtil.error(tag+"返回上传数据 ", datastr);
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
                                    upLoad.setListCoverImg(objData.getString("ListCoverImg"));
                                }
                                if (objData.has("DragRectangleImg")) {
                                    upLoad.setDragRectangleImg(objData.getString("DragRectangleImg"));

                                }
                                if (objData.has("DragSquareImg")) {
                                    upLoad.setDragSquareImg(objData.getString("DragSquareImg"));

                                }
                                if(objData.has("TailorSquareImg")){
                                    upLoad.setTailorSquareImg(objData.getString("TailorSquareImg"));
                                    
                                }
                                if(objData.has("ImgFileUrl")){
                                    upLoad.setImageFileUrl(objData.getString("ImgFileUrl"));
                                }
                                if(objData.has("DragSquareBigImg")){
                                    upLoad.setDragSquareBigImg(objData.getString("DragSquareBigImg"));
                                }
                                if(objData.has("DragRectangleBigImg")){
                                    upLoad.setDragRectangleBigImg(objData.getString("DragRectangleBigImg"));
                                }
                                if(objData.has("DefaultType")){
                                    upLoad.setDefaultType(objData.getString("DefaultType"));
                                }
                                    

                            } else {
                                ComponentUtil.showToast(
                                        context,
                                        context.getResources().getString(
                                                R.string.upload_error));
                            }
                        }
                    } else if (status.equals("1002")) {
                    	if(upLoad!=null){
                    		upLoad=null;
                    	}
                        ComponentUtil.showToast(context, context.getResources()
                                .getString(R.string.upload_error_overbig));
                    } else if (status.equals("1003")) {
                    	if(upLoad!=null){
                    		upLoad=null;
                    	}
                        ComponentUtil.showToast(context, context.getResources()
                                .getString(R.string.upload_error_type));
                    } else {
                    	if(upLoad!=null){
                    		upLoad=null;
                    	}
                        LogUtil.systemlogError(
                                tag,
                                status
                                        + context.getResources().getString(
                                                R.string.upload_error));
                    }

                }

            } catch (JSONException e) {
            	if(upLoad!=null){
            		upLoad=null;
            	}
                LogUtil.error(tag+"updata json error", e.toString());
                e.printStackTrace();
            }
        }else{
            LogUtil.error(tag+"返回的json ", "null ");
        }

    }
    
    
    /**
     *将文件解析为bitmap
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


    
    
}