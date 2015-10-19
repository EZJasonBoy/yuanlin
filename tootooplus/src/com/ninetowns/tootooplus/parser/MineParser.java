package com.ninetowns.tootooplus.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.MineBean;

public class MineParser extends AbsParser<MineBean> {

	public MineParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MineBean getParseResult(String netStr) {
		// TODO Auto-generated method stub
		MineBean mineBean = new MineBean();
		try {
			JSONObject jsonObj = new JSONObject(netStr);
			if(jsonObj.has("Status") && jsonObj.getString("Status").equals("1")){
				
				if(jsonObj.has("Data")){
					JSONObject jsonData = jsonObj.getJSONObject("Data");
					if(jsonData.has("UserId")){
						mineBean.setMine_userId(jsonData.getString("UserId"));
					}
					if(jsonData.has("UserName")){
						mineBean.setMine_userName(jsonData.getString("UserName"));
					}
					if(jsonData.has("LogoUrl")){
						mineBean.setMine_logoUrl(jsonData.getString("LogoUrl"));
					}
					
					if(jsonData.has("BusinessStatus")){
						mineBean.setMine_businessStatus(jsonData.getString("BusinessStatus"));
					}
					
					if(jsonData.has("UserGrade")){
						mineBean.setMine_userGrade(jsonData.getString("UserGrade"));
					}
					
					if(jsonData.has("Verified")){
						mineBean.setMine_Verified(jsonData.getString("Verified"));
					}
					
					if(jsonData.has("CoverImage")){
						mineBean.setMine_CoverImage(jsonData.getString("CoverImage"));
					}
					
					if(jsonData.has("Attend")){
						mineBean.setMine_Attend(jsonData.getString("Attend"));
					}
					
					if(jsonData.has("Fans")){
						mineBean.setMine_Fans(jsonData.getString("Fans"));
					}
					
					if(jsonData.has("Relation")){
						mineBean.setMine_Relation(jsonData.getString("Relation"));
					}
					
					if(jsonData.has("SubmitStatus")){
						mineBean.setMine_submitStatus(jsonData.getString("SubmitStatus"));
					}
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return mineBean;
	}

}
