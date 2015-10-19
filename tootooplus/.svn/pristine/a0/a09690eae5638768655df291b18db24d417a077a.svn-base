package com.ninetowns.tootooplus.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.tootooplus.bean.LoginBean;

public class LoginParser {
	
	public LoginBean parserLogin(String jsonStr) throws JSONException{
		LoginBean loginBean = new LoginBean();
		JSONObject jsonObj = new JSONObject(jsonStr);
		if(jsonObj.has("Status")){
			loginBean.setLogin_status(jsonObj.getString("Status"));
		}
		
		if(jsonObj.has("Data")){
			JSONObject jsonData = jsonObj.getJSONObject("Data");
			if(jsonData.has("UserId")){
				loginBean.setLogin_Id(jsonData.getString("UserId"));
			}
			if(jsonData.has("UserName")){
				loginBean.setLogin_name(jsonData.getString("UserName"));
			}
			if(jsonData.has("LogoUrl")){
				loginBean.setLogin_logoUrl(jsonData.getString("LogoUrl"));
			}
			
			if(jsonData.has("LoginType")){
				loginBean.setLogin_type(jsonData.getString("LoginType"));
			}
			
			if(jsonData.has("PublishStory")){
				loginBean.setLogin_publishStory(jsonData.getString("PublishStory"));
			}
			
			if(jsonData.has("IsOneLogin")){
				loginBean.setIsOneLogin(jsonData.getString("IsOneLogin"));
			}
			
			if(jsonData.has("BusinessStatus")){
				loginBean.setLogin_businessStatus(jsonData.getString("BusinessStatus"));
			}
			
			if(jsonData.has("Medal")){
				loginBean.setLogin_medal(jsonData.getString("Medal"));
			}
			
			if(jsonData.has("TCurrency")){
				loginBean.setLogin_tCurrency(jsonData.getString("TCurrency"));
			}
			
			if(jsonData.has("IsLOHAS")){
				loginBean.setLogin_isLOHAS(jsonData.getString("IsLOHAS"));
			}
			
			if(jsonData.has("UserGrade")){
				loginBean.setLogin_userGrade(jsonData.getString("UserGrade"));
			}
			
			if(jsonData.has("CoverImage")){
				loginBean.setLogin_coverImage(jsonData.getString("CoverImage"));
			}
		}
		
		return loginBean;
	}
	
	
	public String parserWechat(String wechatStr) throws JSONException{
		JSONObject jsonObj = new JSONObject(wechatStr);
		String unionid = "";
		if(jsonObj.has("unionid")){
			unionid = jsonObj.getString("unionid");
		}
		
		return unionid;
	}
}
