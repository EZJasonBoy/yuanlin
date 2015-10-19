package com.ninetowns.library.util;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 
 * @Title: ComponentUtil.java
 * @Description: 对一些组件的管理
 * @author wuyulong
 * @date 2014-7-14 下午4:15:14
 * @version V1.0
 */
public class ComponentUtil {
	private static ComponentUtil instance = new ComponentUtil();

	private ComponentUtil() {

	}
/**
 * 
* @Title: ComponentUtil.java  
* @Description:   
* @author wuyulong
* @date 2014-7-16 下午1:38:07  
* @param 
* @return ComponentUtil
 */
	public static ComponentUtil getInstance() {
		if (instance != null) {
			return instance;
		}
		return instance;

	}

	/**
	 * 
	 * @Title: ComponentUtil.java
	 * @Description: 吐司提示   LENGTH_LONG 3s  LENGTH_SHOT 2.5s
	 * @author wuyulong
	 * @date 2014-7-15 下午4:41:10
	 * @param
	 * @return void
	 */
	public static void showToast(Context activity, String str) {
		Toast.makeText(activity, str, Toast.LENGTH_LONG).show();

	}



	public static void showInfoDialog(Context context, String message,
			String titleStr, String positiveStr,
			DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		localBuilder.show();
	}

	static AlertDialog alertDialog;

	/** 含有标题、内容、两个按钮的对话框 **/
	public static AlertDialog showAlertDialog(Context context, String title,
			String message, String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		alertDialog = new AlertDialog.Builder(context).setTitle(title)
				.setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();

		return alertDialog;
	}



	
	
			/**
			 * 
			* @Title: ComponentUtil.java  
			* @Description: 设置全屏 
			* @author wuyulong
			* @date 2014-9-2 下午3:30:25  
			* @param 
			* @return void
			 */
		public static void setFullScreen(Activity context){
	        //设置无标题  
		    context.requestWindowFeature(Window.FEATURE_NO_TITLE);  
	        //设置全屏  
		    context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		}

	
}
