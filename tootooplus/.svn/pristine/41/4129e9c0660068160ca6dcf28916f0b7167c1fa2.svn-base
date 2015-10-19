package com.ninetowns.tootooplus.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.lidroid.xutils.http.ResponseInfo;

public class ParserUitils {
	
	
	public static boolean isSuccess(ResponseInfo<String> responseInfo){
		if(!TextUtils.isEmpty(responseInfo.result)){
			try {
				JSONObject object=new JSONObject(responseInfo.result);
				if(null!=object&&object.has("Status")){
					int status=object.getInt("Status");
					if(1==status){
						return true;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return false;
		
	}

}
