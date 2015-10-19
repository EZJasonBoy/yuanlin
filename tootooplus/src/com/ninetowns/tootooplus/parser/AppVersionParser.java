package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.tootooplus.bean.AppVersionBean;

public class AppVersionParser {
	
	public AppVersionBean parserVersion(String jsonStr) throws JSONException{
		AppVersionBean versionBean = new AppVersionBean();
		JSONObject jsonObj = new JSONObject(jsonStr);
		
		if(jsonObj.has("Status")){
			versionBean.setVersion_status(jsonObj.getString("Status"));
		}
		
		if(jsonObj.has("Message")){
			versionBean.setVersion_msg(jsonObj.getString("Message"));
		}
		
		if(jsonObj.has("UpgradeType")){
			versionBean.setVersion_upgradeType(jsonObj.getString("UpgradeType"));
		}
		
		if(jsonObj.has("DownloadUrl")){
			versionBean.setVersion_downloadUrl(jsonObj.getString("DownloadUrl"));
		}
		
		if(jsonObj.has("ActivityType")){
			List<String> typeList = new ArrayList<String>();
			JSONArray typeArray = jsonObj.getJSONArray("ActivityType");
			for(int i = 0; i < typeArray.length(); i++){
				JSONObject jsonItem = typeArray.getJSONObject(i);
				if(jsonItem.has("Type")){
					typeList.add(jsonItem.getString("Type"));
				}
			}
			
			versionBean.setVersion_lineTypes(typeList);
		}
		
		return versionBean;
	}

}
