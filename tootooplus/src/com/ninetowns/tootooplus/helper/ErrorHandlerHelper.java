package com.ninetowns.tootooplus.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootooplus.util.TimeUtil;

/**
 * 
 * @ClassName: ErrorHandlerHelper
 * @Description: 异常捕获
 * @author wuyulong
 * @date 2015-4-2 上午11:06:14
 * 
 */
public class ErrorHandlerHelper implements UncaughtExceptionHandler {

	public static final String TAG = "ErrorHandler";

	private static ErrorHandlerHelper INSTANCE = new ErrorHandlerHelper();

	private Context mContext;

	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private Map<String, String> infos = new HashMap<String, String>();

	private String path;

	private ErrorHandlerHelper() {
	}

	public static ErrorHandlerHelper getInstance() {
		return INSTANCE;
	}

	public void init(Context context) {
		mContext = context;

		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
			LogUtil.error("throwable", "ex是"+ex.getMessage());
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}

			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 
	 * @Title: createDialogError
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void createDialogError(Throwable ex) {
		if (onListenerError != null) {
			onListenerError.onListenerError(ex);
		}
	}

	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}

		// 使用 Toast 来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				
				Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出", Toast.LENGTH_LONG)
						.show();
				LogUtil.error("错误run", ex.getMessage()+"");
				Looper.loop();
			}
		}.start();
		LogUtil.error("错误信息", ex.getMessage());
		LogUtil.systemlogError("错误信息", ex.getMessage());
		createDialogError(ex);
		collectDeviceInfo(mContext);
		// 保存日志文件
		String fileName = saveCrashInfo2File(ex);
		System.out.println("filename ===="+fileName);
		// httpUploadFile(fileName);

		return true;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);

			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}

		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		String fileName = null;
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();

		String result = writer.toString();
		sb.append(result);
		try {
//			long timestamp = System.currentTimeMillis();
//			String time = String.valueOf(new Date().getTime());
			String timestamp=TimeUtil.formatDate(new Date());
			fileName = "Error" + "-" + timestamp + ".txt";

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				path = "/mnt/sdcard/TooToo/ERROR/";
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}

			return path + fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}

		return fileName;
	}

	/**
	 * 
	 * @ClassName: OnListenerError
	 * @Description: 错误信息布局监听器
	 * @author wuyulong
	 * @date 2015-4-2 上午11:28:06
	 * 
	 */
	public interface OnListenerError {
		public void onListenerError(Throwable ex);
	}

	private OnListenerError onListenerError;

	/**
	 * 
	 * @Title: setOnListenerError
	 * @Description: 设置错误信息监听器
	 * @param
	 * @return
	 * @throws
	 */
	public void setOnListenerError(OnListenerError onListenerError) {
		this.onListenerError = onListenerError;
	}
	/**
	 * TODO 发送到服务器
	 * 
	 * @param filePath
	 *            void
	 */
	// private void httpUploadFile(String filePath)
	// {
	// String serverurl = "http://"+":28480/upload.jsp"; //服务器地址
	// try{
	// File file = new File(filePath);
	// URL url = new URL(serverurl);
	// HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	// connection.setDoInput(true);
	// connection.setDoOutput(true);
	// connection.setChunkedStreamingMode(1024*1024);
	// connection.setRequestMethod("POST");
	// connection.setRequestProperty("Charset", "UTF-8");
	// connection.setRequestProperty("filename", file.getName());
	//
	// DataOutputStream out = new
	// DataOutputStream(connection.getOutputStream());
	//
	// DataInputStream in = new DataInputStream(new FileInputStream(file));
	// byte[] bytes = new byte[1024];
	// int num = 0;
	// while((num = in.read(bytes))!=-1){
	// System.out.println("发送字节==="+num);
	// out.write(bytes,0,num);
	// }
	// in.close();
	// out.flush();
	// out.close();
	//
	//
	// BufferedReader reader = new BufferedReader(new
	// InputStreamReader(connection.getInputStream()));
	// String line = null;
	// while((line = reader.readLine())!=null){
	// System.out.println(line.toString());
	// }
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }

}