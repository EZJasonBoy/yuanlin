package com.ninetowns.library.helper;

import com.ninetowns.library.helper.ConstantsHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BaseSharedPreferenceHelper {
	

	/**
	 * 获取沱沱加数据http地址
	 * @param context
	 * @return
	 */
	public static String getReqHttpUrl(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsHelper.RTMP_HTTP_SHARE_PREFS,Context.MODE_APPEND);
		String req_http_url = "";
		if(!sharePrefs.getString("req_http_url", "").equals("")){
			req_http_url = sharePrefs.getString("req_http_url", "");
		}
		
		return req_http_url;
	}
	
	/**
	 * 获取沱沱加rtmp地址
	 * @param context
	 * @return
	 */
	public static String getReqRtmpUrl(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsHelper.RTMP_HTTP_SHARE_PREFS,Context.MODE_APPEND);
		String req_rtmp_url = "";
		if(!sharePrefs.getString("req_rtmp_url", "").equals("")){
			req_rtmp_url = sharePrefs.getString("req_rtmp_url", "");
		}
		
		return req_rtmp_url;
	}
	
	/**
	 * 保存获取的聊天需要的rtmp地址和沱沱加数据http地址
	 * @param context
	 * @param req_rtmp_url
	 * @param req_http_url
	 */
	public static void saveRtmpHttpUrl(Context context, String req_rtmp_url, String req_http_url){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsHelper.RTMP_HTTP_SHARE_PREFS,Context.MODE_APPEND);
		Editor editor = sharePrefs.edit();
		editor.putString("req_rtmp_url", req_rtmp_url).putString("req_http_url", req_http_url).commit();
	}
	
}
